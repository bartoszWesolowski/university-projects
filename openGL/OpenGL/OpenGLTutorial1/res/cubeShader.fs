#version 330

out vec4 outputColor;

smooth in vec3 colorOut;

void main() {
	outputColor = vec4(colorOut, 1.0);
	//gl_FragColor = colorOut;
}
