#include "cube.h"


Cube::Cube(const glm::vec3& pos, float size, Shader* shader, Camera* camera) :
	GlObject(shader, camera) {

	glm::vec3 vertecise[8] = {
		pos,
		glm::vec3(pos.x + size, pos.y, pos.z),
		glm::vec3(pos.x + size, pos.y, pos.z + size),
		glm::vec3(pos.x, pos.y, pos.z + size),
		glm::vec3(pos.x, pos.y + size, pos.z),
		glm::vec3(pos.x + size, pos.y + size, pos.z),
		glm::vec3(pos.x + size, pos.y + size, pos.z + size),
		glm::vec3(pos.x, pos.y + size, pos.z + size),
	};	

	glm::vec2 texCords[8] = {
		glm::vec2(0.1, 0.5),
		glm::vec2(0.5, 0.5),
		glm::vec2(0.5, 0.1),
		glm::vec2(0.1, 0.5),
		glm::vec2(0.5, 0.5),
		glm::vec2(0.5, 0.1),
		glm::vec2(0.1, 0.5),
		glm::vec2(0.5, 0.5),
	};


	unsigned int indices[] = {
		0, 1, 2,
		0, 2, 3,
		0, 1, 5,
		0, 5, 4,
		4, 5, 6,
		4, 6, 7,
		3, 2, 6,
		3, 6, 7,
		1, 2, 6,
		1, 6, 5,
		0, 3, 7,
		0, 7, 4,
	};
	glGenVertexArrays(1, &m_vertexArrayObject);
	glBindVertexArray(m_vertexArrayObject);


	glGenBuffers(1, &m_vertexArrayBuffer);

	glBindBuffer(GL_ARRAY_BUFFER, m_vertexArrayBuffer);
	//wype³niamy bufer danymi
	glBufferData(GL_ARRAY_BUFFER, 8 * sizeof(vertecise[0]), &vertecise[0], GL_STATIC_DRAW);
	glEnableVertexAttribArray(Shader::POSITION_ATTRIB_LOCATION); // dane w attrib array 0
	//ka¿dy kawa³ek danych to 3 floaty !
	glVertexAttribPointer(Shader::POSITION_ATTRIB_LOCATION, 3, GL_FLOAT, GL_FALSE, 0, 0);

	//kolory
	glGenBuffers(1, &m_colorArrayBuffer);

	glBindBuffer(GL_ARRAY_BUFFER, m_colorArrayBuffer);
	//wype³niamy bufer danymi
	glBufferData(GL_ARRAY_BUFFER, 8 * sizeof(vertecise[0]), &vertecise[0], GL_STATIC_DRAW);
	glEnableVertexAttribArray(Shader::COLOR_ATTRIB_LOCATION); // dane w attrib array 0
	//ka¿dy kawa³ek danych to 3 floaty !
	glVertexAttribPointer(Shader::COLOR_ATTRIB_LOCATION, 3, GL_FLOAT, GL_FALSE, 0, 0);

	//wspó³rzêdne tekstury
	glGenBuffers(1, &m_texCoordsArrayBuffer);

	glBindBuffer(GL_ARRAY_BUFFER, m_texCoordsArrayBuffer);
	//wype³niamy bufer danymi
	glBufferData(GL_ARRAY_BUFFER, 8 * sizeof(texCords[0]), &texCords[0], GL_STATIC_DRAW);
	glEnableVertexAttribArray(Shader::TEXTURE_COORDINATE_ATTRIB_LOCATION); // dane w attrib array 0
	//ka¿dy kawa³ek danych to 3 floaty !
	glVertexAttribPointer(Shader::TEXTURE_COORDINATE_ATTRIB_LOCATION, 2, GL_FLOAT, GL_FALSE, 0, 0);


	//indeksy
	glGenBuffers(1, &m_indexArrayBuffer);
	glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, m_indexArrayBuffer);
	//wype³niamy bufer danymi

	glBufferData(GL_ELEMENT_ARRAY_BUFFER, sizeof(indices), &indices[0], GL_STATIC_DRAW);

	glBindVertexArray(0);
}


void Cube::Draw() {

	BindShader();
	UpdateShader();
	glBindVertexArray(m_vertexArrayObject);
	glDrawElements(GL_TRIANGLES, 36, GL_UNSIGNED_INT, 0);

	//glDrawArrays(GL_TRIANGLES, 0, m_drawCount); // jak du¿o wierzcho³ków narysowaæ

	glBindVertexArray(0);
}

void Cube::setColor(const glm::vec3& color) {
	glm::vec3 colors[8];
	for (unsigned int i = 0; i < 8; i++) {
		colors[i] = color;
	}

	glBindBuffer(GL_ARRAY_BUFFER, m_colorArrayBuffer);
	//wype³niamy bufer danymi
	glBufferSubData(GL_ARRAY_BUFFER, 0, 8 * sizeof(colors[0]), &colors[0]);

}

Cube::~Cube()
{
}
