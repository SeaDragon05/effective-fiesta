package games.trainSim.test;

import org.lwjglx.util.vector.Vector3f;

import com.engine.objects.Entity;

public class TrainTruck {
	private Entity model;
	private Vector3f offset;
	private int wheelCount;
	public TrainTruck() {}
	public TrainTruck(Entity entity, Vector3f offset, int count) {
		this.model = entity;
		this.offset = offset;
		this.wheelCount = count;
	}
	public void setModel(Entity entity) {
		this.model = entity;
	}
	public void setOffset(Vector3f off) {
		this.offset = off;
	}
	public Vector3f getOffset() {
		return this.offset;
	}
	public int getWheelCount() {
		return this.wheelCount;
	}
	public void setWheelCount(int n) {
		this.wheelCount = n;
	}
	public Entity getModel() {
		return this.model;
	}
}
