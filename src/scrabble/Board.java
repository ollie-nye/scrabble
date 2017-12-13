package scrabble;

import data.BoardScorer;
import data.Coordinate;
import data.Result;
import player.PlayersContainer;
import validation.Validator;

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
	 * If the first word has been played, this is true. Used in validation
	 */
	public static boolean firstWordPlayed = false;
	
	/**
	 * Validator for testing words. New validator is a new word
	 */
	private Validator validator = new Validator();
	
	/**
	 * One half of the variable pair that makes up a move
	 */
	private Coordinate partialPlace = null;
	
	/**
	 * Other half of the variable pair that makes up a move
	 */
	private Tile partialTile = null;

	/**
	 * Instance of the BoardUI
	 */
	private BoardUI ui;
	
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
	
	/**
	 * Recreates the validator for a new word
	 */
	public void validatorReset(){
		validator = new Validator();
	}
	
	/**
	 * Types of multiplier tiles on the board
	 */
	private char[][] types = new char[][]{
		{'w', 'n', 'n', 'l', 'n', 'n', 'n', 'w', 'n', 'n', 'n', 'l', 'n', 'n', 'w'},
		{'n', 'w', 'n', 'n', 'n', 'l', 'n', 'n', 'n', 'l', 'n', 'n', 'n', 'w', 'n'},
		{'n', 'n', 'w', 'n', 'n', 'n', 'l', 'n', 'l', 'n', 'n', 'n', 'w', 'n', 'n'},
		{'l', 'n', 'n', 'w', 'n', 'n', 'n', 'l', 'n', 'n', 'n', 'w', 'n', 'n', 'l'},
		{'n', 'n', 'n', 'n', 'w', 'n', 'n', 'n', 'n', 'n', 'w', 'n', 'n', 'n', 'n'},
		{'n', 'l', 'n', 'n', 'n', 'l', 'n', 'n', 'n', 'l', 'n', 'n', 'n', 'l', 'n'},
		{'n', 'n', 'l', 'n', 'n', 'n', 'l', 'n', 'l', 'n', 'n', 'n', 'l', 'n', 'n'},
		{'w', 'n', 'n', 'l', 'n', 'n', 'n', 'w', 'n', 'n', 'n', 'l', 'n', 'n', 'w'},
		{'n', 'n', 'l', 'n', 'n', 'n', 'l', 'n', 'l', 'n', 'n', 'n', 'l', 'n', 'n'},
		{'n', 'l', 'n', 'n', 'n', 'l', 'n', 'n', 'n', 'l', 'n', 'n', 'n', 'l', 'n'},
		{'n', 'n', 'n', 'n', 'w', 'n', 'n', 'n', 'n', 'n', 'w', 'n', 'n', 'n', 'n'},
		{'l', 'n', 'n', 'w', 'n', 'n', 'n', 'l', 'n', 'n', 'n', 'w', 'n', 'n', 'l'},
		{'n', 'n', 'w', 'n', 'n', 'n', 'l', 'n', 'l', 'n', 'n', 'n', 'w', 'n', 'n'},
		{'n', 'w', 'n', 'n', 'n', 'l', 'n', 'n', 'n', 'l', 'n', 'n', 'n', 'w', 'n'},
		{'w', 'n', 'n', 'l', 'n', 'n', 'n', 'w', 'n', 'n', 'n', 'l', 'n', 'n', 'w'}
	};
	
	/**
	 * Scores of multiplier tiles on the board
	 */
	private int[][] scores = new int[][]{
		{3, 0, 0, 2, 0, 0, 0, 3, 0, 0, 0, 2, 0, 0, 3},
		{0, 2, 0, 0, 0, 3, 0, 0, 0, 3, 0, 0, 0, 2, 0},
		{0, 0, 2, 0, 0, 0, 2, 0, 2, 0, 0, 0, 2, 0, 0},
		{2, 0, 0, 2, 0, 0, 0, 2, 0, 0, 0, 2, 0, 0, 2},
		{0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0},
		{0, 3, 0, 0, 0, 3, 0, 0, 0, 3, 0, 0, 0, 3, 0},
		{0, 0, 2, 0, 0, 0, 2, 0, 2, 0, 0, 0, 2, 0, 0},
		{3, 0, 0, 2, 0, 0, 0, 2, 0, 0, 0, 2, 0, 0, 3},
		{0, 0, 2, 0, 0, 0, 2, 0, 2, 0, 0, 0, 2, 0, 0},
		{0, 3, 0, 0, 0, 3, 0, 0, 0, 3, 0, 0, 0, 3, 0},
		{0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0},
		{2, 0, 0, 2, 0, 0, 0, 2, 0, 0, 0, 2, 0, 0, 2},
		{0, 0, 2, 0, 0, 0, 2, 0, 2, 0, 0, 0, 2, 0, 0},
		{0, 2, 0, 0, 0, 3, 0, 0, 0, 3, 0, 0, 0, 2, 0},
		{3, 0, 0, 2, 0, 0, 0, 3, 0, 0, 0, 2, 0, 0, 3}
	};
	
	/**
	 * Holds the spaces that determine move scores
	 */
	private BoardScorer[][] scoreBoard = new BoardScorer[15][15];
	
	/**
	 * Letters played by the players
	 */
	private Tile[][] letters = new Tile[15][15];
	
	/**
	 * Populates the BoardScorer with data from the arrays for use in the rest of the game
	 */
	private Board() {
		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 15; j++) {
				boolean isLetter;
				boolean isWord;
				int score;
				switch (types[i][j]) {
				case 'l':
					scoreBoard[i][j] = new BoardScorer(true, scores[i][j]);
					break;
				case 'w':
					scoreBoard[i][j] = new BoardScorer(false, scores[i][j]);
					break;
				default:
					scoreBoard[i][j] = null;
					break;
				}
			}
		}
		
		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 15; j++) {
				letters[i][j] = null;
			}
		}
	}
	
	/**
	 * Sets the UI for the system to use
	 * @param ui
	 */
	public void setUI(BoardUI ui) {
		this.ui = ui;
	}
	
	/**
	 * Gets the tile at the given coordinate
	 * @param x		X to get from
	 * @param y		Y to get from
	 * @return		Returns the tile at the given position
	 */
	public Tile getTile(int x, int y) {
		if (x >= 0 && x < 15 && y >= 0 && y < 15) {
			return letters[x][y];
		}
		return null;
	}
	
	/**
	 * Gets a score from a given coordinate
	 * @param x		X to get from
	 * @param y		Y to get from
	 * @return		BoardScorer object containing multiplier type and score multiplier
	 */
	public BoardScorer getScore(int x, int y) {
		if (x >= 0 && x < 15 && y >= 0 && y < 15) {
			return scoreBoard[x][y];
		}
		return null;
	}
	
	/**
	 * Places the tile at the given coordinates if both partialPlace variables are set
	 * @param tile	Tile to play
	 * @param x		X of coordinate to play
	 * @param y		Y of coordinate to play
	 * @return		Result of the play
	 */
	public Result place(Tile tile, int x, int y) {
		Result res = validator.validateMove(tile, x, y);
		if (res.isLegal()) {
			this.letters[x][y] = tile;
			PlayersContainer.getInstance().getPlayer(Scrabble.currentPlayer).removeLetter(tile);
			this.ui.update();
		}
		partialTile = null;
		partialPlace = null;
		return res;
	}
	
	/**
	 * Specifies the tile that has been clicked for the next space placement
	 * @param tile	Tile clicked by the user
	 */
	public void partialPlace(Tile tile) {
		this.partialTile = tile;
		if (this.partialPlace != null) { //both required elements are provided
			Board.getInstance().place(partialTile, partialPlace.getX(), partialPlace.getY());
		}
	}
	
	/**
	 * Specifies the coordinate that has been clicked for the next tile placement
	 * @param x		X of tile clicked
	 * @param y		Y of tile clicked
	 */
	public void partialPlace(int x, int y) {
		this.partialPlace = new Coordinate(x, y);
		if (this.partialTile != null) { //both required elements are provided
			Board.getInstance().place(partialTile, partialPlace.getX(), partialPlace.getY());
		}
	}

	/**
	 * Returns the last result for validation checks
	 * @return		Result containing the last validation check
	 */
	public Result getLastResult() {
		return this.validator.getLastResult();
	}
	
	//ignore for mvp
	public String getWord(){
		return this.validator.getWord();
	}

	
	public static void main(String[] args) {
		Board brd = Board.getInstance();
		Result res = brd.place(LetterBag.getInstance().pick(), 5, 7);
		System.out.println(res.isLegal() + " - " + res.possibleWords());
		res = brd.place(LetterBag.getInstance().pick(), 5, 9);
		System.out.println(res.isLegal() + " - " + res.possibleWords());
		res = brd.place(LetterBag.getInstance().pick(), 5, 9);
		System.out.println(res.isLegal() + " - " + res.possibleWords());
	}
	
}
