package com.own.master.horrorgame.ai;

import java.util.ArrayList;

import com.own.master.engine.io.AudioPlayer;
import com.own.master.engine.objects.GameObject;
import com.own.master.engine.objects.Gameable;

public class GameThread implements Runnable, Gameable {
	// musics and soonds:
	public static final String spawn = "./sounds/voice/spoops/spawn.wav";
	public static final String despawn = "./sounds/voice/spoops/removed.wav";

	public static final String ambient_huge_1 = "./sounds/ambient/ambient4.wav";
	public static final String ambient_huge_2 = "./sounds/general/horror/ambient7.wav";
	public static final String ambient_huge_3 = "./sounds/general/horror/ambient9.wav";

	public static final String ambient_far_1 = "./sounds/ambient/Distant_ambient10.wav";
	public static final String ambient_far_2 = "./sounds/general/horror/ambient1.wav";
	public static final String ambient_far_3 = "./sounds/general/horror/ambient11.wav";

	public static final String ambient_world_1 = "./sounds/ambient/wind/ambient5.wav";
	public static final String ambient_world_2 = "./sounds/ambient/wind/ambient6.wav";

	public static final String horror_chill_1 = "./sounds/general/horror/ambient3.wav";
	public static final String horror_chill_2 = "./sounds/music/ambient8.wav";

	public static final String music_1 = "./sounds/music/ambient2.wav";

	public static final String music_none = "";
	public static final String music_low = "./sounds/music/spoops/low.wav";
	public static final String music_medium = "";
	public static final String music_high = "";

	public static int spoopCount = 0;
	public static int cycleCount = 0;

	boolean isAlive = true;

	public GameThread() {
	}

	@Override
	public void run() {
		while (isAlive) {
			this.game();
		}
	}

	@Override
	public void kill() {
		isAlive = false;
	}

	@Override
	public void game() {// this is a port and a modified version of Chrisbot6's renderman code for
						// blockland
		// add to the cycle count:
		cycleCount += 1;
		// play the game audio to tell what the threat level is
		if (cycleCount >= 4) {// this is so that we dont layer too much audio
			cycleCount = 0;
			if (spoopCount == 1) {
				AudioPlayer.playaudio(music_low);
			} else if (spoopCount == 2) {
				AudioPlayer.playaudio(music_medium);
			} else if (spoopCount >= 4) {
				AudioPlayer.playaudio(music_high);
			} else {
				AudioPlayer.playaudio(music_none);
			}
		}
		//spawn in spoops?
		//harmless spoop:
		if (Math.random() * 2 < 1) {
			createHarlmessSpoop();
			// wait the cycle time
			cycle();
			return;
		}
		//harmful spoop:
		if (Math.random() * 6 < 1) {
			createSpoop();
			//update the spoop count:
			spoopCount += 1;
			// wait the cycle time
			cycle();
			return;
		}
		
	}

	public static void createSpoop() {
		
	}

	public static void createHarlmessSpoop() {
		
	}

	@Override
	public int getValue(ArrayList<GameObject> hand) {
		return -1;
	}

	@Override
	public void checkTable() {
	}// do not need this

	@Override
	public GameThread self() {
		return this;// HOW DOES THIS WORK?!?!?!
	}

	public void cycle() {// this is so that spoops dont compute and move at the speed of light
		try {
			Thread.sleep(300);
		} catch (Exception e) {
			System.err.println("Exception!!");
			System.exit(441044);
		}
	}
}
