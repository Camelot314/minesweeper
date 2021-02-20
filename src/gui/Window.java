package gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import model.GameObject;

public class Window extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = -604725187146294335L;
	private StatusBar statusBar;
	private MineField mineField;
	private GameObject game;
	public int cellSize; 
	
	/**
	 * Initializes everything. Every class
	 * in the GUI should only have a reference
	 * to the exact same game that is created here.
	 */
	public Window (int rows, int cols, int cellSize, String difficulty) {
		super("MineSweeper " + rows + "x" + cols + " " + difficulty.toUpperCase());
		this.cellSize = cellSize;
		
		if (difficulty.equals("extreme")) {
			game = new GameObject(rows, cols, 5);
		} else if (difficulty.equals("hard")) {
			game = new GameObject(rows,cols, 7);
		} else if (difficulty.equals("medium")) {
			game = new GameObject(rows, cols, 9);
		} else {
			game = new GameObject(rows, cols, 12);
		}
		
		statusBar = new StatusBar(game, this);
		mineField = new MineField(game, this);
		
		add(mineField, BorderLayout.NORTH);
		add(statusBar, BorderLayout.CENTER);
		
		int width = cols * cellSize;
		int height = rows * cellSize + 100;
		
		width = width < 400 ? 400 : width;
		
		setSize(width, height);
		setVisible(true);
	}
	
	/**
	 * Will be called by the reset button.
	 * This will then tell the MineField to reset;
	 */
	public void newGame() {
		game.newGame();
		statusBar.reset();
		mineField.reset();
	}
	
	/**
	 * Calls the Change bomb count method of the 
	 * status bar.
	 */
	public void changeBombNumber() {
		statusBar.changeBombCount();
	}
	
	/**
	 * Changes the title of the window depending on 
	 * the current game status.
	 * @param gameStatus
	 */
	public void changeTitle(boolean[] gameStatus) {
		if (gameStatus[2] == true) {
			if (gameStatus[0] == true) {
				statusBar.changeTitle("YOU LOSE");
			} else {
				statusBar.changeTitle("YOU WIN");
			}
		}
	}
	
	/** 
	 * Everything below this point is so that the computer 
	 * can actually interact with and play the game.
	 */
	
	
	/**
	 * Returns a copy of the game board not the same one;
	 * @return
	 */
	public char[][] showBoard() {
		return game.getBoard();
	}
	
	public void showBoard(char[][] given) {
		game.updateGiven(given);
	}
	
	/**
	 * Returns the status of the current game;
	 * @return
	 */
	public boolean[] getGameStatus() {
//		System.out.println(Arrays.toString(game.getStatus()));
		return game.getStatus();
		
		
	}
	
	/**
	 * Returns the number of bombs left in the game;
	 * @return
	 */
	public int getBombsRemaining() {
		return game.getBombsRemaining();
	}
	
	/**
	 * hits the mineButton specified
	 * @param row
	 * @param col
	 */
	public void pressButton(int row, int col, boolean isRight) {
		mineField.hitButton(row, col, isRight);
	}
	
	
}
