import java.util.ArrayList;
import java.util.Collections;

public class Card_Deck {

	private ArrayList<UNO_Card> deck;
	
	private final String[] NUMBER_CARD_COLORS = {"RED","YELLOW","GREEN","BLUE"};
	private final String[] WILD_ACTIONS = {"COLOR_PICKER", "DRAW_4"};
	private final String[] ACTIONS = {"REVERSE", "SKIP", "DRAW_2"};
	
	public Card_Deck() {
		deck = new ArrayList<>();
		
		createDeck();
		shuffle();
	}
	
	public void createDeck() {
		
		//add 0's to deck
		for(String color : NUMBER_CARD_COLORS) {
			UNO_Card card = new Number_Card(color, 0);
			deck.add(card);
		}
		
		//add remaining numbers twice
		for(int i = 1; i < 10; i++) {
			for(String color: NUMBER_CARD_COLORS) {
				UNO_Card card = new Number_Card(color, i);
				UNO_Card card2 = new Number_Card(color, i);		
				
				deck.add(card);
				deck.add(card2);
			}
		}
		
		//add action cards
		for(String action: ACTIONS) {
			for(String color: NUMBER_CARD_COLORS) {
				UNO_Card card = new Action_Card(color, action);
				UNO_Card card2 = new Action_Card(color, action);
				
				deck.add(card);
				deck.add(card2);
			}
		}
		
		//add wild cards
		for(String action: WILD_ACTIONS) {
			UNO_Card card = new Wild_Card(action);
			UNO_Card card2 = new Wild_Card(action);
			UNO_Card card3 = new Wild_Card(action);
			UNO_Card card4 = new Wild_Card(action);
			
			deck.add(card);
			deck.add(card2);
			deck.add(card3);
			deck.add(card4);
		}	
	}
	
	public void shuffle() {
		Collections.shuffle(deck);
	}
	
	public ArrayList<UNO_Card> getDeck(){
		return deck;
	}
}
