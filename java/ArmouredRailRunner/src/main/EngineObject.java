package main;

public class EngineObject {
	Thread thread;
	
	
	public EngineObject() {
	}
	
	public Thread getThread() {
		return this.thread;
	}
	public void set(String thread_name) {
		this.thread = new Thread("game");
	}
}
