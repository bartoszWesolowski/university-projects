#pragma once
#include "glObject.h"
#include "mesh.h"
class Tree :
	public GlObject
{
public:
	Tree(Shader* shader, Camera* camera);
	void Draw();
	void setPos(glm::vec3 pos) {
		trunk->getTransform()->setPos(pos);
		up_part->getTransform()->setPos(pos);

	}
	~Tree();

private:
	Mesh* trunk;
	Mesh* up_part;
};

