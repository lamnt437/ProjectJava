package lt.binsearch;

import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import general.SearchArrayController;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class BinSearchController extends SearchArrayController {
	
	@Override
	public void initArray(int[] array, int size) {
		for (int i = 0; i < size; i++) {
			array[i] = (int)(Math.random() * 50 + 1);
		}
		Arrays.sort(array);
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
						Thread.sleep(2000);
						int status = ((BinSearchEngine) engine).check();
						
						if (status == 0) {
							// highlight
							int mid = engine.getArray()[engine.getCurrentPosition()];
							textSearch.setText("Comparing " + target + " and " + mid);
							Thread.sleep(2000);
							textSearch.setText(target  + " == " + mid);
							Thread.sleep(2000);
							rec.setFill(Color.BLUE);
							textSearch.setText("Found!");
						}
						else if(status == -1) {
							//if target < current
							int mid = engine.getArray()[engine.getCurrentPosition()];
							textSearch.setText("Comparing " + target + " and " + mid);
							Thread.sleep(2000);
							textSearch.setText(target  + " < " + mid);
							Thread.sleep(2000);
							textSearch.setText("Eliminate right side of array and then check left side of array");
							Thread.sleep(2000);
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
							Thread.sleep(2000);
							textSearch.setText(target  + " > " + mid);
							Thread.sleep(2000);
							textSearch.setText("Eliminate left side of array and then check right side of array");
							Thread.sleep(2000);
							for(int i = start; i <= loc; i++) {
								currentPane = (StackPane) arrayRec.getChildren().get(i);
								rec = (Rectangle) currentPane.getChildren().get(0);
								rec.setFill(Color.RED);
							}
						}
						
						if(engine.isFinished()) {
							if (((BinSearchEngine) engine).getStart() > ((BinSearchEngine) engine).getEnd()) {
								textSearch.setText("There is no element left!");
								Thread.sleep(2000);
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
			timer.schedule(task, 0, 2000);
		}
	}
}
