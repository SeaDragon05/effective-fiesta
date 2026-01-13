package com.engine.utils;

public class TrackDoesNotHaveNextException extends Exception {
	private static final long serialVersionUID = 1L;
	public TrackDoesNotHaveNextException(String errorMessage, Throwable err) {
		super(errorMessage, err);
	}
}
