package com.engine.postProcess;

import com.engine.interfaces.ShaderProgram;

public class ContrastShader extends ShaderProgram {

	private static final String VERTEX_FILE = "src/shaders/postProcessing/contrastVertex.glsl";
	private static final String FRAGMENT_FILE = "src/shaders/postProcessing/contrastFragment.glsl";
	
	public ContrastShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void getAllUniformLocations() {	
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
	}
}
