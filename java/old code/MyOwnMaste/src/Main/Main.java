package Main;

import java.io.File;
import java.util.ArrayList;

import org.lwjgl.glfw.GLFW;
import org.lwjglx.util.vector.Vector2f;

import com.own.master.cardgame.base.TableInterface;
import com.own.master.engine.graphics.Renderer;
import com.own.master.engine.graphics.Shader;
import com.own.master.engine.io.AudioPlayer;
import com.own.master.engine.io.Input;
import com.own.master.engine.io.Window;
import com.own.master.engine.math.Vector3f;
import com.own.master.engine.objects.FullLight;
import com.own.master.engine.objects.Gameable;
import com.own.master.engine.objects.font.FontType;
import com.own.master.engine.objects.font.GUIText;
import com.own.master.engine.utils.FileUtils;
import com.own.master.horrorgame.ai.AI;
import com.own.master.menu.GUI;
import com.own.master.tts.TTS;
import com.own.master.ui.Chat;

public class Main implements Runnable {
	public static long time2;
	public Thread game;
	public static Window window;
	public static Renderer renderer;
	public Shader shader;
	public Shader fontShader;
	public static Player player = new Player(new Vector3f(0,0,0), new Vector3f(0,0,0), null);
	public static int WIDTH = 1600, HEIGHT = 900;
	public static ArrayList<FullLight> Lights = new ArrayList<FullLight>();
	public static ArrayList<GUIText> messages;
	public static GUIText testMessage = new GUIText();
	public static String[] title = new String[5];
	public static int gameDID = 0;
	public static final String music1 = "./sounds/music/music1.wav";
	public static final String music2 = "./sounds/music/sus.wav";
	public static final String music3 = "./sounds/music/win.wav";
	public static final String ui1 = "./sounds/ui/click.wav";
	public static final String ui2 = "./sounds/ui/confirm.wav";
	public static final String csh = "./sounds/general/flip1.wav";
	public static float FOV = 90;
	public static int gr_full_bright = 0;
	public static int gr_shadow_enable = 0;
	public static int gr_shadow_detail = 10;
	public static int gr_bump_enable = 1;
	public static Vector3f gr_shadow_color = new Vector3f(1f, 0f, 0f);
	public static int gr_fog_enable = 1;
	public static float gr_fog_distance = 10;
	public static float gr_fog_density = 1.9f;
	public static Vector3f gr_fog_color = new Vector3f(0, 0, 0);
	public static boolean updateCards = false;
	public static boolean update_setup = false;
	public static boolean isSettingUp = false;
	public static Thread gameThread;
	public static Gameable GThread;
	public static boolean kill = false;
	public static Main mainThread; 
	public static TableInterface table;
	public static Chat what;
	public static AI AIspoops;// = new AI(50, 10);

	public static void main(String[] args) {
		//@SuppressWarnings("unused") // only used to let the user choose what game they want to play.
		GUI wind = new GUI();
		TTS.Say("anime is not pronounced correctly with this t t s");
		title[1] = "BlackJack";
		title[2] = "Scum";
		title[0] = "Screwy Lewy";
		title[3] = "Test";
		title[4] = "font test";
	}

	public static void runProgram(int ch) {
		System.out.println("Loading program....");
		if (ch == 1 || ch == 2 || ch == 3 || ch == 4 || ch == 5) {
			gameDID = ch;
			System.out.println(gameDID);
			AudioPlayer.playaudio(ui2);
			mainThread = new Main();
			mainThread.start();
		}
	}

	public void start() {
		game = new Thread(this, "game");
		game.start();
	}

	public void init() {
		window = new Window(WIDTH, HEIGHT, title[gameDID - 1]);
		window.setDensity(0.1f);
		window.setGradient(0.1f);
		shader = new Shader("./shaders/mainVertex.glsl", "./shaders/mainFragment2.glsl");
		fontShader = new Shader("./shaders/font/fontVertex.txt", "./shaders/font/fontFragment.txt");
		renderer = new Renderer(window, shader, fontShader);
		window.setBackgroundColor(gr_fog_color);
		window.create();
		shader.create();
		fontShader.create();
		FontType test = new FontType(0, new File("./font/segoeUI.fnt"));
		testMessage = new GUIText("Welcome to this 3d project of mine! Perhaps in some way you are seeing this message!", 1, test, new Vector2f(10,10), 0.3f, false);
		what = new Chat();
		what.setText(testMessage);
		AIspoops = new AI();
		FileUtils.setup();
		switch (gameDID) {// AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
		case 1:
			TTS.Say("Screwy Lewy");
			com.own.master.cardgame.SL.Map.load();
			com.own.master.cardgame.SL.Map.create();
			com.own.master.cardgame.SL.Map.createObjects();
			break;
		case 2:
			TTS.Say("Black jack");
			table = new com.own.master.cardgame.BJ.Table();
			com.own.master.cardgame.BJ.Map.load();
			com.own.master.cardgame.BJ.Map.create();
			com.own.master.cardgame.BJ.Map.createObjects();
			// AudioPlayer.playaudio(music1);
			break;
		case 3:
			TTS.Say("Scum");
			com.own.master.cardgame.scum.Map.load();
			com.own.master.cardgame.scum.Map.create();
			com.own.master.cardgame.scum.Map.createObjects();
			break;
		case 4:
			TTS.Say("RektLand, The bedroom, but with the spoops");
			com.own.master.RektLand.Maps.Bedroom.Map.load();
			com.own.master.RektLand.Maps.Bedroom.Map.create();
			com.own.master.RektLand.Maps.Bedroom.Map.createObjects();
			break;
		case 5:
			what = new Chat();
			what.setText(testMessage);
			break;
		}
	}

	@Override
	public void run() {
		init();
		if (gameDID == 2) {
			GThread = new com.own.master.cardgame.BJ.GameThread();
			gameThread = new Thread((Runnable) GThread);//AAAAAAAAAAAA
			gameThread.start();
			table.setup();
		} else if (gameDID == 4) {
			GThread = new com.own.master.horrorgame.ai.GameThread();
			gameThread = new Thread((Runnable) GThread);//AAAAAAAAAAAA
			gameThread.start();
		}
		AudioPlayer end = new AudioPlayer();
		while (!window.shouldClose() && !Input.isKeyDown(GLFW.GLFW_KEY_ESCAPE)) {
			update();
			//renderLights();
			what.renderText(renderer);
			render(renderer);
			devControls();
			if (Input.isKeyDown(GLFW.GLFW_KEY_F11))
				window.setFullscreen(!window.isFullscreen());

			if (Input.isKeyDown(GLFW.GLFW_KEY_T)) {
				System.out.println("Distance: " + player.getCamera().getDistance());
				System.out.println("Hirizonatil Angle: " + player.getCamera().getHorizontalAngle());
				System.out.println("Vertical Angle: " + player.getCamera().getVerticalAngle());
			}
			if (gameDID == 2) {
				table.checkRenderUpdates();
				if (com.own.master.cardgame.BJ.GameThread.darkness > 1.f) {
					GThread.kill();
					kill = true;
					try {
						end = new AudioPlayer(music2);
						end.playaudio();
					} catch (Exception e) {
					}
					break;
				}
			}
		}
		close();
		if (kill) {
			try {
				Thread.sleep(end.getLength());
			} catch (Exception e) {
			}
		}
		System.exit(0);
	}

	public static void devControls() {
		float x = player.getCamera().getPosition().getX();
		float y = player.getCamera().getPosition().getY();
		float z = player.getCamera().getPosition().getZ();
		float ax = player.getCamera().getRotation().getX();
		float ay = player.getCamera().getRotation().getY();
		float speed = 0.01f;
		if (Input.isKeyDown(GLFW.GLFW_KEY_W)) {
			x += speed * (float) -Math.sin(Math.toRadians(ay));
			y += speed * (float) Math.sin(Math.toRadians(ax));
			z += speed * (float) -Math.cos(Math.toRadians(ay));
			player.getCamera().setPostition(new Vector3f(x, y, z));
		}
		if (Input.isKeyDown(GLFW.GLFW_KEY_S)) {
			x += speed * (float) Math.sin(Math.toRadians(ay));
			y += speed * (float) -Math.sin(Math.toRadians(ax));
			z += speed * (float) Math.cos(Math.toRadians(ay));
			player.getCamera().setPostition(new Vector3f(x, y, z));
		}
		if (Input.isKeyDown(GLFW.GLFW_KEY_A)) {
			x += speed * (float) -Math.cos(Math.toRadians(ay));
			z += speed * (float) Math.sin(Math.toRadians(ay));
			player.getCamera().setPostition(new Vector3f(x, y, z));
		}
		if (Input.isKeyDown(GLFW.GLFW_KEY_D)) {
			x += speed * (float) Math.cos(Math.toRadians(ay));
			z += speed * (float) -Math.sin(Math.toRadians(ay));
			player.getCamera().setPostition(new Vector3f(x, y, z));
		}
		if (Input.isKeyDown(GLFW.GLFW_KEY_F)) {
			if (gr_full_bright == 0) {
				gr_full_bright = 1;
			} else {
				gr_full_bright = 0;
			}

		}
		if (Input.isKeyDown(GLFW.GLFW_KEY_B)) {
			if (gr_bump_enable == 0) {
				gr_bump_enable = 1;
			} else {
				gr_bump_enable = 0;
			}

		}
	}

	private void update() {
		// float thing = (float) (Math.sin(0.001 * System.currentTimeMillis()) / 7) +
		// 3.8227f;
		// camera.setPostition(new Vector3f(camera.getPosition().getX(), thing,
		// camera.getPosition().getZ()));
		//MusicPlayer.playRandom();
		window.update();
		player.getCamera().update();
		player.update();
	}

	private void render(Renderer rnd) {
		if (gameDID == 1) {
			com.own.master.cardgame.SL.Map.render(rnd);
		} else if (gameDID == 2) {
			com.own.master.cardgame.BJ.Map.render(rnd);
		} else if (gameDID == 3) {
			//com.own.master.cardgame.scum.Map.render(rnd);
		} else if (gameDID == 4) {
			com.own.master.RektLand.Maps.Bedroom.Map.render(rnd);
			//AIspoops.render(rnd);
		} else {
			what.renderText(rnd);
		}
		window.swapBuffers();
	}

	private void close() {
		window.destroy();
		shader.destroy();
		fontShader.destroy();
		what.destroy();
		if (gameDID == 1)
			com.own.master.cardgame.SL.Map.destroy();
		if (gameDID == 2)
			com.own.master.cardgame.BJ.Map.destroy();
		if (gameDID == 3)
			com.own.master.cardgame.scum.Map.destroy();
		if (gameDID == 4) {
			com.own.master.RektLand.Maps.Bedroom.Map.destroy();
			AIspoops.destroy();
		}
	}
}
