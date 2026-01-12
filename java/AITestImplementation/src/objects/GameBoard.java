package objects;

public class GameBoard {
	private int points = 0;
	
	private SimpleSlots[] LEFT, RIGHT;
	public GameBoard() {
		LEFT = new SimpleSlots[5];
		RIGHT = new SimpleSlots[5];
		for (int i = 0; i < 5; i++) {
			LEFT[i] = new SimpleSlots(this, i, i+1);
			RIGHT[i] = new SimpleSlots(this, i, 5, Tile.typeLookUpTable[i]);
		}
	}
	public SimpleSlots getRow(boolean LR, int index) {
		if (LR) return LEFT[index];
		else return RIGHT[index];
	}
	public void addPoints(int i) {
		points += i;
	}
	
	
}
