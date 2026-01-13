package com.engine.renderer;

import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjglx.util.vector.Matrix4f;
import org.lwjglx.util.vector.Vector2f;

import com.engine.graphics.SpotLightShader;
import com.engine.math.Maths;
import com.engine.objects.Entity;
import com.engine.objects.RawModel;
import com.engine.objects.SpotLight;
import com.engine.objects.TexturedModel;
import com.engine.shadowTest.SpotShadowShader;

public class SpotLightEntityRenderer {
	
	private SpotLightShader shader;
	
	private Matrix4f projectionMatrix;
	
	public SpotLightEntityRenderer(SpotLightShader shader, SpotLight light) {
		this.shader = shader;

		shader.start();
		this.projectionMatrix = light.getProjectionMatrix();
		shader.loadProjectionMatrix(light.getProjectionMatrix());
		shader.loadViewMatrix(light.getLightSource());
		shader.stop();
		
	}

	public void render(Map<TexturedModel, List<Entity>> entities) {
		for (TexturedModel model : entities.keySet()) {
			if (model == null)
				continue;
			RawModel rawModel = model.getRawModel();
			bindModel(rawModel);
			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getTexture().getID());
			if (model.getTexture().isHasTransparency()) {
				MasterRenderer.disableCulling();
			}
			for (Entity entity : entities.get(model)) {
				prepareInstance(entity);
				GL11.glDrawElements(GL11.GL_TRIANGLES, rawModel.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
			}
			if (model.getTexture().isHasTransparency()) {
				MasterRenderer.enableCulling();
			}
		}
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL30.glBindVertexArray(0);
		
	}
	
	private void bindModel(RawModel rawModel) {
		GL30.glBindVertexArray(rawModel.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
	}
	private void prepareInstance(Entity entity) {
		if (entity == null) {
			return;
		}
		Matrix4f transformationMatrix = Maths.createTransformationMatrix(entity.getPosition(), entity.getRotX(), entity.getRotY(), entity.getRotZ(), entity.getScale());
		shader.loadTransformationMatrix(transformationMatrix);
	} 
	public void updateProjectionMatrix(Matrix4f projectionMatrix) {
		shader.start(); 
		shader.loadProjectionMatrix(projectionMatrix);
		shader.stop();
	}

	public Matrix4f getProjectionMatrix() {
		return this.projectionMatrix;
	}
}
