#version 400 core

in vec3 position;
in vec2 textureCoords;
in vec3 normal;
in vec3 tangent;

out vec2 passTextureCoords;
out vec3 surfaceNormal;
out vec3 toLightVector[20];
out vec3 trueVector[20];
out vec3 passLightNormal[20];
out vec3 toCameraVector;
out float visibility;
out vec4 shadowCoords;
out vec4 spotShadowCoords1;
out vec4 spotShadowCoords2;
out vec4 spotShadowCoords3;
out vec4 spotShadowCoords4;
out vec4 spotShadowCoords5;
out vec3 reflectedVector;
out vec3 passPosition;
out vec3 passPosition1;
out vec3 passPosition2;
out vec3 passPosition3;
out vec3 viewVector;
uniform mat4 transformationMatrix;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform vec3 lightPosition[20];
uniform vec3 lightPositionEyeSpace[20];
uniform vec3 lightDirection[20];
uniform vec4 plane;
uniform int maxLights;
uniform float reflectivity;
uniform vec3 cameraPosition;

uniform mat4 toShadowMapSpace;
uniform mat4 toShadowMapSpace1;
uniform mat4 toShadowMapSpace2;
uniform mat4 toShadowMapSpace3;
uniform mat4 toShadowMapSpace4;
uniform mat4 toShadowMapSpace5;

uniform float useFakeLighting;

uniform float numberOfRows;
uniform vec2 offset;

uniform bool hasNormalMap;


uniform float density;// = 0.005;
uniform float gradient;// = 1.9;

float getDistance(vec4 pt1, vec4 pt2) {//classic
	vec3 dist = vec3((pt2.x - pt1.x), (pt2.y - pt1.y), (pt2.z - pt1.z));
	return sqrt((dist.x * dist.x) + (dist.y * dist.y) + (dist.z * dist.z));
}

void main(void) {
	//for the og thing:
	vec4 worldPosition = transformationMatrix * vec4(position, 1.0);
	shadowCoords = toShadowMapSpace * worldPosition;
	//spotShadowCoords1 = toShadowMapSpace1 * worldPosition;
	//spotShadowCoords2 = toShadowMapSpace2 * worldPosition;
	//spotShadowCoords3 = toShadowMapSpace3 * worldPosition;
	//spotShadowCoords4 = toShadowMapSpace4 * worldPosition;
	//spotShadowCoords5 = toShadowMapSpace5 * worldPosition;//AAAAAAAAAAAAAAAAAAAAAAAAA
	gl_ClipDistance[0] = dot(worldPosition, plane);
	if (hasNormalMap) {
		mat4 modelViewMatrix = viewMatrix * transformationMatrix;//
		vec4 positionRelativeToCam = modelViewMatrix * vec4(position,1.0);//
		passPosition3 = positionRelativeToCam.xyz;
		gl_Position = projectionMatrix * positionRelativeToCam;//
		passTextureCoords = (textureCoords/numberOfRows) + offset;//
		vec3 surfaceNorm = (modelViewMatrix * vec4(normal,0.0)).xyz;//

		vec3 norm = normalize(surfaceNorm);
		vec3 tang = normalize((modelViewMatrix * vec4(tangent, 0.0)).xyz);
		vec3 bitang = normalize(cross(norm, tang));

		mat3 toTangentSpace = mat3(
				tang.x, bitang.x, norm.x,
				tang.y, bitang.y, norm.y,
				tang.z, bitang.z, norm.z
		);

		//surfaceNorm = (transformationMatrix * vec4(normal, 0.0)).xyz;
		surfaceNormal = (transformationMatrix * vec4(normal, 0.0)).xyz;
		for(int i=0;i<maxLights;i++){
			toLightVector[i] = toTangentSpace * (lightPositionEyeSpace[i] - positionRelativeToCam.xyz);
		}
		toCameraVector = toTangentSpace * (-positionRelativeToCam.xyz);//AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA

		float distance = length(positionRelativeToCam.xyz);
		visibility = exp(-pow((distance*density),gradient));
		visibility = clamp(visibility,0.0,1.0);
		viewVector = normalize(worldPosition.xyz - cameraPosition);
		reflectedVector = reflect(viewVector, normalize(normal));
	} else {
		vec4 positionRelativeToCam = viewMatrix * worldPosition;
		passPosition3 = positionRelativeToCam.xyz;
		gl_Position = projectionMatrix * positionRelativeToCam;
		passTextureCoords = (textureCoords/numberOfRows) + offset;
		vec3 actualNormal = normal;
		if (useFakeLighting > 0.5) {
			actualNormal = vec3(0,1,0);
		}
		surfaceNormal = (transformationMatrix * vec4(actualNormal, 0.0)).xyz;
		for (int i =0; i<maxLights;i++) {
			toLightVector[i] = lightPosition[i] - worldPosition.xyz;
		}
		trueVector = toLightVector;
		toCameraVector = (inverse(viewMatrix) * vec4(0,0,0,1)).xyz - worldPosition.xyz;
		float distance = length(positionRelativeToCam.xyz);
		visibility = exp(-pow((distance*density), gradient));
		visibility = clamp(visibility,0.0,1.0);

		vec3 unitNormal = normalize(normal);
		viewVector = normalize(worldPosition.xyz - cameraPosition);
		reflectedVector = reflect(viewVector, unitNormal);
	}
	passPosition = worldPosition.xyz;
	passPosition1 = gl_Position.xyz;
	passPosition2 = position.xyz;
}
/*
 * gl_Position = projectionMatrix * positionRelativeToCam;
	passTextureCoords = (textureCoords/numberOfRows) + offset;
	vec3 actualNormal = normal;
	if (useFakeLighting > 0.5) {
		actualNormal = vec3(0,1,0);
	}
	surfaceNormal = (transformationMatrix * vec4(actualNormal, 0.0)).xyz;
	for (int i =0; i<maxLights;i++) {
		toLightVector[i] = lightPosition[i] - worldPosition.xyz;
	}
	toCameraVector = (inverse(viewMatrix) * vec4(0,0,0,1)).xyz - worldPosition.xyz;
	float distance = length(positionRelativeToCam.xyz);
	visibility = exp(-pow((distance*density), gradient));
	visibility = clamp(visibility,0.0,1.0);

	vec3 unitNormal = normalize(normal);
	vec3 viewVector = normalize(worldPosition.xyz - cameraPosition);
	reflectedVector = reflect(viewVector, unitNormal);
 */
