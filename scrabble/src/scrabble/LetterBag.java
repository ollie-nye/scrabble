package scrabble;

import java.util.Stack;
import java.util.HashMap;

/**
 * LetterBag class provides the next random letter for each player. Can be used multiple times for each player in a turn.
 * @author Ollie Nye
 * @version 1.0
 */

public class LetterBag {
	
	private static final int maxLetters = 150;
	
	private static final HashMap<String, Integer[]> letters = new HashMap<>();
	
	/**
	 * 
	 */
	public void LetterBag() {
		
	}
	
	
	public void LetterBag(int maxLetters) {
		//put ( <letter>, (new Integer[]( <quantity in bag>, <score>))
		this.letters.put("a", (new Integer[]{9, 1}));
		this.letters.put("b", (new Integer[]{2, 3}));
		this.letters.put("c", (new Integer[]{2, 3}));
		this.letters.put("d", (new Integer[]{4, 2}));
		this.letters.put("e", (new Integer[]{12, 1}));
		this.letters.put("f", (new Integer[]{2, 4}));
		this.letters.put("g", (new Integer[]{3, 2}));
		this.letters.put("h", (new Integer[]{2, 4}));
		this.letters.put("i", (new Integer[]{9, 1}));
		this.letters.put("j", (new Integer[]{1, 8}));
		this.letters.put("k", (new Integer[]{1, 5}));
		this.letters.put("l", (new Integer[]{4, 1}));
		this.letters.put("m", (new Integer[]{2, 3}));
		this.letters.put("n", (new Integer[]{6, 1}));
		this.letters.put("o", (new Integer[]{8, 1}));
		this.letters.put("p", (new Integer[]{2, 3}));
		this.letters.put("q", (new Integer[]{1, 10}));
		this.letters.put("r", (new Integer[]{6, 1}));
		this.letters.put("s", (new Integer[]{4, 1}));
		this.letters.put("t", (new Integer[]{6, 1}));
		this.letters.put("u", (new Integer[]{4, 1}));
		this.letters.put("v", (new Integer[]{2, 4}));
		this.letters.put("w", (new Integer[]{2, 4}));
		this.letters.put("x", (new Integer[]{1, 8}));
		this.letters.put("y", (new Integer[]{2, 4}));
		this.letters.put("z", (new Integer[]{1, 10}));
	}
}
