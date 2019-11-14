import java.util.ArrayList;

public class Main {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Card_Deck deck = new Card_Deck();
		
		ArrayList<UNO_Card> cardDeck = deck.getDeck();
		
		System.out.println(cardDeck.size());
		
		for(int i = 0; i < cardDeck.size(); i++) {
			UNO_Card card = cardDeck.get(i);
			System.out.println("Card Color: " + card.getColor() + " | Card Value: " + card.getValue() + " | Card Type: " + card.getType() + "| Card Action: " + card.getAction());
		}
				
	}

}
