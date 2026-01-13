package com.own.master.utg.objects;

import java.util.ArrayList;
import java.util.Random;

public class WO {
	private String word;
	private ArrayList<String> lastWord;
	private ArrayList<String> nextWord;
	private boolean isStart;
	private int uses = 1;
	public WO(String lastWord, String word, String nextWord) {
		if (lastWord.contains(".")) {
			isStart = true;
		}
		this.word = word;
		this.lastWord = new ArrayList<>() {
			private static final long serialVersionUID = 8704258374950675817L;
			@Override
			public String toString() {
				String result = "";
				for (String element : this) {
					result += element + "\n";
				}
				return result;
			}
		};
		this.nextWord = new ArrayList<>() {
			private static final long serialVersionUID = 8704258374950675817L;
			@Override
			public String toString() {
				String result = "";
				for (String element : this) {
					result += element + "\n";
				}
				return result;
			}
		};
		this.lastWord.add(lastWord);
		this.nextWord.add(nextWord);
	}
	public WO(String word) {
		this.word = word;
		this.lastWord = new ArrayList<>() {
			private static final long serialVersionUID = 8704258374950675817L;
			@Override
			public String toString() {
				String result = "";
				for (String element : this) {
					result += element + "\n";
				}
				return result;
			}
		};
		this.nextWord = new ArrayList<>() {
			private static final long serialVersionUID = 8704258374950675817L;
			@Override
			public String toString() {
				String result = "";
				for (String element : this) {
					result += element + "\n";
				}
				return result;
			}
		};
	}
	public WO(String word, String nextWord) {
		this.word = word;
		this.lastWord = new ArrayList<>() {
			private static final long serialVersionUID = 8704258374950675817L;
			@Override
			public String toString() {
				String result = "";
				for (String element : this) {
					result += element + "\n";
				}
				return result;
			}
		};
		this.nextWord = new ArrayList<>() {
			private static final long serialVersionUID = 8704258374950675817L;
			@Override
			public String toString() {
				String result = "";
				for (String element : this) {
					result += element + "\n";
				}
				return result;
			}
		};
		this.nextWord.add(nextWord);
	}
	public void add(WO word) {
		for (int i = 0; i < word.getLastWordList().size(); i++) {
			this.lastWord.add(word.getLastWordList().get(i));
		}
		for (int i = 0; i < word.getNextWordList().size(); i++) {
			this.nextWord.add(word.getNextWordList().get(i));
		}
		uses += 1;
	}
	public int getUses() {
		return this.uses;
	}
	public String getWord() {
		return word;
	}
	public ArrayList<String> getLastWordList() {
		return lastWord;
	}
	public void addLast(String lastWord) {
		this.lastWord.add(lastWord);
	}
	public ArrayList<String> getNextWordList() {
		return nextWord;
	}
	public String getRandomNext() {
		Random rnd = new Random();
		return this.nextWord.get(rnd.nextInt(this.nextWord.size()));
	}
	public String getRandomLast() {
		Random rnd = new Random();
		return this.lastWord.get(rnd.nextInt(this.lastWord.size()));
	}
	public void addNext(String nextWord) {
		this.nextWord.add(nextWord);
	}
	public boolean isStart() {
		return isStart;
	}
	public void setStart(boolean isStart) {
		this.isStart = isStart;
	}
}
