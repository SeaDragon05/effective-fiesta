package com.engine.objects;

import org.lwjglx.util.vector.Matrix4f;
import org.lwjglx.util.vector.Vector3f;

import main.Engine;

public class SpotLight {
	private Vector3f position, rotation, color, attenuation = new Vector3f(1,0,0);
	private float FOV;
	private int mapSize;
	private Camera lightSource;
	public SpotLight(Vector3f position, Vector3f rotation, float FOV, int mapSize, Vector3f color, Vector3f attenuation) {
		this.position = position;
		this.rotation = rotation;
		this.FOV = FOV;
		this.mapSize = mapSize;
		this.color = color;
		this.attenuation = attenuation;
		this.lightSource = new Camera(position, rotation);
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
	public float getFOV() {
		return FOV;
	}
	public void setFOV(float fOV) {
		FOV = fOV;
	}
	public Camera getLightSource() {
		return lightSource;
	}
	public void setLightSource(Camera lightSource) {
		this.lightSource = lightSource;
	}
	public int getMapSize() {
		// TODO Auto-generated method stub
		return mapSize;
	}
	public Matrix4f getProjectionMatrix() {
		Matrix4f projectionMatrix = new Matrix4f();
		float aspectRatio = (float) mapSize / (float) mapSize;
		float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV / 2f))));
		float x_scale = (float) y_scale / aspectRatio;
		float frustum_length = 1000 - 1;
		
		projectionMatrix.m00 = x_scale;
		projectionMatrix.m11 = y_scale;
		projectionMatrix.m22 = -((1000 + 1) / frustum_length);
		projectionMatrix.m23 = -1;
		projectionMatrix.m32 = -((2 * 1 * 1000) / frustum_length);
		projectionMatrix.m33 = 0;
		return projectionMatrix;
	}
}
