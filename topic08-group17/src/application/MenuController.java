package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import lt.astar.AStarMain;
import lt.binsearch.BinSearchMain;
import lt.seqsearch.SeqSearchMain;

public class MenuController implements Initializable {
	ObservableList list = FXCollections.observableArrayList();
	@FXML
	private ChoiceBox<String> choiceBox;
	
	@FXML
	private Button submit;
	
	private void loadData() {
		list.removeAll(list);
		choiceBox.getItems().clear();
		list.add("1.Sequential Search");
		list.add("2.Binary Search");
		list.add("3.A* Search");
		choiceBox.getItems().addAll(list);
		choiceBox.setValue("Choosing...");
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		loadData();
	}
	
	public void submitChoice() {
		String choice = choiceBox.getValue();
		Stage s = new Stage();
		if (choice == "1.Sequential Search") {
			new SeqSearchMain().start(s);
		} else if (choice == "2.Binary Search") {
			new BinSearchMain().start(s);
		} else {
			new AStarMain().start(s);
		}
	}
}
