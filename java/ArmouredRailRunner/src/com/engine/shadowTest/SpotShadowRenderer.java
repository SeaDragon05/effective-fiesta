package com.engine.shadowTest;

import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjglx.util.vector.Matrix4f;
import org.lwjglx.util.vector.Vector3f;

import com.engine.graphics.ShadowBox;
import com.engine.graphics.ShadowFrameBuffer;
import com.engine.objects.Camera;
import com.engine.objects.Entity;
import com.engine.objects.TexturedModel;
import com.engine.renderer.MasterRenderer;
import com.engine.renderer.ShadowMapEntityRenderer;
import com.engine.renderer.ShadowMapMasterRenderer;

import main.Engine;

public class SpotShadowRenderer {
	
	private SpotShadowShader shader;
	
	private ShadowFrameBuffer shadowFbo;
	
	private Camera source;
	
	private ShadowMapEntityRenderer entityRenderer;
	
	private Matrix4f projectionViewMatrix = new Matrix4f();
	private Matrix4f projectionMatrix = new Matrix4f();
	
	
	public SpotShadowRenderer(Camera source, Matrix4f projectionViewMatrix) {
		createProjectionMatrix();
		shader = new SpotShadowShader();
		shader.start();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.loadTransformationMatrix(this.getViewMatrix());
		shader.loadViewMatrix(source);
		shader.stop();
		shadowFbo = new ShadowFrameBuffer(ShadowMapMasterRenderer.SHADOW_MAP_SIZE, ShadowMapMasterRenderer.SHADOW_MAP_SIZE);
		this.source = source;
		this.projectionViewMatrix = projectionViewMatrix;
		entityRenderer = new ShadowMapEntityRenderer(shader, projectionViewMatrix);
	}
	
	
	public void render(Map<TexturedModel, List<Entity>> entities, Camera source) {
		prepare();
		entityRenderer.Spot_render(entities);
		finish();
	}
	
	public Matrix4f getViewMatrix() {
		return this.projectionViewMatrix;
	}
	
	public int getShadowMap() {
		return shadowFbo.getShadowMap();
	}
	
	public void cleanUp() {
		shader.cleanUp();
		shadowFbo.cleanUp(); 
	}
	
	private void prepare() {
		//updateOrthoProjectionMatrix(box.getWidth(), box.getHeight(), box.getLength());
		//updateLightViewMatrix(lightDirection, box.getCenter());
		//Matrix4f.mul(projectionMatrix, lightViewMatrix, projectionViewMatrix);
		shadowFbo.bindFrameBuffer();
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		shader.start();
	}
	private void finish() {
		shader.stop();
		shadowFbo.unbindFrameBuffer();
	}
	
	private void createProjectionMatrix() {
		projectionMatrix = new Matrix4f();
		float aspectRatio = (float) 8192 / (float) 8192 ;
		float y_scale = (float) ((1f / Math.tan(Math.toRadians(60 / 2f))));
		float x_scale = (float) y_scale / aspectRatio;
		float frustum_length = MasterRenderer.FAR_PLANE - MasterRenderer.NEAR_PLANE;
		
		projectionMatrix.m00 = x_scale;
		projectionMatrix.m11 = y_scale;
		projectionMatrix.m22 = -((MasterRenderer.FAR_PLANE + MasterRenderer.NEAR_PLANE) / frustum_length);
		projectionMatrix.m23 = -1;
		projectionMatrix.m32 = -((2 * MasterRenderer.NEAR_PLANE * MasterRenderer.FAR_PLANE) / frustum_length);
		projectionMatrix.m33 = 0;
	}


	public Matrix4f getToShadowMapSpaceMatrix() {
		return projectionViewMatrix;
	}
}
