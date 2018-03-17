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

	private final int x;
	private final int y;
	
	public Coordinate(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {return x;}
	public int getY() {return y;}

	public Coordinate getNear(char direction) {
		switch (direction) {
			case 'U':
				return new Coordinate(x, y - 1);
			case 'D':
				return new Coordinate(x, y + 1);
			case 'L':
				return new Coordinate(x - 1, y);
			case 'R':
				return new Coordinate(x + 1, y);
			default:
				return this;
		}
	}
	

	@Override
	public String toString() {
		return "(" + x + "," + y + ")";
	}
}
