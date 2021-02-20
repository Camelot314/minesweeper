package gui.Instructions;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import gui.Gui;

public class InstructionsPane extends JFrame{
	private Selector difficulty, horizontalSize, verticalSize;
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6025552846097653604L;

	/**
	 * Creates a frame that is in a grid. It has the instructions
	 * plus 3 combo boxes for selecting difficulty and size.
	 */
	public InstructionsPane() {
		Color silver = new Color(192,192,192);
		TextBox difText = new TextBox("Difficulty", silver);
		difficulty = new Selector(new String[] {"Easy", "Medium", "Hard", "Extreme"});
		
		
		String [] sizeSelections = new String[100];
		for (int i = 0; i < sizeSelections.length; i ++) {
			sizeSelections[i] = Integer.toString((i + 1));
		}
		
		TextBox hozText = new TextBox("Horizontal Size", silver);
		horizontalSize = new Selector(sizeSelections);
		
		
		TextBox vertText = new TextBox("Vertical Size", silver);
		verticalSize = new Selector(sizeSelections);
		
		JPanel confirmPanel = new JPanel();
		JButton confirm = new JButton("OK");
		confirm.setSize(new Dimension(45,45));
		confirm.addActionListener(
				/**
				 * ActionListner object to detect button clicks.
				 */
				new ActionListener() {
					/**
					 * When button is clicked it calls the onTap function.
					 */
					@Override
					public void actionPerformed(ActionEvent e) {
							confirmation();
					}
				}
		);
		confirmPanel.add(confirm);
		
		
		setTitle("Instructions");
		setSize(500, 500);
		setLayout(new GridLayout(0,2));
		
		add(new TextBox("Instructions:", 18, silver));
		add(new TextBox());
		add(new TextBox("Left Click to Reveal\nRight Click to Flag", 15, silver));
		add(new TextBox());
		
		add(difText);
		add(difficulty);
		add(hozText);		
		add(horizontalSize);
		add(vertText);
		add(verticalSize);
		add(new TextBox(silver));
		
		add(confirmPanel);
				
		setVisible(true);
	}
	
	/**
	 * Will return the inputs of the Combo Boxes.
	 * @param key
	 * @return String representing the input.
	 */
	public String get(char key) {
		String toReturn;
		switch(key) {
			case 'd' : toReturn = difficulty.getSelection();
				break;
			case 'w' : toReturn = horizontalSize.getSelection();
				break;
			case 'h': 	toReturn = verticalSize.getSelection();
				break;
			default: 	toReturn = null;
		}
		return toReturn;
	}
	
	/**
	 * Method that is called when the Okay button is pressed.
	 * This starts the minesweeper game.
	 */
	public void confirmation() {
		setVisible(false);
		Gui.startUp(this);
	}
	
}
