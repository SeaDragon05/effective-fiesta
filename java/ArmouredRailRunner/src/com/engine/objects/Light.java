package com.engine.objects;

import org.lwjglx.util.vector.Matrix4f;
import org.lwjglx.util.vector.Vector3f;

public class Light {
	private Vector3f position;
	private Vector3f color;
	private Vector3f attenuation = new Vector3f(1,0,0);
	private Vector3f direction = new Vector3f(0,0,0);
	private Matrix4f MTM = new Matrix4f();
	public Light(Vector3f position, Vector3f color, Vector3f attenuation) {
		this.position = position;
		this.color = color;
		this.attenuation = attenuation;
	}
	public Light(Vector3f position, Vector3f color, Vector3f attenuation, Vector3f direction) {
		this.position = position;
		this.color = color;
		this.attenuation = attenuation;
		this.direction = direction;
	}
	public Vector3f getPosition() {
		return position;
	}
	public void setPosition(Vector3f position) {
		this.position = position;
	}
	public Vector3f getDirection() {
		return this.direction;
	}
	public void setDirection(Vector3f dir) {
		this.direction = dir;
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
	public Matrix4f getAssignedModelTM() {
		return MTM;
	}
	public void setAssignedModelTM(Matrix4f mat4) {
		this.MTM = mat4;
	}
	
}
