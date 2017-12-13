package scrabble;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import player.Player;
import player.PlayersContainer;

import java.util.Stack;

/*
 * sets up board UI
 * from coordinate (0,0)-(14,14) are the board positions
 * players hands stored in separate arrays
 */
public class BoardUI implements ActionListener {
	private ScrabbleTile[][] boardTiles;
	private ScrabbleTile[][] playerTiles;
	private Tile[][] playerLetters;

	private JButton scorer1;
	private ScrabbleTile scorer2;
	private ScrabbleTile scorer3;
	private ScrabbleTile scorer4;
	private JButton abc;
	private ScrabbleTile abcd;
	private ScrabbleTile abcde;
	private ScrabbleTile abcdef;

	private Font font = new Font("Helvetica", Font.BOLD, 14);

	private JFrame jf;
	private JFrame menu;

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
				// System.out.println((new Integer(i)).toString() + " " + (new
				// Integer(j)).toString() + " " + ((tile != null) ?
				// tile.getContent() : "empty"));
				boardTiles[i][j].setText((tile != null) ? tile.getContent() : " ");
			}
		}
		scorer1.setText("Player 1 Score: " + Integer.toString(PlayersContainer.getInstance().getPlayer(0).getScore()));
		scorer2.setText("Player 2 Score: " + Integer.toString(PlayersContainer.getInstance().getPlayer(1).getScore()));
		scorer3.setText("Player 3 Score: " + Integer.toString(PlayersContainer.getInstance().getPlayer(2).getScore()));
		scorer4.setText("Player 4 Score: " + Integer.toString(PlayersContainer.getInstance().getPlayer(3).getScore()));

		if (PlayersContainer.getInstance().getPlayer(0).getLastWord() != null){
		abc.setText("P1 Last Word: " + PlayersContainer.getInstance().getPlayer(0).getLastWord());
		}
		if (PlayersContainer.getInstance().getPlayer(1).getLastWord() != null){
		abcd.setText("P2 Last Word: " + PlayersContainer.getInstance().getPlayer(1).getLastWord());
		}
		if (PlayersContainer.getInstance().getPlayer(2).getLastWord() != null){
		abcde.setText("P3 Last Word: " + PlayersContainer.getInstance().getPlayer(2).getLastWord());
		}
		if (PlayersContainer.getInstance().getPlayer(3).getLastWord() != null){
		abcdef.setText("P4 Last Word: " + PlayersContainer.getInstance().getPlayer(3).getLastWord());
		}
		
		for (int i = 0; i < Scrabble.maxPlayers; i++) {
			if (i == Scrabble.currentPlayer) {
				playerLetters[i] = PlayersContainer.getInstance().getPlayer(i).getLetterList();
				for (int j = 0; j < playerTiles[i].length; j++) {
					Tile tile = playerLetters[i][j];
					playerTiles[i][j].setText((tile != null) ? tile.getContent() : " ");
				}
			} else {
				for (int j = 0; j < playerTiles[i].length; j++) {

					playerTiles[i][j].setText(" ");
				}
			}
		}
		// Tile[] p1Letters =
		// PlayersContainer.getInstance().getPlayer(0).getLetterList();
		// for (int i = 0; i < p1Tiles.length; i++) {
		// p1Tiles[i].setText(p1Letters[i].getContent());
		//
		// }

	}

	public void create(int numPlayers) {

		jf = new JFrame();
		menu = new JFrame();

		menu.setSize(1000, 1000);
		try {
			menu.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("MainGameMenu.png")))));
		} catch (IOException e) {
			e.printStackTrace();
		}
		menu.pack();
		menu.setVisible(true);
		menu.setLayout(new GridBagLayout());
		GridBagConstraints d = new GridBagConstraints();
		Dimension dimms = Toolkit.getDefaultToolkit().getScreenSize();
		menu.setLocation(dimms.width / 2 - menu.getSize().width / 2, dimms.height / 2 - menu.getSize().height / 2);

		JButton b = new JButton();
		b.setBorderPainted(false);
		b.setPreferredSize(new Dimension(300, 300));

		b.setIcon(new ImageIcon(("play.png")));

		b.addActionListener(this);

		b.validate();
		menu.add(b, d);
		menu.setVisible(true);

		jf.setSize(1000, 1000);
		try {
			jf.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("ScorePanel.jpg")))));
		} catch (IOException e) {
			e.printStackTrace();
		}
		jf.pack();
		jf.setVisible(true);
		jf.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		Dimension dims = Toolkit.getDefaultToolkit().getScreenSize();
		jf.setLocation(dims.width / 2 - jf.getSize().width / 2, dims.height / 2 - jf.getSize().height / 2);
		// sets up centre board
		JPanel menuPanel = new JPanel();
		CardLayout cardLayout = new CardLayout();
		menuPanel.setLayout(cardLayout);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(15, 15, 5, 5));
		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 15; j++) {
				int wid = jf.getWidth();
				int hei = jf.getHeight();
				int dim = 0;
				if (wid >= hei) {
					dim = hei / 20;
				} else {
					dim = wid / 20;
				}
				ScrabbleTile scrabbleTile = new ScrabbleTile(i, j, 0, dim, dim);
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
			// scrabbleTile.setText(LetterBag.getInstance().pick().getContent());
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
				scrabbleTile.setBackgroundColor(Color.magenta);
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
				scrabbleTile.setBackgroundColor(Color.red);
				playerTiles[3][i] = scrabbleTile;
				rightPanel.add(playerTiles[3][i]);

			}
			c.gridx = 2;
			c.gridy = 1;
			jf.add(rightPanel, c);
		}

		JPanel nextTurnPanel = new JPanel();
		ScrabbleTile scrabbleTile = new ScrabbleTile(-2, -2, -2);
		scrabbleTile.setBackgroundColor(Color.cyan);
		scrabbleTile.setText("End Turn");

		nextTurnPanel.add(scrabbleTile);

		c.gridx = 2;
		c.gridy = 0;

		jf.add(nextTurnPanel, c);

		JPanel dasdasaakjsdhbfaklsdbfak = new JPanel();
		dasdasaakjsdhbfaklsdbfak.setLayout(new GridBagLayout());
		GridBagConstraints aslkdfasdasdfasdfgasdklj = new GridBagConstraints();
		aslkdfasdasdfasdfgasdklj.gridx = 0;
		aslkdfasdasdfasdfgasdklj.gridy = 0;

		JPanel scorePanel = new JPanel();

		scorePanel.setLayout(new GridLayout(4, 1, 0, 0));

		scorer1 = new JButton("asidhaisodhaisod");
		scorer1.setFont(font);
		scorer1.setBackground(Color.blue);
		scorer1.setForeground(Color.white);
		scorePanel.add(scorer1);
		scorer2 = new ScrabbleTile(-2, -1, -3);
		scorer2.setBackgroundColor(Color.yellow);
		scorePanel.add(scorer2);
		scorer3 = new ScrabbleTile(-2, -1, -3);
		scorer3.setBackgroundColor(Color.magenta);
		scorePanel.add(scorer3);
		scorer4 = new ScrabbleTile(-2, -1, -3);
		scorer4.setBackgroundColor(Color.red);
		scorePanel.add(scorer4);

		c.gridx = 0;
		c.gridy = 0;
		jf.add(scorePanel, c);

		JPanel scorePanel2 = new JPanel();

		scorePanel2.setLayout(new GridLayout(4, 1, 0, 0));

		abc = new JButton("asd");
		abc.setText("P1 Last Word:    ");
		abc.setBackground(Color.blue);
		abc.setForeground(Color.white);
		abc.setFont(font);
		scorePanel2.add(abc);

		abcd = new ScrabbleTile(1, 1, -3);
		abcd.setText("P2 Last Word:    ");
		abcd.setBackgroundColor(Color.yellow);
		scorePanel2.add(abcd);

		abcde = new ScrabbleTile(1, 1, -3);
		abcde.setText("P3 Last Word:    ");
		abcde.setBackgroundColor(Color.magenta);
		scorePanel2.add(abcde);

		abcdef = new ScrabbleTile(1, 1, -3);
		abcdef.setText("P4 Last Word:    ");
		abcdef.setBackgroundColor(Color.red);
		scorePanel2.add(abcdef);

		c.gridx = 2;
		c.gridy = 2;

		jf.add(scorePanel2, c);

		jf.setVisible(true);

		jf.setVisible(false);

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		jf.setVisible(true);
		menu.dispose();
	}

}
