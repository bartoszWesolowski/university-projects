#include "display.h"
#include <GL/glew.h>
#include <iostream>


Display::Display(int width, int height, const std::string& title)
{
	//SDL tylko do okna !!!
	//inicjalizyjemy wszystko
	SDL_Init(SDL_INIT_EVERYTHING);

	int bitColorNumber = 8; //liczba bit�w na kolor
	SDL_GL_SetAttribute(SDL_GL_RED_SIZE, bitColorNumber);
	SDL_GL_SetAttribute(SDL_GL_BLUE_SIZE, bitColorNumber);
	SDL_GL_SetAttribute(SDL_GL_GREEN_SIZE, bitColorNumber);
	SDL_GL_SetAttribute(SDL_GL_ALPHA_SIZE, bitColorNumber);

	//buffer g��bi kt�ry przyrysowaniu pixeli powoduje �e GL przy rysowaniu pixeli
	//jak te si� nak�adaj� sprawdza kt�ry jest dalej od kamery, je�li rysujemy pixel kt�ry jest bli�ej kamery ni� ten istniej�cy
	//to nadpisujemy informacj�, je�li jest dalej to nic nie robimy
	SDL_GL_SetAttribute(SDL_GL_DEPTH_SIZE, 16);

	SDL_GL_SetAttribute(SDL_GL_BUFFER_SIZE, 4 * bitColorNumber); // liczba bit�w na jeden pixel na kolory, przynajmniej tyle bit�w co podamy
	SDL_GL_SetAttribute(SDL_GL_DOUBLEBUFFER, 1); // tworzymy miejsce na rysowanie w pami�ci 

	//tworzymy okno z zaznaczeniem �e b�dziemy malowa� tam openglem 
	m_window = SDL_CreateWindow(title.c_str(), SDL_WINDOWPOS_CENTERED, SDL_WINDOWPOS_CENTERED, width, height, SDL_WINDOW_OPENGL);
	//teraz mo�emy rysowa� w oknie przez GPU
	m_glContext = SDL_GL_CreateContext(m_window);

	GLenum status = glewInit();
	//je��i co� posz�o nie tak w glewInit() :
	if (status != GLEW_OK) {
		std::cerr << "\nGLEW FILED TO INITIALIZE!!!!\n";
	}

	m_isClosed = false;

	//w��cza dzia�anie DEPTH_BUFFERA, ale przy od�wie�eniu obrazu na ko�cu nale�y wyczy�ci� test g��bi bo 
	//zaczynaj�� rysowanie mamy ju� jakie� pixele i nie ma ich bra� pod uwag� por�wnuj�c odleg�o�� od kamery
	glEnable(GL_DEPTH_TEST);

	//nie rysujemy �cian kt�rych twarz jeste w stron� przeciwn� do kamery  - przydatne do typowych figur
	//glEnable(GL_CULL_FACE);
	//glCullFace(GL_BACK);
}


Display::~Display()
{
	//porz�dek jest wa�ny tutaj!!
	SDL_GL_DeleteContext(m_glContext);
	SDL_DestroyWindow(m_window);
	SDL_Quit();
}


void Display::Update() {
	//mamy miejsce na dwa okna w bufferach, jedno dla okna, drugie dla openGl
	//wy�wietla si� to co w oknie wi�c je�li chcemy pokaza� to co opengl ma naryswowa� musimy zamieni� je miejscami
	//po to �eby okno nie wy�wietli�o czego� co jeszcze nie sko�czy�o si� rysowa� !!
	SDL_GL_SwapWindow(m_window);

	//obs�uga event�w;

	SDL_Event e;

	while (SDL_PollEvent(&e)) {
		if (e.type == SDL_QUIT) {
			m_isClosed = true;
		}

		if (e.type == SDL_KEYDOWN) {
			scene->KeyPressed(e.key.keysym.sym);
		}
	}

}


bool Display::IsClosed() {
	return m_isClosed;
}

void Display::Clear(float r, float g, float b, float a) {
	glClearColor(r, g, b, a);
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
}