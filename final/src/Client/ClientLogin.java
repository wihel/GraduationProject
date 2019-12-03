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





public class ClientLogin{// clientlogin Ŭ���� ����

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

	ClientLogin()// clientlogin ������ ���ۺ�
	{
		// ������ ����
		login = new JFrame("�α���");

		// �� ����
		// �� ����
	      JLabel id_label = new JLabel("");
	      id_label.setIcon(new ImageIcon("img\\id5.PNG"));
	      JLabel pass_label = new JLabel("");
	      pass_label.setIcon(new ImageIcon("img\\pass5.PNG"));
	      JLabel lblNewLabel = new JLabel("");
	      lblNewLabel.setIcon(new ImageIcon("img\\LOGIN5.PNG"));
	      JLabel pc_label = new JLabel("");
	      pc_label.setIcon(new ImageIcon("img\\seat5.PNG"));
	      JPanel panel = new JPanel();

	      // ��ư ����
	      JButton join_btn = new JButton("");
	      join_btn.setIcon(new ImageIcon("img\\join5.PNG"));
	      JButton log_btn = new JButton("");
	      log_btn.setIcon(new ImageIcon("img\\log4.PNG"));
	      JButton exit_btn = new JButton("");
	      exit_btn.setIcon(new ImageIcon("img\\quit5.PNG"));

	      // id,pass,pc��ü ����
	      id = new JTextField();
	      login.getContentPane().add(id);
	      pc = new JTextField();
	      pass = new JPasswordField();

	      // ���̵� �ʵ�� �н����� �ʵ� �Է±��ڼ� ����
	      pass.setDocument(new JTextFieldLimit(10));
	      id.setDocument(new JTextFieldLimit(10));
	      pc.setDocument(new JTextFieldLimit(2));
	      login.getContentPane().add(pass);
	      
	      id.setBounds(1593, 802, 125, 30);
	      pass.setBounds(1593, 844, 125, 30);
	      pc.setBounds(1593, 887, 125, 30);

	      // ���� ��ũ������� �޾ƿ´�
	      int width = Toolkit.getDefaultToolkit().getScreenSize().width;
	      int height = Toolkit.getDefaultToolkit().getScreenSize().height;

	      // //������Ʈ ��ġ��
	      id_label.setBounds(1486, 802, 105, 35);
	      pass_label.setBounds(1486, 844, 105, 35);
	      pc_label.setBounds(1486, 887, 105, 35);
	      log_btn.setBounds(1738, 803, 130, 70);
	      join_btn.setBounds(1486, 988, 105, 35);
	      exit_btn.setBounds(1760, 988, 105, 35);
	      lblNewLabel.setBounds(0, 0, 1920, 1080);
	      
	      
	      // ������Ʈ ���պ�
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
	      panel.setLayout(null); // �ۼַ�Ʈ ���̾ƿ�
		login.add(panel);
		// ��ư�� �̺�Ʈ ������ ���պ�
		exit_btn.addActionListener(new ExitProcess());
		join_btn.addActionListener(new JoinProcess());
		log_btn.addActionListener(new LoginProcess());

		login.setBounds(0, 0, 1920, 1080);
		login.setResizable(false);
		login.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		login.setVisible(true);
		
		new Thread(new ClientConnector()).start();
		
	}// client_login ������ �����

	public ClientLogin(ClientChat a){
		this.chat1 = a;
		System.out.println(chat1);
	}
	
	
	// JTextFieldLimitŬ���� ����(���̵� ��� �Է� ���ڼ� ����)

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

	// ExitProcess Ŭ���� ���� (�����̺�Ʈ ����)
	private class ExitProcess implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.exit(0);
		}

	}

	// ExitProcess Ŭ���� ����(�����̺�Ʈ ����)

	// JoinProcess Ŭ���� ���� (ȸ������ â�� ����)
	private class JoinProcess implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			Join = new JoinMember(out, in);
		}

	}
	// JoinProcess Ŭ���� ����

	// LoginProcess Ŭ���� ���� (login�� ó���Ѵ�)
	private class LoginProcess implements ActionListener, Runnable{
	
		public void actionPerformed(ActionEvent arg0) {
			char [] pass1 = pass.getPassword();
			password = new String(pass1);
			IdLogin = id.getText();
			run();
			
			try {
				Integer.parseInt(pc.getText());
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "�ǽù�ȣ�� �Է��ϼ���.");
				return;
			}
			if(id.getText().equals("��ȸ��")) {
				JOptionPane.showMessageDialog(null, "��ȸ������ �α��� �� �� �����ϴ�");
				login.dispose();
				
			} else if(id.getText().equals("")){
				JOptionPane.showMessageDialog(null, "��ȸ������ �α����մϴ�.");
				login.dispose();
			
			} else {
				
				//LoginMemberProcess�� �̵��Ͽ� LoginMember �޼ҵ���� ��
				//���̵�� ��й�ȣ�� DB���� ��ġ�ϸ� True���� ��ȯ
				//��ġ���� ������ False			
				//boolean logIn = LoginMemberProcess.loginMember(id.getText(),
						//password);
	
				if(logIn == false) {
					JOptionPane.showMessageDialog(null, "���̵� �������� �ʰų� �߸� �Է��ϼ̽��ϴ�.");
				} else {
					int pcNum = Integer.parseInt(pc.getText());
					try {
						out.writeUTF("�α���");
						out.writeInt(pcNum);
						out.writeUTF(id.getText());
						//out.writeUTF("�α���#asmin/1234");
						
						out.flush();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
					JOptionPane.showMessageDialog(null, "�α��� �Ǿ����ϴ�");
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
				out.writeUTF("���̵��й�ȣüũ");
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
	
	
	
	

	// ������ ������ ���� Ŭ���̾�Ʈ Ŀ����
		public class ClientConnector implements Runnable {
			@Override
			public void run() {
				try {
					String serverIp = "127.0.0.1";// "172.168.0.80";
					socket = new Socket(InetAddress.getByName(serverIp), 7793);
					System.out.println("���Ἲ��");
					in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
					out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
					
					/*while(Loginboolean == false) {
						System.out.println(out);
						new JoinMember(out);
						System.out.println("����");
						Loginboolean = true;
						
					}*/
					
					/*int pcNum = Integer.parseInt(pc.getText());
					out.writeInt(pcNum);
					out.writeUTF(id.getText());
					//out.writeUTF("�α���#asmin/1234");
					out.writeUTF("�α���");
					out.flush();*/

					while (true) {
						String str = in.readUTF();
						System.out.println(str + "Ŭ���̾�Ʈ �α��κ�");
						// ä�ø޽��� ó����
						if (str.equals("�޽���")) {
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
						
						if(str.equals("���̵���ߺ�")) {
							
							//Join.actionPerformed(dupl_id);
							Loginboolean = false;
							//new IdConfirm(Loginboolean);
							Join.LoginSuccess(Loginboolean);
						}
						if(str.equals("���̵��ߺ�")) {
							//Join.dupl_id = true;
							Loginboolean = true;
							Join.LoginFailed(Loginboolean);
							
							//Confirm.dupl_id = true;
							
							//Join.LoginFailed(Loginboolean);
						}
						
						if(str.equals("�Ѵ���ġ")) {
							System.out.println("��ġ�ϳ�?");
							logIn = true;
						}
						
						if(str.equals("����ġ")) {
							System.out.println("����ġ�ϳ�?");
							logIn = false;
						}
						

						if (str.equals("�������")) {
							System.out.println("������� ������");
							Integer money = in.readInt();
							//lblmoney.setText(money.toString() + "��");
							//lbltime5.setText(in.readUTF());
							String Time = in.readUTF();
							String ClientMoney = money.toString();
							
							Lastseat a = new Lastseat(Time,ClientMoney,"a");
						}

						// �α׾ƿ� ó����
						if (str.equals("�α׾ƿ�")) {
							socket.close();
						}
					}

				} catch (IOException e) {// ������ ������ �������� â�� ����

					if (chat1 != null) {
						chat1.closeFrame();
					}
					//doClient = false;
					//dispose();
					JOptionPane.showMessageDialog(null, "������ ������ ������ϴ�.");
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
			
			
			
			

		}// Ŭ���̾�Ʈ Ŀ��������
		
		
	
	// LoginProcess Ŭ���� ����
}// client_login Ŭ���� �����
