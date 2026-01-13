package games.trainSim.base;

import java.util.ArrayList;
import java.util.List;

import org.lwjglx.util.vector.Matrix4f;
import org.lwjglx.util.vector.Vector3f;

import com.engine.audio.AudioMaster;
import com.engine.audio.Source;
import com.engine.data.TrackLocationData;
import com.engine.math.Maths;
import com.engine.objects.Entity;
import com.engine.objects.Player;

import main.Engine;

/**
 * rewrite of the TrainCar class to implement newer data types and for easier
 * file loading and organization
 * 
 * TrainObject class Contains all objects for one car Sets itself on the assigned
 * track
 * 
 * @Author Isaac Dredge
 */
public class TrainObject implements TrainCarInterface {
	public static final int TYPE_ENGINE = 1;
	public static final int TYPE_WAGON = 2;
	public static final int TYPE_SPECIALCAR = 3;
	public static final Vector3f defaultPos = new Vector3f(0,0,0);
	private List<Entity> staticModelList = new ArrayList<Entity>();
	private List<Vector3f> staticModelOffset = new ArrayList<Vector3f>();
	private List<Entity> nonStaticModelList = new ArrayList<Entity>();
	private List<Vector3f> nonStaticModelOffset = new ArrayList<Vector3f>();
	private List<Entity> axleModels = new ArrayList<Entity>();
	private List<Vector3f> axleOffsets = new ArrayList<Vector3f>();
	private List<Entity> truckModels = new ArrayList<Entity>();
	private Vector3f[] truckOffsets = new Vector3f[2];
	private ReTrack assignedLine;
	private Player player = null;
	private float distanceFromFront = 0;
	private boolean playSound = false;
	// data
	private int truckAxleCount = 0;
	private int type = 0;
	private int gunCount = 0;
	private int crewCount = 0;
	private int slotCount = 0;
	private boolean isEngine = false;
	private boolean isEngineOn = false;
	private float power = 0;
	private float fuel = 0;
	private float health = 0;
	private float armor = 0;
	private float weight = 0.0f;

	//bad programmer:
	private Source rail = AudioMaster.newSoundSource(1, 10, 30, true, new Vector3f(0, 0, 0));
	private Source engine = AudioMaster.newSoundSource(1, 10, 30, true, new Vector3f(0, 0, 0));

	private Vector3f position = defaultPos;
	private Vector3f rotation = defaultPos;
	private Vector3f bodyOffset = defaultPos;

	private float distanceToFront;
	private float distanceToBack;
	private Vector3f firstPersonCameraOffset = defaultPos;
	private Vector3f cabinLightOffset = defaultPos;
	private Vector3f headLightPos = defaultPos;
	
	public TrainObject() {
		this.truckOffsets[0] = new Vector3f(0,0,0);
		this.truckOffsets[1] = new Vector3f(0,0,0);
	}
	

	public void setPlayer(Player player) {
		this.player = player;// POINTER!?!??!!??!
	}

	public void setPlay(boolean value) {
		this.playSound = value;
	}

	public void pushToMasinList() {
		for (Entity model : staticModelList) {
			Engine.entities.add(model);//the forbidden line for some reason (eclipse crashed 2 times on this line and idk why)
		}
		for (Entity model : nonStaticModelList) {
			Engine.entities.add(model);
		}
		for (Entity model : axleModels) {
			Engine.entities.add(model);
		}
		for (Entity model : truckModels) {
			Engine.entities.add(model);
		}
	}

	public void turnEndgineOn() {
		isEngineOn = true;
	}

	public boolean isEngineOn() {
		return isEngineOn;
	}

	public Vector3f getRotatedPosition(Vector3f offset) {
		Vector3f newPos = Maths.RotateVec3AroundPoint(offset, position,
				new Vector3f(-this.rotation.getX(), this.rotation.getY(), this.rotation.getZ()));
		return newPos;
	}

	public void setAssignedLine(ReTrack track) {
		this.assignedLine = track;// this SHOULD BE A POINTER
	}
	
	/**
	 * SetOnTrack sets the train on the assigned track the length is the distance
	 * from the start used to move the train along the track version 3 because I
	 * couldn't get the math right till now
	 * 
	 * @param length the distance with a weird name. Need to change this
	 * @param speed  the difference between the last distance and this difference,
	 *               or how fast the object is traveling Used for sounds
	 * 
	 *               This is super complex so basicaly it finds where it is on the
	 *               assigned track, and transforms all assigned models onto that
	 *               tracks position data. I used a rotation method that rotates
	 *               basically everything around the objects main position. It also
	 *               rotates the wheels accordingly which isn't the best idea since
	 *               how openGL rotates things.
	 * 
	 *               It starts by getting the front truck assemblies position on the
	 *               track: the index of the assigned line which loops through that
	 *               list. Then gets the next one and what ever remaining distance
	 *               value we have will be used as a ratio to determine the exact
	 *               position and rotation the object needs. Then it works the same
	 *               way for the back truck, does the same thing. With both of these
	 *               positions, we can figure out how to transform the body of the
	 *               train. Then we can determine other things as well such as gun
	 *               positions, wheels, etc. Finally, with everything computed, we
	 *               then just assign all the positions and rotations to each
	 *               object. This was all done from scratch, and basically no guides
	 *               on how to do it. It was fun figuring out the math and ideas
	 *               needed, and seeing it gradually coming together.
	 * 
	 *               Fun fact: I 'yeeted' train cars along randomly generated track
	 *               to bug fix this. It was funny to see the train cars gradually
	 *               move away from where they were supposed to be
	 *               
	 *               This version uses lists for all the models
	 */
	public void setOnTrack(float length, float speed) {
		length += distanceFromFront;
		this.rail.setPitch(Math.abs(speed/2) * 4);
		this.rail.setVolume(Math.abs(speed/2));
		if (!rail.isPlaying()) {
			this.rail.play(Engine.TA0);
		}
		Vector3f pos1, pos2, rot1, rot2, trainCarPosition = new Vector3f(), trainCarRotation = new Vector3f();
		List<Vector3f> pos1_wheels = new ArrayList<Vector3f>();
		List<Vector3f> pos2_wheels = new ArrayList<Vector3f>();
		
		TrackLocationData data1 = assignedLine.getTrackFromDistance(length + this.truckOffsets[0].z);
		
		if (data1.getIndex() > assignedLine.getLine().size() - 2) {
			data1.setIndex(data1.getIndex() - assignedLine.getLine().size() + 1);
		}
		
		float ratio = ((data1.getDistance() / assignedLine.getDistance()) % 1); //MAGIC
		
		pos1 = Maths.blend(assignedLine.getLine().get(data1.getIndex()).getPosition(),
				assignedLine.getLine().get(data1.getIndex() + 1).getPosition(), ratio);

		rot1 = Maths.blend(assignedLine.getLine().get(data1.getIndex()).getRotation(),
				assignedLine.getLine().get(data1.getIndex() + 1).getRotation(), ratio);
		
		TrackLocationData data2 = assignedLine.getTrackFromDistance(length + this.truckOffsets[1].z);
		if (data2.getIndex() > assignedLine.getLine().size() - 2) {
			data2.setIndex(data2.getIndex() - assignedLine.getLine().size() + 1);
		}
		ratio = ((data2.getDistance() / assignedLine.getDistance()) % 1);
		pos2 = Maths.blend(assignedLine.getLine().get(data2.getIndex()).getPosition(),
				assignedLine.getLine().get(data2.getIndex() + 1).getPosition(), ratio);
		rot2 = Maths.blend(assignedLine.getLine().get(data2.getIndex()).getRotation(),
				assignedLine.getLine().get(data2.getIndex() + 1).getRotation(), ratio);

		//float wheelRotation = (float) ((2 * Math.PI * (1.17625 * 4)) * length);
		pos2.y += 2;// the distance from the ground
		pos1.y += 2;
		for (int i = 0; i < truckAxleCount; i ++) {
			pos1_wheels.add(Maths.RotateVec3AroundPoint(axleOffsets.get(i), pos1, rot1));
		}
		for (int i = truckAxleCount; i < 2 * truckAxleCount; i ++) {
			pos2_wheels.add(Maths.RotateVec3AroundPoint(axleOffsets.get(i), pos2, rot2));
		}
		
		//updated code for the newer train object models
		trainCarPosition.x = (pos1.x + pos2.x) / 2;
		trainCarPosition.y = (pos1.y + pos2.y) / 2;
		//trainCarPosition.y += bodyOffset.y - 2;
		trainCarPosition.z = (pos1.z + pos2.z) / 2;
		trainCarRotation.x = rot1.x;
		trainCarRotation.y = -(float) ((float) Math.atan2(pos1.getZ() - pos2.getZ(), pos1.getX() - pos2.getX())
				* (180 / Math.PI)) + 90;
		trainCarRotation.z = (rot1.z + rot2.z) / 2;
		// set the positions:
		this.rail.setPosition(trainCarPosition);
		this.position = trainCarPosition;
		this.rotation = trainCarRotation;
		//loop through all the static models and set both rot and pos values:
		for (int i = 0; i < staticModelList.size(); i ++) {
			Vector3f offset = Vector3f.add(staticModelOffset.get(i), this.bodyOffset, null);
			staticModelList.get(i).setPosition(Maths.RotateVec3AroundPoint(offset, trainCarPosition, trainCarRotation));
			staticModelList.get(i).setRotation(trainCarRotation);
		}
		//set for non static
		for (int i = 0; i < nonStaticModelList.size(); i ++) {
			nonStaticModelList.get(i).setPosition(Maths.RotateVec3AroundPoint(nonStaticModelOffset.get(i), trainCarPosition, trainCarRotation));
			//nonStaticModelList.get(i).setRotation(trainCarRotation);
		}
		//set for the 2 trucks (not gonna program for more or less because reasons)
		//front truck:
		truckModels.get(0).setPosition(pos1);
		truckModels.get(0).setRotation(rot1);
		//rear truck:
		truckModels.get(1).setPosition(pos2);
		truckModels.get(1).setRotation(rot2);
		//and now for the wheels:
		//front first
		for (int i = 0; i < truckAxleCount; i ++) {
			this.axleModels.get(i).setPosition(Maths.RotateVec3AroundPoint(this.axleOffsets.get(i), pos1, rot1));
		}
		//and for the back
		for (int i = truckAxleCount; i < 2 * truckAxleCount; i ++) {
			this.axleModels.get(i).setPosition(Maths.RotateVec3AroundPoint(this.axleOffsets.get(i), pos2, rot2));
		}
		if (player != null) {
			if (this.isEngineOn) {
				Engine.trainFrontLight.setPosition(Maths.RotateVec3AroundPoint(this.cabinLightOffset, trainCarPosition, trainCarRotation));
				Matrix4f transformationMatrix = Maths.createTransformationMatrix(Engine.trainFrontLight.getPosition(), rotation.x, rotation.y, rotation.z, 1);
				Engine.trainFrontLight.setAssignedModelTM(transformationMatrix);
				Engine.trainFrontLight.setDirection(new Vector3f(0.0000f, 0.1056f, -0.9944f));
			}
			Vector3f offset = Vector3f.add(this.firstPersonCameraOffset, this.bodyOffset, null);
			player.setPosition(Maths.RotateVec3AroundPoint(offset, trainCarPosition, trainCarRotation));
			player.setRotation(trainCarRotation);
			engine.setPosition(trainCarPosition);
			Engine.trainCabinLight.setPosition(
					Maths.RotateVec3AroundPoint(this.headLightPos, this.staticModelList.get(0).getPosition(), this.staticModelList.get(0).getRotation()));
		}
	}

	
	public Vector3f matrixMul(Matrix4f tm, Vector3f ln) {
		Vector3f result = ln;//new Vector4f(0,0,0,0);
		result.x = ((tm.m00 * ln.x) + (tm.m01 * ln.y) + (tm.m02 * ln.z));
		result.y = ((tm.m10 * ln.x) + (tm.m11 * ln.y) + (tm.m12 * ln.z));
		result.z = ((tm.m20 * ln.x) + (tm.m21 * ln.y) + (tm.m22 * ln.z));
		return result;
	}

	public Vector3f getBodyModelRotatedVec3(Vector3f point) {
		return Maths.RotateVec3AroundPoint(point, this.position, this.rotation);
	}


	public List<Entity> getStaticModelList() {
		return staticModelList;
	}


	public void setStaticModelList(List<Entity> staticModelList) {
		this.staticModelList = staticModelList;
	}

	public List<Vector3f> getStaticModelOffset() {
		return staticModelOffset;
	}


	public void setStaticModelOffset(List<Vector3f> staticModelOffset) {
		this.staticModelOffset = staticModelOffset;
	}


	public List<Entity> getNonStaticModelList() {
		return nonStaticModelList;
	}


	public void setNonStaticModelList(List<Entity> nonStaticModelList) {
		this.nonStaticModelList = nonStaticModelList;
	}


	public List<Vector3f> getNonStaticModelOffset() {
		return nonStaticModelOffset;
	}


	public void setNonStaticModelOffset(List<Vector3f> nonStaticModelOffset) {
		this.nonStaticModelOffset = nonStaticModelOffset;
	}


	public List<Entity> getAxleModels() {
		return axleModels;
	}


	public void setAxleModels(List<Entity> axleModels) {
		this.axleModels = axleModels;
	}


	public List<Vector3f> getAxleOffsets() {
		return axleOffsets;
	}


	public void setAxleOffsets(List<Vector3f> axleOffsets) {
		this.axleOffsets = axleOffsets;
	}


	public List<Entity> getTruckModels() {
		return truckModels;
	}


	public void setTruckModels(List<Entity> truckModels) {
		this.truckModels = truckModels;
	}


	public Vector3f[] getTruckOffsets() {
		return truckOffsets;
	}


	public void setTruckOffsets(Vector3f[] truckOffsets) {
		this.truckOffsets = truckOffsets;
	}


	public float getDistanceFromFront() {
		return distanceFromFront;
	}


	public void setDistanceFromFront(float distanceFromFront) {
		this.distanceFromFront = distanceFromFront;
	}


	public boolean isPlaySound() {
		return playSound;
	}


	public void setPlaySound(boolean playSound) {
		this.playSound = playSound;
	}


	public int getTruckAxleCount() {
		return truckAxleCount;
	}


	public void setTruckAxleCount(int truckAxleCount) {
		this.truckAxleCount = truckAxleCount;
	}


	public int getType() {
		return type;
	}


	public void setType(int type) {
		this.type = type;
	}


	public int getGunCount() {
		return gunCount;
	}


	public void setGunCount(int gunCount) {
		this.gunCount = gunCount;
	}


	public int getCrewCount() {
		return crewCount;
	}


	public void setCrewCount(int crewCount) {
		this.crewCount = crewCount;
	}


	public int getSlotCount() {
		return slotCount;
	}


	public void setSlotCount(int slotCount) {
		this.slotCount = slotCount;
	}


	public boolean isEngine() {
		return isEngine;
	}


	public void setEngine(boolean isEngine) {
		this.isEngine = isEngine;
	}


	public float getPower() {
		return power;
	}


	public void setPower(float power) {
		this.power = power;
	}


	public float getFuel() {
		return fuel;
	}


	public void setFuel(float fuel) {
		this.fuel = fuel;
	}


	public float getHealth() {
		return health;
	}


	public void setHealth(float health) {
		this.health = health;
	}


	public float getArmor() {
		return armor;
	}


	public void setArmor(float armor) {
		this.armor = armor;
	}


	public float getWeight() {
		return weight;
	}


	public void setWeight(float weight) {
		this.weight = weight;
	}


	public Source getRail() {
		return rail;
	}


	public void setRail(Source rail) {
		this.rail = rail;
	}


	public Source getEngine() {
		return engine;
	}


	public void setEngine(Source engine) {
		this.engine = engine;
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


	public float getDistanceToFront() {
		return distanceToFront;
	}


	public void setDistanceToFront(float distanceToFront) {
		this.distanceToFront = distanceToFront;
	}


	public float getDistanceToBack() {
		return distanceToBack;
	}


	public void setDistanceToBack(float distanceToBack) {
		this.distanceToBack = distanceToBack;
	}


	public Vector3f getFirstPersonCameraOffset() {
		return firstPersonCameraOffset;
	}


	public void setFirstPersonCameraOffset(Vector3f firstPersonCameraOffset) {
		this.firstPersonCameraOffset = firstPersonCameraOffset;
	}


	public Vector3f getCabinLightOffset() {
		return cabinLightOffset;
	}


	public void setCabinLightOffset(Vector3f cabinLightOffset) {
		this.cabinLightOffset = cabinLightOffset;
	}


	public Vector3f getHeadLightPos() {
		return headLightPos;
	}


	public void setHeadLightPos(Vector3f headLightPos) {
		this.headLightPos = headLightPos;
	}


	public ReTrack getAssignedLine() {
		return assignedLine;
	}


	public Player getPlayer() {
		return player;
	}


	public void setEngineOn(boolean isEngineOn) {
		this.isEngineOn = isEngineOn;
	}


	public void setBodyOffset(Vector3f bodyOffset) {
		this.bodyOffset = bodyOffset;
	}


}
