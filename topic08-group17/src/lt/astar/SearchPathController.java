package lt.astar;

import general.Controller;
import general.NodeRectangle;

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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class SearchPathController extends Controller implements Initializable {
	
	@FXML
	private Button pauseBtn;
	
	private int size = 10;
	private int recSize = 50;
	private NodeRectangle[][] recs = new NodeRectangle[size][size];
	private SearchPathEngine engine;
	private Map map = new Map();
	private boolean paused = false;
	private Timeline timeline = new Timeline();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		// TODO (don't really need to do anything here).
		gridpane.setGridLinesVisible(true);
	      
	}
	
	public void runSearch(ActionEvent event) {
		textSearch.setText("Running...");
		if(!paused) {
			
			/* add background */
			for(int i = 0; i < size; i++) {		//row
	        	for(int j = 0; j < size; j++) {	//col
	        		recs[j][i] = new NodeRectangle(Color.WHITE, recSize - 2);
			        
			        
			        GridPane.setRowIndex(recs[j][i].getNode(), i);
			        GridPane.setColumnIndex(recs[j][i].getNode(), j);
			        
			        gridpane.getChildren().add(recs[j][i].getNode());
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
	        			recs[i][j].setColor(Color.BLACK);
	        	}
	        }	        
	        /* end highlight border*/
	        
	        
	        /* running */
	        engine = new SearchPathEngine(map);
	        
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
	                    		if(isInList(openList, temp)) {
	                    			temp = getNode(openList, temp);
	                    			recs[i][j].setColor(Color.GREEN);
	                    			recs[i][j].setLabel("" + temp.getF());
	                    		}
	                    			
	                    		if(isInList(closedList, temp)) {
	                    			temp = getNode(closedList, temp);
	                    			recs[i][j].setColor(Color.RED);
	                    			recs[i][j].setLabel("" + temp.getF());
	                    		}
	                    		if(isInList(neighbors, temp)) {
	                    			recs[i][j].setColor(Color.BLUE);
	                    		}
	                    		if(curNode.equals(temp)) {
	                    			recs[i][j].setColor(Color.ORANGE);
	                    		}
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
	                        			recs[i][j].setColor(Color.YELLOW);
	                        	}
	                        }
	                    	textSearch.setText("Found path!");
	                    }
	                    else {
	                    	textSearch.setText("Not found path!");
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
		textSearch.setText("Paused!");
		timeline.pause();
	}
	
	public void stopSearch(ActionEvent event) {
		textSearch.setText("Stopped!");
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
		textSearch.setText("Click on the area above to add obstacles!");
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
			recs[y][x] = new NodeRectangle(Color.BLACK, recSize - 2);
			
			GridPane.setRowIndex(recs[y][x].getNode(), y);
	        GridPane.setColumnIndex(recs[y][x].getNode(), x);
	        
	        gridpane.getChildren().add(recs[y][x].getNode());
		}
		
	}
	
	public boolean isInList(ArrayList<Node> list, Node n) {
    	for(Node node : list) {
    		if(n.equals(node))
    			return true;
    	}
    	return false;
    }
	
	public Node getNode(ArrayList<Node> list, Node n) {
		for(Node node : list) {
    		if(n.equals(node))
    			return node;
    	}
    	return null;
	}
}
