package com.engine.math;

import java.util.ArrayList;
import java.util.List;

import org.lwjglx.util.vector.Vector3f;

import com.engine.data.TrackInformationData;
import com.engine.data.TrackPointData;
import com.engine.utils.Loader;

import games.trainSim.base.Track;
import games.trainSim.base.TrackPoint;

public class Maths2 {

	public static List<Vector3f> CalculateTrackPositions(Vector3f pt1, Vector3f pt2, Vector3f pt3, float distance) {
		List<Vector3f> result = new ArrayList<Vector3f>();
		float x1, y1, x1S;
		float x2, y2, x2S;
		float x3, y3, x3S;
		float p1p2Distance = getDistance(pt1, pt2);
		float p1p2Step = p1p2Distance/distance;
		float p2p3Distance = getDistance(pt2, pt3);
		float p2p3Step = p2p3Distance/distance;
		// pt1 setup:
		x1 = pt1.x;
		y1 = pt1.z;
		x1S = x1 * x1;
		// pt2 setup:
		x2 = pt2.x;
		y2 = pt2.z;
		x2S = x2 * x2;
		// pt3 setup:
		x3 = pt3.x;
		y3 = pt3.z;
		x3S = x3 * x3;
		//
		float f1x2, f1x, f1y, f2x2, f2x, f2y;
		f1x2 = x1S - x2S; f2x2 = x2S - x3S;
		f1x = x1 - x2; f2x = x2 - x3;
		f1y = y1 - y2; f2y = y2 - y3;
		//
		
		float Scaledf1x2 = (f1x2 * -1) * (f2x * -1);
		//float Scaledf1x = (f1x * -1) * (f2x * -1);
		float Scaledf1y = (f1y * -1) * (f2x * -1);
		float Scaledf2x2 = (f2x2 * -1) * (f1x * -1);
		//float Scaledf2x = Scaledf1x;//same thing
		float Scaledf2y = (f2y * -1) * (f1x * -1);
		//
		
		
		
		float mfx2 = Scaledf1x2 - Scaledf2x2;
		//float mfx = Scaledf1x - Scaledf2x;
		float mfy = Scaledf1y - Scaledf2y;
		
		//mfx2 * a = mfy
		///mfx2      /mfx2
		//a = mfy/mfx2
		
		float a = mfy / mfx2;
		
		//ax2 + bx = y
		//-ax2       -ax2
		//bx = y - ax2
		
		float b = f1y - (f1x2 * a);
		
		//ax2 + bx + c = y
		//c = -(ax2 + bx) + y
		
		float c = f1y - (a + b);
		
		for (float i = x1; i < x1 + p1p2Distance; i++) {
			Vector3f point = new Vector3f(0,pt1.y,0);
			float x = (x1 + (p1p2Step * i));
			point.x = (a * (x * x)) + (b * x) + c;
			point.z = y1 + (i * p1p2Step);
			result.add(point);
		}
		for (float i = x2; i < x2 + p2p3Distance; i++) {
			Vector3f point = new Vector3f(0,pt1.y,0);
			float x = (x2 + (p2p3Step * i));
			point.x = (a * (x * x)) + (b * x) + c;
			point.z = y2 + (i * p1p2Step);
			result.add(point);
		}
		
		return result;
	}
	public static Track createTrack(List<Vector3f> points, float trackDistance, Loader loader) {
		List<Vector3f> trackPoints = new ArrayList<Vector3f>();
		for (int i = 0; i < points.size(); i++) {
			try {
				addList(trackPoints, CalculateTrackPositions(points.get(i), points.get(i+1), points.get(i+2), trackDistance));
			} catch (Exception e) {}
		}
		Track track = null;// = new Track(generateTrackData(trackPoints, trackDistance), trackDistance, loader);
		return track;
	}
	public static float getDistance(Vector3f p1, Vector3f p2) {
		Vector3f dist = new Vector3f((p2.x - p1.x), (p2.y - p1.y), (p2.z - p1.z));
		return (float) Math.sqrt((dist.x * dist.x) + (dist.y * dist.y) + (dist.z * dist.z));
	}
	public static List<TrackPoint> generateTrackData(List<Vector3f> trackPoints, float distance) {
		//TrackPoint data = new TrackPoint(new Vector3f(0,0,0), new Vector3f(0,0,0), 1.0f, false);
		List<TrackPoint> line = new ArrayList<TrackPoint>();
		for (int i = 0; i < trackPoints.size(); i ++) {
			try {
				line.add(new TrackPoint(trackPoints.get(i), calculateRotation(trackPoints.get(i), trackPoints.get(i+1)), distance, false));
			} catch (Exception e) {}
		}
		System.out.println("Track has " + line.size() + " track segmants");
		return line;
	}
	public static Vector3f calculateRotation(Vector3f p1, Vector3f p2) {
		if (p1 == p2) {
			return new Vector3f(0,0,0);
		}
		Vector3f result = new Vector3f(0,0,0);
		float angle = (float) Math.atan2(p1.z - p2.z, p1.x - p2.x);
		result.y = angle;
		return result;
	}
	public static void addList(List<Vector3f> trackPoints, List<Vector3f> list) {
		for (int i = 0; i < list.size(); i ++) {
			trackPoints.add(list.get(i));
		}
	}
	/*
	/*
	 * Taken from StackOverflow This is a really fun problem.
	 * 
	 * You would start by solving a system of equation in three variables to find
	 * the quadratic formula that represents your curve. Any quadratic equation is
	 * defined by three or more points, so you can find the formula if you have
	 * three or more points.
	 * 
	 * The formula for the generalized quadratic is y=ax2+bx+c
	 * 
	 * . We know x and y, so we will be solving for a, b, and c. However, before we
	 * begin, we have to convert to Cartesian points, or everything else will be
	 * messed up. Our three points are now (132, -201); (295, -661); and (664,
	 * -1085). Now lets set up the system.
	 * 
	 * a(132)2+b(132)+c=−201 
	 * a(295)2+b(295)+c=−661 
	 * a(664)2+b(664)+c=−1085
	 * 
	 * Simplifying, we have
	 * 
	 * 17424a+132b+c=−201 
	 * 87025a+295b+c=−661 
	 * 440896a+664b+c=−1085
	 * 
	 * We now subtract equations 1 and 2, as well as equations 2 and 3, to get rid
	 * of the c. 
	 * −69601a−163b=460 
	 * −353871a−369b=424
	 * 
	 * We can multiply both sides of each equation by -1 to make the left sides
	 * positive. 69601a+163b=−460 353871a+369b=−424
	 * 
	 * Before we continue, we must scale the equations so they can be solved with
	 * subtraction.
	 * 
	 * 25682769a+60147b=−169740 
	 * 57680973a+60147b=−69112
	 * 
	 * We can now subtract the two equations, getting rid of the b.
	 * 
	 * −31998204a=−100628 
	 * a=0.003144802...
	 * 
	 * We can substitute this answer into 69601a+163b=−460
	 * 
	 * in order to solve for b.
	 * 
	 * 69601(0.003144802)+163b=−460 163b=−678.881364002 b=−4.164916344
	 * 
	 * We can now solve for c by using 17424a+132b+c=−201
	 * 
	 * 17424(0.003144802)+132(−4.164916344)+c=−201 −494.97392736+c=−201
	 * c=293.97392736
	 * 
	 * We now know that the a, b, and c terms to our quadratic, which is
	 *
	 * f(x)=0.003144802x2−4.164916344x+293.97392736
	 * 
	 * For convenience, I will round to three decimal places, making the equation
	 * 
	 * f(x)=0.003x2−4.165x+293.974
	 * 
	 * Now, if you want six equal rectangles from 132 to 644, you just recognize
	 * that the difference between these two points is 512, so the difference you
	 * want between each one is 512/6, or 85.3333. Therefore, your seven points are
	 * 
	 * f(132) f(132+85.3) f(132+85.3(2) f(132+85.3(3) f(132+85.3(4) f(132+85.3(5)
	 * f(132+85.3(6).
	 * 
	 * This would give you seven points where the x was balanced evenly. But what if
	 * you wanted the actual length of the curve to be split up evenly? This is also
	 * a really cool problem.
	 * 
	 * The arc length of a function f(x)continuous on [a,b] is given by
	 * 
	 * ∫ba1+[f′(x)]2−−−−−−−−−√dx
	 * 
	 * It would probably be easiest to just solve the indefinite integral and use it
	 * to determine arc length, as substitutions will be involved.
	 * 
	 * ∫1+[0.0056x+1.61]2−−−−−−−−−−−−−−−−−√dx
	 * 
	 * We can now make the substitution u=0.0056x+1.61
	 * 
	 * .
	 * 
	 * dudx=0.0056 0.0056dx=du dx=du0.0056
	 * 
	 * Therefore, our integral becomes
	 * 
	 * ∫u2+1−−−−−√du0.0056
	 * 
	 * 178.57∫u2+1−−−−−√du
	 * 
	 * This next part is a bit of a thinker. Imagine a right triangle with sides u
	 * and 1. By the Pythagorean theorem, the hypotenuse would have length
	 * u2+1−−−−−√. Now imagine an angle θ which was adjacent to the side of length 1
	 * and opposite the side of length u. The hypotenuse divided by the adjacent
	 * side would represent our current situation, so we make another substitution.
	 * This time, we substitute secθ for u2+1−−−−−√
	 * 
	 * because in this scenario, they are equivalent.
	 * 
	 * Going back to the triangle, the opposite side to θ would be of length u, and
	 * the adjacent side length 1, so u=tanθ
	 * 
	 * . Furthermore,
	 * 
	 * dudθ=sec2θ
	 * 
	 * du=sec2θ dθ
	 * 
	 * So our integral is now
	 * 
	 * 178.57∫sec3θ dθ
	 * 
	 * This is getting really long, so I'm going to skip a few steps.
	 * http://symbolab.com/ is there to help integrate by parts and stuff from here.
	 * (the actual steps can be seen here:
	 * http://symbolab.com/solver/step_by_step/%5Cint%20sec%5E%7B3%7D%5Ctheta%20d%
	 * 5Ctheta/?origin=button)
	 * 
	 * Anywho, you get the idea. It's 2AM, and I really have to get to bed. I would
	 * greatly appreciate help completing the integral.
	 * 
	 */
}
