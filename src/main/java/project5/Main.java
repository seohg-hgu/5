package project5;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.SwingConstants;

public class Main extends JFrame implements ActionListener{

	private JPanel contentPane; //기본 패널
	static JPanel startpanel;//시작화면 패널
	static JButton btnPlusTime; //시간 추가 버튼
	static String MODE=""; //?
	static String stoneColor="";  //?
	static int count=0; //기본 count
	static int tCount=0; // 시간 count
	static int tMax=15; //시간 추가
	static Board board; //바둑판 패널
	static JLabel timerNumber; //타이머 남은 시간 
	static Timer timer; //타이머
	static JLabel bLabel; 
	static JLabel wLabel;
	static int[][] b = new int[19][19];
	static int[][] w = new int[19][19];
	static int[][] r = new int[19][19];
	static int[][] bwr = new int[19][19];
	static ArrayList<Point> bwList = new ArrayList<Point>();
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Main() {
		//기본 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 850, 515);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//게임 화면
		board = new Board();
		board.setBounds(0, 0, 470, 515);
		board.setVisible(true);
		
		//시작 화면
		startpanel=new JPanel();
		startpanel.setBounds(0,0, 850, 515);
		startpanel.setBackground(Color.pink);
		startpanel.setVisible(true);
		
		JLabel startBackground = new JLabel("");
		startBackground.setIcon(new ImageIcon("D:\\배경\\라바시작.jpg"));
		startBackground.setBounds(0, 0, 850, 515);
		startpanel.setVisible(true);
		
		JButton btnStart = new JButton("Start");
		btnStart.setFont(new Font("Microsoft YaHei UI Light", Font.BOLD, 14));
		btnStart.setBounds(370, 220, 120, 60);
		startBackground.add(btnStart);
		
		contentPane.add(startpanel);
		startpanel.add(startBackground);
		//시작화면 버튼
		btnStart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub	
				if(e.getSource()==btnStart) {
					MODE="GAME";
					startpanel.setVisible(false);
					board.setVisible(true);
					btnPlusTime.setVisible(true);
					//board.timer.restart();
				}
			}	
		});
		board.setLayout(null);
		
		contentPane.add(board);
		
		timerNumber=new JLabel("Timer");
		timerNumber.setFont(new Font("굴림", Font.PLAIN, 15));
		timerNumber.setBounds(488, 137, 104, 50);
		timerNumber.setBackground(Color.white);
		contentPane.add(timerNumber);
		
		timer = new Timer(1000,new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int tmp=count;
				tCount++;
				// TODO Auto-generated method stub
				
				System.out.println(tCount);
				Main.timerNumber.setText(String.valueOf(tMax-tCount));
				//timerNumber.setText("");
				//repaint();
				
				
				if(tCount==tMax) {
					System.out.println(count);
					tCount=0;
					if((count%4)%2==1) {
						count+=2;
						System.out.println(count);
						tMax=15;
					}else {
						count++;
						System.out.println(count);
						tMax=15;
					}
					timer.restart();
				}
				
			}
		});
		

		
		
		JLabel lableTimer = new JLabel("Timer");
		lableTimer.setBounds(492, 77, 100, 50);
		contentPane.add(lableTimer);
		lableTimer.setFont(new Font("굴림", Font.PLAIN, 15));
		lableTimer.setBackground(Color.white);
		
		bLabel = new JLabel("BLACK");
		bLabel.setBackground(Color.BLACK);
		bLabel.setBounds(500, 300, 47, 50);
		contentPane.add(bLabel);
		
		wLabel = new JLabel("WHITE");
		wLabel.setForeground(Color.WHITE);
		wLabel.setBackground(Color.BLACK);
		wLabel.setBounds(550, 300, 47, 50);
		contentPane.add(wLabel);
		
		btnPlusTime = new JButton("시간추가");
		btnPlusTime.setForeground(Color.BLACK);
		btnPlusTime.setBackground(Color.LIGHT_GRAY);
		btnPlusTime.setBounds(492, 17, 100, 50);
		contentPane.add(btnPlusTime);
		
		//게임화면 버튼
		btnPlusTime.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub	
				if(e.getSource()==btnPlusTime) {
					MODE="GAME";
					tMax+=2;
					//board.timer.restart();
				}
			}	
		});

	

		
		
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
