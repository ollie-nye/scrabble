package scrabble;

import data.Tile;

import java.util.*;

/**
 * LetterBag class provides the next random letter for each player.
 * Can be used multiple times for each player in a turn.
 * @author Ollie Nye
 * @version 1.0
 */
public class LetterBag {

	private HashMap<Tile, Integer> tiles = new HashMap<>();
	private ArrayList<Tile> tileList = new ArrayList<>();
	private Stack<Tile> tileStack = new Stack<>();
	private Score score = new Score();

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

	public void fill() {
        //put ( <letter>, (new Integer[]( <quantity in bag>, <score>))
        tiles.put(new Tile('a', Score.getLetterScore('a')), 9);
        tiles.put(new Tile('b', Score.getLetterScore('b')), 2);
        tiles.put(new Tile('c', Score.getLetterScore('c')), 2);
        tiles.put(new Tile('d', Score.getLetterScore('d')), 4);
        tiles.put(new Tile('e', Score.getLetterScore('e')), 12);
        tiles.put(new Tile('f', Score.getLetterScore('f')), 2);
        tiles.put(new Tile('g', Score.getLetterScore('g')), 3);
        tiles.put(new Tile('h', Score.getLetterScore('h')), 2);
        tiles.put(new Tile('i', Score.getLetterScore('i')), 9);
        tiles.put(new Tile('j', Score.getLetterScore('j')), 1);
        tiles.put(new Tile('k', Score.getLetterScore('k')), 1);
        tiles.put(new Tile('l', Score.getLetterScore('l')), 4);
        tiles.put(new Tile('m', Score.getLetterScore('m')), 2);
        tiles.put(new Tile('n', Score.getLetterScore('n')), 6);
        tiles.put(new Tile('o', Score.getLetterScore('o')), 8);
        tiles.put(new Tile('p', Score.getLetterScore('p')), 2);
        tiles.put(new Tile('q', Score.getLetterScore('q')), 1);
        tiles.put(new Tile('r', Score.getLetterScore('r')), 6);
        tiles.put(new Tile('s', Score.getLetterScore('s')), 4);
        tiles.put(new Tile('t', Score.getLetterScore('t')), 6);
        tiles.put(new Tile('u', Score.getLetterScore('u')), 4);
        tiles.put(new Tile('v', Score.getLetterScore('v')), 2);
        tiles.put(new Tile('w', Score.getLetterScore('w')), 2);
        tiles.put(new Tile('x', Score.getLetterScore('x')), 1);
        tiles.put(new Tile('y', Score.getLetterScore('y')), 2);
        tiles.put(new Tile('z', Score.getLetterScore('z')), 1);
        shake();
    }

    public void empty() {
	    tiles.clear();
	    tileList.clear();
	    tileStack.clear();
    }
	
	public Tile pick() {
		if (!tileStack.isEmpty()){
			return tileStack.pop();
		} else {
			return null;
		}
	}

	public void returnToBag(ArrayList<Tile> tiles) {
		this.tileStack.addAll(tiles);
		shake();
	}
	
	//this is only for test purposes
	public void pickABunch(){
		while(tileStack.size() > 3){
			tileStack.pop();
		}
		System.out.println("letterBadDepleted");
	}
	
	public boolean isEmpty(){
		if(!tileStack.isEmpty()){
			return false;
		}
		else return true;
	}
}