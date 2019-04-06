#version 330

attribute vec3 position;
attribute vec2 texCoord;
attribute vec3 colorIn;
attribute vec3 normal;

varying vec2 outputTexCoord;
varying vec2 colorOut;
varying vec3 normal0;


uniform mat4 transform;

void main() {
	gl_Position =  transform * vec4(position, 1.0);
	outputTexCoord = texCoord;
	normal0 = (transform * vec4(normal, 0.0)).xyz; // normalna przemielona przez przesunięcie, rotacje
}
