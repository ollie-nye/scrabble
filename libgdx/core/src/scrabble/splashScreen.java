package scrabble;


import java.awt.*;
import javax.swing.*;


public class splashScreen {
	public splashScreen() {
	JWindow window = new JWindow();
	window.setBounds(500, 150, 800, 600);
	window.getContentPane().add(
	    new JLabel("", new ImageIcon("logo.png"), SwingConstants.CENTER));
	    
	Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	window.setLocation(dim.width/2-window.getSize().width/2, dim.height/2-window.getSize().height/2);
	
	window.setVisible(true);
	try {
	    Thread.sleep(4000);
	} catch (InterruptedException e) {
	    e.printStackTrace();
	}
	
	window.dispose();

}
	}
