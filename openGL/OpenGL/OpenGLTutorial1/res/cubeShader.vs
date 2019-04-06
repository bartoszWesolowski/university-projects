#version 330

layout (location = 0) in vec3 position;
layout (location = 3) in vec3 colorIn;

smooth out vec3 colorOut;

uniform mat4 transform;

void main() {
	gl_Position =  transform * vec4(position, 1.0);
	colorOut = colorIn;
}
