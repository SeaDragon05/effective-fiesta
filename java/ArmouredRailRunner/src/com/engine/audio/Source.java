package com.engine.audio;

import org.lwjgl.openal.AL10;
import org.lwjglx.util.vector.Vector3f;

/*
 * Source class
 * Sources are sound sources that play sounds
 * This object can only be initiaded by the audiomaster class
 * This is because we need to delete all instances of each sound source when the game closes
 * Having all sound sources initialized by one class allows us to do this.
 */
public class Source {
	private int sourceId;
	private float volume = 1f;
	public static float GLOBAL_VOLUME = 0.5f;
	public static final float MAX_VOLUME = 1f;
	private Vector3f pos;

	// roll off? distance? max distance
	protected Source(float ROF, float DIST, float MD) {
		try {
			sourceId = AL10.alGenSources();
			AL10.alSourcef(sourceId, AL10.AL_GAIN, volume * GLOBAL_VOLUME);
			AL10.alSourcef(sourceId, AL10.AL_ROLLOFF_FACTOR, ROF);
			AL10.alSourcef(sourceId, AL10.AL_REFERENCE_DISTANCE, DIST);
			AL10.alSourcef(sourceId, AL10.AL_MAX_DISTANCE, MD);
		} catch (IllegalStateException e) {
			System.err.println("Error: Audio system is not supported");
		} catch (Exception e) {
			System.err.println("Error: Audio system is not supported");
		}
	}

	public void play(int buffer) {
		stop();
		AL10.alSourcei(sourceId, AL10.AL_BUFFER, buffer);
		resume();
	}

	public void pause() {
		AL10.alSourcePause(sourceId);
	}

	public void resume() {
		AL10.alSourcePlay(sourceId);
	}

	public void stop() {
		AL10.alSourceStop(sourceId);
	}

	public boolean isPlaying() {
		return AL10.alGetSourcei(sourceId, AL10.AL_SOURCE_STATE) == AL10.AL_PLAYING;
	}

	public void delete() {
		stop();
		AL10.alDeleteSources(sourceId);
	}

	public void setVelocity(Vector3f vel) {
		AL10.alSource3f(sourceId, AL10.AL_VELOCITY, vel.x, vel.y, vel.z);
	}

	public void setVelocity(float x, float y, float z) {
		AL10.alSource3f(sourceId, AL10.AL_VELOCITY, x, y, z);
	}

	public void setLooping(boolean value) {
		AL10.alSourcei(sourceId, AL10.AL_LOOPING, value ? AL10.AL_TRUE : AL10.AL_FALSE);
	}

	public void setVolume(float volume) {
		if (volume > MAX_VOLUME) {
			volume = 1f;
		}
		this.volume = volume;
		AL10.alSourcef(sourceId, AL10.AL_GAIN, volume * GLOBAL_VOLUME);
	}

	public void setPitch(float pitch) {
		AL10.alSourcef(sourceId, AL10.AL_PITCH, pitch);
	}

	public void setPosition(Vector3f pos) {
		// AL10.alSource3f(sourceId, AL10.AL_POSITION, pos.x, pos.y, pos.z);
		this.pos = pos;
	}

	public void setPosition(float x, float y, float z) {
		// AL10.alSource3f(sourceId, AL10.AL_POSITION, x, y, z);
		this.pos = new Vector3f(x, y, z);
	}

	public void setStaticPosition(Vector3f position) {// should only be used in audiomaster
		AL10.alSource3f(sourceId, AL10.AL_POSITION, position.x, position.y, position.z);
	}

	public Vector3f getPosition() {
		return this.pos;
	}

	public float getVolume() {
		return this.volume;
	}

	protected void update() {
		AL10.alSourcef(sourceId, AL10.AL_GAIN, this.volume * GLOBAL_VOLUME);
	}
}
