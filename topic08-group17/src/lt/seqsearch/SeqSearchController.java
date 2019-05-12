package lt.seqsearch;

import java.util.Timer;
import java.util.TimerTask;

import general.SearchArrayController;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class SeqSearchController extends SearchArrayController {
	
	public void startSearch() {
		
		if (input.getText() == null || input.getText().trim().isEmpty()) {
			textSearch.setText("You must enter input first!");
		} else if (flag == 0) {
			textSearch.setText("You must enter initialize first!");
		} else {
			target = Integer.parseInt(input.getText());
			engine = new SeqSearchEngine(array, target);
	    	Timer timer = new Timer();
			TimerTask task = new TimerTask() {
				@Override
				public void run() {
					int loc = ((SeqSearchEngine) engine).getCurrentPosition();
					try {
						textSearch.setText("Traversing the array.");
						Thread.sleep(1000);
						int target = engine.getTarget();
						Thread.sleep(1000);
						// highlight
						StackPane currentPane = (StackPane) arrayRec.getChildren().get(loc);
						Rectangle rec = (Rectangle) currentPane.getChildren().get(0);
						boolean status = ((SeqSearchEngine) engine).check();
						rec.setFill(Color.GREEN);
						Thread.sleep(1000);
						if (status == true) {
							// highlight
							int cur = engine.getArray()[engine.getCurrentPosition()];
							textSearch.setText("Checking if " + target + " = " + cur);
							Thread.sleep(1000);
							textSearch.setText(target + "==" + cur);
							Thread.sleep(1000);
							rec.setFill(Color.BLUE);
							textSearch.setText("Found!");
						} else {
							int cur = engine.getArray()[engine.getCurrentPosition()];
							textSearch.setText("Checking if " + target + " = " + cur);
							Thread.sleep(1000);
							textSearch.setText(target + "!=" + cur);
							Thread.sleep(1000);
							rec.setFill(Color.RED);
							Thread.sleep(1000);
							textSearch.setText("Go to the next element.");
						}
						
						if(engine.isFinished())
							cancel();
						else {
							if (engine.getCurrentPosition() == (size - 1)) {
								textSearch.setText("There is no element left!");
								Thread.sleep(1000);
								textSearch.setText(target + " is not found!");
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
