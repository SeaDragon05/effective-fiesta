package main;


import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

import org.lwjgl.glfw.GLFW;

import engine.chapters.Chapter1;
import engine.graphics.Renderer;
import engine.graphics.Shader;
import engine.io.Input;
import engine.io.Window;
import engine.maths.Vector3f;
import engine.objects.Camera;
import engine.objects.Light;

public class Main implements Runnable {
	public static long time2;
	public Thread game;
	public static Window window;
	public static Renderer renderer;
	public Shader shader;
	public Shader fontShader;
	public static int WIDTH = 640, HEIGHT = 480;
	public static int level = 2;
	public static Camera camera = new Camera(new Vector3f(0,0,0), new Vector3f(0,0,0));
	public static int lightCount = 0;
	public static ArrayList<Light> lights = new ArrayList<Light>();
	public void start() {
		game = new Thread(this, "game");
		game.start();
	}
	public void init() {
		window = new Window(WIDTH, HEIGHT, "Totaly normal game");
		window.setBackgroundColor(0.3f, 0.3f, 0.3f);
		window.setDensity(0.045f);
		window.setGradient(1.5f);
		shader = new Shader("./shaders/mainVertex.glsl", "./shaders/mainFragment.glsl");
		fontShader = new Shader("./shaders/font/fontVertex.txt", "./shaders/font/fontFragment.txt");
		renderer = new Renderer(window, shader, fontShader);
		window.setBackgroundColor(0.03f, 0.03f, 0.03f);
		window.create();
		shader.create();
		try {
			File gamestate = new File("./Saves/gameState/gameState.txt");
			Scanner filescnr = new Scanner(gamestate);
			String line = filescnr.nextLine();
			String[] contents = line.split(",");
			level = Integer.parseInt(contents[0]);
			filescnr.close();
			Chapter1.init_map();
			Chapter1.createMap();
			if (level == 1) {
				window.setTitle("Oh no");
			} else if (level == 2) {
				Chapter1.init_map2();
			}
			
		} catch (Exception e) {
			System.err.println("Cannot find setup file!");
			level = 0;
			Chapter1.init_map();
			Chapter1.createMap();
		}       
		
	}
	
	public void run() {
		init();
		while (!window.shouldClose() && !Input.isKeyDown(GLFW.GLFW_KEY_ESCAPE)) {
			update();
			render();
			if (Input.isKeyDown(GLFW.GLFW_KEY_F11)) window.setFullscreen(!window.isFullscreen());
				
				if (Input.isKeyDown(GLFW.GLFW_KEY_T)) {
					System.out.println("Distance: " + camera.getDistance());
					System.out.println("Hirizonatil Angle: " + camera.getHorizontalAngle());
					System.out.println("Vertical Angle: " + camera.getVerticalAngle());
				}
		}
		close();
	}
	
	private void update() {
		window.update();
	}
	
	private void render() {
		Chapter1.render();
		window.swapBuffers();
	}
	
	private void close() {
		System.out.println(level);
		window.destroy();
		Chapter1.destroyMap();
		shader.destroy();
	}
	public static void write_to_file(String file_name, String contents) {
		try {
			File file = new File(file_name);
	        FileWriter myWriter = new FileWriter(file);
	        myWriter.write(contents);
	        myWriter.close();
	    } catch (Exception e) {}
	}

	public static void main(String[] args) {
		new Main().start();
	}
}