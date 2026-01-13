package com.engine.graphics;

import org.lwjglx.util.vector.Matrix4f;
import org.lwjglx.util.vector.Vector3f;

import com.engine.interfaces.ShaderProgram;
import com.engine.math.Maths;
import com.engine.objects.Camera;

import main.Engine;

public class SkyboxShader extends ShaderProgram{

	private static final String VERTEX_FILE = "src/shaders/skyboxVertexShader.glsl";
	private static final String FRAGMENT_FILE = "src/shaders/skyboxFragmentShader.glsl";
	
	public static float ROTATE_SPEED;
	
	private int location_projectionMatrix;
	private int location_viewMatrix;
	private int location_fogColor;
	private int location_cubeMap;
	private int location_cubeMap2;
	private int location_blendFactor;

	private float rotation = 0;
	
	public SkyboxShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}
	
	public void loadProjectionMatrix(Matrix4f matrix){
		super.loadMatrix(location_projectionMatrix, matrix);
	}
	
	public void loadFogColor() {
		super.loadVector(location_fogColor, Engine.fogColor);
	}

	public void loadViewMatrix(Camera camera){
		Matrix4f matrix = Maths.createViewMatrix(camera);
		matrix.m30 = 0;
		matrix.m31 = 0;
		matrix.m32 = 0;
		//rotation += ROTATE_SPEED * Engine.window.getFrameTimeSeconds();
		//Matrix4f.rotate((float) Math.toRadians(rotation), new Vector3f(0,1,0), matrix, matrix);
		super.loadMatrix(location_viewMatrix, matrix);
	}
	
	public void connectTextureUnits() {
		super.loadInt(location_cubeMap, 0);
		super.loadInt(location_cubeMap2, 1);
	}
	
	public void loadBlendFactor(float blend) {
		super.loadFloat(location_blendFactor, blend);
	}
	
	@Override
	protected void getAllUniformLocations() {
		location_projectionMatrix = super.getUniformLocation("projectionMatrix");
		location_viewMatrix = super.getUniformLocation("viewMatrix");
		location_fogColor = super.getUniformLocation("fogColor");
		location_cubeMap = super.getUniformLocation("cubeMap");
		location_cubeMap2 = super.getUniformLocation("cubeMap2");
		location_blendFactor = super.getUniformLocation("blendFactor");
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
	}
}
