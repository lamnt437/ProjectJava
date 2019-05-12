package general;

import java.util.ArrayList;
import java.util.List;

import general.Controller;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class SearchArrayController extends Controller {
	@FXML
	protected TextField input;
	
	@FXML
	protected HBox arrayRec;
	
	@FXML
	protected Label labelSearch;
	
	protected int flag = 0;
	protected int size = 10;
	protected List<StackPane> rectangles = new ArrayList<StackPane>();
	protected SearchArrayEngine engine;
	protected int[] array = new int[size];
	protected int target = 1;
	
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
}
