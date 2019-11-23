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
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class Controller{
	
	//players hands
	public HBox hbox_down;
	public HBox hbox_up;
	public VBox vbox_left;
	public VBox vbox_right;
	public ArrayList<Pane> playerContainer;
	public ImageView draw_card;
	
	public Button add_card_button;
	public ImageView mid_card;
	public Boolean play = true;
	public Image back_image;
	
	public int updatePlayerIndex = -1;
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
				
		playerContainer = new ArrayList<>();

		game = new Game_Engine(players);
		game.start();	
		System.out.println("\nPlayer to start: " + game.getGameTurn());
	}
	
	public void handleAddCardButtonClick() throws FileNotFoundException {
		
		play();
		updateView();
		
	}
	
	public void updateView() {
		hbox_down.getChildren().clear();
		hbox_up.getChildren().clear();
		vbox_left.getChildren().clear();
		vbox_right.getChildren().clear();
		
		/*ArrayList<UNO_Card> remaining_deck = game.getRemainingCards();
		for(int i = 0; i < remaining_deck.size(); i++) {
			ImageView view = new ImageView(back_image);
			vbox_deck.getChildren().add(view);
		}
		vbox_deck.setSpacing(-109.5);*/
		
		for(int i = 0; i < players.length; i++) {
			updatePlayerIndex = i;
			ArrayList<UNO_Card> p_hand = players[i].getHand();

			for(int j = 0; j < p_hand.size(); j++) {
				int index = j;
				UNO_Card card = p_hand.get(j);
				ImageView cardView = new ImageView();
				cardView.setFitWidth(100);
				cardView.setFitHeight(140);		
				cardView.setImage(card.getImage());
				
				//remove card from hand
				if(game.getGameTurn() == 0) {					
					cardView.setOnMouseClicked(new EventHandler<MouseEvent>(){
			            @Override
			            public void handle(MouseEvent event) {
			            	
			            	UNO_Card middle_card = game.getPlayedCard();
			            	System.out.println("\nCard Type: " + middle_card.getType() + "\nCard Color: " + middle_card.getColor());

			            	ArrayList<UNO_Card> rp_hand = players[0].getHand();
			            	
			            	Boolean cond1 = (!middle_card.getColor().equals(card.getColor()) && middle_card.getValue() == card.getValue());
			            	Boolean cond2 = (middle_card.getColor().equals(card.getColor()) && middle_card.getValue() != card.getValue());
			            	Boolean cond3 = (!middle_card.getColor().equals(card.getColor()) && middle_card.getValue() != card.getValue() &&  card.getColor().equals("BLACK"));
			            	Boolean cond4 = (middle_card.getColor().equals(card.getColor()) && middle_card.getValue() == card.getValue());

			            	
			            	if(cond1){
			            		System.out.println("COND1 OK!!");
			            	}
			            	if(cond2) {
			            		System.out.println("COND2 OK!!");
			            	}
			            	if(cond3) {
			            		System.out.println("COND3 OK!!");
			            	}
			            	
			            	if(cond1 || cond2 || cond3 || cond4) {
			            		cardView.setImage(null);
				            	hbox_down.getChildren().remove(cardView);
				            	playOnClick(index);
				            	System.out.println("\nIndex: " + index + "\nCard Type: " + card.getType() + "\nCard Color: " + card.getColor() + "\nCard Value: " + card.getValue() + "\nCard Action: " + card.getAction());
				            	updateView();
			            	}
			            	else {     	
			            		System.out.println("YOU CANNOT PLAY THIS CARD !!");
			            	}
			            }
			        });				
				}
				
				//animation - move selected card to up
				for(Node child: hbox_down.getChildren()) {
					ImageView imageView = (ImageView) child;				
					imageView.setOnMouseEntered(new EventHandler<MouseEvent>() {
						@Override
						public void handle(MouseEvent arg0) {
							imageView.setTranslateY(-30);
						}
					});
				}
				
				//animation - move selected card to down
				for(Node child: hbox_down.getChildren()) {
					ImageView imageView = (ImageView) child;				
					imageView.setOnMouseExited(new EventHandler<MouseEvent>() {
						@Override
						public void handle(MouseEvent arg0) {
							imageView.setTranslateY(0);
						}
					});
				}
				
				//add cards starting from the middle
				if(i % 2 == 0) {
					((HBox) playerContainer.get(i)).setSpacing(-50); //-30
					((HBox) playerContainer.get(i)).setAlignment(Pos.CENTER);
					playerContainer.get(i).getChildren().add(cardView);
				}
				else if(i == 1){
					((VBox) playerContainer.get(i)).setSpacing(-90); //-65
					((VBox) playerContainer.get(i)).setAlignment(Pos.CENTER_LEFT);
					cardView.setRotate(90);
					playerContainer.get(i).getChildren().add(cardView);
				}			
				else {
					((VBox) playerContainer.get(i)).setSpacing(-90); //-65
					((VBox) playerContainer.get(i)).setAlignment(Pos.CENTER_RIGHT);
					cardView.setRotate(90);
					playerContainer.get(i).getChildren().add(cardView);
				}
			}
		}
		
		Boolean[] check = new Boolean[players[0].getHand().size()];
		Boolean check2 = true;
		
		//if there is no available card, draw card from deck
		for(int k = 0; k < check.length; k++) {
			UNO_Card c = players[0].getHand().get(k);
        	UNO_Card middle_card = game.getPlayedCard();

			Boolean cond1 = (!middle_card.getColor().equals(c.getColor()) && middle_card.getValue() == c.getValue());
        	Boolean cond2 = (middle_card.getColor().equals(c.getColor()) && middle_card.getValue() != c.getValue());
        	Boolean cond3 = (!middle_card.getColor().equals(c.getColor()) && middle_card.getValue() != c.getValue() &&  c.getColor().equals("BLACK"));
        	Boolean cond4 = (middle_card.getColor().equals(c.getColor()) && middle_card.getValue() == c.getValue());
        		
			if(cond1 || cond2 || cond3 || cond4) 
				check[k] = true;
			else
				check[k] = false;
		}
		
		for(int k = 0; k < check.length; k++) {
			if(check[k])
				System.out.println(k + " is OKEY | ");
		}
		
		for(int k = 0; k < check.length; k++) {
			if(check[k]) {
				check2 = false;
				break;
			}
		}
					
		//check for all possible cards, create boolean array, if any dont add, otherwise add
		if(check2) {
			draw_card.setOnMouseClicked(new EventHandler<MouseEvent>(){
	            @Override
	            public void handle(MouseEvent event) {
	            	players[0].drawCard(game.getRemainingCards());
	            	players[game.getGameTurn()].setTurn(false);
	    			
	    			game.setGameTurn((game.getGameTurn() + game.getGameDirection() + 4) % players.length);
	    			players[game.getGameTurn()].setTurn(true);

	    			System.out.println("\nPlayer 1:" + players[0].isTurn() + "\nPlayer 2:" + players[1].isTurn() + "\nPlayer 3:" + players[2].isTurn() + "\nPlayer 4:" + players[3].isTurn());
	            	updateView();
	            }
	        });		
		}
		
		UNO_Card playedCard = game.getPlayedCard();
		mid_card.setImage(playedCard.getImage());
		System.out.println(mid_card.getImage().getRequestedWidth());
		centerImage(mid_card);
	}
	
	//play function for bots
	public void play() {
	
		if(!game.isGameEnded() && players[game.getGameTurn()].isBot()) {
			int turn = game.getGameTurn();
			int direction = game.getGameDirection();
			
			game.playCard(players[turn]);		
			game.setGameTurn((game.getGameTurn() + direction + 4) % players.length);
			players[game.getGameTurn()].setTurn(true);

			System.out.println("\nPlayer 1:" + players[0].isTurn() + "\nPlayer 2:" + players[1].isTurn() + "\nPlayer 3:" + players[2].isTurn() + "\nPlayer 4:" + players[3].isTurn());
			
		}	
	}
	
	//play function for real player - add card check before playing
	public void playOnClick(int index) {
		if(!game.isGameEnded()) {
			String text = "";
			Boolean isPlayed = false;
			int turn = game.getGameTurn();
			int direction = game.getGameDirection();
						
			game.getPlayedCards().push(players[turn].getHand().remove(index));
			isPlayed = true;
			
			if(game.getPlayedCard().getType() == 1)
				text = "Played: " + game.getPlayedCard().getColor()  + "_" + game.getPlayedCard().getValue();
			else {
				text = "Played: " + game.getPlayedCard().getColor()  + "_" + game.getPlayedCard().getAction();
			}
			
			System.out.println(text);
			
			//perform last played cards action
			if(isPlayed) {
				if(game.getPlayedCard().getAction().equals("REVERSE")) {
					game.reverseGameDirection();
				}
				else if(game.getPlayedCard().getAction().equals("SKIP")) {
					game.skipPlayer();
				}
				else if(game.getPlayedCard().getAction().equals("DRAW_2")) {
					game.drawTwo();
				}
				else if(game.getPlayedCard().getAction().equals("DRAW_4")) {
					game.drawFour();
				}
				else if(game.getPlayedCard().getAction().equals("COLOR_PICKER")) {
					game.colorPicker();
				}
				else {}
			}
			
			players[turn].setTurn(false);
			
			game.setGameTurn((game.getGameTurn() + direction + 4) % players.length);
			players[game.getGameTurn()].setTurn(true);

			System.out.println("\nPlayer 1:" + players[0].isTurn() + "\nPlayer 2:" + players[1].isTurn() + "\nPlayer 3:" + players[2].isTurn() + "\nPlayer 4:" + players[3].isTurn());
			
		}	
	}
	
	public void centerImage(ImageView view) {
		Image image = game.getPlayedCard().getImage();
		
        if (image != null) {
            double w = 0;
            double h = 0;

            double ratioX = view.getFitWidth() / image.getWidth();
            double ratioY = view.getFitHeight() / image.getHeight();

            double reducCoeff = 0;
            if(ratioX >= ratioY) {
                reducCoeff = ratioY;
            } else {
                reducCoeff = ratioX;
            }

            w = image.getWidth() * reducCoeff;
            h = image.getHeight() * reducCoeff;

            view.setX((view.getFitWidth() - w) / 2);
            view.setY((view.getFitHeight() - h) / 2);

        }
    }

	@FXML
	public void initialize() throws FileNotFoundException {
		//Two threads are used to update view, but look for it 
		playerContainer.add(hbox_down);
		playerContainer.add(vbox_right);
		playerContainer.add(hbox_up);
		playerContainer.add(vbox_left);
		
		String filename = "images/card_back.png";
		FileInputStream input = null;
		try {
			input = new FileInputStream(filename);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		back_image = new Image(input);
		draw_card.setImage(back_image);
		centerImage(draw_card);
		
		updateView();
		
		
		
		/*new Thread(() -> {
			while(!game.isGameEnded() && play) {
						
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
					
					play = false;
					
				});	
			}
		}).start();*/
	}
}
