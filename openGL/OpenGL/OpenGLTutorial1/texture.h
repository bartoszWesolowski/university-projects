#pragma once
#include <string>
#include <GL\glew.h>


class Texture
{
public:
	Texture(const std::string& fileName);
	//podpinanie odpowiedniej tekstury

	void Bind(unsigned int unit);
	~Texture();

private:
	//uchyt do tekstury
	GLuint m_texture;

};

