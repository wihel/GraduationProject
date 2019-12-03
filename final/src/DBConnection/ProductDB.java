package DBConnection;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ProductDB {

   Connection con = null;
   ResultSet rs;
   
   public ProductDB(){
      try {
         Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
         System.out.println("드라이버 적용 확인");
         String url = "jdbc:sqlserver://localhost:1433;databaseName=stock";
         con = DriverManager.getConnection(url, "sa", "1234");
      } catch (ClassNotFoundException e) {
         System.out.println("드라이버 미적용 : 빌드 패치에 커넥터를 추가하시오");
      }
      catch (SQLException e) {
         System.out.println("데이터베이스 접속 실패 : 아이디, 패스워드, 데이터베이스 설정 등을 확인하시오");
      }
   }
   //insert, update, delete 쿼리문
   public int executeUpdate(String sql) {
      Statement ps = null;
      System.out.println("전송된 쿼리문 : " + sql);
      try {
         ps = con.createStatement();
         return ps.executeUpdate(sql);
      } catch (SQLException e) {
         e.printStackTrace();
      }
      return 0;
   }
   //select 쿼리문
   public ResultSet executeQuery(String sql) {
      Statement ps = null;
     
      System.out.println("전송된 쿼리문 : " + sql);
      try {
         ps = con.createStatement();
         return ps.executeQuery(sql);
      } catch (SQLException e) {
         e.printStackTrace();
      }
      return null;
   }   
}
