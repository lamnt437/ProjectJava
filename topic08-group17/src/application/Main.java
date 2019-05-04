package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	@Override
	public void start(Stage stage) {
		try {
			// Read file fxml and draw interface
            Parent root = FXMLLoader.load(getClass()
                    .getResource("Menu.fxml"));
 
            
            stage.setTitle("Java Project - Group 17");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
