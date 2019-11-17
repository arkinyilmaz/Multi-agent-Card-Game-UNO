import java.util.ArrayList;

public class Player {
	
	private String name = null;
	private Boolean turn;
	private ArrayList<UNO_Card> hand;
	private Boolean isBot;
	
	public Player(String name, Boolean isBot) {
		this.name = name;
		this.turn = false;
		this.hand = new ArrayList<UNO_Card>();
		this.isBot = isBot;
	}
	
	public String getName() {
		return name;
	}
	
	public ArrayList<UNO_Card> getHand() {
		return hand;
	}
	
	public void drawCard(ArrayList<UNO_Card> remainingDeck) {
		hand.add(remainingDeck.get(0));
		remainingDeck.remove(0);
	}
}
