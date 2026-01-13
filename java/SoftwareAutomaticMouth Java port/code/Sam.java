package code;

import java.io.UnsupportedEncodingException;

public class Sam implements SamInterface {

	public enum Flags {
		FLAG_PLOSIVE(0x0001), FLAG_STOPCONS(0x0002), /* stop consonant */
		FLAG_VOICED(0x0004),
		/* 0x08 */
		FLAG_DIPHONG(0x0010), FLAG_DIP_YX(0x0020), FLAG_CONSTONANT(0x0040), /*
																									 * dipthong ending
																									 * with YX
																									 */
		FLAG_VOWEL(0x0080), FLAG_PUNCT(0x0100),
		/* 0x200 */
		FLAG_ALVEOLAR(0x0400), FLAG_NASAL(0x0800), FLAG_LIQUIC(0x1000), /* liquic consonant */
		FLAG_FRICATIVE(0x2000);

		private final int value;

		Flags(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}

	public enum Constants {
		pR((23)), pD((57)), pT((69)), BREAK((254)),
		END((255));

		private final int value;

		Constants(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}


	private int[] input = new int[256]; // tab39445
	// standard sam sound
	private int speed = 72;
	private int pitch = 64;
	private int mouth = 128;
	private int throat = 128;

	private boolean singMode = false;

	private int[] stress = new int[256]; // numbers from 0 to 8
	private int[] phonemeLength = new int[256]; // tab40160
	private int[] phonemeindex = new int[256];

	private int[] phonemeIndexOutput = new int[60]; // tab47296
	private int[] stressOutput = new int[60]; // tab47365
	private int[] phonemeLengthOutput = new int[60]; // tab47416

	private int bufferPos = 0;
	private byte[] buffer = null;
	@Override
	public void setInput(String _input) {
		int[] converted = new int[_input.length()];
		for (int x = 0; x < converted.length; x++) {
			converted[x] = _input.charAt(x);
		}
		converted = SamTabs.convert(converted);
		
		int i, l;
		l = converted.length;
		
		if (l> 254) l = 254;
		for (i=0; i<l; i++) {
			this.input[i] = converted[i];
		}
		this.input[l] = 0;
	}
	@Override
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	@Override
	public void setPitch(int pitch) {
		this.pitch = pitch;
	}
	@Override
	public void setMouth(int mouth) {
		this.mouth = mouth;
	}
	@Override
	public void setThroat(int throat) {
		this.throat = throat;
	}
	@Override
	public void enableSingMode() {this.singMode = true;}
	
	
	private void Init() {
		int i;
		
		bufferPos = 0;
		
		buffer = new byte[22050*10];
		
		for(i=0; i<256; i++) {
			stress[i] = 0;
			phonemeLength[i] = 0;
		}

		for(i=0; i<60; i++) {
			phonemeIndexOutput[i] = 0;
			stressOutput[i] = 0;
			phonemeLengthOutput[i] = 0;
		}
		phonemeindex[255] = Constants.END.getValue();
	}
	
	@Override
	public int SAMMain() throws Exception {
		int X = 0;
		Init();
		phonemeindex[255] = 32;
		
		if (!Parser1()) return 0;
		System.out.println("Success!");
		
		return 0;
	}
	private int full_match(int sign1, int sign2) {
	    int Y = 0;
	    do {
	        // GET FIRST CHARACTER AT POSITION Y IN signInputTable
	        // --> should change name to PhonemeNameTable1
	        int A = SamTabs.signInputTable1[Y];
	        if (A == sign1) {
	        	System.out.println(": True");
	            A = SamTabs.signInputTable2[Y];
	            System.out.print("new A: " + A + ((A != 42 ? " Yes" : "") + "\n"));
	            System.out.println("Ok, so we are now looking for " + sign2 + " = " + A);
	            // NOT A SPECIAL AND MATCHES SECOND CHARACTER?
	            if ((A != 42) && (A == sign2)) return Y;
	        }
	    } while (++Y != 81);
	    return -1;
	}

	private int wild_match(int sign1) {
	    int Y = 0;
	    do {
			if (SamTabs.signInputTable2[Y] == '*') {
				if (SamTabs.signInputTable1[Y] == sign1) return Y;
			}
		} while (++Y != 81);
	    return -1;
	}
	
	private boolean Parser1() {
		int sign1;
		int position = 0;
		int srcpos = 0;
		
		stress = new int[256];
		
		while((sign1 = input[srcpos]) != 155) {
			System.out.println("DEBUG:");
			System.out.println("Value: " + sign1);
			int match;
			int sign2 = input[++srcpos];
	        if ((match = full_match(sign1, sign2)) != -1) {
	        	System.out.println("No wildCards (found)");
	            // Matched both characters (no wildcards)
	            phonemeindex[position++] = match;
	            ++srcpos; // Skip the second character of the input as we've matched it
	        } else if ((match = wild_match(sign1)) != -1) {
	        	System.out.println("WildCard (found)");
	            // Matched just the first character (with second character matching '*'
	            phonemeindex[position++] = match;
	        } else {
	        	System.out.println("Stress character?");
	            // Should be a stress character. Search through the
	            // stress table backwards.
	            match = 8; // End of stress table. FIXME: Don't hardcode.
	            while( (sign1 != SamTabs.stressInputTable[match]) && (match>0) ) --match;
	            
	            if (match == 0) {
	            	System.out.println("I failed");
	            	return false; // failure
	            }

	            stress[position-1] = match; // Set stress for prior phoneme
	        }
		} //while

	    phonemeindex[position] = Constants.END.getValue();
	    return true;
	}
	
	
	@Override
	public short[] getBuffer() {
		return null;
	}
	@Override
	public int getBufferLength() {
		return 0;
	}
}
