import java.util.Random;
//this is a basic map showing the variuos functions of th 3d eng that I ported and heavily modified
//basic functions:
//eng.eng.DrawLine(x1, y1, z1, x2, y2, z2, hud1);
//
import javax.swing.*;
import java.awt.*;
public class map extends JPanel {
   static Random rand = new Random();
   static double spawnx = 0.0;//-48.021900529732676;
   static double spawny = 7.0;
   static double spawnz = -10.0;//-56.24639057765156;
   static double look = 0.01;//42.01;
   static double dist = 40;
   public map(){}
   public static void compile(RealEngine eng) {
      double[] point1 = new double[3]; 
      double[] point2 = new double[3];
      double[] point3 = new double[3];
      double[] normalp1 = new double[3]; 
      double[] normalp2 = new double[3];
      double[] normalp3 = new double[3];
      int[] red = eng.packint(255, 255, 255);
      int[] green = eng.packint(255, 255, 255);
      int[] blue = eng.packint(255, 255, 255);
      int[] white = eng.packint(255, 255, 255);
      int[] black = eng.packint(255, 255, 255);
      int[] grey = eng.packint(255, 255, 255);
      int[] cement = grey;
      int[] wood = eng.packint(185, 156, 107);
      
      point1 = eng.pack(15.0, -10.0, 80.0);
      point2 = eng.pack(15.0, 20.0, 80.0);
      point3 = eng.pack(-15.0, 20.0, 80.0);
      normalp1 = eng.pack(0.5773,-0.5773,-0.5773);
      normalp2 = eng.pack(0.5773,0.5773,-0.5773);
      normalp2 = eng.pack(-0.5773,0.5773,-0.5773);
      eng.GeometryAdd(point1, point2, point3, normalp1, normalp2, normalp3, 0, cement);
      
      point1 = eng.pack(15.0, -10.0, 90.0);
      point2 = eng.pack(15.0, 20.0, 90.0);
      point3 = eng.pack(15.0, 20.0, 80.0);
      normalp1 = eng.pack(0.5773,-0.5773,0.5773);
      normalp2 = eng.pack(0.5773,0.5773,0.5773);
      normalp2 = eng.pack(0.5773,0.5773,-0.5773);
      eng.GeometryAdd(point1, point2, point3, normalp1, normalp2, normalp3, 0, cement);
      
      point1 = eng.pack(-15.0, -10.0, 90.0);
      point2 = eng.pack(-15.0, 20.0, 90.0);
      point3 = eng.pack(-15.0, 20.0, 80.0);
      normalp1 = eng.pack(0.5773,-0.5773,0.5773);
      normalp2 = eng.pack(0.5773,0.5773,0.5773);
      normalp2 = eng.pack(0.5773,0.5773,-0.5773);
      eng.GeometryAdd(point1, point2, point3, normalp1, normalp2, normalp3, 0, cement);
      
      point1 = eng.pack(15.0, -10.0, 90.0);
      point2 = eng.pack(15.0, 20.0, 80.0);
      point3 = eng.pack(15.0, -10.0, 80.0);
      normalp1 = eng.pack(0.5773,-0.5773,0.5773);
      normalp2 = eng.pack(0.5773,0.5773,-0.5773);
      normalp2 = eng.pack(0.5773,-0.5773,-0.5773);
      eng.GeometryAdd(point1, point2, point3, normalp1, normalp2, normalp3, 0, cement);
      
      point1 = eng.pack(-15.0, -10.0, 90.0);
      point2 = eng.pack(-15.0, 20.0, 80.0);
      point3 = eng.pack(-15.0, -10.0, 80.0);
      normalp1 = eng.pack(-0.5773,-0.5773,0.5773);
      normalp2 = eng.pack(-0.5773,0.5773,-0.5773);
      normalp2 = eng.pack(-0.5773,-0.5773,-0.5773);
      eng.GeometryAdd(point1, point2, point3, normalp1, normalp2, normalp3, 0, cement);
      
      point1 = eng.pack(15.0, -10.0, 80.0);
      point2 = eng.pack(-15.0, -10.0, 80.0);
      point3 = eng.pack(-15.0, 20.0, 80.0);
      normalp1 = eng.pack(0.5773,-0.5773,-0.5773);
      normalp2 = eng.pack(-0.5773,-0.5773,-0.5773);
      normalp2 = eng.pack(-0.5773,0.5773,-0.5773);
      eng.GeometryAdd(point1, point2, point3, normalp1, normalp2, normalp3, 0, cement);
      
      point1 = eng.pack(15.0, -10.0, 90.0);
      point2 = eng.pack(15.0, 20.1, 90.0);
      point3 = eng.pack(-15.0, 20.0, 90.0);
      normalp1 = eng.pack(0.5773,-0.5773,0.5773);
      normalp2 = eng.pack(0.5773,0.5773,0.5773);
      normalp2 = eng.pack(-0.5773,0.5773,0.5773);
      eng.GeometryAdd(point1, point2, point3, normalp1, normalp2, normalp3, 0, cement);
      
      point1 = eng.pack(15.0, -10.0, 90.0);
      point2 = eng.pack(-15.0, -10.0, 90.0);
      point3 = eng.pack(-15.0, 20.0, 90.0);
      normalp1 = eng.pack(0.5773,0.5773,0.5773);
      normalp2 = eng.pack(-0.5773,-0.5773,0.5773);
      normalp2 = eng.pack(-0.5773,0.5773,0.5773);
      eng.GeometryAdd(point1, point2, point3, normalp1, normalp2, normalp3, 0, cement);
      
      ///////////////////////////////////////////////////////////
      point1 = eng.pack(45.0, -10.0, 30.0);
      point2 = eng.pack(45.0, 20.0, 30.0);
      point3 = eng.pack(45.0, -10.0, 120.0);
      normalp1 = eng.pack(0.5773,-0.5773,-0.5773);
      normalp2 = eng.pack(0.5773,0.5773,-0.5773);
      normalp2 = eng.pack(0.5773,-0.5773,0.5773);
      eng.GeometryAdd(point1, point2, point3, normalp1, normalp2, normalp3, 0, grey);
      
      point1 = eng.pack(45.0, 20.0, 30.0);
      point2 = eng.pack(45.0, 20.0, 120.0);
      point3 = eng.pack(45.0, -10.0, 120.0);
      normalp1 = eng.pack(0.5773,0.5773,-0.5773);
      normalp2 = eng.pack(0.5773,0.5773,0.5773);
      normalp2 = eng.pack(0.5773,-0.5773,0.5773);
      eng.GeometryAdd(point1, point2, point3, normalp1, normalp2, normalp3, 0, grey);
      
      
      point1 = eng.pack(-45.0, -10.0, 30.0);
      point2 = eng.pack(-45.0, 20.0, 30.0);
      point3 = eng.pack(-45.0, -10.0, 120.0);
      normalp1 = eng.pack(0.5773,0.5773,0.5773);
      normalp2 = eng.pack(0.5773,0.5773,0.5773);
      normalp2 = eng.pack(0.5773,0.5773,0.5773);
      eng.GeometryAdd(point1, point2, point3, normalp1, normalp2, normalp3, 0, grey);
      
      
      point1 = eng.pack(-45.0, 20.0, 30.0);
      point2 = eng.pack(-45.0, 20.0, 120.0);
      point3 = eng.pack(-45.0, -10.0, 120.0);
      normalp1 = eng.pack(0.5773,0.5773,0.5773);
      normalp2 = eng.pack(0.5773,0.5773,0.5773);
      normalp2 = eng.pack(0.5773,0.5773,0.5773);
      eng.GeometryAdd(point1, point2, point3, normalp1, normalp2, normalp3, 0, grey);
      
      
      point1 = eng.pack(30.0, -10.0, 30.0);
      point2 = eng.pack(45.0, -10.0, 30.0);
      point3 = eng.pack(45.0, 20.0, 30.0);
      normalp1 = eng.pack(0.5773,0.5773,0.5773);
      normalp2 = eng.pack(0.5773,0.5773,0.5773);
      normalp2 = eng.pack(0.5773,0.5773,0.5773);
      eng.GeometryAdd(point1, point2, point3, normalp1, normalp2, normalp3, 0, grey);
      
      point1 = eng.pack(45.0, -10.0, 120.0);
      point2 = eng.pack(45.0, 20.0, 120.0);
      point3 = eng.pack(75.0, -10.0, 180.0);
      normalp1 = eng.pack(0.5773,0.5773,0.5773);
      normalp2 = eng.pack(0.5773,0.5773,0.5773);
      normalp2 = eng.pack(0.5773,0.5773,0.5773);
      eng.GeometryAdd(point1, point2, point3, normalp1, normalp2, normalp3, 0, grey);
      
      point1 = eng.pack(45.0, 20.0, 120.0);
      point2 = eng.pack(75.0, -10.0, 180.0);
      point3 = eng.pack(75.0, 20.0, 180.0);
      normalp1 = eng.pack(0.5773,0.5773,0.5773);
      normalp2 = eng.pack(0.5773,0.5773,0.5773);
      normalp2 = eng.pack(0.5773,0.5773,0.5773);
      eng.GeometryAdd(point1, point2, point3, normalp1, normalp2, normalp3, 0, grey);
      
      point1 = eng.pack(-45.0, -10.0, 120.0);
      point2 = eng.pack(-45.0, 20.0, 120.0);
      point3 = eng.pack(-75.0, -10.0, 180.0);
      normalp1 = eng.pack(0.5773,0.5773,0.5773);
      normalp2 = eng.pack(0.5773,0.5773,0.5773);
      normalp2 = eng.pack(0.5773,0.5773,0.5773);
      eng.GeometryAdd(point1, point2, point3, normalp1, normalp2, normalp3, 0, grey);
      
      point1 = eng.pack(-45.0, 20.0, 120.0);
      point2 = eng.pack(-75.0, -10.0, 180.0);
      point3 = eng.pack(-75.0, 20.0, 180.0);
      normalp1 = eng.pack(0.5773,0.5773,0.5773);
      normalp2 = eng.pack(0.5773,0.5773,0.5773);
      normalp2 = eng.pack(0.5773,0.5773,0.5773);
      eng.GeometryAdd(point1, point2, point3, normalp1, normalp2, normalp3, 0, grey);
      
      point1 = eng.pack(30.0, -10.0, 180.0);
      point2 = eng.pack(-30.0, -10.0, 180.0);
      point3 = eng.pack(30.0, 20.0, 180.0);
      normalp1 = eng.pack(0.5773,-0.5773,0.5773);
      normalp2 = eng.pack(-0.5773,-0.5773,0.5773);
      normalp2 = eng.pack(0.5773,0.5773,0.5773);
      eng.GeometryAdd(point1, point2, point3, normalp1, normalp2, normalp3, 0, grey);
      
      point1 = eng.pack(30.0, 20.0, 180.0);
      point2 = eng.pack(-30.0, 20.0, 180.0);
      point3 = eng.pack(-30.0, -10.0, 180.0);
      normalp1 = eng.pack(0.5773,0.5773,0.5773);
      normalp2 = eng.pack(0.5773,0.5773,0.5773);
      normalp2 = eng.pack(0.5773,0.5773,0.5773);
      eng.GeometryAdd(point1, point2, point3, normalp1, normalp2, normalp3, 0, grey);
      
      point1 = eng.pack(30.0, -10.0, 30.0);
      point2 = eng.pack(30.0, -10.0, -30.0);
      point3 = eng.pack(30.0, 20.0, -30.0);
      normalp1 = eng.pack(0.5773,-0.5773,0.5773);
      normalp2 = eng.pack(0.5773,-0.5773,-0.5773);
      normalp2 = eng.pack(0.5773,0.5773,-0.5773);
      eng.GeometryAdd(point1, point2, point3, normalp1, normalp2, normalp3, 0, grey);
      
      point1 = eng.pack(30.0, -10.0, 30.0);
      point2 = eng.pack(30.0, 20.0, 30.0);
      point3 = eng.pack(30.0, 20.0, -30.0);
      normalp1 = eng.pack(0.5773,-0.5773,0.5773);
      normalp2 = eng.pack(0.5773,0.5773,0.5773);
      normalp2 = eng.pack(0.5773,0.5773,-0.5773);
      eng.GeometryAdd(point1, point2, point3, normalp1, normalp2, normalp3, 0, grey);
      
      point1 = eng.pack(-30.0, -10.0, 30.0);
      point2 = eng.pack(-30.0, -10.0, -30.0);
      point3 = eng.pack(-30.0, 20.0, -30.0);
      normalp1 = eng.pack(0.5773,-0.5773,0.5773);
      normalp2 = eng.pack(0.5773,-0.5773,-0.5773);
      normalp2 = eng.pack(0.5773,0.5773,-0.5773);
      eng.GeometryAdd(point1, point2, point3, normalp1, normalp2, normalp3, 0, grey);
      
      point1 = eng.pack(-30.0, -10.0, 30.0);
      point2 = eng.pack(-30.0, 20.0, 30.0);
      point3 = eng.pack(-30.0, 20.0, -30.0);
      normalp1 = eng.pack(0.5773,-0.5773,-0.5773);
      normalp2 = eng.pack(0.5773,0.5773,-0.5773);
      normalp2 = eng.pack(0.5773,0.5773,0.5773);
      eng.GeometryAdd(point1, point2, point3, normalp1, normalp2, normalp3, 0, grey);
      
      point1 = eng.pack(30.0, -10.0, 30.0);
      point2 = eng.pack(30.0, 20.0, 30.0);
      point3 = eng.pack(45.0, 20.0, 30.0);
      normalp1 = eng.pack(0.5773,0.5773,0.5773);
      normalp2 = eng.pack(0.5773,0.5773,0.5773);
      normalp2 = eng.pack(0.5773,0.5773,0.5773);
      eng.GeometryAdd(point1, point2, point3, normalp1, normalp2, normalp3, 0, grey);
      
      point1 = eng.pack(-30.0, -10.0, 30.0);
      point2 = eng.pack(-30.0, 20.0, 30.0);
      point3 = eng.pack(-45.0, 20.0, 30.0);
      normalp1 = eng.pack(0.5773,0.5773,0.5773);
      normalp2 = eng.pack(0.5773,0.5773,0.5773);
      normalp2 = eng.pack(0.5773,0.5773,0.5773);
      eng.GeometryAdd(point1, point2, point3, normalp1, normalp2, normalp3, 0, grey);
      
      point1 = eng.pack(-30.0, -10.0, 30.0);
      point2 = eng.pack(-45.0, -10.0, 30.0);
      point3 = eng.pack(-45.0, 20.0, 30.0);
      normalp1 = eng.pack(0.5773,0.5773,0.5773);
      normalp2 = eng.pack(0.5773,0.5773,0.5773);
      normalp2 = eng.pack(0.5773,0.5773,0.5773);
      eng.GeometryAdd(point1, point2, point3, normalp1, normalp2, normalp3, 0, grey);
      
      point1 = eng.pack(30.0, -10.0, -30.0);
      point2 = eng.pack(30.0, 20.0, -30.0);
      point3 = eng.pack(-30.0, -10.0, -30.0);
      normalp1 = eng.pack(0.5773,0.5773,0.5773);
      normalp2 = eng.pack(0.5773,0.5773,0.5773);
      normalp2 = eng.pack(0.5773,0.5773,0.5773);
      eng.GeometryAdd(point1, point2, point3, normalp1, normalp2, normalp3, 0, grey);
      
      point1 = eng.pack(30.0, 20.0, -30.0);
      point2 = eng.pack(-30.0, -10.0, -30.0);
      point3 = eng.pack(-30.0, 20.0, -30.0);
      normalp1 = eng.pack(0.5773,0.5773,0.5773);
      normalp2 = eng.pack(0.5773,0.5773,0.5773);
      normalp2 = eng.pack(0.5773,0.5773,0.5773);
      eng.GeometryAdd(point1, point2, point3, normalp1, normalp2, normalp3, 0, grey);
      
      point1 = eng.pack(75.0, -10.0, 180.0);
      point2 = eng.pack(75.0, 20.0, 180.0);
      point3 = eng.pack(75.0, -10.0, 240.0);
      normalp1 = eng.pack(0.5773,0.5773,0.5773);
      normalp2 = eng.pack(0.5773,0.5773,0.5773);
      normalp2 = eng.pack(0.5773,0.5773,0.5773);
      eng.GeometryAdd(point1, point2, point3, normalp1, normalp2, normalp3, 0, grey);
      
      point1 = eng.pack(75.0, 20.0, 180.0);
      point2 = eng.pack(75.0, -10.0, 240.0);
      point3 = eng.pack(75.0, 20.0, 240.0);
      normalp1 = eng.pack(0.5773,0.5773,0.5773);
      normalp2 = eng.pack(0.5773,0.5773,0.5773);
      normalp2 = eng.pack(0.5773,0.5773,0.5773);
      eng.GeometryAdd(point1, point2, point3, normalp1, normalp2, normalp3, 0, grey);
      
      point1 = eng.pack(-75.0, -10.0, 180.0);
      point2 = eng.pack(-75.0, 20.0, 180.0);
      point3 = eng.pack(-75.0, -10.0, 240.0);
      normalp1 = eng.pack(0.5773,0.5773,0.5773);
      normalp2 = eng.pack(0.5773,0.5773,0.5773);
      normalp2 = eng.pack(0.5773,0.5773,0.5773);
      eng.GeometryAdd(point1, point2, point3, normalp1, normalp2, normalp3, 0, grey);
      
      point1 = eng.pack(-75.0, 20.0, 180.0);
      point2 = eng.pack(-75.0, -10.0, 240.0);
      point3 = eng.pack(-75.0, 20.0, 240.0);
      normalp1 = eng.pack(0.5773,0.5773,0.5773);
      normalp2 = eng.pack(0.5773,0.5773,0.5773);
      normalp2 = eng.pack(0.5773,0.5773,0.5773);
      eng.GeometryAdd(point1, point2, point3, normalp1, normalp2, normalp3, 0, grey);
      
      point1 = eng.pack(30.0, -10.0, 180.0);
      point2 = eng.pack(30.0, 20.0, 180.0);
      point3 = eng.pack(30.0, -10.0, 240.0);
      normalp1 = eng.pack(0.5773,0.5773,0.5773);
      normalp2 = eng.pack(0.5773,0.5773,0.5773);
      normalp2 = eng.pack(0.5773,0.5773,0.5773);
      eng.GeometryAdd(point1, point2, point3, normalp1, normalp2, normalp3, 0, grey);
      
      point1 = eng.pack(30.0, -10.0, 240.0);
      point2 = eng.pack(30.0, 20.0, 240.0);
      point3 = eng.pack(30.0, 20.0, 180.0);
      normalp1 = eng.pack(0.5773,0.5773,0.5773);
      normalp2 = eng.pack(0.5773,0.5773,0.5773);
      normalp2 = eng.pack(0.5773,0.5773,0.5773);
      eng.GeometryAdd(point1, point2, point3, normalp1, normalp2, normalp3, 0, grey);
      
      point1 = eng.pack(-30.0, -10.0, 180.0);
      point2 = eng.pack(-30.0, 20.0, 180.0);
      point3 = eng.pack(-30.0, -10.0, 240.0);
      normalp1 = eng.pack(0.5773,0.5773,0.5773);
      normalp2 = eng.pack(0.5773,0.5773,0.5773);
      normalp2 = eng.pack(0.5773,0.5773,0.5773);
      eng.GeometryAdd(point1, point2, point3, normalp1, normalp2, normalp3, 0, grey);
      
      point1 = eng.pack(-30.0, -10.0, 240.0);
      point2 = eng.pack(-30.0, 20.0, 240.0);
      point3 = eng.pack(-30.0, 20.0, 180.0);
      normalp1 = eng.pack(0.5773,0.5773,0.5773);
      normalp2 = eng.pack(0.5773,0.5773,0.5773);
      normalp2 = eng.pack(0.5773,0.5773,0.5773);
      eng.GeometryAdd(point1, point2, point3, normalp1, normalp2, normalp3, 0, grey);
      
      point1 = eng.pack(30.0, -10.0, 30.0);
      point2 = eng.pack(30.0, -10.0, -30.0);
      point3 = eng.pack(-30.0, -10.0, 30.0);
      normalp1 = eng.pack(0.5773,0.5773,0.5773);
      normalp2 = eng.pack(0.5773,0.5773,0.5773);
      normalp2 = eng.pack(0.5773,0.5773,0.5773);
      eng.GeometryAdd(point1, point2, point3, normalp1, normalp2, normalp3, 0, grey);
      
      point1 = eng.pack(30.0, -10.0, -30.0);
      point2 = eng.pack(-30.0, -10.0, -30.0);
      point3 = eng.pack(-30.0, -10.0, 30.0);
      normalp1 = eng.pack(0.5773,0.5773,0.5773);
      normalp2 = eng.pack(0.5773,0.5773,0.5773);
      normalp2 = eng.pack(0.5773,0.5773,0.5773);
      eng.GeometryAdd(point1, point2, point3, normalp1, normalp2, normalp3, 0, grey);
      
      point1 = eng.pack(30.0, 20.0, 30.0);
      point2 = eng.pack(30.0, 20.0, -30.0);
      point3 = eng.pack(-30.0, 20.0, 30.0);
      normalp1 = eng.pack(0.5773,0.5773,0.5773);
      normalp2 = eng.pack(0.5773,0.5773,0.5773);
      normalp2 = eng.pack(0.5773,0.5773,0.5773);
      eng.GeometryAdd(point1, point2, point3, normalp1, normalp2, normalp3, 0, grey);
      
      point1 = eng.pack(30.0, 20.0, -30.0);
      point2 = eng.pack(-30.0, 20.0, -30.0);
      point3 = eng.pack(-30.0, 20.0, 30.0);
      normalp1 = eng.pack(0.5773,0.5773,0.5773);
      normalp2 = eng.pack(0.5773,0.5773,0.5773);
      normalp2 = eng.pack(0.5773,0.5773,0.5773);
      eng.GeometryAdd(point1, point2, point3, normalp1, normalp2, normalp3, 0, grey);
      
      point1 = eng.pack(45.0, -10.0, 30.0);
      point2 = eng.pack(45.0, -10.0, 120.0);
      point3 = eng.pack(-45.0, -10.0, 30.0);
      normalp1 = eng.pack(0.5773,0.5773,0.5773);
      normalp2 = eng.pack(0.5773,0.5773,0.5773);
      normalp2 = eng.pack(0.5773,0.5773,0.5773);
      eng.GeometryAdd(point1, point2, point3, normalp1, normalp2, normalp3, 0, grey);
      
      point1 = eng.pack(-45.0, -10.0, 30.0);
      point2 = eng.pack(-45.0, -10.0, 120.0);
      point3 = eng.pack(45.0, -10.0, 120.0);
      normalp1 = eng.pack(0.5773,0.5773,0.5773);
      normalp2 = eng.pack(0.5773,0.5773,0.5773);
      normalp2 = eng.pack(0.5773,0.5773,0.5773);
      eng.GeometryAdd(point1, point2, point3, normalp1, normalp2, normalp3, 0, grey);
      
      point1 = eng.pack(45.0, 20.0, 30.0);
      point2 = eng.pack(45.0, 20.0, 120.0);
      point3 = eng.pack(-45.0, 20.0, 30.0);
      normalp1 = eng.pack(0.5773,0.5773,0.5773);
      normalp2 = eng.pack(0.5773,0.5773,0.5773);
      normalp2 = eng.pack(0.5773,0.5773,0.5773);
      eng.GeometryAdd(point1, point2, point3, normalp1, normalp2, normalp3, 0, grey);
      
      point1 = eng.pack(-45.0, 20.0, 30.0);
      point2 = eng.pack(-45.0, 20.0, 120.0);
      point3 = eng.pack(45.0, 20.0, 120.0);
      normalp1 = eng.pack(0.5773,0.5773,0.5773);
      normalp2 = eng.pack(0.5773,0.5773,0.5773);
      normalp2 = eng.pack(0.5773,0.5773,0.5773);
      eng.GeometryAdd(point1, point2, point3, normalp1, normalp2, normalp3, 0, grey);
      
      point1 = eng.pack(45.0, -10.0, 120.0);
      point2 = eng.pack(75.0, -10.0, 180.0);
      point3 = eng.pack(45.0, -10.0, 180.0);
      normalp1 = eng.pack(0.5773,0.5773,0.5773);
      normalp2 = eng.pack(0.5773,0.5773,0.5773);
      normalp2 = eng.pack(0.5773,0.5773,0.5773);
      eng.GeometryAdd(point1, point2, point3, normalp1, normalp2, normalp3, 0, grey);
      
      point1 = eng.pack(45.0, -10.0, 120.0);
      point2 = eng.pack(45.0, -10.0, 180.0);
      point3 = eng.pack(-45.0, -10.0, 120.0);
      normalp1 = eng.pack(0.5773,0.5773,0.5773);
      normalp2 = eng.pack(0.5773,0.5773,0.5773);
      normalp2 = eng.pack(0.5773,0.5773,0.5773);
      eng.GeometryAdd(point1, point2, point3, normalp1, normalp2, normalp3, 0, grey);
      
      point1 = eng.pack(-45.0, -10.0, 120.0);
      point2 = eng.pack(-45.0, -10.0, 180.0);
      point3 = eng.pack(45.0, -10.0, 180.0);
      normalp1 = eng.pack(0.5773,0.5773,0.5773);
      normalp2 = eng.pack(0.5773,0.5773,0.5773);
      normalp2 = eng.pack(0.5773,0.5773,0.5773);
      eng.GeometryAdd(point1, point2, point3, normalp1, normalp2, normalp3, 0, grey);
      
      point1 = eng.pack(-45.0, -10.0, 120.0);
      point2 = eng.pack(-75.0, -10.0, 180.0);
      point3 = eng.pack(-45.0, -10.0, 180.0);
      normalp1 = eng.pack(0.5773,0.5773,0.5773);
      normalp2 = eng.pack(0.5773,0.5773,0.5773);
      normalp2 = eng.pack(0.5773,0.5773,0.5773);
      eng.GeometryAdd(point1, point2, point3, normalp1, normalp2, normalp3, 0, grey);
      
      point1 = eng.pack(45.0, 20.0, 120.0);
      point2 = eng.pack(75.0, 20.0, 180.0);
      point3 = eng.pack(45.0, 20.0, 180.0);
      normalp1 = eng.pack(0.5773,0.5773,0.5773);
      normalp2 = eng.pack(0.5773,0.5773,0.5773);
      normalp2 = eng.pack(0.5773,0.5773,0.5773);
      eng.GeometryAdd(point1, point2, point3, normalp1, normalp2, normalp3, 0, grey);
      
      point1 = eng.pack(45.0, 20.0, 120.0);
      point2 = eng.pack(45.0, 20.0, 180.0);
      point3 = eng.pack(-45.0, 20.0, 120.0);
      normalp1 = eng.pack(0.5773,0.5773,0.5773);
      normalp2 = eng.pack(0.5773,0.5773,0.5773);
      normalp2 = eng.pack(0.5773,0.5773,0.5773);
      eng.GeometryAdd(point1, point2, point3, normalp1, normalp2, normalp3, 0, grey);
      
      point1 = eng.pack(-45.0, 20.0, 120.0);
      point2 = eng.pack(-45.0, 20.0, 180.0);
      point3 = eng.pack(45.0, 20.0, 180.0);
      normalp1 = eng.pack(0.5773,0.5773,0.5773);
      normalp2 = eng.pack(0.5773,0.5773,0.5773);
      normalp2 = eng.pack(0.5773,0.5773,0.5773);
      eng.GeometryAdd(point1, point2, point3, normalp1, normalp2, normalp3, 0, grey);
      
      point1 = eng.pack(-45.0, 20.0, 120.0);
      point2 = eng.pack(-75.0, 20.0, 180.0);
      point3 = eng.pack(-45.0, 20.0, 180.0);
      normalp1 = eng.pack(0.5773,0.5773,0.5773);
      normalp2 = eng.pack(0.5773,0.5773,0.5773);
      normalp2 = eng.pack(0.5773,0.5773,0.5773);
      eng.GeometryAdd(point1, point2, point3, normalp1, normalp2, normalp3, 0, grey);
      
      point1 = eng.pack(30.0, -10.0, 180.0);
      point2 = eng.pack(30.0, -10.0, 240.0);
      point3 = eng.pack(75.0, -10.0, 240.0);
      normalp1 = eng.pack(0.5773,0.5773,0.5773);
      normalp2 = eng.pack(0.5773,0.5773,0.5773);
      normalp2 = eng.pack(0.5773,0.5773,0.5773);
      eng.GeometryAdd(point1, point2, point3, normalp1, normalp2, normalp3, 0, grey);
      
      point1 = eng.pack(30.0, -10.0, 180.0);
      point2 = eng.pack(75.0, -10.0, 180.0);
      point3 = eng.pack(75.0, -10.0, 240.0);
      normalp1 = eng.pack(0.5773,0.5773,0.5773);
      normalp2 = eng.pack(0.5773,0.5773,0.5773);
      normalp2 = eng.pack(0.5773,0.5773,0.5773);
      eng.GeometryAdd(point1, point2, point3, normalp1, normalp2, normalp3, 0, grey);
      
      point1 = eng.pack(-30.0, -10.0, 180.0);
      point2 = eng.pack(-30.0, -10.0, 240.0);
      point3 = eng.pack(-75.0, -10.0, 240.0);
      normalp1 = eng.pack(0.5773,0.5773,0.5773);
      normalp2 = eng.pack(0.5773,0.5773,0.5773);
      normalp2 = eng.pack(0.5773,0.5773,0.5773);
      eng.GeometryAdd(point1, point2, point3, normalp1, normalp2, normalp3, 0, grey);
      
      point1 = eng.pack(-30.0, -10.0, 180.0);
      point2 = eng.pack(-75.0, -10.0, 180.0);
      point3 = eng.pack(-75.0, -10.0, 240.0);
      normalp1 = eng.pack(0.5773,0.5773,0.5773);
      normalp2 = eng.pack(0.5773,0.5773,0.5773);
      normalp2 = eng.pack(0.5773,0.5773,0.5773);
      eng.GeometryAdd(point1, point2, point3, normalp1, normalp2, normalp3, 0, grey);
      
      point1 = eng.pack(30.0, 20.0, 180.0);
      point2 = eng.pack(30.0, 20.0, 240.0);
      point3 = eng.pack(75.0, 20.0, 240.0);
      normalp1 = eng.pack(0.5773,0.5773,0.5773);
      normalp2 = eng.pack(0.5773,0.5773,0.5773);
      normalp2 = eng.pack(0.5773,0.5773,0.5773);
      eng.GeometryAdd(point1, point2, point3, normalp1, normalp2, normalp3, 0, grey);
      
      point1 = eng.pack(30.0, 20.0, 180.0);
      point2 = eng.pack(75.0, 20.0, 180.0);
      point3 = eng.pack(75.0, 20.0, 240.0);
      normalp1 = eng.pack(0.5773,0.5773,0.5773);
      normalp2 = eng.pack(0.5773,0.5773,0.5773);
      normalp2 = eng.pack(0.5773,0.5773,0.5773);
      eng.GeometryAdd(point1, point2, point3, normalp1, normalp2, normalp3, 0, grey);
      
      point1 = eng.pack(-30.0, 20.0, 180.0);
      point2 = eng.pack(-30.0, 20.0, 240.0);
      point3 = eng.pack(-75.0, 20.0, 240.0);
      normalp1 = eng.pack(0.5773,0.5773,0.5773);
      normalp2 = eng.pack(0.5773,0.5773,0.5773);
      normalp2 = eng.pack(0.5773,0.5773,0.5773);
      eng.GeometryAdd(point1, point2, point3, normalp1, normalp2, normalp3, 0, grey);
      
      point1 = eng.pack(-30.0, 20.0, 180.0);
      point2 = eng.pack(-75.0, 20.0, 180.0);
      point3 = eng.pack(-75.0, 20.0, 240.0);
      normalp1 = eng.pack(0.5773,0.5773,0.5773);
      normalp2 = eng.pack(0.5773,0.5773,0.5773);
      normalp2 = eng.pack(0.5773,0.5773,0.5773);
      eng.GeometryAdd(point1, point2, point3, normalp1, normalp2, normalp3, 0, grey);
      
      point1 = eng.pack(30.0, -10.0, 240.0);
      point2 = eng.pack(75.0, -10.0, 290.0);
      point3 = eng.pack(30.0, 20.0, 240.0);
      normalp1 = eng.pack(0.5773,0.5773,0.5773);
      normalp2 = eng.pack(0.5773,0.5773,0.5773);
      normalp2 = eng.pack(0.5773,0.5773,0.5773);
      eng.GeometryAdd(point1, point2, point3, normalp1, normalp2, normalp3, 0, grey);
      
      point1 = eng.pack(30.0, 20.0, 240.0);
      point2 = eng.pack(75.0, -10.0, 290.0);
      point3 = eng.pack(75.0, 20.0, 290.0);
      normalp1 = eng.pack(0.5773,0.5773,0.5773);
      normalp2 = eng.pack(0.5773,0.5773,0.5773);
      normalp2 = eng.pack(0.5773,0.5773,0.5773);
      eng.GeometryAdd(point1, point2, point3, normalp1, normalp2, normalp3, 0, grey);
      
      point1 = eng.pack(75.0, -10.0, 240.0);
      point2 = eng.pack(125.0, -10.0, 240.0);
      point3 = eng.pack(75.0, 20.0, 240.0);
      normalp1 = eng.pack(0.5773,0.5773,0.5773);
      normalp2 = eng.pack(0.5773,0.5773,0.5773);
      normalp2 = eng.pack(0.5773,0.5773,0.5773);
      eng.GeometryAdd(point1, point2, point3, normalp1, normalp2, normalp3, 0, grey);
      
      point1 = eng.pack(125.0, -10.0, 240.0);
      point2 = eng.pack(125.0, 20.0, 240.0);
      point3 = eng.pack(75.0, 20.0, 240.0);
      normalp1 = eng.pack(0.5773,0.5773,0.5773);
      normalp2 = eng.pack(0.5773,0.5773,0.5773);
      normalp2 = eng.pack(0.5773,0.5773,0.5773);
      eng.GeometryAdd(point1, point2, point3, normalp1, normalp2, normalp3, 0, grey);
      
      point1 = eng.pack(30.0, -10.0, 240.0);
      point2 = eng.pack(75.0, -10.0, 290.0);
      point3 = eng.pack(75.0, -10.0, 240.0);
      normalp1 = eng.pack(0.5773,0.5773,0.5773);
      normalp2 = eng.pack(0.5773,0.5773,0.5773);
      normalp2 = eng.pack(0.5773,0.5773,0.5773);
      eng.GeometryAdd(point1, point2, point3, normalp1, normalp2, normalp3, 0, grey);
      //Shapes.Cube(eng.pack(eng.x, eng.y - 150.0, eng.z), 500.0, 500.0, 500.0, 0, eng.packint(0, 100, 255));
      //Shapes.SCube(eng.pack(0.0, 0.0, 45.0), 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, grey);
      //detail: (LOL)
      
      
      
      
      Shapes.Cube(eng.pack(0.0, -10.0, 85.0), 14.0, 33.0, 5.0, 0, wood, eng);
      Shapes.Cube(eng.pack(0.0, 15.0, 85.0), 14.0, 33.0, 5.0, 0, wood, eng);
      Shapes.Cube(eng.pack(44.0, -10.0, 75.0), 90.0, 2.0, 2.0, 0, wood, eng);
      Shapes.Cube(eng.pack(44.0, 18.0, 75.0), 90.0, 2.0, 2.0, 0, wood, eng);
      Shapes.Cube(eng.pack(-44.0, -10.0, 75.0), 90.0, 2.0, 2.0, 0, wood, eng);
      Shapes.Cube(eng.pack(-44.0, 18.0, 75.0), 90.0, 2.0, 2.0, 0, wood, eng);
      Shapes.Cube(eng.pack(44.0, -10.0, 31.0), 2.0, 2.0, 30.0, 0, wood, eng);
      Shapes.Cube(eng.pack(-44.0, -10.0, 31.0), 2.0, 2.0, 30.0, 0, wood, eng);
      Shapes.Cube(eng.pack(44.0, -10.0, 40.0), 2.0, 2.0, 30.0, 0, wood, eng);
      Shapes.Cube(eng.pack(-44.0, -10.0, 40.0), 2.0, 2.0, 30.0, 0, wood, eng);
      Shapes.Cube(eng.pack(44.0, -10.0, 50.0), 2.0, 2.0, 30.0, 0, wood, eng);
      Shapes.Cube(eng.pack(-44.0, -10.0, 50.0), 2.0, 2.0, 30.0, 0, wood, eng);
      Shapes.Cube(eng.pack(44.0, -10.0, 60.0), 2.0, 2.0, 30.0, 0, wood, eng);
      Shapes.Cube(eng.pack(-44.0, -10.0, 60.0), 2.0, 2.0, 30.0, 0, wood, eng);
      Shapes.Cube(eng.pack(44.0, -10.0, 70.0), 2.0, 2.0, 30.0, 0, wood, eng);
      Shapes.Cube(eng.pack(-44.0, -10.0, 70.0), 2.0, 2.0, 30.0, 0, wood, eng);
      Shapes.Cube(eng.pack(44.0, -10.0, 80.0), 2.0, 2.0, 30.0, 0, wood, eng);
      Shapes.Cube(eng.pack(-44.0, -10.0, 80.0), 2.0, 2.0, 30.0, 0, wood, eng);
      Shapes.Cube(eng.pack(44.0, -10.0, 90.0), 2.0, 2.0, 30.0, 0, wood, eng);
      Shapes.Cube(eng.pack(-44.0, -10.0, 90.0), 2.0, 2.0, 30.0, 0, wood, eng);
      
      Shapes.Cube(eng.pack(-29.0, -10.0, -29.0), 2.0, 2.0, 30.0, 0, wood, eng);
      Shapes.Cube(eng.pack(-29.0, -10.0, -20.0), 2.0, 2.0, 30.0, 0, wood, eng);
      Shapes.Cube(eng.pack(-29.0, -10.0, -10.0), 2.0, 2.0, 30.0, 0, wood, eng);
      Shapes.Cube(eng.pack(-29.0, -10.0, 0.0), 2.0, 2.0, 30.0, 0, wood, eng);
      Shapes.Cube(eng.pack(-29.0, -10.0, 10.0), 2.0, 2.0, 30.0, 0, wood, eng);
      Shapes.Cube(eng.pack(-29.0, -10.0, 20.0), 2.0, 2.0, 30.0, 0, wood, eng);
      Shapes.Cube(eng.pack(-29.0, -10.0, 31.0), 2.0, 2.0, 30.0, 0, wood, eng);
      
      
      Shapes.Cube(eng.pack(29.0, -10.0, -29.0), 2.0, 2.0, 30.0, 0, wood, eng);
      Shapes.Cube(eng.pack(29.0, -10.0, -20.0), 2.0, 2.0, 30.0, 0, wood, eng);
      Shapes.Cube(eng.pack(29.0, -10.0, -10.0), 2.0, 2.0, 30.0, 0, wood, eng);
      Shapes.Cube(eng.pack(29.0, -10.0, 0.0), 2.0, 2.0, 30.0, 0, wood, eng);
      Shapes.Cube(eng.pack(29.0, -10.0, 10.0), 2.0, 2.0, 30.0, 0, wood, eng);
      Shapes.Cube(eng.pack(29.0, -10.0, 20.0), 2.0, 2.0, 30.0, 0, wood, eng);
      Shapes.Cube(eng.pack(29.0, -10.0, 31.0), 2.0, 2.0, 30.0, 0, wood, eng);
      Shapes.Cube(eng.pack(20.0, -10.0, -29.0), 2.0, 2.0, 30.0, 0, wood, eng);
      Shapes.Cube(eng.pack(10.0, -10.0, -29.0), 2.0, 2.0, 30.0, 0, wood, eng);
      Shapes.Cube(eng.pack(0.0, -10.0, -29.0), 2.0, 2.0, 30.0, 0, wood, eng);
      Shapes.Cube(eng.pack(-10.0, -10.0, -29.0), 2.0, 2.0, 30.0, 0, wood, eng);
      Shapes.Cube(eng.pack(-20.0, -10.0, -29.0), 2.0, 2.0, 30.0, 0, wood, eng);
      
      Shapes.Cube(eng.pack(29.0, -10.0, 0.0), 60.0, 2.0, 2.0, 0, wood, eng);
      Shapes.Cube(eng.pack(-29.0, -10.0, 0.0), 60.0, 2.0, 2.0, 0, wood, eng);
      Shapes.Cube(eng.pack(29.0, 19.0, 0.0), 60.0, 2.0, 2.0, 0, wood, eng);
      Shapes.Cube(eng.pack(-29.0, 19.0, 0.0), 60.0, 2.0, 2.0, 0, wood, eng);
      
      Shapes.Cube(eng.pack(0.0, -10.0, -29.0), 2.0, 60.0, 2.0, 0, wood, eng);
      Shapes.Cube(eng.pack(0.0, 19.0, -29.0), 2.0, 60.0, 2.0, 0, wood, eng);
      
      Shapes.Cube(eng.pack(0.0, 18.0, -20.0), 2.0, 60.0, 3.0, 0, wood, eng);
      Shapes.Cube(eng.pack(0.0, 18.0, -10.0), 2.0, 60.0, 3.0, 0, wood, eng);
      Shapes.Cube(eng.pack(0.0, 18.0, 0.0), 2.0, 60.0, 3.0, 0, wood, eng);
      Shapes.Cube(eng.pack(0.0, 18.0, 10.0), 2.0, 60.0, 3.0, 0, wood, eng);
      Shapes.Cube(eng.pack(0.0, 18.0, 20.0), 2.0, 60.0, 3.0, 0, wood, eng);
      Shapes.Cube(eng.pack(0.0, 18.0, 31.0), 2.0, 90.0, 3.0, 0, wood, eng);
      
      Shapes.Cube(eng.pack(0.0, 18.0, 40.0), 2.0, 90.0, 3.0, 0, wood, eng);
      Shapes.Cube(eng.pack(0.0, 18.0, 50.0), 2.0, 90.0, 3.0, 0, wood, eng);
      Shapes.Cube(eng.pack(0.0, 18.0, 60.0), 2.0, 90.0, 3.0, 0, wood, eng);
      Shapes.Cube(eng.pack(0.0, 18.0, 70.0), 2.0, 90.0, 3.0, 0, wood, eng);
      Shapes.Cube(eng.pack(0.0, 18.0, 80.0), 2.0, 90.0, 3.0, 0, wood, eng);
      Shapes.Cube(eng.pack(0.0, 18.0, 90.0), 2.0, 90.0, 3.0, 0, wood, eng);
      
      Shapes.Cube(eng.pack(30.0, 18.0, 55.0), 10.0, 3.0, 3.0, 0, wood, eng);
      Shapes.Cube(eng.pack(-30.0, 18.0, 55.0), 10.0, 3.0, 3.0, 0, wood, eng);
      //da stairs (LOLOL):
      
      //Shapes.Edge(eng.pack(-25.0, -10.0, 26.0), 6.0, 1.0, 5.0, 0, 1, wood, eng);
      //Shapes.Edge(eng.pack(-16.0, -10.0, 26.0), 6.0, 1.0, 5.0, 0, 1, wood, eng);
      Shapes.Cube(eng.pack(-20.5, -5.5, 27.0), 6.0, 10.0, 1.0, 0, wood, eng);
      
      //Shapes.Edge(eng.pack(-25.0, -6.0, 21.2), 6.0, 1.0, 5.0, 0, 1, wood, eng);
      //Shapes.Edge(eng.pack(-16.0, -6.0, 21.2), 6.0, 1.0, 5.0, 0, 1, wood, eng);
      Shapes.Cube(eng.pack(-20.5, -1.5, 22.0), 6.0, 10.0, 1.0, 0, wood, eng);
      
      //Shapes.Edge(eng.pack(-25.0, -2.0, 16.4), 6.0, 1.0, 5.0, 0, 1, wood, eng);
      //Shapes.Edge(eng.pack(-16.0, -2.0, 16.4), 6.0, 1.0, 5.0, 0, 1, wood, eng);
      Shapes.Cube(eng.pack(-20.5, 2.5, 17.0), 6.0, 10.0, 1.0, 0, wood, eng);
      
      //Shapes.Edge(eng.pack(-25.0, 2.0, 11.6), 6.0, 1.0, 5.0, 0, 1, wood, eng);
      //Shapes.Edge(eng.pack(-16.0, 2.0, 11.6), 6.0, 1.0, 5.0, 0, 1, wood, eng);
      Shapes.Cube(eng.pack(-20.5, 6.5, 13.0), 6.0, 10.0, 1.0, 0, wood, eng);
      
      
      Shapes.Cube(eng.pack(0.0, -11.0, 0.0), 60.0, 60.0, 1.0, 0, cement, eng);
      Shapes.Cube(eng.pack(0.0, 20.0, 0.0), 60.0, 60.0, 1.0, 0, cement, eng);
      
      Shapes.Cube(eng.pack(0.0, -10.5, -30.0), 1.0, 60.0, 30.0, 0, cement, eng);
      
      Shapes.Cube(eng.pack(30.5, -10.0, 0.0), 60.0, 1.0, 30.0, 0, cement, eng);
      Shapes.Cube(eng.pack(-30.5, -10.0, 0.0), 60.0, 1.0, 30.0, 0, cement, eng);
      
      Shapes.Cube(eng.pack((15 / 2) + 30, -10.0, 29.5), 1.0, 15.0, 30.0, 0, cement, eng);
      Shapes.Cube(eng.pack(-(15 / 2) - 30, -10.0, 29.5), 1.0, 15.0, 30.0, 0, cement, eng);
      
      Shapes.Cube(eng.pack(0.0, -10.0, 85.0), 10.0, 30.0, 30.0, 0, cement, eng);
      
      Shapes.Cube(eng.pack(45.5, -10.0, ((120 - 30) / 2) + 30), 120 - 30, 1.0, 30.0, 0, cement, eng);
      Shapes.Cube(eng.pack(-45.5, -10.0, ((120 - 30) / 2) + 30), 120 - 30, 1.0, 30.0, 0, cement, eng);
      
      //Shapes.Cube(eng.pack(0.0, -11.0, 80.0), 90.0, 90.0, 1.0,0,cement,eng);
      
      Shapes.Cube(eng.pack(0.0, -11.0, 35.0), 10.0, 90.0, 1.0,0,cement,eng);
      Shapes.Cube(eng.pack(0.0, -11.0, 45.0), 10.0, 90.0, 1.0,0,cement,eng);
      Shapes.Cube(eng.pack(0.0, -11.0, 55.0), 10.0, 90.0, 1.0,0,cement,eng);
      Shapes.Cube(eng.pack(0.0, -11.0, 65.0), 10.0, 90.0, 1.0,0,cement,eng);
      Shapes.Cube(eng.pack(0.0, -11.0, 75.0), 10.0, 90.0, 1.0,0,cement,eng);
      Shapes.Cube(eng.pack(0.0, -11.0, 85.0), 10.0, 90.0, 1.0,0,cement,eng);
      Shapes.Cube(eng.pack(0.0, -11.0, 95.0), 10.0, 90.0, 1.0,0,cement,eng);
      Shapes.Cube(eng.pack(0.0, -11.0, 105.0), 10.0, 90.0, 1.0,0,cement,eng);
      Shapes.Cube(eng.pack(0.0, -11.0, 115.0), 10.0, 90.0, 1.0,0,cement,eng);
      Shapes.Cube(eng.pack(0.0, -11.0, 125.0), 10.0, 90.0, 1.0,0,cement,eng);
      Shapes.Cube(eng.pack(0.0, -11.0, 135.0), 10.0, 90.0, 1.0,0,cement,eng);
      Shapes.Cube(eng.pack(0.0, -11.0, 145.0), 10.0, 90.0, 1.0,0,cement,eng);
      Shapes.Cube(eng.pack(0.0, 20.0, 35.0), 10.0, 90.0, 1.0,0,cement,eng);
      Shapes.Cube(eng.pack(0.0, 20.0, 45.0), 10.0, 90.0, 1.0,0,cement,eng);
      Shapes.Cube(eng.pack(0.0, 20.0, 55.0), 10.0, 90.0, 1.0,0,cement,eng);
      Shapes.Cube(eng.pack(0.0, 20.0, 65.0), 10.0, 90.0, 1.0,0,cement,eng);
      Shapes.Cube(eng.pack(0.0, 20.0, 75.0), 10.0, 90.0, 1.0,0,cement,eng);
      Shapes.Cube(eng.pack(0.0, 20.0, 85.0), 10.0, 90.0, 1.0,0,cement,eng);
      Shapes.Cube(eng.pack(0.0, 20.0, 95.0), 10.0, 90.0, 1.0,0,cement,eng);
      Shapes.Cube(eng.pack(0.0, 20.0, 105.0), 10.0, 90.0, 1.0,0,cement,eng);
      Shapes.Cube(eng.pack(0.0, 20.0, 115.0), 10.0, 90.0, 1.0,0,cement,eng);
      Shapes.Cube(eng.pack(0.0, 20.0, 125.0), 10.0, 90.0, 1.0,0,cement,eng);
      Shapes.Cube(eng.pack(0.0, 20.0, 135.0), 10.0, 90.0, 1.0,0,cement,eng);
      Shapes.Cube(eng.pack(0.0, 20.0, 145.0), 10.0, 90.0, 1.0,0,cement,eng);
      
      
      
      
      
      /*
      double[][] normal = new double[8][3];
      double[][] points = new double[8][3];
      points[0] = eng.pack(10.0000,-10.0000,-10.0000);
      normal[0] = eng.pack(0.5773,-0.5773,-0.5773);
      points[1] = eng.pack(-10.0000,-10.0000,10.0000);
      normal[1] = eng.pack(-0.5773,-0.5773,0.5773);
      points[2] = eng.pack(10.0000,-10.0000,10.0000);
      normal[2] = eng.pack(0.5773,-0.5773,0.5773);
      points[3] = eng.pack(-10.0000,10.0000,10.0000);
      normal[3] = eng.pack(-0.5773,0.5773,0.5773);
      points[4] = eng.pack(10.0000,10.0000,-10.0000);
      normal[4] = eng.pack(0.5773,0.5773,-0.5773);
      points[5] = eng.pack(10.0000,10.0000,10.0000);
      normal[5] = eng.pack(0.5773,0.5773,0.5773);
      points[6] = eng.pack(-10.0000,-10.0000,-10.0000);
      normal[6] = eng.pack(-0.5773,-0.5773,-0.5773);
      points[7] = eng.pack(-10.0000,10.0000,-10.0000);
      normal[7] = eng.pack(-0.5773,0.5773,-0.5773);
      int p1 = 0;
      int p2 = 1;
      int p3 = 2;
      eng.GeometryAdd(points[p1], points[p2], points[p3], normal[p1], normal[p2], normal[p3], 0, cement);
      p1 = 3;
      p2 = 4;
      p3 = 5;
      eng.GeometryAdd(points[p1], points[p2], points[p3], normal[p1], normal[p2], normal[p3], 0, cement);
      p1 = 5;
      p2 = 0;
      p3 = 2;
      eng.GeometryAdd(points[p1], points[p2], points[p3], normal[p1], normal[p2], normal[p3], 0, cement);
      p1 = 4;
      p2 = 6;
      p3 = 0;
      eng.GeometryAdd(points[p1], points[p2], points[p3], normal[p1], normal[p2], normal[p3], 0, cement);
      p1 = 6;
      p2 = 3;
      p3 = 1;
      eng.GeometryAdd(points[p1], points[p2], points[p3], normal[p1], normal[p2], normal[p3], 0, cement);
      p1 = 2;
      p2 = 3;
      p3 = 5;
      eng.GeometryAdd(points[p1], points[p2], points[p3], normal[p1], normal[p2], normal[p3], 0, cement);
      p1 = 0;
      p2 = 6;
      p3 = 1;
      eng.GeometryAdd(points[p1], points[p2], points[p3], normal[p1], normal[p2], normal[p3], 0, cement);
      p1 = 3;
      p2 = 7;
      p3 = 4;
      eng.GeometryAdd(points[p1], points[p2], points[p3], normal[p1], normal[p2], normal[p3], 0, cement);
      p1 = 5;
      p2 = 4;
      p3 = 0;
      eng.GeometryAdd(points[p1], points[p2], points[p3], normal[p1], normal[p2], normal[p3], 0, cement);
      p1 = 4;
      p2 = 7;
      p3 = 6;
      eng.GeometryAdd(points[p1], points[p2], points[p3], normal[p1], normal[p2], normal[p3], 0, cement);
      p1 = 6;
      p2 = 7;
      p3 = 3;
      eng.GeometryAdd(points[p1], points[p2], points[p3], normal[p1], normal[p2], normal[p3], 0, cement);
      p1 = 2;
      p2 = 1;
      p3 = 3;
      eng.GeometryAdd(points[p1], points[p2], points[p3], normal[p1], normal[p2], normal[p3], 0, cement);
      */
      
      eng.filled = true;
      return;
   }
   public static void map3(Graphics g, RealEngine eng) {
      eng.DrawLine(-30.0, -10.0, -30.0, -30.0, -10.0, 30.0, 0, g);
      eng.DrawLine(30.0, 20.0, 30.0, -30.0, 20.0, -30.0, 0, g);
      eng.DrawLine(30.0, -10.0, 30.0, 30.0, -10.0, -30.0, 0, g);
      eng.DrawLine(30.0, -10.0, -30.0, -30.0, -10.0, -30.0, 0, g);
      eng.DrawLine(-30.0, 20.0, 30.0, -30.0, -10.0, 30.0, 0, g);
      eng.DrawLine(-30.0, 20.0, -30.0, -30.0, -10.0, -30.0, 0, g);
      eng.DrawLine(30.0, 20.0, 30.0, 30.0, -10.0, 30.0, 0, g);
      eng.DrawLine(30.0, 20.0, -30.0, 30.0, -10.0, -30.0, 0, g);
      eng.DrawLine(-30.0, 20.0, -30.0, -30.0, 20.0, 30.0, 0, g);
      eng.DrawLine(-30.0, 20.0, 30.0, 30.0, 20.0, 30.0, 0, g);
      eng.DrawLine(30.0, 20.0, 30.0, 30.0, 20.0, -30.0, 0, g);
      eng.DrawLine(30.0, 20.0, -30.0, -30.0, 20.0, -30.0, 0, g);
      eng.DrawLine(30.0, 20.0, -30.0, -30.0, -10.0, -30.0, 0, g);
      eng.DrawLine(-30.0, -10.0, 30.0, -30.0, 20.0, -30.0, 0, g);
      eng.DrawLine(30.0, -10.0, 30.0, 30.0, 20.0, -30.0, 0, g);
      eng.DrawLine(-30.0, -10.0, 30.0, -45.0, -10.0, 30.0, 0, g);
      eng.DrawLine(30.0, -10.0, 30.0, 45.0, -10.0, 30.0, 0, g);
      eng.DrawLine(45.0, -10.0, 30.0, 45.0, -10.0, 120.0, 0, g);
      eng.DrawLine(-45.0, -10.0, 30.0, -45.0, -10.0, 120.0, 0, g);
      eng.DrawLine(-15.0, -10.0, 80.0, 15.0, -10.0, 80.0, 0, g);
      eng.DrawLine(-15.0, -10.0, 80.0, -15.0, -10.0, 90.0, 0, g);
      eng.DrawLine(15.0, -10.0, 80.0, 15.0, -10.0, 90.0, 0, g);
      eng.DrawLine(15.0, -10.0, 90.0, -15.0, -10.0, 90.0, 0, g);
      eng.DrawLine(-45.0, -10.0, 120.0, -75.0, -10.0, 180.0, 0, g);
      eng.DrawLine(45.0, -10.0, 120.0, 75.0, -10.0, 180.0, 0, g);
      eng.DrawLine(-45.0, -10.0, 30.0, -45.0, 20.0, 30.0, 0, g);
      eng.DrawLine(45.0, -10.0, 30.0, 45.0, 20.0, 30.0, 0, g);
      eng.DrawLine(45.0, 20.0, 30.0, -45.0, 20.0, 30.0, 0, g);
      eng.DrawLine(45.0, 20.0, 30.0, 45.0, 20.0, 120.0, 0, g);
      eng.DrawLine(45.0, 20.0, 30.0, 45.0, -10.0, 120.0, 0, g);
      eng.DrawLine(45.0, 20.0, 120.0, 45.0, -10.0, 120.0, 0, g);
      eng.DrawLine(45.0, -10.0, 30.0, 30.0, 20.0, 30.0, 0, g);
      eng.DrawLine(-45.0, -10.0, 30.0, -30.0, 20.0, 30.0, 0, g);
      eng.DrawLine(-45.0, 20.0, 30.0, -45.0, 20.0, 120.0, 0, g);
      eng.DrawLine(-45.0, 20.0, 30.0, -45.0, -10.0, 120.0, 0, g);
      eng.DrawLine(-45.0, 20.0, 120.0, -45.0, -10.0, 120.0, 0, g);
      eng.DrawLine(15.0, -10.0, 80.0, 15.0, 20.0, 80.0, 0, g);
      eng.DrawLine(-15.0, -10.0, 80.0, -15.0, 20.0, 80.0, 0, g);
      eng.DrawLine(-15.0, -10.0, 90.0, -15.0, 20.0, 90.0, 0, g);
      eng.DrawLine(15.0, -10.0, 90.0, 15.0, 20.0, 90.0, 0, g);
      eng.DrawLine(15.0, 20.0, 90.0, -15.0, -10.0, 90.0, 0, g);
      eng.DrawLine(15.0, 20.0, 90.0, -15.0, 20.0, 90.0, 0, g);
      eng.DrawLine(15.0, 20.0, 90.0, 15.0, 20.0, 80.0, 0, g);
      eng.DrawLine(15.0, 20.0, 80.0, -15.0, 20.0, 80.0, 0, g);
      eng.DrawLine(-15.0, 20.0, 80.0, -15.0, 20.0, 90.0, 0, g);
      eng.DrawLine(-15.0, 20.0, 80.0, -15.0, -10.0, 90.0, 0, g);
      eng.DrawLine(15.0, 20.0, 80.0, 15.0, -10.0, 90.0, 0, g);
      eng.DrawLine(15.0, 20.0, 80.0, -15.0, -10.0, 80.0, 0, g);
      eng.DrawLine(-45.0, 20.0, 120.0, -75.0, 20.0, 180.0, 0, g);
      eng.DrawLine(-45.0, 20.0, 120.0, -75.0, -10.0, 180.0, 0, g);
      eng.DrawLine(-75.0, 20.0, 180.0, -75.0, -10.0, 180.0, 0, g);
      eng.DrawLine(45.0, 20.0, 120.0, 75.0, 20.0, 180.0, 0, g);
      eng.DrawLine(45.0, 20.0, 120.0, 75.0, -10.0, 180.0, 0, g);
      eng.DrawLine(75.0, 20.0, 180.0, 75.0, -10.0, 180.0, 0, g);
      eng.DrawLine(75.0, -10.0, 180.0, 75.0, -10.0, 240.0, 0, g);
      eng.DrawLine(-75.0, -10.0, 180.0, -75.0, -10.0, 240.0, 0, g);
      eng.DrawLine(-75.0, 20.0, 180.0, -75.0, 20.0, 240.0, 0, g);
      eng.DrawLine(-75.0, -10.0, 180.0, -75.0, 20.0, 240.0, 0, g);
      eng.DrawLine(-75.0, -10.0, 240.0, -75.0, 20.0, 240.0, 0, g);
      eng.DrawLine(-75.0, -10.0, 240.0, -115.0, -10.0, 240.0, 0, g);
      eng.DrawLine(-115.0, -10.0, 240.0, -75.0, 20.0, 240.0, 0, g);
      eng.DrawLine(-115.0, 20.0, 240.0, -75.0, 20.0, 240.0, 0, g);
      eng.DrawLine(-115.0, 20.0, 240.0, -115.0, -10.0, 240.0, 0, g);
      eng.DrawLine(-30.0, -10.0, 180.0, 30.0, -10.0, 180.0, 0, g);
      eng.DrawLine(-30.0, 20.0, 180.0, 30.0, 20.0, 180.0, 0, g);
      eng.DrawLine(-30.0, -10.0, 180.0, -30.0, -10.0, 240.0, 0, g);
      eng.DrawLine(-30.0, -10.0, 180.0, -30.0, 20.0, 180.0, 0, g);
      eng.DrawLine(-30.0, -10.0, 240.0, -75.0, -10.0, 290.0, 0, g);
      eng.DrawLine(-75.0, -10.0, 290.0, -115.0, -10.0, 290.0, 0, g);
      eng.DrawLine(-115.0, -10.0, 290.0, -115.0, 20.0, 290.0, 0, g);
      eng.DrawLine(-115.0, 20.0, 290.0, -75.0, 20.0, 290.0, 0, g);
      eng.DrawLine(-75.0, 20.0, 290.0, -30.0, 20.0, 240.0, 0, g);
      eng.DrawLine(-30.0, 20.0, 240.0, -30.0, 20.0, 180.0, 0, g);
      eng.DrawLine(-115.0, 20.0, 290.0, -75.0, -10.0, 290.0, 0, g);
      eng.DrawLine(-75.0, -10.0, 290.0, -75.0, 20.0, 290.0, 0, g);
      eng.DrawLine(-75.0, 20.0, 290.0, -30.0, -10.0, 240.0, 0, g);
      eng.DrawLine(-30.0, -10.0, 240.0, -30.0, 20.0, 240.0, 0, g);
      eng.DrawLine(-30.0, 20.0, 240.0, -30.0, -10.0, 180.0, 0, g);
      eng.DrawLine(-30.0, 20.0, 180.0, 30.0, -10.0, 180.0, 0, g);
      eng.DrawLine(30.0, -10.0, 180.0, 30.0, 20.0, 180.0, 0, g);
      eng.DrawLine(30.0, -10.0, 180.0, 30.0, -10.0, 240.0, 0, g);
      eng.DrawLine(30.0, -10.0, 240.0, 75.0, -10.0, 290.0, 0, g);
      eng.DrawLine(30.0, 20.0, 240.0, 30.0, 20.0, 180.0, 0, g);
      eng.DrawLine(30.0, 20.0, 180.0, 30.0, -10.0, 240.0, 0, g);
      eng.DrawLine(30.0, -10.0, 240.0, 30.0, 20.0, 240.0, 0, g);
      eng.DrawLine(30.0, 20.0, 240.0, 75.0, 20.0, 290.0, 0, g);
      eng.DrawLine(75.0, 20.0, 290.0, 30.0, -10.0, 240.0, 0, g);
      eng.DrawLine(75.0, 20.0, 290.0, 75.0, -10.0, 290.0, 0, g);
      eng.DrawLine(75.0, 20.0, 240.0, 75.0, 20.0, 180.0, 0, g);
      eng.DrawLine(75.0, 20.0, 240.0, 75.0, -10.0, 240.0, 0, g);
      eng.DrawLine(75.0, 20.0, 240.0, 75.0, -10.0, 180.0, 0, g);
      eng.DrawLine(75.0, -10.0, 240.0, 125.0, -10.0, 240.0, 0, g);
      eng.DrawLine(75.0, 20.0, 240.0, 125.0, 20.0, 240.0, 0, g);
      eng.DrawLine(75.0, 20.0, 240.0, 125.0, -10.0, 240.0, 0, g);
      eng.DrawLine(125.0, -10.0, 240.0, 125.0, 20.0, 240.0, 0, g);
      eng.DrawLine(75.0, -10.0, 290.0, 125.0, -10.0, 290.0, 0, g);
      eng.DrawLine(75.0, 20.0, 290.0, 125.0, 20.0, 290.0, 0, g);
      eng.DrawLine(125.0, 20.0, 290.0, 125.0, -10.0, 290.0, 0, g);
      eng.DrawLine(75.0, -10.0, 290.0, 125.0, 20.0, 290.0, 0, g);
   
   }
   public static void map_windows(RealEngine eng) {
      double[] point1 = new double[3];//eng.pack(15.0, -10.0, 90.0);
      double[] point2 = new double[3];//eng.pack(15.0, 20.0, 90.0);
      double[] point3 = new double[3];//eng.pack(-15.0, 20.0, 90.0);
      //eng.GeometryAdd(point1, point2, point3);
      int[] red = eng.packint4(255, 255, 255, 100);
      int[] green = eng.packint(255, 255, 255);
      int[] blue = eng.packint(255, 255, 255);
      int[] white = eng.packint(255, 255, 255);
      int[] black = eng.packint(255, 255, 255);
      int[] grey = eng.packint(132, 133, 124);
      int[] cement = eng.packint(132, 133, 124);
      int[] wood = eng.packint(185, 156, 107);
   }
   public static double[][] getLights() {
      double[][] lights = new double[1][3];
      lights[0][0] = 0.0;
      lights[0][1] = 0.0;
      lights[0][2] = -40;
      /*
      lights[1][0] = 0.0;
      lights[1][1] = 0.0;
      lights[1][2] = dist;
      
      lights[2][0] = 0.0;
      lights[2][1] = 0.0;
      lights[2][2] = 0.0;
      
      lights[3][0] = 33.0;
      lights[3][1] = 15.0;
      lights[3][2] = 120.0;
      lights[4][0] = 0.0;
      lights[4][1] = 0.0;
      lights[4][2] = 0.0;
      lights[5][0] = 0.0;
      lights[5][1] = 0.0;
      lights[5][2] = 0.0;
      lights[6][0] = 0.0;
      lights[6][1] = 0.0;
      lights[6][2] = 0.0;
      */
      if (dist > 0){
         dist -= 0.05;
      }
      return lights;
   }
   public static double[][] getLightColor() {
      double[][] color = new double[20][3];
      color[0][0] = 255.0;
      color[0][1] = 255.0;
      color[0][2] = 255.0;
      color[1][0] = 255.0;
      color[1][1] = 255.0;
      color[1][2] = 255.0;
      color[2][0] = 255.0;
      color[2][1] = 255.0;
      color[2][2] = 255.0;
      color[3][0] = 255.0;
      color[3][1] = 255.0;
      color[3][2] = 255.0;
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
   }/*
   public static void test(RealEngine eng) {
      eng.setupangles();
      
      double[] point1 = new double[3];//eng.pack(15.0, -10.0, 90.0);
      double[] point2 = new double[3];//eng.pack(15.0, 20.0, 90.0);
      double[] point3 = new double[3];//eng.pack(-15.0, 20.0, 90.0);
      //eng.GeometryAdd(point1, point2, point3);
      int[] red = eng.packint(255, 255, 255);
      int[] green = eng.packint(255, 255, 255);
      int[] blue = eng.packint(255, 255, 255);
      int[] white = eng.packint(255, 255, 255);
      int[] black = eng.packint(255, 255, 255);
      int[] grey = eng.packint(132, 133, 124);
      int[] cement = eng.packint(255, 120, 148);
      int[] wood = eng.packint(185, 156, 107);
      int[] Color = eng.packint(255, 100, 59);
      
      point1 = eng.pack(30.0, -10.0, 20.0);
      point2 = eng.pack(30.0, -10.0, -20.0);
      point3 = eng.pack(-30.0, -10.0, 20.0);
      eng.GeometryAdd(point1, point2, point3, 0, wood);
      
      point1 = eng.pack(30.0, -10.0, -20.0);
      point2 = eng.pack(-30.0, -10.0, -20.0);
      point3 = eng.pack(-30.0, -10.0, 20.0);
      eng.GeometryAdd(point1, point2, point3, 0, wood);
      
      Shapes.Cube(eng.pack(20.0, -10.0, 22.0), 2.5, 10.0, 20.0, 0, Color, eng);
   }*/
}