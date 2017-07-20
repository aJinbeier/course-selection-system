import java.awt.*;  

import javax.swing.*;  

import java.sql.*; 
import java.util.*; 

import javax.swing.filechooser.*;

import java.io.*;
import java.awt.event.*;  
public class AddSC extends JPanel implements ActionListener{
	JTextField courseno,studentno,score;
	JButton input;   

	public AddSC() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			System.err.println("不能设置外观:   " + e);
		}
		courseno = new JTextField(12);
		studentno = new JTextField(12);
		score = new JTextField(12);
		input = new JButton("input");
		input.addActionListener(this);
		Box box1 = Box.createHorizontalBox();// 横放box
		Box box2 = Box.createHorizontalBox();
		Box box3 = Box.createHorizontalBox();
		Box box4 = Box.createHorizontalBox();
		box1.add(new JLabel("courseno:"));
		box1.add(courseno);
		box2.add(new JLabel("studentno:"));
		box2.add(studentno);
		box3.add(new JLabel("score:"));
		box3.add(score);
		box4.add(input);
		Box boxH = Box.createVerticalBox();// 竖放box
		boxH.add(box1);
		boxH.add(box2);
		boxH.add(box3);
		boxH.add(box4);
		boxH.add(Box.createVerticalGlue());
		JPanel messPanel = new JPanel();
		messPanel.add(boxH);
		setLayout(new BorderLayout());
		add(messPanel, BorderLayout.CENTER);
		validate();
	}
	
	public void actionPerformed(ActionEvent c){  
		Object obj=c.getSource();  
		if(obj==input){   
			if(courseno.getText().equals("")||studentno.getText().equals("")){    
				JOptionPane.showMessageDialog(this,"填写courseno与studentno才能input！" );
			}   
			else { 
				Statement stmt=null; 
				ResultSet rs=null,rs1=null,rsC=null,rsS=null;   
				String sql,sql1,sqlS,sqlC; 
				sqlC="select * from course where Cno='"+courseno.getText()+"'"; 
				sqlS="select * from student where Sno='"+studentno.getText()+"'"; 
				sql1="select * from s_c where Cno='"+courseno.getText()+"' and Sno='"+studentno.getText()+"'";
				sql="insert into S_C values('"+courseno.getText()+"','"+studentno.getText()+"','"+score.getText()+"')";
				try{  
					Connection dbConn1=CONN(); 
					stmt=(Statement)dbConn1.createStatement();
					rsC=stmt.executeQuery(sqlC);
					if(rsC.next()){     
						rsS=stmt.executeQuery(sqlS);     
						if(rsS.next()){ 
							rs1=stmt.executeQuery(sql1); 
							if(rs1.next()){
								JOptionPane.showMessageDialog(this,"该学生以选该课程号，无法添加");
							} 
							else{ 
								stmt.executeUpdate(sql);  
								JOptionPane.showMessageDialog(this,"添加成功");    
							} 
							rs1.close();    
						}     
						else{
							JOptionPane.showMessageDialog(this,"该学生不存在，无法添加");
						}     
						rsS.close();    
					} 
					else{
						JOptionPane.showMessageDialog(this,"该课程不存在，无法添加");
					}   
					rsC.close(); 
					stmt.close();     
				} 
				catch(SQLException e){
					System.out.print("SQL Exception occur.Message is:"+e.getMessage());      }  
				} 
		}
	}
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
