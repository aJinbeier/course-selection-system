import java.awt.*;
import java.sql.*;
import javax.swing.*;
import java.awt.event.*;

public class User extends JFrame {
	private JLabel use, password;
	private JTextField k1;//用户名输入框	
	private JPasswordField k2;//密码输入框
	private JButton b1, b2;
	//登录窗口
	public User(JFrame f) {
		super("系统登录");
		Container c = getContentPane();
		c.setLayout(new FlowLayout());
		use = new JLabel("usename");
		//use.setFont(new Font("Serif", Font.PLAIN, Font.BOLD));
		password = new JLabel("password");
		k1 = new JTextField(12);
		k2 = new JPasswordField(12);
		b1 = new JButton("login ");
		b2 = new JButton("exit");
		//设置登录方法
		BHandler b = new BHandler();
		EXIT d = new EXIT();
		b1.addActionListener(b);
		b2.addActionListener(d);
		c.add(use);
		//添加控件
		c.add(k1);
		c.add(password);
		c.add(k2);
		c.add(b1);
		c.add(b2);
		setBounds(600, 300, 250, 150);
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private class BHandler implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			if (k1.getText().equals("") || k2.getText().equals("")) {
				JOptionPane.showMessageDialog(User.this, "用户名密码不能为空");
			} else {
				Statement stmt = null;
				ResultSet rs = null;
				String sql;
				sql = "select * from admin where uname='" + k1.getText()+"'";
				try {
					Connection con = CONN();
					stmt = con.createStatement();
					rs = stmt.executeQuery(sql);
					if (rs.next()) {
						String xm = rs.getString("password");
						//System.out.println("xxxxxx"+xm);
						if (k2.getText().equals(xm.trim())) {
							JOptionPane.showMessageDialog(User.this, "登录成功");
							dispose();
							new Menu();
						} else {
							JOptionPane.showMessageDialog(User.this, "密码错误");
						}
					} else {
						JOptionPane.showMessageDialog(User.this, "用户名错误");
					}
					rs.close();
					stmt.close();
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(User.this, "SQL Exception"
							+ e.getMessage());
				}
			}
		}
	}
	private class EXIT implements ActionListener{
		public void actionPerformed(ActionEvent enen){
			System.exit(0);
		}
	}
	public static Connection CONN(){
		String connectionUrl = "jdbc:sqlserver://localhost:1433;" +
				   "databaseName=st;user=biubiubiu;password=123456;";
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
	public static void main(String[] args){
		User f1 = new User(new JFrame());
	}
}