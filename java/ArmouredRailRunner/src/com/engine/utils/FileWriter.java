package com.engine.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import main.Engine;

/*
 * This class is used to create and write to files
 * Mainly used to create a log file and write down most of what happens
 * Code taken from https://www.w3schools.com/java/java_files_create.asp
 */
public class FileWriter {
	public static void createFile(String file) {
		try {
			File myObj = new File(file);
			myObj.createNewFile();
			java.io.FileWriter myWriter = new java.io.FileWriter(file);
			myWriter.write("---Report errors with this---\n\n\n");
			myWriter.close();
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}

	public static void writeToFile(String file, String line) {
		try {
			String result = read(file, line);
			java.io.FileWriter myWriter = new java.io.FileWriter(file);
			myWriter.write(result);
			myWriter.close();
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}

	public static String read(String file, String line) {
		try {
			String result = "";
			File myObj = new File(file);
			Scanner myReader = new Scanner(myObj);
			while (myReader.hasNextLine()) {
				String data = myReader.nextLine();
				result = result + data + "\n";
			}
			myReader.close();
			return result + line;
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
			return "";
		}
	}
}
