package model;


import javafx.animation.TranslateTransition;
import javafx.scene.SubScene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.util.Duration;




public class ScrabbleSubScene extends SubScene{
	private final String BACKGROUND_IMAGE = "model/resources/yellow_tile.png";
	
	private boolean isHidden;
	
	public ScrabbleSubScene() {
		super(new AnchorPane(), 600, 400);
		prefWidth(600);
		prefHeight(400);
		
		Image backgroundImage = new Image(BACKGROUND_IMAGE, 600, 400, false, true);
		BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
		AnchorPane root2 = (AnchorPane) this.getRoot();
		root2.setBackground(new Background(background));
		
		isHidden = true;
		
		setLayoutX(1024*2);
		setLayoutY(180);
	}

	
	public void moveSubScene() {
		TranslateTransition transition = new TranslateTransition();
		transition.setDuration(Duration.seconds(.3));
		transition.setNode(this);
		
		if (isHidden) {
		transition.setToX(-676*2 - 320);
		} else {
		transition.setToX(676*2 + 320);
		}
		
		transition.play();
		isHidden = !isHidden; 
	}
	
	public AnchorPane getPane() {
		return (AnchorPane) this.getRoot();
	}
}
