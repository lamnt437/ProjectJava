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
	protected HBox arrayNode;
	
	@FXML
	protected Label labelSearch;
	
	protected int flag = 0;
	protected int size = 10;
//	protected List<StackPane> rectangles = new ArrayList<StackPane>();
	protected SearchArrayEngine engine;
	protected int[] array = new int[size];
	protected int target = 1;
	
	protected List<NodeShape> nodes = new ArrayList<NodeShape>();
	
	public void initArray(int[] array, int size) {
		for (int i = 0; i < size; i++) {
			array[i] = (int)(Math.random() * 50 + 1);
		}
	}
	
	public void createRectangles(int size) {
		this.initArray(this.array, size);
		for (int i = 0; i < size; i++) {
			NodeRectangle node = new NodeRectangle("" + array[i], Color.BISQUE, 47);
			nodes.add(node);
		}
		this.arrayNode.setPadding(new Insets(5, 10, 5, 20));
		this.arrayNode.setSpacing(15);
		for (NodeShape node : nodes) {
			this.arrayNode.getChildren().add(node.getNode());
		}
	}
	
	public void initSearch() {
		if (flag == 0) {
			this.createRectangles(this.size);
			flag = 1;
		} else {
			arrayNode.getChildren().clear();
			nodes.clear();
			this.createRectangles(this.size);
		}
	}
}
