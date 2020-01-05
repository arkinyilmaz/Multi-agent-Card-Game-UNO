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
	
	// get the number of each colors in hand
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
	
	// get the total hand points for the player
	public int getHandPoints() {
		int points = 0;
		for(int i = 0; i < hand.size(); i++) {
			points += hand.get(i).getPoint();
		}
		return points;
	}
	
	// draw 1 card from the pile to hand
	public void drawCard(ArrayList<UNO_Card> remainingDeck) {
		hand.add(remainingDeck.get(0));
		remainingDeck.remove(0);
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
}
