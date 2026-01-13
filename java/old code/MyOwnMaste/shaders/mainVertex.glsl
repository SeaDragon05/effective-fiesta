#version 460 core

//get buffers:
in vec3 position;
in vec3 color;
in vec2 textureCoord;//this order DOES MATTER!!!!!!
in vec3 normal;//this is in the order of the buffer index id number


//define passing variables:
out vec2 passTextureCoord;
out vec3 passNormal;
out vec3 passColor;
out vec3 passPosition;
out vec3 passLightColor[10];
out vec3 passLightPosition[10];
out float passLightness[10];
out vec3 passCameraVec;
out float visibility;

//get variables:
uniform mat4 transformationMatrix;//
uniform mat4 viewMatrix;//
uniform mat4 projectionMatrix;//
uniform vec3 lightColor[10];//
uniform vec3 lightPosition[10];//
uniform float lightBr[10];//
uniform float density;//
uniform float gradient;//
uniform int numbLights;
uniform int shadowEnable;

//program:
void main(void) {
	vec4 worldPosition = transformationMatrix * vec4(position,1.0);
	mat4 modelViewMatrix = viewMatrix * transformationMatrix;
	vec4 positionRelativeToCam = viewMatrix * vec4(position,1.0);
	gl_Position = projectionMatrix * viewMatrix * transformationMatrix * vec4(position, 1.0);
	passTextureCoord = textureCoord;
	passColor = color;
	passPosition = position;
	passNormal = (transformationMatrix * vec4(normal,0.0)).xyz;
	for (int i = 0; i < numbLights; i += 1) {
		passLightColor[i] = lightColor[i];
		passLightPosition[i] = lightPosition[i] - worldPosition.xyz;
		passLightness[i] = lightBr[i];
	}
	passCameraVec = (inverse(viewMatrix) * vec4(0.0, 0.0, 0.0, 1.0)).xyz - worldPosition.xyz;
	float distance = length(positionRelativeToCam.xyz);
	visibility = exp(-pow((distance*density), gradient));
	visibility = clamp(visibility,0.0,1.0);
}
/*
vec4 worldPosition = tranformationMatrix * vec4(position, 1.0);
gl_ClipDistance[0] = dot(worldPosition, plane);
mat4 modelViewMatrix = viewMatrix * transformationMatrix;
vec4 positionRelativeToCam = modelViewMatrix * vec4(position,1.0);
gl_Position = projectionMatrix * (positionRelativeToCam);
*/