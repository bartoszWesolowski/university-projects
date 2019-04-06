#include "tree.h"


Tree::Tree(Shader* shader, Camera* camera) : GlObject(shader, camera)
{

	trunk = new Mesh("./res/obj/pien.obj", shader, m_camera, NULL);
	up_part = new Mesh("./res/obj/korona.obj", shader, m_camera, NULL);

	up_part->SetColor(glm::vec3(0.0, 0.75, 0.2));
	up_part->getTransform()->setScale(glm::vec3(0.09, 0.09, 0.09));
	up_part->SetAtPoint(glm::vec3(0.0255, 0.25, -0.02));

	trunk->SetColor(glm::vec3(1.0, 1.0, 0.2));
	trunk->getTransform()->setScale(glm::vec3(0.1, 0.1, 0.1));
}

void Tree::Draw() {
	trunk->Draw();
	up_part->Draw();
}

Tree::~Tree()
{
}
