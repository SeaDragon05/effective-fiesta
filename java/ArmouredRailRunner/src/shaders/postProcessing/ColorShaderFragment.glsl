#version 330 core

in vec2 textureCoords;

out vec4 outColor;

uniform sampler2D color;

float random(vec3 seed4, int i) {
	float dot_product = dot(vec4(seed4, i), vec4(12.9898,78.233,45.164,94.673));
	return fract(sin(dot_product) * 43758.5453);
}

void main(void) {
	//make dark colors darker
	//make bright colors somewhat darker
	//wash out some select colors (not blue dominant)
	vec4 pixelColor = texture(color, textureCoords);

	outC0lor = pixelColor * random(gl_FragCoord.xyy, 0);

}
