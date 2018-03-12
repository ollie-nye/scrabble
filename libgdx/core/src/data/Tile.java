package data;

import player.Player;

/**
 * Communication object between classes carrying tile data
 * @author Ollie
 * @version 1
 */

public class Tile {
	private String content;
	private int score;
	private Player player;
	
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

	public String toString() {
		return this.content;
	}

	public void setPlayer(Player player) {
	    this.player = player;
    }

    public Player getPlayer() {
	    return player;
    }
}
