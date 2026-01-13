package com.engine.renderer;

import java.util.List;
import java.util.Map;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjglx.util.vector.Matrix4f;
import org.lwjglx.util.vector.Vector2f;

import com.engine.graphics.ModelTexture;
import com.engine.graphics.StaticShader;
import com.engine.io.Input;
import com.engine.math.Maths;
import com.engine.objects.CubeMap;
import com.engine.objects.Entity;
import com.engine.objects.RawModel;
import com.engine.objects.TexturedModel;

import main.Engine;

public class EntityRenderer {

	private StaticShader shader;
	private CubeMap enviromentMap;

	public EntityRenderer(StaticShader shader, Matrix4f projectionMatrix, CubeMap enviromentMap) {
		this.shader = shader;
		this.enviromentMap = enviromentMap;
		shader.start();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.connectShadowStuff(ShadowMapMasterRenderer.SHADOW_MAP_SIZE);
		shader.loaadStuff();
		shader.stop();
	}

	public void render(Map<TexturedModel, List<Entity>> entities, List<Matrix4f> toShadowSpace) {
		shader.loadToShadowSpaceMatrix(toShadowSpace.get(0));
		try {
			//shader.loadToShadowSpaceMatrix1(toShadowSpace.get(1));
			//shader.loadToShadowSpaceMatrix2(toShadowSpace.get(2));
			//shader.loadToShadowSpaceMatrix3(toShadowSpace.get(3));
			//shader.loadToShadowSpaceMatrix4(toShadowSpace.get(4));
			//shader.loadToShadowSpaceMatrix5(toShadowSpace.get(5));
		} catch (Exception e) {
		}
		if (Input.isKeyDown(GLFW.GLFW_KEY_O)) {
			Engine.shadowsEnabled = false;
			shader.loaadStuff();
		}

		if (Input.isKeyDown(GLFW.GLFW_KEY_P)) {
			Engine.shadowsEnabled = true;
			shader.loaadStuff();
		}
		bindEnvMap();
		for (TexturedModel model : entities.keySet()) {
			prepareTexturedModel(model);

			// System.out.println("rendering model " + model.toString());
			List<Entity> batch = entities.get(model);
			for (Entity entity : batch) {
				if (entity != null) {
					prepareInstance(entity);
					GL11.glDrawElements(GL11.GL_TRIANGLES, model.getRawModel().getVertexCount(), GL11.GL_UNSIGNED_INT,
							0);
				}
			}
			unbindTexturedModel();
		}

	}

	private void bindEnvMap() {
		GL13.glActiveTexture(GL13.GL_TEXTURE1);
		GL11.glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, enviromentMap.getTexture());
	}

	private void prepareTexturedModel(TexturedModel model) {
		if (model == null)
			return;
		RawModel mood = model.getRawModel();// yess, 'mood'
		GL30.glBindVertexArray(mood.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		if (model.getTexture().hasNormalMap())//IMPORTANT!
			GL20.glEnableVertexAttribArray(3);
		ModelTexture texture = model.getTexture();
		shader.loadNumberOfRows(texture.getNumberOfRows());
		if (texture.isHasTransparency()) {
			MasterRenderer.disableCulling();
		}
		shader.loadFakeLightingVariable(texture.isUseFakeLighting());
		shader.loadShineVariables(texture.getShineDamper(), texture.getReflectivity());
		shader.loadObjectSpecific(texture.getRoughness());
		shader.loadRoughMap(texture.hasRoughMap());
		shader.loadNormalMap(texture.hasNormalMap(), texture.getNormalAmount());
		shader.loadReflectiveMap(texture.hasReflectiveMap());
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getTexture().getID());
		/////
		GL13.glActiveTexture(GL13.GL_TEXTURE2);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getTexture().getRoughMap());
		GL13.glActiveTexture(GL13.GL_TEXTURE3);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getTexture().getNormalMap());
		GL13.glActiveTexture(GL13.GL_TEXTURE4);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getTexture().getReflectiveMap());

	}

	private void unbindTexturedModel() {
		MasterRenderer.enableCulling();
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		GL20.glDisableVertexAttribArray(3);
		GL30.glBindVertexArray(0);
	}

	private void prepareInstance(Entity entity) {
		if (entity == null) {
			return;
		}
		Matrix4f transformationMatrix = Maths.createTransformationMatrix(entity.getPosition(), entity.getRotX(),
				entity.getRotY(), entity.getRotZ(), entity.getScale());
		shader.loadTransformationMatrix(transformationMatrix);
		shader.loadOffset(new Vector2f(entity.getTextureXOffset(), entity.getTextureYOffset()));
	}

	public void updateProjectionMatrix(Matrix4f projectionMatrix) {
		shader.start();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.stop();
	}

}
