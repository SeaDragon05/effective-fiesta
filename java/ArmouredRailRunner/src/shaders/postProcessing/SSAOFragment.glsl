#version 330 core
/*
 * by far the dumbest thing ever
 * This is so stupid, honestly
 * Screen spaced ambient occlusion, if you can call it that
 * Uses the normals for a difference and calculates darkness with that
 * and other things as well because its faster to do it in one pass than two passes
 * and also because its easier to do it in this shader versus making another shader so there's that
 */
in vec2 textureCoords;

out vec4 outColor;

uniform sampler2D color;
uniform sampler2D positionCol;
uniform sampler2D depth;
uniform sampler2D normal;
uniform mat4 viewMatrix;
uniform int frame;


vec2 poissonDisk[16] = vec2[](
   vec2( -0.94201624, -0.39906216 ),
   vec2( 0.94558609, -0.76890725 ),
   vec2( -0.094184101, -0.92938870 ),
   vec2( 0.34495938, 0.29387760 ),
   vec2( -0.91588581, 0.45771432 ),
   vec2( -0.81544232, -0.87912464 ),
   vec2( -0.38277543, 0.27676845 ),
   vec2( 0.97484398, 0.75648379 ),
   vec2( 0.44323325, -0.97511554 ),
   vec2( 0.53742981, -0.47373420 ),
   vec2( -0.26496911, -0.41893023 ),
   vec2( 0.79197514, 0.19090188 ),
   vec2( -0.24188840, 0.99706507 ),
   vec2( -0.81409955, 0.91437590 ),
   vec2( 0.19984126, 0.78641367 ),
   vec2( 0.14383161, -0.14100790 )
);

float getDistance(vec4 pt1, vec4 pt2) {
	vec3 dist = vec3((pt2.x - pt1.x), (pt2.y - pt1.y), (pt2.z - pt1.z));
	return sqrt((dist.x * dist.x) + (dist.y * dist.y) + (dist.z * dist.z));
}

float random(vec3 seed4, int i) {
	float dot_product = dot(vec4(seed4, i), vec4(12.9898,78.233,45.164,94.673));
	return fract(sin(dot_product) * 43758.5453);
}

vec4 colorThing(vec4 col, float colorLoss) {
	if (col.rgb == vec3(0)) {
		col.rgb = vec3(0.01);
	}
	float brightness = 1;//((col.x + col.y + col.z) / 3);
	vec4 result = vec4(0,0,0,col.a);
	//float b = (brightness * colorLoss);
	//vec3 d = brightness - col.rgb;
	float amount = brightness * 0.05;
	//result = col * brightness;//MAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAGIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIK
	result.r = (result.r + (random(gl_FragCoord.xyy * col.rgb, frame) * amount)) + ((col.r * (col.r - col.b + col.g)) * brightness);//MORE MAGIK
	result.g = (result.g + (random(gl_FragCoord.xyy * col.rgb, frame + 1) * amount)) + ((col.g * (col.g - col.b + col.r)) * brightness);
	result.b = (result.b + (random(gl_FragCoord.xyy * col.rgb, frame + 2) * amount)) + ((col.b * (col.b - col.g + col.r)) * brightness);

	//result = result - vec4((abs(d) * colorLoss),0);
	return result;
}

vec4 colorThing1(vec4 col, float colorLoss) {
	if (col.rgb == vec3(0)) {
		col.rgb = vec3(0.01);
	}
	vec4 result = vec4(0,0,0,col.a);
	float brightness = 0;
	//float b = (brightness * colorLoss);
	//vec3 d = brightness - col.rgb;
	float amount = 0.1;
	//result = col * brightness;//MAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAGIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIK
	result.r = (result.r + (random(gl_FragCoord.xyy * col.r, frame) * amount)) + ((col.r) * (brightness + col.r));//MORE MAGIK
	result.g = (result.g + (random(gl_FragCoord.xyy * col.g, frame + 1) * amount)) + ((col.g) * (brightness + col.g));
	result.b = (result.b + (random(gl_FragCoord.xyy * col.b, frame + 2) * amount)) + ((col.b) * (brightness + col.b));

	//result = result - vec4((abs(d) * colorLoss),0);
	return result;
}


vec4 blackWhite(vec4 colo) {
	vec4 result = vec4(0,0,0,colo.a);
	result.rgb = vec3((colo.r + colo.g + colo.b) / 3);
	return result;
}

void main(void) {
	//magik goes here
	outColor = vec4(0);
	vec4 D0 = texture(depth, textureCoords);
	//D0.z = 1.0 - D0.z;
	vec4 N0 = texture(normal, textureCoords);
	vec4 P0 = texture(positionCol, textureCoords);
	vec4 C0 = texture(color, textureCoords);
	vec3 toCameraVector = (inverse(viewMatrix) * vec4(0,0,0,1)).xyz - P0.xyz;
	vec3 unitVecToCam = normalize(toCameraVector);
	float total = 0.0;
	float distanceAmount = 90;
	float amount = distanceAmount;
	float refl = 0;
	//my dumb butt DIDN't COMMENT THIS SECTION SO I HAVE NO IDEA WHAT IS GOING ON
	if (D0.z < 1) {

		for (int i=0;i<16;i+=4){
			//attempting to comment
			//get the index for the pixel we will compare
			int index = int(16.0*random(gl_FragCoord.xyy, frame))%16;
			//get the pixel with the new index and use that to ssao
			vec4 P1 = texture(positionCol, textureCoords + poissonDisk[index]/(amount));
			//TRY TO SEE IF THE NEW PIXEL IS TOO FAR
			//BUT IT DON'T WORK FOR SOME REASON
			if (P1.z > 200) {
				continue;
			}
			//COLOR!
			vec4 CL1 = texture(color, textureCoords + poissonDisk[index]/(amount/2));
			//NORMAL! IMPORTANT FOR SSAO
			vec4 NL1 = texture(normal, textureCoords + poissonDisk[index]/(amount/2));
			//WHAT
			float C1B = (CL1.x + CL1.y + CL1.z);
			//WHAAAAAAAAAAAAAAAAA?????
			if (C1B / 3 > 0.8) {
				vec3 reflectedLight = reflect(
						-N0.xyz,
						NL1.xyz);
				float specularFactor = (dot(reflectedLight, unitVecToCam) * 1);
			}
			//okay i have no idea
			vec4 N1 = texture(normal, textureCoords + poissonDisk[index]/(amount));//what is this
			vec4 delta = P1 - P0;
			float dp0 = dot(delta, N0);//what
			float dp1 = dot(delta, N1);
			float ND = getDistance(N0, N1);//WHAT
			if (dp0 >= 0 && dp1 >= 0) {//SOMETHING
				float am = (ND * D0.z) * 0.82;
				total += am;//* (1.0 - getDistance(P0, P1));//dumb fix
				//??????????
			}
		}
	}
	total/=2;
	//outColor = C0 + vec4(vec3(total *  (1.0 - D0.z)), 0);
	//outColor = colorThing(C0,1) - colorCheck(vec4(vec3((refl + total) * (1.0 - D0.z)), 0));
	//outColor = colorThing1(C0,1) - colorCheck(vec4(vec3((refl + total) * (1.0 - D0.z)), 0));
	//outColor = C0 - colorCheck(vec4(vec3((refl + total) * (1.0 - D0.z)), 0));
	//outColor = colorCheck(outColor);
	//outColor = vec4(D0.zzz/3, 1);//see the depth buffer
	//outColor = N0;//see the normals

	C0 = vec4(vec3(C0.r - total, C0.g - total, C0.b - total), 1);
	C0.r = C0.r < 0 ? 0 : C0.r;
	C0.g = C0.g < 0 ? 0 : C0.g;
	C0.b = C0.b < 0 ? 0 : C0.b;
	C0.rgb *= 1.3;
	outColor = colorThing1(C0,1);
	//outColor = blackWhite(outColor);
}
