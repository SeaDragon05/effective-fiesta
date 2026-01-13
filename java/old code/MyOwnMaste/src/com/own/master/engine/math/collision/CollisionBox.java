package com.own.master.engine.math.collision;

import com.own.master.engine.math.Vector3f;

public class CollisionBox {
	private Vector3f position;
	private float length;
	private float width;
	private float height;
	private int type;
	public static final int typeSphere = 0;
	public static final int typeBox = 1;
	public static final int typePyramid = 2;
	public static final int typeFlat = 3;
	public static final int typeMesh = 4;
	public CollisionBox(Vector3f position, float length, float width, float height, int type) {
		if (type > 5 || type < 0) {
			//System.out.println("Error: Invalid type!");
			this.type = typeBox;
		}
		this.position = position;
		this.length = length;
		this.width = width;
		this.height = height;
		this.type = type;
	}
}
