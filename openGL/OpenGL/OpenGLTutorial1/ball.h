#pragma once
#include "mesh.h"
class Ball : public Mesh
{
public:
	Ball(const std::string& fileName, Shader* shader, Camera* camera, Texture* texture) : Mesh(fileName, shader, camera, texture) { }
	
	void moveBall();

	void moveInACircle(float radius);

	

	~Ball();

private:
	float velX = 0.005f;
	float velZ = 0.002f;
	float maxX = 2.0f;
	float maxZ = 2.0f;

	float loopDuration = 5.0f;
	float PI = 3.1415f;
	float currentTime = 0.0f;
	float deltaTime = 0.01f;

	glm::vec3 startPosition = glm::vec3(0, 0.5, 0);

};

