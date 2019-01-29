package view;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.stage.Stage;

import application.backend.Scrabble;
import application.backend.*;

public class GameViewManager {
	private AnchorPane gamePane;
	private Scene gameScene;
	private Stage gameStage;
	
	private final static String BACKGROUND_IMAGE = "view/resources/yellow_panel.png";
	
	private static final int GAME_HEIGHT = 1392;
	private static final int GAME_WIDTH= 1024;
	private int counter = 0;
	private boolean loaded = false;
	
	private Scrabble game;
	private Stage menuStage;
	private int numberOfPlayers;
	private AnimationTimer gameTimer;
	
	private boolean paused;
	
	public GameViewManager() {
		initializeStage();
	}
	
	private void initializeStage() {
		gamePane = new AnchorPane();
		gameScene = new Scene(gamePane, GAME_WIDTH, GAME_HEIGHT);
		gameStage = new Stage();
		gameStage.setScene(gameScene);
		
	}
	

	private void createGameElements(int numberOfPlayers) {
		paused = false;
		game = new Scrabble(numberOfPlayers, gamePane);
	}
	
	public void createNewGame(Stage menuStage, int NumPlayers) {
		this.menuStage = menuStage;
		this.menuStage.hide();
		createBackground();
		
		
		gameStage.show();
		createGameElements(numberOfPlayers);
		createGameLoop();
		
	}
	
	private void endGame() {
		game.endGame();
		paused = true;
	}
	private void createGameLoop() {
		loaded = false; // This exists to give the game elements time to load in the beginning before the player turn happens
		System.out.println("Loading...");
		counter = 0;
		gameTimer = new AnimationTimer() {
			
			@Override
			public void handle(long now) {
				if (!paused && loaded) {
					System.out.println("ROUND----------------------------------- \nStarting new playing phase \n-----------------------------------");
					if (!game.playingPhase()) { // If the playing phase detects that the game has ended it will return false
						System.out.println("ENDING----------------------------------- \n 	Game has ended \n ----------------------------------- ");
						endGame();
					}
				} else if (counter < 100){
					counter ++;
					if (counter % 10 == 0) { System.out.println("	" + counter + "%"); }
				} else if (counter >= 1){
					loaded = true;
				}
			}
		};
		gameTimer.start();
	}
	
	
	private void createBackground() {
		Image backgroundImage = new Image(BACKGROUND_IMAGE, 6, 6, false, true);
		BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
		gamePane.setBackground(new Background(background));
	}
	
}
