package DrawBaseLine;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.Stroke;
import javax.swing.JPanel;
import java.awt.geom.*;

/**
 * Class used to create a DrawPanel where shapes will be drawn by the programmer
 * (via code).
 * 
 * @author Jared N. Plumb
 * @version 1.0
 * @since 2019-11-26
 * @Minor modifications by Jeff Light
 * @since 2019-12-01
 * @ThisIsSparta
 */
public class DrawPanel extends JPanel {
	// Attributes
	private static final long serialVersionUID = 6311020027600344213L;

	public DrawPanel() {
		this.setPreferredSize(new Dimension(1600, 900)); // Sets the dimensions of the DrawPanel. Change this if your
															// screen doesn't support this size.
		this.setOpaque(true);
		this.setBackground(Color.WHITE); // Sets the background of the DrawPanel LIGHT_GRAY. You may change this if
											// desired.
	}

	/**
	 * Overrides the JComponent.paintComponent method. EVERYTHING that gets drawn to
	 * the Component (which sits on the content frame of the JFrame) is drawn in
	 * this method. Draw12a Draw all your objects in the paintComponent method.
	 * Automatically called by the event handler whenever the screen needs to be
	 * redrawn. DO NOT CALL THIS METHOD MANUALLY
	 * 
	 * @param g //All drawing in Java must go through a Graphics object
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g; // Allows us to draw using both the Graphics class methods and the Graphics2D
										// class methods
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // Makes drawn shapes
		g2.setColor(Color.white);
		g2.setColor(Color.RED);
		g2.drawLine(((720 / 2) + 15), ((640 / 2) + 15), ((720 / 2) - 15), ((640 / 2) - 15));
		g2.drawLine(((720 / 2) - 15), ((640 / 2) + 15), ((720 / 2) + 15), ((640 / 2) - 15));// the middle cursor thing
		repaint();
		g2.setColor(Color.black);
		setupangles();//sets up the angles according to the camera's angles
		demo();//move the camera
		drawlineat(-30.0, -10.0, -30.0, -30.0, -10.0, 30.0, g2);
		drawlineat(30.0, 20.0, 30.0, -30.0, 20.0, -30.0, g2);
		drawlineat(30.0, -10.0, 30.0, 30.0, -10.0, -30.0, g2);
		drawlineat(30.0, -10.0, -30.0, -30.0, -10.0, -30.0, g2);
		drawlineat(-30.0, 20.0, 30.0, -30.0, -10.0, 30.0, g2);
		drawlineat(-30.0, 20.0, -30.0, -30.0, -10.0, -30.0, g2);
		drawlineat(30.0, 20.0, 30.0, 30.0, -10.0, 30.0, g2);
		drawlineat(30.0, 20.0, -30.0, 30.0, -10.0, -30.0, g2);
		drawlineat(-30.0, 20.0, -30.0, -30.0, 20.0, 30.0, g2);
		drawlineat(-30.0, 20.0, 30.0, 30.0, 20.0, 30.0, g2);
		drawlineat(30.0, 20.0, 30.0, 30.0, 20.0, -30.0, g2);
		drawlineat(30.0, 20.0, -30.0, -30.0, 20.0, -30.0, g2);
		drawlineat(30.0, 20.0, -30.0, -30.0, -10.0, -30.0, g2);
		drawlineat(-30.0, -10.0, 30.0, -30.0, 20.0, -30.0, g2);
		drawlineat(30.0, -10.0, 30.0, 30.0, 20.0, -30.0, g2);
		drawlineat(-30.0, -10.0, 30.0, -45.0, -10.0, 30.0, g2);
		drawlineat(30.0, -10.0, 30.0, 45.0, -10.0, 30.0, g2);
		drawlineat(45.0, -10.0, 30.0, 45.0, -10.0, 120.0, g2);
		drawlineat(-45.0, -10.0, 30.0, -45.0, -10.0, 120.0, g2);
		drawlineat(-15.0, -10.0, 80.0, 15.0, -10.0, 80.0, g2);
		drawlineat(-15.0, -10.0, 80.0, -15.0, -10.0, 90.0, g2);
		drawlineat(15.0, -10.0, 80.0, 15.0, -10.0, 90.0, g2);
		drawlineat(15.0, -10.0, 90.0, -15.0, -10.0, 90.0, g2);
		drawlineat(-45.0, -10.0, 120.0, -75.0, -10.0, 180.0, g2);
		drawlineat(45.0, -10.0, 120.0, 75.0, -10.0, 180.0, g2);
		drawlineat(-45.0, -10.0, 30.0, -45.0, 20.0, 30.0, g2);
		drawlineat(45.0, -10.0, 30.0, 45.0, 20.0, 30.0, g2);
		drawlineat(45.0, 20.0, 30.0, -45.0, 20.0, 30.0, g2);
		drawlineat(45.0, 20.0, 30.0, 45.0, 20.0, 120.0, g2);
		drawlineat(45.0, 20.0, 30.0, 45.0, -10.0, 120.0, g2);
		drawlineat(45.0, 20.0, 120.0, 45.0, -10.0, 120.0, g2);
		drawlineat(45.0, -10.0, 30.0, 30.0, 20.0, 30.0, g2);
		drawlineat(-45.0, -10.0, 30.0, -30.0, 20.0, 30.0, g2);
		drawlineat(-45.0, 20.0, 30.0, -45.0, 20.0, 120.0, g2);
		drawlineat(-45.0, 20.0, 30.0, -45.0, -10.0, 120.0, g2);
		drawlineat(-45.0, 20.0, 120.0, -45.0, -10.0, 120.0, g2);
		drawlineat(15.0, -10.0, 80.0, 15.0, 20.0, 80.0, g2);
		drawlineat(-15.0, -10.0, 80.0, -15.0, 20.0, 80.0, g2);
		drawlineat(-15.0, -10.0, 90.0, -15.0, 20.0, 90.0, g2);
		drawlineat(15.0, -10.0, 90.0, 15.0, 20.0, 90.0, g2);
		drawlineat(15.0, 20.0, 90.0, -15.0, -10.0, 90.0, g2);
		drawlineat(15.0, 20.0, 90.0, -15.0, 20.0, 90.0, g2);
		drawlineat(15.0, 20.0, 90.0, 15.0, 20.0, 80.0, g2);
		drawlineat(15.0, 20.0, 80.0, -15.0, 20.0, 80.0, g2);
		drawlineat(-15.0, 20.0, 80.0, -15.0, 20.0, 90.0, g2);
		drawlineat(-15.0, 20.0, 80.0, -15.0, -10.0, 90.0, g2);
		drawlineat(15.0, 20.0, 80.0, 15.0, -10.0, 90.0, g2);
		drawlineat(15.0, 20.0, 80.0, -15.0, -10.0, 80.0, g2);
		drawlineat(-45.0, 20.0, 120.0, -75.0, 20.0, 180.0, g2);
		drawlineat(-45.0, 20.0, 120.0, -75.0, -10.0, 180.0, g2);
		drawlineat(-75.0, 20.0, 180.0, -75.0, -10.0, 180.0, g2);
		drawlineat(45.0, 20.0, 120.0, 75.0, 20.0, 180.0, g2);
		drawlineat(45.0, 20.0, 120.0, 75.0, -10.0, 180.0, g2);
		drawlineat(75.0, 20.0, 180.0, 75.0, -10.0, 180.0, g2);
		drawlineat(75.0, -10.0, 180.0, 75.0, -10.0, 240.0, g2);
		drawlineat(-75.0, -10.0, 180.0, -75.0, -10.0, 240.0, g2);
		drawlineat(-75.0, 20.0, 180.0, -75.0, 20.0, 240.0, g2);
		drawlineat(-75.0, -10.0, 180.0, -75.0, 20.0, 240.0, g2);
		drawlineat(-75.0, -10.0, 240.0, -75.0, 20.0, 240.0, g2);
		drawlineat(-75.0, -10.0, 240.0, -115.0, -10.0, 240.0, g2);
		drawlineat(-115.0, -10.0, 240.0, -75.0, 20.0, 240.0, g2);
		drawlineat(-115.0, 20.0, 240.0, -75.0, 20.0, 240.0, g2);
		drawlineat(-115.0, 20.0, 240.0, -115.0, -10.0, 240.0, g2);
		drawlineat(-30.0, -10.0, 180.0, 30.0, -10.0, 180.0, g2);
		drawlineat(-30.0, 20.0, 180.0, 30.0, 20.0, 180.0, g2);
		drawlineat(-30.0, -10.0, 180.0, -30.0, -10.0, 240.0, g2);
		drawlineat(-30.0, -10.0, 180.0, -30.0, 20.0, 180.0, g2);
		drawlineat(-30.0, -10.0, 240.0, -75.0, -10.0, 290.0, g2);
		drawlineat(-75.0, -10.0, 290.0, -115.0, -10.0, 290.0, g2);
		drawlineat(-115.0, -10.0, 290.0, -115.0, 20.0, 290.0, g2);
		drawlineat(-115.0, 20.0, 290.0, -75.0, 20.0, 290.0, g2);
		drawlineat(-75.0, 20.0, 290.0, -30.0, 20.0, 240.0, g2);
		drawlineat(-30.0, 20.0, 240.0, -30.0, 20.0, 180.0, g2);
		drawlineat(-115.0, 20.0, 290.0, -75.0, -10.0, 290.0, g2);
		drawlineat(-75.0, -10.0, 290.0, -75.0, 20.0, 290.0, g2);
		drawlineat(-75.0, 20.0, 290.0, -30.0, -10.0, 240.0, g2);
		drawlineat(-30.0, -10.0, 240.0, -30.0, 20.0, 240.0, g2);
		drawlineat(-30.0, 20.0, 240.0, -30.0, -10.0, 180.0, g2);
		drawlineat(-30.0, 20.0, 180.0, 30.0, -10.0, 180.0, g2);
		drawlineat(30.0, -10.0, 180.0, 30.0, 20.0, 180.0, g2);
		drawlineat(30.0, -10.0, 180.0, 30.0, -10.0, 240.0, g2);
		drawlineat(30.0, -10.0, 240.0, 75.0, -10.0, 290.0, g2);
		drawlineat(30.0, 20.0, 240.0, 30.0, 20.0, 180.0, g2);
		drawlineat(30.0, 20.0, 180.0, 30.0, -10.0, 240.0, g2);
		drawlineat(30.0, -10.0, 240.0, 30.0, 20.0, 240.0, g2);
		drawlineat(30.0, 20.0, 240.0, 75.0, 20.0, 290.0, g2);
		drawlineat(75.0, 20.0, 290.0, 30.0, -10.0, 240.0, g2);
		drawlineat(75.0, 20.0, 290.0, 75.0, -10.0, 290.0, g2);
		drawlineat(75.0, 20.0, 240.0, 75.0, 20.0, 180.0, g2);
		drawlineat(75.0, 20.0, 240.0, 75.0, -10.0, 240.0, g2);
		drawlineat(75.0, 20.0, 240.0, 75.0, -10.0, 180.0, g2);
		drawlineat(75.0, -10.0, 240.0, 125.0, -10.0, 240.0, g2);
		drawlineat(75.0, 20.0, 240.0, 125.0, 20.0, 240.0, g2);
		drawlineat(75.0, 20.0, 240.0, 125.0, -10.0, 240.0, g2);
		drawlineat(125.0, -10.0, 240.0, 125.0, 20.0, 240.0, g2);
		drawlineat(75.0, -10.0, 290.0, 125.0, -10.0, 290.0, g2);
		drawlineat(75.0, 20.0, 290.0, 125.0, 20.0, 290.0, g2);
		drawlineat(125.0, 20.0, 290.0, 125.0, -10.0, 290.0, g2);
		drawlineat(75.0, -10.0, 290.0, 125.0, 20.0, 290.0, g2);
	}// end of method paintComponent(Graphics)
		// 3d engine stuff:

	public Dimension getPreferredSize() {// changes the resolution
		return new Dimension(720, 640);
	}
	static double x = 0.0;//camera location
	static double y = 7.0;
	static double z = -45.0;
	static double x2 = 0.0;//do not touch:
	static double y2 = 0.0;
	static double z2 = 0.0;
	static double anglex = 0.0;
	static double angley = 0.0;
	static double anglez = 0.0;
	static double anglexcos = 0.0;
	static double angleycos = -90.0;
	static double anglezcos = 0.0;
	static double anglexsin = 0.0;
	static double angleysin = 0.0;
	static double anglezsin = 0.0;
	static double startingx = 0.0;
	static double startingy = 0.0;
	static double startingz = 0.0;
	static double endingx = 0.0;
	static double endingy = 0.0;
	static double endingz = 0.0;
	static double changexby = 0.0;
	static double changeyby = 0.0;
	static double changezby = 0.0;
	final static double viewfactor = 270.0;//have fun adjusting these
	final static int maxdist = 1000;
	final static int repeat = 10;
	static double speed = 1.0;//broken/etc:
	static double draw_x1 = 0.0;
	static double draw_y1 = 0.0;
	static double draw_x2 = 0.0;
	static double draw_y2 = 0.0;
	public static void setupangles() {
		anglexcos = Math.cos(anglex);
		anglexsin = Math.sin(anglex);
		angleycos = Math.cos(angley);
		angleysin = Math.sin(angley);
		anglezcos = Math.cos(anglez);
		anglezsin = Math.sin(anglez);
		return;
	}

	public static void setupdraws(double X1L, double Y1L, double Z1L, int varE) {
		x2 = X1L - x;
		y2 = Y1L - y;
		z2 = Z1L - z;
		if (varE == 0) {
			startingx = ((angleycos * ((anglezsin * y2) + (anglezcos * x2))) - (angleysin * z2)) * -1;
			startingy = ((anglexsin * ((angleycos * z2) + (angleysin * ((anglezsin * y2) + (anglezcos * x2)))))
					+ (angleycos * ((anglezcos * y2) - (anglezsin * x2))));
			startingz = ((anglexcos * ((angleycos * z2) + (angleysin * ((anglezsin * y2) + (anglezcos * x2)))))
					- (angleysin * ((anglezcos * y2) - (anglezsin * x2)))) * -1;
		} else {
			endingx = ((angleycos * ((anglezsin * y2) + (anglezcos * x2))) - (angleysin * z2)) * -1;
			endingy = ((anglexsin * ((angleycos * z2) + (angleysin * ((anglezsin * y2) + (anglezcos * x2)))))
					+ (angleycos * ((anglezcos * y2) - (anglezsin * x2))));
			endingz = ((anglexcos * ((angleycos * z2) + (angleysin * ((anglezsin * y2) + (anglezcos * x2)))))
					- (angleysin * ((anglezcos * y2) - (anglezsin * x2)))) * -1;
		}
	}
	public void drawlineat(double X1D, double Y1D, double Z1D, double X2D, double Y2D, double Z2D, Graphics g) {
		setupdraws(X1D, Y1D, Z1D, 0);
		setupdraws(X2D, Y2D, Z2D, 1);
		double fov = viewfactor;
		changexby = (endingx - startingx) / repeat;
		changeyby = (endingy - startingy) / repeat;
		changezby = (endingz - startingz) / repeat;
		draw_x1 = (((startingx * fov) / startingz)) + (640 / 2);
		draw_y1 = (((startingy * fov) / startingz)) + (720 / 2);
		double startingx1 = startingx;
		double startingy1 = startingy;
		double startingz1 = startingz;
		int rep = 1;
		for (int j = 0; j < repeat; j += 1) {
			startingx1 = (startingx1 + changexby);
			startingy1 = (startingy1 + changeyby);
			startingz1 = (startingz1 + changezby);
			if (rep == 0) {
				startingx = (startingx + changexby);
				startingy = (startingy + changeyby);
				startingz = (startingz + changezby);
			}
			rep = 0;
			if (((0 < startingz) && (((Math.abs((startingy * fov) / startingz)) < 640)
					&& (((Math.abs((startingy * fov) / startingz)) < 720)))) == false) {
				draw_x2 = (((startingx * fov) / startingz));
				draw_y2 = (((startingy * fov) / startingz));
				draw_x1 = (((startingx1 * fov) / startingz1));
				draw_y1 = (((startingy1 * fov) / startingz1));
				if ((draw_x1 + (720 / 2)) > -100 && draw_x1 < 500) {
					if ((draw_y1 + (640 / 2)) > -100 && draw_y1 < 500) {
						if ((draw_x2 + (720 / 2)) > -100 && draw_x2 < 500) {
							if ((draw_y2 + (640 / 2)) > -100 && draw_y2 < 500) {
								if ((Math.abs(startingz / fov)) < (maxdist * 0.01)
										&& (Math.abs(startingx / fov)) < (maxdist * 0.01)
										&& (Math.abs(startingy / fov)) < (maxdist * 0.01)) {
									g.drawLine((int) draw_x1 + (720 / 2), (int) draw_y1 + (640 / 2),
											(int) draw_x2 + (720 / 2), (int) draw_y2 + (640 / 2));
								}
							}
						}
					}
				}
			}
		}
		return;
	}
	public static void demo() {//moves the camera in a loop
		if (z < 10) {
			z += 0.1;
			y = (Math.cos(z) / 2) + 7;
		} else {
			if (z >= 45 && x > -30) {
				x -= 0.1;
				y = (Math.cos(x) / 2) + 7;
			} else {
				if (z < 140) {
					z += 0.1;
					y = (Math.cos(z) / 2) + 7;
				} else {
					if (x > -50) {
						x -= 0.1;
						y = (Math.cos(x) / 2) + 7;
					} else {
						if (z < 190) {
							z += 0.1;
							y = (Math.cos(z) / 2) + 7;
						} else {
							x = 0;
							z = -45;
						}
					}
				}
			}
		}
	}
}// end of class DrawPanel