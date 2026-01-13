#version 400 core
/*
 *
 * Terrain fragment shader
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
 * I wish I could merge this shader with the static shader, but that would be wayyy too much data and too much rewriting
 * Instead, this is one of the very few shaders that aren't in the main shaders
 * Because its the terrains, and terrains typically have a ton of textures
 *
 */
in vec2 passTextureCoords;
in vec3 surfaceNormal;
in vec3 toLightVector[20];
in vec3 toCameraVector;
in float visibility;
in vec3 passPosition;
in vec4 shadowCoords;

//out vec4 outColor;
layout (location = 0) out vec4 outColor;
layout (location = 1) out vec4 outDepth;
layout (location = 2) out vec4 outNormal;
layout (location = 3) out vec4 outPosition;

uniform sampler2D backgroundTexture;
uniform sampler2D rTexture;
uniform sampler2D rRougMap;
uniform sampler2D rReflMap;
uniform sampler2D rNrmlMap;
uniform sampler2D gTexture;
uniform sampler2D gRougMap;
uniform sampler2D gReflMap;
uniform sampler2D gNrmlMap;//AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
uniform sampler2D bTexture;
uniform sampler2D bRougMap;
uniform sampler2D bReflMap;
uniform sampler2D bNrmlMap;
uniform sampler2D blendMap;
uniform sampler2D shadowMap;

//copied from the main fragment shader:
uniform vec3 lightColor[20];
uniform vec3 attenuation[20];
uniform vec3 lightDirection[20];
uniform float shineDamper;
uniform float reflectivity;
uniform float roughness;
uniform vec3 skyColor;
uniform int maxLights;
uniform vec3 fogColor;
//uniform int pcfCount;//unused
uniform bool shadowsEnable;
uniform bool hasRoughMap;
uniform bool hasReflectiveMap;
uniform bool hasNormalMap;
uniform float normalAmount;
uniform float mapSize;
uniform float gradient;
uniform float density;

//cel shading stuff:
const float levels = 5.0;
const bool useCelShading = false;
//better shadow stuff:
uniform int pcfCount;

//copied functions from the main fragment shader. Yes, this gets old fast

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

float random(vec3 seed4, int i) {
	float dot_product = dot(vec4(seed4, i), vec4(12.9898,78.233,45.164,94.673));
	return fract(sin(dot_product) * 43758.5453);
}
//different shadow things:
float getVisibility(sampler2D map, vec4 coords) {
	if (!shadowsEnable) {
		return 1.0;
	} else {
		float texelSize = 1.0 / mapSize;
		float total = 0.0;
		float totalTexels = (pcfCount * 2.0+ 1.0) * (pcfCount * 2.0 + 1.0);
		for (int x = -pcfCount; x<=pcfCount; x++) {
			for (int y = -pcfCount; y<=pcfCount; y++) {
				float objectNearestLight = texture(map, coords.xy + vec2(x,y) * texelSize).r;
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
	float distance = length(passPosition);
	float visibility = exp(-pow((distance*density), gradient));
	visibility = clamp(visibility,0.0,1.0);
	return visibility;
}

float getVisibility2(sampler2D map, vec4 coords) {//this one is pixel noisy, my favorite
	if (!shadowsEnable) {
		return 1.0;
	} else {
		float bias = 0.005;
		float total = 0.0;
		for (int i=0;i<16;i++){
			int index = int(16.0*random(gl_FragCoord.xyy, i))%16;
			if (texture(map, coords.xy + poissonDisk[index]/700.0).z < coords.z - bias) {
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

float getVisibility3(sampler2D map, vec4 coords) {//this one is pixely
	if (!shadowsEnable) {
		return 1.0;
	} else {
		float bias = 0.009;
		float total = 0.0;
		for (int i=0;i<16;i++){
			if (texture(map, coords.xy + poissonDisk[i]/700.0).z < coords.z - bias) {
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

float getLightFactor(float total, vec4 coords) {//shadow if enabled filter
	if (!shadowsEnable) {
		return 1.0;
	} else {
		return 1.0 - (total * coords.w);
	}
}

vec3 getNormals() {//this is to simplify the look of the lighting calculations
	vec3 unitNormal;
	//if (hasNormalMap) {
	//	vec4 normalValue = texture(normalMap,passTextureCoords);
	//	vec4 normalMapValue = 2.0 * normalValue - 1.0;//AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
	//	unitNormal = normalize(normalMapValue.rgb);
	//} else {
		unitNormal = normalize(surfaceNormal);
	//}
	return unitNormal;
}

float getDistance(vec3 pt1, vec3 pt2) {//classic
	vec3 dist = vec3((pt2.x - pt1.x), (pt2.y - pt1.y), (pt2.z - pt1.z));
	return sqrt((dist.x * dist.x) + (dist.y * dist.y) + (dist.z * dist.z));
}

void main(void) {

	float mapSize = 8192;
	float texelSize = 1.0 / mapSize;
	float total = 0.0;
	float totalTexels = (pcfCount * 2.0 + 1.0) * (pcfCount * 2.0 + 1.0);
	for (int x = -pcfCount; x<=pcfCount; x++) {
		for (int y = -pcfCount; y<=pcfCount; y++) {
			float objectNearestLight = texture(shadowMap, shadowCoords.xy + vec2(x,y) * texelSize).r;
			if (shadowCoords.z > objectNearestLight + 0.003) {
				total += 2;
			}
		}
	}

	total /= totalTexels;

	float lightFactor = 1.0 - (total * shadowCoords.w);

	vec4 blendMapColor = texture(blendMap, passTextureCoords);
	
	float backTextureAmount = 1 - (blendMapColor.r + blendMapColor.g + blendMapColor.b);
	
	vec2 tiledCoords = passTextureCoords * 40;
	vec4 backgroundTextureColor = texture(backgroundTexture, tiledCoords) * backTextureAmount;
	vec4 rTextureColor = texture(rTexture, tiledCoords) * blendMapColor.r;
	vec4 gTextureColor = texture(gTexture, tiledCoords) * blendMapColor.g;
	vec4 bTextureColor = texture(bTexture, tiledCoords) * blendMapColor.b;
	vec4 totalColor = backgroundTextureColor + rTextureColor + gTextureColor + bTextureColor;
	vec3 unitVectorToCamera = normalize(toCameraVector);
	vec3 unitNormal = normalize(surfaceNormal);
	
	vec3 totalDiffuse = vec3(0.0);
	vec3 totalSpecular = vec3(0.0);
	for (int i = 0;i<maxLights;i++) {
			float distance = length(toLightVector[i]);
			float attFactor = attenuation[i].x + (attenuation[i].y * distance) + (attenuation[i].z * distance * distance);
			vec3 unitLightVector = normalize(toLightVector[i]);
			float nDotl = dot(unitNormal, unitLightVector);
			float brightness = max(nDotl, 0.0);
			if (useCelShading) {
				float level = floor(brightness * levels);
				brightness = level / levels;
			}
			vec3 lightDirection = -unitLightVector;
			vec3 reflectedLightDirection = reflect(lightDirection, unitNormal);
			float specularFactor = dot(reflectedLightDirection, unitVectorToCamera);
			specularFactor = (max(specularFactor,0.0));
			float dampedFactor = pow(specularFactor,shineDamper);
			if (useCelShading) {
				float level = floor(dampedFactor * levels);
				dampedFactor = level / levels;
			}
			totalDiffuse = totalDiffuse + (brightness * lightColor[i])/attFactor;
			if (i == 0 && lightColor[0].r > 0.01) {
				totalDiffuse = totalDiffuse * lightFactor;
			}
			totalSpecular = totalSpecular + (dampedFactor * reflectivity * lightColor[i])/attFactor;
	}
	totalDiffuse = max(totalDiffuse,0.1);
	
	
	outColor = vec4(totalDiffuse,1.0) * totalColor + vec4(totalSpecular,1.0);
	outColor = mix(vec4(fogColor,1.0), outColor, visibility);
	
}
