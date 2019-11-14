import java.util.ArrayList;

public class Main {
	
	public static void main(String[] args) {
		
		Dealer dealer = new Dealer();
		
		Player p1 = new Player("Arko");
		Player p2 = new Player("Zey");
		
		Player[] players = { p1, p2};
			
		//obtain
		ArrayList<UNO_Card> cardDeck = dealer.getRemainingDeck();
		
		//print deck size
		System.out.println(cardDeck.size());
		
		//print each card
		for(int i = 0; i < cardDeck.size(); i++) {
			UNO_Card card = cardDeck.get(i);
			System.out.println("Card Color: " + card.getColor() + " | Card Value: " + card.getValue() + " | Card Type: " + card.getType() + "| Card Action: " + card.getAction());
		}
		
		//spread cards to players
		dealer.spreadCards(players);
		
		//print p1 hand
		printPlayerHand(p1);
		
		//print p2 hand
		printPlayerHand(p2);
		
		//print the number of remaining cards
		System.out.println("Number of remaining cards: " + cardDeck.size());
				
	}
	
	public static void printPlayerHand(Player p) {
		System.out.println("PLAYER: " + p.getName());
		ArrayList<UNO_Card> p_hand = p.getHand();
		for(int i = 0; i < p_hand.size(); i++) {
			System.out.println("Card Color: " + p_hand.get(i).getColor() + " | Card Value: " + p_hand.get(i).getValue() 
								+ " | Card Type: " + p_hand.get(i).getType() + "| Card Action: " + p_hand.get(i).getAction());
		}
	}

}
