package com.engine.objects;

import org.lwjgl.opengl.GL15;

public class RawModel {
	private int vaoID;
	private int vertexCount;
	private String fileName;
	public RawModel(int vaoID, int vertexCount, String fileName) {
		this.vaoID = vaoID;
		this.vertexCount = vertexCount;
		this.fileName = fileName;
	}

	public int getVaoID() {
		return vaoID;
	}
	public String getFileName() {
		return this.fileName;
	}

	public int getVertexCount() {
		return vertexCount;
	}
	public void cleanUp() {
		GL15.glDeleteBuffers(vaoID);
	}
}
