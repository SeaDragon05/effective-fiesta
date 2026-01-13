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
uniform mat4 lightMat4;

void main() {
	
	
	outColor = texture(tex, passTextureCoord) * 0.4;
}