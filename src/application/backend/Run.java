package application.backend;
/**
 * This class serves the purpose of creating the scrabble class instance (by extension starting the game and such) 
 * and maybe some other stuff in the future such as game size or whatnot.
 * @author 21wwalling-sotolongo
 */
public class Run {
	public static void main(String[] args) {
        new Scrabble();
    }
	public Run(){
		new Scrabble();	
	}
}
