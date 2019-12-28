package com.lsy.dbcompare.compare.menu;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import com.lsy.dbcompare.dao.ConnInfoDao;
import com.lsy.dbcompare.dao.TaskInfoDao;
import com.lsy.dbcompare.entity.TaskInfo;

public class AddTaskInfo extends JDialog implements ActionListener{
	public static void main(String[] args) {
		new AddTaskInfo();
	}
	JTextField taskNameText,startHoursText,startMinusText;
	//连接信息
	JComboBox<Object> connNameField1,connNameField2;
    JLabel taskNameLabel,conn1Label,conn2Label,startTimeLabel,hoursLable,minusLable;
    JButton Addyes,Addno;
    TaskInfo task = new TaskInfo();
    AddTaskInfo(){
    	this.setTitle("增加一个任务");
    	taskNameText=new JTextField();
    	connNameField1= new JComboBox<Object>();       
    	connNameField2= new JComboBox<Object>();   
    	startHoursText=new JTextField();
    	startMinusText=new JTextField();
	    //增加日期控件
	 /*   Chooser creSer = Chooser.getInstance();
	    creSer.register(AdddCreDate);
	    Chooser buySer = Chooser.getInstance();
	    buySer.register(AdddBuyDate);*/
	    
    	taskNameLabel=new JLabel("任务名称",SwingConstants.RIGHT);
    	taskNameLabel.setLocation(10, 10);
    	taskNameLabel.setSize(55, 15);
    	taskNameText.setLocation(70, 10);
    	taskNameText.setSize(55, 15);
    	conn1Label=new JLabel("数据源1",SwingConstants.RIGHT);
    	conn2Label=new JLabel("数据源2",SwingConstants.RIGHT);
    	startTimeLabel=new JLabel("开始时间",SwingConstants.RIGHT);
    	hoursLable=new JLabel("时--",SwingConstants.RIGHT);
    	minusLable=new JLabel("分",SwingConstants.RIGHT);
	  
	    Addyes=new JButton("确定");
	    Addno=new JButton("取消");
	    this.add(taskNameLabel);this.add(taskNameText);
	    this.add(conn1Label);this.add(connNameField1); 
	    this.add(conn2Label);this.add(connNameField2);
	    JPanel p = new JPanel();
	   // p.setLayout(new GridLayout(1,4));
	    p.setLayout(new FlowLayout(FlowLayout.LEFT));
	    p.setLocation(10, 30);
	    p.setSize(400, 30);
	    p.add(startTimeLabel);p.add(Box.createHorizontalStrut(12));p.add(startHoursText);p.add(hoursLable);p.add(minusLable);
	    this.add(p);this.add(new JPanel());
	    this.add(Addyes); this.add(Addno);
	    this.Addyes.addActionListener(this);
	    this.Addno.addActionListener(this);
	    this.setLayout(null);
	    this.setSize(450,300);
	    this.setLocation(200, 90);
	    this.setVisible(true);
	    this.setResizable(false);
		//1. 动态获取连接信息
		ArrayList<String> connNames = ConnInfoDao.getAllConnNames();
		connNameField1.setModel(new DefaultComboBoxModel<Object>(connNames.toArray()));
		connNameField1.setSelectedIndex(-1);//默认不选中任何项
		
		//2. 动态获取数据库信息
		connNameField1.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED){
					//清空关联容器
					
					//--获取选中连接名
					String connName = connNameField1.getSelectedItem().toString();
					//--获取连接信息
					task.setConnInfo1(ConnInfoDao.getConnInfoByConnName(connName));
				}
			}
		});
		connNameField2.setModel(new DefaultComboBoxModel<Object>(connNames.toArray()));
		connNameField2.setSelectedIndex(-1);//默认不选中任何项
		//2. 动态获取数据库信息
		connNameField2.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED){
					//清空关联容器
					//--获取选中连接名
					String connName = connNameField2.getSelectedItem().toString();
					//--获取连接信息
					task.setConnInfo2(ConnInfoDao.getConnInfoByConnName(connName));
				}
			}
		});
    }
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==Addyes){
	    	task.setTaskId(System.currentTimeMillis()+"");
	    	task.setTaskName(taskNameText.getText());
	    	//task.setConnInfo1(ConnInfoDao.getConnInfoByConnName(connNameField1.getSelectedItem().toString()));
	    	//task.setConnInfo2(ConnInfoDao.getConnInfoByConnName(connNameField2.getSelectedItem().toString()));
			boolean result =TaskInfoDao.addTaskInfo(task);
			if (result) {
				JOptionPane.showMessageDialog(this, "添加成功", null,JOptionPane.WARNING_MESSAGE);  					
			}
	/*		refresh();
			showData();*/
		}
		this.dispose();
	 }
}