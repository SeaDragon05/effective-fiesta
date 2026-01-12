package objects;

public class Sensor {
	public static final byte Type_Touch = 0;
	private byte type;
	private byte port;
	public Sensor(byte type, byte port) throws PortDoesNotExistException, SensorTypeDoesNotExistException {
		
	}
}
