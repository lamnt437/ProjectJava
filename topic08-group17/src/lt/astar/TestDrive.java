//package lt.astar;
//import java.util.ArrayList;
//
//public class TestDrive {
//
//    public static void main(String[] args) {
//        Map map = new Map();
//
//        /* init map */
//
//        ArrayList<Node> allNodes = new ArrayList<Node>();
//        for(int i = 0; i < 10; i++) {
//            for(int j = 0; j < 10; j++) {
//                Node tempNode = new Node();
//                tempNode.setX(i);
//                tempNode.setY(j);
//                tempNode.setParent(null);
//                allNodes.add(tempNode);
//            }
//        }
//
//        Node startNode = new Node();
//        startNode.setX(0);
//        startNode.setY(0);
//        map.setStartNode(startNode);
//
//        Node endNode = new Node();
//        endNode.setX(9);
//        endNode.setY(9);
//        map.setEndNode(endNode);
//
//        ArrayList<Node> borderList = new ArrayList<Node>();
//        Node obstacle = new Node();
//        obstacle.setX(0);
//        obstacle.setY(4);
//        borderList.add(obstacle);
//
//        startNode.setH(map.calculateH(startNode));
//        startNode.setG(0);
//        startNode.setF();
//        endNode.setH(map.calculateH(endNode));
//
//        map.setAllNodes(allNodes);
//        map.setBorderList(borderList);
//        map.setStartNode(startNode);
//        map.setEndNode(endNode);
//
//        /* end init map */
//
//        SearchPath engine = new SearchPath(map);
//
////        ArrayList<Node> path = engine.searchPath();
////        engine.printPath(path);
//    }
//
//}
