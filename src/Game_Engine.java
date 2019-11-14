import java.util.ArrayList;

public class Game_Engine {
	
	Dealer dealer;
	Player[] players;
	ArrayList<UNO_Card> remainingCards;
	
	//create game engine object
	public Game_Engine(Player[] players) {
		dealer = new Dealer();
		this.players = players;
		remainingCards = dealer.getRemainingDeck();
	}
	
	public void start() {
		//shuffle deck
		dealer.shuffleDeck();
				
		//print deck size
		System.out.println("Initial Deck Size is: " + remainingCards.size());
		
		//print each card
		for(int i = 0; i < remainingCards.size(); i++) {
			UNO_Card card = remainingCards.get(i);
			System.out.println("Card Color: " + card.getColor() + " | Card Value: " + card.getValue() + " | Card Type: " + card.getType() + "| Card Action: " + card.getAction());
		}
		
		//spread cards to each player
		dealer.spreadCards(players);
		
		//print the number of remaining cards
		System.out.println("Number of remaining cards: " + remainingCards.size());
	}
}
