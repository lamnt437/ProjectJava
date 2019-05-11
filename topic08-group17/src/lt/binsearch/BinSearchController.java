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
					textSearch.setText("Next middle = (" + start + " + " + end + " )/2 =" + loc);
					// highlight
					StackPane currentPane = (StackPane) arrayRec.getChildren().get(loc);
					Rectangle rec = (Rectangle) currentPane.getChildren().get(0);
					rec.setFill(Color.GREEN);
					int target = engine.getTarget();
					
					try {
						Thread.sleep(3000);
						int status = ((BinSearchEngine) engine).check();
						
						if (status == 0) {
							// highlight
							int mid = engine.getArray()[engine.getCurrentPosition()];
							textSearch.setText("Comparing " + target + " and " + mid);
							Thread.sleep(3000);
							textSearch.setText(target  + " == " + mid);
							Thread.sleep(3000);
							rec.setFill(Color.BLUE);
							textSearch.setText("Found!");
						}
						else if(status == -1) {
							//if target < current
							int mid = engine.getArray()[engine.getCurrentPosition()];
							textSearch.setText("Comparing " + target + " and " + mid);
							Thread.sleep(3000);
							textSearch.setText(target  + " < " + mid);
							Thread.sleep(3000);
							textSearch.setText("Eliminate right side of array and then check left side of array");
							Thread.sleep(3000);
							for(int i = loc; i <= end; i++) {
								currentPane = (StackPane) arrayRec.getChildren().get(i);
								rec = (Rectangle) currentPane.getChildren().get(0);
								rec.setFill(Color.RED);
							}
						}
						else {
							//if target > current
							int mid = engine.getArray()[engine.getCurrentPosition()];
							textSearch.setText("Comparing " + target + " and " + mid);
							Thread.sleep(3000);
							textSearch.setText(target  + " > " + mid);
							Thread.sleep(3000);
							textSearch.setText("Eliminate left side of array and then check right side of array");
							Thread.sleep(3000);
							for(int i = start; i <= loc; i++) {
								currentPane = (StackPane) arrayRec.getChildren().get(i);
								rec = (Rectangle) currentPane.getChildren().get(0);
								rec.setFill(Color.RED);
							}
						}
						
						if(engine.isFinished()) {
							if (((BinSearchEngine) engine).getStart() > ((BinSearchEngine) engine).getEnd()) {
								textSearch.setText("There is no element left!");
								Thread.sleep(3000);
								textSearch.setText(target + " is not found!");
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
			timer.schedule(task, 0, 3000);
		}
	}
}
