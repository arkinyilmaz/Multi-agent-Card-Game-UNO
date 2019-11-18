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
	
	public Boolean isBot() {
		return isBot;
	}
	
	public ArrayList<UNO_Card> getHand() {
		return hand;
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
	
	public void playCard(UNO_Card lastPlayedCard) {
		
		//card in the middle
		String lastPlayedCardColor = lastPlayedCard.getColor();
		int lastPlayedCardValue = lastPlayedCard.getValue();
		
		//store the number of possible next moves for each card
		int[] possibleNextMoves = new int[hand.size()];
		
		//store the number of remaining hand points
		int[] remainingHandPoints = new int[hand.size()];
		
		//add next players hand situation
		
		for(int i = 0; i < hand.size(); i++) {
			
			UNO_Card card = hand.get(i);
			
			if(card.getColor().equals("BLACK"))
				possibleNextMoves[i] = 1;
			else if(!card.getColor().equals(lastPlayedCardColor) && card.getValue() != lastPlayedCardValue) {
				possibleNextMoves[i] = 0;
			}
			else{
				int amount = 0;
				for(int j = 0; j < hand.size(); j++) {
					if(hand.get(j).getColor().equals(card.getColor())) {
						amount++;
					}
				}
				
				possibleNextMoves[i] = amount;
			}
			
			remainingHandPoints[i] = getHandPoints() - card.getPoint();
		}
		
		String nm = "";
		String hp = ""
				; 
		for(int i = 0; i < hand.size(); i++) {
			nm = nm + possibleNextMoves[i] + " | ";
			hp = hp + remainingHandPoints[i] + " | ";
		}
		
		System.out.println("Possible Next Moves: " + nm);
		System.out.println("Remaining Hand Points: " + hp);		
	}
}
