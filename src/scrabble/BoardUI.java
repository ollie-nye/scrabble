package scrabble;

import java.awt.*;

import javax.swing.*;

import player.Player;
import player.PlayersContainer;

import java.util.Stack;

/*
 * sets up board UI
 * from coordinate (0,0)-(14,14) are the board positions
 * player 1 hand is 
 */
public class BoardUI {
	private ScrabbleTile[][] boardTiles;
	private ScrabbleTile[][] playerTiles;
	private Tile[][] playerLetters;

	public static void main(String[] args) {
		BoardUI ui = new BoardUI(Scrabble.maxPlayers);
	}

	public BoardUI(int numPlayers) {
		playerTiles = new ScrabbleTile[Scrabble.maxPlayers][];
		playerLetters = new Tile[Scrabble.maxPlayers][];
		Board.getInstance().setUI(this);
		boardTiles = new ScrabbleTile[15][15];
		create(numPlayers);

	}

	public void update() {

		/*
		 * only completed for 2 player play atm;
		 */
		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 15; j++) {
				Tile tile = Board.getInstance().getTile(i, j);
				//System.out.println((new Integer(i)).toString() + " " + (new Integer(j)).toString() + " " + ((tile != null) ? tile.getContent() : "empty"));
				boardTiles[i][j].setText((tile != null) ? tile.getContent() : " ");
			}
		}
		
		for (int i = 0; i < Scrabble.maxPlayers; i++) {
			playerLetters[i] = PlayersContainer.getInstance().getPlayer(i).getLetterList();
			for (int j = 0; j < playerTiles[i].length; j++) {
				playerTiles[i][j].setText(playerLetters[i][j].getContent());
			}
		}
		//Tile[] p1Letters = PlayersContainer.getInstance().getPlayer(0).getLetterList();
		//for (int i = 0; i < p1Tiles.length; i++) {
		//	p1Tiles[i].setText(p1Letters[i].getContent());
//
		//}

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
		playerTiles[0] = new ScrabbleTile[7];
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(1, 7, 5, 5));
		Player player = PlayersContainer.getInstance().getPlayer(0);
		Tile[] tiles = player.getLetterList();
		for (int i = 0; i < 7; i++) {
			ScrabbleTile scrabbleTile = new ScrabbleTile(i, -1, 1);
			scrabbleTile.setBackgroundColor(Color.blue);
			scrabbleTile.setText(tiles[i].getContent());
			//scrabbleTile.setText(LetterBag.getInstance().pick().getContent());
			playerTiles[0][i] = scrabbleTile;
			topPanel.add(playerTiles[0][i]);

		}
		c.gridx = 1;
		c.gridy = 0;
		jf.add(topPanel, c);

		// sets up player 2
		playerTiles[1] = new ScrabbleTile[7];
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridLayout(1, 7, 5, 5));
		for (int i = 0; i < 7; i++) {
			ScrabbleTile scrabbleTile = new ScrabbleTile(i, -1, 2);
			scrabbleTile.setBackgroundColor(Color.yellow);
			playerTiles[1][i] = scrabbleTile;
			bottomPanel.add(playerTiles[1][i]);

		}
		c.gridx = 1;
		c.gridy = 2;
		jf.add(bottomPanel, c);

		// sets up player 3
		playerTiles[2] = new ScrabbleTile[7];
		if (numPlayers > 2) {

			JPanel leftPanel = new JPanel();
			leftPanel.setLayout(new GridLayout(7, 1, 5, 5));
			for (int i = 0; i < 7; i++) {
				ScrabbleTile scrabbleTile = new ScrabbleTile(i, -1, 3);
				scrabbleTile.setBackgroundColor(Color.red);
				playerTiles[2][i] = scrabbleTile;
				leftPanel.add(playerTiles[2][i]);

			}
			c.gridx = 0;
			c.gridy = 1;
			jf.add(leftPanel, c);
		}

		// sets up player 4
		playerTiles[3] = new ScrabbleTile[7];
		if (numPlayers > 3) {

			JPanel rightPanel = new JPanel();
			rightPanel.setLayout(new GridLayout(7, 1, 5, 5));
			for (int i = 0; i < 7; i++) {
				ScrabbleTile scrabbleTile = new ScrabbleTile(i, -1, 4);
				scrabbleTile.setBackgroundColor(Color.orange);
				playerTiles[3][i] = scrabbleTile;
				rightPanel.add(playerTiles[3][i]);

			}
			c.gridx = 2;
			c.gridy = 1;
			jf.add(rightPanel, c);
		}
		jf.setVisible(true);

	}

}
