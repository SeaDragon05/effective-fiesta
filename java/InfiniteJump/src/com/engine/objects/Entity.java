package com.engine.objects;

import org.lwjglx.util.vector.Vector3f;

public class Entity {
	private Vector3f position;
	private Vector3f rotation;
	private Vector3f scale;
	

	public Entity(Vector3f position, Vector3f rotation, Vector3f scale) {
		this.position = position;
		this.rotation = rotation;
		this.scale = scale;
	}
	
	public void increasePosition(Vector3f c) {
		Vector3f.add(position, c, position);
	}

	public void increaseRotation(Vector3f c) {
		Vector3f.add(rotation, c, rotation);
	}


	public Vector3f getPosition() {
		return position;
	}


	public void setPosition(Vector3f position) {
		this.position = position;
	}


	public Vector3f getRotation() {
		return rotation;
	}


	public void setRotation(Vector3f rotation) {
		this.rotation = rotation;
	}


	public Vector3f getScale() {
		return scale;
	}


	public void setScale(Vector3f scale) {
		this.scale = scale;
	}
}
