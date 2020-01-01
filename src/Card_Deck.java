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
	
	//creates game deck (4 + 72 + 24 + 8 = 108 cards in total) 
	public void createDeck(){
		
		/* add 0's to deck
		 * 4 different zero's in deck (1 for each color) 
		 * point is 0
		 */
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
		
		/* add remaining number cards (1 to 9) 
		 * 72 different number's in deck (2 for each color) 
		 * point is equal to (number) on itself
		 */
		for(int number = 1; number < 10; number++) {
			for(String color: NUMBER_CARD_COLORS) {
				for(int j = 0; j < 2; j++) {
					UNO_Card card = new Number_Card(color, number);
					card.setPoint(number);			
					filename = "images/" + color.toLowerCase() + "_" + number + ".png"; 
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
		
		/* add action cards (reverse, skip, draw_2)
		 * 24 different action's in deck (2 for each color)
		 * point is equal to 20  
		 */
		int j = -1;
		for(String action: ACTIONS) {
			for(String color: NUMBER_CARD_COLORS) {
				for(int i = 0; i < 2; i++) {
					UNO_Card card = new Action_Card(color, j, action);
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
		
		/* add wild cards (color picker, draw_4)
		 * 8 different wild's in deck (4 for each action)
		 * point is equal to 50
		 */
		j = -15;
		for(String action: WILD_ACTIONS) {
			for(int i = 0; i < 4; i++) {
				UNO_Card card = new Wild_Card(-15,action);
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
