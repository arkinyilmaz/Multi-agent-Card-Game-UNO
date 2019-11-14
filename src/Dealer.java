import java.util.ArrayList;
import java.util.Collections;

public class Dealer {
	
	Card_Deck deck;
	
	public Dealer() {
		deck = new Card_Deck();
		shuffleDeck();
	}
	
	public void shuffleDeck() {
		Collections.shuffle(deck.getDeck());
	}
	
	public void spreadCards(Player[] players) {
		
		int numOfPlayer = players.length;
		
		for(int i = 0; i < 7; i++) {
			for(int j = 0; j < numOfPlayer; j++) {
				players[j].getHand().add(deck.getDeck().get(0));
				deck.getDeck().remove(0);
			}
		}		
	}
	
	public ArrayList<UNO_Card> getRemainingDeck() {
		return deck.getDeck();
	}

}
