#include "scene.h"
#include "math.h"


Scene::Scene()
{
	m_camera = new Camera(glm::vec3(0.2f, 1.0f, 1.7f), 70.0f, 1.333f, 0.01f, 1000.0f);
	m_robotCamera = new Camera(glm::vec3(0.2f, 1.0f, 1.7f), 70.0f, 1.333f, 0.01f, 1000.0f);
	m_robotCamera->setPosition(glm::vec3(-0.6, 0.6, 0.07));
	m_robotCamera->setForward(glm::vec3(1.0, -0.6, 0.0));

	m_transform = new Transform();

	currentCamera = m_robotCamera;
}


void Scene::PrepareObjects() {
	simpeShade = new Shader("./res/cubeShader");

	textureShaders = new Shader("./res/shader");
	
	Texture* ballTexture = new Texture("./res/textures/podstawa.bmp");
	Texture* planeTexture = new Texture("./res/textures/grass.jpg");
	Texture* coinTexture = new Texture("./res/textures/coin.jpg");
	Texture* stadiumTexture = new Texture("./res/textures/stadium.jpg");
	Texture* skyboxTexture = new Texture("./res/textures/skybox.jpg");

	createCoins(numberOfCoins, textureShaders, currentCamera, coinTexture);


	stadium = new Mesh("./res/obj/stadium.obj", textureShaders, currentCamera, stadiumTexture);
	stadium->getTransform()->setPos(glm::vec3(3.5, 4.0, 0.0));
	stadium->getTransform()->setScale(glm::vec3(2.0, 2.0, 2.0));
	//stadium->getTransform()->getRot().y = 90;

	skybox = new Mesh("./res/obj/skybox.obj", textureShaders, currentCamera, skyboxTexture);
	skybox->getTransform()->setPos(glm::vec3(-25.5, 0.0, -10.0));
	skybox->getTransform()->setScale(glm::vec3(25.0, 25.0, 25.0));


	//cube2 = new Cube(glm::vec3(0.5f, 0.0f, 0.0f), 0.2f, simpeShade, currentCamera);
	//cube2->setColor(glm::vec3(0.0, 1.0, 0.0));

	plane = new Mesh("./res/obj/cub.obj", textureShaders, currentCamera, planeTexture);
	plane->getTransform()->setPos(glm::vec3(-500, -0.05, -500));
	plane->getTransform()->setScale(glm::vec3(1000.0, 0.01f, 1000.0));

	axisX = new Axis(glm::vec3(0.0f, 0.0f, 0.0f), glm::vec3(100.0f, 0.0f, 0.0f), glm::vec3(1.0f, 0.0f, 0.0f), simpeShade, currentCamera);
	axisY = new Axis(glm::vec3(0.0f, 0.0f, 0.0f), glm::vec3(0.0f, 100.0f, 0.0f), glm::vec3(0.0f, 1.0f, 0.0f), simpeShade, currentCamera);
	axisZ = new Axis(glm::vec3(0.0f, 0.0f, 0.0f), glm::vec3(0.0f, 0.0f, 1000.0f), glm::vec3(0.0f, 0.0f, 1.0f), simpeShade, currentCamera);



	//ballTexture->Bind(0);
	ball = new Ball("./res/obj/sphere.obj", textureShaders, currentCamera, ballTexture);

	ball->getTransform()->setScale(glm::vec3(0.1, 0.1f, 0.1f));
	ball->getTransform()->getPos().y += 0.3;


	robot1 = new Robot(textureShaders, currentCamera);
	robot1->Scale(0.02f);
	robot1->Move(glm::vec3(0.0, 0.2, 0.0));
}


void Scene::UpdateRobotCamera(Robot* robot) {
	glm::vec3 rpos = robot->GetCurrentCenterPosition();
	m_robotCamera->setPosition(glm::vec3(rpos.x - 0.9, rpos.y + 0.6, rpos.z - 0.01));
}


void Scene::createCoins(int number, Shader* shader, Camera* camera, Texture* texture) {
	glm::vec3 coinPos(0.4, 0.05, 0.0);
	coins.clear();
	for (int i = 0; i < numberOfCoins; i++) {
		coins.push_back(new Coin(shader, camera, texture));
		coins[i]->Move(coinPos);
		coinPos.x = Math::GetRandom(-2.6, 2.50);
		coinPos.z = Math::GetRandom(-10.6, -2.50);
	}
}


Scene::~Scene()
{

}

void Scene::Draw() {
	//cube1->Draw();

	//plane->Draw();

	textureShaders->UpdateAmbient(ambient);


	axisX->Draw();
	axisY->Draw();
	axisZ->Draw();

	ball->moveInACircle(1.0f);
	ball->Draw();

	//robot1->turnLeft();
	UpdateRobotCamera(robot1);
	robot1->Draw();

	stadium->Draw();

	skybox->Draw();
	for (int i = 0; i < coins.size(); i++) {
		coins[i]->getTransform()->getRot().x += 2.0f;
		coins[i]->getTransform()->getRot().y += 2.0f;
		coins[i]->Draw();
	}

	robot1->collectCoins(coins);
}

const int W_KEY = 119;
const int A_KEY = 97;
const int S_KEY = 115;
const int D_KEY = 100;

const int KEY_1 = 1073741913; 
const int KEY_2 = 1073741914; 
const int KEY_3 = 1073741915; 
const int KEY_4 = 1073741916;
const int KEY_5 = 1073741917;
const int KEY_6 = 1073741918;
const int KEY_7 = 1073741919;
const int KEY_8 = 1073741920;
const int KEY_9 = 1073741921;

//plus na klawiaturze numerycznej
const int KEY_PLUS = 1073741911;
//minus na klawiaturze numerycznej
const int KEY_MINUS = 1073741910;

const int ARROW_UP = 1073741906;
const int ARROW_DOWN = 1073741905;
const int ARROW_LEFT = 1073741904;
const int ARROW_RIGHT = 1073741903;



const int KEY_F1 = 1073741882;
const int KEY_F2 = 1073741883;
void Scene::KeyPressed(int key) {
	std::cout << "Wciœniêto klawisz: " << key << "\n";
	switch (key) {
		case D_KEY: {
			//cube2->getTransform()->getPos().x += MOVING_SPEED;
			break;
		}
		case A_KEY: {
			//cube2->getTransform()->getPos().x -= MOVING_SPEED;
			break;
		}
		case W_KEY: {
			//cube2->getTransform()->getPos().z -= MOVING_SPEED;
			//m_transform->getPos().y += MOVING_SPEED;
			break;
		}
		case S_KEY: {
			//cube2->getTransform()->getPos().z += MOVING_SPEED;
			//m_transform->getPos().y -= MOVING_SPEED;
			break;
		}

		//obs³uga kamery!!
		case KEY_PLUS: {
						m_camera->getPosition().y += MOVING_SPEED;
						break;
		}
		case KEY_MINUS: {
						   m_camera->getPosition().y -= MOVING_SPEED;
						   break;
		}

		case KEY_1: {
						m_camera->lookUp(); 
						break;
		}
		case KEY_3: {
						m_camera->lookDown();
						break;
		}

		case KEY_4: {
						m_camera->turnLeft();
						break;
		}
		case KEY_6: {
						m_camera->turnRight();
						break;
		}
		case KEY_8: {
						m_camera->moveForward();
						break;
		}
		case KEY_2: {
						m_camera->moveBackward();
						break;
		}

		case KEY_7: {
						m_camera->moveLeft();
						break;
		}
		case KEY_9: {
						m_camera->moveRight();
						break;
		}

		//przesuwanie kamery
		case ARROW_LEFT : {
			robot1->turnLeft();
			robot1->collectCoins(coins);
			break;
		}
		case ARROW_RIGHT: {
			robot1->turnRight();
			robot1->collectCoins(coins);
			break;
		}

		case ARROW_UP: {
			robot1->moveForward();
			robot1->collectCoins(coins);
			break;
		}
		case ARROW_DOWN: {
			robot1->moveBackward();
			robot1->collectCoins(coins);
			break;
		}

		case KEY_F1: {
			addAmbientLight(AMBIENT_DIFF);
			break;
		}
		case KEY_F2: {
			addAmbientLight(-1.0 * AMBIENT_DIFF);
			break;
		}
	}

	std::cout << robot1->GetCurrentCenterPosition().x << " " << robot1->GetCurrentCenterPosition().y << " " << robot1->GetCurrentCenterPosition().z << "\n";
}

