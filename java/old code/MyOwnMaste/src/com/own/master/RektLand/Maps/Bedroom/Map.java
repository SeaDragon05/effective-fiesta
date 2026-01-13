package com.own.master.RektLand.Maps.Bedroom;

import com.own.master.engine.graphics.Renderer;
import com.own.master.engine.math.Vector3f;
import com.own.master.engine.objects.FullLight;
import com.own.master.engine.objects.GameObject;
import com.own.master.engine.utils.FileUtils;

import Main.Main;

public class Map {
	public static com.own.master.engine.objects.Map map = new com.own.master.engine.objects.Map("The_Bedroom");// SHEESH
	public static void load() {
		System.out.println("Loading objects (Phase 1 out of #)");
		Main.player.setPos(new Vector3f(-5.7726f, 17.698f, -3.4345f));
		Main.Lights.add(new FullLight(new Vector3f(-12.782f, 23.214f, -12.759f), new Vector3f(-90,0,0), new Vector3f(1f, 1f, 1f), 2, 2));
		Main.Lights.add(new FullLight(Main.player.getPos(), new Vector3f(0,0,0), new Vector3f(1f, 1f, 1f), 0.7f, 2));//playah light because its blockland rip off lmao
		map.addMesh(FileUtils.loadOBJ("./models/The_Bedroom/bedFrame.obj", "/textures/wood01.jpg", new Vector3f(0, 0, 0), new Vector3f(1,1,1), 10, 1));
		map.addMesh(FileUtils.loadOBJ("./models/The_Bedroom/bedMatress.obj", "/textures/shirt1.jpg", new Vector3f(0, 0, 0), new Vector3f(1,1,1), 1, 0));
		map.addMesh(FileUtils.loadOBJ("./models/The_Bedroom/bedPillow.obj", "/textures/shirt2.jpg", new Vector3f(0, 0, 0), new Vector3f(1,1,1), 1, 1));
		map.addMesh(FileUtils.loadOBJ("./models/The_Bedroom/bedSecret.obj", "/textures/paper05.jpg", new Vector3f(0, 0, 0), new Vector3f(1,1,1), 1, 0.5f));




		map.addMesh(FileUtils.loadOBJ("./models/The_Bedroom/BuildingBase.obj", "/textures/TTcementSand01.jpg", new Vector3f(0, 0, 0), new Vector3f(1,1,1), 1, 1));
		map.addMesh(FileUtils.loadOBJ("./models/The_Bedroom/BuildingCarpet.obj", "/textures/carpet.png", new Vector3f(0, 0, 0), new Vector3f(1,1,1), 1, 1));
		map.addMesh(FileUtils.loadOBJ("./models/The_Bedroom/BuildingCeiling.obj", "/textures/plast09.jpg", new Vector3f(0, 0, 0), new Vector3f(1,1,1), 1, 0));
		map.addMesh(FileUtils.loadOBJ("./models/The_Bedroom/BuildingConcrete.obj", "/textures/ttcementblue01.jpg", new Vector3f(0, 0, 0), new Vector3f(1,1,1), 1, 0.8f));
		map.addMesh(FileUtils.loadOBJ("./models/The_Bedroom/BuildingDoorHandle.obj", "/textures/gold.jpg", new Vector3f(0, 0, 0), new Vector3f(1,1,1), 8, 0.1f));
		map.addMesh(FileUtils.loadOBJ("./models/The_Bedroom/BuildingInnerWalls.obj", "/textures/wall.png", new Vector3f(0, 0, 0), new Vector3f(1,1,1), 5, 0));
		map.addMesh(FileUtils.loadOBJ("./models/The_Bedroom/BuildingOutlets.obj", "/textures/stoveTop.jpg", new Vector3f(0, 0, 0), new Vector3f(1,1,1), 1, 0.5f));
		map.addMesh(FileUtils.loadOBJ("./models/The_Bedroom/BuildingOutterFrame.obj", "/textures/wood01.jpg", new Vector3f(0, 0, 0), new Vector3f(1,1,1), 10, 1));
		map.addMesh(FileUtils.loadOBJ("./models/The_Bedroom/BuildingOutterWalls.obj", "/textures/bricks1.jpg", new Vector3f(0, 0, 0), new Vector3f(1,1,1), 1, 1));
		map.addMesh(FileUtils.loadOBJ("./models/The_Bedroom/BuildingRoof.obj", "/textures/TTasphalt01.jpg", new Vector3f(0, 0, 0), new Vector3f(1,1,1), 1, 1));
		map.addMesh(FileUtils.loadOBJ("./models/The_Bedroom/BuildingSecret1.obj", "/textures/wall.png", new Vector3f(0, 0, 0), new Vector3f(1,1,1), 1, 0));
		map.addMesh(FileUtils.loadOBJ("./models/The_Bedroom/BuildingUnknown1.obj", "/textures/TTasphalt0fsda1.jpg", new Vector3f(0, 0, 0), new Vector3f(1,1,1), 1, 0.3f));
		map.addMesh(FileUtils.loadOBJ("./models/The_Bedroom/BuildingVent.obj", "/textures/zinc03.jpg", new Vector3f(0, 0, 0), new Vector3f(1,1,1), 1, 0.1f));




		map.addMesh(FileUtils.loadOBJ("./models/The_Bedroom/dresserDisplay.obj", "/textures/stoveTop.jpg", new Vector3f(0, 0, 0), new Vector3f(1,1,1), 3,0.1f));
		map.addMesh(FileUtils.loadOBJ("./models/The_Bedroom/dresserHandles.obj", "/textures/zinc03.jpg", new Vector3f(0, 0, 0), new Vector3f(1,1,1), 2,0));
		map.addMesh(FileUtils.loadOBJ("./models/The_Bedroom/dresserInnerSecrets.obj", "/textures/wood01.jpg", new Vector3f(0, 0, 0), new Vector3f(1,1,1), 10,1));
		map.addMesh(FileUtils.loadOBJ("./models/The_Bedroom/dresserLampMetal.obj", "/textures/gold.jpg", new Vector3f(0, 0, 0), new Vector3f(1,1,1), 15,0.5f));
		map.addMesh(FileUtils.loadOBJ("./models/The_Bedroom/dresserLampOutterShade.obj", "/textures/lampshade.jpg", new Vector3f(0, 0, 0), new Vector3f(1,1,1), 1,0));
		map.addMesh(FileUtils.loadOBJ("./models/The_Bedroom/dresserLampShade.obj", "/textures/paper05.jpg", new Vector3f(0, 0, 0), new Vector3f(1,1,1), 1,0));
		map.addMesh(FileUtils.loadOBJ("./models/The_Bedroom/dresserMetal.obj", "/textures/TTcementBlue01.jpg", new Vector3f(0, 0, 0), new Vector3f(1,1,1), 15,0.4f));
		map.addMesh(FileUtils.loadOBJ("./models/The_Bedroom/dresserWood.obj", "/textures/wood01.jpg", new Vector3f(0, 0, 0), new Vector3f(1,1,1), 10, 1));

		map.addMesh(FileUtils.loadOBJ("./models/The_Bedroom/Poster.obj", "/textures/wcdi.png", new Vector3f(0, 0, 0), new Vector3f(1,1,1), 1, 0));




	}
	public static void create() {
		System.out.println("Creating objects (Phase 2 out of #)");
		map.create();
	}
	public static void destroy() {
		map.destroy();
	}
	public static void render(Renderer rnd) {
		Main.Lights.get(1).setPosition(Main.player.getPos());
		for (GameObject element : map.getStaticObjectList()) {
			rnd.renderMesh(element, 1);
		}
	}
	public static void createObjects() {
		System.out.println("Creating game objects (Phase 3 out of #)");
		for (int i = 0; i < map.getMeshes().size(); i++) {//render the static objects
			map.addStaticObjectList(new GameObject(new Vector3f(0,0,0), new Vector3f(0,0,0), new Vector3f(1,1,1), map.getMesh(i)));
		}
	}
}
