#version 400 core

in vec3 passColor;
in vec3 passNormal;
in vec2 passTextureCoord;//this order PROBABLY MATTERS TOO!!!!
in vec3 passLightColor[10];
in vec3 passLightPosition[10];
in vec3 passPosition;
in vec3 passCameraVec;
in float shade;
in float visibility;

out vec4 outColor;

uniform sampler2D tex;
uniform vec3 lightColor[10];
uniform vec3 lightPosition[10];
uniform vec3 normal;
uniform float shineDamper;
uniform float reflectivity;
uniform vec3 skyColor;
uniform int numbLightsF;

void main() {
	
 	vec3 unitNormal = normalize(passNormal);
 	vec4 resultColor = vec4(0,0,0,1);
 	vec3 diffuse = vec3(0,0,0);
 	vec3 finalSpecular = vec3(0,0,0);
 	for (int i = 0; i < numbLightsF; i++) {
 		float brightness = max(dot(unitNormal, normalize(passLightPosition[i])),0.0);
 		//define brightness level of a point relative to a light source?
 		
		diffuse += brightness * passLightColor[i];
		//add the overall diffuse for the final result
		
		vec4 textureColor = texture(tex, passTextureCoord);
		//get the texture color for the dot
		
		float lightBrightness = 100.0f;
		//set a constant level of brightness for some reason
		
		vec3 distance = abs(lightPosition[i] - passPosition);
		//get the distance in a weird way that does NOT GET ACTUAL DISTANCE
		
		float thingy = sqrt((distance.x * distance.x) + (distance.y * distance.y) + (distance.z * distance.z));
		//get the TRUE DISTACE by using a magic formula that makes it the true distance
		
		vec3 thing = vec3(thingy, thingy, thingy);
		//convert it to a vec3
		
		vec3 unitVecToCam = normalize(passCameraVec);
		//normalize the camera position for some reason
		
		vec3 lightDir = -normalize(passLightPosition[i]);
		//normalize the light position and get the negative value
		
		vec3 reflectedLight = reflect(lightDir, unitNormal);
		//reflect two normalized values
		
		float specularFactor = dot(reflectedLight, unitVecToCam);
		//calculate the specular value based on angles
		
		specularFactor = max(specularFactor, 0.0);
		//make sure its more than 0. This is self explanitory
		
		float dampedFactor = pow(specularFactor / thingy, shineDamper);
		//damped factor creates the falloff if you will of the great shininess of the surface
		
		finalSpecular += dampedFactor * reflectivity * lightColor[i];
		//add everything to the overall result
		
	}
	vec4 result;
	result = (vec4(diffuse,1.0) * texture(tex, passTextureCoord) + vec4(finalSpecular,1.0));
	//mash everything into eachother
	
	outColor = mix(vec4(skyColor,1.0), result, visibility);
	//this calculates fog?
}