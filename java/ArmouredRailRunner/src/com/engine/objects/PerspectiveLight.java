package com.engine.objects;

import org.lwjglx.util.vector.Vector2f;
import org.lwjglx.util.vector.Vector3f;

import com.engine.graphics.ShadowFrameBuffer;

public class PerspectiveLight {

	private Vector3f position;
	private Vector3f rotation;
	private Vector3f color;
	private Vector3f attenuation = new Vector3f(1,0,0);
	private float FOV;
	private ShadowFrameBuffer buffer;
	public PerspectiveLight(Vector3f position, Vector3f rotation, float FOV,  Vector2f dimentions, Vector3f color, Vector3f attenuation) {
		this.position = position;
		this.color = color;
		this.attenuation = attenuation;
		this.rotation = rotation;
		this.FOV = FOV;
		this.buffer = new ShadowFrameBuffer((int) dimentions.x, (int) dimentions.y);
	}
	public PerspectiveLight(Vector3f position, Vector3f rotation, float FOV, Vector2f dimentions, Vector3f color) {
		this.position = position;
		this.color = color;
		this.rotation = rotation;
		this.FOV = FOV;
		this.buffer = new ShadowFrameBuffer((int) dimentions.x, (int) dimentions.y);
	}
	public int getBufferID() {
		return buffer.getShadowMap();
	}
	public Vector3f getPosition() {
		return position;
	}
	public void setPosition(Vector3f position) {
		this.position = position;
	}
	public Vector3f getColor() {
		return color;
	}
	public void setColor(Vector3f color) {
		this.color = color;
	}
	public Vector3f getAttenuation() {
		return attenuation;
	}
	public void setAttenuation(Vector3f attenuation) {
		this.attenuation = attenuation;
	}
	public Vector3f getRotation() {
		return rotation;
	}
	public void setRotation(Vector3f rotation) {
		this.rotation = rotation;
	}
	public float getFOV() {
		return FOV;
	}
	public void setFOV(float fOV) {
		FOV = fOV;
	}
}
