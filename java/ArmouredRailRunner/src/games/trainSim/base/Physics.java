package games.trainSim.base;

import org.lwjglx.util.vector.Vector3f;

abstract class Physics {
	/*
	 * physics class that houses all physics things and all
	 * seperate from maths since this is something that is very complex lol
	 * also its a property that needs to be applied to objects
	 */
	private Vector3f phy_positionA;
	private Vector3f phy_positionB;
	private Vector3f velocity;
	
	public Physics() {
		 
	}
	
}
