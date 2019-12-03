package menulist;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.IOException;

import javax.management.Query;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import DBConnection.ProductDB;

public class Popularitemenu extends JPanel{
	Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 18);
	JLabel LabelResult;//���ձ��ϴ� ��
	  JButton minus[],plus[];
	  //���� ���� ��ư //��� ���ϴ� ��ư
	  JButton btnImg[],ok[];
	  		//���� ���ù�ư, Ȯ��
	  int sum;
	  int count = 0;
	  //database db = new database();
	  int PcNum;
	  DataOutputStream Queryout;
      Menu Mainmenu;
      
	  JLabel LabelPrice[];
	  JTextField suja[];
	    String show = "";
	    String menu[] = { "�ݶ�", "���̴�", "ȯŸ", "�Ҷ��Ҷ�", "��ī��", "���", "�Ұ�����", "�ֵ���" };
	    int price[] = { 1000, 1000, 1000, 1500, 2500, 2500, 2700, 2500 };
	    ImageIcon[] img = { 
	    		new ImageIcon("Image/1.png"),
	    		new ImageIcon("Image/2.png"),
	    		new ImageIcon("Image/3.png"),
	    		new ImageIcon("Image/4.png"),
	    		new ImageIcon("Image/5.png"),
	    		new ImageIcon("Image/6.png"),
	    		new ImageIcon("Image/7.png"),
	    		new ImageIcon("Image/8.png")};
	    
	    void btnOrderM(JTable table) {//�߰� ��ư �Լ�
	        JOptionPane.showMessageDialog(null,  " �ֹ��Ǿ����ϴ�. \n�̿����ּż� �����մϴ�.");
	          for (int i = 0; i < table.getRowCount(); i++)          	
	          {
	              btnImg[i].setEnabled(true);
	              minus[i].setEnabled(false);
	              plus[i].setEnabled(false);
	              suja[i].setText("0");
	              }
	    }
	    void btnClearM(JTable table) {//������ư �Լ�
	          for (int i = 0; i < menu.length; i++) {
	             btnImg[i].setEnabled(true);
	              minus[i].setEnabled(false);
	              plus[i].setEnabled(false);
	              suja[i].setText("0");
	              DefaultTableModel model = (DefaultTableModel)table.getModel();
	              model.setNumRows(0);
	              LabelResult.setText("�����Ǳݾ� = 0");
	          }
	    }

	public Popularitemenu() {

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

      // "-" ��ư
      minus[i] = new JButton("");
      minus[i].setIcon(new ImageIcon(Menu.class.getResource("/image/button/m.PNG")));
      minus[i].setBounds(btnImg[i].getX(), suja[i].getY(), 20, 20);
      minus[i].setEnabled(false);

      // "+" ��ư
      plus[i] = new JButton("");
      plus[i].setIcon(new ImageIcon(Menu.class.getResource("/image/button/P.PNG")));
      plus[i].setBounds(btnImg[i].getX() + (100 - 20), suja[i].getY(), 20, 20);
      plus[i].setEnabled(false);

      // ����
      LabelPrice[i] = new JLabel(price[i] + "��");
      LabelPrice[i].setBounds(btnImg[i].getX() + 20, suja[i].getY() - 25, 100, 20);
      LabelPrice[i].setForeground(Color.white);
      // Ȯ�� ��ư
      ok[i] = new JButton("Ȯ��");
      ok[i].setBounds(btnImg[i].getX(), suja[i].getY() + 30, 100, 20);
      ok[i].setEnabled(false);

      add(btnImg[i]);
      add(suja[i]);
      add(minus[i]);
      add(plus[i]);
      add(LabelPrice[i]);
      add(ok[i]);
  }
  
LabelResult = new JLabel("������ �ݾ� = 0");
LabelResult.setForeground(Color.BLACK);
LabelResult.setBackground(Color.BLACK);
LabelResult.setToolTipText("");
LabelResult.setHorizontalAlignment(SwingConstants.RIGHT);
setLayout(null);
//�̺�Ʈ��
for (int i = 0; i < menu.length; i++) {
    
	int j = i;
// �ܹ��� ��ư �̺�Ʈ
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
// "-" ��ư �̺�Ʈ
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
// "+" ��ư �̺�Ʈ
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
    	 
         sum = price[j]*count; 
         //Mainmenu = new Menu(sum);
         
        /* for (int i = 0; i<me.table.getRowCount(); i++){ 
        	 //int amount = Integer.parseInt(me.table.getValueAt(i, 3)+""); 
        	 //total += amount; 
         }*/

         LabelResult.setText("������ �ݾ� = "+Integer.toString(sum)); 
         
         System.out.println(count);
         
         
         String menuDB[] = {"Coke", "Cider", "Fanta", "sodduck", "Pikachu", "Lamen",
         		"Hamburger","Hotdog"
         };
         
         //String InsertQuery = "insert into stock(Coke) Values(" + count + ");";
         //String selectQuery = "update stock set Coke = Coke-" + count +
         		//"where Coke > " + count + ";";
         
         String updateQuery = "update stock set "+ menuDB[j] + "="+ menuDB[j] + "-" + count +
         		"where " + menuDB[j] + ">"+ count + ";";
         
         try {
        	Queryout.writeUTF("�ֹ�");
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
	
	
	public void Popularitemenu(int a, DataOutputStream out) {
		PcNum = a;
		Queryout = out;
	}
	
}
