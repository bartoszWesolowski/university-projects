#include "camera.h"
#include <iostream>

Camera::~Camera()
{
}

void Camera::moveForward() {
	m_position.x += 0.05 * cosf(toRadians(m_angle));
	m_position.z += 0.05 * sinf(toRadians(m_angle));
	std::cout << "x: " << m_position.x << "\n";
	std::cout << "y: " << m_position.z << "\n";
}

void Camera::moveBackward() {
	m_position.x -= 0.05 * cosf(toRadians(m_angle));
	m_position.z -= 0.05 * sinf(toRadians(m_angle));

	std::cout << "x: " << m_position.x << "\n";
	std::cout << "y: " << m_position.z << "\n";
}
void Camera::turnLeft() { 
	m_angle -= ALFA;
	m_forward.x = cosf(toRadians(m_angle));
	m_forward.z = sinf(toRadians(m_angle));
	//std::cout << "angle: " << m_angle << "\n";
}

void Camera::turnRight() {
	m_angle += ALFA;
	m_forward.x = cosf(toRadians(m_angle));
	m_forward.z = sinf(toRadians(m_angle));
	//std::cout << "angle: " << m_angle << "\n";
}

void Camera::moveLeft() {
	m_position.x -= VEL_X;
}
void Camera::moveRight() {

	m_position.x += VEL_X;
}

void Camera::lookDown() {

	m_forward.y -= UP_DOWN_LOOK;
}

void Camera::lookUp() {
	m_forward.y += UP_DOWN_LOOK;
}