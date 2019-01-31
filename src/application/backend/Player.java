/**
 * This is a player class, it will be everything that a human would be able to do in a scrabble game
 * @author 21wwalling-sotolongo
 *
 */
package application.backend;
import javafx.scene.layout.AnchorPane;
import view.GameViewManager;

import java.util.*;

public class Player 
{
	private String name; // Player's name
	private Hand hand; // Player's hand
	private static int numPlayers = 0; // Class variable that increases for each player made
	private int score = 0; // Players score
	private Scanner sc = new Scanner(System.in); // Scanner used to get user input

	@SuppressWarnings("unused")
	private AnchorPane gamePane;
	
	/**
	 * Constructor for the Player class that creates player with the name given
	 * @param name the name for the player object
	 * @author 21wwalling-sotolongo
	 */
	public Player(String name, AnchorPane gamePane) {
		numPlayers ++;
		this.name = name;
		this.hand = new Hand();
		this.gamePane = gamePane;
	}
	
	/**
	 * Constructor for the the Player class that creates a player with numerical name ie Player 1 [Player n] depending on num
	 * playes
	 * @author 21wwalling-sotolongo
	 */
	public Player(AnchorPane gamePane) {
		numPlayers ++;
		this.name = "Player " + numPlayers; 
		this.hand = new Hand();
		this.gamePane = gamePane;
	}
	
	/**
	 * fills a hand to its limit with 7 cards from pile
	 * @param pile Pile object to draw 7 tiles from
	 * @author 21wwalling-sotolongo
	 */
	public void fillHand(Pile pile) {
		pile.dealStart(hand);
	}
	
	
	
	/**
	 * Part of the turn where the player enters locations for their tiles to be played and entered
	 * @param board Board object where the plays are put
	 * @param locationsOfTurns ArrayList<int[]> where the player has put thier turns
	 * @param blankIndexes ArrayList<Integer> Index in hand where the blank came from
	 * @return
	 */
	private int playTiles (Board board, ArrayList<int[]> locationsOfTurns, ArrayList<Integer> blankIndexes) {
		while(hand.checkIfEmpty()){ // Lets the player put down the letters onto the board
			board.printStatus();
			board.printStatus(true);
			int index = readInt(-1,6, "Enter the index of the card to be played (-1 to forfeit turn)"); // Ask for the card in the hand to be placed
			
			if (index == -1) {
				System.out.println("\nFORFEIT----------------------------------- \n\tYour turn has been forfieted, returning cards to hand! \n ----------------------------------------");
				returnTilesToHandFromBoard(board, locationsOfTurns, blankIndexes);
				return 0;
			}
			if (hand.getLetterIndex(index) == null) {
				System.out.println("\nERROR---------------------------------- \n\tThat tile has already been played\n ----------------------------------------");
				continue;
			} else if (hand.getLetterIndex(index).getCharacter() == ' ') { // Checks if the letter at that index is a blank, and if so then it sets it to something else
				System.out.println("You are playing a blank!");
				hand.getLetterIndex(index).setBlankValue(sc);
				blankIndexes.add(index);
			}
			
			int col = readInt(0,14, "Enter the column for the letter to go") ;  // Ask for the x coordinate
			int row = readInt(0,14, "Enter the row for the letter to go") ; // Ask for the y coordinate
			
			if (board.get(col, row).getStatus() == Location.EMPTY) {// Removes the letter and plays it if there is no letter on that spot
				Letter letter = hand.remove(index);
				board.setLetter(col, row, letter);
			} else { // If there is a letter on that spot then it tells player and continues
				System.out.println("\nERROR---------------------------------- \n\tYou cannot play over the same tile!\n ----------------------------------------");
				continue;
			}
			
			// Updates the hashmap with values of where the tiles have been placed
			locationsOfTurns.add(new int[]{col, row, index});
			
			// Checks if the user's turn is over or not
			if (readInt("Done with your turn? (0 is yes)") == 0) {
				break;
			}
			System.out.println("This is now your hand:\n"  + hand );
			
		}
		return 1;
	}

	/**
	 * Take the input from the player to get the letter from their hand, remove it, and put it to 
	 * the location on the board that is designated by user input. This repeats until the hand is either empty 
	 * or the play says that their turn is done.
	 * Once the letters have been moved to the board, the board is then checked to make sure that the move is valid
	 * and that they form actual words, which if the words aren't real or the move isn't valid, then it is looped back to 
	 * the drawing phase. Only once all the words (at least one is needed) are valid and the move is legal, then the 
	 * final valid words are scored and the player's score is updated with that value. It then will return the value
	 * for the player's turn OR 0 if the turn is forfeited
	 * 
	 * @param board the playing board
	 * @param pile the pile being used to draw peices
	 * @return the value of the player's turn
	 * @author 21wwalling-sotolongo
	 */
	
	public int takeTurn(Board board, Pile pile, GameViewManager view) {
		ArrayList<int[]> locationsOfTurns; // Keeps tracj of the indexes and the locations of all the moves
		String[][] collection; // Collection of all the words that are on the grid
		ArrayList<String> finalWords = new ArrayList<String>(), badWords = new ArrayList<String>(); // This is a list of the individual words and badWords are the words that are invalid
		ArrayList<Integer> blankIndexes; // Keeps track of indexes of blanks so that later it can be used to return invalid moves with blanks
		
			
			locationsOfTurns = new ArrayList<int[]>(); // Initiallizes and Resets the list
			blankIndexes = new ArrayList<Integer>();  // Initiallizes and Resets the list
			System.out.println("This is your hand:\n"  + hand );
			
			if (playTiles(board, locationsOfTurns, blankIndexes) == 0) {
				return 0;
			}
			
			System.out.println("\nEnd of turn board:");
			board.printStatus();
			board.printStatus(true);
			
			collection = new String[locationsOfTurns.size()][2];
			int scoreToReturn = this.checkValidWord(locationsOfTurns, collection, finalWords, badWords, blankIndexes, board, pile, view);
		
		
		for (int i=0; i<locationsOfTurns.size(); i++) { // Loop to replace the words that were played
			hand.addLetter(pile.drawTop()); // Adds letter to the hand
		}
		
		return scoreToReturn; // Returns the totalScore of the player's move
	}
	
	
	/**
	 * Logic of the turn to check if the word that was played is valid (ie exitsts in dictionary) or played legally on board
	 * @param locationsOfTurns ArrayList<int[]> of where the turnds were played
	 * @param collection String[][] of the words that the player has played
	 * @param finalWords ArrayList<String> to be filled by the valid words found --> no repeats
	 * @param badWords	ArrayList<String> of the invalid plays of the words
	 * @param blankIndexes ArrayList<Integer> Where the blank was played so it can be returned to the hand as blank
	 * @param board Board object where the turn was played
	 * @param pile Pile object to draw/return to the pile
	 * @param view GameViewManager object that may be used for things like buttons/alerts
	 * @return the score of the player
	 */
	private int checkValidWord(ArrayList<int[]> locationsOfTurns, String[][]collection, ArrayList<String> finalWords, ArrayList<String> badWords, ArrayList<Integer> blankIndexes, Board board, Pile pile, GameViewManager view) {
		boolean reloop = false; // This just is to say if there has been a bad play
		// --------
		// This is the logic for finding words and making sure that the play was a valid one
		int lastCol, lastRow;
		lastCol = locationsOfTurns.get(0)[0];
		lastRow = locationsOfTurns.get(0)[1];
		
		
		// finds all the words that are inside of the last move
		for (int i=0; i<locationsOfTurns.size(); i+= 1) {
			int col = locationsOfTurns.get(i)[0];
			int row = locationsOfTurns.get(i)[1];
			String[] foundWords = board.findWords(col, row);
			
			collection[i] = foundWords;
			
			// Checks if the last location is in line with the previous
			if (lastCol != col && lastRow != row) {
				System.out.println("\nERROR---------------------------------- \n\tThe plays must all be in the same row or column!\n ----------------------------------------");
				reloop = true;
			}
		
			//  Checks to see if the letter is all by itself
			if (foundWords[0] == "" && foundWords[1] == "") {
				System.out.println("\nERROR----------------------------------- \n\tYou have to play the letter next to another letter!\n ----------------------------------------");
				reloop = true;
			}
		}
		
		if (reloop) { // If the user put the tiles in an invalid spot then the code will loop to the beginning of the turn and restart/undo the the turn
			System.out.println("Your played letter will now return to your inventory \nand you will start placing them again!\n");
			returnTilesToHandFromBoard(board, locationsOfTurns, blankIndexes);
			reloop = false;
			return this.takeTurn(board, pile, view);
		}
		
		finalWords = removeDuplicates(collection); // Get unrepeated words from the collection[] and initialize finalWords with that value
		badWords = findBadWords(finalWords); // Search the finalWords ArrayList for words that don't actually exist
		
		
		if (badWords.size() > 0) { // if there are any non real words then tell player the words that don't exist and return them while looping to the top
			System.out.println("\nERROR----------------------------------- \n\tSome of your words " + badWords + " don't exist! \n ----------------------------------------");
			returnTilesToHandFromBoard(board, locationsOfTurns, blankIndexes);
			return this.takeTurn(board, pile, view);
		}	
		return displayEndOfTurnInfo(finalWords);
	}
	
	/**
	 * Prints out the scores that the player has played and it also updates the total score by the amount the player scored
	 * 
	 * @param finalWords the list of the words that the player played (these are the words that get scored)
	 * @return the value of the player's total score
	 */
	private int displayEndOfTurnInfo(ArrayList<String> finalWords) {
		//	Loops through the words they played and the corresponding values
		System.out.println("These are the words you played: ");
		int adder = 0, totalScore = 0; // Adder for values of letter within each word. totalScore of all the words within the ArrayList<String>
		for (int i=0; i<finalWords.size(); i++) {
			adder = 0;
			for (int k=0; k<finalWords.get(i).length(); k++) {
				adder += Letter.getValue(finalWords.get(i).charAt(k));
			}
			System.out.print("\t" + finalWords.get(i) + " (" + adder +")");
			totalScore += adder;
		}
		System.out.println("\tTotal From Turn: " + totalScore + " (" + score + totalScore  +")"); // Print out total score for that turn and the new total score
		score += totalScore; // Update the player's total score
		return totalScore;
		
	}
	/**
	 * Method finds any words that aren't real using method in Scrabble.java (which reads from txt file of valid words)
	 * and will return those words in the form of an ArrayList<String>
	 * @param words
	 * @return ArrayList<String> of the words that are invalid
	 * @author 21wwalling-sotolongo
	 */
	public static ArrayList<String> findBadWords(ArrayList<String> words){
		ArrayList<String> badWords = new ArrayList<String>();
		for (int i=0; i<words.size(); i++) {
			if (!Scrabble.isStringARealWord(words.get(i))) {
				badWords.add(words.get(i));
			}
		}
		return badWords;
	}
	
	/**
	 * Takesa a 2D list and simplifies it to a 1D list 
	 * @param OldCollection String[][] that will simplified
	 * @return simplified version of the parameter
	 * @author 21wwalling-sotolongo
	 */
	public static String[] NestedStringListToSingle (String[][] OldCollection) {
		String[] collection = new String[OldCollection.length * 2];
		int adder = 0;
		for (int i=0; i<OldCollection.length; i++) {
			for (int j=0; j<OldCollection[i].length; j++) {
				collection[adder] = OldCollection[i][j];
				adder ++;
			}
		}
		return collection;
	}
	
	/**
	 * Shows the actual "words" that are in the OldCollection by getting rid of "" [empty strings] and duplicates
	 * of any words within that OldCollection
	 * @param OldCollection String[][] of words to be checked
	 * @return ArrayList<String> gets 1 of repeated words and ices the ""
	 * @author 21wwalling-sotolongo
	 */
	public static ArrayList<String> removeDuplicates(String[][] OldCollection){
		String[] collection = NestedStringListToSingle(OldCollection);
		String word = "";
		boolean repeated = false;
		ArrayList<String> finalWords = new ArrayList<String>();
		for (int i=0; i<collection.length; i++) {
			word = collection[i];
			if (word == "") {continue;}
			for (int k=i+1; k<collection.length; k++) {
				// System.out.println("Comparing " + word + " + " + collection[k]);
				if (collection[k].equals(word)) {
					collection[i] = "";
					repeated = true;
				}
			}
			if (!repeated) { // if the word isn't repeated then it is added to the final cut
				finalWords.add(word);
			}
			repeated = false;
		}
		return finalWords;
	}

	/**
	 * Loops through all the plays that player made in the ArrayList<int[]> and will find the location of that turn on the board
	 * and will remove the letter from that object and add it back to hand at the index where it used to be
	 * @param board object of the game
	 * @param locationsOfTurns ArrayList<int[]> expection [Column, Row, IndexCardWasPlayedFrom]
	 * @param blankIndexes ArrayList<int>  of hand indexes of the blanks expects [IndexCardWasPlayedFrom]
	 * @author 21wwalling-sotolongo
	 */
	
	private void returnTilesToHandFromBoard(Board board, ArrayList<int[]> locationsOfTurns, ArrayList<Integer> blankIndexes) {
		for (int i=0; i<locationsOfTurns.size(); i++) {
			int col = locationsOfTurns.get(i)[0];
			int row = locationsOfTurns.get(i)[1];
			int index = locationsOfTurns.get(i)[2];
			Letter letter = board.get(col, row).removeLetter();
			if (hand.addLetter(letter, index) == false) {
				hand.addLetter(letter);
			}
		}
		for (int i=0; i<blankIndexes.size(); i++) {
			hand.getLetterIndex(blankIndexes.get(i)).setToBlank();
		}
	}
	
	
	/**
	 * Gets an input and checks if that input is within the proper range
	 * ** The range is inclusive **
	 * This acts the same way as the CodeHS readInt but it takes upper and lower range 
	 * 
	 * @param minRange integer value
	 * @param maxRange integer value
	 * @param message What the function asks the user to input for
	 * @return the acceptable value, inclusive (example; readInt(1,1) allows for 1)
	 * @author 21wwalling-sotolongo
	 */
	private int readInt(int minRange, int maxRange, String message) {
		System.out.println(message);
		int index = -1; // Index is equal to what the value the player enters is
		
		while (true) {
			if (maxRange != Integer.MAX_VALUE && minRange != Integer.MIN_VALUE) {
				System.out.println("Enter a number >= " + minRange + " and <=" + maxRange);
			} else {
				System.out.println("Enter a number");
			}
			// Try to get input, catch if it is a string or something
			String input = sc.nextLine();
			
			try {
				index = Integer.parseInt(input);
			} catch (NumberFormatException e){
				//"\nERROR----------------------------------- \n\tYou have to play the letter next to another letter!\n ----------------------------------------"
				if (maxRange != Integer.MAX_VALUE && minRange != Integer.MIN_VALUE) {
					System.out.println("\nERROR----------------------------------- \n\tIllegal character, you must enter a number, you entered " + input + "\n ----------------------------------------");
				}
				index = Integer.MIN_VALUE;
			}
			if (index == -123) {
				System.out.println("\nEXIT----------------------------------- \n\tYou entered the kill code, \n Thanks for playing");
				try {
		            Thread.sleep(2000);
		        } catch (InterruptedException e) {
		            e.printStackTrace();
		        }
				System.out.println("\n Attempting to exit");
				System.exit(0);
			}
			// Check if the number is within the range
			if (index > maxRange || index < minRange) {
				System.out.println("\nERROR----------------------------------- \n\tThe number must be between " + minRange + " and " + maxRange +"\n ----------------------------------------");
				continue;
			}
			break; // Breaks if nothing is wrong with the user's input
		}
		return index;
	}
	
	/**
	 * displays a message and then gets the user input.
	 * The range is all possible integers
	 * Calls the readInt(int minRange, int maxRange, String message) 
	 * with the message and MIN_VALUE and MAX_VALUE as the possible ranges
	 * Acts the same way as the CodeHS readInt() except if there is no number it returns MIN_RANGE
	 * @param message string  of what the function asks the user to input
	 * @return the user input
	 * @author 21wwalling-sotolongo
	 */
	
	@SuppressWarnings("unused")
	private int readInt(String message) {
		return readInt(Integer.MIN_VALUE, Integer.MAX_VALUE, message);
	}
	
	/**
	 * Method to draw a Letter from a Pile object and then put that letter into the hand
	 * @param pile object to have the Letter drawn from
	 * @return The letter that was drawn
	 * @author 21wwalling-sotolongo
	 */
	public Letter draw(Pile pile) {
		Letter letter = pile.drawTop();
		hand.addLetter(letter);
		return letter;
	}
	
	/**
	 * The string representation of the object
	 * @return The string of the object
	 * @author 21wwalling-sotolongo
	 */
	@Override
	public String toString(){
		return name + " with a hand of " + hand;
	}
	
	public int getScore() {
		return score;
	}
	
	public void addScore(int score) {
		this.score += score;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
	
	public String getName() {
		return name;
	}
	
	public Hand getHand() {
		return hand;
	}
}