
public class Node implements Comparable<Node> {
    private int x;
    private int y;
    private Node parent;

    private int g;
    private int h;
    private int f;

    public Node() {
    	
    }
    
    public Node(int x, int y) {
    	this.x = x;
    	this.y = y;
    }

    public int getG() {
        return g;
    }
    public void setG(int g) {
        this.g = g;
    }
    public int getH() {
        return h;
    }
    public void setH(int h) {
        this.h = h;
    }
    public int getF() {
        return f;
    }
    public void setF() {
        this.f = g + h;
    }


    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }
    public Node getParent() {
        return parent;
    }
    public void setParent(Node parent) {
        this.parent = parent;
    }

    @Override
    public String toString() {
        return String.format("(%d:%d)", x, y);
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Node) {
            return ((Node) o).getX() == x && ((Node) o).getY() == y;
        }
        return false;
    }

    @Override
    public int compareTo(Node o) {
        if(o instanceof Node) {
            Node param = (Node) o;
            if(f < param.getF())
                return -1;
            else if(f > param.getF())
                return 1;
            else
                return 0;
        }
        throw new ClassCastException();
    }
}
