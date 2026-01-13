import java.io.*;
import java.awt.*;
public class Shapes {
   public static void Cube(double[] loc, double length, double width, double height, double sky, int[] color3, RealEngine eng) {
      double xleft = (loc[0] - (width / 2));
      double yup = loc[1] + height;
      double zback = (loc[2] - (length / 2));
      double xright = (loc[0] + (width / 2));
      double ydown = loc[1];
      double zfront = (loc[2] + (length / 2));
      double[] point1 = new double[3];
      double[] point2 = new double[3];
      double[] point3 = new double[3];
      double[] normal_p1 = new double[3];
      double[] normal_p2 = new double[3];
      double[] normal_p3 = new double[3];
      //cannot exceed 1 (int) or -1 (int), no, not even 1.0000001 could work nor -1.00001
      //the normal is the same for all points, except if its a smooth edge, then you would want to ajust it for that point
      //this is used to compute the intensity of light on a surface, and to see if a light source effects its color or brightness
      //with out it, you get an effect where light passes through solid objects and can only be stopped by distance
      //honestly, they need to rename the normal to something else like point direction, face data, or even face direction
      int skybox = (int) sky;
      point1 = eng.pack(xleft, ydown, zback);
      point2 = eng.pack(xright, ydown, zback);
      point3 = eng.pack(xleft, yup, zback);
      normal_p1 = eng.pack(-0.5773, -0.5773, -0.5773);
      normal_p2 = eng.pack(0.5773, -0.5773, -0.5773);
      normal_p3 = eng.pack(-0.5773, 0.5773, -0.5773);
      eng.GeometryAdd(point1, point2, point3, normal_p1, normal_p2, normal_p3, skybox, color3);
      
      point1 = eng.pack(xleft, yup, zback);
      point2 = eng.pack(xright, yup, zback);
      point3 = eng.pack(xright, ydown, zback);
      normal_p1 = eng.pack(-0.5773, 0.5773, -0.5773);
      normal_p2 = eng.pack(0.5773, 0.5773, -0.5773);
      normal_p3 = eng.pack(0.5773, -0.5773, -0.5773);
      eng.GeometryAdd(point1, point2, point3, normal_p1, normal_p2, normal_p3, skybox, color3);
      
      point1 = eng.pack(xleft, ydown, zfront);
      point2 = eng.pack(xright, ydown, zfront);
      point3 = eng.pack(xleft, yup, zfront);
      normal_p1 = eng.pack(-0.5773, -0.5773, 0.5773);
      normal_p2 = eng.pack(0.5773, -0.5773, 0.5773);
      normal_p3 = eng.pack(-0.5773, 0.5773, 0.5773);
      eng.GeometryAdd(point1, point2, point3, normal_p1, normal_p2, normal_p3, skybox, color3);
      
      point1 = eng.pack(xleft, yup, zfront);
      point2 = eng.pack(xright, yup, zfront);
      point3 = eng.pack(xright, ydown, zfront);
      normal_p1 = eng.pack(-0.5773, 0.5773, 0.5773);
      normal_p2 = eng.pack(0.5773, 0.5773, 0.5773);
      normal_p3 = eng.pack(0.5773, -0.5773, 0.5773);
      eng.GeometryAdd(point1, point2, point3, normal_p1, normal_p2, normal_p3, skybox, color3);
      
      point1 = eng.pack(xleft, ydown, zback);
      point2 = eng.pack(xleft, ydown, zfront);
      point3 = eng.pack(xleft, yup, zfront);
      normal_p1 = eng.pack(-0.5773, -0.5773, -0.5773);
      normal_p2 = eng.pack(-0.5773, -0.5773, 0.5773);
      normal_p3 = eng.pack(-0.5773, 0.5773, 0.5773);
      eng.GeometryAdd(point1, point2, point3, normal_p1, normal_p2, normal_p3, skybox, color3);
      
      point1 = eng.pack(xleft, yup, zfront);
      point2 = eng.pack(xleft, yup, zback);
      point3 = eng.pack(xleft, ydown, zback);
      normal_p1 = eng.pack(-0.5773, 0.5773, 0.5773);
      normal_p2 = eng.pack(-0.5773, 0.5773, -0.5773);
      normal_p3 = eng.pack(-0.5773, -0.5773, -0.5773);
      eng.GeometryAdd(point1, point2, point3, normal_p1, normal_p2, normal_p3, skybox, color3);
      
      point1 = eng.pack(xright, ydown, zback);
      point2 = eng.pack(xright, ydown, zfront);
      point3 = eng.pack(xright, yup, zfront);
      normal_p1 = eng.pack(0.5773, -0.5773, -0.5773);
      normal_p2 = eng.pack(0.5773, -0.5773, 0.5773);
      normal_p3 = eng.pack(0.5773, 0.5773, 0.5773);
      eng.GeometryAdd(point1, point2, point3, normal_p1, normal_p2, normal_p3, skybox, color3);
      
      point1 = eng.pack(xright, yup, zfront);
      point2 = eng.pack(xright, yup, zback);
      point3 = eng.pack(xright, ydown, zback);
      normal_p1 = eng.pack(0.5773, 0.5773, 0.5773);
      normal_p2 = eng.pack(0.5773, 0.5773, -0.5773);
      normal_p3 = eng.pack(0.5773, -0.5773, -0.5773);
      eng.GeometryAdd(point1, point2, point3, normal_p1, normal_p2, normal_p3, skybox, color3);
      
      point1 = eng.pack(xleft, yup, zback);
      point2 = eng.pack(xleft, yup, zfront);
      point3 = eng.pack(xright, yup, zback);
      normal_p1 = eng.pack(-0.5773, 0.5773, -0.5773);
      normal_p2 = eng.pack(-0.5773, 0.5773, 0.5773);
      normal_p3 = eng.pack(0.5773, 0.5773, -0.5773);
      eng.GeometryAdd(point1, point2, point3, normal_p1, normal_p2, normal_p3, skybox, color3);
      
      point1 = eng.pack(xleft, yup, zfront);
      point2 = eng.pack(xright, yup, zfront);
      point3 = eng.pack(xright, yup, zback);
      normal_p1 = eng.pack(-0.5773, 0.5773, 0.5773);
      normal_p2 = eng.pack(0.5773, 0.5773, 0.5773);
      normal_p3 = eng.pack(0.5773, 0.5773, -0.5773);
      eng.GeometryAdd(point1, point2, point3, normal_p1, normal_p2, normal_p3, skybox, color3);
      
      point1 = eng.pack(xleft, ydown, zback);
      point2 = eng.pack(xleft, ydown, zfront);
      point3 = eng.pack(xright, ydown, zback);
      normal_p1 = eng.pack(-0.5773, -0.5773, -0.5773);
      normal_p2 = eng.pack(-0.5773, -0.5773, 0.5773);
      normal_p3 = eng.pack(0.5773, -0.5773, -0.5773);
      eng.GeometryAdd(point1, point2, point3, normal_p1, normal_p2, normal_p3, skybox, color3);
      
      point1 = eng.pack(xleft, ydown, zfront);
      point2 = eng.pack(xright, ydown, zfront);
      point3 = eng.pack(xright, ydown, zback);
      normal_p1 = eng.pack(-0.5773, -0.5773, 0.5773);
      normal_p2 = eng.pack(0.5773, -0.5773, 0.5773);
      normal_p3 = eng.pack(0.5773, -0.5773, -0.5773);
      eng.GeometryAdd(point1, point2, point3, normal_p1, normal_p2, normal_p3, skybox, color3);
      return;
   }/*
   public static void Pyramid(double[] loc, double height, double length, double width, int[] color3, RealEngine eng) {
      //same as cube, use loc to find the top (x & z + height)
      double xleft = (loc[0] - (width / 2));
      double zback = (loc[2] - (length / 2));
      double xright = (loc[0] + (width / 2));
      double ydown = loc[1];
      double zfront = (loc[2] + (length / 2));
      double yup = loc[1] + height;
      
      
      double[] point1 = new double[3];
      double[] point2 = new double[3];
      double[] point3 = new double[3];
      
      point1 = eng.pack(xleft, ydown, zback);
      point2 = eng.pack(xleft, ydown, zfront);
      point3 = eng.pack(xright, ydown, zback);
      eng.GeometryAdd(point1, point2, point3, 0, color3);
      
      point1 = eng.pack(xleft, ydown, zfront);
      point2 = eng.pack(xright, ydown, zfront);
      point3 = eng.pack(xright, ydown, zback);
      eng.GeometryAdd(point1, point2, point3, 0, color3);
      
      point1 = eng.pack(xleft, ydown, zfront);
      point2 = eng.pack(xright, ydown, zfront);
      point3 = eng.pack(loc[0], loc[1] + height, loc[2]);
      eng.GeometryAdd(point1, point2, point3, 0, color3);
      
      point1 = eng.pack(xleft, ydown, zfront);
      point2 = eng.pack(xleft, ydown, zback);
      point3 = eng.pack(loc[0], loc[1] + height, loc[2]);
      eng.GeometryAdd(point1, point2, point3, 0, color3);
      
      point1 = eng.pack(xright, ydown, zfront);
      point2 = eng.pack(xright, ydown, zback);
      point3 = eng.pack(loc[0], loc[1] + height, loc[2]);
      eng.GeometryAdd(point1, point2, point3, 0, color3);
      
      point1 = eng.pack(xleft, ydown, zback);
      point2 = eng.pack(xright, ydown, zback);
      point3 = eng.pack(loc[0], loc[1] + height, loc[2]);
      eng.GeometryAdd(point1, point2, point3, 0, color3);
      return;
   }
   public static void Edge(double[] loc, double length, double width, double height, int direction, int updown, int[] color3, RealEngine eng) {
                          //location      lenght         width              height     0,1,2,3         0,1         color
                                                         //                                          0down 1up
      double xleft = (loc[0] - (width / 2));
      double zback = (loc[2] - (length / 2));
      double xright = (loc[0] + (width / 2));
      double ydown = loc[1];
      double zfront = (loc[2] + (length / 2));
      double yup = loc[1] + height;
      double[] point1 = new double[3];
      double[] point2 = new double[3];
      double[] point3 = new double[3];
      //base
      if (updown == 0) {
         point1 = eng.pack(xleft, ydown, zback);
         point2 = eng.pack(xleft, ydown, zfront);
         point3 = eng.pack(xright, ydown, zfront);
         eng.GeometryAdd(point1, point2, point3, 0, color3);
         point1 = eng.pack(xleft, ydown, zback);
         point2 = eng.pack(xright, ydown, zfront);
         point3 = eng.pack(xright, ydown, zback);
         eng.GeometryAdd(point1, point2, point3, 0, color3);
         if (direction == 0) {
            point1 = eng.pack(xleft, ydown, zback);
            point2 = eng.pack(xleft, ydown, zfront);
            point3 = eng.pack(xleft, yup, zfront);
            eng.GeometryAdd(point1, point2, point3, 0, color3);
            point1 = eng.pack(xright, ydown, zback);
            point2 = eng.pack(xright, ydown, zfront);
            point3 = eng.pack(xright, yup, zfront);
            eng.GeometryAdd(point1, point2, point3, 0, color3);
            point1 = eng.pack(xleft, ydown, zfront);
            point2 = eng.pack(xright, ydown, zfront);
            point3 = eng.pack(xright, yup, zfront);
            eng.GeometryAdd(point1, point2, point3, 0, color3);
            point1 = eng.pack(xleft, ydown, zfront);
            point2 = eng.pack(xleft, yup, zfront);
            point3 = eng.pack(xright, yup, zfront);
            eng.GeometryAdd(point1, point2, point3, 0, color3);
            point1 = eng.pack(xleft, yup, zfront);
            point2 = eng.pack(xleft, ydown, zback);
            point3 = eng.pack(xright, ydown, zback);
            eng.GeometryAdd(point1, point2, point3, 0, color3);
            point1 = eng.pack(xleft, yup, zfront);
            point2 = eng.pack(xright, yup, zfront);
            point3 = eng.pack(xright, ydown, zback);
            eng.GeometryAdd(point1, point2, point3, 0, color3);
         } else if (direction == 1) {
            point1 = eng.pack(xleft, ydown, zback);
            point2 = eng.pack(xleft, yup, zback);
            point3 = eng.pack(xright, ydown, zback);
            eng.GeometryAdd(point1, point2, point3, 0, color3);
            point1 = eng.pack(xleft, ydown, zfront);
            point2 = eng.pack(xleft, yup, zfront);
            point3 = eng.pack(xright, ydown, zfront);
            eng.GeometryAdd(point1, point2, point3, 0, color3);
            point1 = eng.pack(xleft, ydown, zback);
            point2 = eng.pack(xleft, ydown, zfront);
            point3 = eng.pack(xleft, yup, zback);
            eng.GeometryAdd(point1, point2, point3, 0, color3);
            point1 = eng.pack(xleft, yup, zback);
            point2 = eng.pack(xleft, yup, zfront);
            point3 = eng.pack(xleft, ydown, zfront);
            eng.GeometryAdd(point1, point2, point3, 0, color3);
            point1 = eng.pack(xleft, yup, zback);
            point2 = eng.pack(xleft, yup, zfront);
            point3 = eng.pack(xright, ydown, zfront);
            eng.GeometryAdd(point1, point2, point3, 0, color3);
            point1 = eng.pack(xleft, yup, zback);
            point2 = eng.pack(xright, ydown, zback);
            point3 = eng.pack(xright, ydown, zfront);
            eng.GeometryAdd(point1, point2, point3, 0, color3);
         } else if (direction == 2) {
            point1 = eng.pack(xright, ydown, zback);
            point2 = eng.pack(xright, yup, zback);
            point3 = eng.pack(xleft, ydown, zback);
            eng.GeometryAdd(point1, point2, point3, 0, color3);
            point1 = eng.pack(xright, ydown, zfront);
            point2 = eng.pack(xright, yup, zfront);
            point3 = eng.pack(xleft, ydown, zfront);
            eng.GeometryAdd(point1, point2, point3, 0, color3);
            point1 = eng.pack(xright, ydown, zback);
            point2 = eng.pack(xright, ydown, zfront);
            point3 = eng.pack(xright, yup, zback);
            eng.GeometryAdd(point1, point2, point3, 0, color3);
            point1 = eng.pack(xright, yup, zback);
            point2 = eng.pack(xright, yup, zfront);
            point3 = eng.pack(xright, ydown, zfront);
            eng.GeometryAdd(point1, point2, point3, 0, color3);
            point1 = eng.pack(xright, yup, zback);
            point2 = eng.pack(xright, yup, zfront);
            point3 = eng.pack(xleft, ydown, zfront);
            eng.GeometryAdd(point1, point2, point3, 0, color3);
            point1 = eng.pack(xright, yup, zback);
            point2 = eng.pack(xleft, ydown, zback);
            point3 = eng.pack(xleft, ydown, zfront);
            eng.GeometryAdd(point1, point2, point3, 0, color3);
         } else if (direction == 3) {
            point1 = eng.pack(xleft, ydown, zfront);
            point2 = eng.pack(xleft, ydown, zback);
            point3 = eng.pack(xleft, yup, zback);
            eng.GeometryAdd(point1, point2, point3, 0, color3);
            point1 = eng.pack(xright, ydown, zfront);
            point2 = eng.pack(xright, ydown, zback);
            point3 = eng.pack(xright, yup, zback);
            eng.GeometryAdd(point1, point2, point3, 0, color3);
            point1 = eng.pack(xleft, ydown, zback);
            point2 = eng.pack(xright, ydown, zback);
            point3 = eng.pack(xright, yup, zback);
            eng.GeometryAdd(point1, point2, point3, 0, color3);
            point1 = eng.pack(xleft, ydown, zback);
            point2 = eng.pack(xleft, yup, zback);
            point3 = eng.pack(xright, yup, zback);
            eng.GeometryAdd(point1, point2, point3, 0, color3);
            point1 = eng.pack(xleft, yup, zback);
            point2 = eng.pack(xleft, ydown, zfront);
            point3 = eng.pack(xright, ydown, zfront);
            eng.GeometryAdd(point1, point2, point3, 0, color3);
            point1 = eng.pack(xleft, yup, zback);
            point2 = eng.pack(xright, yup, zback);
            point3 = eng.pack(xright, ydown, zfront);
            eng.GeometryAdd(point1, point2, point3, 0, color3);
         } else {
            System.out.println("Unknown direction: " + direction);
         }
      } else {
         point1 = eng.pack(xleft, yup, zback);
         point2 = eng.pack(xleft, yup, zfront);
         point3 = eng.pack(xright, yup, zfront);
         eng.GeometryAdd(point1, point2, point3, 0, color3);
         point1 = eng.pack(xleft, yup, zback);
         point2 = eng.pack(xright, yup, zfront);
         point3 = eng.pack(xright, yup, zback);
         eng.GeometryAdd(point1, point2, point3, 0, color3);
         if (direction == 0) {
            point1 = eng.pack(xleft, yup, zback);
            point2 = eng.pack(xleft, yup, zfront);
            point3 = eng.pack(xleft, ydown, zfront);
            eng.GeometryAdd(point1, point2, point3, 0, color3);
            point1 = eng.pack(xright, yup, zback);
            point2 = eng.pack(xright, yup, zfront);
            point3 = eng.pack(xright, ydown, zfront);
            eng.GeometryAdd(point1, point2, point3, 0, color3);
            point1 = eng.pack(xleft, yup, zfront);
            point2 = eng.pack(xright, yup, zfront);
            point3 = eng.pack(xright, ydown, zfront);
            eng.GeometryAdd(point1, point2, point3, 0, color3);
            point1 = eng.pack(xleft, yup, zfront);
            point2 = eng.pack(xleft, ydown, zfront);
            point3 = eng.pack(xright, ydown, zfront);
            eng.GeometryAdd(point1, point2, point3, 0, color3);
            point1 = eng.pack(xleft, ydown, zfront);
            point2 = eng.pack(xleft, yup, zback);
            point3 = eng.pack(xright, yup, zback);
            eng.GeometryAdd(point1, point2, point3, 0, color3);
            point1 = eng.pack(xleft, ydown, zfront);
            point2 = eng.pack(xright, ydown, zfront);
            point3 = eng.pack(xright, yup, zback);
            eng.GeometryAdd(point1, point2, point3, 0, color3);
         } else if (direction == 1) {
            point1 = eng.pack(xleft, yup, zback);
            point2 = eng.pack(xleft, ydown, zback);
            point3 = eng.pack(xright, yup, zback);
            eng.GeometryAdd(point1, point2, point3, 0, color3);
            point1 = eng.pack(xleft, yup, zfront);
            point2 = eng.pack(xleft, ydown, zfront);
            point3 = eng.pack(xright, yup, zfront);
            eng.GeometryAdd(point1, point2, point3, 0, color3);
            point1 = eng.pack(xleft, yup, zback);
            point2 = eng.pack(xleft, yup, zfront);
            point3 = eng.pack(xleft, ydown, zback);
            eng.GeometryAdd(point1, point2, point3, 0, color3);
            point1 = eng.pack(xleft, ydown, zback);
            point2 = eng.pack(xleft, ydown, zfront);
            point3 = eng.pack(xleft, yup, zfront);
            eng.GeometryAdd(point1, point2, point3, 0, color3);
            point1 = eng.pack(xleft, ydown, zback);
            point2 = eng.pack(xleft, ydown, zfront);
            point3 = eng.pack(xright, yup, zfront);
            eng.GeometryAdd(point1, point2, point3, 0, color3);
            point1 = eng.pack(xleft, ydown, zback);
            point2 = eng.pack(xright, yup, zback);
            point3 = eng.pack(xright, yup, zfront);
            eng.GeometryAdd(point1, point2, point3, 0, color3);
         } else if (direction == 2) {
            point1 = eng.pack(xright, yup, zback);
            point2 = eng.pack(xright, ydown, zback);
            point3 = eng.pack(xleft, yup, zback);
            eng.GeometryAdd(point1, point2, point3, 0, color3);
            point1 = eng.pack(xright, yup, zfront);
            point2 = eng.pack(xright, ydown, zfront);
            point3 = eng.pack(xleft, yup, zfront);
            eng.GeometryAdd(point1, point2, point3, 0, color3);
            point1 = eng.pack(xright, yup, zback);
            point2 = eng.pack(xright, yup, zfront);
            point3 = eng.pack(xright, ydown, zback);
            eng.GeometryAdd(point1, point2, point3, 0, color3);
            point1 = eng.pack(xright, ydown, zback);
            point2 = eng.pack(xright, ydown, zfront);
            point3 = eng.pack(xright, yup, zfront);
            eng.GeometryAdd(point1, point2, point3, 0, color3);
            point1 = eng.pack(xright, ydown, zback);
            point2 = eng.pack(xright, ydown, zfront);
            point3 = eng.pack(xleft, yup, zfront);
            eng.GeometryAdd(point1, point2, point3, 0, color3);
            point1 = eng.pack(xright, ydown, zback);
            point2 = eng.pack(xleft, yup, zback);
            point3 = eng.pack(xleft, yup, zfront);
            eng.GeometryAdd(point1, point2, point3, 0, color3);
         } else if (direction == 3) {
            point1 = eng.pack(xleft, yup, zfront);
            point2 = eng.pack(xleft, yup, zback);
            point3 = eng.pack(xleft, ydown, zback);
            eng.GeometryAdd(point1, point2, point3, 0, color3);
            point1 = eng.pack(xright, yup, zfront);
            point2 = eng.pack(xright, yup, zback);
            point3 = eng.pack(xright, ydown, zback);
            eng.GeometryAdd(point1, point2, point3, 0, color3);
            point1 = eng.pack(xleft, yup, zback);
            point2 = eng.pack(xright, yup, zback);
            point3 = eng.pack(xright, ydown, zback);
            eng.GeometryAdd(point1, point2, point3, 0, color3);
            point1 = eng.pack(xleft, yup, zback);
            point2 = eng.pack(xleft, ydown, zback);
            point3 = eng.pack(xright, ydown, zback);
            eng.GeometryAdd(point1, point2, point3, 0, color3);
            point1 = eng.pack(xleft, ydown, zback);
            point2 = eng.pack(xleft, yup, zfront);
            point3 = eng.pack(xright, yup, zfront);
            eng.GeometryAdd(point1, point2, point3, 0, color3);
            point1 = eng.pack(xleft, ydown, zback);
            point2 = eng.pack(xright, ydown, zback);
            point3 = eng.pack(xright, yup, zfront);
            eng.GeometryAdd(point1, point2, point3, 0, color3);
         } else {
            System.out.println("Unknown direction: " + direction);
         }
      }
   }
   public static void Octahedron(double[] loc, double length, double width, double height, int size, RealEngine eng) {
      double xleft = (loc[0] - (width / 2));
      double zback = (loc[2] - (length / 2));
      double xright = (loc[0] + (width / 2));
      double ydown = loc[1];
      double zfront = (loc[2] + (length / 2));
      double yup = loc[1] + height;
      
      
      double[] point1 = new double[3];
      double[] point2 = new double[3];
      double[] point3 = new double[3];
      
     
      
      
      return; //memes
   }
   public static void Floor(double level, int[] color3, RealEngine eng) { //bring in the realengine object so we dont have to get messy
      //loc y is the level
      double[] point1 = new double[3];
      double[] point2 = new double[3];
      double[] point3 = new double[3];
      
      double thing = 150;
      point1 = eng.pack(eng.getX() - thing, level, eng.getZ() - thing);
      point2 = eng.pack(eng.getX() - thing, level, eng.getZ() + thing);
      point3 = eng.pack(eng.getX() + thing, level, eng.getZ() + thing);
      eng.GeometryAdd(point1, point2, point3, 0, color3);
      
      point1 = eng.pack(eng.getX() - thing, level, eng.getZ() - thing);
      point2 = eng.pack(eng.getX() + thing, level, eng.getZ() + thing);
      point3 = eng.pack(eng.getX() + thing, level, eng.getZ() - thing);
      eng.GeometryAdd(point1, point2, point3, 0, color3);
      return;
   }
   public static void SCube(double[] loc, double length, double width, double height, double turnx, double turny, double turnz, int[] color3, RealEngine eng) {
      double xleft = -(width / 2);
      double yup = height;
      double zback = -(length / 2);
      double xright = (width / 2);
      double ydown = 0;
      double zfront = (length / 2);
      double[] point1 = new double[3];
      double[] point2 = new double[3];
      double[] point3 = new double[3];
      int skybox = 0;
      point1 = rotatePoint(eng.pack(xleft, ydown, zback), turnx, turny, turnz, loc);
      point2 = rotatePoint(eng.pack(xright, ydown, zback), turnx, turny, turnz, loc);
      point3 = rotatePoint(eng.pack(xleft, yup, zback), turnx, turny, turnz, loc);
      eng.GeometryAdd(point1, point2, point3, skybox, color3);
      
      point1 = rotatePoint(eng.pack(xleft, yup, zback), turnx, turny, turnz, loc);
      point2 = rotatePoint(eng.pack(xright, yup, zback), turnx, turny, turnz, loc);
      point3 = rotatePoint(eng.pack(xright, ydown, zback), turnx, turny, turnz, loc);
      eng.GeometryAdd(point1, point2, point3, skybox, color3);
      
      point1 = rotatePoint(eng.pack(xleft, ydown, zfront), turnx, turny, turnz, loc);
      point2 = rotatePoint(eng.pack(xright, ydown, zfront), turnx, turny, turnz, loc);
      point3 = rotatePoint(eng.pack(xleft, yup, zfront), turnx, turny, turnz, loc);
      eng.GeometryAdd(point1, point2, point3, skybox, color3);
      
      point1 = rotatePoint(eng.pack(xleft, yup, zfront), turnx, turny, turnz, loc);
      point2 = rotatePoint(eng.pack(xright, yup, zfront), turnx, turny, turnz, loc);
      point3 = rotatePoint(eng.pack(xright, ydown, zfront), turnx, turny, turnz, loc);
      eng.GeometryAdd(point1, point2, point3, skybox, color3);
      
      point1 = rotatePoint(eng.pack(xleft, ydown, zback), turnx, turny, turnz, loc);
      point2 = rotatePoint(eng.pack(xleft, ydown, zfront), turnx, turny, turnz, loc);
      point3 = rotatePoint(eng.pack(xleft, yup, zfront), turnx, turny, turnz, loc);
      eng.GeometryAdd(point1, point2, point3, skybox, color3);
      
      point1 = rotatePoint(eng.pack(xleft, yup, zfront), turnx, turny, turnz, loc);
      point2 = rotatePoint(eng.pack(xleft, yup, zback), turnx, turny, turnz, loc);
      point3 = rotatePoint(eng.pack(xleft, ydown, zback), turnx, turny, turnz, loc);
      eng.GeometryAdd(point1, point2, point3, skybox, color3);
      
      point1 = rotatePoint(eng.pack(xright, ydown, zback), turnx, turny, turnz, loc);
      point2 = rotatePoint(eng.pack(xright, ydown, zfront), turnx, turny, turnz, loc);
      point3 = rotatePoint(eng.pack(xright, yup, zfront), turnx, turny, turnz, loc);
      eng.GeometryAdd(point1, point2, point3, skybox, color3);
      
      point1 = rotatePoint(eng.pack(xright, yup, zfront), turnx, turny, turnz, loc);
      point2 = rotatePoint(eng.pack(xright, yup, zback), turnx, turny, turnz, loc);
      point3 = rotatePoint(eng.pack(xright, ydown, zback), turnx, turny, turnz, loc);
      eng.GeometryAdd(point1, point2, point3, skybox, color3);
      
      point1 = rotatePoint(eng.pack(xleft, yup, zback), turnx, turny, turnz, loc);
      point2 = rotatePoint(eng.pack(xleft, yup, zfront), turnx, turny, turnz, loc);
      point3 = rotatePoint(eng.pack(xright, yup, zback), turnx, turny, turnz, loc);
      eng.GeometryAdd(point1, point2, point3, skybox, color3);
      
      point1 = rotatePoint(eng.pack(xleft, yup, zfront), turnx, turny, turnz, loc);
      point2 = rotatePoint(eng.pack(xright, yup, zfront), turnx, turny, turnz, loc);
      point3 = rotatePoint(eng.pack(xright, yup, zback), turnx, turny, turnz, loc);
      eng.GeometryAdd(point1, point2, point3, skybox, color3);
      
      point1 = rotatePoint(eng.pack(xleft, ydown, zback), turnx, turny, turnz, loc);
      point2 = rotatePoint(eng.pack(xleft, ydown, zfront), turnx, turny, turnz, loc);
      point3 = rotatePoint(eng.pack(xright, ydown, zback), turnx, turny, turnz, loc);
      eng.GeometryAdd(point1, point2, point3, skybox, color3);
      
      point1 = rotatePoint(eng.pack(xleft, ydown, zfront), turnx, turny, turnz, loc);
      point2 = rotatePoint(eng.pack(xright, ydown, zfront), turnx, turny, turnz, loc);
      point3 = rotatePoint(eng.pack(xright, ydown, zback), turnx, turny, turnz, loc);
      eng.GeometryAdd(point1, point2, point3, skybox, color3);
   }
   
   public static double[] rotatePoint(double[] point, double a_y, double a_x, double a_z, double[] loc) {
      double x2 = point[0];
      double y2 = point[1];
      double z2 = point[2];
      double ax_c = Math.cos(Math.toRadians(a_x));
      double ax_s = Math.sin(Math.toRadians(a_x));
      double ay_c = Math.cos(Math.toRadians(a_y));
      double ay_s = Math.sin(Math.toRadians(a_y));
      double az_c = Math.cos(Math.toRadians(a_z));
      double az_s = Math.sin(Math.toRadians(a_z));
      double[] sts = new double[3];
      sts[0] = ((ay_c * ((az_s * y2) + (az_c * x2))) - (ay_s * z2));
      sts[1] = ((ax_s * ((ay_c * z2) + (ay_s * ((az_s * y2) + (az_c * x2))))) + (ax_c * ((az_c * y2) - (az_s * x2)))) * -1;
      sts[2] = ((ax_c * ((ay_c * z2) + (ay_s * ((az_s * y2) + (az_c * x2))))) - (ax_s * ((az_c * y2) - (az_s * x2))));
      
      sts[0] += loc[0];
      sts[1] += loc[1];
      sts[2] += loc[2];
      return sts;
   }*/
   public static void main(String[] args) {
      main.main(args);
   }
}