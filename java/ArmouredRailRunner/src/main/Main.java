package main;

import java.io.File;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.openal.AL10;
import org.lwjgl.openal.AL11;
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
import com.engine.interfaces.Game;
import com.engine.io.Input;
import com.engine.objects.Camera;
import com.engine.objects.CubeMap;
import com.engine.objects.Entity;
import com.engine.objects.Light;
import com.engine.objects.ParticleSystem;
import com.engine.objects.Player;
import com.engine.objects.Popup;
import com.engine.objects.TexturedModel;
import com.engine.postProcess.Fbo;
import com.engine.postProcess.PostProcessing;
import com.engine.renderer.GuiRenderer;
import com.engine.renderer.MasterRenderer;
import com.engine.utils.MasterLoader;

import games.trainSim.base.ReTrack;
import games.trainSim.base.TrainData;
import games.trainSim.base.TrainLoader;

public class Main implements Runnable, Game {
	/**
	 * main method, just a simple line that starts the game thread. nothing special
	 * about this other than its the game starter kind of like a small gas engine
	 * starter to a large diesel engine
	 */
	public static void main(String[] args) {
		new Main().start();
	}

	/**
	 * engine objects:
	 */
	static Player player;
	static Camera camera;
	static FontType font;
	static CubeMap map;
	static Fbo fbo;
	/**
	 * game specific objects:
	 */
	// audio:
	static int AM0, LM0, LM1, INFO0, INFO1, INFO2, GM0, GM1, UI0, EN0, EN1, ENG_ON1, ENG_ON2, ENG_ON3, MM0, MM1,
			testThing;
	static float pos = 0.2f;
	static TrainData train;
	static float speed = 0, lastSpeed = 0;
	static float distance = 250f;
	static Popup subtitles;
	static Popup hudInfo;
	static Source MenuMusic, loadingMusic, source1, source2, hud, engine, engine2, engine3, engine4, testSound,
			ambient0;
	static ReTrack track;
	static Light sun;
	static ParticleSystem trainSmoke, trainSnow;
	static ParticleTexture smoke, trackSnow, weatherSnow;// = new ParticleTexture(loader.loadTexture("smoke"), 8);
	static GUIText speedIndicator;
	static int fpsCount = 0;
	static Light railLight;
	static Light railLight2;
	/*
	 * these variables keep the scripts running in such a way that makes sense. the
	 * main game script is ran many times a second, so we use these to excecute
	 * certain bits of code only once when a condition is met. Also think of it as
	 * leap frogging
	 */
	static long time, engineTime;// time vars
	static boolean is0 = false, is1 = false, is2 = false, is3 = false, is4 = false, is5 = false, is6 = false,
			is7 = false, is8 = false, is9 = false;// is ok
	static boolean if0 = false, if1 = false, if2 = false, if3 = false, if4 = false;// is if ok
	static boolean ic0 = false, ic1 = false, ic2 = false, ic3 = false, ic4 = false, ic5 = false, ic6 = false,
			ic7 = false, ic8 = false;// is credits ok
	static boolean fa0 = false, fa1 = false, fa2 = false;

	/**
	 * @method run main game script and loop prints system information for debugging
	 *         (VERY IMPORTANT INFO FOR BUG REPORTS) sets up engine runs until the
	 *         user exits the game calls the close method to clean up objects when
	 *         exiting stops all threads when closed
	 */
	@Override
	public void run() {
		// Engine.printSystemInformation();
		Game.init(null);
		Engine.window.setTitle("Armoured Rail Runner");
		// this function needs to be called first so that we can do certain things
		// whilst loading the game
		setupEngineUtils();
		// display the main menu
		displayMenu();
		AudioMaster.init();
		AL10.alDistanceModel(AL11.AL_LINEAR_DISTANCE_CLAMPED);
		MM0 = AudioMaster.loadSound("sound/music/menu/horrorscape.wav");
		MenuMusic = AudioMaster.newSoundSource(1, 1, 1, true, new Vector3f());
		MenuMusic.play(MM0);
		// print the report
		Engine.finishLoading();// This only prints data, and does not effect anything else
		while (!Input.isKeyDown(GLFW.GLFW_KEY_ENTER)) {
			// update();
			updateLoadingScreen();// using the same functions again wow
		}
		MenuMusic.stop();
		// clear the screen
		clearLoadingScreen();
		// create audio bois
		setupAudio();
		// create the players camera
		setupCamera();
		// init the rendering system
		setupGraphicsStuff();
		// load world
		createGameObjects();
		// create train object
		createTrain();
		// load lights
		createLights();
		// clear the loading screen so that it doesn't over lap
		clearLoadingScreen();
		// print the report
		Engine.finishLoading();// This only prints data, and does not effect anything else
		// do some <pre>-loop stuff
		preLoopStuff();
		while (!Engine.window.shouldClose() && !Input.isKeyDown(GLFW.GLFW_KEY_ESCAPE)) {
			if (loadingMusic.getVolume() > 0) {
				loadingMusic.setVolume(loadingMusic.getVolume() - 0.003f);
			} else {
				loadingMusic.stop();
			}
			gameControls();
			Gevents(font, distance);
			update();
			updateParticles();
			render();

			if (Input.isKeyDown(GLFW.GLFW_KEY_L)) {
				Engine.renderer.updateProjectionMatrix();
				System.out.println("Woow");
			}
		}
		close();
		System.exit(0);
	}

	/**
	 * @method start() starts the main game thread titled "ARR"
	 */
	@Override
	public void start() {
		Engine.MainThread.thread = new Thread(this, "main_thread");
		Engine.MainThread.thread.start();
	}

	/**
	 * @method close() closes objects within the game engine. this is memory
	 *         management in java. Wow.
	 *        l i n e s
	 */
	public void close() {
		Engine.println("Game is closing", Engine.color_red);
		Engine.print("Destroying audio...", Engine.color_yellow);
		AudioMaster.cleanUp();
		Engine.println(" Success", Engine.color_green);
		Engine.print("Destroying canvas...", Engine.color_yellow);
		Engine.window.destroy();
		Engine.println(" Success", Engine.color_green);
		Engine.print("Destroying all loaded objects...", Engine.color_yellow);
		MasterLoader.cleanUp();
		Engine.println(" Success", Engine.color_green);
		Engine.print("Destroying FBOs...", Engine.color_yellow);
		if (fbo != null) {
			fbo.cleanUp();
			Engine.println(" Success", Engine.color_green);
		} else { 
			Engine.println(" Not set, not executing", Engine.color_cyan);
			}
		Engine.print("Destroying GUIs...", Engine.color_yellow);
		Engine.guiRenderer.cleanUp();
		Engine.println(" Success", Engine.color_green);
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
		TextMaster.cleanUp();
		Engine.println(" Success", Engine.color_green);
		Engine.println("This is farewell", Engine.color_white);
		System.exit(0);
	}

	/**
	 * update just updates stuff that needs it every loop such updates can be audio,
	 * enviroment, etc
	 */
	public static void update() {
		AudioMaster.setListenderData(camera.getPosition(), camera.getRotation());
		engine.setPosition(train.getCar(0).getPosition());
		// testSound.setPosition(train.getCar(0).getPosition());
	}

	/**
	 * render function contains all render calls to display an image on screen
	 * renders the shadow map first, then renders the scene, then renders guis and
	 * text updates the buffers
	 */
	public static void render() {
		// actual rendering process goes brrrrrrrrrrr:
		camera.move();// update the camera
		Engine.renderer.renderShadowMap(Engine.entities, camera, Engine.sun);// woo
		ParticleMaster.update();
		fbo.bindFrameBuffer();
		Engine.renderer.renderScene(Engine.entities, Engine.terrains, Engine.lights, camera, new Vector4f(0, 0, 0, 0));
		ParticleMaster.renderParticles(camera, Engine.renderer.getProjectionMatrix(), Engine.lights);
		fbo.unbindFrameBuffer();
		// render to the screen stuff:
		PostProcessing.doPostProcessing(fbo);
		// gui items:
		updateSpeed();
		Engine.guiRenderer.render(Engine.guis);
		FadedTextMaster.update();
		TextMaster.render();
		// update the game:
		Game.update();
	}

	public static void updateSpeed() {
		try {
			speedIndicator.remove();
		} catch (Exception e) {
		}
		speedIndicator = new GUIText("Speed: " + speed, 1, font, new Vector2f(0.10f, 0.05f), 0.4f, false);
		speedIndicator.setColour(1, 1, 1);
	}

	public void updateParticles() {
		Vector3f parLoc = new Vector3f(train.getCar(0).getPosition()),
				trLoc = new Vector3f(train.getCar(0).getPosition());
		parLoc.y += 17;
		if (train.getCar(0).isEngineOn()) {
			trainSmoke.generateParticles(parLoc);
		}
		if (speed > 0.3f) {
			trainSnow.generateParticles(trLoc);
		}
	}

	/**
	 * updates the loading screen when called
	 */
	public void updateLoadingScreen() {
		try {
			Engine.renderer.updateProjectionMatrix();
		} catch (Exception e) {
		}
		Engine.guiRenderer.render(Engine.guis);
		TextMaster.render();
		Game.update();
		if (Engine.window.shouldClose()) {
			close();
		}
	}

	/**
	 * init all the audios that will be used in the game can create another method
	 * to use this at a later time each variable is an int value which is a
	 * 'pointer' to the audio memory location
	 */
	public void setupAudio() {
		loadInfo("Creating sound sources and initializing sound objects");
		AM0 = AudioMaster.loadSound("sound/nature/ambient5.wav");
		Engine.TA0 = AudioMaster.loadSound("sound/common/04-rails.wav");
		Engine.TA1 = AudioMaster.loadSound("sound/ambient/creak4Mono.wav");
		LM0 = AudioMaster.loadSound("sound/music/348275__devern__fantasy-orchestra.wav");
		LM1 = AudioMaster.loadSound("sound/copyrighted/music/Project Borealis OST 5. f(R) gravity.wav");
		INFO0 = AudioMaster.loadSound("sound/voice/radio/radio1.wav");
		INFO1 = AudioMaster.loadSound("sound/voice/tank1crew/radioOperator/mission/start.wav");
		INFO2 = AudioMaster.loadSound("sound/voice/radio/radioB6.wav");
		GM0 = AudioMaster.loadSound("sound/music/mission1/journey.wav");
		GM1 = AudioMaster.loadSound("sound/copyrighted/music/Project Borealis OST 1. Chaos Theory.wav");
		UI0 = AudioMaster.loadSound("sound/ui/213148__complex-waveform__click.wav");
		EN0 = AudioMaster.loadSound("sound/engines/ALCO_FPA/offIdle.wav");
		EN1 = AudioMaster.loadSound("sound/engines/ALCO_FPA/startup.wav");
		ENG_ON1 = AudioMaster.loadSound("sound/engines/ALCO_FPA/idle.wav");
		ENG_ON2 = AudioMaster.loadSound("sound/engines/ALCO_FPA/run1.wav");
		ENG_ON3 = AudioMaster.loadSound("sound/engines/ALCO_FPA/run2.wav");
		testThing = AudioMaster.loadSound("sound/REPULSIVE - Tryst mono.wav");
		loadingMusic = AudioMaster.newSoundSource(10, 1, 1, true, new Vector3f(0, 0, 0));
		source1 = AudioMaster.newSoundSource(1, 5, 15, false, new Vector3f(0, 0, 0));
		source1.setLooping(false);
		source2 = AudioMaster.newSoundSource(1, 1, 15, false, new Vector3f(0, 0, 0));
		source2.setLooping(false);
		testSound = AudioMaster.newSoundSource(1, 5, 250, false, new Vector3f(0, 0, 0));
		testSound.setLooping(true);
		ambient0 = AudioMaster.newSoundSource(1, 1, 1, true, new Vector3f(0, 0, 0));
		engine = AudioMaster.newSoundSource(1, 10, 250, true, new Vector3f(0, 0, 0));
		engine2 = AudioMaster.newSoundSource(1, 10, 250, true, new Vector3f(0, 0, 0));
		engine3 = AudioMaster.newSoundSource(1, 10, 250, true, new Vector3f(0, 0, 0));
		engine4 = AudioMaster.newSoundSource(1, 10, 250, true, new Vector3f(0, 0, 0));
		hud = AudioMaster.newSoundSource(1, 1, 1, false, new Vector3f(0, 0, 0));
		hud.setLooping(false);
		// play the loading music
		loadingMusic.play(LM1);

	}

	/**
	 * sets up all the graphics objects defines and sets each object with specific
	 * params game will not work without this being called
	 */
	public void setupGraphicsStuff() {
		// cube map
		// map = new CubeMap(new String[] { "gameModels/theSnowDesert/skyBox/right",
		// "gameModels/theSnowDesert/skyBox/left",
		// "gameModels/theSnowDesert/skyBox/top",
		// "gameModels/theSnowDesert/skyBox/bottom",
		// "gameModels/theSnowDesert/skyBox/front",
		// "gameModels/theSnowDesert/skyBox/back" }, loader);
		// map = new CubeMap(new String[] {
		// "gameModels/theSnowDesert/reflectionMap/right",
		// "gameModels/theSnowDesert/reflectionMap/left",
		// "gameModels/theSnowDesert/skyBox/top",
		// "gameModels/theSnowDesert/skyBox/bottom",
		// "gameModels/theSnowDesert/reflectionMap/front",
		// "gameModels/theSnowDesert/reflectionMap/back" }, loader);
		// map = new CubeMap(new String[] { "right", "left", "top", "bottom", "front",
		// "back" }, loader);

		map = new CubeMap(new String[] { "skybox/cloudy/0004", // right - back
				"skybox/cloudy/0002", // left - front
				"skybox/cloudy/0005", // same as
				"skybox/cloudy/0006", // same as
				"skybox/cloudy/0001", // front - right
				"skybox/cloudy/0003" // back - left
		});

		// Renderer:
		Engine.renderer = new MasterRenderer(camera, map);
		// particles
		ParticleMaster.init(Engine.renderer.getProjectionMatrix());
		smoke = new ParticleTexture(MasterLoader.loadTexturePointer("smoke"), 8);
		trackSnow = new ParticleTexture(MasterLoader.loadTexturePointer("snowParticle"), 8);
		trainSmoke = new ParticleSystem(smoke, 30, 0.5f, -0.6f, 3, 20);
		trainSnow = new ParticleSystem(trackSnow, 80, 1, 0, 20, 20);
		// post processing (bloom effect)
		fbo = new Fbo(Engine.window.getWidth(), Engine.window.getHeight(), Fbo.DEPTH_RENDER_BUFFER, false);
		PostProcessing.init();
		// on screen text
		// load the loading screen
		loadingScreen();
		missionInfo();
		// put on the screen that we created the rendering system
		loadInfo("Creaded rendering system");
	}

	/**
	 * 
	 * these are the things that happen at the VERY start of the mission like
	 * playing music, setup of certain text, etc. anything that only executes once
	 * before the main game loop and after the setup goes here
	 */
	/**
	 * 
	 */
	public void preLoopStuff() {
		time = System.currentTimeMillis();
		// play the radio man thing:
		source1.play(INFO0);
		ambient0.play(AM0);
		engine.play(EN0);
		// testSound.play(testThing);
		// movie like stuff:
		GuiTexture bar = new GuiTexture(MasterLoader.loadTexturePointer("defaultBlack"), new Vector2f(0f, .9f),
				new Vector2f(1f, 0.1f));
		Engine.guis.add(bar);
		GuiTexture bar2 = new GuiTexture(MasterLoader.loadTexturePointer("defaultBlack"), new Vector2f(0f, -.9f),
				new Vector2f(1f, 0.1f));
		Engine.guis.add(bar2);
		GUIText tp = new GUIText("Third Person Camera", 1, font, new Vector2f(0.75f, 0.05f), 0.4f, false);
		tp.setColour(1, 1, 1);
		System.gc();//o h b o y
	}

	/**
	 * annoying things that require to be setup before anything else SOO MANY BUGS
	 * HAPPENED TO GET HERE
	 */
	public void setupEngineUtils() {
		font = new FontType(MasterLoader.loadTexturePointer("segoeUI"), new File("res/segoeUI.fnt"));
		TextMaster.init();
		Engine.guiRenderer = new GuiRenderer();
		AudioMaster.init();
	}

	/**
	 * loads the camera info stuff player object is set to be the lead train object
	 * camera will be mounted onto the player object sets the starting pitch and yaw
	 */
	public void setupCamera() {
		loadInfo("Creating camera");
		player = new Player(null, new Vector3f(0, 5, -15), 0, 0, 0, 1f);
		camera = new Camera(player);
		player.setPosition(new Vector3f(0, 0, 0));
		camera.setPosition(new Vector3f(0, 5, 5));
		camera.setPitch(20);
		camera.setYaw(0);
	}

	/**
	 * adds the loading screen to the screen with some text Loads the image, then
	 * the text in that order simple, and not used outside of loading
	 */
	public void loadingScreen() {
		GuiTexture loading = new GuiTexture(MasterLoader.loadTexturePointer("loadingScreen1024"), new Vector2f(0f, 0f),
				new Vector2f(1f, 1f));
		Engine.guis.add(loading);
		GUIText LM1 = new GUIText("Loading...", 3, font, new Vector2f(0, 0.01f), 1, false);
		LM1.setColour(0, 0, 0);
		GUIText LM2 = new GUIText("Status:", 1, font, new Vector2f(0, 0.1f), 1, false);
		LM2.setColour(0, 0, 0);
	}

	/**
	 * adds all the light sources that we will be using
	 */
	public void createLights() {
		loadInfo("Creating lights");
		// just the one light: THE S U N
		// sun = new Light(new Vector3f(100000,100000,10), new Vector3f(0,0,0), new
		// Vector3f(0.02f,0.02f,0.02f));
		Engine.lights.add(Engine.sun);
		Engine.lights.add(Engine.trainCabinLight);
		Engine.lights.add(Engine.trainFrontLight);
		Engine.sun.setPosition(new Vector3f(22443, 698, 22443));
		Engine.sun.setColor(new Vector3f(.43f, .2f, .1f));
		Engine.trainFrontLight.setColor(new Vector3f(0, 0, 0));
		// Engine.trainFrontLight.setColor(new Vector3f(5,5,5));
		Engine.lights.add(
				new Light(new Vector3f(-25, 55, 250), new Vector3f(3f, 2.6f, 2.4f), new Vector3f(0.05f, 0.05f, 0.04f)));
		Engine.lights.add(new Light(new Vector3f(-25, 55, 350), new Vector3f(3f, 2.7f, 2.455f),
				new Vector3f(0.05f, 0.05f, 0.04f)));
		Engine.lights.add(
				new Light(new Vector3f(45, 55, 250), new Vector3f(3f, 2.6f, 2.4f), new Vector3f(0.05f, 0.05f, 0.04f)));
		Engine.lights.add(new Light(new Vector3f(45, 55, 350), new Vector3f(3f, 2.7f, 2.455f),
				new Vector3f(0.05f, 0.05f, 0.04f)));
		railLight = new Light(new Vector3f(-10, 52.419f, 555.47f), new Vector3f(5, 0, 0),
				new Vector3f(0.05f, 0.05f, 0.04f), new Vector3f(0, -0.3618f, 0.9323f));
		railLight2 = new Light(new Vector3f(-10, 52.419f, 555.47f), new Vector3f(2, 0, 0),
				new Vector3f(0.05f, 0.05f, 0.04f));
		Engine.lights.add(railLight2);
		Engine.lights.add(railLight);
		// Engine.lights.add(new Light(new Vector3f(-23.054f, 37.042f, -291.71f), new
		// Vector3f(3,3,3)));
	}

	/**
	 * loads info text onto the loading screen arg1: String, the text to be
	 * displayed shifts the pos down by .1 for the next info to be displayed
	 */
	public void loadInfo(String text) {
		Engine.println("INFO: " + Engine.color_cyan + text, Engine.color_default);
		GUIText newText = new GUIText(text, 1, font, new Vector2f(0, pos), 1, false);
		newText.setColour(0, 0, 0);
		pos += 0.02f;
		updateLoadingScreen();
	}

	/**
	 * clears the text buffers and the gui buffer
	 */
	public void clearLoadingScreen() {
		Engine.guis.clear();
		TextMaster.clear();
	}

	/**
	 * loads in the worlds static objects loads in the main building, the terrain,
	 * and the track not the train since that is organized into another method
	 */
	public void createGameObjects() {
		loadInfo("Loading world");
		// debug tool
		// this model is located at point 0,0,0, and points in each direction once

		TexturedModel dev_tool_origin = MasterLoader.loadTexturedModel("origin", "tank1texture");
		// add the entity to the render list
		Engine.entities.add(new Entity(dev_tool_origin, 0, new Vector3f(0, 0, 0), 0, 0, 0, 10));

		TexturedModel hangar = MasterLoader.loadTexturedModel("hangar2v2", "tank1texture");
		// add the entity to the render list
		Engine.entities.add(new Entity(hangar, 0, new Vector3f(0, 0f, 0), 0, 0, 0, 1));

		TexturedModel detail = MasterLoader.loadTexturedModel("map1Detail", "d");
		// add the entity to the render list
		Engine.entities.add(new Entity(detail, 0, new Vector3f(0, 0f, 0), 0, 0, 0, 1));
		//Engine.entities.get(Engine.entities.size() - 1).setDrawDistanceEnabled(false);// make it that the detail doesn't
																						// disappear

		TexturedModel detail1 = MasterLoader.loadTexturedModel("fence", "TrainSim/testScene/texture");
		// add the entity to the render list
		Engine.entities.add(new Entity(detail1, 0, new Vector3f(0, 0f, 0), 0, 0, 0, 1));

		TexturedModel detail2 = MasterLoader.loadTexturedModel("mapDetail1", "gameModels/objects/output512");
		detail2.getTexture().setReflectiveMap(MasterLoader.loadTexturePointer("gameModels/objects/roughness512"));
		detail2.getTexture().setRoughMap(MasterLoader.loadTexturePointer("gameModels/objects/roughness512"));

		// add the entity to the render list
		Engine.entities.add(new Entity(detail2, 0, new Vector3f(0, 0f, 0), 0, 0, 0, 1));

		// terrain
		TexturedModel hangarTerrain = MasterLoader.loadTexturedModel("hangar1terrainHuge", "snow01");
		// texture things:
		hangarTerrain.getTexture().setReflectivity(0.6f);
		hangarTerrain.getTexture().setReflectiveMap(MasterLoader.loadTexturePointer("textures/snow/specular"));
		// add the entity to the render list
		Engine.entities.add(new Entity(hangarTerrain, 0, new Vector3f(0, 0f, 0), 0, 180, 0, 1));
		Engine.entities.get(Engine.entities.size() - 1).setDrawDistanceEnabled(false);
		//Engine.entities.get(Engine.entities.size() - 1).setDrawToShadowShader(false);

		// main track
		loadInfo("Creating track objects");
		// track = new Track(500, 12.01f, new Vector3f(0, 11.7f, 0), loader);

		String trackData = "distance = 12.01" + 
				"\namount = 400"
				+ "\n200,0"//
				+ "\n30,0.1"
				+ "\n30,0.2"
				+ "\n32,0.3"
				+ "\n40,0" 
				+ "\n120,0.5" 
				+ "\n190,0" 
				+ "\n45,-0.1" 
				+ "\n100,0"
				+ "\n100,-0.1" 
				+ "\n150, -0.2" 
				+ "\n100, 0" 
				+ "\n100,0.4";
		//track = ReTrack.createTrackFromString(trackData, new Vector3f(0, 0f, 0));

		String trackData1 = "distance = 12.01" + "\namount = 400" + "\n190,0";
		//ReTrack.createTrackFromString(trackData1, new Vector3f(0, 0, 2300));

		// Terrain terrain = new Terrain(0, 0, null, null, null, trackData);
		
		track = new ReTrack("res/track/test");
		
	}

	/**
	 * creates the train that the player will use
	 */
	public void createTrain() {
		loadInfo("Creating train");
		train = new TrainLoader(150).createTestTrain(4096, 0);
		/*
		 * train.addWagon(50); train.addWagon(2); train.addWagon(2); train.addWagon(2);
		 * train.addWagon(2); train.addWagon(2); train.addWagon(2); train.addWagon(2);
		 * train.addWagon(2);
		 */
		train.setAssignedLine(track);
		train.getCar(0).setPlayer(player);
		train.getCar(0).setPlay(true);
	}

	/**
	 * the game controls that the user uses to control the train allows for the
	 * following controls: w: move forward or slowdown when in reverse s: slow down
	 * when mobing forward or reverse space: slow down faster while moving t: shows
	 * some dev info
	 */
	public void gameControls() {
		float maxSpeed = 1.0f, acceleration = 0.001f, friction = 0.0001f;
		float tempSpeed = speed * Engine.window.getFrameTimeSeconds() * 100;
		distance += tempSpeed;
		train.update(distance, tempSpeed);
		// testWagon2.update(distance);
		if (Input.isKeyDown(GLFW.GLFW_KEY_W) && train.canMove()) {
			speed += acceleration;
			lastSpeed = speed * 2;
		} else if (Input.isKeyDown(GLFW.GLFW_KEY_S) && train.canMove()) {
			speed -= acceleration;
		} else if (Input.isKeyDown(GLFW.GLFW_KEY_SPACE)) {// e brake
			if (speed <= -acceleration) {
				speed += 0.002f;
			} else if (speed >= acceleration) {
				speed -= 0.002f;
			}
		} else {
			if (speed < 0) {
				speed += friction;
			} else if (speed > 0) {
				speed -= friction;
			}
		}
		if (speed > maxSpeed) {
			speed = maxSpeed;
		} else if (speed < -0.2f) {
			speed = -0.2f;
		}
		if (Input.isKeyDown(GLFW.GLFW_KEY_T)) {
			Engine.println(
					"index distance: " + train.getCar(0).getAssignedLine().getTrackFromDistance(distance).getIndex()
							+ "\ntrue distance: " + distance, Engine.color_cyan);
		}
		if (train.getCar(0).isEngineOn()) {
			engine.setPitch((float) Math.cbrt(Math.abs(speed) / 2) + 0.6f);
			engine.setPosition(train.getCar(0).getPosition());
			engine.setVolume((float) -(0.8 * (speed * speed)) + 1);
			engine2.setPitch((float) Math.cbrt(Math.abs(6 * speed) - 1 / 2));
			engine2.setPosition(train.getCar(0).getPosition());
			engine2.setVolume((5 * -(speed * speed)) + (5 * speed));
			engine3.setPitch((float) Math.cbrt(Math.abs(4 * speed) / 2) - 0.2f);
			engine3.setPosition(train.getCar(0).getPosition());
			engine3.setVolume(speed);
			if (Input.isKeyDown(GLFW.GLFW_KEY_W)) {
				// engine.setPitch((float) (Math.sqrt(speed) / 0.56568542f) + 0.5f);
			}
			if (Input.isKeyDown(GLFW.GLFW_KEY_F)) {
				Engine.println(lastSpeed, Engine.color_cyan);
			}
		}
		/*
		 * this next section controls the guns on the train, if there are any guns that
		 * is first, we need to get the mouses x y coords
		 */
		// float mouseX = (float) Input.getMouseX() + Engine.window.getWidth();
		// float mouseY = (float) Input.getMouseY() + Engine.window.getHeight();
		// now we need to get the 3d coords from that section of the buffer:
		// Vector3f mouse3DPosition = new Vector3f();

	}

	/**
	 * the game script: I cannot lie, it is super hard to read and understand so
	 * keep that in mind when reading the comments
	 * 
	 * @param font   the f o n t
	 * @param input1 usually the distance of the train on the track
	 * 
	 */
	public void Gevents(FontType font, float input1) {
		// section1 : at the start of the game loop this will run after 2.02 seconds
		// have passed:
		if (time + 2020 < System.currentTimeMillis() && !is1) {
			// fire this event and make sure that it does not execute again:
			is1 = true;
			// display the subtitles
			subtitles = new Popup(
					"T-H-9, This is H-Dog 7. You are cleared to engage. Radio silence is now in effect unitl bravo six, over.",
					font, new Vector3f(1, 1, 1), Popup.TYPE_BOTTOM_LEFT);
		}
		if (time + 10217 < System.currentTimeMillis() && !is2 && is1) {
			// after event 1 has fired, wait until game time hits 10.217 seconds
			// fire:
			is2 = true;
			// remove the current subtites:
			subtitles.remove();
			// play crew members line:
			source2.play(INFO1);
			// subtitle it:
			subtitles = new Popup("Radio silence has begun and the signal is lost. Its a go ahead to start!", font,
					new Vector3f(1, 1, 1), Popup.TYPE_BOTTOM_LEFT);
		}
		if (time + 11217 + 5227 < System.currentTimeMillis() && is2 && !is3) {
			// fire event:
			is3 = true;
			// remove crew members subtitles
			subtitles.remove();
		}
		// second section:
		// this is the tutorial section where the controls are introduced
		if (time + 11000 < System.currentTimeMillis() && !is4 && !hud.isPlaying() && is3) {
			// fire event:
			is4 = true;
			// play ui noise
			hud.play(UI0);
			// display info
			hudInfo = new Popup("Press I to start the engine", font, new Vector3f(1, 1, 1), Popup.TYPE_CENTER);
		}
		if (!train.canMove() && time + 11000 < System.currentTimeMillis() && Input.isKeyDown(GLFW.GLFW_KEY_I) && is4
				&& !is5) {
			// fire event:
			is5 = true;
			// remove the info
			hudInfo.remove();
			// play the ui noise again to indicate that it changed
			hud.play(UI0);
			// display the new message, this will make it look like that it changed
			// instantly
			hudInfo = new Popup("Starting engine...", font, new Vector3f(1, 1, 1), Popup.TYPE_CENTER);
			// engine time is set so we can play the noise for a time without interruption
			// before the train starts
			// also to make it more emersive if it wasn't already i guess
			engineTime = System.currentTimeMillis();
			// play the engine startup noise
			engine.play(EN1);
		}
		if (engineTime + 2419 < System.currentTimeMillis() && is5 && !is6) {// exact time to start the engine lmao
			// fire event:
			is6 = true;
			// play ui noise
			hud.play(UI0);
			// remove the info
			hudInfo.remove();
			// actually turn on the engine. surprise! to turn on a digital engine, you just
			// have to call one function to instantly turn it on!
			train.turnEngineOn();
			// engine noise loop play:
			engine.play(ENG_ON1);
			engine2.play(ENG_ON2);
			engine3.play(ENG_ON3);
			// turn on the front light:
			Engine.trainFrontLight.setColor(new Vector3f(3, 3, 3));
		}
		if (engineTime + 5000 < System.currentTimeMillis() && is6 == true && railLight2.getColor().getX() == 2) {
			railLight.setColor(new Vector3f(0, 5, 0));
			railLight2.setColor(new Vector3f(0, 2, 0));
		}
		// section3: this section is a tutorial for moving the train
		// if the player has not moved for a time indicated by the distance (we will
		// only do it under 270 to give advice)
		if (input1 < 870f && engineTime + 15000 + 4000 < System.currentTimeMillis() && train.canMove() && is6 && !is7) {
			// fire event: player has not moved beyond 270 distance
			is7 = true;
			// play the ui noise
			hud.play(UI0);
			// display the info
			hudInfo = new Popup("Press W to move forward", font, new Vector3f(1, 1, 1), Popup.TYPE_CENTER);
		}
		if (input1 > 870 && is7 && !if1) {
			// fire event:
			if1 = true;
			// play the ui noise to indicate that the message disapeared
			hud.play(UI0);
			// remove the info
			hudInfo.remove();
			railLight.setColor(new Vector3f(5, 0, 0));
			railLight2.setColor(new Vector3f(0, 0, 0));
		}

		if (input1 > 1000 && !source1.isPlaying() && !if2) {
			if2 = true;
			source1.play(GM1);
		}
		// this section only displays the credits thing at the start of the game
		// displaying only the name of the game, my name, and 15.ai for the voices.
		// Thanks to 15.ai for the voices!
		// 15.ai is super cool and super free. I recommend trying it out. Make spongebob
		// swear. I dare you.
		if (input1 > 1020 && !ic0) {
			ic0 = true;
			FadedTextMaster.newFadedText("Armored Rail Runner", font, new Vector2f(0.7f, 0.7f), 2, 20,
					new Vector3f(1, 1, 1));
		}
		if (input1 > 1100 && !ic1 && ic0) {
			ic1 = true;
			FadedTextMaster.newFadedText("Created by Isaac Dredge", font, new Vector2f(0.75f, 0.75f), 2, 20,
					new Vector3f(1, 1, 1));
		}
		if (input1 > 1200 && !ic2 && ic1) {
			ic2 = true;
			FadedTextMaster.newFadedText("Based on the flash game Rail of War by Youda Games", font,
					new Vector2f(0.6f, 0.8f), 2, 20, new Vector3f(1, 1, 1));
		}
		if (input1 > 1500 && !ic3 && ic2) {
			ic3 = true;
			FadedTextMaster.newFadedText("Thanks to:", font, new Vector2f(0.7f, 0.7f), 2, 20, new Vector3f(1, 1, 1));
		}
		if (input1 > 1550 && !ic4 && ic3) {
			ic4 = true;
			FadedTextMaster.newFadedText("15.ai", font, new Vector2f(0.75f, 0.75f), 2, 20, new Vector3f(1, 1, 1));
		}
		if (input1 > 1600 && !ic5 && ic4) {
			ic5 = true;
			FadedTextMaster.newFadedText("Freesound.org", font, new Vector2f(0.8f, 0.8f), 2, 20, new Vector3f(1, 1, 1));
		}
		if (input1 > 1700 && !ic6 && ic5) {
			ic6 = true;
			FadedTextMaster.newFadedText("Special thanks to:", font, new Vector2f(0.7f, 0.7f), 2, 20,
					new Vector3f(1, 1, 1));
		}
		if (input1 > 1750 && !ic7 && ic6) {
			ic7 = true;
			FadedTextMaster.newFadedText("ThinMatrix", font, new Vector2f(0.75f, 0.75f), 2, 20, new Vector3f(1, 1, 1));
		}
		if (input1 > 1800 && !ic8 && ic7) {
			ic8 = true;
			FadedTextMaster.newFadedText("Some blueprint hosting site", font, new Vector2f(0.8f, 0.8f), 2, 20,
					new Vector3f(1, 1, 1));
		}

		if (input1 > 18000 && !fa0) {
			fa0 = true;
			subtitles = new Popup("T-H-9, This is H-Dog 7. You are nearing Bravo 6.", font, new Vector3f(1, 1, 1),
					Popup.TYPE_BOTTOM_LEFT);
			hud.play(INFO2);
		}

	}

	/**
	 * @function displayMenu()
	 * @return void displays the main menu with options:
	 */
	public void displayMenu() {
		GuiTexture loading = new GuiTexture(MasterLoader.loadTexturePointer("menuScreen"), new Vector2f(0f, 0f),
				new Vector2f(1f, 1f));
		Engine.guis.add(loading);
		GUIText LM1 = new GUIText("Armoured rail runner build 0.0.2b", 3, font, new Vector2f(0.01f, 0.01f), 1, false);
		LM1.setColour(0, 0, 0);
		GUIText LM2 = new GUIText("Press ENTER to continue...", 1, font, new Vector2f(0, 0.1f), 1, false);
		LM2.setColour(0, 0, 0);
	}

	/**
	 * @function missionInfo()
	 * @return void Adds loading screen information for the user to read while they
	 *         wait for the game to load.
	 */
	public void missionInfo() {
		GuiTexture loading = new GuiTexture(MasterLoader.loadTexturePointer("guis/Santa_Fe_ALCO_PA-1_1946"),
				new Vector2f(0.5f, -0.1f), new Vector2f(0.4f, 0.5f));
		Engine.guis.add(loading);
		GUIText LM1 = new GUIText(
				"The ALCO PA was a family of A1A-A1A diesel locomotives built to haul passenger trains. "
						+ "The locomotives were built in Schenectady, New York, in the United States by a partnership of the American "
						+ "Locomotive Company (ALCO) and General Electric (GE) between June, 1946 and December, 1953. Designed by "
						+ "General Electric's Ray Patten (along with their ALCO FA cousins), they were of a cab unit design; both "
						+ "cab-equipped lead A unit PA and cabless booster B unit PB models were built. While externally the PB models "
						+ "were slightly shorter than the PA model, they shared many of the same characteristics, both aesthetically "
						+ "and mechanically. However, they were not as reliable as EMD E-units.",
				1, font, new Vector2f(0.55f, 0f), 0.4f, false);
		LM1.setColour(1, 1, 1);
		GUIText LM2 = new GUIText(
				"ALCO's designation of P indicates that they were geared for higher speeds and passenger use, "
						+ "whereas the F designation marks these locomotives as being geared primarily for freight use. However, beyond "
						+ "this, their design was largely similar - aside from the PA/PB's both being larger A1A-A1A types with an even "
						+ "more striking nose - and many railroads used PA and FA locomotives for both freight and passenger service. (From Wikipedia)",
				1, font, new Vector2f(0.55f, 0.8f), 0.4f, false);
		LM2.setColour(1, 1, 1);
		missionTips();
	}

	public void missionTips() {
		String[] tips = new String[10];
		tips[0] = "But you, I am affraid, are maidenless. Attack the D point! I am heavy weapons guy, and this is my weapon. Wake up, and smell the ashes.";
		tips[1] = "Trainz + gunz = coolness";
		tips[2] = "Beware of a diesel runaway! Your train will lose control and can give your position away! And make sure to stop it in time before its too late!";
		tips[3] = "This is the rong restraunt!";
		tips[4] = "alco pa my waifu, not some anime 2d character!";
		tips[5] = "stormy storm story time: Winter here. Cold. Fish walk on land. Evolution time.";
		tips[6] = "first girlfriend brought another dude on our second date. That was our last date. Second 'girlfriend' tried to convince me that age was just a number.";
		tips[7] = "walmart is lit, saw a bird flying in there once while working there. Also saw a parrot on a guys sholder just chillin";
		tips[8] = "schools lame";
		tips[9] = "konichiwa, watashi wa amerika jin desu";

		GUIText text = new GUIText(tips[(int) Math.floor(Math.random() * 10)], 1, font, new Vector2f(0, 0.9f), 0.4f,
				false);
		text.setColour(1, 1, 1);
	}
}
