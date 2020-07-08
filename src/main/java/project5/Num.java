package project5;

import java.awt.Point;
import java.util.ArrayList;

public class Num {
	ArrayList<Num> link = new ArrayList<Num>();
	Point p;
	int score;
	
	public Num() {
	
	}
	
	public Num(Point p,int score) {
		this.p=p;
		this.score=score;
	}
}
