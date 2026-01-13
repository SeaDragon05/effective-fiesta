#version 140

out vec4 out_colour;

in vec2 textureCoords1;
in vec2 textureCoords2;
in float blend;
//new inputs from vertex shader:
in vec4 shadowCoords;
in vec3 toLightVector[20];
in vec3 passPosition;


uniform sampler2D particleTexture;//1
//new stuff for lighting:

uniform sampler2D shadowMap;//5 - the sun, will always be used unless if disabled shadows is true
uniform vec3 lightColor[20];
uniform vec3 attenuation[20];
uniform vec3 lightDirection[20];
uniform vec3 fogColor;
uniform vec3 skyColor;


void main(void){
	//iro - color
	vec4 color1 = texture(particleTexture, textureCoords1);
	vec4 color2 = texture(particleTexture, textureCoords2);
	vec4 pColor = mix(color1, color2, blend);
	out_colour = mix(pColor, vec4(fogColor, 0), 0.5);

}
