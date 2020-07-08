package project5;

import java.awt.Point;
import java.util.ArrayList;

public class Node {
	ArrayList<Node> link = new ArrayList<Node>();
	Point p;
	double score;
	
	public Node() {
	
	}
	
	public Node(Point p,int score) {
		this.p=p;
		this.score=score;
	}
}
