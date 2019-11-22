import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Locale;

import javafx.scene.image.Image;

public class Card_Deck {

	private ArrayList<UNO_Card> deck;
	
	private final String[] NUMBER_CARD_COLORS = {"RED","YELLOW","GREEN","BLUE"};
	private final String[] WILD_ACTIONS = {"COLOR_PICKER", "DRAW_4"};
	private final String[] ACTIONS = {"REVERSE", "SKIP", "DRAW_2"};
	
	private final int WILD_CARD_POINTS = 50;
	private final int ACTION_CARD_POINTS = 20;
	
	private String filename = "";
	private FileInputStream input = null;
	
	public Card_Deck() {
		deck = new ArrayList<>();
		
		createDeck();
	}
	
	/*if(card_type == 1) {
		fileName = "images/" + card_color.toLowerCase() + "_" + card_value + ".png";
	}
	else {
		fileName = "images/" + card_color.toLowerCase() + "_" + action.toLowerCase(Locale.ENGLISH) + ".png";
	}*/
	
	public void createDeck(){
		
		//add 0's to deck
		for(String color : NUMBER_CARD_COLORS) {
			UNO_Card card = new Number_Card(color, 0);
			card.setPoint(0);
			filename = "images/" + color.toLowerCase() + "_0.png"; 
			try {
				input = new FileInputStream(filename);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			card.setImage(new Image(input));
			deck.add(card);
		}
		
		//add remaining numbers twice
		for(int i = 1; i < 10; i++) {
			for(String color: NUMBER_CARD_COLORS) {
				for(int j = 0; j < 2; j++) {
					UNO_Card card = new Number_Card(color, i);
					card.setPoint(i);			
					filename = "images/" + color.toLowerCase() + "_" + i + ".png"; 
					try {
						input = new FileInputStream(filename);
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					card.setImage(new Image(input));
					deck.add(card);
				}
			}
		}
		
		//add action cards
		int j = -1;
		for(String action: ACTIONS) {
			for(String color: NUMBER_CARD_COLORS) {
				for(int i = 0; i < 2; i++) {
					UNO_Card card = new Action_Card(color, action, j);
					card.setPoint(ACTION_CARD_POINTS);
					filename = "images/" + color.toLowerCase() + "_" + action.toLowerCase(Locale.ENGLISH) + ".png"; 
					try {
						input = new FileInputStream(filename);
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					card.setImage(new Image(input));
					deck.add(card);
				}
			}
			j--;
		}
		
		//add wild cards
		j = -15;
		for(String action: WILD_ACTIONS) {
			for(int i = 0; i < 4; i++) {
				UNO_Card card = new Wild_Card(action, -15);
				card.setPoint(WILD_CARD_POINTS);
				filename = "images/black_" + action.toLowerCase(Locale.ENGLISH) + ".png"; 
				try {
					input = new FileInputStream(filename);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				card.setImage(new Image(input));
				deck.add(card);
			}
			j--;
		}	
	}
	
	public ArrayList<UNO_Card> getDeck(){
		return deck;
	}
}
