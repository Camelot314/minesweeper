package ai;

import java.util.ArrayList;
import java.util.Random;

import javax.swing.SwingUtilities;

import gui.Window;
import model.GameFunctions;

/**
 * This is the object that houses the algorithm for the minesweeper solver.
 * @author kjara
 *
 */
public class Brain {

	private Window game;
	private char[][] currentBoard;
	private int[] workingCoords;
	private int[][] surroundingCoords;
	private Random random;
	private ArrayList<int[]> coordsLeft;
	
	public Brain (Window gui) {
		
		random = new Random();
		
		
		
		this.game = gui;
		currentBoard = gui.showBoard();
		workingCoords = new int[2];
		workingCoords[0] = random.nextInt(currentBoard.length);
		workingCoords[1] = random.nextInt(currentBoard[0].length);
		coordsLeft = new ArrayList<>();
		
		
			// {row, col}
		int row = getRow();
		int col = getCol();
		int[] rows = {row, row, row + 1, row + 1, row - 1, row - 1, row + 1, row - 1};
		int[] cols = {col + 1, col - 1, col + 1, col - 1, col + 1, col - 1, col , col};
		
		surroundingCoords = new int[][] {rows, cols};
		reveal(getRow(), getCol());
		
	}
	
	/**
	 * Super long and complicated but it is very memory and speed efficient.
	 * Essencially it does the same thing as but uses much less memory:
	 * 
	 * int[] rows = {row, row, row + 1, row + 1, row - 1, row - 1, row + 1, row - 1};
	 * int[] cols = {col + 1, col - 1, col + 1, col - 1, col + 1, col - 1, col , col};
	 * 
	 * because it wont create 2 new objects every time it is called.
	 * This method will be called A LOT so this should save a lot of time.
	 * 
	 */
	private void setSurrounding() {
		// Row Array
		
		surroundingCoords[0][0] = getRow();
		surroundingCoords[0][1] = surroundingCoords[0][0];
		
		surroundingCoords[0][2] = getRow() + 1;
		surroundingCoords[0][3] = surroundingCoords[0][2];
		
		surroundingCoords[0][4] = getRow() - 1;
		surroundingCoords[0][5] = surroundingCoords[0][4];
		
		surroundingCoords[0][6] = surroundingCoords[0][2];
		surroundingCoords[0][7] = surroundingCoords[0][4];
		
		// Col array
		
		surroundingCoords[1][0] = getCol() + 1;
		surroundingCoords[1][1] = getCol() - 1;
		
		surroundingCoords[1][2] = surroundingCoords[1][0];
		surroundingCoords[1][3] = surroundingCoords[1][1];
		
		surroundingCoords[1][4] = surroundingCoords[1][0];
		surroundingCoords[1][5] = surroundingCoords[1][1];
		
		surroundingCoords[1][6] = getCol();
		surroundingCoords[1][7] = surroundingCoords[1][6];
		
		/** 
		 * This can be done in a for loop but that would require
		 * making two new objects which is not efficient. I need
		 * Everything here to run hella fast.
		 */
	}
	
	/**
	 * Starts the solver.
	 */
	public void start() {
		reveal(getRow(), getCol());
		boolean finished = false;
		int count = 0;
		while (!finished) {
			if (count != 0) {
				revealRandom();
			}
			count ++;
			simpleFlagLoop();
//			System.out.println("round " + count);
			finished = true;
			coordsLeft.clear();
			for (int row = 0; row < getRow(); row ++) {
				if (!finished) {
					break;
				}
				for (int col = 0; col < getCol(); col ++) {
					if (currentBoard[row][col] == GameFunctions.SECRET_CHAR) {
						
						finished = false;
						coordsLeft.add(new int[] {row, col});
					}
					
				}
			}
		}


		
	}

	/**
	 * The loop that is called while the solver is running. 
	 * This was to prevent having nested while loops which
	 * looks bad.
	 */
	private void simpleFlagLoop() {
		boolean didSomething = true;
//		int count = 0;
		while (didSomething && !game.getGameStatus()[2]) {
			didSomething = simpleFlags();
			SwingUtilities.updateComponentTreeUI(game);
//			if (didSomething) {
//				System.out.println("did something " + count);
//			} else {
//				System.out.println("can't change");
//			}
//			count ++;
		}
	}
	
	/**
	 * Kinda complicated:
	 * 2 Rules:
	 * 1) if there are the same number of secret or flag chars next to a 
	 * 		coordinate as the number of the coordinate. Then all surrounding 
	 * 		locations are bombs.
	 * 2) if a position is touching the same number of bombs as its number
	 * 		then reveal that current position.
	 * @return boolean true if action was performed, false otherwise.
	 */
	private boolean simpleFlags() {
		boolean didSomething = false;
		for (int row = 0; row < currentBoard.length; row ++) {
			for (int col = 0; col < currentBoard[row].length; col ++) {
				setCoord(row, col);
				
				char working = currentBoard[getRow()][getCol()];
				if (Character.isDigit(working)) {
					int digit = Integer.parseInt(Character.toString(working));
					int surrSecOrBomb = secOrBombNextTo(getRow(), getCol());
					int surrSec = getCountAdj(GameFunctions.SECRET_CHAR);

					if (surrSec == 0) {
						continue;
					}
					
					int surroundFlag = getCountAdj(GameFunctions.FLAG_CHAR);
					if (digit == surroundFlag) {
						reveal(getRow(), getCol());
						didSomething = true;
					}
					
					
					
					if (surrSecOrBomb <= digit) {
						didSomething = true;
						for (int i = 0; i < surroundingCoords[0].length; i ++) {
							flag(surroundingCoords[0][i], surroundingCoords[1][i]);
						}
					}
					
					
					
				}
			}
		}
		return didSomething;
		
	}
	
	/**
	 * This is the algorithm that does all the revealing. 
	 * @param reveal
	 * @return
	 */
	public boolean simple(boolean reveal) {
		boolean didSomething = false;
		for (int row = 0; row < currentBoard.length; row ++) {
			for (int col = 0; col < currentBoard[row].length; col ++) {
				setCoord(row, col);
				
				char working = currentBoard[getRow()][getCol()];
				if (Character.isDigit(working)) {
					int digit = Integer.parseInt(Character.toString(working));
					int surrSecOrBomb = secOrBombNextTo(getRow(), getCol());
					int surrSec = getCountAdj(GameFunctions.SECRET_CHAR);

					if (surrSec == 0) {
						continue;
					}
					
					if (reveal) {
						int surroundFlag = getCountAdj(GameFunctions.FLAG_CHAR);
						if (digit == surroundFlag) {
							reveal(getRow(), getCol());
							didSomething = true;
						}
					} else {
						if (surrSecOrBomb <= digit) {
							didSomething = true;
							for (int i = 0; i < surroundingCoords[0].length; i ++) {
								flag(surroundingCoords[0][i], surroundingCoords[1][i]);
							}
						}
					}					
				}
			}
		}
		return didSomething;
	}
	
	
	private void reveal (int row, int col) {
		game.pressButton(row, col, false);
		currentBoard = game.showBoard();
	}
	
	/**
	 * Flags the specified locations.
	 * @param row
	 * @param col
	 */
	private void flag (int row, int col) {
		if (GameFunctions.isValidLocal(currentBoard, row, col)) {
			if (currentBoard[row][col] == GameFunctions.SECRET_CHAR) {
				game.pressButton(row, col, true);
				currentBoard = game.showBoard();

			}
			
		}
	}
	
	
	/**
	 * Returns the row value of the working coords
	 * @return
	 */
	private int getRow() {
		return workingCoords[0];
	}
	
	/**
	 * Returns the col value of the working coords.
	 * @return
	 */
	private int getCol() {
		return workingCoords[1];
	}
	
	/**
	 * Sets the working coords to the input values
	 * @param row
	 * @param col
	 */
	private void setCoord(int row, int col) {
		workingCoords[0] = row;
		workingCoords[1] = col;
		setSurrounding();
	}
	
	/**
	 * Returns the number of secret locations around the current working char;
	 * @param row
	 * @param col
	 * @return number of secrets or bombs next to;
	 */
	private int secOrBombNextTo(int row, int col) {
		int count = 0;
		count += GameFunctions.detectXNextTo(currentBoard, row, col, GameFunctions.FLAG_CHAR, surroundingCoords);
		count += GameFunctions.detectXNextTo(currentBoard, row, col, GameFunctions.SECRET_CHAR, surroundingCoords);		
		return count;
	}
	
	/**
	 * Returns the number of times the char appears around the coord
	 * @param key
	 * @return number of times occurs.
	 */
	private int getCountAdj(char key) {
		return GameFunctions.detectXNextTo(currentBoard, getRow(), getCol(), key, surroundingCoords);
	}
	
	/**
	 * Reveals a random new postion on the board. 
	 */
	private void revealRandom() {
		int row, col;		
		
		int nextSet = random.nextInt(coordsLeft.size());
		row = coordsLeft.get(nextSet)[0];
		col = coordsLeft.get(nextSet)[1];
		reveal(row, col);
		
		coordsLeft.clear();
	}
	
	

}
