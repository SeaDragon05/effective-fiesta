package com.engine.renderer;

import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;

import com.engine.graphics.ShadowFrameBuffer;
import com.engine.graphics.SpotLightShader;
import com.engine.objects.Entity;
import com.engine.objects.SpotLight;
import com.engine.objects.TexturedModel;

import main.Engine;

public class SpotLightMasterRenderer {
	
	private SpotLightObject spotLight;
	
	private SpotLightShader shader;
	
	public SpotLightMasterRenderer() {
		if (Engine.spotLights.isEmpty()) {
			System.err.println("NOTICE: No spotlights were initilized before the graphics were!\nSkipping...");
			return;
		}
		shader = new SpotLightShader();
		spotLight = new SpotLightObject(shader, Engine.spotLights.get(0));
		
	}
	
	
	
	public void render(Map<TexturedModel, List<Entity>> entities) {
		if (!Engine.shadowsEnabled)
			return;
		
		prepare();
		spotLight.getEntityRenderer().render(entities);
		finish();
	}
	
	
	public int getDepthBuffer() {
		return this.spotLight.getShadowFbo().getDepthTexture();
	}
	
	public void prepare() {
		this.spotLight.getShadowFbo().bindFrameBuffer();
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		this.spotLight.getShader().start();
	}
	
	public void finish() {
		this.spotLight.getShader().stop();
		this.spotLight.getShadowFbo().unbindFrameBuffer();
	}
}
