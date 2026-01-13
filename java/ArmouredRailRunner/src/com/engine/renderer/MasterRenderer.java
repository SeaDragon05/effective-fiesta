package com.engine.renderer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjglx.util.vector.Matrix4f;
import org.lwjglx.util.vector.Vector3f;
import org.lwjglx.util.vector.Vector4f;

import com.engine.graphics.StaticShader;
import com.engine.graphics.TerrainShader;
import com.engine.graphics.NormalMappingShader;
import com.engine.graphics.SpotLightShader;
import com.engine.io.Input;
import com.engine.math.Maths;
import com.engine.objects.Camera;
import com.engine.objects.CubeMap;
import com.engine.objects.Entity;
import com.engine.objects.Light;
import com.engine.objects.Terrain;
import com.engine.objects.TexturedModel;
import com.engine.shadowTest.SpotShadowRenderer;
import com.engine.utils.Loader;

import main.Engine;

public class MasterRenderer {

	public static float FOV = 10;

	public static float NEAR_PLANE = 0.1f;
	public static float FAR_PLANE = 0.1f;
	public static float RENDER_DISTANCE = 3000f;

	private Matrix4f projectionMatrix;

	private StaticShader shader = new StaticShader();
	private EntityRenderer renderer;

	private TerrainRenderer terrainRenderer;
	private TerrainShader terrainShader = new TerrainShader();

	private Map<TexturedModel, List<Entity>> entities = new HashMap<TexturedModel, List<Entity>>();
	private List<Terrain> terrains = new ArrayList<Terrain>();

	private SkyboxRenderer skyboxRenderer;
	private ShadowMapMasterRenderer shadowMapRenderer;

	public MasterRenderer(Camera cam, CubeMap cubeMap) {
		enableCulling();
		createProjectionMatrix();
		renderer = new EntityRenderer(shader, projectionMatrix, cubeMap);
		terrainRenderer = new TerrainRenderer(terrainShader, projectionMatrix);
		skyboxRenderer = new SkyboxRenderer(projectionMatrix);
		shadowMapRenderer = new ShadowMapMasterRenderer(cam);
		//spotLightRenderer = new SpotLightMasterRenderer();
	}

	public Matrix4f getProjectionMatrix() {
		return projectionMatrix;
	}

	public static void enableCulling() {
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);
	}

	public void renderScene(List<Entity> entities, List<Terrain> terrains, List<Light> lights, Camera camera,
			Vector4f clipPlane) {
		if (terrains != null) {
			for (Terrain terrain : terrains) {
				processTerrain(terrain);
			}
		}
		for (Entity entity : entities) {
			if (entity == null || getDistance(camera.getPosition(), entity.getPosition()) > RENDER_DISTANCE) {
				if (entity.getDrawDistanceEnabled()) {
						continue;
				}
			}
			processEntity(entity);
			
		}
		render(lights, camera, clipPlane);
	}

	public static void disableCulling() {

		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);
	}
	public Matrix4f getShadowMapSpaceMatrix() {
		return this.shadowMapRenderer.getToShadowMapSpaceMatrix();
	}
	
	public void render(List<Light> lights, Camera camera, Vector4f clipPlane) {
		prepare();
		shader.start();
		shader.loadClipPlane(clipPlane);
		shader.loadLights(lights, Maths.createViewMatrix(camera));
		shader.loadViewMatrix(camera);
		shader.loadSkyColor(Engine.skyColor.x, Engine.skyColor.y, Engine.skyColor.z);
		List<Matrix4f> thigns = new ArrayList<Matrix4f>();
		thigns.add(shadowMapRenderer.getToShadowMapSpaceMatrix());
		/*
		try {
			thigns.add(this.spotLightRenderer.get(0).getEntityRenderer().getProjectionMatrix());
			thigns.add(this.spotLightRenderer.get(1).getEntityRenderer().getProjectionMatrix());
			thigns.add(this.spotLightRenderer.get(2).getEntityRenderer().getProjectionMatrix());
			thigns.add(this.spotLightRenderer.get(3).getEntityRenderer().getProjectionMatrix());
			thigns.add(this.spotLightRenderer.get(4).getEntityRenderer().getProjectionMatrix());
		} catch (Exception e) {
		}*/
		renderer.render(entities, thigns);
		shader.stop();
		terrainShader.start();
		terrainShader.loadClipPlane(clipPlane);
		terrainShader.loadLights(lights);
		terrainShader.loadViewMatrix(camera);
		terrainShader.loadSkyColor(Engine.skyColor.x, Engine.skyColor.y, Engine.skyColor.z);
		terrainRenderer.render(terrains, shadowMapRenderer.getToShadowMapSpaceMatrix());
		skyboxRenderer.render(camera);
		terrains.clear();
		entities.clear();
	}

	public void processTerrain(Terrain terrain) {
		terrains.add(terrain);
	}

	public void processEntity(Entity entity) {
		TexturedModel entityModel = entity.getModel();
		List<Entity> batch = entities.get(entityModel);
		if (batch != null) {
			batch.add(entity);
		} else {
			List<Entity> newBatch = new ArrayList<Entity>();
			newBatch.add(entity);
			entities.put(entityModel, newBatch);
		}
	}
	
	public void renderShadowMap(List<Entity> entityList, Camera camera, Light sun) {
		for (Entity entity : entityList) {
			if (getDistance(camera.getPosition(), entity.getPosition()) > RENDER_DISTANCE) {
				if (entity == null || entity.getDrawDistanceEnabled()) {
					continue;
				}
			}
			if (entity.getDrawToShadowShader()) {
				processEntity(entity);
			}
		}
		shadowMapRenderer.render(entities, sun);
	}

	public int getShadowMapTexture() {
		return shadowMapRenderer.getShadowMap();
	}

	public void prepare() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glClearColor(Engine.skyColor.x, Engine.skyColor.y, Engine.skyColor.z, 1);
		GL13.glActiveTexture(GL13.GL_TEXTURE5);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, getShadowMapTexture());
		//GL13.glActiveTexture(GL13.GL_TEXTURE6);
	//	GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.spotLightRenderer.getBufferFrom(0));
		//GL13.glActiveTexture(GL13.GL_TEXTURE7);
		//GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.spotLightRenderer.getBufferFrom(1));
		//GL13.glActiveTexture(GL13.GL_TEXTURE8);
		//GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.spotLightRenderer.getBufferFrom(2));
		//GL13.glActiveTexture(GL13.GL_TEXTURE9);
		//GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.spotLightRenderer.getBufferFrom(3));
		//GL13.glActiveTexture(GL13.GL_TEXTURE10);
		//GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.spotLightRenderer.getBufferFrom(4));
		
	}

	public void cleanUp() {
		shader.cleanUp();
		terrainShader.cleanUp();
		shadowMapRenderer.cleanUp();
	}

	private void createProjectionMatrix() {
		projectionMatrix = new Matrix4f();
		float aspectRatio = (float) Engine.window.getWidth() / (float) Engine.window.getHeight();
		float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV / 2f))));
		float x_scale = (float) y_scale / aspectRatio;
		float frustum_length = FAR_PLANE - NEAR_PLANE;

		projectionMatrix.m00 = x_scale;
		projectionMatrix.m11 = y_scale;
		projectionMatrix.m22 = -((FAR_PLANE + NEAR_PLANE) / frustum_length);
		projectionMatrix.m23 = -1;
		projectionMatrix.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustum_length);
		projectionMatrix.m33 = 0;
	}

	public void shadowRenderComplete() {
		entities.clear();
	}

	public void renderTerrainShadowMap(List<Terrain> terrains, Light sun, Loader loader) {
		for (Terrain terrain : terrains) {
			processEntity(terrain.convertTerrainToEntity(loader));
		}
		shadowMapRenderer.render(entities, sun);

	}

	public void updateProjectionMatrix() {
		GL11.glViewport(0, 0, Engine.WIDTH, Engine.HEIGHT);
		createProjectionMatrix();
		renderer.updateProjectionMatrix(projectionMatrix);
		terrainRenderer.updateProjectionMatrix(projectionMatrix);
		skyboxRenderer.updateProjectionMatrix(projectionMatrix);
	}

	public float getDistance(Vector3f pt1, Vector3f pt2) {
		Vector3f dist = new Vector3f((pt2.x - pt1.x), (pt2.y - pt1.y), (pt2.z - pt1.z));
		return (float) Math.sqrt((dist.x * dist.x) + (dist.y * dist.y) + (dist.z * dist.z));
	}
}
