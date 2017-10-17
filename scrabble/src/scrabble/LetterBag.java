package scrabble;

import java.util.Stack;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.ArrayList;

/**
 * LetterBag class provides the next random letter for each player. Can be used multiple times for each player in a turn.
 * @author Ollie Nye
 * @version 1.0
 */

public class LetterBag {
	
	private static final int maxLetters = 100;
	
	private static final HashMap<String, Integer[]> letters = new HashMap<>();
	
	private ArrayList<String> letterList = new ArrayList<>();
	
	private Stack<String> letterStack = new Stack<>();
	
	/**
	 * 
	 */
	public void LetterBag() {
		this.LetterBag(this.maxLetters);
	}
	
	
	public void LetterBag(int maxLetters) {
		//put ( <letter>, (new Integer[]( <quantity in bag>, <score>))
		letters.put("a", (new Integer[]{9, 1}));
		letters.put("b", (new Integer[]{2, 3}));
		letters.put("c", (new Integer[]{2, 3}));
		letters.put("d", (new Integer[]{4, 2}));
		letters.put("e", (new Integer[]{12, 1}));
		letters.put("f", (new Integer[]{2, 4}));
		letters.put("g", (new Integer[]{3, 2}));
		letters.put("h", (new Integer[]{2, 4}));
		letters.put("i", (new Integer[]{9, 1}));
		letters.put("j", (new Integer[]{1, 8}));
		letters.put("k", (new Integer[]{1, 5}));
		letters.put("l", (new Integer[]{4, 1}));
		letters.put("m", (new Integer[]{2, 3}));
		letters.put("n", (new Integer[]{6, 1}));
		letters.put("o", (new Integer[]{8, 1}));
		letters.put("p", (new Integer[]{2, 3}));
		letters.put("q", (new Integer[]{1, 10}));
		letters.put("r", (new Integer[]{6, 1}));
		letters.put("s", (new Integer[]{4, 1}));
		letters.put("t", (new Integer[]{6, 1}));
		letters.put("u", (new Integer[]{4, 1}));
		letters.put("v", (new Integer[]{2, 4}));
		letters.put("w", (new Integer[]{2, 4}));
		letters.put("x", (new Integer[]{1, 8}));
		letters.put("y", (new Integer[]{2, 4}));
		letters.put("z", (new Integer[]{1, 10}));
		
		for (Map.Entry<String, Integer[]> entry : this.letters.entrySet()) {
			String letter = entry.getKey();
			Integer quantity = entry.getValue()[0];
			Integer value = entry.getValue()[1];
			
			letterList.add(letter);
		}
		
		Iterator<String> letter = letterList.iterator();
		Random random = new Random();
		while (letter.hasNext()) {
			Integer nextLetter = random.nextInt(letterList.size() - 1);
			String letterToPush = letterList.get(nextLetter);
			letterList.remove(nextLetter);
			letterStack.push(letterToPush);
		}
	}
	
	public String getNextLetter() {
		return letterStack.pop();
	}
	
	public static void main(String args[]) {
		LetterBag bag = new LetterBag();
		for (int i = 0; i < 100; i++) {
			bag.getNextLetter();
		}
	}
}