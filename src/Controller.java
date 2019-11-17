import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Locale;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

public class Controller {
	
	public HBox card_slot_hbox;
	public Button add_card_button;
	public int i = 0;
	Player p1, p2, p3, p4;
	
	public Controller() {
		p1 = new Player("Arko", false);
		p2 = new Player("Zey", true);
		p3 = new Player("Akkorus", true);
		p4 = new Player("Ali", true);
		
		Player[] players = { p1, p2, p3, p4};
		
		Game_Engine game = new Game_Engine(players);
		game.start();
		
		//print players' hands
		for(int i = 0 ; i < players.length; i++) {
			printPlayerHand(players[i]);
		}
	}
	
	public void printPlayerHand(Player p) {
		System.out.println("\nPLAYER: " + p.getName());
		ArrayList<UNO_Card> p_hand = p.getHand();
		for(int i = 0; i < p_hand.size(); i++) {
			System.out.println("Card Color: " + p_hand.get(i).getColor() + " | Card Value: " + p_hand.get(i).getValue() 
								+ " | Card Type: " + p_hand.get(i).getType() + "| Card Action: " + p_hand.get(i).getAction());
		}
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
			
			if(card_type == 1) {
				fileName = "images/" + card_color.toLowerCase() + "_" + card_value + ".png";
			}
			else {
				fileName = "images/" + card_color.toLowerCase() + "_" + action.toLowerCase(Locale.ENGLISH) + ".png";
			}
			
			System.out.println("Filename is: " + fileName);
			FileInputStream input = new FileInputStream(fileName);
			Image image = new Image(input);
			ImageView card = new ImageView();
			card.setId("blue_" + i);
			card.setImage(image);
			
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
}
