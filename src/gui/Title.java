package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

public class Title extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7787301732501168245L;
	private Font font;
	private String titleText;
	/**
	 * Constructor
	 */
	public Title() {
		super();
//		setBackground(new Color(101,115,131));
		setBackground(Color.BLACK);
		font = new Font("SansSerif", Font.PLAIN, 18);
		titleText = "MineSweeper";
	}
	
	


	
	/**
	 * Draws the box and the text using the font.
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.WHITE);
		g.setFont(font);
		
		int horizontalOffset = 
				getSize().width / 2 - 75;
		g.drawString(titleText, horizontalOffset, 34);
		
		
	}
	
	/**
	 * Method that changes the title of the box. Given
	 * an input title.
	 * @param input
	 */
	public void changeTitle(String input) {
		if (input != null) {
			titleText = input;
			repaint();
		}
	}
	
	/**
	 * Resets the title.
	 */
	public void reset() {
		titleText = "MineSweeper";
		repaint();
	}
	
	public String getTitle() {
		return titleText;
	}
	
}
