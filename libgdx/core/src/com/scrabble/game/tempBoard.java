package com.scrabble.game;

import java.util.Random;
public class tempBoard {
	private static tempBoard instance = null;
	 final String alphabet = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	 final int yarr = alphabet.length();
	 Random r = new Random();
	
	public static tempBoard getInstance() {
		if (instance == null) {
			instance = new tempBoard();
		}
		return instance;
	}
	
	public String randomLetter(){
		return Character.toString(alphabet.charAt(r.nextInt(yarr)));

		    

	}
	
	private String[][] types = new String[][]{
		{"w", "n", "n", "l", "n", "n", "n", "w", "n", "n", "n", "l", "n", "n", "w"},
		{"n", "w", "n", "n", "n", "l", "n", "n", "n", "l", "n", "n", "n", "w", "n"},
		{"n", "n", "w", "n", "n", "n", "l", "n", "l", "n", "n", "n", "w", "n", "n"},
		{"l", "n", "n", "w", "n", "n", "n", "l", "n", "n", "n", "w", "n", "n", "l"},
		{"n", "n", "n", "n", "w", "n", "n", "n", "n", "n", "w", "n", "n", "n", "n"},
		{"n", "l", "n", "n", "n", "l", "n", "n", "n", "l", "n", "n", "n", "l", "n"},
		{"n", "n", "l", "n", "n", "n", "l", "n", "l", "n", "n", "n", "l", "n", "n"},
		{"w", "n", "n", "l", "n", "n", "n", "w", "n", "n", "n", "l", "n", "n", "w"},
		{"n", "n", "l", "n", "n", "n", "l", "n", "l", "n", "n", "n", "l", "n", "n"},
		{"n", "l", "n", "n", "n", "l", "n", "n", "n", "l", "n", "n", "n", "l", "n"},
		{"n", "n", "n", "n", "w", "n", "n", "n", "n", "n", "w", "n", "n", "n", "n"},
		{"l", "n", "n", "w", "n", "n", "n", "l", "n", "n", "n", "w", "n", "n", "l"},
		{"n", "n", "w", "n", "n", "n", "l", "n", "l", "n", "n", "n", "w", "n", "n"},
		{"n", "w", "n", "n", "n", "l", "n", "n", "n", "l", "n", "n", "n", "w", "n"},
		{"w", "n", "n", "l", "n", "n", "n", "w", "n", "n", "n", "l", "n", "n", "w"}
	};
	
	public String getLetter(int x, int y){
		if (x<15 && y<15){
			
		return types[x][y];
		}
		else{
			return "9";
		}
		
	}	
	
	public void changeLetter(int x, int y){
		types[x][y] = randomLetter();
		System.out.println("called");
		System.out.print(types [1][1]);		
	}
	
}