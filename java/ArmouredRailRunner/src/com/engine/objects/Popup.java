package com.engine.objects;

import org.lwjglx.util.vector.Vector2f;
import org.lwjglx.util.vector.Vector3f;

import com.engine.font.FontType;
import com.engine.font.GUIText;
import com.engine.font.TextMaster;
import com.engine.graphics.GuiTexture;
import com.engine.utils.Event;
import com.engine.utils.MasterLoader;

import main.Engine;
 
public class Popup extends Event {
	public static final int TYPE_BOTTOM_LEFT = 0;
	public static final int TYPE_TOP_LEFT = 1;
	public static final int TYPE_BOTTOM_RIGHT = 2;
	public static final int TYPE_TOP_RIGHT = 3;
	public static final int TYPE_CENTER = 4;
	public static final Vector2f B_L_POS_T = new Vector2f(0.1f, 0.9f);
	public static final Vector2f B_L_POS_I = new Vector2f(-0.5f, -0.9f);
	public static final Vector2f C_POS_T = new Vector2f(0.44f, 0.48f);
	public static final Vector2f C_POS_I = new Vector2f(0, 0);
	private GUIText text;
	private GuiTexture texture;
	public Popup(String text, FontType font, Vector3f textColor, int type) {
		super(0);
		if (type == TYPE_BOTTOM_LEFT) {
			this.text = new GUIText(text, 1f, font, B_L_POS_T, 0.3f, false);
			this.texture = new GuiTexture(MasterLoader.loadTexturePointer("guis/common/hudPopup"), B_L_POS_I,
					new Vector2f(0.15f, 0.1f));
			this.text.setColour(textColor.x, textColor.y, textColor.z);
			this.add();
		}
		else if (type == TYPE_CENTER) {
			this.text = new GUIText(text, 1f, font, C_POS_T, 0.3f, false);
			this.texture = new GuiTexture(MasterLoader.loadTexturePointer("guis/common/hudPopup"), C_POS_I,
					new Vector2f(0.15f, 0.1f));
			this.text.setColour(textColor.x, textColor.y, textColor.z);
			this.add();
		}
	}
	public Popup(String text, FontType font, Vector3f textColor, int type, int EventType) {
		super(EventType);
		if (type == TYPE_BOTTOM_LEFT) {
			this.text = new GUIText(text, 1f, font, B_L_POS_T, 0.3f, false);
			this.texture = new GuiTexture(MasterLoader.loadTexturePointer("guis/common/hudPopup"), B_L_POS_I,
					new Vector2f(0.15f, 0.1f));
			this.text.setColour(textColor.x, textColor.y, textColor.z);
			//this.add();
		}
		else if (type == TYPE_CENTER) {
			this.text = new GUIText(text, 1f, font, C_POS_T, 0.3f, false);
			this.texture = new GuiTexture(MasterLoader.loadTexturePointer("guis/common/hudPopup"), C_POS_I,
					new Vector2f(0.15f, 0.1f));
			this.text.setColour(textColor.x, textColor.y, textColor.z);
			//this.add();
		}
	}
	public void add() {
		Engine.guis.add(texture);
	}
	public void remove() {
		TextMaster.remove(text);
		Engine.guis.remove(texture);
	}
	@Override
	public void fire() {
		if (isSet())
			this.add();
	}
}
