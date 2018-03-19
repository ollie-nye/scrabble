package validation;

import data.Coordinate;
import data.Letter;
import data.Tile;
import scrabble.Board;

import java.io.Serializable;
import java.util.ArrayList;



/**
 * @author Ollie Nye
 * @version 1.1
 */

/*
 * REVISIONS
 * 1.0 - Create class and constructor
 * 1.1 - 
 */
public class WordOperations implements Serializable {
	
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
		/**
		ops.board.testPlace(new Letter(new Tile("p", 0), new Coordinate(0, 6)));
		ops.board.testPlace(new Letter(new Tile("a", 0), new Coordinate(1, 6)));
		ops.board.testPlace(new Letter(new Tile("t", 0), new Coordinate(3, 6)));
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
		
		
		Coordinate loc = new Coordinate(2, 6);
				
		Letter testLetter = null;
		
		if (ops.board.getTile(loc) != null) {
			testLetter = new Letter(ops.board.getTile(loc), loc);
		}
		System.out.println("Trace from letter");
		for (String word : ops.identifyWords(testLetter)) {
			System.out.println(word);
		}
		
		
		System.out.println("Trace from word");
		for (ArrayList<Letter> word : ops.identifyWordsLetterlist(testLetter)) {
			for (Letter letter : word) {
				System.out.print(letter.toString() + ", ");
			}
			System.out.println("");
		}
		 **/
	}
	
	
	public ArrayList<String> identifyWords(Letter letter) {
		ArrayList<String> words = new ArrayList<>();
		ArrayList<ArrayList<Letter>> wordsH = new ArrayList<>();
		ArrayList<ArrayList<Letter>> wordsV = new ArrayList<>();
		
		this.originLetter = letter;

		wordsH = traceWords(letter, Orient.H, wordsH, 1);
		wordsV = traceWords(letter, Orient.V, wordsV, 1);
		
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
	
	public ArrayList<ArrayList<Letter>> identifyWordsLetterlist(Letter letter) {
		ArrayList<ArrayList<Letter>> words = new ArrayList<>();
		ArrayList<ArrayList<Letter>> wordsH = new ArrayList<>();
		ArrayList<ArrayList<Letter>> wordsV = new ArrayList<>();
		
		this.originLetter = letter;

		wordsH = traceWords(letter, Orient.H, wordsH, 2);
		wordsV = traceWords(letter, Orient.V, wordsV, 2);
		
		for (ArrayList<Letter> newWordList : wordsH) {
			words.add(newWordList);
		}
		
		for (ArrayList<Letter> newWordList : wordsV) {
			boolean exists = false;
			for (ArrayList<Letter> wordList : words) {
				if (convertToString(wordList).equals(convertToString(newWordList))) {
					exists = true;
					break;
				}
			}
			if (!exists) {
				words.add(newWordList);
			}
		}
		
		return words;
	}
	
	private String convertToString(ArrayList<Letter> word) {
		String returnWord = "";
		for (Letter letter : word) {
			returnWord += letter.getTile().toString();
		}
		return returnWord;
	}
	
	private ArrayList<ArrayList<Letter>> traceWords(Letter letter, Orient orientation, ArrayList<ArrayList<Letter>> words, int depth) {
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
			if (depth > 0) {
				if (letter.toString().equals(this.originLetter.toString())) {
					if (!beenHereBefore) {
						beenHereBefore = true;
						traceWords(nextLetter, (orientation == Orient.H)?Orient.V:Orient.H, words, depth - 1);
					}
				} else {
					traceWords(nextLetter, (orientation == Orient.H)?Orient.V:Orient.H, words, 0);
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
			if (!(letter.getLocation().getY() <= 1)) {
				nextLoc = new Coordinate(x, y - 1);
				Tile tile = board.getTile(nextLoc);
				if (tile == null) { return null; }
				returnLetter = new Letter(tile, nextLoc) ;
			}
			break;
		case DOWN:
			if (!(letter.getLocation().getY() >= 13)) {
				nextLoc = new Coordinate (x, y + 1);
				Tile tile = board.getTile(nextLoc);
				if (tile == null) { return null; }
				returnLetter = new Letter(tile, nextLoc) ;
			}
			break;
		case LEFT:
			if (!(letter.getLocation().getX() <= 1)) {
				nextLoc = new Coordinate (x - 1, y);
				Tile tile = board.getTile(nextLoc);
				if (tile == null) { return null; }
				returnLetter = new Letter(tile, nextLoc) ;
			}
			break;
		case RIGHT:
			if (!(letter.getLocation().getX() >= 13)) {
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
