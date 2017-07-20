import java.awt.*;
import java.awt.event.*;
import java.sql.*;

import javax.swing.*;

public class UpdateC extends JPanel implements ActionListener{
	String save=null;
	JTextField cno1,cno,cname;
	JButton update,search; 
	public UpdateC(){
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}  
		catch(Exception e){
			System.err.println("不能设置外观:   "+e);
		}   
		cno1=new JTextField(12);
		cno=new JTextField(12);
		cname=new JTextField(12);
		update=new JButton("update");
		search=new JButton("search");
		Box box1=Box.createHorizontalBox();//横放box  
		Box box2=Box.createHorizontalBox();
		Box box3=Box.createHorizontalBox();
		Box box4=Box.createHorizontalBox();
		Box box5=Box.createHorizontalBox();
		box1.add(new JLabel("cno:",JLabel.CENTER));
		box1.add(cno);
		box2.add(new JLabel("cname:",JLabel.CENTER));
		box2.add(cname);
		box3.add(update);
		box5.add(new JLabel("cno:",JLabel.CENTER));
		box5.add(cno1);  
		box5.add(search);
		update.addActionListener(this);
		search.addActionListener(this);
		Box boxH=Box.createVerticalBox();
		//竖放box  
		boxH.add(box1);
		boxH.add(box2);
		boxH.add(box3);
		boxH.add(box4);
		boxH.add(Box.createVerticalGlue());
		JPanel picPanel=new JPanel();
		JPanel messPanel=new JPanel();
		messPanel.add(box5);
		picPanel.add(boxH);
		setLayout(new BorderLayout());
		JSplitPane splitV=new JSplitPane(JSplitPane.VERTICAL_SPLIT,messPanel,picPanel);//分割  
		add(splitV,BorderLayout.CENTER);
		validate();
	}  
	public void actionPerformed(ActionEvent e){
		Object obj=e.getSource();
		Statement stmt=null;
		ResultSet rs=null,rs1=null;
		String sql=null,sql1=null,sqlSC=null;
		if(obj==search){
			if(cno1.getText().equals(""))
				JOptionPane.showMessageDialog(this,"请填写查询的学号！" );
			else{
				sql1="select * from course where cno='"+cno1.getText()+"'";
				try{
					Connection dbConn1=CONN();
					stmt=(Statement)dbConn1.createStatement();
					rs1=stmt.executeQuery(sql1);
					if(rs1.next()){
						cno.setText(rs1.getString("Cno").trim());
						cname.setText(rs1.getString("Cname").trim());
						save=cno1.getText();
					} 
					else{
						JOptionPane.showMessageDialog(this,"没有这个cno的课程" );
					}      
					stmt.close();
					rs1.close();
				}catch(SQLException e1){
					System.out.print("SQL Exception occur.Message is:"+e1.getMessage());
				} 
			} 
		}
		else{
			if(obj==update){
				if(save==null){
					JOptionPane.showMessageDialog(this,"还没找到需要修改的课程" );
				}  
				else{
					if(cno.getText().equals("")||cname.getText().equals("")){
						JOptionPane.showMessageDialog(this,"课程信息填满才能修改！" );
					} 
					else{
						sql="update course set Cno='"+cno.getText()+"',Cname='"+cname.getText()+"' where Cno='"+save+"'";
						if(save.trim().equals(cno.getText().trim())){
							 try{
							      Connection dbConn1=CONN();
							      stmt=(Statement)dbConn1.createStatement();
							      stmt.executeUpdate(sql);
							      save=null;
							      JOptionPane.showMessageDialog(this,"修改完成" );
							      cno.setText("");
							      cname.setText("");
							      stmt.close();
							  }
							 catch(SQLException e1){
							      System.out.print("SQL Exception occur.Message is:"+e1.getMessage());
							 }
						}
						
						else{
							sql1="select * from course where Cno='"+cno.getText()+"'";
							     try{
							        Connection dbConn1=CONN();
							        stmt=(Statement)dbConn1.createStatement();
							        rs1=stmt.executeQuery(sql1);
							        if(rs1.next()){
							        	JOptionPane.showMessageDialog(this,"已存在此cno课程" );
							        } 
							        else{
							        	sqlSC="update s_c set Sno='"+cno.getText()+"' where Cno='"+save+"'";
							        	stmt.executeUpdate(sql);
							        	stmt.executeUpdate(sqlSC);
							        	save=null;
							        	JOptionPane.showMessageDialog(null,"修改完成" );
							        	cno.setText("");
							             cname.setText("");
							        }  
							        stmt.close();
							        rs1.close();  
							        }
							     catch(SQLException e1){
							          System.out.print("SQL Exception occur.Message is:"+e1.getMessage());
							     } 
						}
					}
				}
		
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