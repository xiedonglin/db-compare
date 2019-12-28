package com.lsy.dbcompare.login;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.lsy.dbcompare.compare.DBCompareGUI;

/**
 * <h4>功能描述: 用户登录</h4>
 * <h5><pre>
 * author 纳川
 * 2018年5月14日
 * email 1029243332@qq.com
 * </pre></h5>
 */
public class LoginGUI extends JFrame{
	private static final long serialVersionUID = 8737131467173688381L;
	private LoginGUI self = this;
	
	public LoginGUI() {
		this.init();
		this.addComponents();
		this.setVisible(true);//小可爱，组件已准备好，该你现身啦！
	}

	/**
	 * 初始化窗体
	 */
	private void init() {
		this.setTitle("数据比对-用户登录");
		this.setSize(350, 300);
		this.setResizable(false);
		this.setCursor(new Cursor(Cursor.HAND_CURSOR));
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//使用坐标控制组件位置，布局须为空
		this.getContentPane().setLayout(null); 
	}
	
	/**
	 * 添加组件
	 */
	private void addComponents() {
		//组件准备
		JLabel usernameLable = new JLabel("账号");
		JTextField usernameField = new JTextField();
		
		JLabel pwdLable = new JLabel("密码");
		JTextField pwdField = new JPasswordField();
		
		JButton loginBtn = new JButton("登录");
		JButton cancelBtn = new JButton("取消");
		
		usernameLable.setBounds(60, 50, 40, 30);
		usernameField.setBounds(100, 50, 160, 30);
        pwdLable.setBounds(60, 100, 40, 30);
        pwdField.setBounds(100, 100, 160, 30);
        loginBtn.setBounds(80, 160, 70, 30);
        cancelBtn.setBounds(180, 160, 70, 30);
        
        //组件装入容器
        getContentPane().add(usernameLable);
        getContentPane().add(usernameField);
        getContentPane().add(pwdLable);
        getContentPane().add(pwdField);
        getContentPane().add(loginBtn);
        getContentPane().add(cancelBtn);
        
        //登录按钮事件处理
        loginBtnClick(usernameField, pwdField, loginBtn);
        //取消按钮事件处理
        cancelBtnClick(cancelBtn);
	}
	
	/**
	 * 登录按钮事件处理
	 */
	private void loginBtnClick(final JTextField usernameField, final JTextField pwdField, JButton loginBtn) {
		//登录
        loginBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String username = usernameField.getText();
				String pwd = pwdField.getText();
				try {
					Map<String, String> users = UsersUtil.getUsers();
					if(0 == users.size()) {
						throw new Exception();
					}
					String password = users.get(username);
					if(null == password || !password.equals(pwd)) {
						JOptionPane.showMessageDialog(self, "用户名或密码错误！");
					}else {
						//送君千里终须一别，就此一别啦！
						self.dispose();
						
						//进入数据比对页面--666，革命已成功一半了！歇会儿，看看诗和远方！！!
						new DBCompareGUI();
					}
					
				} catch (Exception exp) {
					JOptionPane.showMessageDialog(self, "用户数据丢失，请检查配置文件！","提示",JOptionPane.WARNING_MESSAGE);
				}
			}
		});
	}
	
	/**
	 * 取消按钮事件处理
	 */
	private void cancelBtnClick(JButton cancelBtn) {
		cancelBtn.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);//退出程序
			}
		});
	}
}
