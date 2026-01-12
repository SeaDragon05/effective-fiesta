package port;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Phonemes {
	public static Map<String, String> phoneticDictionary;

	public static void PhonemeConverter() {
		phoneticDictionary = new HashMap<>();
		// phoneticDictionary.put(, );

		phoneticDictionary.put(" (A.)", "EH4Y. ");
		phoneticDictionary.put("(A) ", "AH");
		phoneticDictionary.put(" (ARE) ", "AAR");
		phoneticDictionary.put(" (AR)O", "AXR");
		phoneticDictionary.put("(AR)#", "EH4R");
		phoneticDictionary.put(" ^(AS)#", "EY4S");
		phoneticDictionary.put("(A)WA", "AX");
		phoneticDictionary.put("(AW)", "AO5");
		phoneticDictionary.put(" :(ANY)", "EH4NIY");
		phoneticDictionary.put("(A)^+#", "EY5");
		phoneticDictionary.put("#:(ALLY)", "ULIY");
		phoneticDictionary.put(" (AL)#", "UL");
		phoneticDictionary.put("(AGAIN)", "AXGEH4N");
		phoneticDictionary.put("#:(AG)E", "IHJ");
		phoneticDictionary.put("(A)^%", "EY");
		phoneticDictionary.put("(A)^+:#", "AE");
		phoneticDictionary.put(" :(A)^+ ", "EY4");
		phoneticDictionary.put(" (ARR)", "AXR");
		phoneticDictionary.put("(ARR)", "AE4R");
		phoneticDictionary.put(" ^(AR) ", "AA5R");
		phoneticDictionary.put("(AR)", "AA5R");
		phoneticDictionary.put("(AIR)", "EH4R");
		phoneticDictionary.put("(AI)", "EY4");
		phoneticDictionary.put("(AY)", "EY5");
		phoneticDictionary.put("(AU)", "AO4");
		phoneticDictionary.put("#:(AL) ", "UL");
		phoneticDictionary.put("#:(ALS) ", "ULZ");
		phoneticDictionary.put("(ALK)", "AO4K");
		phoneticDictionary.put("(AL)^", "AOL");
		phoneticDictionary.put(" :(ABLE)", "EY4BUL");
		phoneticDictionary.put("(ABLE)", "AXBUL");
		phoneticDictionary.put("(A)VO", "EY4");
		phoneticDictionary.put("(ANG)+", "EY4NJ");
		phoneticDictionary.put("(ATARI)", "AHTAA4RIY");
		phoneticDictionary.put("(A)TOM", "AE");
		phoneticDictionary.put("(A)TTI", "AE");
		phoneticDictionary.put(" (AT) ", "AET");
		phoneticDictionary.put(" (A)T", "AH");
		phoneticDictionary.put("(A)", "AE");

		phoneticDictionary.put(" (B) ", "BIY4");
		phoneticDictionary.put(" (BE)^#", "BIH");
		phoneticDictionary.put("(BEING)", "BIY4IHNX");
		phoneticDictionary.put(" (BOTH) ", "BOW4TH");
		phoneticDictionary.put(" (BUS)#", "BIH4Z");
		phoneticDictionary.put("(BREAK)", "BREY5K");
		phoneticDictionary.put("(BUIL)", "BIH4L");
		phoneticDictionary.put("(B)", "B");

		phoneticDictionary.put(" (C) ", "SIY4");
		phoneticDictionary.put(" (CH)^", "K");
		phoneticDictionary.put("^E(CH)", "K");
		phoneticDictionary.put("(CHA)R#", "KEH5");
		phoneticDictionary.put("(CH)", "CH");
		phoneticDictionary.put(" S(CI)#", "SAY4");
		phoneticDictionary.put("(CI)A", "SH");
		phoneticDictionary.put("(CI)O", "SH");
		phoneticDictionary.put("(CI)EN", "SH");
		phoneticDictionary.put("(CITY)", "SIHTIY");
		phoneticDictionary.put("(C)+", "S");
		phoneticDictionary.put("(CK)", "K");
		phoneticDictionary.put("(COMMODORE)", "KAA4MAHDOHR");
		phoneticDictionary.put("(COM)", "KAHM");
		phoneticDictionary.put("(CUIT)", "KIHT");
		phoneticDictionary.put("(CREA)", "KRIYEY");
		phoneticDictionary.put("(C)", "K");

		phoneticDictionary.put(" (D) ", "DIY4");
		phoneticDictionary.put(" (DR.) ", "DAA4KTER");
		phoneticDictionary.put("#:(DED) ", "DIHD");
		phoneticDictionary.put(".E(D) ", "D");
		phoneticDictionary.put("#:^E(D) ", "T");
		phoneticDictionary.put(" (DE)^#", "DIH");
		phoneticDictionary.put(" (DO) ", "DUW");
		phoneticDictionary.put(" (DOES)", "DAHZ");
		phoneticDictionary.put("(DONE) ", "DAH5N");
		phoneticDictionary.put("(DOING)", "DUW4IHNX");
		phoneticDictionary.put(" (DOW)", "DAW");
		phoneticDictionary.put("#(DU)A", "JUW");
		phoneticDictionary.put("#(DU)^#", "JAX");
		phoneticDictionary.put("(D)", "D");

		phoneticDictionary.put(" (E) ", "IYIY4");
		// phoneticDictionary.put("#:(E) =");
		// phoneticDictionary.put("\":^(E) =");
		phoneticDictionary.put(" :(E) ", "IY");
		phoneticDictionary.put("#(ED) ", "D");
		// phoneticDictionary.put("#:(E)D =");
		phoneticDictionary.put("(EV)ER", "EH4V");
		phoneticDictionary.put("(E)^%", "IY4");
		phoneticDictionary.put("(ERI)#", "IY4RIY");
		phoneticDictionary.put("(ERI)", "EH4RIH");
		phoneticDictionary.put("#:(ER)#", "ER");
		phoneticDictionary.put("(ERROR)", "EH4ROHR");
		phoneticDictionary.put("(ERASE)", "IHREY5S");
		phoneticDictionary.put("(ER)#", "EHR");
		phoneticDictionary.put("(ER)", "ER");
		phoneticDictionary.put(" (EVEN)", "IYVEHN");
		// phoneticDictionary.put("#:(E)W=");
		phoneticDictionary.put("@(EW)", "UW");
		phoneticDictionary.put("(EW)", "YUW");
		phoneticDictionary.put("(E)O", "IY");
		phoneticDictionary.put("#:&(ES) ", "IHZ");
		// phoneticDictionary.put("#:(E)S =");
		phoneticDictionary.put("#:(ELY) ", "LIY");
		phoneticDictionary.put("#:(EMENT)", "MEHNT");
		phoneticDictionary.put("(EFUL)", "FUHL");
		phoneticDictionary.put("(EE)", "IY4");
		phoneticDictionary.put("(EARN)", "ER5N");
		phoneticDictionary.put(" (EAR)^", "ER5");
		phoneticDictionary.put("(EAD)", "EHD");
		phoneticDictionary.put("#:(EA) ", "IYAX");
		phoneticDictionary.put("(EA)SU", "EH5");
		phoneticDictionary.put("(EA)", "IY5");
		phoneticDictionary.put("(EIGH)", "EY4");
		phoneticDictionary.put("(EI)", "IY4");
		phoneticDictionary.put(" (EYE)", "AY4");
		phoneticDictionary.put("(EY)", "IY");
		phoneticDictionary.put("(EU)", "YUW5");
		phoneticDictionary.put("(EQUAL)", "IY4KWUL");
		phoneticDictionary.put("(E)", "EH");

		phoneticDictionary.put(" (F) ", "EH4F");
		phoneticDictionary.put("(FUL)", "FUHL");
		phoneticDictionary.put("(FRIEND)", "FREH5ND");
		phoneticDictionary.put("(FATHER)", "FAA4DHER");
		// phoneticDictionary.put("(F)F=");
		phoneticDictionary.put("(F)", "F");

		phoneticDictionary.put(" (G) ", "JIY4");
		phoneticDictionary.put("(GIV)", "GIH5V");
		phoneticDictionary.put(" (G)I^", "G");
		phoneticDictionary.put("(GE)T", "GEH5");
		phoneticDictionary.put("SU(GGES)", "GJEH4S");
		phoneticDictionary.put("(GG)", "G");
		phoneticDictionary.put(" B#(G)", "G");
		phoneticDictionary.put("(G)+", "J");
		phoneticDictionary.put("(GREAT)", "GREY4T");
		phoneticDictionary.put("(GON)E", "GAO5N");
		// phoneticDictionary.put("#(GH)=");
		phoneticDictionary.put(" (GN)", "N");
		phoneticDictionary.put("(G)", "G");

		phoneticDictionary.put(" (H) ", "EY4CH");
		phoneticDictionary.put(" (HAV)", "/HAE6V");
		phoneticDictionary.put(" (HERE)", "/HIYR");
		phoneticDictionary.put(" (HOUR)", "AW5ER");
		phoneticDictionary.put("(HOW)", "/HAW");
		phoneticDictionary.put("(H)#", "/H");
		// phoneticDictionary.put("(H)=");

		phoneticDictionary.put(" (IN)", "IHN");
		phoneticDictionary.put(" (I) ", "AY4");
		phoneticDictionary.put("(I) ", "AY");
		phoneticDictionary.put("(IN)D", "AY5N");
		phoneticDictionary.put("SEM(I)", "IY");
		phoneticDictionary.put(" ANT(I)", "AY");
		phoneticDictionary.put("(IER)", "IYER");
		phoneticDictionary.put("#:R(IED) ", "IYD");
		phoneticDictionary.put("(IED) ", "AY5D");
		phoneticDictionary.put("(IEN)", "IYEHN");
		phoneticDictionary.put("(IE)T", "AY4EH");
		phoneticDictionary.put("(I\")", "AY5");
		phoneticDictionary.put(" :(I)^%", "AY5");
		phoneticDictionary.put(" :(IE) ", "AY4");
		phoneticDictionary.put("(I)%", "IY");
		phoneticDictionary.put("(IE)", "IY4");
		phoneticDictionary.put(" (IDEA)", "AYDIY5AH");
		phoneticDictionary.put("(I)^+:#", "IH");
		phoneticDictionary.put("(IR)#", "AYR");
		phoneticDictionary.put("(IZ)%", "AYZ");
		phoneticDictionary.put("(IS)%", "AYZ");
		phoneticDictionary.put("I^(I)^#", "IH");
		phoneticDictionary.put("+^(I)^+", "AY");
		phoneticDictionary.put("#:^(I)^+", "IH");
		phoneticDictionary.put("(I)^+", "AY");
		phoneticDictionary.put("(IR)", "ER");
		phoneticDictionary.put("(IGH)", "AY4");
		phoneticDictionary.put("(ILD)", "AY5LD");
		phoneticDictionary.put(" (IGN)", "IHGN");
		phoneticDictionary.put("(IGN) ", "AY4N");
		phoneticDictionary.put("(IGN)^", "AY4N");
		phoneticDictionary.put("(IGN)%", "AY4N");
		phoneticDictionary.put("(ICRO)", "AY4KROH");
		phoneticDictionary.put("(IQUE)", "IY4K");
		phoneticDictionary.put("(I)", "IH");

		phoneticDictionary.put(" (J) ", "JEY4");
		phoneticDictionary.put("(J)", "J");

		phoneticDictionary.put(" (K) ", "KEY4");
		// phoneticDictionary.put(" (K)N=");
		phoneticDictionary.put("(K)", "K");

		phoneticDictionary.put(" (L) ", "EH4L");
		phoneticDictionary.put("(LO)C#", "LOW");
		// phoneticDictionary.put("L(L)=");
		phoneticDictionary.put("#:^(L)%", "UL");
		phoneticDictionary.put("(LEAD)", "LIYD");
		phoneticDictionary.put(" (LAUGH)", "LAE4F");
		phoneticDictionary.put("(L)", "L");

		phoneticDictionary.put(" (M) ", "EH4M");
		phoneticDictionary.put(" (MR.) ", "MIH4STER");
		phoneticDictionary.put(" (MS.)", "MIH5Z");
		phoneticDictionary.put(" (MRS.) ", "MIH4SIXZ");
		phoneticDictionary.put("(MOV)", "MUW4V");
		phoneticDictionary.put("(MACHIN)", "MAHSHIY5N");
		// phoneticDictionary.put("M(M)=");
		phoneticDictionary.put("(M)", "M");

		phoneticDictionary.put(" (N) ", "EH4N");
		phoneticDictionary.put("E(NG)+", "NJ");
		phoneticDictionary.put("(NG)R", "NXG");
		phoneticDictionary.put("(NG)#", "NXG");
		phoneticDictionary.put("(NGL)%", "NXGUL");
		phoneticDictionary.put("(NG)", "NX");
		phoneticDictionary.put("(NK)", "NXK");
		phoneticDictionary.put(" (NOW) ", "NAW4");
		// phoneticDictionary.put("N(N)=");
		phoneticDictionary.put("(NON)E", "NAH4N");
		phoneticDictionary.put("(N)", "N");

		phoneticDictionary.put(" (O) ", "OH4W");
		phoneticDictionary.put("(OF) ", "AHV");
		phoneticDictionary.put(" (OH) ", "OW5");
		phoneticDictionary.put("(OROUGH)", "ER4OW");
		phoneticDictionary.put("#:(OR) ", "ER");
		phoneticDictionary.put("#:(ORS) ", "ERZ");
		phoneticDictionary.put("(OR)", "AOR");
		phoneticDictionary.put(" (ONE)", "WAHN");
		phoneticDictionary.put("#(ONE) ", "WAHN");
		phoneticDictionary.put("(OW)", "OW");
		phoneticDictionary.put(" (OVER)", "OW5VER");
		phoneticDictionary.put("PR(O)V", "UW4");
		phoneticDictionary.put("(OV)", "AH4V");
		phoneticDictionary.put("(O)^%", "OW5");
		phoneticDictionary.put("(O)^EN", "OW");
		phoneticDictionary.put("(O)^I#", "OW5");
		phoneticDictionary.put("(OL)D", "OW4L");
		phoneticDictionary.put("(OUGHT)", "AO5T");
		phoneticDictionary.put("(OUGH)", "AH5F");
		phoneticDictionary.put(" (OU)", "AW");
		phoneticDictionary.put("H(OU)S#", "AW4");
		phoneticDictionary.put("(OUS)", "AXS");
		phoneticDictionary.put("(OUR)", "OHR");
		phoneticDictionary.put("(OULD)", "UH5D");
		phoneticDictionary.put("(OU)^L", "AH5");
		phoneticDictionary.put("(OUP)", "UW5P");
		phoneticDictionary.put("(OU)", "AW");
		phoneticDictionary.put("(OY)", "OY");
		phoneticDictionary.put("(OING)", "OW4IHNX");
		phoneticDictionary.put("(OI)", "OY5");
		phoneticDictionary.put("(OOR)", "OH5R");
		phoneticDictionary.put("(OOK)", "UH5K");
		phoneticDictionary.put("F(OOD)", "UW5D");
		phoneticDictionary.put("L(OOD)", "AH5D");
		phoneticDictionary.put("M(OOD)", "UW5D");
		phoneticDictionary.put("(OOD)", "UH5D");
		phoneticDictionary.put("F(OOT)", "UH5T");
		phoneticDictionary.put("(OO)", "UW5");
		phoneticDictionary.put("(O\")", "OH");
		phoneticDictionary.put("(O)E", "OW");
		phoneticDictionary.put("(O) ", "OW");
		phoneticDictionary.put("(OA)", "OW4");
		phoneticDictionary.put(" (ONLY)", "OW4NLIY");
		phoneticDictionary.put(" (ONCE)", "WAH4NS");
		phoneticDictionary.put("(ON\"T)", "OW4NT");
		phoneticDictionary.put("C(O)N", "AA");
		phoneticDictionary.put("(O)NG", "AO");
		phoneticDictionary.put(" :^(O)N", "AH");
		phoneticDictionary.put("I(ON)", "UN");
		phoneticDictionary.put("#:(ON)", "UN");
		phoneticDictionary.put("#^(ON)", "UN");
		phoneticDictionary.put("(O)ST", "OW");
		phoneticDictionary.put("(OF)^", "AO4F");
		phoneticDictionary.put("(OTHER)", "AH5DHER");
		phoneticDictionary.put("R(O)B", "RAA");
		phoneticDictionary.put("^R(O):#", "OW5");
		phoneticDictionary.put("(OSS) ", "AO5S");
		phoneticDictionary.put("#:^(OM)", "AHM");
		phoneticDictionary.put("(O)", "AA");

		phoneticDictionary.put(" (P) ", "PIY4");
		phoneticDictionary.put("(PH)", "F");
		phoneticDictionary.put("(PEOPL)", "PIY5PUL");
		phoneticDictionary.put("(POW)", "PAW4");
		phoneticDictionary.put("(PUT) ", "PUHT");
		// phoneticDictionary.put("(P)P=");
		// phoneticDictionary.put("(P)S=");
		// phoneticDictionary.put("(P)N=");
		phoneticDictionary.put("(PROF.)", "PROHFEH4SER");
		phoneticDictionary.put("(P)", "P");

		phoneticDictionary.put(" (Q) ", "KYUW4");
		phoneticDictionary.put("(QUAR)", "KWOH5R");
		phoneticDictionary.put("(QU)", "KW");
		phoneticDictionary.put("(Q)", "K");

		phoneticDictionary.put(" (R) ", "AA5R");
		phoneticDictionary.put(" (RE)^#", "RIY");
		// phoneticDictionary.put("(R)R=");
		phoneticDictionary.put("(R)", "R");

		phoneticDictionary.put(" (S) ", "EH4S");
		phoneticDictionary.put("(SH)", "SH");
		phoneticDictionary.put("#(SION)", "ZHUN");
		phoneticDictionary.put("(SOME)", "SAHM");
		phoneticDictionary.put("#(SUR)#", "ZHER");
		phoneticDictionary.put("(SUR)#", "SHER");
		phoneticDictionary.put("#(SU)#", "ZHUW");
		phoneticDictionary.put("#(SSU)#", "SHUW");
		phoneticDictionary.put("#(SED)", "ZD");
		phoneticDictionary.put("#(S)#", "Z");
		phoneticDictionary.put("(SAID)", "SEHD");
		phoneticDictionary.put("^(SION)", "SHUN");
		// phoneticDictionary.put("(S)S=");
		phoneticDictionary.put(".(S) ", "Z");
		phoneticDictionary.put("#:.E(S) ", "Z");
		phoneticDictionary.put("#:^#(S) ", "S");
		phoneticDictionary.put("U(S) ", "S");
		phoneticDictionary.put(" :#(S) ", "Z");
		phoneticDictionary.put("##(S) ", "Z");
		phoneticDictionary.put(" (SCH)", "SK");
		// phoneticDictionary.put("(S)C+=");
		phoneticDictionary.put("#(SM)", "ZUM");
		phoneticDictionary.put("#(SN)\"", "ZUM");
		phoneticDictionary.put("(STLE)", "SUL");
		phoneticDictionary.put("(S)", "S");

		phoneticDictionary.put(" (T) ", "TIY4");
		phoneticDictionary.put(" (THE) #", "DHIY");
		phoneticDictionary.put(" (THE) ", "DHAX");
		phoneticDictionary.put("(TO) ", "TUX");
		phoneticDictionary.put(" (THAT)", "DHAET");
		phoneticDictionary.put(" (THIS) ", "DHIHS");
		phoneticDictionary.put(" (THEY)", "DHEY");
		phoneticDictionary.put(" (THERE)", "DHEHR");
		phoneticDictionary.put("(THER)", "DHER");
		phoneticDictionary.put("(THEIR)", "DHEHR");
		phoneticDictionary.put(" (THAN) ", "DHAEN");
		phoneticDictionary.put(" (THEM) ", "DHAEN");
		phoneticDictionary.put("(THESE) ", "DHIYZ");
		phoneticDictionary.put(" (THEN)", "DHEHN");
		phoneticDictionary.put("(THROUGH)", "THRUW4");
		phoneticDictionary.put("(THOSE)", "DHOHZ");
		phoneticDictionary.put("(THOUGH) ", "DHOW");
		phoneticDictionary.put("(TODAY)", "TUXDEY");
		phoneticDictionary.put("(TOMO)RROW", "TUMAA5");
		phoneticDictionary.put("(TO)TAL", "TOW5");
		phoneticDictionary.put(" (THUS)", "DHAH4S");
		phoneticDictionary.put("(TH)", "TH");
		phoneticDictionary.put("#:(TED)", "TIXD");
		phoneticDictionary.put("S(TI)#N", "CH");
		phoneticDictionary.put("(TI)O", "SH");
		phoneticDictionary.put("(TI)A", "SH");
		phoneticDictionary.put("(TIEN)", "SHUN");
		phoneticDictionary.put("(TUR)#", "CHER");
		phoneticDictionary.put("(TU)A", "CHUW");
		phoneticDictionary.put(" (TWO)", "TUW");
		// phoneticDictionary.put("&(T)EN =");
		phoneticDictionary.put("(T)", "T");

		phoneticDictionary.put(" (U) ", "YUW4");
		phoneticDictionary.put(" (UN)I", "YUWN");
		phoneticDictionary.put(" (UN)", "AHN");
		phoneticDictionary.put(" (UPON)", "AXPAON");
		phoneticDictionary.put("@(UR)#", "UH4R");
		phoneticDictionary.put("(UR)#", "YUH4R");
		phoneticDictionary.put("(UR)", "ER");
		phoneticDictionary.put("(U)^ ", "AH");
		phoneticDictionary.put("(U)^^", "AH5");
		phoneticDictionary.put("(UY)", "AY5");
		// phoneticDictionary.put(" G(U)#=");
		// phoneticDictionary.put("G(U)%=");
		phoneticDictionary.put("G(U)#", "W");
		phoneticDictionary.put("#N(U)", "YUW");
		phoneticDictionary.put("@(U)", "UW");
		phoneticDictionary.put("(U)", "YUW");

		phoneticDictionary.put(" (V) ", "VIY4");
		phoneticDictionary.put("(VIEW)", "VYUW5");
		phoneticDictionary.put("(V)", "V");

		phoneticDictionary.put(" (W) ", "DAH4BULYUW");
		phoneticDictionary.put(" (WERE)", "WER");
		phoneticDictionary.put("(WA)SH", "WAA");
		phoneticDictionary.put("(WA)ST", "WEY");
		phoneticDictionary.put("(WA)S", "WAH");
		phoneticDictionary.put("(WA)T", "WAA");
		phoneticDictionary.put("(WHERE)", "WHEHR");
		phoneticDictionary.put("(WHAT)", "WHAHT");
		phoneticDictionary.put("(WHOL)", "/HOWL");
		phoneticDictionary.put("(WHO)", "/HUW");
		phoneticDictionary.put("(WH)", "WH");
		phoneticDictionary.put("(WAR)#", "WEHR");
		phoneticDictionary.put("(WAR)", "WAOR");
		phoneticDictionary.put("(WOR)^", "WER");
		phoneticDictionary.put("(WR)", "R");
		phoneticDictionary.put("(WOM)A", "WUHM");
		phoneticDictionary.put("(WOM)E", "WIHM");
		phoneticDictionary.put("(WEA)R", "WEH");
		phoneticDictionary.put("(WANT)", "WAA5NT");
		phoneticDictionary.put("ANS(WER)", "ER");
		phoneticDictionary.put("(W)", "W");

		phoneticDictionary.put(" (X) ", "EH4KR");
		phoneticDictionary.put(" (X)", "Z");
		phoneticDictionary.put("(X)", "KS");

		phoneticDictionary.put(" (Y) ", "WAY4");
		phoneticDictionary.put("(YOUNG)", "YAHNX");
		phoneticDictionary.put(" (YOUR)", "YOHR");
		phoneticDictionary.put(" (YOU)", "YUW");
		phoneticDictionary.put(" (YES)", "YEHS");
		phoneticDictionary.put(" (Y)", "Y");
		phoneticDictionary.put("F(Y)", "AY");
		phoneticDictionary.put("PS(YCH)", "AYK");
		phoneticDictionary.put("#:^(Y)", "IY");
		phoneticDictionary.put("#:^(Y)I", "IY");
		phoneticDictionary.put(" :(Y) ", "AY");
		phoneticDictionary.put(" :(Y)#", "AY");
		phoneticDictionary.put(" :(Y)^+:#", "IH");
		phoneticDictionary.put(" :(Y)^#", "AY");
		phoneticDictionary.put("(Y)", "IH");

		phoneticDictionary.put(" (Z) ", "ZIY4");
		phoneticDictionary.put("(Z)", "Z");

	}

	public static String textToPhonemes(String input) {
		input = input.toUpperCase();
		if (phoneticDictionary == null)
			PhonemeConverter();
		List<String> words = new ArrayList<String>();
		{
			String word = "";
			for (int i = 0; i < input.length(); i++) {
				if (input.charAt(i) != ' ') {
					word += input.charAt(i);
				} else {
					words.add(word);
					word = "";
				}
			}
		}
		String result = "";
		for (int i = 0; i < words.size(); i++) {
			String word = words.get(i);
			String phonetic;
			if ((phonetic = Search(word)) != word) {
				//found a word
				result += phonetic + " ";
			}
		}

		return result;
	}

	private static String Search(String word) {
		for (String entry : phoneticDictionary.keySet()) {
			if (entry.contains(word)) {
				return entry;
			}
		}
		return word;
	}

}
