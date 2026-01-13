package main;

import org.lwjgl.glfw.GLFW;
import org.lwjglx.util.vector.Vector3f;
import org.lwjglx.util.vector.Vector4f;

import com.engine.audio.AudioMaster;
import com.engine.font.TextMaster;
import com.engine.graphics.ParticleMaster;
import com.engine.interfaces.Game;
import com.engine.io.Input;
import com.engine.objects.Camera;
import com.engine.objects.CubeMap;
import com.engine.objects.Entity;
import com.engine.objects.Player;
import com.engine.objects.TexturedModel;
import com.engine.postProcess.PostProcessing;
import com.engine.renderer.MasterRenderer;
import com.engine.utils.MasterLoader;

import games.trainSim.base.Track;
import games.trainSim.base.TrainData;

public class LowRamTest implements Runnable, Game {
	/*
	 * This class is used for the Nintendo Wii version of this engine. It has a hard
	 * limit of 64 megabytes of ram. The Nintendo Wii has a total of 88 MB of ram
	 * for the entire system. This ram is divided into two sections: MEM1 and MEM2
	 * MEM1 has 24 megabytes in total, and is the fastest ram. MEM2 has 64 megabytes
	 * in total, and is typically used for non gpu related items. The OS and other
	 * system functions will typically take about 20 MB's of ram, allowing only 64
	 * minimum for the entire game. Since my C++ compiler isn't fully set up atm,
	 * this will have to do for the testing of resources.
	 */
	public static void main(String[] args) {
		System.out.println("Test");
		new LowRamTest().start();
	}

	static String title = "64MBTest";
	static Camera camera = null;
	static CubeMap map = null;
	static TrainData testWagon = null;

	@Override
	public void start() {
		Engine.MainThread.thread = new Thread(this, title);
		Engine.MainThread.thread.start();
	}

	public static void createGameObjects() {
		camera = new Camera(new Vector3f(0, 0, 0), new Vector3f(0, 0, 0));
		camera.setPlayer(new Player(null, null, 0, 0, 0, 0));
		camera.getPlayer().setPosition(new Vector3f(0, 0, 0));
		camera.setPosition(new Vector3f(0, 5, 5));
		camera.setPitch(20);
		camera.setYaw(0);
		
	}

	public void close() {
		Engine.println("Game is closing", Engine.color_red);
		Engine.print("Destroying audio...", Engine.color_yellow);
		try {
			AudioMaster.cleanUp();
			Engine.println(" Success", Engine.color_green);
		} catch (Exception e) {
			Engine.println(" Not set, not executing", Engine.color_cyan);
		}
		Engine.print("Destroying canvas...", Engine.color_yellow);
		Engine.window.destroy();
		Engine.println(" Success", Engine.color_green);
		Engine.print("Destroying all loaded objects...", Engine.color_yellow);
		MasterLoader.cleanUp();
		Engine.println(" Success", Engine.color_green);

		Engine.print("Destroying GUIs...", Engine.color_yellow);
		try {
			Engine.guiRenderer.cleanUp();
			Engine.println(" Success", Engine.color_green);
		} catch (Exception e) {
			Engine.println(" Not set, not executing", Engine.color_cyan);
		}

		Engine.print("Destroying renderer...", Engine.color_yellow);
		if (Engine.renderer != null) {
			Engine.renderer.cleanUp();
			Engine.println(" Success", Engine.color_green);
		} else {
			Engine.println(" Not set, not executing", Engine.color_cyan);
		}

		Engine.print("Destroying post process...", Engine.color_yellow);
		if (PostProcessing.isSetup) {
			PostProcessing.cleanUp();
			Engine.println(" Success", Engine.color_green);
		} else {
			Engine.println(" Not set, not executing", Engine.color_cyan);
		}

		Engine.print("Destroying particle system...", Engine.color_yellow);
		if (ParticleMaster.isSetup) {
			ParticleMaster.cleanUp();
			Engine.println(" Success", Engine.color_green);
		} else {
			Engine.println(" Not set, not executing", Engine.color_cyan);
		}

		Engine.print("Destroying everything else...", Engine.color_yellow);
		try {
			TextMaster.cleanUp();
			Engine.println(" Success", Engine.color_green);
		} catch (Exception e) {
			Engine.println(" Not set, not executing", Engine.color_cyan);
		}
		Engine.println("This is farewell", Engine.color_white);
		System.exit(0);
	}

	@Override
	public void run() {
		Game.init(null);
		Engine.window.setTitle(title);
		createGameObjects();
		loadStuff();
		Engine.finishLoading();
		while (!Engine.window.shouldClose() && !Input.isKeyDown(GLFW.GLFW_KEY_ESCAPE)) {
			// game stuff
			render();
			Game.update();
		}
		close();
	}

	public static void loadStuff() {
		// debug tool
		// this model is located at point 0,0,0, and points in each direction once

		TexturedModel dev_tool_origin = MasterLoader.loadTexturedModel("origin", "null");
		// add the entity to the render list
		Engine.entities.add(new Entity(dev_tool_origin, 0, new Vector3f(0, 0, 0), 0, 0, 0, 10));
		

		//testWagon = new TrainData();
		
		Track track = Track.createRandomizedTrack(30, new Vector3f(0,0,0));
		
		
		
		map = new CubeMap(new String[] { "textures/missing", "textures/missing", "textures/missing", "textures/missing",
				"textures/missing", "textures/missing" });
		Engine.renderer = new MasterRenderer(camera, map);

	}

	public static void render() {
		camera.move();// update the camera
		Engine.renderer.renderScene(Engine.entities, Engine.terrains, Engine.lights, camera, new Vector4f(0, 0, 0, 0));
	}

}
