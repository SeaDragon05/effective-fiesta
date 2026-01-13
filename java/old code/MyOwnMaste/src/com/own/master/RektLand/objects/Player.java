package com.own.master.RektLand.objects;

import java.util.ArrayList;

import com.own.master.engine.graphics.Renderer;
import com.own.master.engine.math.Vector3f;
import com.own.master.engine.objects.Camera;
import com.own.master.engine.objects.Equipable;
import com.own.master.engine.objects.GameObject;
import com.own.master.engine.objects.Playerable;

public class Player implements Playerable {

	@Override
	public Vector3f getPos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector3f getRot() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GameObject getModel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getHealth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getArmour() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setPos(Vector3f pos) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setRot(Vector3f rot) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setModel(GameObject object) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setHealth(int value) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setArmour(int value) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addHealth(int value) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addArmour(int value) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isAI() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void spawn() {
		// TODO Auto-generated method stub

	}

	@Override
	public void kill() {
		// TODO Auto-generated method stub

	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public Camera getCamera() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Equipable> getInventory() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isRenderable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void render(Renderer rnd) {
		// TODO Auto-generated method stub

	}

	@Override
	public Player self() {
		return this;
	}

	@Override
	public void lookAt(Vector3f tar) {
		// TODO Auto-generated method stub

	}

	@Override
	public void isRenderable(boolean b) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean shouldNotSpoop() {
		// TODO Auto-generated method stub
		return false;
	}

}
