import javax.swing.*;
import java.awt.*;
public class example extends JPanel {
   static double[][] points = new double[2500][3];
   static double[][] normal = new double[2500][3];
   static int p1 = 0;
   static int p2 = 0;
   static int p3 = 0;
   
   static int[] cement = {255, 255, 255};
   public example(){}
   public static void compile(RealEngine eng, double X, double Y, double Z) {
      points[0] = eng.pack(-1.0000 + X,1.0000 + Y,-1.0000 + Z); 
      normal[0] = eng.pack(-0.5773,0.5773,-0.5773); 
      points[1] = eng.pack(-1.0000 + X,-1.0000 + Y,1.0000 + Z); 
      normal[1] = eng.pack(-0.5773,-0.5773,0.5773); 
      points[2] = eng.pack(-1.0000 + X,-1.0000 + Y,-1.0000 + Z); 
      normal[2] = eng.pack(-0.5773,-0.5773,-0.5773); 
      points[3] = eng.pack(-1.0000 + X,1.0000 + Y,1.0000 + Z); 
      normal[3] = eng.pack(-0.5773,0.5773,0.5773); 
      points[4] = eng.pack(1.0000 + X,-1.0000 + Y,1.0000 + Z); 
      normal[4] = eng.pack(0.5773,-0.5773,0.5773); 
      points[5] = eng.pack(1.0000 + X,1.0000 + Y,1.0000 + Z); 
      normal[5] = eng.pack(0.5773,0.5773,0.5773); 
      points[6] = eng.pack(1.0000 + X,-1.0000 + Y,-1.0000 + Z); 
      normal[6] = eng.pack(0.5773,-0.5773,-0.5773); 
      points[7] = eng.pack(1.0000 + X,1.0000 + Y,-1.0000 + Z); 
      normal[7] = eng.pack(0.5773,0.5773,-0.5773); 
   
      p1 = 0;
      p2 = 1;
      p3 = 2;
      eng.GeometryAdd(points[p1], points[p2], points[p3], normal[p1], normal[p2], normal[p3], 0, cement);
      p1 = 3;
      p2 = 4;
      p3 = 1;
      eng.GeometryAdd(points[p1], points[p2], points[p3], normal[p1], normal[p2], normal[p3], 0, cement);
      p1 = 5;
      p2 = 6;
      p3 = 4;
      eng.GeometryAdd(points[p1], points[p2], points[p3], normal[p1], normal[p2], normal[p3], 0, cement);
      p1 = 7;
      p2 = 2;
      p3 = 6;
      eng.GeometryAdd(points[p1], points[p2], points[p3], normal[p1], normal[p2], normal[p3], 0, cement);
      p1 = 4;
      p2 = 2;
      p3 = 1;
      eng.GeometryAdd(points[p1], points[p2], points[p3], normal[p1], normal[p2], normal[p3], 0, cement);
      p1 = 3;
      p2 = 7;
      p3 = 5;
      eng.GeometryAdd(points[p1], points[p2], points[p3], normal[p1], normal[p2], normal[p3], 0, cement);
      p1 = 0;
      p2 = 3;
      p3 = 1;
      eng.GeometryAdd(points[p1], points[p2], points[p3], normal[p1], normal[p2], normal[p3], 0, cement);
      p1 = 3;
      p2 = 5;
      p3 = 4;
      eng.GeometryAdd(points[p1], points[p2], points[p3], normal[p1], normal[p2], normal[p3], 0, cement);
      p1 = 5;
      p2 = 7;
      p3 = 6;
      eng.GeometryAdd(points[p1], points[p2], points[p3], normal[p1], normal[p2], normal[p3], 0, cement);
      p1 = 7;
      p2 = 0;
      p3 = 2;
      eng.GeometryAdd(points[p1], points[p2], points[p3], normal[p1], normal[p2], normal[p3], 0, cement);
      p1 = 4;
      p2 = 6;
      p3 = 2;
      eng.GeometryAdd(points[p1], points[p2], points[p3], normal[p1], normal[p2], normal[p3], 0, cement);
      p1 = 3;
      p2 = 0;
      p3 = 7;
      eng.GeometryAdd(points[p1], points[p2], points[p3], normal[p1], normal[p2], normal[p3], 0, cement);
   
   }
   
}