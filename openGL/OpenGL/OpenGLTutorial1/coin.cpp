#include "coin.h"
#include <iostream>


Coin::Coin(Shader* shader, Camera* camera, Texture* texture) : Mesh("./res/obj/coin.obj", shader, camera, texture) {
	m_transform->setPos(glm::vec3(0, 0, 0));
	radius = (bounds_X.y - bounds_X.x) / 2.0f;
	Scale(0.08f);
}

void Coin::Scale(float scale) {
	m_transform->setScale(glm::vec3(scale, scale, scale));
	radius *= scale;
	std::cout << "Promieñ: " << radius << "\n";
}

Coin::~Coin() { }
