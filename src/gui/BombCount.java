package gui;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * The box for displaying the current bomb count.
 * @author kjara
 *
 */
public class BombCount extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1366285109694383656L;
	private int numBombs = 0;
	private Font numberFont;
	
	/**
	 * constructor that is initialized with a number to start
	 * the count with. It also changes the font.
	 * @param numBombs
	 */
	public BombCount(int numBombs) {
		super();
		this.numBombs = numBombs;
		numberFont = new Font("SansSerif", Font.PLAIN, 18);
		
	}
	
	/**
	 * Draws the box and the text using the font.
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.fillRect(25, 15, 45, 30);
		g.setColor(Color.RED);
		g.setFont(numberFont);
		g.drawString(Integer.toString(numBombs), 36, 34);
		
		
	}
	
	/**
	 * Changes the number of bombs and repaints the box.
	 * @param newBombs
	 */
	public void setBombs (int newBombs) {
		numBombs = newBombs < 0 ? 0 : newBombs;
		repaint();
	}
	
	/**
	 * Will return the current number being displayed.
	 * @return
	 */
	public int getBombs() {
		return numBombs;
	}
	
	/**
	 * Size used by the layout manager.
	 */
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(100, 50);
	}
	
	/**
	 * Used by the layout manager.
	 */
	@Override
	public Dimension getMinimumSize() {
		return getPreferredSize();
	}
}
