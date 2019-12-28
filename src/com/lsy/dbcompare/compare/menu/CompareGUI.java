package com.lsy.dbcompare.compare.menu;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;

import com.lsy.dbcompare.compare.content.DBCompareContent;
import com.lsy.dbcompare.dao.ConnInfoDao;
import com.lsy.dbcompare.dao.DbDao;
import com.lsy.dbcompare.entity.ConnInfo;
import com.lsy.dbcompare.util.DataUtil;

/**
 * <h4>功能描述：数据比对对话框
 * <h5><pre>
 * @author 纳川
 * 2018年5月15日
 * email 1029243332@qq.com
 * </pre></h5>
 */
public class CompareGUI extends JFrame{
	private CompareGUI self = this;
	private JSplitPane centerBox = new JSplitPane();
	//连接信息
	protected ConnInfo connInfoLeft;
	protected ConnInfo connInfoRight;
	//所选比对列
	protected ArrayList<String> selColsLeft = new ArrayList<String>();
	protected ArrayList<String> selColsRight = new ArrayList<String>();
	
	public CompareGUI() {
		this.init();
		this.addComponents();
		this.setVisible(true);
		centerBox.setDividerLocation(0.5);
	}
	
	private void init() {
		this.setTitle("数据比对");
		this.setSize(600, 610);
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
	
	private JSplitPane getCenterBox(){
		centerBox.setLocation(0, 0);
		centerBox.setSize(594, 538);
		centerBox.setEnabled(false);
		centerBox.setDividerSize(1);
		centerBox.setLeftComponent(this.getLeftBox());
		centerBox.setRightComponent(this.getRightBox());
		
		return centerBox;
	}
	
	private JPanel getLeftBox() {
		//初始化
		JPanel leftBox = new JPanel();
		leftBox.setBackground(Color.WHITE);
		leftBox.setBorder(new LineBorder(UIManager.getColor("Panel.background"), 10));
		
		//向centerBox中添加组件
		JLabel label = new JLabel("数据源1");
		label.setFont(new Font("宋体", Font.BOLD, 12));
		label.setBackground(UIManager.getColor("Panel.background"));
		label.setBounds(10, 10, 216, 15);
		leftBox.add(label);
		
		//Label
		JLabel connNameLabel = new JLabel("连接名");
		connNameLabel.setBounds(36, 52, 49, 21);
		JLabel dbLabel = new JLabel("数据库");
		dbLabel.setBounds(36, 105, 49, 21);
		JLabel tableLabel = new JLabel("表名");
		tableLabel.setBounds(36, 157, 49, 21);
		JLabel colLabel = new JLabel("列名");
		colLabel.setBounds(36, 210, 49, 21);

		//连接信息
		final JComboBox<Object> connNameField = new JComboBox<Object>();
		connNameField.setBounds(85, 52, 170, 21);
		//数据库
		final JComboBox<Object> dbField = new JComboBox<Object>();
		dbField.setBounds(85, 105, 170, 21);
		//表
		final JComboBox<Object> tableField = new JComboBox<Object>();
		tableField.setBounds(85, 157, 170, 21);
		leftBox.setLayout(null);
		//列
		final JScrollPane colPane = new JScrollPane();
		colPane.setBounds(85, 210, 170, 286);
		final JPanel ckPanel = new JPanel();
		ckPanel.setLayout(null);
		ckPanel.setPreferredSize(new Dimension(200, 600));
		colPane.setViewportView(ckPanel);
		
		//添加组件
		leftBox.add(connNameLabel);
		leftBox.add(connNameField);
		leftBox.add(dbLabel);
		leftBox.add(dbField);
		leftBox.add(tableLabel);
		leftBox.add(tableField);
		leftBox.add(colLabel);
		leftBox.add(colPane);
		
		//-------------------------------------事件（数据）处理----------------------------------//
		//1. 动态获取连接信息
		ArrayList<String> connNames = ConnInfoDao.getAllConnNames();
		connNameField.setModel(new DefaultComboBoxModel<Object>(connNames.toArray()));
		connNameField.setSelectedIndex(-1);//默认不选中任何项
		
		//2. 动态获取数据库信息
		connNameField.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED){
					//清空关联容器
					dbField.removeAllItems();
					tableField.removeAllItems();
					ckPanel.removeAll();
					ckPanel.repaint();
					
					//--获取选中连接名
					String connName = connNameField.getSelectedItem().toString();
					//--获取连接信息
					connInfoLeft = ConnInfoDao.getConnInfoByConnName(connName );
					//--获取数据库
					Object[] dbs = DbDao.getAllDbs(connInfoLeft).toArray();
					dbField.setModel(new DefaultComboBoxModel<Object>(dbs));
					dbField.setSelectedIndex(-1);
				}
			}
		});
		//192.168.0.11  root Admin123
		//192.168.0.162 root root
		//locahost（192.168.0.176） root Admin123
		//都是mysql
		//3. 动态获取表信息
		dbField.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED){
					//清空关联容器
					tableField.removeAllItems();
					ckPanel.removeAll();
					ckPanel.repaint();
					
					//--获取选中的数据库
					String dbName = dbField.getSelectedItem().toString();
					connInfoLeft.setDbName(dbName);
					//--获取数据中的表
					Object[] tables = DbDao.getAllTables(connInfoLeft).toArray();
					tableField.setModel(new DefaultComboBoxModel<Object>(tables));
					tableField.setSelectedIndex(-1);
				}
			}
		});
		
		//4. 获取表中的列
		tableField.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED){
					//清空关联容器
					ckPanel.removeAll();
					ckPanel.repaint();
					
					String tb = tableField.getSelectedItem().toString();
					connInfoLeft.setTableName(tb);
					ArrayList<String> allCols = DbDao.getAllCols(connInfoLeft);
					String[] clos = new String[allCols.size()];
					allCols.toArray(clos);
					for (int i = 0; i < clos.length; i++) {
						final JCheckBox ckBox = new JCheckBox(clos[i]);
						ckBox.setBounds(6, (5 + 25 * i), 160, 23);
						ckPanel.add(ckBox);
						//5. 列选中事件处理
						ckBox.addItemListener(new ItemListener() {
							@Override
							public void itemStateChanged(ItemEvent e) {
								if(ckBox.isSelected()){
									selColsLeft.add(ckBox.getText());
								}else{
									selColsLeft.remove(ckBox.getText());
								}
							}
						});
					}
					ckPanel.repaint();
				}
			}
		});
		
		return leftBox;
	}
	
	
	private JPanel getRightBox() {
		//初始化
		JPanel rightBox = new JPanel();
		rightBox.setBackground(Color.WHITE);
		rightBox.setBorder(new LineBorder(UIManager.getColor("Panel.background"), 10));
		
		//向centerBox中添加组件
		JLabel label = new JLabel("数据源2");
		label.setFont(new Font("宋体", Font.BOLD, 12));
		label.setBackground(UIManager.getColor("Panel.background"));
		label.setBounds(10, 10, 216, 15);
		rightBox.add(label);
		
		//Label
		JLabel connNameLabel = new JLabel("连接名");
		connNameLabel.setBounds(36, 52, 49, 21);
		JLabel dbLabel = new JLabel("数据库");
		dbLabel.setBounds(36, 105, 49, 21);
		JLabel tableLabel = new JLabel("表名");
		tableLabel.setBounds(36, 157, 49, 21);
		JLabel colLabel = new JLabel("列名");
		colLabel.setBounds(36, 210, 49, 21);
		
		//连接信息
		final JComboBox<Object> connNameField = new JComboBox<Object>();
		connNameField.setBounds(85, 52, 170, 21);
		//数据库
		final JComboBox<Object> dbField = new JComboBox<Object>();
		dbField.setBounds(85, 105, 170, 21);
		//表
		final JComboBox<Object> tableField = new JComboBox<Object>();
		tableField.setBounds(85, 157, 170, 21);
		rightBox.setLayout(null);
		//列
		final JScrollPane colPane = new JScrollPane();
		colPane.setBounds(85, 210, 170, 286);
		final JPanel ckPanel = new JPanel();
		ckPanel.setLayout(null);
		ckPanel.setPreferredSize(new Dimension(200, 600));
		colPane.setViewportView(ckPanel);
		
		//添加组件
		rightBox.add(connNameLabel);
		rightBox.add(connNameField);
		rightBox.add(dbLabel);
		rightBox.add(dbField);
		rightBox.add(tableLabel);
		rightBox.add(tableField);
		rightBox.add(colLabel);
		rightBox.add(colPane);
		
		//-------------------------------------事件（数据）处理----------------------------------//
		//1. 动态获取连接信息
		ArrayList<String> connNames = ConnInfoDao.getAllConnNames();
		connNameField.setModel(new DefaultComboBoxModel<Object>(connNames.toArray()));
		connNameField.setSelectedIndex(-1);//默认不选中任何项
		
		//2. 动态获取数据库信息
		connNameField.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED){
					//清空关联容器
					dbField.removeAllItems();
					tableField.removeAllItems();
					ckPanel.removeAll();
					ckPanel.repaint();
					
					//--获取选中连接名
					String connName = connNameField.getSelectedItem().toString();
					//--获取连接信息
					connInfoRight = ConnInfoDao.getConnInfoByConnName(connName );
					//--获取数据库
					Object[] dbs = DbDao.getAllDbs(connInfoRight).toArray();
					dbField.setModel(new DefaultComboBoxModel<Object>(dbs));
					dbField.setSelectedIndex(-1);
				}
			}
		});
		
		//3. 动态获取表信息
		dbField.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED){
					//清空关联容器
					tableField.removeAllItems();
					ckPanel.removeAll();
					ckPanel.repaint();
					
					//--获取选中的数据库
					String dbName = dbField.getSelectedItem().toString();
					connInfoRight.setDbName(dbName);
					//--获取数据中的表
					Object[] tables = DbDao.getAllTables(connInfoRight).toArray();
					tableField.setModel(new DefaultComboBoxModel<Object>(tables));
					tableField.setSelectedIndex(-1);
				}
			}
		});
		
		//4. 获取表中的列
		tableField.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED){
					//清空关联容器
					ckPanel.removeAll();
					ckPanel.repaint();
					selColsRight.clear();
					selColsRight.clear();
					
					String tb = tableField.getSelectedItem().toString();
					connInfoRight.setTableName(tb);
					ArrayList<String> allCols = DbDao.getAllCols(connInfoRight);
					String[] clos = new String[allCols.size()];
					allCols.toArray(clos);
					for (int i = 0; i < clos.length; i++) {
						final JCheckBox ckBox = new JCheckBox(clos[i]);
						ckBox.setBounds(6, (5 + 25 * i), 160, 23);
						ckPanel.add(ckBox);
						//5. 列选中事件处理
						ckBox.addItemListener(new ItemListener() {
							@Override
							public void itemStateChanged(ItemEvent e) {
								if(ckBox.isSelected()){
									selColsRight.add(ckBox.getText());
								}else{
									selColsRight.remove(ckBox.getText());
								}
							}
						});
					}
					ckPanel.repaint();
				}
			}
		});
		
		return rightBox;
	}
	
	private JPanel getBtnBox() {
		JPanel btnBox = new JPanel();
		btnBox.setBounds(0, 538, 594, 33);
		
		JButton okBtn = new JButton("比对");
		JButton cancelBtn = new JButton("取消");
		okBtn.setBounds(226, 7, 74, 28);
		cancelBtn.setBounds(310, 7, 74, 28);
		btnBox.add(okBtn);
		btnBox.add(cancelBtn);
		
		//事件处理
		okBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(connInfoLeft == null || connInfoRight == null){
					JOptionPane.showMessageDialog(self, "请完善数据源信息！");
				}else if(connInfoLeft.getTableName() == null){
					JOptionPane.showMessageDialog(self, "请选择左表！");
				}else if(connInfoRight.getTableName() == null){
					JOptionPane.showMessageDialog(self, "请选择右表！");
				}else if(selColsLeft.size() != selColsRight.size()){
					JOptionPane.showMessageDialog(self, "左右表选择的列数不一致！");
				}else if(selColsLeft.size() == 0 || selColsRight.size() == 0){
					JOptionPane.showMessageDialog(self, "请至少选择一列进行数据比对！");
				}else{
					//数据比对
					System.out.println(connInfoLeft);
					System.out.println(connInfoRight);
					Vector<Object[]> dataLeft = DbDao.getSelData(connInfoLeft, selColsLeft);
					Vector<Object[]> dataRight = DbDao.getSelData(connInfoRight, selColsRight);
					
					HashMap<String, List<Object[]>> data = DataUtil.compareData(dataLeft,dataRight);
					self.dispose();
					DBCompareContent.getDbCompareContent().showTables(data,selColsLeft,selColsRight);
				}
			}
		});
		
		cancelBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				self.dispose();
			}
		});
		
		return btnBox;
	}
}
