package com.own.master.engine.math;


import java.util.Objects;

public class Vector3f {
	private float x, y, z;

	public Vector3f(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Vector3f(Vector3f pos) {
		this.x = pos.getX();
		this.y = pos.getY();
		this.z = pos.getZ();

	}
	public Vector3f(float value) {
		this.x = value;
		this.y = value;
		this.z = value;

	}

	public void set(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public static Vector3f add(Vector3f vector1, Vector3f vector2) {
		return new Vector3f(vector1.getX() + vector2.getX(),vector1.getY() + vector2.getY(),vector1.getZ() + vector2.getZ());
	}

	public static Vector3f subtract(Vector3f vector1, Vector3f vector2) {
		return new Vector3f(vector1.getX() - vector2.getX(),vector1.getY() - vector2.getY(),vector1.getZ() - vector2.getZ());
	}

	public static Vector3f multiply(Vector3f vector1, Vector3f vector2) {
		return new Vector3f(vector1.getX() * vector2.getX(),vector1.getY() * vector2.getY(),vector1.getZ() * vector2.getZ());
	}

	public static Vector3f divide(Vector3f vector1, Vector3f vector2) {
		return new Vector3f(vector1.getX() / vector2.getX(),vector1.getY() / vector2.getY(),vector1.getZ() / vector2.getZ());
	}

	public static float length(Vector3f vector) {
		return (float) Math.sqrt((vector.getX() * vector.getX()) + (vector.getY() * vector.getY()) + (vector.getZ() * vector.getZ()));
	}

	public static Vector3f normalize(Vector3f vector) {
		float length = Vector3f.length(vector);
	    return Vector3f.divide(vector, new Vector3f(length, length, length));
	}

	public static float dot(Vector3f vector1, Vector3f vector2) {
		return vector1.getX() * vector2.getX() + vector1.getY() * vector2.getY() + vector1.getZ() * vector2.getZ();
	}
	public static float computeNDotL(Vector3f point, Vector3f normal, Vector3f lightPos) {
		  Vector3f lightDirection = subtract(point, lightPos);
	      normal = normalize(normal);
	      lightDirection = normalize(lightDirection);
	      return Math.max(0, dot(normal, lightDirection));
	}
	public static float clamp(float value, float min, float max) {
		return Math.max(min, Math.min(value, max));
	}
	public static float interpolate(float min, float max, float gradient) {
		return min + (max - min) * clamp(gradient, 0, 1);
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y, z);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if ((obj == null) || (getClass() != obj.getClass()))
			return false;
		Vector3f other = (Vector3f) obj;
		return Float.floatToIntBits(x) == Float.floatToIntBits(other.x)
				&& Float.floatToIntBits(y) == Float.floatToIntBits(other.y)
				&& Float.floatToIntBits(z) == Float.floatToIntBits(other.z);
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getZ() {
		return z;
	}

	public void setZ(float z) {
		this.z = z;
	}

	public void set(Vector3f p) {
		this.x = p.getX();
		this.y = p.getY();
		this.z = p.getZ();

	}

	public void add(Vector3f p) {
		this.x += p.getX();
		this.y += p.getY();
		this.z += p.getZ();
	}
}
