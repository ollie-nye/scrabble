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
		boolean spaceEmpty = (Board.getInstance().getTile(x, y) == null) ? true : false; // If the space on the board is empty
		
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
					String searchString = generateSearchString(nextMove);
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
					String searchString = generateSearchString(nextMove);
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
					if (y == location) { //along the same row
						allowedMove = testWord(x, tile, x, y);
					} else {
						allowedMove = false;
					}
				} else  if (direction == VERTICAL) {
					if (x == location) { //along the same row
						allowedMove = testWord(y, tile, x, y);
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
		
		return new Result(allowedMove, possibleWords);
	}

	/**
	 * Method to remove duplicated code. Tests a selection of characters, testing the regez string against the dictionary
	 * @param location		Row/column to test against
	 * @param tile			Tile in question for testing
	 * @param x				X coordinate of Tile
	 * @param y				Y coordinate of Tile
	 * @return				Boolean allowedMove
	 */
	private boolean testWord(int location, Tile tile, int x, int y) {
		boolean allowedMove = true;
		String[] nextMove = currentPlay;
		nextMove[location] = tile.getContent();
		String searchString = generateSearchString(nextMove);
		possibleWords = dictionary.searchList(searchString);
		if (possibleWords == 0) {
			allowedMove = false;
		} else {
			playedTiles.put(tile, new Coordinate(x, y));
			currentPlay[location] = tile.getContent();
		}
		return allowedMove;
	}
	
	private String generateSearchString(String[] nextMove) {
		String searchString = "";
		int lastLetter = 0;
		boolean hasBeenEmpty = false;
		boolean firstLetterPrinted = false;
		for (int i = 0; i < 15; i++) {
			String currentLetter = nextMove[i];
			if (currentLetter == null) {
				searchString += "." + (!firstLetterPrinted ? "?" : "");
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
	
	public static void main(String[] args) {
		Validator val = new Validator();
		String searchString = val.generateSearchString(new String[] {null,null,null,null,null,null,null,null,"h",null,"l",null,"o",null,null});
		System.out.println(searchString);
		System.out.println((new Dictionary()).searchList(searchString));
	}
}
