package model;

public enum SHIP {
	BLUE("view/resources/shipchooser/playerShip1_blue.png", "view/resources/shipchooser/playerLife1_blue.png"),
	GREEN("view/resources/shipchooser/playerShip1_green.png", "view/resources/shipchooser/playerLife1_green.png"),
	ORANGE("view/resources/shipchooser/playerShip1_orange.png", "view/resources/shipchooser/playerLife1_orange.png"),
	RED("view/resources/shipchooser/playerShip1_red.png", "view/resources/shipchooser/playerLife1_red.png");
	
	private String urlShip;
	private String urlShipLife;
	
	private SHIP(String urlShip, String urlShipLife) {
		this.urlShip = urlShip;
		this.urlShipLife = urlShipLife;
	}
	
	public String getUrl() {
		return this.urlShip;
	}
	
	public String getUrlLife() {
		return urlShipLife;
	}
	
	public int getDifficulty () {
		int associtedDifficulty;
		switch (this) {
	        case BLUE:  associtedDifficulty = 3;
	                 break;
	        case GREEN:  associtedDifficulty = 4;
	                 break;
	        case ORANGE:  associtedDifficulty = 5;
	                 break;
	        case RED: associtedDifficulty = 6;
	        		 break;
	        default: associtedDifficulty = 3;
	                 break;
		}
		return associtedDifficulty;
	}
}

