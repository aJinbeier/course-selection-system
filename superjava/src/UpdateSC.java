import java.awt.*;
import java.awt.event.*;
import java.sql.*;

import javax.swing.*;

public class UpdateSC extends JPanel implements ActionListener {
	String saveC = null;
	String saveS = null;
	JTextField cno1, sno1, sno, cno, score;
	JButton update, search;

	public UpdateSC() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			System.err.println("不能设置外观:   " + e);
		}
		sno1 = new JTextField(12);
		cno1 = new JTextField(12);
		cno = new JTextField(12);
		sno = new JTextField(12);
		score = new JTextField(12);
		update = new JButton("update");
		search = new JButton("search");
		Box box1 = Box.createHorizontalBox();// 横放box
		Box box2 = Box.createHorizontalBox();
		Box box3 = Box.createHorizontalBox();
		Box box4 = Box.createHorizontalBox();
		Box box5 = Box.createHorizontalBox();
		box1.add(new JLabel("cno:", JLabel.CENTER));
		box1.add(cno);
		box2.add(new JLabel("sno:", JLabel.CENTER));
		box2.add(sno);
		box3.add(new JLabel("score:", JLabel.CENTER));
		box3.add(score);
		box4.add(update);
		box5.add(new JLabel("cno:", JLabel.CENTER));
		box5.add(cno1);
		box5.add(new JLabel("sno:", JLabel.CENTER));
		box5.add(sno1);
		box5.add(search);
		Box boxH = Box.createVerticalBox();// 竖放box
		boxH.add(box1);
		boxH.add(box2);
		boxH.add(box3);
		boxH.add(box4);
		boxH.add(Box.createVerticalGlue());
		update.addActionListener(this);
		search.addActionListener(this);
		JPanel picPanel = new JPanel();
		JPanel messPanel = new JPanel();
		messPanel.add(box5);
		picPanel.add(boxH);
		setLayout(new BorderLayout());
		JSplitPane splitV = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
				messPanel, picPanel);// 分割
		add(splitV, BorderLayout.CENTER);
		validate();
	}

	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		Statement stmt = null;
		ResultSet rs = null, rs1 = null, rsC = null, rsS = null;
		String sql, sql1, sqlS, sqlC;
		if (obj == search) {
			if (cno1.getText().equals("") || sno1.getText().equals(""))
				JOptionPane.showMessageDialog(this, "请填写完成查询的信息！");
			else {
				sql1 = "select * from s_c where Cno='" + cno1.getText()
						+ "' and Sno='" + sno1.getText() + "'";
				try {
					Connection dbConn1 = CONN();
					stmt = (Statement) dbConn1.createStatement();
					rs1 = stmt.executeQuery(sql1);
					if (rs1.next()) {
						cno.setText(rs1.getString("cno").trim());
						sno.setText(rs1.getString("sno").trim());
						score.setText(rs1.getString("score").trim());
						saveC = cno1.getText().trim();
						saveS = sno1.getText().trim();
					} else {
						JOptionPane.showMessageDialog(this, "没有这个cno的学生");
					}
					stmt.close();
					rs1.close();
				} catch (SQLException e1) {
					System.out.print("SQL Exception occur.Message is:"
							+ e1.getMessage());
				}
			}
		} else {
			if (obj == update) {
				if (saveC == null || saveS == null)
					JOptionPane.showMessageDialog(this,
							"还没search需要update的学生/课程");
				else {
					if (cno.getText().equals("") || sno.getText().equals("")) {
						JOptionPane.showMessageDialog(this, "课程信息填满才能update！");
					} else {
						sqlC = "select * from course where Cno='"
								+ cno.getText() + "'";
						sqlS = "select * from student where Sno='"
								+ sno.getText() + "'";
						sql1 = "select * from s_c where Cno='" + cno.getText()
								+ "' and Sno='" + sno.getText() + "'";
						sql = "update s_c set Cno='" + cno.getText() + "',Sno='"
								+ sno.getText() + "',score='" + score.getText()
								+ "' where Cno='" + saveC + "' and Sno='"
								+ saveS + "'";
						try {
							Connection dbConn1 = CONN();
							stmt = (Statement) dbConn1.createStatement();
							rsC = stmt.executeQuery(sqlC);
							if (rsC.next()) {
								rsS = stmt.executeQuery(sqlS);
								if (rsS.next()) {
									if (cno.getText().trim().equals(saveC)
											&& sno.getText().trim()
													.equals(saveS)) {
										stmt.executeUpdate(sql);
										JOptionPane.showMessageDialog(this,
												"添加成功");
										saveC = null;
										saveS = null;
									} else {
										rs1 = stmt.executeQuery(sql1);
										if (rs1.next()) {
											JOptionPane.showMessageDialog(this,
													"学生与课程号以存在，无法update");
										} else {
											stmt.executeUpdate(sql);
											JOptionPane.showMessageDialog(this,
													"添加成功");
											saveC = null;
											saveS = null;
										}
										rs1.close();
									}
								} else {
									JOptionPane.showMessageDialog(this,
											"该学生不存在，无法update");
								}
								rsS.close();
							} else {
								JOptionPane.showMessageDialog(this,
										"该课程不存在，无法update");
							}
							rsC.close();
							stmt.close();
						} catch (SQLException e1) {
							System.out.print("SQL Exception occur.Message is:"
									+ e1.getMessage());
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