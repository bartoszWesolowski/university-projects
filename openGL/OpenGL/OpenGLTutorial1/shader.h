#pragma once
#include <string>
#include <GL/glew.h>
#include "transform.h"
#include "camera.h"
class Shader
{
public:
	Shader(const std::string& fileName);

	void Bind();
	void Update(const Transform& transform, const Camera& camera);
	void UpdateAmbient(glm::vec3& ambient);
	virtual ~Shader();

	static const unsigned int POSITION_ATTRIB_LOCATION = 0;
	static const unsigned int TEXTURE_COORDINATE_ATTRIB_LOCATION = 1;
	static const unsigned int NORMAL_ATTRIB_LOCATION = 2;
	//Numer atrybuty przekazywanego do shadera jako zmienna colorIn
	static const unsigned int COLOR_ATTRIB_LOCATION = 3;



protected:
private:
	static const unsigned int NUM_SHADERS = 2;
	void operator=(const Shader& shader) {}
	Shader(const Shader& shader) {}

	std::string LoadShader(const std::string& fileName);
	void CheckShaderError(GLuint shader, GLuint flag, bool isProgram, const std::string& errorMessage);
	GLuint CreateShader(const std::string& text, unsigned int type);


	enum {
		TRANSFORM_U,
		AMBIENT_LIGHT_U,
		NUM_UNIFORMS // przechowuje iloœæ wszystkich enumów z przodu!
	};

	GLuint m_program;
	GLuint m_shaders[NUM_SHADERS];
	GLuint m_uniforms[NUM_UNIFORMS];

};

