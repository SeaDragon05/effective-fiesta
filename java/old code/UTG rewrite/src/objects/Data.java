package objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import main.Main;

public class Data {

	private HashMap<String, wordObject> data;
	private ArrayList<wordObject> manifest;

	public Data() {
		data = new HashMap<String, wordObject>() {
			private static final long serialVersionUID = 1L;
			@Override
			public String toString() {
				int connections = 0;
				for (int i = 0; i < this.size(); i++) {
					connections += this.get(i).getLastWordList().size() + this.get(i).getNextWordList().size();
				}
				return "Data list contains " + this.size() + " words with " + connections + " connections within";
			}
		};
		manifest = new ArrayList<wordObject>();
	}
	public void add(wordObject word) {//add new wordObject
		if (data.containsKey(word.getWord())) {
			data.get(word.getWord()).add(word);
		} else {
			data.put(word.getWord(), word);
		}
		if (word.isStart()) {
			manifest.add(word);
		}
	}
	public void add(String word, String next) {
		if (data.containsKey(word)) {
			data.get(word).addNext(next);
		} else {
			Main.error_count += 1;
		}
	}
	public HashMap<String, wordObject> getData() {
		return data;
	}
	public ArrayList<String> getWordLastList(String word) {
		return this.data.get(word).getLastWordList();
	}
	public ArrayList<String> getWordNextList(String word) {
		return this.data.get(word).getNextWordList();
	}
	public wordObject getStarterWord() {
		Random rnd = new Random();
		return manifest.get(rnd.nextInt(manifest.size()));
	}
}
