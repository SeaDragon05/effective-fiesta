package objects;

public class SensorTypeDoesNotExistException extends Exception {
	public SensorTypeDoesNotExistException(byte type) {
		super("Sensor type not recognized! Got: " + type);
	}
}
