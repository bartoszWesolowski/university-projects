#pragma once
#include <glm/glm.hpp>
#include <GL/glew.h>
#include <vector>
#include "obj_loader.h"
#include <string>
#include "shader.h"
#include "glObject.h"
#include "texture.h"
#include <iostream>

class Vertex {
public:
	//poprzez referencjê !!
	Vertex(const glm::vec3& pos, const glm::vec2& texCoord){
		this->pos = pos;
		this->texCoord = texCoord;
	}

	glm::vec3* getPos() { return &pos; }
	glm::vec2* getTexCoords() { return &texCoord; }
private:
	glm::vec3 pos;
	glm::vec2 texCoord;
	glm::vec3 normal;
};


class Mesh : public GlObject
{
public:
	Mesh(Vertex* verticies, unsigned int numVertecies, unsigned int* indices, unsigned int numIndices);
	Mesh(const std::string& fileName, Shader* shader, Camera* camera, Texture* texture);
	~Mesh();
	Mesh() { }
	void Draw();
	void SetAtPoint(glm::vec3 point);
	void SetAtOrigin();
	void SetColor(const glm::vec3& color);

	//funckja przesuwaj¹ca mesh o vector offset
	void Move(glm::vec3 offset) {
		m_transform->getPos().x += offset.x;
		m_transform->getPos().y += offset.y;
		m_transform->getPos().z += offset.z;
	}
	void Scale(glm::vec3 scale) {
		m_transform->setScale(scale);
	}

	void printBounds() {
		std::cout << "Min x: " << bounds_X.x << " max x: " << bounds_X.y << "\n";
		std::cout << "Min y: " << bounds_Y.x << " max y: " << bounds_Y.y << "\n";
		std::cout << "Min z: " << bounds_Z.x << " max z: " << bounds_Z.y << "\n";
	}

	void printCurrentCenterPos() {
		updateCenterPosition();
		std::cout << "Center position: (" << center_pos.x << ", " << center_pos.y << ", " << center_pos.z << ")\n";
	}
	void updateCenterPosition();

	glm::vec3 getCurrentCentrPos();
	//minimalny i maksymalny iks
	glm::vec2 bounds_X;
	glm::vec2 bounds_Y;
	glm::vec2 bounds_Z;
	glm::vec3 center_pos;

private:
	void InitMesh(const IndexedModel& model);

	enum {
		POSITION_VB, //position vertex buffer
		TEXCORD_VB, //texture vertex buffer
		NORMAL_VB,
		INDEX_VB, //index vertex buffer
		COLOR_VB,

		NUM_BUFFERS
	};

	GLuint m_vertexArrayObject;
	Texture* m_texture;
	GLuint m_vertexArrayBuffers[NUM_BUFFERS];
	//jak du¿o mamy narysowaæ
	unsigned int m_drawCount;

	void getBounds(std::vector<glm::vec3> verticies);
};

