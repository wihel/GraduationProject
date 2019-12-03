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
	ServerManager vc = ServerManager.getInstance("��Ʈ��Ʈ");

	String id;
	String pc;
	JPanel contentPane;
	int money; // ���� �����
	int money2; // ���� �����2
	int timer; // ���� �ð� Ŭ����
	JLabel label; // �¼� ��ȣ
	JLabel lblid2;
	JLabel lbltime2;
	JLabel lblmoney2;
	JLabel label2;
	JLabel lblid; // ����� ���̵�
	JLabel timemoney2;
	static JLabel lbltime5; // ����� �̿�ð� ������
	String bank;
	static JLabel lblmoney; // ����� �̿��� ������
	boolean Loginboolean = false;

	boolean logout = false;  
	boolean click = false;
	
	
	JButton btnchat = new JButton(); // ä�ù�ư
	JButton btnmenu = new JButton(); // �ֹ���ư
	JButton btnend = new JButton(); // �����ư

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
		lblid2 = new JLabel("�� ���̵�");
		lbltime2 = new JLabel("�̿� �ð�");
		lblmoney2 = new JLabel("�̿� ���");
		image1 = new ImageIcon(ManagerMain1.class.getResource("/logo/MainLogo.PNG")); // �̹��� ��� (label2 �ΰ� �̹���)
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
		// btnend.setBorderPainted(false); // ������ �����
		btnend.setContentAreaFilled(false);
		btnend.setFocusPainted(false);

		label.setFont(new Font("���� ���", Font.BOLD, 80));
		label.setForeground(Color.WHITE);
		lblid2.setFont(new Font("���� ���", Font.PLAIN, 20));
		lblid2.setForeground(Color.WHITE);
		lbltime2.setFont(new Font("���� ���", Font.PLAIN, 20));
		lbltime2.setForeground(Color.WHITE);
		lblmoney2.setFont(new Font("���� ���", Font.PLAIN, 20));
		lblmoney2.setForeground(Color.WHITE);
		label2.setFont(new Font("���� ���", Font.BOLD, 40));
		label2.setForeground(Color.WHITE);
		lblid.setForeground(Color.WHITE);
		lbltime5.setForeground(Color.WHITE);
		lblmoney.setForeground(Color.WHITE);
		btnchat.setFont(new Font("���� ���", Font.BOLD, 20));
		btnmenu.setFont(new Font("���� ���", Font.BOLD, 20));
		btnend.setFont(new Font("���� ���", Font.BOLD, 16));

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
		lblmoney.setText(getMoney + "��");
	}

	void display() {
		setTitle("Ŭ���̾�Ʈ �α��� ȭ��");
		setLayout(null);
		setBounds(1480, 0, 450, 424);
		setVisible(true);

		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

	}

	// ê�̺�ƮŬ���� ����(ä��â�� �ҷ��´�)
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
		// �����ư ������ ����ݰ� �Բ� �޼��� ����
		
		if (e.getSource() == btnmenu) {
			 //bugerHamberger4 burger = new bugerHamberger4();
			new Menu(Integer.parseInt(pc), out);
		}

		if (e.getSource() == btnend) {
			System.out.println("�α׾ƿ� ��ư");			
			logout = true;
			if(logout == true) {
				JOptionPane.showMessageDialog(null, lblmoney.getText() + "�Դϴ�. �̿����ּż� �����մϴ�." + "");
				System.out.println("Ŭ���̾�Ʈ �α׾ƿ� ������");
				try {
					out.writeUTF("�α׾ƿ�");
					out.flush();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			click = true;
			String Moneysum = lblmoney.getText();
			
			try {
				out.writeUTF("�̿�����޽���");
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

	// ������ ������ ���� Ŭ���̾�Ʈ Ŀ����
	/*private class ClientConnector implements Runnable {
		@Override
		public void run() {
			try {
				String serverIp = "127.0.0.1";// "172.168.0.80";
				socket = new Socket(InetAddress.getByName(serverIp), 7793);
				System.out.println("���Ἲ��");
				in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
				out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
				
				while(Loginboolean == true) {
					JoinMember a = new JoinMember(out);
					
				}
				
				int pcNum = Integer.parseInt(pc);
				out.writeInt(pcNum);
				out.writeUTF(id);
				//out.writeUTF("�α���#asmin/1234");
				out.writeUTF("�α���");
				out.flush();

				while (true) {
					String str = in.readUTF();
					// ä�ø޽��� ó����
					if (str.equals("�޽���")) {
						String msg = in.readUTF();
						if (chat == null) {
							chat = new ClientChat(out, pc);
						}
						chat.chatFrameVisible();
						chat.addChat(msg);
					}
					
					

					if (str.equals("�������")) {
						System.out.println("������� ������");
						Integer money = in.readInt();
						lblmoney.setText(money.toString() + "��");
						lbltime5.setText(in.readUTF());
					}

					// �α׾ƿ� ó����
					if (str.equals("�α׾ƿ�")) {
						socket.close();
					}
				}

			} catch (IOException e) {// ������ ������ �������� â�� ����

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

	}*/  // Ŭ���̾�Ʈ Ŀ��������

	// ��� ��� ������
	/*
	 * class money01 implements Runnable {
	 * 
	 * public void run() { money = 0; try { while(!click) { // �¼����� 6���� ���������� 100����
	 * �ø���. ��ȸ���� ��� // ������ ������ ���� 1�ʰ� ���������� 100���� �ø���. Thread.sleep(6000); money +=
	 * 100; lblmoney.setText(money + "��");
	 * 
	 * }//while }//try catch (InterruptedException e) { e.printStackTrace(); }
	 * lblmoney.setText((money-100) + "��"); }//run }//money
	 */

	// �̿�ð� ������
	/*
	 * class timer extends Thread { public void run() { int hour = 0, min = 0, sec =
	 * 0;
	 * 
	 * // ������ ���� 10�� ������ �����Ѵ�. while (!click == true) { sec++; if (sec % 60 == 0) {
	 * min++; sec = 0; if (min % 60 == 0) { min = 0; hour++; } } try { if(click ==
	 * true) {
	 * 
	 * }
	 * 
	 * Thread.sleep(1000); out.writeUTF("�ð�"); lbltime5.setText(hour + "�ð�" + min +
	 * "��" + sec + "��"); out.writeUTF(hour + "�ð�" + min + "��" + sec + "��"); }
	 * 
	 * catch (InterruptedException e) { System.out.println("Seat : �α׾ƿ��� �ѵ��ϳ׿�~!");
	 * break; } catch (IOException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); }
	 * 
	 * }
	 * 
	 * 
	 * //lbltime5.setText(hour + "�ð�" + (min-1) + "��" + (sec=59) + "��");
	 * 
	 * //if(sec==0) //lbltime5.setText(hour + "�ð�" + (min-1) + "��" + (sec=59) +
	 * "��"); //else // lbltime5.setText(hour + "�ð�" + min + "��" + (sec-1) + "��"); }
	 * }
	 */
}
