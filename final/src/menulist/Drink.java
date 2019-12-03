package menulist;
 
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataOutputStream;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import DBConnection.ProductDB;

public class Drink extends JPanel{
	Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 18);
	JLabel LabelResult;//총합구하는 라벨
	  JButton minus[],plus[];
	  //제고 빼는 버튼 //재고 더하는 버튼
	  JButton btnImg[],ok[];
	  		//음식 선택버튼, 확인
	  int sum;
	  //database db = new database();
	  JLabel LabelPrice[];
	  JTextField suja[];
	  int count = 0;
	    String show = "";
	    String menu[] = { "콜라", "환타"};
	    int price[] = { 1000, 1000 };
	    Menu MainMenu;
	    
	    int PcNum;
		DataOutputStream Queryout;

	    
	    ImageIcon[] img = { 
	            new ImageIcon("Image/drink1.png"),
	            new ImageIcon("Image/drink2.png"),
	            new ImageIcon("Image/no.png"),
	            new ImageIcon("Image/no.png"),
	            new ImageIcon("Image/no.png"),
	            new ImageIcon("Image/no.png"),
	            new ImageIcon("Image/no.png"),
	            new ImageIcon("Image/no.png")};
	    
	    void btnOrderM(JTable table) {//추가 버튼 함수
	        JOptionPane.showMessageDialog(null,  " 주문되었습니다. \n이용해주셔서 감사합니다.");
	          for (int i = 0; i < table.getRowCount(); i++)          	
	          {
	              btnImg[i].setEnabled(true);
	              minus[i].setEnabled(false);
	              plus[i].setEnabled(false);
	              suja[i].setText("0");
	              }
	    }
	    void btnClearM(JTable table) {//삭제버튼 함수
	          for (int i = 0; i < menu.length; i++) {
	             btnImg[i].setEnabled(true);
	              minus[i].setEnabled(false);
	              plus[i].setEnabled(false);
	              suja[i].setText("0");
	              DefaultTableModel model = (DefaultTableModel)table.getModel();
	              model.setNumRows(0);
	              LabelResult.setText("총합의금액 = 0");
	          }
	    }
	public Drink() {

  btnImg = new JButton[menu.length];
  suja = new JTextField[menu.length];
  minus = new JButton[menu.length];
  plus = new JButton[menu.length];
  ok = new JButton[menu.length];
  LabelPrice = new JLabel[menu.length];

  
  for (int i = 0; i < menu.length; i++) {
      btnImg[i] = new JButton();
      btnImg[i].setIcon(img[i]);
      if (i < 4) {
          btnImg[i].setBounds(25 + i * 150, 20, 100, 100);
      } else {
          btnImg[i].setBounds(25 + (i - 4) * 150, 220, 100, 100);
      }
      btnImg[i].setBorderPainted(false);
      btnImg[i].setContentAreaFilled(false);
      btnImg[i].setFocusPainted(false);
      
      suja[i] = new JTextField("0");
      suja[i].setBackground(Color.white);
      suja[i].setEditable(false);
      suja[i].setBounds(btnImg[i].getX() + 25, btnImg[i].getY() + 130, 50, 20);

      // "-" 버튼
      minus[i] = new JButton("");
      minus[i].setIcon(new ImageIcon(Menu.class.getResource("/image/button/m.PNG")));
      minus[i].setBounds(btnImg[i].getX(), suja[i].getY(), 20, 20);
      minus[i].setEnabled(false);

      // "+" 버튼
      plus[i] = new JButton("");
      plus[i].setIcon(new ImageIcon(Menu.class.getResource("/image/button/P.PNG")));
      plus[i].setBounds(btnImg[i].getX() + (100 - 20), suja[i].getY(), 20, 20);
      plus[i].setEnabled(false);

      // 가격
      LabelPrice[i] = new JLabel(price[i] + "원");
      LabelPrice[i].setBounds(btnImg[i].getX() + 20, suja[i].getY() - 25, 100, 20);
      LabelPrice[i].setForeground(Color.white);
      // 확인 버튼
      ok[i] = new JButton("확인");
      ok[i].setBounds(btnImg[i].getX(), suja[i].getY() + 30, 100, 20);
      ok[i].setEnabled(false);

      add(btnImg[i]);
      add(suja[i]);
      add(minus[i]);
      add(plus[i]);
      add(LabelPrice[i]);
      add(ok[i]);
  }
  
LabelResult = new JLabel("총합의 금액 = 0");
LabelResult.setForeground(Color.BLACK);
LabelResult.setBackground(Color.BLACK);
LabelResult.setToolTipText("");
LabelResult.setHorizontalAlignment(SwingConstants.RIGHT);
setLayout(null);
//이벤트단
for (int i = 0; i < menu.length; i++) {
    
	int j = i;
// 햄버그 버튼 이벤트
	btnImg[i].addActionListener(new ActionListener() {
     @Override
     public void actionPerformed(ActionEvent e) {
         minus[j].setEnabled(true);
         plus[j].setEnabled(true);
         btnImg[j].setEnabled(false);
         ok[j].setEnabled(true);

         count = 0;
     }
 });
// "-" 버튼 이벤트
 minus[i].addActionListener(new ActionListener() {
     @Override
     public void actionPerformed(ActionEvent e) {
         if (count > 0) {
             count = count - 1;
             suja[j].setText(count + "");
             ok[j].setEnabled(true);
         } else {
             minus[j].setEnabled(false);
         }
     }
 });
// "+" 버튼 이벤트
 plus[i].addActionListener(new ActionListener() {
 	 
     @Override
     public void actionPerformed(ActionEvent e) {
         count = count + 1;
         suja[j].setText(count + "");
         ok[j].setEnabled(true);
         if (count > 0) {
             minus[j].setEnabled(true);
         }
     }
 });
 ok[i].addActionListener(new ActionListener() { 
     @Override
     public void actionPerformed(ActionEvent e) {
    	 
         sum += price[j]*count; 

        /* for (int i = 0; i<me.table.getRowCount(); i++){ 
        	 //int amount = Integer.parseInt(me.table.getValueAt(i, 3)+""); 
        	 //total += amount; 
         }*/

         //LabelResult.setText("총합의 금액 = "+Integer.toString(sum)); 
         
         //System.out.println(count);
         //new Menu(sum);
         
         
         String menuDB[] = {"Coke", "Fanta"
         };
         
         //String InsertQuery = "insert into stock(Coke) Values(" + count + ");";
         String selectQuery = "update stock set Coke = Coke-" + count +
         		"where Coke > " + count + ";";
         
         String select1Query = "update stock set "+ menuDB[j] + "="+ menuDB[j] + "-" + count +
         		"where " + menuDB[j] + ">"+ count + ";";
                          
         try {
         	Queryout.writeUTF("주문");
 			Queryout.writeInt(PcNum);
 			Queryout.writeUTF(menuDB[j]);
 			Queryout.writeInt(count);
 			Queryout.writeInt(sum);
 			Queryout.flush();
 			
 		} catch (IOException e1) {
 			// TODO Auto-generated catch block
 			e1.printStackTrace();
 		}
         
         //db.executeUpdate(select1Query);
         
         System.out.println(j);
         show = btnImg[j].getActionCommand();
         String[] order = { menu[j], String.valueOf(price[j]), String.valueOf(count), String.valueOf(price[j]*count) };
         
         Menu me = new Menu(order);
         //me.AddOrder(order);
         //me.table.updateUI();
         ok[j].setEnabled(false);
     }
 });
 }
}
	public void Drink(int a, DataOutputStream out) {
		PcNum = a;
		Queryout = out;
	}
	
}
