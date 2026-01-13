#version 400 core
/*
 * Fragment shader
 * Author: Isaac Dredge
 * Thanks to ThinMatrix for the very helpful tutorials on OpenGL
 * This shader stems from those tutorials
 * Yes, it is vastly different from the simple one function shader that was used in ThinMatrix's tutorials
 * There are a few different shadow calculations in here, feel free to try them out as they all have different effects on the edges
 * of the shadows.
 * They are called getVisibility and have two arguments: the shadow depth buffer and the shadow coords
 * Each one is different and they are super cool. I personally like getVisibility2 with its noisy effect
 *
 * I found it easier to organize the shader by using various functions.
 *
 * Notes on what I did:
 * Each texture has many values to calculate lighting effects from roughness (reflections via sky-box) and reflectivity (light reflection)
 * Roughness is the metalic look of an object where the reflectivity is how much white light reflects off of the surface
 * There is a lighting calculation thats in here that I wrote when I was in high school writing a 3d engine from scratch (literally scratch, then JS, then Java without the GPU)
 * made it so that lights can only shine in one direction using an nDotl product which is imo stupid on what its supposed to solve
 *
 *
 * i am not smart, this took about a month to figure out and write - take what you want from this shader, or modify it
 * Just don't break it, Promise me that?
 */

//variables passed from the vertex shader
in vec2 passTextureCoords;
in vec3 surfaceNormal;
in vec3 toLightVector[20];
in vec3 trueVector[20];
in vec3 toCameraVector;
in float visibility;
in vec4 shadowCoords;
in vec4 spotShadowCoords1;
in vec4 spotShadowCoords2;
in vec4 spotShadowCoords3;
in vec4 spotShadowCoords4;
in vec4 spotShadowCoords5;
in vec3 reflectedVector;
in vec3 passPosition;
in vec3 passPosition1;
in vec3 passPosition2;
in vec3 passPosition3;
in vec3 viewVector;
//in vec3 passLightPosition[20];

//layout means which buffer thing it writes to. Color is default on location 0, even without this specification.
//these are used to calculate my implementation of SSAO.
//SSAO Hint: The difference in normals usually indicates that there is an edge or corner within a specified space
layout (location = 0) out vec4 outColor;
layout (location = 1) out vec4 outDepth;
layout (location = 2) out vec4 outNormal;
layout (location = 3) out vec4 outPosition;

//variables passed from the shader class
uniform sampler2D textureSampler; //0
uniform samplerCube enviroMap; //1
uniform sampler2D roughMap; //2
uniform sampler2D normalMap; //3
uniform sampler2D reflectiveMap; //4
uniform sampler2D shadowMap; //5 - the sun, will always be used unless if disabled shadows is true
uniform sampler2D shadowMap1; //6
uniform sampler2D shadowMap2; //7
uniform sampler2D shadowMap3; //8
uniform sampler2D shadowMap4; //9
uniform sampler2D shadowMap5; //10 These shadow maps are currently unused and will be implemented at some point
uniform vec3 lightColor[20];
uniform vec3 attenuation[20];
uniform vec3 lightDirection[20];
uniform float shineDamper;
uniform float reflectivity;
uniform float roughness;
uniform vec3 skyColor;
uniform int maxLights;
uniform vec3 fogColor;
uniform int pcfCount; //unused
uniform bool shadowsEnable;
uniform bool hasRoughMap;
uniform bool hasReflectiveMap;
uniform bool hasNormalMap;
uniform float normalAmount;
uniform float mapSize;
uniform float gradient;
uniform float density;
//these variables are typically only used by the vertex shader, however there are some calculations here that require this data.
uniform mat4 transformationMatrix;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform vec3 lightPosition[20];
uniform mat4 lightTransformationMatrix[20];

//noise disk thing from the OpenGL website tutorial
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

const float levels = 3.0;
const bool useCelShading = false;

//get random
float random(vec3 seed4, int i) {
	float dot_product = dot(vec4(seed4, i),
			vec4(12.9898, 78.233, 45.164, 94.673));
	return fract(sin(dot_product) * 43758.5453);
}
//different shadow things:
float getVisibility(sampler2D map, vec4 coords) {
	if (!shadowsEnable) {
		return 1.0;
	} else {
		float texelSize = 1.0 / mapSize;
		float total = 0.0;
		float totalTexels = (pcfCount * 2.0 + 1.0) * (pcfCount * 2.0 + 1.0);
		for (int x = -pcfCount; x <= pcfCount; x++) {
			for (int y = -pcfCount; y <= pcfCount; y++) {
				float objectNearestLight = texture(map,
						coords.xy + vec2(x, y) * texelSize).r;
				if (coords.z > objectNearestLight + 0.004) {
					total += 1;
				}
			}
		}
		total /= totalTexels;
		return total;
	}
}

float getFogVisibility() {
	float distance = length(passPosition3);
	float visibility = exp(-pow((distance * density), gradient));
	visibility = clamp(visibility, 0.0, 1.0);
	return visibility;
}

float getVisibility2(sampler2D map, vec4 coords) { //this one is pixel noisy, my favorite
	if (!shadowsEnable) {
		return 1.0;
	} else {
		float bias = 0.005;
		float total = 0.2;
		for (int i = 0; i < 16; i += 1) { //optimization, but worse look
			int index = int(16.0 * random(gl_FragCoord.xyy, i)) % 16;
			if (texture(map, coords.xy + poissonDisk[index] / 700.0).z
					< coords.z - bias) {
				total += 0.2;
			}
		}
		if (texture(map, coords.xy).z < coords.z - bias) {
			total = 1.0;
		}
		if (total > 1.0) {
			total = 1.0;
		}
		return total;
	}
}

float getVisibility3(sampler2D map, vec4 coords) { //this one is pixely
	if (!shadowsEnable) {
		return 1.0;
	} else {
		float bias = 0.009;
		float total = 0.0;
		for (int i = 0; i < 16; i++) {
			if (texture(map, coords.xy + poissonDisk[i] / 700.0).z
					< coords.z - bias) {
				total += 0.2;
			}
		}
		if (texture(map, coords.xy).z < coords.z - bias) {
			total = 1.0;
		}
		if (total > 1.0) {
			total = 1.0;
		}
		return total;
	}
}

float getLightFactor(float total, vec4 coords) { //shadow if enabled filter
	if (!shadowsEnable) {
		return 1.0;
	} else {
		return 1.0 - (total * coords.w);
	}
}

vec3 getNormalMapValue() {
	if (hasNormalMap) {
		vec4 normalValue = texture(normalMap, passTextureCoords);
		vec4 normalMapValue = 2.0 * normalValue - 1.0; //AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
		return normalMapValue.rgb;
	} else {
		return surfaceNormal;
	}
}

vec3 getNormals() { //this is to simplify the look of the lighting calculations
	vec3 unitNormal;
	if (hasNormalMap) {
		unitNormal = normalize(getNormalMapValue());
	} else {
		unitNormal = normalize(surfaceNormal);
	}
	return unitNormal;
}

float getDistance(vec3 pt1, vec3 pt2) { //classic
	vec3 dist = vec3((pt2.x - pt1.x), (pt2.y - pt1.y), (pt2.z - pt1.z));
	return sqrt((dist.x * dist.x) + (dist.y * dist.y) + (dist.z * dist.z));
}

vec4 OGlighting(int amount) {
	//this is from my first attempt at 'shaders' from RealEngine, converted to fit this engine
	//it was a wild ride, but this is something that i created with no help and no sources
	//Originally made it back in 11th or 12th grade in 2017-2018
	//Made some modifications to suit this. Main changes are normal calculations (original messed them up for some reason)
	//added shader settings to be calculated at the end (reflectivity and roughness)
	//Also added support for directional lights using normals to determine where its pointing

	//get the color of the pixel using the texture
	vec4 surfaceTexture = texture(textureSampler, passTextureCoords);
	if (surfaceTexture.a < 0.5) {
		discard;
	}
	//very important variables to determine how the surface will appear
	float rough = texture(roughMap, passTextureCoords).r * roughness;
	float refl = texture(reflectiveMap, passTextureCoords).r * reflectivity;
	//predefine constant variables
	vec4 result = vec4(0, 0, 0, 1);
	vec3 specular = vec3(0, 0, 0);
	vec3 diffuse = vec3(0, 0, 0);
	vec3 unitNormal = getNormals();
	vec3 vector = reflect(viewVector, unitNormal);//totaly not a waste of time and besides the fact that this is faster in the vertex shader rather than the fragment shader
	//but muh better graphics!
	vec3 unitVecToCam = normalize(toCameraVector);
	vec4 WorldPosition = transformationMatrix * vec4(passPosition, 1.0);

	//main lighting calculation loop
	for (int i = 1; i < amount; i++) {//i = 1 because 0 is reserved for the sun and we dont want to do that
		vec4 loopColor = vec4(0);
		//fix code to get the real normal surface thing
		vec3 unitLightVector = normalize(toLightVector[i]);

		float nDotl = dot(unitNormal, unitLightVector);
		//figure out if its a directional light:
		if (lightDirection[i] != vec3(0, 0, 0)) {
			//calculate the new nDotl so that we don't light up surfaces that shouldn't be lighted
			//get the light normal using the lights transformation matrix
			vec3 lightNormal = (lightTransformationMatrix[i]
					* vec4(
							(hasNormalMap) ?
									normalize(lightDirection[i]) :
									lightDirection[i], 0.0)).xyz;
			//normalize(lightDirection[i]);//(transformationMatrix * vec4(vec3(0,0,-1), 0.0)).xyz;
			//normalize it because math reasons
			lightNormal = normalize(lightNormal);
			//calculate the nDotl for the current fragment for this light source
			float lightDir = dot(lightNormal, unitLightVector);
			//do the funny and make sure that we are correctly lighting the area with this light source
			nDotl = getDistance(lightPosition[i], passPosition) < 1.2 ?
					lightDir : (lightDir > nDotl ? nDotl : lightDir);//do not question this line
		}
		//comments below are weirdly placed btw

		float brightness = max(nDotl, 0.0);
		//float brightness = 1;//max(0.0, dot(surfaceNormal, normalize(lightPosition[i])));
		//brightness of the surface according to the surface normal
		vec3 lightSurface = brightness * lightColor[i];
		//creates a variable with the surfaces brightness with the lights color
		float lightBrightness = (lightColor[i].x + lightColor[i].y
				+ lightColor[i].z) / 3;
		//gets the brightness of the light into a more comfortable variable
		float distance = length(toLightVector[i]);//getDistance(lightPosition[i], passPosition);//length(toLightVector[i]);//length(abs(lightPosition[i] - passPosition));
		//gets the distance between the light and the fragment to better calculate the light intensity
		vec4 surfaceColor = surfaceTexture * vec4(lightSurface, 1.0);
		//gets the color of the surface and plasters it against the lighting calculations above it
		float lighting = lightBrightness / distance;
		/*if (attenuation[i].x > 0.5) {
		 lighting = attenuation[i].x + (attenuation[i].y * distance) + (attenuation[i].z * distance * distance);
		 } else {
		 lighting = lightBrightness/distance;
		 }*/

		//this is the magical variable to calculate the lighting
		loopColor[0] = lighting * surfaceColor[0] * lightColor[i][0];
		loopColor[1] = lighting * surfaceColor[1] * lightColor[i][1];
		loopColor[2] = lighting * surfaceColor[2] * lightColor[i][2];
		//simplified version of my original lighting thing because it was really complex for some reason

		//いえいえ
		diffuse = diffuse + (lighting * lightColor[i]);

		vec3 reflectedLight = reflect(-unitLightVector, unitNormal);
		vec3 halfwayDir = normalize(unitLightVector + unitVecToCam);

		//This next line took FOREVER to debug because it showed incorrect lighting from a great distance from the light to the object
		//makes it so that reflected light isn't infinite and not lossless with distance
		float specularFactor = (dot(reflectedLight, unitVecToCam) * brightness)
				* (8 / (distance >= 1 ? 1 : distance));	//i hope that this doesn't break the universe: divide by zero
		//float specularFactor = (dot(unitNormal, halfwayDir) * brightness) * (8/(distance >= 1 ? 1 : distance));//i hope that this doesn't break the universe: divide by zero

		float blFactor = pow(max(dot(unitNormal, halfwayDir), 0.0), 16.0);
		specularFactor = max(specularFactor * blFactor * lighting, 0.0)
				* lightBrightness;		//wtf

		float dampedFactor = pow(specularFactor, shineDamper);
		//float dampedFactor = pow(specularFactor, shineDamper);
		//calculate the shine of the surface
		vec3 finalSpecular = dampedFactor
				* (hasReflectiveMap ? refl : reflectivity) * lightColor[i];	// * vec3(result);
		//thing to make the thingy less shiny?
		result += loopColor + vec4(finalSpecular, 0.0);
		//add what we got to the total color. Aaaand compute the next light
		//result = mix(vec4(fogColor,1.0), result, visibility);//don't enable unless you want chaos
	}
	//reflection moved outside loop for better lighting calulation and slightly better performence
	//get the reflected color:
	//result = mix(vec4(fogColor,1.0), result, visibility);
	vec4 reflectedColor;
	if (hasNormalMap) {
		reflectedColor = mix(vec4(fogColor, 0.0),
				texture(enviroMap,
						reflect(viewVector,
								vec3(surfaceNormal.rg - getNormalMapValue().rg,
										surfaceNormal.b))), 1);
	} else {
		reflectedColor = mix(vec4(fogColor, 0.0), texture(enviroMap, vector),
				1);
	}
	//mix it in
	//result = mix(result, reflectedColor, (hasRoughMap ? rough : roughness));
	result += (reflectedColor * (hasRoughMap ? rough : roughness));
	//result = mix
	//return the magik
	//result = max(result,0.1);
	return result;
}
vec4 calculateLighting() {
	//this is the main light calculation from the tutorials
	float total = getVisibility2(shadowMap, shadowCoords);
	if (total > 1.0) {
		total = 1.0;
	}
	float shadowColorFactor = 1.0 - total;
	float lightFactor = getLightFactor(total, shadowCoords);
	vec3 unitVectorToCamera = normalize(toCameraVector);
	vec3 unitNormal = getNormals();
	vec3 vector = reflect(viewVector, unitNormal);
	vec3 totalDiffuse = vec3(0.0);
	vec3 totalSpecular = vec3(0.0);
	float rough = texture(roughMap, passTextureCoords).r * roughness;
	float refl = texture(reflectiveMap, passTextureCoords).r * reflectivity;
	//calculates the surface brightness and color to each light
	float distance = length(toLightVector[0]);
	float attFactor = attenuation[0].x + (attenuation[0].y * distance)
			+ (attenuation[0].z * distance * distance);
	vec3 unitLightVector = normalize(toLightVector[0]);
	float nDotl = dot(unitNormal, unitLightVector);
	float brightness = max(nDotl, 0.0);
	vec3 lightDirection = -unitLightVector;
	vec3 reflectedLightDirection = reflect(lightDirection, unitNormal);
	float specularFactor = dot(reflectedLightDirection, unitVectorToCamera);
	specularFactor = (max(specularFactor, 0.0));
	float dampedFactor = pow(specularFactor, shineDamper);
	totalDiffuse = totalDiffuse + (brightness * lightColor[0]) / attFactor;
	totalDiffuse = totalDiffuse * lightFactor;
	dampedFactor = dampedFactor * (1.0 - total);
	if (hasReflectiveMap) {
		totalSpecular = totalSpecular
				+ (dampedFactor * refl * lightColor[0]) / attFactor;
	} else {
		totalSpecular = totalSpecular
				+ (dampedFactor * reflectivity * lightColor[0]) / attFactor;
	}

	vec4 textureColor = texture(textureSampler, passTextureCoords);
	if (textureColor.a < 0.5) {
		discard;
	}
	vec4 result = vec4(0, 0, 0, 1);
	result = vec4(totalDiffuse, 1.0) * textureColor + vec4(totalSpecular, 1.0);

	//result = mix(vec4(fogColor,1.0), result, visibility);
	//vec4 reflectedColor = mix(vec4(fogColor,1.0), (vec4(totalDiffuse,1.0) * texture(enviroMap, reflectedVector) + vec4(totalSpecular,1.0)), visibility);
	vec4 reflectedColor = mix(vec4(fogColor, 1.0),
			(vec4(totalDiffuse, 1.0) * texture(enviroMap, vector)
					+ vec4(totalSpecular, 1.0)), visibility);
	if (hasRoughMap) {
		result = mix(result, reflectedColor, (rough));
	} else {
		result = mix(result, reflectedColor, roughness);
	}
	return result + (vec4(0.1, 0.1, 0.1, 1) * total);
}
vec4 getDepth(vec3 cam, vec3 pos) {
	vec3 result = vec3(1 / getDistance(cam, pos));
	return vec4(result, 1);
}

vec4 blackAndWhite(vec4 color) {
	vec4 result = vec4(0, 0, 0, 1);
	result.x = (color.x + color.y + color.z) / 3;
	result.y = result.x;
	result.z = result.x;
	return result;
}

void main(void) {
	//main method for the fragment shader
	//calculate the suns shadows:
	//calculate all the other lights with a custom lighting algorithm
	if (shadowsEnable) {
		outColor = calculateLighting();	//calculate t h e  s u n
	} else {
		outColor = vec4(0.1, 0.1, 0.14, 1);
	}
	outDepth = vec4(2 / passPosition1.xyz, 1);//getDepth(toCameraVector, passPosition1);
	//outNormal = vec4(getNormals(), 1);//double calc needs to be optimized
	//outNormal = vec4(surfaceNormal,1);
	if (hasNormalMap) {
		outNormal = vec4(surfaceNormal.rg - getNormalMapValue().rg,
				surfaceNormal.b, 1);
	} else {
		outNormal = vec4(surfaceNormal, 1.0);
	}
	outPosition = vec4(passPosition2 / 1000, 1);
	//outColor += spotLight(1,1);
	outColor += OGlighting(maxLights);	//calculate t h e   l  i  g  h  t  s
	//outColor = blackAndWhite(outColor);//BLACK AND WHITE BECAUSE IM INTP LOLOLOLOL
	//outColor = vec4(outColor.r, outColor.r, outColor.r, 1);
}
