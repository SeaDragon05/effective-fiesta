package main;

import java.util.ArrayList;
import java.util.List;

import org.lwjglx.util.vector.Vector3f;

import com.engine.graphics.Window;
import com.engine.objects.Entity;
import com.engine.objects.Light;

public class Engine {
	public static List<Light> lights = new ArrayList<Light>() {
		private static final long serialVersionUID = 1L;
		@Override
		public boolean add(Light object) {
			if (object == null) {
				System.err.println("Error: Could not add NULL light!");
				Thread.dumpStack();//hANDY!
				System.err.println("Please do not push null lights to the main light list as this does cause issues!");
				return false;
			}
			if (this.size() > Engine.MAX_LIGHTS) {
				System.err.println("Error: Couldn't add light because we are at maximum!");
				return false;
			}
			this.add(size(), object);
			return true;
		}
	};
	public static List<Entity> entities = new ArrayList<Entity>() {
		private static final long serialVersionUID = 1L;
		//overrided due to some null entities being pushed somewhere
		@Override
		public boolean add(Entity object) {
			if (object == null) {
				System.err.println("Error: Could not add NULL entity!");
				Thread.dumpStack();//hANDY!
				System.err.println("Please do not push null entities to the main render list as this does cause issues!");
				return false;
			}
			//this.add(object, elementData, size());//does not work
			this.add(size(), object);//WHY IS THIS THE ONLY PUBLIC ONE???
	        return true;
		}
	};
	public static int WIDTH = 1280;
	public static int HEIGHT = 686;
	public static Window window;
	public static final String color_default = "\u001B[0m";
	public static final String color_black = "\u001B[30m";
	public static final String color_red = "\u001B[31m";
	public static final String color_green = "\u001B[32m";
	public static final String color_yellow = "\u001B[33m";
	public static final String color_blue = "\u001B[34m";
	public static final String color_magenta = "\u001B[35m";
	public static final String color_cyan = "\u001B[36m";
	public static final String color_white = "\u001B[37m";
	public static int dataBlocks = 0;
	public static int textureData = 0;
	public static int modelData = 0;
	public static int soundData = 0;
	public static float game_gravity = 2;
	public static int model_lod;
	public static int texture_lod;
	public static float celShading_levels;
	public static boolean shadowType = false;
	public static boolean useCelShading = true;
	public static boolean shadowsEnabled = true;
	public static float PLAYER_RUN_SPEED = 20;
	public static float PLAYER_TURN_SPEED = 160;
	public static float PLAYER_JUMP_POWER = 30;
	public static int MAX_LIGHTS = 3;
	public static float gradient;
	public static float density;
	public static int shadowSoftness;
	public static Vector3f skyColor = new Vector3f(0.5f,0.5f,0.5f);
	public static boolean shake = true;
	
	public static void finishLoading() {
		println("Loading report:", "\u001B[94m");
		println(" 	Model object count: " + modelData, color_blue);
		println(" 	texture count: " + textureData, color_blue);
		println(" 	sounds: " + soundData, color_blue);
		println(" 	Total objects loaded: " + dataBlocks, color_blue);
	}

	public static void printDateAndTime() {

	}

	public static void println(String input, String color) {
		System.out.println(color + input + color_default);
		//FileWriter.writeToFile("output.log", (input.isBlank() || input.isEmpty() ? "\n" : input));

	}
	public static void print(String input, String color) {
		System.out.print(color + input + color_default);
		//FileWriter.writeToFile("output.log", (input.isBlank() || input.isEmpty() ? "\n" : input));

	}

	public static void println(float input, String color) {
		System.out.println(color + input + color_default);
		//FileWriter.writeToFile("output.log", Float.toString(input));
	}
}
