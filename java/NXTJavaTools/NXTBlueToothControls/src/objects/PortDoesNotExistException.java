package objects;

public class PortDoesNotExistException extends Exception {
	public PortDoesNotExistException(byte port) {
		super("Given port does not exist! Got " + port);
	}
}
