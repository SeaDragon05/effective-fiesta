package com.engine.shadowTest;

import org.lwjglx.util.vector.Matrix4f;

import com.engine.interfaces.ShaderProgram;
import com.engine.math.Maths;
import com.engine.objects.Camera;

public class SpotShadowShader extends ShaderProgram {
	
	
	private static final String VERTEX_FILE = "src/shaders/spotShadowVertexShader.glsl";
	private static final String FRAGMENT_FILE = "src/shaders/spotShadowFragmentShader.glsl";
	
	private int location_viewMatrix;
	private int location_lightPosition;
	private int location_transformationMatrix;
	private int location_projectionMatrix;
	
	public SpotShadowShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}


	@Override
	protected void getAllUniformLocations() {
		location_viewMatrix = super.getUniformLocation("viewMatrix");
		location_lightPosition = super.getUniformLocation("lightPosition");
		location_transformationMatrix = super.getUniformLocation("transformationMatrix");
		location_projectionMatrix = super.getUniformLocation("projectionMatrix");
	}


	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "in_position");
		super.bindAttribute(1, "in_textureCoords");
		
	}
	
	public void loadProjectionMatrix(Matrix4f projection) {//not loaded
		super.loadMatrix(location_projectionMatrix, projection);
	}
	
	public void loadTransformationMatrix(Matrix4f transformationMatrix) {
		super.loadMatrix(location_transformationMatrix, transformationMatrix);
	}
	
	public void loadViewMatrix(Camera camera) {//not loaded
		Matrix4f viewMatrix = Maths.createViewMatrix(camera);
		super.loadMatrix(location_viewMatrix, viewMatrix);
		super.loadVector(location_lightPosition, camera.getPosition());
	}
}
