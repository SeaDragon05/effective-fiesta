package com.own.master.engine.objects;

import com.own.master.engine.graphics.Mesh;
import com.own.master.engine.math.Vector3f;

public class GameObject {
	private Vector3f position, rotation, scale;
	private Mesh mesh;

	public GameObject(Vector3f position, Vector3f rotation, Vector3f scale, Mesh mesh) {
		this.position = position;
		this.rotation = rotation;
		this.scale = scale;
		this.mesh = mesh;
	}
	public GameObject() {
		// TODO Auto-generated constructor stub
	}
	public Vector3f getPosition() {
		return position;
	}
	public Vector3f getRotation() {
		return rotation;
	}

	public Vector3f getScale() {
		return scale;
	}

	public Mesh getMesh() {
		return mesh;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}
	public void addPosition(Vector3f position) {
		this.position.setX(this.position.getX() + position.getX());
		this.position.setY(this.position.getY() + position.getY());
		this.position.setZ(this.position.getZ() + position.getZ());
	}

	public void setRotation(Vector3f v) {
		this.rotation = v;
	}
	public void addRotation(Vector3f v) {
		this.rotation.setX(this.rotation.getX() + v.getX());
		this.rotation.setY(this.rotation.getY() + v.getY());
		this.rotation.setZ(this.rotation.getZ() + v.getZ());
	}

	public void setScale(Vector3f scale) {
		this.scale = scale;
	}

	public void setMesh(Mesh mesh) {
		this.mesh = mesh;
	}
}