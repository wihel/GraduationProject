package Server_DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import DBConnection.UserCheckDB;



//���̵� �� �޼ҵ�
public class IdCheckProcess {

	
	//���̵� �ߺ��̸� True �ߺ��� �ƴϸ� False�� JoinMember ȸ������ 
	//Ŭ���������� ��ȯ���ش�.
	public static boolean checkId(String id) {
		UserCheckDB pool = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		boolean flag = false;
		
		try {
			pool = UserCheckDB.getInstance();
		} catch(Exception e) {
			e.printStackTrace();
		}
		try {
			con = pool.getConnection();
			sql = "select id from member where id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			flag = pstmt.executeQuery().next();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return flag;
	}
}
