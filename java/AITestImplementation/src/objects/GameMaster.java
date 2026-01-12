package objects;

import AI.AIMaster;
/**
 * GameMaster uses all parts of the game and runs it here. This is where the main game loop is
 */
public class GameMaster {
	/**
	 * Main game loop 
	 * @param args
	 */
	public static void main(String[] args) {
		
	}
	
	public static AIMaster AI_1;
	public static AIMaster AI_2;//pitting two ai's against each other LMAO
	public static void setUpGame() {
		GameData.fillTileList();
		GameData.mix();
		GameData.setUpFactories();
		AI_1 = new AIMaster();
		AI_2 = new AIMaster();
	}
}
