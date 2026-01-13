package code;

public interface SamInterface {
	public abstract void setInput(String input);
	public abstract void setSpeed(int speed);
	public abstract void setPitch(int pitch);
	public abstract void setMouth(int mouth);
	public abstract void setThroat(int throat);
	public abstract void enableSingMode();
	
	public abstract int SAMMain() throws Exception;
	
	public abstract short[] getBuffer();
	
	public abstract int getBufferLength();
}
