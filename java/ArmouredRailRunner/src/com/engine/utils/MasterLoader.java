package com.engine.utils;

import java.util.ArrayList;
import java.util.List;

import com.engine.graphics.ModelTexture;
import com.engine.objects.RawModel;
import com.engine.objects.TexturedModel;

import main.Engine;

//Since lwjgl doesn't sort out this kind of thing, this does

public class MasterLoader {
	static Loader loader = new Loader();
	static List<String> textureUrls = new ArrayList<String>();
	static List<Integer> texturePointers = new ArrayList<Integer>();
	static List<RawModel> models = new ArrayList<RawModel>();
	static List<String> modelUrls = new ArrayList<String>();
	
	public static TexturedModel loadTexturedModel(String modelURL, String textureURL) {
		ModelTexture texture = new ModelTexture(loadTexturePointer(textureURL));
		RawModel model = loadRawModel(modelURL);
		return new TexturedModel(model, texture);
	}
	
	public static TexturedModel loadNormalMappedTexturedModel(String modelURL, String textureURL) {
		ModelTexture texture = new ModelTexture(loadTexturePointer(textureURL));
		RawModel model = loadNormalMappedObject(modelURL);
		return new TexturedModel(model, texture);
	}
	
	public static Loader getLoader() {
		return loader;
	}
	
	public static RawModel loadRawModel(String URL) {
		if (modelUrls.contains(URL)) {
			for (int i = 0; i < modelUrls.size(); i++) {
				if (modelUrls.get(i) == URL) {
					Engine.println("The model: " + Engine.color_red + URL + ".obj \u001B[33malready exists in memory", "\u001B[33m");
					return models.get(i);
				}
			}
		}
		modelUrls.add(URL);
		RawModel model = OBJLoader.loadObjModel(URL, loader);
		models.add(model);
		return model;
	}

	public static int loadTexturePointer(String url) {
		if (textureUrls.contains(url)) {
			for (int i = 0; i < textureUrls.size(); i++) {
				if (textureUrls.get(i) == url) {
					Engine.println("The texture: " + Engine.color_red + url + ".png \u001B[33malready exists in memory", "\u001B[33m");
					return texturePointers.get(i);
				}
			}
		}
		textureUrls.add(url);
		int pointer = loader.loadTexture(url);
		texturePointers.add(pointer);
		return pointer;
	}
	
	public static void cleanUp() {
		//loader.cleanUp();
	}

	public static RawModel loadNormalMappedObject(String URL) {
		if (modelUrls.contains(URL)) {
			for (int i = 0; i < modelUrls.size(); i++) {
				if (modelUrls.get(i) == URL) {
					Engine.println("The model: " + URL + ".obj already exists in memory", Engine.color_red);
					Engine.println("It is possible that this object may not display correctly and could cause problems if it wasn't loaded as a normal mapped object!", "\u001B[31m");
					return models.get(i);//HUGE RISK
				}
			}
		}
		modelUrls.add(URL);
		RawModel model = NormalMappedObjLoader.loadObjModel(URL, loader);
		models.add(model);
		return model;
	}
}
