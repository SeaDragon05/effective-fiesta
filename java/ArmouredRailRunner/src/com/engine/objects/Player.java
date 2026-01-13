package com.engine.objects;

import org.lwjgl.glfw.GLFW;
import org.lwjglx.util.vector.Vector3f;

import com.engine.io.Input;
import com.engine.math.Maths;

import main.Engine;
import main.EngineTest;

public class Player extends Entity {
	
	public static float RUN_SPEED;
	public static float TURN_SPEED;
	public static float GRAVITY;
	public static float JUMP_POWER;
	private float modelHeight = 7;
	
	private float TERRAIN_HEIGHT = 0;
	
	private float currentSpeed = 0;
	private float currentTurnSpeed = 0;
	private float upwardsSpeed = 0;

	public Player(TexturedModel model, Vector3f position, float x, float y, float z, float scale) {
		super(model, position, x, y, z, scale);
	}



	public void move(Terrain terrain) {
		checkInputs();
		super.increaseRotation(0, currentTurnSpeed * Engine.window.getFrameTimeSeconds(), 0);
		float distance = currentSpeed * Engine.window.getFPS() / 120;//wtf
		float dx = (float) (distance * Math.sin(Math.toRadians(super.getRotY())));
		float dz = (float) (distance * Math.cos(Math.toRadians(super.getRotY())));
		upwardsSpeed += GRAVITY * Engine.window.getFrameTimeSeconds();
		super.increasePosition(dx, upwardsSpeed * Engine.window.getFrameTimeSeconds(), dz);
		
		TERRAIN_HEIGHT = terrain.getHeightTerrain(super.getPosition().x, super.getPosition().z);
		if (super.getPosition().y <TERRAIN_HEIGHT + modelHeight) {
			upwardsSpeed = 0;
			super.getPosition().y = TERRAIN_HEIGHT + modelHeight;
		}
	}
	public void jump() {
		if (super.getPosition().y <= TERRAIN_HEIGHT + modelHeight)
			this.upwardsSpeed = JUMP_POWER;
	}
	
	public void checkInputs() {
		if (Input.isKeyDown(GLFW.GLFW_KEY_W)) {
			this.currentSpeed = RUN_SPEED * Engine.window.getFrameTimeSeconds();
			EngineTest.system.setPps(120);
		} else if (Input.isKeyDown(GLFW.GLFW_KEY_S)) {
			this.currentSpeed = -RUN_SPEED * Engine.window.getFrameTimeSeconds();
		} else { 
			EngineTest.system.setPps(20);
			this.currentSpeed /= 3;
		}
		if (Input.isKeyDown(GLFW.GLFW_KEY_A)) {
			this.currentTurnSpeed = TURN_SPEED;
		} else if (Input.isKeyDown(GLFW.GLFW_KEY_D)) {
			this.currentTurnSpeed = -TURN_SPEED;
		} else {
			this.currentTurnSpeed /= 4;
		}
		if (Input.isKeyDown(GLFW.GLFW_KEY_SPACE)) {
			jump();
		}
		if (Input.isKeyDown(GLFW.GLFW_KEY_LEFT_CONTROL)) {
			
		}
	}
	public Vector3f getRotatedPosition(Vector3f offset) {
		Vector3f newPos = Maths.RotateVec3AroundPoint(offset, getPosition(), new Vector3f(-this.getRotX(), this.getRotY(), this.getRotZ()));
		return newPos;
	}



	public void move(int i) {
		checkInputs();
		super.increaseRotation(0, currentTurnSpeed * Engine.window.getFrameTimeSeconds(), 0);
		float distance = currentSpeed * Engine.window.getFPS() / 120;
		float dx = (float) (distance * Math.sin(Math.toRadians(super.getRotY())));
		float dz = (float) (distance * Math.cos(Math.toRadians(super.getRotY())));
		upwardsSpeed += GRAVITY * Engine.window.getFrameTimeSeconds();
		super.increasePosition(dx, upwardsSpeed * Engine.window.getFrameTimeSeconds(), dz);
		
		TERRAIN_HEIGHT = i;
		if (super.getPosition().y <TERRAIN_HEIGHT + modelHeight) {
			upwardsSpeed = 0;
			super.getPosition().y = TERRAIN_HEIGHT + modelHeight;
		}
		
	}
}
