import java.util.ArrayList;
import java.util.Collections;

public class SearchPath {
    private Map map;
    
    private ArrayList<Node> openList = new ArrayList<Node>();
    private ArrayList<Node> closedList = new ArrayList<Node>();
    private ArrayList<Node> path = new ArrayList<Node>();
    private ArrayList<Node> neighbors;
    
    private Node startNode;
    private Node endNode;
    private Node curNode;
    
    private boolean isFound = false;

    /* constructor */
    public SearchPath(Map map) {
    	this.map = map;
    	startNode = map.getStartNode();
    	endNode = map.getEndNode();
    	curNode = null;
    }

    /* get */
    public boolean getFoundStatus() {
    	return isFound;
    }
    
    public ArrayList<Node> getOpenList() {
        return openList;
    }
    
    public ArrayList<Node> getClosedList() {
        return closedList;
    }


    public Node findNode(ArrayList<Node> list, Node n) {
        for(Node node : list) {
            if(node.equals(n))
                return node;
        }
        return null;
    }
    
    public ArrayList<Node> getPath(){
    	if(isFound)
    		return path;
    	return null;
    }
    
    public ArrayList<Node> getNeighbors() {
		return neighbors;
	}
    
    public void reset() {
    	openList = new ArrayList<Node>();
        closedList = new ArrayList<Node>();
        path = new ArrayList<Node>();
        neighbors = new ArrayList<Node>();
        curNode = null;
    }
    
    
    /* search path */

    public void init() {
    	 //enqueue startNode to open List
        openList.add(startNode);
    }
    
    public boolean checkFinished() {
    	return endNode.equals(curNode) || openList.isEmpty();
    }
    
    public boolean checkTarget() {
    	return endNode.equals(curNode);
    }
    
    public void nextStep() {
    	if(checkFinished())
    		return;
    	
    	//dequeue node with smallest f from open List
        Collections.sort(openList);
        curNode = openList.remove(0);
        
        //if cur is target then return
        if(checkTarget())
        	return;
        
        //get neighbors + calculate
        neighbors = map.getNeighbors(curNode);

        for(Node neighbor : neighbors) {
            //calculate g
            int currentG = curNode.getG() + 1;


            //find if exist in open list then compare with current g cost
            Node foundNode = findNode(openList, neighbor);

            if(foundNode != null) {                
                //update directly on neighbor
            	neighbor = foundNode;

                //compare g cost
                int oldG = neighbor.getG();
                if(oldG <= currentG) continue;
            }

            //else find if exist in closed list
            else {

                foundNode = findNode(closedList, neighbor);
                if(foundNode != null) {
                    neighbor = foundNode;

                    //compare g cost
                    int oldG = neighbor.getG();
                    if(oldG <= currentG) continue;

                    //move this neighbor to openList
                    closedList.remove(neighbor);
                    openList.add(neighbor);
                }

                else {
                    int hCost = map.calculateH(neighbor);
                    neighbor.setH(hCost);
                    openList.add(neighbor);
                }
            }

            neighbor.setG(currentG);
            neighbor.setF();
            neighbor.setParent(curNode);
        }
        
        //add cur to closed list
        closedList.add(curNode);
        
        //return all info to main
    }
    
    //construct path
    public void constructPath() {
    	if(endNode.equals(curNode) && isFound == false) {
    		path.add(curNode);
            Node foundParent = curNode.getParent();
            while(foundParent != null) {
                path.add(foundParent);
                foundParent = foundParent.getParent();
            }

            //reverse path
            Collections.reverse(path);
            isFound = true;
    	}
    }
    
//    public ArrayList<Node> searchPath(){
//        // pseudo-code
//        ArrayList<Node> path = new ArrayList<Node>();
//
//        Node startNode = map.getStartNode();
//        Node endNode = map.getEndNode();

//        //enqueue startNode to open List
//        openList.add(startNode);

//        //init cur
//        Node cur = null;

//        while(!openList.isEmpty()) {

//            //dequeue node with smallest f from open List
//            Collections.sort(openList);
//            cur = openList.remove(0);

//            //if cur is target then return
//            if(cur.equals(endNode))
//                break;

            //else

//            neighbors = map.getNeighbors(cur);
//
//            for(Node neighbor : neighbors) {
//                //calculate g
//                int currentG = cur.getG() + 1;
//
//
//                //find if exist in open list then compare with current g cost
//                Node foundNode = findNode(openList, neighbor);
//
//                if(foundNode != null) {
//                    neighbor = foundNode;
//                    //update directly on neighbor
//
//                    //compare g cost
//                    int oldG = neighbor.getG();
//                    if(oldG <= currentG) continue;
//                }
//
//                //else find if exist in closed list
//                else {
//
//                    foundNode = findNode(closedList, neighbor);
//                    if(foundNode != null) {
//                        neighbor = foundNode;
//
//                        //compare g cost
//                        int oldG = neighbor.getG();
//                        if(oldG <= currentG) continue;
//
//                        //move this neighbor to openList
//                        closedList.remove(neighbor);
//                        openList.add(neighbor);
//                    }
//
//                    else {
//                        int hCost = map.calculateH(neighbor);
//                        neighbor.setH(hCost);
//                        openList.add(neighbor);
//                    }
//                }
//
//                neighbor.setG(currentG);
//                neighbor.setF();
//                neighbor.setParent(cur);
//            }

//            //add cur to closed list
//            closedList.add(cur);

//        }

//        if(!cur.equals(endNode))
//            return null;

//        path.add(cur);
//        Node foundParent = cur.getParent();
//        while(foundParent != null) {
//            path.add(foundParent);
//            foundParent = foundParent.getParent();
//        }
//
//        //reverse path
//        Collections.reverse(path);

//        return path;
//    }
    
    

	public void printPath(ArrayList<Node> path) {
        for(Node node : path) {
            System.out.print(node.toString() + " ");
        }
    }
}
