package ServerGUI;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import ButtonArray.ButtonToArray;

public class ManagerMain1 extends ButtonToArray implements ActionListener {
	private JPanel contentPane; // 전체패널
	JLabel lblPc;// 로고 들어가는 라벨
	JButton btn1, btn2, btn3, btn4;
	// 회원버튼//매장버튼//홈//메뉴
	JTabbedPane tabbedPane;// 주문상품목록 DB들어갈 패널을 넣기위한 화면

	JLayeredPane layeredPane;

	JPanel panel;// 주문상품목록 DB들어갈 패널

	JFrame frame; // 전체 프레임

	JPanel Seat_panel; // 좌석 패널

	ImageIcon icon; // panel 백그라운드 이미지

	JPanel map_panel; // 뒷 배경 패널
	JLabel label_img; // 이미지 라벨

	String[][] rec = { { "1", "Steve", "AUS" }, { "2", "Virat", "IND" }, { "3", "Kane", "NZ" }, { "4", "David", "AUS" },
			{ "5", "Ben", "ENG" }, { "6", "Eion", "ENG" }, };
	String[] header = { "Rank", "Player", "Country" };
	JPanel table_panel;

	ManagerMainMenuView MmMv = null;// 변경되는 패널
	ManagerMainUserlist MmUl = null;

	public static void main(String[] args) {
		new ManagerMain1();

	}

	public ManagerMain1() {

		frame = new JFrame();
		frame.setTitle("ODD PC Cafe");
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(ManagerMain1.class.getResource("/logo/logo.PNG"))); /// 창
																													/// 아이콘
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(0, 0, 1920, 1080);

		table_panel = new JPanel();
		table_panel.setVisible(true);
		table_panel.setBounds(0, 0, 400, 700);
		table_panel.setLayout(null);

		JTable table = new JTable(rec, header);
		table_panel.add(table);

		JLabel label = new JLabel("클라이언트 4");
		label.setBounds(0, 700, 10, 10);

		// icon = new ImageIcon("/logo/map.png");

		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		frame.setContentPane(contentPane);
		contentPane.setLayout(null);

		lblPc = new JLabel();
		lblPc.setIcon(new ImageIcon(ManagerMain1.class.getResource("/logo/MainLogo.PNG")));
		lblPc.setForeground(Color.WHITE);
		lblPc.setBounds(20, 20, 245, 120);
		contentPane.add(lblPc);

		label_img = new JLabel("");
		label_img.setIcon(new ImageIcon(ManagerMain1.class.getResource("/logo/map.png")));
		label_img.setBounds(0, 0, 1550, 883);

		map_panel = new JPanel();
		map_panel.setLayout(null);
		map_panel.setBounds(0, 0, 1550, 883);

		layeredPane = new JLayeredPane();
		layeredPane.setBounds(342, 131, 1550, 900);
		contentPane.add(layeredPane);
		layeredPane.setLayout(null);

		Seat_panel = new JPanel();
		Seat_panel.setBounds(0, 0, 1550, 900);
		Seat_panel.setLayout(null);
		int x = 500;
		int y = 200;
		for (int i = 1; i < 49; i++) {
			ClassArray[i] = new ButtonArray.PanelUI(i);
			ClassArray[i].setBounds(x, y, 100, 100);

			x += 125;
			if (i % 8 == 0 && i != 0) {
				x = 500;
				y += 100;
			}
			Seat_panel.add(ClassArray[i]);

			// seat30.add(ClassArray[i]);
		}
		map_panel.add(label_img);

		Seat_panel.add(map_panel);

		// layeredPane.add(map_panel, new Integer(2));
		layeredPane.add(Seat_panel, new Integer(3));

		MmUl = new ManagerMainUserlist();
		layeredPane.add(MmUl);
		MmUl.setBounds(0, 0, 1550, 900);

		MmMv = new ManagerMainMenuView();
		layeredPane.add(MmMv);
		MmMv.setBounds(0, 0, 1550, 900);

		btn1 = new JButton(); // 회원버튼
		btn1.setIcon(new ImageIcon(ManagerMain1.class.getResource("/image/button/Pcbtn2.PNG")));
		btn1.setBounds(445, 53, 103, 59);
		btn1.setBorderPainted(false);// 버튼테두리 없에기
		contentPane.add(btn1);
		btn1.addActionListener(this);

		btn2 = new JButton();// 매장버튼
		btn2.setIcon(new ImageIcon(ManagerMain1.class.getResource("/image/button/Userlistbtn.PNG")));
		btn2.setBounds(584, 53, 103, 59);
		btn2.setBorderPainted(false);// 버튼테두리 없에기
		contentPane.add(btn2);
		btn2.addActionListener(this);

		btn3 = new JButton();// 홈버튼
		btn3.setIcon(new ImageIcon(ManagerMain1.class.getResource("/image/button/Menubtn.PNG")));
		btn3.setBounds(719, 53, 103, 59);
		btn3.setBorderPainted(false);// 버튼테두리 없에기
		contentPane.add(btn3);
		btn3.addActionListener(this);
		btn4 = new JButton();// 메뉴버튼
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(12, 150, 280, 831);
		contentPane.add(tabbedPane);

		panel = new JPanel(); // 주문목록패널
		panel.add(label);
		panel.setBackground(Color.GRAY);
		panel.setBounds(100, 100, 280, 830);

		tabbedPane.addTab("주문목록", null, table, null);

		panel.setBounds(342, 131, 1550, 900);
		// contentPane.add(panel);
		frame.setResizable(false);
		frame.setVisible(true);
	}

	public void switchPane(JPanel panel) {// 패널 1번과 2번 변경 후 재설정
		layeredPane.removeAll();
		layeredPane.add(panel);
		layeredPane.repaint();
		layeredPane.revalidate();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btn1) {
			switchPane(Seat_panel);
		}
		if (e.getSource() == btn2) {
			switchPane(MmUl);
		}
		if (e.getSource() == btn3) {
			switchPane(MmMv);
		}
	}
}
