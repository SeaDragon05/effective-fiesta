package com.engine.objects;

import org.lwjgl.glfw.GLFW;
import org.lwjglx.input.Mouse;
import org.lwjglx.util.vector.Matrix4f;
import org.lwjglx.util.vector.Vector3f;

import com.engine.io.Input;
import com.engine.math.Maths;

public class Camera {
	
	private float dist = 0;
	private float ang = 0;
	
	private Vector3f position = new Vector3f(0,0,0);
	private float pitch = 20, yaw = 0, roll;

	private float mouseSensitivity = 0.15f, horizontalAngle = 0, verticalAngle = 0;
	private double oldMouseX, oldMouseY = 0, newMouseX, newMouseY;
	
	private Player player;
	
	public Camera(Player player) {
		this.player = player;
		
	}
	
	public Camera(Vector3f pos, Vector3f rot) {
		this.position = pos;
		this.pitch = rot.x;
		this.yaw = rot.y;
		this.roll = rot.z;
	}

	public void move() {
		update();
		calcZoom();
		calcAng();
		calcPitch();
		float hdist = calcDist();
		float vdist = calcVert();
		calcCamPos(hdist, vdist + 5);
		this.yaw = 180 - (player.getRotY() + ang);
	}
	
	public void setPlayer(Player p) {
		this.player = p;
	}
	

	public void update() {
		newMouseX = Input.getMouseX();
		newMouseY = Input.getMouseY();
		float dx = (float) ((float) newMouseX - oldMouseX);
		float dy = (float) ((float) newMouseY - oldMouseY);
		if (Input.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_LEFT)) {
			horizontalAngle = (horizontalAngle + dx * mouseSensitivity);
			verticalAngle = (verticalAngle - dy * mouseSensitivity);
		}
		if (Input.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_RIGHT)) {
			dist += dy;//risky boi 
		}
		yaw = -horizontalAngle;
		pitch = verticalAngle;
		ang = horizontalAngle;
		oldMouseX = newMouseX;
		oldMouseY = newMouseY;
	}
	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public float getPitch() {
		return pitch;
	}

	public void setPitch(float pitch) {
		this.pitch = pitch;
	}

	public float getYaw() {
		return yaw;
	}

	public void setYaw(float yaw) {
		this.yaw = yaw;
	}

	public float getRoll() {
		return roll;
	}
	
	private void calcCamPos(float hdist, float vdist) {
		float theta = player.getRotY() + ang;
		float xoffset = (float) (hdist * Math.sin(Math.toRadians(theta)));
		float zoffset = (float) (hdist * Math.cos(Math.toRadians(theta)));
		position.x = player.getPosition().x - xoffset;
		position.y = player.getPosition().y + vdist;
		position.z = player.getPosition().z - zoffset;
		
	}
	
	public void freeLook() {
		
	}

	public void setRoll(float roll) {
		this.roll = roll;
	}
	private float calcDist() {
		return (float) (dist * Math.cos(Math.toRadians(pitch)));
	}

	private float calcVert() {
		return (float) (dist * Math.sin(Math.toRadians(pitch)));
	}
	
	
	private void calcZoom() {
		float zL = Mouse.getDWheel() * 0.1f;
		dist -= zL;
	}
	
	private void calcPitch() {
		if (Input.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_RIGHT)) {
			float pitchChange = Mouse.getDY() * 0.1f;
			pitch -= pitchChange;
		}
	}
	
	private void calcAng() {
		if (Input.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_LEFT)) {
			float angleChange = Mouse.getDX() * 0.3f;
			ang += angleChange;
		}
	}

	public void invertPitch() {
		this.pitch = -pitch;
	}

	public Vector3f getRotation() {
		return new Vector3f(pitch, yaw, roll);
	}

	public Matrix4f getWorldTransformationMatrix() {
		return Maths.createViewMatrix(this);
	}

	public Player getPlayer() {
		return this.player;
	}
}
