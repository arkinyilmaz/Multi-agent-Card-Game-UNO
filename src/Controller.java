import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;

public class Controller{
	
	//players hands
	public HBox hbox_down;
	public HBox hbox_up;
	public VBox vbox_left;
	public VBox vbox_right;
	public ArrayList<Pane> playerContainer;
	public ImageView draw_card;
	public Rectangle color_display;
	public ImageView game_direction;
	public ImageView game_turn;
	public Label card_left_label;
	
	public Button add_card_button;
	public ImageView mid_card;
	public Boolean play = true;
	public Image back_image;
	
	private Stage primaryStage;
	
	public int updatePlayerIndex = -1;
	Player p1, p2, p3, p4;
	Player[] players;
	Game_Engine game;
	Popup popup;
		
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
	}
	
	public void updateView() {
			
		hbox_down.getChildren().clear();
		hbox_up.getChildren().clear();
		vbox_left.getChildren().clear();
		vbox_right.getChildren().clear();
		
		//update game turn indicator
		if(game.getGameTurn() == 0) {
			game_turn.setLayoutX(364);
			game_turn.setLayoutY(219);
			game_turn.setRotate(0);
		}
		else if(game.getGameTurn() == 1) {
			game_turn.setLayoutX(434);
			game_turn.setLayoutY(131);
			game_turn.setRotate(270);
		}
		else if(game.getGameTurn() == 2) {
			game_turn.setLayoutX(364);
			game_turn.setLayoutY(32);
			game_turn.setRotate(180);
		}
		else {
			game_turn.setLayoutX(295);
			game_turn.setLayoutY(131);
			game_turn.setRotate(90);
		}
		
		card_left_label.setText("Card Left: " + game.getRemainingCards().size());
		if(game.getRemainingCards().size() == 0)
			game.reCreateDeck();
		
		for(int i = 0; i < players.length; i++) {
			updatePlayerIndex = i;
			ArrayList<UNO_Card> p_hand = players[i].getHand();

			for(int j = 0; j < p_hand.size(); j++) {
				int index = j;
				UNO_Card card = p_hand.get(j);
				ImageView cardView = new ImageView();
				cardView.setFitWidth(85);
				cardView.setFitHeight(125);		
				cardView.setImage(card.getImage());
				
				//remove card from hand
				if(game.getGameTurn() == 0) {					
					cardView.setOnMouseClicked(new EventHandler<MouseEvent>(){
			            @Override
			            public void handle(MouseEvent event) {
			            	
			            	UNO_Card middle_card = game.getPlayedCard();
			            	System.out.println("\n\nCard Type: " + middle_card.getType() + "\nCard Color: " + middle_card.getColor());
			            	
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
				            	
				            	popup = null;
				            	
				            	//if it is wild card
				            	if(game.getPlayedCard().getType() == 3) {
				            		try {
				            			popup = new Popup();
				            			ColorPicker_Controller controller = new ColorPicker_Controller(game,popup,color_display);
				            			FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("color_picker.fxml"));
				            			fxmlloader.setController(controller);
				            			
				            			popup.getContent().add((Parent)fxmlloader.load());
				            		} 
				            		catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
				            				
				            		popup.setX(600);
				            		popup.setY(300);
				            		popup.show(primaryStage);	
				            	}
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
				if(i == 0) {
					((HBox) playerContainer.get(i)).setSpacing(-50); //-30
					((HBox) playerContainer.get(i)).setAlignment(Pos.TOP_CENTER);
					playerContainer.get(i).getChildren().add(cardView);
				}
				else if(i == 1){
					((VBox) playerContainer.get(i)).setSpacing(-90); //-65
					((VBox) playerContainer.get(i)).setAlignment(Pos.CENTER_LEFT);
					cardView.setRotate(90);
					playerContainer.get(i).getChildren().add(cardView);
				}			
				else if(i == 3){
					((VBox) playerContainer.get(i)).setSpacing(-90); //-65
					((VBox) playerContainer.get(i)).setAlignment(Pos.CENTER_RIGHT);
					cardView.setRotate(90);
					playerContainer.get(i).getChildren().add(cardView);
				}
				else {
					((HBox) playerContainer.get(i)).setSpacing(-50); //-30
					((HBox) playerContainer.get(i)).setAlignment(Pos.BOTTOM_CENTER);
					playerContainer.get(i).getChildren().add(cardView);
				}
			}
		}
		
		if(game.getGameTurn() == 0) {
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
					System.out.print(k + " is OKEY | ");
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
		            	if(game.getGameTurn() == 0) {
			            	players[0].drawCard(game.getRemainingCards());
			            	players[game.getGameTurn()].setTurn(false);
			    			
			    			game.setGameTurn((game.getGameTurn() + game.getGameDirection() + 4) % players.length);
			    			players[game.getGameTurn()].setTurn(true);
	
			    			System.out.println("\nPlayer 1:" + players[0].isTurn() + "\nPlayer 2:" + players[1].isTurn() + "\nPlayer 3:" + players[2].isTurn() + "\nPlayer 4:" + players[3].isTurn());
			    			updateView();
		            	}
		            }
		        });			
			}
		}
		
		UNO_Card playedCard = game.getPlayedCard();
		mid_card.setImage(playedCard.getImage());
		centerImage(mid_card);
		
		
		String hex = "";
		switch(playedCard.getColor()) {
			case "RED":
				hex = "#ff5555";
				break;
			case "YELLOW":
				hex = "#ffaa00";
				break;
			case "GREEN":
				hex = "#53a653";
				break;
			case "BLUE":
				hex = "#5555fd";
				break;	
		}
		color_display.setFill(Color.web(hex));
		
		//game direction
		if(game.getGameDirection() == 1)
			game_direction.setScaleY(1);
		else
			game_direction.setScaleY(-1);	
		
		if(game.isGameEnded()){
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirmation Dialog");
			alert.setHeaderText(game.getWinner().getName() + " wins the game!");
			alert.setContentText("Do you want to play again?");
			
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK){
			    // ... user chose OK
				game = new Game_Engine(players);
				game.start();
				updateView();
			} else {
				Platform.exit();
			    // ... user chose CANCEL or closed the dialog
			}
		}
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
				updateView();
		}
	}
	
	//play function for real player - add card check before playing
	public void playOnClick(int index) {
		if(!game.isGameEnded() && !players[game.getGameTurn()].isBot()) {
			int direction = game.getGameDirection();
			
			game.playCard(index);
			
			game.setGameTurn((game.getGameTurn() + direction + 4) % players.length);
			players[game.getGameTurn()].setTurn(true);

			System.out.println("\nPlayer 1:" + players[0].isTurn() + "\nPlayer 2:" + players[1].isTurn() + "\nPlayer 3:" + players[2].isTurn() + "\nPlayer 4:" + players[3].isTurn());
			updateView();
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
		
		hbox_up.setDisable(true);
		vbox_left.setDisable(true);	
		vbox_right.setDisable(true);
		
		String filename = "images/deck.png";
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
		//play();
		
		/*new Thread(() -> {
			while(!game.isGameEnded()) {				
				Platform.runLater(() -> {
									
				});				
			}
		}).start();*/
	}
	
	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}
}
