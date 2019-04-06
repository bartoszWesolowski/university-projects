#include "robot.h"
#include "camera.h"
#include "math.h"
#include <iostream>

Robot::Robot(Shader* shader, Camera* camera) : GlObject(shader, camera)
{
	Texture* bodyTexture = new Texture("./res/textures/barca.jpg");
	body = new Mesh("./res/obj/body.obj", shader, m_camera, bodyTexture);
	radius = (body->bounds_X.y - body->bounds_X.x) / 2.0f;
	meshes[BODY_MESH] = body;

	Texture* headTexture = new Texture("./res/textures/head.jpg");
	head = new Mesh("./res/obj/head.obj", shader, m_camera, headTexture);
	meshes[HEAD_MESH] = head;

	Texture* earsTexture = new Texture("./res/textures/ears.jpg");
	ears = new Mesh("./res/obj/ears.obj", shader, m_camera, earsTexture);
	meshes[EARS_MESH] = ears;

	Texture* eyesTexture = new Texture("./res/textures/eyes.jpg");
	eyes = new Mesh("./res/obj/eyes.obj", shader, m_camera, eyesTexture);
	meshes[EYES_MESH] = eyes;

	Texture* legsTexture = new Texture("./res/textures/legs.jpg");
	legs = new Mesh("./res/obj/legs.obj", shader, m_camera, legsTexture);
	meshes[LEGS_MESH] = legs;

	for (unsigned int i = 0; i < NUM_OF_MESHES; i++) {
		meshes[i]->getTransform()->getRot().y = 200;
	}

	m_angle = 90.0f;
}


void Robot::Draw() {
	for (unsigned int i = 0; i < NUM_OF_MESHES; i++) {
		meshes[i]->Draw();
	}

}


void Robot::Move(glm::vec3 offset) {
	for (unsigned int i = 0; i < NUM_OF_MESHES; i++) {
		meshes[i]->Move(offset);
	}
}



void Robot::Scale(float scale) {
	for (unsigned int i = 0; i < NUM_OF_MESHES; i++) {
		meshes[i]->Scale(glm::vec3(scale, scale, scale));
	}

	radius *= scale;
}

glm::vec3 Robot::GetCurrentCenterPosition() {
	return meshes[BODY_MESH]->getCurrentCentrPos();
}


void Robot::moveForward() {
	for (unsigned int i = 0; i < NUM_OF_MESHES; i++) {
		meshes[i]->getTransform()->getPos().x -= 0.05 * cosf(Camera::toRadians(m_angle));
		meshes[i]->getTransform()->getPos().z -= 0.05 * sinf(Camera::toRadians(m_angle));
	}
}

void Robot::moveBackward() {

	for (unsigned int i = 0; i < NUM_OF_MESHES; i++) {
		meshes[i]->getTransform()->getPos().x += 0.05 * cosf(Camera::toRadians(m_angle));
		meshes[i]->getTransform()->getPos().z += 0.05 * sinf(Camera::toRadians(m_angle));
	}

}
void Robot::turnLeft() {
	m_angle -= ALFA;

	for (unsigned int i = 0; i < NUM_OF_MESHES; i++) {
		meshes[i]->getTransform()->getRot().y += ALFA;
		meshes[i]->getTransform()->getPos().x -= 0.05 * cosf(Camera::toRadians(m_angle));
		meshes[i]->getTransform()->getPos().z -= 0.05 * sinf(Camera::toRadians(m_angle));
	}
}

void Robot::turnRight() {
	m_angle += ALFA;

	for (unsigned int i = 0; i < NUM_OF_MESHES; i++) {
		meshes[i]->getTransform()->getRot().y -= ALFA;
		meshes[i]->getTransform()->getPos().x -= 0.05 * cosf(Camera::toRadians(m_angle));
		meshes[i]->getTransform()->getPos().z -= 0.05 * sinf(Camera::toRadians(m_angle));
	}
}


void Robot::collectCoins(std::vector<Coin*>& coins) {
	glm::vec3 start = GetCurrentCenterPosition();
	glm::vec3 end;
	for (std::vector<Coin*>::iterator it = coins.begin(); it != coins.end(); ) {
		end = (*it)->getCurrentCentrPos();
		float distance = radius + (*it)->GetRadius();
		if (Math::getDistance(start, end) <= distance) {
			std::cout << "ZEBRANO MONETE!\n";
			delete * it;
			it = coins.erase(it);
		}
		else {
			++it;
		}
	}
}
Robot::~Robot()
{
}
