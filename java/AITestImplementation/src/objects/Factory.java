package objects;
/**
 * I honestly could have written this to be much better :(
 */
public class Factory {
	private Tile[] tiles;
	public Factory(Tile tile1, Tile tile2, Tile tile3, Tile tile4) {
		tiles = new Tile[4];
		tiles[0] = tile1;
		tiles[1] = tile2;
		tiles[2] = tile3;
		tiles[3] = tile4;
	}
	public Tile[] getTiles() {
		return tiles;
	}
	public void setTiles(Tile[] tiles) {
		this.tiles = tiles;
	}
	public Tile[] removeTiles() {
		Tile[] r = tiles;
		this.tiles = new Tile[4];
		return r;
	}
	public boolean containsTiles() {
		return tiles[0] != null && tiles[1] != null && tiles[2] != null && tiles[3] != null;
	}
	public int getTileTypeCount(Tile.Tile_Type type) {
		int result = 0;
		for (int i = 0; i < 4; i++) if (tiles[i].getType() == type) result++;
		return result;
	}
}
