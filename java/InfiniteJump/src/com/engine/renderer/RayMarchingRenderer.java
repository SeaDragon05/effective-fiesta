package com.engine.renderer;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjglx.BufferUtils;

import com.engine.interfaces.RenderingSystem;
import com.engine.objects.Entity;

public class RayMarchingRenderer implements RenderingSystem {

	float[] vertices = {
			-1,1,0
			,-1,-1,0
			,1,-1,0
			,1,1,0};
	int[] indices = {0,3,2,0,1,2};
	float[] bloat = {
			0,0,
			0,1,
			1,1,
			1,1};
	int vaoID;
	int vboID_1;
	int vboID_2;
	
	public RayMarchingRenderer() {
		createVAO();
		bindIB(indices);
		storeData(0, 3, vertices);
		storeData(1, 2, bloat);
		GL30.glBindVertexArray(0);
	}
	
	@Override
	public void render() {
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL30.glBindVertexArray(vaoID);
		GL20.glEnableVertexAttribArray(0);
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, 4);
		GL11.glDrawElements(GL11.GL_TRIANGLES, 4, GL11.GL_UNSIGNED_INT, 0);
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
	}

	@Override
	public void prepare() {
		GL11.glClearColor(0,0,0,0);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
	}
	
	private void createVAO() {
		vaoID = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vaoID);
	}

	private void bindIB(int[] ind) {
		vboID_1 = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID_1);
		IntBuffer buffer = storeInt(ind);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		
	}
	
	private IntBuffer storeInt(int[] data) {
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
	
	private void storeData(int attr, int coordSize, float[] data) {
		vboID_2 = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID_2);
		FloatBuffer buffer = FB(data);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(attr, coordSize, GL11.GL_FLOAT, false, 0, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}
	private FloatBuffer FB(float[] data) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}

	@Override
	public void destroy() {
		GL30.glDeleteVertexArrays(vaoID);
		
	}
}
