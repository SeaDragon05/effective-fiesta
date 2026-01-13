package com.engine.utils;

import java.util.ArrayList;

public abstract class Event {
	static final int once = 1;
	static final int nope = 2;
	private boolean is0 = false, is1 = false;
	private int type = 0;
	public Event(int type) {
		this.type = type;
	}
	public abstract void fire();
	protected boolean isSet() {
		return this.is0;
	}
	protected boolean isFired() {
		return this.is1;
	}
	
}
