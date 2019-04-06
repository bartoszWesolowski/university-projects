#include <iostream>
#include <GL/glew.h>
#include <vector>
#include <iostream>

#include "display.h"
#include "shader.h"
#include "mesh.h"
#include "texture.h"
#include "transform.h"
#include "camera.h"
#include "axis.h"
#include "cube.h"
#include "math.h"


int main(int argc, char** argv) {
	///*
	std::vector<glm::vec3> vec;
	std::cout << vec.max_size() << "\n";
	Display display(1000, 800, "Hello World");
	Scene* scene = new Scene();
	scene->PrepareObjects();

	display.setScene(scene);

	//Mesh monkeyMesh("./res/monkey3.obj");
	//Shader shaders("./res/shader");
	//Texture texture("./res/bricks.jpg");

	//shaders.Bind();
	//texture.Bind(0);

	std::cout<<Math::getDistance(glm::vec3(0.0, 0.0, 0.0), glm::vec3(1.0, 0.0, 0.0)) << "\n";
	while (!display.IsClosed()) {
		display.Clear(0.1f, 0.15f, 0.3f, 1.0f);

		scene->Draw();

		display.Update();
	}

	//system("pause");
	return 0;
}


