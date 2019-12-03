
package menulist;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import DBConnection.ProductDB;

public class Menu extends JFrame implements ActionListener {

	private JPanel contentPane;// ��ü�г�
	JButton btnFaver, btnDrink, btnSnack, btnRamen, btnFreeze, btnExit;
	// �α��ǰ��ư,����,����,���,�õ�,����
	JButton btnOrder, btnClear;
	// �ֹ���ư ��ҹ�ư
	JScrollPane scrollPane;
	static String[] columnName = { "��ǰ��", "����", "����", "�Ѿ�" };
	static DefaultTableModel model = new DefaultTableModel(columnName, 0);
	JTable table;
	ProductDB db = new ProductDB();
	JLabel logo;
	int Cupresult; // ���� ���� ���ϴ� �޼ҵ�
	JLayeredPane layeredPane;
	Popularitemenu Popular;
	Drink Drink;// ����Ǵ� �г�
	Snackmenu Sanck;
	CupRamen Cup;
	Instantmenu In;// ����Ǵ� �г�
	DataOutputStream out;
	int pc;

	public static void main(String[] args) {
		//Menu frame = new Menu();
	}

	public Menu(int pc , DataOutputStream out) {
		this.pc = pc;
		this.out = out;
		
		
		
		setBackground(Color.lightGray);
		contentPane = new JPanel();
		contentPane.setBackground(Color.white);
		setContentPane(contentPane);
		contentPane.setLayout(null);

		table = new JTable(model);
		table.setFont(new Font("���� ���", Font.PLAIN, 16));
		scrollPane = new JScrollPane(table);
		scrollPane.setBounds(0, 728, 605, 200);
		contentPane.add(scrollPane);

		btnFaver = new JButton();// �α��ǰ
		btnFaver.setIcon(new ImageIcon(Menu.class.getResource("/image/button/ingi.PNG")));
		btnFaver.setBounds(0, 165, 200, 75);
		btnFaver.setBorderPainted(false);// ��ư�׵θ� ������
		btnFaver.addActionListener(this);
		contentPane.add(btnFaver);

		btnDrink = new JButton("");// ���
		btnDrink.setIcon(new ImageIcon(Menu.class.getResource("/image/button/drinka.PNG")));
		btnDrink.setBounds(205, 165, 200, 75);
		btnDrink.setBorderPainted(false);// ��ư�׵θ� ������
		contentPane.add(btnDrink);
		btnDrink.addActionListener(this);

		btnSnack = new JButton("");// ����
		btnSnack.setIcon(new ImageIcon(Menu.class.getResource("/image/button/snackk.PNG")));
		btnSnack.setBounds(410, 165, 200, 75);
		btnSnack.setBorderPainted(false);// ��ư�׵θ� ������
		contentPane.add(btnSnack);
		btnSnack.addActionListener(this);

		btnRamen = new JButton("");// ���
		btnRamen.setIcon(new ImageIcon(Menu.class.getResource("/image/button/Ramena.PNG")));
		btnRamen.setBounds(0, 245, 200, 75);
		btnRamen.setBorderPainted(false);// ��ư�׵θ� ������
		contentPane.add(btnRamen);
		btnRamen.addActionListener(this);

		btnFreeze = new JButton("");// �õ�
		btnFreeze.setIcon(new ImageIcon(Menu.class.getResource("/image/button/INS.PNG")));
		btnFreeze.setBounds(205, 245, 200, 75);
		btnFreeze.setBorderPainted(false);// ��ư�׵θ� ������
		contentPane.add(btnFreeze);
		btnFreeze.addActionListener(this);

		btnExit = new JButton("");// ����
		btnExit.setIcon(new ImageIcon(Menu.class.getResource("/image/button/nono.PNG")));
		btnExit.setBounds(410, 245, 200, 75);
		contentPane.add(btnExit);

		logo = new JLabel(); // �ΰ�
		logo.setBounds(0, 0, 610, 164);
		logo.setBackground(Color.blue);
		logo.setIcon(new ImageIcon(Menu.class.getResource("/image/button/logologo.PNG")));
		contentPane.add(logo);

		layeredPane = new JLayeredPane();
		layeredPane.setBounds(0, 325, 750, 400);
		contentPane.add(layeredPane);
		layeredPane.setLayout(null);

		Popular = new Popularitemenu(); // �ǳ� �ʻ���
		layeredPane.add(Popular);
		Popular.setBounds(0, 0, 750, 400);
		Popular.setBackground(Color.LIGHT_GRAY);
		
		Popular.Popularitemenu(pc, out);

		
		Drink = new Drink();
		layeredPane.add(Drink);
		Drink.setBounds(0, 0, 750, 500);
		Drink.setBackground(Color.LIGHT_GRAY);
		Drink.Drink(pc, out);

		Sanck = new Snackmenu();
		layeredPane.add(Sanck);
		Sanck.setBounds(0, 0, 750, 500);
		Sanck.setBackground(Color.LIGHT_GRAY);
		Sanck.Snackmenu(pc, out);

		Cup = new CupRamen();
		layeredPane.add(Cup);
		Cup.setBounds(0, 0, 750, 500);
		Cup.setBackground(Color.LIGHT_GRAY);
		Cup.CupRemen(pc, out);

		In = new Instantmenu();
		layeredPane.add(In);
		In.setBounds(0, 0, 750, 500);
		In.setBackground(Color.LIGHT_GRAY);
		In.Instantmenu(pc, out);
		btnClear = new JButton(new ImageIcon("Image/chiso.PNG")); // �ʱ�ȭ
		btnClear.setBounds(0, 930, 250, 70);
		add(btnClear);
		btnClear.addActionListener(this);

		btnOrder = new JButton(new ImageIcon("Image/odera.PNG")); // �ֹ�
		btnOrder.setBounds(355, 930, 250, 70);
		add(btnOrder);
		btnOrder.addActionListener(this);

		setTitle("��ǰ�ֹ�");
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setBounds(0, 0, 615, 1040);
		setVisible(true);
	}

	public Menu(String[] order) {
		model.addRow(order);
	}

	public void CupRamen(int result) {
		Cupresult += result;
		System.out.println(Cupresult);
	}

	public void resultMethod(int result) {
		int Cupresult = result;
		System.out.println(Cupresult);

	}

	public void switchPane(JPanel panel) {// �г� 1���� 2�� ���� �� �缳��
		layeredPane.removeAll();
		layeredPane.add(panel);
		layeredPane.repaint();
		layeredPane.revalidate();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnFaver) {
			switchPane(Popular);
			
		}
		if (e.getSource() == btnDrink) {
			switchPane(Drink);
			
			
		}
		if (e.getSource() == btnSnack) {
			switchPane(Sanck);
			
		}
		if (e.getSource() == btnRamen) {
			switchPane(Cup);
			
		}
		if (e.getSource() == btnFreeze) {
			switchPane(In);
			
		}
		if (e.getSource() == btnClear) {
			Popular.btnClearM(table);
			Drink.btnClearM(table);
			Sanck.btnClearM(table);
			Cup.btnClearM(table);
			In.btnClearM(table);

		}
		if (e.getSource() == btnOrder) {
			Popular.btnOrderM(table);
		}

	}
}// Menu
