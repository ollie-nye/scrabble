package scrabble;

import data.Coordinate;
import data.Letter;
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
     *
     * THE BIG COW STOOD ON THE HILL
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
	public static boolean incrementTurn() {
		Result lastResult = Board.getInstance().getLastResult();
		String lastWord = Board.getInstance().getWord();
		Player player = PlayersContainer.getInstance().getPlayer(currentPlayer);

		if (lastResult.isCompleteWord()) {
            Random random = new Random();
			player.addLetters();
			player.setScore((random.nextInt(13) + 7));
			player.setScore(player.getScore() + player.getMoveScore());
			player.setMoveScore(0);
			player.setLastWord(lastWord);
			currentPlayer += 1;
			if (currentPlayer > 3) {
				currentPlayer = 0;
			}
			Board.getInstance().validatorReset();
			if(PlayersContainer.getInstance().getPlayer(currentPlayer) instanceof AIPlayer) {

            }
            return true;
		} else {
			JOptionPane.showMessageDialog(null, "This is not a complete word", "Error", JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
	}
	
	/**
	 * Places if move is legal
	 */
	public void place() {
		Result res = Board.getInstance().place(new Letter(partialTile, partialPlace));
		if (res.isLegal()) {
			PlayersContainer.getInstance().getPlayer(Scrabble.currentPlayer).removeLetter(partialTile);
		}
	}
}
