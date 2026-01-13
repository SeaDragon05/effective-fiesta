package com.engine.utils;

import com.engine.objects.RawModel;

public class LoaderTest {
	public static void main(String[] args) {
		Loader loader = new Loader();
		int t1 = loader.loadTexture("");
		int t2 = loader.loadTexture("");
		System.out.println(t1 == t2);
		OBJLoader loader2 = new OBJLoader();
		RawModel m1 = loader2.loadObjModel("", loader);
		RawModel m2 = loader2.loadObjModel("", loader);
		System.out.println(m1.getVaoID() == m2.getVaoID());
	}
}
