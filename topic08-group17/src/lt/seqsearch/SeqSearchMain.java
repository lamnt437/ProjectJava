package lt.seqsearch;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SeqSearchMain extends Application {
    @Override
    public void start(Stage stage) {    	
        try {
            // Read file fxml and draw interface
            Parent root = FXMLLoader.load(getClass()
                    .getResource("SeqSearch.fxml"));
 
            
            stage.setTitle("Sequential Searching Algorithm Visualization");
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