package general;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class NodeCircle extends NodeShape {
	public NodeCircle(String labelText, Color color, int size) {
		background = new StackPane();
		label = new Text(labelText);
		box = new Circle();
		
		((Circle) box).setRadius(size);
		((Circle) box).setRadius(size);
		box.setFill(color);
		background.getChildren().addAll(box, label);
	}
	
	public NodeCircle(Color color, int size) {
		background = new StackPane();
		label = new Text("");
		box = new Circle();
		
		((Circle) box).setRadius(size);
		((Circle) box).setRadius(size);
		box.setFill(color);
		background.getChildren().addAll(box, label);
	}
}
