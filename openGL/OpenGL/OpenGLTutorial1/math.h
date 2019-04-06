#pragma once
#include <cstdlib>
#include <math.h>
#include <glm/glm.hpp>
class Math
{
public:
	Math();
	static float GetRandom(float min, float max) {
		return min + static_cast <float> (rand()) / (static_cast <float> (RAND_MAX / (max - min)));
	}

	static float clapm(float x, float min, float max) {
		if (x < min) { return min; }
		if (x > max) { return max; }
		return x;
	}

	static float getDistance(glm::vec3 start, glm::vec3 end) {
		return sqrt(pow(start.x - end.x, 2) + pow(start.y - end.y, 2) + pow(start.z - end.z, 2)) ;
	}
	~Math();
};

