package com.engine.objects;

import org.lwjglx.util.vector.Vector2f;

import com.engine.font.FontType;
import com.engine.font.GUIText;
import com.engine.graphics.GuiTexture;
import com.engine.utils.Loader;

public class Button {
	private Vector2f loc;
	private Vector2f size;
	private GUIText text;
	private GuiTexture texture;
	
	public Button(String text, FontType font, float fontSize, Vector2f pos, Vector2f size, String buttonImg, Loader loader) {
		this.loc = pos;
		this.size = size;
		this.text = new GUIText(text, fontSize, font, size, 1, false);
		this.texture = new GuiTexture(loader.loadTexture(buttonImg), pos, size);
	}
	public Button(String text, FontType font, float fontSize, Vector2f pos, Vector2f size, int buttonImg) {
		this.loc = pos;
		this.size = size;
		this.text = new GUIText(text, fontSize, font, size, 1, false);
		this.texture = new GuiTexture(buttonImg, pos, size);
	}
	public Vector2f getLoc() {
		return loc;
	}
	public void setLoc(Vector2f loc) {
		this.loc = loc;
		this.text.getPosition().x = loc.x;
		this.text.getPosition().y = loc.y;
		this.texture.getPosition().x = loc.x;
		this.texture.getPosition().y = loc.y;
	}
	public Vector2f getSize() {
		return size;
	}
}
