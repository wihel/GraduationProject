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
	/*1. HashMap이란?
	HashMap은 Map을 구현한다. Key와 value를 묶어 하나의 entry로 저장한다는 특징을 갖는다. 
	그리고 hashing을 사용하기 때문에 많은양의 데이터를 검색하는데 뛰어난 성능을 보인다.*/

	public Seat[] pcseat = new Seat[50];
	
	public static MsgToCustomer[] chatClient = new MsgToCustomer[50];
	//관리자의 메시지를 클라이언트에게 전송하는 클래스를 50개의 배열로 객체변수 선언
	
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
		System.out.println(s+ "에서 서버매니저 호출");
		return instance;
	}
	
	private ServerManager() {
		System.out.println("브이컨트롤 : 브이컨트롤 가동 ");
		// 동기화시켜서 쓰레드간 비동기화발생하지않도록
		Collections.synchronizedMap(clients);
	}
	
	
	public static void main(String[] args) throws IOException {
		//ServerManagerSangseungja instance = new ServerManagerSangseungja();
		Array = new ManagerMain1();		
		Thread a = new ServerStart();
		a.start();
		//m.conbtn(Client.Seatnum);
		//System.out.println("넘어온 값" +Client.Seatnum);
		
	}

	
	
	
	// 02.새로운 자리받는 메소드
	public void newSeat(int num, String name, Socket socket) {
		pcseat[num] = new Seat(num, name);
		clients.put(pcseat[num], socket);
		
	}
	
	
	// 05.로그인 처리 from HostPcServer
		public void login(int num, String name) {
			int money = pcseat[num].getMoney();
			
			Array.ClassArray[num].label[0].setForeground(Color.red);
			Array.ClassArray[num].label[0].setText((num) + ". 로그인");
			Array.ClassArray[num].label[1].setText(name + "님");
			Array.ClassArray[num].label[2].setText("");
			Array.ClassArray[num].label[3].setText(money + "원");
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
				System.out.println("실험: 첫번째 실행이 된 적이 없으므로 새로 스타트");
				pcseat[num].setFirst(true);
				pcseat[num].start();
			}
		}
		
		
		// 06.로그아웃처리 from HostPcServer
		public void logout(int num) {
			System.out.println("Vcontrol : " + num + "번째 자리를 로그아웃시킬려합니다.");
			pcseat[num].interrupt();
			//Array.ClassArray[num].setBackground(Color.white);
			Array.ClassArray[num].label[0].setForeground(Color.white);
			Array.ClassArray[num].label[0].setText((num) + ". 빈자리");
			
			ddaom(num);
			Array.ClassArray[num].isLogined = false;
			// 인터럽트시켜서 요금 올리기 중지
			pcseat[num].interrupt();
			pcseat[num].setLogin(false);
			try {
				socket = clients.get(pcseat[num]);
				dataout = new DataOutputStream(socket.getOutputStream());
				dataout.writeUTF("로그아웃");
				System.out.println("로그아웃 메쏘드");
				// 계산메소드
				//this.payOff(pcseat[num]);
				// 클라이언트에서 없애기
				clients.remove(pcseat[num]);
			} catch (IOException e) {
				System.out.println("브이컨트롤 : 로그아웃 메시지 보내는 데 실패함");
			}

		}
		
		// 07.나머지 따옴표 처리 from HostPcServer
		public void ddaom(int num) {
			Array.ClassArray[num].label[1].setText("");
			Array.ClassArray[num].label[2].setText("");
			Array.ClassArray[num].label[3].setText("");

		}
		
		
		public void continueMoney(int num, Calendar date) {
			int money = pcseat[num].getMoney();
			Array.ClassArray[num].label[3].setText(money + "원");

			Calendar dateAfter = Calendar.getInstance();
			//calendar 객체를 얻기 위해 getInstance() 메서드를 호출 하면 
			//현재의 일짜와 시각으로 초기화가 되기 때문에 별도의 설정 작업 없이도 
			//메서드로 현재 시간을 얻어 올 수 있습니다.
					
			dateAfter.setTimeInMillis(System.currentTimeMillis());
			//currenttimemillis 현재시간 구하는 메서드

			long differ = (dateAfter.getTimeInMillis() - date.getTimeInMillis()) / (1000);
			
			long differ_hour = differ / 3600;
			
			long differ_minute = differ / 60;

			String gametime = differ_hour + "시" + differ_minute +"분" + (differ % 60) +"초";
			if(differ_minute == 60) {
				differ_minute = 0;
			}
			Array.ClassArray[num].label[2].setText(gametime);

			
			// 바로 밑에 클라이언트로 사용시간 보내주는 메소드 실행
			sendTime(pcseat[num], money, gametime);
		}
		
		public void sendTime(Seat pcseat, int money, String gametime) {
			try {
				dataout = new DataOutputStream(clients.get(pcseat).getOutputStream());
				//Dataoutputstream은 자바의 기본 데이터 타입별로 출력하는 별도의 메소드들이 있으며,
				//기본 데이터를 매개 변수로 호출한다.
				//예를 들어 double데이터를 출력하려면 writedouble(double v), 
				//char 데이터를 출력하려면 writeChar(int v)이용한다.
				//문자열을 출력할때는 writeUTF(string str)을 사용한다.
				dataout.writeUTF("요금정보");
				dataout.writeInt(money);
				dataout.writeUTF(gametime);
			} catch (IOException e) {
				System.out.println("요금정보 메시지 보내는데 오류! ");
			}
		}
		
		// 10. 클라이언트로 부터 받은 메시지
		public void messageFromPC(int num, String message) {
			if (chatClient[num] == null) { 
				chatClient[num] = new MsgToCustomer(num); //MsgToCustomer(num)이 채팅창 UI 생성자 부분
			}
			chatClient[num].setVisible(true);// MstToCustomer 채팅창 UI를 보여줌			
			chatClient[num].ta.append(message); // 클라이언트가 보낸 메시지를 담아서 TextArea에 표기해줌
			
		}
		
		// 11. 단체계산
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





