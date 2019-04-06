#pragma once
#include <glm/glm.hpp>
#include <glm/gtx/transform.hpp>
#include <iostream>

class Camera
{
public:
	//fov - float of view, 
	//zNear - z najbli¿ej które widzimy
	Camera(const glm::vec3& pos, float fov, float aspect, float zNear, float zFar) {
		m_perspective = glm::perspective(fov, aspect, zNear, zFar);
		m_position = pos; // póŸniej gettery i settery jeœli bêdzie trzeba operowaæ kamer¹
		m_forward = glm::vec3(0, -1.5, -1.0);
		m_up = glm::vec3(0, 1, 0);
	}

	inline glm::mat4 GetVievProjection() const {
		return m_perspective * glm::lookAt(m_position, m_position + m_forward, m_up);
	}

	inline void setPosition(const glm::vec3& pos) { m_position = pos; }
	inline void setForward(glm::vec3& forward) { m_forward = forward; }
	inline glm::vec3& getForward() { return m_forward; }
	inline glm::vec3& getPosition() { return m_position; }
	void moveForward();
	void moveBackward();	
	void moveLeft();
	void moveRight();
	void lookDown();
	void lookUp();
	void turnLeft(); 
	void turnRight(); 
	~Camera();
	Camera() {}

	inline static float toRadians(float angle) {
		return angle * (3.1415f / 180.f);
	}

	void print() {
		std::cout << "Pos: (" << m_position.x << ", " << m_position.y << ", " << m_position.z << ")\n";
		std::cout << "Forward: (" << m_forward.x << ", " << m_forward.y << ", " << m_forward.z << ")\n";
	}
private:
	glm::mat4 m_perspective;
	glm::vec3 m_position;
	glm::vec3 m_forward; // kierunek do przodu w który patrzymy
	glm::vec3 m_up;  //kierunek góry
	float m_angle = 270.0f;
	//k¹t o który bêdziemy siê obracaæ przy przyciœniêciu klawisza
	const float ALFA = 5.0f;
	const float VEL_X = 0.1f;
	const float VEL_Y = 0.1f;
	const float UP_DOWN_LOOK = 0.1f;


	
};

