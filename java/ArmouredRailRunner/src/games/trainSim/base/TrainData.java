package games.trainSim.base;

import java.util.ArrayList;
import java.util.List;

import org.lwjglx.util.vector.Vector3f;

import com.engine.utils.Loader;

public class TrainData {
	private List<TrainObject> trainCars = new ArrayList<TrainObject>();
	private float distance;

	public TrainData() {
	}
	
	public void addTrainObject(TrainObject trainObject) {
		if (this.trainCars.size() != 0) {
			trainObject.setAssignedLine(trainCars.get(0).getAssignedLine());
			trainCars.get(0).setDistanceFromFront(88);
		}
		this.trainCars.add(trainObject);
	}
	
	
	public boolean canMove() {
		return this.trainCars.get(0).isEngineOn();
	}
	public void turnEngineOn() {
		this.trainCars.get(0).turnEndgineOn();
	}

	public void setAssignedLine(ReTrack track) {
		for (TrainObject cars : trainCars) {
			cars.setAssignedLine(track);
		}
	}

	public TrainObject getCar(int index) {
		return this.trainCars.get(index);
	}

	public void update(float amount, float speed) {//no slack whatsoever
		for (TrainObject cars : trainCars) {
			cars.setOnTrack(this.distance + amount, speed);
		}
	}

	public void setDistance(float f) {
		this.distance = f;
	}
}
