package main;

/*
 * This class file is an example of how to use the game engine.
 * If there are any unclear methods or unsurities, note that this
 * engine is based on ThinMatrix's YouTube tutorials.
 * There, please watch any tutorials on any subject that you are unclear on.
 * The file system isn't the same, but is similar in class names.
 * Most, if not all methods are the same. There are added methods in some classes.
 * 
 * The main class is not this file, rather all the essential objects are declared in a class file named "Engine"
 * This class file contains global variables that can be adjusted via configuration file, or the game class such as this class.
 * 
 */
//da impooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooorts!
import java.io.File;

import org.lwjgl.glfw.GLFW;
import org.lwjglx.util.vector.Vector2f;
import org.lwjglx.util.vector.Vector3f;
import org.lwjglx.util.vector.Vector4f;

import com.engine.audio.AudioMaster;
import com.engine.audio.Source;
import com.engine.font.FadedTextMaster;
import com.engine.font.FontType;
import com.engine.font.GUIText;
import com.engine.font.TextMaster;
import com.engine.graphics.GuiTexture;
import com.engine.graphics.ParticleMaster;
import com.engine.graphics.ParticleTexture;
import com.engine.graphics.TerrainTexture;
import com.engine.graphics.TerrainTexturePack;
//import com.engine.graphics.WaterFrameBuffers;
//import com.engine.graphics.WaterShader;
//import com.engine.graphics.WaterTile;
import com.engine.interfaces.Game;
import com.engine.objects.Camera;
import com.engine.objects.CubeMap;
import com.engine.objects.Entity;
import com.engine.objects.ParticleSystem;
import com.engine.objects.Player;
import com.engine.objects.Popup;
import com.engine.objects.Terrain;
import com.engine.objects.TexturedModel;
import com.engine.postProcess.Fbo;
import com.engine.postProcess.PostProcessing;
import com.engine.renderer.GuiRenderer;
import com.engine.renderer.MasterRenderer;
//import com.engine.renderer.WaterRenderer;
import com.engine.utils.MasterLoader;

import games.trainSim.base.ReTrack;
import games.trainSim.base.TrainData;
import games.trainSim.base.TrainLoader;

import com.engine.io.Input;

public class EngineTest implements Runnable, Game {
	//static Loader loader = new Loader();
	static ParticleTexture PT;// = new ParticleTexture(loader.loadTexture("smoke"), 8);
	public static ParticleSystem system;// = new ParticleSystem(PT, 10, 1, -0.6f, 4, 5);
	static int HT2;// = AudioMaster.loadSound("sound/music/horror/horrortexture2.wav");
	public static Terrain terrain;
	public static Terrain terrain2;
	static int TA3;
	static int TA4;
	static int TA5;
	static int TA6;
	static int TA7;
	static int EN1, EN2, EN3;
	static int GS1, GS2, GS3;
	static int M1, M2, M3, M4, M5;
	static int INFO1, INFO2, INFO3, INFO4;
	static long time, engineTime;
	static Popup subtitles;
	static Popup hudInfo;
	static Source source1, source2, hud, engine;// = new Source(); NO SOURCE
	// these variables keep the scripts running in such a way that makes sense.
	// the main game script is ran many times a second, so we use these to excecute
	// certain bits of code only once when
	// a condition is met. Also think of it as leap frogging in a way
	static boolean is0 = false, is1 = false, is2 = false, is3 = false, is4 = false, is5 = false, is6 = false,
			is7 = false, is8 = false, is9 = false;// [is] okay
	static boolean if0 = false, if1 = false, if2 = false, if3 = false, if4 = false;// is if ok
	static boolean ic0 = false, ic1 = false, ic2 = false, ic3 = false, ic4 = false, ic5 = false;// is credits ok
	static GUIText subs, hudSubs;
	static GuiTexture help1, help2;
	static TrainData testTrain;
	
	public static EngineTest process;

	public static void main(String[] args) throws Exception {
		// print system information so that debugging and issue fixing can be easier
		// "IT WONT RUN PLZ FIX"!!!
		//System.out.println("\n\nSystem information:");
		//Sigar sigar = new Sigar();
		//org.hyperic.sigar.CpuInfo[] cpuInfoList = sigar.getCpuInfoList();
		//System.out.println("CPU Model: " + cpuInfoList[0].getModel());
		///System.out.println("Core count: " + Runtime.getRuntime().availableProcessors());
		//System.out.println("Free memory: " + Runtime.getRuntime().freeMemory());
		//long maxMemory = Runtime.getRuntime().maxMemory();
		//System.out.println("Maximum memory: " + (maxMemory == Long.MAX_VALUE ? "no limit (WOW)" : maxMemory));
		//System.out.println("Total memory available to JVM: " + Runtime.getRuntime().totalMemory());
		process = new EngineTest();//.start();// creates the main rendering and game thread
		process.start();
		
	}

	public void start() {
		Engine.MainThread.thread = new Thread(this, "game");
		Engine.MainThread.thread.start();
	}

	@Override
	public void run() {
		//Engine.printSystemInformation();
		Game.init(null);
		GuiRenderer guiRenderer = new GuiRenderer();
		TextMaster.init();
		FontType font = new FontType(MasterLoader.loadTexturePointer("segoeUI"), new File("res/segoeUI.fnt"));
		GUIText LM1 = new GUIText("Loading...", 3, font, new Vector2f(0, 0.01f), 1, false);
		LM1.setColour(0, 0, 0);
		GUIText LM2 = new GUIText("Status:", 1, font, new Vector2f(0, 0.1f), 1, false);
		LM2.setColour(0, 0, 0);

		GuiTexture loading = new GuiTexture(MasterLoader.loadTexturePointer("loadingScreen1024"), new Vector2f(0f, 0f),
				new Vector2f(1f, 1f));
		Engine.guis.add(loading);

		guiRenderer.render(Engine.guis);
		TextMaster.render();
		Game.update();

		int t1 = MasterLoader.loadTexturePointer("null");
		int t2 = MasterLoader.loadTexturePointer("null");
		System.out.println(t1 == t2);
		
		System.out.println("--Creating game Entities--");
		TerrainTexture ground = new TerrainTexture(MasterLoader.loadTexturePointer(""));
		TerrainTexturePack tts = new TerrainTexturePack(ground, ground, ground, ground);
		terrain = new Terrain(0, 0, tts, ground, "heightmap");
		TexturedModel hangar = MasterLoader.loadTexturedModel("hangar2v2", "tank1texture");
				//new TexturedModel(OBJLoader.loadObjModel("hangar2v2", loader),
				//new ModelTexture(loader.loadTexture("tank1texture")));
		//add the entity to the render list
		Engine.entities.add(new Entity(hangar, 0, new Vector3f(0, 0f, -16), 0, 0, 0, 1));//terrain
		//Engine.entities.get(Engine.entities.size() - 1).setDrawDistanceEnabled(false);
		TexturedModel hangarTerrain = MasterLoader.loadTexturedModel("hangar1terrainHuge", "snow01");
				//new TexturedModel(OBJLoader.loadObjModel("hangar1terrainHuge", loader),
				//new ModelTexture(loader.loadTexture("snow01")));
		//texture things:
		hangarTerrain.getTexture().setReflectivity(0.6f);
		hangarTerrain.getTexture().setRoughness(0.2f);
		hangarTerrain.getTexture().setReflectiveMap(MasterLoader.loadTexturePointer("textures/snow/specular"));
		//add the entity to the render list
		//Engine.entities.add(new Entity(hangarTerrain, 0, new Vector3f(0, 0, -16), 0, 180, 0, 1));
		//Engine.terrains.add(terrain);
		System.out.println("Font loaded");
		System.out.println("Adding lights");
		Engine.lights.add(Engine.sun);//0
		//Engine.sun.setPosition(new Vector3f(22443, 698, 25311));
		//Engine.sun.setColor(new Vector3f(0.7f,0.1f,0.1f));
		//Engine.lights.get(0).setColor(new Vector3f(0.2f,0.2f,0.2f));
		Engine.lights.add(Engine.trainFrontLight);//1
		//Engine.lights.add(Engine.trainCabinLight);//2
		//Engine.trainCabinLight.setColor(new Vector3f(1,1,1));

		Engine.sun.setPosition(new Vector3f(22443, 698, 22443));
		Engine.sun.setColor(new Vector3f(0.44f, 0.2f, 0.2f));
		int a = 2;
		Engine.sun.setColor(new Vector3f(Engine.sun.getColor().x *= a,Engine.sun.getColor().y *= a,Engine.sun.getColor().z *= a));
		//Engine.trainFrontLight.setColor(new Vector3f(5,5,5));
		//Engine.lights.add(new Light(new Vector3f(-25, 55, 250), new Vector3f(3f,2.6f,2.4f), new Vector3f(0.05f, 0.05f, 0.04f)));
		//Engine.lights.add(new Light(new Vector3f(-25, 55, 350), new Vector3f(3f,2.7f,2.455f), new Vector3f(0.05f, 0.05f, 0.04f)));
		//Engine.lights.add(new Light(new Vector3f(45, 55, 250), new Vector3f(3f,2.6f,2.4f), new Vector3f(0.05f, 0.05f, 0.04f)));
		//Engine.lights.add(new Light(new Vector3f(45, 55, 350), new Vector3f(3f,2.7f,2.455f), new Vector3f(0.05f, 0.05f, 0.04f)));
		
		//Engine.lights.get(1).setColor(new Vector3f(3,3,3));
		GUIText LM3 = new GUIText("Lights added", 1, font, new Vector2f(0, 0.15f), 1, false);
		LM3.setColour(0, 0, 0);
		guiRenderer.render(Engine.guis);
		TextMaster.render();
		Game.update();
		Player player = new Player(null, new Vector3f(0, 0, 0), 0, 0, 0, 1f);
		
		GUIText LM4 = new GUIText("Train model loaded", 1, font, new Vector2f(0, 0.2f), 1, false);
		LM4.setColour(0, 0, 0);
		guiRenderer.render(Engine.guis);
		TextMaster.render();
		Game.update();
		TexturedModel detail2 = MasterLoader.loadTexturedModel("mapDetail1", "gameModels/objects/roughness512");
				//new TexturedModel(OBJLoader.loadObjModel("mapDetail1", loader),
				//new ModelTexture(loader.loadTexture("gameModels/objects/output512")));
		detail2.getTexture().setReflectiveMap(MasterLoader.loadTexturePointer("gameModels/objects/roughness512"));
		detail2.getTexture().setRoughMap(MasterLoader.loadTexturePointer("gameModels/objects/roughness512"));
		
		//add the entity to the render list
		Engine.entities.add(new Entity(detail2, 0, new Vector3f(0, 0f, 0), 0, 0, 0, 1));
		ReTrack track = new ReTrack("res/track/test");
		//Track track = Track.createTestTrack(new Vector3f(0,0,0), loader);
		Camera camera = new Camera(player);
		//CubeMap map = new CubeMap(new String[] { "right", "left", "top", "bottom", "front", "back" }, loader);
		//CubeMap map = new CubeMap(new String[] { "gameModels/theSnowDesert/reflectionMap/test_right", "gameModels/theSnowDesert/reflectionMap/test_left", "gameModels/theSnowDesert/skyBox/top", "gameModels/theSnowDesert/skyBox/bottom", "gameModels/theSnowDesert/reflectionMap/test_front", "gameModels/theSnowDesert/reflectionMap/test_back" }, loader);
		/*CubeMap map1 = new CubeMap(new String[] { 
			"skybox/bright_sunset/right",//right - back
			"skybox/bright_sunset/left", //left - front
			"skybox/bright_sunset/top",	//same as
			"skybox/bright_sunset/bottom", //same as
			"skybox/bright_sunset/front",//front - right
			"skybox/bright_sunset/back" //back - left
			});*/
		CubeMap map = new CubeMap(new String[] { 
			"skybox/cloudy/0004",//right - back
			"skybox/cloudy/0002", //left - front
			"skybox/cloudy/0005",	//same as
			"skybox/cloudy/0006", //same as
			"skybox/cloudy/0001",//front - right
			"skybox/cloudy/0003" //back - left
		});
		MasterRenderer renderer = new MasterRenderer(camera, map);
		ParticleMaster.init(renderer.getProjectionMatrix());
		//Particle smoke = new Particle(new ParticleTexture(loader.loadTexture("smoke"), 8), new Vector3f(0,0,0), new Vector3f(0,10,0), 1, 80, 10, 1);
		//ParticleTexture smoke = new ParticleTexture(MasterLoader.loadTexturePointer("smoke"), 8);
		//ParticleMaster.addParticle(smoke);
		AudioMaster.init();
		GUIText LM5 = new GUIText("World loaded", 1, font, new Vector2f(0, 0.25f), 1, false);
		LM5.setColour(0, 0, 0);
		guiRenderer.render(Engine.guis);
		TextMaster.render();
		renderer.updateProjectionMatrix();
		Game.update();
		PT = new ParticleTexture(MasterLoader.loadTexturePointer("smoke"), 8); 
		system = new ParticleSystem(PT, 80, 1, -0.6f, 1, 20);
		system.setDirection(new Vector3f(0,6,0), 9);
		system.setScaleError(1);
		system.setLifeError(2);
		system.randomizeRotation();
		Fbo multisampledFbo = new Fbo(Engine.window.getWidth(), Engine.window.getHeight(), Fbo.DEPTH_RENDER_BUFFER, true);
		Fbo outputFbo = new Fbo(Engine.window.getWidth(), Engine.window.getHeight(), Fbo.DEPTH_TEXTURE, false);
		Fbo fbo = new Fbo(Engine.window.getWidth(), Engine.window.getHeight(), Fbo.DEPTH_RENDER_BUFFER, true);
		PostProcessing.init();
		testTrain = new TrainLoader(150).createTestTrain(4096, 0);//that worked
		testTrain.setAssignedLine(track);
		testTrain.getCar(0).setPlayer(player);
		testTrain.addTrainObject(TrainLoader.test_createTrainObject(testTrain, 0, 4096, 0));
		//testWagon.getCar(0).getBody().getModel().getTexture().setNormalMap(loader.loadTexture("/gameModels/ALCO_M-K_PA-4/normals"));
		//testWagon.getCar(0).getBody().getModel().getTexture().setNormalAmount(0);
		
		TexturedModel dev_tool_origin = MasterLoader.loadTexturedModel("origin", "tank1texture");
				//new TexturedModel(OBJLoader.loadObjModel("origin", loader),
				//new ModelTexture(loader.loadTexture("tank1texture")));
		//add the entity to the render list
		Engine.entities.add(new Entity(dev_tool_origin, 0, new Vector3f(0, 0, 0), 0, 0, 0, 10));
		
		float distance = 250.0f;
		//Source music = AudioMaster.newSoundSource(1, 1, 1, true, new Vector3f(0, 0, 0));
		//music.play(HT2);
		float speed = 0.0f;
		GUIText LM6 = new GUIText("World Entities loaded", 1, font, new Vector2f(0, 0.3f), 1, false);
		LM6.setColour(0, 0, 0);
		guiRenderer.render(Engine.guis);
		TextMaster.render();
		renderer.updateProjectionMatrix();
		Game.update();
		Engine.guis.remove(0);
		TextMaster.remove(LM1);
		TextMaster.remove(LM2);
		TextMaster.remove(LM3);
		TextMaster.remove(LM4);
		TextMaster.remove(LM5);
		TextMaster.remove(LM6);
		System.out.println("Creating GUI");
		source1 = AudioMaster.newSoundSource(1, 1, 1, false, new Vector3f(0, 0, 0));
		source1.setLooping(false);
		source2 = AudioMaster.newSoundSource(1, 1, 1, false, new Vector3f(0, 0, 0));
		source2.setLooping(false);
		//Source stationAmbience = AudioMaster.newSoundSource(1, 5, 3, true, new Vector3f(0, 10, 0));
		//stationAmbience.play(TA4);
		engine = AudioMaster.newSoundSource(1, 1, 1, true, new Vector3f(0, 0, 0));
		hud = AudioMaster.newSoundSource(1, 1, 1, false, new Vector3f(0, 0, 0));
		hud.setLooping(false);
		time = System.currentTimeMillis();
		//source1.play(INFO1);
		Engine.finishLoading();
		float lastSpeed = 0;
		//float aiTrainSpeed = 0.45f;
		//float aiTrainDistance = 150;
		float maxSpeed = (5280 / 12.01f) * 40;
		System.out.println("Starting..,");
		while (!Engine.window.shouldClose() && !Input.isKeyDown(GLFW.GLFW_KEY_ESCAPE)) {
			//aiTrainDistance += aiTrainSpeed;
			//thing.update(aiTrainDistance, aiTrainSpeed);
			distance += speed * 0.1f;
			ParticleMaster.update();
			testTrain.update(distance, speed);
			camera.move();
			Vector3f parLoc = new Vector3f(testTrain.getCar(0).getPosition());
			parLoc.y += 17;
			system.generateParticles(parLoc);
			//system.setDirection(testWagon.getCar(0).getWheels().getRotation(), speed);
			if (Input.isKeyDown(GLFW.GLFW_KEY_W)) {
				speed += 0.1f;
				lastSpeed = speed * 2;
				//new Particle(smoke, testWagon.getCar(0).getBody().getPosition(), new Vector3f(0,0,0), 1, 4, 0, 20);
			} else if (Input.isKeyDown(GLFW.GLFW_KEY_S)) {
				speed -= 0.1f;
			} else if (Input.isKeyDown(GLFW.GLFW_KEY_SPACE)) {// e brake
				if (speed <= -0.001) {
					speed += 0.2f;
				} else if (speed >= 0.001) {
					speed -= 0.2f;
				}
			} else {
				if (speed < 0) {
					speed += 0.0004f;
				} else if (speed > 0) {
					speed -= 0.0004f;
				}
			}
			if (speed > maxSpeed) {
				speed = maxSpeed;
			} else if (speed < -maxSpeed) {
				speed = -maxSpeed;
			}
			if (Input.isKeyDown(GLFW.GLFW_KEY_T)) {
				System.out.println("index distance: "
						+ testTrain.getCar(0).getAssignedLine().getTrackFromDistance(distance).getIndex()
						+ "\ntrue distance: " + distance);
			}
			//if (Input.isKeyDown(GLFW.GLFW_KEY_L) || Engine.window.isResized()) {
			renderer.updateProjectionMatrix();
			//}
			if (testTrain.getCar(0).isEngineOn()) {
				engine.setPitch((float) ((speed * speed) / (0.2f * lastSpeed)) + 0.5f);
				if (Input.isKeyDown(GLFW.GLFW_KEY_W)) {
					engine.setPitch((float) (Math.sqrt(speed) / 0.56568542f) + 0.5f);
				}
				if (Input.isKeyDown(GLFW.GLFW_KEY_F)) {
					System.out.println(lastSpeed);
				}
			}
			renderer.renderShadowMap(Engine.entities, camera, Engine.sun);
			fbo.bindFrameBuffer();
			renderer.renderScene(Engine.entities, Engine.terrains, Engine.lights, camera, new Vector4f(0, 0, 0, 0));
			ParticleMaster.renderParticles(camera, renderer.getProjectionMatrix(), Engine.lights);
			fbo.unbindFrameBuffer();
			PostProcessing.doPostProcessing(fbo);
			guiRenderer.render(Engine.guis);
			FadedTextMaster.update();
			TextMaster.render();
			Game.update();
		}
		AudioMaster.cleanUp();
		fbo.cleanUp();
		PostProcessing.cleanUp();
		outputFbo.cleanUp();
		multisampledFbo.cleanUp();
		TextMaster.cleanUp();
		guiRenderer.cleanUp();
		renderer.cleanUp();
		MasterLoader.cleanUp();
		ParticleMaster.cleanUp();
		close();
		System.exit(0);
	}

	public void close() {
		Engine.window.destroy();
	}
	
}
