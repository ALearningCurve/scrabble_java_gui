/**
 * This is a class the represents a tile/letter in the game of scrabble
 * @author 21wwalling-sotolongo
 *
 */
package application.backend;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;

public class Letter extends Button{
	 public static final HashMap<Integer, String> SCRABBLE_SCORES = (HashMap<Integer, String>) createMap();  
	 private int value;  
	 private char character = ' ';

	 private final String FONT_PATH = "src/model/resources/kenvector_future.ttf";
	private final String BUTTON_PRESSED_STYLE = "-fx-background-color: transparent; -fx-background-image: url('view/resources/scrabbletiles/scrabble_board_peice_selected.png');";
	private final String BUTTON_FREE_STYLE = "-fx-background-color: transparent; -fx-background-image: url('view/resources/scrabbletiles/scrabble_board_peice.png');";
	
	public final static int HEIGHT = 45;
	public final static int WIDTH = 49;
	
	 /**
	  * This is the constructor for the class, sets character and finds value for that character
	  * @author 21wwalling-sotolongo
	  * @param character that the object represents
	  */
	 public Letter(char character){
		 this.character = character;
		 this.value = getValue(this.character);
		 
		 setText(this.toString());
		 setButtonFont();
		 setPrefWidth(WIDTH);
		 setPrefHeight(HEIGHT);
		 setStyle(BUTTON_FREE_STYLE);
		 initializeButtonListeners();
	 } 
	 
	 public void updateTextOnButton() {
			setText(this.toString());
	}
	 
	 /**
	  * sets a Letter instance's value and character to that of a blank 
	  */
	 public void setToBlank() {
		 this.character = ' ';
		 this.value = getValue(this.character);
	 }
	 
	 /**
	  * When called it asks for user input to get the new value for a blank
	  * @param sc a scanner being used |||| as long as java.util is imported you can make one with $new Scanner(System.in) ||||
	  */
	 public void setBlankValue(Scanner sc) {
		 if (character != ' ') {
			 System.out.println("This letter is not a blank! (Now Exiting Selection Process)");
			 return;
		 } 
		 char newCharacter = ' ';
		 while (newCharacter == ' ') {
			System.out.println("Enter a letter!");
			String input = sc.nextLine(); // Reads the user's input as a string
			
			try { // Tries to get a value from the beginning of the string
				newCharacter = input.charAt(0); 
				newCharacter = Character.toUpperCase(newCharacter);
				
			} catch (IndexOutOfBoundsException  e){ // Catches if there is no user input
				System.out.println("\nERROR----------------------------------- \n\tMake sure to enter a letter! \n ----------------------------------------");
				newCharacter = ' ';
				continue;
			
		    } catch (Exception e){	// Catches whatever error the user caused
				System.out.println("\nERROR----------------------------------- \n\tIllegal character, you must enter a number, you entered \'" + input + "\'\n ----------------------------------------");
				e.printStackTrace(); // Prints out the error
				newCharacter = ' ';
				continue;
			}
	
			// Check if the number is within the range, if it is -1 then the input doesn't exist as a valid letter
			// So it will loop back to the beginning of the input finder
			if (getValue(newCharacter) <= 0) {
				System.out.println("\nERROR----------------------------------- \n\tYou must enter a letter A-Z!\n ----------------------------------------");
				newCharacter = ' ';
				continue;
			}
			break; // Breaks if nothing is wrong with the user's input
		}
		 setNewCharacter(newCharacter); // Sets the letter to be an actual letter
	 }
	 
	 /**
	  * provides a string conversion of the object with information such as value
	  * @author 21wwalling-sotolongo
	  * @return the string of the letter
	  */
	public String toStringInfo() {
		 return "\"" + this.character + "\" (value " + this.value + ")";
	 }
	
	/**
	 * Provides a string conversion of the letter as single letter such as: a
	 * @author 21wwalling-sotolongo
	 * @return single letter form of the letter 
	 */
	public String toString() {
		if (character == ' ') {
			return "/";
		} else {
			return Character.toString(character); 
		}
		
	}
	
	/**
	 * returns the value of the letter
	 * @return int value of letter
	 */
	
	public int getValue() {
		return value;
	}
	 /**
	  * Returns that value of the character put into the function
	  * @author 21wwalling-sotolongo
	  * @param character what to find the value of 
	  * @return value of the character
	  */
	 public static int getValue(char character) {
		 int value = -1; // Returns negative one if no value if found
		 for (int key:SCRABBLE_SCORES.keySet()) {
			 for (int i=0; i<SCRABBLE_SCORES.get(key).length(); i++) {
				 if (SCRABBLE_SCORES.get(key).charAt(i) == Character.toUpperCase(character)){
					 value = key;
				 }
			 }
		 }
		 return value;
	 }
	 
	 /**
	  * if the letter is a blank then it will be converted to another letter, updates the value and the letter
	  * @author 21wwalling-sotolongo
	  * @param character the new letter for the object
	  * @return t/f if the conversion has been successful or possible
	  */
	 private boolean setNewCharacter(char character){
		 if (this.character == ' ' && this.value == 0) {
			 this.character = character;
			 this.value = getValue(this.character);
			 
			 System.out.println("This blank and has been converted to \'" + this.character + "\' with value " + this.value + "!");
			 return true;
		 }
		 System.out.println("This is not a blank!");
		 return false; // This means that the character nor the value are that of a blank, so do nothing
	 }
	 
	 /**
	  * Creates a hash map for all the values of the letters 
	  * @return a hash-map of the values of character
	  * @author 21wwalling-sotolongo
	  */
	 private static Map<Integer, String> createMap()
	 {
		// LETTER LIST [(1, "E A O I N R T L S U"), (2, "D G"), (3, "B C M P"), (4, "F H V W Y"), (5, "K"), (8, "J X"), (10, "Q Z")];
		Map<Integer,String> myMap = new HashMap<Integer,String>();
		myMap.put(1, "EAOINRTLSU");
		myMap.put(2, "DG");
		myMap.put(3, "BCMP");
		myMap.put(4, "FHVWY");
		myMap.put(5, "K");
		myMap.put(8, "JX");
		myMap.put(10,"QZ");
		myMap.put(0," ");
		return myMap;
	 }

	 /**
	  * returns the character that the letter represents
	  * @return char of the letter the object represents
	  */
	public char getCharacter() {
		return character;
	}	 
	
	////////////////////////////
	private void setButtonFont() {
		try {
			setFont(Font.loadFont(new FileInputStream(FONT_PATH), 15));
		} catch (FileNotFoundException e) {
			setFont(Font.font("Verdana", 10));
		}
	}
	
	@SuppressWarnings("unused")
	private void setButtonPressedStyle() {
		setStyle(BUTTON_PRESSED_STYLE);
		// setPrefHeight(HEIGHT);
		setLayoutY(getLayoutY() + 2);
	}
	
	@SuppressWarnings("unused")
	private void setButtonReleasedStyle() {
		setStyle(BUTTON_FREE_STYLE);
		// setPrefHeight(HEIGHT);
		setLayoutY(getLayoutY() - 2);
	}
	
	
	
	private void initializeButtonListeners() {
		
		
		setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.getButton().equals(MouseButton.PRIMARY)) {
					//setButtonPressedStyle();
				}
			}
		});
		
		setOnMouseReleased(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.getButton().equals(MouseButton.PRIMARY)) {
					//setButtonReleasedStyle();
				}
			}
		});
		
		setOnMouseEntered(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				setEffect(new DropShadow());
				setStyle(BUTTON_PRESSED_STYLE);
			}
		});
		
		setOnMouseExited(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				setEffect(null);
				setStyle(BUTTON_FREE_STYLE);
			}
		});
	}	
}



