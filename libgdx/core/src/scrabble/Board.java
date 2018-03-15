package scrabble;

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

public class Board {
	
	/**
	 * Singleton pattern instance variable
	 */
	private static Board instance = null;

	/**
	 * Validator for testing words. New validator is a new word
	 */
	private NewValidator validator = new NewValidator(this);
	
	/**
	 * One half of the variable pair that makes up a move
	 */
	private Coordinate partialPlace = null;
	
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
		if (location.getX() >= 0 && location.getX() < boardSizeX && location.getY() >= 0 && location.getY() < boardSizeY) {
			return letters[location.getX()][location.getY()];
		}
		return null;
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

	public Tile getPartialTile() {
	    return partialTile;
    }

	public void resetPartial() {
		partialPlace = null;
		partialTile = null;
	}
}
