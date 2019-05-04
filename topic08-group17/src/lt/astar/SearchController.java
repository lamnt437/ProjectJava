package lt.astar;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class SearchController implements Initializable {
	@FXML
	private GridPane gridpane;
	
	@FXML
	private Button startBtn;
	
	@FXML
	private Button pauseBtn;
	
	@FXML
	private Button stopBtn;
	
	@FXML
	private TextField textInfo;
	
	private int size = 10;
	private int recSize = 50;
	private Rectangle[][] recs = new Rectangle[size][size];
	private SearchPath engine;
	private Map map = new Map();
	private boolean paused = false;
	
	Timeline timeline = new Timeline();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		// TODO (don't really need to do anything here).
		gridpane.setGridLinesVisible(true);
	      
	}
	
	public void runSearch(ActionEvent event) {
		textInfo.setText("Running...");
		if(!paused) {
			
			/* add background */
			for(int i = 0; i < size; i++) {		//row
	        	for(int j = 0; j < size; j++) {	//col
	        		recs[j][i] = new Rectangle();
	        		recs[j][i].setWidth(48);
	        		recs[j][i].setHeight(48);
	        		recs[j][i].setFill(Color.WHITE);
			        
			        
			        GridPane.setRowIndex(recs[j][i], i);
			        GridPane.setColumnIndex(recs[j][i], j);
			        
			        gridpane.getChildren().add(recs[j][i]);
	        	}
	        }
			/*end background*/
			
			
			/* init map */			
			ArrayList<Node> allNodes = new ArrayList<Node>();
			for(int i = 0; i < size; i++) {
				for(int j = 0; j < size; j++) {
					Node tempNode = new Node();
					tempNode.setX(i);
					tempNode.setY(j);
					tempNode.setParent(null);
					allNodes.add(tempNode);
				}
			}
			
			Node startNode = new Node();
			startNode.setX(0);
			startNode.setY(0);
			map.setStartNode(startNode);
			
			Node endNode = new Node();
			endNode.setX(9);
			endNode.setY(9);
			map.setEndNode(endNode);
			
			startNode.setH(map.calculateH(startNode));
			startNode.setG(0);
			startNode.setF();
			endNode.setH(map.calculateH(endNode));
			
			map.setAllNodes(allNodes);
			map.setStartNode(startNode);
			map.setEndNode(endNode);			
			/* end init map */
			
			
			/* highlight border*/			
	        for(int i = 0; i < size; i++) {
	        	for(int j = 0; j < size; j++) {
	        		Node temp = new Node();
	        		temp.setX(i);
	        		temp.setY(j);
	        		if(map.isBorder(temp))
	        			recs[i][j].setFill(Color.BLACK);
	        	}
	        }	        
	        /* end highlight border*/
	        
	        
	        /* running */
	        engine = new SearchPath(map);
	        
	        engine.init();
	        
	        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(70), new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent actionEvent) {
	                if(!engine.checkFinished()) {
	                	engine.nextStep();
	                	
	                	// get info then display
	                	ArrayList<Node> openList = engine.getOpenList();
	                	ArrayList<Node> closedList = engine.getClosedList();
	                	ArrayList<Node> neighbors = engine.getNeighbors();
	                	Node curNode = engine.getCurNode();
	                	
	                	for(int i = 0; i < size; i++) {
	                    	for(int j = 0; j < size; j++) {
	                    		Node temp = new Node();
	                    		temp.setX(i);
	                    		temp.setY(j);
	                    		if(isInList(openList, temp))
	                    			recs[i][j].setFill(Color.GREEN);
	                    		if(isInList(closedList, temp))
	                    			recs[i][j].setFill(Color.RED);
	                    		if(isInList(neighbors, temp))
	                    			recs[i][j].setFill(Color.BLUE);
	                    		if(curNode.equals(temp))
	                    			recs[i][j].setFill(Color.PINK);
	                    	}
	                    }
	                }
	                else {
	                	engine.constructPath();
	                    if(engine.getFoundStatus()) {
	                    	ArrayList<Node> path = engine.getPath();
	                    	for(int i = 0; i < size; i++) {
	                        	for(int j = 0; j < size; j++) {
	                        		Node temp = new Node();
	                        		temp.setX(i);
	                        		temp.setY(j);
	                        		if(isInList(path, temp))
	                        			recs[i][j].setFill(Color.YELLOW);
	                        	}
	                        }
	                    	textInfo.setText("Found path!");
	                    }
	                    else {
	                    	textInfo.setText("Not found path!");
	                    }
	                	timeline.stop();
	                }
	            }
	        }));
	        
	        // Repeat indefinitely until stop() method is called.
	        timeline.setCycleCount(Animation.INDEFINITE);
	        timeline.setAutoReverse(true);
	        timeline.play();
		}
		else {
			timeline.play();
			paused = false;
		}
	}
	
	public void pauseSearch(ActionEvent event) {
		paused = true;
		textInfo.setText("Paused!");
		timeline.pause();
	}
	
	public void stopSearch(ActionEvent event) {
		textInfo.setText("Stopped!");
		engine.reset();
		timeline.stop();
		paused = false;
	}
	
	public void resetSearch(ActionEvent event){
		map.reset();
		if(engine != null)
			engine.reset();
		gridpane.getChildren().clear();
		gridpane.setGridLinesVisible(true);
		textInfo.setText("Click on the area above to add obstacles!");
		paused = false;
	}
	
	public void gridClicked(MouseEvent mouseEvent) {
		// add obstacles when clicked
		double xPos = mouseEvent.getX();
		double yPos = mouseEvent.getY();
		System.out.printf("(%.2f, %.2f)\n", xPos, yPos);
		
		int x = ((int)xPos) / recSize;
		int y = ((int)yPos) / recSize;
		
		Node newNode = new Node(x, y);
		boolean addStatus = map.addBorder(newNode);
		if(addStatus) {
			recs[y][x] = new Rectangle();
			recs[y][x].setWidth(48);
			recs[y][x].setHeight(48);
			recs[y][x].setFill(Color.BLACK);
			
			GridPane.setRowIndex(recs[y][x], y);
	        GridPane.setColumnIndex(recs[y][x], x);
	        
	        gridpane.getChildren().add(recs[y][x]);
		}
		
	}
	
	public boolean isInList(ArrayList<Node> list, Node n) {
    	for(Node node : list) {
    		if(n.equals(node))
    			return true;
    	}
    	return false;
    }
}
