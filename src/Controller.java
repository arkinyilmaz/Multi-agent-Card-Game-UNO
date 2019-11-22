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
	
	public Button add_card_button;
	public ImageView mid_card;
	public Boolean play = true;
	
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

		
		for(int i = 0; i < players.length; i++) {
			updatePlayerIndex = i;
			ArrayList<UNO_Card> p_hand = players[i].getHand();

			for(int j = 0; j < p_hand.size(); j++) {
				
				UNO_Card card = p_hand.get(j);
				ImageView cardView = new ImageView();
				cardView.setFitWidth(100);
				cardView.setFitHeight(140);		
				cardView.setImage(card.getImage());
				//remove card from hand
				cardView.setOnMouseClicked(new EventHandler<MouseEvent>(){

		            @Override
		            public void handle(MouseEvent event) {
		            	System.out.println("Card Type: " + card.getType() + "\nCard Color: " + card.getColor() + "\nCard Value: " + card.getValue() + "\nCard Action: " + card.getAction());
		            	cardView.setImage(null);
		            }
		        });
				
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
					((VBox) playerContainer.get(i)).setSpacing(-85); //-65
					((VBox) playerContainer.get(i)).setAlignment(Pos.CENTER_LEFT);
					cardView.setRotate(90);
					playerContainer.get(i).getChildren().add(cardView);
				}			
				else {
					((VBox) playerContainer.get(i)).setSpacing(-85); //-65
					((VBox) playerContainer.get(i)).setAlignment(Pos.CENTER_RIGHT);
					cardView.setRotate(90);
					playerContainer.get(i).getChildren().add(cardView);
				}
			}
		}
		
		UNO_Card playedCard = game.getPlayedCard();
		mid_card.setImage(playedCard.getImage());
		System.out.println(mid_card.getImage().getRequestedWidth());
		centerImage();
	}
	
	public void play() {
	
		if(!game.isGameEnded()) {
			int turn = game.getGameTurn();
			int direction = game.getGameDirection();
			
			game.playCard(players[turn]);		
			game.setGameTurn((game.getGameTurn() + direction + 4) % players.length);
		}	
	}
	
	public void centerImage() {
		Image image = game.getPlayedCard().getImage();
		
        if (image != null) {
            double w = 0;
            double h = 0;

            double ratioX = mid_card.getFitWidth() / image.getWidth();
            double ratioY = mid_card.getFitHeight() / image.getHeight();

            double reducCoeff = 0;
            if(ratioX >= ratioY) {
                reducCoeff = ratioY;
            } else {
                reducCoeff = ratioX;
            }

            w = image.getWidth() * reducCoeff;
            h = image.getHeight() * reducCoeff;

            mid_card.setX((mid_card.getFitWidth() - w) / 2);
            mid_card.setY((mid_card.getFitHeight() - h) / 2);

        }
    }

	@FXML
	public void initialize() throws FileNotFoundException {
		//Two threads are used to update view, but look for it 
		playerContainer.add(hbox_down);
		playerContainer.add(vbox_right);
		playerContainer.add(hbox_up);
		playerContainer.add(vbox_left);

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
