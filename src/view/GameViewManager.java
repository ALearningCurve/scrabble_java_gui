package view;

import application.backend.Scrabble;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.stage.Stage;

public class GameViewManager {
	private AnchorPane gamePane;
	private Scene gameScene;
	private Stage gameStage;
	
	private final static String BACKGROUND_IMAGE = "view/resources/yellow_panel.png";
	
	private static final int GAME_HEIGHT = 1392;
	private static final int GAME_WIDTH= 1024;
	
	private int counter = 0;
	private boolean loaded = false;
	private static final int MAX_COUNTER = 100;
	
	private Scrabble game;
	private Stage menuStage;
	private int numberOfPlayers;
	private AnimationTimer gameTimer;
	private AnimationTimer gameT;
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
		game = new Scrabble(numberOfPlayers, gamePane, this);
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
				if (!paused && loaded ) {
					gameTimer.stop();
					System.out.println("ROUND----------------------------------- \nStarting new playing phase \n-----------------------------------");
					if (!game.playingPhase()) { // If the playing phase detects that the game has ended it will return false
						System.out.println("ENDING----------------------------------- \n 	Game has ended \n ----------------------------------- ");
						endGame();
					}
					
					
				} else if (counter < MAX_COUNTER){
					counter ++;
					if (MAX_COUNTER%20 == counter%20) { System.out.println("	" + (int)(counter/(double) MAX_COUNTER * 100) + "%"); }
				} else if (counter >= 1){
					loaded = true;
				} 
			}
		};
		gameTimer.start();
		
		gameT = new AnimationTimer() {
			
			@Override
			public void handle(long now) {
				gameTimer.start();
			}
		};
		gameT.start();
	}
	
	
	private void createBackground() {
		Image backgroundImage = new Image(BACKGROUND_IMAGE, 6, 6, false, true);
		BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
		gamePane.setBackground(new Background(background));
	}
	
}
