package gui;

import javax.swing.JPanel;
import model.GameObject;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;

/**
 * JPanel that contains all the buttons for the mines.
 * @author kjara
 *
 */
public class MineField extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4485095031396157919L;
	private ArrayList<MineButton> mines;
	private int maxMines, rows, cols;
	private GameObject game;
	private Window bridge;
	private ArrayList <int[]> incorrectBombs;
	
	/**
	 * Constructor initializes the board
	 * with 25 buttons in a square (for testing).
	 * Gives each location a button with an id
	 * and adds the button to an arrayList. Takes 2 params,
	 * one is a reference to the game so it can convert 
	 * from a 2 coordinate system to the one digit ID.
	 * It takes a bridge param which is a reference to the 
	 * window so it can talk to all the other parts of the 
	 * GUI.
	 * @param game
	 * @param bridge
	 */
	public MineField(GameObject game, Window bridge) {
		super();
		this.bridge = bridge;
		this.game = game;
		mines = new ArrayList<>();
		incorrectBombs = new ArrayList<>();
		rows = game.getBoard().length;
		cols = game.getBoard()[0].length;
		maxMines = rows * cols;
		
		setLayout( new GridLayout(rows,cols));
		
		for (int i = 0; i < maxMines; i ++) {
			MineButton temp = new MineButton(i, game, this, bridge);
			mines.add(temp);
			add(temp);			
		}
		
	}
	
	/**
	 * This is the method called by every button when the button is 
	 * pressed. In order to update the whole board. If the arrayList
	 * of incorrect coords is larger than zero then it will go through
	 * the coordinate list, convert the coordinate into an id and then
	 * call the highlight function on the mines that were on the list. 
	 */
	public void changeField() {
		for (int i = 0; i < mines.size(); i ++) {
			mines.get(i).changeIcon();
		}
		if (incorrectBombs.size() > 0) {
			for (int i = 0; i < incorrectBombs.size(); i ++) {
				
				int idToChange = convertToId(incorrectBombs.get(i)[0], incorrectBombs.get(i)[1]);
				mines.get(idToChange).changeColor(true);
			}
		}
	}
	
	/**
	 * Hits the specified button. If isRight is true then it 
	 * right clicks the specified button. If false then it left clicks it.
	 * @param row
	 * @param col
	 * @param isRight
	 */
	public void hitButton(int row, int col, boolean isRight) {
		int idToHit = convertToId(row, col);
		
		if(!isRight) {
			mines.get(idToHit).computerLeftClick();
		} else {
			mines.get(idToHit).computerRightClick();
		}
		
	}
		
	/**
	 * Used to Reset everything.
	 */
	public void reset() {
		incorrectBombs.clear();
		changeField();
		for (int i = 0; i < mines.size(); i ++) {
			mines.get(i).changeColor(false);
		}
	}
	
	/**
	 * size used by layout manager.
	 */
	public Dimension getPreferredSize() {
		return new Dimension(bridge.cellSize * cols, bridge.cellSize * rows);
	}
	
	/**
	 * Used by the layout manager.
	 */
	public Dimension getMinimumSize() {
		return getPreferredSize();
	}
	
	/**
	 * Get method used by the mine button so it can log all
	 * the incorrect bomb coords.
	 * @return a reference to the same ArrayList incorrectBombs.
	 */
	public ArrayList<int[]> getIncorrectCoords() {
		return incorrectBombs;
	}
	
	/**
	 * Takes a row and col coordinate and returns a 1D id.
	 * @param row
	 * @param col
	 * @return
	 */
	private int convertToId(int row, int col) {
		return row * game.getWidth() + col;
		
	}
}
