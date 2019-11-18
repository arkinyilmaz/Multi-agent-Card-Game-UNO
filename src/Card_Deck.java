import java.util.ArrayList;

public class Card_Deck {

	private ArrayList<UNO_Card> deck;
	
	private final String[] NUMBER_CARD_COLORS = {"RED","YELLOW","GREEN","BLUE"};
	private final String[] WILD_ACTIONS = {"COLOR_PICKER", "DRAW_4"};
	private final String[] ACTIONS = {"REVERSE", "SKIP", "DRAW_2"};
	
	private final int WILD_CARD_POINTS = 50;
	private final int ACTION_CARD_POINTS = 20;
	
	public Card_Deck() {
		deck = new ArrayList<>();
		
		createDeck();
	}
	
	public void createDeck() {
		
		//add 0's to deck
		for(String color : NUMBER_CARD_COLORS) {
			UNO_Card card = new Number_Card(color, 0);
			card.setPoint(0);
			deck.add(card);
		}
		
		//add remaining numbers twice
		for(int i = 1; i < 10; i++) {
			for(String color: NUMBER_CARD_COLORS) {
				for(int j = 0; j < 2; j++) {
					UNO_Card card = new Number_Card(color, i);
					card.setPoint(i);
					deck.add(card);
				}
			}
		}
		
		//add action cards
		for(String action: ACTIONS) {
			for(String color: NUMBER_CARD_COLORS) {
				for(int i = 0; i < 2; i++) {
					UNO_Card card = new Action_Card(color, action);
					card.setPoint(ACTION_CARD_POINTS);
					deck.add(card);
				}
			}
		}
		
		//add wild cards
		for(String action: WILD_ACTIONS) {
			for(int i = 0; i < 4; i++) {
				UNO_Card card = new Wild_Card(action);
				card.setPoint(WILD_CARD_POINTS);
				deck.add(card);
			}
		}	
	}
	
	public ArrayList<UNO_Card> getDeck(){
		return deck;
	}
}
