package com.engine.utils;

public class TrackDistanceOutOfBoundsException extends Exception {
	private static final long serialVersionUID = 1L;

	public TrackDistanceOutOfBoundsException(String errorMessage, Throwable err) {
		super(errorMessage, err);
	}
}
