package com.engine.objects;

import org.lwjglx.util.vector.Vector2f;
import org.lwjglx.util.vector.Vector3f;

import com.engine.graphics.ParticleMaster;
import com.engine.graphics.ParticleTexture;

import main.Engine;

public class Particle {

	private Vector3f position;
	private Vector3f velocity;
	private float gravityEffect;
	private float lifeLength;
	private float rotation;
	private float scale;
	
	private ParticleTexture texture;
	
	private Vector2f texOffset1 = new Vector2f();
	private Vector2f texOffset2 = new Vector2f();
	private float blend;
	
	
	private float elapsedTime = 0;
	
	
	public Particle(ParticleTexture texture, Vector3f position, Vector3f velocity, float gravityEffect, float lifeLength, float rotation,
			float scale) {
		this.texture = texture;
		this.position = position;
		this.velocity = velocity;
		this.gravityEffect = gravityEffect;
		this.lifeLength = lifeLength;
		this.rotation = rotation;
		this.scale = scale;
		ParticleMaster.addParticle(this);
	}
	public Vector3f getPosition() {
		return position;
	}
	public Vector3f getVelocity() {
		return velocity;
	}
	public float getRotation() {
		return rotation;
	}
	public boolean update() {
		velocity.y += Player.GRAVITY * gravityEffect * Engine.window.getFrameTimeSeconds();
		Vector3f change = new Vector3f(velocity);
		change.scale(Engine.window.getFrameTimeSeconds());
		Vector3f.add(change, position, position);
		updateTextureCoordInfo();
		elapsedTime += Engine.window.getFrameTimeSeconds();
		return elapsedTime < lifeLength;
	}
	
	private void updateTextureCoordInfo() {
		float lifeFactor = elapsedTime / lifeLength;
		int stageCount = texture.getNumberOfRows() * texture.getNumberOfRows();
		float atlasProgression = lifeFactor * stageCount;
		int index1 = (int) Math.floor(atlasProgression);
		int index2 = index1 < stageCount - 1 ? index1 + 1 : index1;
		this.blend = atlasProgression % 1;
		setTextureOffset(texOffset1, index1);
		setTextureOffset(texOffset2, index2);
	}
	
	private void setTextureOffset(Vector2f offset, int index) {
		int colum = index % texture.getNumberOfRows();
		int row = index / texture.getNumberOfRows();
		offset.x = (float) colum / texture.getNumberOfRows();
		offset.y = (float) row / texture.getNumberOfRows();
	}
	public float getScale() {
		return this.scale;
	}
	public ParticleTexture getTexture() {
		return texture;
	}
	public Vector2f getTexOffset1() {
		return texOffset1;
	}
	public Vector2f getTexOffset2() {
		return texOffset2;
	}
	public float getBlend() {
		return blend;
	}
	
}
