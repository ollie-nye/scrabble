package scrabble;

import java.util.Stack;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.ArrayList;

/**
 * LetterBag class provides the next random letter for each player. Can be used multiple times for each player in a turn.
 * @author Ollie Nye
 * @version 1.0
 */

public class LetterBag {
	
	private static LetterBag instance = null;
	private HashMap<Tile, Integer> tiles = new HashMap<>();
	private ArrayList<Tile> tileList = new ArrayList<>();
	private Stack<Tile> tileStack = new Stack<>();
	
	/**
	 * Singleton design pattern
	 * @return The instance of LetterBag
     */

	public static LetterBag getInstance() {
		if (instance == null) {
			instance = new LetterBag();
		}
		return instance;
	}
	
	public LetterBag() {
		//put ( <letter>, (new Integer[]( <quantity in bag>, <score>))
		tiles.put(new Tile("a", 1), 9);
		tiles.put(new Tile("b", 3), 2);
		tiles.put(new Tile("c", 3), 2);
		tiles.put(new Tile("d", 2), 4);
		tiles.put(new Tile("e", 1), 12);
		tiles.put(new Tile("f", 4), 2);
		tiles.put(new Tile("g", 2), 3);
		tiles.put(new Tile("h", 4), 2);
		tiles.put(new Tile("i", 1), 9);
		tiles.put(new Tile("j", 8), 1);
		tiles.put(new Tile("k", 5), 1);
		tiles.put(new Tile("l", 1), 4);
		tiles.put(new Tile("m", 3), 2);
		tiles.put(new Tile("n", 1), 6);
		tiles.put(new Tile("o", 1), 8);
		tiles.put(new Tile("p", 3), 2);
		tiles.put(new Tile("q", 10), 1);
		tiles.put(new Tile("r", 1), 6);
		tiles.put(new Tile("s", 1), 4);
		tiles.put(new Tile("t", 1), 6);
		tiles.put(new Tile("u", 1), 4);
		tiles.put(new Tile("v", 4), 2);
		tiles.put(new Tile("w", 4), 2);
		tiles.put(new Tile("x", 8), 1);
		tiles.put(new Tile("y", 4), 2);
		tiles.put(new Tile("z", 10), 1);
		
		shake();
	}
	
	private void shake() {
		for (Map.Entry<Tile, Integer> entry : this.tiles.entrySet()) {
			Tile tile = entry.getKey();
			Integer quantity = entry.getValue();
			
			for (int i = 0; i < quantity; i++) {
				tileList.add(tile);
			}
		}
		
		Random random = new Random();
		while (tileList.size() > 0) {
			Integer nextTile;
			if (tileList.size() == 1) {
				nextTile = 0;
			} else {
				nextTile = random.nextInt(tileList.size() - 1);
			}
			Tile tileToPush = tileList.get(nextTile);
			tileList.remove(tileToPush);
			tileStack.push(tileToPush);
		}
	}
	
	public Tile pick() {
		return tileStack.pop();
	}

	public void returnToBag(ArrayList<Tile> tiles) {
		this.tileStack.addAll(tiles);
		shake();
	}
}