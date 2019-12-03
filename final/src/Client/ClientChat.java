package Client;


import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;



public class ClientChat { // 클라이언트 챗 클래스 시작-채팅을 하기위한 클래스

	private DataOutputStream out;
	private String pc;
	private JFrame chatFrame;
	private JTextField text;
	private JTextArea textArea;
	private String connectState;
	
	ClientChat(DataOutputStream out, String pc){ // 클라이언트챗 생성자 시작
		this.out = out;
		this.pc = pc;
		
		chatFrame = new JFrame("관리자에게 메시지 전송");
		
		//현재 스크린사이즈를 받아온다.
		int width = Toolkit.getDefaultToolkit().getScreenSize().width/3;
		int height = Toolkit.getDefaultToolkit().getScreenSize().height/4;
		
		// 컴포넌트 정의
		textArea = new JTextArea();
		text = new JTextField(25);
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		textArea.setEditable(false);
		JPanel south = new JPanel();
		JButton transBtn = new JButton("전송");
		JScrollPane center  = new JScrollPane(textArea,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		//컴포넌트 결합
		south.add(text);
		south.add(transBtn);
		//이벤트 추가
		transBtn.addActionListener(new TransBtnEvent());
		text.addActionListener(new TransKeyEvent());
		
		chatFrame.add(south, "South");
		chatFrame.add(center, "Center");
		chatFrame.setBounds(width, height, 400, 300);
		chatFrame.setResizable(false); //창 크기 변경 불가능  True = 변경 가능
		chatFrame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		chatFrame.setVisible(true); //화면에 보이게 하기
		
	} //클라이언트 챗 생성자 종료
	
	
	private class TransBtnEvent implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			try {
				out.writeUTF("메시지");
				String msg = pc + "번 PC :" +text.getText() + "\n";		
				text.setText("");
				textArea.append(msg); //문서의 끝에 지정된 텍스트를 추가한다.
				out.writeUTF(msg);
				out.flush();
			} catch (IOException e) {
				chatFrame.dispose(); 
				//  * 원하는 하나의 Frame만 종료 시키기 위해서는 dispose() 메소드를 
				//    사용하여야 한다.
			}
			
		}
	}
	// 채팅 이벤트 처리 버튼 클래스 종료
	
	
	// 채팅 이벤트 처리 키리스너 클래스 시작
	private class TransKeyEvent implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			try {
				out.writeUTF("메시지");
				String msg = pc + "번 PC : " + text.getText() + "\n";
				text.setText("");
				textArea.append(msg); //문서의 끝에 지정된 텍스트를 추가한다.
				out.writeUTF(msg);
				out.flush(); //버퍼에 남아있는 데이터를 강제로 빼냄
				
			} catch(IOException e) {
				chatFrame.dispose();
			}
			
			
		}
	}
	// 채팅 이벤트 처리 키리스너 클래스 종료
	
	
	//관리자에게서 받은 메시지를 표시하기 위한 메소드 시작
	void addChat(String s) {
		textArea.append(s + "\n");
	}
	//관리자에게서 받은 메시지를 표시하기 위한 메소드 종료
	
	
	// 통신연결상태에 따라 채팅창을 종료하기 위한 메소드 시작
	void closeFrame() {
		if(chatFrame != null) {
			chatFrame.dispose();
		}
	}
	// 통신연결상태에 따라 채팅창을 종료하기 위한 메소드 종료
	
	// 숨겨진 채팅창 다시보이게 하기 위한 메소드 시작
	void chatFrameVisible() {
		chatFrame.setVisible(true);
	}

} //클라이언트 챗 클래스 종료
