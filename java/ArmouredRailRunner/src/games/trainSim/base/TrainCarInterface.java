package games.trainSim.base;

public interface TrainCarInterface {
	abstract void setOnTrack(float length, float speed);
	abstract void pushToMasinList();
	abstract boolean isEngineOn();
	abstract void turnEndgineOn();
}
