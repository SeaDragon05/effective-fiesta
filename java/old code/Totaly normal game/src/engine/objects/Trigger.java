package engine.objects;

public class Trigger {
	private boolean value;
	private String name;
	public Trigger(String name, boolean value) {
		this.value = value;
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public boolean getValue() {
		return value;
	}
	public void setValue(boolean value) {
		this.value = value;
	}
	public void setName(String name) {
		this.name = name;
	}
}
