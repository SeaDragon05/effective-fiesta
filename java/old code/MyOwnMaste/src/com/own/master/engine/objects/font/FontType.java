package com.own.master.engine.objects.font;

import java.io.File;

import com.own.master.engine.graphics.Material;
import com.own.master.engine.graphics.Mesh;

public class FontType {
	private int textureAtlas;
	private TextMeshCreator loader;

	public FontType(int textureAtlas, File fontFile) {
		this.textureAtlas = textureAtlas;
		this.loader = new TextMeshCreator(fontFile);
	}

	public int getTextureAtlas() {
		return textureAtlas;
	}

	public Mesh loadText(GUIText text, Material mat) {
		return loader.createTextMesh(text, mat);
	}
	public TextMeshCreator getLoader() {
		return loader;
	}
}
