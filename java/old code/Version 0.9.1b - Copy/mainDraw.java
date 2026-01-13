import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import java.io.*;
import java.awt.image.*;
import javax.imageio.*;


public class mainDraw extends JPanel {
   static RealEngine Main = new RealEngine();
   static boolean setup = false;
   static boolean shadows = false;
   static double ammount = 0.0;
   static double amount1 = 1.0;
   static double amount2 = 0.0;
   public int x = 50;
   public int y = 50;
   static boolean half = false;
   static boolean draw1 = false;
   static boolean draw2 = false;
   static boolean draw3 = false;
   static boolean draw4 = false;
   static boolean drawn1 = false;
   static boolean drawn2 = false;
   static boolean drawn3 = false;
   static boolean drawn4 = false;
   static boolean light1 = false;
   static boolean light2 = false;
   static boolean light3 = false;
   static boolean light4 = false;
   static boolean lights1 = false;
   static boolean lights2 = false;
   static boolean lights3 = false;
   static boolean lights4 = false;
   static boolean light5 = false;
   static boolean light6 = false;
   static boolean notpressed;
   static boolean draw = false;
   static double thingy = 0;
   static boolean thg = false;
   static long scoreTime = System.currentTimeMillis();
   private int thread;
   static int frames = 0;
   static int score = 0;
   static int len = Main.length / (Main.cores / 2) - 1;
   static int wid = Main.width / (Main.cores / 2) - 1;
   static boolean compile = true;
   static boolean compile2 = compile;
   private BufferedImage image;
   public mainDraw() {
      try {                
         image = ImageIO.read(new File("logo.png"));
      } catch (IOException ex) {
            // handle exception...
      }
   }
   double[] fade = RealEngine.pack(0,0,0);
   @Override
   public void paintComponent(Graphics g) {
      super.paintComponent(g);
      sb();
      RealEngine.length = getWidth();
      RealEngine.width = getHeight();
      Color thin = new Color (125, 125, 125);
      long t1 = System.currentTimeMillis();
      long t2;
      Main.cores = Runtime.getRuntime().availableProcessors();
      if (draw) {
         g.setColor(thin);
         g.fillRect(0, 0, getWidth(), getHeight());
         if (setup == false) {
            main.setupRealEngine(Main);
            //mainDraw_map_outside();
            map_WareHouse.compile(Main);
            System.out.println("Map compiled");
            System.out.println(RealEngine.polynumber);
            if (differ(RealEngine.polynumber)) {
               RealEngine.polynumber += 1;
               System.out.println("Adjusting the polycount by one");
               if (differ(RealEngine.polynumber)) {
                  RealEngine.polynumber += 1;
                  System.out.println("Adjusting the polycount by one again");
               }
            }
            setup = true;
         }
         if (RealEngine.multithread) {
            Main.setupangles();
            Main.clear(0, 0, 0);
            List<Thread> threads = new ArrayList<Thread>();
            try {
               for (int i = 0; i < Main.cores; i += 1) {
                  Thread core = new Thread(new render(i));//get all threads that we will use
                  core.start();
                  threads.add(core);
               }
               for (int i = 0; i < Main.cores; i++) {  
                  threads.get(i).join(); 
               } 
            } catch (Exception e) {
               System.out.println(e.getMessage());
            }
            if (Main.waterReflections) {
               List<Thread> thread_reflections = new ArrayList<Thread>();
               try {
                  for (int i = 0; i < Main.cores; i += 1) {
                     Thread core = new Thread(new render_reflections(i));//get all threads that we will use
                     core.start();
                     thread_reflections.add(core);
                  }
                  for (int i = 0; i < Main.cores; i++) {  
                     thread_reflections.get(i).join(); 
                  } 
               } catch (Exception e) {
                  System.out.println(e.getMessage());
               }
            }
           /* List<Thread> threads1 = new ArrayList<Thread>();
            try {
               for (int i = 0; i < Main.cores; i += 1) {
                  Thread core = new Thread(new render2(i, Main.cores, Main.packint(0,0,25), Main.packint(0,0,25), true));//get all threads that we will use
                  core.start();
                  threads1.add(core);
               }
               for (int i = 0; i < Main.cores; i++) {  
                  threads1.get(i).join(); 
               } 
            } catch (Exception e) {
               System.out.println(e.getMessage());
            }*/
            //Main.bloom();
         } else {
            Main.setupangles();
            Main.clear(0, 0, 0);
            Main.render(Main, 0, Main.polynumber);
            //Main.lighting(Main);
         }
         t2 = System.currentTimeMillis();
         score += (t2 - t1);
         frames += 1;
         if (t2 - scoreTime >= 1 * 1000) {
            System.out.println("score: " + score * frames);
            System.out.println("frames : " + frames);
            score = 0;
            scoreTime = t2;
            frames = 0;
         }
         map.map3(g, Main);
         //Main.postProcess(Main, 0);
         Main.present(g);
      } else {
         g.drawImage(image, 0, 0, this);
         Wait(5);
         draw = true;
      }
      repaint();
   }
   public static void sb() {
      RealEngine.sb_x = RealEngine.x / RealEngine.sb_move_offset + RealEngine.sb_x_offset;
      RealEngine.sb_y = RealEngine.y / RealEngine.sb_move_offset + RealEngine.sb_y_offset;
      RealEngine.sb_z = RealEngine.z / RealEngine.sb_move_offset + RealEngine.sb_z_offset;
   }
   public void Wait(double time) {
      time *= 1000;
      long t1 = System.currentTimeMillis();
      long t2 = 0;
      while (t2 - t1 < time) {
         t2 = System.currentTimeMillis();
      }
      repaint();
      return;
   }
   public void moveRight() {
      Main.changeX(Math.cos(Math.toRadians(Main.getAy())));
      Main.changeZ(-Math.sin(Math.toRadians(Main.getAy())));
   }

   public void moveLeft() {
      Main.changeX(-Math.cos(Math.toRadians(Main.getAy())));
      Main.changeZ(Math.sin(Math.toRadians(Main.getAy())));
   }

   public void moveDown() {
      Main.changeX(-Math.sin(Math.toRadians(Main.getAy())));
      Main.changeZ(-Math.cos(Math.toRadians(Main.getAy())));
   }

   public void moveUp() {
      Main.changeX(Math.sin(Math.toRadians(Main.getAy())));
      Main.changeZ(Math.cos(Math.toRadians(Main.getAy())));
   }
   
   public void lookRight() {
      Main.changeAy(1.0);
   }
   
   public void lookLeft() {
      Main.changeAy(-1.0);
   }
   
   public void lookDown() {
      Main.changeAx(1.0);
   }
   
   public void lookUp() {
      Main.changeAx(-1.0);
   }
   public void mainDraw_map_outside() {//or the main map construction
      terrain.compile(Main, 4, -5, 4);
      terrain.compile(Main, -5, -5, 4);
      terrain.compile(Main, 4, -5, -5);
      terrain.compile(Main, -5, -5, -5);
      terrain.compile(Main, 10, -5, 5);
      terrain.compile(Main, 10, -5, -5);
      terrain.compile(Main, 4, -5, 21);
      terrain.compile(Main, -5, -5, 21);
      terrain.compile(Main, 4, -5, 13);
      terrain.compile(Main, -5, -5, 13);
      terrain.compile(Main, 10, -5, 21);
      terrain.compile(Main, 10, -5, 13);
      terrain.compile(Main, -12, -5, 22); 
      terrain.compile(Main, -5, -5, 24); 
      terrain.compile(Main, -18, -5, 22); 
      terrain.compile(Main, 5, -5, 23); 
      exp_side1.compile(Main, -10, 0, 5, Main.packint(160, 75, 44));
      exp_side1.compile(Main, -10, 0, -5, Main.packint(160, 75, 44));
      exp_side1.compile(Main, -10, 0.1, 5.1, Main.packint(255,255,255));
      exp_side1.compile(Main, -9.999, 0.1, -4.9, Main.packint(255,255,255));            
      exp_side1.compile(Main, -10, 10, 5, Main.packint(160, 75, 44));
      exp_side1.compile(Main, -10, 10, -5, Main.packint(160, 75, 44));
      exp_side1.compile(Main, -10, 10.1, 5.1, Main.packint(255,255,255));
      exp_side1.compile(Main, -9.999, 10.1, -4.9, Main.packint(255,255,255));
      exp_side1.compile(Main, -10, 0, 15, Main.packint(160, 75, 44));
      exp_side1.compile(Main, -10, 0.1, 15.1, Main.packint(255,255,255));
      exp_side1.compile(Main, -10, 10, 15, Main.packint(160, 75, 44));
      exp_side1.compile(Main, -10, 10.1, 15.1, Main.packint(255,255,255));
      roof.compile(Main, -11, 15.60, -4, Main.packint(150,150,150)); 
      roof_under.compile(Main, -11, 15.59, -4, Main.packint(150,150,150));
      roof.compile(Main, -11, 15.60,  6, Main.packint(150,150,150)); 
      roof_under.compile(Main, -11, 15.59,  6, Main.packint(150,150,150));
      roof.compile(Main, -11, 15.60, 16, Main.packint(150,150,150)); 
      roof_under.compile(Main, -11, 15.59, 16, Main.packint(150,150,150));
      exp_hill.compile(Main, 17, -5, 0, Main.packint(255, 255, 255));
      exp_hill.compile(Main, 17, -5, 20, Main.packint(255, 255, 255));
      exp_hill.compile(Main, 17, -5, 30, Main.packint(255, 255, 255));
      exp_side2.compile(Main, -15, 0, 20, Main.packint(160, 75, 44));
      exp_side2.compile(Main, -15, 10, 20, Main.packint(160, 75, 44));
      exp_side2.compile(Main, -25, 14, 20, Main.packint(160, 75, 44));
      exp_side2.compile(Main, -15.1, 0.1, 20.05, Main.packint(255, 255, 255));
      exp_side2.compile(Main, -15.1, 10.1, 20.05, Main.packint(255, 255, 255));
      exp_side2.compile(Main, -25.1, 14.1, 20.05, Main.packint(255, 255, 255));
      hills.compile(Main, 5,-5,34, Main.packint(255, 255, 255));
      exp_side2.compile(Main, -5, -3, -10, Main.packint(160, 75, 44));
      exp_side2.compile(Main, -5.1, -3.1, -9.95, Main.packint(255, 255, 255));
      exp_side2.compile(Main, 5, -3, -10, Main.packint(160, 75, 44));
      exp_side2.compile(Main, 5.1, -3.1, -9.95, Main.packint(255, 255, 255));
      exp_side2.compile(Main, -5, 7, -10, Main.packint(160, 75, 44));
      exp_side2.compile(Main, -5.1, 7.1, -9.95, Main.packint(255, 255, 255));
      roof.compile(Main, -11, 15.60, -14, Main.packint(150,150,150)); 
      roof_under.compile(Main, -11, 15.59, -14, Main.packint(150,150,150));
      
      //tower_skybox.compile(Main, 5, -5, 5, Main.packint(255, 0, 255));
      
   }
   public static void mainDraw_map_inside() {
      terrain.compile(Main, -5, -5, 24); 
      terrain.compile(Main, -18, -5, 22); 
      terrain.compile(Main, 5, -5, 23); 
      hills.compile(Main, 5,-5,34, Main.packint(255, 255, 255));
   }
   static double thingyero = 0;
   public static double[][] lights() {
      double[][] light = new double[20][3];
      light[0][0] = -9.0 - Main.x;
      light[0][1] = 4.0 - Main.y;
      light[0][2] = 25.0 - Main.z;
      light[1][0] = -9.0 - Main.x;
      light[1][1] = 4.0 - Main.y;
      light[1][2] = 0.0 - Main.z;
      light[2][0] = 40.0 - Main.x;
      light[2][1] = 40.0 - Main.y;
      light[2][2] = 40.0 - Main.z;
      return light;
   }
   public static double[][] lightc() {
      double[][] color = new double[20][4];
      color[0][0] = 255;//r
      color[0][1] = 255;//g
      color[0][2] = 255;//b
      color[0][3] = 7;//brightness
      color[1][0] = 255;
      color[1][1] = 255;
      color[1][2] = 255;
      color[0][3] = 14;
      color[2][0] = 255;
      color[2][1] = 200;
      color[2][2] = 200;
      color[2][3] = 20;//ItS thE SuN!
      
      return color;
   }
   public static boolean differ(int number) {
      String num = Integer.toString(number);
      int digitSum = 0;
      for(int i = 0; i<num.length(); i++) {
         digitSum = digitSum + num.charAt(i)-'0';
      }
      return (digitSum % 3 == 0);
   }

   public static void main(String[] args) {
      main.main(args);
   }
}
class render implements Runnable {
   static RealEngine Main = mainDraw.Main;
   static int thi = Main.polynumber / Main.cores;
   private int calcLoad0;// = (thi * (thread - 1));
   private int calcLoad1;// = (thi * thread);
   private int thread;
   public render(int thread) {
      this.calcLoad0 = (thi * thread);
      this.calcLoad1 = (thi * (thread + 1));
      this.thread = thread;
      if (!mainDraw.differ(this.calcLoad0)) {
         this.calcLoad0 -= 1;
      }
      if (!mainDraw.differ(this.calcLoad0)) {
         this.calcLoad0 -= 1;
      }
      if (!mainDraw.differ(this.calcLoad1)) {
         this.calcLoad1 += 1;
      }
      if (!mainDraw.differ(this.calcLoad1)) {
         this.calcLoad1 += 1;
      }
   }
   public void run() {
      try {
         //"gpu" code:
         Main.render(Main, calcLoad0, calcLoad1);
         return;
      } catch (Exception e) {
      }  
   }
}
class render_reflectiveSurface implements Runnable {
   static RealEngine Main = mainDraw.Main;
   static int thi = Main.water_polynumber / Main.cores;
   private int calcLoad0;// = (thi * (thread - 1));
   private int calcLoad1;// = (thi * thread);
   private int thread;
   public render_reflectiveSurface(int thread) {
      this.calcLoad0 = (thi * thread);
      this.calcLoad1 = (thi * (thread + 1));
      this.thread = thread;
      if (!mainDraw.differ(this.calcLoad0)) {
         this.calcLoad0 -= 1;
      }
      if (!mainDraw.differ(this.calcLoad0)) {
         this.calcLoad0 -= 1;
      }
      if (!mainDraw.differ(this.calcLoad1)) {
         this.calcLoad1 += 1;
      }
      if (!mainDraw.differ(this.calcLoad1)) {
         this.calcLoad1 += 1;
      }
   }
   public void run() {
      try {
         //"gpu" code:
         Main.render_water(Main, calcLoad0, calcLoad1);
         return;
      } catch (Exception e) {
      }  
   }
}
class render_reflections implements Runnable {
   static RealEngine Main = mainDraw.Main;
   static int thi = Main.polynumber / Main.cores;
   private int calcLoad0;// = (thi * (thread - 1));
   private int calcLoad1;// = (thi * thread);
   private int thread;
   public render_reflections(int thread) {
      this.calcLoad0 = (thi * thread);
      this.calcLoad1 = (thi * (thread + 1));
      this.thread = thread;
      if (!mainDraw.differ(this.calcLoad0)) {
         this.calcLoad0 -= 1;
      }
      if (!mainDraw.differ(this.calcLoad0)) {
         this.calcLoad0 -= 1;
      }
      if (!mainDraw.differ(this.calcLoad1)) {
         this.calcLoad1 += 1;
      }
      if (!mainDraw.differ(this.calcLoad1)) {
         this.calcLoad1 += 1;
      }
   }
   public void run() {
      try {
         //"gpu" code:
         Main.render_waterReflection(Main, calcLoad0, calcLoad1);
         return;
      } catch (Exception e) {
      }  
   }
}
class t_present extends JPanel implements Runnable  {
   static RealEngine Main = mainDraw.Main;
   private int thread;
   private int cores;
   public Graphics g;
   public t_present(int thread, int cores, Graphics g) {
      this.thread = thread;
      this.cores = cores;
      this.g = g;
   }
   public void run() {
      try {
         for (int i = thread; i < RealEngine.length; i += Main.cores) {
            for (int j = 0; j < RealEngine.width; j += 1) {
               if (Main.buffercol[i][j][0] != -1 && Main.buffercol[i][j][1] != -1 && Main.buffercol[i][j][2] != -1) {
                  Main.buffercol[i][j] = Main.packcol(Main.buffercol[i][j][0], Main.buffercol[i][j][1], Main.buffercol[i][j][2]);
                  Color thisss = new Color (Main.buffercol[i][j][0], Main.buffercol[i][j][1], Main.buffercol[i][j][2]);
                  this.g.setColor(thisss);
                  this.g.drawLine(i, j, i, j);
               }
            }
         }
      } catch (Exception e) {
         System.out.println(e);
      }
   }
}
class render2 implements Runnable {
   static RealEngine eng = mainDraw.Main;
   private int thread;
   private int cores;
   private boolean ref;
   private int[] leastcolor = new int[3];
   private int[] skycolor = new int[3];
   public render2(int thread, int cores, int[] leastcolor, int[] skycolor, boolean lightreflections) {
      this.thread = thread;
      this.cores = cores;
      this.leastcolor = leastcolor;
      this.skycolor = skycolor;
      this.ref = lightreflections;
   }
   public void run() {
      try {
         double[][] lights_L = mainDraw.lights();
         double[][] lights_C = mainDraw.lightc();
         for (int i = thread; i < RealEngine.length; i += this.cores) {
            for (int j = 0; j < RealEngine.width; j += 1) {
               if (eng.bufferz[i][j] > 100000.0){
                  eng.buffercol[i][j] = this.skycolor;
               } else {
                  int colour[] = new int[3];
                  int color3[] = new int[3];
               
                  colour[0] = 0;
                  colour[1] = 0;
                  colour[2] = 0;
                  int[] point = new int[2];
                  point[0] = i;
                  point[1] = j;
                  color3[0] = eng.getBuffercol(point[0], point[1], 0);
                  color3[1] = eng.getBuffercol(point[0], point[1], 1);
                  color3[2] = eng.getBuffercol(point[0], point[1], 2);
                  colour[0] = 0;
                  colour[1] = 0;
                  colour[2] = 0;
                  
                  double xdist = 0;
                  double ydist = 0;
                  double zdist = 0;
                  for (int k = 0; k < RealEngine.maxlights; k += 1) {
                     double[] light = lights_L[k];
                     double[] color = lights_C[k];
                     double xaxis = eng.getBuffer3d(point[0], point[1], 0);
                     double yaxis = eng.getBuffer3d(point[0], point[1], 1);
                     double zaxis = eng.getBuffer3d(point[0], point[1], 2);
                     xdist = Math.abs(light[0] - xaxis);
                     ydist = Math.abs(light[1] - yaxis);
                     zdist = Math.abs(light[2] - zaxis);
                     double thingy = Math.sqrt((xdist * xdist) + (ydist * ydist) + (zdist * zdist));
                  
                     int brightness = (int) color[3];
                  
                     if (color[0] != color[1] && color[0] != color[2]) {
                        colour[0] += (int) ((brightness / (thingy)) * (color3[0] + (color[0] - color[2] - color[1])));
                     } else if (color[0] != color[2]) {
                        colour[0] += (int) ((brightness / (thingy)) * (color3[0] + (color[0] - color[1])));
                     } else {            
                        colour[0] += (int) ((brightness / (thingy)) * (color3[0] + (color[0] - color[2])));
                     }
                     if (color[1] != color[0] && color[1] != color[2]) {//
                        colour[1] += (int) ((brightness / (thingy)) * (color3[1] + (color[1] - color[2] - color[0])));//
                     } else if (color[1] != color[2]) {//
                        colour[1] += (int) ((brightness / (thingy)) * (color3[1] + (color[1] - color[0])));//
                     } else {           //
                        colour[1] += (int) ((brightness / (thingy)) * (color3[1] + (color[1] - color[2])));//
                     }
                     if (color[2] != color[1] && color[2] != color[0]) {//
                        colour[2] += (int) ((brightness / (thingy)) * (color3[2] + (color[2] - color[1] - color[0])));//
                     } else if (color[2] != color[1]) {//
                        colour[2] += (int) ((brightness / (thingy)) * (color3[2] + (color[2] - color[0])));//
                     } else {            
                        colour[2] += (int) ((brightness / (thingy)) * (color3[2] + (color[2] - color[1])));
                     }
                  }
                  if (colour[0] > 255) {
                     colour[0] = 255;
                  } else if (colour[0] < this.leastcolor[0]) {
                     colour[0] = this.leastcolor[0];
                  }
                  if (colour[1] > 255) {
                     colour[1] = 255;
                  } else if (colour[1] < this.leastcolor[1]) {
                     colour[1] = this.leastcolor[1];
                  }
                  if (colour[2] > 255) {
                     colour[2] = 255;
                  } else if (colour[2] < this.leastcolor[2]) {
                     colour[2] = this.leastcolor[2];
                  }
               
                  eng.buffercol[point[0]][point[1]] = colour;
               }
            }
         }
      } catch (Exception e) {
      }    
   }
}
class render_shadows implements Runnable {
   //highly experemental, may not work at all
   //if you found this, dont use it
   //Also, if you are good that this sort of thing, go ahead and make changes for all I care
   //but, if you get it working, please let me know what you did
   //My origional design of this was to go pixel by pixel of the buffers, determining whether or not if a pixel
   //was visible to a light source by tracing it back to that light source baised on what was rendered
   //Now, this might sound bad, but some how it worked ONE TIME before i broke it
   //If its not visible, dont touch the origional pixel
   //If its visible, similar to the render2 method, light it up accordingly
   //detailed on how it origionaly worked:
   //get both locations of the current pixel and the light (if on screen)
   //then, collect the 3d location of both using the 3d buffer
   //use math to get the slope on the buffer, not the 3d world
   //use that slope to trace it back to the light while checking the intervening pixels
   //if a pixel is within a certian distance and may be in that line, break the entire thing and move on
   //if nothing broke that loop, cast the light
   //the further the depth, the 'thicker' the intervening pixels would be (further away does not carry that much pixels)
   //to figure out if a pixel, now called looped pix, is in the way. By using the current pixels xyz coords, get the looped pixs
   //coords as well.  Get the lights as well and figure out the xy, xz, yz slopes and trace the pixel on that slope, if its not on the
   //slope with a certian space, its not in the way. But, if it does fall on all the slopes, its in the way
   //HOWEVER, this only works if the light is within view of the camera and MUST fall in the buffers area
   //TL;DR: You figure out the 2d portion, then the 3d portion, then determine if you can light it up
   
   //TL;DR its basic screen spaced raytracing that may or may not work
   static RealEngine Main = mainDraw.Main;
   private int thread;
   private int cores;
   private int[] leastcolor = new int[3];
   public render_shadows(int thread, int cores, int[] leastcolor) {
      this.thread = thread;
      this.cores = cores;
      this.leastcolor = leastcolor;
   }
   public void run() {
      try {
         double[][] lights = mainDraw.lights();
         for (int i = thread; i < RealEngine.length; i += this.cores) {
            for (int j = 0; j < RealEngine.width; j += 1) {
               //get teh current pixel
               double[] pixel = new double[5];
               pixel[0] = i;
               pixel[1] = j;
               pixel[2] = Main.buffer3d[(int)pixel[0]][(int)pixel[1]][0];
               pixel[3] = Main.buffer3d[(int)pixel[0]][(int)pixel[1]][1];
               pixel[4] = Main.buffer3d[(int)pixel[0]][(int)pixel[1]][2];
               double[] pixel3d = Main.pack(pixel[2], pixel[3], pixel[4]);
               //get the ligths
               for (int k = 0; k < RealEngine.maxlights; k++) {
                  //convert the light location
                  double[] lightloc = transformpoint(lights[k]);
                  double[] light3d = Main.pack(lightloc[2],lightloc[3],lightloc[4]);
                  if (RealEngine.s_Check(lightloc)) {
                     //if within view, continue, if not, break, or move on to the next light
                     //get the 2d slopes
                     //get the 3d slopes and store them
                     //begin tracing
                     boolean _switch = (pixel[0] < lightloc[0]);
                     boolean yswitch = (pixel[1] < lightloc[1]);
                     int yaxis;
                     if (yswitch) {
                        yaxis = (int)pixel[1];
                     } else {
                        yaxis = (int)lightloc[1];
                     }
                     if (_switch) {
                        for (int xaxis = (int)lightloc[0]; xaxis < pixel[0]; xaxis++) {
                           double slope2d = (pixel[1] - lightloc[1]) / (pixel[0] - lightloc[0]);
                           double b = lightloc[0] - (slope2d * lightloc[0]);
                           double[] loopedpix = Main.buffer3d[xaxis][(int)b];
                           if (online(light3d, loopedpix, pixel3d, 0)) {
                              if (online(light3d, loopedpix, pixel3d, 1)) {
                                 if (online(light3d, loopedpix, pixel3d, 2)) {
                                    //xaxis = (int)pixel[0];//stop the loop
                                    //light the looped pixel
                                    lightit(pixel, k);
                                 }
                              }
                           }
                        }
                     } else {
                        for (int xaxis = (int)pixel[0]; xaxis < lightloc[0]; xaxis++) {
                           double slope2d = (lightloc[1] - pixel[1]) / (lightloc[0] - pixel[0]);
                           double b = pixel[0] - (slope2d * pixel[0]);
                           double[] loopedpix = Main.buffer3d[xaxis][(int)b];
                           if (online(pixel3d, loopedpix, light3d, 0)) {
                              if (online(pixel3d, loopedpix, light3d, 1)) {
                                 if (online(pixel3d, loopedpix, light3d, 2)) {
                                    //xaxis = (int)lightloc[0];//stop the loop
                                    //light the looped pixel
                                    lightit(pixel, k);
                                 }
                              }
                           }
                        }
                     }
                  } else {
                     lightit(pixel, k);
                  }
               }
            }
         }
      } catch (Exception e) {
         System.out.println(e);
         e.printStackTrace(System.out);
      }
   }
   public static boolean online(double[] p1, double[] p3, double[] p2, int xz) {
      double thickness = ((p3[0] + p3[1] + p3[2]) / 3);
      if (xz == 0) {
         double slope = (p2[1] - p1[1]) / (p2[0] - p1[0]);
         double b = p1[1] - (slope * p1[0]);
         int i = 0;
         if (((slope * p3[0]) + b) >= (p3[1] + thickness) && ((slope * p3[0]) + b) <= (p3[1] - thickness)) {
            return true;
         } else {
            return false;
         }
      } else if (xz == 1) {
         double slope = (p2[0] - p1[0]) / (p2[2] - p1[2]);
         double b = p1[0] - (slope * p1[2]);
         if (((slope * p3[2]) + b) >= (p3[0] + thickness) && ((slope * p3[2]) + b) <= (p3[0] - thickness)) {
            return true;
         } else {
            return false;
         }
      } else {
         double slope = (p2[1] - p1[1]) / (p2[2] - p1[2]);
         double b = p1[1] - (slope * p1[2]);
         if (((slope * p3[2]) + b) >= (p3[1] + thickness) && ((slope * p3[2]) + b) <= (p3[1] - thickness)) {
            return true;
         } else {
            return false;
         }
      }
   
   }
   public void lightit(double[] pixel, int lightn) {
      double[][] lights_L = mainDraw.lights();
      double[][] lights_C = mainDraw.lightc();
      int colour[] = new int[3];
      int color3[] = new int[3];
            
      colour[0] = 0;
      colour[1] = 0;
      colour[2] = 0;
      int[] point = new int[2];
      point[0] = (int)pixel[0];
      point[1] = (int)pixel[1];
      color3[0] = Main.getBuffercol(point[0], point[1], 0);
      color3[1] = Main.getBuffercol(point[0], point[1], 1);
      color3[2] = Main.getBuffercol(point[0], point[1], 2);
      colour[0] = 0;
      colour[1] = 0;
      colour[2] = 0;
                  
      double xdist = 0;
      double ydist = 0;
      double zdist = 0;
      double[] light = lights_L[lightn];
      double[] color = lights_C[lightn];
      double xaxis = Main.getBuffer3d(point[0], point[1], 0);
      double yaxis = Main.getBuffer3d(point[0], point[1], 1);
      double zaxis = Main.getBuffer3d(point[0], point[1], 2);
      xdist = Math.abs(light[0] - xaxis);
      ydist = Math.abs(light[1] - yaxis);
      zdist = Math.abs(light[2] - zaxis);
      double thingy = Math.sqrt((xdist * xdist) + (ydist * ydist) + (zdist * zdist));
                  
      int brightness = (int) color[3];
                  
      if (color[0] != color[1] && color[0] != color[2]) {
         colour[0] += (int) ((brightness / (thingy)) * (color3[0] + (color[0] - color[2] - color[1])));
      } else if (color[0] != color[2]) {
         colour[0] += (int) ((brightness / (thingy)) * (color3[0] + (color[0] - color[1])));
      } else {            
         colour[0] += (int) ((brightness / (thingy)) * (color3[0] + (color[0] - color[2])));
      }
      if (color[1] != color[0] && color[1] != color[2]) {//
         colour[1] += (int) ((brightness / (thingy)) * (color3[1] + (color[1] - color[2] - color[0])));//
      } else if (color[1] != color[2]) {//
         colour[1] += (int) ((brightness / (thingy)) * (color3[1] + (color[1] - color[0])));//
      } else {           //
         colour[1] += (int) ((brightness / (thingy)) * (color3[1] + (color[1] - color[2])));//
      }
      if (color[2] != color[1] && color[2] != color[0]) {//
         colour[2] += (int) ((brightness / (thingy)) * (color3[2] + (color[2] - color[1] - color[0])));//
      } else if (color[2] != color[1]) {//
         colour[2] += (int) ((brightness / (thingy)) * (color3[2] + (color[2] - color[0])));//
      } else {            
         colour[2] += (int) ((brightness / (thingy)) * (color3[2] + (color[2] - color[1])));
      }
      if (colour[0] > 255) {
         colour[0] = 255;
      } else if (colour[0] < this.leastcolor[0]) {
         colour[0] = this.leastcolor[0];
      }
      if (colour[1] > 255) {
         colour[1] = 255;
      } else if (colour[1] < this.leastcolor[1]) {
         colour[1] = this.leastcolor[1];
      }
      if (colour[2] > 255) {
         colour[2] = 255;
      } else if (colour[2] < this.leastcolor[2]) {
         colour[2] = this.leastcolor[2];
      }
               
      Main.shadowmap[point[0]][point[1]][0] += colour[0];
      Main.shadowmap[point[0]][point[1]][1] += colour[1];
      Main.shadowmap[point[0]][point[1]][2] += colour[2];
      
               
   }
   public static double[] transformpoint(double[] coords) {
      double[] org = coords;
      double[] start = vertex(coords);
      double fov = RealEngine.viewfactor;
      double[] twocoords = new double[5];
      double test1 = 0;
      double test2 = 0;
      if (((0 < start[2]) && (((Math.abs((start[1] * fov)/ start[2])) < RealEngine.width) && (((Math.abs((start[1] * fov)/ start[2])) < RealEngine.length))))) {
         test1 = (((start[0] * fov) / start[2]));
         test2 = (((start[1] * fov) / start[2]));
         twocoords[2] = (coords[0]) - RealEngine.x;
         twocoords[3] = (coords[1]) - RealEngine.y;
         twocoords[4] = (coords[2]) - RealEngine.z;         
         twocoords[0] = test1;
         twocoords[1] = test2;
      } else {
         twocoords[0] = RealEngine.length + RealEngine.width;
         twocoords[1] = RealEngine.length + RealEngine.width;
         twocoords[2] = RealEngine.length + RealEngine.width;
         twocoords[3] = RealEngine.length + RealEngine.width;
         twocoords[4] = RealEngine.length + RealEngine.width;
      }
      return twocoords;
   }
   
   public static double[] vertex(double[] point) {
      double x2 = point[0] - RealEngine.x;
      double y2 = point[1] - RealEngine.y;
      double z2 = point[2] - RealEngine.z;
      double[] sts = new double[3];
      sts[0] = ((RealEngine.ayc * ((RealEngine.azs * y2) + (RealEngine.azc * x2))) - (RealEngine.ays * z2));
      sts[1] = ((RealEngine.axs * ((RealEngine.ayc * z2) + (RealEngine.ays * ((RealEngine.azs * y2) + (RealEngine.azc * x2))))) + (RealEngine.axc * ((RealEngine.azc * y2) - (RealEngine.azs * x2)))) * -1;
      sts[2] = ((RealEngine.axc * ((RealEngine.ayc * z2) + (RealEngine.ays * ((RealEngine.azs * y2) + (RealEngine.azc * x2))))) - (RealEngine.axs * ((RealEngine.azc * y2) - (RealEngine.azs * x2))));
      return sts;
   }
}