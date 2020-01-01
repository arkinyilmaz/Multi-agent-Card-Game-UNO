import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Arc;
import javafx.stage.Popup;

public class ColorPicker_Controller {
	public Arc yellow_picker;
	public Arc red_picker;
	public Arc green_picker;
	public Arc blue_picker;
	
	private Game_Engine game;
	private Popup popup;
	
	public ColorPicker_Controller(Game_Engine game, Popup popup) {
		this.game = game;
		this.popup = popup;
		
		System.out.print("hiii");
	}
		
	@FXML
	public void initialize() {
		yellow_picker.addEventHandler(MouseEvent.MOUSE_PRESSED,
		        new EventHandler<MouseEvent>() {
		          public void handle(MouseEvent me) {
		            popup.hide();
		            game.getPlayedCard().setColor("YELLOW");
		          }
		        });
		
		red_picker.addEventHandler(MouseEvent.MOUSE_PRESSED,
		        new EventHandler<MouseEvent>() {
		          public void handle(MouseEvent me) {
		        	popup.hide();
		            game.getPlayedCard().setColor("RED");
		          }
		        });
		
		green_picker.addEventHandler(MouseEvent.MOUSE_PRESSED,
		        new EventHandler<MouseEvent>() {
		          public void handle(MouseEvent me) {
			            popup.hide();
			            game.getPlayedCard().setColor("GREEN");
		          }
		        });
		
		blue_picker.addEventHandler(MouseEvent.MOUSE_PRESSED,
		        new EventHandler<MouseEvent>() {
		          public void handle(MouseEvent me) {
			            popup.hide();
			            game.getPlayedCard().setColor("BLUE");
		          }
		        });
	}
}
