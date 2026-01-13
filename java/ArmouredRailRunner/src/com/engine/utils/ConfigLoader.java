package com.engine.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.engine.graphics.ShadowBox;
import com.engine.graphics.SkyboxShader;
import com.engine.graphics.WaterFrameBuffers;
import com.engine.graphics.WaterTile;
import com.engine.graphics.Window;
import com.engine.objects.Player;
import com.engine.renderer.MasterRenderer;
import com.engine.renderer.ShadowMapMasterRenderer;
import com.engine.renderer.WaterRenderer;

import main.Engine;

public class ConfigLoader {

	public static final String defaultConfigFile = "res/config/config.cfg";

	public static File file;

	static int settingsSet = 0;

	public static void loadConfiguraion(String file) {
		System.out.println("----Loading config file...----");
		FileReader fr = null;
		if (file == null) {
			try {
				fr = new FileReader(new File(defaultConfigFile));
			} catch (FileNotFoundException e1) {
				System.err.println("Couldn't find default config file! Fatal engine error!");
				e1.printStackTrace();
				System.exit(99901);
			}
		} else {
			try {
				fr = new FileReader(new File(file));
			} catch (FileNotFoundException e) {
				System.err.println("Couldn't find custom config file! Using default...");
				try {
					fr = new FileReader(new File(defaultConfigFile));
				} catch (FileNotFoundException e1) {
					System.err.println("Couldn't find default config file! Fatal engine error!");
					e1.printStackTrace();
					System.exit(99901);
				}
				//e.printStackTrace();
			}
		}
		BufferedReader reader = new BufferedReader(fr);
		String line;
		try {
			while (true) {
				line = reader.readLine();
				String[] currentLine = line.split(" ");
				if (line.startsWith("{")) {
					System.out.println("----Found config file----");
				} else if (line.startsWith("}")) {
					break;
				} else {
					// find out if its a boolean, float, or an int:
					if (currentLine[2].contains("true") || currentLine[2].contains("false")) {
						// parse the float:
						if (setVariable(currentLine[0], Boolean.parseBoolean(currentLine[2])) == false) {
							System.out.println(
									"Could not find a variable named '" + currentLine[0] + "'! Boolean Skipping...");
						} else {
							settingsSet += 1;
						}
					} else {
						try {
							int value = Integer.parseInt(currentLine[2]);
							if (setVariable(currentLine[0], value) == false) {
								System.out.println("Could not find a variable named '" + currentLine[0]
										+ "'! Integer Skipping...");
							} else {
								settingsSet += 1;
							}
						} catch (Exception e) {
							try {
								float value = Float.parseFloat(currentLine[2]);
								if (setVariable(currentLine[0], value) == false) {
									System.out.println("Could not find a variable named '" + currentLine[0]
											+ "'! Float skipping...");
								} else {
									settingsSet += 1;
								}
							} catch (Exception eE) {
								System.out.println("Could not figure out what '" + currentLine[2] + "' is on variable '"
										+ currentLine[0] + "' . Skipping...");
							}
						}
					}
				}
			}
			System.out.println("Loaded " + settingsSet + " parameters successfuly");
			Engine.dataBlocks += 1;
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(99902);
		}
	}

	public static boolean setVariable(String name, int value) {
		if (name.contains("width")) {
			Engine.WIDTH = value;
			return true;
		}
		if (name.contains("SHADOW_PCFCOUNT")) {
			Engine.shadowSoftness = value;
			return true;
		}
		if (name.contains("height")) {
			Engine.HEIGHT = value;
			return true;
		}
		if (name.contains("SHADOW_MAP_SIZE")) {
			ShadowMapMasterRenderer.SHADOW_MAP_SIZE = value;
			return true;
		}
		if (name.contains("REFLECTION_WIDTH")) {
			WaterFrameBuffers.REFLECTION_WIDTH = value;
			return true;
		}
		if (name.contains("REFLECTION_HEIGHT")) {
			WaterFrameBuffers.REFLECTION_HEIGHT = value;
			return true;
		}
		if (name.contains("REFRACTION_WIDTH")) {
			WaterFrameBuffers.REFRACTION_WIDTH = value;
			return true;
		}
		if (name.contains("REFRACTION_HEIGHT")) {
			WaterFrameBuffers.REFRACTION_HEIGHT = value;
			return true;
		}
		if (name.contains("TILE_SIZE")) {
			WaterTile.TILE_SIZE = value;
			return true;
		}
		if (name.contains("maxLights")) {
			if (value > 20) {
				System.out.println("Cannot set maxLights higer than 20! Setting to 20...");
				value = 20;
			} else if (value < 0) {
				System.out.println("Cannot set maxLights lower than 0! Setting to 0...");
				value = 0;
			}
			//NormalMappingShader.MAX_LIGHTS = value;
			//StaticShader.MAX_LIGHTS = value;
			//TerrainShader.MAX_LIGHTS = value;
			//WaterShader.MAX_LIGHTS = value;
			Engine.MAX_LIGHTS = value;
			return true;
		}

		return false;
	}

	public static boolean setVariable(String name, float value) {
		if (name.contains("WAVE_SPEED")) {
			WaterRenderer.WAVE_SPEED = value;
			return true;
		}
		if (name.contains("FOV")) {
			MasterRenderer.FOV = value;
			return true;
		}
		if (name.contains("NEAR_PLANE")) {
			MasterRenderer.NEAR_PLANE = value;
			return true;
		}
		if (name.contains("FAR_PLANE")) {
			MasterRenderer.FAR_PLANE = value;
			return true;
		}
		if (name.contains("SHADOW_DISTANCE")) {
			ShadowBox.SHADOW_DISTANCE = value;
			return true;
		}
		if (name.contains("skyBoxRotateSpeed")) {
			SkyboxShader.ROTATE_SPEED = value;
			return true;
		}
		if (name.contains("OFFSET")) {
			ShadowBox.OFFSET = value;
			return true;
		}
		if (name.contains("RUN_SPEED")) {
			Player.RUN_SPEED = value;
			return true;
		}
		if (name.contains("TURN_SPEED")) {
			Player.TURN_SPEED = value;
			return true;
		}
		if (name.contains("GRAVITY")) {
			Player.GRAVITY = value;
			return true;
		}
		if (name.contains("JUMP_POWER")) {
			Player.JUMP_POWER = value;
			return true;
		}
		if (name.contains("fogGradient")) {
			Engine.gradient = value;
			return true;
		}
		if (name.contains("fogDensity")) {
			Engine.density = value;
			return true;
		}
		if (name.contains("fogColorR")) {
			Engine.skyColor.x = value;
			Engine.fogColor.x = value;
			return true;
		}
		if (name.contains("fogColorG")) {
			Engine.skyColor.y = value;
			Engine.fogColor.y = value;
			return true;
		}
		if (name.contains("fogColorB")) {
			Engine.skyColor.z = value;
			Engine.fogColor.z = value;
			return true;
		}
		return false;
	}

	public static boolean setVariable(String name, boolean value) {
		if (name.contains("vsync")) {
			Window.vsync = value;
			return true;
		}
		if (name.contains("SHADOW_ENABLE")) {
			Engine.shadowsEnabled = value;
			return true;
		}
		return false;
	}
}
