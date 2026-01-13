package com.own.master.engine.graphics;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;
import org.lwjglx.util.vector.Vector4f;

import com.own.master.engine.io.Window;
import com.own.master.engine.math.Matrix4f;
import com.own.master.engine.math.Vector3f;
import com.own.master.engine.objects.FullLight;
import com.own.master.engine.objects.GameObject;

import Main.Main;

public class Renderer {
	private Shader shader;
	private Shader fontShader;
	private Window window;

	public Renderer(Window window, Shader shader, Shader fontShader) {
		this.shader = shader;
		this.window = window;
		this.fontShader = fontShader;
	}

	public void renderMesh(GameObject object, int shouldShade) {
		if (this.window == null) {
			if (Main.gr_shadow_enable == 0) {
				return;
			}
			// renderShadow(object);//Shadows are supper buggy and I have no idea whats
			// going on
			return;
		}
		GL30.glBindVertexArray(object.getMesh().getVAO());
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL30.glEnableVertexAttribArray(0);// actual mesh stuff
		GL30.glEnableVertexAttribArray(1);// colors
		GL30.glEnableVertexAttribArray(2);// texture coords
		GL30.glEnableVertexAttribArray(3);// normals
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, object.getMesh().getIBO());
		GL13.glBindTexture(GL13.GL_TEXTURE_2D, object.getMesh().getMaterial().getTextureID());
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		shader.bind();
		shader.setUniform("transformationMatrix",
				Matrix4f.transform(object.getPosition(), object.getRotation(), object.getScale()));
		shader.setUniform("viewMatrix", Matrix4f.view(Main.player.getCamera().getPosition(), Main.player.getCamera().getRotation()));
		shader.setUniform("projectionMatrix", window.getProjection());
		for (int i = 0; i < Main.Lights.size(); i++) {
			shader.setUniform("lightColor[" + i + "]", Main.Lights.get(i).getLight().getColor());
			shader.setUniform("lightPosition[" + i + "]", Main.Lights.get(i).getPosition());
			shader.setUniform("lightBr[" + i + "]", Main.Lights.get(i).getLight().getBrightness());
		}
		shader.setUniform("shineDamper", object.getMesh().getMaterial().getShineDamper());
		shader.setUniform("reflectivity", object.getMesh().getMaterial().getReflectivity());
		shader.setUniform("roughness", object.getMesh().getMaterial().getRoughness());
		shader.setUniform("gradient", window.getGradient());
		shader.setUniform("density", window.getDensity());
		shader.setUniform("numbLights", Main.Lights.size());
		shader.setUniform("numbLightsF", Main.Lights.size());
		shader.setUniform("shadowLightCount", Main.Lights.size());
		shader.setUniform("fullBright", Main.gr_full_bright);
		shader.setUniform("shadowEnable", Main.gr_shadow_enable);
		shader.setUniform("bumpEnable", Main.gr_bump_enable);
		GL11.glDrawElements(GL11.GL_TRIANGLES, object.getMesh().getIndices().length, GL11.GL_UNSIGNED_INT, 0);
		shader.unbind();
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
		GL30.glDisableVertexAttribArray(0);
		GL30.glDisableVertexAttribArray(1);
		GL30.glDisableVertexAttribArray(2);
		GL30.glDisableVertexAttribArray(3);
		GL30.glBindVertexArray(0);
	}

	public void renderFont(Mesh mesh) {//no game objects here sunny d boi
		GL30.glBindVertexArray(mesh.getVAO());
		GL30.glEnableVertexAttribArray(0);// actual mesh stuff
		GL30.glEnableVertexAttribArray(1);// texture coords
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, mesh.getIBO());
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, mesh.getMaterial().getTextureID());
		fontShader.bind();
		fontShader.setUniform("color", new Vector3f(0,0,1));
		GL11.glDrawElements(GL11.GL_TRIANGLES, mesh.getIndices().length, GL11.GL_UNSIGNED_INT, 0);
		fontShader.unbind();
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
		GL30.glDisableVertexAttribArray(0);
		GL30.glDisableVertexAttribArray(1);
		GL30.glBindVertexArray(0);
	}




















	public static Vector3f getEyeSpacePosition(FullLight light, Matrix4f Matrix) {
		// AAAAAAAAAAAAAAAAAAAAAAAAAAA
		Vector3f position = light.getPosition();
		Vector4f eyeSpacePos = new Vector4f(position.getX(), position.getY(), position.getZ(), 1f);
		Vector4f result = transform(Matrix, eyeSpacePos, eyeSpacePos);
		return new Vector3f(result.getX(), result.getY(), result.getZ());
	}

	public static Vector4f transform(Matrix4f left, Vector4f right, Vector4f dest) {// got the github class file and
																					// rewrote it to suit ME OWN CODE
																					// LMAOOOO
		if (dest == null)
			dest = new Vector4f();

		float x = left.get(0, 0) * right.getX() + left.get(1, 0) * right.getY() + left.get(2, 0) * right.getZ()
				+ left.get(3, 0) * right.getW();
		float y = left.get(0, 1) * right.getX() + left.get(1, 1) * right.getY() + left.get(2, 1) * right.getZ()
				+ left.get(3, 1) * right.getW();
		float z = left.get(0, 2) * right.getX() + left.get(1, 2) * right.getY() + left.get(2, 2) * right.getZ()
				+ left.get(3, 2) * right.getW();
		float w = left.get(0, 3) * right.getX() + left.get(1, 3) * right.getY() + left.get(2, 3) * right.getZ()
				+ left.get(3, 3) * right.getW();

		dest.setX(x);
		dest.setY(y);
		dest.setZ(z);
		dest.setW(w);

		return dest;
	}
}