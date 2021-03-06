package com.lsy.dbcompare.compare.menu;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;

import com.lsy.dbcompare.dao.ConnInfoDao;
import com.lsy.dbcompare.entity.ConnInfo;
import com.lsy.dbcompare.util.JDBCUtil;

/**
 * <h4>功能描述：数据库连接对话框 - MySql
 * <h5><pre>
 * @author 纳川
 * 2018年5月15日
 * email 1029243332@qq.com
 * </pre></h5>
 */
public class ConnMySqlGUI extends JFrame{
	private ConnMySqlGUI self = this;
	private JTextField connNameField;
	private JTextField hostField;
	private JTextField portField;
	private JTextField usernameField;
	private JPasswordField pwdField;
	
	public ConnMySqlGUI() {
		this.init();
		this.addComponents();
		this.setVisible(true);
	}
	
	private void init() {
		this.setTitle("MySql - 新建连接");
		this.setSize(400, 426);
		this.setResizable(false);
		this.setAlwaysOnTop(true);
		this.setLocationRelativeTo(this.getParent());
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.getContentPane().setLayout(null);
	}
	
	private void addComponents() {
		this.getContentPane().add(this.getCenterBox());
		this.getContentPane().add(this.getBtnBox());
	}
	
	private JPanel getCenterBox() {
		//初始化centerBox
		JPanel centerBox = new JPanel();
		centerBox.setBackground(Color.WHITE);
		centerBox.setBorder(new LineBorder(UIManager.getColor("Panel.background"), 10));
		centerBox.setBounds(0, 0, 394, 340);
		centerBox.setLayout(null);
		
		//向centerBox中添加组件
		//Label
		JLabel connNameLabel = new JLabel("连接名：");
		JLabel hostLabel = new JLabel("主机名或IP：");
		JLabel portLabel = new JLabel("端口：");
		JLabel usernameLabel = new JLabel("用户名：");
		JLabel pwdLabel = new JLabel("密码：");
		
		connNameLabel.setBounds(55, 29, 66, 21);
		hostLabel.setBounds(55, 87, 88, 21);
		portLabel.setBounds(55, 146, 66, 21);
		usernameLabel.setBounds(55, 203, 66, 21);
		pwdLabel.setBounds(55, 263, 66, 21);
		
		//文本框
		connNameField = new JTextField();
		hostField = new JTextField("localhost");
		portField = new JTextField("3306");
		usernameField = new JTextField("root");
		pwdField = new JPasswordField();
		
		connNameField.setBounds(153, 29, 158, 21);
		hostField.setBounds(153, 87, 158, 21);
		portField.setBounds(153, 146, 158, 21);
		usernameField.setBounds(153, 203, 158, 21);
		pwdField.setBounds(153, 263, 158, 21);
		
		centerBox.add(connNameLabel);
		centerBox.add(connNameField);
		centerBox.add(hostLabel);
		centerBox.add(hostField);
		centerBox.add(portLabel);
		centerBox.add(portField);
		centerBox.add(usernameLabel);
		centerBox.add(usernameField);
		centerBox.add(pwdLabel);
		centerBox.add(pwdField);
		
		return centerBox;
	}
	
	private JPanel getBtnBox() {
		JPanel btnBox = new JPanel();
		btnBox.setLayout(null);
		btnBox.setBounds(0, 350, 394, 48);
		
		JButton testBtn = new JButton("连接测试");
		JButton okBtn = new JButton("确定");
		JButton cancelBtn = new JButton("取消");
		
		testBtn.setBounds(10, 7, 120, 28);
		okBtn.setBounds(226, 7, 74, 28);
		cancelBtn.setBounds(310, 7, 74, 28);
		
		btnBox.add(testBtn);
		btnBox.add(okBtn);
		btnBox.add(cancelBtn);
		
		//【连接测试】
		testBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String connName = connNameField.getText().trim();
				String host = hostField.getText().trim();
				String port = portField.getText().trim();
				String username = usernameField.getText().trim();
				String pwd = String.valueOf(pwdField.getPassword()).trim();
				ConnInfo info = new ConnInfo(connName, "MySql", host, port, username, pwd);
				if(JDBCUtil.getConnection(info) == null){
					JOptionPane.showMessageDialog(self, "连接失败！");
				}else{
					JDBCUtil.closeConnection();
					JOptionPane.showMessageDialog(self, "连接成功！");
				}
			}
		});
		
		//【确定】
		okBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String connName = connNameField.getText().trim();
				String host = hostField.getText().trim();
				String port = portField.getText().trim();
				String username = usernameField.getText().trim();
				String pwd = String.valueOf(pwdField.getPassword()).trim();
				ConnInfo info = new ConnInfo(connName, "MySql", host, port, username, pwd);
				
				if(JDBCUtil.getConnection(info) == null){
					JOptionPane.showMessageDialog(self, "连接失败！");
				}else{
					JDBCUtil.closeConnection();
					ConnInfoDao.addConnInfo(info);
					self.dispose();
				}
			}
		});
		
		//【取消】
		cancelBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				self.dispose();
			}
		});
		
		return btnBox;
	}
}
