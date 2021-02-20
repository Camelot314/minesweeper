package gui.Instructions;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

public class TextBox extends JPanel{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3970613770664843617L;
	private Font font;
	private String text;
	private int width, height, fontSize;
	private ArrayList<String> textLines;

	/**
	 * Constructor
	 * @param text
	 * @param fontSize
	 * @param color
	 */
	public TextBox(String text, int fontSize, Color color) {
		super();
		font = new Font("SansSerif", Font.PLAIN, fontSize);
		this.text = text;		
		this.width = 200;
		this.height = 200;
		this.fontSize = fontSize;
		setSize(width, height);
		textLines = new ArrayList<>();
		trimText();
		if (color != null) {
			setBackground(color);
		}
		
	}
	
	/**
	 * Constructor
	 * @param color
	 */
	public TextBox(Color color) {
		this("", 0, color);
	}
	
	/**
	 * Constructor.
	 * @param text
	 * @param color
	 */
	public TextBox(String text, Color color) {
		this (text, 15, color);
	}
	
	/**
	 * Default Constructor.
	 */
	public TextBox() {
		this("", null);
	}
	

	/**
	 * Adds the text to the box.
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.setFont(font);
		int offSet = 25;
		for (String text : textLines) {
			
			g.drawString(text, 15, offSet);
			offSet += fontSize + 5;
		}
		
	}
	
	/**
	 * Used by the layout manager.
	 * @return
	 */
	@Override
	public Dimension getPreferredSize() {
		
		return new Dimension(width, height);
		
	}
	
	/**
	 * Used by the layout manager.
	 */
	@Override
	public Dimension getMinimumSize() {
		return getPreferredSize();
	}
	
	/**
	 * If the input is multiple lines
	 * then it cuts them into separate strings.
	 */
	public void trimText() {
		char char1;
		String toAdd = "";
		for (int i = 0; i < text.length(); i ++) {
			char1 = text.charAt(i);
			
			if (char1 != '\n') {
				toAdd += "" + char1;
			} else {
				textLines.add(toAdd);
				toAdd = "";
			}
		}
		if (!toAdd.isEmpty()) {
			textLines.add(toAdd);
		}
	}
	
	/**
	 * Returns the lines of text
	 * @return
	 */
	public ArrayList<String> getTextLines () {
		ArrayList<String> copy = new ArrayList<>();
		for (String s: textLines) {
			copy.add(s);
		}
		return copy;
	}
}
