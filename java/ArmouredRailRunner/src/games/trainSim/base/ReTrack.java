package games.trainSim.base;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjglx.util.vector.Vector3f;

import com.engine.data.TrackInformationData;
import com.engine.data.TrackLocationData;
import com.engine.objects.Entity;
import com.engine.objects.TexturedModel;
import com.engine.utils.MasterLoader;

import main.Engine;

public class ReTrack {
	public static final Vector3f startPoint = new Vector3f(0, 0, 0);
	public static final Vector3f startRotation = new Vector3f(0, 0, 0);
	public ReTrack(String file) {
		this.path = file;
		parseFileAndCreateLine();
	}
	private List<TrackPoint> line = new ArrayList<TrackPoint>();
	private float distance;
	private String path;
	
	public void parseFileAndCreateLine() {
		
		List<String> source = new ArrayList<String>();
		List<TrackInformationData> trackData = new ArrayList<TrackInformationData>();
		boolean isStart = false;
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(path));
			String line;
			while ((line = reader.readLine()) != null) {
				source.add(line);
			}
			reader.close();
		} catch (Exception e) {
			System.err.println("Could not read track file!");
			e.printStackTrace();
			System.exit(-200);
		}
		
		int line = 0;
		
		for (String item : source) {
			line++;
			String[] contents = item.split("[,\\s]+");
			//error checking
			if (contents[0].toLowerCase().contains("end")) {
				break;
			}
			if (contents.length < 4 && !item.toLowerCase().contains("start") ) {
				System.err.println("Error in parse of track file: " + "Line: " + line + " '" + item + "' Is not a valid line!");
				System.err.println("Track file syntax is as follows:"
						+ "\nstart\n"
						+ "trackPoint: *length* rx, ry\n"
						+ "end\n"
						+ "Anything else it will error out!");
				System.exit(-200);
			}
			if (contents[0].toLowerCase() == "start") {
				continue;
			}
			//parse the file:
			//no errors, then parse the line:
			//look for an end command:
			//no end command, continue:
			if (contents[0].toLowerCase().contains("trackpoint")) {
				//defining the track point
				//first, get the distance:
				try {
					int amount = Integer.parseInt(contents[1]);
					float degrees = Float.parseFloat(contents[2]);
					float slope = Float.parseFloat(contents[3]);
					trackData.add(new TrackInformationData(amount, degrees, slope));
				} catch (Exception e) {
					System.err.println("Error in parse of track file: " + "Line: " + line + " '" + item + "' Is not valid!");
					e.printStackTrace();
					System.exit(-200);
				}
			}
		}
		//Track information file is parsed and there were no errors at this point, now we need to create the track itself:
		TrackPoint start = new TrackPoint(startPoint);
		Random rnd = new Random(9903241);
		this.line.add(start);
		float x = 0;
		float y = 0;
		float z = 0;
		float currentSlope = 0;
		float currentTurn = 0;
		float tiltZ = 0;
		float tiltRand = 0;
		float maxDegreeAmount = 10;
		distance = 12.01f;
		for (int trA = 0; trA < trackData.size(); trA++) {
			for (int i = 0; i < (trackData.get(trA).getAmount() >= maxDegreeAmount ? trackData.get(trA).getAmount() : maxDegreeAmount); i++) {
				currentSlope += trackData.get(trA).getSlope();
				currentTurn += trackData.get(trA).getDegrees();			    
			    // Calculate the new coordinates using the updated slope and turn
				x += (float) (distance * Math.sin(Math.toRadians(currentTurn)) * Math.cos(Math.toRadians(currentSlope)));
			    y += (float) (distance * Math.sin(Math.toRadians(currentSlope)));
			    z += (float) (distance * Math.cos(Math.toRadians(currentTurn)) * Math.cos(Math.toRadians(currentSlope)));

			    // Create a TrackPoint and add it to the line
			    TrackPoint tr = new TrackPoint(new Vector3f(x, y, z), new Vector3f(-currentSlope, currentTurn, 0), distance, i < trackData.size() - 1);
			    this.line.add(tr);
			}
		}
		//not QUITE done yet, we now have the data of the track, but we need a 3d visual representation of it
		//create 3d objects in the 3d world where each track point thing is so that we have a train on tracks
		
		TexturedModel track3D = MasterLoader.loadTexturedModel("TrainSim/rail/wet/model", "TrainSim/rail/wet/texture");
		track3D.getTexture().setRoughMap(MasterLoader.loadTexturePointer("TrainSim/rail/wet/roughness"));
		// track3D.getTexture().setNormalMap(loader.loadTexture("gameModels/track/bumpTexture"));
		// track3D.getTexture().setNormalAmount(1);
		track3D.getTexture().setReflectiveMap(MasterLoader.loadTexturePointer("TrainSim/rail/wet/shiny"));
		track3D.getTexture().setReflectivity(1);
		
		for (int i = 1; i < this.line.size(); i += 1) {
			Engine.entities.add(new Entity(track3D, this.line.get(i).getPosition(), this.line.get(i).getRotation(), 1f));
		}
		System.out.println("There are " + this.line.size() + " track objects");
	}
	
	public TrackLocationData getTrackFromDistance(float distance) {
		int loops = 0;
		int index = 0;
		while (true) {
			loops++;
			if (distance > this.distance) {
				distance -= this.distance;
				index += 1;
			}
			if (distance < this.distance) {
				return new TrackLocationData(index, distance);
			}
			if (index > this.line.size() - 2) {
				index = 0;
			}
			if (loops >= 65535) {
				System.err.println("FATAL ERROR: UNKNOWN");
				System.exit(-9001);
			}
		}
	}
	
	public List<TrackPoint> getLine() {
		return line;
	}

	public float getDistance() {
		return distance;
	}
}
