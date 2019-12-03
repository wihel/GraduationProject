package Server_DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import DBConnection.UserCheckDB;



public class LoginMemberProcess {

	
	// �α��� �޼ҵ�
	//���̵�� ��й�ȣ�� ��ġ�ϸ� True���� ClientLoginŬ������ ��ȯ
	public static boolean loginMember(String id, String pass) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		String DBPass = null;
		boolean flag = false;
		UserCheckDB pool = null;

		try {
			pool = UserCheckDB.getInstance();

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			con = pool.getConnection();
			sql = "select password from member where id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			// System.out.println("id ���� " + id);
			rs = pstmt.executeQuery();
			rs.next();
			DBPass = rs.getString("password");
			// System.out.println(hashPass);		
			
			
			if (pass.equals(DBPass) && DBPass.equals(pass))
				flag = true;
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return flag;
	}
}
