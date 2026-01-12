package port;

public class Sam {
	public static final int pR = 23, pD = 57, pT = 69, BREAK = 254, END = 255;//

	static int[] input = new int[256];
	// standard sam sound
	public static int speed = 72;
	public static int pitch = 64;
	public static int mouth = 128;
	public static int throat = 128;
	public static boolean singmode = false;

	static int[] stress = new int[256]; // numbers from 0 to 8
	static int[] phonemeLength = new int[256]; // tab40160
	static int[] phonemeindex = new int[256];

	static int[] phonemeIndexOutput = new int[60]; // tab47296
	static int[] stressOutput = new int[60]; // tab47365
	static int[] phonemeLengthOutput = new int[60]; // tab47416

	// contains the final soundbuffer
	static int bufferpos = 0;

	public static int[] buffer = null;
	
	public static double outputLength = 10;

	public static void SetInput(int[] _input) {
		for (int i = 0; i < ((_input.length > 254) ? 254 : _input.length); i++) {
			input[i] = _input[i];
		}
		input[((_input.length > 254) ? 254 : _input.length)] = 0;
	}

	public static int[] GetBuffer() {
		return buffer;
	}

	public static int GetBufferLength() {
		return bufferpos;
	}

	public static void init() {
		Render.SetMouthThroat(mouth, throat);

		bufferpos = 0;

		// sample @ 22050Hz for 10 seconds
		buffer = new int[(int) (Main.audioQuality * outputLength)];
		// Changed the above line for advanced singing

		for (int i = 0; i < 256; i++) {
			stress[i] = 0;
			phonemeLength[i] = 0;
		}

		for (int i = 0; i < 60; i++) {
			phonemeIndexOutput[i] = 0;
			stressOutput[i] = 0;
			phonemeLengthOutput[i] = 0;
		}
		phonemeindex[255] = END;
	}

	public static int SAMMain() {
		int X = 0;// unknown variable
		init();
		phonemeindex[255] = 32;

		if (Parser1() == 0) return 0;
		if (Main.debug)
			Debug.printPhonemes(phonemeindex, phonemeLength, stress);
		Parser2();
		CopyStress();
		SetPhonemeLength();
		AdjustLengths();
		Code41240();
		do {
			if (phonemeindex[X] > 80) {
				phonemeindex[X] = END;
				break; // error: delete all behind it
			}
		} while (++X != 0);
		InsertBreath();
		if (Main.debug)
			Debug.printPhonemes(phonemeindex, phonemeLength, stress);

		PrepareOutput();
		return 1;
	}

	private static void PrepareOutput() {
		int srcpos = 0; // Position in source
		int destpos = 0; // Position in output
		while (true) {
			int A = phonemeindex[srcpos];
			phonemeIndexOutput[destpos] = A;
			if (A == END) {
				//System.out.println("Ending...");
				Render.RenderIt();
				return;
			} else if (A == BREAK) {
				phonemeIndexOutput[destpos] = END;
				Render.RenderIt();
				destpos = 0;
			} else if (A == 0) {
			} else {
				phonemeLengthOutput[destpos] = phonemeLength[srcpos];
				stressOutput[destpos] = stress[srcpos];
				++destpos;
			} 
			++srcpos;
		}
	}

	private static void InsertBreath() {
		int mem54 = 255;
		int len = 0;
		int index; // variable Y

		int pos = 0;

		while ((index = phonemeindex[pos % 256]) != END) {
			pos%=256;
			len += phonemeLength[pos];
			if (len < 232) {
				if (index == BREAK) {
				} else if ((SamData.flags[index] & SamData.Flags.FLAG_PUNCT.getValue()) == 0) {
					if (index == 0) {
						mem54 = pos;
					}
				} else {
					len = 0;
					Insert(++pos, BREAK, 0, 0);
				}
			} else {
				pos = mem54;
				phonemeindex[pos] = 31; // 'Q*' glottal stop
				phonemeLength[pos] = 4;
				stress[pos] = 0;

				len = 0;
				Insert(++pos, BREAK, 0, 0);
			}
			++pos;
		}
	}

	// Iterates through the phoneme buffer, copying the stress value from
	// the following phoneme under the following circumstance:

//	     1. The current phoneme is voiced, excluding plosives and fricatives
//	     2. The following phoneme is voiced, excluding plosives and fricatives, and
//	     3. The following phoneme is stressed
	//
	// In those cases, the stress value+1 from the following phoneme is copied.
	//
	// For example, the word LOITER is represented as LOY5TER, with as stress
	// of 5 on the dipthong OY. This routine will copy the stress value of 6 (5+1)
	// to the L that precedes it.

	private static void CopyStress() {
		// loop thought all the phonemes to be output
		int pos = 0; // mem66
		int Y;
		while ((Y = phonemeindex[pos]) != END) {
			// if CONSONANT_FLAG set, skip - only vowels get stress
			if ((SamData.flags[Y] & 64) != 0) {
				Y = phonemeindex[pos + 1];

				// if the following phoneme is the end, or a vowel, skip
				if (Y != END && (SamData.flags[Y] & 128) != 0) {
					// get the stress value at the next position
					Y = stress[pos + 1];
					if (Y != 0 && (Y & 128) == 0) {
						// if next phoneme is stressed, and a VOWEL OR ER
						// copy stress from next phoneme to this one
						stress[pos] = Y + 1;
					}
				}
			}

			++pos;
		}
	}

	private static void Insert(int position, int newIndex, int newLength, int newStress) {
		position%=256;
		int i;
		if (Main.debug) System.out.println("DEBUG: inserting something!");
		for (i = 253; i >= position; i--) // ML : always keep last safe-guarding 255
		{
			phonemeindex[i + 1] = phonemeindex[i];
			phonemeLength[i + 1] = phonemeLength[i];
			stress[i + 1] = stress[i];
		}

		phonemeindex[position] = newIndex % 256;
		phonemeLength[position] = newLength % 256;
		stress[position] = newStress % 256;
	}

	private static int full_match(int sign1, int sign2) {
		int Y = 0;
		do {
			// GET FIRST CHARACTER AT POSITION Y IN signInputTable
			// --> should change name to PhonemeNameTable1
			int A = SamData.signInputTable1[Y];

			if (A == sign1) {
				A = SamData.signInputTable2[Y];
				// NOT A SPECIAL AND MATCHES SECOND CHARACTER?
				if ((A != '*') && (A == sign2))
					return Y;
			}
		} while (++Y != 81);
		return -1;
	}

	private static int wild_match(int sign1) {
		int Y = 0;
		do {
			if (SamData.signInputTable2[Y] == '*') {
				if (SamData.signInputTable1[Y] == sign1)
					return Y;
			}
		} while (++Y != 81);
		return -1;
	}

	// The input[] buffer contains a string of phonemes and stress markers along
	// the lines of:
	//
//	     DHAX KAET IHZ AH5GLIY. <0x9B>
	//
	// The byte 0x9B marks the end of the buffer. Some phonemes are 2 bytes
	// long, such as "DH" and "AX". Others are 1 byte long, such as "T" and "Z".
	// There are also stress markers, such as "5" and ".".
	//
	// The first character of the phonemes are stored in the table
	// signInputTable1[].
	// The second character of the phonemes are stored in the table
	// signInputTable2[].
	// The stress characters are arranged in low to high stress order in
	// stressInputTable[].
	//
	// The following process is used to parse the input[] buffer:
	//
	// Repeat until the <0x9B> character is reached:
	//
//	        First, a search is made for a 2 character match for phonemes that do not
//	        end with the '*' (wildcard) character. On a match, the index of the phoneme 
//	        is added to phonemeIndex[] and the buffer position is advanced 2 bytes.
	//
//	        If this fails, a search is made for a 1 character match against all
//	        phoneme names ending with a '*' (wildcard). If this succeeds, the 
//	        phoneme is added to phonemeIndex[] and the buffer position is advanced
//	        1 byte.
	//
//	        If this fails, search for a 1 character match in the stressInputTable[].
//	        If this succeeds, the stress value is placed in the last stress[] table
//	        at the same index of the last added phoneme, and the buffer position is
//	        advanced by 1 byte.
	//
//	        If this fails, return a 0.
	//
	// On success:
	//
//	    1. phonemeIndex[] will contain the index of all the phonemes.
//	    2. The last index in phonemeIndex[] will be 255.
//	    3. stress[] will contain the stress value for each phoneme

	// input[] holds the string of phonemes, each two bytes wide
	// signInputTable1[] holds the first character of each phoneme
	// signInputTable2[] holds te second character of each phoneme
	// phonemeIndex[] holds the indexes of the phonemes after parsing input[]
	//
	// The parser scans through the input[], finding the names of the phonemes
	// by searching signInputTable1[] and signInputTable2[]. On a match, it
	// copies the index of the phoneme into the phonemeIndexTable[].
	//
	// The character <0x9B> marks the end of text in input[]. When it is reached,
	// the index 255 is placed at the end of the phonemeIndexTable[], and the
	// function returns with a 1 indicating success.
	public static int Parser1() {
		int sign1;
		int position = 0;
		int srcpos = 0;

		// memset(stress, 0, 256); // Clear the stress table.

		stress = new int[256];

		while ((sign1 = input[srcpos]) != 155) { // 155 (\233) is end of line marker
			int match;
			int sign2 = input[++srcpos];
			if ((match = full_match(sign1, sign2)) != -1) {
				// Matched both characters (no wildcards)
				phonemeindex[position++] = match;
				++srcpos; // Skip the second character of the input as we've matched it
			} else if ((match = wild_match(sign1)) != -1) {
				// Matched just the first character (with second character matching '*'
				phonemeindex[position++] = match;
			} else {
				// Should be a stress character. Search through the
				// stress table backwards.
				match = 8; // End of stress table. FIXME: Don't hardcode.
				while ((sign1 != SamData.stressInputTable[match]) && (match > 0))
					match -= 1;

				if (match == 0)
					return 0; // failure

				stress[position - 1] = match; // Set stress for prior phoneme
			}
		} // while

		phonemeindex[position] = END;
		return 1;
	}

	// change phonemelength depedendent on stress
	private static void SetPhonemeLength() {
		int position = 0;
		while (phonemeindex[position] != 255) {
			int A = stress[position];
			if ((A == 0) || ((A & 128) != 0)) {
				phonemeLength[position] = SamData.phonemeLengthTable[phonemeindex[position]];
			} else {
				phonemeLength[position] = SamData.phonemeStressedLengthTable[phonemeindex[position]];
			}
			position++;
		}
	}

	private static void Code41240() {
		int pos = 0;

		while (phonemeindex[pos] != END) {
			int index = phonemeindex[pos];

			if ((SamData.flags[index] & SamData.Flags.FLAG_STOPCONS.getValue()) != 0) {
				if ((SamData.flags[index] & SamData.Flags.FLAG_PLOSIVE.getValue()) != 0) {
					int A;
					int X = pos;
					while (phonemeindex[++X] == 0)
						; /* Skip pause */
					A = phonemeindex[X];
					if (A != END) {
						if ((SamData.flags[A] & 8) != 0 || (A == 36) || (A == 37)) {
							++pos;
							continue;
						} // '/H' '/X'
					}

				}
				Insert(pos + 1, index + 1, SamData.phonemeLengthTable[index + 1], stress[pos]);
				Insert(pos + 2, index + 2, SamData.phonemeLengthTable[index + 2], stress[pos]);
				pos += 2;
			}
			++pos;
		}
	}

	private static void ChangeRule(int position, int mem60, String descr) {
		if (Main.debug)
			System.out.printf("RULE: %s\n", descr);
		phonemeindex[position] = 13; // rule;
		Insert(position + 1, mem60, 0, stress[position]);
	}

	private static void drule(String str) {
		if (Main.debug)
			System.out.printf("RULE: %s\n", str);
	}

	private static void drule_pre(String descr, int X) {
		drule(descr);
		if (Main.debug) {
			System.out.printf("PRE\n");
			System.out.printf("phoneme %d (%c%c) length %d\n", X, SamData.signInputTable1[phonemeindex[X]],
					SamData.signInputTable2[phonemeindex[X]], phonemeLength[X]);
		}
	}

	private static void drule_post(int X) {
		if (Main.debug) {
			System.out.printf("POST\n");
			System.out.printf("phoneme %d (%c%c) length %d\n", X, SamData.signInputTable1[phonemeindex[X]],
					SamData.signInputTable2[phonemeindex[X]], phonemeLength[X]);
		}
	}

	// Rewrites the phonemes using the following rules:
	//
//	       <DIPTHONG ENDING WITH WX> -> <DIPTHONG ENDING WITH WX> WX
//	       <DIPTHONG NOT ENDING WITH WX> -> <DIPTHONG NOT ENDING WITH WX> YX
//	       UL -> AX L
//	       UM -> AX M
//	       <STRESSED VOWEL> <SILENCE> <STRESSED VOWEL> -> <STRESSED VOWEL> <SILENCE> Q <VOWEL>
//	       T R -> CH R
//	       D R -> J R
//	       <VOWEL> R -> <VOWEL> RX
//	       <VOWEL> L -> <VOWEL> LX
//	       G S -> G Z
//	       K <VOWEL OR DIPTHONG NOT ENDING WITH IY> -> KX <VOWEL OR DIPTHONG NOT ENDING WITH IY>
//	       G <VOWEL OR DIPTHONG NOT ENDING WITH IY> -> GX <VOWEL OR DIPTHONG NOT ENDING WITH IY>
//	       S P -> S B
//	       S T -> S D
//	       S K -> S G
//	       S KX -> S GX
//	       <ALVEOLAR> UW -> <ALVEOLAR> UX
//	       CH -> CH CH' (CH requires two phonemes to represent it)
//	       J -> J J' (J requires two phonemes to represent it)
//	       <UNSTRESSED VOWEL> T <PAUSE> -> <UNSTRESSED VOWEL> DX <PAUSE>
//	       <UNSTRESSED VOWEL> D <PAUSE>  -> <UNSTRESSED VOWEL> DX <PAUSE>

	private static void rule_alveolar_uw(int X) {
		// ALVEOLAR flag set?
		if ((SamData.flags[phonemeindex[X - 1]] & SamData.Flags.FLAG_ALVEOLAR.getValue()) != 0) {
			drule("<ALVEOLAR> UW -> <ALVEOLAR> UX");
			phonemeindex[X] = 16;
		}
	}

	private static void rule_ch(int X) {
		drule("CH -> CH CH+1");
		Insert(X + 1, 43, 0, stress[X]);
	}

	private static void rule_j(int X) {
		drule("J -> J J+1");
		Insert(X + 1, 45, 0, stress[X]);
	}

	private static void rule_g(int pos) {
		// G <VOWEL OR DIPTHONG NOT ENDING WITH IY> -> GX <VOWEL OR DIPTHONG NOT ENDING
		// WITH IY>
		// Example: GO

		int index = phonemeindex[pos + 1];

		// If dipthong ending with YX, move continue processing next phoneme
		if ((index != 255) && ((SamData.flags[index] & SamData.Flags.FLAG_DIP_YX.getValue()) == 0)) {
			// replace G with GX and continue processing next phoneme
			drule("G <VOWEL OR DIPTHONG NOT ENDING WITH IY> -> GX <VOWEL OR DIPTHONG NOT ENDING WITH IY>");
			phonemeindex[pos] = 63; // 'GX'
		}
	}

	private static void change(int pos, int val, String rule) {
		drule(rule);
		phonemeindex[pos] = val;
	}

	private static void rule_dipthong(int p, int pf, int pos) {
		// <DIPTHONG ENDING WITH WX> -> <DIPTHONG ENDING WITH WX> WX
		// <DIPTHONG NOT ENDING WITH WX> -> <DIPTHONG NOT ENDING WITH WX> YX
		// Example: OIL, COW

		// If ends with IY, use YX, else use WX
		int A = ((pf & SamData.Flags.FLAG_DIP_YX.getValue()) != 0) ? 21 : 20; // 'WX' = 20 'YX' = 21

		// Insert at WX or YX following, copying the stress
		if (A == 20) {
			drule("insert WX following dipthong NOT ending in IY sound");
		} else if (A == 21) {
			drule("insert YX following dipthong ending in IY sound");
		}
		Insert(pos + 1, A, 0, stress[pos]);

		if (p == 53)
			rule_alveolar_uw(pos); // Example: NEW, DEW, SUE, ZOO, THOO, TOO
		else if (p == 42)
			rule_ch(pos); // Example: CHEW
		else if (p == 44)
			rule_j(pos); // Example: JAY
	}

	private static void Parser2() {
		int pos = 0;
		int p;

		if (Main.debug)
			System.out.println("Parser2");

		while ((p = phonemeindex[pos]) != END) {
			int pf, prior;

			if (Main.debug) {
				System.out.printf("%d: %c%c\n", pos, SamData.signInputTable1[p], SamData.signInputTable2[p]);
			}

			if (p == 0) {
				++pos;
				continue;
			}

			pf = SamData.flags[p];

			// Java does not like it when you call for -1
			// C code said that when -1 is called, a zero is born
			try {
				prior = phonemeindex[pos - 1];
			} catch (Exception e) {
				prior = 0;
			}

			if ((pf & SamData.Flags.FLAG_DIPTHONG.getValue()) != 0)
				rule_dipthong(p, pf, pos);
			else if (p == 78)
				ChangeRule(pos, 24, "UL -> AX L");
			else if (p == 79)
				ChangeRule(pos, 27, "UM -> AX M");
			else if (p == 80)
				ChangeRule(pos, 28, "UN -> AX N");
			else if ((pf & SamData.Flags.FLAG_VOWEL.getValue()) != 0 && stress[pos] != 0) {
				// RULE:
				// <STRESSED VOWEL> <SILENCE> <STRESSED VOWEL> -> <STRESSED VOWEL> <SILENCE> Q
				// <VOWEL>
				// EXAMPLE: AWAY EIGHT
				if (phonemeindex[pos + 2] == 0) { // If following phoneme is a pause, get next
					p = phonemeindex[pos + 2];
					if (p != END && (SamData.flags[p] & SamData.Flags.FLAG_VOWEL.getValue()) != 0 && stress[pos + 2] != 0) {
						drule("Insert glottal stop between two stressed vowels with space between them");
						Insert(pos + 2, 31, 0, 0);
					}
				}
			} else if (p == pR) {
				if (prior == pT)
					change(pos - 1, 42, "T R -> CH R");
				else if (prior == pD)
					change(pos - 1, 44, "D R -> J R");
				else if ((SamData.flags[prior] & SamData.Flags.FLAG_VOWEL.getValue()) != 0)
					change(pos, 18, "<VOWEL> R -> <VOWEL> RX");
			} else if (p == 24 && (SamData.flags[prior] & SamData.Flags.FLAG_VOWEL.getValue()) != 0) {
				change(pos, 19, "<VOWEL> L -> <VOWEL> LX");
			} else if (prior == 60 && p == 32) {
				change(pos, 38, "G S -> G Z");
			} else if (p == 60) {
				rule_g(pos);
			} else {
				if (p == 72) {
					int Y = phonemeindex[pos + 1];
					if ((SamData.flags[Y % 81] & SamData.Flags.FLAG_DIP_YX.getValue()) == 0 || Y == END) {
						change(pos, 75,
								"K <VOWEL OR DIPTHONG NOT ENDING WITH IY> -> KX <VOWEL OR DIPTHONG NOT ENDING WITH IY>");
						p = 75;
						pf = SamData.flags[75];
					} 
				}
			}

			if ((SamData.flags[p] & SamData.Flags.FLAG_PLOSIVE.getValue()) != 0 && (prior == 32)) {
				if (Main.debug) {
					System.out.printf("RULE: S* %d%d -> S* %d%d\n", SamData.signInputTable1[p],
							SamData.signInputTable2[p], SamData.signInputTable1[p - 12],
							SamData.signInputTable2[p - 12]);
				}
				phonemeindex[pos] = p - 12;
			} else if ((pf & SamData.Flags.FLAG_PLOSIVE.getValue()) == 0) {
				p = phonemeindex[pos];
				if (p == 53) {
					rule_alveolar_uw(pos);
				} else if (p == 42) {
					rule_ch(pos);
				} else if (p == 44) {
					rule_j(pos);
				}

			}

			if (p == 69 || p == 57) { // 'T', 'D'
				// RULE: Soften T following vowel
				// NOTE: This rule fails for cases such as "ODD"
				// <UNSTRESSED VOWEL> T <PAUSE> -> <UNSTRESSED VOWEL> DX <PAUSE>
				// <UNSTRESSED VOWEL> D <PAUSE> -> <UNSTRESSED VOWEL> DX <PAUSE>
				// Example: PARTY, TARDY
				if ((SamData.flags[phonemeindex[pos - 1]] & SamData.Flags.FLAG_VOWEL.getValue()) != 0) {
					p = phonemeindex[pos + 1];
					if (p == 0) {
						p = phonemeindex[pos + 2];
					}
					if ((SamData.flags[p % 81] & SamData.Flags.FLAG_VOWEL.getValue()) != 0 
							&& stress[pos + 1] == 0) {
						change(pos, 30, "Soften T or D following vowel or ER and preceding a pause -> DX");
					}
				}
			}
			pos++;
		}
	}

	// Applies various rules that adjust the lengths of phonemes
	//
//	         Lengthen <FRICATIVE> or <VOICED> between <VOWEL> and <PUNCTUATION> by 1.5
//	         <VOWEL> <RX | LX> <CONSONANT> - decrease <VOWEL> length by 1
//	         <VOWEL> <UNVOICED PLOSIVE> - decrease vowel by 1/8th
//	         <VOWEL> <UNVOICED CONSONANT> - increase vowel by 1/2 + 1
//	         <NASAL> <STOP CONSONANT> - set nasal = 5, consonant = 6
//	         <VOICED STOP CONSONANT> {optional silence} <STOP CONSONANT> - shorten both to 1/2 + 1
//	         <LIQUID CONSONANT> <DIPTHONG> - decrease by 2
	//
	private static void AdjustLengths() {
		// LENGTHEN VOWELS PRECEDING PUNCTUATION
		//
		// Search for punctuation. If found, back up to the first vowel, then
		// process all phonemes between there and up to (but not including) the
		// punctuation.
		// If any phoneme is found that is a either a fricative or voiced, the duration
		// is
		// increased by (length * 1.5) + 1

		// loop index
		{
			int X = 0;
			int index;

			while ((index = phonemeindex[X]) != END) {
				int loopIndex;

				// not punctuation?
				if ((SamData.flags[index] & SamData.Flags.FLAG_PUNCT.getValue()) == 0) {
					++X;
					continue;
				}

				loopIndex = X;

				while (--X != 0 && (SamData.flags[phonemeindex[X]] & SamData.Flags.FLAG_VOWEL.getValue()) == 0)
					; // back up while not a vowel
				if (X == 0)
					break;

				do {
					// test for vowel
					index = phonemeindex[X];

					// test for fricative/unvoiced or not voiced
					if ((SamData.flags[index] & SamData.Flags.FLAG_FRICATIVE.getValue()) == 0
							|| (SamData.flags[index] & SamData.Flags.FLAG_VOICED.getValue()) != 0) { // nochmal \FCberpr\FCfen
						int A = phonemeLength[X];
						// change phoneme length to (length * 1.5) + 1
						drule_pre("Lengthen <FRICATIVE> or <VOICED> between <VOWEL> and <PUNCTUATION> by 1.5", X);
						phonemeLength[X] = (A >> 1) + A + 1;
						drule_post(X);
					}
				} while (++X != loopIndex);
				X++;
			} // while
		}
		// Similar to the above routine, but shorten vowels under some circumstances

		// Loop through all phonemes
		int loopIndex = 0;
		int index;

		while ((index = phonemeindex[loopIndex]) != END) {
			int X = loopIndex;

			if ((SamData.flags[index] & SamData.Flags.FLAG_VOWEL.getValue()) != 0) {
				index = phonemeindex[loopIndex + 1];
				if ((SamData.flags[index % 81] & SamData.Flags.FLAG_CONSONANT.getValue()) == 0) {
					if ((index == 18) || (index == 19)) { // 'RX', 'LX'
						index = phonemeindex[loopIndex + 2];
						if ((SamData.flags[index] & SamData.Flags.FLAG_CONSONANT.getValue()) != 0) {
							drule_pre("<VOWEL> <RX | LX> <CONSONANT> - decrease length of vowel by 1\n", loopIndex);
							phonemeLength[loopIndex]--;
							drule_post(loopIndex);
						}
					}
				} else { // Got here if not <VOWEL>
					int flag = (index == END) ? 65 : SamData.flags[index]; // 65 if end marker

					if ((flag & SamData.Flags.FLAG_VOICED.getValue()) == 0) { // Unvoiced
						// *, .*, ?*, ,*, -*, DX, S*, SH, F*, TH, /H, /X, CH, P*, T*, K*, KX
						if ((flag & SamData.Flags.FLAG_PLOSIVE.getValue()) != 0) { // unvoiced plosive
							// RULE: <VOWEL> <UNVOICED PLOSIVE>
							// <VOWEL> <P*, T*, K*, KX>
							drule_pre("<VOWEL> <UNVOICED PLOSIVE> - decrease vowel by 1/8th", loopIndex);
							phonemeLength[loopIndex] -= (phonemeLength[loopIndex] >> 3);
							drule_post(loopIndex);
						}
					} else {
						int A;
						drule_pre("<VOWEL> <VOICED CONSONANT> - increase vowel by 1/2 + 1\n", X - 1);
						// decrease length
						A = phonemeLength[loopIndex];
						phonemeLength[loopIndex] = (A >> 2) + A + 1; // 5/4*A + 1
						drule_post(loopIndex);
					}
				}
			} else if ((SamData.flags[index] & SamData.Flags.FLAG_NASAL.getValue()) != 0) { // nasal?
				// RULE: <NASAL> <STOP CONSONANT>
				// Set punctuation length to 6
				// Set stop consonant length to 5
				index = phonemeindex[++X];
				if (index != END && (SamData.flags[index] & SamData.Flags.FLAG_STOPCONS.getValue()) != 0) {
					drule("<NASAL> <STOP CONSONANT> - set nasal = 5, consonant = 6");
					phonemeLength[X] = 6; // set stop consonant length to 6
					phonemeLength[X - 1] = 5; // set nasal length to 5
				}
			} else if ((SamData.flags[index] & SamData.Flags.FLAG_STOPCONS.getValue()) != 0) { // (voiced) stop consonant?
				// RULE: <VOICED STOP CONSONANT> {optional silence} <STOP CONSONANT>
				// Shorten both to (length/2 + 1)

				// move past silence
				while ((index = phonemeindex[++X]) == 0);

				if (index != END && (SamData.flags[index] & SamData.Flags.FLAG_STOPCONS.getValue()) != 0) {
					// FIXME, this looks wrong?
					// RULE: <UNVOICED STOP CONSONANT> {optional silence} <STOP CONSONANT>
					drule("<UNVOICED STOP CONSONANT> {optional silence} <STOP CONSONANT> - shorten both to 1/2 + 1");
					phonemeLength[X] = (phonemeLength[X] >> 1) + 1;
					phonemeLength[loopIndex] = (phonemeLength[loopIndex] >> 1) + 1;
					X = loopIndex;
				}
			} else if ((SamData.flags[index] & SamData.Flags.FLAG_LIQUIC.getValue()) != 0) { // liquic consonant?
				// RULE: <VOICED NON-VOWEL> <DIPTHONG>
				// Decrease <DIPTHONG> by 2
				try {
					index = phonemeindex[X - 1]; // prior phoneme;
				} catch (Exception e) {
					index = 0;
				}
				// FIXME: The debug code here breaks the rule.
				// prior phoneme a stop consonant>
				if ((SamData.flags[index] & SamData.Flags.FLAG_STOPCONS.getValue()) != 0)
					drule_pre("<LIQUID CONSONANT> <DIPTHONG> - decrease by 2", X);

				phonemeLength[X] -= 2; // 20ms
				drule_post(X);
			}

			++loopIndex;
		}
	}
}
