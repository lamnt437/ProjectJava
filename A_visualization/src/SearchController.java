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
	
	int size = 10;
	Rectangle[][] recs = new Rectangle[size][size];
	SearchPath engine;
	
	Timeline timeline = new Timeline();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		// TODO (don't really need to do anything here).
	      
	}
	
	public void runSearch(ActionEvent event) {
		for(int i = 0; i < size; i++) {		//row
        	for(int j = 0; j < size; j++) {	//col
        		recs[i][j] = new Rectangle();
        		recs[i][j].setWidth(48);
        		recs[i][j].setHeight(48);
				recs[i][j].setFill(Color.WHITE);
		        
		        
		        GridPane.setRowIndex(recs[i][j], i);
		        GridPane.setColumnIndex(recs[i][j], j);
		        
		        gridpane.getChildren().addAll(recs[i][j]);
        	}
        }
		
		/////////////////////////////////////////////////////working
		        
		Map map = new Map();
		
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
		
		ArrayList<Node> borderList = new ArrayList<Node>();
		Node obstacle = new Node();
		obstacle.setX(0);
		obstacle.setY(4);
		borderList.add(obstacle);
		borderList.add(new Node(1, 6));
		borderList.add(new Node(6, 9));
		
		startNode.setH(map.calculateH(startNode));
		startNode.setG(0);
		startNode.setF();
		endNode.setH(map.calculateH(endNode));
		
		map.setAllNodes(allNodes);
		map.setBorderList(borderList);
		map.setStartNode(startNode);
		map.setEndNode(endNode);
		
		/* end init map */
		
		/* highlight */
        
        // highlight border
        for(int i = 0; i < size; i++) {
        	for(int j = 0; j < size; j++) {
        		Node temp = new Node();
        		temp.setX(i);
        		temp.setY(j);
        		if(map.isBorder(temp))
        			recs[i][j].setFill(Color.BLACK);
        	}
        }
        
        /* end highlight */
        
        /* running */
        engine = new SearchPath(map);
        
        engine.init();
        
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(20), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
            	/* wrap timeline here */
                if(!engine.checkFinished()) {
                	engine.nextStep();
                	
                	// get info then display
                	ArrayList<Node> openList = engine.getOpenList();
                	ArrayList<Node> closedList = engine.getClosedList();
                	ArrayList<Node> neighbors = engine.getNeighbors();
                	
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
                    }
                	timeline.stop();
                }
                /* timeline */
            }
        }));
        // Repeat indefinitely until stop() method is called.
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.setAutoReverse(true);
        timeline.play();
        
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
        	
		}
	}
	
	public void pauseSearch(ActionEvent event) {
		timeline.pause();
	}
	
	public void stopSearch(ActionEvent event) {
		engine.reset();
		timeline.stop();
	}
	
	public void gridClicked(ActionEvent event) {
		System.out.println("Grid clicked!");
	}
	
	public boolean isInList(ArrayList<Node> list, Node n) {
    	for(Node node : list) {
    		if(n.equals(node))
    			return true;
    	}
    	return false;
    }
}
