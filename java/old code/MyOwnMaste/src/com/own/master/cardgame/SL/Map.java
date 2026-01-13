package com.own.master.cardgame.SL;

import com.own.master.engine.graphics.Renderer;
import com.own.master.engine.math.Vector3f;
import com.own.master.engine.objects.FullLight;
import com.own.master.engine.objects.GameObject;
import com.own.master.engine.utils.FileUtils;

import Main.Main;

public class Map {
	public static com.own.master.engine.objects.Map map = new com.own.master.engine.objects.Map("Screwy Lewy");// SHEESH
	public static void load() {
		Main.player.setPos(new Vector3f(0,4.2765f,-5.3511f));
		Main.player.setRot(new Vector3f(180, 0, 0));
		Main.Lights.add(new FullLight(new Vector3f(0, 7.7322f, 0.17064f), FullLight.DOWN, new Vector3f(1f, 1f, 1f), 5, 50));
		//Main.Lights.add(new FullLight(new Vector3f(0, 7.7322f, 17.7322f), FullLight.DOWN, new Vector3f(1f, 1f, 1f), 5, 10));
		Vector3f na = new Vector3f(0,0,0);
		Vector3f ni = new Vector3f(1,1,1);
		System.out.println("Loading objects (Phase 1 out of #)");
		map.addMesh(FileUtils.loadOBJ("./models/scumMap/Chairs.obj", "/textures/common/wood.png", na, ni, 2, 1));//0
		map.addMesh(FileUtils.loadOBJ("./models/scumMap/Floor.obj", "/textures/tableMap/newFloor.png", na, ni, 5, 0.5f));//1
		map.addMesh(FileUtils.loadOBJ("./models/scumMap/Lights.obj", "/textures/tableMap/Wall Texture.png", na, ni, 7, 0));//2
		map.addMesh(FileUtils.loadOBJ("./models/scumMap/TableTop.obj", "/textures/common/wood.png", na, ni, 2, 0.3f));//3
		map.addMesh(FileUtils.loadOBJ("./models/scumMap/Walls.obj", "/textures/tableMap/Wall Texture.png", na, ni, 4, 0));//4
		map.addMesh(FileUtils.loadOBJ("./models/scumMap/Window1.obj", "/textures/tableMap/Black.png", na, ni, 20,0));//5
		map.addMesh(FileUtils.loadOBJ("./models/scumMap/Window2.obj", "/textures/tableMap/Black.png", na, ni, 20,0));//6
		map.addMesh(FileUtils.loadOBJ("./models/scumMap/WindowFrame1.obj", "/textures/common/wood.png", na, ni, 2,1));//7
		map.addMesh(FileUtils.loadOBJ("./models/scumMap/WindowFrame2.obj", "/textures/common/wood.png", na, ni, 2,1));//8

	}//na ni totally not japanese for what
	public static void create() {
		System.out.println("Creating objects (Phase 2 out of #)");
		map.create();
		for (FullLight element : Main.Lights) {
			element.create();
		}
	}
	public static void destroy() {
		map.destroy();
		for (FullLight element : Main.Lights) {
			element.destroy();
		}
	}
	public static void render(Renderer rnd) {
		for (GameObject element : map.getStaticObjectList()) {
			rnd.renderMesh(element,1);
		}
	}
	public static void createObjects() {
		System.out.println("Creating game objects (Phase 3 out of #)");
		for (int i = 0; i < map.getMeshes().size(); i++) {//render the static objects
			map.addStaticObjectList(new GameObject(new Vector3f(0,0,0), new Vector3f(0,0,0), new Vector3f(1,1,1), map.getMesh(i)));
		}
	}
	public static void render(Renderer rnd, int l) {
		for (GameObject element : map.getStaticObjectList()) {
			rnd.renderMesh(element, l);
		}
	}
}
