package validation;

import java.util.HashMap;
import java.util.Map.Entry;

import data.Coordinate;
import data.Result;
import scrabble.Board;
import scrabble.Tile;
import scrabble.Dictionary;


/**
 * Validator for each move made by a player, AI and human alike
 * @author Ollie Nye
 * @version 1.1
 */

public class Validator {
	//initial tile
	//find direction
	//test all tiles for 'possible' move & complete word
	//return a MoveResult object to caller
	
	private String[] currentPlay = new String[15]; //15 characters, maximum word length
	private HashMap<Tile, Coordinate> playedTiles = new HashMap<>();
	
	private static final int VERTICAL = 1;
	private static final int HORIZONTAL = 2;
	
	private int direction = 0;
	private int location = 0;
	private Dictionary dictionary = new Dictionary();
	
	public Validator() {}
	
	public Result validateMove(Tile tile, int x, int y) {
		boolean allowedMove = true;
		int possibleWords = 0;
		boolean spaceEmpty;
		Tile prospectiveTile = Board.getInstance().getTile(x, y);
		spaceEmpty = (prospectiveTile == null) ? true : false;
		
		if (spaceEmpty) {
			//possibly allowed move
			if (playedTiles.size() == 0) { //first move of the turn
				playedTiles.put(tile, new Coordinate(x, y));
			} else if (playedTiles.size() == 1) { //second move of the turn
				Tile firstTile = null;
				Coordinate firstCoordinate = null;
				//This will only fire once, the size has to be 1 to get here.
				for (Entry<Tile, Coordinate> entry : playedTiles.entrySet()) {
					firstTile = entry.getKey();
					firstCoordinate = entry.getValue();
				}
				if (firstCoordinate.getX() == x) { //vertical direction			
					String[] nextMove = new String[15];
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
				} else if (firstCoordinate.getY() == y) { //horizontal direction
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
				
				
				//get item's coordinate
				//test against given x or y
				//if one is similar, word is possibly allowed. Do dictionary check to confirm
			} else {
				if (direction == HORIZONTAL) {
					if (y == location) { //along the same row
						String[] nextMove = currentPlay;
						nextMove[x] = tile.getContent();
						String searchString = generateSearchString(nextMove);
						possibleWords = dictionary.searchList(searchString);
						if (possibleWords == 0) {
							allowedMove = false;
						} else {
							playedTiles.put(tile, new Coordinate(x, y));
							currentPlay[x] = tile.getContent();
						}
					}
				} else {
					if (x == location) { //along the same row
						String[] nextMove = currentPlay;
						nextMove[y] = tile.getContent();
						String searchString = generateSearchString(nextMove);
						possibleWords = dictionary.searchList(searchString);
						if (possibleWords == 0) {
							allowedMove = false;
						} else {
							playedTiles.put(tile, new Coordinate(x, y));
							currentPlay[y] = tile.getContent();
						}
					}
				}
			}
		} else {
			allowedMove = false;
		}
		
		return new Result(allowedMove, possibleWords);
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
