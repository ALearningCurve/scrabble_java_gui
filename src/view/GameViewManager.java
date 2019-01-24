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
	
	private static final int GAME_HEIGHT = 768;
	private static final int GAME_WIDTH= 1024;
	
	private Scrabble game;
	private Stage menuStage;
	private int numberOfPlayers;
	private AnimationTimer gameTimer;
	
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
		game = new Scrabble(numberOfPlayers, gamePane);
	}
	
	public void createNewGame(Stage menuStage, int NumPlayers) {
		this.menuStage = menuStage;
		this.menuStage.hide();
		createBackground();
		
		
		gameStage.show();
		createGameLoop();
		createGameElements(numberOfPlayers);
		
	}
	
	
	private void createGameLoop() {
		gameTimer = new AnimationTimer() {

			@Override
			public void handle(long now) {
			
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
