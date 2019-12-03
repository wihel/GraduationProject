package Server_DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import DBConnection.UserCheckDB;



//아이디 비교 메소드
public class IdCheckProcess {

	
	//아이디가 중복이면 True 중복이 아니면 False를 JoinMember 회원가입 
	//클래스쪽으로 반환해준다.
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
