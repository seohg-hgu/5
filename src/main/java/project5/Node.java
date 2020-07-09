package project5;

import java.awt.Point;
import java.util.ArrayList;

public class Node {
	ArrayList<Node> link = new ArrayList<Node>();
	Point p;
	double score;
	int[][] st;
	
	public Node() {
	
	}
	
	public Node(double n) { 
		score = n; 
	} 

}
