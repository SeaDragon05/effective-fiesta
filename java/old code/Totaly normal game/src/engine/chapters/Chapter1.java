package engine.chapters;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import engine.io.SimpleAudioPlayer;
import engine.maths.Physics;
import engine.maths.Vector3f;
import engine.objects.GameObject;
import engine.objects.Light;
import engine.objects.Map;
import engine.objects.Trigger;
import engine.utils.FileUtils;
import main.Main;

public class Chapter1 {
	static Map map = new Map("map_chapter1");
	public static SimpleAudioPlayer music;
	public static Vector3f spawnPosition = new Vector3f(0, 2, 0);
	public static Vector3f spawnRotation = new Vector3f(0, 0, 0);
	public static boolean timed = true;
	public static long time2 = 0;
	public static float angle = 25;
	public static float scarydistance = 12;
	public static float color = 0.1f;
	public static int event = 0;
	public static int glitchLevel = 0;
	static ArrayList<Trigger> heightTrigger = new ArrayList<Trigger>();
	static int x = 0;
	static float h = 10f;
	public static int lightThing = 0;
	static boolean me = false;
	static boolean m2 = false;
	
	public static void init_map() {
		heightTrigger.add(new Trigger(null, false));
		heightTrigger.add(new Trigger(null, false));
		heightTrigger.add(new Trigger(null, false));
		heightTrigger.add(new Trigger(null, false));
		heightTrigger.add(new Trigger(null, false));
		heightTrigger.add(new Trigger(null, false));
		heightTrigger.add(new Trigger(null, false));
		heightTrigger.add(new Trigger(null, false));
		heightTrigger.add(new Trigger(null, false));
		heightTrigger.add(new Trigger(null, false));
		//contains scripts, meshes, etc using the map object to store data
		//load and store meshes:
		map.addMesh(FileUtils.loadOBJ("./models/playerCube.obj", "/textures/wood.png", new Vector3f(0,0,0) , new Vector3f(0.6f,0.6f,0.6f), 10));//0
		map.addMesh(FileUtils.loadOBJ("./models/cube.obj", "/textures/block.png", new Vector3f(0,0,0), new Vector3f(1,1,1), 0));//1
		map.addMesh(FileUtils.loadOBJ("./models/tut1.obj", "/textures/silver.png", new Vector3f(0,0,0), new Vector3f(1,1,1), 10));//2
		map.addMesh(FileUtils.loadOBJ("./models/tut2.obj", "/textures/silver.png", new Vector3f(0,0,0), new Vector3f(1,1,1), 10));//3
		map.addMesh(FileUtils.loadOBJ("./models/city.obj", "/textures/black.png", new Vector3f(0,0,0), new Vector3f(1,1,1), 10));//4
		map.addMesh(FileUtils.loadOBJ("./models/bridge.obj", "/textures/black.png", new Vector3f(0,2.27669f,0), new Vector3f(1,1,1), 10));//5
		map.addMesh(FileUtils.loadOBJ("./models/engi.obj", "/textures/silver.png", new Vector3f(0f,0,0), new Vector3f(1,1,1), 10));//6
		map.addMesh(FileUtils.loadOBJ("./models/cave_desc.obj", "/textures/black.png", new Vector3f(0,0,0), new Vector3f(1f,1f,1), 10));//7
		
		
		//create objects:
		map.createObject(new Vector3f(0,0,0), new Vector3f(0,0,0), new Vector3f(1f,1f,1f), 6, 0, -1);//creates the player
		//create the base objects
		map.createObject(new Vector3f(0,-1, 0), new Vector3f(0,0,0), new Vector3f(15,1,1), 1, 1, 0);
		map.createObject(new Vector3f(8, 0f, 0f), new Vector3f(0,0,0), new Vector3f(1, 1, 1), 1, 1, 0);
		map.createObject(new Vector3f(-8, 0f, 0f), new Vector3f(0,0,0), new Vector3f(1, 1, 1), 1, 1, 0);
		//fill the lists with random objects stacking up
		for (int i = 0; i < 350; i++) {
			if (i == 50 || i == 90 || i == 150 || i == 249) {
				float xaxis = (float) Math.random() * 14 - 7;
				map.createObject(new Vector3f(xaxis,(float) (i * 2) + 2, 0), new Vector3f(0,0,0), new Vector3f(1,1,1), 4, 1, 0);
				Main.lights.add(new Light(new Vector3f(xaxis, (float) (i * 2) + 2, 3), new Vector3f(1,1,1), 10));
			} else {
				map.createObject(new Vector3f((float) Math.random() * 14 - 7,(float) (i * 2) + 2, 0), new Vector3f(0,0,0), new Vector3f(1,1,1), 4, 1, 0);
			}
		}
		map.createObject(new Vector3f(-3, 3f, 0f), new Vector3f(0,0,0), new Vector3f(1, 1, 1), 1, 2, 0);
		Main.lights.add(new Light(new Vector3f(-3, 3, -2), new Vector3f(1,1,1), 10));
		map.createObject(new Vector3f(3, 3f,-45f), new Vector3f(0,0,0), new Vector3f(1, 1, 1), 1, 6, 0);
		Main.lights.add(new Light(new Vector3f(3, 3, -2), new Vector3f(1,1,1), 10));
		Main.lightCount = 6;
		for (int i = 4; i < 400; i++) {
			//objects[i] = new GameObject(new Vector3f((float) Math.random() * 14 - 7, (float) (i * 2) + 2, -6), new Vector3f(0,0,0), new Vector3f(1, 1, 1), cubemesh, 0);
			map.createObject(new Vector3f((float) Math.random() * 14 - 7, (float) (i * 2) + 2, -6), new Vector3f(0,0,0), new Vector3f(1, 1, 1), 1, 1, 0);
		}
		//various setup items:
		map.addTrigger(new Trigger("theFall", false));//ID:0
		map.addTrigger(new Trigger("Map2", false));//ID:1
		map.setPlayerPhysics(new Physics(2f, spawnPosition, 0.4f, 0f, map.getPlayerObject()));
		//scripts:
		if (Main.level == 0) {
			try {
				music = new SimpleAudioPlayer("./music/554__bebeto__ambient-loopWAV.wav");
				music.play();
			} catch(Exception e) {
				System.out.println("Error loading sound!");
				System.err.println(e);
			}
		}
	}
	public static void init_map2() {
		map.getStaticObjectList1().clear();
		Main.window.setBackgroundColor(0.9f, 0.1f, 0.1f);
		Main.window.setDensity(0.05f);
		Main.window.setGradient(1.5f);
		map.getPlayerPhysics().setPosition(new Vector3f(0, 500, 0));
		map.getPlayerPhysics().setVelocity(new Vector3f(0,0,0));
		//clear various map items
		Main.lights.removeAll(Main.lights);
		Main.lightCount = 0;
		Main.lights.add(new Light(new Vector3f(0, 3, 0), new Vector3f(1,1,1), 10));
		for (int i = 4; i < 400; i++) {
			map.createObject(new Vector3f((float) Math.random() * 14 - 7, (float) (i * 2) + 2, -6), new Vector3f(0,0,0), new Vector3f(1, 1, 1), 1, 1, 0);
		}
		for (int i = 4; i < 400; i++) {
			map.createObject(new Vector3f((float) Math.random() * 14 - 14, (float) (i * 2) + 2, 6), new Vector3f(0,0,0), new Vector3f(1, 1, 1), 1, 1, 0);
		}
		for (int i = 0; i < 230; i++) {
			float xaxis = (float) Math.random() * 14 - 7;
			map.createObject(new Vector3f(xaxis,(float) (i * 2) + 2, 0), new Vector3f(0,0,0), new Vector3f(1,1,1), 4, 0, 0);
		}
		map.createObject(new Vector3f(0, 0, 0), new Vector3f(0,0,0), new Vector3f(1,1,1), 1, 4, 0);
		map.createObject(new Vector3f(0, 0, 0), new Vector3f(0,0,0), new Vector3f(1,1,1), 1, 5, 0);
		map.createObject(new Vector3f(0, 0, 0), new Vector3f(0,0,0), new Vector3f(1,1,1), 2, 7, 0);
		
		map.addTrigger(new Trigger("renderNpc", true));
		
		
	}
	public static void init_map3() {
		map.setPhysicalObjectList(new ArrayList<GameObject>());
		for (int i = 0; i < 400; i++) {
			map.createObject(new Vector3f((float) Math.random() * 14 - 7, (float) (i * 2) + 2, -6), new Vector3f(0,0,0), new Vector3f(1, 1, 1), 1, 1, 0);
			map.createObject(new Vector3f((float) Math.random() * 14 - 7, (float) (i * 2) + 2, -6), new Vector3f(0,0,0), new Vector3f(1, 1, 1), 1, 1, 0);
		}
		for (int i = 0; i < 230; i++) {
			float xaxis = (float) Math.random() * 14 - 7;
			float zaxis = (float) Math.random() * 14 - 7;
			map.createObject(new Vector3f(xaxis,(float) (i) + 2, zaxis), new Vector3f(0,0,0), new Vector3f(1,1,1), 4, 0, 0);
		}
		
	}
	public static void createMap() {
		map.create();
	}
	
	public static void destroyMap() {
		map.destroy();
	}

	/**
	 * Main script: render()
	 * This runs all the events that can happen within the chapter
	 * Not exactly efficient, but it does what it needs to accomplish
	 * 
	 * @author Koroskor
	 */
	public static void render() {
		if (Main.level == 0) Main.window.setTitle("Totaly normal game");
		if (map.checkTrigger(0)) Main.window.setTitle("Oh no");
		if (Main.level == 0) {
			Main.renderer.renderMesh(map.getPlayerObject(), Main.camera, 1);
			if (!map.checkTrigger(0)) {
				Main.camera.update(map.getPlayerObject(), new Vector3f(12f, 0, 25.1999f));
			}
			map.getPlayerPhysics().update();
			for (int i = 0; i < map.getStaticObjectList1().size(); i ++) {
				Main.renderer.renderMesh(map.getStaticObjectList1().get(i), Main.camera, 1);
			}
			for (int i = 0; i < map.getPhysicalObjectList().size(); i ++) {
				map.getPlayerPhysics().collision(map.getPhysicalObjectList().get(i));//check collisions
				Main.renderer.renderMesh(map.getPhysicalObjectList().get(i), Main.camera, 1);
			}
			setGlitchLevel();
			if (map.getPlayerPhysics().getPosition().getY() >= (map.getPhysicalObjectList().size() * 2 + 1) * 0.70f) {
				map.setTrigger(0, true);
				map.getPhysicalObjectList().clear();
				try {
					if (!m2) {
						m2 = true;
						music.stop();
						SimpleAudioPlayer.playaudio("./music/glitched_end.wav");
					}
					SimpleAudioPlayer.playaudio("./sounds/theFall.wav");//this starts many times a second and gives that chill effect
				} catch (Exception e) {}
			}
			if (map.checkTrigger(0)) {
				if (angle > -90) {
					angle -= 0.23f;
					scarydistance += 0.02f;
				}
				Vector3f thing = new Vector3f(scarydistance, 0, angle);
				Main.camera.update(map.getPlayerObject(), thing);
				//renderer.renderMesh(enemy, camera, 1);
				if (map.getPlayerPhysics().getPosition().getY() <= 0) {
					Main.level = 1;
					//wait for like 5 seconds while playing a ohno sound
					try {
						music = new SimpleAudioPlayer("./music/385078__devern__atmosphere-entry.wav");
						music.play();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					//update game state
					try {
						Scanner scnr = new Scanner(new File("./Saves/gameState/gameState.txt"));
						String line = scnr.nextLine();
						String[] contents = line.split(",");
						contents[0] = "1";
						//write_to_file("./Saves/gameState/gameState.txt", contents[0] + "," + contents[1]);
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
				}
			}
		}
		if (Main.level == 1) {
			if (timed) {
				time2 = System.currentTimeMillis();
				System.out.println("SET");
				timed = false;
			}
			if (waitUntil(12000)) {
				if (!map.getTriggers().get(1).getValue()) {
					map.getPlayerPhysics().setLevel(2.75f); 
					map.setTrigger(1, true);
					Main.window.setTitle("Totaly normal... Right?");
					System.out.println("MENU");
					init_map2();
				} 
			} else {
				Main.window.setBackgroundColor(Main.window.getBackground().getX() + 0.001f, 0.1f, 0.1f);
			}
			
			if (map.getTriggers().get(1).getValue()) {
				Main.camera.update(map.getPlayerObject(), new Vector3f(12f, 45, -45));
				Main.renderer.renderMesh(map.getPlayerObject(), Main.camera, 1);
				if (event == 0) {
					map.getPlayerPhysics().update();
				}
				for (int i = 0; i < map.getPhysicalObjectList().size(); i ++) {
					map.getPlayerPhysics().collision(map.getPhysicalObjectList().get(i));//check collisions
					Main.renderer.renderMesh(map.getPhysicalObjectList().get(i), Main.camera, 1);
				}
				for (int i = 0; i < map.getStaticObjectList1().size(); i ++) {
					Main.renderer.renderMesh(map.getStaticObjectList1().get(i), Main.camera, 1);
				}
				Main.renderer.renderMesh(map.getStaticObjectList2().get(0), Main.camera, 1);
				if (map.getPlayerObject().getPosition().getY() <= 2.75 && map.checkTrigger(2)) {
					map.setTrigger(2, false);
					event = 1;
					try {
						music.stop();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (!map.checkTrigger(2)) {
					Main.level = 2;
					timed = true;
					if (timed) {
						time2 = System.currentTimeMillis();
						System.out.println("SET");
						timed = false;
						map.setTrigger(2, true);
						SimpleAudioPlayer.playaudio("./sounds/346782__vandrandepinnen__glitch-collection.wav");
						Main.window.setBackgroundColor(0f, 0f, 0f);
						init_map3();
					}
				}
			}
		}
		if (Main.level == 2) {
			if (waitUntil(2168)) {
				if (map.checkTrigger(2)) {
					try {
						music = new SimpleAudioPlayer("./music/455855__andrewkn__wandering.wav");
						music.play();
					} catch (Exception e) {
						System.err.println(e);
					}
					Main.window.setBackgroundColor(1f, 1f, 1f);
					map.setTrigger(2, false);
					map.createObject(new Vector3f(0, -10, 0), new Vector3f(180,0,0), new Vector3f(1,1,1), 1, 4, 0);
					Main.camera.update(map.getPlayerObject(), new Vector3f(8f, -10, 15));
					
				}
				Main.camera.update(map.getPlayerObject());
				map.getPlayerPhysics().update3d();
				Main.renderer.renderMesh(map.getPlayerObject(), Main.camera, 1);
				for (int i = 0; i < map.getPhysicalObjectList().size(); i ++) {
					map.getPlayerPhysics().collision3d(map.getPhysicalObjectList().get(i));//check 3d collisions
					Main.renderer.renderMesh(map.getPhysicalObjectList().get(i), Main.camera, 1);
				}
				
			}
		}
	}
	public static boolean waitUntil(long millis) {
		long time1 = System.currentTimeMillis() - millis;
		if (time1 >= time2) {
			return true;
		} else {
			return false;
		}
	}
	public static Map getMap() {
		return map;
	}
	public static void setMap(Map map) {
		Chapter1.map = map;
	}
	public static SimpleAudioPlayer getMusic() {
		return music;
	}
	public static void setMusic1(SimpleAudioPlayer music) {
		Chapter1.music = music;
	}
	public static void setGlitchLevel() {
		if (x < 10) {
			if (!heightTrigger.get(x).getValue() &&  map.getPlayerObject().getPosition().getY() >= h) {
				glitchLevel += 1;
				heightTrigger.get(x).setValue(true);
				h += 30f;
				x += 1;
			}
			if (x == 2 && !me) {
				me = true;
				try {
					music.stop();
					music = new SimpleAudioPlayer("./music/glitched.wav");
					music.play();
				} catch (UnsupportedAudioFileException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (LineUnavailableException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		} else {
			glitchLevel = 40;
			lightThing = -6;
		}
	}
}
