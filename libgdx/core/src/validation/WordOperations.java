package validation;

import java.util.ArrayList;
import data.Coordinate;
import data.Letter;
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
		ops.board.testPlace(new Letter(new Tile("i", 0), new Coordinate(2, 5)));
		ops.board.testPlace(new Letter(new Tile("n", 0), new Coordinate(3, 5)));
		ops.board.testPlace(new Letter(new Tile("t", 0), new Coordinate(4, 5)));
		ops.board.testPlace(new Letter(new Tile("e", 0), new Coordinate(5, 5)));
		ops.board.testPlace(new Letter(new Tile("l", 0), new Coordinate(6, 5)));
		ops.board.testPlace(new Letter(new Tile("l", 0), new Coordinate(7, 5)));
		ops.board.testPlace(new Letter(new Tile("i", 0), new Coordinate(8, 5)));
		ops.board.testPlace(new Letter(new Tile("g", 0), new Coordinate(9, 5)));
		ops.board.testPlace(new Letter(new Tile("e", 0), new Coordinate(10, 5)));
		ops.board.testPlace(new Letter(new Tile("n", 0), new Coordinate(11, 5)));
		ops.board.testPlace(new Letter(new Tile("t", 0), new Coordinate(12, 5)));
		ops.board.testPlace(new Letter(new Tile("s", 0), new Coordinate(2, 3)));
		ops.board.testPlace(new Letter(new Tile("w", 0), new Coordinate(2, 4)));
		ops.board.testPlace(new Letter(new Tile("s", 0), new Coordinate(2, 6)));
		ops.board.testPlace(new Letter(new Tile("h", 0), new Coordinate(2, 7)));
		ops.board.testPlace(new Letter(new Tile("i", 0), new Coordinate(6, 6)));
		ops.board.testPlace(new Letter(new Tile("g", 0), new Coordinate(6, 7)));
		ops.board.testPlace(new Letter(new Tile("h", 0), new Coordinate(6, 8)));
		ops.board.testPlace(new Letter(new Tile("t", 0), new Coordinate(6, 9)));
		ops.board.testPlace(new Letter(new Tile("r", 0), new Coordinate(10, 4)));
		ops.board.testPlace(new Letter(new Tile("a", 0), new Coordinate(10, 6)));
		ops.board.testPlace(new Letter(new Tile("d", 0), new Coordinate(10, 7)));
		
		ops.board.testPlace(new Letter(new Tile("h", 0), new Coordinate(2, 12)));
		ops.board.testPlace(new Letter(new Tile("e", 0), new Coordinate(3, 12)));
		//ops.board.testPlace(new Letter(new Tile("n", 0), new Coordinate(4, 12)));
		
		
		Coordinate loc = new Coordinate(2, 12);
		
		Letter testLetter = null;
		
		if (ops.board.getTile(loc) != null) {
			testLetter = new Letter(ops.board.getTile(loc), loc);
		}
		ops.identifyWords(testLetter);
	}
	
	
	public ArrayList<String> identifyWords(Letter letter) {
		ArrayList<String> words = new ArrayList<>();
		ArrayList<ArrayList<Letter>> wordsH = new ArrayList<>();
		ArrayList<ArrayList<Letter>> wordsV = new ArrayList<>();
		
		this.originLetter = letter;

		wordsH = traceWords(letter, Orient.H, wordsH, false);
		wordsV = traceWords(letter, Orient.V, wordsV, false);
		
		for (ArrayList<Letter> newWordList : wordsH) {
			String newWord = "";
			for (Letter newWordLetter : newWordList) {
				newWord += newWordLetter.getTile().toString();
			}
			words.add(newWord);
		}
		
		for (ArrayList<Letter> newWordList : wordsV) {
			String newWord = "";
			for (Letter newWordLetter : newWordList) {
				newWord += newWordLetter.getTile().toString();
			}
			if (!words.contains(newWord)) {
				words.add(newWord);
			}
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
		Coordinate nextLoc = null;
		switch (direction) {
		case UP:
			if (!(letter.getLocation().getY() < 0)) {
				nextLoc = new Coordinate(x, y - 1);
				Tile tile = board.getTile(nextLoc);
				if (tile == null) { return null; }
				returnLetter = new Letter(tile, nextLoc) ;
			}
			break;
		case DOWN:
			if (!(letter.getLocation().getY() > 15)) {
				nextLoc = new Coordinate (x, y + 1);
				Tile tile = board.getTile(nextLoc);
				if (tile == null) { return null; }
				returnLetter = new Letter(tile, nextLoc) ;
			}
			break;
		case LEFT:
			if (!(letter.getLocation().getX() < 0)) {
				nextLoc = new Coordinate (x - 1, y);
				Tile tile = board.getTile(nextLoc);
				if (tile == null) { return null; }
				returnLetter = new Letter(tile, nextLoc) ;
			}
			break;
		case RIGHT:
			if (!(letter.getLocation().getX() > 15)) {
				nextLoc = new Coordinate (x + 1, y);
				Tile tile = board.getTile(nextLoc);
				if (tile == null) { return null; }
				returnLetter = new Letter(tile, nextLoc) ;
			}
			break;
		}
		return returnLetter;
	}
}
