package com.engine.graphics;

import java.util.List;

import org.lwjglx.util.vector.Matrix4f;
import org.lwjglx.util.vector.Vector3f;
import org.lwjglx.util.vector.Vector4f;

import com.engine.interfaces.ShaderProgram;
import com.engine.math.Maths;
import com.engine.objects.Camera;
import com.engine.objects.Light;

import main.Engine;

public class TerrainShader extends ShaderProgram {

	private static final String VERTEX_FILE = "src/shaders/terrainVertexShader.glsl";
	private static final String FRAGMENT_FILE = "src/shaders/terrainFragmentShader.glsl";
	
	private int location_transformationMatrix;
	private int location_projectionMatrix;
	private int location_viewMatrix;
	private int location_lightPosition[];
	private int location_lightColor[];
	private int location_attenuation[];
	private int location_shineDamper;
	private int location_reflectivity;
	private int location_skyColor;
	private int location_backgroundTexture;
	private int location_rTexture;
	private int location_gTexture;
	private int location_bTexture;
	private int location_blendMap;
	private int location_plane;
	private int location_toShadowMapSpace;
	private int location_shadowMap; 
	private int location_MAXLIGHTS;
	private int location_fogDensity;
	private int location_fogGradient;
	private int location_fogColor;
	private int location_pcfCount;
	
	
	public TerrainShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoords");
		super.bindAttribute(2, "normal");
	}

	@Override
	protected void getAllUniformLocations() {
		location_transformationMatrix = super.getUniformLocation("transformationMatrix");
		location_projectionMatrix = super.getUniformLocation("projectionMatrix");
		location_viewMatrix = super.getUniformLocation("viewMatrix");
		location_shineDamper = super.getUniformLocation("shineDamper");
		location_reflectivity = super.getUniformLocation("reflectivity");
		location_skyColor = super.getUniformLocation("skyColor");
		location_backgroundTexture = super.getUniformLocation("backgroundTexture");
		location_rTexture = super.getUniformLocation("rTexture");
		location_gTexture = super.getUniformLocation("gTexture");
		location_bTexture = super.getUniformLocation("bTexture");
		location_blendMap = super.getUniformLocation("blendMap");
		location_plane = super.getUniformLocation("plane");
		location_toShadowMapSpace = super.getUniformLocation("toShadowMapSpace");
		location_shadowMap = super.getUniformLocation("shadowMap");
		location_MAXLIGHTS = super.getUniformLocation("maxLights");
		location_fogGradient = super.getUniformLocation("gradient");
		location_fogDensity = super.getUniformLocation("denisty");
		location_fogColor = super.getUniformLocation("fogColor");
		location_pcfCount = super.getUniformLocation("pcfCount");
		location_lightPosition = new int[Engine.MAX_LIGHTS];
		location_lightColor = new int[Engine.MAX_LIGHTS];
		location_attenuation = new int[Engine.MAX_LIGHTS];
		for (int i = 0; i < Engine.MAX_LIGHTS;i++) {
			location_lightPosition[i] = super.getUniformLocation("lightPosition[" + i + "]");
			location_lightColor[i] = super.getUniformLocation("lightColor[" + i + "]");
			location_attenuation[i] = super.getUniformLocation("attenuation[" + i + "]");
			
		}
	}
	public void loadStufff() {
		super.loadFloat(location_fogGradient, Engine.gradient);
		super.loadFloat(location_fogDensity, Engine.density);
		super.loadVector(location_fogColor, Engine.fogColor);
		super.loadInt(location_MAXLIGHTS, Engine.MAX_LIGHTS);
		super.loadInt(location_pcfCount, Engine.shadowSoftness);
	}
	public void connectTextureUnits() {
		super.loadInt(location_backgroundTexture, 0);
		super.loadInt(location_rTexture, 1); 
		super.loadInt(location_gTexture, 2);
		super.loadInt(location_bTexture, 3);
		super.loadInt(location_blendMap, 4);
		super.loadInt(location_shadowMap, 5);
	}
	
	public void loadToShadowSpaceMatrix(Matrix4f matrix) {
		super.loadMatrix(location_toShadowMapSpace, matrix);
	}
	public void loadClipPlane(Vector4f value) {
		super.load4DVector(location_plane, value);
	}
	
	public void loadSkyColor(float r, float g, float b) {
		super.loadVector(location_skyColor, new Vector3f(r, g, b));
	}
	public void loadShineVariables(float damper, float refl) {
		super.loadFloat(location_shineDamper, damper);
		super.loadFloat(location_reflectivity, refl);
	}
	public void loadTransformationMatrix(Matrix4f matrix) {
		super.loadMatrix(location_transformationMatrix, matrix);
	}
	
	public void loadLights(List<Light> lights) {
		for(int i = 0;i<Engine.MAX_LIGHTS;i++) {
			if (i<lights.size()) {
				super.loadVector(location_lightPosition[i], lights.get(i).getPosition());
				super.loadVector(location_lightColor[i], lights.get(i).getColor());
				super.loadVector(location_attenuation[i], lights.get(i).getAttenuation());
			} else {
				super.loadVector(location_lightPosition[i], new Vector3f(0,0,0));
				super.loadVector(location_lightColor[i], new Vector3f(0,0,0));
				super.loadVector(location_attenuation[i], new Vector3f(1,0,0));
			}
		}
	}
	
	public void loadProjectionMatrix(Matrix4f projection) {
		super.loadMatrix(location_projectionMatrix, projection);
	}
	public void loadViewMatrix(Camera camera) {
		Matrix4f viewMatrix = Maths.createViewMatrix(camera);
		super.loadMatrix(location_viewMatrix, viewMatrix);
	}
}
