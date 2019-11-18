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
			System.out.println("Card Color: " + card.getColor() + " | Card Value: " + card.getValue() + " | Card Type: " + card.getType() + "| Card Action: " + card.getAction()
			+ " | Card Point: " + card.getPoint());
		}
		
		//spread cards to each player
		dealer.spreadCards(players);
		
		//print players' hands
		for(int i = 0 ; i < players.length; i++) {
			printPlayerHand(players[i]);
		}	
		
		System.out.println("\nNumber of remaining cards: " + remainingCards.size());
		
		//open first card from deck to start game
		playedCards.push(remainingCards.remove(0));
		
		System.out.println("\nStarting Card: " + playedCards.peek().getColor() + "\n" + playedCards.peek().getValue() + "\n" + playedCards.peek().getAction());
		
		//print the number of remaining cards
		System.out.println("\nNumber of remaining cards: " + remainingCards.size());
		
		players[0].playCard(getPlayedCard());
	}
	
	public UNO_Card getPlayedCard() {
		return playedCards.peek();
	}
	
	public void printPlayerHand(Player p) {
		System.out.println("\nPLAYER: " + p.getName());
		ArrayList<UNO_Card> p_hand = p.getHand();
		for(int i = 0; i < p_hand.size(); i++) {
			System.out.println("Card Color: " + p_hand.get(i).getColor() + " | Card Value: " + p_hand.get(i).getValue() 
								+ " | Card Type: " + p_hand.get(i).getType() + "| Card Action: " + p_hand.get(i).getAction());
		}
	}
}
