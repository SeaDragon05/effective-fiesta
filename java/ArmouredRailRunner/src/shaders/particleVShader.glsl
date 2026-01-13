#version 400 core

in vec2 position;

out vec2 textureCoords1;

out vec2 textureCoords2;
out float blend;

//new out variables
out vec4 shadowCoords;
out vec3 toLightVector[20];
out float visibility;
out vec3 passPosition;

uniform mat4 projectionMatrix;
uniform mat4 modelViewMatrix;
uniform mat4 transformationMatrix;

uniform vec2 texOffset1;
uniform vec2 texOffset2;
uniform vec2 texCoordInfo;

//new input variables:
uniform vec3 lightPosition[20];
uniform vec3 lightDirection[20];

uniform int maxLights;
uniform mat4 toShadowMapSpace;
uniform float density = 0.005;
uniform float gradient = 1.9;
void main(void){
	vec2 textureCoords = position + vec2(0.5, 0.5);
	textureCoords.y = 1.0 - textureCoords.y;
	textureCoords /= texCoordInfo.x;
	textureCoords1 = textureCoords + texOffset1;
	textureCoords2 = textureCoords + texOffset2;
	blend = texCoordInfo.y;
	gl_Position = projectionMatrix * modelViewMatrix * vec4(position, 0.0, 1.0);
	passPosition = gl_Position.xyz;
	for (int i =0; i<maxLights;i++) {//simple compute yay
		toLightVector[i] = lightPosition[i] - passPosition.xyz;
	}

}
