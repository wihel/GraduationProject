package Client;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

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

import Client.ClientLogin;
import Server_DB.IdCheckProcess;
import Server_DB.JoinMemberProcess;

public class JoinMember{

	boolean dupl_id;
	private DataOutputStream out;
	private DataInputStream in;
	private JFrame join;
	private JTextField id;
	private JTextField tel;
	private JTextField age;
	private JPasswordField pass; // 비밀번호
	private JPasswordField comp_pass; // 비밀번호 확인 필드
	private String reConfirmId;
	
	String IdCheck;
	
	// 아이디 중복인지 검사
	JoinMember member;
	
	
	JoinMember(DataOutputStream out, DataInputStream in){
		this.out = out;
		this.in = in;
			
		
		// 회워가입 창 프레임 생성
		join = new JFrame("회원가입");
		
		//텍스트 필드
		id = new JTextField();
		pass = new JPasswordField();
		tel = new JTextField();
		age = new JTextField();
		
		// 텍스트 필드 제한 설정
	      // 텍스트 필드 제한 설정
	      id.setDocument(new JTextFieldLimit(10));
	      pass.setDocument(new JTextFieldLimit(10));
	      tel.setDocument(new JTextFieldLimit(11));
	      age.setDocument(new JTextFieldLimit(6));
	      
	      //라벨 생성
	      JLabel id_label = new JLabel("아이디");
	      JLabel idN_label = new JLabel("*아이디 1 ~ 10자");
	      JLabel pass_label = new JLabel("비밀번호");
	      JLabel passN_label = new JLabel("*비밀번호 1 ~ 10자");
	      JLabel tel_label = new JLabel("핸드폰 번호");
	      JLabel telH_label = new JLabel(" ('-' 제외)");
	      JLabel age_label = new JLabel("생년월일");
	      JLabel age_labelE = new JLabel("ex)960704");
	      
	      //버튼 생성
	      JButton id_btn =  new JButton("중복확인");
	      JButton join_btn = new JButton("회원가입");
	      JButton close_btn = new JButton("닫기");
	      
	      //컴포넌트 사이즈 및 배치설정
	      id.setBounds(95,40,85,20);
	      pass.setBounds(95,88,105,20);
	      tel.setBounds(95, 143, 120, 20);
	      age.setBounds(95, 174, 60, 20);
	      id_label.setBounds(47, 35, 85, 30);
	      idN_label.setBounds(83,55,130,30);
	      pass_label.setBounds(35, 85, 105, 30);
	      passN_label.setBounds(83,105,130,30);
	      tel_label.setBounds(22, 137, 105, 30);
	      telH_label.setBounds(220,137, 105,30);
	      age_label.setBounds(36, 170, 105, 30);
	      age_labelE.setBounds(163,170,105,30);
	      id_btn.setBounds(195, 37, 105, 25);
	      join_btn.setBounds(70, 310, 90, 25); 
	      close_btn.setBounds(175, 310, 90, 25);
	      JPanel panel = new JPanel();
	      
	      //버튼 이벤트 결합부
	      id_btn.addActionListener(new IdConfirm());
	      join_btn.addActionListener(new JoinProcess());
	      close_btn.addActionListener(new CloseProcess());
	      
	      int width = Toolkit.getDefaultToolkit().getScreenSize().width;
	      int height = Toolkit.getDefaultToolkit().getScreenSize().height;
	      
	      panel.setLayout(null);
	      panel.add(id_btn);
	      panel.add(id_label);
	      panel.add(pass_label);
	      panel.add(tel_label);
	      panel.add(tel);
	      panel.add(id);
	      panel.add(pass);
	      panel.add(join_btn);
	      panel.add(close_btn);
	      panel.add(age_label);
	      panel.add(age);
	      panel.add(idN_label);
	      panel.add(passN_label);
	      panel.add(telH_label);
	      panel.add(age_labelE);
	      
	      
	      
	      join.add(panel);
	      join.setBounds(width/3, height/4, 330, 380);
		join.setResizable(false);
		join.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
		join.setVisible(true);
	} // joinMember 생성자 종료
	
	
	public boolean LoginSuccess(boolean a) {
		dupl_id = a;
		return dupl_id;
		
	}
	
	public boolean LoginFailed(boolean b) {
		dupl_id = b;
		return dupl_id;
	}
	
	// JTextFieldLimit클래스 시작(텍스트필드 제한)
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
		
	// IdConfirm클래스 아이디 중복확인 이벤트 처리 부
	
		
	private class IdConfirm implements ActionListener,Runnable{
		
		public void actionPerformed(ActionEvent arg0) {
			IdCheck = id.getText();
			System.out.println(dupl_id);
			run();
			
			
			System.out.println(dupl_id);
			
			if (id.getText().equals("비회원"))// 아이디 검사
			{
				JOptionPane.showMessageDialog(null, "아이디로 '비회원'을 쓸수 없습니다.");
			} else if (id.getText().equals("")) {

				JOptionPane.showMessageDialog(null, "아이디를 입력해 주세요");
			} else {
				//DB애 id가 중복인지 IdCheckProcess로 가서 메소드를 실행 후 
				//중복이면 True, 중복이 아니면 False를 반환
				//dupl_id = IdCheckProcess.checkId(id.getText());
				//member.LoginSuccess(dupl_id);
				
				//ClientLogin a;
				//dupl_id = a.LoginCheck();
				if (dupl_id == false) {
					JOptionPane.showMessageDialog(null, "사용 가능한 아이디입니다.");
					reConfirmId = id.getText();
				} else if(dupl_id == true){
					JOptionPane.showMessageDialog(null, "중복된 아이디입니다.");					
				}
			}
		}

		@Override
		public void run() {
			try {
				out.writeUTF("아이디체크");
				out.writeUTF(IdCheck);
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
		
		
	
	
	private class JoinProcess implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			char [] pass1 = pass.getPassword();
			String password = new String(pass1);
			
			int input_age;
			try {
				input_age = Integer.parseInt(age.getText());
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "나이에 숫자를 입력해주세요");
				return;
			}
			
			if(dupl_id == false) { // 아이디 중복검사 통과했고 아이디 중복검사 체크를 실행했으면
				//dupl_id = IdCheckProcess.checkId(id.getText());
				
				if(id.getText().equals("비회원")) {
					JOptionPane.showMessageDialog(null, "아이디로 비회원을 쓸수 없습니다.");
					dupl_id = true;
				} else if(id.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "아이디를 입력해주세요");
				} else if(dupl_id == true) {
					JOptionPane.showMessageDialog(null,"아이디 중복검사를 해주세요");
					// 혹시라도 유저가 회원가입시 중복검사 버튼 누르고 가입시는 다른아이디로 가입할 때 검사
				} else if(!reConfirmId.equals(id.getText())) {
					JOptionPane.showMessageDialog(null, "아이디 중복검사를 다시 해주세요");
				} else {
					String Joinid = id.getText();
					String Jointel = tel.getText();
					
					try {
						out.writeUTF("회원가입");
						out.writeUTF(Joinid);
						out.writeUTF(password);
						out.writeUTF(Jointel);
						out.writeInt(input_age);
						out.flush();
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					//JoinMemberProcess.insertMember(id.getText(), password,
							//tel.getText(), input_age);
					
					
					System.out.println(password);
					JOptionPane.showMessageDialog(null, "회원가입에 성공하셨습니다.");
					join.dispose();
				}
			} else {
				JOptionPane.showMessageDialog(null, "아이디 중복검사를 다시 해주세요.");
			}
			
		}

	}
	
	// CloseProcess 클래스 시작
		private class CloseProcess implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				join.dispose();

			}

		}
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
