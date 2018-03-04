package validation;

import java.util.ArrayList;

import data.Coordinate;
import scrabble.Board;
import scrabble.Tile;

/**
 * @author Ollie Nye
 * @version 1.1
 */
/*
 * REVISIONS
 * 1.0 - Create class and constructor
 * 1.1 - 
 */
public class WordOperations {
	
	private enum Direction {UP, DOWN, LEFT, RIGHT};
	
	private enum Orient {H, V};
	
	private Board board = null;
	
	private Letter originLetter;
	
	private boolean beenHereBefore = false;
	
	public WordOperations(Board board) {
		this.board = board;
	}
	
	public static void main(String args[]) {
		WordOperations ops = new WordOperations(new Board());
		ops.board.testPlace(new Tile("i", 0), 2, 5);
		ops.board.testPlace(new Tile("n", 0), 3, 5);
		ops.board.testPlace(new Tile("t", 0), 4, 5);
		ops.board.testPlace(new Tile("e", 0), 5, 5);
		ops.board.testPlace(new Tile("l", 0), 6, 5);
		ops.board.testPlace(new Tile("l", 0), 7, 5);
		ops.board.testPlace(new Tile("i", 0), 8, 5);
		ops.board.testPlace(new Tile("g", 0), 9, 5);
		ops.board.testPlace(new Tile("e", 0), 10, 5);
		ops.board.testPlace(new Tile("n", 0), 11, 5);
		ops.board.testPlace(new Tile("t", 0), 12, 5);
		ops.board.testPlace(new Tile("s", 0), 2, 3);
		ops.board.testPlace(new Tile("w", 0), 2, 4);
		ops.board.testPlace(new Tile("s", 0), 2, 6);
		ops.board.testPlace(new Tile("h", 0), 2, 7);
		ops.board.testPlace(new Tile("i", 0), 6, 6);
		ops.board.testPlace(new Tile("g", 0), 6, 7);
		ops.board.testPlace(new Tile("h", 0), 6, 8);
		ops.board.testPlace(new Tile("t", 0), 6, 9);
		ops.board.testPlace(new Tile("r", 0), 10, 4);
		ops.board.testPlace(new Tile("a", 0), 10, 6);
		ops.board.testPlace(new Tile("d", 0), 10, 7);
		
		int x = 2;
		int y = 5;
		
		Letter testLetter = null;
		
		if (ops.board.getTile(x, y) != null) {
			testLetter = new Letter(ops.board.getTile(x, y), new Coordinate(x, y));
		}
		
		ops.identifyWords(testLetter);
	}
	
	public ArrayList<ArrayList<Letter>> identifyWords(Letter letter) {
		ArrayList<ArrayList<Letter>> words = new ArrayList<>();
		
		this.originLetter = letter;

		words = traceWords(letter, Orient.H, words, false);
		
		for (ArrayList<Letter> word : words) {
			String wrd = "";
			for (Letter ltr : word) {
				wrd += ltr.getLetter().getContent();
			}
			System.out.println(wrd);
		}
		
		return words;
	}
	
	private ArrayList<ArrayList<Letter>> traceWords(Letter letter, Orient orientation, ArrayList<ArrayList<Letter>> words, boolean lowestLevel) {
		if (letter == null) { return words; }
		ArrayList<Letter> currentWord = new ArrayList<>();
		boolean initialDirection = true; //down or right, in order of reading the word
		Letter nextLetter = letter;
		while (true) {
			if (nextLetter == null) {
				if (initialDirection) {
					initialDirection = false;
					nextLetter = getNextLetter(letter, (orientation == Orient.H)?(initialDirection)?Direction.RIGHT:Direction.LEFT:(initialDirection)?Direction.DOWN:Direction.UP);
					continue;
				} else {
					break;
				}
			}
			if (!lowestLevel) {
				if (letter.toString().equals(this.originLetter.toString())) {
					if (!beenHereBefore) {
						beenHereBefore = true;
						traceWords(nextLetter, (orientation == Orient.H)?Orient.V:Orient.H, words, false);
					}
				} else {
					traceWords(nextLetter, (orientation == Orient.H)?Orient.V:Orient.H, words, true);
				}
			}
			//System.out.println(nextLetter.toString());
			if (initialDirection) {
				currentWord.add(nextLetter);
			} else {
				currentWord.add(0, nextLetter);
			}
			nextLetter = getNextLetter(nextLetter, (orientation == Orient.H)?(initialDirection)?Direction.RIGHT:Direction.LEFT:(initialDirection)?Direction.DOWN:Direction.UP);
		}
		if (currentWord.size() > 1) {
			words.add(currentWord);
		}
		return words;
	}
	
	private Letter getNextLetter(Letter letter, Direction direction) {
		int x = letter.getLocation().getX();
		int y = letter.getLocation().getY();
		Letter returnLetter = null;
		switch (direction) {
		case UP:
			if (!(letter.getLocation().getY() < 0)) {
				Tile tile = board.getTile(x, y - 1);
				if (tile == null) { return null; }
				returnLetter = new Letter(tile, new Coordinate (x, y - 1)) ;
			}
			break;
		case DOWN:
			if (!(letter.getLocation().getY() > 15)) {
				Tile tile = board.getTile(x, y + 1);
				if (tile == null) { return null; }
				returnLetter = new Letter(tile, new Coordinate (x, y + 1)) ;
			}
			break;
		case LEFT:
			if (!(letter.getLocation().getX() < 0)) {
				Tile tile = board.getTile(x - 1, y);
				if (tile == null) { return null; }
				returnLetter = new Letter(tile, new Coordinate (x - 1, y)) ;
			}
			break;
		case RIGHT:
			if (!(letter.getLocation().getX() > 15)) {
				Tile tile = board.getTile(x + 1, y);
				if (tile == null) { return null; }
				returnLetter = new Letter(tile, new Coordinate (x + 1, y)) ;
			}
			break;
		}
		return returnLetter;
	}
}
