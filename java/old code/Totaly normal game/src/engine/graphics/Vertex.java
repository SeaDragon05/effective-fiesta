package engine.graphics;

import engine.maths.Vector2f;
import engine.maths.Vector3f;

public class Vertex {
	private Vector3f point, normal, color;
	private Vector2f textureCoord;
	
	public Vertex(Vector3f point, Vector3f normal, Vector2f textureCoord) {
		this.point = point;
		this.normal = normal;
		this.textureCoord = textureCoord;
	}

	public Vector3f getPoint() {
		return point;
	}
	
	public Vector3f getColor() {
		return color;
	}
	
	public Vector2f getTextureCoord() {
		return textureCoord;
	}

	public Vector3f getNormal() {
		return normal;
	}

	public void setPosition(Vector3f point) {
		this.point = point;
	}

	public void setNormal(Vector3f normal) {
		this.normal = normal;
	}

	public void setColor(Vector3f color) {
		this.color = color;
	}

	public void setTextureCoord(Vector2f textureCoord) {
		this.textureCoord = textureCoord;
	}
}