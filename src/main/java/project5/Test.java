package project5;

import javax.swing.JFrame;

public class Test {
	
	JFrame mainFrame = new JFrame();
	static Node num;
	
	public static void main(String[] args) {
		new Test();
	}
	
//	int [] t = new int[] {-1, 3, 5,  1, -6, -4, 0, 9};
//	int[][][] test = new int[][][] {{{-1, 3}, {5, 1}}, {{-6, -4}, {0, 9}}}; 
	
	/*public Test() {
		Num num = makeTree2();
		
		
		System.out.println(minimax2(num, 4, Integer.MIN_VALUE, Integer.MAX_VALUE, true));
	}
	*/
	
	private Node makeTree1() {
		num = new Node(); // 1열
		
		num.link.add(new Node()); // 3, -4
		num.link.add(new Node());
		
		Node tmp1 = num.link.get(0); // 3, 5
		tmp1.link.add(new Node());
		tmp1.link.add(new Node());
		
		Node tmp2 = tmp1.link.get(0);
		tmp2.link.add(Board.n1);
		tmp2.link.add(Board.n2);
		
		tmp2 = tmp1.link.get(1);
		tmp2.link.add(Board.n3);
		tmp2.link.add(Board.n4);
		
		
		return num;
	}
	
	/*
	private Num makeTree2() {
		Num num = new Num(); // 1열
		
		num.link.add(new Num());
		num.link.add(new Num());
		
		Num tmp1 = num.link.get(0);
		tmp1.link.add(new Num());
		tmp1.link.add(new Num());
		
		Num tmp2 = tmp1.link.get(0);
		tmp2.link.add(new Num());
		tmp2.link.add(new Num());
		
		Num tmp3 = tmp2.link.get(0);
		tmp3.link.add(new Num(8));
		tmp3.link.add(new Num(5));
		
		tmp3 = tmp2.link.get(1);
		tmp3.link.add(new Num(6));
		tmp3.link.add(new Num(-4));
		
		tmp2 = tmp1.link.get(1);
		tmp2.link.add(new Num());
		tmp2.link.add(new Num());
		
		tmp3 = tmp2.link.get(0);
		tmp3.link.add(new Num(3));
		tmp3.link.add(new Num(8));
		
		tmp3 = tmp2.link.get(1);
		tmp3.link.add(new Num(4));
		tmp3.link.add(new Num(-6));
		
		//-----------tmp1 get2
		
		tmp1 = num.link.get(1);
		tmp1.link.add(new Num());
		tmp1.link.add(new Num());
		
		tmp2 = tmp1.link.get(0);
		tmp2.link.add(new Num());
		tmp2.link.add(new Num());
		
		tmp3 = tmp2.link.get(0);
		tmp3.link.add(new Num(1));
		tmp3.link.add(new Num(100000));
		
		tmp3 = tmp2.link.get(1);
		tmp3.link.add(new Num(5));
		tmp3.link.add(new Num(2));
		
		tmp2 = tmp1.link.get(1);
		tmp2.link.add(new Num());
		tmp2.link.add(new Num());
		
		tmp3 = tmp2.link.get(0);
		tmp3.link.add(new Num(-1000));
		tmp3.link.add(new Num(333333));
		
		tmp3 = tmp2.link.get(1);
		tmp3.link.add(new Num(11111111));
		tmp3.link.add(new Num(22222222));
		
		return num;
	}
	*/
	/*public Node minimax1(Node position, int depth, boolean maximizingPlayer) {
		if(depth == 0 || gameover()) return position;
		
		//double maxEval = Integer.MIN_VALUE;
		//double minEval = Integer.MAX_VALUE;
		
		if(maximizingPlayer) {
			for(int i=0; i<position.link.size(); i++) {
				Node eval = minimax1(position.link.get(i), depth-1, false);
				maxEval = Math.max(maxEval, eval.score);
				if()
			}
//			System.out.println(maxEval);
			return maxEval;
		}
		else {
			for(int i=0; i<position.link.size(); i++) {
				Node eval = minimax1(position.link.get(i), depth-1, true);
				minEval = Math.min(minEval, eval.score);
			}
//			System.out.println(minEval);
			return minEval;
		}
	}*/
	/*
	private int minimax2(Num position, int depth, int alpha, int beta, boolean maximizingPlayer) {
		if(depth == 0 || gameover()) return position.num;
		
		int maxEval = Integer.MIN_VALUE;
		int minEval = Integer.MAX_VALUE;
		
		if(maximizingPlayer) {
			for(int i=0; i<position.link.size(); i++) {
				int eval = minimax2(position.link.get(i), depth-1, alpha, beta, false);
				maxEval = Math.max(maxEval, eval);
				alpha = Math.max(alpha, eval);
				if(beta <= alpha) {
					break;
				}
			}
			System.out.println(maxEval);
			return maxEval;
		}
		else {
			for(int i=0; i<position.link.size(); i++) {
				int eval = minimax2(position.link.get(i), depth-1, alpha, beta, true);
				minEval = Math.min(minEval, eval);
				beta = Math.min(beta, eval);
				if(beta <= alpha) {
					break;
				}
			}
			System.out.println(minEval);
			return minEval;
		}
	}
	*/
	private boolean gameover() {
		return false;
	}
	
	
}
