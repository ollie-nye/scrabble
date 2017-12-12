package scrabble;

import data.Coordinate;
import data.Result;
import player.AIPlayer;
import player.HumanPlayer;
import player.Player;
import player.PlayersContainer;
import javax.swing.JOptionPane;

/**
 * Main class for controlling the game.
 * 
 * @author Ollie Nye
 * @version 1.3
 */
/*
 * REVISIONS
 * 1.0 - Create class and constructor
 * 1.1 - 
 * 1.2 - 
 * 1.3 - 
 */

public class Scrabble {
	
	/**
	 * Global current player variable
	 */
	public static int currentPlayer = 0;
	
	/**
	 * Used in creating the player arrays
	 */
	public static int maxPlayers = 4;
	
	/**
	 * Holds a reference to the instance of the Board
	 */
	private Board board = Board.getInstance();
	
	/**
	 * Holds the current UI in use
	 */
	private static BoardUI ui;
	
	/**
	 * Holds a reference to the instance of the PlayersContainer
	 */
	private PlayersContainer players = PlayersContainer.getInstance();
	
	/**
	 * One half of a move is stored here
	 */
	Tile partialTile = null;
	
	/**
	 * The other half of a move is stored here
	 */
	Coordinate partialPlace = null;
	
	/**
	 * Creates players and the UI
	 */
	public Scrabble() {
		players.addPlayer(new HumanPlayer("Jam"));
		players.addPlayer(new HumanPlayer("PB"));
		players.addPlayer(new HumanPlayer("Toast"));
		players.addPlayer(new HumanPlayer("Butter"));
		ui = new BoardUI(4);
	}
	
	
	/**
	 * Method to increment the turn within the game, recreating validation and letters when required
	 */
	public static void incrementTurn() {
		Result lastResult = Board.getInstance().getLastResult();

		if (lastResult.isCompleteWord()) {
			PlayersContainer.getInstance().getPlayer(currentPlayer).addLetter();
			currentPlayer += 1;
			if (currentPlayer > 3) {
				currentPlayer = 0;
			}
			Board.getInstance().validatorReset();
			ui.update();
			
			
		} else {
			JOptionPane.showMessageDialog(null, "This is not a complete word", "Error", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	/**
	 * Creates game and updates the UI for initial state
	 * @param args
	 */
	public static void main(String[] args) {
		splashScreen splash = new splashScreen();
		Scrabble game = new Scrabble();
		
		/**Board brd = Board.getInstance();
		Result res = brd.place(LetterBag.getInstance().pick(), 5, 7);
		System.out.println(res.isLegal() + " - " + res.possibleWords());
		res = brd.place(LetterBag.getInstance().pick(), 5, 9);
		System.out.println(res.isLegal() + " - " + res.possibleWords());
		res = brd.place(LetterBag.getInstance().pick(), 5, 8);
		System.out.println(res.isLegal() + " - " + res.possibleWords());
		res = brd.place(LetterBag.getInstance().pick(), 5, 10);
		System.out.println(res.isLegal() + " - " + res.possibleWords());
		res = brd.place(LetterBag.getInstance().pick(), 5, 6);
		System.out.println(res.isLegal() + " - " + res.possibleWords());
		res = brd.place(LetterBag.getInstance().pick(), 9, 2);
		System.out.println(res.isLegal() + " - " + res.possibleWords());*/
		ui.update();
	}
	
	/**
	 * Passes the picked Tile to the game for saving
	 * @param tile		Tile to be placed
	 */
	public void partialPlace(Tile tile) {
		this.partialTile = tile;
		if (this.partialPlace != null) { //both required elements are provided
			place();
		}
	}
	
	/**
	 * Passes the picked coordinates to the game for saving
	 * @param x			X to save
	 * @param y			Y to save
	 */
	public void partialPlace(int x, int y) {
		this.partialPlace = new Coordinate(x, y);
		if (this.partialTile != null) { //both required elements are provided
			place();
		}
	}
	
	/**
	 * Places if move is legal
	 */
	public void place() {
		Result res = Board.getInstance().place(partialTile, partialPlace.getX(), partialPlace.getY());
		if (res.isLegal()) {
			PlayersContainer.getInstance().getPlayer(Scrabble.currentPlayer).removeLetter(partialTile);
			ui.update();
		}
	}

}
