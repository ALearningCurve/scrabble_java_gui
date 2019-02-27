package application.backend;

/**
 * This is a hand to represent the hand/rack which each player has and will hold/do things with the 
 * letters
 * @author 21wwalling-sotolongo
 */

public class Hand {
	public static final int MAX_CARDS_IN_HAND = 7;
	private Letter[] hand;
	
	/**
	 * Constructor for the hand class
	 * 		- Creates a list to store the letters which it has
	 */
	public Hand() {
		hand = new Letter[MAX_CARDS_IN_HAND];
	}
	
	public Letter[] getLetterList() {
		return hand;
	}
	/**
	 * Adds the parameter to the hand, Its the same a drawing
	 * returns whether or not the letter has been added successfully
	 * 
	 * @param letter that is added to the array
	 * @return whether or not the letter was added
	 * @author 21wwalling-sotolongo
	 */
	public boolean addLetter(Letter letter) {
			boolean full = true;
			int index = -1;
			for (int i=0; i<MAX_CARDS_IN_HAND; i++) {
				if (hand[i] == null) {
					full = false;
					index = i;
					break;
				}
			}
		    // can't add to full hand or if there is no spot for the tile to go
			if (full == true || index == -1) {
				// System.out.println("The hand is already full, nothing can be added!");
				return false; // If the list is full, then nothing can be added

			}
			hand[index] = letter;
			return true; // This means that a card was added	
	}
	
	/**
	 * returns whether or not the hand is empty (true for if there is cards and false if there isn't)
	 * @return boolean of whether or not hand is empty
	 */
	public boolean checkIfEmpty() {
		for (int i=0; i<MAX_CARDS_IN_HAND; i++) {
			if (hand[i] != null) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Adds the parameter to the hand, Its the same a drawing
	 * returns whether or not the letter has been added successfully
	 * Adds to the specified index of the hand
	 * 
	 * @param letter that is added to the array
	 * @param index of where the letter should be returned to
	 * @return whether or not the letter was added
	 * @author 21wwalling-sotolongo
	 */
	public boolean addLetter(Letter newTile, int index) {
		if (hand[index] == null) {
			hand[index] = newTile;
			return true;
		}
		return false;
	}
	/**
	 * See addLetter(e)
	 * @param letter to be added
	 */
	public boolean drawLetter(Letter letter) {
		return addLetter(letter);
	}
	
	/**
	 * return the letter at the given index
	 * @param index int of the letter's index
	 * @return letter at the index
	 * @author 21wwalling-sotolongo
	 */
	public Letter getLetterIndex(int index) {
		return hand[index];
	}

	/**
	 * Removes and returns a letter at an index
	 * @param index where the letter to be removed is located
	 * @return the letter that was removed
	 */
	public Letter remove(int index) {
		try {
			Letter l = hand[index];
			hand[index] = null;
			return l;
		} catch (Exception e){
			return null;
		}
		
	}
	
	
	public int checkIfThingsInHandHaveBeenClicked() {
		int clickedIndex = -1;
		for (int i =0; i <hand.length; i ++) {
			if (hand[i] != null && hand[i].clicked) {
				hand[i].clicked = false;
				clickedIndex = i;
			}
		}
		return clickedIndex;
	}
	/**
	 * Returns the string representation of a hand object
	 * @return the string representation of the hand
	 * @author 21wwalling-sotolongo
	 */
	@Override
	public String toString() {
		String str = "";
		String message;
		for (int i=0; i<hand.length; i++) {
			if (hand[i] != null) {
				message = hand[i].toString();
			} else {
				message = "Has been played";
			}
			str += "  " + i + ". " + message + "\n";
		}
		return str;
	}
	
	public Letter[] getList() {
		return hand;
	}
}
