package data;

/**
 * Communication object between classes carrying tile data
 * @author Ollie
 * @version 1
 */

public class Tile {
	private final String content;
	private final int score;
	
	public Tile(String content, int score) {
		this.content = content;
		this.score = score;
	}
	
	public String getContent() {
		return content;
	}
	
	public int getScore() {
		return score;
	}

	@Override
	public String toString() {
		return content;
	}
}
