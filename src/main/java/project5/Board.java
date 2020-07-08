package project5;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Board extends JPanel implements MouseListener, MouseMotionListener {
	Point aa;
	
	AudioInputStream inAudio;
	Clip clip1;
	int[][] resultArr=new int[19][19];
	
	public Board() {
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		repaint();
	}

	public void paintComponent(Graphics g) {
		Color brown = new Color(204, 153, 051);
		g.setColor(brown);
		g.fillRect(5, 5, 460, 460);

		for (int i = 0; i < 19; i++) {
			g.setColor(Color.black);
			g.drawLine(10, 10 + 25 * i, 460, 10 + 25 * i);
			g.drawLine(10 + 25 * i, 10, 10 + 25 * i, 460);
		}
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				g.fillOval(82 + 150 * i, 82 + 150 * j, 6, 6);
			}
		}
		for (Point t : Main.bwList) {
			if (Main.r[t.x][t.y] == 1) {
				g.setColor(Color.RED);
				// System.out.println("R");
			}
			if (Main.b[t.x][t.y] == 1) {
				g.setColor(Color.black);
				// System.out.println("B");
			}
			if (Main.w[t.x][t.y] == 1) {
				g.setColor(Color.white);
				// System.out.println("W");
			}
			g.fillOval(25 * t.x, 25 * t.y, 20, 20);
			// System.out.println(t.x+t.y+": ");
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		for (int i = 0; i < 19; i++) {
			for (int j = 0; j < 19; j++) {
				if ((e.getPoint().x >= 0 + 25 * i && e.getPoint().x <= 25 * i + 20)
						&& (e.getPoint().y >= 25 * j && e.getPoint().y <= 25 * j + 20)) {
					if (Main.bwr[i][j] == 0) {
						Main.bwr[i][j] = 1;
						Sounds();
						Point t = new Point();
						t.x = i;
						t.y = j;
						Main.bwList.add(t);
						if (Main.setting == true) {
							Main.wLabel.setVisible(true);
							Main.bLabel.setVisible(true);
							Main.type = 'r';
							Main.r[i][j] = 1;

							// Main.rCount++;
						} else {

							if (Main.user == Main.BLACK) {
								if (Main.count == 0) {
									compareBlack();
									Main.wLabel.setVisible(false);
									Main.bLabel.setVisible(true);
									Main.user = Main.BLACK;
									Main.b[i][j] = 1;
									System.out.println(i+" "+j);
									// Main.count++;
									// 백돌 그리기
									compareBlack(); 
									comWhite();
									repaint();
									compareBlack();
									comWhite();
									repaint();
									Main.count++;
									Main.user = Main.BLACK;
								} else if (Main.count % 4 == 0 || Main.count % 4 == 3) {
									// BLACK
									compareBlack();
									Main.wLabel.setVisible(false);
									Main.bLabel.setVisible(true);
									Main.user = Main.BLACK;
									Main.b[i][j] = 1;
									System.out.println(i+" "+j);
									repaint();
									if (Main.count % 4 == 0) {
										//백돌 출력
										compareBlack();
										comWhite();
										repaint();
										compareBlack();
										comWhite();
										repaint();
									
										Main.count++;
										Main.user = Main.BLACK;
									} else if (Main.count % 4 == 3) {
										// System.out.println("HI3");
										Main.user = Main.BLACK;
										//카운트 증가
										Main.count++;
									}
									
									  if(winCheck(Main.b,i,j)) { 
										  repaint(); 
										  Object[] options = {"재시작", "종료"}; 
										  int n= JOptionPane.showOptionDialog(Main.board,"BLACK WIN!","",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,options, options[0]);
										  if(n==0) { 
											  Main.setting=false; 
											  clearArr();//배열 클리어하기
										  }else {
											  Main.setting=false; 
											  clearArr();//배열 클리어하기
											  quit(); 
										  }
									 
									  }
									 
								}
								repaint();
								//for timer
								if (Main.count == 0) {
									Main.timer.stop();
									Main.tMax = 15;
									Main.tCount = 0;
									Main.timer.restart();
								}
								if (Main.count > 0) {
									if (Main.count % 4 == 0 || Main.count % 4 == 2) {
										Main.timer.stop();
										Main.tMax = 15;
										Main.tCount = 0;
										Main.timer.restart();
									}
								}
								
							} else if (Main.user == Main.WHITE) { //user==white && computer==black 
								if (Main.count == 0) {
									Main.count++;
									Main.user = Main.WHITE;
								} else if (Main.count % 4 == 1 || Main.count % 4 == 2) {
									// WHITE
									Main.wLabel.setVisible(true);
									Main.bLabel.setVisible(false);
									Main.user = Main.WHITE;
									Main.w[i][j] = 1;
									repaint();

									if (winCheck(Main.w, i, j)) {
										repaint();
										Object[] options = { "재시작", "종료" };
										int n = JOptionPane.showOptionDialog(Main.board, "WHITE WIN!", "",
												JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options,
												options[0]);
										if (n == 0) {
											Main.setting = false;
											clearArr();// 배열 클리어하기
										} else {
										}
										Main.setting = false;
										clearArr();// 배열 클리어하기
										quit();
									}

								}

								if (Main.count % 4 == 2) {
									compareWhite();
									comBlack();
									repaint();
									compareWhite();
									comBlack();
									repaint();
									Main.count++;
									Main.user = Main.WHITE;
								} else if (Main.count % 4 == 1) {
									Main.user = Main.WHITE;
									Main.count++;
									
								}
							}
							repaint();
							if (Main.count == 0) {
								Main.timer.stop();
								Main.tMax = 15;
								Main.tCount = 0;
								Main.timer.restart();
							}
							if (Main.count > 0) {
								if (Main.count % 4 == 0 || Main.count % 4 == 2) {
									Main.timer.stop();
									Main.tMax = 15;
									Main.tCount = 0;
									Main.timer.restart();
								}
							}
						}

					}

					repaint();
				}
			}
		}
		
		/*//가중치 확인
		for(int i=0;i<19;i++) {
			for(int j=0;j<19;j++) {
				System.out.print(resultArr[j][i]+ "    ");
			}
			System.out.println();
		}
		System.out.println();
		*/

	}
	
	//computer = white
	public void comWhite() {
		Main.wLabel.setVisible(true);
		Main.bLabel.setVisible(false);
		Main.user = Main.WHITE;
		Main.w[aa.x][aa.y] = 1;
		Main.bwr[aa.x][aa.y] = 1;
		Point t = new Point();
		t.x =aa.x;
		t.y =aa.y;
		Main.bwList.add(t);
		Sounds();
		Main.count++;
		if (winCheck(Main.w, aa.x, aa.y)) {
			repaint();
			Object[] options = { "재시작", "종료" };
			int n = JOptionPane.showOptionDialog(Main.board, "WHITE WIN!", "",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options,
					options[0]);
			if (n == 0) {
				Main.setting = false;
				clearArr();// 배열 클리어하기
			} else {
			}
			Main.setting = false;
			clearArr();// 배열 클리어하기
			quit();
		}
	}
	
	//computer = black
	public void comBlack() {
		Main.wLabel.setVisible(false);
		Main.bLabel.setVisible(true);
		Main.user = Main.BLACK;
		Main.b[aa.x][aa.y] = 1;
		Main.bwr[aa.x][aa.y] = 1;
		Point t = new Point();
		t.x =aa.x;
		t.y =aa.y;
		Main.bwList.add(t);
		Sounds();
		Main.count++;
		if (winCheck(Main.b, aa.x, aa.y)) {
			repaint();
			Object[] options = { "재시작", "종료" };
			int n = JOptionPane.showOptionDialog(Main.board, "BLACK WIN!", "",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options,
					options[0]);
			if (n == 0) {
				Main.setting = false;
				clearArr();// 배열 클리어하기
			} else {
			}
			Main.setting = false;
			clearArr();// 배열 클리어하기
			quit();
		}
	}
	

	// 승패 확인
	public boolean winCheck(int[][] arr, int i, int j) {
		int tI, tJ, sCount;

		// 가로 검사
		tI = i;
		tJ = j;
		sCount = 1;
		while (tI > 0) {
			tI--;
			if (arr[tI][tJ] == 1) {
				sCount++;
			} else {
				break;
			}
		}
		tI = i;
		while (tI < 18) {
			tI++;
			if (arr[tI][tJ] == 1) {
				sCount++;
			} else {
				break;
			}
		}
		if (sCount == 6) {
			return true;
		}

		// 세로 검사
		tI = i;
		tJ = j;
		sCount = 1;
		while (tJ > 0) {
			tJ--;
			if (arr[tI][tJ] == 1) {
				sCount++;
			} else {
				break;
			}
		}
		tJ = j;
		while (tJ < 18) {
			tJ++;
			if (arr[tI][tJ] == 1) {
				sCount++;
			} else {
				break;
			}
		}
		if (sCount == 6) {
			return true;
		}

		// 대각선 검사1
		tI = i;
		tJ = j;
		sCount = 1;
		while (tJ > 0 && tI > 0) {
			tJ--;
			tI--;
			if (arr[tI][tJ] == 1) {
				sCount++;
			} else {
				break;
			}
		}
		tI = i;
		tJ = j;
		while (tJ < 18 && tI < 18) {
			tJ++;
			tI++;
			if (arr[tI][tJ] == 1) {
				sCount++;
			} else {
				break;
			}
		}
		if (sCount == 6) {
			return true;
		}
		// 대각선 검사2
		tI = i;
		tJ = j;
		sCount = 1;
		while (tJ < 18 && tI > 0) { // 왼
			tJ++;
			tI--;
			if (arr[tI][tJ] == 1) {
				sCount++;
			} else {
				break;
			}
		}
		tI = i;
		tJ = j;
		while (tJ > 0 && tI < 18) { // 오
			tJ--;
			tI++;
			if (arr[tI][tJ] == 1) {
				sCount++;
			} else {
				break;
			}
		}
		if (sCount == 6) {
			return true;
		}
		return false;

	}

	void computerTurn() {

	}

	// 재시작 & 종료 전 clear
	void clearArr() {
		for (int i = 0; i < 19; i++) {
			for (int j = 0; j < 19; j++) {
				Main.b[i][j] = 0;
				Main.w[i][j] = 0;
				Main.r[i][j] = 0;
				Main.bwr[i][j] = 0;
				resultArr[i][j]=0;
			}
		}
		Main.bwList.clear();
		Main.count = 0;
		Main.timer.stop();
		Main.timerNumber.setText("0");
	}
	
	//start패널로 돌아가기 
	void quit() {
		Main.startpanel.setVisible(true);
		Main.btnPlusTime.setVisible(false);
		Main.btnBack.setVisible(false);
	}
	
	// 착수 효과음
	public void Sounds() {
		try {
			inAudio = AudioSystem.getAudioInputStream(new File("D:\\효과음\\123.wav"));
			clip1 = AudioSystem.getClip();
			clip1.open(inAudio);
			clip1.start();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	
	//가중치
	public double connect6ShapeScore(int consecutive, int openEnds, int currentTurn) { // shape에 따른 가중치 부여
		if (openEnds == 0 && consecutive < 6)
			return 0;
		switch (consecutive) {
		case 5:
			switch (openEnds) {
			case 1:
				if (currentTurn == Main.user) // this.currentTurn
					return 100000000;
				return 50;
			case 2:
				if (currentTurn == Main.user)
					return 100000000;
				return 500000;
			}
		case 4:
			switch (openEnds) {
			case 1:
				if (currentTurn == Main.user)
					return 100000000;
				return 50;
			case 2:
				if (currentTurn == Main.user)
					return 100000000;
				return 500000;
			}
		case 3:
			switch (openEnds) {
			case 1:
				if (currentTurn == Main.user)
					return 7;
				return 5;
			case 2:
				if (currentTurn == Main.user)
					return 10000;
				return 50;
			}
		case 2:
			switch (openEnds) {
			case 1:
				return 2;
			case 2:
				return 5;
			}
		case 1:
			switch (openEnds) {
			case 1:
				return 0.5;
			case 2:
				return 1;
			}
		default:
			return 200000000; // 우승!
		}
	}

	
	//computer = white인 경우 가로 검사
	public int[][] analyzeHorizontalSetsForBlack(int current_turn) {
		double score = 0;
		int countConsecutive = 0;
		int openEnds = 0;

		for (int i = 0; i < 19; i++) {
			for (int a = 0; a < 19; a++) {
				if (Main.b[a][i] == 1) { // 돌이 검정색이 되면 연속점 1증가
					countConsecutive++;
					resultArr[a][i]=Integer.MIN_VALUE;
				}
				else if (Main.bwr[a][i] == 0 && countConsecutive > 0) { // 비어있을 경우 연속된 돌이 있을 때,
					openEnds++;
					score += connect6ShapeScore(countConsecutive, openEnds, 0); // currentTurn is black
					resultArr[a][i]+=score;
					countConsecutive = 0;
					openEnds = 1;
				} else if (Main.bwr[a][i] == 0) // 비어있을 경우
					openEnds = 1;
				else if (countConsecutive > 0) {
					/*
					 * score+= connect6ShapeScore(countConsecutive, openEnds, 0); // currentTurn is
					 * black if (score > max) { max=score; n.p.x = a; n.p.y = i; }
					 */
					//countConsecutive = 0;
					//openEnds = 0;
					score += connect6ShapeScore(countConsecutive, openEnds, 0); // currentTurn is black
					resultArr[a][i]+=score;
					countConsecutive = 0;
					openEnds = 0;

				} else
					openEnds = 0;
			}
			/*if (countConsecutive > 0) {
				score = connect6ShapeScore(countConsecutive, openEnds, 0); // currentTurn is black
				resultArr[a][i]+=score;
			}*/

			countConsecutive = 0;
			openEnds = 0;
		}
		for (int i = 0; i < 19; i++) {
			for (int a = 18; a >=0; a--) {
				if (Main.b[a][i] == 1) { // 돌이 검정색이 되면 연속점 1증가
					countConsecutive++;
					resultArr[a][i]=Integer.MIN_VALUE;
				}
				else if (Main.bwr[a][i] == 0 && countConsecutive > 0) { // 비어있을 경우 연속된 돌이 있을 때,
					openEnds++;
					score += connect6ShapeScore(countConsecutive, openEnds, 0); // currentTurn is black
					resultArr[a][i]+=score;
					countConsecutive = 0;
					openEnds = 1;
				} else if (Main.bwr[a][i] == 0) // 비어있을 경우
					openEnds = 1;
				else if (countConsecutive > 0) {
					/*
					 * score+= connect6ShapeScore(countConsecutive, openEnds, 0); // currentTurn is
					 * black if (score > max) { max=score; n.p.x = a; n.p.y = i; }
					 */
					//countConsecutive = 0;
					//openEnds = 0;
					score += connect6ShapeScore(countConsecutive, openEnds, 0); // currentTurn is black
					resultArr[a][i]+=score;
					countConsecutive = 0;
					openEnds = 0;

				} else
					openEnds = 0;
			}
			/*if (countConsecutive > 0) {
				score = connect6ShapeScore(countConsecutive, openEnds, 0); // currentTurn is black
				resultArr[a][i]+=score;
			}*/
			countConsecutive = 0;
			openEnds = 0;
		}
		
		return Main.bwr;
	}
	//computer = white인 경우 세로 검사
	public int[][] analyzeVerticalSetsForBlack(int current_turn) {
		double score = 0;
		int countConsecutive = 0;
		int openEnds = 0;

		for (int a = 0; a < 19; a++) {
			for (int i = 0; i < 19; i++) {
				if (Main.b[a][i] == 1) { // 돌이 검정색이 되면 연속점 1증가
					countConsecutive++;
					resultArr[a][i]=Integer.MIN_VALUE;
				}
				else if (Main.bwr[a][i] == 0 && countConsecutive > 0) {
					openEnds++;
					score += connect6ShapeScore(countConsecutive, openEnds, 0); // currentTurn is black
					resultArr[a][i]+=score;
					countConsecutive = 0;
					openEnds = 1;
				} else if (Main.bwr[a][i] == 0)
					openEnds = 1;
				else if (countConsecutive > 0) {
					/*
					 * score += connect6ShapeScore(countConsecutive, openEnds, 0); // currentTurn is
					 * black if (score > max) { max=score; n.p.x = a; n.p.y = i; }
					 */
					//countConsecutive = 0;
					//openEnds = 0;
					score += connect6ShapeScore(countConsecutive, openEnds, 0); // currentTurn is black
					resultArr[a][i]+=score;
					countConsecutive = 0;
					openEnds = 0;
				} else
					openEnds = 0;
			}
			/*if (countConsecutive > 0) {
				score = connect6ShapeScore(countConsecutive, openEnds, 0); // currentTurn is black
				resultArr[a][i]+=score;
			}*/
			countConsecutive = 0;
			openEnds = 0;
		}
		for (int a = 0; a < 19; a++) {
			for (int i = 18; i >=0; i--) {
				if (Main.b[a][i] == 1) { // 돌이 검정색이 되면 연속점 1증가
					countConsecutive++;
					resultArr[a][i]=Integer.MIN_VALUE;
				}
				else if (Main.bwr[a][i] == 0 && countConsecutive > 0) {
					openEnds++;
					score += connect6ShapeScore(countConsecutive, openEnds, 0); // currentTurn is black
					resultArr[a][i]+=score;
					countConsecutive = 0;
					openEnds = 1;
				} else if (Main.bwr[a][i] == 0)
					openEnds = 1;
				else if (countConsecutive > 0) {
					/*
					 * score += connect6ShapeScore(countConsecutive, openEnds, 0); // currentTurn is
					 * black if (score > max) { max=score; n.p.x = a; n.p.y = i; }
					 */
					//countConsecutive = 0;
					//openEnds = 0;
					score += connect6ShapeScore(countConsecutive, openEnds, 0); // currentTurn is black
					resultArr[a][i]+=score;
					countConsecutive = 0;
					openEnds = 0;
				} else
					openEnds = 0;
			}
			/*if (countConsecutive > 0) {
				score = connect6ShapeScore(countConsecutive, openEnds, 0); // currentTurn is black
				//resultArr[a][i]+=score;
			}*/
			countConsecutive = 0;
			openEnds = 0;
		}
		
		return Main.bwr;
	}
	
	//computer = white인 경우 대각선 검사
	public int[][] analyzeLeftDiagonalSetsForBlack(int current_turn) {
		double score = 0;
		int countConsecutive = 0;
		int openEnds = 0;
		int n = 19;
		
		for (int i = 0; i <= 2 * n - 2; i++) {
			int lb, ub;
			if (19<= i)
				lb = -(2 * n - 2 - i);
			else
				lb = -i;
			ub = -lb;

			for (int diff = lb; diff <= ub; diff += 2) {
				int x = (i + diff) >> 1;
				int y = i - x;
				//System.out.println("a "+a+" i "+i);
				if (Main.b[x][y] == 1) { // 돌이 검정색이 되면 연속점 1증가
					countConsecutive++;
					resultArr[x][y]=Integer.MIN_VALUE;
				}
				else if (Main.bwr[x][y] == 0 && countConsecutive > 0) { // 연속점이 빈곳을 만나서 끝났을 경우
					openEnds++;
					score += connect6ShapeScore(countConsecutive, openEnds, 0); // currentTurn is black
					resultArr[x][y]+=score;
					countConsecutive = 0;
					openEnds = 1;
				} else if (Main.bwr[x][y] == 0) // 빈 점이 그냥 등장할 경우
					openEnds = 1;
				else if (countConsecutive > 0) { // 연속점이 다른 돌에 만나서 끝났을 경우
					score+= connect6ShapeScore(countConsecutive, openEnds, 0); // currentTurn is black
					resultArr[x][y]+=score;
					countConsecutive = 0;
					openEnds = 0;
				} else
					openEnds = 0;
			}
			if (countConsecutive > 0) // 연속점이 벽에 만나서 끝났을 경우
				score = connect6ShapeScore(countConsecutive, openEnds, 0); // currentTurn is black
			//resultArr[x][y]+=score;
			countConsecutive = 0;
			openEnds = 0;
		}

		
		return resultArr;
	}

	//computer = white인 경우 대각선 검사
	public int[][] analyzeRightDiagonalSetsForBlack(int current_turn) {
		double score = 0;
		int countConsecutive = 0;
		int openEnds = 0;
		int n = 19;

		for (int i = 0; i <= 2 * n - 2; i++) {
			int lb, ub;
			if (19 <= i)
				lb = -(2 * n - 2 - i);
			else
				lb = -i;
			ub = -lb;

			for (int diff = lb; diff <= ub; diff += 2) {
				int x = (i + diff) >> 1;
				int y = 18 - i + x;

				if (Main.b[x][y] == 1){ // 돌이 검정색이 되면 연속점 1증가
					countConsecutive++;
					resultArr[x][y]=Integer.MIN_VALUE;
				}
				else if (Main.bwr[x][y] == 0 && countConsecutive > 0) { // 연속점이 다른 돌에 만나서 끝났을 경우
					openEnds++;
					score += connect6ShapeScore(countConsecutive, openEnds, 0); // currentTurn is black
					resultArr[x][y]+=score;
					countConsecutive = 0;
					openEnds = 1;
				} else if (Main.bwr[x][y] == 0) // 빈 점이 그냥 등장할 경우
					openEnds = 1;
				else if (countConsecutive > 0) { // 연속점이 다른 돌에 만나서 끝났을 경우
					score+= connect6ShapeScore(countConsecutive, openEnds, 0); // currentTurn is black
					resultArr[x][y]+=score;
					countConsecutive = 0;
					openEnds = 0;
				} else
					openEnds = 0;
			}
			if (countConsecutive > 0) // 연속점이 벽에 만나서 끝났을 경우
				score += connect6ShapeScore(countConsecutive, openEnds, 0); // currentTurn is black
			//resultArr[x][y]+=score;
			countConsecutive = 0;
			openEnds = 0;
		}

		for (int i = 0; i <= 2 * n - 2; i++) {
			int lb, ub;
			if (19 <= i)
				lb = -(2 * n - 2 - i);
			else
				lb = -i;
			ub = -lb;

			for (int diff = lb; diff <= ub; diff += 2) {
				int x = (i + diff) >> 1;
				int y = 18 - i + x;

				if (Main.b[x][y] == 1){ // 돌이 검정색이 되면 연속점 1증가
					countConsecutive++;
					resultArr[x][y]=Integer.MIN_VALUE;
				}
				else if (Main.bwr[x][y] == 0 && countConsecutive > 0) { // 연속점이 다른 돌에 만나서 끝났을 경우
					openEnds++;
					score += connect6ShapeScore(countConsecutive, openEnds, 0); // currentTurn is black
					resultArr[x][y]+=score;
					countConsecutive = 0;
					openEnds = 1;
				} else if (Main.bwr[x][y] == 0) // 빈 점이 그냥 등장할 경우
					openEnds = 1;
				else if (countConsecutive > 0) { // 연속점이 다른 돌에 만나서 끝났을 경우
					score+= connect6ShapeScore(countConsecutive, openEnds, 0); // currentTurn is black
					resultArr[x][y]+=score;
					countConsecutive = 0;
					openEnds = 0;
				} else
					openEnds = 0;
			}
			if (countConsecutive > 0) // 연속점이 벽에 만나서 끝났을 경우
				score += connect6ShapeScore(countConsecutive, openEnds,0); // currentTurn is black
			//resultArr[x][y]+=score;
			countConsecutive = 0;
			openEnds = 0;
		}
		return resultArr;
	}

	// computer = white 일때, 체크 
	public void compareBlack() {
		analyzeHorizontalSetsForBlack(Main.BLACK);
		analyzeVerticalSetsForBlack(Main.BLACK);
		analyzeLeftDiagonalSetsForBlack(Main.BLACK);
		analyzeRightDiagonalSetsForBlack(Main.BLACK);

		int max = resultArr[0][0];
		aa =new Point();
		for (int i = 0; i < 19; i++) {
			for (int j = 0; j < 19; j++) {
				if((resultArr[i][j]>max)&&Main.bwr[i][j]==0) {
					max=resultArr[i][j];
					aa.x=i;
					aa.y=j;
				}
			}
		}
	}

	//computer = BLACK인 경우 가로 검사
	public int[][] analyzeHorizontalSetsForWhite(int current_turn) {
		double score = 0;
		int countConsecutive = 0;
		int openEnds = 0;

		for (int i = 0; i < 19; i++) {
			for (int a = 0; a < 19; a++) {
				if (Main.w[a][i] == 1) { // 돌이 검정색이 되면 연속점 1증가
					countConsecutive++;
					resultArr[a][i]=Integer.MIN_VALUE;
				}
				else if (Main.bwr[a][i] == 0 && countConsecutive > 0) { // 비어있을 경우 연속된 돌이 있을 때,
					openEnds++;
					score += connect6ShapeScore(countConsecutive, openEnds, 1); // currentTurn is black
					resultArr[a][i]+=score;
					countConsecutive = 0;
					openEnds = 1;
				} else if (Main.bwr[a][i] == 0) // 비어있을 경우
					openEnds = 1;
				else if (countConsecutive > 0) {
					/*
					 * score+= connect6ShapeScore(countConsecutive, openEnds, 0); // currentTurn is
					 * black if (score > max) { max=score; n.p.x = a; n.p.y = i; }
					 */
					//countConsecutive = 0;
					//openEnds = 0;
					score += connect6ShapeScore(countConsecutive, openEnds, 1); // currentTurn is black
					resultArr[a][i]+=score;
					countConsecutive = 0;
					openEnds = 0;

				} else
					openEnds = 0;
			}
			/*if (countConsecutive > 0) {
				score = connect6ShapeScore(countConsecutive, openEnds, 0); // currentTurn is black
				resultArr[a][i]+=score;
			}*/

			countConsecutive = 0;
			openEnds = 0;
		}
		for (int i = 0; i < 19; i++) {
			for (int a = 18; a >=0; a--) {
				if (Main.w[a][i] == 1) { // 돌이 검정색이 되면 연속점 1증가
					countConsecutive++;
					resultArr[a][i]=Integer.MIN_VALUE;
				}
				else if (Main.bwr[a][i] == 0 && countConsecutive > 0) { // 비어있을 경우 연속된 돌이 있을 때,
					openEnds++;
					score += connect6ShapeScore(countConsecutive, openEnds, 1); // currentTurn is black
					resultArr[a][i]+=score;
					countConsecutive = 0;
					openEnds = 1;
				} else if (Main.bwr[a][i] == 0) // 비어있을 경우
					openEnds = 1;
				else if (countConsecutive > 0) {
					/*
					 * score+= connect6ShapeScore(countConsecutive, openEnds, 0); // currentTurn is
					 * black if (score > max) { max=score; n.p.x = a; n.p.y = i; }
					 */
					//countConsecutive = 0;
					//openEnds = 0;
					score += connect6ShapeScore(countConsecutive, openEnds, 1); // currentTurn is black
					resultArr[a][i]+=score;
					countConsecutive = 0;
					openEnds = 0;

				} else
					openEnds = 0;
			}
			/*if (countConsecutive > 0) {
				score = connect6ShapeScore(countConsecutive, openEnds, 0); // currentTurn is black
				resultArr[a][i]+=score;
			}*/
			countConsecutive = 0;
			openEnds = 0;
		}
		
		return Main.bwr;
	}
	//computer = Black인 경우 세로 검사
	public int[][] analyzeVerticalSetsForWhite(int current_turn) {
		double score = 0;
		int countConsecutive = 0;
		int openEnds = 0;

		for (int a = 0; a < 19; a++) {
			for (int i = 0; i < 19; i++) {
				if (Main.w[a][i] == 1) { // 돌이 검정색이 되면 연속점 1증가
					countConsecutive++;
					resultArr[a][i]=Integer.MIN_VALUE;
				}
				else if (Main.bwr[a][i] == 0 && countConsecutive > 0) {
					openEnds++;
					score += connect6ShapeScore(countConsecutive, openEnds, 1); // currentTurn is black
					resultArr[a][i]+=score;
					countConsecutive = 0;
					openEnds = 1;
				} else if (Main.bwr[a][i] == 0)
					openEnds = 1;
				else if (countConsecutive > 0) {
					/*
					 * score += connect6ShapeScore(countConsecutive, openEnds, 0); // currentTurn is
					 * black if (score > max) { max=score; n.p.x = a; n.p.y = i; }
					 */
					//countConsecutive = 0;
					//openEnds = 0;
					score += connect6ShapeScore(countConsecutive, openEnds, 1); // currentTurn is black
					resultArr[a][i]+=score;
					countConsecutive = 0;
					openEnds = 0;
				} else
					openEnds = 0;
			}
			/*if (countConsecutive > 0) {
				score = connect6ShapeScore(countConsecutive, openEnds, 0); // currentTurn is black
				resultArr[a][i]+=score;
			}*/
			countConsecutive = 0;
			openEnds = 0;
		}
		for (int a = 0; a < 19; a++) {
			for (int i = 18; i >=0; i--) {
				if (Main.w[a][i] == 1) { // 돌이 검정색이 되면 연속점 1증가
					countConsecutive++;
					resultArr[a][i]=Integer.MIN_VALUE;
				}
				else if (Main.bwr[a][i] == 0 && countConsecutive > 0) {
					openEnds++;
					score += connect6ShapeScore(countConsecutive, openEnds, 1); // currentTurn is black
					resultArr[a][i]+=score;
					countConsecutive = 0;
					openEnds = 1;
				} else if (Main.bwr[a][i] == 0)
					openEnds = 1;
				else if (countConsecutive > 0) {
					/*
					 * score += connect6ShapeScore(countConsecutive, openEnds, 0); // currentTurn is
					 * black if (score > max) { max=score; n.p.x = a; n.p.y = i; }
					 */
					//countConsecutive = 0;
					//openEnds = 0;
					score += connect6ShapeScore(countConsecutive, openEnds, 1); // currentTurn is black
					resultArr[a][i]+=score;
					countConsecutive = 0;
					openEnds = 0;
				} else
					openEnds = 0;
			}
			/*if (countConsecutive > 0) {
				score = connect6ShapeScore(countConsecutive, openEnds, 0); // currentTurn is black
				//resultArr[a][i]+=score;
			}*/
			countConsecutive = 0;
			openEnds = 0;
		}
		
		return Main.bwr;
	}
	
	//computer = Black인 경우 대각선 검사
	public int[][] analyzeLeftDiagonalSetsForWhite(int current_turn) {
		double score = 0;
		int countConsecutive = 0;
		int openEnds = 0;
		int n = 19;
		
		for (int i = 0; i <= 2 * n - 2; i++) {
			int lb, ub;
			if (19<= i)
				lb = -(2 * n - 2 - i);
			else
				lb = -i;
			ub = -lb;

			for (int diff = lb; diff <= ub; diff += 2) {
				int x = (i + diff) >> 1;
				int y = i - x;
				//System.out.println("a "+a+" i "+i);
				if (Main.w[x][y] == 1) { // 돌이 검정색이 되면 연속점 1증가
					countConsecutive++;
					resultArr[x][y]=Integer.MIN_VALUE;
				}
				else if (Main.bwr[x][y] == 0 && countConsecutive > 0) { // 연속점이 빈곳을 만나서 끝났을 경우
					openEnds++;
					score += connect6ShapeScore(countConsecutive, openEnds, 1); // currentTurn is black
					resultArr[x][y]+=score;
					countConsecutive = 0;
					openEnds = 1;
				} else if (Main.bwr[x][y] == 0) // 빈 점이 그냥 등장할 경우
					openEnds = 1;
				else if (countConsecutive > 0) { // 연속점이 다른 돌에 만나서 끝났을 경우
					score+= connect6ShapeScore(countConsecutive, openEnds, 1); // currentTurn is black
					resultArr[x][y]+=score;
					countConsecutive = 0;
					openEnds = 0;
				} else
					openEnds = 0;
			}
			if (countConsecutive > 0) // 연속점이 벽에 만나서 끝났을 경우
				score = connect6ShapeScore(countConsecutive, openEnds, 0); // currentTurn is black
			//resultArr[x][y]+=score;
			countConsecutive = 0;
			openEnds = 0;
		}

		
		return resultArr;
	}

	//computer = Black인 경우 대각선 검사
	public int[][] analyzeRightDiagonalSetsForWhite(int current_turn) {
		double score = 0;
		int countConsecutive = 0;
		int openEnds = 0;
		int n = 19;

		for (int i = 0; i <= 2 * n - 2; i++) {
			int lb, ub;
			if (19 <= i)
				lb = -(2 * n - 2 - i);
			else
				lb = -i;
			ub = -lb;

			for (int diff = lb; diff <= ub; diff += 2) {
				int x = (i + diff) >> 1;
				int y = 18 - i + x;

				if (Main.w[x][y] == 1){ // 돌이 검정색이 되면 연속점 1증가
					countConsecutive++;
					resultArr[x][y]=Integer.MIN_VALUE;
				}
				else if (Main.bwr[x][y] == 0 && countConsecutive > 0) { // 연속점이 다른 돌에 만나서 끝났을 경우
					openEnds++;
					score += connect6ShapeScore(countConsecutive, openEnds, 1); // currentTurn is black
					resultArr[x][y]+=score;
					countConsecutive = 0;
					openEnds = 1;
				} else if (Main.bwr[x][y] == 0) // 빈 점이 그냥 등장할 경우
					openEnds = 1;
				else if (countConsecutive > 0) { // 연속점이 다른 돌에 만나서 끝났을 경우
					score+= connect6ShapeScore(countConsecutive, openEnds, 1); // currentTurn is black
					resultArr[x][y]+=score;
					countConsecutive = 0;
					openEnds = 0;
				} else
					openEnds = 0;
			}
			if (countConsecutive > 0) // 연속점이 벽에 만나서 끝났을 경우
				score += connect6ShapeScore(countConsecutive, openEnds, 1); // currentTurn is black
			//resultArr[x][y]+=score;
			countConsecutive = 0;
			openEnds = 0;
		}

		for (int i = 0; i <= 2 * n - 2; i++) {
			int lb, ub;
			if (19 <= i)
				lb = -(2 * n - 2 - i);
			else
				lb = -i;
			ub = -lb;

			for (int diff = lb; diff <= ub; diff += 2) {
				int x = (i + diff) >> 1;
				int y = 18 - i + x;

				if (Main.w[x][y] == 1){ // 돌이 검정색이 되면 연속점 1증가
					countConsecutive++;
					resultArr[x][y]=Integer.MIN_VALUE;
				}
				else if (Main.bwr[x][y] == 0 && countConsecutive > 0) { // 연속점이 다른 돌에 만나서 끝났을 경우
					openEnds++;
					score += connect6ShapeScore(countConsecutive, openEnds, 1); // currentTurn is black
					resultArr[x][y]+=score;
					countConsecutive = 0;
					openEnds = 1;
				} else if (Main.bwr[x][y] == 0) // 빈 점이 그냥 등장할 경우
					openEnds = 1;
				else if (countConsecutive > 0) { // 연속점이 다른 돌에 만나서 끝났을 경우
					score+= connect6ShapeScore(countConsecutive, openEnds, 1); // currentTurn is black
					resultArr[x][y]+=score;
					countConsecutive = 0;
					openEnds = 0;
				} else
					openEnds = 0;
			}
			if (countConsecutive > 0) // 연속점이 벽에 만나서 끝났을 경우
				score += connect6ShapeScore(countConsecutive, openEnds, 1); // currentTurn is black
			//resultArr[x][y]+=score;
			countConsecutive = 0;
			openEnds = 0;
		}
		return resultArr;
	}

	// computer = BLACK 일때, 체크 
	public void compareWhite() {
		analyzeHorizontalSetsForBlack(Main.WHITE);
		analyzeVerticalSetsForBlack(Main.WHITE);
		analyzeLeftDiagonalSetsForBlack(Main.WHITE);
		analyzeRightDiagonalSetsForBlack(Main.WHITE);

		int max = resultArr[0][0];
		aa =new Point();
		for (int i = 0; i < 19; i++) {
			for (int j = 0; j < 19; j++) {
				if((resultArr[i][j]>max)&&Main.bwr[i][j]==0) {
					max=resultArr[i][j];
					aa.x=i;
					aa.y=j;
				}
			}
		}
	}
	
	public void firstBlack() { //computer==black 일 떄 , 첫 번째 위치를 지정하는 함수 
		aa =new Point();
		int min=50;
		int count;
		
		for (int i = 5; i < 15; i=i+4) {
			for (int j = 5; j < 15; j=j+4) {
				count=0;
				for(int k=i-3;k<=i+3;k++) {
					for(int l=j-3;l<=j+3;l++) {
						if(Main.bwr[k][l]==1) {
							count++;
						}
					}
				}
				if(count<min) {
					min=count;
					aa.x=i;aa.y=j;
				}
			}
		}
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		if (Main.setting == false && Main.count == 0 && Main.user == Main.WHITE) {
			//compareBlack(); 
			firstBlack();
			comBlack();
			repaint();
			Main.user = Main.WHITE;
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
	}

}
