package model;
//import java.util.Scanner;

/**
 * This provides a non GUI version of the game
 * Only uncomment once.
 * you have to specify coordinates.
 * @author kjara
 *
 */
public class Driver {

//	public static void main(String[] args) {
//		Scanner sc = new Scanner(System.in);
//		System.out.print("How Big? ");
//		int size = sc.nextInt();
//		GameObject game = new GameObject(size);
//		
//		input(sc, game);
//		
//		sc.close();
//	}
//	
//	/**
//	 * This will keep calling the input method until they quit.
//	 * @param sc
//	 * @param key
//	 * @param vis
//	 * @param bombs
//	 */
//	public static void input(Scanner sc, GameObject game) {
//		boolean breakLoop = false;
////		ArrayList<int[]> bombCoords= new ArrayList<>();
//		boolean[] gameOutcome = new boolean[] {false, false, false};
//		// gameOutcome = {lose, win, over}
//		
//		while (gameOutcome[2] == false) {
//			System.out.println();
//			System.out.println("Board Number of Bombs remaining: " + game.getBombsRemaining());
//			GameFunctions.printMap(game.getBoard());
//			System.out.print("Reveal (R), Flag (F), Quit (Q): ");
//			
//			char input = sc.next().toLowerCase().charAt(0);
//			if (input == 'q') {
//				breakLoop = true;
//				System.out.println("Quitting...");
//				break;
//			} else if (input == 'r' || input == 'f') {
//				System.out.print("Position (x y): ");
//				int xPos = sc.nextInt();
//				int yPos = sc.nextInt();
//				
//				int row = GameFunctions.convert(game.getBoard(), yPos, true);
//				int col = GameFunctions.convert(game.getBoard(), xPos, false);
//				
//				if (input == 'r') {
//					gameOutcome = game.reveal(row, col);	
//				} else if (!game.flag(row, col)){
//					System.out.println();
//					System.out.println("-------No remaining flags--------");
//				}
//			} else {
//				continue;
//			}
//		}
//		
//		if(!breakLoop) {
//			System.out.print("Play again? (Y/N): ");
//			char answer = sc.next().toLowerCase().charAt(0);
//			if (answer == 'y') {
//				game.newGame();;
//				
//				input(sc, game);
//			}
//		}
//	}

}
