package games.trainSim.test;

import java.util.List;

import org.lwjglx.util.vector.Vector3f;

import com.engine.objects.Entity;

public class TrainModel {
	private List<Entity> modelList;
	private List<Vector3f> modelOffsets;
	private boolean isStatic = true;
	public TrainModel() {}
	public TrainModel(List<Entity> ml, List<Vector3f> mo) {
		this.modelList = ml;
		this.modelOffsets = mo;
	}
	public void addModel(Entity entity) {
		this.modelList.add(entity);
	}
	public List<Entity> getModels() {
		return this.modelList;
	}
	public void addOffset(Vector3f offset) {
		this.modelOffsets.add(offset);
	}
	public List<Vector3f> getOffsets() {
		return this.modelOffsets;
	}
	public void addData(Entity entity, Vector3f offset) {
		this.modelList.add(entity);
		this.modelOffsets.add(offset);
	}
	public void makeNonStatic() {
		this.isStatic = false;
	}
	public boolean isStatic() {
		return this.isStatic;
	}
}
