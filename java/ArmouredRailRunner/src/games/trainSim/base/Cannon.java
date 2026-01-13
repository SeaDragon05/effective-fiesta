package games.trainSim.base;

import org.lwjglx.util.vector.Vector3f;

import com.engine.objects.Entity;

public class Cannon {
	private Entity model;
	private TrainObject assignedCar;
	private Vector3f offset;
	private Vector3f position;
	private Vector3f rotation;
	private Vector3f lookAt;
	private boolean isActivated = false;
	private boolean isRifled = false;
	
	private int ammoCount = 0;
	private float gunSize = 0;//measured in MM
	
	public boolean load() {
		return false;
	}
	public boolean fire() {
		scan();
		return false;
	}
	public boolean repair() {
		return false;
	}
	public void scan() {}
}
