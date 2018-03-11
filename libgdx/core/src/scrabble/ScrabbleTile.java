package scrabble;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ScrabbleTile extends JButton implements MouseListener {

	private boolean splitBackground;
	private Color backgroundColor;
	private Color backgroundColor2;
	private Color foregroundColor;
	private int boardOrPlayer; //this is set to 0 for board, 1 for player 1, 2 for player 2 etc
	private String content;
	private int[] coordinates;
	

	private Font font = new Font("Helvetica", Font.BOLD, 14);

	public ScrabbleTile(int x, int y, int z) {
		content = " ";
		setBackgroundColor(new Color(0x666666));
		coordinates = new int[2];
		addMouseListener(this);
		setCoordinates(x,y);
		boardOrPlayer = z;
		
	}

	public ScrabbleTile(int x, int y, int z, int width, int height) {
		this(x, y, z);
		this.setSize(width, height);
		this.setPreferredSize(new Dimension(width, height));
		
	}
	
	

	public void reset() {
		content = " ";	
		}

	public void setCoordinates(int x, int y) {
		coordinates[0] = x;
		coordinates[1] = y;
		/*
		 * the coordinates for the board range from (0,0) to (14,14) the
		 * coordinates for p.1 =
		 */
	}

	public void setBackgroundColor(Color color) {
		backgroundColor = color;
		splitBackground = false;
		double fontColor = 1 - (((0.299 * backgroundColor.getRed()) + (0.587 * backgroundColor.getGreen())
				+ (0.144 * backgroundColor.getBlue())) / 255.0);
		if (fontColor < 0.5) {
			foregroundColor = Color.black;
		} else {
			foregroundColor = Color.white;
		}
	}

	public void setBackgroundColor(Color color, Color color2) {
		backgroundColor = color;
		backgroundColor2 = color2;
		splitBackground = true;
		double fontColor = 1 - (((0.299 * ((backgroundColor.getRed() + backgroundColor2.getRed()) / 2))
				+ (0.587 * ((backgroundColor.getGreen() + backgroundColor2.getGreen()) / 2))
				+ (0.144 * ((backgroundColor.getBlue() + backgroundColor2.getBlue()) / 2))) / 255.0);
		if (fontColor < 0.5) {
			foregroundColor = Color.black;
		} else {
			foregroundColor = Color.white;
		}
	}

	public void setBackgroundSelected(boolean selected) {
		if (selected) {
			backgroundColor = backgroundColor.brighter();
			if (splitBackground) {
				backgroundColor2 = backgroundColor2.brighter();
			}
		} else {
			backgroundColor = backgroundColor.darker();
			if (splitBackground) {
				backgroundColor2 = backgroundColor2.darker();
			}
		}
		repaint();
	}

	@Override
	public void paintComponent(Graphics graphics) {
		if (splitBackground) {
			
			graphics.setColor(backgroundColor);
			graphics.fillRect(0, 0, this.getWidth() / 2, this.getHeight());
			graphics.setColor(backgroundColor2);
			graphics.fillRect(this.getWidth() / 2, 0, this.getWidth() / 2, this.getHeight());
		} else {
			graphics.setColor(backgroundColor);
			graphics.fillRect(0, 0, this.getWidth(), this.getHeight());
		}
		graphics.setColor(foregroundColor);
		graphics.setFont(font);
		if (graphics.getFontMetrics().stringWidth(content) > this.getWidth()) {
			String line1 = "";
			String line2 = "";
			String[] contents = content.split(" ");
			int countLine1 = (contents.length / 2) + (contents.length % 2);
			for (int count1 = 0; count1 < countLine1; count1++) {
				line1 = line1 + " " + contents[count1];
			}
			for (int count2 = countLine1; count2 < contents.length; count2++) {
				line2 = line2 + " " + contents[count2];
			}
			graphics.drawString(line1, (this.getWidth() - graphics.getFontMetrics().stringWidth(line1)) / 2,
					(this.getHeight() / 2) - 3);
			graphics.drawString(line2, (this.getWidth() - graphics.getFontMetrics().stringWidth(line2)) / 2,
					(this.getHeight() / 2) + 12);
		} else {
			graphics.drawString(content, (this.getWidth() - graphics.getFontMetrics().stringWidth(content)) / 2,
					(this.getHeight() / 2) + 4);
		}
	}

	public void mousePressed(MouseEvent e) {
		backgroundColor = backgroundColor.darker();
		if (splitBackground) {
			backgroundColor2 = backgroundColor2.darker();
		}		
		repaint();
	}

	public void mouseReleased(MouseEvent e) {
		backgroundColor = backgroundColor.brighter();

		if (splitBackground) {
			backgroundColor2 = backgroundColor2.brighter();
		}
		repaint();
	}

	public void mouseEntered(MouseEvent e) {
		backgroundColor = backgroundColor.darker();
		if (splitBackground) {
			backgroundColor2 = backgroundColor2.darker();
		}
		repaint();
	}

	public void mouseExited(MouseEvent e) {
		backgroundColor = backgroundColor.brighter();
		if (splitBackground) {
			backgroundColor2 = backgroundColor2.brighter();
		}
		repaint();
	}

	public void mouseClicked(MouseEvent e) {
		if (boardOrPlayer == 0) {
			Board.getInstance().partialPlace(coordinates[0], coordinates[1]);
		}
		if (boardOrPlayer > 0){
			if (boardOrPlayer == Scrabble.currentPlayer +1){
			Board.getInstance().partialPlace(new Tile(this.getText(), 0));
			}
		}
		//this part should not be used in final game, created for hack to avoid making new button
		if (boardOrPlayer == -2){
			Scrabble.incrementTurn();
		}
		
		
		System.out.println(coordinates[0] + ", " + coordinates[1]);
		
	}

	public void setText(String content) {
		this.content = content;
		repaint();
	}

	
	//this method should not be used in final game, created for hack to avoid making new button

	

	public String getText() {
		return this.content;
	}

	

}


