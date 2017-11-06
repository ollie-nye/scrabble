package UI;

import java.awt.*;

import javax.swing.*;

import scrabble.Board;
import scrabble.Tile;

import java.util.Stack;

/*
 * sets up board UI
 * from coordinate (0,0)-(14,14) are the board positions
 * player 1 hand is 
 */
public class BoardUI {
	private ScrabbleTile[][] boardTiles;
	private ScrabbleTile[] p1Tiles;
	private ScrabbleTile[] p2Tiles;
	private ScrabbleTile[] p3Tiles;
	private ScrabbleTile[] p4Tiles;

	public static void main(String[] args) {
		BoardUI ui = new BoardUI(4);

	}

	public BoardUI(int numPlayers) {

		boardTiles = new ScrabbleTile[15][15];
		create(numPlayers);

	}

	public void update() {

		/*
		 * only completed for 2 player play atm;
		 */
		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 15; i++) {
				Tile tile = Board.getInstance().getTile(i, j);
				boardTiles[i][j].setText((tile != null) ? tile.getContent() : " ");
			}
		}
		for (int i = 0; i < p1Tiles.length; i++) {
			p1Tiles[i].setText(checkAgainstPlayer1());

		}
		for (int i = 0; i < p2Tiles.length; i++) {
			p1Tiles[i].setText(checkAgainstPlayer2());

		}

	}

	public void create(int numPlayers) {

		JFrame jf = new JFrame();
		
		jf.setSize(1000, 1000);
		jf.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		// sets up centre board

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(15, 15, 5, 5));
		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 15; j++) {
				ScrabbleTile scrabbleTile = new ScrabbleTile(i, j, 0);
				scrabbleTile.setBackgroundColor(Color.green);

				boardTiles[i][j] = scrabbleTile;
				buttonPanel.add(boardTiles[i][j]);
			}

		}
		c.gridx = 1;
		c.gridy = 1;
		Insets inset = new Insets(5, 5, 5, 5);
		c.insets = inset;
		jf.add(buttonPanel, c);

		// sets up player 1
		p1Tiles = new ScrabbleTile[7];
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(1, 7, 5, 5));
		for (int i = 0; i < 7; i++) {
			ScrabbleTile scrabbleTile = new ScrabbleTile(i, -1, 1);
			scrabbleTile.setBackgroundColor(Color.blue);
			p1Tiles[i] = scrabbleTile;
			topPanel.add(p1Tiles[i]);

		}
		c.gridx = 1;
		c.gridy = 0;
		jf.add(topPanel, c);

		// sets up player 2
		p2Tiles = new ScrabbleTile[7];
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridLayout(1, 7, 5, 5));
		for (int i = 0; i < 7; i++) {
			ScrabbleTile scrabbleTile = new ScrabbleTile(i, -1, 2);
			scrabbleTile.setBackgroundColor(Color.yellow);
			p2Tiles[i] = scrabbleTile;
			bottomPanel.add(p2Tiles[i]);

		}
		c.gridx = 1;
		c.gridy = 2;
		jf.add(bottomPanel, c);

		// sets up player 3
		p3Tiles = new ScrabbleTile[7];
		if (numPlayers > 2) {

			JPanel leftPanel = new JPanel();
			leftPanel.setLayout(new GridLayout(7, 1, 5, 5));
			for (int i = 0; i < 7; i++) {
				ScrabbleTile scrabbleTile = new ScrabbleTile(i, -1, 3);
				scrabbleTile.setBackgroundColor(Color.red);
				p3Tiles[i] = scrabbleTile;
				leftPanel.add(p3Tiles[i]);

			}
			c.gridx = 0;
			c.gridy = 1;
			jf.add(leftPanel, c);
		}

		// sets up player 4
		p4Tiles = new ScrabbleTile[7];
		if (numPlayers > 3) {

			JPanel rightPanel = new JPanel();
			rightPanel.setLayout(new GridLayout(7, 1, 5, 5));
			for (int i = 0; i < 7; i++) {
				ScrabbleTile scrabbleTile = new ScrabbleTile(i, -1, 4);
				scrabbleTile.setBackgroundColor(Color.orange);
				p4Tiles[i] = scrabbleTile;
				rightPanel.add(p4Tiles[i]);

			}
			c.gridx = 2;
			c.gridy = 1;
			jf.add(rightPanel, c);
		}
		jf.setVisible(true);

	}

}