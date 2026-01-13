package com.engine.math;

import org.lwjglx.util.vector.Matrix4f;
import org.lwjglx.util.vector.Vector2f;
import org.lwjglx.util.vector.Vector3f;

import com.engine.objects.Camera;

public class Maths {

	public static Matrix4f createTransformationMatrix(Vector2f translation, Vector2f scale) {
		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();
		Matrix4f.translate(translation, matrix, matrix);
		Matrix4f.scale(new Vector3f(scale.x, scale.y, 1f), matrix, matrix);
		return matrix;
	}

	public static float barryCentric(Vector3f p1, Vector3f p2, Vector3f p3, Vector2f pos) {// I have no idea how this
																							// works, but it gets
																							// collision data
																							// from the terrain
		float det = (p2.z - p3.z) * (p1.x - p3.x) + (p3.x - p2.x) * (p1.z - p3.z);
		float l1 = ((p2.z - p3.z) * (pos.x - p3.x) + (p3.x - p2.x) * (pos.y - p3.z)) / det;
		float l2 = ((p3.z - p1.z) * (pos.x - p3.x) + (p1.x - p3.x) * (pos.y - p3.z)) / det;
		float l3 = 1.0f - l1 - l2;
		return l1 * p1.y + l2 * p2.y + l3 * p3.y;
	}

	public static Matrix4f createTransformationMatrix(Vector3f translation, float rx, float ry, float rz, float scale) {
		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();
		Matrix4f.translate(translation, matrix, matrix);
		Matrix4f.rotate((float) Math.toRadians(rz), new Vector3f(0, 0, 1), matrix, matrix);
		Matrix4f.rotate((float) Math.toRadians(ry), new Vector3f(0, 1, 0), matrix, matrix);
		Matrix4f.rotate((float) Math.toRadians(rx), new Vector3f(1, 0, 0), matrix, matrix);
		Matrix4f.scale(new Vector3f(scale, scale, scale), matrix, matrix);
		return matrix;
	}

	public static Matrix4f createViewMatrix(Camera camera) {
		Matrix4f viewMatrix = new Matrix4f();
		viewMatrix.setIdentity();
		Matrix4f.rotate((float) Math.toRadians(camera.getPitch()), new Vector3f(1, 0, 0), viewMatrix, viewMatrix);
		Matrix4f.rotate((float) Math.toRadians(camera.getYaw()), new Vector3f(0, 1, 0), viewMatrix, viewMatrix);
		Vector3f cameraPos = camera.getPosition();
		Vector3f negativeCameraPos = new Vector3f(-cameraPos.x, -cameraPos.y, -cameraPos.z);
		Matrix4f.translate(negativeCameraPos, viewMatrix, viewMatrix);
		return viewMatrix;
	}

	public static Vector3f RotateVec3AroundPoint(Vector3f vector, Vector3f point, Vector3f angles) {
		angles = new Vector3f((float) Math.toRadians(angles.x), (float) Math.toRadians(angles.y), (float) Math.toRadians(angles.z));
		Vector3f ans = new Vector3f((float) Math.sin(angles.x),(float) Math.sin(angles.y),(float) Math.sin(angles.z));
		Vector3f anc = new Vector3f((float) Math.cos(angles.x),(float) Math.cos(angles.y),(float) Math.cos(angles.z));
		float newX = (float) ((anc.y * ((ans.z * vector.y) + (anc.z * vector.x))) - (ans.y * vector.z));
		float newY = (float) ((ans.x * ((anc.y * vector.z) + (ans.y * ((ans.z * vector.y) + (anc.z * vector.x))))) + (anc.x * ((anc.z * vector.y) - (ans.z * vector.x))));
		float newZ = (float) ((anc.x * ((anc.y * vector.z) + (ans.y * ((ans.z * vector.y) + (anc.z * vector.x))))) - (ans.x * ((anc.z * vector.y) - (ans.z * vector.x))));
		return new Vector3f(-newX + point.x, newY + point.y, newZ + point.z);
	}
	public static Vector3f blend(Vector3f a, Vector3f b, float ratio) {
		if (ratio > 1f) {
			ratio = 1f;
		} else if (ratio < 0f) {
			ratio = 0f;
		}
		float iRatio = 1.0f - ratio;
		Vector3f result = new Vector3f(0, 0, 0);
		result.x = (a.x * iRatio) + (b.x * ratio);
		result.y = (a.y * iRatio) + (b.y * ratio);
		result.z = (a.z * iRatio) + (b.z * ratio);
		return result;
	}
	public static Vector3f combine(Vector3f a, Vector3f b) {
		Vector3f result = new Vector3f(0,0,0);
		result.x = (a.x + b.x) / 2;
		result.y = (a.y + b.y) / 2;
		result.z = (a.z + b.z) / 2;
		return result;
	}
	public static void multiply(Vector3f vector, float value) {// flexing them pointers
		vector.x = vector.x * value;
		vector.y = vector.y * value;
		vector.z = vector.z * value;
	}
}
