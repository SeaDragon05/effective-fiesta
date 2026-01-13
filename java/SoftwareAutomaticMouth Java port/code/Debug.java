package code;

public class Debug {
	public static void PrintPhonemes(int[] phonemeindex, int[] phonemeLength, int[] stress) {
		int i = 0;
		System.out.print("===========================================\n");

		System.out.print("Internal Phoneme presentation:\n\n");
		System.out.print(" idx    phoneme  length  stress\n");
		System.out.print("------------------------------\n");

		while((phonemeindex[i] != 255) && (i < 255))
		{
			if (phonemeindex[i] < 81)
			{
				System.out.printf(" %3i      %c%c      %3i       %i\n",
				phonemeindex[i],
				SamTabs.signInputTable1[phonemeindex[i]],
				SamTabs.signInputTable2[phonemeindex[i]],
				phonemeLength[i],
				stress[i]
				);
			} else
			{
				System.out.printf(" %3i      ??      %3i       %i\n", phonemeindex[i], phonemeLength[i], stress[i]);
			}
			i++;
		}
		System.out.print("===========================================\n");
		System.out.print("\n");
	}
	
	public static void PrintOutput(
			int[] flag,
			int[] f1,
			int[] f2,
			int[] f3,
			int[] a1,
			int[] a2,
			int[] a3,
			int[] p
			) {
		int i = 0;
		System.out.print("===========================================\n");
		System.out.print("Final data for speech output:\n\n");
		System.out.print(" flags ampl1 freq1 ampl2 freq2 ampl3 freq3 pitch\n");
		System.out.print("------------------------------------------------\n");
		while(i < 255)
		{
			System.out.printf("%5i %5i %5i %5i %5i %5i %5i %5i\n", flag[i], a1[i], f1[i], a2[i], f2[i], a3[i], f3[i], p[i]);
			i++;
		}
		System.out.print("===========================================\n");
	}
}
