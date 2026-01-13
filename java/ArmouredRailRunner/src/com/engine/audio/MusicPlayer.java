package com.engine.audio;

import org.lwjglx.util.vector.Vector3f;

public class MusicPlayer {
	static Source source = AudioMaster.newSoundSource(1, 1, 1, false, new Vector3f(0,0,0));
	public static void playMusic(int buffer) {
		if (!source.isPlaying()) {
			source.play(buffer);
		}
	}
}
