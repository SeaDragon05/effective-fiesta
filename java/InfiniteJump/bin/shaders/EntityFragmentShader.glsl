#version 140

in vec2 passTC;
in vec3 surfaceNormal;
in vec3 toLightVector;
in vec3 toCameraVector;
in float visibility;

out vec4 out_Color;

uniform sampler2D textureSampler;
uniform vec3 lightColor;
uniform float shineDamper;
uniform float reflectivity;
uniform vec3 skyColor;

const float levels = 5;

void main(void) {

	vec3 unitNormal = normalize(surfaceNormal);
	vec3 unitLightVector = normalize(toLightVector);
	
	float nDotl = dot(unitNormal, unitLightVector);
	float brightness = max(nDotl, 0.1);
	float level = floor(brightness * levels);
	brightness = level / levels;
	
	vec3 diffuse = brightness * lightColor;
	vec3 unitVectorToCamera = normalize(toCameraVector);
	vec3 lightDirection = -unitLightVector;
	vec3 reflectedLightDirection = reflect(lightDirection,unitNormal);
	float specularFactor = dot(reflectedLightDirection, unitVectorToCamera);
	specularFactor = max(specularFactor,0);
	float specLevel = floor(specularFactor * 20);
	specularFactor = specLevel / 20;
	float dampedFactor = pow(specularFactor, shineDamper);
	vec3 finalSpecular = dampedFactor * reflectivity * lightColor;
	finalSpecular = max(finalSpecular,0);
	
	out_Color = vec4(diffuse,1.0) * texture(textureSampler,passTC) + vec4(finalSpecular, 1);
	
	out_Color = mix(vec4(skyColor, 1.0), out_Color, visibility);
}