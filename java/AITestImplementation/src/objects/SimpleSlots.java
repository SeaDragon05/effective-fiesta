package objects;

import AI.AIMaster;
import objects.Tile.Tile_Type;

public class SimpleSlots {
	private GameBoard parentGameBoard;
	private Tile[] tiles;
	private int length, rowNumber;
	private Tile.Tile_Type[] tile_rules;
	private Tile.Tile_Type lockedTileType = Tile.Tile_Type.NOTYPE;
	private boolean hasOneTileType = false;

	public SimpleSlots(GameBoard b, int rowNumber, int length) {
		this.parentGameBoard = b;
		this.rowNumber = rowNumber;
		hasOneTileType = true;
		this.length = length;
		tile_rules = new Tile.Tile_Type[length];
		for (int i = 0; i < length; i++)
			tile_rules[i] = Tile.Tile_Type.NOTYPE;
		tiles = new Tile[length];
	}

	public SimpleSlots(GameBoard b, int rowNumber, int length, Tile.Tile_Type[] types) {
		if (types == null)
			throw new IllegalArgumentException();
		this.parentGameBoard = b;
		this.rowNumber = rowNumber;
		this.length = length;
		tile_rules = types;
		tiles = new Tile[length];
	}

	public void set(Tile tile) {
		if (hasOneTileType) {
			if (lockedTileType == Tile_Type.NOTYPE)
				lockedTileType = tile.getType();
			if (lockedTileType == tile.getType()) {
				int index = this.getOccupiedCount();
				tiles[index] = tile;
			} else {
				throw new IllegalStateException("Attemped to add incorrect tile type to left row!");
			}
		} else {
			Tile.Tile_Type type = tile.getType();
			if (tiles[Tile.GetTileIndexForRow(tile_rules, type)] == null) {
				tiles[Tile.GetTileIndexForRow(tile_rules, type)] = tile;
			} else {
				throw new IllegalStateException();
			}
		}
	}

	public Tile remove(int index) {
		if (index < 0 || index > length)
			throw new IllegalArgumentException("Index out of bounds of length " + length + "!");
		Tile result = tiles[index];
		tiles[index] = null;
		return result;
	}

	public Tile get(int index) {
		if (index < 0 || index > length)
			throw new IllegalArgumentException("Index out of bounds of length " + length + "!");
		return tiles[index];
	}

	public int getLength() {
		return length;
	}

	public int getOccupiedCount() {
		int result = 0;
		for (int i = 0; i < length; i++) {
			if (tiles[i] != null)
				result++;
		}
		return result;
	}

	public int getUnoccupiedCount() {
		int result = 0;
		for (int i = 0; i < length; i++) {
			if (tiles[i] == null)
				result++;
		}
		return result;
	}

	public Tile.Tile_Type[] getTileRules() {
		return tile_rules;
	}

	public Tile.Tile_Type getTileRule(int index) {
		return tile_rules[index];
	}

	public boolean isOccupied() {
		for (Tile tile : tiles)
			if (tile != null)
				return true;
		return false;
	}

	public boolean isSlotOccupied(Tile.Tile_Type type) {
		return tiles[Tile.GetTileIndexForRow(tile_rules, type)] != null;
	}

	public boolean isFull() {
		for (Tile tile : tiles)
			if (tile == null)
				return false;
		return true;
	}

	public Tile[] getTiles() {
		return tiles;
	}

	public boolean isLocked() {
		return hasOneTileType;
	}

	public Tile.Tile_Type getLockedTileType() {
		if (!isLocked())
			throw new IllegalStateException();
		return lockedTileType;
	}

	public void setLockedTileType(Tile.Tile_Type type) {
		if (!isLocked())
			throw new IllegalStateException();
		lockedTileType = type;
	}

	public void unlockTileType() {
		if (!isLocked())
			throw new IllegalStateException();
		lockedTileType = Tile.Tile_Type.NOTYPE;
	}

	public float AI_getLockedWeight() {
		if (!isLocked())
			throw new IllegalStateException();
		int occupied = 0;
		for (Tile tile : tiles)
			if (tile != null)
				occupied++;
		return (float) occupied / length;
	}

	public void set(Tile[] tiles) {
		if (tiles.length >= length) {
			this.parentGameBoard.addPoints(length - tiles.length);
		}
		int l = 0;
		for (int i = 0; i < tiles.length; i++) {
			try {
				set(tiles[i]);
				l++;
			} catch (IndexOutOfBoundsException e) {
				this.parentGameBoard.addPoints(-1);
			}
		}
		if (AIMaster.debug) System.out.println("Set " + l + " tiles on row " + (rowNumber + 1) + " : " + this.length);
	}

}
