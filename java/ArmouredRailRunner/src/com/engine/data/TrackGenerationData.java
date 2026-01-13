package com.engine.data;

import java.util.ArrayList;
import java.util.List;

public class TrackGenerationData {
	/*
	 * Data type to help create track lines
	 * each index in both lists represents a track point guide
	 * used to help generate tracks without the need to create multiple java classes or methods
	 */
	private List<TrackInformationData> data;
	public TrackGenerationData() {
		data = new ArrayList<TrackInformationData>();
	}
	public void addPoint(int amount, float degrees) {
		this.data.add(new TrackInformationData(amount, degrees));
	}
	public TrackInformationData getDataAt(int index) {
		return this.data.get(index);
	}
	public List<TrackInformationData> getList() {
		return this.data;
	}
	
}
