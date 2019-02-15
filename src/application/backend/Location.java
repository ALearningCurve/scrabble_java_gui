package application.backend;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
/**
 * This is a class the represents a location within the board
 * @author 21wwalling-sotolongo
 *
 */

public class Location extends Button{
	public static final int EMPTY = 0;
	public static final int FULL = 1;
	private int status = EMPTY;
	private Letter letter = null; 

	private final String FONT_PATH = "src/model/resources/kenvector_future.ttf";
	private final String BUTTON_PRESSED_STYLE = "-fx-background-color: transparent; -fx-background-image: url('view/resources/scrabbletiles/scrabble_board_peice_selected.png');";
	private final String BUTTON_FREE_STYLE = "-fx-background-color: transparent; -fx-background-image: url('view/resources/scrabbletiles/scrabble_board_peice.png');";
	
	public final static int HEIGHT = 45;
	public final static int WIDTH = 49;
	
	private boolean isClicked;
	
	
	private Text tileValue;
	/**
	 * Location constructor. 
	 * @author 21wwalling-sotolongo
	 */
	public Location() {
		setClicked(false);
		setText(this.toString());
		setButtonFont();
		createValueOfTileVBox();
		setPrefWidth(WIDTH);
		setPrefHeight(HEIGHT);
		setStyle(BUTTON_FREE_STYLE);
		initializeButtonListeners();
		// System.out.println(this.getChildren());
		
	}
	
	public void updateTextOnButton() {
		setText(this.toString());
	}
	/**
	 * Resets the values of the location to be EMPTY and have no letter (letter variable set to null)
	 * And returns the letter which the location previously had
	 * @return the letter which the location held
	 */
	public Letter removeLetter() {
		status  = EMPTY;
		Letter temp = letter;
		letter = null;
		return temp;
	}
	/**
	 * Set the status of this Location.
	 * @param status of what is at the location
	 * @author 21wwalling-sotolongo
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 *  Get the status of this Location.
	 *  @author 21wwalling-sotolongo
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}
	
	/**
	 *  Get the letter at the location of the space
	 * @return the letter at that location
	 */
	public Letter getLetter() {
		return letter;
	}
	
	/**
	 *  Set the letter at the spot
	 * @param l the letter to be set at that spot
	 * @author 21wwalling-sotolongo
	 */
	public void setLetter(Letter l) {
		status = FULL;
		this.letter = l;
		updateTextOnButton();
	}
	
	/**
	 *  Method to get the string of what is at the location
	 *  @author 21wwalling-sotolongo
	 *  @return the string representation
	 */
	@Override
	public String toString() {
		if (status == 0) {
			return " ";
		} else {
			return letter.toString();
		}
	}
	
	
	
	// // /// // // // // // // //
	
	private void createValueOfTileVBox() {
		/*
		valueLabel = new HBox();
		this.getChildren().add(valueLabel);
		valueLabel.setLayoutX(0);
		valueLabel.setLayoutY(0);
		*/
		tileValue = new Text(".");
		tileValue.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		this.getChildren().add(tileValue);
		

	}
	
	private void setButtonFont() {
		try {
			setFont(Font.loadFont(new FileInputStream(FONT_PATH), 18));
		} catch (FileNotFoundException e) {
			setFont(Font.font("Verdana", 12));
		}
	}
	
	private void setButtonPressedStyle() {
		setStyle(BUTTON_PRESSED_STYLE);
		// setPrefHeight(HEIGHT);
		setLayoutY(getLayoutY() + 2);
		
	}
	
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
					setButtonPressedStyle();
					setClicked(true);
				}
			}
		});
		
		setOnMouseReleased(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.getButton().equals(MouseButton.PRIMARY)) {
					setButtonReleasedStyle();
					setClicked(false);
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

	public boolean isClicked() {
		return isClicked;
	}

	public void setClicked(boolean isClicked) {
		this.isClicked = isClicked;
	}	
}
