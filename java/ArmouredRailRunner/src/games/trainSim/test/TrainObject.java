package games.trainSim.test;

import java.io.File;

import org.lwjglx.util.vector.Vector3f;

/**
 * 
 * @author Isaac Dredge
 * Main train object class that holds all the information about the train car
 * Not the full train, just one car
 */
public class TrainObject {
	private Vector3f position;
	private Vector3f rotation;
	private TrainModel model;
	private TrainTruck truck1;
	private TrainTruck truck2;
	
	private boolean hasEngine;
	private float weight;
	private float enginePower;
	
	public TrainObject() {}
	
}