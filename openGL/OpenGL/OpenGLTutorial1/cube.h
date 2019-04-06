#pragma once
#include <glm/glm.hpp>
#include <GL/glew.h>
#include "shader.h"
#include "camera.h"
#include "glObject.h"
#include "shader.h"

class Cube : public GlObject
{
public:
	Cube() { }
	Cube(const glm::vec3& pos, float size, Shader* shader, Camera* camera);
	~Cube();
	void Draw();

	void setColor(const glm::vec3& color);
protected:
	GLuint m_vertexArrayObject;
	GLuint m_vertexArrayBuffer;
	GLuint m_indexArrayBuffer;
	GLuint m_colorArrayBuffer;
	GLuint m_texCoordsArrayBuffer;

	glm::vec3 m_color;

};

