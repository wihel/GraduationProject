package control.manage_member.dbprocess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import DBConnection.UserCheckDB;
import model_manage_member.MemberInfo;

public class ReadMemberProcess {

	// ȸ�� DB�� ���� ���� �о�´�
	public static void readMember(ArrayList<MemberInfo> list) {

		ResultSet rs = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		UserCheckDB pool = null;

		try {
			pool = UserCheckDB.getInstance();

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {

			int num = 0;
			// id, tel, mileage, age
			con = pool.getConnection();
			sql = "select id,tel,mileage,age from member where id<>'admin'";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				String id = rs.getString("id");
				String tel = rs.getString("tel");
				String mileage = new Integer(rs.getInt("mileage")).toString();
				String age = new Integer(rs.getInt("age")).toString();
				list.add(new MemberInfo(id, tel, mileage, age));
				num++;

			}
			if (num == 0) {
				JOptionPane.showMessageDialog(null, "��ȸ�� �����Ͱ� ���� ���� �ʽ��ϴ�.");
			}

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			pool.freeConnection(con, pstmt);
		}

	}
	// ȸ��DB���� ���о� ���� �޼ҵ� ����
	
	
	//Ư�� ȸ�� ���� �ҷ��´�.
	/*public static MemberInfo readPerson(String id) {

		ResultSet rs = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		DBConnectionMgr pool = null;
		MemberInfo memberInfo=null;
		
		try {
			pool = DBConnectionMgr.getInstance();

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {

			int num = 0;
			// id, tel, mileage, age
			con = pool.getConnection();
			sql = "select id,tel,mileage,age from member where id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				String id2 = rs.getString("id");
				String tel = rs.getString("tel");
				String mileage = new Integer(rs.getInt("mileage")).toString();
				String age = new Integer(rs.getInt("age")).toString();
				memberInfo = new MemberInfo(id2, tel, mileage, age);
				num++;
			}
			if (num == 0) {
				JOptionPane.showMessageDialog(null, "��ȸ�� �����Ͱ� ���� ���� �ʽ��ϴ�.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		return memberInfo;

	}*/
	// ȸ��DB���� ���о� ���� �޼ҵ� ����
	

}
