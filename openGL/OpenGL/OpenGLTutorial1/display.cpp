#include "display.h"
#include <GL/glew.h>
#include <iostream>


Display::Display(int width, int height, const std::string& title)
{
	//SDL tylko do okna !!!
	//inicjalizyjemy wszystko
	SDL_Init(SDL_INIT_EVERYTHING);

	int bitColorNumber = 8; //liczba bitów na kolor
	SDL_GL_SetAttribute(SDL_GL_RED_SIZE, bitColorNumber);
	SDL_GL_SetAttribute(SDL_GL_BLUE_SIZE, bitColorNumber);
	SDL_GL_SetAttribute(SDL_GL_GREEN_SIZE, bitColorNumber);
	SDL_GL_SetAttribute(SDL_GL_ALPHA_SIZE, bitColorNumber);

	//buffer g³êbi który przyrysowaniu pixeli powoduje ¿e GL przy rysowaniu pixeli
	//jak te siê nak³adaj¹ sprawdza który jest dalej od kamery, jeœli rysujemy pixel który jest bli¿ej kamery ni¿ ten istniej¹cy
	//to nadpisujemy informacjê, jeœli jest dalej to nic nie robimy
	SDL_GL_SetAttribute(SDL_GL_DEPTH_SIZE, 16);

	SDL_GL_SetAttribute(SDL_GL_BUFFER_SIZE, 4 * bitColorNumber); // liczba bitów na jeden pixel na kolory, przynajmniej tyle bitów co podamy
	SDL_GL_SetAttribute(SDL_GL_DOUBLEBUFFER, 1); // tworzymy miejsce na rysowanie w pamiêci 

	//tworzymy okno z zaznaczeniem ¿e bêdziemy malowaæ tam openglem 
	m_window = SDL_CreateWindow(title.c_str(), SDL_WINDOWPOS_CENTERED, SDL_WINDOWPOS_CENTERED, width, height, SDL_WINDOW_OPENGL);
	//teraz mo¿emy rysowaæ w oknie przez GPU
	m_glContext = SDL_GL_CreateContext(m_window);

	GLenum status = glewInit();
	//jeœ³i coœ posz³o nie tak w glewInit() :
	if (status != GLEW_OK) {
		std::cerr << "\nGLEW FILED TO INITIALIZE!!!!\n";
	}

	m_isClosed = false;

	//w³¹cza dzia³anie DEPTH_BUFFERA, ale przy odœwie¿eniu obrazu na koñcu nale¿y wyczyœciæ test g³êbi bo 
	//zaczynaj¹æ rysowanie mamy ju¿ jakieœ pixele i nie ma ich braæ pod uwagê porównuj¹c odleg³oœæ od kamery
	glEnable(GL_DEPTH_TEST);

	//nie rysujemy œcian których twarz jeste w stronê przeciwn¹ do kamery  - przydatne do typowych figur
	//glEnable(GL_CULL_FACE);
	//glCullFace(GL_BACK);
}


Display::~Display()
{
	//porz¹dek jest wa¿ny tutaj!!
	SDL_GL_DeleteContext(m_glContext);
	SDL_DestroyWindow(m_window);
	SDL_Quit();
}


void Display::Update() {
	//mamy miejsce na dwa okna w bufferach, jedno dla okna, drugie dla openGl
	//wyœwietla siê to co w oknie wiêc jeœli chcemy pokazaæ to co opengl ma naryswowaæ musimy zamieniæ je miejscami
	//po to ¿eby okno nie wyœwietli³o czegoœ co jeszcze nie skoñczy³o siê rysowaæ !!
	SDL_GL_SwapWindow(m_window);

	//obs³uga eventów;

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