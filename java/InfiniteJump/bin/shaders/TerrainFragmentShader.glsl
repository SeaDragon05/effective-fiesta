#version 140

in vec2 passTC;
in vec3 surfaceNormal;
in vec3 toLightVector;
in vec3 toCameraVector;
in float visibility;

out vec4 out_Color;

uniform sampler2D backgroundTexture;
uniform sampler2D rTexture;
uniform sampler2D gTexture;
uniform sampler2D bTexture;
uniform sampler2D blendMap;

uniform vec3 lightColor;
uniform float bgShineDamper;
uniform float bgReflectivity;
uniform float rShineDamper;
uniform float rReflectivity;
uniform float gShineDamper;
uniform float gReflectivity;
uniform float bShineDamper;
uniform float bReflectivity;
uniform vec3 skyColor;

const float levels = 5;

void main(void) {

	vec4 blendMapColor = texture(blendMap, passTC);
	
	float backTextureAmount = 1 - (blendMapColor.r + blendMapColor.g + blendMapColor .b);
	vec2 tiledCoords = passTC * 40.0;
	vec4 backgroundTextureColor = texture(backgroundTexture, tiledCoords) * backTextureAmount;
	vec4 rTextureColor = texture(rTexture, tiledCoords) * blendMapColor.r;
	vec4 gTextureColor = texture(gTexture, tiledCoords) * blendMapColor.g;
	vec4 bTextureColor = texture(bTexture, tiledCoords) * blendMapColor.b;
	
	float shineDamper = (blendMapColor.r * rShineDamper) + (blendMapColor.g * gShineDamper) + (blendMapColor.b * bShineDamper) ;
	float reflectivity = (blendMapColor.r * rReflectivity) + (blendMapColor.g * gReflectivity) + (blendMapColor.b * bReflectivity);
	shineDamper += (backTextureAmount * bgShineDamper);
	reflectivity += (backTextureAmount * bgReflectivity);
	vec4 totalColor = backgroundTextureColor + rTextureColor + gTextureColor + bTextureColor;



	vec3 unitNormal = normalize(surfaceNormal);
	vec3 unitLightVector = normalize(toLightVector);
	
	float nDotl = dot(unitNormal, unitLightVector);
	float brightness = max(nDotl, 0.1);float level = floor(brightness * levels);
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
	out_Color = vec4(diffuse,1.0) * totalColor + vec4(finalSpecular, 1);
	out_Color = mix(vec4(skyColor, 1.0), out_Color, visibility);
}