package data;

/**
 * Communication object between classes carrying tile data
 * @author Ollie
 * @version 1
 */

public class Tile {
	private String content;
	private int score;
	
	public Tile(String content, int score) {
		this.content = content;
		this.score = score;
	}
	
	public String getContent() {
		return this.content;
	}
	
	public int getScore() {
		return this.score;
	}

	@Override
	public String toString() {
		return this.content;
	}
}
