package com.own.master.cardgame.BJ;

import com.own.master.engine.graphics.Renderer;
import com.own.master.engine.math.Vector3f;
import com.own.master.engine.objects.FullLight;
import com.own.master.engine.objects.GameObject;
import com.own.master.engine.utils.FileUtils;

import Main.Main;

public class Map {
	public static com.own.master.engine.objects.Map map = new com.own.master.engine.objects.Map("Black Jack");// SHEESH
	static final int thi = Main.gr_full_bright;
	public static void load()  {
		Main.Lights.add(new FullLight(new Vector3f(0, 5.8723f, 0), new Vector3f(-90,0,0), new Vector3f(1f, 1f, 1f), 3, 10));
		Main.Lights.add(new FullLight(new Vector3f(-3.3693f, 4.9975f, 6.0664f), new Vector3f(-90,0,0), new Vector3f(1f, 1f, 1f), 2, 2));
		System.out.println("Loading objects (Phase 1 out of #)");
		Main.player.setPos(new Vector3f(0, 3.8227f, 4.5131f));
		Main.player.setRot(new Vector3f(0,0,0));
		map.addMesh(FileUtils.loadOBJ("./models/tableMap/Wall_1.obj", "/textures/Fabri14.jpg", new Vector3f(0, 0, 0), new Vector3f(1,1,1), 1,0));// 0
		map.addMesh(FileUtils.loadOBJ("./models/tableMap/lighttest.obj", "/textures/tableMap/Wall Texture.png", new Vector3f(0, 0, 0), new Vector3f(1,1,1), 1,0));// 1
		map.addMesh(FileUtils.loadOBJ("./models/tableMap/Wall_2.obj", "/textures/zinc03.jpg", new Vector3f(0, 0, 0), new Vector3f(1,1,1), 1,0));// 2
		map.addMesh(FileUtils.loadOBJ("./models/tableMap/frameImage.obj", "/textures/ART.JPG", new Vector3f(0, 0, 0), new Vector3f(1,1,1), 1,0));// 3
		map.addMesh(FileUtils.loadOBJ("./models/tableMap/frame.obj", "/textures/common/wood01.jpg", new Vector3f(0, 0, 0), new Vector3f(1,1,1), 10,0));// 4
		map.addMesh(FileUtils.loadOBJ("./models/tableMap/Table.obj", "/textures/common/wood01.jpg", new Vector3f(0,0,0), new Vector3f(1,1,1), 10,0));// 5
		map.addMesh(FileUtils.loadOBJ("./models/testTILE.obj", "/textures/Seamless_marble_tile_texture.jpg", new Vector3f(0,0,0), new Vector3f(1,1,1), 10,0));// 6
		map.addMesh(FileUtils.loadOBJ("./models/tableMap/Table_Top.obj", "/textures/plast09.jpg", new Vector3f(0,0,0), new Vector3f(1,1,1), 10,0));// 7
		map.addMesh(FileUtils.loadOBJ("./models/tableMap/chair.obj", "/textures/common/wood01.jpg", new Vector3f(0,0,0), new Vector3f(1,1,1), 0,0));//8
		map.addMesh(FileUtils.loadOBJ("./models/tableMap/Wall_skirts.obj", "/textures/common/wood01.jpg", new Vector3f(0, 0, 0), new Vector3f(1,1,1), 10,0));// 9
		map.addMesh(FileUtils.loadOBJ("./models/tableMap/lampBase.obj", "/textures/gold.jpg", new Vector3f(0, 0, .0f), new Vector3f(1,1,1), 20,0));// 10
		map.addMesh(FileUtils.loadOBJ("./models/tableMap/lampBody.obj", "/textures/common/wood01.jpg", new Vector3f(0, 0, 0), new Vector3f(1,1,1), 10,0));// 11
		map.addMesh(FileUtils.loadOBJ("./models/tableMap/lampHead.obj", "/textures/paper05.jpg", new Vector3f(0, 0, .0f), new Vector3f(1,1,1), 20,0));// 12
		map.addMesh(FileUtils.loadOBJ("./models/tableMap/Window.obj", "/textures/common/wood01.jpg", new Vector3f(0, 0, .0f), new Vector3f(1,1,1), 20,0));// 13
		map.addMesh(FileUtils.loadOBJ("./models/tableMap/Dresser/GOLD.obj", "/textures/gold.jpg", new Vector3f(0, 0, .0f), new Vector3f(1,1,1), 20,0));// 14
		map.addMesh(FileUtils.loadOBJ("./models/tableMap/Dresser/LAMPSHADE.obj", "/textures/paper05.jpg", new Vector3f(0, 0, .0f), new Vector3f(1,1,1), 20,0));// 15
		map.addMesh(FileUtils.loadOBJ("./models/tableMap/Dresser/PARTICLEBOARD.obj", "/textures/particleBoard.jpg", new Vector3f(0, 0, .0f), new Vector3f(1,1,1), 20,0));// 16
		map.addMesh(FileUtils.loadOBJ("./models/tableMap/Dresser/STOVETOP.obj", "/textures/stoveTop.jpg", new Vector3f(0, 0, .0f), new Vector3f(1,1,1), 20,0));// 17
		map.addMesh(FileUtils.loadOBJ("./models/tableMap/Dresser/TTCEMENTBLUE01.obj", "/textures/TTcementBlue01.jpg", new Vector3f(0, 0, .0f), new Vector3f(1,1,1), 20,0));// 18
		map.addMesh(FileUtils.loadOBJ("./models/tableMap/Dresser/wood.obj", "/textures/wood01.jpg", new Vector3f(0, 0, .0f), new Vector3f(1,1,1), 20,0));// 19
		map.addMesh(FileUtils.loadOBJ("./models/tableMap/Dresser/wood1.obj", "/textures/wood01.jpg", new Vector3f(0, 0, .0f), new Vector3f(1,1,1), 20,0));// 20
		map.addMesh(FileUtils.loadOBJ("./models/tableMap/Dresser/ZINC03.obj", "/textures/zinc03.jpg", new Vector3f(0, 0, .0f), new Vector3f(1,1,1), 20,0));// 21

		map.addMesh(FileUtils.loadOBJ("./models/skybox_01/up.obj", "/textures/skybox_01/sky_up.jpg", new Vector3f(0, 0, 0f), new Vector3f(1,1,1), 20,0));// 22
		map.addMesh(FileUtils.loadOBJ("./models/skybox_01/west.obj", "/textures/skybox_01/sky_west.jpg", new Vector3f(0, 0, 0f), new Vector3f(1,1,1), 20,0));// 13
		map.addMesh(FileUtils.loadOBJ("./models/skybox_01/south.obj", "/textures/skybox_01/sky_south.jpg", new Vector3f(0, 0, 0f), new Vector3f(1,1,1), 20,0));// 13
		map.addMesh(FileUtils.loadOBJ("./models/skybox_01/east.obj", "/textures/skybox_01/sky_east.jpg", new Vector3f(0, 0, 0f), new Vector3f(1,1,1), 20,0));// 13
		map.addMesh(FileUtils.loadOBJ("./models/skybox_01/north.obj", "/textures/skybox_01/sky_north.jpg", new Vector3f(0, 0, 0f), new Vector3f(1,1,1), 20,0));// 13


	}
	public static void create() {
		System.out.println("Creating objects (Phase 2 out of #)");
		map.create();
		for (FullLight element : Main.Lights) {
			element.create();
		}
	}
	public static void destroy() {
		map.destroy();
		Main.table.destroy();
	}
	public static void render(Renderer rnd) {
		for (int i = 0; i < map.getStaticObjectList().size(); i++) {
			if (i >= 22) {
				Main.gr_full_bright = 1;
				map.getStaticObjectList().get(i).setPosition(Main.player.getPos());//the skybox
			}
			rnd.renderMesh(map.getStaticObjectList().get(i), 1);
			Main.gr_full_bright = thi;
		}
		Main.table.renderCards(rnd);
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
