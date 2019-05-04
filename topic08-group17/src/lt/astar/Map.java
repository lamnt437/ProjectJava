package lt.astar;
import java.util.ArrayList;
import java.lang.Math;

public class Map {
    ArrayList<Node> allNodes = new ArrayList<Node>();
    ArrayList<Node> borderList = new ArrayList<Node>();
    Node startNode;
    Node endNode;

    public ArrayList<Node> getAllNodes() {
        return allNodes;
    }
    
    public void setAllNodes(ArrayList<Node> allNodes) {
        this.allNodes = allNodes;
    }
    
    public ArrayList<Node> getBorderList() {
        return borderList;
    }
    
    public void setBorderList(ArrayList<Node> borderList) {
        this.borderList = borderList;
    }
    
    public boolean addBorder(Node newNode) {
    	if(newNode != null) {
	    	for(Node node : borderList) {
	    		if(node.equals(newNode)) {
	    			return false;
	    		}
	    	}
	    	borderList.add(newNode);
	    	return true;
    	}
    	return false;
    }
    
    public Node getStartNode() {
        return startNode;
    }
    
    public void setStartNode(Node startNode) {
        this.startNode = startNode;
    }
    
    public Node getEndNode() {
        return endNode;
    }
    
    public void setEndNode(Node endNode) {
        this.endNode = endNode;
    }

    public boolean isExist(Node n) {
        for(Node node : allNodes) {
            if(n.equals(node))
                return true;
        }
        return false;
    }

    public boolean isBorder(Node n) {
        for(Node node : borderList) {
            if(n.equals(node))
                return true;
        }
        return false;
    }

    public Node findNode(ArrayList<Node> list, Node n) {
        for(Node node : list) {
            if(node.equals(n))
                return node;
        }
        return null;
    }

    public boolean isValidNode(Node n) {
        //find if exists in all node
        if(isExist(n)) {
            //find if node is border
            if(!isBorder(n))
                return true;
        }
        return false;
    }

    public boolean isInPath(ArrayList<Node> path, Node n) {
    	for(Node node : path) {
    		if(n.equals(node))
    			return true;
    	}
    	return false;
    }


    public ArrayList<Node> getNeighbors(Node n){
        ArrayList<Node> neighbors = new ArrayList<Node>();

        int nx = n.getX();
        int ny = n.getY();

        int tempX;
        int tempY;

        //up
        Node tempUp = new Node();
        tempX = nx;
        tempY = ny - 1;
        tempUp.setX(tempX);
        tempUp.setY(tempY);
        if(isValidNode(tempUp))
            neighbors.add(tempUp);

        //down
        Node tempDown = new Node();
        tempX = nx;
        tempY = ny + 1;
        tempDown.setX(tempX);
        tempDown.setY(tempY);
        //check valid node + check in open list + check closed list
        if(isValidNode(tempDown)){
            neighbors.add(tempDown);
        }


        //left
        Node tempLeft = new Node();
        tempX = nx - 1;
        tempY = ny;
        tempLeft.setX(tempX);
        tempLeft.setY(tempY);
        if(isValidNode(tempLeft))
            neighbors.add(tempLeft);

        //right
        Node tempRight = new Node();
        tempX = nx + 1;
        tempY = ny;
        tempRight.setX(tempX);
        tempRight.setY(tempY);
        if(isValidNode(tempRight))
            neighbors.add(tempRight);

        return neighbors;
    }



    public int calculateH(Node currentNode) {
        //current node's coordinate
        int u = currentNode.getX();
        int v = currentNode.getY();

        //end node's coordinate
        int x = endNode.getX();
        int y = endNode.getY();

        int hCost = Math.abs(u - x) + Math.abs(v - y);

        return hCost;
    }
    
    public void reset() {
    	borderList = new ArrayList<Node>();
    }
}
