package code;

import java.nio.charset.Charset;
import java.util.Scanner;

public class Main {
	public static boolean debug = true;
	public static void main(String[] args) throws Exception {
		//Sam sam = new Sam();
		//sam.setInput("AX Fax");
		//sam.SAMMain();
		//System.out.println(sam.getBuffer().toString());
		
		int i;
		int phonetic = 0;
		int[] input = new int[256];
		

		for(i=0; input[i] != 0; i++)
			input[i] = toupper((int)input[i]);

		if (debug)
		{
			if (phonetic == 1) System.out.printf("phonetic input: %s\n", input);
			else System.out.printf("text input: %s\n", input); 
		}
		
		
	}
}
