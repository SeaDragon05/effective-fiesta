package com.engine.graphics;

import org.lwjglx.util.vector.Matrix4f;

import com.engine.interfaces.ShaderProgram;
import com.engine.math.Maths;
import com.engine.objects.Camera;

public class SpotLightShader extends ShaderProgram {
	
	private static final String VERTEX_FILE = "src/shaders/shadowVertexShader.glsl";
	private static final String FRAGMENT_FILE = "src/shaders/shadowFragmentShader.glsl";

	private int location_viewMatrix;
	private int location_transformationMatrix;
	private int location_projectionMatrix;
	
	
	
	public SpotLightShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}
	
	@Override
	protected void getAllUniformLocations() {
		location_transformationMatrix = super.getUniformLocation("transformationMatrix");
		location_projectionMatrix = super.getUniformLocation("projectionMatrix");
		location_viewMatrix = super.getUniformLocation("viewMatrix");
	}
	
	public void loadTransformationMatrix(Matrix4f transformationMatrix){
		super.loadMatrix(location_transformationMatrix, transformationMatrix);
	}
	
	public void loadProjectionMatrix(Matrix4f projectionMatrix){
		super.loadMatrix(location_projectionMatrix, projectionMatrix);
	}
	
	public void loadViewMatrix(Camera camera){
		Matrix4f viewMatrix = Maths.createViewMatrix(camera);
		super.loadMatrix(location_viewMatrix, viewMatrix);
		//super.loadVector(location_cameraPosition, camera.getPosition());
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "texCoords");
	}

}
