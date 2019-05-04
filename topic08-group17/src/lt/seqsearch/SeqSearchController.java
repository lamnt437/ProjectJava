package lt.seqsearch;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class SeqSearchController {
	@FXML
	private GridPane gridpane;
	
	@FXML
	private Button btnStart;
	
	@FXML
	private Button btnStop;
	
	@FXML
	private Button btnReset;
	
	@FXML
	private TextField input;
	
	@FXML
	private HBox arrayRec;
	
	@FXML
	private TextField textSearch;
	
	@FXML
	private Label labelSearch;
	
	int flag = 0;
	int size = 10;
	List<StackPane> rectangles = new ArrayList<StackPane>();
	SearchEngine engine;
	int[] array = new int[size];
	int target = 1;
	
	public void createRectangles(int size) {
		for (int i = 0; i < size; i++) {
			StackPane tempPane = new StackPane();
			int tempNumber = (int)(Math.random() * 50 + 1);
			array[i] = tempNumber;
			Text tempText = new Text("" + array[i]);
			Rectangle temp = new Rectangle();
			temp.setWidth(47);
			temp.setHeight(47);
			temp.setFill(Color.BISQUE);
			tempPane.getChildren().addAll(temp, tempText);
			rectangles.add(tempPane);
		}
		arrayRec.setPadding(new Insets(5, 10, 5, 20));
		arrayRec.setSpacing(15);
		for (StackPane rec : rectangles) {
			arrayRec.getChildren().add(rec);
		}
	}
	
	public void initSearch() {
		if (flag == 0) {
			this.createRectangles(size);
			flag = 1;
		} else {
			arrayRec.getChildren().clear();
			rectangles.clear();
			this.createRectangles(size);
		}
	}
	
	public void startSearch() {
		
		if (input.getText() == null || input.getText().trim().isEmpty()) {
			textSearch.setText("You must enter input first!");
		} else if (flag == 0) {
			textSearch.setText("You must enter initialize first!");
		} else {
			target = Integer.parseInt(input.getText());
			engine = new SearchEngine(array, target);
	    	Timer timer = new Timer();
			TimerTask task = new TimerTask() {
				@Override
				public void run() {
					int loc = engine.getCurrentPosition();
					try {
						Thread.sleep(1000);
						// highlight
						StackPane currentPane = (StackPane) arrayRec.getChildren().get(loc);
						Rectangle rec = (Rectangle) currentPane.getChildren().get(0);
						boolean status = engine.check();
						rec.setFill(Color.GREEN);
						Thread.sleep(1000);
						if (status == true) {
							// highlight
							rec.setFill(Color.BLUE);
							String noti = engine.getTarget() + "=" + engine.getArray()[engine.getCurrentPosition()];
							textSearch.setText(noti);
							Thread.sleep(1000);
							textSearch.setText("Found!");
						} else {
							String noti = engine.getTarget() + "!=" + engine.getArray()[engine.getCurrentPosition()];
							textSearch.setText(noti);
							Thread.sleep(1000);
							currentPane.setVisible(false);
						}
						
						if(engine.isFinished())
							cancel();
						else {
							if (engine.getCurrentPosition() == (size - 1)) {
								textSearch.setText("Not Found!");
								cancel();
							}
							engine.goNext();
						}
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			};
			timer.schedule(task, 0, 1000);
		}   	
	}
}
