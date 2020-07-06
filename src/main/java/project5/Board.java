package project5;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.Timer;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Board extends JPanel implements MouseListener , MouseMotionListener{

	Board(){
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		//repaint();
		
		repaint();
	}
	
	
	public void paintComponent(Graphics g) {
		Color brown = new Color(204,153,051);
		g.setColor(brown);
		g.fillRect(5, 5, 460, 460);
		
		for(int i=0;i<19;i++) {
			g.setColor(Color.black);
			g.drawLine(10,10+25*i,460, 10+25*i);
			g.drawLine(10+25*i, 10,10+25*i, 460);
		}
		for(int i=0;i<3;i++) {
			for(int j=0;j<3;j++) {
				g.fillOval(82+150*i, 82+150*j,6,6);
			}
		}
		

		for(Point t: Main.bwList) {
			if(Main.r[t.x][t.y]==1) {
				g.setColor(Color.RED);
			}
			if(Main.b[t.x][t.y]==1) {
				g.setColor(Color.black);
			}
			if(Main.w[t.x][t.y]==1) {
				g.setColor(Color.white);
			}
			g.fillOval(25*t.x, 25*t.y,20,20);
		}
		
		//timer.stop();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		//System.out.println(e.getPoint().x+" " +e.getPoint().y);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		for(int i=0;i<19;i++) {
			for(int j=0;j<19;j++) {
				if((e.getPoint().x>=0+25*i&&e.getPoint().x<=25*i+20)&&(e.getPoint().y>=25*j&&e.getPoint().y<=25*j+20)) {
					if(Main.bwr[i][j]==0) {
						Main.bwr[i][j]=1;
						Point t= new Point();
						t.x=i; 
						t.y=j;
						Main.bwList.add(t);
						if(Main.count<6) {
							Main.wLabel.setVisible(true);
							Main.bLabel.setVisible(true);
							Main.r[i][j]=1;
							Main.count++;
						}else if(Main.count==6){
							Main.wLabel.setVisible(false);
							Main.bLabel.setVisible(true);
							Main.b[i][j]=1;
							Main.count++;
						}else if(Main.count%4==3||Main.count%4==0){
							Main.wLabel.setVisible(true);
							Main.bLabel.setVisible(false);
							Main.w[i][j]=1;
							Main.count++;
							if(winCheck(Main.w,i,j)) {
								repaint();
								Object[] options = {"재시작",
			                    "종료"};
								int n = JOptionPane.showOptionDialog(Main.board,"WHITE WIN!","",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,options, options[0]);
								if(n==0) {
									clearArr();//배열 클리어하기
								}else {
									clearArr();//배열 클리어하기
									quit();
								}
								
							}
						}else if(Main.count%4==1||Main.count%4==2){
							Main.wLabel.setVisible(false);
							Main.bLabel.setVisible(true);
							Main.b[i][j]=1;
							Main.count++;
							if(winCheck(Main.b,i,j)) {
								repaint();
								Object[] options = {"재시작",
			                    "종료"};
								int n = JOptionPane.showOptionDialog(Main.board,"BLACK WIN!","",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,options, options[0]);
								if(n==0) {
									clearArr();//배열 클리어하기
								}else {
									clearArr();//배열 클리어하기
									quit();
								}
							}
							//Main.count++;
						}
						repaint();
						if(Main.count>=6) {
							if(Main.count%4==0||Main.count%4==2) {
								Main.timer.stop();
								Main.tMax=15;
								Main.tCount=0;
								Main.timer.restart();
							}
						}
						
					}
					
				}
			}
			//g.setColor(Color.black);
			//g.drawLine(10,10+25*i,460, 10+25*i);
			//g.drawLine(10+25*i, 10,10+25*i, 460);
		}
		System.out.println(e.getPoint().x+" " +e.getPoint().y);
	}

	public boolean winCheck(int[][] arr, int i, int j) {
		int tI, tJ, sCount;
		
		//가로 검사
		tI=i; tJ=j; sCount=1;
		while(tI>0) {
			tI--;
			if(arr[tI][tJ]==1) {
				sCount++;
			}else {
				break;
			}
		}
		tI=i;
		while(tI<18) {
			tI++;
			if(arr[tI][tJ]==1) {
				sCount++;
			}else {
				break;
			}
		}
		if(sCount==6) {
			return true;
		}
		
		//세로 검사
		tI=i; tJ=j; sCount=1;
		while(tJ>0) {
			tJ--;
			if(arr[tI][tJ]==1) {
				sCount++;
			}else {
				break;
			}
		}
		tJ=j;
		while(tJ<18) {
			tJ++;
			if(arr[tI][tJ]==1) {
				sCount++;
			}else {
				break;
			}
		}
		if(sCount==6) {
			return true;
		}
		
		//대각선 검사1
		tI=i; tJ=j; sCount=1;
		while(tJ>0&&tI>0) {
			tJ--;tI--;
			if(arr[tI][tJ]==1) {
				sCount++;
			}else {
				break;
			}
		}
		tI=i; tJ=j;
		while(tJ<18&&tI<18) {
			tJ++;tI++;
			if(arr[tI][tJ]==1) {
				sCount++;
			}else {
				break;
			}
		}
		if(sCount==6) {
			return true;
		}
		//대각선 검사2
		tI=i; tJ=j; sCount=1;
		while(tJ<18&&tI>0) {  //왼
			tJ++;tI--;
			if(arr[tI][tJ]==1) {
				sCount++;
			}else {
				break;
			}
		}
		tI=i; tJ=j;
		while(tJ>0&&tI<18) {  //오
			tJ--;tI++;
			if(arr[tI][tJ]==1) {
				sCount++;
			}else {
				break;
			}
		}
		if(sCount==6) {
			return true;
		}
		return false;
		
	}
	
	void clearArr() {
		for(int i=0;i<19;i++) {
			for(int j=0;j<19;j++) {
				Main.b[i][j]=0;
				Main.w[i][j]=0;
				Main.r[i][j]=0;
				Main.bwr[i][j]=0;
			}
		}
		Main.bwList.clear();
		Main.count=0;
		Main.timer.stop();
		Main.timerNumber.setText("0");
	}
	void quit() {
		Main.startpanel.setVisible(true);
		Main.btnPlusTime.setVisible(false);
		
	}
	
	
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		//System.out.println(e.getPoint().x+" " +e.getPoint().y);
	}
	
}
