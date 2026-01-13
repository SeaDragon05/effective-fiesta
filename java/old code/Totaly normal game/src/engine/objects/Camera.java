package engine.objects;

import org.lwjgl.glfw.GLFW;

import engine.io.Input;
import engine.maths.Vector3f;

public class Camera {
	private Vector3f position, rotation;
	private float moveSpeed = .05f, mouseSensitivity = 0.15f, distance = 8.0f, horizontalAngle = 0, verticalAngle = 0;
	private double oldMouseX, oldMouseY = 0, newMouseX, newMouseY;

	private boolean shouldMoveMouse;

	public Camera(Vector3f postition, Vector3f rotation) {
		this.position = postition;
		this.rotation = rotation;
		this.shouldMoveMouse = false;
	}

	public void update() {
		newMouseX = Input.getMouseX();
		newMouseY = Input.getMouseY();

		if (Input.isKeyDown(GLFW.GLFW_KEY_A))
			position = Vector3f.add(position,
					new Vector3f(moveSpeed * (float) -Math.cos(Math.toRadians(rotation.getY())), 0,
							moveSpeed * (float) Math.sin(Math.toRadians(rotation.getY()))));
		if (Input.isKeyDown(GLFW.GLFW_KEY_D))
			position = Vector3f.add(position,
					new Vector3f(moveSpeed * (float) Math.cos(Math.toRadians(rotation.getY())), 0,
							moveSpeed * (float) -Math.sin(Math.toRadians(rotation.getY()))));
		if (Input.isKeyDown(GLFW.GLFW_KEY_W))
			position = Vector3f.add(position,
					new Vector3f(moveSpeed * (float) -Math.sin(Math.toRadians(rotation.getY())), 0,
							moveSpeed * (float) -Math.cos(Math.toRadians(rotation.getY()))));
		if (Input.isKeyDown(GLFW.GLFW_KEY_S))
			position = Vector3f.add(position,
					new Vector3f(moveSpeed * (float) Math.sin(Math.toRadians(rotation.getY())), 0,
							moveSpeed * (float) Math.cos(Math.toRadians(rotation.getY()))));
		if (Input.isKeyDown(GLFW.GLFW_KEY_SPACE))
			position = Vector3f.add(position, new Vector3f(0, moveSpeed, 0));
		if (Input.isKeyDown(GLFW.GLFW_KEY_LEFT_SHIFT))
			position = Vector3f.add(position, new Vector3f(0, -moveSpeed, 0));
		if (Input.isKeyDown(GLFW.GLFW_KEY_LEFT_CONTROL))
			position = Vector3f.add(position, new Vector3f(0, -moveSpeed, 0));

		if (shouldMoveMouse) {

			float dx = (float) ((float) newMouseX - oldMouseX);
			float dy = (float) ((float) newMouseY - oldMouseY);

			rotation = Vector3f.add(rotation, new Vector3f(-dy * mouseSensitivity, -dx * mouseSensitivity, 0));

			oldMouseX = newMouseX;
			oldMouseY = newMouseY;

		}

	}

	public void update(GameObject object, Vector3f values) {
		float horizontalDistance = (float) (values.getX() * Math.cos(Math.toRadians(rotation.getX())));
		float verticalDistance = (float) (values.getX() * Math.sin(Math.toRadians(rotation.getX())));
		float xOffset = (float) (horizontalDistance * Math.sin(Math.toRadians(values.getY())));
		float zOffset = (float) (horizontalDistance * Math.cos(Math.toRadians(values.getY())));
		position.set(object.getPosition().getX() + xOffset, object.getPosition().getY() - verticalDistance,
				object.getPosition().getZ() + zOffset);
		rotation.set(values.getZ(), values.getY(), 0);

	}

	public void update(GameObject object) {
		newMouseX = Input.getMouseX();
		newMouseY = Input.getMouseY();
		float dx = (float) ((float) newMouseX - oldMouseX);
		float dy = (float) ((float) newMouseY - oldMouseY);
		if (Input.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_LEFT)) {
			setVerticalAngle(getVerticalAngle() - dy * mouseSensitivity);
			setHorizontalAngle(getHorizontalAngle() + dx * mouseSensitivity);
		}
		if (Input.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_RIGHT)) {
			if (getDistance() > 2) {
				if (getDistance() < 30) {
					setDistance(getDistance() + dy * mouseSensitivity);
				} else {
					setDistance(29.9f);
				}
			} else {
				setDistance(2.1f);
			}
		}
		float horizontalDistance = (float) (getDistance() * Math.cos(Math.toRadians(rotation.getX())));
		float verticalDistance = (float) (getDistance() * Math.sin(Math.toRadians(rotation.getX())));

		float xOffset = (float) (horizontalDistance * Math.sin(Math.toRadians(-horizontalAngle)));
		float zOffset = (float) (horizontalDistance * Math.cos(Math.toRadians(-horizontalAngle)));

		position.set(object.getPosition().getX() + xOffset, object.getPosition().getY() - verticalDistance,
				object.getPosition().getZ() + zOffset);

		rotation.set(getVerticalAngle(), -getHorizontalAngle(), 0);

		oldMouseX = newMouseX;
		oldMouseY = newMouseY;

	}

	public void update(Vector3f position, Vector3f rotation) {
		this.position = position;
		this.rotation = rotation;
	}

	public Vector3f getPosition() {
		return position;
	}

	public Vector3f getRotation() {
		return rotation;
	}

	public void moveMouse(boolean b) {
		shouldMoveMouse = b;
	}

	public void setPositionY(float y) {
		position.setY(y);
	}

	public float getDistance() {
		return distance;
	}

	public void setDistance(float distance) {
		this.distance = distance;
	}

	public float getHorizontalAngle() {
		return horizontalAngle;
	}

	public void setHorizontalAngle(float horizontalAngle) {
		this.horizontalAngle = horizontalAngle;
	}

	public float getVerticalAngle() {
		return verticalAngle;
	}

	public void setVerticalAngle(float verticalAngle) {
		this.verticalAngle = verticalAngle;
	}

}
