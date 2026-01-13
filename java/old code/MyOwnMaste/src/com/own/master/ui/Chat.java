package com.own.master.ui;

import com.own.master.engine.graphics.Material;
import com.own.master.engine.graphics.Mesh;
import com.own.master.engine.graphics.Renderer;
import com.own.master.engine.objects.font.FontType;
import com.own.master.engine.objects.font.GUIText;

public class Chat {
	private Mesh mesh;
	private GUIText thing;
	public static final Material mat = new Material("/textures/bricks1.jpg", 0);//"/font/segoeUI.png", 0);
	public Chat() {}
	public void renderText(Renderer rnd) {
		rnd.renderFont(mesh);
	}
	public void setText(GUIText text) {
		thing = text;
		FontType font = thing.getFont();
		mesh = font.loadText(thing, mat);
		mesh.create();

	}
	public void updateText(String text) {
		mesh.destroy();
		mesh = thing.getFont().loadText(thing, mat);
	}
	public void destroy() {
		mesh.destroy();
	}
	public GUIText getText() {
		return thing;
	}
}
