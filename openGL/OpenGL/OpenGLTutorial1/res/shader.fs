#version 330

varying vec2 outputTexCoord;

uniform sampler2D diffuse;
uniform vec3 ambient;

varying vec2 colorOut;
varying vec3 normal0;



void main() {
	gl_FragColor = texture2D(diffuse, outputTexCoord) * vec4(ambient, 0.0);
		//* clamp(dot(-vec3(0, -2, -1.0), normal0), 0.0, 1.0);
}
