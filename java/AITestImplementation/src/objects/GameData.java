package objects;

import java.util.ArrayList;
import java.util.Random;

import objects.Tile.Tile_Type;
/**
 * GameData holds all tiles and main game logic
 */
public class GameData {

	static Random rnd = new Random();
	public static ArrayList<Tile> TileList = new ArrayList<Tile>();
	public static Factory[] factories = new Factory[6];
	public static ArrayList<Tile> theVoid;
	
	
	public static void Setup() {
		theVoid = new ArrayList<Tile>();
		fillTileList();
		mix();
		setUpFactories();
	}
	
	public static void fillTileList() {
		int tileTypeAmount = 20;//rules
		for (Tile.Tile_Type type : Tile.Tile_Type.values()) {
			for (int i = 0; i < tileTypeAmount; i++) { 
				TileList.add(new Tile(type));
			}
		}
	}
	
	public static void mix() {
		if (TileList.isEmpty()) return;
		ArrayList<Tile> newList = new ArrayList<Tile>();
		for (int i = 0; i < TileList.size(); i++)
			newList.add(TileList.remove(rnd.nextInt(TileList.size())));
		for (int i = 0; i < newList.size(); i++) TileList.add(newList.remove(0));
	}
	
	public static void setUpFactories() {
		for (int i = 0; i < 6; i++) {	
			factories[i] = new Factory(TileList.remove(0), TileList.remove(0), TileList.remove(0), TileList.remove(0));
		}
	}

	public static ArrayList<Factory> AI_getAvailableFactories() {
		ArrayList<Factory> result = new ArrayList<Factory>();
		for (int i = 0; i < 6; i++) {
			if (factories[i].containsTiles()) result.add(factories[i]);
			else result.add(null);
		}
		return result;
	}

	public static Tile[] getTilesFromFactory(Tile_Type tileType, int factoryLoc, int tileCount) {
		Tile[] result = new Tile[tileCount];
		Tile[] tiles = factories[factoryLoc].removeTiles();
		int index = 0;
		for (int i = 0; i < 4; i++) {
			if (tiles[i].getType() == tileType) {
				result[index] = tiles[i];
				index++;
			} else {
				theVoid.add(tiles[i]);
			}
		}
		return result;
	}
	
}
