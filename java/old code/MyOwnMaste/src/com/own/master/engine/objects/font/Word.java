package com.own.master.engine.objects.font;

import java.util.ArrayList;

public class Word {
	private ArrayList<Character> characters = new ArrayList<>();
	private float width = 0;
	private float fontSize;

	public Word(float fontSize) {
		this.fontSize = fontSize;
	}
	public void addChar(Character character) {
		this.characters.add(character);
	}
	public ArrayList<Character> getCharacters() {
		return this.characters;
	}
	public double getWordWidth() {
		return this.width;
	}
}
