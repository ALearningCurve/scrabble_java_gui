package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.text.Font;

public class InfoLabel extends Label{
	
	final static String FONT_PATH = "src/model/resources/kenvector_future.ttf";
	public static final String BACKGROUND_IMAGE = "view/resources/yellow_small_panel.png";
	private static final int WIDTH = 380;
	private static final int HEIGHT = 49;
	
	public InfoLabel(String text) {
		setPrefWidth(WIDTH);
		setPrefHeight(HEIGHT);
		setPadding(new Insets(0,0,0,0));
		setText(text);
		setWrapText(true);
		setLabelFont();
		setAlignment(Pos.CENTER); 
		
		BackgroundImage backgroundImage = new BackgroundImage(new Image(BACKGROUND_IMAGE, WIDTH, HEIGHT, false, true), 
				BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
		setBackground(new Background(backgroundImage));
	}
	
	private void setLabelFont(){
		try {
			setFont(Font.loadFont(new FileInputStream(new File(FONT_PATH)), 23));
		} catch (FileNotFoundException e) {
			setFont(Font.font("Verdana", 23));
			
		}
	}
	
}
