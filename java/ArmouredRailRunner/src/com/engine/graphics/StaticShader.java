package com.engine.graphics;

import java.util.List;

import org.lwjglx.util.vector.Matrix4f;
import org.lwjglx.util.vector.Vector2f;
import org.lwjglx.util.vector.Vector3f;
import org.lwjglx.util.vector.Vector4f;

import com.engine.interfaces.ShaderProgram;
import com.engine.math.Maths;
import com.engine.objects.Camera;
import com.engine.objects.Light;

import main.Engine;

public class StaticShader extends ShaderProgram {

	private static final String VERTEX_FILE = "src/shaders/vertexShader.glsl";
	private static final String FRAGMENT_FILE = "src/shaders/fragmentShader.glsl";

	private int location_transformationMatrix;
	private int location_projectionMatrix;
	private int location_viewMatrix;
	private int location_cameraPosition;
	private int location_lightPosition[];
	private int location_lightPositionEyeSpace[];
	private int location_lightColor[];
	private int location_attenuation[];
	private int location_lightDirection[];
	private int location_lightTM[];
	// private int location_lightHasShadows[];
	private int location_shineDamper;
	private int location_reflectivity;
	private int location_hasReflectiveMap;
	private int location_reflectiveMap;
	private int location_useFakeLighting;
	private int location_skyColor;
	private int location_offset;
	private int location_numberOfRows;
	private int location_plane;
	private int location_shadowsEnable;
	private int location_toShadowMapSpace;
	private int location_toShadowMapSpace1;
	private int location_toShadowMapSpace2;
	private int location_toShadowMapSpace3;
	private int location_toShadowMapSpace4;
	private int location_toShadowMapSpace5;
	private int location_shadowMap;
	private int location_shadowMap2;
	private int location_shadowMap3;
	private int location_shadowMap4;
	private int location_shadowMap5;
	private int location_shadowMap1;
	private int location_MAXLIGHTS;
	private int location_fogDensity;
	private int location_fogGradient;
	private int location_fogColor;
	private int location_pcfCount;
	private int location_enviroMap;
	private int location_roughness;
	private int location_hasRoughMap;
	private int location_roughMap;
	private int location_hasNormalMap;
	private int location_normalMap;
	private int location_normalAmount;
	private int location_mapSize;

	public StaticShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoords");
		super.bindAttribute(2, "normal");
		super.bindAttribute(3, "tangent");
	}

	@Override
	protected void getAllUniformLocations() {
		location_transformationMatrix = super.getUniformLocation("transformationMatrix");
		location_projectionMatrix = super.getUniformLocation("projectionMatrix");
		location_reflectivity = super.getUniformLocation("reflectivity");
		location_cameraPosition = super.getUniformLocation("cameraPosition");
		location_viewMatrix = super.getUniformLocation("viewMatrix");
		location_shineDamper = super.getUniformLocation("shineDamper");
		location_reflectivity = super.getUniformLocation("reflectivity");
		location_hasReflectiveMap = super.getUniformLocation("hasReflectiveMap");
		location_reflectiveMap = super.getUniformLocation("reflectiveMap");
		location_useFakeLighting = super.getUniformLocation("useFakeLighting");
		location_skyColor = super.getUniformLocation("skyColor");
		location_numberOfRows = super.getUniformLocation("numberOfRows");
		location_offset = super.getUniformLocation("offset");
		location_plane = super.getUniformLocation("plane");
		location_shadowsEnable = super.getUniformLocation("shadowsEnable");
		location_toShadowMapSpace = super.getUniformLocation("toShadowMapSpace");
		location_toShadowMapSpace1 = super.getUniformLocation("toShadowMapSpace1");
		location_toShadowMapSpace2 = super.getUniformLocation("toShadowMapSpace2");
		location_toShadowMapSpace3 = super.getUniformLocation("toShadowMapSpace3");
		location_toShadowMapSpace4 = super.getUniformLocation("toShadowMapSpace4");
		location_toShadowMapSpace5 = super.getUniformLocation("toShadowMapSpace5");
		location_shadowMap = super.getUniformLocation("shadowMap");
		location_shadowMap1 = super.getUniformLocation("shadowMap1");
		location_shadowMap2 = super.getUniformLocation("shadowMap2");
		location_shadowMap3 = super.getUniformLocation("shadowMap3");
		location_shadowMap4 = super.getUniformLocation("shadowMap4");
		location_shadowMap5 = super.getUniformLocation("shadowMap5");
		location_MAXLIGHTS = super.getUniformLocation("maxLights");
		location_fogGradient = super.getUniformLocation("gradient");
		location_fogDensity = super.getUniformLocation("density");
		location_fogColor = super.getUniformLocation("fogColor");
		location_pcfCount = super.getUniformLocation("pcfCount");
		location_enviroMap = super.getUniformLocation("enviroMap");
		location_roughness = super.getUniformLocation("roughness");
		location_hasRoughMap = super.getUniformLocation("hasRoughMap");
		location_roughMap = super.getUniformLocation("roughMap");
		location_hasNormalMap = super.getUniformLocation("hasNormalMap");
		location_normalMap = super.getUniformLocation("normalMap");
		location_normalAmount = super.getUniformLocation("normalAmount");
		location_mapSize = super.getUniformLocation("mapSize");
		location_lightPosition = new int[Engine.MAX_LIGHTS];
		location_lightPositionEyeSpace = new int[Engine.MAX_LIGHTS];
		location_lightColor = new int[Engine.MAX_LIGHTS];
		location_attenuation = new int[Engine.MAX_LIGHTS];
		location_lightDirection = new int[Engine.MAX_LIGHTS];
		location_lightTM = new int[Engine.MAX_LIGHTS];
		
		for (int i = 0; i < Engine.MAX_LIGHTS; i++) {
			location_lightPosition[i] = super.getUniformLocation("lightPosition[" + i + "]");
			location_lightPositionEyeSpace[i] = super.getUniformLocation("lightPositionEyeSpace[" + i + "]");
			location_lightColor[i] = super.getUniformLocation("lightColor[" + i + "]");
			location_attenuation[i] = super.getUniformLocation("attenuation[" + i + "]");
			location_lightDirection[i] = super.getUniformLocation("lightDirection[" + i + "]");
			location_lightTM[i] = super.getUniformLocation("lightTransformationMatrix[" + i + "]");
			// if (i < 6)//only 6 light casters
			// location_lightHasShadows[i] = super.getUniformLocation("lightHasShadows[" + i
			// + "]");
		}
	}

	public void loaadStuff() {
		super.loadBoolean(location_shadowsEnable, Engine.shadowsEnabled);
		super.loadFloat(location_fogGradient, Engine.gradient);
		super.loadFloat(location_fogDensity, Engine.density);
		super.loadVector(location_fogColor, Engine.fogColor);
		super.loadInt(location_MAXLIGHTS, Engine.MAX_LIGHTS);
		super.loadInt(location_pcfCount, Engine.shadowSoftness);
		super.loadInt(location_enviroMap, 1);
	}

	public void loadObjectSpecific(float roughness) {
		super.loadFloat(location_roughness, roughness);
	}

	public void connectShadowStuff(float mapSize) {
		super.loadInt(location_shadowMap, 5);
		super.loadFloat(location_mapSize, mapSize);

		super.loadInt(location_shadowMap1, 6);
		super.loadInt(location_shadowMap2, 7);
		super.loadInt(location_shadowMap3, 8);
		super.loadInt(location_shadowMap4, 9);
		super.loadInt(location_shadowMap5, 10);
	}

	public void loadToShadowSpaceMatrix(Matrix4f matrix) {
		super.loadMatrix(location_toShadowMapSpace, matrix);
	}

	public void loadToShadowSpaceMatrix1(Matrix4f matrix) {
		super.loadMatrix(location_toShadowMapSpace1, matrix);
	}

	public void loadToShadowSpaceMatrix2(Matrix4f matrix) {
		super.loadMatrix(location_toShadowMapSpace2, matrix);
	}

	public void loadToShadowSpaceMatrix3(Matrix4f matrix) {
		super.loadMatrix(location_toShadowMapSpace3, matrix);
	}

	public void loadToShadowSpaceMatrix4(Matrix4f matrix) {
		super.loadMatrix(location_toShadowMapSpace4, matrix);
	}

	public void loadToShadowSpaceMatrix5(Matrix4f matrix) {
		super.loadMatrix(location_toShadowMapSpace5, matrix);
	}

	public void loadClipPlane(Vector4f value) {
		super.load4DVector(location_plane, value);
	}

	public void loadNumberOfRows(int value) {
		super.loadFloat(location_numberOfRows, value);
	}

	public void loadOffset(Vector2f value) {
		super.load2DVector(location_offset, value);
	}

	public void loadSkyColor(float r, float g, float b) {
		super.loadVector(location_skyColor, new Vector3f(r, g, b));
	}

	public void loadFakeLightingVariable(boolean value) {
		super.loadBoolean(location_useFakeLighting, value);
	}

	public void loadShineVariables(float damper, float refl) {
		super.loadFloat(location_shineDamper, damper);
		super.loadFloat(location_reflectivity, refl);
	}

	public void loadTransformationMatrix(Matrix4f matrix) {
		super.loadMatrix(location_transformationMatrix, matrix);
	}

	public void loadLights(List<Light> lights, Matrix4f viewMatrix) {
		for (int i = 0; i < Engine.MAX_LIGHTS; i++) {
			if (i < lights.size()) {
				super.loadVector(location_lightPositionEyeSpace[i], getEyeSpacePosition(lights.get(i), viewMatrix));
				super.loadVector(location_lightPosition[i], lights.get(i).getPosition());
				super.loadVector(location_lightColor[i], lights.get(i).getColor());
				super.loadVector(location_attenuation[i], lights.get(i).getAttenuation());
				super.loadVector(location_lightDirection[i], lights.get(i).getDirection());
				super.loadMatrix(location_lightTM[i], lights.get(i).getAssignedModelTM());
				// if (i < 6)
				// super.loadBoolean(location_lightHasShadows[i], lights.get(i).hasShadows());
			} else {
				super.loadVector(location_lightPosition[i], new Vector3f(0, 0, 0));
				super.loadVector(location_lightColor[i], new Vector3f(0, 0, 0));
				super.loadVector(location_attenuation[i], new Vector3f(1, 0, 0));
				super.loadVector(location_lightDirection[i], new Vector3f(0,0,0));
				// if (i < 6)
				// super.loadBoolean(location_lightHasShadows[i], false);
			}
		}
	}

	public void loadProjectionMatrix(Matrix4f projection) {
		super.loadMatrix(location_projectionMatrix, projection);
	}

	public void loadViewMatrix(Camera camera) {
		Matrix4f viewMatrix = Maths.createViewMatrix(camera);
		super.loadMatrix(location_viewMatrix, viewMatrix);
		super.loadVector(location_cameraPosition, camera.getPosition());
	}

	public void loadRoughMap(boolean hasRoughMap) {
		super.loadBoolean(location_hasRoughMap, hasRoughMap);
		super.loadInt(location_roughMap, 2);
	}

	public void loadNormalMap(boolean hasNormalMap, float amount) {
		super.loadBoolean(location_hasNormalMap, hasNormalMap);
		super.loadInt(location_normalMap, 3);
		super.loadFloat(location_normalAmount, amount);
	}

	public void loadReflectiveMap(boolean hasReflectiveMap) {
		super.loadBoolean(location_hasReflectiveMap, hasReflectiveMap);
		super.loadInt(location_reflectiveMap, 4);
	}
	
	private Vector3f getEyeSpacePosition(Light light, Matrix4f viewMatrix){
		Vector3f position = light.getPosition();
		Vector4f eyeSpacePos = new Vector4f(position.x,position.y, position.z, 1f);
		Matrix4f.transform(viewMatrix, eyeSpacePos, eyeSpacePos);
		return new Vector3f(eyeSpacePos);
	}
}
