import java.util.ArrayList;
import java.util.Stack;

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
	
	public Boolean isBot() {
		return isBot;
	}
	
	public Boolean isTurn() {
		return turn;
	}
	
	public void setTurn(Boolean b) {
		turn = b;
	}
	
	public ArrayList<UNO_Card> getHand() {
		return hand;
	}
	
	public int[] getColorsInHand(){
		
		int[] colors_in_hand = {0,0,0,0};
		
		for(int i = 0; i < hand.size(); i++) {
			if(hand.get(i).getColor().equals("RED"))
				colors_in_hand[0]++;
			else if (hand.get(i).getColor().equals("YELLOW"))
				colors_in_hand[1]++;
			else if (hand.get(i).getColor().equals("GREEN"))
				colors_in_hand[2]++;
			else 
				colors_in_hand[3]++;
		}
		
		return colors_in_hand;
	}
	
	public int getHandPoints() {
		int points = 0;
		for(int i = 0; i < hand.size(); i++) {
			points += hand.get(i).getPoint();
		}
		return points;
	}
	
	public void drawCard(ArrayList<UNO_Card> remainingDeck) {
		hand.add(remainingDeck.get(0));
		remainingDeck.remove(0);
	}
}
