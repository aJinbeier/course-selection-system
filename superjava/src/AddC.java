import java.awt.*;

import javax.swing.*;

import java.sql.*;
import java.awt.event.*;
public class AddC extends JPanel implements ActionListener{
	 JTextField courseno,coursename;
	 JButton input;   
	public AddC(){ 
	 try {
		 UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	}
	 catch(Exception e){
		System.err.println("不能设置外观:   "+e);}   
	 	courseno=new JTextField(12);
	 	coursename=new JTextField(12);
	 	input=new JButton("input");
	 	input.addActionListener(this); 
	 	Box box1=Box.createHorizontalBox();//横放box
	 	Box box2=Box.createHorizontalBox();
	 	Box box3=Box.createHorizontalBox();
	 	Box box4=Box.createHorizontalBox();
	 	box1.add(new JLabel("courseno:"));
	 	box1.add(courseno); 
	 	box2.add(new JLabel("coursename:"));
	 	box2.add(coursename); 
	 	box4.add(input); 
	 	Box boxH=Box.createVerticalBox();//竖放box
	 	boxH.add(box1);
	 	boxH.add(box2); 
	 	boxH.add(box3);
	 	boxH.add(box4); 
	 	boxH.add(Box.createVerticalGlue()); 
	 	JPanel messPanel=new JPanel();
	 	messPanel.add(boxH); 
	 	setLayout(new BorderLayout()); 
	 	add(messPanel,BorderLayout.CENTER);
	 	validate(); 
	} 
	public void actionPerformed(ActionEvent c){
		Object obj=c.getSource();
		if(obj==input){
			if(courseno.getText().equals("")||coursename.getText().equals("")){
				JOptionPane.showMessageDialog(this,"学生信息请填满再录入！" ); 
			} 
			Statement stmt=null; 
			ResultSet rs=null,rs1=null; 
			String sql,sql1; 
			sql1="select * from course where Cno='"+courseno.getText()+"'"; 
			sql="insert into course values('"+courseno.getText()+"','"+coursename.getText()+"')"; 
			try{
				Connection dbConn1=CONN();   
				stmt=(Statement)dbConn1.createStatement();
				rs1=stmt.executeQuery(sql1);
				if(rs1.next()){
					JOptionPane.showMessageDialog(this,"该课号已存在，无法添加");
				}
				else{ 
					stmt.executeUpdate(sql);  
					JOptionPane.showMessageDialog(this,"添加成功"); 
				} 
				rs1.close();     
	    		stmt.close(); 
			} 
			catch(SQLException e){ 
				System.out.print("SQL Exception occur.Message is:"+e.getMessage());
			} 
		} 
	}  
	//连接数据库方法 
	public static Connection CONN(){
		String connectionUrl = "jdbc:sqlserver://localhost:1433;" +
				   "databaseName=st;user=mtc-ims;password=123456;";
		Connection con = null;
		Statement stmt = null;
		PreparedStatement pstmt = null;
		try{
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		}
		catch(ClassNotFoundException e){
			System.out.println(e);
		}
		try{
			con = DriverManager.getConnection(connectionUrl);
		}
		catch (SQLTimeoutException e){
			System.out.println(e);
		}
		catch(SQLException e){
			System.out.println(e);
		}
		return con;
	}
}
