package com.engine.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjglx.BufferUtils;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import com.engine.data.TextureData;
import com.engine.graphics.ModelTexture;
import com.engine.objects.RawModel;

import de.matthiasmann.twl.utils.PNGDecoder;
import de.matthiasmann.twl.utils.PNGDecoder.Format;
import main.Engine;

public class Loader implements AutoCloseable {

	private List<Integer> vaos = new ArrayList<Integer>();
	private List<Integer> vbos = new ArrayList<Integer>();
	private List<Integer> textures = new ArrayList<Integer>();
	private boolean closed = false;

	protected Loader() {
	}

	public RawModel loadToVAO(float[] positions, float[] textureCoords, float[] normals, int[] indices,
			String fileName) {
		int vaoID = createVAO();
		bindIndicesBuffer(indices);
		storeDataInAttributeList(0, 3, positions);
		storeDataInAttributeList(1, 2, textureCoords);
		storeDataInAttributeList(2, 3, normals);
		unbindVAO();
		return new RawModel(vaoID, indices.length, fileName);
	}

	public int loadToVAO(float[] positions, float[] textureCoords) {
		int vaoID = createVAO();
		storeDataInAttributeList(0, 2, positions);
		storeDataInAttributeList(1, 2, textureCoords);
		unbindVAO();
		return vaoID;
	}

	public RawModel loadToVAO(float[] positions, float[] textureCoords, float[] normals, int[] indices,
			float[] tangents, String fileName) {
		int vaoID = createVAO();
		bindIndicesBuffer(indices);
		storeDataInAttributeList(0, 3, positions);
		storeDataInAttributeList(1, 2, textureCoords);
		storeDataInAttributeList(2, 3, normals);
		storeDataInAttributeList(3, 3, tangents);
		unbindVAO();
		return new RawModel(vaoID, indices.length, fileName);
	}

	public RawModel loadToVAO(float[] positions, int dimentions, String fileName) {
		int vaoID = createVAO();
		this.storeDataInAttributeList(0, dimentions, positions);
		unbindVAO();
		return new RawModel(vaoID, positions.length / 2, fileName);
	}

	public int loadTexture(String fileName) {
		Texture texture = null;
		try {
			texture = TextureLoader.getTexture("PNG", new FileInputStream("res/" + fileName + ".png"));
			GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
			GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL14.GL_TEXTURE_LOD_BIAS, -2.4f);
			// if(GLContext.getCapabilities().GL_EXT_texture_filter_anisotropic) {
			// float amount = Math.min(4f,
			// GL11.glGetFloat(EXTTextureFilterAnisotropic.GL_MAX_TEXTURE_MAX_ANISOTROPY_EXT));
			// GL11.glTexParameterf(GL11.GL_TEXTURE_2D,
			// EXTTextureFilterAnisotropic.GL_TEXTURE_MAX_ANISOTROPY_EXT, amount);
			// }
		} catch (IOException e) {
			try {
				// return a missing texture thats a purple checker thingy
				texture = TextureLoader.getTexture("PNG", new FileInputStream("res/textures/missing.png"));
				GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
				GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
				GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL14.GL_TEXTURE_LOD_BIAS, -2.4f);
				System.err.println("INFO: Could not find res/" + fileName + ".png!");
			} catch (Exception f) {
				e.printStackTrace();
				f.printStackTrace();
				System.err.println(
						"Missing game asset: Missing texture is missing. You really effed up there. Try reinstalling the game and don't mod the files please\n"
								+ "Or if you are modding, make sure that res/textures/missing.png exists because that currently doesn't exist");
				System.exit(100503);
			}
		}
		Engine.dataBlocks += 1;
		Engine.textureData += 1;
		textures.add(texture.getTextureID());
		return texture.getTextureID();
	}

	public int loadCubeMap(String[] textureFiles) {
		int texID = GL11.glGenTextures();
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, texID);
		for (int i = 0; i < textureFiles.length; i++) {
			try {
				TextureData data = decodeTextureFile("res/" + textureFiles[i] + ".png");
				GL11.glTexImage2D(GL13.GL_TEXTURE_CUBE_MAP_POSITIVE_X + i, 0, GL11.GL_RGBA, data.getWidth(),
						data.getHeight(), 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, data.getBuffer());
			} catch (Exception e) {
				TextureData data = decodeTextureFile("res/textures/missing.png");
				GL11.glTexImage2D(GL13.GL_TEXTURE_CUBE_MAP_POSITIVE_X + i, 0, GL11.GL_RGBA, data.getWidth(),
						data.getHeight(), 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, data.getBuffer());
			}
		}
		GL11.glTexParameteri(GL13.GL_TEXTURE_CUBE_MAP, GL13.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL13.GL_TEXTURE_CUBE_MAP, GL13.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
		Engine.dataBlocks += 1;
		Engine.modelData += 1;
		textures.add(texID);
		return texID;
	}

	private TextureData decodeTextureFile(String fileName) {
		int width = 0;
		int height = 0;
		ByteBuffer buffer = null;
		try {
			FileInputStream in = new FileInputStream(fileName);
			PNGDecoder decoder = new PNGDecoder(in);
			width = decoder.getWidth();
			height = decoder.getHeight();
			buffer = ByteBuffer.allocateDirect(4 * width * height);
			decoder.decode(buffer, width * 4, Format.RGBA);
			buffer.flip();
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Tried to load texture " + fileName + ", didn't work");
		}
		return new TextureData(buffer, width, height);
	}

	public void cleanUp() {
		for (int i = 0; i < vaos.size(); i++) {
			GL30.glDeleteVertexArrays(vaos.get(i));
		}
		for (int i = 0; i < vbos.size(); i++) {
			GL15.glDeleteBuffers(vbos.get(i));
		}
		for (int i = 0; i < textures.size(); i++) {
			GL11.glDeleteTextures(textures.get(i));
		}
	}

	private int createVAO() {
		int vaoID = GL30.glGenVertexArrays();
		Engine.dataBlocks += 1;
		Engine.modelData += 1;
		vaos.add(vaoID);
		GL30.glBindVertexArray(vaoID);
		return vaoID;
	}

	private void storeDataInAttributeList(int attributeNumber, int size, float[] data) {
		int vboID = GL15.glGenBuffers();
		Engine.dataBlocks += 1;
		Engine.modelData += 1;
		vbos.add(vboID);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
		FloatBuffer buffer = storeDataInFloatBuffer(data);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(attributeNumber, size, GL11.GL_FLOAT, false, 0, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}

	private void unbindVAO() {
		GL30.glBindVertexArray(0);
	}

	private void bindIndicesBuffer(int[] indices) {
		int vboID = GL15.glGenBuffers();
		Engine.dataBlocks += 1;
		Engine.modelData += 1;
		vbos.add(vboID);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID);
		IntBuffer buffer = storeDataInIntBuffer(indices);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
	}

	private IntBuffer storeDataInIntBuffer(int[] data) {
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}

	private FloatBuffer storeDataInFloatBuffer(float[] data) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}

	public RawModel loadToVAO(float[] positions, String fileName) {
		int vaoID = createVAO();
		this.storeDataInAttributeList(0, 3, positions);
		unbindVAO();
		return new RawModel(vaoID, positions.length / 3, fileName);
	}

	@Override
	public void close() throws Exception {
		this.cleanUp();
		this.closed = true;
	}

	@Override
	protected void finalize() throws Throwable {
		try {
			if (!closed) {
				// throw a warning if the object is not closed
				System.err.println("Warning: Loader not closed properly");
			}
		} finally {
			super.finalize();
		}
	}
}
