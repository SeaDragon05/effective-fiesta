package com.own.master.utg.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import com.own.master.utg.objects.DL;
import com.own.master.utg.objects.WO;


public class UTG {
	private String FileDir;
	private DL data;
	private int error_count = 0;
	public UTG() {}
	public void setFileDir(String dir) {
		this.FileDir = dir;
		this.data = new DL();//not having this line was causing errors because I forgot to add it beforehand for some reason lol.
		try {
			Scanner scnr = new Scanner(new File(FileDir));
			scnr.close();
		} catch (FileNotFoundException e) {
			System.err.println("Error: File " + FileDir + " was not found or does not exist!\nThis will cause errors!");
		} catch (NullPointerException e) {
			System.err.println("Error: File is null?!\nThis will cause errors!");
		}
	}
	public int getErrors() {
		return error_count;
	}
	public void compileData() {
		try {
			File file = new File(FileDir);
			Scanner scnr = new Scanner(file);
			scnr.nextLine();// skip the info line
			String lastWord = scnr.next();
			String word = scnr.next();
			String nextWord = scnr.next();
			data.add(new WO(lastWord, word));
			data.add(new WO(lastWord, word, nextWord));
			while (scnr.hasNext()) {
				lastWord = word;
				word = nextWord;
				nextWord = scnr.next();
				data.add(new WO(lastWord, word, nextWord));
			}
			data.add(new WO(word, nextWord));
			scnr.close();
		} catch (FileNotFoundException e) {
			error_count += 1;
			System.err.println("Error: File " + FileDir + " was not found or does not exist!");
		} catch (NullPointerException e) {
			error_count += 1;
			System.err.println("Error: data is null!");
		}
	}
	public ArrayList<String> generate(int i) {
		ArrayList<String> result = new ArrayList<>();
		for (int loop = 0; loop < i; loop++) {
			WO word = data.getStarterWord();
			String sentence = "";
			sentence += word.getWord() + " ";
			for (int j = 0; j < 400; j++) {
				word = data.getData().get(word.getRandomNext());
				sentence += word.getWord() + " ";
				if (word.getWord().contains(".")) {
					break;
				}
			}
			result.add(sentence);
		}
		return result;
	}
	public String generate() {
		WO word = data.getStarterWord();
		String sentence = "";
		sentence += word.getWord() + " ";
		for (int j = 0; j < 400; j++) {
			word = data.getData().get(word.getRandomNext());
			sentence += word.getWord() + " ";
			if (word.getWord().contains(".")) {
				break;
			}
		}
		return sentence;
	}
}
