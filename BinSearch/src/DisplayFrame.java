import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class DisplayFrame extends Application {
	List<StackPane> rectangles = new ArrayList<StackPane>();
//	SearchEngine engine;
	HBox controlBlock = new HBox();
	int[] array = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14};
	int size = array.length;
	int target = 10;
//	int targetIndex = 1;
//	int target = array[targetIndex];
	SearchEngine engine = new SearchEngine(array, target);
	
	public void resetColor() {
		int loc = engine.getCurrentPosition();
		int start = engine.getStart();
		int end = engine.getEnd();
		// highlight
		StackPane currentPane = (StackPane) controlBlock.getChildren().get(loc);
		Rectangle rec = (Rectangle) currentPane.getChildren().get(0);
		rec.setFill(Color.GREEN);
		for (int i = 0; i < controlBlock.getChildren().size(); i++) {
			
		}
	}
	
	public void doSearch() {
		int loc = engine.getCurrentPosition();
		int start = engine.getStart();
		int end = engine.getEnd();
		// highlight
		StackPane currentPane = (StackPane) controlBlock.getChildren().get(loc);
		Rectangle rec = (Rectangle) currentPane.getChildren().get(0);
		rec.setFill(Color.GREEN);
		
		try {
			Thread.sleep(1000);
			
			int status = engine.check();
			
			if (status == 0) {
				// highlight
				rec.setFill(Color.BLUE);
			}
			else if(status == -1) {
				for(int i = loc; i <= end; i++) {
					currentPane = (StackPane) controlBlock.getChildren().get(i);
					currentPane.setVisible(false);
//					rec = (Rectangle) currentPane.getChildren().get(0);
//					rec.setFill(Color.RED);
//					rec.setVisible(false);
				}
			}
			else {
				for(int i = start; i <= loc; i++) {
					currentPane = (StackPane) controlBlock.getChildren().get(i);
					currentPane.setVisible(false);
//					rec = (Rectangle) currentPane.getChildren().get(0);
//					rec.setFill(Color.RED);
//					rec.setVisible(false);
				}
			}
			
			if(engine.isFinished())
				return;
			engine.goNext();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void init() throws Exception {
		
		
//		int target = 100;
		

		for (int i = 0; i < size; i++) {
			StackPane tempPane = new StackPane();
			Text tempText = new Text("" + array[i]);
			Rectangle temp = new Rectangle();
			temp.setWidth(20);
			temp.setHeight(20);
			temp.setFill(Color.BISQUE);
//			if (i == targetIndex)
//				temp.setFill(Color.YELLOW);
			tempPane.getChildren().addAll(temp, tempText);
			rectangles.add(tempPane);
		}
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		controlBlock.setPadding(new Insets(5, 10, 5, 20));
		controlBlock.setSpacing(5);

		for (StackPane rec : rectangles) {
			controlBlock.getChildren().add(rec);
		}

		Scene scene = new Scene(controlBlock, 500, 250);

		primaryStage.setTitle("Hello");
		primaryStage.setScene(scene);
		primaryStage.show();
		
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				doSearch();
			}
		};
		
		
		timer.schedule(task, 0, 3000);
		//set button
				Button buttonReset = new Button("Reset");
				buttonReset.setOnAction(new EventHandler<ActionEvent>() {
		            @Override
		            public void handle(ActionEvent event) {
		            	for (int i = 0; i < controlBlock.getChildren().size(); i++) {
			            	StackPane currentPane = (StackPane) controlBlock.getChildren().get(i);
			            	currentPane.setVisible(true);
		            	}
		            	
		            	try {
							init();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		            }
		        });
				Button buttonStart = new Button("Start");
				buttonStart.setOnAction(new EventHandler<ActionEvent>() {
		            @Override
		            public void handle(ActionEvent event) {

		            }
		        });
				controlBlock.getChildren().addAll(buttonReset,buttonStart);

	}
	
	

	public static void main(String[] args) {
		launch(args);
	}
}
