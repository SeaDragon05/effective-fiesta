package engine.objects;

import engine.graphics.Mesh;
import engine.maths.Vector3f;

public class GameObject {
	private Vector3f position, rotation, scale;
	private Mesh mesh;
	private int objectTypeID;
	
	public GameObject(Vector3f position, Vector3f rotation, Vector3f scale, Mesh mesh, int objectTypeID) {
		this.position = position;
		this.rotation = rotation;
		this.scale = scale;
		this.mesh = mesh;
		this.objectTypeID = objectTypeID;
	}
	
	public void update() {
		position.setZ(position.getZ() - 0.05f);
	}
	
	public int getObjectTypeID() {
		return objectTypeID;
	}
	
	public void setObjectTypeID(int value) {
		objectTypeID = value;
	}
	
	public Vector3f getPosition() {
		return position;
	}

	public Vector3f getRotation() {
		return rotation;
	}

	public Vector3f getScale() {
		return scale;
	}

	public Mesh getMesh() {
		return mesh;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public void setRotation(Vector3f v) {
		this.rotation = v;
	}

	public void setScale(Vector3f scale) {
		this.scale = scale;
	}

	public void setMesh(Mesh mesh) {
		this.mesh = mesh;
	}

	public void update3d() {
		// TODO Auto-generated method stub
		
	}
}