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



public class ClientChat { // Ŭ���̾�Ʈ ê Ŭ���� ����-ä���� �ϱ����� Ŭ����

	private DataOutputStream out;
	private String pc;
	private JFrame chatFrame;
	private JTextField text;
	private JTextArea textArea;
	private String connectState;
	
	ClientChat(DataOutputStream out, String pc){ // Ŭ���̾�Ʈê ������ ����
		this.out = out;
		this.pc = pc;
		
		chatFrame = new JFrame("�����ڿ��� �޽��� ����");
		
		//���� ��ũ������� �޾ƿ´�.
		int width = Toolkit.getDefaultToolkit().getScreenSize().width/3;
		int height = Toolkit.getDefaultToolkit().getScreenSize().height/4;
		
		// ������Ʈ ����
		textArea = new JTextArea();
		text = new JTextField(25);
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		textArea.setEditable(false);
		JPanel south = new JPanel();
		JButton transBtn = new JButton("����");
		JScrollPane center  = new JScrollPane(textArea,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		//������Ʈ ����
		south.add(text);
		south.add(transBtn);
		//�̺�Ʈ �߰�
		transBtn.addActionListener(new TransBtnEvent());
		text.addActionListener(new TransKeyEvent());
		
		chatFrame.add(south, "South");
		chatFrame.add(center, "Center");
		chatFrame.setBounds(width, height, 400, 300);
		chatFrame.setResizable(false); //â ũ�� ���� �Ұ���  True = ���� ����
		chatFrame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		chatFrame.setVisible(true); //ȭ�鿡 ���̰� �ϱ�
		
	} //Ŭ���̾�Ʈ ê ������ ����
	
	
	private class TransBtnEvent implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			try {
				out.writeUTF("�޽���");
				String msg = pc + "�� PC :" +text.getText() + "\n";		
				text.setText("");
				textArea.append(msg); //������ ���� ������ �ؽ�Ʈ�� �߰��Ѵ�.
				out.writeUTF(msg);
				out.flush();
			} catch (IOException e) {
				chatFrame.dispose(); 
				//  * ���ϴ� �ϳ��� Frame�� ���� ��Ű�� ���ؼ��� dispose() �޼ҵ带 
				//    ����Ͽ��� �Ѵ�.
			}
			
		}
	}
	// ä�� �̺�Ʈ ó�� ��ư Ŭ���� ����
	
	
	// ä�� �̺�Ʈ ó�� Ű������ Ŭ���� ����
	private class TransKeyEvent implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			try {
				out.writeUTF("�޽���");
				String msg = pc + "�� PC : " + text.getText() + "\n";
				text.setText("");
				textArea.append(msg); //������ ���� ������ �ؽ�Ʈ�� �߰��Ѵ�.
				out.writeUTF(msg);
				out.flush(); //���ۿ� �����ִ� �����͸� ������ ����
				
			} catch(IOException e) {
				chatFrame.dispose();
			}
			
			
		}
	}
	// ä�� �̺�Ʈ ó�� Ű������ Ŭ���� ����
	
	
	//�����ڿ��Լ� ���� �޽����� ǥ���ϱ� ���� �޼ҵ� ����
	void addChat(String s) {
		textArea.append(s + "\n");
	}
	//�����ڿ��Լ� ���� �޽����� ǥ���ϱ� ���� �޼ҵ� ����
	
	
	// ��ſ�����¿� ���� ä��â�� �����ϱ� ���� �޼ҵ� ����
	void closeFrame() {
		if(chatFrame != null) {
			chatFrame.dispose();
		}
	}
	// ��ſ�����¿� ���� ä��â�� �����ϱ� ���� �޼ҵ� ����
	
	// ������ ä��â �ٽú��̰� �ϱ� ���� �޼ҵ� ����
	void chatFrameVisible() {
		chatFrame.setVisible(true);
	}

} //Ŭ���̾�Ʈ ê Ŭ���� ����
