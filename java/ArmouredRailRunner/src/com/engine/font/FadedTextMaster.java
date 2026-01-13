package com.engine.font;

import java.util.ArrayList;
import java.util.List;

import org.lwjglx.util.vector.Vector2f;
import org.lwjglx.util.vector.Vector3f;

import com.engine.utils.Loader;

public class FadedTextMaster {
	static List<FadedText> credits = new ArrayList<FadedText>();
	public static void newFadedText(String text, FontType font, Vector2f loc, int fadeTime, int lifeTime, Vector3f textColor) {
		credits.add(new FadedText(text, font, loc, fadeTime, lifeTime, textColor));
	}
	public static void update() {
		for (FadedText text : credits) {
			text.update();
		}
	}
	@SuppressWarnings("unlikely-arg-type")
	public static void remove(GUIText text) {
		credits.remove(text);
	}
}
