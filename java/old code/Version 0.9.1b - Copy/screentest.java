import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.ArrayList;
import java.io.*;
import java.net.URL;
import javax.sound.sampled.*;
import javax.swing.*;
public class screentest {
   static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
   public static void main(String[] args) {
      System.out.println(screenSize.getWidth() + " x " +screenSize.getHeight());
   }
}