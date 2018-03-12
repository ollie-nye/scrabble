package scrabble;

import data.Result;

import javax.swing.*;

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
	 * Method to increment the turn within the game, recreating validation and letters when required
	 */
	public static boolean incrementTurn() {
		Result lastResult = Board.getInstance().getLastResult();

		if (lastResult.isCompleteWord()) {
			Board.getInstance().validatorReset();
            return true;
		} else {
			JOptionPane.showMessageDialog(null, "This is not a complete word", "Error", JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
	}
}
