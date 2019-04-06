#pragma once
#include <string>
#include <SDL2\SDL.h>
#include "scene.h"
class Display
{
public:
	Display(int width, int height, const std::string& title);
	~Display();

	void Clear(float r, float g, float b, float a);
	void Update();
	bool IsClosed();
	inline void setScene(Scene* s) { scene = s; }

private:
	SDL_Window* m_window;
	//kontekst który pozwala na przekazanie danych do okna poprzez GPU (nasze zmienne) a nie system operacyjny "przejêcie kontroli nad oknem"
	SDL_GLContext m_glContext;

	bool m_isClosed;
	Scene* scene;
};

