package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;

import Server.ServerManager;
import model.People;
import model.PeoplesModel;


@SuppressWarnings("serial")
public class CalculatorFrame extends JFrame implements ActionListener, KeyListener {
	ServerManager vc = ServerManager.getInstance("���������");

	int num;
	JButton button1, button2;
	JLabel label = new JLabel("��� ���޴�(��������..)");
	ArrayList<People> peoples;
	
	public CalculatorFrame(ArrayList<People> people) {
		this.peoples = people;
		
		setSize(500, 500);
		setTitle("����޴�");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout(4, 4));
		setUndecorated(true);
		
		
		
		// ������ ȭ�� �߾� �迭
		Dimension frameSize = this.getSize();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((screenSize.width - frameSize.width) / 2,
				(screenSize.height - frameSize.height) / 2);

		// ���͵��� �߰��г�
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		// panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
		Border blackBorder = BorderFactory.createLineBorder(Color.BLACK);
		panel.setBorder(blackBorder);

		// ���̺�
		PeoplesModel model = new PeoplesModel(peoples);
		JTable table = new JTable(model);
		panel.add(new JScrollPane(table));
		
		
		
		// �ǾƷ����� �Ʒ��г�
		int sum=0;
		for(int i=0;i<peoples.size();i++){
			sum+=peoples.get(i).getMoney();
		}
		JPanel �Ʒ��г� = new JPanel();
		JLabel money= new JLabel("�� ��� : "+sum);
		button1 = new JButton("���!");
		button1.addActionListener(this);
		button2 = new JButton("���");
		button2.addActionListener(this);
		
		�Ʒ��г�.add(money);
		�Ʒ��г�.add(button1);
		�Ʒ��г�.add(button2);
		
		add(label, BorderLayout.NORTH);
		add(panel, BorderLayout.CENTER);
		add(�Ʒ��г�, BorderLayout.SOUTH);
		setVisible(true);
	}

	public static void main(String[] args) {
		ArrayList<People> peoples = new ArrayList<People>();
		peoples.add(new People(1, "nick", "�����ð�", 300));
		new CalculatorFrame(peoples);
	}
	
	@Override
	// �����ư ������
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == button1) {
			for (int i = 0; i < peoples.size(); i++) {
				vc.logout(peoples.get(i).getNum());
				//������ �����Ѵ�. �Ż��� ���ؼ�~
			}
			dispose();
		} else if (e.getSource() == button2) {
			dispose();
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==10){
		
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
	
	//�߻����̺��
	
}
