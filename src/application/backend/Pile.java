/** 
 * This is a class representing a pile of letters to draw from (think deck from blackjack)
 * @author 21wwalling-sotolongo
 *
 */
package application.backend;
import java.util.*;


public class Pile {
	public static final int TOTAL_LETTERS = 100;
	public static final String EVERY_LETTER = "EEEEEEEEEEEEAAAAAAAAAIIIIIIIIIOOOOOOOONNNNNNRRRRRRTTTTTTLLLLSSSSUUUUDDDDGGGBBCCMMPPFFHHVVWWYYKJXQZ  "; // Every letter in the scrabble pile, has two blanks at the end
	private ArrayList<Letter> pile = new ArrayList<Letter>();
	
	/**
	 * Constructor for Pile,
	 *     - Makes a new pile (ArrayList)
	 *     - Shuffles the pile
	 * @author 21wwalling-sotolongo
	 */
	public Pile() {
		generatePile();
		shuffle();
	}
	
	/**
	 * Generates a pile of letters and adds them to the end of the pile ArrayList
	 * Gets letters from EVERY_LETTER at the index i
	 * @author 21wwalling-sotolongo
	 */
	private void generatePile() {
		for (int i=0; i<TOTAL_LETTERS; i++) {
			pile.add(new Letter(EVERY_LETTER.charAt(i)));
		}
	}
	
	/**
	 * Removes a letters from the pile and returns it, unless there is no more tiles in which null is returned.
	 * @author 21wwalling-sotolongo
	 * @return first letter in the pile
	 */
	public Letter drawTop() {
		if (pile.size() > 0) {
			return pile.remove(0);
		}
		return null;
	}
	
	/**
	 * Adds the number of cards that the hand starts of with (7)
	 * @param hand
	 */
	public void dealStart(Hand hand) {
		for (int i=0; i < 7; i++) {
			hand.addLetter(drawTop());
		}	
	}
	
	/**
	 * Adds a letter to the end of the pile
	 * @param letter to be added to the end of the pile
	 */
	public void returnLetter(Letter letter) {
		pile.add(letter);
	}
	
	/**
	 * Shuffles the pile using java.util.random
	 * @author 21wwalling-sotolongo
	 */
	public void shuffle() {
		Random r = new Random(); // Create instance of random
		Letter[] temp = new Letter[pile.size()]; // This is the temporary list to hold the new random locations
		for (int i=0; pile.size() > 0; i++) { // Copy over the letters in the unshuffled list and remove and add a random letter from the orignal list to the temp
			temp[i] = pile.remove(r.nextInt(pile.size()));
		}
		
		for (int i=0; i<temp.length; i++) { // Merge the Array back to the ArrayList via iteration
			pile.add(temp[i]); 
		}
	}	
	
	/**
	 * Gives the size of the pile (how many letters are in it)
	 * @return int of the size of the pile
	 */
	public int getSize() {
		return pile.size();
	}
	/**
	 * String representation of the object
	 * @author 21wwalling-sotolongo
	 * @return String representation
	 */
	@Override
	public String toString() {
		String str = "PILE length "  + pile.size() + ": \n";
		for (int i=0; i<pile.size(); i++) {
			str += "\t" + pile.get(i) + "\n";
		}
		return str;
	}
}
