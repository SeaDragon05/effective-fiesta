package main;

import java.io.File;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.speech.Central;
import javax.speech.synthesis.Synthesizer;
import javax.speech.synthesis.SynthesizerModeDesc;

import io.Audio;
import io.Window;
import objects.Data;
import objects.wordObject;

public class Main {
	static String FileDir = "";
	public static int error_count = 0;
	public static Data data = new Data();
	static long time1 = System.currentTimeMillis();
	public static boolean advanced_setting_general_music = true;// "Toggle music"
	public static boolean advanced_setting_general_TTS = true;// "Toggle TTS"
	public static boolean advanced_setting_general_userSubm = true;// "Toggle user submitted data"
	public static boolean advanced_setting_generation_chaos = false;// "Toggle generation unpredictability" this
																	// disables double, tripple, and quad compile if
																	// enabled. provides very interesting results
	public static boolean advanced_setting_generation_unlimitedTime = false;// "Toggle compile time limit" this allows
																			// the compiler unlimited time to create a
																			// database without having to terminate
																			// early (if theres a ton of data)
	public static boolean advanced_setting_data_massDatabase = false;// this is for the mass database, compile will NOT
																		// create a new directory, but instead it will
																		// use an existing one called "Learn"
	public static int compile_setting_amount = 4;// this is for data manipulation. it allows the user to define how long
													// the compiled word count will be. From 1 to 4 atm
	public static boolean advanced_setting_generation_multithread = false;// true;//this is for multi threaded compile,
																			// where it divides up the document into
																			// sections and each thread
	// works in each section, allowing much faster compiles
	static boolean creation_setting_random = true;
	static boolean creation_setting_topic_chooser = false;
	static boolean compile_setting_save_previous_word_data = false;
	static boolean compile_setting_save_word_sentence_data = false;
	static boolean compile_setting_save_possible_definition_word_data = false;
	static boolean compile_setting_after_define_word = false;// this defines each SINGLE word and determines what kind
																// of word it is, for example, is it a thing? or a verb?
	static boolean compile_setting_after_guess_language = false;// probably wont be implemented
	static boolean compile_setting_after_check_data = false;// this goes over the data and determines what words lead to
															// dead ends, and marks them for avoidence
	public static int multi_threaded_line_count = 0;
	public static int window_line_count = 0;// max: 27

	public static Window window;
	static String logo_wav = "audio/logo.wav";
	static String like_str = "audio/like.wav";
	static String one_hour = "audio/one_hour.wav";
	static String copypasta = "audio/copypasta.wav";
	static String Sound0 = "audio/woosh0.wav";
	static String Sound1 = "audio/woosh1.wav";
	static String Sound2 = "audio/woosh2.wav";
	static String Sound3 = "audio/woosh3.wav";
	static String Sound4 = "audio/woosh4.wav";
	static String Sound5 = "audio/woosh5.wav";
	static String Sound6 = "audio/woosh6.wav";
	static String Sound7 = "audio/woosh7.wav";
	static String error = "audio/error.wav";
	static String music0 = "audio/music0.wav";
	public static void test() {
		String[] words = new String[5];
		words[0] = "no";
		words[1] = "dumb";
		words[2] = "sun";
		words[3] = "that";
		words[4] = "cream";
		FileDir = "input/beemoviescript.txt";
		compileData();
		compileComplex();
		FileDir = "input/data.txt";
		compileData();
		compileComplex();
		FileDir = "input/lorax.txt";
		compileData();
		compileComplex();
		Generate(50);
	}
	public static void checkWindow() {
		Window.Text.setCaretPosition(Window.Text.getDocument().getLength());// CODE!
		if (compile_setting_amount <= 1) {
			advanced_setting_generation_chaos = true;
		}
	}
	public static void menu_run() {
		window.updateLists();
		Random rand = new Random();
		boolean loop = true;
		int amount = 5;// times the program will run for
		do {
			Generate(amount);
			amount -= 1;
			if (amount <= 0) {
				loop = false;
			}
		} while (loop);
		// printLOGO();//end of program
		window.updateStatus("Program ended. Please press play to start again. Waiting for user input...");
		Say("End of program. I hope you enjoyed it, to some point. There are moments where it generates meaningful stuff and funny things too. But most of it is utter nonsense");
	}
	public static void run_program() {Say("WHTAT");}
	public static void main(String[] args) {
		window = new Window();// creates the window
		Window.Window_Run(window);// closing the window will end the program
		window.clearText();
		Window.Text.setText(
				"Welcome to the Useless Text Generator!\nPress the play button on the top of this screen, or go to\nFile>Run...>Run\nAll generated text will appear here, line by line.\nThere are a few preset txt files, but you can add your own!\nJust add a txt file to data/usersubmitted! The Useless Text Generator will take care of the rest!\nI hope you enjoy my useless text generator!\n\n");
		// Say("Thank you for downloading this awesome project!");
		Audio.playaudio(logo_wav);
		test();
	}

	public static void Generate(String[] words, int i) {
		for (int loop = 0; loop < i; loop++) {
			wordObject word = data.getStarterWord();
			String sentence = "";
			sentence += word.getWord() + " ";
			final int max = words.length;
			int th = 0;
			for (int j = 0; j < 400; j++) {
				// wordObject plH = word;
				if (word.getNextWordList().contains(words[th]) && th < max) {
					word = data.getData().get(words[th]);
					sentence += word.getWord() + " ";
					th++;
				} else {
					word = data.getData().get(word.getRandomNext());
					sentence += word.getWord() + " ";
				}
				if (word.getWord().contains(".")) {
					break;
				}
			}
			Say(sentence);
		}
	}

	public static void Generate(String start, String[] words, int i) {
		try {
			for (int loop = 0; loop < i; loop++) {
				wordObject word = data.getData().get(start);
				String sentence = "";
				sentence += word.getWord() + " ";
				final int max = words.length;
				int th = 0;
				for (int j = 0; j < 400; j++) {
					// wordObject plH = word;
					if (word.getNextWordList().contains(words[th]) && th < max) {
						word = data.getData().get(words[th]);
						sentence += word.getWord() + " ";
						th++;
					} else {
						word = data.getData().get(word.getRandomNext());
						sentence += word.getWord() + " ";
					}
					// if (word.getUses())
					if (word.getWord().contains(".")) {
						break;
					}
				}
				Say(sentence);
			}
		} catch (NullPointerException e) {
			System.err.println("Error: Could not find word " + start + "!");
			Generate(words, i);
		}
	}

	public static void Generate(int i) {
		int largest = 0;
		int lowest = 10000000;
		Set<String> listy = data.getData().keySet(); // WHATEVER
		Object[] list = listy.toArray();
		for (int il = 0; il < data.getData().size(); il++) {
			if (largest < data.getData().get(list[il]).getUses()) {
				largest = data.getData().get(list[il]).getUses();
			}
			if (lowest > data.getData().get(list[il]).getUses()) {
				lowest = data.getData().get(list[il]).getUses();
			}
		}
		for (int loop = 0; loop < i; loop++) {
			wordObject word = data.getStarterWord();
			String sentence = "";
			sentence += word.getWord() + " ";
			for (int j = 0; j < 400; j++) {
				// wordObject plH = word;
				word = data.getData().get(word.getRandomNext());
				// if (word.getUses())
				sentence += word.getWord() + " ";
				if (word.getWord().contains(".")) {
					break;
				}
			}
			Say(sentence);
		}
	}

	public static void Say(String thing) {// this logs what is inputed
		checkWindow();
		window.updateText(thing);
		if (advanced_setting_general_TTS) {
			try {
				System.setProperty("freetts.voices",
						"com.sun.speech.freetts.en.us." + "cmu_us_kal.KevinVoiceDirectory");
				Central.registerEngineCentral("com.sun.speech.freetts" + ".jsapi.FreeTTSEngineCentral");
				Synthesizer synthesizer = Central.createSynthesizer(new SynthesizerModeDesc(Locale.US));
				synthesizer.allocate();
				synthesizer.resume();
				synthesizer.speakPlainText(thing, null);
				synthesizer.waitEngineState(Synthesizer.QUEUE_EMPTY);
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			try {
				TimeUnit.SECONDS.sleep(5);
			} catch (Exception E) {
			}
		}
	}

	public static void Say_n(String thing) {// this does not log what is inputed
		window.updateTextInfo("System: " + thing);
		if (advanced_setting_general_TTS) {
			try {
				System.setProperty("freetts.voices",
						"com.sun.speech.freetts.en.us." + "cmu_us_kal.KevinVoiceDirectory");
				Central.registerEngineCentral("com.sun.speech.freetts" + ".jsapi.FreeTTSEngineCentral");
				Synthesizer synthesizer = Central.createSynthesizer(new SynthesizerModeDesc(Locale.US));
				synthesizer.allocate();
				synthesizer.resume();
				synthesizer.speakPlainText(thing, null);
				synthesizer.waitEngineState(Synthesizer.QUEUE_EMPTY);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void compileData() {
		try {
			File file = new File(FileDir);
			Scanner scnr = new Scanner(file);
			scnr.nextLine();// skip the info line
			String lastWord = scnr.next();
			String word = scnr.next();
			String nextWord = scnr.next();
			data.add(new wordObject(lastWord, word));
			data.add(new wordObject(lastWord, word, nextWord));
			System.out.println(System.currentTimeMillis() - time1);
			while (scnr.hasNext()) {
				lastWord = word;
				word = nextWord;
				nextWord = scnr.next();
				data.add(new wordObject(lastWord, word, nextWord));
			}
			System.out.println(System.currentTimeMillis() - time1);
			data.add(new wordObject(word, nextWord));
			scnr.close();
		} catch (Exception e) {
			error_count += 1;
		}
	}

	public static void compileComplex() {
		try {
			File file = new File(FileDir);
			Scanner scnr = new Scanner(file);
			scnr.nextLine();// skip the info line
			String word1 = scnr.next();
			String word2 = scnr.next();
			String word3 = scnr.next();
			String word4 = scnr.next();
			String word5 = scnr.next();
			String word6 = scnr.next();
			String word7 = scnr.next();
			String word8 = scnr.next();
			quad_words(word1, word2, word3, word4, word5, word6, word7, word8);
			System.out.println(System.currentTimeMillis() - time1);
			while (scnr.hasNext()) {
				word1 = word2;
				word2 = word3;
				word3 = word4;
				word4 = word5;
				word5 = word6;
				word6 = word7;
				word7 = word8;
				word8 = scnr.next();
				quad_words(word1, word2, word3, word4, word5, word6, word7, word8);

			}
			System.out.println(System.currentTimeMillis() - time1);
			scnr.close();
		} catch (Exception e) {
			error_count += 1;
		}
	}

	public static void quad_words(String word_1, String word_2, String word_3, String word_4, String word_5,
			String word_6, String word_7, String word_8) {// magik
		data.add(new wordObject(word_1, (word_2 + " " + word_3)));
		data.add(new wordObject(word_2, (word_3 + " " + word_4)));
		data.add(new wordObject(word_3, (word_4 + " " + word_5)));
		data.add(new wordObject(word_4, (word_5 + " " + word_6)));
		data.add(new wordObject(word_5, (word_6 + " " + word_7)));
		data.add(new wordObject(word_6, (word_7 + " " + word_8)));
		data.add(new wordObject((word_1 + " " + word_2), word_3));
		data.add(new wordObject((word_2 + " " + word_3), word_4));
		data.add(new wordObject((word_3 + " " + word_4), word_5));
		data.add(new wordObject((word_4 + " " + word_5), word_6));
		data.add(new wordObject((word_5 + " " + word_6), word_7));
		data.add(new wordObject((word_6 + " " + word_7), word_8));
		data.add(new wordObject((word_1 + " " + word_2), (word_3 + " " + word_4)));
		data.add(new wordObject((word_2 + " " + word_3), (word_4 + " " + word_5)));
		data.add(new wordObject((word_3 + " " + word_4), (word_5 + " " + word_6)));
		data.add(new wordObject((word_4 + " " + word_5), (word_6 + " " + word_7)));
		data.add(new wordObject((word_5 + " " + word_6), (word_7 + " " + word_8)));
		data.add(new wordObject(word_1, (word_2 + " " + word_3 + " " + word_4)));
		data.add(new wordObject(word_2, (word_3 + " " + word_4 + " " + word_5)));
		data.add(new wordObject(word_3, (word_4 + " " + word_5 + " " + word_6)));
		data.add(new wordObject(word_4, (word_5 + " " + word_6 + " " + word_7)));
		data.add(new wordObject(word_5, (word_6 + " " + word_7 + " " + word_8)));
		data.add(new wordObject((word_1 + " " + word_2), (word_3 + " " + word_4 + " " + word_5)));
		data.add(new wordObject((word_2 + " " + word_3), (word_4 + " " + word_5 + " " + word_6)));
		data.add(new wordObject((word_3 + " " + word_4), (word_5 + " " + word_6 + " " + word_7)));
		data.add(new wordObject((word_4 + " " + word_5), (word_6 + " " + word_7 + " " + word_8)));
		data.add(new wordObject((word_1 + " " + word_2 + " " + word_3), word_4));
		data.add(new wordObject((word_2 + " " + word_3 + " " + word_4), word_5));
		data.add(new wordObject((word_3 + " " + word_4 + " " + word_5), word_6));
		data.add(new wordObject((word_4 + " " + word_5 + " " + word_6), word_7));
		data.add(new wordObject((word_5 + " " + word_6 + " " + word_7), word_8));//
		data.add(new wordObject((word_1 + " " + word_2 + " " + word_3), (word_4 + " " + word_5)));
		data.add(new wordObject((word_2 + " " + word_3 + " " + word_4), (word_5 + " " + word_6)));
		data.add(new wordObject((word_3 + " " + word_4 + " " + word_5), (word_6 + " " + word_7)));
		data.add(new wordObject((word_1 + " " + word_2 + " " + word_3), (word_4 + " " + word_5 + " " + word_6)));
		data.add(new wordObject((word_2 + " " + word_3 + " " + word_4), (word_5 + " " + word_6 + " " + word_7)));
		data.add(new wordObject((word_3 + " " + word_4 + " " + word_5), (word_6 + " " + word_7 + " " + word_8)));
		data.add(new wordObject(word_1, (word_2 + " " + word_3 + " " + word_4 + " " + word_5)));
		data.add(new wordObject(word_2, (word_3 + " " + word_4 + " " + word_5 + " " + word_6)));
		data.add(new wordObject(word_3, (word_4 + " " + word_5 + " " + word_6 + " " + word_7)));
		data.add(new wordObject(word_4, (word_5 + " " + word_6 + " " + word_7 + " " + word_8)));
		data.add(new wordObject((word_1 + " " + word_2), (word_3 + " " + word_4 + " " + word_5 + " " + word_6)));
		data.add(new wordObject((word_2 + " " + word_3), (word_4 + " " + word_5 + " " + word_6 + " " + word_7)));
		data.add(new wordObject((word_3 + " " + word_4), (word_5 + " " + word_6 + " " + word_7 + " " + word_8)));
		data.add(new wordObject((word_1 + " " + word_2 + " " + word_3),
				(word_4 + " " + word_5 + " " + word_6 + " " + word_7)));
		data.add(new wordObject((word_2 + " " + word_3 + " " + word_4),
				(word_5 + " " + word_6 + " " + word_7 + " " + word_8)));
		data.add(new wordObject((word_1 + " " + word_2 + " " + word_3 + " " + word_4),
				(word_5 + " " + word_6 + " " + word_7 + " " + word_8)));
		data.add(new wordObject((word_1 + " " + word_2 + " " + word_3 + " " + word_4), word_5));
		data.add(new wordObject((word_2 + " " + word_3 + " " + word_4 + " " + word_5), word_6));
		data.add(new wordObject((word_3 + " " + word_4 + " " + word_5 + " " + word_6), word_7));
		data.add(new wordObject((word_4 + " " + word_5 + " " + word_6 + " " + word_7), word_8));
		data.add(new wordObject((word_1 + " " + word_2 + " " + word_3 + " " + word_4), (word_5 + " " + word_6)));
		data.add(new wordObject((word_2 + " " + word_3 + " " + word_4 + " " + word_5), (word_6 + " " + word_7)));
		data.add(new wordObject((word_3 + " " + word_4 + " " + word_5 + " " + word_6), (word_7 + " " + word_8)));
		data.add(new wordObject((word_1 + " " + word_2 + " " + word_3 + " " + word_4),
				(word_5 + " " + word_6 + " " + word_7)));
		data.add(new wordObject((word_2 + " " + word_3 + " " + word_4 + " " + word_5),
				(word_6 + " " + word_7 + " " + word_8)));
		data.add(new wordObject((word_1 + " " + word_2 + " " + word_3 + " " + word_4),
				(word_5 + " " + word_6 + " " + word_7 + " " + word_8)));
	}
}
