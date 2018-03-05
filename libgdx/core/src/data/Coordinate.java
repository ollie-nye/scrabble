package data;

/**
 * @author Ollie Nye
 * @version 1.2
 */
/*
 * REVISIONS
 * 1.0 - Create class and constructor
 * 1.1 - Add getters, assume unchanged from initialisation for lack of setters
 * 1.2 - Add toString method
 */

public class Coordinate {

	private int x;
	private int y;
	
	public Coordinate(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {return this.x;}
	public int getY() {return this.y;}
	

	public String toString() {
		return "(" + this.x + "," + this.y + ")";
	}
}
