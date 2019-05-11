package general;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class NodeRectangle extends NodeShape {
	public NodeRectangle(String labelText, Color color, int size) {
		background = new StackPane();
		label = new Text(labelText);
		box = new Rectangle();
		
		((Rectangle) box).setWidth(size);
		((Rectangle) box).setHeight(size);
		box.setFill(color);
		background.getChildren().addAll(box, label);
	}
	
	public NodeRectangle(Color color, int size) {
		background = new StackPane();
		label = new Text("");
		box = new Rectangle();
		
		((Rectangle) box).setWidth(size);
		((Rectangle) box).setHeight(size);
		box.setFill(color);
		background.getChildren().addAll(box, label);
	}
}
