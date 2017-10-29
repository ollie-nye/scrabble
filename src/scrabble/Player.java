package scrabble;

import java.util.ArrayList;

/**
 * Contains all methods related to the player component
 * @author Ollie Nye
 * @version 1.0
 */

public class Player {
	
	private static final int PLAY_LENGTH = 7;

	private static ArrayList<String> letters = new ArrayList<>();
	
	private String name = "";
	
	public void Player(String name) {
		this.name = name;
		refillLetters();
	}
	
	private void refillLetters() {
		while (letters.size() < PLAY_LENGTH) {
			LetterBag.getInstance().pick();
		}
	}
}
