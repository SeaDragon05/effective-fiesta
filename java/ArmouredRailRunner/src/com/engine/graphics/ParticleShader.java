package com.engine.graphics;

import java.util.List;

import com.engine.objects.Light;

import main.Engine;

import org.lwjglx.util.vector.Matrix4f;
import org.lwjglx.util.vector.Vector2f;
import org.lwjglx.util.vector.Vector3f;

import com.engine.interfaces.ShaderProgram;

public class ParticleShader extends ShaderProgram {

	private static final String VERTEX_FILE = "src/shaders/particleVShader.glsl";
	private static final String FRAGMENT_FILE = "src/shaders/particleFShader.glsl";

	private int location_modelViewMatrix;
	private int location_projectionMatrix;
	private int location_texOffset1;
	private int location_texOffset2;
	private int location_texCoordInfo;
	
	//lighting variables:

	private int location_lightPosition[];
	private int location_lightColor[];
	private int location_attenuation[];
	private int location_lightDirection[];
	private int location_lightTM[];
	private int location_MAXLIGHTS;
	
	//shadow stuff:

	private int location_shadowsEnable;
	private int location_toShadowMapSpace;
	private int location_shadowMap;
	private int location_mapSize;
	
	//misc vars:
	private int location_fogDensity;
	private int location_fogGradient;
	private int location_fogColor;
	private int location_skyColor;
	
	

	public ParticleShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	public void getAllUniformLocations() {
		location_modelViewMatrix = super.getUniformLocation("modelViewMatrix");
		location_projectionMatrix = super.getUniformLocation("projectionMatrix");
		location_texOffset1 = super.getUniformLocation("texOffset1");
		location_texOffset2 = super.getUniformLocation("texOffset2");
		location_texCoordInfo = super.getUniformLocation("texCoordInfo");
		

		location_shadowsEnable = super.getUniformLocation("shadowsEnable");
		location_toShadowMapSpace = super.getUniformLocation("toShadowMapSpace");
		location_shadowMap = super.getUniformLocation("shadowMap");
		location_MAXLIGHTS = super.getUniformLocation("maxLights");
		location_fogGradient = super.getUniformLocation("gradient");
		location_fogDensity = super.getUniformLocation("density");
		location_fogColor = super.getUniformLocation("fogColor");
		location_skyColor = super.getUniformLocation("skyColor");

		location_mapSize = super.getUniformLocation("mapSize");
		location_lightPosition = new int[Engine.MAX_LIGHTS];
		location_lightColor = new int[Engine.MAX_LIGHTS];
		location_attenuation = new int[Engine.MAX_LIGHTS];
		location_lightDirection = new int[Engine.MAX_LIGHTS];
		location_lightTM = new int[Engine.MAX_LIGHTS];
		for (int i = 0; i < Engine.MAX_LIGHTS; i++) {
			location_lightPosition[i] = super.getUniformLocation("lightPosition[" + i + "]");
			location_lightColor[i] = super.getUniformLocation("lightColor[" + i + "]");
			location_attenuation[i] = super.getUniformLocation("attenuation[" + i + "]");
			location_lightDirection[i] = super.getUniformLocation("lightDirection[" + i + "]");
			location_lightTM[i] = super.getUniformLocation("lightTransformationMatrix[" + i + "]");
		}
		
	}

	@Override
	public void bindAttributes() {
		super.bindAttribute(0, "position");
	}

	public void loadModelViewMatrix(Matrix4f modelView) {
		super.loadMatrix(location_modelViewMatrix, modelView);
	}
	
	public void loadTextureCoordInfo(Vector2f offset1, Vector2f offset2, float numRows, float blend) {
		super.load2DVector(location_texOffset1, offset1);
		super.load2DVector(location_texOffset2, offset2);
		super.load2DVector(location_texCoordInfo, new Vector2f(numRows, blend));
	}
	public void loadMisc() {
		super.loadBoolean(location_shadowsEnable, Engine.shadowsEnabled);
		super.loadFloat(location_fogGradient, Engine.gradient);
		super.loadFloat(location_fogDensity, Engine.density);
		super.loadVector(location_fogColor, Engine.fogColor);
		super.loadInt(location_MAXLIGHTS, Engine.MAX_LIGHTS);
	}

	public void connectShadowStuff(float mapSize) {
		super.loadInt(location_shadowMap, 5);
		super.loadFloat(location_mapSize, mapSize);
	}
	
	public void loadToShadowSpaceMatrix(Matrix4f matrix) {
		super.loadMatrix(location_toShadowMapSpace, matrix);
	}

	public void loadSkyColor(float r, float g, float b) {
		super.loadVector(location_skyColor, new Vector3f(r, g, b));
	}
	
	public void loadLights(List<Light> lights) {
		for (int i = 0; i < Engine.MAX_LIGHTS; i++) {
			if (i < lights.size()) {
				super.loadVector(location_lightPosition[i], lights.get(i).getPosition());
				super.loadVector(location_lightColor[i], lights.get(i).getColor());
				super.loadVector(location_attenuation[i], lights.get(i).getAttenuation());
				super.loadVector(location_lightDirection[i], lights.get(i).getDirection());
				super.loadMatrix(location_lightTM[i], lights.get(i).getAssignedModelTM());
			} else {
				super.loadVector(location_lightPosition[i], new Vector3f(0, 0, 0));
				super.loadVector(location_lightColor[i], new Vector3f(0, 0, 0));
				super.loadVector(location_attenuation[i], new Vector3f(1, 0, 0));
				super.loadVector(location_lightDirection[i], new Vector3f(0,0,0));
			}
		}
	}

	public void loadProjectionMatrix(Matrix4f projectionMatrix) {
		super.loadMatrix(location_projectionMatrix, projectionMatrix);
	}

}
