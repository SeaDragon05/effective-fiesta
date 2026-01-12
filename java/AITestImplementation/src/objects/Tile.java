package objects;

public class Tile {
	public static enum Tile_Type {
		Blue, Orange, Red, Black, Frost, NOTYPE
		
	}
	@Override
	public String toString() {
		if (this.type == Tile_Type.Blue) return "Blue";
		else if (this.type == Tile_Type.Orange) return "Orange";
		else if (this.type == Tile_Type.Red) return "Red";
		else if (this.type == Tile_Type.Black) return "Black";
		else if (this.type == Tile_Type.Frost) return "Frost";
		else return "NOTYPE";
	};
	public static Tile_Type[] TILETYPE = {Tile_Type.Blue, Tile_Type.Orange, Tile_Type.Red, Tile_Type.Black, Tile_Type.Frost};
	public static final String[] TileTypeString = {"Blue", "Orange", "Red", "Black", "Frost"};
	
	public static final String[] TileTypeStr_Row1 = { "B", "O", "R", "A", "F", "U" };
	public static final String[] TileTypeStr_Row2 = { "F", "B", "O", "R", "A", "U" };
    public static final String[] TileTypeStr_Row3 = { "A", "F", "B", "O", "R", "U" };
    public static final String[] TileTypeStr_Row4 = { "R", "A", "F", "B", "O", "U" };
    public static final String[] TileTypeStr_Row5 = { "O", "R", "A", "F", "B", "U" }; 
    public static final int[] TileTypeInt_Row1 = { 0, 1, 2, 3, 4, 5 };//correct
    
    public static final int[][] indexLookUpTable = {
    		{0, 1, 2, 3, 4},
    		{1, 2, 3, 4, 0},
    		{2, 3, 4, 0, 1},
    		{3, 4, 0, 1, 2},
    		{4, 0, 1, 2, 3},
    		};
    public static final Tile_Type[][] typeLookUpTable = {
    		{Tile_Type.Blue, Tile_Type.Orange, Tile_Type.Red, Tile_Type.Black, Tile_Type.Frost},
    		{Tile_Type.Frost, Tile_Type.Blue, Tile_Type.Orange, Tile_Type.Red, Tile_Type.Black},
    		{Tile_Type.Black, Tile_Type.Frost, Tile_Type.Blue, Tile_Type.Orange, Tile_Type.Red},
    		{Tile_Type.Red, Tile_Type.Black, Tile_Type.Frost, Tile_Type.Blue, Tile_Type.Orange},
    		{Tile_Type.Orange, Tile_Type.Red, Tile_Type.Black, Tile_Type.Frost, Tile_Type.Blue},
    		
    		};
	
	private Tile_Type type;
	public Tile(Tile_Type type) {
		this.setType(type);
	}
	public Tile_Type getType() {
		return type;
	}
	public void setType(Tile_Type type) {
		this.type = type;
	}
	public String getTypeStr() {
		if (this.type == Tile_Type.Blue) return TileTypeStr_Row1[0];
		else if (this.type == Tile_Type.Orange) return TileTypeStr_Row1[1];
		else if (this.type == Tile_Type.Red) return TileTypeStr_Row1[2];
		else if (this.type == Tile_Type.Black) return TileTypeStr_Row1[3];
		else return TileTypeStr_Row1[4];
	}
	public int getTypeInt() {
		if (this.type == Tile_Type.Blue) return TileTypeInt_Row1[0];
		else if (this.type == Tile_Type.Orange) return TileTypeInt_Row1[1];
		else if (this.type == Tile_Type.Red) return TileTypeInt_Row1[2];
		else if (this.type == Tile_Type.Black) return TileTypeInt_Row1[3];
		else return TileTypeInt_Row1[4];
	}
	public static Tile_Type getTypeRowAtIndex(int r, int i) {
		return typeLookUpTable[r][i];
	}
	
	public int GetTypeIndexForRow(Tile_Type[] rules)
	{
		for (int i = 0; i < 5; i++) {
			if (rules[i] == type) return i;
		}
		return -1;
	}
	public static int GetTileIndexForRow(Tile_Type[] rules, Tile_Type type) {
		for (int i = 0; i < 5; i++) {
			if (rules[i] == type) return i;
		}
		return -1;
	}
	
	
	public static String getTypeStr(Tile_Type type) {
		if (type == Tile_Type.Blue) return TileTypeStr_Row1[0];
		else if (type == Tile_Type.Orange) return TileTypeStr_Row1[1];
		else if (type == Tile_Type.Red) return TileTypeStr_Row1[2];
		else if (type == Tile_Type.Black) return TileTypeStr_Row1[3];
		else return TileTypeStr_Row1[4];
	}
	public static int getTypeInt(Tile_Type type) {
		if (type == Tile_Type.Blue) return 0;
		else if (type == Tile_Type.Orange) return 1;
		else if (type == Tile_Type.Red) return 2;
		else if (type == Tile_Type.Black) return 3;
		else return 4;
	}
	public static String getTypeStr(int type) {
		return TileTypeString[type];
	}
}
