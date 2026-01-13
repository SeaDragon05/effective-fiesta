package com.own.master.horrorgame.objects;

import java.util.ArrayList;

import com.own.master.engine.graphics.Mesh;
import com.own.master.engine.graphics.Renderer;
import com.own.master.engine.io.AudioPlayer;
import com.own.master.engine.math.Vector3f;
import com.own.master.engine.objects.Camera;
import com.own.master.engine.objects.Equipable;
import com.own.master.engine.objects.GameObject;
import com.own.master.engine.objects.Playerable;

public class Spoops implements Playerable {
	private Vector3f pos;
	private Vector3f rot;
	private GameObject model;
	private Playerable target;
	private boolean isRenderable = false;//set all to false so that we dont see them
	private long lifeTime = 0;
	public Spoops(Vector3f pos, Vector3f rot, Mesh model, long time) {
		this.lifeTime = time;
		this.pos = pos;
		this.rot = rot;
		this.model = new GameObject(pos, rot, new Vector3f(1,1,1), model);
	}

	public void setTarget(Playerable object) {
		this.target = object;
	}

	public Playerable getTarget() {
		return this.target;
	}

	@Override
	public void update() {
		//update if needed
		//no need for updates, however, for debug....
	}

	@Override
	public Vector3f getPos() {
		return this.pos;
	}

	@Override
	public Vector3f getRot() {
		return this.rot;
	}

	@Override
	public int getHealth() {
		return -1;// invincible
	}

	@Override
	public int getArmour() {
		// does not need armour
		return 0;
	}

	@Override
	public void setPos(Vector3f pos) {
		this.pos = pos;
		this.model.setPosition(pos);
	}

	@Override
	public void setRot(Vector3f rot) {
		this.rot = rot;
		this.model.setRotation(rot);
	}

	@Override
	public void setHealth(int value) {
		System.out.println("Cannot set health of player type SPOOPS!");
	}

	@Override
	public void setArmour(int value) {
		System.out.println("Cannot set armour of player type SPOOPS!");
	}

	@Override
	public void addHealth(int value) {
		System.out.println("Cannot add health of player type SPOOPS!");
	}

	@Override
	public void addArmour(int value) {
		System.out.println("Cannot add armour of player type SPOOPS!");
	}

	@Override
	public boolean isAI() {
		// This is an AI, so we will need to return true
		return true;
	}

	@Override
	public ArrayList<Equipable> getInventory() {
		// This is a spoopy AI, there is no need for any items
		return null;
	}

	@Override
	public void spawn() {
		isRenderable = true;
		this.pos = new Vector3f(this.target.getPos());
		AudioPlayer.playaudio("./sounds/voice/spoops/spawn.wav");

	}

	@Override
	public void kill() {
		isRenderable = false;
		AudioPlayer.playaudio("./sounds/voice/spoops/removed.wav");
	}

	@Override
	public void destroy() {
		this.model.getMesh().destroy();
	}

	@Override
	public Camera getCamera() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Spoops self() {
		return this;
	}


	@Override
	public GameObject getModel() {
		return model;
	}


	@Override
	public void setModel(GameObject object) {
		this.model = object;
	}


	@Override
	public void render(Renderer rnd) {
		this.lifeTime -= 100;
		if (isRenderable) rnd.renderMesh(model, 1);
	}


	@Override
	public boolean isRenderable() {
		return this.isRenderable;
	}

	@Override
	public void lookAt(Vector3f values) {


	}

	@Override
	public void isRenderable(boolean b) {
		this.isRenderable = b;
		
	}

	@Override
	public boolean shouldNotSpoop() {
		if (this.lifeTime <= 0) {
			return true;
		}
		return false;
	}
}
