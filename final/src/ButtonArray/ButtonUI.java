package ButtonArray;

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






public class ButtonUI extends ButtonClass implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JPanel panel; // ��ư�� �� �ǳ�
	JLayeredPane lpane;
	
	public ButtonUI(int i) {
		num = i;
		setLayout(null);
		
		
		// ���̷��̾�� �г�
		// �����̳ʵ��� ���ļ� ����� �� �ְ� ���ش�
		lpane = new JLayeredPane();
		lpane.setBounds(0, 0, 1600, 900);
		lpane.setLayout(null);
		
		
		panel = new JPanel();
		panel.setLayout(null);
		panel.setBounds(10, 10, 200, 200);
		
		
		/*for(int j = 0;j<=button.length - 1;j++) {
			
			button[j] = new JButton("�¼� : " + j+1);
			panel.add(button[j]);
			button[j].addMouseListener(new MousePopupListener());
		}*/
		
		for(int k = 0 ; k <= 0; k++) {
			//button[k] = new JButton();
			
		}
	
		
		int y = 15;
		for(int a = 0; a < 4; a++) {
			if(a == 0) {
				label[a] = new JLabel((i) + ". ���ڸ�");
			}
			else
				label[a] = new JLabel("1");
			
			
			label[a].setBounds(20, y, 80, 15);
			y += 16;
			
			//button[0].setText(label[a].getText());
			//button[0].add(label[a]);
			
			
		}
		//panel.add(button[0]);
		//button[0].setBounds(20, 15, 90, 80);	
		
		
		
		lpane.add(panel, new Integer(0),0);
		
		add(lpane);
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
	    	/*for(int i= 0; i <= button.length - 1; i++) {	    		
	    		if(e.getSource() == button[i]) {
	    			pMenu.show(button[i], e.getX(), e.getY());
	    		}*/
	    		
	    	}
	   
	    }


	  //}//MouseListener
	
	public static void main(String[] args) {
		JFrame f = new JFrame();
		f.setTitle("��Ʈ �г�");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(200, 200);

		JPanel panel = new ButtonUI(1);
		f.add(panel);

		f.setVisible(true);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
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
