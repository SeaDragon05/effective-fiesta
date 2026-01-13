package games.trainSim.base;

import java.util.List;

import org.lwjglx.util.vector.Vector3f;

import com.engine.data.TrackPointData;
/*
 * find the current two points
 * get their position
 * get the position between both points
 * calculate the difference in degrees
 * move the new point perpindicular to both points
 * this new point acts as a third point
 * use quadratics to place track segments between all three points
 */
public class TrackCreator {
	public static Track createTrack(Vector3f startPosition, Vector3f startRotation, List<TrackPointData> data, float trackLength) throws Exception {
		Track track = new Track();
		for (int i = 0; i < data.size() - 1; i ++) {
			TrackPointData thirdPoint = null;
			
		}
		return track;
	}

	public static float calculateTrackDegrees(TrackPointData trackPointData, TrackPointData trackPointData2) {
		float x1 = trackPointData.getRotation().x;
		float x2 = trackPointData2.getRotation().x;
		return x2 - x1;
	}

	public static int calculateTrackLength(TrackPointData trackPointData, TrackPointData trackPointData2, float trackLength) {
		float distance = getDistance(trackPointData.getPosition(), trackPointData2.getPosition());
		float amount = distance / trackLength;
		int amountInt = (int) Math.floor(amount);
		if (amount - amountInt > 0) {
			amountInt += 1;
		}
		return amountInt;
	}

	public static float getDistance(Vector3f p1, Vector3f p2) {
		Vector3f dist = new Vector3f((p2.x - p1.x), (p2.y - p1.y), (p2.z - p1.z));
		return (float) Math.sqrt((dist.x * dist.x) + (dist.y * dist.y) + (dist.z * dist.z));
	}
}
