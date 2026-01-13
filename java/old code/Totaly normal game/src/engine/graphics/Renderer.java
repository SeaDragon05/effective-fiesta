package engine.graphics;


import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

import engine.chapters.Chapter1;
import engine.io.Window;
import engine.maths.Matrix4f;
import engine.objects.Camera;
import engine.objects.GameObject;
import main.Main;

public class Renderer {
	private Shader shader;
	private Window window;
	
	public Renderer(Window window, Shader shader, Shader fontShader) {
		this.shader = shader;
		this.window = window;
	}
	
	public void renderMesh(GameObject object, Camera camera, int shouldShade) {
		GL30.glBindVertexArray(object.getMesh().getVAO());
		GL30.glEnableVertexAttribArray(0);//actual mesh stuff
		GL30.glEnableVertexAttribArray(1);//colors
		GL30.glEnableVertexAttribArray(2);//textures
		GL30.glEnableVertexAttribArray(3);//normals
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, object.getMesh().getIBO());
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL13.glBindTexture(GL13.GL_TEXTURE_2D, object.getMesh().getMaterial().getTextureID());
		shader.bind();
		shader.setUniform("transformationMatrix", Matrix4f.transform(object.getPosition(), object.getRotation(), object.getScale()));
		shader.setUniform("viewMatrix", Matrix4f.view(camera.getPosition(), camera.getRotation()));
		shader.setUniform("projectionMatrix", window.getProjection());
		for (int i = 0; i < Main.lightCount; i++) {
				shader.setUniform("lightColor[" + i + "]", Main.lights.get(i).getColor());
				shader.setUniform("lightPosition[" + i + "]", Main.lights.get(i).getPosition());
		}
		shader.setUniform("shineDamper", object.getMesh().getMaterial().getShineDamper());
		shader.setUniform("reflectivity", object.getMesh().getMaterial().getReflectivity());
		shader.setUniform("gradient", window.getGradient());
		shader.setUniform("density", window.getDensity());
		shader.setUniform("skyColor", window.getBackground());
		shader.setUniform("shouldShade", (float) shouldShade);
		if (Main.level == 0) {
			shader.setUniform("numbLightsF", Chapter1.glitchLevel + Main.lightCount);
			shader.setUniform("numbLights", Chapter1.lightThing + Main.lightCount);
		} else {
			shader.setUniform("numbLightsF", Main.lightCount);
			shader.setUniform("numbLights", Main.lightCount);
		}
		GL11.glDrawElements(GL11.GL_TRIANGLES, object.getMesh().getIndices().length, GL11.GL_UNSIGNED_INT, 0);
		shader.unbind();
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
		GL30.glDisableVertexAttribArray(0);
		GL30.glDisableVertexAttribArray(1);
		GL30.glDisableVertexAttribArray(2);
		GL30.glDisableVertexAttribArray(3);
		GL30.glBindVertexArray(0);
	}
}