package Server_DB;

import java.sql.Connection;

import java.sql.PreparedStatement;

import DBConnection.UserCheckDB;



public class JoinMemberProcess {

	public static int insertMember(String id, String pass, String tel, int age) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		int flag = 0; // 0은 실패, 1= 회원가입 성공, 2= 아이디가 중복됨
		UserCheckDB pool = null;
		
		try {
			pool = UserCheckDB.getInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			//num, id, password, tel, mileage, age
			con = pool.getConnection();
			sql = "insert member(id, password, tel, mileage, age )"
					+ "values(?,?,?,?,?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pass);
			pstmt.setString(3, tel);
			pstmt.setInt(4, 0);
			pstmt.setInt(5, age);
			
			// executeupdate() = 적용된 행의 갯수를 리턴
			if(pstmt.executeUpdate() == 1)
				flag = 1;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		return flag;
	}
	
}
