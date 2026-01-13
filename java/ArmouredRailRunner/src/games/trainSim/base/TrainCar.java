package games.trainSim.base;

import java.util.List;

import org.lwjglx.util.vector.Matrix4f;
import org.lwjglx.util.vector.Vector3f;
import org.lwjglx.util.vector.Vector4f;

import com.engine.audio.AudioMaster;
import com.engine.audio.Source;
import com.engine.data.TrackLocationData;
import com.engine.math.Maths;
import com.engine.objects.Entity;
import com.engine.objects.Player;

import main.Engine;
import main.EngineTest;

/**
 * TrainCar class Contains all objects for one car Sets itself on the assigned
 * track
 * 
 * @Author Isaac Dredge
 */
public class TrainCar implements TrainCarInterface {
	// things for the types
	public static final int TYPE_ENGINE = 1;
	public static final int TYPE_WAGON = 2;
	public static final int TYPE_SPECIALCAR = 3;
	// models and their offsets
	private Entity body;
	private Vector3f bodyOffset;
	private Entity wheels;
	private Vector3f wheel1Offset;
	private Entity wheels2;
	private Vector3f wheel2Offset;
	private Entity gun1;
	private Vector3f gun1Offset;
	private Entity gun2;
	private Vector3f gun2Offset;
	private Entity model0 = null;
	private Entity model1 = null;
	private Entity model2 = null;
	private Entity model3 = null;
	private Entity model4 = null;
	private Entity model5 = null;
	private Vector3f model0Offset;
	private Vector3f model1Offset;
	private Vector3f model2Offset;
	private Vector3f model3Offset;
	private Vector3f model4Offset;
	private Vector3f model5Offset;
	private Vector3f camOffset, lightOffset1, lightOffset2;
	private Track assignedLine;
	private int[] trackNumber;
	private float[] trackPos;
	private Vector3f[] trackPos3f;
	private List<Vector3f> wheelFrontOffsets;
	private List<Vector3f> wheelBackOffsets;
	private List<Entity> wheelModel;
	private Player player = null;
	private float distanceFromFront;
	private boolean playSound = false;
	// data
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
	private Source rail;// = AudioMaster.newSoundSource(1, 10, 30, true, new Vector3f(0, 0, 0));
	private Source engine;// = AudioMaster.newSoundSource(1, 10, 30, true, new Vector3f(0, 0, 0));

	private Vector3f position;
	private Vector3f rotation;

	private float distanceToFront;
	private float distanceToBack;

	// unused:
	private Vector3f phy_positionF_P, phy_positionF_D = new Vector3f(0, 0, 0), phy_positionB_P,
			phy_positionB_D = new Vector3f(0, 0, 0);

	/**
	 * 
	 * @param distanceFromFront This should be 0 if its the first car, or its the
	 *                          length of each car added together thats before this
	 *                          object
	 * @param body              Model entity
	 * @param bodyOffset        Vector3f offset, usually just uses the y value to
	 *                          make it not touch the ground/clip through it
	 * @param wheelModels       Model entity list
	 * @param wheelFrontOffsets annoying arraylist of vector3's that usually just
	 *                          use the y and z values
	 * @param wheels            wheel model, not an arraylist for faster rendering.
	 *                          Different models will increase render time
	 * @param wheel1Offset      front truck offset. Not to be confused with the
	 *                          acutal wheels. AKA the thing that holds the wheels
	 *                          together
	 * @param wheelBackOffsets
	 * @param wheels2
	 * @param wheel2Offset
	 * @param gun1
	 * @param gun1Offset
	 * @param gun2
	 * @param gun2Offset
	 * @param type
	 * @param gunCount
	 * @param crewCount
	 * @param slotCount
	 * @param isEngine
	 * @param power
	 * @param fuel
	 * @param health
	 * @param armor
	 * @param position
	 * @param rotation
	 * @param distanceToFront
	 * @param distancetoBack
	 */
	public TrainCar(float distanceFromFront, Entity body, Vector3f bodyOffset, List<Entity> wheelModels,
			List<Vector3f> wheelFrontOffsets, Entity wheels, Vector3f wheel1Offset, List<Vector3f> wheelBackOffsets,
			Entity wheels2, Vector3f wheel2Offset, Entity gun1, Vector3f gun1Offset, Entity gun2, Vector3f gun2Offset,
			int type, int gunCount, int crewCount, int slotCount, boolean isEngine, float power, float fuel,
			float health, float armor, Vector3f position, Vector3f rotation, float distanceToFront,
			float distancetoBack) {
		this.distanceFromFront = distanceFromFront;
		this.body = body;
		this.bodyOffset = bodyOffset;
		this.wheels = wheels;
		this.wheel1Offset = wheel1Offset;
		this.wheels2 = wheels2;
		this.wheel2Offset = wheel2Offset;
		this.gun1 = gun1;
		this.gun1Offset = gun1Offset;
		this.gun2 = gun2;
		this.gun2Offset = gun2Offset;
		this.type = type;
		this.gunCount = gunCount;
		this.crewCount = crewCount;
		this.slotCount = slotCount;
		this.isEngine = isEngine;
		this.power = power;
		this.fuel = fuel;
		this.health = health;
		this.armor = armor;
		this.position = position;
		this.rotation = rotation;
		this.trackNumber = new int[2];
		this.trackPos = new float[2];
		this.trackPos3f = new Vector3f[2];
		this.trackPos3f[0] = new Vector3f(0, 0, 0);
		this.trackPos3f[1] = new Vector3f(0, 0, 0);
		this.wheelFrontOffsets = wheelFrontOffsets;
		this.wheelBackOffsets = wheelBackOffsets;
		this.wheelModel = wheelModels;
		this.distanceToFront = distanceToFront;
		this.distanceToBack = distancetoBack;
		this.camOffset = new Vector3f(3.2687f, 10.1f, 29);
		this.lightOffset1 = new Vector3f(0, 18, 26.735f);
		this.lightOffset2 = new Vector3f(0, 13, 43.239f);
		this.rail = AudioMaster.newSoundSource(1, 1, 75, true, new Vector3f(0, 0, 0));
		this.engine = AudioMaster.newSoundSource(1, 10, 30, true, new Vector3f(0, 0, 0));
		this.rail.stop();
		this.engine.stop();
	}

	public TrainCar() {
	}

	public void setPlayer(Player player) {
		this.player = player;// POINTER!?!??!!??!
	}

	public void setPlay(boolean value) {
		this.playSound = value;
	}

	public void pushToMasinList() {
		Engine.entities.add(body);
		Engine.entities.add(wheels);
		Engine.entities.add(wheels2);
		for (Entity whele : wheelModel) {
			Engine.entities.add(whele);
		}
		if (gun1 != null) 
			Engine.entities.add(gun1);
		if (gun2 != null)
			Engine.entities.add(gun2);
		if (model0 != null)
			Engine.entities.add(model0);
		if (model1 != null)
			Engine.entities.add(model1);
		if (model2 != null)
			Engine.entities.add(model2);
		if (model3 != null)
			Engine.entities.add(model3);
		if (model4 != null)
			Engine.entities.add(model4);
		if (model5 != null)
			Engine.entities.add(model5);
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

	public void setAssignedLine(Track track) {
		this.assignedLine = track;// this SHOULD BE A POINTER
	}

	/*
	 * updates the gun positions and rotations positions are easily calculated,
	 * while the rotations are calculated much differently
	 */
	public void updateGuns(Vector3f lookAt) {
		if (gun1 == null && gun2 == null) {
			return;
		}
		Vector3f adj1 = Vector3f.add(gun1Offset, getBodyOffset(), null);
		Vector3f pt1 = Vector3f.sub(lookAt, getBodyModelRotatedVec3(adj1), null);
		Vector3f adj2 = Vector3f.add(gun2Offset, getBodyOffset(), null);
		Vector3f pt2 = Vector3f.sub(lookAt, getBodyModelRotatedVec3(adj2), null);
		Vector3f gun1Pos = getBodyModelRotatedVec3(gun1Offset);
		Vector3f gun2Pos = getBodyModelRotatedVec3(gun2Offset);
		float rot1x = (float) Math.atan2(pt1.y, pt1.z);
		float rot1y;
		if (pt1.z >= 0) {
			rot1y = (float) -Math.atan2(pt1.x * Math.cos(rot1x), pt1.z);
		} else {
			rot1y = (float) Math.atan2(pt1.x * Math.cos(rot1x), -pt1.z);
		}
		Vector3f gr1 = new Vector3f(rot1x, rot1y, 0);
		float rot2x = (float) Math.atan2(pt1.y, pt1.z);
		float rot2y;
		if (pt2.z >= 0) {
			rot2y = (float) -Math.atan2(pt2.x * Math.cos(rot2x), pt2.z);
		} else {
			rot2y = (float) Math.atan2(pt2.x * Math.cos(rot2x), -pt2.z);
		}
		Vector3f gr2 = new Vector3f(rot2x, rot2y, 0);
		this.gun1.setPosition(gun1Pos);
		this.gun1.setRotation(gr1);
		this.gun2.setPosition(gun2Pos);
		this.gun2.setRotation(gr2);
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
	 */
	public void setOnTrack(float length, float speed) {
		
		// corrected math lmao im so bad at this
		length += distanceFromFront;
		this.rail.setPitch(Math.abs(speed/2) * 4);
		this.rail.setVolume(Math.abs(speed/2));
		if (!rail.isPlaying()) {
			this.rail.play(Engine.TA0);
		}
		Vector3f pos1, pos1W1, pos1W2, pos1W3, pos2, pos2W1, pos2W2, pos2W3, rot1, rot1W, rot2, rot2W,
				bodyPos = new Vector3f(0, 0, 0), bodyRot = new Vector3f(0, 0, 0);
		TrackLocationData data1 = assignedLine.getTrackFromDistance(length + this.wheel1Offset.z);
		if (data1.getIndex() > assignedLine.getLine().size() - 2) {
			data1.setIndex(data1.getIndex() - assignedLine.getLine().size() + 1);
		}
		float ratio = ((data1.getDistance() / assignedLine.getDistance()) % 1);
		pos1 = Maths.blend(assignedLine.getLine().get(data1.getIndex()).getPosition(),
				assignedLine.getLine().get(data1.getIndex() + 1).getPosition(), ratio);

		rot1 = Maths.blend(assignedLine.getLine().get(data1.getIndex()).getRotation(),
				assignedLine.getLine().get(data1.getIndex() + 1).getRotation(), ratio);
		TrackLocationData data2 = assignedLine.getTrackFromDistance(length + this.wheel2Offset.z);
		if (data2.getIndex() > assignedLine.getLine().size() - 2) {
			data2.setIndex(data2.getIndex() - assignedLine.getLine().size() + 1);
		}
		ratio = ((data2.getDistance() / assignedLine.getDistance()) % 1);
		pos2 = Maths.blend(assignedLine.getLine().get(data2.getIndex()).getPosition(),
				assignedLine.getLine().get(data2.getIndex() + 1).getPosition(), ratio);
		rot2 = Maths.blend(assignedLine.getLine().get(data2.getIndex()).getRotation(),
				assignedLine.getLine().get(data2.getIndex() + 1).getRotation(), ratio);

		float wheelRotation = (float) ((2 * Math.PI * (1.17625 * 4)) * length);

		// the bug that i noticed came from an oversight which brought about the wheels
		// not positioning and rotating correctly
		// It was incorrectly positioning the trucks on the track while using way different track data that did not reflect the trucks position and rotation
		// the offsets were correct, no need to divide by 2 when we define it
		// we already used the z values, now we use the y values
		pos2.y += 2;// the distance from the ground
		pos1.y += 2;
		this.phy_positionF_P = pos1;
		this.phy_positionB_P = pos2;
		pos1W1 = Maths.RotateVec3AroundPoint(this.wheelFrontOffsets.get(0), pos1, rot1);
		pos1W2 = Maths.RotateVec3AroundPoint(this.wheelFrontOffsets.get(1), pos1, rot1);
		pos1W3 = Maths.RotateVec3AroundPoint(this.wheelFrontOffsets.get(2), pos1, rot1);
		float rx = 0;// rot1.z;
		float ry = rot1.y + 90;
		float rz = wheelRotation;// AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
		rot1W = rot1; //new Vector3f(rx, ry, rz);
		pos2W1 = Maths.RotateVec3AroundPoint(this.wheelBackOffsets.get(0), pos2, rot2);
		pos2W2 = Maths.RotateVec3AroundPoint(this.wheelBackOffsets.get(1), pos2, rot2);
		pos2W3 = Maths.RotateVec3AroundPoint(this.wheelBackOffsets.get(2), pos2, rot2);
		//rx = 0;
		//ry = rot2.y + 90;
		//rz = wheelRotation;// AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
		//rx = po
		rot2W = rot2; // = new Vector3f(rx, ry, rz);
		bodyPos.x = (pos1.x + pos2.x) / 2;
		bodyPos.y = (pos1.y + pos2.y) / 2;
		bodyPos.y += bodyOffset.y - 2;
		bodyPos.z = (pos1.z + pos2.z) / 2;
		bodyRot.x = rot1.x;
		bodyRot.y = -(float) ((float) Math.atan2(pos1.getZ() - pos2.getZ(), pos1.getX() - pos2.getX())
				* (180 / Math.PI)) + 90;
		bodyRot.z = (rot1.z + rot2.z) / 2;
		// set the positions:
		this.rail.setPosition(bodyPos);
		this.position = bodyPos;
		this.body.setPosition(bodyPos);
		this.body.setRotation(bodyRot);
		this.wheels2.setPosition(pos2);
		this.wheels2.setRotation(rot2);
		this.wheelModel.get(0).setRotation(rot2W);
		this.wheelModel.get(0).setPosition(pos2W1);
		this.wheelModel.get(1).setRotation(rot2W);
		this.wheelModel.get(1).setPosition(pos2W2);
		this.wheelModel.get(2).setRotation(rot2W);
		this.wheelModel.get(2).setPosition(pos2W3);
		this.wheels.setRotation(rot1);
		this.wheels.setPosition(pos1);
		// System.out.println(this.wheels.getPosition());
		// System.out.println(this.wheels2.getPosition());
		this.wheelModel.get(3).setRotation(rot1W);
		this.wheelModel.get(3).setPosition(pos1W1);
		this.wheelModel.get(4).setRotation(rot1W);
		this.wheelModel.get(4).setPosition(pos1W2);
		this.wheelModel.get(5).setRotation(rot1W);
		this.wheelModel.get(5).setPosition(pos1W3);
		if (player != null) {
			if (this.isEngineOn) {
				Engine.trainFrontLight.setPosition(Maths.RotateVec3AroundPoint(this.lightOffset2, bodyPos, bodyRot));
				Matrix4f transformationMatrix = Maths.createTransformationMatrix(Engine.trainFrontLight.getPosition(), body.getRotX(),
						body.getRotY(), body.getRotZ(), body.getScale());
				Engine.trainFrontLight.setAssignedModelTM(transformationMatrix);
				Engine.trainFrontLight.setDirection(new Vector3f(0.0000f, 0.1056f, -0.9944f));
			}
			player.setPosition(Maths.RotateVec3AroundPoint(this.camOffset, bodyPos, bodyRot));
			player.setRotation(bodyRot);
			engine.setPosition(bodyPos);
		}

		if (this.gun1 != null) {
			this.gun1.setPosition(Maths.RotateVec3AroundPoint(this.gun1Offset, bodyPos, bodyRot));
			this.gun1.setRotation(bodyRot);
		}
		
		if (this.gun2 != null) {
			this.gun2.setPosition(Maths.RotateVec3AroundPoint(this.gun2Offset, bodyPos, bodyRot));
			this.gun2.setRotation(bodyRot);
		}
		
		if (this.model0 != null) {
			this.model0.setPosition(Maths.RotateVec3AroundPoint(this.model0Offset, bodyPos, bodyRot));
			this.model0.setRotation(bodyRot);
		}
		
		if (this.model1 != null) {
			this.model1.setPosition(Maths.RotateVec3AroundPoint(this.model1Offset, bodyPos, bodyRot));
			this.model1.setRotation(bodyRot);
		}
		
		if (this.model2 != null) {
			this.model2.setPosition(Maths.RotateVec3AroundPoint(this.model2Offset, bodyPos, bodyRot));
			this.model2.setRotation(bodyRot);
		}
		
		if (this.model3 != null) {
			this.model3.setPosition(Maths.RotateVec3AroundPoint(this.model3Offset, bodyPos, bodyRot));
			this.model3.setRotation(bodyRot);
		}
		
		if (this.model4 != null) {
			this.model4.setPosition(Maths.RotateVec3AroundPoint(this.model4Offset, bodyPos, bodyRot));
			this.model4.setRotation(bodyRot);
		}
		
		if (this.model5 != null) {
			this.model5.setPosition(Maths.RotateVec3AroundPoint(this.model5Offset, bodyPos, bodyRot));
			this.model5.setRotation(bodyRot);
		}
		if (this.player != null) {
			Engine.trainCabinLight.setPosition(
					Maths.RotateVec3AroundPoint(this.lightOffset1, this.body.getPosition(), this.body.getRotation()));
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
		return Maths.RotateVec3AroundPoint(point, this.body.getPosition(), this.body.getRotation());
	}

	public Entity getBody() {
		return body;
	}

	public void setBody(Entity body) {
		this.body = body;
	}

	public Vector3f getBodyOffset() {
		return bodyOffset;
	}

	public void setBodyOffset(Vector3f bodyOffset) {
		this.bodyOffset = bodyOffset;
	}

	public Entity getWheels() {
		return wheels;
	}

	public void setWheels(Entity wheels) {
		this.wheels = wheels;
	}

	public Vector3f getWheel1Offset() {
		return wheel1Offset;
	}

	public void setWheel1Offset(Vector3f wheel1Offset) {
		this.wheel1Offset = wheel1Offset;
	}

	public Entity getWheels2() {
		return wheels2;
	}

	public void setWheels2(Entity wheels2) {
		this.wheels2 = wheels2;
	}

	public Vector3f getWheel2Offset() {
		return wheel2Offset;
	}

	public void setWheel2Offset(Vector3f wheel2Offset) {
		this.wheel2Offset = wheel2Offset;
	}

	public Entity getGun1() {
		return gun1;
	}

	public void setGun1(Entity gun1) {
		this.gun1 = gun1;
	}

	public Vector3f getGun1Offset() {
		return gun1Offset;
	}

	public void setGun1Offset(Vector3f gun1Offset) {
		this.gun1Offset = gun1Offset;
	}

	public Entity getGun2() {
		return gun2;
	}

	public void setGun2(Entity gun2) {
		this.gun2 = gun2;
	}

	public Vector3f getGun2Offset() {
		return gun2Offset;
	}

	public void setGun2Offset(Vector3f gun2Offset) {
		this.gun2Offset = gun2Offset;
	}

	public int[] getTrackNumber() {
		return trackNumber;
	}

	public void setTrackNumber(int[] trackNumber) {
		this.trackNumber = trackNumber;
	}

	public float[] getTrackPos() {
		return trackPos;
	}

	public void setTrackPos(float[] trackPos) {
		this.trackPos = trackPos;
	}

	public Vector3f[] getTrackPos3f() {
		return trackPos3f;
	}

	public void setTrackPos3f(Vector3f[] trackPos3f) {
		this.trackPos3f = trackPos3f;
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

	public Track getAssignedLine() {
		return assignedLine;
	}

	public Player getPlayer() {
		return player;
	}

	public float getDistance() {
		return distanceFromFront;
	}

	public float getDistanceToFront() {
		return distanceToFront;
	}

	public float getDistanceToBack() {
		return distanceToBack;
	}

	public void updateGunsDisabled() {

	}

	public Entity getModel0() {
		return model0;
	}

	public void setModel0(Entity model0) {
		this.model0 = model0;
	}

	public Entity getModel1() {
		return model1;
	}

	public void setModel1(Entity model1) {
		this.model1 = model1;
	}

	public Entity getModel2() {
		return model2;
	}

	public void setModel2(Entity model2) {
		this.model2 = model2;
	}

	public Entity getModel3() {
		return model3;
	}

	public void setModel3(Entity model3) {
		this.model3 = model3;
	}

	public Entity getModel4() {
		return model4;
	}

	public void setModel4(Entity model4) {
		this.model4 = model4;
	}

	public Entity getModel5() {
		return model5;
	}

	public void setModel5(Entity model5) {
		this.model5 = model5;
	}

	public Vector3f getModel0Offset() {
		return model0Offset;
	}

	public void setModel0Offset(Vector3f model0Offset) {
		this.model0Offset = model0Offset;
	}

	public Vector3f getModel1Offset() {
		return model1Offset;
	}

	public void setModel1Offset(Vector3f model1Offset) {
		this.model1Offset = model1Offset;
	}

	public Vector3f getModel2Offset() {
		return model2Offset;
	}

	public void setModel2Offset(Vector3f model2Offset) {
		this.model2Offset = model2Offset;
	}

	public Vector3f getModel3Offset() {
		return model3Offset;
	}

	public void setModel3Offset(Vector3f model3Offset) {
		this.model3Offset = model3Offset;
	}

	public Vector3f getModel4Offset() {
		return model4Offset;
	}

	public void setModel4Offset(Vector3f model4Offset) {
		this.model4Offset = model4Offset;
	}

	public Vector3f getModel5Offset() {
		return model5Offset;
	}

	public void setModel5Offset(Vector3f model5Offset) {
		this.model5Offset = model5Offset;
	}
}
