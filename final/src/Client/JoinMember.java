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
	private JPasswordField pass; // ��й�ȣ
	private JPasswordField comp_pass; // ��й�ȣ Ȯ�� �ʵ�
	private String reConfirmId;
	
	String IdCheck;
	
	// ���̵� �ߺ����� �˻�
	JoinMember member;
	
	
	JoinMember(DataOutputStream out, DataInputStream in){
		this.out = out;
		this.in = in;
			
		
		// ȸ������ â ������ ����
		join = new JFrame("ȸ������");
		
		//�ؽ�Ʈ �ʵ�
		id = new JTextField();
		pass = new JPasswordField();
		tel = new JTextField();
		age = new JTextField();
		
		// �ؽ�Ʈ �ʵ� ���� ����
	      // �ؽ�Ʈ �ʵ� ���� ����
	      id.setDocument(new JTextFieldLimit(10));
	      pass.setDocument(new JTextFieldLimit(10));
	      tel.setDocument(new JTextFieldLimit(11));
	      age.setDocument(new JTextFieldLimit(6));
	      
	      //�� ����
	      JLabel id_label = new JLabel("���̵�");
	      JLabel idN_label = new JLabel("*���̵� 1 ~ 10��");
	      JLabel pass_label = new JLabel("��й�ȣ");
	      JLabel passN_label = new JLabel("*��й�ȣ 1 ~ 10��");
	      JLabel tel_label = new JLabel("�ڵ��� ��ȣ");
	      JLabel telH_label = new JLabel(" ('-' ����)");
	      JLabel age_label = new JLabel("�������");
	      JLabel age_labelE = new JLabel("ex)960704");
	      
	      //��ư ����
	      JButton id_btn =  new JButton("�ߺ�Ȯ��");
	      JButton join_btn = new JButton("ȸ������");
	      JButton close_btn = new JButton("�ݱ�");
	      
	      //������Ʈ ������ �� ��ġ����
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
	      
	      //��ư �̺�Ʈ ���պ�
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
	} // joinMember ������ ����
	
	
	public boolean LoginSuccess(boolean a) {
		dupl_id = a;
		return dupl_id;
		
	}
	
	public boolean LoginFailed(boolean b) {
		dupl_id = b;
		return dupl_id;
	}
	
	// JTextFieldLimitŬ���� ����(�ؽ�Ʈ�ʵ� ����)
		private class JTextFieldLimit extends PlainDocument {
			private int limit; // ������ ����

			private JTextFieldLimit(int limit) // ������ : ������ ���̸� ���ڷ� ����
			{
				super();
				this.limit = limit;
			}

			// �ؽ�Ʈ �ʵ带 ä��� �޽�� : �������̵�
			public void insertString(int offset, String str, AttributeSet attr)
					throws BadLocationException {
				if (str == null)
					return;

				if (getLength() + str.length() <= limit)
					super.insertString(offset, str, attr);
			}
		}// JTextFieldLimitŬ���� ����
		
	// IdConfirmŬ���� ���̵� �ߺ�Ȯ�� �̺�Ʈ ó�� ��
	
		
	private class IdConfirm implements ActionListener,Runnable{
		
		public void actionPerformed(ActionEvent arg0) {
			IdCheck = id.getText();
			System.out.println(dupl_id);
			run();
			
			
			System.out.println(dupl_id);
			
			if (id.getText().equals("��ȸ��"))// ���̵� �˻�
			{
				JOptionPane.showMessageDialog(null, "���̵�� '��ȸ��'�� ���� �����ϴ�.");
			} else if (id.getText().equals("")) {

				JOptionPane.showMessageDialog(null, "���̵� �Է��� �ּ���");
			} else {
				//DB�� id�� �ߺ����� IdCheckProcess�� ���� �޼ҵ带 ���� �� 
				//�ߺ��̸� True, �ߺ��� �ƴϸ� False�� ��ȯ
				//dupl_id = IdCheckProcess.checkId(id.getText());
				//member.LoginSuccess(dupl_id);
				
				//ClientLogin a;
				//dupl_id = a.LoginCheck();
				if (dupl_id == false) {
					JOptionPane.showMessageDialog(null, "��� ������ ���̵��Դϴ�.");
					reConfirmId = id.getText();
				} else if(dupl_id == true){
					JOptionPane.showMessageDialog(null, "�ߺ��� ���̵��Դϴ�.");					
				}
			}
		}

		@Override
		public void run() {
			try {
				out.writeUTF("���̵�üũ");
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
				JOptionPane.showMessageDialog(null, "���̿� ���ڸ� �Է����ּ���");
				return;
			}
			
			if(dupl_id == false) { // ���̵� �ߺ��˻� ����߰� ���̵� �ߺ��˻� üũ�� ����������
				//dupl_id = IdCheckProcess.checkId(id.getText());
				
				if(id.getText().equals("��ȸ��")) {
					JOptionPane.showMessageDialog(null, "���̵�� ��ȸ���� ���� �����ϴ�.");
					dupl_id = true;
				} else if(id.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "���̵� �Է����ּ���");
				} else if(dupl_id == true) {
					JOptionPane.showMessageDialog(null,"���̵� �ߺ��˻縦 ���ּ���");
					// Ȥ�ö� ������ ȸ�����Խ� �ߺ��˻� ��ư ������ ���Խô� �ٸ����̵�� ������ �� �˻�
				} else if(!reConfirmId.equals(id.getText())) {
					JOptionPane.showMessageDialog(null, "���̵� �ߺ��˻縦 �ٽ� ���ּ���");
				} else {
					String Joinid = id.getText();
					String Jointel = tel.getText();
					
					try {
						out.writeUTF("ȸ������");
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
					JOptionPane.showMessageDialog(null, "ȸ�����Կ� �����ϼ̽��ϴ�.");
					join.dispose();
				}
			} else {
				JOptionPane.showMessageDialog(null, "���̵� �ߺ��˻縦 �ٽ� ���ּ���.");
			}
			
		}

	}
	
	// CloseProcess Ŭ���� ����
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
