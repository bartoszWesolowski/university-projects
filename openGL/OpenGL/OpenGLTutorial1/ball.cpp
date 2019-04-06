#include "ball.h"
#include <iostream>

Ball::~Ball()
{
}

void Ball::moveBall() {
	m_transform->getPos().x += velX;
	m_transform->getRot().z -= velX * 1000;


	if (m_transform->getPos().x > maxX || m_transform->getPos().x < 0.0f) {
		velX *= -1.0;
	}
}

void Ball::moveInACircle(float radius) {
	m_transform->getPos().x = startPosition.x + cosf(2 * PI * currentTime / loopDuration) * radius;
	//std::cout << "X: " << m_transform->getPos().x << "\n";
	m_transform->getPos().z = startPosition.z + sinf(2 * PI * currentTime / loopDuration) * radius;
	//std::cout << "Z: " << m_transform->getPos().z << "\n";
	currentTime += deltaTime;
}