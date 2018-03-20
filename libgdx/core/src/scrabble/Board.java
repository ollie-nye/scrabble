package scrabble;

import java.io.Serializable;

import data.Coordinate;
import data.Letter;
import data.Result;
import data.Tile;
import player.Player;
import validation.NewValidator;

/**
 * Main class that holds the board and organises the game
 * @author Ollie Nye
 * @verion 1.3
 */
/*
 * REVISIONS
 * 1.0 - Create class and constructor
 * 1.1 - Add Board constructs such as types and scores
 * 1.2 - Add class fields for Validator, instance, placed tiles and coordinates and ui
 * 1.3 - Add firstWordPlayed class variable
 */

public class Board implements Serializable {
	
	/**
	 * Singleton pattern instance variable
	 */
	private static Board instance = null;
	/**
	 * Other half of the variable pair that makes up a move
	 */
	private Tile partialTile = null;
	/**
	 * Letters played by the players
	 */
	private Tile[][] letters = new Tile[boardSizeX][boardSizeY];
	private static int boardSizeX = 15;
    private static int boardSizeY = 15;
private static boolean isShuffle = false;
    /**
	 * Singleton pattern getter method
	 * @return		Instance of the Board, creating a new one if required
	 */
	public static Board getInstance() {
		if (instance == null) {
			instance = new Board();
		}
		return instance;
	}

    public Board() {
		clearBoard();
		resetPartial();
    }

    public void clearBoard() {
		for (int x = 0; x < boardSizeX; x++) {
			for (int y = 0; y < boardSizeY; y++) {
				letters[x][y] = null;
			}
		}
	}

	/**
	 * Gets the tile at the given coordinate
	 * @param location
	 * @return		Returns the tile at the given position
	 */
	public Tile getTile(Coordinate location) {
		return letters[location.getX()][location.getY()];
	}
	
	public Tile[][] returnBoard(){
		return letters;
	}

	public void setBoard(Tile[][] newBoard){
		letters = newBoard;
	}
	
    /**
     * Removes tile at passed coordinate.
     * @param location  location of tile to remove
     * @return tile     removed tile
     */
	public void removeTile(Coordinate location) {
        letters[location.getX()][location.getY()] = null;
	}

	public void place(Tile tile, Coordinate coordinate) {
		letters[coordinate.getX()][coordinate.getY()] = tile;
	}

	//TODO: Can be swapped to just use place method now instead of this test method.
	public void testPlace(Letter letter) {
		letters[letter.getLocation().getX()][letter.getLocation().getY()] = letter.getTile();
	}
	
	/**
	 * Specifies the tile that has been clicked for the next space placement
	 * @param tile	Tile clicked by the user
	 */
	public void partialPlace(Tile tile) {
		partialTile = tile;
	}
	
	/**
	 * Specifies the coordinate that has been clicked for the next tile placement
     * @param coordinate
	 */
	public void partialPlace(Coordinate coordinate) {
		if (coordinate != null) { //both required elements are provided
            Game.getCurrentMove().addTile(partialTile, coordinate);
        }
	}
	public void toggleShuffle(){
		if (isShuffle){
			isShuffle = false;
		
		}
		else{
			isShuffle = true;
		}
	}
	public boolean isShuffle(){
		return isShuffle;
	}

	public Tile getPartialTile() {
	    return partialTile;
    }

	public void resetPartial() {
		partialTile = null;
	}

	public boolean isEmpty() {
		for (int x = 0; x < boardSizeX; x++) {
			for (int y = 0; y < boardSizeY; y++) {
				if(letters[x][y] != null) {
					return false;
				}
			}
		}
		return true;
	}
}
