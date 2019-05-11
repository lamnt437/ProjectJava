package lt.binsearch;

import general.SearchArrayController;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import javafx.geometry.Insets;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class BinSearchController extends SearchArrayController {
	
	public void createRectangles(int size) {
		for (int i = 0; i < size; i++) {
			int tempNumber = (int)(Math.random() * 50 + 1);
			array[i] = tempNumber;
		}
		Arrays.sort(array);
		for (int i = 0; i < size; i++) {
			StackPane tempPane = new StackPane();
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
	    	System.out.println(target);
	    	engine = new BinSearchEngine(array, target);
	    	Timer timer = new Timer();
			TimerTask task = new TimerTask() {
				@Override
				public void run() {
					int loc = ((BinSearchEngine) engine).getCurrentPosition();
					int start = ((BinSearchEngine) engine).getStart();
					int end = ((BinSearchEngine) engine).getEnd();
					// highlight
					StackPane currentPane = (StackPane) arrayRec.getChildren().get(loc);
					Rectangle rec = (Rectangle) currentPane.getChildren().get(0);
					rec.setFill(Color.GREEN);
					
					try {
						Thread.sleep(1000);
						
						int status = ((BinSearchEngine) engine).check();
						
						if (status == 0) {
							// highlight
							rec.setFill(Color.BLUE);
						}
						else if(status == -1) {
							//if target < current
							String noti = engine.getTarget() + "<=" + engine.getArray()[engine.getCurrentPosition()];
							textSearch.setText(noti);
							Thread.sleep(1000);
							for(int i = loc; i <= end; i++) {
								currentPane = (StackPane) arrayRec.getChildren().get(i);
								currentPane.setVisible(false);
							}
						}
						else {
							//if target > current
							String noti = engine.getTarget() + ">=" + engine.getArray()[engine.getCurrentPosition()];
							textSearch.setText(noti);
							Thread.sleep(1000);
							for(int i = start; i <= loc; i++) {
								currentPane = (StackPane) arrayRec.getChildren().get(i);
								currentPane.setVisible(false);
							}
						}
						
						if(engine.isFinished()) {
							if (((BinSearchEngine) engine).getStart()> ((BinSearchEngine) engine).getEnd()) {
								textSearch.setText("Not Found!");
							} else {
								
							}
							cancel();
						} else {
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
