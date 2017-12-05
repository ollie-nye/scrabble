package validation;

import java.util.HashMap;
import java.util.Map.Entry;

import data.Coordinate;
import data.Result;
import scrabble.Board;
import scrabble.Tile;


/**
 * Validator for each move made by a player, AI and human alike
 * @author Ollie Nye
 * @version 1.3
 * 
 * REVISIONS
 * 1.0 - Created class and constructor, added method stubs to allow interfacing with rest of project
 * 1.1 - Filled out method stubs for basic functionality, only working with the current turn of letters
 * 1.2 - Extended validation functionality to incorporate the rest of the board in the search
 * 1.3 - Added extended Javadoc
 */

public class Validator {

	/**
	 * Holds the whole row/column of the currently played tiles. Also contains letters from the board inside played tile limits.
	 */
	private String[] currentPlay = new String[15];
	
	/**
	 * Holds the list of played tiles and their coordinates
	 */
	private HashMap<Tile, Coordinate> playedTiles = new HashMap<>();

	/**
	 * Holds last validator Result for use in end turn
	 */
	private Result result;
	
	/**
	 * Defines the static constant VERTICAL for use in this class
	 */
	private static final int VERTICAL = 1;
	
	/**
	 * Defines the static constant HORIZONTAL for use in this class
	 */
	private static final int HORIZONTAL = 2;
	
	/**
	 * Definition for this class for the direction of the word. 0 is not set, 1 is vertical, 2 is horizontal, as given in the two previous definitions
	 */
	private int direction = 0;
	
	/**
	 * Holds the common row/column that should be shared between all letters played to allow the move
	 */
	private int location = 0;
	
	/**
	 * Reference to the dictionary for spellchecking
	 */
	private Dictionary dictionary = new Dictionary();

	/**
	 * Holds the number of possible words given the current turn
	 */
	private int possibleWords = 0;

	/**
	 * Constructor for class
	 */
	public Validator() {}

	/**
	 * Main validation method for testing new words
	 * @param tile 		New tile to be tested against the rest of the board
	 * @param x 		X coordinate of the tile
	 * @param y 		Y coordinate of the tile
	 * @return			Result showing if the move is legal and the number of possible words given the set of letters
	 */
	public Result validateMove(Tile tile, int x, int y) {
		boolean allowedMove = true;
		boolean spaceEmpty = (Board.getInstance().getTile(x, y) == null); // If the space on the board is empty
		String searchString = "";
		
		if (spaceEmpty) { // Possibly allowed move, as the space is empty
			if (playedTiles.size() == 0) { // The played tiles this turn list is empty, so this is the first move of the turn
				playedTiles.put(tile, new Coordinate(x, y)); // Accept by default
			} else if (playedTiles.size() == 1) { // Second move of the turn, need to determine direction
				Tile firstTile = null;
				Coordinate firstCoordinate = null;

				for (Entry<Tile, Coordinate> entry : playedTiles.entrySet()) { // This loop will only fire once, the size has to be 1 to get here
					firstTile = entry.getKey();
					firstCoordinate = entry.getValue();
				}
				if (firstCoordinate.getX() == x) { // Vertical direction
					String[] nextMove = new String[15]; //
					nextMove[firstCoordinate.getY()] = firstTile.getContent();
					nextMove[y] = tile.getContent();
					searchString = generateSearchString(nextMove, VERTICAL, x);
					possibleWords = dictionary.searchList(searchString);
					if (possibleWords == 0) {
						allowedMove = false;
					} else {
						direction = VERTICAL;
						location = x;
						playedTiles.put(tile, new Coordinate(x, y));
						currentPlay[firstCoordinate.getY()] = firstTile.getContent();
						currentPlay[y] = tile.getContent();
					}
				} else if (firstCoordinate.getY() == y) { // Horizontal direction
					String[] nextMove = new String[15];
					nextMove[firstCoordinate.getX()] = firstTile.getContent();
					nextMove[x] = tile.getContent();
					searchString = generateSearchString(nextMove, HORIZONTAL, y);
					possibleWords = dictionary.searchList(searchString);
					if (possibleWords == 0) {
						allowedMove = false;
					} else {
						direction = HORIZONTAL;
						location = y;
						playedTiles.put(tile, new Coordinate(x, y));
						currentPlay[firstCoordinate.getX()] = firstTile.getContent();
						currentPlay[x] = tile.getContent();
					}
				} else {
					allowedMove = false;
				}
			} else {
				if (direction == HORIZONTAL) {
					if (y == location) { // Along the same row
						String[] nextMove = currentPlay;
						nextMove[x] = tile.getContent();
						searchString = generateSearchString(nextMove, direction, location);
						possibleWords = dictionary.searchList(searchString);
						if (possibleWords == 0) {
							allowedMove = false;
						} else {
							playedTiles.put(tile, new Coordinate(x, y));
							currentPlay[x] = tile.getContent();
						}
					} else {
						allowedMove = false;
					}
				} else  if (direction == VERTICAL) {
					if (x == location) { // Along the same column
						String[] nextMove = currentPlay;
						nextMove[y] = tile.getContent();
						searchString = generateSearchString(nextMove, direction, location);
						possibleWords = dictionary.searchList(searchString);
						if (possibleWords == 0) {
							allowedMove = false;
						} else {
							playedTiles.put(tile, new Coordinate(x, y));
							currentPlay[y] = tile.getContent();
						}
					} else {
						allowedMove = false;
					}
				} else {
					allowedMove = false;
				}
			}
		} else {
			allowedMove = false;
		}
		this.result = new Result(allowedMove, possibleWords, isCompleteWord());
		return this.result;
	}

	/**
	 * Generates the regex string that searches the dictionary for possible matching words. Does not actually search the dictionary with this string
	 * @param nextMove		Current positions of all played letters this turn
	 * @return				String containing
	 */
	private String generateSearchString(String[] nextMove, int direction, int location) {
		String searchString = "";
		int lastLetter = 0;
		boolean hasBeenEmpty = false;
		boolean firstLetterPrinted = false;
		boolean completeWord = true;
		int y = (direction == HORIZONTAL)?location:0;
		int x = (direction == VERTICAL)?location:0;
		for (int i = 0; i < 15; i++) {
			if (direction == HORIZONTAL) {
				x = i;
			} else if (direction == VERTICAL) {
				y = i;
			}
			Tile tile = Board.getInstance().getTile(x, y);
			String currentLetter = (nextMove[i] != null)?nextMove[i]:(tile != null)?tile.getContent():null;
			if (currentLetter == null) {
				searchString += "." + (!firstLetterPrinted ? "?" : "");
				if (firstLetterPrinted) {
					completeWord = false;
				}
			} else {
				searchString += currentLetter;
				firstLetterPrinted = true;
				lastLetter = i;
			}
		}
		if (lastLetter < 13) {
			searchString = searchString.substring(0, searchString.indexOf(".."));
			for (int i = 0; i < (14 - lastLetter); i++) {
				searchString += ".?";
			}
		} else if (lastLetter == 13) {
			searchString += "?";
		}
		return searchString;
	}

	public Result getLastResult() {
		return this.result;
	}

	public boolean isCompleteWord() {
		String searchString = "(\\b";
		for (String character : currentPlay) {
			searchString += (character != null)?((character.matches("[a-z]"))?character:""):"";
		}
		searchString += "\\b)";
		boolean returnVal = (dictionary.searchList(searchString) == 1);
		return returnVal;
	}

	/**
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		/*
		Validator val = new Validator();
		String searchString = val.generateSearchString(new String[] {null,null,null,null,null,null,null,null,"h",null,"l",null,"o",null,null});
		System.out.println(searchString);
		System.out.println((new Dictionary()).searchList(searchString));
		*/
	}
}
