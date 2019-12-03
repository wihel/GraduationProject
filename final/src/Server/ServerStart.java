package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;

import javax.swing.JOptionPane;

import ButtonArray.ButtonToArray;
import DBConnection.ProductDB;
import DBConnection.UserCheckDB;
import Server_DB.IdCheckProcess;
import Server_DB.JoinMemberProcess;
import Server_DB.LoginMemberProcess;

import java.io.DataInputStream;
import java.io.DataOutputStream;


public class ServerStart extends Thread{
	ServerManager vc = ServerManager.getInstance("ServerStart");
	ServerReceiver receiver = null;
	ButtonToArray Array;
	ProductDB db = new ProductDB();
	
	public void run() {

		ServerSocket serverSocket = null;
		Socket socket = null;

		try {
			serverSocket = new ServerSocket(7793);
			System.out.println("ȣ��Ʈ �ǽ� : " + "PC�� ȣ��Ʈ�� ���۵˴ϴ�");

			// ������ ��� �޾Ƴ��� ������~
			while (true) {
				socket = serverSocket.accept();
				System.out.println("ȣ��Ʈ �ǽ� : " + "[" + socket.getInetAddress()
						+ "]���� �����Ͽ���!");

				receiver = new ServerReceiver(socket);
				receiver.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// ���� ���ù� Ŭ���� - �� Ŭ������ ������ ���涧���� ��� �����.
		class ServerReceiver extends Thread {
			Socket socket;
			DataInputStream in;
			DataOutputStream out;

			// �����ڿ����� ���������� �޾Ƽ� ��ǲ�ƿ�ǲ ��Ʈ���� �ϳ� ����� �����Ѵ�~
			ServerReceiver(Socket socket) {
				this.socket = socket;
				try {
					in = new DataInputStream(socket.getInputStream());
					out = new DataOutputStream(socket.getOutputStream());
					
					System.out.println("ȣ��Ʈ �ǽ� : �ξƿ� �����Ϸ�!");
				} catch (IOException e) {
					System.out.println("ȣ��Ʈ �ǽ� : " + "���� ���ù� ���� ����� ����");
				}
			}// ������ ��

			// ���ù� ��ŸƮ~
			public void run() {
				int num = 0;
				String name = "";

				try {
					System.out.println("ServerReceiver thread start");
					// ��Ʈ��ũ ������ ��û �޽��� ��� �ޱ� ó��
					while (in != null) {
						
						String s = in.readUTF();
						System.out.println(s);						
						switch (s) {
						case "���̵�üũ":
							String getId = in.readUTF();
							System.out.println(getId);
							if(IdCheckProcess.checkId(getId) == false) {
								out.writeUTF("���̵���ߺ�");
								out.flush();
							}
							else if(IdCheckProcess.checkId(getId) == true) {
								out.writeUTF("���̵��ߺ�");
								out.flush();
							}
							break;
							
						case "���̵��й�ȣüũ":
							String FinalIdCheck = in.readUTF();
							String FinalPwCheck = in.readUTF();
							System.out.println(FinalIdCheck);
							System.out.println(FinalPwCheck);
							if(LoginMemberProcess.loginMember(FinalIdCheck, FinalPwCheck) == true) {
								out.writeUTF("�Ѵ���ġ");
								out.flush();
							}
							else if(LoginMemberProcess.loginMember(FinalIdCheck, FinalPwCheck) == false){
								out.writeUTF("����ġ");
								out.flush();
							}
							break;
							
						case "ȸ������":
							String Joinid = in.readUTF();
							String Joinpw = in.readUTF();
							String Jointel = in.readUTF();
							int Joinage = in.readInt();
							JoinMemberProcess.insertMember(Joinid, Joinpw, Jointel, Joinage);
							break;
							
						case "�α���":
							System.out.println("������ �α���");
							num = in.readInt();
							name = in.readUTF();
							System.out.println("ȣ��Ʈ �ǽ� : " + "�ڸ� ��ȣ�� " + num);
							System.out.println("ȣ��Ʈ �ǽ� : " + "�� ����� �̸��� " + name + "�Դϴ�.");
							vc.newSeat(num, name, socket);
							vc.login(num, name);
							break;                 
						case "�α׾ƿ�":
							System.out.println("������ �α׾ƿ�");
							vc.logout(num);
							break; // ȭ�麯ȯ �޼ҵ�
						case "��ǻ�Ͳ�":
							break;
						case "�̿�����޽���":
							String Money = in.readUTF();
							String pc = in.readUTF();
							JOptionPane.showMessageDialog(null, pc + " �� �մ� " + "�̿��� : " 
							+ Money + "���Խ��ϴ�");
							break;
						case "�޽���":
							System.out.println("������ �޼���");
							String message = in.readUTF();
							vc.messageFromPC(num, message);
							break; // �޽���ó�� �޼ҵ�
						case "�ֹ�":
							int seatNum = in.readInt();
							String getItem = in.readUTF();
							int getNum = in.readInt();
							int getPrice = in.readInt();
							System.out.println(seatNum + "������ �ֹ��޾Ҵ�~");
							UserCheckDB Query = UserCheckDB.getInstance();
							String updateQuery = "update stock set "+ getItem + "="+ getItem + "-" + getNum +
					         		"where " + getItem + ">"+ getNum + ";";
							db.executeUpdate(updateQuery);

							System.out.println(vc.pcseat[seatNum] + "�� ���� ���Դϴ�.");
							vc.pcseat[seatNum].setMoney(getPrice
									+ vc.pcseat[seatNum].getMoney());
							
							JOptionPane.showMessageDialog(null, seatNum + "�� �¼����� " + getItem
									+ "��/�� " + getNum + "�� �ֹ� �޾ҽ��ϴ�. \n ������ �˾Ƽ� �÷Ƚ��ϴ�." );
							break;
						}
					}
				} catch (IOException e) {
					System.out.println("ȣ��Ʈ�ǽ�: " + "Ŭ���̾�Ʈ���� ������ ���� : �����ų�..");
				} finally {
					System.out.println("ȣ��Ʈ�ǽ�: " + "��ǻ�Ͱ� ����~");

				}
			}// ����
		}
	
	
}
