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
	private JScrollPane scrollPane; // ���̺� ��ũ�ѹ� �ڵ����� �����ǰ� �ϱ�

	int index = 5;
	
	private String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";       
	private String url = "jdbc:sqlserver://localhost:1433;databaseName=stock";
	// @ȣ��Ʈ IP : ��Ʈ : SID

	
	private String colNames[] = { "�ݶ�", "���̴�", "ȯŸ",
			"�Ҷ��Ҷ�", "��ī��", "���", "�ܹ���", "�ֵ���", "��Ĩ",
			"��¡��Ĩ", "�����", "�Ҵ�", "�Ŷ��", "�ձ���", "����"}; // ���̺� �÷� ����
	private DefaultTableModel model = new DefaultTableModel(colNames, 0); // ���̺� ������ �� ��ü ����

	
	
	
	private Connection con = null;
	private PreparedStatement pstmt = null;	        
	private ResultSet rs = null;   // ���Ϲ޾� ����� ��ü ���� ( select���� ������ �� �ʿ� )
	
	
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

		setLayout(null); // ���̾ƿ� ��ġ������ ����

		
		
		table = new JTable(model); // ���̺� �𵨰�ü ����
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
		
		table.getTableHeader().setFont(new Font("����",Font.BOLD,28));//�۾�ü
	    table.getTableHeader().setOpaque(false);
	    table.getTableHeader().setBackground(new Color(32,136,203));//���̺� ��� ��׶��� �ٲܶ�
	    table.getTableHeader().setForeground(new Color(255,255,255));//���̺� ��� ���� �ٲܶ�

	    table.setBackground(new Color(255,255,255));//���̺� ��׶��� ���ٲܶ�
	    //table.setForeground(new Color(32,136,255));//���̺� ���ڻ��ٲܶ�

	    table.setFont(new Font("����",Font.BOLD,32));
	    
	    update_Btn = new JButton("��� ����");
	    update_Btn.setBounds(530, 120, 100, 60);
	    update_Btn.addActionListener(this);
	    add(update_Btn);
	    
		// table.addMouseListener(new JTableMouseListener()); // ���̺� ���콺������ ����

		scrollPane = new JScrollPane(table); // ���̺� ��ũ�� ����� �ϱ�
		scrollPane.setSize(1600, 900);
		add(scrollPane);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == update_Btn) {
			select();
		}
		
	}

	private void select() { // ���̺� ���̱� ���� �˻�
		
		String query = "select * from stock";
		
		try {
			Class.forName(driver);			                
			con = DriverManager.getConnection(url, "sa", "1234");		                
			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery(); // ���Ϲ޾ƿͼ� �����͸� ����� ��ü����
			model.setNumRows(0);
			if(sqlCheck == false) {
				while (rs.next()) { // ���� ���� �����ͼ� ���̺����� �߰�
					//model.addColumn("��ǰ��", (new Object[] {"�ݶ�", "���̴�", "ȯŸ",
						//"�Ҷ��Ҷ�", "��ī��", "���", "�ܹ���", "�ֵ���"}));
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

				con.close(); // ��ü ������ �ݴ� ������ ����� ��ü�� �ݾ��ش�.

			} catch (Exception e2) {
			
			}

		}

	}

	

}
