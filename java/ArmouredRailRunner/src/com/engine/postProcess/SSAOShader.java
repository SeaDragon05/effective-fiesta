package com.engine.postProcess;

import com.engine.interfaces.ShaderProgram;

public class SSAOShader extends ShaderProgram {
	
	private static final String VERTEX_FILE = "src/shaders/postProcessing/simpleVertex.glsl";
	private static final String FRAGMENT_FILE = "src/shaders/postProcessing/SSAOFragment.glsl";

	private int location_color;
	private int location_positionCol;
	private int location_depth;
	private int location_norma;
	private int location_frame;
		
	public SSAOShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	
	protected void connectTextureUnits(){
		super.loadInt(location_color, 0);
		super.loadInt(location_positionCol, 1);
		super.loadInt(location_depth, 2);
		super.loadInt(location_norma, 3);
	}
	
	@Override
	protected void getAllUniformLocations() {
		location_positionCol = super.getUniformLocation("positionCol");
		location_depth = super.getUniformLocation("depth");
		location_norma = super.getUniformLocation("normal");
		location_frame = super.getUniformLocation("frame");
	}
	
	protected void loadValues(int frame) {
		super.loadInt(location_frame, frame);
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
	}

}
