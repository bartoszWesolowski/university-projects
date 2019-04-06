#include "mesh.h"
#include <vector>
#include <iostream>


Mesh::Mesh(const std::string& fileName, Shader* shader, Camera* camera, Texture* texture) : GlObject(shader, camera) {
	this->m_texture = texture;
	IndexedModel model = OBJModel(fileName).ToIndexedModel();
	InitMesh(model);
}

Mesh::Mesh(Vertex* verticies, unsigned int numVertecies, unsigned int* indices, unsigned int numIndices) {
	IndexedModel model;

	for (unsigned i = 0; i < numVertecies; i++) {
		model.positions.push_back(*verticies[i].getPos());
		model.texCoords.push_back(*verticies[i].getTexCoords());
	}
	
	for (unsigned int i = 0; i < numIndices; i++) {
		model.indices.push_back(indices[i]);
	}

	InitMesh(model);
}


Mesh::~Mesh()
{
	glDeleteVertexArrays(1, &m_vertexArrayObject);
}

void Mesh::Draw() {
	BindShader();
	if (m_texture != NULL) {
		m_texture->Bind(0);
	}
	UpdateShader();
	glBindVertexArray(m_vertexArrayObject);
	glDrawElements(GL_TRIANGLES, m_drawCount, GL_UNSIGNED_INT, 0);

	//glDrawArrays(GL_TRIANGLES, 0, m_drawCount); // jak du¿o wierzcho³ków narysowaæ

	glBindVertexArray(0);
}

void Mesh::SetColor(const glm::vec3& color) {

	std::vector<glm::vec3> colors;
	for (int i = 0; i < m_drawCount; i++) {
		colors.push_back(color);
	}
	glBindVertexArray(m_vertexArrayObject);

	glBindBuffer(GL_ARRAY_BUFFER, m_vertexArrayBuffers[COLOR_VB]);
	glBufferData(GL_ARRAY_BUFFER, m_drawCount* sizeof(colors[0]), &colors[0], GL_STATIC_DRAW);
	glEnableVertexAttribArray(Shader::COLOR_ATTRIB_LOCATION); // dane w attrib array 0
	//ka¿dy kawa³ek danych to 3 floaty !
	glVertexAttribPointer(Shader::COLOR_ATTRIB_LOCATION, 3, GL_FLOAT, GL_FALSE, 0, 0);
	glBindVertexArray(0);

}

void Mesh::InitMesh(const IndexedModel& model) {


	m_drawCount = model.indices.size();
	getBounds(model.positions);

	glGenVertexArrays(1, &m_vertexArrayObject);
	glBindVertexArray(m_vertexArrayObject);

	glGenBuffers(NUM_BUFFERS, m_vertexArrayBuffers);

	//--------------------POSITIONS CORDS DO SHADERA ----------------------------------------
	glBindBuffer(GL_ARRAY_BUFFER, m_vertexArrayBuffers[POSITION_VB]);
	//wype³niamy bufer danymi
	glBufferData(GL_ARRAY_BUFFER, model.positions.size() * sizeof(model.positions[0]), &model.positions[0], GL_STATIC_DRAW);
	glEnableVertexAttribArray(Shader::POSITION_ATTRIB_LOCATION); // dane w attrib array 0
	//ka¿dy kawa³ek danych to 3 floaty !
	glVertexAttribPointer(Shader::POSITION_ATTRIB_LOCATION, 3, GL_FLOAT, GL_FALSE, 0, 0);

	//---------------------TEXCORDS DO SHADERA --------------------------------------------
	glBindBuffer(GL_ARRAY_BUFFER, m_vertexArrayBuffers[TEXCORD_VB]);
	//wype³niamy bufer danymi
	glBufferData(GL_ARRAY_BUFFER, model.texCoords.size() * sizeof(model.texCoords[0]), &model.texCoords[0], GL_STATIC_DRAW);

	glEnableVertexAttribArray(Shader::TEXTURE_COORDINATE_ATTRIB_LOCATION); // dane w attrib array 0
	//ka¿dy kawa³ek danych to 3 floaty !
	glVertexAttribPointer(Shader::TEXTURE_COORDINATE_ATTRIB_LOCATION, 2, GL_FLOAT, GL_FALSE, 0, 0);

	//------------------Normals Do SHADERA -------------------------------
	glBindBuffer(GL_ARRAY_BUFFER, m_vertexArrayBuffers[NORMAL_VB]);
	//wype³niamy bufer danymi
	glBufferData(GL_ARRAY_BUFFER, model.normals.size() * sizeof(model.normals[0]), &model.normals[0], GL_STATIC_DRAW);
	glEnableVertexAttribArray(Shader::NORMAL_ATTRIB_LOCATION); // dane w attrib array 2
	//ka¿dy kawa³ek danych to 3 floaty !
	glVertexAttribPointer(Shader::NORMAL_ATTRIB_LOCATION, 3, GL_FLOAT, GL_FALSE, 0, 0);


	//------------------INDEKSY DO SHADERA ---------------------------
	glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, m_vertexArrayBuffers[INDEX_VB]);
	//wype³niamy bufer danymi
	glBufferData(GL_ELEMENT_ARRAY_BUFFER, model.indices.size() * sizeof(model.indices[0]), &model.indices[0], GL_STATIC_DRAW);

	//odbindowuje wszystko - odpina
	glBindVertexArray(0);
}

void Mesh::SetAtPoint(glm::vec3 point) {
	m_transform->setPos(point);
}
void Mesh::SetAtOrigin(){
	SetAtPoint(glm::vec3(0.0, 0.0, 0.0));
}

void Mesh::updateCenterPosition() {
	glm::vec4 pos = getTransform()->getModel() * glm::vec4(center_pos, 1.0);
	center_pos.x = pos.x;
	center_pos.y = pos.y;
	center_pos.z = pos.z;
}

glm::vec3 Mesh::getCurrentCentrPos() {
	updateCenterPosition();
	return center_pos;
}
void Mesh::getBounds(std::vector<glm::vec3> verticies) {
	if (!verticies.empty()) {
		bounds_X = glm::vec2(verticies[0].x, verticies[0].x);
		bounds_Y = glm::vec2(verticies[0].y, verticies[0].y);
		bounds_Z = glm::vec2(verticies[0].z, verticies[0].z);
	}
	for (unsigned int i = 0; i < verticies.size(); i++) {
		//
		if (bounds_X.x > verticies[i].x) {
			bounds_X.x = verticies[i].x;
		}

		if (bounds_X.y < verticies[i].x) {
			bounds_X.y = verticies[i].x;
		}

		if (bounds_Y.x > verticies[i].y) {
			bounds_Y.x = verticies[i].y;
		}

		if (bounds_Y.y < verticies[i].y) {
			bounds_Y.y = verticies[i].y;
		}

		if (bounds_Z.x > verticies[i].z) {
			bounds_Z.x = verticies[i].z;
		}

		if (bounds_Z.y < verticies[i].z) {
			bounds_Z.y = verticies[i].z;
		}
	}

	center_pos = glm::vec3((bounds_X.x + bounds_X.y) / 2.0f, (bounds_Z.x + bounds_Z.y) / 2.0f, (bounds_Z.x + bounds_Z.y) / 2.0f);
}