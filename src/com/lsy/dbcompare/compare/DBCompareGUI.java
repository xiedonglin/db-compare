package com.lsy.dbcompare.compare;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import com.lsy.dbcompare.compare.content.DBCompareContent;
import com.lsy.dbcompare.compare.menu.DBCompareMenu;

/**
 * <h4>功能描述：数据比对主页面
 * <h5><pre>
 * @author 纳川
 * 2018年5月14日
 * email 1029243332@qq.com
 * </pre></h5>
 */
public class DBCompareGUI extends JFrame{
	private static final long serialVersionUID = -828432873186551206L;
	private DBCompareMenu menu = new DBCompareMenu();
	private DBCompareContent content = DBCompareContent.getDbCompareContent();
	
	public DBCompareGUI() {
		this.init();
		this.setVisible(true);//小可爱，组件已准备好，该你现身啦！
		content.showDiverLocation();
	}

	/**
	 * 初始化窗体
	 */
	private void init() {
		this.setTitle("数据库 - 数据比对工具");
		
		//设置窗口默认全屏
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize(screensize);
		
		this.setResizable(true);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.setJMenuBar(menu);
		this.setContentPane(content);
	}
}
