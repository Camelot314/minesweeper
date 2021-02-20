package gui;

import javax.swing.JFrame;


import ai.Brain;
import gui.Instructions.InstructionsPane;
/**
 * GUI version of the game.
 * Right click to add a bomb
 * left click to reveal a square.
 * You can change the field size by changing rows and cols
 * You can change the difficulty by the word in the Window constructor.
 * It goes from easy, medium, hard, to extreme.
 * @author kjara
 *
 */
public class Gui {

	/**
	 * Main method that starts up the input dialog box.
	 * @param args
	 */
	public static void main(String[] args) {
		InstructionsPane instructions = new InstructionsPane();
		instructions.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	/**
	 * Method that takes the inputs given and parses them and 
	 * starts up the mine sweeper gui.
	 * @param input
	 */
	private static void getSelections(InstructionsPane input) {
		int rows = Integer.parseInt(input.get('h'));
		int cols = Integer.parseInt(input.get('w'));
		String difficulty = input.get('d');
		
		// if all icons exists then decreases
		// cell size. Otherwise they stay big
		// because 45 is min size to read text.
		// the icons themselves are 25 px in size.
		int cellSize = IconLoader.allExist() ? 25 : 45;
		Window mineSweeper = new Window(rows, cols, cellSize, difficulty);
		
		mineSweeper.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		/**
		 * Comment out the next two lines to run the robot that will try to solve the game. 
		 * 
		 */
		Brain ai = new Brain(mineSweeper);
		ai.start();

		
		
		
		
	}
	
	/**
	 * Method called by the input dialogue to initiate the parsing
	 * of the input and starting of the game.
	 * @param instructions
	 */
	public static void startUp (InstructionsPane instructions) {
		getSelections(instructions);
	}

}
