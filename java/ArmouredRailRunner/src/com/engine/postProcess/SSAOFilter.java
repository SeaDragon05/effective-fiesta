package com.engine.postProcess;

import java.util.Random;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

public class SSAOFilter {
	private ImageRenderer renderer;
	private SSAOShader shader;
	private Random rnd;
	
	public SSAOFilter(int width, int height){
		shader = new SSAOShader();
		shader.start();
		shader.connectTextureUnits();
		shader.stop();
		renderer = new ImageRenderer(width, height);
		rnd = new Random();
	}

	public void render(Fbo fbo) {
		shader.start();
		shader.loadValues(rnd.nextInt(13));
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, fbo.getColourTexture());
		GL13.glActiveTexture(GL13.GL_TEXTURE1);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, fbo.getPositions());
		GL13.glActiveTexture(GL13.GL_TEXTURE2);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, fbo.getTrueDepthTex());
		GL13.glActiveTexture(GL13.GL_TEXTURE3);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, fbo.getNormalTex());
		renderer.renderQuad();
		shader.stop();
	}
	public int getColorTexture() {
		return renderer.getOutputTexture();
	}

	public void cleanUp() {
		renderer.cleanUp();
		shader.cleanUp();
	}
}
