package com.engine.postProcess;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import main.Engine;

public class Fbo {

	public static final int NONE = 0;
	public static final int DEPTH_TEXTURE = 1;
	public static final int DEPTH_RENDER_BUFFER = 2;

	private final int width;
	private final int height;

	private int frameBuffer;

	private int colourTexture;
	private int depthTexture;
	private int positionTexture;

	private int newDepthTex;
	private int normalTex;

	private int depthBuffer;
	private int colourBuffer;

	/**
	 * Creates an FBO of a specified width and height, with the desired type of
	 * depth buffer attachment.
	 * 
	 * @param width           - the width of the FBO.
	 * @param height          - the height of the FBO.
	 * @param depthBufferType - an int indicating the type of depth buffer
	 *                        attachment that this FBO should use.
	 */
	public Fbo(int width, int height, int depthBufferType, boolean miltiSample) {
		this.width = width;
		this.height = height;
		initialiseFrameBuffer(depthBufferType);
	}

	/**
	 * Deletes the frame buffer and its attachments when the game closes.
	 */
	public void cleanUp() {
		GL30.glDeleteFramebuffers(frameBuffer);
		GL11.glDeleteTextures(colourTexture);
		GL11.glDeleteTextures(depthTexture);
		GL11.glDeleteTextures(newDepthTex);
		GL30.glDeleteRenderbuffers(depthBuffer);
		GL30.glDeleteRenderbuffers(colourBuffer);
	}

	/**
	 * Binds the frame buffer, setting it as the current render target. Anything
	 * rendered after this will be rendered to this FBO, and not to the screen.
	 */
	public void bindFrameBuffer() {
		GL30.glBindFramebuffer(GL30.GL_DRAW_FRAMEBUFFER, frameBuffer);
		GL11.glViewport(0, 0, width, height);
	}

	/**
	 * Unbinds the frame buffer, setting the default frame buffer as the current
	 * render target. Anything rendered after this will be rendered to the screen,
	 * and not this FBO.
	 */
	public void unbindFrameBuffer() {
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
		GL11.glViewport(0, 0, Engine.window.getWidth(), Engine.window.getHeight());
	}

	/**
	 * Binds the current FBO to be read from (not used in tutorial 43).
	 */
	public void bindToRead() {
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		GL30.glBindFramebuffer(GL30.GL_READ_FRAMEBUFFER, frameBuffer);
		GL11.glReadBuffer(GL30.GL_COLOR_ATTACHMENT0);
	}

	/**
	 * @return The ID of the texture containing the colour buffer of the FBO.
	 */
	public int getColourTexture() {
		return colourTexture;
	}

	/**
	 * @return The texture containing the FBOs depth buffer.
	 */
	public int getDepthTexture() {
		return depthTexture;
	}
	
	
	public int getTrueDepthTex() {
		return newDepthTex;
	}
	public int getNormalTex() {
		return normalTex;
	}
	public int getPositions() {
		return this.positionTexture;
	}

	/**
	 * Creates the FBO along with a colour buffer texture attachment, and possibly a
	 * depth buffer.
	 * 
	 * @param type - the type of depth buffer attachment to be attached to the FBO.
	 */
	private void initialiseFrameBuffer(int type) {
		frameBuffer = createFrameBuffer(GL30.GL_COLOR_ATTACHMENT0);
		colourTexture = createTextureAttachment(GL30.GL_COLOR_ATTACHMENT0);
		newDepthTex = createTextureAttachment(GL30.GL_COLOR_ATTACHMENT1);
		normalTex = createTextureAttachment(GL30.GL_COLOR_ATTACHMENT2);
		positionTexture = createTextureAttachment(GL30.GL_COLOR_ATTACHMENT3);
		//if (type == DEPTH_RENDER_BUFFER) {
			depthBuffer = createDepthBufferAttachment();
		//} else if (type == DEPTH_TEXTURE) {
			createDepthTextureAttachment();
		//}
		unbindFrameBuffer();
	}

	/**
	 * Creates a new frame buffer object and sets the buffer to which drawing will
	 * occur - colour attachment 0. This is the attachment where the colour buffer
	 * texture is.
	 * 
	 */
	private int createFrameBuffer(int type) {
		int buffer = GL30.glGenFramebuffers();
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, buffer);
		determineDrawBuffers();
		return buffer;
	}
	/**
	 * Binds all the buffers to the renderer so that they are written to
	 */
	private void determineDrawBuffers() {
		IntBuffer drawBuffers = BufferUtils.createIntBuffer(4);
		drawBuffers.put(GL30.GL_COLOR_ATTACHMENT0);
		drawBuffers.put(GL30.GL_COLOR_ATTACHMENT1);
		drawBuffers.put(GL30.GL_COLOR_ATTACHMENT2);
		drawBuffers.put(GL30.GL_COLOR_ATTACHMENT3);
		drawBuffers.flip();
		GL20.glDrawBuffers(drawBuffers);
	}

	/**
	 * Creates a texture and sets it as the colour buffer attachment for this FBO.
	 */
	private int createTextureAttachment(int type) {
		int texture = GL11.glGenTextures();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, (ByteBuffer) null);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
		GL30.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, type, GL11.GL_TEXTURE_2D, texture, 0);
		return texture;
	}

	/**
	 * Adds a depth buffer to the FBO in the form of a texture, which can later be
	 * sampled.
	 */
	private int createDepthTextureAttachment() {
		int texture = GL11.glGenTextures();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL14.GL_DEPTH_COMPONENT24, width, height, 0, GL11.GL_DEPTH_COMPONENT, GL11.GL_FLOAT, (ByteBuffer) null);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
		GL30.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, GL30.GL_DEPTH_ATTACHMENT, GL11.GL_TEXTURE_2D, texture, 0);
		return texture;
	}

	/**
	 * Adds a depth buffer to the FBO in the form of a render buffer. This can't be
	 * used for sampling in the shaders.
	 */
	private int createDepthBufferAttachment() {
		int buffer = GL30.glGenRenderbuffers();
		GL30.glBindRenderbuffer(GL30.GL_RENDERBUFFER, buffer);
		GL30.glRenderbufferStorage(GL30.GL_RENDERBUFFER, GL14.GL_DEPTH_COMPONENT24, width, height);
		GL30.glFramebufferRenderbuffer(GL30.GL_FRAMEBUFFER, GL30.GL_DEPTH_ATTACHMENT, GL30.GL_RENDERBUFFER, buffer);
		return buffer;
	}

}
