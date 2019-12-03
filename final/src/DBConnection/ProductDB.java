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
         System.out.println("����̹� ���� Ȯ��");
         String url = "jdbc:sqlserver://localhost:1433;databaseName=stock";
         con = DriverManager.getConnection(url, "sa", "1234");
      } catch (ClassNotFoundException e) {
         System.out.println("����̹� ������ : ���� ��ġ�� Ŀ���͸� �߰��Ͻÿ�");
      }
      catch (SQLException e) {
         System.out.println("�����ͺ��̽� ���� ���� : ���̵�, �н�����, �����ͺ��̽� ���� ���� Ȯ���Ͻÿ�");
      }
   }
   //insert, update, delete ������
   public int executeUpdate(String sql) {
      Statement ps = null;
      System.out.println("���۵� ������ : " + sql);
      try {
         ps = con.createStatement();
         return ps.executeUpdate(sql);
      } catch (SQLException e) {
         e.printStackTrace();
      }
      return 0;
   }
   //select ������
   public ResultSet executeQuery(String sql) {
      Statement ps = null;
     
      System.out.println("���۵� ������ : " + sql);
      try {
         ps = con.createStatement();
         return ps.executeQuery(sql);
      } catch (SQLException e) {
         e.printStackTrace();
      }
      return null;
   }   
}
