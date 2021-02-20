package model;

import java.util.ArrayList;

/**
 * The object that holds all aspects of the individual game
 * going on.
 * @author kjara
 *
 */
public class GameObject {
	private char[][] board, key;
	private int bombs, bombsRemaining, numFlags;
	private boolean[] gameStatus;
	// {lose, win, over};
	
	/**
	 * Constructor for every param
	 * @param width
	 * @param height
	 * @param bombs
	 */
	public GameObject(int height, int width, int frequency) {
		bombs = width * height / frequency;
		bombsRemaining = bombs;
		key = GameFunctions.createMap(width, height, bombs);
		board = GameFunctions.createVis(key);
		gameStatus = new boolean[3];
	}
	
	
	/**
	 * Constructor for height and with but no bombs
	 * @param width
	 * @param height
	 */
	public GameObject(int height, int width) {
		this(width, height, ((width * height) / 7));
	}
	
	/**
	 * Constructor for only size;
	 * @param size
	 */
	public GameObject(int size) {
		this(size, size);
	}
	
	/**
	 * default constructor.
	 */
	public GameObject() {
		this(40);
	}
	
	/**
	 * Returns a deep copy of the game board;
	 * @return
	 */
	public char[][] getBoard() {
		char [][] temp = new char[board.length][];
		
		for (int row = 0; row < board.length; row ++) {
			char[] tempRow = new char[board[row].length];
			
			for ( int col = 0; col < board[row].length; col ++) {
				tempRow[col] = board[row][col];
			}
			temp[row] = tempRow;
		}
		
		return temp;
	}
	
	/**
	 * Returns the width of the gameBoard
	 * @return
	 */
	public int getWidth() {
		return board[0].length;
	}
	
	/**
	 * Returns the height of the board.
	 * @return
	 */
	public int getHeight() {
		return board.length;
	}
	
	/**
	 * returns the nubmber of bombs left on the board
	 * @return
	 */
	public int getBombsRemaining() {
		return bombsRemaining;
	}
	
	/**
	 * Will toggle the flag at the specified location.
	 * Will return true if the flag has been toggled. Will
	 * return false if trying to add an additional flag
	 * when there are no more flags remaining. 
	 * @param row
	 * @param col
	 * @return
	 */
	public boolean flag(int row, int col) {
		boolean toReturn = false;
		if (board[row][col] == GameFunctions.FLAG_CHAR) {
			numFlags = GameFunctions.toggleFlag(board, row, col, numFlags);
			toReturn = true;
		} else {
			if (numFlags == bombs) {
				toReturn = false;
			} else {
				numFlags = GameFunctions.toggleFlag(board, row, col, numFlags);
				toReturn =  true;
			}
			
			
		}
		
		bombsRemaining = bombs - numFlags;
		return toReturn;
	}
	
	/**
	 * Does the flag method but converts the 1 digit id into 
	 * row and col. The very first id of 0 is the top left 
	 * location. 
	 * @param id
	 * @return
	 */
	public boolean flag(int id) {
		
		int [] location = convertBase(id);
		
		return flag (location[0], location[1]);
		
	}
	
	/**
	 * Reveals the specified location
	 * @param row
	 * @param col
	 * @return
	 */
	public boolean[] reveal (int row, int col, ArrayList<int[]> bombCoords) {
		// toReturn  = [lose, win, over];
		
		
		gameStatus[0] = GameFunctions.reveal(board, key, row, col, bombCoords);
		// check comment for info
		gameStatus[1] = gameStatus[0] ? false : GameFunctions.win(board, key);
		gameStatus[2] = gameStatus[0] || gameStatus[1];
		
		
		boolean[] gameOutcome = getStatus();
		
		if (gameOutcome[0]) {
			GameFunctions.lose(board, key, bombCoords);
		}
		if (gameOutcome[1]) {
			GameFunctions.win(board, key);
		}
		
		return gameOutcome;
	}


	/**
	 * Returns the gameStatus. It is a copy.
	 */
	public boolean[] getStatus() {
		boolean [] gameOutcome = new boolean[3];
		for (int i = 0; i < gameStatus.length; i ++) {
			gameOutcome[i] = gameStatus[i];
		}
		return gameOutcome;
	}

	
	
	
	/**
	 * Reveals the location using an id;
	 * @param id
	 * @return
	 */
	public boolean[] reveal(int id, ArrayList<int[]> bombCoords) {
		int[] location = convertBase(id);
		
		return reveal(location[0], location[1], bombCoords);
	}
	
	/**
	 * Reveals the location without the input of an ArrayList.
	 * @param id
	 * @return
	 */
	public boolean[] reveal(int id) {
		return reveal(id, null);
	}
	
	/**
	 * reveals the specified coordinates without the input of an arraylist.
	 * @param row
	 * @param col
	 * @return
	 */
	public boolean[] reveal(int row, int col) {
		return reveal(row, col, null);
	}
	

	/**
	 * Resets the game board and key for a new game;
	 */
	public void newGame() {
		int height = getHeight();
		int width = getWidth();
		bombsRemaining = bombs;
		numFlags = 0;
		
		key = GameFunctions.createMap(width, height, bombs);
		board = GameFunctions.createVis(key);
		gameStatus = new boolean[3];
	}
	
	/**
	 * Returns the specific char value of the visible board
	 * at the specified coordinate.
	 * @param row
	 * @param col
	 * @return
	 */
	public char getCharOf (int row, int col) {
		return board[row][col];
	}
	
	/**
	 * Does the same thing as above but takes in a 
	 * single id param.
	 * @param id
	 * @return
	 */
	public char getCharOf(int id) {
		int[] coords = convertBase(id);
		
		return getCharOf(coords[0], coords[1]);
	}
	
	/**
	 * converts the base 10 id for the location into the row and
	 * column coordinates. It does this by changing them into the base
	 * (size of the row length) and then the first number is the row number
	 * while the last number is the col number. An Id of 0 corresponds
	 * to the top left location and the last id is the bottom right location.
	 * @param id
	 * @return int array with the first digit being the row coordinate
	 * and the second digit being the col coordinate. 
	 */
	public int[] convertBase(int id) {
		int boardWidth = getWidth();
		int col = id % boardWidth;
		int place, row;
		
		place = row = 0;
		while (id >= boardWidth) {
			id -= boardWidth;
			place ++;
		}
		row = place;
		
		
		return new int[] {row, col};
	}
	
	/**
	 * An attempt to make the get board faster and more memory efficient
	 * because an object isn't being created. 
	 * @param array
	 */
	public void updateGiven(char[][] array) {
		if (
				array.length == board.length 
				|| array[0] != null
				|| array[0].length == board[0].length
			) {
				for (int row = 0; row < array.length; row ++) {
					for (int col = 0; col < array[row].length; col ++) {
						array[row][col] = board[row][col];
					}
				}
			}
		else {
			throw new IllegalArgumentException("invalid input to updateGiven");
		}
	}
	
}
