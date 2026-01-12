package port;

public class Debug {

	public static void printPhonemes(int[] phonemeindex, int[] phonemeLength, int[] stress) {
		int i = 0;
		System.out.printf("===========================================\n");
		System.out.printf("Internal Phoneme presentation:\n\n");
		System.out.printf(" #    idx    phoneme  length  stress\n");
		System.out.printf("------------------------------------\n");
		while ((phonemeindex[i] != 255) && (i < 255)) {
			if (phonemeindex[i] < 81) {
				System.out.printf("%2d   %3d      %c%c      %3d       %d%n", i, phonemeindex[i],
						(char) SamData.signInputTable1[phonemeindex[i]],
						(char) SamData.signInputTable2[phonemeindex[i]], phonemeLength[i], stress[i]);
			} else {
				System.out.printf("%2d   %3d      ??      %3d       %d\n", i,  phonemeindex[i], phonemeLength[i], stress[i]);
			}
			i++;
		}
		System.out.printf("===========================================\n");
		System.out.printf("\n");
	}

	public static void PrintRule(int offset) {
		int A = 0;
		System.out.printf("Applying rule: ");
		do {
			A = 0;//Reciter.getRuleByte(offset, i);
			if ((A & 127) == '=') {
				System.out.printf(" -> ");
			} else {
				System.out.printf("%c", A & 127);
			}
		} while ((A & 128) == 0);
		System.out.printf("\n");
	}

	public static void printOutput(int[] flag, int[] f1, int[] f2, int[] f3, int[] a1, int[] a2, int[] a3, int[] p) {
		int i = 0;
		System.out.printf("===========================================\n");
		System.out.printf("Final data for speech output:\n\n");
		System.out.printf(" flags ampl1 freq1 ampl2 freq2 ampl3 freq3 pitch\n");
		System.out.printf("------------------------------------------------\n");
		while (i < 255) {
			System.out.printf("%5d %5d %5d %5d %5d %5d %5d %5d\n", flag[i], a1[i], f1[i], a2[i], f2[i], a3[i], f3[i],
					p[i]);
			i++;
		}
		System.out.printf("===========================================\n");

	}
}