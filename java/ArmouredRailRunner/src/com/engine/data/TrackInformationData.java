package com.engine.data;
/*
 * data type that holds 2 values
 * int amount and float degrees
 * used in creation of static track
 */
public class TrackInformationData {
	public TrackInformationData(int amount, float degrees) {
		this.amount = amount;
		this.degrees = degrees;
	}
	public TrackInformationData(int amount, float degrees, float slope) {
		this.amount = amount;
		this.degrees = degrees;
		this.slope = slope;
	}
	private int amount;
	private float degrees;
	private float slope;
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public float getDegrees() {
		return degrees;
	}
	public float getSlope() {
		return slope;
	}
	public void setSlope(float slope) {
		this.slope = slope;
	}
	public void setDegrees(float degrees) {
		this.degrees = degrees;
	}
}
