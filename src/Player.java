import java.util.ArrayList;

public class Player {
	
	private String name = null;
	private Boolean turn;
	private ArrayList<UNO_Card> hand;
	
	public Player(String name) {
		this.name = name;
		this.turn = false;
		this.hand = new ArrayList<UNO_Card>();
	}
	
	public String getName() {
		return name;
	}
	
	public ArrayList<UNO_Card> getHand() {
		return hand;
	}
	

}
