#pragma once
#include "shader.h"
#include "transform.h"
#include "camera.h"
class GlObject
{
public:
	GlObject();
	GlObject(Shader* shader, Camera* camera) {
		m_transform = new Transform();
		m_camera = camera;
		m_shader = shader;
	}

	~GlObject();
	void BindShader() { m_shader->Bind(); }
	virtual void Draw();
	void UpdateShader() {
		m_shader->Update(*m_transform, *m_camera);
	}

	Transform* getTransform() { return m_transform;}


protected:
	Camera* m_camera;
	Transform* m_transform;
	Shader* m_shader;
};
