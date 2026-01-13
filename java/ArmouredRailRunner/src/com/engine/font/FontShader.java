package com.engine.font;

import org.lwjglx.util.vector.Vector2f;
import org.lwjglx.util.vector.Vector3f;

import com.engine.interfaces.ShaderProgram;

public class FontShader extends ShaderProgram{

	private static final String VERTEX_FILE = "src/shaders/fontVertex.glsl";
	private static final String FRAGMENT_FILE = "src/shaders/fontFragment.glsl";
	
	private int location_color;
	private int location_translation;
	private int location_fade;
	
	public FontShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void getAllUniformLocations() {
		location_color = super.getUniformLocation("color");
		location_translation = super.getUniformLocation("translation");
		location_fade = super.getUniformLocation("fade");
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoords");
		
	}
	
	protected void loadColor(Vector3f color) {
		super.loadVector(location_color, color);
	}

	protected void loadTranslation(Vector2f translation) {
		super.load2DVector(location_translation, translation);
	}
	
	protected void loadFade(float fade) {
		super.loadFloat(location_fade, fade);
	}
}
