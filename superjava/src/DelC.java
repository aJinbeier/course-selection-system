import java.awt.*;
import java.awt.event.*;
import java.sql.*;

import javax.swing.*;

public class DelC extends JPanel implements	ActionListener{
	String save	= null;
	JTextField cno1,cno,cname;
	JButton	shanchu,chazhao;
	
	public DelC(){
		try	{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch(Exception	e){
			System.err.println("不能设置外观:	  "+e);
		}
	
		cno1=new JTextField(12);
		cno=new JTextField(12);
		cname=new JTextField(12);
		shanchu=new	JButton("删除");
		chazhao=new	JButton("查找");
		Box	box1=Box.createHorizontalBox();//横放box
		Box	box2=Box.createHorizontalBox();
		Box	box3=Box.createHorizontalBox();
		Box	box4=Box.createHorizontalBox();
		Box	box5=Box.createHorizontalBox();
		box1.add(new JLabel("课号:",JLabel.CENTER));
		box1.add(cno);
		box2.add(new JLabel("课名:",JLabel.CENTER));
		box2.add(cname);
		box4.add(shanchu);
		box5.add(new JLabel("课号:",JLabel.CENTER));
		box5.add(cno1);
		box5.add(chazhao);
		Box	boxH=Box.createVerticalBox();//竖放box
		boxH.add(box1);
		boxH.add(box2);
		boxH.add(box3);
		boxH.add(box4);
		boxH.add(Box.createVerticalGlue());
	
		shanchu.addActionListener(this);
		chazhao.addActionListener(this);
	
		JPanel picPanel=new	JPanel();
		JPanel messPanel=new JPanel();
		messPanel.add(box5);
		picPanel.add(boxH);
		setLayout(new BorderLayout());
		JSplitPane splitV=new JSplitPane(JSplitPane.VERTICAL_SPLIT,messPanel,picPanel);//分割
		add(splitV,BorderLayout.CENTER);
		validate();

	}
	public void	actionPerformed(ActionEvent	e){
		Object obj=e.getSource();
		Statement stmt=null;
		ResultSet rs=null,rs1=null;
		String sql=null,sql1=null,sqlSC=null;
		if(obj==chazhao){
			if(cno1.getText().equals(""))
				JOptionPane.showMessageDialog(this,"请填写查询的课号！" );
			else{
				sql1="select * from	course where	Cno='"+cno1.getText()+"'";
				try{
						Connection dbConn1=CONN();
						stmt=(Statement)dbConn1.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
						rs1=stmt.executeQuery(sql1);
						if(rs1.next()){
							cno.setText(rs1.getString("cno").trim());
							cname.setText(rs1.getString("Cname").trim());
							save=cno1.getText().trim();			
						}else{
							JOptionPane.showMessageDialog(this,"没有这个课号的课程"	);
						}
						stmt.close();
						rs1.close();
					}catch(SQLException	e1){
						System.out.print("SQL Exception occur.Message is:"+e1.getMessage());
					}
				}
			}else{
				if(obj==shanchu){
					if(save==null)
						JOptionPane.showMessageDialog(this,"还没查找需要修改的课程" );
					else{
						sql="delete from course	where Cno='"+save+"'";
						sqlSC="delete from s_c where	Cno='"+save+"'";
						try{
							Connection dbConn1=CONN();
							stmt=(Statement)dbConn1.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
							stmt.executeUpdate(sql);
							stmt.executeUpdate(sqlSC);
							save=null;
							JOptionPane.showMessageDialog(this,"删除完成" );
							cno.setText("");
							cname.setText("");
			
							stmt.close();
						}catch(SQLException	e1){
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
