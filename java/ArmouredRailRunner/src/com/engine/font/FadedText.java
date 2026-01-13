package com.engine.font;

import org.lwjglx.util.vector.Vector2f;
import org.lwjglx.util.vector.Vector3f;

import com.engine.utils.Loader;

public class FadedText {
	private GUIText text;
	//fade1 is fade in, fade2 is to hold the text to a visible standard after the first fade, fade3 is to fade into oblivion
	private boolean fade1 = false, fade2 = false;
	private float fade = 0;
	@SuppressWarnings("unused")
	private int fadeTime = 0;
	private float currentFade = 0;//used to hold the fade during the second phase
	private int lifeTime = 0;
	protected FadedText(String text, FontType font, Vector2f loc, int fadeTime, int lifeTime, Vector3f textColor) {
		this.text = new GUIText(text, 1f, font, loc, 0.3f, false);
		this.text.setColour(textColor.x, textColor.y, textColor.z);
		this.fadeTime = fadeTime;
		this.lifeTime = lifeTime;
	}
	public void update() {//for the introduction credits
		if (!fade1) {
			if (fade >= 1) {
				fade1 = true;
			} else {
				this.fade += 0.01f;
				this.text.setFade(fade);
			}
		} else if (fade1 && !fade2) {
			if (currentFade >= lifeTime) {
				fade2 = true;
			} else {
				currentFade += 0.1f;
			}
		} else if (fade1 && fade2) {
			if (fade <= 0) {
				this.remove();
				return;
			}
			this.fade -= 0.01f;
			this.text.setFade(fade);
		}
	}
	protected void remove() {
		FadedTextMaster.remove(text);
	}
}
