package engine.maths;

public class Vector3i {
	private int x, y, z;
	
	public Vector3i(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public void set(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public static Vector3i add(Vector3i vector1, Vector3i vector2) {
		return new Vector3i(vector1.getX() + vector2.getX(),vector1.getY() + vector2.getY(),vector1.getZ() + vector2.getZ());
	}
	
	public static Vector3i subtract(Vector3i vector1, Vector3i vector2) {
		return new Vector3i(vector1.getX() - vector2.getX(),vector1.getY() - vector2.getY(),vector1.getZ() - vector2.getZ());
	}
	
	public static Vector3i multiply(Vector3i vector1, Vector3i vector2) {
		return new Vector3i(vector1.getX() * vector2.getX(),vector1.getY() * vector2.getY(),vector1.getZ() * vector2.getZ());
	}
	
	public static Vector3i divide(Vector3i vector1, Vector3i vector2) {
		return new Vector3i(vector1.getX() / vector2.getX(),vector1.getY() / vector2.getY(),vector1.getZ() / vector2.getZ());
	}
	
	public static int length(Vector3i vector) {
		return (int) Math.sqrt((vector.getX() * vector.getX()) + (vector.getY() * vector.getY()) + (vector.getZ() * vector.getZ()));
	}
	public static int clamp(int value, int min, int max) {
		return Math.max(min, Math.min(value, max));
	}
	public static int interpolate(int min, int max, int gradient) {
		return min + (max - min) * clamp(gradient, 0, 1);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getZ() {
		return z;
	}

	public void setZ(int z) {
		this.z = z;
	}
}