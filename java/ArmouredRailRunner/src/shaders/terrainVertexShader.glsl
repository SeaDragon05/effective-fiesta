#version 400 core

in vec3 position;
in vec2 textureCoords;
in vec3 normal;

out vec2 passTextureCoords;
out vec3 surfaceNormal;
out vec3 toLightVector[20];
out vec3 toCameraVector;
out float visibility;
out vec3 passPosition;
out vec4 shadowCoords;

uniform mat4 transformationMatrix;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform vec3 lightPosition[20];
uniform vec4 plane;
uniform int maxLights;

uniform mat4 toShadowMapSpace;

const float density = 0.000;
const float gradient = 1.1;

const float shadowDistance = 100;
const float transitionDistance = 10;

void main(void) {

	vec4 worldPosition = transformationMatrix * vec4(position, 1.0);
	shadowCoords = toShadowMapSpace * worldPosition;

	gl_ClipDistance[0] = dot(worldPosition, plane);
	vec4 positionRelativeToCam = viewMatrix * worldPosition;
	gl_Position = projectionMatrix * positionRelativeToCam;
	passTextureCoords = textureCoords;
	
	surfaceNormal = (transformationMatrix * vec4(normal, 0.0)).xyz;
	for (int i =0; i<maxLights;i++) {
		toLightVector[i] = lightPosition[i] - worldPosition.xyz;
	}
	toCameraVector = (inverse(viewMatrix) * vec4(0,0,0,1)).xyz - worldPosition.xyz;
	passPosition = position;
	float distance = length(positionRelativeToCam.xyz);
	visibility = exp(-pow((distance*density), gradient));
	visibility = clamp(visibility,0.0,1.0);

	distance = distance - (shadowDistance - transitionDistance);
	distance = distance / transitionDistance;
	shadowCoords.w = clamp(1.0-distance, 0.0, 1.0);

}
