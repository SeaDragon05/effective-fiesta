package games.trainSim.base;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjglx.util.vector.Vector3f;

import com.engine.data.TrackInformationData;
import com.engine.data.TrackLocationData;
import com.engine.objects.Entity;
import com.engine.objects.TexturedModel;
import com.engine.utils.MasterLoader;
import com.engine.utils.TrackDistanceOutOfBoundsException;

import main.Engine;

public class Track {
	public static final Vector3f startPoint = new Vector3f(0, 0, 0);
	public static final Vector3f startRotation = new Vector3f(0, 0, 0);

	private List<TrackPoint> line = new ArrayList<TrackPoint>();
	private float distance;

	public static Track createTrackFromString(String file, Vector3f position) {
		String fileName = "USERCREATEDFILE";
		float distance = TrackInformationLoader.getDistanceFromFile(file);
		List<TrackInformationData> data = new ArrayList<TrackInformationData>();
		TrackInformationLoader.fillData(data, fileName, file);
		return new Track(distance, position, data);
	}

	public static Track createRandomizedTrack(int amount, Vector3f position) {
		Random rnd = new Random();
		String fileName = "LOCALTESTFILE";
		String fileContents = "distance = 12.01" + "\namount = 400" + "\n60,0" + "\n30,1" + "\n30,0";
		for (int i = 0; i < amount; i++) {
			int length = rnd.nextInt(30) + 10;
			double angle = rnd.nextDouble(2) - 1;
			fileContents += "\n" + length + "," + angle;
		}
		float distance = TrackInformationLoader.getDistanceFromFile(fileContents);
		List<TrackInformationData> data = new ArrayList<TrackInformationData>();
		TrackInformationLoader.fillData(data, fileName, fileContents);
		return new Track(distance, position, data);
	}

	public Track() {
	}

	public void invert() {
		List<TrackPoint> nLine = new ArrayList<TrackPoint>();
		int size = this.line.size();
		for (int i = 0; i < size; i += 1) {
			nLine.add(this.line.get(this.line.size() - 1));
			this.line.remove(this.line.size() - 1);
			Vector3f rot = nLine.get(i).getRotation();
			rot.z = -rot.z;
			nLine.get(i).setRotation(rot);
		}
		this.line = nLine;
	}

	public Track(float distance, Vector3f position, List<TrackInformationData> data) {
		this.distance = distance;
		TrackPoint start = new TrackPoint(startPoint);
		Random rnd = new Random(9903241);
		line.add(start);
		float x = position.x;
		float y = position.y;
		float z = position.z;
		float angleY = 0;
		float tilt = 0;
		float tiltRand = 0;
		for (int trA = 0; trA < data.size(); trA++) {
			for (int i = 0; i < (data.get(trA).getAmount() >= 10 ? data.get(trA).getAmount() : 10); i++) {
				angleY += data.get(trA).getDegrees();
				x += (float) ((distance) * Math.sin(Math.toRadians(angleY)));
				// (float) ((distance) * Math.cos(Math.toRadians(0)));
				// y += (float) ((distance) * Math.cos(Math.toRadians(angleY)));
				z += (float) ((distance) * Math.cos(Math.toRadians(angleY)));
				try {
					if (data.get(trA - 1).getDegrees() != 0 && i <= 10) {
						// angleY += data.get(trA - 1).getDegrees();
						tilt += data.get(trA - 1).getDegrees();
					}
				} catch (Exception e) {
				}
				try {
					if (data.get(trA + 1).getDegrees() != 0 && i >= data.get(trA).getAmount() - 10 && tilt != 0) {
						if (tilt != data.get(trA + 1).getDegrees() * 10) {

							tilt -= data.get(trA + 1).getDegrees();
						}
					}
				} catch (Exception e) {
				}
				if (data.get(trA).getDegrees() != 0) {
					//////////////////////////////////////////////////////////////////////////////////////
					tilt = ((data.get(trA).getDegrees() * -10));
					//////////////////////////////////////////////////////////////////////////////////////
				}
				tiltRand = (rnd.nextFloat(0.5f) - 0.25f) / 10;// randomize it a bit
				tilt += tiltRand;
				TrackPoint tr = new TrackPoint(new Vector3f(x, y, z), new Vector3f(0, angleY, tilt), distance,
						// TrackPoint tr = new TrackPoint(new Vector3f(x, y, z), new Vector3f(0, angleY,
						// 0), distance,
						i < data.get(trA).getAmount() - 2);
				line.add(tr);
			}
		}
		TexturedModel trackS = null;
		TexturedModel trackSnow = null;

		trackS = MasterLoader.loadTexturedModel("TrainSim/rail/wet/model", "TrainSim/rail/wet/texture");
		// new
		// TexturedModel(NormalMappedObjLoader.loadObjModel("gameModels/track/modelLOD1",
		// loader),
		// new ModelTexture(loader.loadTexture("gameModels/track/trackTexture")));

		trackS.getTexture().setRoughMap(MasterLoader.loadTexturePointer("TrainSim/rail/wet/roughness"));
		// trackS.getTexture().setNormalMap(loader.loadTexture("gameModels/track/bumpTexture"));
		// trackS.getTexture().setNormalAmount(1);
		trackS.getTexture().setReflectiveMap(MasterLoader.loadTexturePointer("TrainSim/rail/wet/shiny"));
		trackS.getTexture().setReflectivity(1);

		trackSnow = MasterLoader.loadTexturedModel("gameModels/track/trackSnow", "snow01");
		// new TexturedModel(OBJLoader.loadObjModel("gameModels/track/trackSnow"),
		// new ModelTexture(loader.loadTexture("snow01")));

		//trackSnow.getTexture().setReflectivity(1f);
		//trackSnow.getTexture().setReflectiveMap(MasterLoader.loadTexturePointer("textures/snow/specular"));
		//trackSnow.getTexture().setRoughness(0.2f);
		//trackSnow.getTexture().setRoughMap(MasterLoader.loadTexturePointer("textures/snow/specular"));

		for (int i = 1; i < line.size(); i += 1) {
			Vector3f rotation = line.get(i).getRotation();
			Vector3f pos = line.get(i).getPosition();
			Entity newTrack = new Entity(trackS, pos, rotation, 1f);
			Entity snow = new Entity(trackSnow, pos, rotation, 1f);
			//newTrack.setDrawToShadowShader(false);// optimization
			Engine.entities.add(newTrack);
			Engine.entities.add(snow);
		}
		trackS = null;//memory management
	}

	public TrackLocationData getTrackFromDistance(float distance) {
		int index = 0;
		while (true) {
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
		}
	}

	public Vector3f getTrackLocationFromDistance(TrackLocationData point) throws TrackDistanceOutOfBoundsException {
		if (point.getDistance() > distance) {
			throw new TrackDistanceOutOfBoundsException("Distance given (" + point.getDistance()
					+ ") exceeds maximum distance allowed" + "\nMax distance allowed is " + distance + "!",
					new Exception());
		}
		if (point.getDistance() == distance) {
			return line.get(point.getIndex() + 1).getPosition();
		}
		return line.get(point.getIndex()).getTrackPosition3f(point.getDistance());
	}

	public List<TrackPoint> getLine() {
		return line;
	}

	public float getDistance() {
		return distance;
	}

}
