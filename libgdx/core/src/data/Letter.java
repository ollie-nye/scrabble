package data;

import scrabble.Tile;

/**
 * @author Ollie Nye
 * @version 1.1
 */
/*
 * REVISIONS
 * 1.0 - Create class and constructor
 * 1.1 - 
 */
public class Letter {
	private Coordinate location;
	private Tile letter;
	
	public Letter(Tile letter, Coordinate location) {
		this.letter = letter;
		this.location = location;
	}
	
	public Tile getLetter() {return this.letter;}
	
	public Coordinate getLocation() {return this.location;}
	
	public String toString() {
		return this.letter.toString() + " at " + this.location.toString();
	}
}
