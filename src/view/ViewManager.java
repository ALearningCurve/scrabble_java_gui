package view;


import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.InfoLabel;
import model.ShipPicker;
import model.ScrabbleButton;
import model.ScrabbleSubScene;


public class ViewManager {
	
	
	private static final int HEIGHT = 768;
	private static final int WIDTH= 1024;
	private AnchorPane mainPane;
	private Scene mainScene;
	private Stage mainStage;
	private final static int MENU_BUTTONS_START_X = 100;
	private final static int MENU_BUTTONS_START_Y = 150;
	private List<ScrabbleButton> menuButtons;
	// private List<ShipPicker> shipsList;
	
	private ScrabbleSubScene creditsSubScene;
	private ScrabbleSubScene helpSubScene;
	private ScrabbleSubScene scoresSubScene;
	private ScrabbleSubScene shipChooserScene;
	
	private ScrabbleSubScene sceneToHide;
	private int choosenNumberOfPlayers = 1;
	
	
	public ViewManager() {
		menuButtons = new ArrayList<>();
		mainPane =  new AnchorPane();
		mainScene =  new Scene(mainPane, WIDTH, HEIGHT);
		mainStage = new Stage();
		mainStage.setScene(mainScene);
		createButtons();
		createSubScenes();
		createBackground();
	};
	
	private void showSubScene(ScrabbleSubScene subScene) {
		if (sceneToHide != null) {
			sceneToHide.moveSubScene();
		}
		
		sceneToHide = subScene;
		subScene.moveSubScene();
	}
	private void createSubScenes() {
		creditsSubScene = new ScrabbleSubScene();
		mainPane.getChildren().add(creditsSubScene);
		
		helpSubScene = new ScrabbleSubScene();
		mainPane.getChildren().add(helpSubScene);
		
		scoresSubScene = new ScrabbleSubScene();
		mainPane.getChildren().add(scoresSubScene);
		
		
		createShipChooserSubScene();
	}
	
	private void createShipChooserSubScene() {
		shipChooserScene = new ScrabbleSubScene();
		mainPane.getChildren().add(shipChooserScene);
		
		InfoLabel chooseShipLabel = new InfoLabel("PICK A SHIP!");
		chooseShipLabel.setLayoutX(110);
		chooseShipLabel.setLayoutY(25);
		
		shipChooserScene.getPane().getChildren().add(chooseShipLabel);
		// shipChooserScene.getPane().getChildren().add(createShipsToChoose());
		shipChooserScene.getPane().getChildren().add(createButtonToStart());
	}
	
	/*
	 * use this to later make a player number selection
	private HBox createShipsToChoose() {
		HBox box = new HBox();
		box.setSpacing((double) 20);
		shipsList = new ArrayList<>();
		for (SHIP ship : SHIP.values()) {
			ShipPicker shipToPick = new ShipPicker(ship);
			shipsList.add(shipToPick);
			box.getChildren().add(shipToPick);
			shipToPick.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) { 
					for (ShipPicker ship : shipsList) {
						ship.setIsCircleChoosen(false);
					}
					shipToPick.setIsCircleChoosen(true);
					choosenShip = shipToPick.getShip();
				}
			});
		}
		box.setLayoutX(300 - (118*2));
		box.setLayoutY(100);
		return box;
	}
	*/
	
	private ScrabbleButton createButtonToStart() {
		ScrabbleButton startButton = new ScrabbleButton("Start");
		startButton.setLayoutX(350);
		startButton.setLayoutY(300);
		
		startButton.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				GameViewManager gameManager = new GameViewManager();
				gameManager.createNewGame(mainStage, choosenNumberOfPlayers);
				
			}
		});
		return startButton;
	}
	public Stage getMainStage() {
		return mainStage;
	}
	
	private void addMenuButton(ScrabbleButton button) {
		button.setLayoutX(MENU_BUTTONS_START_X);
		button.setLayoutY(MENU_BUTTONS_START_Y + (menuButtons.size() * 100));
		menuButtons.add(button);
		mainPane.getChildren().add(button);
	}
	
	private void createButtons() {
		createStartButton();
		createHelpButton();
		createScoresButton();
		createCreditsButton();
		createExitButton();
	}
	
	private void createStartButton() {
		ScrabbleButton startButton = new ScrabbleButton("Play");
		addMenuButton(startButton);
		
		startButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// showSubScene(shipChooserScene);
				GameViewManager gameManager = new GameViewManager();
				gameManager.createNewGame(mainStage, choosenNumberOfPlayers);
			}
		});
	}
	private void createScoresButton() {
		ScrabbleButton scoresButton = new ScrabbleButton("Score");
		addMenuButton(scoresButton);
		
		scoresButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				showSubScene(scoresSubScene);	
			}
		});
	}
	
	private void createHelpButton() {
		ScrabbleButton helpButton = new ScrabbleButton("Help");
		addMenuButton(helpButton);
		
		helpButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				showSubScene(helpSubScene);	
			}
		});
	}
	private void createCreditsButton() {
		ScrabbleButton creditsButton = new ScrabbleButton("Credits");
		addMenuButton(creditsButton);
		

		creditsButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				showSubScene(creditsSubScene);
			}
		});
	
	}
	
	private void createExitButton() {
		ScrabbleButton exitButton = new ScrabbleButton("Exit");
		addMenuButton(exitButton);
		
		exitButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				mainStage.close();
			}
		});
	}
	
	private void createBackground() {
		Image backgroundImage = new Image("view/resources/deep_blue.png", 6, 6, false, true);
		BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
		mainPane.setBackground(new Background(background));
	}
}