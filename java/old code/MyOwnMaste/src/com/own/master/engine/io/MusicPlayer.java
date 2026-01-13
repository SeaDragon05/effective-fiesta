package com.own.master.engine.io;

import java.util.Random;

public class MusicPlayer {
	public static String[] musicList = new String[5];
	public static AudioPlayer player;
	public static long time1 = 0;
	public static long clipTime;
	public static boolean isPlaying = false;
	public static Random rnd = new Random();

	public static void setup() {
		player = new AudioPlayer();
		musicList[0] = "./sounds/music/SmashFull.wav";// copyrighted, from civ 3, cannot ship
		musicList[1] = "./sounds/music/StarsFull.wav";// copyrighted, from civ 3, cannot ship
		musicList[2] = "./sounds/music/music1.wav";// free to use, but not comercialy
	}

	public static void playRandom() {
		try {
			if (!isPlaying) {
				isPlaying = true;
				setup();
				player = new AudioPlayer(musicList[rnd.nextInt(3)]);
				clipTime = player.length / 1000;
				time1 = System.currentTimeMillis() + clipTime;
				//System.out.println("The current time is " + System.currentTimeMillis() + "\nAnd the length of the sound is " + clipTime + "\nAnd lastly the stop time is " + time1);
				player.play();
			}
			long time2 = System.currentTimeMillis();
			if (time2 > time1) {
				System.out.println("Stopped music");
				player.stop();
				isPlaying = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
}
