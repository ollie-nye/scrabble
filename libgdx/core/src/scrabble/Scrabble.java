package scrabble;

import data.Coordinate;
import data.Result;
import player.AIPlayer;
import player.HumanPlayer;
import player.Player;
import player.PlayersContainer;
import javax.swing.JOptionPane;
import java.util.Random;

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
	}
	
	
	/**
	 * Method to increment the turn within the game, recreating validation and letters when required
	 */
	public static void incrementTurn() {
		Result lastResult = Board.getInstance().getLastResult();
		
		String lastWord = Board.getInstance().getWord();
		if (lastResult.isCompleteWord()) {
            Random random = new Random();
			PlayersContainer.getInstance().getPlayer(currentPlayer).addLetter();
			//TODO: Change to scoring system
			PlayersContainer.getInstance().getPlayer(currentPlayer).setScore((random.nextInt(13) + 7));
            //PlayersContainer.getInstance().getPlayer(currentPlayer).

			PlayersContainer.getInstance().getPlayer(currentPlayer).setLastWord(lastWord);
			currentPlayer += 1;
			if (currentPlayer > 3) {
				currentPlayer = 0;
			}
			Board.getInstance().validatorReset();
			
			
			
			
		} else {
			JOptionPane.showMessageDialog(null, "This is not a complete word", "Error", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	/**
	 * Creates game and updates the UI for initial state
	 * @param args
	 */
	/*
	 * public static void main(String[] args) {
	 
		splashScreen splash = new splashScreen();
		Scrabble game = new Scrabble();
		
		Board brd = Board.getInstance();
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
		System.out.println(res.isLegal() + " - " + res.possibleWords());
		ui.update();
	}
	*/
	
	/**
	 * Places if move is legal
	 */
	public void place() {
		Result res = Board.getInstance().place(partialTile, partialPlace.getX(), partialPlace.getY());
		if (res.isLegal()) {
			PlayersContainer.getInstance().getPlayer(Scrabble.currentPlayer).removeLetter(partialTile);
		}
	}
}
