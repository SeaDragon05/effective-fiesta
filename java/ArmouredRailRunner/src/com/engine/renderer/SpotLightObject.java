package com.engine.renderer;

import org.lwjglx.util.vector.Matrix4f;

import com.engine.graphics.ShadowFrameBuffer;
import com.engine.graphics.SpotLightShader;
import com.engine.objects.SpotLight;
import com.engine.postProcess.Fbo;

public class SpotLightObject {

	public SpotLightObject(SpotLightShader shader, SpotLight light) {
		SHADOW_MAP_SIZE = light.getMapSize();
		this.shadowFbo = new Fbo(SHADOW_MAP_SIZE, SHADOW_MAP_SIZE, Fbo.DEPTH_RENDER_BUFFER, false);
		this.shader = shader;
		this.entityRenderer = new SpotLightEntityRenderer(shader, light);
		toShadowSpace = entityRenderer.getProjectionMatrix();
	}

	private int SHADOW_MAP_SIZE;

	private Fbo shadowFbo;
	
	private SpotLightShader shader;

	private SpotLightEntityRenderer entityRenderer;
	
	private Matrix4f toShadowSpace;

	public int getSHADOW_MAP_SIZE() {
		return SHADOW_MAP_SIZE;
	}

	public void setSHADOW_MAP_SIZE(int sHADOW_MAP_SIZE) {
		SHADOW_MAP_SIZE = sHADOW_MAP_SIZE;
	}

	public Fbo getShadowFbo() {
		return shadowFbo;
	}

	public void setShadowFbo(Fbo shadowFbo) {
		this.shadowFbo = shadowFbo;
	}

	public SpotLightShader getShader() {
		return shader;
	}

	public void setShader(SpotLightShader shader) {
		this.shader = shader;
	}
	
	public SpotLightEntityRenderer getEntityRenderer() {
		return this.entityRenderer;
	}

}
