package data;

public class BoardScorer {
	private boolean type; //true for letter score, false for word score
	private int score;
	
	public BoardScorer(boolean type, int score) {
		this.type = type;
		this.score = score;
	}
	
	public boolean isLetterScore() {
		return this.type;
	}
	
	public int getMultiplier() {
		return this.score;
	}
}
