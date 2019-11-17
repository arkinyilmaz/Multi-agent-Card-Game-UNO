import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application{
	
	public static void main(String[] args) {
		
		launch(args);
				
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		
		Parent root = FXMLLoader.load(getClass().getResource("uno_game.fxml"));
		primaryStage.setTitle("xXxUNOMASTERSxXx");
		primaryStage.setScene(new Scene(root));
		primaryStage.show();		
	}
}
