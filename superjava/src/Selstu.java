import java.awt.*;
import java.awt.event.*;
import java.sql.*;

import javax.swing.*;

public class Selstu extends JPanel implements ActionListener{
	JTextField XH,XM,XB;
	JButton ch;
	JTextField KH,KM;
	JButton ch1;
	JTextField KH1,XH1,CJ;
	JButton ch2;

	public Selstu (){
		XH=new JTextField(12);
		XM=new JTextField(12);
		XB=new JTextField(12);
		KH=new JTextField(12);
		KM=new JTextField(12);
		KH1=new JTextField(12);
		XH1=new JTextField(12);
		CJ=new JTextField(12);
		ch=new JButton("查找学生信息");
		ch1=new JButton("查找课程信息");
		ch2=new JButton("查找选课信息");
	
		
		Box box1=Box.createHorizontalBox();//横放box
		Box box2=Box.createHorizontalBox();
		Box box4=Box.createHorizontalBox();
		Box box5=Box.createHorizontalBox();
		Box box6=Box.createHorizontalBox();
		Box box7=Box.createHorizontalBox();
		box1.add(new JLabel("学号:",JLabel.CENTER));
		box1.add(XH);
		box1.add(new JLabel("姓名:",JLabel.CENTER));
		box1.add(XM);
		box1.add(new JLabel("系别:",JLabel.CENTER));
		box1.add(XB);
		box2.add(ch);
		
		box4.add(new JLabel("课号:",JLabel.CENTER));
		box4.add(KH);
		box4.add(new JLabel("课名:",JLabel.CENTER));
		box4.add(KM);
		box6.add(ch1);
		
		box5.add(new JLabel("课号:",JLabel.CENTER));
		box5.add(KH1);
		box5.add(new JLabel("学号:",JLabel.CENTER));
		box5.add(XH1);
		box5.add(new JLabel("成绩:",JLabel.CENTER));
		box5.add(CJ);
		box7.add(ch2);

		Box boxH1=Box.createVerticalBox();//竖放box
		boxH1.add(box1);
		boxH1.add(box2);
		boxH1.add(Box.createVerticalGlue());
		Box boxH2=Box.createVerticalBox();//竖放box
		boxH2.add(box4);
		boxH2.add(box6);
		boxH2.add(Box.createVerticalGlue()); 
		Box boxH3=Box.createVerticalBox();//竖放box
		boxH3.add(box5);
		boxH3.add(box7);
		boxH3.add(Box.createVerticalGlue()); 
               
		ch.addActionListener(this);
		ch1.addActionListener(this);
		ch2.addActionListener(this);
        
		JPanel messPanel=new JPanel();
		JPanel picPanel=new JPanel();
		JPanel threePanel=new JPanel();
		messPanel.add(boxH1);
		picPanel.add(boxH2);
		threePanel.add(boxH3);
		setLayout(new BorderLayout());
		JSplitPane splitV=new JSplitPane(JSplitPane.VERTICAL_SPLIT,messPanel,picPanel);//分割
		add(splitV,BorderLayout.CENTER);
		JSplitPane splitV1=new JSplitPane(JSplitPane.VERTICAL_SPLIT,splitV,threePanel);//分割
		add(splitV1,BorderLayout.CENTER);
		validate();
		
	
	}
	
	public void actionPerformed(ActionEvent c){
		Object obj=c.getSource();
		Statement stmt=null;
		ResultSet rs=null;
		int row=0;
		int i=0;
	    String sql=null;
	    Students K;
	    SelC K1;
	    SelSC K2;
		if(obj==ch){
			if(XH.getText().equals("")&&XM.getText().equals("")&&XB.getText().equals("")){
				sql="select * from Student ";System.out.print("000");
			}
			else{
				if(XH.getText().equals("")){
					if(XM.getText().equals("")){
						sql="select * from student where major like'%"+XB.getText()+"%'";System.out.print("001");}
					else{if(XB.getText().equals("")){sql="select * from student where sname like'%"+XM.getText()+"%'";System.out.print("010");}
					     else{sql="select * from student where sname like'%"+XM.getText()+"%'and major like'%"+XB.getText()+"%'";System.out.print("011");}}}
				else{if(XM.getText().equals("")){
					      if(XB.getText().equals("")){sql="select * from student where sno like'%"+XH.getText()+"%'";System.out.print("100");}
				          else{sql="select * from student where sno like'%"+XH.getText()+"%' and major like'%"+XB.getText()+"%'";System.out.print("101");}}
				     else{if(XB.getText().equals("")){sql="select * from student where  sno like'%"+XH.getText()+"%' and sname like'%"+XM.getText()+"%'";System.out.print("110");}
				           else{sql="select * from student where  sno like'%"+XH.getText()+"%' and sname like'%"+XM.getText()+"%' and major like'%"+XB.getText()+"%'";System.out.print("111");}}}
		}
			K=new Students(sql);
		 }
		else{if(obj==ch1){
			if(KH.getText().equals("")&&KM.getText().equals("")){
				sql="select * from course ";System.out.print("00");
			}
			else{
				if(KH.getText().equals("")){sql="select * from course where cname like'%"+KM.getText()+"%'";System.out.print("01");}
				else{if(XB.getText().equals("")){sql="select * from course where cno like'%"+KH.getText()+"%'";System.out.print("10");}
				     else{sql="select * from course where  cno like'%"+KH.getText()+"%' and cname like'%"+XM.getText()+"%'";System.out.print("11");}
			}
			}
			 K1=new SelC(sql);}
		else{if(obj==ch2){
			if(KH1.getText().equals("")&&XH1.getText().equals("")&&CJ.getText().equals("")){
				sql="select sc.cno,cname,sc.sno,sname,score from sc,c,s where c.cno=sc.cno and s.sno=sc.sno";System.out.print("000");//000
			}
			else{
				if(KH1.getText().equals("")){
					if(XH1.getText().equals("")){
			/**/			sql="select sc.cno,cname,sc.sno,sname,score from sc,c,s where course like'%"+CJ.getText()+"%' and course.cno=sc.cno and student.sno=sc.sno";System.out.print("001");}
					else{if(CJ.getText().equals("")){sql="select sc.cno,cname,sc.sno,sname,score from sc,course,student where sc.sno like'%"+XH1.getText()+"%' and course.cno=sc.cno and student.sno=sc.sno";System.out.print("010");}
					     else{sql="select sc.cno,cname,sc.sno,sname,score from sc,c,s where sc.sno like'%"+XH1.getText()+"%'and scorse like'%"+CJ.getText()+"%' and course.cno=sc.cno and student.sno=sc.sno";System.out.print("011");}}}
				else{if(XH1.getText().equals("")){
					      if(CJ.getText().equals("")){sql="select sc.cno,cname,sc.sno,sname,score from sc,course,student where sc.cno like'%"+KH1.getText()+"%' and c.cno=sc.cno and student.sno=sc.sno";System.out.print("100");}
				          else{sql="select sc.cno,cname,sc.sno,sname,score from sc,course,student where sc.cno like'%"+KH1.getText()+"%' and score like'%"+CJ.getText()+"%' and course.cno=sc.cno and student.sno=sc.sno";System.out.print("101");}}
				     else{if(CJ.getText().equals("")){sql="select sc.cno,cname,sc.sno,sname,score from sc,course,student where  sc.cno like'%"+KH1.getText()+"%' and sc.sno like'%"+XH1.getText()+"%' and course.cno=sc.cno and student.sno=sc.sno";System.out.print("110");}
				           else{sql="select sc.cno,cname,sc.sno,sname,score from sc,course,student where  sc.cno like'%"+KH1.getText()+"%' and sc.sno like'%"+XH1.getText()+"%' and score like'%"+CJ.getText()+"%' and course.cno=sc.cno and student.sno=sc.sno";System.out.print("111");}}}
			}
			K2=new SelSC(sql);
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
