package Server;

import java.awt.Color;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;

import ButtonArray.*;
import ServerGUI.ManagerMain1;
import chat.MsgToCustomer;
import chat.Seat;
import model.People;
import view.CalculatorFrame;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextArea;




public class ServerManager{
	JPanel panel;
	JPanel seat30;
	
	//JButton [] Seat;
	JButton Seat1;
	JButton Seat2;
	JLabel seatnumlabel;
	
	static ButtonToArray Array;
	JLayeredPane lpane;
	
	JPopupMenu pMenu;
	JMenuItem miEnd, miInfo;

	public HashMap<Seat, Socket> clients 
	= new HashMap<Seat, Socket>();
	/*1. HashMap�̶�?
	HashMap�� Map�� �����Ѵ�. Key�� value�� ���� �ϳ��� entry�� �����Ѵٴ� Ư¡�� ���´�. 
	�׸��� hashing�� ����ϱ� ������ �������� �����͸� �˻��ϴµ� �پ ������ ���δ�.*/

	public Seat[] pcseat = new Seat[50];
	
	public static MsgToCustomer[] chatClient = new MsgToCustomer[50];
	//�������� �޽����� Ŭ���̾�Ʈ���� �����ϴ� Ŭ������ 50���� �迭�� ��ü���� ����
	
	JMenuItem miChat;

	JTextArea txtViewChat;
		
	ServerSocket serverSocket;
	Socket socket;
	
	OutputStream out;
	DataOutputStream dataout;
	InputStream in;
	DataInputStream datain;

	
	private static ServerManager instance = new ServerManager();
	
	public static ServerManager getInstance(String s) {
		System.out.println(s+ "���� �����Ŵ��� ȣ��");
		return instance;
	}
	
	private ServerManager() {
		System.out.println("������Ʈ�� : ������Ʈ�� ���� ");
		// ����ȭ���Ѽ� �����尣 �񵿱�ȭ�߻������ʵ���
		Collections.synchronizedMap(clients);
	}
	
	
	public static void main(String[] args) throws IOException {
		//ServerManagerSangseungja instance = new ServerManagerSangseungja();
		Array = new ManagerMain1();		
		Thread a = new ServerStart();
		a.start();
		//m.conbtn(Client.Seatnum);
		//System.out.println("�Ѿ�� ��" +Client.Seatnum);
		
	}

	
	
	
	// 02.���ο� �ڸ��޴� �޼ҵ�
	public void newSeat(int num, String name, Socket socket) {
		pcseat[num] = new Seat(num, name);
		clients.put(pcseat[num], socket);
		
	}
	
	
	// 05.�α��� ó�� from HostPcServer
		public void login(int num, String name) {
			int money = pcseat[num].getMoney();
			
			Array.ClassArray[num].label[0].setForeground(Color.red);
			Array.ClassArray[num].label[0].setText((num) + ". �α���");
			Array.ClassArray[num].label[1].setText(name + "��");
			Array.ClassArray[num].label[2].setText("");
			Array.ClassArray[num].label[3].setText(money + "��");
			System.out.println(Array.ClassArray[num].label[0].getText());
			System.out.println(Array.ClassArray[num].label[1].getText());
			System.out.println(Array.ClassArray[num].label[2].getText());
			System.out.println(Array.ClassArray[num].label[3].getText());
			//Array.ClassArray[num].isLogined = true;
			//Array.ClassArray[num].nickname = name;
			pcseat[num].setUsername(name);
			pcseat[num].setLogin(true);
			System.out.println("??" + pcseat[num]);
			if (!pcseat[num].isFirst()) {
				System.out.println("����: ù��° ������ �� ���� �����Ƿ� ���� ��ŸƮ");
				pcseat[num].setFirst(true);
				pcseat[num].start();
			}
		}
		
		
		// 06.�α׾ƿ�ó�� from HostPcServer
		public void logout(int num) {
			System.out.println("Vcontrol : " + num + "��° �ڸ��� �α׾ƿ���ų���մϴ�.");
			pcseat[num].interrupt();
			//Array.ClassArray[num].setBackground(Color.white);
			Array.ClassArray[num].label[0].setForeground(Color.white);
			Array.ClassArray[num].label[0].setText((num) + ". ���ڸ�");
			
			ddaom(num);
			Array.ClassArray[num].isLogined = false;
			// ���ͷ�Ʈ���Ѽ� ��� �ø��� ����
			pcseat[num].interrupt();
			pcseat[num].setLogin(false);
			try {
				socket = clients.get(pcseat[num]);
				dataout = new DataOutputStream(socket.getOutputStream());
				dataout.writeUTF("�α׾ƿ�");
				System.out.println("�α׾ƿ� �޽��");
				// ���޼ҵ�
				//this.payOff(pcseat[num]);
				// Ŭ���̾�Ʈ���� ���ֱ�
				clients.remove(pcseat[num]);
			} catch (IOException e) {
				System.out.println("������Ʈ�� : �α׾ƿ� �޽��� ������ �� ������");
			}

		}
		
		// 07.������ ����ǥ ó�� from HostPcServer
		public void ddaom(int num) {
			Array.ClassArray[num].label[1].setText("");
			Array.ClassArray[num].label[2].setText("");
			Array.ClassArray[num].label[3].setText("");

		}
		
		
		public void continueMoney(int num, Calendar date) {
			int money = pcseat[num].getMoney();
			Array.ClassArray[num].label[3].setText(money + "��");

			Calendar dateAfter = Calendar.getInstance();
			//calendar ��ü�� ��� ���� getInstance() �޼��带 ȣ�� �ϸ� 
			//������ ��¥�� �ð����� �ʱ�ȭ�� �Ǳ� ������ ������ ���� �۾� ���̵� 
			//�޼���� ���� �ð��� ��� �� �� �ֽ��ϴ�.
					
			dateAfter.setTimeInMillis(System.currentTimeMillis());
			//currenttimemillis ����ð� ���ϴ� �޼���

			long differ = (dateAfter.getTimeInMillis() - date.getTimeInMillis()) / (1000);
			
			long differ_hour = differ / 3600;
			
			long differ_minute = differ / 60;

			String gametime = differ_hour + "��" + differ_minute +"��" + (differ % 60) +"��";
			if(differ_minute == 60) {
				differ_minute = 0;
			}
			Array.ClassArray[num].label[2].setText(gametime);

			
			// �ٷ� �ؿ� Ŭ���̾�Ʈ�� ���ð� �����ִ� �޼ҵ� ����
			sendTime(pcseat[num], money, gametime);
		}
		
		public void sendTime(Seat pcseat, int money, String gametime) {
			try {
				dataout = new DataOutputStream(clients.get(pcseat).getOutputStream());
				//Dataoutputstream�� �ڹ��� �⺻ ������ Ÿ�Ժ��� ����ϴ� ������ �޼ҵ���� ������,
				//�⺻ �����͸� �Ű� ������ ȣ���Ѵ�.
				//���� ��� double�����͸� ����Ϸ��� writedouble(double v), 
				//char �����͸� ����Ϸ��� writeChar(int v)�̿��Ѵ�.
				//���ڿ��� ����Ҷ��� writeUTF(string str)�� ����Ѵ�.
				dataout.writeUTF("�������");
				dataout.writeInt(money);
				dataout.writeUTF(gametime);
			} catch (IOException e) {
				System.out.println("������� �޽��� �����µ� ����! ");
			}
		}
		
		// 10. Ŭ���̾�Ʈ�� ���� ���� �޽���
		public void messageFromPC(int num, String message) {
			if (chatClient[num] == null) { 
				chatClient[num] = new MsgToCustomer(num); //MsgToCustomer(num)�� ä��â UI ������ �κ�
			}
			chatClient[num].setVisible(true);// MstToCustomer ä��â UI�� ������			
			chatClient[num].ta.append(message); // Ŭ���̾�Ʈ�� ���� �޽����� ��Ƽ� TextArea�� ǥ������
			
		}
		
		// 11. ��ü���
		public void groupPayOff(int x, int num) {
			ArrayList<People> peoples = new ArrayList<People>();

			if (x == 1) {
				peoples.add(new People(num, pcseat[num].getUserame(),
						Array.ClassArray[num].label[2].getText(), pcseat[num].getMoney()));
				
			} 
			new CalculatorFrame(peoples);
		}
		
	
	static String getTime() {
		SimpleDateFormat f=new SimpleDateFormat("[hh:mm:ss]");
		return f.format(new Date());
		
	}//getTime
	
}





