package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import model.GameObject;

/**
 * The status bar is made of 3 parts for testing purposes, 
 * It will eventually only have 2 parts. The left side
 * contains a box that will have the number of the current
 * number of bombs. Box is another JPanel of the class
 * BombCount. The center and right side has 2 buttons,
 * one resets the bomb count and the other adds to it
 * (for testing to check to see if buttons worked). 
 * @author kjara
 *
 */
public class StatusBar extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3440987649558880452L;
	private BombCount bombCount;
	private JButton reset;
	private GameObject game;
	private Title title;
	/**
	 * The constructor. It initializes the bar
	 * with the box for the bomb count and 2 buttons
	 * for adding and resetting the comb count.
	 * The default bomb count is currently hat 40.
	 * 
	 */
	public StatusBar(GameObject game, Window bridge) {
		super();
		this.game = game;
		setBackground(new Color(101,115,131));
		setLayout(new BorderLayout());
		bombCount = new BombCount (game.getBombsRemaining());
		bombCount.setBackground(new Color(101,115,131));
			
		reset = new JButton("Reset");
		reset.setForeground(Color.WHITE);
		reset.setBackground(new Color(101,115,131));
		reset.addActionListener(
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						bridge.newGame();
					}
				}
		);
		
		title = new Title();
		add(title, BorderLayout.CENTER);
		add(reset, BorderLayout.EAST);
		add(bombCount, BorderLayout.WEST);
	}
	
	/**
	 * Given an input string it will change the title of the game
	 * to the input string.
	 * @param input
	 */
	public void changeTitle(String input) {
		title.changeTitle(input);
	}
	
	/**
	 * Changes the bomb count to the number of bombs remaining
	 * in the game count.
	 */
	public void changeBombCount() {
		bombCount.setBombs(game.getBombsRemaining());
	}
	
	/**
	 * Resets the title and the bomb count;
	 */
	public void reset() {
		changeBombCount();
		title.reset();
	}
	
	public String getTitle() {
		return title.getTitle();
	}
}
