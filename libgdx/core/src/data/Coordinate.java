package data;

import java.io.Serializable;

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


public class Coordinate extends Tuple<Integer, Integer> implements Serializable, Comparable<Coordinate> {

	public Coordinate(int x, int y) {
		super(x, y);
	}
	
	public int getX() {
		return left;
	}
	public int getY() {
		return right;
	}

	public Coordinate getNear(char direction) {
		switch (direction) {
			case 'U':
				return new Coordinate(left, right - 1);
			case 'D':
				return new Coordinate(left, right + 1);
			case 'L':
				return new Coordinate(left - 1, right);
			case 'R':
				return new Coordinate(left + 1, right);
			default:
				return this;
		}
	}

	@Override
	public int compareTo(Coordinate o) {
        if (o == null) {
            throw new NullPointerException("A Coordinate object expected.");
        }

        if (((Coordinate) o).getX() < left || ((Coordinate) o).getY() < right) {
            return -1;
        } else if (((Coordinate) o).getX() > left || ((Coordinate) o).getY() > right) {
            return 1;
        } else {
            return 0;
        }
    }
}
