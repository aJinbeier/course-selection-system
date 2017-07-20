import java.awt.*;
import java.awt.event.*;
import java.sql.*;

import javax.swing.*;

public class DelSC extends JPanel implements ActionListener{
	String saveC=null;
	String saveS=null;
	JTextField KH1,XH1,XH,KH,CJ;
	JButton shanchu,chazhao;
	
	public DelSC(){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}catch(Exception e){
			System.err.println("不能设置外观:   "+e);
		}
	
		XH1=new JTextField(12);
		KH1=new JTextField(12);
		KH=new JTextField(12);
		XH=new JTextField(12);
		CJ=new JTextField(12);
		shanchu=new JButton("删除");
		chazhao=new JButton("查找");
	
		Box box1=Box.createHorizontalBox();//横放box
		Box box2=Box.createHorizontalBox();
		Box box3=Box.createHorizontalBox();
		Box box4=Box.createHorizontalBox();
		Box box5=Box.createHorizontalBox();
		box1.add(new JLabel("课号:",JLabel.CENTER));
		box1.add(KH);
		box2.add(new JLabel("学号:",JLabel.CENTER));
		box2.add(XH);
		box3.add(new JLabel("成绩:",JLabel.CENTER));
		box3.add(CJ);
		box4.add(shanchu);
		box5.add(new JLabel("课号:",JLabel.CENTER));
		box5.add(KH1);
		box5.add(new JLabel("学号:",JLabel.CENTER));
		box5.add(XH1);
		box5.add(chazhao);
		Box boxH=Box.createVerticalBox();//竖放box
		boxH.add(box1);
		boxH.add(box2);
		boxH.add(box3);
		boxH.add(box4);
		boxH.add(Box.createVerticalGlue());
	
		shanchu.addActionListener(this);
		chazhao.addActionListener(this);
	
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
		String sql=null,sql1=null;
	
		if(obj==chazhao){
			if(KH1.getText().equals("")||XH1.getText().equals(""))
				JOptionPane.showMessageDialog(this,"请填写完成查询的信息！" );
			else{
				sql1="select * from s_c where Cno='"+KH1.getText()+"' and Sno='"+XH1.getText()+"'";
				try{
					Connection dbConn1=CONN();
					stmt=(Statement)dbConn1.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
					rs1=stmt.executeQuery(sql1);
					if(rs1.next()){
						KH.setText(rs1.getString("Cno").trim());
						XH.setText(rs1.getString("Sno").trim());
						CJ.setText(rs1.getString("score").trim());
						saveC=KH1.getText().trim();	
						saveS=XH1.getText().trim();
					}else{
						JOptionPane.showMessageDialog(this,"没有这个课号的学生" );
					}
					stmt.close();
					rs1.close();
				}catch(SQLException e1){
					System.out.print("SQL Exception occur.Message is:"+e1.getMessage());
				}
			}
		}else{
			if(obj==shanchu){
				if(saveC==null||saveS==null)
					JOptionPane.showMessageDialog(this,"还没查找需要修改的学生/课程" );
				else{
					sql="delete from s_c where Cno='"+saveC+"' and Sno='"+saveS+"'";
					try{
						Connection dbConn1=CONN();
						stmt=(Statement)dbConn1.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
						stmt.executeUpdate(sql);
						saveC=null;
						saveS=null;
						JOptionPane.showMessageDialog(this,"删除完成" );
						KH.setText("");
						XH.setText("");
						CJ.setText("");
						stmt.close();
					}catch(SQLException e1){
						System.out.print("SQL Exception occur.Message is:"+e1.getMessage());
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
