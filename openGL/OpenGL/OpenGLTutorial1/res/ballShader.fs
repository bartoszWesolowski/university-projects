#version 330

varying vec2 outputTexCoord;

uniform sampler2D diffuse;
varying vec2 colorOut;

void main() {
	gl_FragColor = texture2D(diffuse, outputTexCoord);
	//gl_FragColor = vec4(1.0, 0.0, 0.5, 1.0);
}
