package application.backend;
import javafx.scene.layout.AnchorPane;

/**
 * This is a class to represent a playing board in scrabble
 * @author 21wwalling-sotolongo
 *
 */

public class Board {
	private Location[][] grid;

	// Constants for number of rows and columns.
	public static final int NUM_ROWS = 15;
	public static final int NUM_COLS = 15;
	
	private AnchorPane gamePane;
	/**
	 *  Create a new Grid. Initialize each Location in the grid
	 *  to be a new Location object.
	 *  
	 *  @author 21wwalling-sotolongo
	 */
	public Board(AnchorPane gamePane) {
	    grid = new Location[NUM_ROWS][NUM_COLS];
	    
	    // Loop through all the possible coordinates and place a Location object at each
	    // Rows are the x and cols are the y
	    for (int col=0; col<NUM_COLS; col++){
	        for (int row=0; row<NUM_ROWS; row++){
	            grid[col][row] = new Location();
	            grid[col][row].setLayoutY(Location.WIDTH/2 + col * Location.HEIGHT);
	            grid[col][row].setLayoutX(Location.HEIGHT/2 + row * Location.WIDTH);
	            gamePane.getChildren().add(grid[col][row]);
	            // System.out.println(gamePane.getChildren());
	        }
	    }
	    this.gamePane = gamePane;
	}


	/**
	 *  Set the status of this location object.
	 * @param row y cord
	 * @param col x cord
	 * @param status of the location
	 * 
	 * @author 21wwalling-sotolongo
	 */
	public void setStatus(int col, int row, int status) {
		grid[row][col].setStatus(status);
	}

	/**
	 *  Get the status of this location in the grid  
	 * @author 21wwalling-sotolongo
	 * @param row y
	 * @param col x
	 * @return the status
	 */
	public int getStatus(int col, int row) {
		return grid[row][col].getStatus();
	}

	
	/**
	 * Set letter to the location
	 * @author 21wwalling-sotolongo
	 * @param col x 
	 * @param row y
	 * @param letter to be added
	 */
	public void setLetter(int col, int row, Letter letter) {
		grid[row][col].setLetter(letter);
	}
	
	/**
	 * Get the letter at that location
	 * @author 21wwalling-sotolongo
	 * @param col x
	 * @param row y
	 * @return the letter at that Location on the grid
	 */
	public Letter getLetter(int col, int row) {
		return grid[row][col].getLetter();
	}

	/**
	 *  Return whether or not there is a letter here   
	 * @author 21wwalling-sotolongo
	 * @param col x
	 * @param row y
	 * @return if there is a letter at that spot
	 */
	public boolean hasLetter(int col, int row) {
		return grid[row][col].getStatus() == 0;
	}


	/**
	 *  Get the Location object at this row and column position
	 * 
	 * @author 21wwalling-sotolongo
	 * @param row
	 * @param col
	 * @return Location object at that spot
	 */
	public Location get(int col, int row) {
		return grid[row][col];
	}

	/**
	 *  Return the number of rows in the Grid
	 * @return num of rows
	 * @author 21wwalling-sotolongo
	 */
	public int numRows() {
		return NUM_ROWS;
	}

	/**
	 *  Return the number of columns in the grid
	 * @return number of columns
	 * @author 21wwalling-sotolongo
	 */
	public int numCols() {
		return NUM_COLS;
	}


	
	/**
	 * Print the Grid status including a header at the top
	 *  that shows the columns 1 to MAX_COLS as well as numbers along the side for 1 to MAX_ROWS
	 *  If there is no letter print '-"
	 *  If there is a letter print the letter
	 *  @author 21wwalling-sotolongo
	 *
	 *This is what the grid looks like
	*   1 2 3 4 5 6 7 8 9 10 
	* 1  - - - - - - - - - - 
	* 2  - - - - - - - - - - 
	* 3  - - - a - - - - - d 
	* 4  - - - - - - - - - - 
	* 5  - - - - - - - - - - 
	* 6  - - - - - - - - - - 
	* 7  - - - - - - - - - - 
	* 8  - - - - - - b - - - 
	* 9  - - - - - - - - - - 
	* 10 - - - - - - - - - - 
	* imagine it continues until 15...
	*/
	public void printStatus() {
		// Initialize variable used in loop (saves memory to do it beforehand)
		Location loc;
		int stat;
		Letter letter;
		
		// Print out the header
		System.out.println("   0  1  2  3  4  5  6  7  8  9 10 11 12 13 14");
		
		// Loop through the whole grid and print out the information of each location
		for (int row=0; row<NUM_ROWS; row++){
			System.out.print(row + " "); // Print out the left-hand pointers
			if (row < 10) {
				System.out.print(" ");
			}
	        for (int col=0; col<NUM_COLS; col++){
	            loc = grid[row][col];
	            letter = loc.getLetter();
	            stat = loc.getStatus();
	            
	            if (stat == 0) { // If there is nothing print '-'
	            	System.out.print('-');
	            } else { // This means that something is at that location, so print that letter
	            	System.out.print(letter);
	            }
	            
	            System.out.print("  "); //Buffer to make the headers line with what was printed
	        }
	        System.out.println(); // Make new line at the end
	    } 
	}
	
	public void printStatus(AnchorPane gamePane) {
		// Initialize variable used in loop (saves memory to do it beforehand)
		Location loc;
	
		for (int row=0; row<NUM_ROWS; row++){
	        for (int col=0; col<NUM_COLS; col++){
	            loc = grid[row][col]; 
	            loc.updateTextOnButton();
	        }
	    } 
	}
	
	public void printStatus(boolean bol) {
		printStatus(gamePane);
	}
	
	
	/**
	 * This method will search the grid from a provided point and return the words that point is part of
	 * It will check the 4 directions around the point and see what letters are there and then loop through the locations
	 * between the tow points in order to create a string which is then added to the array.
	 * @author 21wwalling-sotolongo
	 * @param col  the column where the location is
	 * @param row  the row where the location is
	 * @return array of the words the location is part of (length of 2)
	 */
	// grid[row][col]...
	public String[] findWords(int col, int row) {
		String[] wordList = new String[2]; // Makes an array to hold the values of the words that it finds
		// Goes to the right of the point
		int locationValueRight = -1;
		for (int i=col; i<15; i++) {
			if (i==14) {
				locationValueRight = 14; // if all the way to the right
			}
			if (grid[row][i].getStatus() == Location.EMPTY) { // If status == 0 that means that there is no letter at that location
				locationValueRight = i - 1;
				break;
			}
		}
		
		// Goes to the left of the point
		int locationValueLeft = -1;
		for (int i=col; i>-1; i--) {
			if (i==0) {
				locationValueLeft = 0;
			}
			if (grid[row][i].getStatus() == Location.EMPTY) {
				locationValueLeft = i + 1;
				break;
			}
		}
		
		// Goes to the above of the point
		int locationValueUp = -1;
		for (int i=row; i>-1; i--) {
			if (i==0) {
				locationValueUp = 0;
			}
			if (grid[i][col].getStatus() == Location.EMPTY) {
				locationValueUp = i + 1;
				break;
			}
		}
		
		// Goes to the below of the point
		int locationValueDown = -1;
		for (int i=row; i<15; i++) {
			if (i==14) {
				locationValueDown = 14;
			}
			if (grid[i][col].getStatus() == Location.EMPTY) {
				locationValueDown = i - 1;
				break;
			}
		}
		
		// System.out.println("\nValues:\n	locationValueLeft: " + locationValueLeft + "\n	locationValueRight: " + locationValueRight + "\n	locationValueUp: " + locationValueUp + "\n	locationValueDown: " + locationValueDown); Displays the values of the word finders
		String filler = "";
		// Generate word from the horizontal coordinates
		for (int i=locationValueLeft; i<locationValueRight+1; i++) {
			filler = filler + grid[row][i].getLetter().toString();
		}
		// Adds the new word
		wordList[0] = filler;
		filler = "";
		for (int i=locationValueUp; i<locationValueDown+1; i++) { // Generate word from the vertical coordinates
			filler = filler + grid[i][col].getLetter().toString();
		}
		// Adds the new word if it actually exists
		wordList[1] = filler;
		
		for (int i=0; i<2; i++) {
			if (wordList[i].length() <= 1) {
				wordList[i] = "";
			}
		}
		
		
		return wordList;
	}
	
	public Integer[] checkIfAnyLocationClicked() { 
		Integer[] coordinate = {-1,-1};
		for (int row=0; row<NUM_ROWS; row++){
	        for (int col=0; col<NUM_COLS; col++){
	            Location loc = grid[row][col];
	            if (loc.isClicked()) {
	            	coordinate[0] = row;
	            	coordinate[1] = col;
	            	return coordinate;
	            }
	        }
	    } 
		return coordinate;
	}
	public String toString() {
		printStatus();
		return "";
	}

	
}
