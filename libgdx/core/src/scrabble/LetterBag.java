package scrabble;

import data.Tile;

import java.io.Serializable;
import java.util.*;

/**
 * LetterBag class provides the next random letter for each player.
 * Can be used multiple times for each player in a turn.
 * @author Ollie Nye
 * @version 1.0
 */
public class LetterBag implements Serializable{

	private HashMap<Tile, Integer> tiles = new HashMap<>();
	private ArrayList<Tile> tileList = new ArrayList<>();
	private Stack<Tile> tileStack = new Stack<>();
	private Score score = new Score();

	private void shake() {
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
	
	public void setList(Stack<Tile> tiles){
		tileStack = tiles;
	}
	public Stack<Tile> getList(){
		return tileStack;
	}

	public void fill() {
        //put ( <letter>, (new Integer[]( <quantity in bag>, <score>))

		for(int i = 0; i < 9; i++) {
			tileList.add(new Tile('a', Score.getLetterScore('a')));
		}
		for(int i = 0; i < 2; i++) {
			tileList.add(new Tile('b', Score.getLetterScore('b')));
		}
		for(int i = 0; i < 2; i++) {
			tileList.add(new Tile('c', Score.getLetterScore('c')));
		}
		for(int i = 0; i < 4; i++) {
			tileList.add(new Tile('d', Score.getLetterScore('d')));
		}
		for(int i = 0; i < 12; i++) {
			tileList.add(new Tile('e', Score.getLetterScore('e')));
		}
		for(int i = 0; i < 2; i++) {
			tileList.add(new Tile('f', Score.getLetterScore('f')));
		}
		for(int i = 0; i < 3; i++) {
			tileList.add(new Tile('g', Score.getLetterScore('g')));
		}
		for(int i = 0; i < 2; i++) {
			tileList.add(new Tile('h', Score.getLetterScore('h')));
		}
		for(int i = 0; i < 9; i++) {
			tileList.add(new Tile('h', Score.getLetterScore('i')));
		}
		for(int i = 0; i < 1; i++) {
			tileList.add(new Tile('i', Score.getLetterScore('j')));
		}
		for(int i = 0; i < 1; i++) {
			tileList.add(new Tile('j', Score.getLetterScore('k')));
		}
		for(int i = 0; i < 4; i++) {
			tileList.add(new Tile('k', Score.getLetterScore('l')));
		}
		for(int i = 0; i < 2; i++) {
			tileList.add(new Tile('l', Score.getLetterScore('m')));
		}
		for(int i = 0; i < 6; i++) {
			tileList.add(new Tile('m', Score.getLetterScore('n')));
		}
		for(int i = 0; i < 8; i++) {
			tileList.add(new Tile('n', Score.getLetterScore('o')));
		}
		for(int i = 0; i < 2; i++) {
			tileList.add(new Tile('o', Score.getLetterScore('p')));
		}
		for(int i = 0; i < 1; i++) {
			tileList.add(new Tile('q', Score.getLetterScore('q')));
		}
		for(int i = 0; i < 6; i++) {
			tileList.add(new Tile('r', Score.getLetterScore('r')));
		}
		for(int i = 0; i < 4; i++) {
			tileList.add(new Tile('s', Score.getLetterScore('s')));
		}
		for(int i = 0; i < 6; i++) {
			tileList.add(new Tile('t', Score.getLetterScore('t')));
		}
		for(int i = 0; i < 4; i++) {
			tileList.add(new Tile('u', Score.getLetterScore('u')));
		}
		for(int i = 0; i < 2; i++) {
			tileList.add(new Tile('v', Score.getLetterScore('v')));
		}
		for(int i = 0; i < 2; i++) {
			tileList.add(new Tile('w', Score.getLetterScore('w')));
		}
		for(int i = 0; i < 1; i++) {
			tileList.add(new Tile('x', Score.getLetterScore('x')));
		}
		for(int i = 0; i < 2; i++) {
			tileList.add(new Tile('y', Score.getLetterScore('y')));
		}
		for(int i = 0; i < 1; i++) {
			tileList.add(new Tile('z', Score.getLetterScore('z')));
		}


        //tiles.put(new Tile('a', Score.getLetterScore('a')), 9);

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
	public void shuffleTile(Tile tile){
		Random random = new Random();
		
		tileStack.add(random.nextInt(tileStack.size()), tile);
	}
}