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
	boolean p=true;
	int nCount;
	int tX, tY;
	
	AudioInputStream inAudio;
	Clip clip1;
	int[][] resultB=new int[19][19]; // 흑돌 가중치 
	int[][] resultW=new int[19][19]; //백돌 가중치
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
						//착수
						Main.bwr[i][j] = 1;
						Sounds();
						Point t = new Point();
						t.x = i;
						t.y = j;
						Main.bwList.add(t);
						
						
						if (Main.setting == true) { //적돌 착수 
							//적돌 착수 (setting)
							Main.wLabel.setVisible(true);
							Main.bLabel.setVisible(true);
							Main.type = 'r';
							Main.r[i][j] = 1;
							
						} else { //흑&백돌 착수 
							
							if (Main.user == Main.BLACK) { // user == black && computer == white 
								if (Main.count == 0) {
									Main.wLabel.setVisible(false);
									Main.bLabel.setVisible(true);
									
									//black turn (user turn)
									Main.user = Main.BLACK;
									Main.b[i][j] = 1;
									Main.count++; //흑돌1개 착수
									System.out.println("흑돌 1개 착수 후 "+ Main.count);
									
									
									// 백돌 그리기 : White turn (computer turn)  백돌 2개 착수 
									compareBlackFirst();
									comWhite();
									repaint();
									compareBlackFirst();
									comWhite();
									repaint();
									System.out.println("백돌 2개 착수 후 "+ Main.count);
									
									//Main.count++;
									Main.user = Main.BLACK;
									
								} else if (Main.count % 4 == 0 || Main.count % 4 == 3) {
									// BLACK turn (user turn)
									//compareBlack();<- 왜 있는지 모르겠음
									
									Main.wLabel.setVisible(false);
									Main.bLabel.setVisible(true);
									Main.user = Main.BLACK;
									Main.b[i][j] = 1; //흑돌1개 착수
									repaint();
									
									if (Main.count % 4 == 0) {//b->w로 turn 넘어갈 떄, 
										Main.count++; 
										System.out.println("b->w로 turn 넘어갈 떄, "+ Main.count);
										//백돌 출력
										Main.user = Main.WHITE;
										p=true;
										compareWhite2(); //6개가 되는 백돌이 있는지 확인-> 순서 바뀜 
										if(p==false) { //공격하지 않을 경우 
											compareBlack2(); //흑돌 위치 확인
										}
										comWhite();
										repaint();
										
										Main.user = Main.WHITE;
										p=true;
										compareWhite2();
										if(p==false) {
											compareBlack2();
										}
										comWhite();
										repaint();
									
										//Main.count++;
										System.out.println("b->w로 turn 넘어갈 떄,백돌 2개 착수 후  "+ Main.count);
										Main.user = Main.BLACK;
										
									} else if (Main.count % 4 == 3) {//b->b
										Main.count++;
										System.out.println("b->b"+Main.count);
									}
									 if(winCheck(Main.b,i,j)) { 
										repaint();
										Object[] options = { "재시작", "종료" };
										int n = JOptionPane.showOptionDialog(Main.board, "BLACK WIN!", "",
												JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
												options, options[0]);
										if (n == 0) {
											Main.setting = false;
											clearArr();// 배열 클리어하기
										} else {
											Main.setting = false;
											clearArr();// 배열 클리어하기
											quit();
										}
									 
									  }
								}
								repaint();
								//for timer
								/*if (Main.count == 0) {
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
								}*/
								
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
									p=true;
									compareWhite2();
									if(p==false) {
										compareBlack2();
									}
									comBlack();
									repaint();
									p=true;
									compareWhite2();  //compareBlack() 함수 사용
									if(p==false) {
										compareBlack2(); //compareWhite() 함수 사용
									}
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
							/*
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
							}*/
						}

					}

					repaint();
				}
			}
		}
		
		//가중치 확인
		/*for(int i=0;i<19;i++) {
			for(int j=0;j<19;j++) {
				System.out.print(resultB[j][i]+ "\t B");
			}
			System.out.println();
		}
		System.out.println();

		for(int i=0;i<19;i++) {
			for(int j=0;j<19;j++) {
				System.out.print(resultW[j][i]+ "\t W");
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
				resultB[i][j]=0;
				resultW[i][j]=0;
			}
		}
		Main.bwList.clear();
		Main.count = 0;
		//Main.timer.stop();
		//Main.timerNumber.setText("0");
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
	/*public double connect6ShapeScore(int consecutive, int openEnds, int currentTurn) { // shape에 따른 가중치 부여
		if (openEnds == 0 && consecutive <= 6)
			return 0;
		switch (consecutive) {
		case 6: 
			switch (openEnds) {
			case 2:
				if (currentTurn == Main.user) { // this.currentTurn
					return 200000000; //공격
				}
				return 50;
			}
			
		case 5:
			switch (openEnds) {
			case 1:
				if (currentTurn == Main.user) { // this.currentTurn
					return 200000000; //공격
				}
				return 50;
				
			case 2:
				if (currentTurn == Main.user)
					return 1000000; //공격
				return 500000;
			}
			
		case 4:
			switch (openEnds) {
			case 1:
				if (currentTurn == Main.user){ // this.currentTurn				
					return 200000000; //공격
				}
				return 50;
			case 2:
				if (currentTurn == Main.user){ // this.currentTurn
					return 200000000; //공격
				}
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
			return 0; // default
		}
	}
*/
	//가중치
		public double connect6ShapeScoreBlack(int consecutive, int openEnds, int con) { // shape에 따른 가중치 부여
			if (openEnds == 0 && consecutive <= 6)
				return 0;
			switch (consecutive) {
			case 6: 
				switch (openEnds) {
				case 2:
					if (Main.count%4==2||Main.count%4==1) { // this.currentTurn
						return 200000000; //공격
					}
					return 50;
				}
				
			case 5:
				switch (openEnds) {
				case 1:
					if (Main.count%4==2||Main.count%4==1) { // this.currentTurn
						return 200000000; //공격
					}
					return 100000000; //공격
					
				case 2:
					if (Main.count%4==2||Main.count%4==1)
						return 200000000; //공격
					return 100000000; //공격
				}
				
			case 4:
				switch (openEnds) {
				case 0: 
					if (Main.count%4==2||Main.count%4==1)
						return 10;
					return 5;	
				case 1:
					if (Main.count%4==2||Main.count%4==1){ // this.currentTurn				
						return 200000000; //공격
					}
					return 50;
				case 2:
					if (Main.count%4==2||Main.count%4==1){ // this.currentTurn
						return 200000000; //공격
					}
					return 500000;
				}
				
			case 3:
				switch (openEnds) {
				case 1:
					if (Main.count%4==2||Main.count%4==1)
						return 7;
					return 5;
				case 2:
					if (Main.count%4==2||Main.count%4==1)
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
				return 0; // default
			}
		}
	//computer = white인 경우 가로 검사
	public int[][] analyzeHorizontalSetsForBlack() {
		double score = 0; //가중치 
		int countConsecutive = 0; //연속된 돌의 수 
		int openEnds = 0; 

		for (int i = 0; i < 19; i++) {
			for (int a = 0; a < 19; a++) {
				if (Main.b[a][i] == 1) { // 돌이 검정색이 되면 연속점 1증가
					countConsecutive++;
					if(countConsecutive==1) {
						if((a-1>0)&&(Main.bwr[a-1][i] == 0)) {
							openEnds=1;
						}else {
							openEnds=0;
						}
					}
					
					resultB[a][i]=Integer.MIN_VALUE;
				}
				
				
				else if (Main.bwr[a][i] == 0 && countConsecutive > 0) { // 비어있을 경우 연속된 돌이 있을 때,
					openEnds++;
					if(a+2<19&&(openEnds==2)&&(countConsecutive==4)&&(Main.bwr[a+1][i] ==1)&&(Main.b[a+1][i] != 1)) {
						//연속 4개 +2 위치가 다른 돌로 막혀있는 경우 
						score= connect6ShapeScoreBlack(countConsecutive, 0, 0); // currentTurn is black
						resultB[a][i]+=score;
					}else {
						score= connect6ShapeScoreBlack(countConsecutive, openEnds, 0); // currentTurn is black
						resultB[a][i]+=score;
					}
					
					
					//한자리가 비어있고 연속된 경우
					if(a+1<19&&Main.bwr[a+1][i]==1&&Main.b[a+1][i]==1) {
						nCount=0;
						tX=a+1; //범위 설정
						if(tX<19) {
							while(tX<19&&Main.bwr[tX][i]==1&&Main.b[tX][i]==1) {
								nCount++;
								tX++;
							}
							if(tX+1<19&&(nCount+countConsecutive==3)&&(Main.bwr[tX][i]==0)&&(Main.bwr[tX+1][i]==1)&&(Main.b[tX+1][i]==1)) {
								//3+0+1인 경우 
								score=connect6ShapeScoreBlack(4, openEnds, 1); // currentTurn is black
								resultB[a][i]+=score;	
							}
							if((nCount+countConsecutive==5)||(nCount+countConsecutive==4)) {
								if((nCount+countConsecutive==4)&&Main.bwr[tX][i]==1&&Main.b[tX][i]!=1) {
									score= connect6ShapeScoreBlack(4, 0, 0); // currentTurn is black
									resultB[a][i]+=score;
								}
								score=connect6ShapeScoreBlack(countConsecutive+nCount, openEnds, 1); // currentTurn is black
								resultB[a][i]+=score;
							}
						}
					}
					if((a+2<19)&&(Main.bwr[a+1][i]==0&&Main.b[a+1][i]==0)&&(Main.bwr[a+2][i]==1&&Main.b[a+2][i]==1)) {
						//두자리가 비어있고 연속된 경우
						nCount=0;
						tX=a+2;
						if(tX<19) {
							while(tX<19&&(Main.bwr[a+1][i] == 0)&& Main.bwr[tX][i]==1&&Main.b[tX][i]==1) {
								nCount++;
								tX++;
							}
							if(nCount+countConsecutive==4) {
								score=connect6ShapeScoreBlack(6, openEnds, 1); // currentTurn is black
								resultB[a][i]+=score;
							}
						}
					}
					//resultB[a][i]+=score;
					countConsecutive = 0;
					openEnds = 1;
				} else if (Main.bwr[a][i] == 0) {// 비어있을 경우
					openEnds = 1;
				} else if (countConsecutive > 0) {
					/*
					 * score+= connect6ShapeScore(countConsecutive, openEnds, 0); // currentTurn is
					 * black if (score > max) { max=score; n.p.x = a; n.p.y = i; }
					 */
					//countConsecutive = 0;
					//openEnds = 0;
					score = connect6ShapeScoreBlack(countConsecutive, openEnds, 0); // currentTurn is black
					resultB[a][i]+=score;
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
					if(countConsecutive==1) {
						if((a+1<19)&&(Main.bwr[a+1][i] == 0)) {
							openEnds=1;
						}else {
							openEnds=0;
						}
					}
					resultB[a][i]=Integer.MIN_VALUE;
				}
				else if (Main.bwr[a][i] == 0 && countConsecutive > 0) { // 비어있을 경우 연속된 돌이 있을 때,
					openEnds++;
					score= connect6ShapeScoreBlack(countConsecutive, openEnds, 0); // currentTurn is black
					resultB[a][i]+=score;
					
					
					//한자리가 비어있고 연속된 경우
					if(a-1>0&&Main.bwr[a-1][i]==1&&Main.b[a-1][i]==1) {
						nCount=0;
						tX=a-1;  //범위 설정
						if(tX>0) {
							while(tX>0&&Main.bwr[tX][i]==1&&Main.b[tX][i]==1) {
								nCount++;
								tX--;
							}
							if(tX-1>0&&(nCount+countConsecutive==3)&&(Main.bwr[tX][i]==0)&&(Main.bwr[tX-1][i]==1)&&(Main.b[tX-1][i]==1)) {
								score=connect6ShapeScoreBlack(4, openEnds, 1); // currentTurn is black
								resultB[a][i]+=score;	
							}
							if((nCount+countConsecutive==5)||(nCount+countConsecutive==4)) {
								score=connect6ShapeScoreBlack(countConsecutive+nCount, openEnds, 1); // currentTurn is black
								resultB[a][i]+=score;
							}
						}
					}
					
					
					if(a-2>0&&(Main.bwr[a-1][i]==0&&Main.b[a-1][i]==0)&&(Main.bwr[a-2][i]==1&&Main.b[a-2][i]==1)) {
						//두자리가 비어있고 연속된 경우
						nCount=0;
						tX=a-2;
						if(tX>1) {
							while(tX>0&&(Main.bwr[a-1][i] == 0)&& Main.bwr[tX][i]==1&&Main.b[tX][i]==1) {
								nCount++;
								tX--;
							}
							if(nCount+countConsecutive==4) {
								score=connect6ShapeScoreBlack(6, openEnds, 1); // currentTurn is black
								resultB[a][i]+=score;
							}
						}
					}
					
					resultB[a][i]+=score;
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
					score = connect6ShapeScoreBlack(countConsecutive, openEnds, 0); // currentTurn is black
					resultB[a][i]+=score;
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
	public int[][] analyzeVerticalSetsForBlack() {
		double score = 0;
		int countConsecutive = 0;
		int openEnds = 0;

		for (int a = 0; a < 19; a++) {
			for (int i = 0; i < 19; i++) {
				if (Main.b[a][i] == 1) { // 돌이 검정색이 되면 연속점 1증가
					countConsecutive++;
					if(countConsecutive==1) {
						if((i-1>0)&&(Main.bwr[a][i-1] == 0)) {
							openEnds=1;
						}else {
							openEnds=0;
						}
					}
					
					resultB[a][i]=Integer.MIN_VALUE;
				}
				else if (Main.bwr[a][i] == 0 && countConsecutive > 0) {
					openEnds++;
					score = connect6ShapeScoreBlack(countConsecutive, openEnds, 0); // currentTurn is black
					resultB[a][i]+=score;
					
					//한자리가 비어있고 연속된 경우
					if(i+1<19&&Main.bwr[a][i+1]==1&&Main.b[a][i+1]==1) {
						nCount=0;
						tX=i+1; //범위 설정
						if(tX<19) {
							while(tX<19&&Main.bwr[a][tX]==1&&Main.b[a][tX]==1) {
								nCount++;
								tX++;
							}
							if(tX+1<19&&(nCount+countConsecutive==3)&&(Main.bwr[a][tX]==0)&&(Main.bwr[a][tX+1]==1)&&(Main.b[a][tX+1]==1)) {
								score=connect6ShapeScoreBlack(4, openEnds, 1); // currentTurn is black
								resultB[a][i]+=score;	
							}
							if((nCount+countConsecutive==5)||(nCount+countConsecutive==4)) {
								score=connect6ShapeScoreBlack(countConsecutive+nCount, openEnds, 1); // currentTurn is black
								resultB[a][i]+=score;
								
							}
						}
					}
					if(i+2<19&&(Main.bwr[a][i+1]==0&&Main.b[a][i+1]==0)&&(Main.bwr[a][i+2]==1&&Main.b[a][i+2]==1)) {
						//두자리가 비어있고 연속된 경우
						nCount=0;
						tX=i+2;
						if(tX<19) {
							while(tX<19&&(Main.bwr[a][i+1] == 0)&& Main.bwr[a][tX]==1&&Main.b[a][tX]==1) {
								nCount++;
								tX++;
							}
							if(nCount+countConsecutive==4) {
								score=connect6ShapeScoreBlack(6, openEnds, 1); // currentTurn is black
								resultB[a][i]+=score;
							}
						}
					}
					
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
					score = connect6ShapeScoreBlack(countConsecutive, openEnds, 0); // currentTurn is black
					resultB[a][i]+=score;
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
					if(countConsecutive==1) {
						if((i+1<19)&&(Main.bwr[a][i+1] == 0)) {
							openEnds=1;
						}else {
							openEnds=0;
						}
					}
					
					resultB[a][i]=Integer.MIN_VALUE;
				}
				else if (Main.bwr[a][i] == 0 && countConsecutive > 0) {
					openEnds++;
					score= connect6ShapeScoreBlack(countConsecutive, openEnds, 0); // currentTurn is black
					resultB[a][i]+=score;
					
					//한자리가 비어있고 연속된 경우
					if((i-1>0)&&Main.bwr[a][i-1]==1&&Main.b[a][i-1]==1) {
						nCount=0;
						tX=i-1;; //범위 설정
						if(tX>0) {
							while(tX>0&&Main.bwr[a][tX]==1&&Main.b[a][tX]==1) {
								nCount++;
								tX--;
							}
							if(tX-1>0&&(nCount+countConsecutive==3)&&(Main.bwr[a][tX]==0)&&(Main.bwr[a][tX-1]==1)&&(Main.b[a][tX-1]==1)) {
								score=connect6ShapeScoreBlack(4, openEnds, 1); // currentTurn is black
								resultB[a][i]+=score;	
							}
							if((nCount+countConsecutive==5)||(nCount+countConsecutive==4)) {
								score=connect6ShapeScoreBlack(countConsecutive+nCount, openEnds, 1); // currentTurn is black
								resultB[a][i]+=score;
								
							}
						}
					}
					
					if((i-2>0)&&(Main.bwr[a][i-1]==0&&Main.b[a][i-1]==0)&&(Main.bwr[a][i-2]==1&&Main.b[a][i-2]==1)) {
						//두자리가 비어있고 연속된 경우
						nCount=0;
						tX=i-2;
						if(tX>1) 
							while(tX>0&&(Main.bwr[a][i-1] == 0)&& Main.bwr[a][tX]==1&&Main.b[a][tX]==1) {
								nCount++;
								tX--;
							}
							if(nCount+countConsecutive==6) {
								score=connect6ShapeScoreBlack(6, openEnds, 1); // currentTurn is black
								resultB[a][i]+=score;
							}
					}
					
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
					score = connect6ShapeScoreBlack(countConsecutive, openEnds, 0); // currentTurn is black
					resultB[a][i]+=score;
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
	public int[][] analyzeRightDiagonalSetsForBlack() {
		double score = 0;
		int countConsecutive = 0;
		int openEnds = 0;
		
		//left bottom to right
		for (int i = 0; i <= 2 * 19 - 2; i++) {
			int lb, ub;
			if (19<= i)
				lb = -(2 * 19 - 2 - i);
			else
				lb = -i;
			ub = -lb;

			for (int diff = lb; diff <= ub; diff += 2) {
				//x, y 좌표 이용
				int x = (i + diff) >> 1;//(i+diff)/2;
				int y = i - x;
				
				//System.out.println("a "+a+" i "+i);
				if (Main.b[x][y] == 1) { // 돌이 검정색이 되면 연속점 1증가
					countConsecutive++;
					if(countConsecutive==1) {
						if((x-1>0)&&(y+1<19)&&(Main.bwr[x-1][y+1] == 0)) {
							openEnds=1;
						}else {
							openEnds=0;
						}
					}
					
					resultB[x][y]=Integer.MIN_VALUE;
				}
				else if (Main.bwr[x][y] == 0 && countConsecutive > 0) { // 연속점이 빈곳을 만나서 끝났을 경우
					openEnds++;
					score = connect6ShapeScoreBlack(countConsecutive, openEnds, 0); // currentTurn is black		
					resultB[x][y]+=score;
					
					//한 자리가 비어있고 연속된 경우
					if(x+1<19&&y-1>0&&Main.bwr[x+1][y-1]==1&&Main.b[x+1][y-1]==1) {
						nCount=0;
						tX=x+1; //범위 설정
						tY=y-1;
						int end=(i + ub) >> 1;
						if(tX<end) {
							while(tX<19&&tY>0&&Main.bwr[tX][tY]==1&&Main.b[tX][tY]==1) {
								nCount++;
								tX++;
								tY--;
							}
							if(tX+1<19&&tX-1>0&&(nCount+countConsecutive==3)&&(Main.bwr[tX][tY]==0)&&(Main.bwr[tX+1][tY-1]==1)&&(Main.b[tX+1][tY-1]==1)) {
								score=connect6ShapeScoreBlack(4, openEnds, 1); // currentTurn is black
								resultB[x][y]+=score;	
							}
							if((nCount+countConsecutive==5)||(nCount+countConsecutive==4)) {
								score=connect6ShapeScoreBlack(countConsecutive+nCount, openEnds, 1); // currentTurn is black
								resultB[x][y]+=score;
							}
						}
					}	
					
					//두 자리가 비어있고 연속된 경우
					if(x+2<19&&y-2>0&&(Main.bwr[x+1][y-1]==0&&Main.b[x+1][y-1]==0)&&(Main.bwr[x+2][y-2]==1&&Main.b[x+2][y-2]==1)) {
						//두자리가 비어있고 연속된 경우
						nCount=0;
						tX=x+2; //범위 설정
						tY=y-2;
						int end=(i + ub) >> 1;
						if(tX<end) {
							while(tX<19&&tY>0&&(Main.bwr[x+1][y-1] == 0)&& Main.bwr[tX][tY]==1&&Main.b[tX][tY]==1) {
								nCount++;
								tX++;
								tY--;
							}
							if(nCount+countConsecutive==4) {
								score=connect6ShapeScoreBlack(6, openEnds, 1); // currentTurn is black
								resultB[x][y]+=score;
							}
						}
					}
					
					countConsecutive = 0;
					openEnds = 1;
				} else if (Main.bwr[x][y] == 0) // 빈 점이 그냥 등장할 경우
					openEnds = 1;
				else if (countConsecutive > 0) { // 연속점이 다른 돌에 만나서 끝났을 경우
					score= connect6ShapeScoreBlack(countConsecutive, openEnds, 0); // currentTurn is black
					resultB[x][y]+=score;
					countConsecutive = 0;
					openEnds = 0;
				} else
					openEnds = 0;
			}
			if (countConsecutive > 0) // 연속점이 벽에 만나서 끝났을 경우
				score = connect6ShapeScoreBlack(countConsecutive, openEnds, 0); // currentTurn is black
			//resultArr[x][y]+=score;
			countConsecutive = 0;
			openEnds = 0;
		}
		
		//right to left bottom
		for (int i = 0; i<=2*19-2; i++) {
			int lb, ub;
			if (19<= i)
				lb = -(2 * 19 - 2 - i);
			else
				lb = -i;
			ub = -lb;

			for (int diff = ub; diff >= lb; diff -= 2) {
				int x = (i + diff) >> 1;
				int y = i - x;
				//System.out.println("a "+a+" i "+i);
				if (Main.b[x][y] == 1) { // 돌이 검정색이 되면 연속점 1증가
					countConsecutive++;
					if(countConsecutive==1) {
						if((x+1<19)&&(y-1>0)&&(Main.bwr[x+1][y-1] == 0)) {
							openEnds=1;
						}else {
							openEnds=0;
						}
					}
					
					resultB[x][y]=Integer.MIN_VALUE;
				}
				else if (Main.bwr[x][y] == 0 && countConsecutive > 0) { // 연속점이 빈곳을 만나서 끝났을 경우
					openEnds++;
					score = connect6ShapeScoreBlack(countConsecutive, openEnds, 0); // currentTurn is black
					resultB[x][y]+=score;
					
					//한 자리가 비어있고 연속된 경우
					if(x-1>0&&y+1<19&&Main.bwr[x-1][y+1]==1&&Main.b[x-1][y+1]==1) {
						nCount=0;
						tX=x-1; //범위 설정
						tY=y+1; 
						int end=(i + ub) >> 1;
						if(tX<end) {
							while(tX<19&&tY>0&&Main.bwr[tX][tY]==1&&Main.b[tX][tY]==1) {
								nCount++;
								tX--;
								tY++;
							}
							if(tX-1>0&&tY+1<19&&(nCount+countConsecutive==3)&&(Main.bwr[tX][tY]==0)&&(Main.bwr[tX-1][tY+1]==1)&&(Main.b[tX-1][tY+1]==1)) {
								score=connect6ShapeScoreBlack(4, openEnds, 1); // currentTurn is black
								resultB[x][y]+=score;	
							}
							if((nCount+countConsecutive==5)||(nCount+countConsecutive==4)) {
								score=connect6ShapeScoreBlack(countConsecutive+nCount, openEnds, 1); // currentTurn is black
								resultB[x][y]+=score;
							}
						}
					}	
					
					//두 자리가 비어있고 연속된 경우
					if(x-2>0&&y+2<19&&(Main.bwr[x-1][y+1]==0&&Main.b[x-1][y+1]==0)&&(Main.bwr[x-2][y+2]==1&&Main.b[x-2][y+2]==1)) {
						//두자리가 비어있고 연속된 경우
						nCount=0;
						tX=x-2; //범위 설정
						tY=y+2;
						int end=(i + ub) >> 1;
						if(tX<end) {
							while(tX<19&&tY>0&&(Main.bwr[x-1][y+1] == 0)&& Main.bwr[tX][tY]==1&&Main.b[tX][tY]==1) {
								nCount++;
								tX--;
								tY++;
							}
							if(nCount+countConsecutive==4) {
								score=connect6ShapeScoreBlack(6, openEnds, 1); // currentTurn is black
								resultB[x][y]+=score;
							}
						}
					}
					
					countConsecutive = 0;
					openEnds = 1;
				} else if (Main.bwr[x][y] == 0) // 빈 점이 그냥 등장할 경우
					openEnds = 1;
				else if (countConsecutive > 0) { // 연속점이 다른 돌에 만나서 끝났을 경우
					score= connect6ShapeScoreBlack(countConsecutive, openEnds, 0); // currentTurn is black
					resultB[x][y]+=score;
					countConsecutive = 0;
					openEnds = 0;
				} else
					openEnds = 0;
			}
			if (countConsecutive > 0) // 연속점이 벽에 만나서 끝났을 경우
				score = connect6ShapeScoreBlack(countConsecutive, openEnds, 0); // currentTurn is black
			//resultArr[x][y]+=score;
			countConsecutive = 0;
			openEnds = 0;
		}

		
		return resultB;
	}

	//computer = white인 경우 대각선 검사
	public int[][] analyzeLeftDiagonalSetsForBlack() {
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

			
			//left to right bottom
			for (int diff = lb; diff <= ub; diff += 2) {
				int x = (i + diff) >> 1;
				int y = 18 - i + x;
				//System.out.println("X: "+x+"Y: " +y);

				if (Main.b[x][y] == 1){ // 돌이 검정색이 되면 연속점 1증가
					countConsecutive++;
					if(countConsecutive==1) {
						if((x-1>0)&&(y-1>0)&&(Main.bwr[x-1][y-1] == 0)) {
							openEnds=1;
						}else {
							openEnds=0;
						}
					}
					
					resultB[x][y]=Integer.MIN_VALUE;
				}
				
				else if (Main.bwr[x][y] == 0 && countConsecutive > 0) { // 연속점이 다른 돌에 만나서 끝났을 경우
					openEnds++;
					score = connect6ShapeScoreBlack(countConsecutive, openEnds, 0); // currentTurn is black
					resultB[x][y]+=score;
				
					//한 자리가 비어있고 연속된 경우
					if(x+1<19&&y+1<19&&Main.bwr[x+1][y+1]==1&&Main.b[x+1][y+1]==1) {
						nCount=0;
						tX=x+1; //범위 설정
						tY=y+1;
						int end=(i + ub) >> 1;
						if(tX<end) {
							while(tX<19&&tY<19&&Main.bwr[tX][tY]==1&&Main.b[tX][tY]==1) {
								nCount++;
								tX++;
								tY++;
							}
							if(tX+1<19&&tY+1<19&&(nCount+countConsecutive==3)&&(Main.bwr[tX][tY]==0)&&(Main.bwr[tX+1][tY+1]==1)&&(Main.b[tX+1][tY+1]==1)) {
								score=connect6ShapeScoreBlack(4, openEnds, 1); // currentTurn is black
								resultB[x][y]+=score;	
							}
							if((nCount+countConsecutive==5)||(nCount+countConsecutive==4)) {
								score=connect6ShapeScoreBlack(countConsecutive+nCount, openEnds, 1); // currentTurn is black
								resultB[x][y]+=score;
							}
						}
					}	
					
					//두 자리가 비어있고 연속된 경우
					if(x+2<19&&y+2<19&&(Main.bwr[x+1][y+1]==0&&Main.b[x+1][y+1]==0)&&(Main.bwr[x+2][y+2]==1&&Main.b[x+2][y+2]==1)) {
						//두자리가 비어있고 연속된 경우
						nCount=0;
						tX=x+2; //범위 설정
						tY=y+2;
						int end=(i + ub) >> 1;
						if(tX<end) {
							while(tX<19&&tY<19&&(Main.bwr[x+1][y+1] == 0)&& Main.bwr[tX][tY]==1&&Main.b[tX][tY]==1) {
								nCount++;
								tX++;
								tY++;
							}
							if(nCount+countConsecutive==4) {
								score=connect6ShapeScoreBlack(6, openEnds, 1); // currentTurn is black
								resultB[x][y]+=score;
							}
						}
					}
				
					countConsecutive = 0;
					openEnds = 1;
				} else if (Main.bwr[x][y] == 0) // 빈 점이 그냥 등장할 경우
					openEnds = 1;
				else if (countConsecutive > 0) { // 연속점이 다른 돌에 만나서 끝났을 경우
					score= connect6ShapeScoreBlack(countConsecutive, openEnds, 0); // currentTurn is black
					resultB[x][y]+=score;
					countConsecutive = 0;
					openEnds = 0;
				} else
					openEnds = 0;
			}
			if (countConsecutive > 0) // 연속점이 벽에 만나서 끝났을 경우
				score = connect6ShapeScoreBlack(countConsecutive, openEnds, 0); // currentTurn is black
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

			for (int diff = ub; diff >=lb; diff -= 2) {
				int x = (i + diff) >> 1;
				int y = 18 - i + x;

				if (Main.b[x][y] == 1){ // 돌이 검정색이 되면 연속점 1증가
					countConsecutive++;
					if(countConsecutive==1) {
						if((x+1<19)&&(y+1<19)&&(Main.bwr[x+1][y+1] == 0)) {
							openEnds=1;
						}else {
							openEnds=0;
						}
					}
					
					resultB[x][y]=Integer.MIN_VALUE;
				}
				else if (Main.bwr[x][y] == 0 && countConsecutive > 0) { // 연속점이 다른 돌에 만나서 끝났을 경우
					openEnds++;
					score = connect6ShapeScoreBlack(countConsecutive, openEnds, 0); // currentTurn is black
					resultB[x][y]+=score;
					
					//한 자리가 비어있고 연속된 경우
					if(x-1>0&&y-1>0&&Main.bwr[x-1][y-1]==1&&Main.b[x-1][y-1]==1) {
						nCount=0;
						tX=x-1; //범위 설정
						tY=y-1;
						int end=(i + ub) >> 1;
						if(tX<end) {
							while(tX>0&&tY>0&&Main.bwr[tX][tY]==1&&Main.b[tX][tY]==1) {
								nCount++;
								tX--;
								tY--;
							}
							if(tX-1>0&&tY-1>0&&(nCount+countConsecutive==3)&&(Main.bwr[tX][tY]==0)&&(Main.bwr[tX-1][tY-1]==1)&&(Main.b[tX-1][tY-1]==1)) {
								score=connect6ShapeScoreBlack(4, openEnds, 1); // currentTurn is black
								resultB[x][y]+=score;	
							}
							if((nCount+countConsecutive==5)||(nCount+countConsecutive==4)) {
								score=connect6ShapeScoreBlack(countConsecutive+nCount, openEnds, 1); // currentTurn is black
								resultB[x][y]+=score;
							}
						}
					}	
					
					//두 자리가 비어있고 연속된 경우
					if((x-2>0)&&y-2>0&&(Main.bwr[x-1][y-1]==0&&Main.b[x-1][y-1]==0)&&(Main.bwr[x-2][y-2]==1&&Main.b[x-2][y-2]==1)) {
						//두자리가 비어있고 연속된 경우
						nCount=0;
						tX=x-2; //범위 설정
						tY=y-2;
						int end=(i + ub) >> 1;
						if(tX<end) {
							while(tX>0&&tY>0&&(Main.bwr[x-1][y-1] == 0)&& Main.bwr[tX][tY]==1&&Main.b[tX][tY]==1) {
								nCount++;
								tX--;
								tY--;
							}
							if(nCount+countConsecutive==4) {
								score=connect6ShapeScoreBlack(6, openEnds, 1); // currentTurn is black
								resultB[x][y]+=score;
							}
						}
					}
					
					countConsecutive = 0;
					openEnds = 1;
				} else if (Main.bwr[x][y] == 0) // 빈 점이 그냥 등장할 경우
					openEnds = 1;
				else if (countConsecutive > 0) { // 연속점이 다른 돌에 만나서 끝났을 경우
					score= connect6ShapeScoreBlack(countConsecutive, openEnds, 0); // currentTurn is black
					resultB[x][y]+=score;
					countConsecutive = 0;
					openEnds = 0;
				} else
					openEnds = 0;
			}
			if (countConsecutive > 0) // 연속점이 벽에 만나서 끝났을 경우
				score = connect6ShapeScoreBlack(countConsecutive, openEnds,0); // currentTurn is black
			//resultArr[x][y]+=score;
			countConsecutive = 0;
			openEnds = 0;
		}
		return resultB;
	}

	// computer = white 일때, 체크 
	public void compareBlack() {
		for (int i = 0; i < 19; i++) {
			for (int j = 0; j < 19; j++) {
				resultB[i][j]=0;
			}
		}
		
		analyzeHorizontalSetsForBlack();
		analyzeVerticalSetsForBlack();
		analyzeLeftDiagonalSetsForBlack();
		analyzeRightDiagonalSetsForBlack();

		int max = 500000;
		aa =new Point();
		aa.x=-1;aa.y=-1;
		for (int i = 0; i < 19; i++) {
			for (int j = 0; j < 19; j++) {
				if((resultB[i][j]>=max)&&Main.bwr[i][j]==0) {
					max=resultB[i][j];
					aa.x=i;
					aa.y=j;
				}
			}
		}
		if(aa.x==-1&&aa.y==-1) {
			p=false;
		}
	}
	public void compareBlack2() {
		for (int i = 0; i < 19; i++) {
			for (int j = 0; j < 19; j++) {
				resultB[i][j]=0;
			}
		}
		
		analyzeHorizontalSetsForBlack();
		analyzeVerticalSetsForBlack();
		analyzeLeftDiagonalSetsForBlack();
		analyzeRightDiagonalSetsForBlack();
		
		System.out.println("This is black");
		for (int j = 0; j < 19; j++) {
			for (int i = 0; i < 19; i++) {
				System.out.print(resultW[i][j]+" ");
			}
			System.out.println("");
		}
		int max =0;
		aa =new Point();
		for (int i = 0; i < 19; i++) {
			for (int j = 0; j < 19; j++) {
				if((resultB[i][j]>max)&&Main.bwr[i][j]==0) {
					max=resultB[i][j];
					aa.x=i;
					aa.y=j;
				}
			}
		}
		if(max<5) {
			compareWhite2();
		}
		
		System.out.println("compare Black2 : aa.x : "+aa.x+" aa.y: "+aa.y+" max: "+max);
	}
	
	public void compareBlackFirst() {
		//가중치 return받는 곳 
		
		for (int i = 0; i < 19; i++) {
			for (int j = 0; j < 19; j++) {
				resultB[i][j]=0; //가중치 초기화
			}
		}
		
		//흑돌에 대한 가중치 계산
		analyzeHorizontalSetsForBlack(); 
		analyzeVerticalSetsForBlack();
		analyzeLeftDiagonalSetsForBlack();
		analyzeRightDiagonalSetsForBlack();

		int max = 0;
		aa = new Point();
		for (int i = 0; i < 19; i++) {
			for (int j = 0; j < 19; j++) {
				if((resultB[i][j]>max)&&(Main.bwr[i][j]==0)) {
					max=resultB[i][j];
					aa.x=i;
					aa.y=j;
				}
			}
		}
	}
	//가중치
	/*public double connect6ShapeScoreWhite(int consecutive, int openEnds, int con) { // shape에 따른 가중치 부여
		if (openEnds == 0 && consecutive <= 6)
			return 0;
		switch (consecutive) {
		case 6: 
			switch (openEnds) {
			case 2:
				if (Main.count%4==1) { // this.currentTurn
					return 200000000; //공격
				}
				return 50;
			}
			
		case 5:
			switch (openEnds) {
			case 1:
				if (Main.count%4==1) { // this.currentTurn
					return 200000000; //공격
				}
				return 100000000; //공격
				
			case 2:
				if (Main.count%4==1)
					return 200000000; //공격
				return 100000000; //공격
			}
			
		case 4:
			switch (openEnds) {
			case 1:
				if (Main.count%4==1){ // this.currentTurn				
					return 200000000; //공격
				}
				return 50;
			case 2:
				if (Main.count%4==1){ // this.currentTurn
					return 200000000; //공격
				}
				return 500000;
			}
			
		case 3:
			switch (openEnds) {
			case 1:
				if (Main.count%4==1)
					return 7;
				return 5;
			case 2:
				if (Main.count%4==1)
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
			return 0; // default
		}
	}*/

	//가중치
	public double connect6ShapeScoreWhite(int consecutive, int openEnds, int con) { // shape에 따른 가중치 부여
		if (openEnds == 0 && consecutive <= 6)
			return 0;
		switch (consecutive) {
		case 6: 
			switch (openEnds) {
			case 2:
				if (Main.count%4==1) { // this.currentTurn
					return 200000000; //공격
				}
				return 50;
			}
			
		case 5:
			switch (openEnds) {
			case 1:
				if (Main.count%4==1) { // this.currentTurn
					return 200000000; //공격
				}
				return 100000000; //공격
				
			case 2:
				if (Main.count%4==1)
					return 200000000; //공격
				return 100000000; //공격
			}
			
		case 4:
			switch (openEnds) {
			case 1:
				if (Main.count%4==1){ // this.currentTurn				
					return 200000000; //공격
				}
				return 50;
			case 2:
				if (Main.count%4==1){ // this.currentTurn
					return 200000000; //공격
				}
				return 500000;
			}
			
		case 3:
			switch (openEnds) {
			case 1:
				if (Main.count%4==1)
					return 7;
				return 5;
			case 2:
				if (Main.count%4==1)
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
			return 0; // default
		}
	}

	public int[][] analyzeHorizontalSetsForWhite() {
		double score = 0;
		int countConsecutive = 0;
		int openEnds = 0;

		for (int i = 0; i < 19; i++) {
			for (int a = 0; a < 19; a++) {
				if (Main.w[a][i] == 1) { // 돌이 검정색이 되면 연속점 1증가
					countConsecutive++;
					if(countConsecutive==1) {
						if((a-1>0)&&(Main.bwr[a-1][i] == 0)) {
							openEnds=1;
						}else {
							openEnds=0;
						}
					}
					//resultW[a][i]=Integer.MIN_VALUE;
				}
				else if (Main.bwr[a][i] == 0 && countConsecutive > 0) { // 비어있을 경우 연속된 돌이 있을 때,
					openEnds++;
					score = connect6ShapeScoreWhite(countConsecutive, openEnds, 0); // currentTurn is black
					resultW[a][i]+=score;
					
					//한자리가 비어있고 연속된 경우
					if(a+1<19&&Main.bwr[a+1][i]==1&&Main.w[a+1][i]==1) {
						nCount=0;
						tX=a+1; //범위 설정
						if(tX<19) {
							while(tX<19&&Main.bwr[tX][i]==1&&Main.w[tX][i]==1) {
								nCount++;
								tX++;
							}
							if(tX+1<19&&(nCount+countConsecutive==3)&&(Main.bwr[tX][i]==0)&&(Main.bwr[tX+1][i]==1)&&(Main.w[tX+1][i]==1)) {
								score=connect6ShapeScoreWhite(4, openEnds, 1); // currentTurn is black
								resultB[a][i]+=score;	
							}
							if((nCount+countConsecutive==5)||(nCount+countConsecutive==4)) {
								score=connect6ShapeScoreWhite(countConsecutive+nCount, openEnds, 1); // currentTurn is black
								resultW[a][i]+=score;	
							}
						}
					}
					if(a+2<19&&(Main.bwr[a+1][i]==0&&Main.w[a+1][i]==0)&&(Main.bwr[a+2][i]==1&&Main.w[a+2][i]==1)) {
						//두자리가 비어있고 연속된 경우
						
						nCount=0;
						tX=a+2;
						if(tX<19) {
							while(tX<19&&(Main.bwr[a+1][i] == 0)&& Main.bwr[tX][i]==1&&Main.w[tX][i]==1) {
								nCount++;
								tX++;
							}
							if(nCount+countConsecutive==4) {
								score=connect6ShapeScoreWhite(6, openEnds, 1); // currentTurn is black
								resultW[a][i]+=score;
							}
						}
					}
					
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
					score= connect6ShapeScoreWhite(countConsecutive, openEnds, 0); // currentTurn is black
					resultW[a][i]+=score;
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
					if(countConsecutive==1) {
						if((a+1<19)&&(Main.bwr[a+1][i] == 0)) {
							openEnds=1;
						}else {
							openEnds=0;
						}
					}
					//resultW[a][i]=Integer.MIN_VALUE;
				}
				else if (Main.bwr[a][i] == 0 && countConsecutive > 0) { // 비어있을 경우 연속된 돌이 있을 때,
					openEnds++;
					score = connect6ShapeScoreWhite(countConsecutive, openEnds, 0); // currentTurn is black
					resultW[a][i]+=score;

					//한자리가 비어있고 연속된 경우
					if(a-1>0&&Main.bwr[a-1][i]==1&&Main.w[a-1][i]==1) {
						nCount=0;
						tX=a-1;
						if(tX>0) {
							while(tX>0&&Main.bwr[tX][i]==1&&Main.w[tX][i]==1) {
								nCount++;
								tX--;
							}
							if(tX-1>0&&(nCount+countConsecutive==3)&&(Main.bwr[tX][i]==0)&&(Main.bwr[tX-1][i]==1)&&(Main.w[tX-1][i]==1)) {
								score=connect6ShapeScoreWhite(4, openEnds, 1); // currentTurn is black
								resultB[a][i]+=score;	
							}
							if((nCount+countConsecutive==5)||(nCount+countConsecutive==4)) {
								score=connect6ShapeScoreWhite(countConsecutive+nCount, openEnds, 1); // currentTurn is black
								resultW[a][i]+=score;
							}
						}
					}
					
					if(a-2>0&&(Main.bwr[a-1][i]==0&&Main.w[a-1][i]==0)&&(Main.bwr[a-2][i]==1&&Main.w[a-2][i]==1)) {
						//두 자리가 비어있고 연속된 경우
						nCount=0;
						tX=a-2;
						if(tX>1) {
							while(tX>0&&(Main.bwr[a-1][i] == 0)&& Main.bwr[tX][i]==1&&Main.w[tX][i]==1) {
								nCount++;
								tX--;
							}
							if(nCount+countConsecutive==4) {
								score=connect6ShapeScoreWhite(6, openEnds, 1); // currentTurn is black
								resultW[a][i]+=score;
							}
						}
					}
					
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
					score= connect6ShapeScoreWhite(countConsecutive, openEnds, 0); // currentTurn is black
					resultW[a][i]+=score;
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
	public int[][] analyzeVerticalSetsForWhite() {
		double score = 0;
		int countConsecutive = 0;
		int openEnds = 0;

		for (int a = 0; a < 19; a++) {
			for (int i = 0; i < 19; i++) {
				if (Main.w[a][i] == 1) { // 돌이 검정색이 되면 연속점 1증가
					countConsecutive++;
					if(countConsecutive==1) {
						if((i-1>0)&&(Main.bwr[a][i-1] == 0)) {
							openEnds=1;
						}else {
							openEnds=0;
						}
					}
					
					//resultW[a][i]=Integer.MIN_VALUE;
				}
				else if (Main.bwr[a][i] == 0 && countConsecutive > 0) {
					openEnds++;
					score = connect6ShapeScoreWhite(countConsecutive, openEnds, 0); // currentTurn is black
					resultW[a][i]+=score;
					
					//한자리가 비어있고 연속된 경우
					if(i+1<19&&Main.bwr[a][i+1]==1&&Main.w[a][i+1]==1) {
						//한 자리가 비어있고 연속된 경우
						nCount=0;
						tX=i+1;
						if(tX<19) {
							while(tX<19&&Main.bwr[a][tX]==1&&Main.w[a][tX]==1) {
								nCount++;
								tX++;
							}
							if(tX+1<19&&(nCount+countConsecutive==3)&&(Main.bwr[a][tX]==0)&&(Main.bwr[a][tX+1]==1)&&(Main.w[a][tX+1]==1)) {
								score=connect6ShapeScoreWhite(4, openEnds, 1); // currentTurn is black
								resultB[a][i]+=score;	
							}
							if((nCount+countConsecutive==5)||(nCount+countConsecutive==4)) {
								score=connect6ShapeScoreWhite(countConsecutive+nCount, openEnds, 1); // currentTurn is black
								resultW[a][i]+=score;
							}
						}
					}
					
					if(i+2<19&&(Main.bwr[a][i+1]==0&&Main.w[a][i+1]==0)&&(Main.bwr[a][i+2]==1&&Main.w[a][i+2]==1)) {
						//두 자리가 비어있고 연속된 경우
						nCount=0;
						tX=i+2;
						if(tX<19) {
							while(tX<19&&(Main.bwr[a][i+1] == 0)&& Main.bwr[a][tX]==1&&Main.w[a][tX]==1) {
								nCount++;
								tX++;
							}
							if(nCount+countConsecutive==4) {
								score=connect6ShapeScoreWhite(6, openEnds, 1); // currentTurn is black
								resultW[a][i]+=score;
							}
						}
					}		
					
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
					score = connect6ShapeScoreWhite(countConsecutive, openEnds, 0); // currentTurn is black
					resultW[a][i]+=score;
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
					if(countConsecutive==1) {
						if((i+1<19)&&(Main.bwr[a][i+1] == 0)) {
							openEnds=1;
						}else {
							openEnds=0;
						}
					}
					//resultW[a][i]=Integer.MIN_VALUE;
				}
				else if (Main.bwr[a][i] == 0 && countConsecutive > 0) {
					openEnds++;
					score = connect6ShapeScoreWhite(countConsecutive, openEnds, 0); // currentTurn is black
					resultW[a][i]+=score;
					
					//한자리가 비어있고 연속된 경우
					if(i-1>0&&Main.bwr[a][i-1]==1&&Main.w[a][i-1]==1) {
						nCount=0;
						tX=i-1; //범위 설정
						if(tX>0) {
							while(tX>0&&Main.bwr[a][tX]==1&&Main.w[a][tX]==1) {
								nCount++;
								tX--;
							}
							if(tX-1>0&&(nCount+countConsecutive==3)&&(Main.bwr[a][tX]==0)&&(Main.bwr[a][tX-1]==1)&&(Main.w[a][tX-1]==1)) {
								score=connect6ShapeScoreWhite(4, openEnds, 1); // currentTurn is black
								resultB[a][i]+=score;	
							}
							if((nCount+countConsecutive==5)||(nCount+countConsecutive==4)) {
								score=connect6ShapeScoreWhite(countConsecutive+nCount, openEnds,1); // currentTurn is black
								resultW[a][i]+=score;
								
							}
						}
					}
					
					if(i-2>0&&(Main.bwr[a][i-1]==0&&Main.w[a][i-1]==0)&&(Main.bwr[a][i-2]==1&&Main.w[a][i-2]==1)) {
						//두자리가 비어있고 연속된 경우
						nCount=0;
						tX=i-2;
						if(tX>1) 
							while(tX>0&&(Main.bwr[a][i-1] == 0)&& Main.bwr[a][tX]==1&&Main.w[a][tX]==1) {
								nCount++;
								tX--;
							}
							if(nCount+countConsecutive==4) {
								score=connect6ShapeScoreWhite(6, openEnds, 1); // currentTurn is black
								resultW[a][i]+=score;
							}
					}
					
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
					score = connect6ShapeScoreWhite(countConsecutive, openEnds, 0); // currentTurn is black
					resultW[a][i]+=score;
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
	public int[][] analyzeLeftDiagonalSetsForWhite() {
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
					if(countConsecutive==1) {
						if((x-1>0)&&(y-1>0)&&(Main.bwr[x-1][y-1] == 0)) {
							openEnds=1;
						}else {
							openEnds=0;
						}
					}
					
					//resultW[x][y]=Integer.MIN_VALUE;
				}
				else if (Main.bwr[x][y] == 0 && countConsecutive > 0) { // 연속점이 빈곳을 만나서 끝났을 경우
					openEnds++;
					score = connect6ShapeScoreWhite(countConsecutive, openEnds, 0); // currentTurn is black
					resultW[x][y]+=score;
					
					//한 자리가 비어있고 연속된 경우
					if(x+1<19&&y+1<19&&Main.bwr[x+1][y+1]==1&&Main.w[x+1][y+1]==1) {
						nCount=0;
						tX=x+1; //범위 설정
						tY=y+1;
						int end=(i + ub) >> 1;
						if(tX<end) {
							while(tX<19&&tY<19&&Main.bwr[tX][tY]==1&&Main.w[tX][tY]==1) {
								nCount++;
								tX++;
								tY++;
							}
							if(tX+1<19&&tY+1<19&&(nCount+countConsecutive==3)&&(Main.bwr[tX][tY]==0)&&(Main.bwr[tX+1][tY+1]==1)&&(Main.w[tX+1][tY+1]==1)) {
								score=connect6ShapeScoreWhite(4, openEnds, 1); // currentTurn is black
								resultB[x][y]+=score;	
							}
							if((nCount+countConsecutive==5)||(nCount+countConsecutive==4)) {
								score=connect6ShapeScoreWhite(countConsecutive+nCount, openEnds, 1); // currentTurn is black
								resultW[x][y]+=score;
							}
						}
					}	
					
					//두 자리가 비어있고 연속된 경우
					if(x+2<19&&y+2<19&&(Main.bwr[x+1][y+1]==0&&Main.w[x+1][y+1]==0)&&(Main.bwr[x+2][y+2]==1&&Main.w[x+2][y+2]==1)) {
						//두자리가 비어있고 연속된 경우
						nCount=0;
						tX=x+2; //범위 설정
						tY=y+2;
						int end=(i + ub) >> 1;
						if(tX<end) {
							while(tX<19&&tY<19&&(Main.bwr[x+1][y+1] == 0)&& Main.bwr[tX][tY]==1&&Main.w[tX][tY]==1) {
								nCount++;
								tX++;
								tY++;
							}
							if(nCount+countConsecutive==4) {
								score=connect6ShapeScoreWhite(6, openEnds, 1); // currentTurn is black
								resultW[x][y]+=score;
							}
						}
					}
					
					countConsecutive = 0;
					openEnds = 1;
				} else if (Main.bwr[x][y] == 0) // 빈 점이 그냥 등장할 경우
					openEnds = 1;
				else if (countConsecutive > 0) { // 연속점이 다른 돌에 만나서 끝났을 경우
					score= connect6ShapeScoreWhite(countConsecutive, openEnds,0); // currentTurn is black
					resultW[x][y]+=score;
					countConsecutive = 0;
					openEnds = 0;
				} else
					openEnds = 0;
			}
			
			if (countConsecutive > 0) // 연속점이 벽에 만나서 끝났을 경우
				score = connect6ShapeScoreWhite(countConsecutive, openEnds, 0); // currentTurn is black
			//resultW[x][y]+=score;
			countConsecutive = 0;
			openEnds = 0;
		}

		for (int i = 0; i <= 2 * n - 2; i++) {
			int lb, ub;
			if (19<= i)
				lb = -(2 * n - 2 - i);
			else
				lb = -i;
			ub = -lb;

			for (int diff = ub; diff >=lb; diff -= 2) {
				int x = (i + diff) >> 1;
				int y = i - x;
				//System.out.println("a "+a+" i "+i);
				if (Main.w[x][y] == 1) { // 돌이 검정색이 되면 연속점 1증가
					countConsecutive++;
					if(countConsecutive==1) {
						if((x+1<19)&&(y+1<19)&&(Main.bwr[x+1][y+1] == 0)) {
							openEnds=1;
						}else {
							openEnds=0;
						}
					}
					
					//resultW[x][y]=Integer.MIN_VALUE;
				}
				else if (Main.bwr[x][y] == 0 && countConsecutive > 0) { // 연속점이 빈곳을 만나서 끝났을 경우
					openEnds++;
					score = connect6ShapeScoreWhite(countConsecutive, openEnds, 0); // currentTurn is black
					resultW[x][y]+=score;
					
					//한 자리가 비어있고 연속된 경우
					if(x-1>0&&y-1>0&&Main.bwr[x-1][y-1]==1&&Main.w[x-1][y-1]==1) {
						nCount=0;
						tX=x-1; //범위 설정
						tY=y-1;
						int end=(i + ub) >> 1;
						if(tX<end) {
							while(tX>0&&tY>0&&Main.bwr[tX][tY]==1&&Main.w[tX][tY]==1) {
								nCount++;
								tX--;
								tY--;
							}
							if(tX-1>0&&tX-1>0&&(nCount+countConsecutive==3)&&(Main.bwr[tX][tY]==0)&&(Main.bwr[tX-1][tY-1]==1)&&(Main.w[tX-1][tY-1]==1)) {
								score=connect6ShapeScoreWhite(4, openEnds, 1); // currentTurn is black
								resultB[x][y]+=score;	
							}
							if((nCount+countConsecutive==5)||(nCount+countConsecutive==4)) {
								score=connect6ShapeScoreWhite(countConsecutive+nCount, openEnds, 1); // currentTurn is black
								resultW[x][y]+=score;
							}
						}
					}	
					
					//두 자리가 비어있고 연속된 경우
					if(x-2>0&&y-2>0&&(Main.bwr[x-1][y-1]==0&&Main.w[x-1][y-1]==0)&&(Main.bwr[x-2][y-2]==1&&Main.w[x-2][y-2]==1)) {
						//두자리가 비어있고 연속된 경우
						nCount=0;
						tX=x-2; //범위 설정
						tY=y-2;
						int end=(i + ub) >> 1;
						if(tX<end) {
							while(tX>0&&tY>0&&(Main.bwr[x-1][y-1] == 0)&& Main.bwr[tX][tY]==1&&Main.w[tX][tY]==1) {
								nCount++;
								tX--;
								tY--;
							}
							if(nCount+countConsecutive==4) {
								score=connect6ShapeScoreWhite(6, openEnds, 1); // currentTurn is black
								resultW[x][y]+=score;
							}
						}
					}
					
					countConsecutive = 0;
					openEnds = 1;
				} else if (Main.bwr[x][y] == 0) // 빈 점이 그냥 등장할 경우
					openEnds = 1;
				else if (countConsecutive > 0) { // 연속점이 다른 돌에 만나서 끝났을 경우
					score= connect6ShapeScoreWhite(countConsecutive, openEnds, 0); // currentTurn is black
					resultW[x][y]+=score;
					countConsecutive = 0;
					openEnds = 0;
				} else
					openEnds = 0;
			}
			
			if (countConsecutive > 0) // 연속점이 벽에 만나서 끝났을 경우
				score = connect6ShapeScoreWhite(countConsecutive, openEnds, 0); // currentTurn is black
			//resultW[x][y]+=score;
			countConsecutive = 0;
			openEnds = 0;
		}
		
		return resultW;
	}

	//computer = Black인 경우 대각선 검사
	public int[][] analyzeRightDiagonalSetsForWhite() {
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
					if(countConsecutive==1) {
						if((x-1>0)&&(y+1<19)&&(Main.bwr[x-1][y+1] == 0)) {
							openEnds=1;
						}else {
							openEnds=0;
						}
					}
					//resultW[x][y]=Integer.MIN_VALUE;
				}
				else if (Main.bwr[x][y] == 0 && countConsecutive > 0) { // 연속점이 다른 돌에 만나서 끝났을 경우
					openEnds++;
					score = connect6ShapeScoreWhite(countConsecutive, openEnds, 0); // currentTurn is black
					resultW[x][y]+=score;
					
					//한 자리가 비어있고 연속된 경우
					if(x+1<19&&y-1>0&&Main.bwr[x+1][y-1]==1&&Main.w[x+1][y-1]==1) {
						nCount=0;
						tX=x+1; //범위 설정
						tY=y-1;
						int end=(i + ub) >> 1;
						if(tX<end) {
							while(tX<19&&tY>0&&Main.bwr[tX][tY]==1&&Main.w[tX][tY]==1) {
								nCount++;
								tX++;
								tY--;
							}
							if(tX+1<19&&tX-1>0&&(nCount+countConsecutive==3)&&(Main.bwr[tX][tY]==0)&&(Main.bwr[tX+1][tY-1]==1)&&(Main.w[tX+1][tY-1]==1)) {
								score=connect6ShapeScoreWhite(4, openEnds, 1); // currentTurn is black
								resultB[x][y]+=score;	
							}
							if((nCount+countConsecutive==5)||(nCount+countConsecutive==4)) {
								score=connect6ShapeScoreWhite(countConsecutive+nCount, openEnds, 1); // currentTurn is black
								resultW[x][y]+=score;
							}
						}
					}	
					
					//두 자리가 비어있고 연속된 경우
					if(x+2<19&&y-2>0&&(Main.bwr[x+1][y-1]==0&&Main.w[x+1][y-1]==0)&&(Main.bwr[x+2][y-2]==1&&Main.w[x+2][y-2]==1)) {
						//두자리가 비어있고 연속된 경우
						nCount=0;
						tX=x+2; //범위 설정
						tY=y-2;
						int end=(i + ub) >> 1;
						if(tX<end) {
							while(tX<19&&tY>0&&(Main.bwr[x+1][y-1] == 0)&& Main.bwr[tX][tY]==1&&Main.w[tX][tY]==1) {
								nCount++;
								tX++;
								tY--;
							}
							if(nCount+countConsecutive==4) {
								score=connect6ShapeScoreWhite(6, openEnds, 1); // currentTurn is black
								resultW[x][y]+=score;
							}
						}
					}
					
					countConsecutive = 0;
					openEnds = 1;
				} else if (Main.bwr[x][y] == 0) // 빈 점이 그냥 등장할 경우
					openEnds = 1;
				else if (countConsecutive > 0) { // 연속점이 다른 돌에 만나서 끝났을 경우
					score= connect6ShapeScoreWhite(countConsecutive, openEnds, 0); // currentTurn is black
					resultW[x][y]+=score;
					countConsecutive = 0;
					openEnds = 0;
				} else
					openEnds = 0;
			}
			if (countConsecutive > 0) // 연속점이 벽에 만나서 끝났을 경우
				score = connect6ShapeScoreWhite(countConsecutive, openEnds, 0); // currentTurn is black
			//resultW[x][y]+=score;
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

			for (int diff = ub; diff >= lb; diff -= 2) {
				int x = (i + diff) >> 1;
				int y = 18 - i + x;

				if (Main.w[x][y] == 1){ // 돌이 검정색이 되면 연속점 1증가
					countConsecutive++;
					if(countConsecutive==1) {
						if((x+1<19)&&(y-1>0)&&(Main.bwr[x+1][y-1] == 0)) {
							openEnds=1;
						}else {
							openEnds=0;
						}
					}
					
					//resultW[x][y]=Integer.MIN_VALUE;
				}
				else if (Main.bwr[x][y] == 0 && countConsecutive > 0) { // 연속점이 다른 돌에 만나서 끝났을 경우
					openEnds++;
					score = connect6ShapeScoreWhite(countConsecutive, openEnds, 0); // currentTurn is black
					resultW[x][y]+=score;
					
					//한 자리가 비어있고 연속된 경우
					if(x-1>0&&y+1<19&&Main.bwr[x-1][y+1]==1&&Main.w[x-1][y+1]==1) {
						nCount=0;
						tX=x-1; //범위 설정
						tY=y+1; 
						int end=(i + ub) >> 1;
						if(tX<end) {
							while(tX<19&&tY>0&&Main.bwr[tX][tY]==1&&Main.w[tX][tY]==1) {
								nCount++;
								tX--;
								tY++;
							}
							if(tX-1>0&&tY+1<19&&(nCount+countConsecutive==3)&&(Main.bwr[tX][tY]==0)&&(Main.bwr[tX-1][tY+1]==1)&&(Main.w[tX-1][tY+1]==1)) {
								score=connect6ShapeScoreWhite(4, openEnds, 1); // currentTurn is black
								resultB[x][y]+=score;	
							}
							if((nCount+countConsecutive==5)||(nCount+countConsecutive==4)) {
								score=connect6ShapeScoreWhite(countConsecutive+nCount, openEnds, 1); // currentTurn is black
								resultW[x][y]+=score;
							}
						}
					}	
					
					//두 자리가 비어있고 연속된 경우
					if(x-2>0&&y+2<19&&(Main.bwr[x-1][y+1]==0&&Main.w[x-1][y+1]==0)&&(Main.bwr[x-2][y+2]==1&&Main.w[x-2][y+2]==1)) {
						//두자리가 비어있고 연속된 경우
						nCount=0;
						tX=x-2; //범위 설정
						tY=y+2;
						int end=(i + ub) >> 1;
						if(tX<end) {
							while(tX<19&&tY>0&&(Main.bwr[x-1][y+1] == 0)&& Main.bwr[tX][tY]==1&&Main.b[tX][tY]==1) {
								nCount++;
								tX--;
								tY++;
							}
							if(nCount+countConsecutive==4) {
								score=connect6ShapeScoreWhite(6, openEnds, 1); // currentTurn is black
								resultW[x][y]+=score;
							}
						}
					}					
					
					countConsecutive = 0;
					openEnds = 1;
				} else if (Main.bwr[x][y] == 0) // 빈 점이 그냥 등장할 경우
					openEnds = 1;
				else if (countConsecutive > 0) { // 연속점이 다른 돌에 만나서 끝났을 경우
					score= connect6ShapeScoreWhite(countConsecutive, openEnds, 0); // currentTurn is black
					resultW[x][y]+=score;
					countConsecutive = 0;
					openEnds = 0;
				} else
					openEnds = 0;
			}
			if (countConsecutive > 0) // 연속점이 벽에 만나서 끝났을 경우
				score = connect6ShapeScoreWhite(countConsecutive, openEnds, 0); // currentTurn is black
			//resultW[x][y]+=score;
			countConsecutive = 0;
			openEnds = 0;
		}
		return resultW;
	}

	// computer = BLACK 일때, 체크 
	public void compareWhite() {
		for (int i = 0; i < 19; i++) {
			for (int j = 0; j < 19; j++) {
				resultW[i][j]=0;
			}
		}
		
		analyzeHorizontalSetsForWhite();
		analyzeVerticalSetsForWhite();
		analyzeLeftDiagonalSetsForWhite();
		analyzeRightDiagonalSetsForWhite();
		
		for (int i = 0; i < 19; i++) {
			for (int j = 0; j < 19; j++) {
				System.out.print(resultW[i][j]);
			}
			System.out.println("");
		}

		int max = 0;
		aa =new Point();
		for (int i = 0; i < 19; i++) {
			for (int j = 0; j < 19; j++) {
				if((resultW[i][j]>max)&&Main.bwr[i][j]==0) {
					max=resultW[i][j];
					aa.x=i;
					aa.y=j;
				}
			}
		}
	}
	
	// computer = White 일때, 체크
	public void compareWhite2() {
		for (int i = 0; i < 19; i++) {
			for (int j = 0; j < 19; j++) {
				resultW[i][j]=0;
			}
		}
		
		analyzeHorizontalSetsForWhite();
		analyzeVerticalSetsForWhite();
		analyzeLeftDiagonalSetsForWhite();
		analyzeRightDiagonalSetsForWhite();
		
		System.out.println("This is white");
		for (int j = 0; j < 19; j++) {
			for (int i = 0; i < 19; i++) {
				System.out.print(resultW[i][j]+" ");
			}
			System.out.println("");
		}

		if(p==true) {
			int max = 100000000; //공격
			aa = new Point();
			aa.x=-1;aa.y=-1;
			for (int i = 0; i < 19; i++) {
				for (int j = 0; j < 19; j++) {
					if((resultW[i][j]>=max)&&Main.bwr[i][j]==0) {
						max=resultW[i][j];
						aa.x=i;
						aa.y=j;
						System.out.println("aa.x: "+aa.x+" aa.y: "+aa.y);
					}
				}
			}
			System.out.println("compareWhite2: "+max+"true1");
			System.out.println("aa.x: "+aa.x+" aa.y: "+aa.y);
			
			if(aa.x==-1&&aa.y==-1) {
				p=false;
			}
		}else {
			int max = 0; //공격
			aa = new Point();
			aa.x=-1;aa.y=-1;
			for (int i = 0; i < 19; i++) {
				for (int j = 0; j < 19; j++) {
					if((resultW[i][j]>=max)&&Main.bwr[i][j]==0) {
						max=resultW[i][j];
						aa.x=i;
						aa.y=j;
						System.out.println("aa.x: "+aa.x+" aa.y: "+aa.y);
					}
				}
			}
			System.out.println("compareWhite2: "+max+"false2");
			System.out.println("aa.x: "+aa.x+" aa.y: "+aa.y);
		}
		
		
	}
	
	public void firstBlack() { //computer==black 일 떄 , 첫 번째 위치를 지정하는 함수 
		aa = new Point();
		int min=5;
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
