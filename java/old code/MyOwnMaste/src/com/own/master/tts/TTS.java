package com.own.master.tts;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

//import javax.speech.Central;
//import javax.speech.synthesis.Synthesizer;
//import javax.speech.synthesis.SynthesizerModeDesc;

public class TTS {
	

	public static void Say(String t) {}
	public static void SayS(String t) {}
	
	/*
	static TTSObject numbs = new TTSObject(1);// meme number?
	static TTSObject words = new TTSObject(0);

	public static void SayWithNumbers(String sentence) {
		ArrayList<String> result = new ArrayList<>();
		Scanner scnr = new Scanner(sentence);
		while (scnr.hasNext()) {
			result.add(scnr.next());
		}
		scnr.close();
		int index = 0;
		do {
			String newSentence = "";
			for (int i = index; i < result.size(); i++) {
				if (!isNumeric(result.get(i))) {
					newSentence += result.get(i) + " ";
				} else {
					index = i + 1;
				}
			}
			//words.Say(newSentence);
			numbs.Say(result.get(index - 1));
			break;
		} while (index < result.size());
	}

	public static boolean isNumeric(String strNum) {
		if (strNum == null) {
			return false;
		}
		try {
			double d = Double.parseDouble(strNum);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

	public static void Say(String t) {
		try {
			System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us." + "cmu_us_kal.KevinVoiceDirectory");
			Central.registerEngineCentral("com.sun.speech.freetts" + ".jsapi.FreeTTSEngineCentral");
			Synthesizer synthesizer = Central.createSynthesizer(new SynthesizerModeDesc(Locale.US));
			synthesizer.allocate();
			synthesizer.pause();
			synthesizer.resume();
			synthesizer.speakPlainText(t, null);
			synthesizer.waitEngineState(Synthesizer.QUEUE_NOT_EMPTY);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void SayNumber(String t) {
		try {
			System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us." + "cmu_us_kal.KevinVoiceDirectory");
			Central.registerEngineCentral("com.sun.speech.freetts" + ".jsapi.FreeTTSEngineCentral");
			Synthesizer synthesizer = Central.createSynthesizer(new SynthesizerModeDesc(Locale.US));
			synthesizer.allocate();
			synthesizer.pause();
			synthesizer.resume();
			synthesizer.speakPlainText(t, null);
			synthesizer.waitEngineState(Synthesizer.QUEUE_NOT_EMPTY);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void SayS(String t) {
		try {
			System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us." + "cmu_us_kal.KevinVoiceDirectory");
			Central.registerEngineCentral("com.sun.speech.freetts" + ".jsapi.FreeTTSEngineCentral");
			Synthesizer synthesizer = Central.createSynthesizer(new SynthesizerModeDesc(Locale.US));
			synthesizer.allocate();
			synthesizer.pause();
			synthesizer.resume();
			synthesizer.speakPlainText(t, null);
			synthesizer.waitEngineState(Synthesizer.QUEUE_EMPTY);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
}
