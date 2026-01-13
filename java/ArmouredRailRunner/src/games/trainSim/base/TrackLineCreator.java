package games.trainSim.base;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjglx.util.vector.Vector3f;

import com.engine.math.Maths;

import main.EngineTest;

public class TrackLineCreator {
	private List<TrackLinePoint> linePoints;
	private List<TrackPoint> line;
	private float distance;
	private float amount;

	public TrackLineCreator(float distance, int amount) {
		this.linePoints = new ArrayList<TrackLinePoint>();
		this.line = new ArrayList<TrackPoint>();
		this.distance = distance;
		this.amount = amount;
	}

	public List<TrackPoint> createLine() {
		for (int i = 0; i < linePoints.size() - 1; i++) {
			TrackLinePoint pt1 = linePoints.get(i);
			TrackLinePoint pt2 = linePoints.get(i + 1);
			float loop = (float) TrackLinePoint.getDistance(pt1, pt2) / this.amount;
			float distance = TrackLinePoint.getDistance(pt1, pt2);
			float x = 0, z = 0;
			Vector3f angles = pt1.getRotation();

			for (int t = 0; t < loop; t++) {
				float trd = t * this.distance;
				Vector3f CurrentAngles = Maths.blend(pt1.getRotation(), pt2.getRotation(), trd / distance);
				x += (float) ((distance) * Math.sin(Math.toRadians(CurrentAngles.y)));
				z += (float) ((distance) * Math.cos(Math.toRadians(CurrentAngles.y))); 
				line.add(new TrackPoint(new Vector3f(x, EngineTest.terrain.getHeightTerrain(x, z), z), CurrentAngles, this.distance, t < loop - 2));
			}
			/*
			 * this.distance = distance; 
			 * Random rnd = new Random(); TrackPoint start = new TrackPoint(startPoint); 
			 * line.add(start); 
			 * float x = 0; 
			 * float z = -100; 
			 * float angle = 180; 
			 * List<Vector3f> listOfAngles = lineMaker.createLine(); 
			 * for (int i = 0; i < amount; i ++) { //
			 * 		angle += rnd.nextFloat(5) - (5/2f); 
			 * 		x += (float) ((distance) * Math.sin(Math.toRadians(angle))); 
			 * 		z += (float) ((distance) * Math.cos(Math.toRadians(angle))); 
			 * 		line.add(new TrackPoint(new Vector3f(x, height, z), new Vector3f(0, angle, 0), distance, i < amount - 2)); 
			 * }
			 */
		}

		return line;
	}

	public void addPoint(TrackLinePoint point) {
		this.linePoints.add(point);
	}
}
