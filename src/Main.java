import java.util.ArrayList;

public class Main {
	
	public static void main(String[] args) {
				
		Player p1 = new Player("Arko");
		Player p2 = new Player("Zey");
		Player p3 = new Player("Akkorus");
		
		Player[] players = { p1, p2, p3};
		
		Game_Engine game = new Game_Engine(players);
		game.start();
		
		//print players' hands
		for(int i = 0 ; i < players.length; i++) {
			printPlayerHand(players[i]);
		}
	}
	
	public static void printPlayerHand(Player p) {
		System.out.println("\nPLAYER: " + p.getName());
		ArrayList<UNO_Card> p_hand = p.getHand();
		for(int i = 0; i < p_hand.size(); i++) {
			System.out.println("Card Color: " + p_hand.get(i).getColor() + " | Card Value: " + p_hand.get(i).getValue() 
								+ " | Card Type: " + p_hand.get(i).getType() + "| Card Action: " + p_hand.get(i).getAction());
		}
	}

}
