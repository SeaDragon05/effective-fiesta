package com.engine.graphics;

import org.lwjglx.util.vector.Vector3f;

import com.engine.interfaces.ShaderProgram;

public class RayMarchingShader extends ShaderProgram {
	
	private static final String VERTEX_FILE = "src/shaders/RayMarchingVertex.glsl";
	private static final String FRAGMENT_FILE = "src/shaders/RayMarchingFragment.glsl";
	
	private int location_iResolution;
	private int location_iTime;
	private int location_iFOV;
	

	public RayMarchingShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}


	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
	}
	@Override
	protected void getAllUniformLocations() {
		location_iResolution = super.getUniformLocation("iResolution");
		location_iTime = super.getUniformLocation("iTime");
		location_iFOV = super.getUniformLocation("iFOV");
	}
	
	public void loadResolution(Vector3f res) {
		super.loadVector(location_iResolution, res);
	}
	
	public void load_iTime(float time) {
		super.loadFloat(location_iTime, time);
	}
	
	public void load_iFOV(float value) {
		value = value < 0 ? 0 : value > 5 ? 5 : value;
		super.loadFloat(location_iFOV, value);
	}
}
