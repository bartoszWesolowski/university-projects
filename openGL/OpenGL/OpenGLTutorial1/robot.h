#pragma once
#include <vector>
#include "mesh.h"
#include "glObject.h"
#include "coin.h"
class Robot : public GlObject
{
public:
	Robot(Shader* shader, Camera* camera);
	~Robot();
	void Draw();
	void Move(glm::vec3 offset);
	void Scale(float scale);

	glm::vec3 GetCurrentCenterPosition();
	void collectCoins(std::vector<Coin*>& coins);
	void moveForward();
	void moveBackward();
	void turnLeft();
	void turnRight();

	float GetRadius() { return radius; }
private:
	float radius;
	enum{
		LEGS_MESH,
		BODY_MESH,
		HEAD_MESH,
		EARS_MESH,
		EYES_MESH,

		NUM_OF_MESHES
	};
	Mesh* legs;
	Mesh* body;
	Mesh* head;
	Mesh* ears;
	Mesh* eyes;

	Mesh* meshes[NUM_OF_MESHES];
	float m_angle = 270.0f;
	//k¹t o który bêdziemy siê obracaæ przy przyciœniêciu klawisza
	const float ALFA = 5.0f;
};

