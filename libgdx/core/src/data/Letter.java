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
	private Tile tile;
	
	public Letter(Tile tile, Coordinate location) {
		this.tile = tile;
		this.location = location;
	}
	
	public Tile getTile() {
	    return tile;
	}

	public Coordinate getLocation() {
		return location;
	}
	
	public String toString() {
		return tile.toString() + " at " + location.toString();
	}
}
