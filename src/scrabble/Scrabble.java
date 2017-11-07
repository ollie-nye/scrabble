package scrabble;

import data.Coordinate;
import data.Result;
import player.AIPlayer;
import player.HumanPlayer;
import player.Player;
import player.PlayersContainer;

/**
 * Main class for controlling the game.
 * @author Ollie Nye
 * @version 1.3
 *
 */

public class Scrabble {
	
	public static int currentPlayer = 0;
	public static int maxPlayers = 4;
	
	private Board board = Board.getInstance();
	private BoardUI ui;
	private PlayersContainer players = PlayersContainer.getInstance();
	
	Tile partialTile = null;
	Coordinate partialPlace = null;
	
	public Scrabble() {
		players.addPlayer(new HumanPlayer("Jam"));
		players.addPlayer(new HumanPlayer("PB"));
		players.addPlayer(new HumanPlayer("Toast"));
		players.addPlayer(new HumanPlayer("Butter"));
		ui = new BoardUI(4);
		
		
	}
	

	
	public static void incrementTurn() {
		currentPlayer += 1;
		if (currentPlayer > 3) {
			currentPlayer = 0;
		}
		Board.getInstance().validatorReset();
	}
	
	public static void main(String[] args) {
		Scrabble game = new Scrabble();
		
		/**Board brd = Board.getInstance();
		Result res = brd.place(LetterBag.getInstance().pick(), 5, 7);
		System.out.println(res.isLegal() + " - " + res.possibleWords());
		res = brd.place(LetterBag.getInstance().pick(), 5, 9);
		System.out.println(res.isLegal() + " - " + res.possibleWords());
		res = brd.place(LetterBag.getInstance().pick(), 5, 8);
		System.out.println(res.isLegal() + " - " + res.possibleWords());
		res = brd.place(LetterBag.getInstance().pick(), 5, 10);
		System.out.println(res.isLegal() + " - " + res.possibleWords());
		res = brd.place(LetterBag.getInstance().pick(), 5, 6);
		System.out.println(res.isLegal() + " - " + res.possibleWords());
		res = brd.place(LetterBag.getInstance().pick(), 9, 2);
		System.out.println(res.isLegal() + " - " + res.possibleWords());*/
		game.ui.update();
	}
	
	public void partialPlace(Tile tile) {
		this.partialTile = tile;
		if (this.partialPlace != null) { //both required elements are provided
			place();
		}
	}
	
	public void partialPlace(int x, int y) {
		this.partialPlace = new Coordinate(x, y);
		if (this.partialTile != null) { //both required elements are provided
			place();
		}
	}
	
	public void place() {
		Result res = Board.getInstance().place(partialTile, partialPlace.getX(), partialPlace.getY());
		if (res.isLegal()) {
			PlayersContainer.getInstance().getPlayer(Scrabble.currentPlayer).removeLetter(partialTile);
			this.ui.update();
		}
	}

}
