package games.trainSim.base;

import org.lwjglx.util.vector.Vector3f;

public class TrackLinePoint {
	private Vector3f position;
	private Vector3f rotation;
	private Vector3f area;
	public TrackLinePoint(Vector3f position, Vector3f rotation, Vector3f area) {
		this.position = position;
		this.rotation = rotation;
		this.area = area;
	}
	public static float getDistance(TrackLinePoint pt1, TrackLinePoint pt2) {
		return (float) Math.sqrt(
				((pt2.getPosition().x - pt2.getPosition().x) * (pt2.getPosition().x - pt2.getPosition().x)) +
				((pt2.getPosition().y - pt2.getPosition().y) * (pt2.getPosition().y - pt2.getPosition().y)) +
				((pt2.getPosition().z - pt2.getPosition().z) * (pt2.getPosition().z - pt2.getPosition().z))
				);
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
	public Vector3f getArea() {
		return area;
	}
	public void setArea(Vector3f area) {
		this.area = area;
	}
}
