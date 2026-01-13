package com.engine.data;
/*
 * data type that has 2 values
 * int index and float distance
 * index refers to the arraylist index of the given track
 * distance is how far the train object is on the track
 * Similar to TrackInformationData, but has a different use
 * I guess you could just use one class, but this makes things more organized
 */
public class TrackLocationData {
	private int index;
	private float distance;
	public TrackLocationData(int index, float distance) {
		this.index = index;
		this.distance = distance;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public float getDistance() {
		return distance;
	}
	public void setDistance(float distance) {
		this.distance = distance;
	}
}
