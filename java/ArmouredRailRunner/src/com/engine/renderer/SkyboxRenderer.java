package com.engine.renderer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjglx.util.vector.Matrix4f;
import org.lwjglx.util.vector.Vector3f;

import com.engine.graphics.SkyboxShader;
import com.engine.objects.Camera;
import com.engine.objects.CubeMap;
import com.engine.objects.RawModel;
import com.engine.utils.Loader;
import com.engine.utils.MasterLoader;

import main.Engine;

public class SkyboxRenderer {

	public static float SIZE = 7000f;

	private static final float[] VERTICES = { 
			
			//back
			-SIZE, SIZE, -SIZE, 
			-SIZE, -SIZE, -SIZE, 
			SIZE, -SIZE, -SIZE, 
			
			SIZE, -SIZE, -SIZE, 
			SIZE, SIZE, -SIZE, 
			-SIZE, SIZE, -SIZE, 
			//end of back
			
			//
			-SIZE, -SIZE, SIZE, 
			-SIZE, -SIZE, -SIZE, 
			-SIZE, SIZE, -SIZE, 
			
			-SIZE, SIZE, -SIZE, 
			-SIZE, SIZE, SIZE, 
			-SIZE,-SIZE, SIZE,
			
			
			
			
			SIZE, -SIZE, -SIZE, 
			SIZE, -SIZE, SIZE, 
			SIZE, SIZE, SIZE, 
			SIZE, SIZE, SIZE, 
			
			SIZE, SIZE, -SIZE, 
			SIZE, -SIZE, -SIZE,
			-SIZE, -SIZE, SIZE, 
			-SIZE, SIZE, SIZE, 
			
			SIZE, SIZE, SIZE, 
			SIZE, SIZE, SIZE, 
			SIZE, -SIZE, SIZE, 
			-SIZE, -SIZE, SIZE,

			-SIZE, SIZE, -SIZE, 
			SIZE, SIZE, -SIZE, 
			SIZE, SIZE, SIZE, 
			SIZE, SIZE, SIZE, 
			
			-SIZE, SIZE, SIZE, 
			-SIZE, SIZE, -SIZE,
			-SIZE, -SIZE, -SIZE, 
			-SIZE, -SIZE, SIZE, 
			
			SIZE, -SIZE, -SIZE, 
			SIZE, -SIZE, -SIZE, 
			-SIZE, -SIZE, SIZE, 
			SIZE, -SIZE, SIZE 
			};
//map = new CubeMap(new String[] { "gameModels/theSnowDesert/skyBox/right", "gameModels/theSnowDesert/skyBox/left", "gameModels/theSnowDesert/skyBox/top", "gameModels/theSnowDesert/skyBox/bottom", "gameModels/theSnowDesert/skyBox/front", "gameModels/theSnowDesert/skyBox/back" }, loader);

	private static String[] TEXTURE_FILES = {
			"skybox/cloudy/0004",//right - back
			"skybox/cloudy/0002", //left - front
			"skybox/cloudy/0005",	//same as
			"skybox/cloudy/0006", //same as
			"skybox/cloudy/0001",//front - right
			"skybox/cloudy/0003" //back - left
			/*
			"skybox/bright_sunset/right",//right - back
			"skybox/bright_sunset/left", //left - front
			"skybox/bright_sunset/top",	//same as
			"skybox/bright_sunset/bottom", //same as
			"skybox/bright_sunset/front",//front - right
			"skybox/bright_sunset/back" //back - left
			*/
	};

	private RawModel cube;
	private int texture;
	private SkyboxShader shader;

	public SkyboxRenderer(Matrix4f projectionMatrix) {
		cube = MasterLoader.getLoader().loadToVAO(VERTICES, 3, "SKYBOX autogen model");
		texture = MasterLoader.getLoader().loadCubeMap(TEXTURE_FILES);
		// nightTexture = loader.loadCubeMap(NIGHT_TEXTURE_FILES);
		shader = new SkyboxShader();
		shader.start();
		shader.connectTextureUnits();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.stop();
	}

	public void render(Camera camera) {
		shader.start();
		shader.loadViewMatrix(camera);
		shader.loadFogColor();
		GL30.glBindVertexArray(cube.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		//bindTextures();
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, texture);
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, cube.getVertexCount());
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);

	}

	public void updateProjectionMatrix(Matrix4f projectionMatrix) {
		shader.start();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.stop();
	}
}
