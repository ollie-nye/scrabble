package scrabble;

public class Scrabble {
	
	Board board = Board.getInstance();
	BoardUI ui;
	//players
	//ui
	
	public Scrabble() {
		ui = new BoardUI(4);
	}
	
	public static void main(String[] args) {
		Scrabble game = new Scrabble();
		Board.getInstance().place(LetterBag.getInstance().pick(), 5, 7);
		game.ui.update();
	}

}
