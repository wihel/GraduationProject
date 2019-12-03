package ServerGUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import DBConnection.UserCheckDB;

public class ManagerMainMenuView extends JPanel implements ActionListener{
	JLabel lblNewLabel;
	private JTable table;
	private JScrollPane scrollPane; // 테이블 스크롤바 자동으로 생성되게 하기

	int index = 5;
	
	private String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";       
	private String url = "jdbc:sqlserver://localhost:1433;databaseName=stock";
	// @호스트 IP : 포트 : SID

	
	private String colNames[] = { "콜라", "사이다", "환타",
			"소떡소떡", "피카츄", "라면", "햄버거", "핫도그", "썬칩",
			"오징어칩", "진라면", "불닭", "신라면", "왕교자", "만두"}; // 테이블 컬럼 값들
	private DefaultTableModel model = new DefaultTableModel(colNames, 0); // 테이블 데이터 모델 객체 생성

	
	
	
	private Connection con = null;
	private PreparedStatement pstmt = null;	        
	private ResultSet rs = null;   // 리턴받아 사용할 객체 생성 ( select에서 보여줄 때 필요 )
	
	
	/*DBConnectionMgr pool = null;
	
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;*/
	
	
	String sql = null;
	JButton update_Btn;
	boolean sqlCheck = false;
	
	
	public ManagerMainMenuView() {
		select();
		
		new JPanel();

		setLayout(null); // 레이아웃 배치관리자 삭제

		
		
		table = new JTable(model); // 테이블에 모델객체 삽입
		table.setRowHeight(50);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		table.getColumnModel().getColumn(0).setPreferredWidth(100);
		table.getColumnModel().getColumn(1).setPreferredWidth(100);
		table.getColumnModel().getColumn(2).setPreferredWidth(100);
		table.getColumnModel().getColumn(3).setPreferredWidth(130);
		table.getColumnModel().getColumn(4).setPreferredWidth(100);
		table.getColumnModel().getColumn(5).setPreferredWidth(100);
		table.getColumnModel().getColumn(6).setPreferredWidth(100);
		table.getColumnModel().getColumn(7).setPreferredWidth(100);
		table.getColumnModel().getColumn(8).setPreferredWidth(100);
		table.getColumnModel().getColumn(9).setPreferredWidth(100);
		table.getColumnModel().getColumn(10).setPreferredWidth(100);
		table.getColumnModel().getColumn(11).setPreferredWidth(100);
		table.getColumnModel().getColumn(12).setPreferredWidth(100);
		table.getColumnModel().getColumn(13).setPreferredWidth(100);
		table.getColumnModel().getColumn(14).setPreferredWidth(100);
		
		table.getTableHeader().setFont(new Font("돋음",Font.BOLD,28));//글씨체
	    table.getTableHeader().setOpaque(false);
	    table.getTableHeader().setBackground(new Color(32,136,203));//테이블 헤더 백그라운드 바꿀때
	    table.getTableHeader().setForeground(new Color(255,255,255));//테이블 헤더 글자 바꿀때

	    table.setBackground(new Color(255,255,255));//테이블 백그라운드 색바꿀때
	    //table.setForeground(new Color(32,136,255));//테이블 글자색바꿀때

	    table.setFont(new Font("돋음",Font.BOLD,32));
	    
	    update_Btn = new JButton("재고 갱신");
	    update_Btn.setBounds(530, 120, 100, 60);
	    update_Btn.addActionListener(this);
	    add(update_Btn);
	    
		// table.addMouseListener(new JTableMouseListener()); // 테이블에 마우스리스너 연결

		scrollPane = new JScrollPane(table); // 테이블에 스크롤 생기게 하기
		scrollPane.setSize(1600, 900);
		add(scrollPane);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == update_Btn) {
			select();
		}
		
	}

	private void select() { // 테이블에 보이기 위해 검색
		
		String query = "select * from stock";
		
		try {
			Class.forName(driver);			                
			con = DriverManager.getConnection(url, "sa", "1234");		                
			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery(); // 리턴받아와서 데이터를 사용할 객체생성
			model.setNumRows(0);
			if(sqlCheck == false) {
				while (rs.next()) { // 각각 값을 가져와서 테이블값들을 추가
					//model.addColumn("상품명", (new Object[] {"콜라", "사이다", "환타",
						//"소떡소떡", "피카츄", "라면", "햄버거", "핫도그"}));
					model.addRow((new Object[] { rs.getString("Coke"), rs.getString("Cider"),
					rs.getString("Fanta"), rs.getString("Sodduck"),rs.getString("Pikachu")
					,rs.getString("Lamen"), rs.getString("Hamburger"), rs.getString("Hotdog"),
					rs.getString("Sunchip"), rs.getString("Ojingchip"),rs.getString("JinRamen"),
					rs.getString("BullDack"), rs.getString("SinRamen"), rs.getString("Wanggyoja"),
					rs.getString("Ralpee")}));
					
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {

				rs.close();
				pstmt.close();

				con.close(); // 객체 생성한 반대 순으로 사용한 객체는 닫아준다.

			} catch (Exception e2) {
			
			}

		}

	}

	

}
