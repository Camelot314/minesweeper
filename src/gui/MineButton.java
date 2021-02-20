package gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.SwingUtilities;
import javax.swing.ImageIcon;

import model.GameFunctions;
import model.GameObject;

/**
 * The minebuttons used in the game.
 * @author kjara
 *
 */
public class MineButton extends JButton{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5827023398994403700L;
	private int id;
	private GameObject game;
	private MineField field;
	private Window bridge;
	private boolean hasNoIcon;
	private ImageIcon icon;
	private ActionListener leftListner;
	private MouseAdapter rightListener;
	
	/**
	 * initializes the button so they all
	 * have references to the same gameBoard and
	 * have a unique id. Also has a reference to the 
	 * minefield object so it can cause changes to all other
	 * mineButtons. It also has a reference to the window so
	 * it can change the title when the game is over.
	 * @param id
	 * @param game
	 * @param field
	 * @param bridge
	 */
	public MineButton(int id, GameObject game, MineField field, Window bridge) {
		super();
		this.field = field;
		this.id = id;
		this.game = game;
		this.bridge = bridge;
		icon = getIcon(game.getCharOf(id));
		changeIcon();
		
		leftListner = new ActionListener() {
			/**
			 * When button is clicked it calls the onTap function.
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				onLeftClick(field.getIncorrectCoords());
				
			}
		};
		
		rightListener = new MouseAdapter() {
			/**
			 * If the event is a right click then it calls
			 * the right click method.
			 */
			@Override
			public void mouseClicked(MouseEvent e) {
				if (SwingUtilities.isRightMouseButton(e)) {
					onRightClick();
				}
			}
		};
		
		addActionListener(
//				/**
//				 * ActionListner object to detect button clicks.
//				 */
				leftListner
		);
		addMouseListener(
//				/**
//				 * MouseAdapter object to detect right clicks.
//				 */
				rightListener
		);
		
	}
	
	/**
	 * reveals location if button is pressed. 
	 * If the game is over (when the returned boolean for reveal is
	 * true in the second position). Then it will change the game
	 * status of the window. It will always call the field.changeField()
	 * method to update the entire board when a location is revealed.
	 * Takes in the param ArrayList<int[]> which is a reference of the 
	 * ArrayList of incorrect coords that lives in the MineField object.
	 * Uses the list to log any incorrect positions.
	 */
	public void onLeftClick(ArrayList<int[]> incorrectCoords) {
		if (game.getCharOf(id) != GameFunctions.FLAG_CHAR) {
			boolean[] gameStatus = game.reveal(id, incorrectCoords);
			if (gameStatus[2]) {
				bridge.changeTitle(gameStatus);
			}
			field.changeField();
		}
	}
	
	/**
	 * Method is called if a right click has occured over the button. 
	 * This will initiate the flag method of the game. It will also 
	 * call the bridge method change bomb number to update the bomb count.
	 */
	public void onRightClick() {
		game.flag(id);
		field.changeField();
		bridge.changeBombNumber();
	}
	
	
	/**
	 * Changes the color of the individual button. It will also call the 
	 * change icon method. If the highlight param is true, then it will 
	 * change the button color to red unless it is using the icon 3 or 6
	 * in which case the button will be blue.
	 * @param highlight
	 */
	public void changeColor(boolean highlight) {
		changeIcon();
		if (highlight) {
			changeIcon(true);
			if (hasNoIcon) {
				setForeground(Color.WHITE);
			} else {
				setForeground(Color.BLACK);
			}
			
		}
	}
	
	/**
	 * this will change the icon. It will also change the color of the 
	 * background. If the icon is the secret icon the background will be
	 * grey otherwise the background will be white.
	 */
	public void changeIcon() {
		changeIconAux(false);
		
		// sets background color
		if (game.getCharOf(id) != GameFunctions.SECRET_CHAR && 
				game.getCharOf(id) != GameFunctions.FLAG_CHAR) {
			
			setBackground(Color.WHITE);
		} else {
			setBackground(new Color(129, 125, 125));
		}
		
		
	}
	
	/**
	 * Used when highlighting certain squares
	 * @param highLight
	 */
	public void changeIcon(boolean highLight) {
		setBackground(Color.RED);
		changeIconAux(true);
	}
	
	/**
	 * How the computer interacts with the button
	 * makes a pseudo mouse event that mimics a right click.
	 */
	public void computerRightClick() {
		MouseEvent anEvent = new MouseEvent(this, 500, 1610481398529L, 0, 9, -10, 1, false, 3);
		rightListener.mouseClicked(anEvent);
	}
	
	/**
	 * How the computer interacts with the button
	 * makes a pseudo action event mimicking a click.
	 */
	public void computerLeftClick() {
		ActionEvent anEvent = new ActionEvent(this, 1001, "");
		leftListner.actionPerformed(anEvent);
	}
	
	/**
	 * Auxiliary method used to highlight change the icon.
	 * @param highlight
	 */
	private void changeIconAux(boolean highlight) {
		char key = game.getCharOf(id);
		
		// removes the text if it previously had no icon and only text
		// otherwise it just removes the icon.
		if (hasNoIcon) {
			setText("");
		} else {
			setIcon(null);
		}
		
		// if we are highlighting and the square is 
		// a 3 or a six it changes the char key
		// to the keys corresponding to the 
		// highlighted pngs.
		if (highlight) {
			key = key == '6' ? 's' : key;
			key = key == '3' ? 't' : key;
		}
		
		icon = getIcon(key);
		hasNoIcon = icon == null;
		
		// if there is no icon then it will change to text otherwise
		// changes the icon.
		if (hasNoIcon) {
			if (key == GameFunctions.FLAG_CHAR) {
				setText("Flag");
			} else if (key == GameFunctions.BOMB_CHAR) {
				setText ("Bomb");
			}else {
				setText(Character.toString(key));
			}
		} else {
			setIcon(getIcon(key));
		}
	}
	
	/**
	 * Static function that will return the Image icon based of 
	 * the given char key. 
	 * @param key
	 * @return
	 */
	private static ImageIcon getIcon(char key) {
		return IconLoader.get(key);
	}
	

}
