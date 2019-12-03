package ButtonArray;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import Server.ServerManager;

public class PanelUI extends ButtonClass implements ActionListener{
	ServerManager vc = ServerManager.getInstance("�ǳ� UI");

	//ServerManager vcm;
	/**
	 * 
	 */
	JPanel panel; // ��ư�� �� �ǳ�
	JLayeredPane lpane;
	
	public PanelUI(int i) {
		num = i;
		setLayout(null);
		isChecked = false;
		
		/*label[0] = new JLabel();
		label[0].setBackground(Color.blue);
		
		setBackground(Color.gray);
		label[1] = new JLabel("��ǻ�Ͳ���");
		label[2] = new JLabel();
		label[3] = new JLabel();
		
		
		add(label[0]);
		add(label[1]);
		add(label[2]);
		add(label[3]);*/
		
			
		// ���̷��̾�� �г�
		// �����̳ʵ��� ���ļ� ����� �� �ְ� ���ش�
		lpane = new JLayeredPane();
		lpane.setBounds(0, 0, 180, 180);
		lpane.setLayout(null);
		
		
		panel = new JPanel();
		panel.setLayout(null);
		panel.setBounds(10, 10, 130, 150);
		panel.setBackground(Color.DARK_GRAY);
		
		/*for(int j = 0;j<=button.length - 1;j++) {
			
			button[j] = new JButton("�¼� : " + j+1);
			panel.add(button[j]);
			button[j].addMouseListener(new MousePopupListener());
		}*/
		

	
		
		int y = 15;
		for(int a = 0; a < 4; a++) {
			if(a == 0) {
				label[a] = new JLabel((i) + ". ���ڸ�");
			}
			else
				label[a] = new JLabel();
			
			label[a].setBounds(20, y, 80, 15);
			label[a].setForeground(Color.white);
			y += 16;
			//button[0].setText(label[a].getText())	
			panel.add(label[a]);
		}
		
		//lpane.add(panel, new Integer(1),0);
		
		add(panel);
		setBackground(Color.black);
		setVisible(true);
		
		
		pMenu = new JPopupMenu();
		miEnd= new JMenuItem("����");
		miEnd.addActionListener(this);
		miInfo = new JMenuItem("ȸ������");
		miChat = new JMenuItem("�޽���������");
		miChat.addActionListener(this);
		pMenu.add(miEnd);
		pMenu.add(miInfo);
		pMenu.add(miChat);
		
		addMouseListener(new MousePopupListener());
	}

	class MousePopupListener extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
			checkPopup(e);
		}

		public void mouseClicked(MouseEvent e) {
			checkPopup(e);
		}

		public void mouseReleased(MouseEvent e) {
			checkPopup(e);
		}

		private void checkPopup(MouseEvent e) {
			if (e.isPopupTrigger()) {
				pMenu.show(PanelUI.this, e.getX(), e.getY());
			}
		}
	}//MouseListener
	
	public static void main(String[] args) {
		JFrame f = new JFrame();
		f.setTitle("��Ʈ �г�");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(200, 200);

		JPanel panel = new PanelUI(1);
		f.add(panel);

		f.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == miEnd) {
			vc.groupPayOff(1, num);
		}
		
		else if(e.getSource() == miChat) {
			System.out.println(vc);
			vc.messageFromPC(num, "ä���� �����մϴ�");
		}
		
	}

	/*@Override
	public void turnOn() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void turnOff() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void checkOn() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void checkOff() {
		// TODO Auto-generated method stub
		
	}*/

}
