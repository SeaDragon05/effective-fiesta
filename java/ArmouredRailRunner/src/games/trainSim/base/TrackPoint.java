package games.trainSim.base;

import org.lwjglx.util.vector.Vector3f;

public class TrackPoint {
	private Vector3f position;
	private Vector3f rotation;
	private float length;
	private boolean hasNext;
	public TrackPoint(Vector3f pos, Vector3f rot, float length, boolean hasNext) {
		this.position = pos;
		this.rotation = rot;
		this.length = length;
		this.hasNext = hasNext;
	}
	public TrackPoint(Vector3f pos) {
		this.position = pos;
		this.rotation = new Vector3f(0,0,0);
	}
	public Vector3f getPosition() {
		return position;
	}
	public Vector3f getRotation() {
		return rotation;
	}
	public float getLength() {
		return this.length;
	}
	public void setPosition(Vector3f pos) {
		this.position = pos;
	}
	public void setRotation(Vector3f rot) {
		this.rotation = rot;
	}
	public Vector3f getTrackPosition3f(float distance) {
		float x = this.position.x + (float) ((distance) * Math.sin(Math.toRadians(this.getRotation().y)));
		float y = this.position.y;
		float z = this.position.z + (float) ((distance) * Math.cos(Math.toRadians(this.getRotation().y)));
		return new Vector3f(x,y,z);
	}
	public boolean getHasNext() {
		return hasNext;
	}
}
