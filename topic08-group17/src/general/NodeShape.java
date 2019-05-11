package general;

import javafx.scene.shape.Shape;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
//import javafx.scene.control.Label;
import javafx.scene.text.Text;

public abstract class NodeShape {
	protected StackPane background;
	protected Shape box;
	protected Text label;
	
	public void setColor(Color color) {
		box.setFill(color);
	}
	
	public void setLabel(String labelText) {
		label.setText(labelText);
	}
	
	public StackPane getNode() {
		return background;
	}
	
	public void blink() {
		
	}
}
