package dumb.stuff;

import java.util.ArrayList;

public class Main {
	public static ArrayList<Vector3f> list = new ArrayList<Vector3f>();
	public static void main(String[] args) {
		for (int i = 0; i < 10; i ++) {
			list.add(new Vector3f(i, 0, 0));
		}
		System.out.println(list.toString());
	}
	
}
