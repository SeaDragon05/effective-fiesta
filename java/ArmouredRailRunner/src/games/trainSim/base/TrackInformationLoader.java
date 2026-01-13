package games.trainSim.base;


import java.io.File;
import java.util.List;
import java.util.Scanner;

import com.engine.data.TrackInformationData;
import com.engine.utils.Loader;

public class TrackInformationLoader {
	
	
	public static Track createTrackFromFile(String path, Loader loader) {
		File file;
		Scanner scnr = null;
		try {
			file = new File(path);
			scnr = new Scanner(file);
		} catch(Exception e) {
			e.printStackTrace();
		}

		scnr.close();
		return null;
	}
	
	public static void fillData(List<TrackInformationData> data, String fileName, String contents) {
		String[] lines = contents.split("\n");
		for (int i = 2; i < lines.length; i++) {
			String[] numbs = lines[i].split(",");
			int amount = 0;
			float degrees = 0;
			try {
				amount = Integer.parseInt(numbs[0]);
				degrees = Float.parseFloat(numbs[1]);
				data.add(new TrackInformationData(amount,degrees));
			} catch (Exception e) {
				System.err.println("ERROR LOADING TRACK:\n"
						+ "File " + fileName + " at line: " + i);
				e.printStackTrace();
				return;
			}
		}
	}
	
	public static int getAmountFromFile(String contents) {
		Scanner scnr = new Scanner(contents);
		scnr.nextLine();
		String line = scnr.nextLine();
		String[] args = line.split(" ");
		int result = 0;
		try {
			result = Integer.parseInt(args[2]);
		} catch (Exception e) {}
		scnr.close();
		return result;
	}
	
	public static float getDistanceFromFile(String contents) {
		Scanner scnr = new Scanner(contents);
		String line = scnr.nextLine();//gets the second line
		String[] args = line.split(" ");
		float result = 0;
		try {
			result = Float.parseFloat(args[2]);
		} catch (Exception e) {}
		scnr.close();
		return result;
	}
	
}
