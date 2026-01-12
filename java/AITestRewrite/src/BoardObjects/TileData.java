package BoardObjects;

public class TileData {
	public static enum TileType {
		Blue, Orange, Red, Black, Frost, NOTYPE
	}
	public static enum TileStatus {
		Unlocked_Empty, Locked_Empty, Unlocked_Occupied, Locked_Occupied
	}
	public static final int[] TileTypeInt = { 0, 1, 2, 3, 4, 5 };
	public static final String[] TileTypeStr = { "B", "O", "R", "A", "N" };
	
	//lookup tables for rules
	
	//index lookups:
	public static final int[] TileTypeInt_r1 = { 0, 1, 2, 3, 4 };
	public static final int[] TileTypeInt_r2 = { 4, 0, 1, 2, 3 };
	public static final int[] TileTypeInt_r3 = { 3, 4, 0, 1, 2 };
	public static final int[] TileTypeInt_r4 = { 2, 3, 4, 0, 1 };
	public static final int[] TileTypeInt_r5 = { 1, 2, 3, 4, 0 };
	public static final int[][] Rule_Matrix = { TileTypeInt_r1, TileTypeInt_r2, TileTypeInt_r3, TileTypeInt_r4, TileTypeInt_r5 };
	
	
}
