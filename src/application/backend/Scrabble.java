package application.backend;

import java.io.BufferedReader;
import java.util.*;

import javafx.scene.layout.AnchorPane;
import view.GameViewManager;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class Scrabble {
	private Pile pile; // Initialize the pile for the class
	private Board board;  // Initialize the board of the class
	private Player[] players;  // Create a list for the players
	private int numPlayers;
	private ArrayList<String> playedWords = new ArrayList<String>();
	public final static String FILE = "src/application/backend/scrabbledictionary/Collins Scrabble Words (2015).txt";
	
	private AnchorPane gamePane;
	private GameViewManager view;
	
	private int consecutiveScoreless;
	private int locationInPlayerList;
	/**
	 * Constructor for scrabble, initializes variables
	 * @param numPlayers the number of players playing
	 */
	public Scrabble(int numPlayers, AnchorPane gamePane, GameViewManager view) {
		if (numPlayers <= 0) {
			numPlayers = 1;
		}
		this.numPlayers = numPlayers;
		this.gamePane = gamePane;
		this.view = view;
		gameLoop();
	}
	
	/**
	 * Constructor for scrabble, initializes variables
	 * only for one player mode
	 */
	public Scrabble() {
		this.numPlayers = 1;
		gameLoop();
	}
	
	/**
	 * Acts as game loop proceding through elements of the game until the game ends
	 */
	private void gameLoop() {
		System.out.println("You have begun to play scrabble! (ver 1) \n\n**To quit enter \"-123\" instead of number input**\n\n");
		pile = new Pile();  // New Pile object
		board = new Board(gamePane);  // New Board object
		makePlayers(); // Makes players
		consecutiveScoreless = 0;
		
	}
	
    /*Function to sort array using insertion sort*/
    public void sort(int arr[]) 
    { 
        int n = arr.length; 
        for (int i=1; i<n; ++i) 
        { 
            int key = arr[i]; 
            int j = i-1; 
            /* Move elements of arr[0..i-1], that are 
               greater than key, to one position ahead 
               of their current position */
            while (j>=0 && arr[j] > key) 
            { 
                arr[j+1] = arr[j]; 
                j = j-1; 
            } 
            arr[j+1] = key; 
        } 
    } 
    
    /**
     * Function that sort players hight to low using insertion sort
     * @param arr array of players
     */
    /*Function to sort array using insertion sort*/
    public void sort(Player arr[]) 
    { 
        int n = arr.length; 
        for (int i=1; i<n; ++i) 
        { 
            int key = arr[i].getScore(); 
            int j = i-1; 
  
            /* Move elements of arr[0..i-1], that are 
               greater than key, to one position ahead 
               of their current position */
            while (j>=0 && arr[j].getScore() > key) 
            { 
                arr[j+1] = arr[j]; 
                j = j-1; 
            } 
            arr[j+1] = arr[i]; 
        } 
    } 
    
	/**
	 * Ends the game by finding the final score for the player
	 * Should loop through the player array and print out their scores and then get the #1 winner
	 */
	public void endGame() {
		// This part below is for making the player have the adjusted value minus that of the cards in the hand
		int adder, totalScores=0, locEmptyHand = -1;
		
		for (int i=0; i<players.length; i++) {
			Player player = players[i];
			adder=0; 
			if (player.getHand().checkIfEmpty()) {
				for (int k=0; k<Hand.MAX_CARDS_IN_HAND; k++) {
					Letter lett = player.getHand().getLetterIndex(k);
					if (lett == null || lett.getCharacter() == ' ') {
						continue;
					}
					adder += Letter.getValue(lett.getCharacter());
				}
				player.setScore(player.getScore() - adder);
				totalScores += adder;
			} else {
				locEmptyHand = i;
				System.out.println(player.getName() + " had cleared their hand");
			}
		}
		
		if (locEmptyHand != -1) {
			players[locEmptyHand].addScore(totalScores);
		}
		
		// sorts the players high to low
		sort(players);
	
		int counter = 0;
		System.out.println("----|| RESULTS ||----");
		for (int i=0; i<players.length; i++) {
			System.out.println("\t"+ (i+1) +". " + players[counter].getName() + " with "  + players[counter].getScore());
			counter ++;
		}
	}
	
	/**
	 * Makes players
	 * @author 21wwalling-sotolongo
	 */
	private void makePlayers() {
		
		players = new Player[numPlayers];
		for (int i=0; i<numPlayers; i++) {
			System.out.println("Making player [" + (1+i) + "]");
			players[i] = new Player(gamePane);
			players[i].fillHand(pile);
		}
		locationInPlayerList = 0;
	}
	
	
	/**
	 * Represents what happens during the playing phase of the game
	 * Players get to play tiles until pile and a player's hand are empty and/or there is 6 consecutive forfiet
	 * at which point the game loop will stop
	 * will end
	 * @author 21wwalling-sotolongo
	 */
	public boolean playingPhase() {
		if (6 != consecutiveScoreless) { // Loops until the players forfeits 6x or player hand & pile are empty
			Player player = players[locationInPlayerList];
			System.out.println("Its " + player.getName() + "\'s (" + player.getScore() +" pts.) turn! \n\n" );
			if (player.takeTurn(board, pile, view) == 0) {
				consecutiveScoreless ++;
				System.out.println("ALERT-----\n " + (6 - consecutiveScoreless) + " forfeits left\n---------");
			} else {
				consecutiveScoreless = 0;
			}
			if (!player.getHand().checkIfEmpty() && pile.getSize() == 0) { // If the pile is empty and one of the hands is empty then the game is ended
				System.out.println("ALERT-----\n " + player.getName() + " has emptied their hand\n---------");
				return false;
			}
			
		} else {
			return false;
		}
		updateLocationInPlayerList();
		return true;
	}
	
	private void updateLocationInPlayerList() {
		locationInPlayerList ++;
		if (locationInPlayerList >= players.length){
			locationInPlayerList = 0;
		}
	}
	/**
	 * Looks through the FILE variable in the class which is a list of the valid scrabble words
	 * and if the word is a valid choice, a boolean will return true if the word is valid and false otherwise
	 * @author 21wwalling-sotolongo
	 * @param goal the string of the word that is trying to be found
	 * @return the boolean value of whether or not the word exists as a real word
	 */
	public static boolean isStringARealWord(String goal) {
		goal.toUpperCase();
		try {
        	try (BufferedReader br = new BufferedReader(new FileReader(FILE))) {
        	    for(String line; (line = br.readLine()) != null; ) {
        	        if (line.equals(goal)) {
        	        	System.out.println("Goal Reached");
        	        	return true;
        	        }
        	    }
        	    br.close();
        	} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
        } finally {}
		return false;
	}
	
	
	/**
	 * Method to return an array list of the words that have been played
	 * @return ArrayList<String> of words that have been played
	 */
	public ArrayList<String> getPlayedWords(){
		return playedWords;
	}
	
	public Board getBoard() {
		return board;
	}
	
	public GameViewManager getView() {
		return view;
	}
}
