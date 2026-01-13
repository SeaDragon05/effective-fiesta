package com.own.master.engine.objects;

import java.util.ArrayList;

import com.own.master.cardgame.base.PCard;
import com.own.master.engine.graphics.Mesh;

public class Map {
	private ArrayList<GameObject> staticObjectList = new ArrayList<>();
	private ArrayList<PCard> NonStaticObjectList = new ArrayList<>();
	private ArrayList<Mesh> meshes = new ArrayList<>();
	private String mapName;
	public Map(String mapName) {
		this.mapName = mapName;
	}
	public void addMesh(Mesh mesh) {
		this.meshes.add(mesh);
	}
	public Mesh getMesh(int index) {
		return this.meshes.get(index);
	}
	public String getName() {
		return mapName;
	}
	public void addStaticObjectList(GameObject object) {
		this.staticObjectList.add(object);
	}
	public void addNonStaticObjectList(PCard object) {
		this.NonStaticObjectList.add(object);
	}
	public void create() {
		for (Mesh element : meshes) {
			element.create();
		}
	}
	public void destroy() {
		for (Mesh element : meshes) {
			element.destroy();
		}
	}
	public ArrayList<GameObject> getStaticObjectList() {
		return staticObjectList;
	}
	public void setStaticObjectList1(ArrayList<GameObject> staticObjectList) {
		this.staticObjectList = staticObjectList;
	}
	public ArrayList<PCard> getNonStaticObjectList() {
		return NonStaticObjectList;
	}
	public void setStaticObjectList2(ArrayList<PCard> NonStaticObjectList) {
		this.NonStaticObjectList = NonStaticObjectList;
	}
	public ArrayList<Mesh> getMeshes() {
		return meshes;
	}
	public void setMeshes(ArrayList<Mesh> meshes) {
		this.meshes = meshes;
	}
	public String getMapName() {
		return mapName;
	}
	public void setMapName(String mapName) {
		this.mapName = mapName;
	}
}
