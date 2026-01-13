package code;

import java.nio.charset.Charset;

public class SamTabs {

	public static int[] convert(int[] list) {
		String encoding = "US-ASCII";
		int[] result = new int[list.length];
		for (int i = 0; i < list.length; i++) {
            String str = Integer.toString(list[i]);
            byte[] bytes = str.getBytes(Charset.forName(encoding));
            if (bytes.length > 0) {
                result[i] = bytes[0];
            }
		}
		return result;
	}
	
	public static int convert(int item) {
		String encoding = "US-ASCII";
        String str = Integer.toString(item);
        byte[] bytes = str.getBytes(Charset.forName(encoding));
        if (bytes.length > 0) {
            return bytes[0];
        }
        return -1;
	}
	
	//tab40672
	private static final int[] _stressInputTable =
	{
		'*', '1', '2', '3', '4', '5', '6', '7', '8'
	};
	
	public static final int[] stressInputTable = convert(_stressInputTable);

	//tab40682
	private static final int[] _signInputTable1 = {
		' ', '.', '?', ',', '-', 'I', 'I', 'E',
		'A', 'A', 'A', 'A', 'U', 'A', 'I', 'E',
		'U', 'O', 'R', 'L', 'W', 'Y', 'W', 'R',
		'L', 'W', 'Y', 'M', 'N', 'N', 'D', 'Q',
		'S', 'S', 'F', 'T', '/', '/', 'Z', 'Z',
		'V', 'D', 'C', '*', 'J', '*', '*', '*',
		'E', 'A', 'O', 'A', 'O', 'U', 'B', '*',
		'*', 'D', '*', '*', 'G', '*', '*', 'G',
		'*', '*', 'P', '*', '*', 'T', '*', '*',
		'K', '*', '*', 'K', '*', '*', 'U', 'U',
		'U'
	};

	public static final int[] signInputTable1 = convert(_signInputTable1);
	
	//tab40763
	private static final int[] _signInputTable2 =
	{
		'*', '*', '*', '*', '*', 'Y', 'H', 'H',
		'E', 'A', 'H', 'O', 'H', 'X', 'X', 'R',
		'X', 'H', 'X', 'X', 'X', 'X', 'H', '*',
		'*', '*', '*', '*', '*', 'X', 'X', '*',
		'*', 'H', '*', 'H', 'H', 'X', '*', 'H',
		'*', 'H', 'H', '*', '*', '*', '*', '*',
		'Y', 'Y', 'Y', 'W', 'W', 'W', '*', '*',
		'*', '*', '*', '*', '*', '*', '*', 'X',
		'*', '*', '*', '*', '*', '*', '*', '*',
		'*', '*', '*', 'X', '*', '*', 'L', 'M',
		'N'
	};

	public static final int[] signInputTable2 = convert(_signInputTable2);
	
	public static final int[] flags = {
		0x8000 , 0xC100 , 0xC100 , 0xC100 , 0xC100 , 0x00A4 , 0x00A4 , 0x00A4 ,
		0x00A4 , 0x00A4 , 0x00A4 , 0x0084 , 0x0084 , 0x00A4 , 0x00A4 , 0x0084 ,
		0x0084 , 0x0084 , 0x0084 , 0x0084 , 0x0084 , 0x0084 , 0x0044 , 0x1044 ,
		0x1044 , 0x1044 , 0x1044 , 0x084C , 0x0C4C , 0x084C , 0x0448 , 0x404C ,

		0x2440 , 0x2040 , 0x2040 , 0x2440 , 0x0040 , 0x0040 , 0x2444 , 0x2044 ,
		0x2044 , 0x2444 , 0x2048 , 0x2040 , 0x004C , 0x2044 , 0x0000 , 0x0000 ,
		0x00B4 , 0x00B4 , 0x00B4 , 0x0094 , 0x0094 , 0x0094 , 0x004E , 0x004E ,

		0x004E , 0x044E , 0x044E , 0x044E , 0x004E , 0x004E , 0x004E , 0x004E ,
		0x004E , 0x004E , 0x004B , 0x004B , 0x004B , 0x044B , 0x044B , 0x044B ,
		0x004B , 0x004B , 0x004B , 0x004B , 0x004B , 0x004B , 0x0080 , 0x00C1 ,
		0x00C1
	};
	

	//public static final int[] flags = convert(_flags);


	//tab45616???
	public static final int[] phonemeStressedLengthTable = 
	{
		0x00 , 0x12 , 0x12 , 0x12 , 8 ,0xB , 9 ,0xB ,
		0xE ,0xF ,0xB , 0x10 ,0xC , 6 , 6 ,0xE ,
		0xC ,0xE ,0xC ,0xB , 8 , 8 ,0xB ,0xA ,
		9 , 8 , 8 , 8 , 8 , 8 , 3 , 5 ,
		2 , 2 , 2 , 2 , 2 , 2 , 6 , 6 ,
		8 , 6 , 6 , 2 , 9 , 4 , 2 , 1 ,
		0xE ,0xF ,0xF ,0xF ,0xE ,0xE , 8 , 2 ,
		2 , 7 , 2 , 1 , 7 , 2 , 2 , 7 ,
		2 , 2 , 8 , 2 , 2 , 6 , 2 , 2 ,
		7 , 2 , 4 , 7 , 1 , 4 , 5 , 5     
	};
	

	//public static final int[] phonemeStressedLengthTable = convert(_phonemeStressedLengthTable);

	//tab45536???
	public static final int[] phonemeLengthTable = 
	{
		0 , 0x12 , 0x12 , 0x12 , 8 , 8 , 8 , 8 ,
		8 ,0xB , 6 ,0xC ,0xA , 5 , 5 ,0xB ,
		0xA ,0xA ,0xA , 9 , 8 , 7 , 9 , 7 ,
		6 , 8 , 6 , 7 , 7 , 7 , 2 , 5 ,
		2 , 2 , 2 , 2 , 2 , 2 , 6 , 6 ,
		7 , 6 , 6 , 2 , 8 , 3 , 1 , 0x1E ,
		0xD ,0xC ,0xC ,0xC ,0xE , 9 , 6 , 1 ,
		2 , 5 , 1 , 1 , 6 , 1 , 2 , 6 ,
		1 , 2 , 8 , 2 , 2 , 4 , 2 , 2 ,
		6 , 1 , 4 , 6 , 1 , 4 , 0xC7 , 0xFF
	};
	
	//public static final int[] phonemeLengthTable = convert(_phonemeLengthTable);
}
