import java.util.ArrayList;
import java.util.Stack;

public class Game_Engine {
	
	Dealer dealer;
	Player[] players;
	ArrayList<UNO_Card> remainingCards;
	Stack<UNO_Card> playedCards;
	
	//create game engine object
	public Game_Engine(Player[] players) {
		dealer = new Dealer();
		this.players = players;
		remainingCards = dealer.getRemainingDeck();
		playedCards = new Stack<UNO_Card>();
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
		
		System.out.println("Number of remaining cards: " + remainingCards.size());
		
		//open first card from deck to start game
		playedCards.push(remainingCards.remove(0));
		
		System.out.println("Starting Card: " + playedCards.peek().getColor() + "\n" + playedCards.peek().getValue() + "\n" + playedCards.peek().getAction());
		
		//print the number of remaining cards
		System.out.println("Number of remaining cards: " + remainingCards.size());
	}
}
