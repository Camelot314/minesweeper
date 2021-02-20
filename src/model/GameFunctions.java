package model;
import java.util.Random;
import java.util.ArrayList;

/**
 * Does all the game algorithms.
 * @author kjara
 *
 */
public class GameFunctions {
	public static final char BOMB_CHAR = '*';
	public static final char SECRET_CHAR = ' ';
	public static final char FLAG_CHAR = '/';
	public static final char BLANK_CHAR = '-';
	public static final Random TEST_RANDOM = new Random();
	
	/**
	 * Creates the game key. It will be a 2 dimensional array of char with
	 * the BOMB_CHAR BLANK_CHAR and numbers. The bombs will be added randomly
	 * in spaces around the board. There will never by adding 2 bombs to the same
	 * location and it will always add exactly the number of specified bombs.
	 * It will then add numbers so that any space adjacent to the bomb will have
	 * a value that is equals to the number of bombs next to the square (diagonals
	 * count). Finally it will take any spaces with a value of zero and changes
	 * them to the BLANK_CHAR.
	 * @param row
	 * @param col
	 * @param bombs
	 * @return 2 dimensional array key
	 */
	public static char[][] createMap(int width, int height, int bombs) {
		char[][] map = new char[height][width];
//		Random random = new Random();
		Random random = TEST_RANDOM;
		int bombsAdded = 0;
		
		//Adding the bombs
		while (bombsAdded < bombs) {
			int rowNum = random.nextInt(height);
			int colNum = random.nextInt(width);
			if (isValidLocal(map, rowNum, colNum)) {
				if (map[rowNum][colNum] != BOMB_CHAR) {
					map[rowNum][colNum] = BOMB_CHAR;
					bombsAdded ++;
				}
			}
		}
		addNumbers(map);
		addBlanks(map);
		
		return map;
	}


	/**
	 * Creates the game board which is a 2 dimensional char array.
	 * It is the same size as the key and all the characters will be the 
	 * SECRET_CHAR.
	 * @param key
	 * @return 2 D char array game board
	 */
	public static char[][] createVis(char[][] key) {
		char[][] toReturn = new char[key.length][key[0].length];
		addSecret(toReturn);
		return toReturn;
	}
	
	/**
	 * Prints the map. It will have a column corresponding to the y value on the 
	 * left and at the bottom a corresponding x value. The spaces will be separated
	 * by a spacer. The y values count in the opposite direction as the corresponding
	 * row number. This is only used in the driver.
	 * @param array
	 */
	public static void printMap(char[][] array) {
		
		for (int row = 0; row < array.length; row++) {
			if (array.length > 10 && row > array.length - 11) {
				System.out.print("  " + convertToY(array, row) + "|   ");
			} else {
				System.out.print(" " + convertToY(array,row) + "|   ");
			}
			
			
			for (int col = 0; col < array[row].length; col++) {
				char letter = array[row][col];
				System.out.print(letter);
				addDivider(array, row, col, '|', true);
			}
			if (row != array.length - 1) {
				System.out.println();
			}
		}
		System.out.println();
		System.out.print("     ");
		if (array.length > 10) {
			System.out.print("  ");
		}
		for(int i = 0; i < array.length; i ++ ) {
			System.out.print("-");
			addDivider(array, array.length - 1, i, '-', true);
		}
		System.out.println();
		System.out.print("     ");
		if (array.length > 10) {
			System.out.print("  ");
		}
		for (int col = 0; col < array[0].length; col++) {
			System.out.print(col);
			if (col > 8 ) {
				addDivider(array, 0, col, ' ', false);
			} else {
				addDivider(array, 0, col, ' ', true);
			}
		}
		System.out.println();
		System.out.println();
	}
	
	/**
	 * Will take in the game board. If the corresponding space does not have a 
	 * flag on it, the method will change the gameboard so that the space has a 
	 * flag. If there is already one there then it will remove the flag.
	 * @param array
	 * @param row
	 * @param col
	 * @param numFlags
	 * @return the current number of flags.
	 */
	public static int toggleFlag(char[][] array, int row, int col, int numFlags) {
		char current = array[row][col];
		
		if (current == FLAG_CHAR) {
			array[row][col] = SECRET_CHAR;
			numFlags --;
		} else if (current == SECRET_CHAR){
			array[row][col] = FLAG_CHAR;
			numFlags ++;
		}
		return numFlags;
	}
	
	/**
	 * Recursive method which will reveal the corresponding spaces on the game board.
	 * If one of the spaces is a blank space, or the original location is adjacent
	 * to a blank space then the method will call itself and reveal all adjacent 
	 * spaces to the blank space until it hits some numbers (will continue if blank
	 * is next to another blank).
	 * 
	 * The method will not reveal a location if a flag has been placed at that 
	 * space on the game board. 
	 * 
	 * The method will start a shortcut if the initial selected location to reveal
	 * has already been revealed. If this is true, then it provided that the location
	 * already has enough flags surrounding it, the method will reveal all the 
	 * surrounding spaces.
	 * 
	 * The method will return false if none of the revealed
	 * spaces is bombs. It will return true if at least one is a bomb.
	 * @param visible
	 * @param map
	 * @param row
	 * @param col
	 * @return boolean true if a revealed is a bomb.
	 */
	public static boolean reveal(char[][] visible, char[][] map, int row, int col, 
			ArrayList<int[]> bombCoords) {
		
		boolean bomb = false;
		if(!isValidLocal(visible, row, col)) {
			return false;
		}
		char key = map[row][col];
		if (key != visible[row][col] && visible[row][col] != FLAG_CHAR) {
			bomb = revealRecursive(visible, map, row, col, bomb, false, bombCoords);
		} else {
			bomb = initiateShortCut(visible, map, row, col, bombCoords);
		}

		return bomb;
	}
	
	/**
	 * The method will check if the current game board is equal to the key.
	 * If the key has a bomb in it, then if the game board has the bomb space
	 * either blank or flagged then the method will return true. It will
	 * print the board if the print param is true;
	 * 
	 * @param correct
	 * @param board
	 * @param print
	 * @return true if the board is the same with the exception of bombs.
	 */
	public static boolean win (char[][] board, char[][] correct) {
		boolean win = true;
		for (int row = 0; row < correct.length; row ++) {
			for (int col = 0; col < correct[row].length; col ++) {
				char key = correct[row][col];
				char test = board[row][col];
				
				if (key != BOMB_CHAR) {
					win =  win && key == test; 
				} else {
					win = win && (test == FLAG_CHAR || test == SECRET_CHAR);
				}
				
			}
		}
		return win;
	}
	
	/**
	 * Converts the input x and y coordinates into a row and column coordinates.
	 * This method is used in the driver and not in any methods in this class.
	 * @param array
	 * @param number
	 * @param isRow
	 * @return int
	 */
	public static int convert(char[][] array, int number, boolean isRow) {
		if (isRow) {
			return array.length - 1 - number;
		} else {
			return number;
		}
	}
	
	/**
	 * Shows the correct board when called. Also shows the locations of any incorrect
	 * flags.
	 * @param board
	 * @param key
	 */
	public static void lose(char[][] board, char[][] key, 
			ArrayList<int[]> bombCoords) {
		
		for (int row = 0; row < board.length; row ++) {
			for (int col = 0; col < board[row].length; col ++) {
				char correct = key[row][col];
				
				if (board[row][col] != FLAG_CHAR) {
					board[row][col] = correct;
				} else if (correct != BOMB_CHAR) {
					board[row][col] = correct;
					bombCoords.add(new int[] {row, col});
				}
			}
		}		
	}
	
	/**
	 * This is a method that takes in a char array and will return true if the 
	 * specified location is a valid location. 
	 * @param array
	 * @param row
	 * @param col
	 * @return boolean
	 */
	public static boolean isValidLocal(char[][] array, int row, int col) {
		if (array == null || row < 0 || row > array.length - 1) {
			return false;
		} else if (array[row] == null) {
			return false;
		} else {
			return !(col < 0 || col > array[row].length - 1);
		}
	}
	
	/**
	 * This is a private method that will return a 2 d int array which corresponds
	 * to all the possible row and column coordinates surrounding given location.
	 * @param row
	 * @param col
	 * @return int[][]
	 */
	public static int[][] surroundCoords (int row, int col) {
		int[] rows = {row, row, row + 1, row + 1, row - 1, row - 1, row + 1, row - 1};
		int[] cols = {col + 1, col - 1, col + 1, col - 1, col + 1, col - 1, col , col};
		
		return new int[][] {rows, cols};
	}
	
	
	public static int detectXNextTo(char[][] array, int row, int col, char x, int[][] surrounding) {
		int count = 0;
		surrounding = surrounding == null ? surroundCoords(row, col) : surrounding;
		
		for(int i = 0; i < surrounding[0].length; i ++) {
			int rowNum = surrounding[0][i], colNum = surrounding[1][i];
			if(isValidLocal(array, rowNum, colNum) && array[rowNum][colNum] == x) {
				count ++;
			}
		}
		
		return count;
	}
	

	
	
	
	/**
	 * Converts the row coordinates into a y coordinate.
	 * @param array
	 * @param row
	 * @return int
	 */
	private static int convertToY (char[][] array, int row) {
		return array.length - 1 - row;
	}
	
	/**
	 * Used by the create map method. For every square adjacent to a bomb
	 * it will change the key so that the square will have a number corresponding
	 * to the number of bombs that is surrounding it. 
	 * @param array
	 */
	private static void addNumbers(char[][] array) {
		for (int row = 0; row < array.length; row ++) {
			for (int col = 0; col < array[row].length; col ++) {
				if(array[row][col] != BOMB_CHAR) {
					array[row][col] = ("" + detectBombs(array, row, col)).charAt(0);
				}
			}
		}
	}

	/**
	 * This is the recursive portion of the reveal method. 
	 * @param visible
	 * @param map
	 * @param row
	 * @param col
	 * @param bomb
	 * @return true if a revealed space is a bomb.
	 */
	private static boolean revealRecursive (char[][] visible, char[][] map, 
			int row, int col, boolean bomb, boolean fromBlank, ArrayList<int[]> bombCoords) {
		
		
		if(!isValidLocal(visible, row, col)) {
			return false;
		}
		char key = map[row][col];
		ArrayList<int[]> coordinates = new ArrayList<>();
		if (key != visible[row][col]) {
			if (key == BLANK_CHAR) {
				visible[row][col] = key;
				int[][] coords = surroundCoords(row, col);
				for (int i = 0; i < coords[0].length; i ++) {
					if((isValidLocal(visible, coords[0][i], coords[1][i])) 
							&& !(visible[coords[0][i]][coords[1][i]] == FLAG_CHAR)) {
						
						revealRecursive(
								visible, map, coords[0][i], coords[1][i], 
								bomb, true, bombCoords
							);
					}
					
				}
			}
			if (!hasBlankNextTo(map, visible, row, col, coordinates)) {
				if (key == BOMB_CHAR) {
					if (bombCoords != null) {
						bombCoords.add(new int[] {row, col});
					}
					return true;
				} else {
					bomb = false;
					visible[row][col] = key;
				}
			} else {
				visible[row][col] = key;
				if (!fromBlank) {
					for (int[] coordinate : coordinates) {
						bomb = bomb || revealRecursive(
								visible, map, coordinate[0], coordinate[1], 
								bomb, false, bombCoords
							);
					}
				}
			}

		}
		return bomb;
	}
	
	
	/**
	 * This is the shortcut method of the reveal method. It will also call
	 * the recursive reveal method. 
	 * @param visible
	 * @param map
	 * @param row
	 * @param col
	 * @return true if a revealed space is a bomb.
	 */
	private static boolean initiateShortCut(char[][] visible, char[][] map, 
			int row, int col, ArrayList<int[]> bombCoords) {
		
		boolean bomb = false;
		if (hasEnoughFlags(visible, row, col)) {
			int[][] coords = surroundCoords(row,col);
			for (int i = 0; i < coords[0].length; i ++) {
				if (isValidLocal(visible, coords[0][i], coords[1][i])) {
					if (visible[coords[0][i]][coords[1][i]] != FLAG_CHAR) {
						bomb = revealRecursive(
								visible, map, coords[0][i], coords[1][i], 
								bomb, false, bombCoords
							);
					}
				}
				
			}
		}
		
		return bomb;
		
	}
	
	/**
	 * This method checks if the specified space has enough flags surrounding it.
	 * For example if the space is labeled 1 and has a least 1 flag next to it 
	 * on the game board then it will return true.
	 * @param visible
	 * @param row
	 * @param col
	 * @return true if there are enough flags surrounding the location on the game board.
	 */
	private static boolean hasEnoughFlags(char[][] visible, int row, int col) {
		boolean enough = false;
		boolean isNumber = Character.isDigit(visible[row][col]);
		
		int count = 0; 
		int needed = isNumber ? Integer.parseInt("" + visible[row][col]) : 0;
		int[][] coords = surroundCoords(row, col);
		
		for (int i = 0; i < coords[0].length; i ++) {
			if (isValidLocal(visible, coords[0][i], coords[1][i])) {
				if (visible[coords[0][i]][coords[1][i]] == FLAG_CHAR) {
					count ++;
				}
			}
			
		}
		if (needed <= count) {
			enough = true;
		}
		
		return enough;
	}
	
	/**
	 * Returns true if the specified location has a blank next to it.
	 * @param map
	 * @param revealed
	 * @param row
	 * @param col
	 * @param coordinates
	 * @return boolean
	 */
	private static boolean hasBlankNextTo (char[][] map, char[][] revealed, 
			int row, int col, ArrayList<int[]> coordinates) {
		
		boolean toReturn = false;
		int [][] coords = surroundCoords(row, col);
		for(int i = 0; i < coords[0].length; i ++) {
			if (isValidLocal(map, coords[0][i], coords[1][i])) {
				if (map[coords[0][i]][coords[1][i]] == BLANK_CHAR) {
					if (revealed[coords[0][i]][coords[1][i]] == SECRET_CHAR) {
						coordinates.add(new int[] {coords[0][i], coords[1][i]});
						toReturn = toReturn || map[coords[0][i]][coords[1][i]] == BLANK_CHAR;
					}
				}
			}
		}
		
		return toReturn;
	}
	
	/**
	 * This is used by the print method. It will add the specified divider if the
	 * addDivider boolean is true. Otherwise it will only add a space. This method
	 * is a void method and will just print.
	 * @param array
	 * @param row
	 * @param col
	 * @param addDivider
	 */
	private static void addDivider(char[][] array, int row, int col, 
			char divider, boolean addDivider) {
		
		if (col != array[row].length - 1) {
			if (addDivider) {
				System.out.print(" " + divider + " ");
			} else {
				System.out.print("  ");
			}
		}
	}
	
	/**
	 * This is a void method that takes in the key and if a location has a value
	 * of zero it will change the char value to BLANK_CHAR.
	 * @param map
	 */
	private static void addBlanks(char[][] map) {
		for (int i = 0; i < map.length; i ++) {
			for(int k = 0; k < map[i].length; k ++) {
				char toChange = map[i][k];
				if(toChange == '0') {
					map[i][k] = BLANK_CHAR;
				}
			}
		}
	}
	
	/**
	 * This is a void method that is used by the createVis method. It will change
	 * every space to the SECRET_CHAR.
	 * @param map
	 */
	private static void addSecret(char[][] map) {
		for (int i = 0; i < map.length; i++) {
			for (int k = 0; k < map[i].length; k++) {

				map[i][k] = SECRET_CHAR;

			}
		}
	}

	
	/**
	 * This is a private method used by add Numbers. It will return a count 
	 * of the number of bombs surrounding the specified location. Diagonals count.
	 * @param array
	 * @param row
	 * @param col
	 * @return int
	 */
	private static int detectBombs(char[][] array, int row, int col) {
		return detectXNextTo(array, row, col, BOMB_CHAR, null);
	}
	
}
