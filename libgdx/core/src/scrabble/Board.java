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
     * Scoring system
     */
	private static Score score = new Score();
	
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

	private static int boardSizeX = 15;
	private static int boardSizeY = 15;
	


	/*
	 * needed for the libgdx UI
	 */
	public Tile getLetter(int x, int y){
		if (x < boardSizeX && y < boardSizeY){
		return letters [x][y];
		}
		else{
			return new Tile("0", 0);
		}
	}
	
	/**
	 * Holds the spaces that determine move scores
	 */
	private BoardScorer[][] scoreBoard = new BoardScorer[15][15];
	
	/**
	 * Letters played by the players
	 */
	private Tile[][] letters = new Tile[boardSizeX][boardSizeY];
	
	/**
	 * Populates the BoardScorer with data from the arrays for use in the rest of the game
	 */
	private Board() {
		for (int x = 0; x < boardSizeY; x++) {
			for (int y = 0; y < boardSizeY; y++) {
				boolean isLetter;
				boolean isWord;
				int score;
				/**
				switch (types[x][y]) {
				case 'l':
					scoreBoard[x][y] = new BoardScorer(true, scores[x][y]);
					break;
				case 'w':
					scoreBoard[x][y] = new BoardScorer(false, scores[x][y]);
					break;
				default:
					scoreBoard[x][y] = null;
					break;
				}
                 **/
			}
		}
		
		for (int x = 0; x < boardSizeX; x++) {
			for (int y = 0; y < boardSizeY; y++) {
				letters[x][y] = null;
			}
		}
	}
	
	
	/**
	 * Gets the tile at the given coordinate
	 * @param x		X to get from
	 * @param y		Y to get from
	 * @return		Returns the tile at the given position
	 */
	public Tile getTile(int x, int y) {
		if (x >= 0 && x < boardSizeX && y >= 0 && y < boardSizeY) {
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
		if (x >= 0 && x < boardSizeX && y >= 0 && y < boardSizeY) {
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
			score.calculateScore(x, y, tile.getContent());
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
