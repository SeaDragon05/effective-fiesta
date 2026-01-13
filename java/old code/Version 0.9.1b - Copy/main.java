import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
public class main extends JFrame implements KeyListener{
   private mainDraw draw;
   public void keyPressed(KeyEvent e) {
      if (mainDraw.draw == true) {
         if(e.getKeyCode()== KeyEvent.VK_RIGHT)
            draw.moveRight();
         else if(e.getKeyCode()== KeyEvent.VK_LEFT)
            draw.moveLeft();
         else if(e.getKeyCode()== KeyEvent.VK_S)
            draw.moveDown();
         else if(e.getKeyCode()== KeyEvent.VK_W)
            draw.moveUp();
         else if(e.getKeyCode()== KeyEvent.VK_A) 
            draw.lookLeft();
         else if(e.getKeyCode()== KeyEvent.VK_D)
            draw.lookRight();
         else if(e.getKeyCode()== KeyEvent.VK_UP) 
            draw.lookUp();
         else if(e.getKeyCode()== KeyEvent.VK_DOWN)
            draw.lookDown();
      }
   }
   public void keyReleased(KeyEvent e) {
      if (mainDraw.draw == true) {
         if(e.getKeyCode()== KeyEvent.VK_RIGHT)
            draw.moveRight();
         else if(e.getKeyCode()== KeyEvent.VK_LEFT)
            draw.moveLeft();
         else if(e.getKeyCode()== KeyEvent.VK_S)
            draw.moveDown();
         else if(e.getKeyCode()== KeyEvent.VK_W)
            draw.moveUp();
         else if(e.getKeyCode()== KeyEvent.VK_A) 
            draw.lookLeft();
         else if(e.getKeyCode()== KeyEvent.VK_D)
            draw.lookRight();
         else if(e.getKeyCode()== KeyEvent.VK_UP) 
            draw.lookUp();
         else if(e.getKeyCode()== KeyEvent.VK_DOWN)
            draw.lookDown();
      }
   }
   public void keyTyped(KeyEvent e) {
      if (mainDraw.draw == true) {
         if(e.getKeyCode()== KeyEvent.VK_RIGHT)
            draw.moveRight();
         else if(e.getKeyCode()== KeyEvent.VK_LEFT)
            draw.moveLeft();
         else if(e.getKeyCode()== KeyEvent.VK_S)
            draw.moveDown();
         else if(e.getKeyCode()== KeyEvent.VK_W)
            draw.moveUp();
         else if(e.getKeyCode()== KeyEvent.VK_A) 
            draw.lookLeft();
         else if(e.getKeyCode()== KeyEvent.VK_D)
            draw.lookRight();
         else if(e.getKeyCode()== KeyEvent.VK_UP) 
            draw.lookUp();
         else if(e.getKeyCode()== KeyEvent.VK_DOWN)
            draw.lookDown();
      }
   }
   public main(){
      this.draw=new mainDraw();
      addKeyListener(this);
      setFocusable(true);
      setFocusTraversalKeysEnabled(false);
   }
   static int crosstype = 2;
   static int seed = 45;
   static double range = 50;
   public static void main(String[] args) {
      javax.swing.SwingUtilities.invokeLater(
         new Runnable() {
            public void run() {
               System.out.println("Creating canvas");
               main Frame = new main();
               System.out.println("Canvas created");
               Frame.getContentPane().add(Frame.draw);
               Frame.setTitle("RealEngine");
               Frame.setResizable(true);
               Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
               Frame.setSize(400, 300);
               Frame.setMinimumSize(new Dimension(400, 300));
               Frame.pack();
               Frame.setVisible(true);
               System.out.println("Canvas ready");
            }
         });
   }
   public static int gettype() {
      return crosstype;
   }
   public static void setupRealEngine(RealEngine eng) {
      RealEngine.multithread = true;
      RealEngine.shadows = false;
      RealEngine.waterReflections = true;
      RealEngine.watercolor = eng.packint(100, 100, 200);
      RealEngine.watertransparency = 5;//1 through 10: 1 solid, 10 invisible
      RealEngine.sb_x = 0;
      RealEngine.sb_y = 0;
      RealEngine.sb_z = 0;
      RealEngine.sb_viewfactor = 550;
      RealEngine.sb_move_offset = 10;
      RealEngine.GPU = false;
      eng.setAngles(eng.pack(5, 150, 0));
      eng.setLoc(eng.pack(-17.0, 0.1, 45.0));
      eng.setFov(140.0);
      eng.setMaxDist(10);
      eng.setBrightness(7.0);
      eng.setMaxLights(2);
      eng.dev(0);
      RealEngine.detail = 0;
      eng.adjust(0);
      System.out.println("CPU Cores: " + eng.cores);
      return;
   }
   public static String getRandomName() {
      Random rand = new Random();
      String[] first = new String[10];
      first[0] = "hungry";
      first[1] = "smart"; 
      first[2] = "huge";
      first[3] = "dumb";
      first[4] = "edgy";
      first[5] = "fast";
      first[6] = "easy";
      first[7] = "difficult";
      first[8] = "communist";
      first[9] = "mindful";
      String[] last = new String[10];
      last[0] = "birb";
      last[1] = "doggo";
      last[2] = "cpu";
      last[3] = "RAM";
      last[4] = "javascript isnt like java";
      last[5] = "minecraft was made in java";
      last[6] = "Java objects";
      last[7] = "Python is painful";
      last[8] = "Half-Life 2: Episode 3";//this costs alyx vance
      last[9] = "zucc juice";
      return first[rand.nextInt(10)] + " " + last[rand.nextInt(10)];
   }
}