import java.util.ArrayList;

public class Main {
	
	public static void main(String[] args) {
		
		//create card deck
		Card_Deck deck = new Card_Deck();
		
		//obtain
		ArrayList<UNO_Card> cardDeck = deck.getDeck();
		
		//print deck size
		System.out.println(cardDeck.size());
		
		//print each card
		for(int i = 0; i < cardDeck.size(); i++) {
			UNO_Card card = cardDeck.get(i);
			System.out.println("Card Color: " + card.getColor() + " | Card Value: " + card.getValue() + " | Card Type: " + card.getType() + "| Card Action: " + card.getAction());
		}
				
	}

}
