package main;

import java.util.ArrayList;
import java.util.List;

import org.hyperic.sigar.Sigar;
import org.lwjglx.util.vector.Vector3f;

import com.engine.graphics.GuiTexture;
import com.engine.graphics.WaterTile;
import com.engine.graphics.Window;
import com.engine.objects.Entity;
import com.engine.objects.Light;
import com.engine.objects.SpotLight;
import com.engine.objects.Terrain;
import com.engine.renderer.GuiRenderer;
import com.engine.renderer.MasterRenderer;
import com.engine.utils.FileWriter;

/*
 * Main data class
 * This class holds all global variables that the engine uses such as rendering settings and model data
 * All of these variables can be set via config files. They are overwritten when the game loads so any
 * changes made here WILL NOT REFLECT ON THE GAME
 */
public class Engine {
	public static EngineObject MainThread = new EngineObject();
	public static Window window;
	public static int WIDTH, HEIGHT;
	public static Vector3f skyColor = new Vector3f(0.7f, 0.7f, 0.7f);
	public static Vector3f fogColor = new Vector3f(0.7f, 0.7f, 0.7f);
	public static Light sun = new Light(new Vector3f(10000, 40000, 10000), new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(1,0,0));//new Light(new Vector3f(60000, 40000, -40000), new Vector3f(0.5f, 0.5f, 0.65f), new Vector3f(1,0,0));// the s u n
	// public static Light testsun = new Light(new Vector3f(60000,40000,15000), new
	// Vector3f(1f,1f,1.2f));//the s u n
	public static Light overHeadSun = new Light(new Vector3f(100, 40000, 100), new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(0,0,0));
	public static Light testLight = new Light(new Vector3f(1, 20, 1), new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(0,0,0));// the s u n
	public static Light trainCabinLight = new Light(new Vector3f(0, 0, 0), new Vector3f(2,2,2), new Vector3f(0f, 0f, 0f));
	public static Light trainFrontLight = new Light(new Vector3f(0,0,0), new Vector3f(3,3,3), new Vector3f(0.6f,0.0001f,0.0007f), new Vector3f(0,0,1));
	public static MasterRenderer renderer;
	public static GuiRenderer guiRenderer;
	public static float celShading_levels;
	public static boolean shadowType = false;
	public static boolean useCelShading;
	public static boolean shadowsEnabled = true;
	public static int MAX_LIGHTS;
	public static float gradient;
	public static float density;
	public static int shadowSoftness;
	public static List<Light> lights = new ArrayList<Light>();
	public static List<SpotLight> spotLights = new ArrayList<SpotLight>();
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
	public static List<Terrain> terrains = new ArrayList<Terrain>();//Need to revive
	public static List<WaterTile> waters = new ArrayList<WaterTile>();// old, don't really need it, but can be used
	public static List<GuiTexture> guis = new ArrayList<GuiTexture>();
	public static List<String> multiThreadedList = new ArrayList<String>();// oh boy oh B o y
	
	public static int TA0, TA1;
	
	public static int dataBlocks = 0;
	public static int textureData = 0;
	public static int modelData = 0;
	public static int soundData = 0;
	
	//global vars: (currently unused)
	public static float game_gravity;
	public static int model_lod;
	public static int texture_lod;
	
	//console colors ha

	public static final String color_default = "\u001B[0m";
	public static final String color_black = "\u001B[30m";
	public static final String color_red = "\u001B[31m";
	public static final String color_green = "\u001B[32m";
	public static final String color_yellow = "\u001B[33m";
	public static final String color_blue = "\u001B[34m";
	public static final String color_magenta = "\u001B[35m";
	public static final String color_cyan = "\u001B[36m";
	public static final String color_white = "\u001B[37m";
	public static void printSystemInformation() {
		FileWriter.createFile("output.log");
		try {
			println("System information:", color_default);
			Sigar sigar = new Sigar();
			org.hyperic.sigar.CpuInfo[] cpuInfoList = sigar.getCpuInfoList();
			println("CPU Model: " + cpuInfoList[0].getModel(), color_default);
			println("Core count: " + Runtime.getRuntime().availableProcessors(), color_default);
			float memory = ((Runtime.getRuntime().freeMemory() / 1024) / 1024);
			println("Free memory: " + memory + " MB" + (memory < 200 ? "\nWARNING: LOW MEMORY DETECTED" : ""), color_default);
			long maxMemory = (Runtime.getRuntime().maxMemory() / 1024) / 1024;
			println("Maximum memory: " + (maxMemory == Long.MAX_VALUE ? "no limit (WOW)" : maxMemory) + " MB", color_default);
			println("Total memory available to JVM: " + ((Runtime.getRuntime().totalMemory() / 1024) / 1024) + " MB", color_default);
			for (Object propertyKeyName : System.getProperties().keySet()) {
				println(propertyKeyName + " - " + System.getProperty(propertyKeyName.toString()), color_default);
			}
		} catch (Exception e) {
			println("INFO: Could not get CPU info!", color_yellow);
		}
	}
	
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
