package engine.objects;

import engine.maths.Vector3f;

public class Light {
	private Vector3f position, color;
	private float brightness;
	public Light(Vector3f position, Vector3f color, float brightness) {
		this.position = position;
		this.color = color;
		this.brightness = brightness;
	}
	public Vector3f getPosition() {
		return position;
	}
	public void setPosition(Vector3f postion) {
		this.position = postion;
	}
	public Vector3f getColor() {
		return color;
	}
	public void setColor(Vector3f color) {
		this.color = color;
	}
	public float getBrightness() {
		return brightness;
	}
	public void setBrightness(float brightness) {
		this.brightness = brightness;
	}
	
}
