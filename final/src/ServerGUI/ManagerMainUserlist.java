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
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import control.manage_member.dbprocess.ReadMemberProcess;
import model_manage_member.MemberInfo;

public class ManagerMainUserlist extends JPanel implements ActionListener{
	JLabel lblNewLabel;
	JTable table; 
	String[] culumnName = {"아이디", "전화번호", "마일리지", "나이"};
	
	private String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";       
	private String url = "jdbc:sqlserver://localhost:1433;databaseName=member";
	// @호스트 IP : 포트 : SID
	
	JButton update_Btn;
	
	
	private Connection con = null;
	private PreparedStatement pstmt = null;	        
	private ResultSet rs = null;   // 리턴받아 사용할 객체 생성 ( select에서 보여줄 때 필요 )
	
	private JScrollPane scrollPane;
	
	
	DefaultTableModel dtm;
	private ArrayList<MemberInfo> list = new ArrayList<MemberInfo>();
	private final int MAX_ROW = 40; // 테이블에서 데이터를 표시하기 위한 최대 줄 수
	
	boolean sqlCheck = false;
	
	
	public ManagerMainUserlist() {
		select();
		
		new JPanel();
		dtm = new DefaultTableModel(culumnName, 0);
		table = new JTable(dtm);
		
		table.setRowHeight(30);
		table.setFont(new Font("segooe UI", Font.BOLD, 20));
		
		table.getTableHeader().setBackground(Color.darkGray);
		table.getTableHeader().setFont(new Font("segooe UI", Font.BOLD, 20));
		table.getTableHeader().setForeground(Color.white);
		
		
		ReadMemberProcess.readMember(list);
		update_Btn = new JButton("회원 갱신");
		update_Btn.setBounds(1300, 70, 100, 60);
		update_Btn.addActionListener(this);
		add(update_Btn);
		
		JScrollPane scrollpane = new JScrollPane(table);
		scrollpane.setBounds(0, 0, 1200, 900);
		
		setLayout(null);
		add(scrollpane);
		
		if (list.size() > 0) {
			list.clear();
		}		
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == update_Btn) {
			select();
		}
		
	}
	
private void select() { // 테이블에 보이기 위해 검색
		
		String query = "select * from member";
		
		try {
			Class.forName(driver);			                
			con = DriverManager.getConnection(url, "sa", "1234");		                
			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery(); // 리턴받아와서 데이터를 사용할 객체생성
			dtm.setNumRows(0);
			if(sqlCheck == false) {	
				System.out.println(list.size());
				while(rs.next()) {
					dtm.addRow(new Object[] { rs.getString("id"), rs.getString("password"),
							rs.getString("tel"), rs.getInt("age")
					});
					
				}
				
				/*for (int i = 0; i < list.size(); i++) {
					String id = list.get(i).getId();
					String tel = list.get(i).getTel();
					String mileage = list.get(i).getMileage();
					String age = list.get(i).getAge();
					String[] str = { id, tel, mileage, age };
					dtm.addRow(str);
				}*/
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
