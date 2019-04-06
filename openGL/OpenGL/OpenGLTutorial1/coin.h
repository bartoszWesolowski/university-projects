#pragma once
#include "mesh.h"
class Coin : public Mesh {
private:
	float radius;
public:
	Coin(Shader* shader, Camera* camera, Texture* texture);
	Coin() { }
	void Scale(float scale);
	float GetRadius() { return radius; }
	~Coin();
};

