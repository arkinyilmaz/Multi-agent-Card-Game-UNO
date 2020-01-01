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
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("uno_game.fxml"));
		Parent root = (Parent)fxmlLoader.load();
		
	    ((Controller) fxmlLoader.getController()).setPrimaryStage(primaryStage);

		Scene scene = new Scene(root);
		primaryStage.setTitle("xXxUNOMASTERSxXx");
		primaryStage.setScene(scene);
		primaryStage.show();		
	}
}
