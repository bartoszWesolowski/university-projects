#version 330

attribute vec3 position;
attribute vec2 texCoord;
attribute vec3 colorIn;

varying vec2 outputTexCoord;
varying vec2 colorOut;

uniform mat4 transform;

uniform float time;

void main() {
	float currTime = mod(time, 5.0);
	
	vec3 offset = vec3(
		time,
		0.0,
		time
	);

	gl_Position =  transform * vec4(position + offset, 1.0f);
	
	outputTexCoord = texCoord;
	
}
