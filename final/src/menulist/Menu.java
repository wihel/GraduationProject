
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

	private JPanel contentPane;// 전체패널
	JButton btnFaver, btnDrink, btnSnack, btnRamen, btnFreeze, btnExit;
	// 인기상품버튼,음료,간식,라면,냉동,종료
	JButton btnOrder, btnClear;
	// 주문버튼 취소버튼
	JScrollPane scrollPane;
	static String[] columnName = { "제품명", "가격", "수량", "총액" };
	static DefaultTableModel model = new DefaultTableModel(columnName, 0);
	JTable table;
	ProductDB db = new ProductDB();
	JLabel logo;
	int Cupresult; // 총합 가격 구하는 메소드
	JLayeredPane layeredPane;
	Popularitemenu Popular;
	Drink Drink;// 변경되는 패널
	Snackmenu Sanck;
	CupRamen Cup;
	Instantmenu In;// 변경되는 패널
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
		table.setFont(new Font("맑은 고딕", Font.PLAIN, 16));
		scrollPane = new JScrollPane(table);
		scrollPane.setBounds(0, 728, 605, 200);
		contentPane.add(scrollPane);

		btnFaver = new JButton();// 인기상품
		btnFaver.setIcon(new ImageIcon(Menu.class.getResource("/image/button/ingi.PNG")));
		btnFaver.setBounds(0, 165, 200, 75);
		btnFaver.setBorderPainted(false);// 버튼테두리 없에기
		btnFaver.addActionListener(this);
		contentPane.add(btnFaver);

		btnDrink = new JButton("");// 움료
		btnDrink.setIcon(new ImageIcon(Menu.class.getResource("/image/button/drinka.PNG")));
		btnDrink.setBounds(205, 165, 200, 75);
		btnDrink.setBorderPainted(false);// 버튼테두리 없에기
		contentPane.add(btnDrink);
		btnDrink.addActionListener(this);

		btnSnack = new JButton("");// 간식
		btnSnack.setIcon(new ImageIcon(Menu.class.getResource("/image/button/snackk.PNG")));
		btnSnack.setBounds(410, 165, 200, 75);
		btnSnack.setBorderPainted(false);// 버튼테두리 없에기
		contentPane.add(btnSnack);
		btnSnack.addActionListener(this);

		btnRamen = new JButton("");// 라면
		btnRamen.setIcon(new ImageIcon(Menu.class.getResource("/image/button/Ramena.PNG")));
		btnRamen.setBounds(0, 245, 200, 75);
		btnRamen.setBorderPainted(false);// 버튼테두리 없에기
		contentPane.add(btnRamen);
		btnRamen.addActionListener(this);

		btnFreeze = new JButton("");// 냉동
		btnFreeze.setIcon(new ImageIcon(Menu.class.getResource("/image/button/INS.PNG")));
		btnFreeze.setBounds(205, 245, 200, 75);
		btnFreeze.setBorderPainted(false);// 버튼테두리 없에기
		contentPane.add(btnFreeze);
		btnFreeze.addActionListener(this);

		btnExit = new JButton("");// 공백
		btnExit.setIcon(new ImageIcon(Menu.class.getResource("/image/button/nono.PNG")));
		btnExit.setBounds(410, 245, 200, 75);
		contentPane.add(btnExit);

		logo = new JLabel(); // 로고
		logo.setBounds(0, 0, 610, 164);
		logo.setBackground(Color.blue);
		logo.setIcon(new ImageIcon(Menu.class.getResource("/image/button/logologo.PNG")));
		contentPane.add(logo);

		layeredPane = new JLayeredPane();
		layeredPane.setBounds(0, 325, 750, 400);
		contentPane.add(layeredPane);
		layeredPane.setLayout(null);

		Popular = new Popularitemenu(); // 판넬 십새끼
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
		btnClear = new JButton(new ImageIcon("Image/chiso.PNG")); // 초기화
		btnClear.setBounds(0, 930, 250, 70);
		add(btnClear);
		btnClear.addActionListener(this);

		btnOrder = new JButton(new ImageIcon("Image/odera.PNG")); // 주문
		btnOrder.setBounds(355, 930, 250, 70);
		add(btnOrder);
		btnOrder.addActionListener(this);

		setTitle("상품주문");
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

	public void switchPane(JPanel panel) {// 패널 1번과 2번 변경 후 재설정
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
