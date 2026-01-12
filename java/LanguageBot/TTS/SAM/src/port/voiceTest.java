package port;

public class voiceTest {
	public static void main(String[] args) {
		Main.Say("This is a test");
		
		
		String sentence = "AEM AH /HAO";
		Sam.singmode = false;
		Sam.outputLength = 6;
		Sam.speed = 45;
		Sam.mouth = 128;
		Sam.throat = 128;
		//public static int speed = 72;
		//public static int pitch = 64;
		//public static int mouth = 128;
		//public static int throat = 128;
		
		Sam.pitch = 53;
		int[] vocals1 = Sing.getTTSAudio(sentence);
		//Sam.pitch = 120;
		int[] vocals2 = Sing.getTTSAudio(sentence);
		Sing.mixVoices(vocals1, vocals2);
		Sing.speak(sentence);
		
		
	}
}
