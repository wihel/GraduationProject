package Client;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import chat.Seat;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import Client.JoinMember;
import Client.ClientLogin;
import Server.ServerManager;
import ServerGUI.ManagerMain1;
import menulist.Menu;



public class Lastseat extends JFrame implements ActionListener {
	JFrame f=new JFrame();
	ServerManager vc = ServerManager.getInstance("라스트시트");

	String id;
	String pc;
	JPanel contentPane;
	int money; // 현재 사용요금
	int money2; // 현재 사용요금2
	int timer; // 현재 시간 클래스
	JLabel label; // 좌석 번호
	JLabel lblid2;
	JLabel lbltime2;
	JLabel lblmoney2;
	JLabel label2;
	JLabel lblid; // 사용자 아이디
	JLabel timemoney2;
	static JLabel lbltime5; // 사용자 이용시간 스레드
	String bank;
	static JLabel lblmoney; // 사용자 이용요금 스레드
	boolean Loginboolean = false;

	boolean logout = false;  
	boolean click = false;
	
	
	JButton btnchat = new JButton(); // 채팅버튼
	JButton btnmenu = new JButton(); // 주문버튼
	JButton btnend = new JButton(); // 종료버튼

	JScrollPane scrollPane;
	ImageIcon icon, image1;
	
	public Seat seat;

	public Seat[] pcseat = new Seat[50];

	ClientChat chat;
	DataOutputStream out;
	Socket socket;
	DataInputStream in;
	
	

	protected static boolean doClient = true;

	public Lastseat(String id, String pc, DataOutputStream out) {
		this.id = id;
		this.pc = pc;
		this.out = out;
		// setSize(500,500);
		// setVisible(true);

		label = new JLabel(pc);
		lblid2 = new JLabel("고객 아이디");
		lbltime2 = new JLabel("이용 시간");
		lblmoney2 = new JLabel("이용 요금");
		image1 = new ImageIcon(ManagerMain1.class.getResource("/logo/MainLogo.PNG")); // 이미지 경로 (label2 로고 이미지)
		label2 = new JLabel(image1);
		lblid = new JLabel(id);
		lbltime5 = new JLabel("");
		lblmoney = new JLabel("");
		btnchat = new JButton(new ImageIcon("img/chat.png"));
		btnchat.addActionListener(new ChatEvent());
		btnchat.setContentAreaFilled(false);
		btnchat.setFocusPainted(false);

		contentPane = new JPanel();

		btnmenu = new JButton(new ImageIcon("img/menu.png"));
		btnmenu.addActionListener(this);
		btnmenu.setContentAreaFilled(false);
		btnmenu.setFocusPainted(false);

		btnend = new JButton(new ImageIcon("img/end.png"));
		btnend.addActionListener(this);
		// btnend.setBorderPainted(false); // 윤관선 지우기
		btnend.setContentAreaFilled(false);
		btnend.setFocusPainted(false);

		label.setFont(new Font("맑은 고딕", Font.BOLD, 80));
		label.setForeground(Color.WHITE);
		lblid2.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		lblid2.setForeground(Color.WHITE);
		lbltime2.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		lbltime2.setForeground(Color.WHITE);
		lblmoney2.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		lblmoney2.setForeground(Color.WHITE);
		label2.setFont(new Font("맑은 고딕", Font.BOLD, 40));
		label2.setForeground(Color.WHITE);
		lblid.setForeground(Color.WHITE);
		lbltime5.setForeground(Color.WHITE);
		lblmoney.setForeground(Color.WHITE);
		btnchat.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		btnmenu.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		btnend.setFont(new Font("맑은 고딕", Font.BOLD, 16));

		label.setBounds(12, 10, 95, 91);
		lblid2.setBounds(40, 144, 112, 27);
		lbltime2.setBounds(40, 199, 87, 27);
		lblmoney2.setBounds(40, 251, 87, 27);
		label2.setBounds(153, 10, 240, 120);
		lblid.setBounds(329, 154, 57, 15);
		lbltime5.setBounds(330, 209, 200, 15);
		lblmoney.setBounds(338, 261, 57, 15);
		btnchat.setBounds(190, 302, 87, 62);
		btnmenu.setBounds(299, 302, 87, 62);
		btnend.setBounds(40, 302, 112, 62);

		contentPane.add(label);
		contentPane.add(lblid2);
		contentPane.add(lbltime2);
		contentPane.add(lblmoney2);
		contentPane.add(label2);
		contentPane.add(lblid);
		contentPane.add(lbltime5);
		contentPane.add(lblmoney);
		contentPane.add(btnchat);
		contentPane.add(btnmenu);
		contentPane.add(btnend);

		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setBackground(Color.black);
		contentPane.setLayout(null);
		// setLayout(null);
		/*
		 * Thread t1 = new Thread(new money01()); t1.start();
		 */
		/*
		 * Thread t2 = new Thread(new timer()); t2.start();
		 */

		//new Thread(new ClientConnector()).start();

	}
	
	Lastseat(String a, String b, String c){	
		String getTime = a;
		String getMoney = b;
		
		lbltime5.setText(getTime);
		lblmoney.setText(getMoney + "원");
	}

	void display() {
		setTitle("클라이언트 로그인 화면");
		setLayout(null);
		setBounds(1480, 0, 450, 424);
		setVisible(true);

		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

	}

	// 챗이벤트클래스 시작(채팅창을 불러온다)
	public class ChatEvent implements ActionListener {

		
		public void actionPerformed(ActionEvent e) {
			if (chat == null) {
				chat = new ClientChat(out, pc);
				new ClientLogin(chat);
				System.out.println("????" + chat);
				return;
			}
			chat.chatFrameVisible();
		}
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		// 종료버튼 누르면 사용요금과 함께 메세지 띄우기
		
		if (e.getSource() == btnmenu) {
			 //bugerHamberger4 burger = new bugerHamberger4();
			new Menu(Integer.parseInt(pc), out);
		}

		if (e.getSource() == btnend) {
			System.out.println("로그아웃 버튼");			
			logout = true;
			if(logout == true) {
				JOptionPane.showMessageDialog(null, lblmoney.getText() + "입니다. 이용해주셔서 감사합니다." + "");
				System.out.println("클라이언트 로그아웃 쓰레드");
				try {
					out.writeUTF("로그아웃");
					out.flush();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			click = true;
			String Moneysum = lblmoney.getText();
			
			try {
				out.writeUTF("이용종료메시지");
				out.writeUTF(Moneysum);
				out.writeUTF(pc);
				out.flush();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			dispose();
		}

	}

	// 서버와 연결을 위한 클라이언트 커넥터
	/*private class ClientConnector implements Runnable {
		@Override
		public void run() {
			try {
				String serverIp = "127.0.0.1";// "172.168.0.80";
				socket = new Socket(InetAddress.getByName(serverIp), 7793);
				System.out.println("연결성공");
				in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
				out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
				
				while(Loginboolean == true) {
					JoinMember a = new JoinMember(out);
					
				}
				
				int pcNum = Integer.parseInt(pc);
				out.writeInt(pcNum);
				out.writeUTF(id);
				//out.writeUTF("로그인#asmin/1234");
				out.writeUTF("로그인");
				out.flush();

				while (true) {
					String str = in.readUTF();
					// 채팅메시지 처리부
					if (str.equals("메시지")) {
						String msg = in.readUTF();
						if (chat == null) {
							chat = new ClientChat(out, pc);
						}
						chat.chatFrameVisible();
						chat.addChat(msg);
					}
					
					

					if (str.equals("요금정보")) {
						System.out.println("요금정보 쓰레드");
						Integer money = in.readInt();
						lblmoney.setText(money.toString() + "원");
						lbltime5.setText(in.readUTF());
					}

					// 로그아웃 처리부
					if (str.equals("로그아웃")) {
						socket.close();
					}
				}

			} catch (IOException e) {// 서버와 연결이 끊어질시 창이 변함

				if (chat != null) {
					chat.closeFrame();
				}
				doClient = false;
				dispose();
				ClientLogin cl = new ClientLogin();

			} finally {
				if (in != null) {
					try {
						in.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (out != null) {
					try {
						out.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

		}

	}*/  // 클라이언트 커넥터종료

	// 요금 계산 스레드
	/*
	 * class money01 implements Runnable {
	 * 
	 * public void run() { money = 0; try { while(!click) { // 좌석에서 6분이 지날때마다 100원씩
	 * 늘린다. 비회원인 경우 // 지금은 시험을 위해 1초가 지날때마다 100원씩 늘린다. Thread.sleep(6000); money +=
	 * 100; lblmoney.setText(money + "원");
	 * 
	 * }//while }//try catch (InterruptedException e) { e.printStackTrace(); }
	 * lblmoney.setText((money-100) + "원"); }//run }//money
	 */

	// 이용시간 스레드
	/*
	 * class timer extends Thread { public void run() { int hour = 0, min = 0, sec =
	 * 0;
	 * 
	 * // 시험을 위해 10초 단위로 변경한다. while (!click == true) { sec++; if (sec % 60 == 0) {
	 * min++; sec = 0; if (min % 60 == 0) { min = 0; hour++; } } try { if(click ==
	 * true) {
	 * 
	 * }
	 * 
	 * Thread.sleep(1000); out.writeUTF("시간"); lbltime5.setText(hour + "시간" + min +
	 * "분" + sec + "초"); out.writeUTF(hour + "시간" + min + "분" + sec + "초"); }
	 * 
	 * catch (InterruptedException e) { System.out.println("Seat : 로그아웃을 한듯하네요~!");
	 * break; } catch (IOException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); }
	 * 
	 * }
	 * 
	 * 
	 * //lbltime5.setText(hour + "시간" + (min-1) + "분" + (sec=59) + "초");
	 * 
	 * //if(sec==0) //lbltime5.setText(hour + "시간" + (min-1) + "분" + (sec=59) +
	 * "초"); //else // lbltime5.setText(hour + "시간" + min + "분" + (sec-1) + "초"); }
	 * }
	 */
}
