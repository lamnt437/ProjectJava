package general;

import java.util.ArrayList;
import java.util.List;

import general.Controller;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import lt.seqsearch.SearchEngine;

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
	protected SearchEngine engine;
	protected int[] array = new int[size];
	protected int target = 1;
}
