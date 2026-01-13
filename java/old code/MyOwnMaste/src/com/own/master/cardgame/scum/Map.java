package com.own.master.cardgame.scum;

import com.own.master.engine.graphics.Renderer;
import com.own.master.engine.math.Vector3f;
import com.own.master.engine.objects.Camera;
import com.own.master.engine.objects.GameObject;

public class Map {
	public static com.own.master.engine.objects.Map map = new com.own.master.engine.objects.Map("map_chapter1");// SHEESH
	public static void load() {
	}
	public static void create() {
		System.out.println("Creating objects (Phase 2 out of #)");
		map.create();
	}
	public static void destroy() {
		map.destroy();
	}
	public static void render(Camera cam, Renderer rnd) {
		for (int i = 0; i < map.getStaticObjectList().size(); i++) {
			//rnd.renderMesh(map.getStaticObjectList().get(i), cam, 1);
		}
	}
	public static void createObjects() {
		System.out.println("Creating game objects (Phase 3 out of #)");
		for (int i = 0; i < 8; i++) {//render the static objects
			map.addStaticObjectList(new GameObject(new Vector3f(0,0,0), new Vector3f(0,0,0), new Vector3f(1,1,1), map.getMesh(i)));
		}
	}
	public static void render(Camera cam, Renderer rnd, int i) {
		// TODO Auto-generated method stub

	}
}
