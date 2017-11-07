package scrabble;

import data.BoardScorer;
import data.Coordinate;
import data.Result;
import player.PlayersContainer;
import validation.Validator;
/**
 * Main class that holds the board and organises the game
 * @author Ollie Nye
 * @verion 1.2
 */

public class Board {
	
	private static Board instance = null;
	
	private Validator validator = new Validator();
	
	private Coordinate partialPlace = null;
	private Tile partialTile = null;
	
	private BoardUI ui;
	
	public static Board getInstance() {
		if (instance == null) {
			instance = new Board();
		}
		return instance;
	}
	
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
	
	
	private BoardScorer[][] scoreBoard = new BoardScorer[15][15];
	private Tile[][] letters = new Tile[15][15];
	
	private Board() {
		
		//Initialise board scoring
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
	
	public void setUI(BoardUI ui) {
		this.ui = ui;
	}
	
	public Tile getTile(int x, int y) {
		if (x >= 0 && x < 15 && y >= 0 && y < 15) {
			return letters[x][y];
		}
		return null;
	}
	
	public BoardScorer getScore(int x, int y) {
		if (x >= 0 && x < 15 && y >= 0 && y < 15) {
			return scoreBoard[x][y];
		}
		return null;
	}
	
	public Result place(Tile tile, int x, int y) {
		Result res = validator.validateMove(tile, x, y);
		if (res.isLegal()) {
			this.letters[x][y] = tile;
		}
		partialTile = null;
		partialPlace = null;
		return res;
	}
	
	public void partialPlace(Tile tile) {
		this.partialTile = tile;
		if (this.partialPlace != null) { //both required elements are provided
			Board.getInstance().place(partialTile, partialPlace.getX(), partialPlace.getY());
		}
	}
	
	public void partialPlace(int x, int y) {
		this.partialPlace = new Coordinate(x, y);
		if (this.partialTile != null) { //both required elements are provided
			Board.getInstance().place(partialTile, partialPlace.getX(), partialPlace.getY());
		}
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
