package com.engine.utils;

import java.util.ArrayList;
import java.util.List;

public class EventMaster {
	private List<Event> events;
	public EventMaster() {
		events = new ArrayList<Event>();
	}
	public void addEvent(Event event) {
		this.events.add(event);
	}
	
	public void exec() {
		for (int i = 0; i < events.size(); i++) {
			
		}
	}
}
