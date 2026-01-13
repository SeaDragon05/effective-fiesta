package com.engine.loader;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import org.lwjglx.util.vector.Vector3f;

import com.engine.graphics.ModelTexture;
import com.engine.objects.Entity;
import com.engine.utils.Loader;

import com.engine.objects.TexturedModel;
import com.engine.utils.OBJLoader;
/**
 * SceneLoader
 * WIP
 * loads config files and loads in many things
 * me brian hurt
 * 
 * @author isaac
 *
 */
public class SceneLoader {
	static final int instruction_invalid = -1;

	static final int instruction_bracket_open = 301;
	static final int instruction_bracket_close = 302;

	static final int instruction_set_name = 400;
	static final int instruction_set_name_of_scene = 401;

	static final int instruction_add_object = 500;
	static final int instruction_add_track = 501;
	static final int instruction_add_wagon = 502;
	static final int instruction_add_entity = 503;

	// entity loading goes from 700 to 900
	static final int instruction_load_entity = 600;

	private Loader loader;
	private int brackets;
	private String scene = "";
	private boolean object = false;
	private boolean name = false;

	public SceneLoader(Loader loader) {
		this.loader = loader;
		this.brackets = 0;
	}

	public void loadSceneFromFile(String sceneFile) {
		File file;
		Scanner scnr;

		try {
			file = new File(sceneFile);
			scnr = new Scanner(file);
		} catch (Exception e) {
			System.err.println("Could not load scene file!");
			e.printStackTrace();
			return;
		}

		while (scnr.hasNextLine()) {
			// get line
			String line = scnr.nextLine();
			String[] contents = line.split(" ");
			// figure out what to do
			int[] instructions = new int[contents.length];
			for (int i = 0; i < instructions.length; i++) {
				instructions[i] = getInstruction(contents[i]);// first one should always be some instruction
			}
			if (instructions[0] != instruction_invalid) {// check to see if it is valid
				if (instructions[0] == instruction_set_name) {// ignores the brackets while parsing because I don't want
																// to write that
					parseInstruction(instructions[0], new String[] { contents[1] });// set the name
				}
				if (instructions[0] == instruction_add_object) {// next set should be valid
					if (instructions[1] != instruction_invalid && instructions[2] == instruction_bracket_open) {
						// check the instruction and check to see if there is a bracket there
						// now, check the next one and see what we should add
						if (instructions[1] == instruction_add_entity) {// entity
							System.out.println("Entity");
						}
						if (instructions[1] == instruction_add_track) {
							System.out.println("Track");

						}
						if (instructions[1] == instruction_add_entity) {
							System.out.println("Wow");
							//entityLoader.loadEntity(instructions);
						}
					} else {
						System.err.println("Error parsing file! " + instruction_invalid);
						break;
					}
				}

				//////
			} else {
				System.err.println("Error parsing file! " + instruction_invalid);
				break;
			}
		}

		scnr.close();
	}
	public void loadSceneFromText(String file) {
		Scanner scnr;
		try {
			scnr = new Scanner(file);
		} catch (Exception e) {
			System.err.println("Could not load scene file!");
			e.printStackTrace();
			return;
		}

		while (scnr.hasNextLine()) {
			// get line
			String line = scnr.nextLine();
			String[] contents = line.split(" ");
			// figure out what to do
			int[] instructions = new int[contents.length];
			for (int i = 0; i < instructions.length; i++) {
				instructions[i] = getInstruction(contents[i]);// first one should always be some instruction
			}
			if (instructions[0] != instruction_invalid) {// check to see if it is valid
				if (instructions[0] == instruction_set_name) {// ignores the brackets while parsing because I don't want
																// to write that
					parseInstruction(instructions[0], new String[] { contents[1] });// set the name
				}
				if (instructions[0] == instruction_add_object) {// next set should be valid
					if (instructions[1] != instruction_invalid && instructions[2] == instruction_bracket_open) {
						// check the instruction and check to see if there is a bracket there
						// now, check the next one and see what we should add
						if (instructions[1] == instruction_add_entity) {// entity

							System.out.println("Entity");
						}
						if (instructions[1] == instruction_add_track) {
							System.out.println("Entity");

						}
						if (instructions[1] == instruction_add_entity) {
							System.out.println("Entity");
							//entityLoader.loadEntity(instructions);
						}
					} else {
						System.err.println("Error parsing file! " + instruction_invalid);
						break;
					}
				}

				//////
			} else {
				System.err.println("Error parsing file! " + instruction_invalid);
				break;
			}
		}

		scnr.close();
	}

	private boolean parseInstruction(int instruction, String[] contents) {
		if (instruction == instruction_add_entity) {
			//return loadEntity(contents);
		}
		return false;
	}

	private int getInstruction(String arg) {// AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
		if (arg == "{")
			
			return instruction_bracket_open;
		if (arg == "}")
			return instruction_bracket_close;
		if (arg.contains("scene"))
			return instruction_set_name;
		if (arg.contains("add"))
			return instruction_add_object;
		if (arg.contains("entity"))
			return instruction_add_entity;

		return instruction_invalid;
	}
}
