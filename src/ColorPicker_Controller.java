import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Rectangle;
import javafx.stage.Popup;

public class ColorPicker_Controller {
	public Arc yellow_picker;
	public Arc red_picker;
	public Arc green_picker;
	public Arc blue_picker;
	
	private Game_Engine game;
	private Popup popup;
	private Rectangle color_display;
	
	public ColorPicker_Controller(Game_Engine game, Popup popup, Rectangle color_display) {
		this.game = game;
		this.popup = popup;	
		this.color_display = color_display;
	}
		
	@FXML
	public void initialize() {
		yellow_picker.addEventHandler(MouseEvent.MOUSE_PRESSED,
		        new EventHandler<MouseEvent>() {
		          public void handle(MouseEvent me) {
		            popup.hide();
		            game.getPlayedCard().setColor("YELLOW");
		    		color_display.setFill(Color.web("#ffaa00"));
		          }
		        });
		
		red_picker.addEventHandler(MouseEvent.MOUSE_PRESSED,
		        new EventHandler<MouseEvent>() {
		          public void handle(MouseEvent me) {
		        	popup.hide();
		            game.getPlayedCard().setColor("RED");
		    		color_display.setFill(Color.web("#ff5555"));
		          }
		        });
		
		green_picker.addEventHandler(MouseEvent.MOUSE_PRESSED,
		        new EventHandler<MouseEvent>() {
		          public void handle(MouseEvent me) {
		            popup.hide();
		            game.getPlayedCard().setColor("GREEN");
		    		color_display.setFill(Color.web("#53a653"));
		          }
		        });
		
		blue_picker.addEventHandler(MouseEvent.MOUSE_PRESSED,
		        new EventHandler<MouseEvent>() {
		          public void handle(MouseEvent me) {
		            popup.hide();
		            game.getPlayedCard().setColor("BLUE");
		    		color_display.setFill(Color.web("#5555fd"));
		          }
		        });
	}
}
