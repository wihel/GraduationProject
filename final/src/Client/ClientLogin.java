package Client;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import Server_DB.LoginMemberProcess;

import Client.Lastseat.ChatEvent;





public class ClientLogin{// clientlogin 클래스 시작

	JTextField id;
	JTextField pc;
	private JPasswordField pass;
	private JFrame login;
	
	static ClientChat chat1;
	DataOutputStream out;
	Socket socket;
	DataInputStream in;
	
	Lastseat seat;
	
	JoinMember Join;
	
	String IdLogin;
	String password;
	
	boolean logIn;
	
	Lastseat cl;
	
	
	boolean Loginboolean = false;

	ClientLogin()// clientlogin 생성자 시작부
	{
		// 프레임 생성
		login = new JFrame("로그인");

		// 라벨 생성
		// 라벨 생성
	      JLabel id_label = new JLabel("");
	      id_label.setIcon(new ImageIcon("img\\id5.PNG"));
	      JLabel pass_label = new JLabel("");
	      pass_label.setIcon(new ImageIcon("img\\pass5.PNG"));
	      JLabel lblNewLabel = new JLabel("");
	      lblNewLabel.setIcon(new ImageIcon("img\\LOGIN5.PNG"));
	      JLabel pc_label = new JLabel("");
	      pc_label.setIcon(new ImageIcon("img\\seat5.PNG"));
	      JPanel panel = new JPanel();

	      // 버튼 생성
	      JButton join_btn = new JButton("");
	      join_btn.setIcon(new ImageIcon("img\\join5.PNG"));
	      JButton log_btn = new JButton("");
	      log_btn.setIcon(new ImageIcon("img\\log4.PNG"));
	      JButton exit_btn = new JButton("");
	      exit_btn.setIcon(new ImageIcon("img\\quit5.PNG"));

	      // id,pass,pc객체 생성
	      id = new JTextField();
	      login.getContentPane().add(id);
	      pc = new JTextField();
	      pass = new JPasswordField();

	      // 아이디 필드와 패스워드 필드 입력글자수 제한
	      pass.setDocument(new JTextFieldLimit(10));
	      id.setDocument(new JTextFieldLimit(10));
	      pc.setDocument(new JTextFieldLimit(2));
	      login.getContentPane().add(pass);
	      
	      id.setBounds(1593, 802, 125, 30);
	      pass.setBounds(1593, 844, 125, 30);
	      pc.setBounds(1593, 887, 125, 30);

	      // 현재 스크린사이즈를 받아온다
	      int width = Toolkit.getDefaultToolkit().getScreenSize().width;
	      int height = Toolkit.getDefaultToolkit().getScreenSize().height;

	      // //컴포넌트 배치부
	      id_label.setBounds(1486, 802, 105, 35);
	      pass_label.setBounds(1486, 844, 105, 35);
	      pc_label.setBounds(1486, 887, 105, 35);
	      log_btn.setBounds(1738, 803, 130, 70);
	      join_btn.setBounds(1486, 988, 105, 35);
	      exit_btn.setBounds(1760, 988, 105, 35);
	      lblNewLabel.setBounds(0, 0, 1920, 1080);
	      
	      
	      // 컴포턴트 결합부
	      panel.add(id);
	      panel.add(pass);
	      panel.add(pc);
	      panel.add(id_label);
	      panel.add(pass_label);
	      panel.add(join_btn);
	      panel.add(log_btn);
	      panel.add(exit_btn);
	      panel.add(pc_label);
	      panel.add(lblNewLabel);
	      panel.setLayout(null); // 앱솔루트 레이아웃
		login.add(panel);
		// 버튼에 이벤트 리스너 결합부
		exit_btn.addActionListener(new ExitProcess());
		join_btn.addActionListener(new JoinProcess());
		log_btn.addActionListener(new LoginProcess());

		login.setBounds(0, 0, 1920, 1080);
		login.setResizable(false);
		login.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		login.setVisible(true);
		
		new Thread(new ClientConnector()).start();
		
	}// client_login 생성자 종료부

	public ClientLogin(ClientChat a){
		this.chat1 = a;
		System.out.println(chat1);
	}
	
	
	// JTextFieldLimit클래스 시작(아이디 비번 입력 글자수 제한)

	private class JTextFieldLimit extends PlainDocument {
		private int limit; // 제한할 길이

		private JTextFieldLimit(int limit) // 생성자 : 제한할 길이를 인자로 받음
		{
			super();
			this.limit = limit;
		}

		// 텍스트 필드를 채우는 메써드 : 오버라이드
		public void insertString(int offset, String str, AttributeSet attr)
				throws BadLocationException {
			if (str == null)
				return;
			if (getLength() + str.length() <= limit)
				super.insertString(offset, str, attr);
		}
	}// JTextFieldLimit클래스 종료

	// ExitProcess 클래스 시작 (종료이벤트 구현)
	private class ExitProcess implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.exit(0);
		}

	}

	// ExitProcess 클래스 종료(종료이벤트 구현)

	// JoinProcess 클래스 시작 (회원가입 창을 띄운다)
	private class JoinProcess implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			Join = new JoinMember(out, in);
		}

	}
	// JoinProcess 클래스 종료

	// LoginProcess 클래스 시작 (login을 처리한다)
	private class LoginProcess implements ActionListener, Runnable{
	
		public void actionPerformed(ActionEvent arg0) {
			char [] pass1 = pass.getPassword();
			password = new String(pass1);
			IdLogin = id.getText();
			run();
			
			try {
				Integer.parseInt(pc.getText());
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "피시번호를 입력하세요.");
				return;
			}
			if(id.getText().equals("비회원")) {
				JOptionPane.showMessageDialog(null, "비회원으로 로그인 할 수 없습니다");
				login.dispose();
				
			} else if(id.getText().equals("")){
				JOptionPane.showMessageDialog(null, "비회원으로 로그인합니다.");
				login.dispose();
			
			} else {
				
				//LoginMemberProcess로 이동하여 LoginMember 메소드실행 후
				//아이디와 비밀번호가 DB값과 일치하면 True값을 반환
				//일치하지 않으면 False			
				//boolean logIn = LoginMemberProcess.loginMember(id.getText(),
						//password);
	
				if(logIn == false) {
					JOptionPane.showMessageDialog(null, "아이디가 존재하지 않거나 잘못 입력하셨습니다.");
				} else {
					int pcNum = Integer.parseInt(pc.getText());
					try {
						out.writeUTF("로그인");
						out.writeInt(pcNum);
						out.writeUTF(id.getText());
						//out.writeUTF("로그인#asmin/1234");
						
						out.flush();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
					JOptionPane.showMessageDialog(null, "로그인 되었습니다");
					login.dispose();
					
					
					Lastseat.doClient = true;
					cl = new Lastseat(id.getText(), pc.getText(), out);
					cl.display();	
				}
			}
		}
		
		
		@Override
		public void run() {
			try {
				out.writeUTF("아이디비밀번호체크");
				out.writeUTF(IdLogin);
				out.writeUTF(password);
				out.flush();
				
				Thread.sleep(1000);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
	}
	
	
	
	

	// 서버와 연결을 위한 클라이언트 커넥터
		public class ClientConnector implements Runnable {
			@Override
			public void run() {
				try {
					String serverIp = "127.0.0.1";// "172.168.0.80";
					socket = new Socket(InetAddress.getByName(serverIp), 7793);
					System.out.println("연결성공");
					in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
					out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
					
					/*while(Loginboolean == false) {
						System.out.println(out);
						new JoinMember(out);
						System.out.println("ㅇㅇ");
						Loginboolean = true;
						
					}*/
					
					/*int pcNum = Integer.parseInt(pc.getText());
					out.writeInt(pcNum);
					out.writeUTF(id.getText());
					//out.writeUTF("로그인#asmin/1234");
					out.writeUTF("로그인");
					out.flush();*/

					while (true) {
						String str = in.readUTF();
						System.out.println(str + "클라이언트 로그인부");
						// 채팅메시지 처리부
						if (str.equals("메시지")) {
							String msg = in.readUTF();
							System.out.println("##"+ chat1);
							if (chat1 == null) {
								System.out.println("!!!" + chat1);
								System.out.println(pc.getText());
								chat1 = new ClientChat(out, pc.getText());
							}
							chat1.chatFrameVisible();
							chat1.addChat(msg);
						}
						
						if(str.equals("아이디노중복")) {
							
							//Join.actionPerformed(dupl_id);
							Loginboolean = false;
							//new IdConfirm(Loginboolean);
							Join.LoginSuccess(Loginboolean);
						}
						if(str.equals("아이디중복")) {
							//Join.dupl_id = true;
							Loginboolean = true;
							Join.LoginFailed(Loginboolean);
							
							//Confirm.dupl_id = true;
							
							//Join.LoginFailed(Loginboolean);
						}
						
						if(str.equals("둘다일치")) {
							System.out.println("일치하냐?");
							logIn = true;
						}
						
						if(str.equals("불일치")) {
							System.out.println("불일치하냐?");
							logIn = false;
						}
						

						if (str.equals("요금정보")) {
							System.out.println("요금정보 쓰레드");
							Integer money = in.readInt();
							//lblmoney.setText(money.toString() + "원");
							//lbltime5.setText(in.readUTF());
							String Time = in.readUTF();
							String ClientMoney = money.toString();
							
							Lastseat a = new Lastseat(Time,ClientMoney,"a");
						}

						// 로그아웃 처리부
						if (str.equals("로그아웃")) {
							socket.close();
						}
					}

				} catch (IOException e) {// 서버와 연결이 끊어질시 창이 변함

					if (chat1 != null) {
						chat1.closeFrame();
					}
					//doClient = false;
					//dispose();
					JOptionPane.showMessageDialog(null, "서버와 연결이 끊겼습니다.");
					System.exit(0);
					//ClientLogin cl = new ClientLogin();

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
			
			
			
			

		}// 클라이언트 커넥터종료
		
		
	
	// LoginProcess 클래스 종료
}// client_login 클래스 종료부
