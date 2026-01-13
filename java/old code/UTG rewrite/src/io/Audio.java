package io;

import java.io.*;

import main.Main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
//This code is NOT MINE
public class Audio {
   Long currentFrame;
   Clip clip;
   String status;
   AudioInputStream audioInputStream;
   static String filePath;
   public Audio()
        throws UnsupportedAudioFileException,
        IOException, LineUnavailableException 
   {
      audioInputStream = 
                AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
      clip = AudioSystem.getClip();
      clip.open(audioInputStream);
   }
   public static void playaudio(String file) {
      if (Main.advanced_setting_general_music) {
         try {
            filePath = file;
            Audio audioPlayer = new Audio();
            audioPlayer.play();
         } catch (Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
         }
      }
   }
   public void play() {
      clip.start();          
      status = "play";
   }
   public void stop() throws UnsupportedAudioFileException,
    IOException, LineUnavailableException 
   {
      currentFrame = 0L;
      clip.stop();
      clip.close();
   }
   public void resetAudioStream() throws UnsupportedAudioFileException, IOException,
                                            LineUnavailableException 
   {
      audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
      clip.open(audioInputStream);
      clip.loop(Clip.LOOP_CONTINUOUSLY);
   }
}
