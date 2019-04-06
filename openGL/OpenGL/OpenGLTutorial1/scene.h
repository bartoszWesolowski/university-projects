#pragma once
#include <iostream>
#include <vector>
#include "transform.h"
#include "camera.h"
#include "cube.h"
#include "axis.h"
#include "mesh.h"
#include "texture.h"
#include "ball.h"
#include "tree.h"
#include "robot.h"
#include "coin.h"

class Scene
{
private:
	enum {
		MONKEY_MESH,

		NUM_OF_MESHS
	};

	const float MOVING_SPEED = 0.1f;
	const float ROTATION_SPEED = 3.5f;
	void UpdateRobotCamera(Robot* robot);
	void UpdateObjects();
	void createCoins(int number, Shader* shader, Camera* camera, Texture* texture);
	void addAmbientLight(float diff) {
		ambient.x += diff;
		ambient.y += diff;
		ambient.z += diff;
	}
	Shader* simpeShade;
	Shader* textureShaders;

	Cube* cube2;


	Axis* axisX;
	Axis* axisY; 
	Axis* axisZ;

	Ball* ball;
	Mesh* plane;
	Mesh* stadium;
	Tree* tree;
	Mesh* skybox;
	Robot* robot1;

	std::vector<Coin*> coins;

	glm::vec3 ambient = glm::vec3(1.0f, 1.0f, 1.0f);

	const float AMBIENT_DIFF = 0.05f;
	float numberOfCoins = 10;
	const float FIELD_WIDTH = 1.0;
	const float FIELD_LENGHT = 2.0;
public:
	Scene();
	~Scene();
	void KeyPressed(int key);
	void Draw();
	void PrepareObjects();
	Camera* currentCamera;
	Camera* m_camera;
	Camera* m_robotCamera;
	Transform* m_transform;

	glm::vec3 cameraForward = glm::vec3(0.0, 0.0, 0.0);
	Texture* ballTexture;
	Texture* planeTexture;

};

