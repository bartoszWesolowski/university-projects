#pragma once
#include <glm/glm.hpp>
#include <GL/glew.h>
#include "glObject.h"
#include "shader.h"
class Axis : public GlObject
{
public:
	Axis(const glm::vec3 start, const glm::vec3& end, const glm::vec3& color, Shader* shader, Camera* camera);
	~Axis();
	void Draw();
private:

	glm::vec3 points[2];
	glm::vec3 colors[2];
	GLuint m_vertexArrayObject;
	GLuint m_vertexBufferObject;
	GLuint m_colorBufferObject;
};



