import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

public class Controller{
	
	public HBox card_slot_hbox;
	public Button add_card_button;
	public ImageView mid_card;
	
	public int i = 0;
	Player p1, p2, p3, p4;
	Player[] players;
	Game_Engine game;
	
	public Controller() {
		p1 = new Player("Player 1", false);
		p2 = new Player("Player 2", true);
		p3 = new Player("Player 3", true);
		p4 = new Player("Player 4", true);
		
		players = new Player[4];
		players[0] = p1;
		players[1] = p2;
		players[2] = p3;
		players[3] = p4;
				
		game = new Game_Engine(players);
		game.start();	
	}
	
	public void handleAddCardButtonClick() throws FileNotFoundException {
		
		ArrayList<UNO_Card> p_hand = p1.getHand();
		
		String fileName = "";
		
		for(int i = 0; i < p_hand.size(); i++) {
			
			UNO_Card c = p_hand.get(i);
			int card_type = c.getType();
			String card_color = c.getColor();
			int card_value = c.getValue();
			String action = c.getAction();
			
			ImageView card = new ImageView();
			card.setId("blue_" + i);
			card.setImage(c.getImage());
			
			//remove card from hand
			card.setOnMouseClicked(new EventHandler<MouseEvent>(){

	            @Override
	            public void handle(MouseEvent event) {
	            	System.out.println("Card Type: " + card_type + "\nCard Color: " + card_color + "\nCard Value: " + card_value + "\nCard Action: " + action);
	            	card.setImage(null);
	            }
	        });
			
			//add cards starting from the middle
			card_slot_hbox.setSpacing(5);
			card_slot_hbox.setAlignment(Pos.BASELINE_CENTER);
			card_slot_hbox.getChildren().add(card);
			
		}
	}

	@FXML
	public void initialize() throws FileNotFoundException {
		//Two threads are used to update view, but look for it 
		new Thread(() -> {
			while(!game.isGameEnded()) {
				
				try {
					Thread.sleep(250);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				int turn = game.getGameTurn();
				int direction = game.getGameDirection();
				
				game.playCard(players[turn]);		
				game.setGameTurn((game.getGameTurn() + direction + 4) % players.length);
				
				Platform.runLater(() -> {
					UNO_Card c = game.getPlayedCard();
					int card_type = c.getType();
					String card_color = c.getColor();
					int card_value = c.getValue();
					String action = c.getAction();
					String fileName = "";
					
					if(card_type == 1) {
						fileName = "images/" + card_color.toLowerCase() + "_" + card_value + ".png";
					}
					else if(card_type == 2){
						fileName = "images/" + card_color.toLowerCase() + "_" + action.toLowerCase(Locale.ENGLISH) + ".png";
					}
					else {
						fileName = "images/black_" + action.toLowerCase(Locale.ENGLISH) + ".png";
					}
					
					FileInputStream input = null;
					try {
						input = new FileInputStream(fileName);
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		
					Image image = new Image(input);
					mid_card.setImage(image);
					
				});	
			}
		}).start();
	}
}
