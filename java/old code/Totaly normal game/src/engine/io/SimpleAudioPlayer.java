package engine.io;

import java.io.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
public class SimpleAudioPlayer {
   Long currentFrame;
   Clip clip;
   AudioInputStream audioInputStream;
   String filePath;
   public SimpleAudioPlayer(String file) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
      audioInputStream = AudioSystem.getAudioInputStream(new File(file).getAbsoluteFile());
      clip = AudioSystem.getClip();
      clip.open(audioInputStream);
      filePath = file;
   }
   public void playaudio() {
         try {
        	 play();
         } catch (Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
       }
   }
   public static void playaudio(String file) {
	         try {
	            SimpleAudioPlayer audioPlayer = new SimpleAudioPlayer(file);
	            audioPlayer.play();
	         } catch (Exception ex) {
	            System.out.println("Error with playing sound.");
	            ex.printStackTrace();
	         }
	      }
	   
	
   public void play() {
      clip.start();     
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
