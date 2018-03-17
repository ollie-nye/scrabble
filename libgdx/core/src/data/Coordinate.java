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

public class Coordinate extends Tuple<Integer, Integer> {

	public Coordinate(int x, int y) {
		super(x, y);
	}
	
	public int getX() {
		return super.getLeft();
	}
	public int getY() {
		return super.getRight();
	}

	public Coordinate getNear(char direction) {
		switch (direction) {
			case 'U':
				return new Coordinate(super.getLeft(), super.getRight() - 1);
			case 'D':
				return new Coordinate(super.getLeft(), super.getRight() + 1);
			case 'L':
				return new Coordinate(super.getLeft() - 1, super.getRight());
			case 'R':
				return new Coordinate(super.getLeft() + 1, super.getRight());
			default:
				return this;
		}
	}
}
