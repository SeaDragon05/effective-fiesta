import java.util.Random;
//this is a basic map showing the variuos functions of th 3d eng that I ported and heavily modified
//basic functions:
//eng.eng.DrawLine(x1, y1, z1, x2, y2, z2, hud1);
//
import javax.swing.*;
import java.awt.*;
public class map_test extends JPanel {
   static Random rand = new Random();
   static double spawnx = 0.0;//-48.021900529732676;
   static double spawny = 7.0;
   static double spawnz = 0.0;//-56.24639057765156;
   static double look = 0.01;//42.01;
   public map_test(){}
   public static void compile(RealEngine eng) {
      double[] point1 = new double[3]; 
      double[] point2 = new double[3];
      double[] point3 = new double[3];
      int[] red = eng.packint(255, 255, 255);
      int[] green = eng.packint(255, 255, 255);
      int[] blue = eng.packint(255, 255, 255);
      int[] white = eng.packint(255, 255, 255);
      int[] black = eng.packint(255, 255, 255);
      int[] grey = eng.packint(132, 133, 124);
      int[] cement = grey;
      int[] wood = eng.packint(185, 156, 107);
      
      //Shapes.SCube(eng.pack(0.0, 5.0, 5.0), 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, white, eng);
      
      
      /*
      point1 = eng.pack(.0, .0, .0);
      point2 = eng.pack(.0, .0, .0);
      point3 = eng.pack(.0, .0, .0);
      eng.GeometryAdd(point1, point2, point3, 0, cement);
      */
      
      eng.filled = true;
      return;
   }
   public static double[][] getLights() {
      double[][] lights = new double[20][3];
      lights[0][0] = 0.0;
      lights[0][1] = 0.0;
      lights[0][2] = 0.0;
      lights[1][0] = 0.0;
      lights[1][1] = 0.0;
      lights[1][2] = 0.0;
      lights[2][0] = 0.0;
      lights[2][1] = 0.0;
      lights[2][2] = 0.0;
      lights[3][0] = 0.0;
      lights[3][1] = 0.0;
      lights[3][2] = 0.0;
      lights[4][0] = 0.0;
      lights[4][1] = 0.0;
      lights[4][2] = 0.0;
      lights[5][0] = 0.0;
      lights[5][1] = 0.0;
      lights[5][2] = 0.0;
      lights[6][0] = 0.0;
      lights[6][1] = 0.0;
      lights[6][2] = 0.0;
      return lights;
   }
   public static double[][] getLightColor() {
      double[][] color = new double[20][3];
      color[0][0] = 255.0;
      color[0][1] = 255.0;
      color[0][2] = 255.0;
      color[1][0] = 0.0;
      color[1][1] = 0.0;
      color[1][2] = 0.0;
      color[2][0] = 0.0;
      color[2][1] = 0.0;
      color[2][2] = 0.0;
      color[3][0] = 0.0;
      color[3][1] = 0.0;
      color[3][2] = 0.0;
      color[4][0] = 0.0;
      color[4][1] = 0.0;
      color[4][2] = 0.0;
      color[5][0] = 0.0;
      color[5][1] = 0.0;
      color[5][2] = 0.0;
      color[6][0] = 0.0;
      color[6][1] = 0.0;
      color[6][2] = 0.0;
      return color;
   }
   public static void main(String[] args) {
      main.main(args);
   }
}