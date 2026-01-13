package com.engine.objects;

import org.lwjglx.util.vector.Vector3f;

public class Entity {
	private TexturedModel model;
	private Vector3f position;
	private float rotX, rotY, rotZ;
	private float scale;
	private boolean drawDistanceEnabled = true;
	private boolean drawToShadowShader = true;
	private int textureIndex = 0;
	
	
	public Entity(TexturedModel model, Vector3f position, float x, float y, float z, float scale) {
		this.model = model;
		this.position = position;
		rotX = x; rotY = y; rotZ = z; this.scale = scale;
	}
	
	public Entity(TexturedModel model, int index, Vector3f position, float x, float y, float z, float scale) {
		this.model = model;
		this.position = position;
		this.textureIndex = index;
		rotX = x; rotY = y; rotZ = z; this.scale = scale;
	}

	public Entity(TexturedModel model, Vector3f position, Vector3f rotation, float scale) {
		this.model = model;
		this.position = position;
		rotX = rotation.x; rotY = rotation.y; rotZ = rotation.z; this.scale = scale;
	}

	public float getTextureXOffset() {
		int colum = textureIndex%model.getTexture().getNumberOfRows();
		return (float)colum/(float)model.getTexture().getNumberOfRows();
	}
	public float getTextureYOffset() {
		int row = textureIndex/model.getTexture().getNumberOfRows();
		return (float)row/(float)model.getTexture().getNumberOfRows();
	}
	
	public void increasePosition(float x, float y, float z) {
		this.position.x+=x;
		this.position.y+=y;
		this.position.z+=z;
	}
	public void increaseRotation(float x, float y, float z) {
		this.rotX+=x;
		this.rotY+=y;
		this.rotZ+=z;
	}
	public boolean getDrawDistanceEnabled() {
		return this.drawDistanceEnabled;
	}
	public void setDrawDistanceEnabled(boolean i) {
		this.drawDistanceEnabled = i;
	}
	public boolean getDrawToShadowShader() {
		return this.drawToShadowShader;
	}
	public void setDrawToShadowShader(boolean i) {
		this.drawToShadowShader = i;
	}
	public TexturedModel getModel() {
		return model;
	}
	public void setModel(TexturedModel model) {
		this.model = model;
	}
	public Vector3f getPosition() {
		return position;
	}
	public void setPosition(Vector3f position) {
		this.position = position;
	}
	public float getRotX() {
		return rotX;
	}
	public void setRotX(float rotX) {
		this.rotX = rotX;
	}
	public float getRotY() {
		return rotY;
	}
	public void setRotY(float rotY) {
		this.rotY = rotY;
	}
	public void setRotation(Vector3f rot) {
		this.rotX = rot.x;
		this.rotY = rot.y;
		this.rotZ = rot.z;
	}
	public float getRotZ() {
		return rotZ;
	}
	public void setRotZ(float rotZ) {
		this.rotZ = rotZ;
	}
	public float getScale() {
		return scale;
	}
	public void setScale(float scale) {
		this.scale = scale;
	}

	public Vector3f getRotation() {
		return new Vector3f(this.rotX, this.rotY, this.rotZ);
	}
	
}
