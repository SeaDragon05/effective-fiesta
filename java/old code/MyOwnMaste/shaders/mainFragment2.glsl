 #version 400 core

in vec3 passColor;
in vec3 passNormal;
in vec2 passTextureCoord;//this order PROBABLY MATTERS TOO!!!!
in vec3 passLightColor[10];
in vec3 passLightPosition[10];
in float passLightness[10];
in vec3 passPosition;
in vec3 passCameraVec;
in float visibility;

out vec4 outColor;

uniform sampler2D tex;
uniform sampler2D bump;
uniform vec3 lightColor[10];
uniform vec3 lightPosition[10];
uniform float shineDamper;
uniform float roughness;
uniform float reflectivity;
uniform vec3 skyColor;
uniform int numbLightsF;
uniform int fullBright;
uniform int shadowEnable;
uniform mat4 transformationMatrix;
uniform int bumpEnable;

void main() {
	if (fullBright == 1 || numbLightsF == 0) {
		outColor = texture(tex, passTextureCoord);
	} else {
		vec4 texColor = texture(tex, passTextureCoord);
		vec4 bumpMap = 2.0 * texture(tex, passTextureCoord, -1.0) - 1.0;
		
		float AAA = (bumpMap.r - bumpMap.g) - (bumpMap.g - bumpMap.b);
		
		//get the point color from the texture (this is prone to failure for some reason)
		vec3 unitNormal;
		
		if (bumpEnable == 1) {
			unitNormal = normalize(passNormal + ((bumpMap.rgb * AAA) * roughness));//this is the closest to bumpmaps without tangent space that I can get lmao
		} else {
			unitNormal = normalize(passNormal);
		}
		
		vec4 result = vec4(0,0,0,1);
		//define the final point color result thing
		
		vec3 specular = vec3(0,0,0);
		//shiny
		
 		vec3 normal = unitNormal;//normalize(passNormal);
 		//calculate the normal before the for loop
 		
 		vec3 unitVecToCam = normalize(passCameraVec);
 		//for the specular, normalizing the camera position
 		
		vec4 WorldPosition = transformationMatrix * vec4(passPosition,1.0);
		
		for (int i = 0; i < numbLightsF; i ++) {
			float brightness = 0;
			if (max(0.0, dot(normal, normalize(passCameraVec))) > 0) {
 				brightness = (max(0.0, dot(normal, normalize(passLightPosition[i]))));
 			} else {
 				brightness = (max(0.0, abs(dot(normal, normalize(passLightPosition[i])))));
 			}
 			//calculate the brightness of the surface acording to the camera. This makes shading more belivable
 			
 			vec3 nLightColor = brightness * passLightColor[i];
 			//get the new light color effects
 			float Br = passLightness[i];
 			//get the light brightness into a better variable beacuse why not
 			
 			float distance = length(abs(lightPosition[i] - passPosition));
 			//get the distance of the light and the current position
 			
 			vec4 surfaceColor = vec4(nLightColor,1.0) * texColor;
 			//so far, we have gotten the normalized surface color of the object according to the light we are focusing on
 			//with this variable, we are taking the light color we calculated and are applying it to the texture color
 			
 			float lighting = Br / distance;
 			//this is to simplify the below script, just dont ask how it works because I forgot. It works and thats all you need to know.
 			//but I will try to explain what the **** is going on
 			
 			/*
 			This section is apart of a shader that I have been developing since 2017 in my own 3d engine that ran on the cpu rather than the gpu
 			To briefly explain whats going on here, with no notes of my 17 year old self that wrote this (am now 22 at time of writing), the
 			first if statment block compares the red value of the light color and adjusts it accordingly by comparing each rgb value
			second compares the green value
			and third compares the blue value
 			*/
 			if (passLightColor[i][0] != passLightColor[i][1] && passLightColor[i][0] != passLightColor[i][2]) {
 				//make sure that red is not equal to green or blue
 				
				result[0] += lighting * (surfaceColor[0] + (passLightColor[i][0] - passLightColor[i][2] - passLightColor[i][1]));
				//red minus blue minus green? yea ask me when I was 17
				
			} else if (passLightColor[0] != passLightColor[2]) {
				//so, red and green are the same, naow red minus 
				result[0] += lighting * (surfaceColor[0] + (passLightColor[i][0] - passLightColor[i][1]));
			} else {
				//or just use red
				result[0] += lighting * (surfaceColor[0] + (passLightColor[i][0] - passLightColor[i][2]));
			}
			
			
			if (passLightColor[i][1] != passLightColor[i][0] && passLightColor[i][1] != passLightColor[i][2]) {
				result[1] += lighting * (surfaceColor[1] + (passLightColor[i][1] - passLightColor[i][2] - passLightColor[i][1]));
			} else if (passLightColor[1] != passLightColor[2]) {
				result[1] += lighting * (surfaceColor[1] + (passLightColor[i][1] - passLightColor[i][1]));
			} else {
				result[1] += lighting * (surfaceColor[1] + (passLightColor[i][1] - passLightColor[i][2]));
			}
			if (passLightColor[i][2] != passLightColor[i][1] && passLightColor[i][2] != passLightColor[i][0]) {
				result[2] += lighting * (surfaceColor[2] + (passLightColor[i][2] - passLightColor[i][1] - passLightColor[i][0]));
			} else if (passLightColor[2] != passLightColor[1]) {
				result[2] += lighting * (surfaceColor[2] + (passLightColor[i][2] - passLightColor[i][0]));
			} else {
				result[2] += lighting * (surfaceColor[2] + (passLightColor[i][2] - passLightColor[i][1]));
			}
			vec3 lightDir = -normalize(passLightPosition[i]);
			vec3 reflectedLight = reflect(lightDir, unitNormal);
			float specularFactor = dot(reflectedLight, unitVecToCam);
			specularFactor = max(specularFactor, 0.0);//wtf
			float dampedFactor = pow(specularFactor, shineDamper);
			vec3 finalSpecular = dampedFactor * reflectivity * passLightness[i] * passLightColor[i];
			
			result += vec4(finalSpecular,1.0) * 0.01;//getting just one percent LOL  
		}
		
		outColor = result;
	}
}/*





		vec4 bumpMap = 2.0 * texture(tex, passTextureCoord, -1.0) - 1.0;

 		brightness = (max(0.0, dot(normal, normalize(passLightPosition[i]))));

		float rg = (bumpMap.r - bumpMap.g);
		float gb = (bumpMap.g - bumpMap.b);
		float rb = (bumpMap.r - bumpMap.b);
		float rgb = ((rg + gb + rb) / 3);
		brightness += rgb;














*/













