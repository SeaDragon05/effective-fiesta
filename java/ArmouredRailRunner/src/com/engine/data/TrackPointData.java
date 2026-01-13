package com.engine.data;

import org.lwjglx.util.vector.Vector3f;

public class TrackPointData {
	private Vector3f position;
	private Vector3f rotation;
	private float min_angle, max_angle;
	
	public TrackPointData(Vector3f p, Vector3f r) {
		this.position = p;
		this.rotation = r;
	}
	public Vector3f getPosition() {
		return this.position;
	}
	public Vector3f getRotation() {
		return this.rotation;
	}
}
