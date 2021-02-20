package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

import org.junit.jupiter.api.Test;

import gui.IconLoader;
import gui.Instructions.TextBox;
import model.GameFunctions;
import model.GameObject;

class GameTests {

	@Test
	void testIdConverter() {
//		GameObject testGame = new GameObject(5);
		int[] bases = new int[] {5, 7, 10, 40, 27, 25};
		int[] ids = new int [] {13, 35, 67, 342, 433, 18};
		int [][] correctCoords = new int[][] {
			{2,3},
			{5,0},
			{6,7},
			{8,22},
			{16,1},
			{0, 18}
		};
		for (int i = 0; i < bases.length; i ++) {
			testConvertorAux(new GameObject(bases[i]), ids[i], correctCoords[i]);
		}
		
		
		
	}
	
	@Test
	public void iconLoaderAllExists() {
		assertTrue(IconLoader.allExist());
	}
	
	
	
	@Test
	public void displayIcon() {
		JFrame frame = new JFrame("DefaultButton");
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    ImageIcon testImage = IconLoader.get(GameFunctions.FLAG_CHAR);
	    
	    JButton button = new JButton();
	    
	    button.setIcon(testImage);
	    button.addActionListener(
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						button.setIcon(IconLoader.get(GameFunctions.BOMB_CHAR));
						
					}
				}
		);
	    frame.add(button);
	    frame.setSize(300, 200);
	    frame.setVisible(true);
	    Scanner sc = new Scanner(System.in);
	    System.out.println("Type something");
	    sc.next();
	    sc.close();
	}
	
	@Test
	public void textBoxConstructor() {
		TextBox test = new TextBox("Left Click to Reveal\nRight Click to Flag", 20, Color.DARK_GRAY);
		
		ArrayList<String> returned = test.getTextLines();
		String answer = returned.toString();
		String correct = "[Left Click to Reveal, Right Click to Flag]";
		assertTrue(answer.equals(correct));
		
		test = new TextBox("Difficulty", 20, Color.DARK_GRAY);
		
		returned = test.getTextLines();
		answer = returned.toString();
		System.out.println(answer);
		correct = "[Difficulty]";
		assertTrue(answer.equals(correct));
		
	}

	private void testConvertorAux(GameObject testGame, int id, int[] correct) {
		int[] coords = testGame.convertBase(id);
				
		System.out.println(Arrays.toString(coords));
		checkCoordsShift(coords, correct);
	}

	private void checkCoordsShift(int[] coords, int[] correctCoords) {
		for (int i = 0; i < coords.length; i ++) {
			assertTrue(coords[i] == correctCoords[i]);
		}
	}

}
