package com.engine.graphics;

import java.util.List;

import org.lwjglx.util.vector.Matrix4f;
import org.lwjglx.util.vector.Vector3f;

import com.engine.interfaces.ShaderProgram;
import com.engine.math.Maths;
import com.engine.objects.Camera;
import com.engine.objects.Light;

public class WaterShader extends ShaderProgram {
	
		public static int MAX_LIGHTS;
		private final static String VERTEX_FILE = "src/shaders/waterVertex.glsl";
		private final static String FRAGMENT_FILE = "src/shaders/waterFragment.glsl";

		private int location_modelMatrix;
		private int location_viewMatrix;
		private int location_projectionMatrix;
		private int location_reflTex;
		private int location_refrTex;
		private int location_dudvMap;
		private int location_moveFactor;
		private int location_cameraPosition;
		private int location_normalMap;
		private int location_depthMap;
		private int location_lightPosition[];
		private int location_lightColor[];
		private int location_attenuation[];

		public WaterShader() {
			super(VERTEX_FILE, FRAGMENT_FILE);
		}

		@Override
		protected void bindAttributes() {
			super.bindAttribute(0, "position");
		}

		@Override
		protected void getAllUniformLocations() {
			location_projectionMatrix = super.getUniformLocation("projectionMatrix");
			location_viewMatrix = super.getUniformLocation("viewMatrix");
			location_modelMatrix = super.getUniformLocation("modelMatrix");
			location_reflTex = super.getUniformLocation("reflectionTexture");
			location_refrTex = super.getUniformLocation("refractionTexture");
			location_dudvMap = super.getUniformLocation("dudvMap");
			location_moveFactor = super.getUniformLocation("moveFactor");
			location_cameraPosition = super.getUniformLocation("cameraPosition");
			location_normalMap = super.getUniformLocation("normalMap");
			location_depthMap = super.getUniformLocation("depthMap");
			location_lightPosition = new int[MAX_LIGHTS];
			location_lightColor = new int[MAX_LIGHTS];
			location_attenuation = new int[MAX_LIGHTS];
			for (int i = 0; i < MAX_LIGHTS;i++) {
				location_lightPosition[i] = super.getUniformLocation("lightPosition[" + i + "]");
				location_lightColor[i] = super.getUniformLocation("lightColor[" + i + "]");
				location_attenuation[i] = super.getUniformLocation("attenuation[" + i + "]");
			}
		}
		
		public void loadLights(List<Light> lights) {
			for(int i = 0;i<MAX_LIGHTS;i++) {
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
		public void loadMoveFactor(float value) {
			super.loadFloat(location_moveFactor, value);
		}
		public void connectTextureUnits() {
			super.loadInt(location_reflTex, 0);
			super.loadInt(location_refrTex, 1);
			super.loadInt(location_dudvMap, 2);
			super.loadInt(location_normalMap, 3);
			super.loadInt(location_depthMap, 4);
		}

		public void loadProjectionMatrix(Matrix4f projection) {
			super.loadMatrix(location_projectionMatrix, projection);
		}
		
		public void loadViewMatrix(Camera camera){
			Matrix4f viewMatrix = Maths.createViewMatrix(camera);
			super.loadVector(location_cameraPosition, camera.getPosition());
			super.loadMatrix(location_viewMatrix, viewMatrix);
		}

		public void loadModelMatrix(Matrix4f modelMatrix){
			super.loadMatrix(location_modelMatrix, modelMatrix);
		}
}
