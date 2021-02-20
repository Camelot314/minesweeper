package gui;

import java.io.File;

import javax.swing.ImageIcon;

import model.GameFunctions;

/**
 * A class dedicated to locating the Icon files.
 * @author kjara
 *
 */
public class IconLoader {
	
	/**
	 * Returns an Icon when given a specified key
	 * @param key
	 * @return ImageIcon
	 */
	public static ImageIcon get(char key) {
		String path = iconExists(key);
		if (path != null) {
			return new ImageIcon(path);
		} else {
			return null;
		}
	}
	
	/**
	 * Checks to see if all of the icon files exist.
	 * @return true if every single png exists, otherwise false;
	 */
	public static boolean allExist() {
		boolean toReturn = true;
		char[] allKeys = { 
				'1', '2', '3', '4', '5', '6', '7', '8',
				GameFunctions.BLANK_CHAR, GameFunctions.BOMB_CHAR, 
				GameFunctions.FLAG_CHAR, GameFunctions.SECRET_CHAR,
				't', 's'
		};
		
		for (char key: allKeys) {
			toReturn = toReturn && iconExists(key) != null;
		}
				
		
		
		return toReturn;
	}
	
	/**
	 * Will return a string that is the file path to the icon
	 * that corresponds to a char key iff the icon png file 
	 * exists. Otherwise will return a null string.
	 * @param key
	 * @return a string that is the file path to an icon.
	 */
	private static String iconExists(char key) {
		
		String filePath = getAbsolutePath();
		String newPath;
		
		switch(key) {
			case GameFunctions.FLAG_CHAR : newPath = filePath.concat("flag.png");
				break;
			case GameFunctions.BOMB_CHAR : newPath = filePath.concat("bomb.png");
				break;
				case GameFunctions.SECRET_CHAR : newPath = filePath.concat("secret.png");
					break;
				case GameFunctions.BLANK_CHAR : newPath = filePath.concat("blank.png");
					break;
				case '1' : 		newPath = filePath.concat("1.png");
					break;
				case '2' : 		newPath = filePath.concat("2.png");
					break;
				case '3': 		newPath = filePath.concat("3.png");
					break;
				case '4': 		newPath = filePath.concat("4.png");
					break;
				case '5': 		newPath = filePath.concat("5.png");
					break;
				case '6': 		newPath = filePath.concat("6.png");
					break;
				case '7': 		newPath = filePath.concat("7.png");
					break;
				case '8': 		newPath = filePath.concat("8.png");
					break;
				case 't': 		newPath = filePath.concat("3_highlighted.png");
					break;
				case 's': 		newPath = filePath.concat("6_highlighted.png");
					break;
				default: 		newPath = "nonExistant";
				
		}
		File iconFile = new File(newPath);
		return iconFile.exists() ? newPath : null;
		
	}
	
	/**
	 * Gets the file path for the current working directory then
	 * adds the folders to find the icon folder. 
	 * the current working directory is C:\....\Minesweeper\
	 * this makes the path relative and able to work on multiple 
	 * different file locations and computers.
	 * @return
	 */
	private static String getAbsolutePath() {
		return new File("").getAbsolutePath().concat("\\src\\gui\\icons\\");
	}
	
}
