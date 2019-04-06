#include "axis.h"


Axis::Axis(const glm::vec3 start, const glm::vec3& end, const glm::vec3& color, Shader* shader, Camera* camera) :
	GlObject(shader, camera)
{
	points[0] = start;
	points[1] = end;

	colors[0] = color;
	colors[1] = color;

	glGenVertexArrays(1, &m_vertexArrayObject);
	glBindVertexArray(m_vertexArrayObject);

	glGenBuffers(1, &m_vertexBufferObject);

	glBindBuffer(GL_ARRAY_BUFFER, m_vertexBufferObject);
	glBufferData(GL_ARRAY_BUFFER, 2 * sizeof(points[0]), &points[0], GL_STATIC_DRAW);
	
	//zero czy 4 ??????
	glEnableVertexAttribArray(Shader::POSITION_ATTRIB_LOCATION); // dane w attrib array 0
	//ka¿dy kawa³ek danych to 3 floaty !
	glVertexAttribPointer(Shader::POSITION_ATTRIB_LOCATION, 3, GL_FLOAT, GL_FALSE, 0, 0);
	

	glGenBuffers(1, &m_colorBufferObject);
	//przekazujemy kolor
	glBindBuffer(GL_ARRAY_BUFFER, m_colorBufferObject);
	glBufferData(GL_ARRAY_BUFFER, 2 * sizeof(colors[0]), &colors[0], GL_STATIC_DRAW);


	glEnableVertexAttribArray(Shader::COLOR_ATTRIB_LOCATION); // dane w attrib array 0
	//ka¿dy kawa³ek danych to 3 floaty !
	glVertexAttribPointer(Shader::COLOR_ATTRIB_LOCATION, 3, GL_FLOAT, GL_FALSE, 0, 0);

	glBindVertexArray(0);
}


void Axis::Draw() {
	BindShader();
	UpdateShader();
	glBindVertexArray(m_vertexArrayObject);

	glLineWidth(2.0f);
	glDrawArrays(GL_LINE_LOOP, 0, 2);

	glBindVertexArray(0);
}
Axis::~Axis()
{
}
