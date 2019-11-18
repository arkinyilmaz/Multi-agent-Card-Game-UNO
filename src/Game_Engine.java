import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Stack;

public class Game_Engine {
	
	Dealer dealer;
	Player[] players;
	ArrayList<UNO_Card> remainingCards;
	Stack<UNO_Card> playedCards;
	int gameDirection;
	int gameTurn;
	
	//create game engine object
	public Game_Engine(Player[] players) {
		dealer = new Dealer();
		this.players = players;
		remainingCards = dealer.getRemainingDeck();
		playedCards = new Stack<UNO_Card>();
		gameDirection = 1;
		gameTurn = new Random().nextInt(players.length);
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
		while(remainingCards.get(0).getType() != 1) {
			dealer.shuffleDeck();
		}
		playedCards.push(remainingCards.remove(0));
		
		System.out.println("\nStarting Card: " + playedCards.peek().getColor() + "\n" + playedCards.peek().getValue() + "\n" + playedCards.peek().getAction());
		
		//print the number of remaining cards
		System.out.println("\nNumber of remaining cards: " + remainingCards.size());
		
		//choose the starting player
		players[gameTurn].setTurn(true);
		
		/*while(!isGameEnded()) {
			playCard(players[gameTurn]);
			gameTurn = (gameTurn + gameDirection + 4) % players.length;
		}*/
		
		//playCard(players[0]);
		System.out.println("\nStarting Card: " + playedCards.peek().getColor() + "\n" + playedCards.peek().getValue() + "\n" + playedCards.peek().getAction());
		System.out.println("\nNumber of remaining cards: " + remainingCards.size());
		printPlayerHand(players[0]);

	}
	
	//check if game is ended or not
	public Boolean isGameEnded() {
		for(int i = 0; i < players.length; i++) {
			if(players[i].getHand().size() == 0) {
				System.out.println(players[i].getName() + " wins!!!!!");
				return true;
			}
		}
		
		return false;
	}
	
	public int getGameTurn() {
		return gameTurn;
	}
	
	public int getGameDirection() {
		return gameDirection;
	}
	
	public void setGameTurn(int gameTurn) {
		this.gameTurn = gameTurn;
	}
	
	//perform action -- skip player
	public void skipPlayer() {
		gameTurn = (gameTurn + gameDirection) % players.length;
	}
	
	//perform action -- reverseGameDirection
	public void reverseGameDirection() {
		if(gameDirection == 1)
			gameDirection = -1;
		else
			gameDirection = 1;
	}
	
	//perform action -- draw two cards
	public void drawTwo() {
		int nextPlayer = (gameTurn + gameDirection + 4) % players.length;
		for(int i = 0; i < 2; i++) {
			players[nextPlayer].drawCard(remainingCards);
		}
	}
	
	//perform wild action -- draw four cards and change color
	public void drawFour() {
		int nextPlayer = (gameTurn + gameDirection + 4) % players.length;
		for(int i = 0; i < 4; i++) {
			players[nextPlayer].drawCard(remainingCards);
		}
		
		int[] colors_in_hand = players[gameTurn].getColorsInHand();
		String[] colors = {"RED","YELLOW","GREEN","BLUE"};
		
		int max = colors_in_hand[0];
		int max_index = 0;
		for(int i = 1; i < colors_in_hand.length; i++) {
			if(colors_in_hand[i] > max) {
				max = colors_in_hand[i];
				max_index = i;
			}
		}
		
		getPlayedCard().setColor(colors[max_index]);
	}
	
	//perform wild action -- change color
	public void colorPicker() {
		int[] colors_in_hand = players[gameTurn].getColorsInHand();
		String[] colors = {"RED","YELLOW","GREEN","BLUE"};
		
		int max = colors_in_hand[0];
		int max_index = 0;
		for(int i = 1; i < colors_in_hand.length; i++) {
			if(colors_in_hand[i] > max) {
				max = colors_in_hand[i];
				max_index = i;
			}
		}
		
		getPlayedCard().setColor(colors[max_index]);
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
	
	public void playCard(Player p) {
		
		//card in the middle
		String lastPlayedCardColor = getPlayedCard().getColor();
		int lastPlayedCardValue = getPlayedCard().getValue();
		
		//store the number of possible next moves for each card
		int[] possibleNextMoves = new int[p.getHand().size()];
		
		//store the number of remaining hand points
		int[] remainingHandPoints = new int[p.getHand().size()];
		
		//add next players hand situation
		
		//compute possible moves and hand points for each card
		for(int i = 0; i < p.getHand().size(); i++) {
			
			UNO_Card card = p.getHand().get(i);
			
			if(card.getColor().equals("BLACK")) {
				int amount = 0;
				for(int j = 0; j < p.getHand().size(); j++) {
					if(p.getHand().get(j).getColor().equals("BLACK")) {
						amount++;
					}
				}
				possibleNextMoves[i] = amount;
			}
			else if(!card.getColor().equals(lastPlayedCardColor) && card.getValue() != lastPlayedCardValue) {
				possibleNextMoves[i] = 0;
			}
			else{
				int amount = 0;
				for(int j = 0; j < p.getHand().size(); j++) {
					if(p.getHand().get(j).getColor().equals(card.getColor())) {
						amount++;
					}
				}
				
				possibleNextMoves[i] = amount;
			}
			
			remainingHandPoints[i] = p.getHandPoints() - card.getPoint();
		}
		
		String nm = "";
		String hp = ""; 
		
		for(int i = 0; i < p.getHand().size(); i++) {
			nm = nm + possibleNextMoves[i] + " | ";
			hp = hp + remainingHandPoints[i] + " | ";
		}
		
		System.out.println("Player: " + p.getName());
		System.out.println("Possible Next Moves: " + nm);
		System.out.println("Remaining Hand Points: " + hp);	
		
		ArrayList<Integer> max_nm_locations = new ArrayList<Integer>();
		ArrayList<Integer> remaining_hp = new ArrayList<Integer>();
		
		//find max element
		int max = possibleNextMoves[0];
		for(int i = 1; i < p.getHand().size(); i++) {
			if(possibleNextMoves[i] > max) {
				max = possibleNextMoves[i];
			}
		}
		
		//add locations of max next moves
		for(int i = 0; i < p.getHand().size(); i++) {
			if(max != 0 && possibleNextMoves[i] == max) {
				max_nm_locations.add(i);
				remaining_hp.add(remainingHandPoints[i]);
			}
		}
		
		/******************* decision mechanism **************************
		 *if there is no possible card -> draw card
		 *if there is only 1 possible card -> play it 
		 *if there is more than 1 card -> play the card with biggest point
		 */
		if(max_nm_locations.size() == 0) {
			p.drawCard(remainingCards);
		}
		else if(max_nm_locations.size() == 1) {
			int loc = max_nm_locations.get(0);
			playedCards.push(p.getHand().remove(loc));
		}
		else {
			int min_point = Collections.min(remaining_hp);
			int min_point_index = remaining_hp.indexOf(min_point);
			
			int loc = max_nm_locations.get(min_point_index);
			playedCards.push(p.getHand().remove(loc));
		}
		
		System.out.println(getPlayedCard().getAction());
		
		//perform action
		if(getPlayedCard().getAction().equals("REVERSE")) {
			reverseGameDirection();
		}
		else if(getPlayedCard().getAction().equals("SKIP")) {
			skipPlayer();
		}
		else if(getPlayedCard().getAction().equals("DRAW_2")) {
			drawTwo();
		}
		else if(getPlayedCard().getAction().equals("DRAW_4")) {
			drawFour();
		}
		else if(getPlayedCard().getAction().equals("COLOR_PICKER")) {
			colorPicker();
		}
		else {}
		
		p.setTurn(false);
	}
}
