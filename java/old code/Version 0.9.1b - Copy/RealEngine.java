import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.ArrayList;
import java.io.*;
import java.net.URL;
import javax.sound.sampled.*;
import javax.swing.*;
public class RealEngine {
   static boolean multithread;
   static boolean GPU;
   static boolean printGeo;
   static boolean objectshading;
   static boolean objectsmoothlighting;
   static boolean skybox;
   static boolean waterReflections;
   static double[] reflectioncamera = new double[6];
   static double waterheight;
   static int cores = Runtime.getRuntime().availableProcessors();
   static int polynumber = 0;
   static int sb_polynumber = 0;
   static int water_polynumber = 0;
   static boolean filled = false;
   static boolean nc = false;
   static boolean cheaplighting;
   static double x;
   static double y;
   static double z;
   static double sb_x_offset;//one time set
   static double sb_y_offset;
   static double sb_z_offset;
   static double sb_x;
   static double sb_y;
   static double sb_z;
   static double sb_move_offset;//sb_x + x times by this
   static double ax;
   static double ay;
   static double az;
   static double axc = 1.0;
   static double ayc = 1.0;
   static double azc = 1.0;
   static double axs = 0.0;
   static double ays = 0.0;
   static double azs = 0.0;
   static double viewfactor;
   static double sb_viewfactor;
   private double weaponfov;
   private int maxdist;
   static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
   static int length;// = (int) screenSize.getHeight();
   static int width;// = (int) screenSize.getWidth();
   private int wireFrameLength = 00;
   private int wireFramewidth = 00;
   static double[][]     bufferz           = new double[(int) screenSize.getWidth()][(int) screenSize.getHeight()];
   static int[][][]      buffercol         = new int   [(int) screenSize.getWidth()][(int) screenSize.getHeight()][3];
   static double[][][]   buffer3d          = new double[(int) screenSize.getWidth()][(int) screenSize.getHeight()][3];
   static int[][][]      shadowmap         = new int   [(int) screenSize.getWidth()][(int) screenSize.getHeight()][3];
   static double[][]     bloombuffer       = new double[(int) screenSize.getWidth()][(int) screenSize.getHeight()];
   static double[][]     reflectionbuffer  = new double[(int) screenSize.getWidth()][(int) screenSize.getHeight()];
   static double[][][]   reflectioncolor   = new double[(int) screenSize.getWidth()][(int) screenSize.getHeight()][3];
   static double[][][]   reflection3d      = new double[(int) screenSize.getWidth()][(int) screenSize.getHeight()][3];
   private int[][]       lights            = new int[20][3];
   private int[]         lightins          = new int[20]; 
   static int polymax = 1000000;
   static double[][] pointsGPU = new double[polymax][3];
   static double[][] NDotLGPU  = new double[polymax][3];
   static double[][] water_pointsGPU = new double[polymax][3];
   static double[][] water_NDotLGPU  = new double[polymax][3];
   static int[][] colorsGPU   = new int[polymax / 3][3];
   static int[][] sb_colors   = new int[polymax / 3][3];
   static int[] watercolor = new int[3];
   static int watertransparency;
   static double brightness;
   static int maxlights;
   private int dev;
   private int adjust; 
   private int threads;
   static int detail;
   static boolean shadows;
   public RealEngine() {}
   public void setFov         (double fov) { this.viewfactor = fov;   }
   public void setMaxDist     (int dist)   { this.maxdist = dist;     }
   public void setBrightness  (double b)   { this.brightness = b;     }
   public void setMaxLights   (int lights) { this.maxlights = lights; }
   public void dev            (int num)    { this.dev = num;          }
   public void detail         (int num)    { this.detail = num;       }
   public void adjust         (int num)    { this.adjust = num;       }
   public void setX           (double num) { this.x = num;            }
   public void setY           (double num) { this.y = num;            }
   public void setZ           (double num) { this.z = num;            }
   public void changeX        (double num) { this.x += num;           }
   public void changeY        (double num) { this.y += num;           }
   public void changeZ        (double num) { this.z += num;           }
   public void changeAx       (double num) { this.ax += num;          }
   public void changeAy       (double num) { this.ay += num;          }
   public void changeAz       (double num) { this.az += num;          }
   public void setAx          (double num) { this.ax = num;           }
   public void setAy          (double num) { this.ay = num;           }
   public void setAz          (double num) { this.az = num;           }
   public double getX   () { 
      return this.x;  }
   public double getY   () { 
      return this.y;  }
   public double getZ   () { 
      return this.z;  }
   public double getAx  () { 
      return this.ax; }
   public double getAy  () { 
      return this.ay; }
   public double getAz  () { 
      return this.az; }
   public double[] getLoc() { 
      return pack(this.x, this.y, this.z); }
   public double getBuffer3d(int i, int j, int k)     { 
      return this.buffer3d[i][j][k]; }
   public double getBufferz(int i, int j)      { 
      return this.bufferz[i][j];  }
   public int    getBuffercol(int i, int j, int k)    { 
      return this.buffercol[i][j][k];}
   public double getShadowmap(int i, int j, int k)    { 
      return this.shadowmap[i][j][k];}
   public int getLength          ()    { 
      return this.length;      }
   public int getWidth           ()    { 
      return this.width;       }
   public double getBrightness   ()    { 
      return this.brightness;  }
   public static void GeometryAdd(double[] p1, double[] p2, double[] p3, double[] normal_p1, double[] normal_p2, double[] normal_p3, int hud, int[] color3) {
      RealEngine eng = mainDraw.Main;
      if (filled == false) {
         eng.pointsGPU[polynumber][0] = p1[0];
         eng.pointsGPU[polynumber][1] = p1[1];
         eng.pointsGPU[polynumber][2] = p1[2];
         eng.pointsGPU[polynumber + 1][0] = p2[0];
         eng.pointsGPU[polynumber + 1][1] = p2[1];
         eng.pointsGPU[polynumber + 1][2] = p2[2];
         eng.pointsGPU[polynumber + 2][0] = p3[0];
         eng.pointsGPU[polynumber + 2][1] = p3[1];
         eng.pointsGPU[polynumber + 2][2] = p3[2];
         eng.colorsGPU[polynumber / 3][0] = color3[0];
         eng.colorsGPU[polynumber / 3][1] = color3[1];
         eng.colorsGPU[polynumber / 3][2] = color3[2];
         eng.NDotLGPU[polynumber] = normal_p1;
         eng.NDotLGPU[polynumber + 1] = normal_p2;
         eng.NDotLGPU[polynumber + 2] = normal_p3;
         polynumber += 3;
      }
   }
   public static void water_GeometryAdd(double[] p1, double[] p2, double[] p3, int hud, int[] color3) {
      RealEngine eng = mainDraw.Main;
      if (filled == false) {
         eng.water_pointsGPU[water_polynumber][0] = p1[0];
         eng.water_pointsGPU[water_polynumber][1] = waterheight;
         eng.water_pointsGPU[water_polynumber][2] = p1[1];
         eng.water_pointsGPU[water_polynumber + 1][0] = p2[0];
         eng.water_pointsGPU[water_polynumber + 1][1] = waterheight;
         eng.water_pointsGPU[water_polynumber + 1][1] = p2[1];
         eng.water_pointsGPU[water_polynumber + 2][0] = p3[0];
         eng.water_pointsGPU[water_polynumber + 2][1] = waterheight;
         eng.water_pointsGPU[water_polynumber + 2][1] = p3[1];
         water_polynumber += 3;
      }
   }
   public static void render(RealEngine eng, int start, int end) {
      for (int i = start; i <= end; i += 3) {
         eng.DrawTriangleg(eng.pointsGPU[i], eng.pointsGPU[i + 1], eng.pointsGPU[i + 2], eng.NDotLGPU[i], eng.NDotLGPU[i + 1], eng.NDotLGPU[i + 2], 0, colorsGPU[i / 3]);
      
      }
   }
   public static void render_water(RealEngine eng, int start, int end) {//called BEFORE water reflection
      for (int i = start; i < end; i ++){
         eng.DrawTriangleg(water_pointsGPU[i], water_pointsGPU[i + 1], water_pointsGPU[i + 2], water_NDotLGPU[i], water_NDotLGPU[i + 1], water_NDotLGPU[i + 2], 1, watercolor);
      }
   }
   public static void render_waterReflection(RealEngine eng, int start, int end) {//rerenders the entire SCENE, might half your framerate if enabled
      for (int i = start; i < end; i ++){
         eng.DrawTriangleg(pointsGPU[i], pointsGPU[i + 1], pointsGPU[i + 2], NDotLGPU[i], NDotLGPU[i + 1], NDotLGPU[i + 2], 2, colorsGPU[i/3]);
      }
   }
   public void setAngles(double[] angles) {
      this.ax = angles[0];
      this.ay = angles[1];
      this.az = angles[2];
   }
   public void setLoc(double[] Loc) {
      this.x = Loc[0];
      this.y = Loc[1];
      this.z = Loc[2];
   }
   public void DrawLine(double p1x, double p1y, double p1z, double p2x, double p2y, double p2z, int hud, Graphics g) {
      int[] color3 = packint(255, 0, 0);
      Color thing = new Color (color3[0], color3[1], color3[2]);
      g.setColor(thing);
      double[] set1 = new double[2];
      double[] set2 = new double[2];
      double[] point1 = new double[3];
      double[] point2 = new double[3];
      point1[0] = p1x;
      point1[1] = p1y;
      point1[2] = p1z;
      point2[0] = p2x;
      point2[1] = p2y;
      point2[2] = p2z;
      set1 = vertex(point1, hud);
      set2 = vertex(point2, hud);
      double fov;
      int repeat = 10;
      fov = this.viewfactor;
      double draw_x2 = 0.0;
      double draw_y2 = 0.0;
      double cxb = (set2[0] - set1[0]) / repeat;
      double cyb = (set2[1] - set1[1]) / repeat;
      double czb = (set2[2] - set1[2]) / repeat;
      double draw_x1 = (((set1[0] * fov) / set1[2])) + (this.wireFramewidth / 2);
      double draw_y1 = (((set1[1] * fov) / set1[2])) + (this.wireFrameLength / 2);
      double sx1 = set1[0];
      double sy1 = set1[1];
      double sz1 = set1[2];
      int rep = 1;
      for (int j = 0; j < repeat; j += 1) {
         sx1 += cxb;
         sy1 += cyb;
         sz1 += czb;
         if (rep == 0) {
            set1[0] += cxb;
            set1[1] += cyb;
            set1[2] += czb;
         }
         rep = 0;
         if (((0 < set1[2]) && (((Math.abs((set1[1] * fov)/ set1[2])) < wireFramewidth) && (((Math.abs((set1[2] * fov)/ set1[2])) < wireFrameLength)))) == true) {
            draw_x2 = (((set1[0] * fov) / set1[2]));
            draw_y2 = (((set1[1] * fov) / set1[2]));
            draw_x1 = (((sx1 * fov) / sz1));
            draw_y1 = (((sy1 * fov) / sz1));
            if ((draw_x1 + (wireFrameLength / 2)) > -wireFramewidth && draw_x1 < wireFrameLength) {
               if ((draw_y1 + (wireFramewidth / 2)) > -wireFramewidth && draw_y1 < wireFrameLength) {
                  if ((draw_x2 + (wireFrameLength / 2)) > -wireFramewidth && draw_x2 < wireFrameLength) {
                     if ((draw_y2 + (wireFramewidth / 2)) > -wireFramewidth && draw_y2 < wireFrameLength) {
                        if (Math.abs(set1[0] / fov) < (maxdist * 0.01) && Math.abs(set1[2] / fov) < (maxdist * 0.01) && Math.abs(set1[1] / fov) < (maxdist * 0.01)) {
                           g.drawLine((int) draw_x1 + (wireFrameLength / 2) - 1, (int) draw_y1 + (wireFramewidth / 2) - 1, (int) draw_x2 + (wireFrameLength / 2) - 1, (int) draw_y2 + (wireFramewidth / 2) - 1);
                           g.drawLine((int) draw_x1 + (wireFrameLength / 2), (int) draw_y1 + (wireFramewidth / 2), (int) draw_x2 + (wireFrameLength / 2), (int) draw_y2 + (wireFramewidth / 2));
                           g.drawLine((int) draw_x1 + (wireFrameLength / 2) + 1, (int) draw_y1 + (wireFramewidth / 2) + 1, (int) draw_x2 + (wireFrameLength / 2) + 1, (int) draw_y2 + (wireFramewidth / 2) + 1);
                           g.drawLine((int) draw_x1 + (wireFrameLength/ 2) - 2, (int) draw_y1 + (wireFramewidth / 2) - 2, (int) draw_x2 + (wireFrameLength / 2) - 2, (int) draw_y2 + (wireFramewidth / 2) - 2);
                           g.drawLine((int) draw_x1 + (wireFrameLength / 2) + 2, (int) draw_y1 + (wireFramewidth / 2) + 2, (int) draw_x2 + (wireFrameLength / 2) + 2, (int) draw_y2 + (wireFramewidth / 2) + 2);
                        }
                     }
                  }
               }
            }
         }
      }
      return;
   }
   public void setreflectioncamera() {
      reflectioncamera[0] = x;
      reflectioncamera[1] = Math.abs(waterheight - y);
      reflectioncamera[2] = z;
      reflectioncamera[3] = -ax;
      reflectioncamera[4] = ay;
      reflectioncamera[5] = Math.toRadians(-180);
      return;
   }
   public void clear(int r, int g, int b) {
      for (int i = 0; i < length; i += 1) {
         for (int j = 0; j < width; j += 1) {
            bufferz[i][j] = 1000000.0;
            reflectionbuffer[i][j] = 1000000.0;
            reflectioncolor[i][j] = pack(0,0,0);
            reflection3d[i][j] = pack(0,0,0);
            buffer3d[i][j][0] = 0;
            buffer3d[i][j][1] = 0;
            buffer3d[i][j][2] = 0;
            bloombuffer[i][j] = 0;
            buffercol[i][j][0] = -1;
            buffercol[i][j][1] = -1;
            buffercol[i][j][2] = -1;
         }
      }
      return;
   }
   public boolean drawtrianglefilter(double[] p1, double[] p2, double[] p3, double[] camera) { 
      if (p1[1] < camera[1]) {
         return false;
      } 
      if (p2[1] < camera[1]) {
         return false;
      }
      if (p3[1] < camera[1]) {
         return false;
      }
      return true;
   }
   public void PutPixel(int pointx, int pointy, double[] depth, int hud, int[] color3) {
      double d = Math.abs(depth[0]) + Math.abs(depth[1]) + Math.abs(depth[2]);
      if (hud == 0) {
         if (bufferz[pointx][pointy] < d) {
            return;
         }
         buffer3d[pointx][pointy][0] = depth[0];
         buffer3d[pointx][pointy][1] = depth[1];
         buffer3d[pointx][pointy][2] = depth[2];
         bufferz[pointx][pointy] = d;
         buffercol[pointx][pointy][0] = color3[0];
         buffercol[pointx][pointy][1] = color3[1];
         buffercol[pointx][pointy][2] = color3[2];
      } else if (hud == 1) {
         if (bufferz[pointx][pointy] < d) {
            return;
         }
         buffer3d[pointx][pointy][0] = depth[0];
         buffer3d[pointx][pointy][1] = depth[1];
         buffer3d[pointx][pointy][2] = depth[2];
         bufferz[pointx][pointy] = d;
         buffercol[pointx][pointy][0] += color3[0];
         buffercol[pointx][pointy][1] += color3[1];
         buffercol[pointx][pointy][2] += color3[2];
      } else {
         reflectionbuffer[pointx][pointy] = d;
         reflectioncolor[pointx][pointy][0] = color3[0];
         reflectioncolor[pointx][pointy][1] = color3[1];
         reflectioncolor[pointx][pointy][2] = color3[2];
         reflection3d[pointx][pointy][0] = depth[0];
         reflection3d[pointx][pointy][1] = depth[1];
         reflection3d[pointx][pointy][2] = depth[2];
      }
      return;
   }
   public void bloom() {
      for (int i = 0; i < this.length; i += 1) {
         for (int j = 0; j < this.width; j += 1) {
            if (this.buffercol[i][j][0] > 255 && this.buffercol[i][j][1] > 255 && this.buffercol[i][j][2] > 255) {
               this.bloombuffer[i][j] = ((this.buffercol[i][j][0] - 255) + (this.buffercol[i][j][1] - 255) + (this.buffercol[i][j][2] - 255)) / (brightness * brightness);
            }
         }
      }
      for (int i = 0; i < this.length; i += 1) {
         for (int j = 0; j < this.width; j += 1) {
            if (this.bloombuffer[i][j] >= 1) {
               try{
                  for (int n = i - (int) this.bloombuffer[i][j]; n < i + (int) this.bloombuffer[i][j]; n += 1) {
                     for (int E = j - (int) this.bloombuffer[i][j]; E < j + (int) this.bloombuffer[i][j]; E += 1) { //EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE
                        double distance = Math.abs(Math.sqrt((this.bloombuffer[i][j] * i) + (this.bloombuffer[i][j] * E)));
                        this.buffercol[n][E][0] += distance;
                        this.buffercol[n][E][1] += distance;
                        this.buffercol[n][E][2] += distance;
                     }
                  }
               } catch (ArrayIndexOutOfBoundsException E) {}
            }
         }
      }
      return;
   }
   public void present(Graphics g) {
      if (adjust == 1) {
         int bright = 0;
         for (int i = 0; i < this.length; i += 1) {
            for (int j = 0; j < this.width; j += 1) {
               for (int k = 0; k < 3 - 1; k += 1) {
                  bright += this.buffercol[i][j][k];
               }
            }
         }
         System.out.println(bright);
         System.out.println(brightness);
         if (bright > 43480595 * (length / width)) {
            brightness -= 0.05;
         } else if (bright < 43480595 * (length / width)) {
            brightness += 0.05;
         }
      }
      for (int i = 0; i < this.length; i += 1) {
         for (int j = 0; j < this.width; j += 1) {
            this.buffercol[i][j] = packcol(this.buffercol[i][j][0], this.buffercol[i][j][1], this.buffercol[i][j][2]);
            Color thisss = new Color (this.buffercol[i][j][0], this.buffercol[i][j][1], this.buffercol[i][j][2]);
            g.setColor(thisss);
            g.drawLine(i, j, i, j);
         }
      }
      return;
   }
   public void showImage(Graphics g) {
      for (int i = 0; i < this.length; i += 1) {
         for (int j = 0; j < this.width; j += 1) {
            if (this.buffercol[i][j][0] != -1 && this.buffercol[i][j][1] != -1 && this.buffercol[i][j][2] != -1) {
               this.buffercol[i][j] = packcol(this.buffercol[i][j][0], this.buffercol[i][j][1], this.buffercol[i][j][2]);
               Color thisss = new Color (this.buffercol[i][j][0], this.buffercol[i][j][1], this.buffercol[i][j][2]);
               g.setColor(thisss);
               g.drawLine(i, j, i, j);
            }
         }
      }
      return;
   }
   public static double clamp(double value, double min, double max) {
      return Math.max(min, Math.min(value, max));}
   public static double interpolate(double min, double max, double gradient) {
      return min + (max - min) * clamp(gradient, 0, 1);}
   public void drawPoint(double[] point, double[] depth, int hud, int[] color3) {   
      point[0] += (length / 2);
      point[1] += (width / 2);
      if (point[0] >= 0 && point[1] >= 0 && point[0] < length && point[1] < width) {
         this.PutPixel((int) point[0], (int) point[1], pack(depth[0], depth[1], depth[2]), hud, color3);
      }
      return;
   }
   public double[] findmiddle(double[] num1, double num2[]) {
      double[] middle = new double[3];
      middle[0] = (num1[0] + num2[0]) / 2;
      middle[1] = (num1[1] + num2[1]) / 2;
      middle[2] = (num1[2] + num2[2]) / 2;
      return middle;
   }
   public void DrawTriangleg(double[] p1, double[] p2, double[] p3,  double[] normal_p1, double[] normal_p2, double[] normal_p3, int hud, int[] color3) {
      /*if (hud == 1) {
         //hud 1 is for the marking of what pixel holds water, or in lamens terms, water reflection switch thing
      }
      if (hud == 2) {
         if (!drawtrianglefilter(p1, p2, p3, reflectioncamera)) {
            return;
         }
      }*/
      double[] pt1 = new double[2];
      double[] pt2 = new double[2];
      double[] pt3 = new double[2];
      pt1 = transformpoint(p1, hud);
      pt2 = transformpoint(p2, hud);
      pt3 = transformpoint(p3, hud);
      if (pt1[0] == pt1[1] && pt1[0] == length + width && pt1[1] == length + width) {
         if (pt1[2] == pt1[3] && pt1[3] == pt1[4] && pt1[0] == pt1[2]) {
            return;
         }
      }
      if (pt2[0] == pt2[1] && pt2[0] == length + width && pt2[1] == length + width) {
         if (pt2[2] == pt2[3] && pt2[3] == pt2[4] && pt2[0] == pt2[2]) {
            return;
         }
      }
      if (pt3[0] == pt3[1] && pt3[0] == length + width && pt3[1] == length + width) {
         if (pt3[2] == pt3[3] && pt3[3] == pt3[4] && pt3[0] == pt3[2]) {
            return;
         }
      }
      Triangle(pt1, pt2, pt3, normal_p1, normal_p2, normal_p3, hud, color3);
      return; 
   }
   public void Triangle(double[] p1, double[] p2, double[] p3, double[] normal_p1, double[] normal_p2, double[] normal_p3, int hud, int[] color3) {
      if (p1[1] > p2[1]) {
         double[] nrmt = normal_p2;
         normal_p2 = normal_p1;
         normal_p1 = nrmt;
         double[] temp = p2;
         p2 = p1;
         p1 = temp;
      }
      if (p2[1] > p3[1]) {
         double[] nrmt = normal_p2;
         normal_p2 = normal_p3;
         normal_p3 = nrmt;
         double[] temp = p2;
         p2 = p3;
         p3 = temp;
      }
      if (p1[1] > p2[1]) {
         double[] nrmt = normal_p2;
         normal_p2 = normal_p1;
         normal_p1 = nrmt;
         double[] temp = p2;
         p2 = p1;
         p1 = temp;
      }
      double[] p_1 = pack(p1[2], p1[3], p1[4]);
      double[] p_2 = pack(p2[2], p2[3], p2[4]);
      double[] p_3 = pack(p3[2], p3[3], p3[4]);
      
      double nl1 = 0;
      double nl2 = 0;
      double nl3 = 0;
      double[][] lights = mainDraw.lights();
      for (int i = 0; i < maxlights; i++){
         double[] lightpos = lights[i];
         nl1 += computeNDotL(p_1, normal_p1, lightpos);
         nl2 += computeNDotL(p_2, normal_p2, lightpos);
         nl3 += computeNDotL(p_3, normal_p3, lightpos);
      }
      float dP1P2, dP1P3;
      if (p2[1] - p1[1] > 0) { 
         dP1P2 = (float)(p2[0] - p1[0]) / (float)(p2[1] - p1[1]);
      } 
      else {
         dP1P2 = 0;
      }
      if (p3[1] - p1[1] > 0) {
         dP1P3 = (float)(p3[0] - p1[0]) / (float)(p3[1] - p1[1]);
      } 
      else {
         dP1P3 = 0;
      }
      if (dP1P2 > dP1P3)
      {
         for (int ythis = (int)p1[1] >> 0; ythis <= (int)p3[1] >> 0; ythis++)
         {
            if (ythis < p2[1])
            {
               this.processScanLine(ythis, p1, p3, p1, p2, nl1, nl3, nl1, nl2, hud, color3);
            }
            else
            {
               this.processScanLine(ythis, p1, p3, p2, p3, nl1, nl3, nl2, nl3, hud, color3);
            }
         }
      }      
      else
      {
         for (int ythis1 = (int)p1[1] >> 0; ythis1 <= (int)p3[1] >> 0; ythis1++)
         {
            if (ythis1 < p2[1])
            {
               this.processScanLine(ythis1, p1, p2, p1, p3, nl1, nl2, nl1, nl3, hud, color3);
            }
            else
            {
               this.processScanLine(ythis1, p2, p3, p1, p3, nl2, nl3, nl1, nl3, hud, color3);
            }
         }
      }
      return;
   }

   public void processScanLine(int data, double[] va, double[] vb, double[] vc, double[] vd, double nl1, double nl2, double nl3, double nl4, int hud, int[] color3) {
      double[] pa = va;
      double[] pb = vb;
      double[] pc = vc;
      double[] pd = vd;
      double gradient1 = pa[1] != pb[1] ? (data - pa[1]) / (pb[1] - pa[1]) : 1;
      double gradient2 = pc[1] != pd[1] ? (data - pc[1]) / (pd[1] - pc[1]) : 1;
      double stx = (int) interpolate(pa[0], pb[0], gradient1) >> 0;
      double enx = (int) interpolate(pc[0], pd[0], gradient2) >> 0;
      double xt1 = interpolate(pa[2], pb[2], gradient1);
      double xt2 = interpolate(pc[2], pd[2], gradient2);
      double yt1 = interpolate(pa[3], pb[3], gradient1);
      double yt2 = interpolate(pc[3], pd[3], gradient2);
      double zt1 = interpolate(pa[4], pb[4], gradient1);
      double zt2 = interpolate(pc[4], pd[4], gradient2);
      double snl = interpolate(nl1, nl2, gradient1);
      double enl = interpolate(nl3, nl4, gradient2);
      double gradient;
      double thisx;
      double thisy;
      double thisz;
      for (int i = (int)stx; i < enx; i ++) {
         gradient = (i - stx) / (enx - stx);
         thisx = interpolate(xt1, xt2, gradient);
         thisy = interpolate(yt1, yt2, gradient);
         thisz = interpolate(zt1, zt2, gradient);
         double ndotl = interpolate(snl, enl, gradient);
         int[] col = packint((int) ((double) color3[0] * ndotl), (int) ((double) color3[1] * ndotl), (int) ((double)color3[2] * ndotl));
         this.drawPoint(pack(i, data, 0), pack(thisx, thisy, thisz), hud, col); 
      }
      return;
   }
   public static double[] pack(double x1, double y1, double z1) {
      double[] thh = new double[3];
      thh[0] = x1;
      thh[1] = y1;
      thh[2] = z1;
      return thh;
   }
   public static int[] packint(int x1, int y1, int z1) {
      int[] thh = new int[3];
      thh[0] = x1;
      thh[1] = y1;
      thh[2] = z1;
      return thh;
   }
   public static int[] packint4(int a, int b, int c, int d) {
      int[] thh = new int[4];
      thh[0] = a;
      thh[1] = b;
      thh[2] = c;
      thh[3] = d;
      return thh;
   }
   public int[] packcol(int x1, int y1, int z1) {
      int[] thh = new int[3];
      int r;
      int g;
      int b;
      if (x1 >= 255) {
         r = 255;
      } else if (x1 <= 0) {
         r = 0;
      } else {
         r = x1;
      }
      if (y1 >= 255) {
         g = 255;
      } else if (y1 <= 0) {
         g = 0;
      } else {
         g = y1;
      }
      if (z1 >= 255) {
         b = 255;
      } else if (z1 <= 0) {
         b = 0;
      } else {
         b = z1;
      }
      thh[0] = r;
      thh[1] = g;
      thh[2] = b;
      return thh;
   }
   public boolean checkcol(int x1) {
      int r;
      if (x1 > 255) {
         return false;
      } else if (x1 > 0) {
         return false;
      } else {
         return true;
      }
   }
   public static int norm_color(int x1) {
      int r;
      if (x1 > 255) {
         r = 255;
      } else if (x1 > 0) {
         r = 0;
      } else {
         return x1;
      }
      return r;
   }
   public static int[] pack2(int coord1, int coord2) {
      int[] thh = new int[2];
      thh[0] = coord1;
      thh[1] = coord2;
      return thh;
   }
   public static double[] packtwo(double coord1, double coord2) {
      double[] thh = new double[2];
      thh[0] = coord1;
      thh[1] = coord2;
      return thh;
   }
   public static int[][] pack3(int[] coord1, int[] coord2, int[] coord3) {
      int[][] thh = new int[3][3];
      thh[0][0] = coord1[0];
      thh[0][1] = coord1[1];
      thh[0][2] = coord1[2];
      thh[1][0] = coord2[0];
      thh[1][1] = coord2[1];
      thh[1][2] = coord2[2];
      thh[2][0] = coord3[0];
      thh[2][1] = coord3[1];
      thh[2][2] = coord3[2];
      return thh;
   }
   public static double[] pack3d(double coord1, double coord2, double coord3) {
      double[] thh = new double[3];
      thh[0] = coord1;
      thh[1] = coord2;
      thh[2] = coord3;
      return thh;
   }
   public double[] transformpoint(double[] coords, int hud) {
      double[] org = coords;
      double[] start = vertex(coords, hud);
      double fov = viewfactor;
      if (hud == 1) {
         fov = sb_viewfactor;
      }
      double[] twocoords = new double[5];
      double test1 = 0;
      double test2 = 0;
      if (((0 < start[2]) && (((Math.abs((start[1] * fov)/ start[2])) < width) && (((Math.abs((start[1] * fov)/ start[2])) < length))))) {
         test1 = (((start[0] * fov) / start[2]));
         test2 = (((start[1] * fov) / start[2]));
         twocoords[2] = (coords[0]) - this.x;
         twocoords[3] = (coords[1]) - this.y;
         twocoords[4] = (coords[2]) - this.z;     
         if (hud == 1) {
            twocoords[2] = (coords[0]) - this.sb_x;
            twocoords[3] = (coords[1]) - this.sb_y;
            twocoords[4] = (coords[2]) - this.sb_z;  
         }    
         twocoords[0] = test1;
         twocoords[1] = test2;
      } else {
         twocoords[0] = length + width;
         twocoords[1] = length + width;
         twocoords[2] = length + width;
         twocoords[3] = length + width;
         twocoords[4] = length + width;
      }
      return twocoords;
   }
   public double[] vertex(double[] point, int hud) {
      double x2 = point[0] - this.x;
      double y2 = point[1] - this.y;
      double z2 = point[2] - this.z;
      if (hud == 1) {
         x2 = (point[0] - (sb_x_offset + this.sb_x)) * sb_move_offset;
         y2 = (point[1] - (sb_y_offset + this.sb_y)) * sb_move_offset;
         z2 = (point[2] - (sb_z_offset + this.sb_z)) * sb_move_offset;
      }
      double[] sts = new double[3];
      sts[0] = ((ayc * ((azs * y2) + (azc * x2))) - (ays * z2));
      sts[1] = ((axs * ((ayc * z2) + (ays * ((azs * y2) + (azc * x2))))) + (axc * ((azc * y2) - (azs * x2)))) * -1;
      sts[2] = ((axc * ((ayc * z2) + (ays * ((azs * y2) + (azc * x2))))) - (axs * ((azc * y2) - (azs * x2))));
      return sts;
   }
   public double[] rotatepoint(double[] point, double anx, double any, double anz) {
      double x2 = point[0];
      double y2 = point[1];
      double z2 = point[2];
      double taxs = Math.sin(anx);
      double taxc = Math.cos(anx);
      double tays = Math.sin(any);
      double tayc = Math.cos(any);
      double tazs = Math.sin(anz);
      double tazc = Math.cos(anz);
      double[] sts = new double[3];
      sts[0] = ((tayc * ((tazs * y2) + (tazc * x2))) - (tays * z2));
      sts[1] = ((taxs * ((tayc * z2) + (tays * ((tazs * y2) + (tazc * x2))))) + (taxc * ((tazc * y2) - (tazs * x2)))) * -1;
      sts[2] = ((taxc * ((tayc * z2) + (tays * ((tazs * y2) + (tazc * x2))))) - (taxs * ((tazc * y2) - (tazs * x2))));
      return sts;
   }
   public boolean Check(double[] pt1) {
      if (pt1[0] == pt1[1] && pt1[0] == length + width && pt1[1] == length + width) {
         if (pt1[2] == pt1[3] && pt1[3] == pt1[4] && pt1[0] == pt1[2]) {
            return false;
         }
      }
      return true;
   }
   public static boolean s_Check(double[] pt1) {
      if (pt1[0] < width && pt1[1] < length && pt1[0] > 0 && pt1[1] > 0) {
         return false;
      }
      return true;
   }
   public void setupangles() {
      this.axc = Math.cos(Math.toRadians(ax));
      this.axs = Math.sin(Math.toRadians(ax));
      this.ayc = Math.cos(Math.toRadians(ay));
      this.ays = Math.sin(Math.toRadians(ay));
      this.azc = Math.cos(Math.toRadians(az));
      this.azs = Math.sin(Math.toRadians(az));
      return;
   }
   public static void lighting(RealEngine eng) {
   /*
      for (int i = 0; i < RealEngine.length; i += 1) {
         for (int j = 0; j < RealEngine.width; j += 1) {
            int[] point = new int[2];
            point[0] = i;
            point[1] = j;
            int colour[] = new int[3];
            int color3[] = new int[3];
            color3[0] = eng.getBuffercol(point[0], point[1], 0);
            color3[1] = eng.getBuffercol(point[0], point[1], 1);
            color3[2] = eng.getBuffercol(point[0], point[1], 2);
            colour[0] = 0;
            colour[1] = 0;
            colour[2] = 0;
            
            double xaxis = eng.getBuffer3d(point[0], point[1], 0);
            double yaxis = eng.getBuffer3d(point[0], point[1], 1);
            double zaxis = eng.getBuffer3d(point[0], point[1], 2);
            double xdist = 0;
            double ydist = 0;
            double zdist = 0;
            double[] light = RealEngine.pack(0,0,0);
            double[] color = RealEngine.pack(255, 255, 255);
            xdist = Math.abs(light[0] - xaxis);
            ydist = Math.abs(light[1] - yaxis);
            zdist = Math.abs(light[2] - zaxis);
            double thingy = Math.sqrt((xdist * xdist) + (ydist * ydist) + (zdist * zdist));
                  
            int brightness = (int) eng.brightness;
                  
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
            } else if (colour[0] < 0) {
               colour[0] = 1;
            }
            if (colour[1] > 255) {
               colour[1] = 255;
            } else if (colour[1] < 0) {
               colour[1] = 1;
            }
            if (colour[2] > 255) {
               colour[2] = 255;
            } else if (colour[2] < 0) {
               colour[2] = 1;
            }
            eng.buffercol[point[0]][point[1]] = colour;
         }
      }*/
   }
   public void postProcess(RealEngine eng, int shadow) {
      double[][] lights = map.getLights();
      double[][] lightcolor = map.getLightColor();
      for (int i = 0; i < this.maxlights; i += 1) {
         double[] point = this.transformpoint(lights[i], 0);
         point[0] += length;
         point[1] += width;
         if (this.Check(point)) {
            double d = Math.abs(point[2]) + Math.abs(point[3]) + Math.abs(point[4]);
            if (this.bufferz[(int) point[0]][(int) point[1]] > d) {
               double thick = (((this.brightness * 4) * this.viewfactor) / d);
               //System.out.println("------------" + thick);
               //System.out.println("------------" + d);
               for (int a = 0; a < this.length; a += 1) {
                  for (int b = 0; b < this.width; b += 1) {
                     double xdist = Math.abs(point[0] - a);
                     double ydist = Math.abs(point[1] - b);
                     int dist = (int) Math.sqrt((xdist * xdist) + (ydist * ydist));
                     if (dist < 100 && (a < this.length && b < this.width) && (a >= 0 && b >= 0)) {
                        if (dist == 0) {
                           dist = 1;
                        }
                        if (shadow == 1) {
                           this.shadowmap[a][b][0] += (255 / dist);
                           this.shadowmap[a][b][1] += (255 / dist);
                           this.shadowmap[a][b][2] += (255 / dist);
                           this.shadowmap[a][b] = packcol(shadowmap[a][b][0], shadowmap[a][b][1], shadowmap[a][b][2]);
                        } else {
                           this.buffercol[a][b][0] += (lightcolor[i][0] / dist);
                           this.buffercol[a][b][1] += (lightcolor[i][1] / dist);
                           this.buffercol[a][b][2] += (lightcolor[i][2] / dist);
                           this.buffercol[a][b] = packcol(buffercol[a][b][0], buffercol[a][b][1], buffercol[a][b][2]);
                        }
                     }
                  }
               }
            }
         } 
      }
   }
   public double computeNDotL(double[] point, double[] normal, double[] lightPos) {
      double[] lightDirection = subtract(point, lightPos);
      normal = normalize(normal);
      lightDirection = normalize(lightDirection);
      return Math.max(0, dot(normal, lightDirection));
   }
   public double dot(double[] point1, double[] point2) {
      return ((point1[0] * point2[0]) + (point1[1] * point2[1]) + (point1[2] * point2[2]));
   }
   public double length(double[] point) {
      return Math.sqrt((point[0] * point[0]) + (point[1] * point[1]) + (point[2] * point[2]));
   }
   public double[] normalize(double[] point) {
      double length = length(point);
      if (length == 0) {
         return point;
      }
      double num = 1.0 / length;
      double[] result = point;
      result[0] *= num;
      result[1] *= num;
      result[2] *= num;
      return result;
   }
   public double[] subtract(double[] item2, double[] item1) {//(10/19/18)
      return pack(item1[0] - item2[0], item1[1] - item2[1], item1[2] - item2[2]);
   }
   public double[] add(double[] item2, double[] item1) {//(10/19/18)
      return pack(item1[0] + item2[0], item1[1] + item2[1], item1[2] + item2[2]);
   }
   public double[] divideby(double[] item, double num) {//(10/19/18)
      return pack((item[0] / num), (item[1] / num), (item[2] / num));
   }
   public static void main(String[] args) {
      main.main(args);
   }
}