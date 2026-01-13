package main;

import java.io.File;

import org.lwjgl.glfw.GLFW;
import org.lwjglx.util.vector.Vector2f;
import org.lwjglx.util.vector.Vector3f;
import org.lwjglx.util.vector.Vector4f;

import com.engine.font.FadedTextMaster;
import com.engine.font.FontType;
import com.engine.font.TextMaster;
import com.engine.graphics.ModelTexture;
import com.engine.graphics.TerrainTexture;
import com.engine.graphics.TerrainTexturePack;
import com.engine.interfaces.Game;
import com.engine.io.Input;
import com.engine.objects.Camera;
import com.engine.objects.CubeMap;
import com.engine.objects.Entity;
import com.engine.objects.Light;
import com.engine.objects.Player;
import com.engine.objects.RawModel;
import com.engine.objects.Terrain;
import com.engine.objects.TexturedModel;
import com.engine.postProcess.Fbo;
import com.engine.postProcess.PostProcessing;
import com.engine.renderer.MasterRenderer;
import com.engine.utils.Loader;
import com.engine.utils.MasterLoader;
import com.engine.utils.NormalMappedObjLoader;
import com.engine.utils.OBJLoader;

public class ShaderTest implements Runnable, Game {
	static float rotation = 0;

	@Override
	public void run() {
		Game.init("res/config/config2.cfg");
		//Game.init(null);
		Player player = new Player(null, new Vector3f(0, 0, 0), 0, 0, 0, 1f);
		Camera camera = new Camera(player);
		camera.setPosition(new Vector3f(0, 5, -15));
		camera.setPitch(10);
		camera.setYaw(180);

		Camera cam2 = new Camera(player);
		cam2.setPosition(new Vector3f(0, 5, 15));
		cam2.setPitch(10);
		cam2.setYaw(0);

		//CubeMap map = new CubeMap(new String[] { "right", "left", "top", "bottom", "front", "back" }, loader);
		CubeMap map = new CubeMap(new String[] { "gameModels/theSnowDesert/reflectionMap/test_right", "gameModels/theSnowDesert/reflectionMap/test_left", "gameModels/theSnowDesert/skyBox/top", "gameModels/theSnowDesert/skyBox/bottom", "gameModels/theSnowDesert/reflectionMap/test_front", "gameModels/theSnowDesert/reflectionMap/test_back" });
		
		MasterRenderer renderer = new MasterRenderer(camera, map);
		Fbo fbo = new Fbo(Engine.window.getWidth(), Engine.window.getHeight(), Fbo.DEPTH_RENDER_BUFFER, false);
		PostProcessing.init();
		TexturedModel track = MasterLoader.loadTexturedModel("gameModels/track/modelLOD1", "gameModels/track/trackTexture");
				//new TexturedModel(NormalMappedObjLoader.loadObjModel("gameModels/track/modelLOD1", loader),//why tf does this break?
				//new ModelTexture(loader.loadTexture("gameModels/track/trackTexture")));
		track.getTexture().setRoughMap(MasterLoader.loadTexturePointer("gameModels/track/reflectivityTexture"));
		track.getTexture().setNormalMap(MasterLoader.loadTexturePointer("gameModels/track/bumpTexture"));
		track.getTexture().setRoughness(.5f);
		track.getTexture().setReflectiveMap(MasterLoader.loadTexturePointer("gameModels/track/reflectivityTexture"));
		track.getTexture().setReflectivity(.5f);

		RawModel model = MasterLoader.loadRawModel("ball");//OBJLoader.loadObjModel("ball", loader);
		TexturedModel sphere1 = new TexturedModel(model, new ModelTexture(MasterLoader.loadTexturePointer("rough")));
		sphere1.getTexture().setReflectivity(0.1f);
		TexturedModel sphere2 = new TexturedModel(model, new ModelTexture(MasterLoader.loadTexturePointer("rough")));
		sphere2.getTexture().setReflectivity(0.4f);
		TexturedModel sphere3 = new TexturedModel(model, new ModelTexture(MasterLoader.loadTexturePointer("rough")));
		sphere3.getTexture().setReflectivity(0.8f);
		TexturedModel sphere4 = new TexturedModel(model, new ModelTexture(MasterLoader.loadTexturePointer("rough")));
		sphere4.getTexture().setReflectivity(1f);		
		TexturedModel sphere1b = new TexturedModel(model, new ModelTexture(MasterLoader.loadTexturePointer("rough")));
		sphere1b.getTexture().setRoughness(0.1f);
		TexturedModel sphere2b = new TexturedModel(model, new ModelTexture(MasterLoader.loadTexturePointer("rough")));
		sphere2b.getTexture().setRoughness(0.4f);
		TexturedModel sphere3b = new TexturedModel(model, new ModelTexture(MasterLoader.loadTexturePointer("rough")));
		sphere3b.getTexture().setRoughness(0.8f);
		TexturedModel sphere4b = new TexturedModel(model, new ModelTexture(MasterLoader.loadTexturePointer("rough")));
		sphere4b.getTexture().setRoughness(1);

		Entity thing = new Entity(track, 0, new Vector3f(0, 0, 0), 0, 90, 0, 1);
		Entity m1 = new Entity(sphere1, 0, new Vector3f(-10, 5, 0), 0, 90, 0, 1);
		Entity m2 = new Entity(sphere2, 0, new Vector3f(-5, 5, 0), 0, 90, 0, 1);
		Entity m3 = new Entity(sphere3, 0, new Vector3f(5, 5, 0), 0, 90, 0, 1);
		Entity m4 = new Entity(sphere4, 0, new Vector3f(10, 5, 0), 0, 90, 0, 1);
		Entity m1a = new Entity(sphere1b, 0, new Vector3f(-10, 8, 0), 0, 90, 0, 1);
		Entity m2a = new Entity(sphere2b, 0, new Vector3f(-5, 8, 0), 0, 90, 0, 1);
		Entity m3a = new Entity(sphere3b, 0, new Vector3f(5, 8, 0), 0, 90, 0, 1);
		Entity m4a = new Entity(sphere4b, 0, new Vector3f(10, 8, 0), 0, 90, 0, 1);
		Entity lot = new Entity(sphere4b, 0, new Vector3f(0, 8, 10), 0, 90, 0, 1);
		

		Engine.entities.add(m1);
		Engine.entities.add(m2);
		Engine.entities.add(m3);
		Engine.entities.add(m4);
		Engine.entities.add(m1a);
		Engine.entities.add(m2a);
		Engine.entities.add(m3a);
		Engine.entities.add(m4a);
		//Engine.entities.add(thing);
		Engine.entities.add(lot);

		//TexturedModel plane = new TexturedModel(OBJLoader.loadObjModel("plane1x1", loader),
		//		new ModelTexture(loader.loadTexture("rough")));

		//Entity ground = new Entity(plane, 0, new Vector3f(0, -7, 0), 0, 0, 0, 20);

		//Engine.entities.add(ground);
		
		TerrainTexture ground = new TerrainTexture(MasterLoader.loadTexturePointer(""));
		TerrainTexturePack tts = new TerrainTexturePack(ground, ground, ground, ground);
		Terrain terrain = new Terrain(0, 0, tts, ground, "heightmap");
		Engine.terrains.add(terrain);

		TexturedModel normalPlane = MasterLoader.loadTexturedModel("plane1x1", "ShaderTest/wood01");
				//new TexturedModel(NormalMappedObjLoader.loadObjModel("plane1x1", loader),
				//new ModelTexture(loader.loadTexture("ShaderTest/wood01")));
		normalPlane.getTexture().setNormalMap(MasterLoader.loadTexturePointer("ShaderTest/test5"));
		//normalPlane.getTexture().setNormalMap(loader.loadTexture("ShaderTest/flat"));
		normalPlane.getTexture().setReflectivity(0.5f);
		normalPlane.getTexture().setRoughness(0.5f);
		normalPlane.getTexture().setHasTransparency(true);
		Entity normalGround = new Entity(normalPlane, 0, new Vector3f(0, -7, 10), 0, 0, 0, 20);
		
		Engine.entities.add(normalGround);
		
		
		Engine.lights.add(Engine.sun);
		Engine.sun.setColor(new Vector3f(1.7f,1.5f,1.45f));
		//Light sun = new Light(new Vector3f(60000, 80000, 6000), new Vector3f(10,10,10), new Vector3f(0f, 0f, 0f));
		//Engine.lights.add(sun);
		Light testLight = new Light(new Vector3f(0, -3, 0), new Vector3f(2,2,2), new Vector3f(0.05f, 0.05f, 0.04f));
		Engine.lights.add(testLight);
		TextMaster.init();
		FontType font = new FontType(MasterLoader.loadTexturePointer("segoeUI"), new File("res/segoeUI.fnt"));

		FadedTextMaster.newFadedText("Armored Rail Runner", font, new Vector2f(0.7f, 0.7f), 10, 20,
				new Vector3f(1, 1, 1));
		int frames = 0;
		int height = 0;
		Engine.finishLoading();
		while (!Engine.window.shouldClose() && !Input.isKeyDown(GLFW.GLFW_KEY_ESCAPE)) {
			frames++;
			testLight.getPosition().y = (float) (Math.sin(frames * 0.02f) * 2);
			lot.getPosition().y = testLight.getPosition().y;
			renderer.renderShadowMap(Engine.entities, camera, Engine.sun);
			rotation += 1;
			thing.setRotY(rotation);
			normalGround.setRotY(-rotation/4);
			fbo.bindFrameBuffer();
			renderer.renderScene(Engine.entities, Engine.terrains, Engine.lights, camera, new Vector4f(0, 0, 0, 0));
			fbo.unbindFrameBuffer();
			PostProcessing.doPostProcessing(fbo);
			FadedTextMaster.update();
			TextMaster.render();
			Game.update();
		}
		PostProcessing.cleanUp();
		TextMaster.cleanUp();
		MasterLoader.cleanUp();
		Game.close();
		System.exit(0);
	}

	@Override
	public void start() {
		Engine.MainThread.thread = new Thread(this, "game");
		Engine.MainThread.thread.start();

	}

	public static void main(String[] args) throws Exception {
		new ShaderTest().start();// creates the main rendering and game thread
	}

}
