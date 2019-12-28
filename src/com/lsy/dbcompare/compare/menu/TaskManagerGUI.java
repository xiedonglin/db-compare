package com.lsy.dbcompare.compare.menu;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;
import com.lsy.dbcompare.dao.ConnInfoDao;
import com.lsy.dbcompare.dao.TaskInfoDao;
import com.lsy.dbcompare.entity.TaskInfo;

/**
 * 功能描述：任务管理GUI，菜单栏-任务-任务管理
 * @author 袁正宜
 * 2018年5月21日
 * email 1009198169@qq.com
 */
public class TaskManagerGUI extends JFrame implements ActionListener{
	private JTable taskTable;
	private JPanel fr1;
	MyTableModel tableModel=null;

	//页面上必须的按钮，依次分是删除，新增，详情
	private JButton deleteBtn,addBtn,detailBtn;
	public static List<TaskInfo>  list;
	
	public TaskManagerGUI() {
		this.init();
		this.btnInit();
		this.loadTable();
		this.setVisible(true);
	}
	
	private void init() {
		this.setTitle("任务管理");
		this.setLocation(300,10);
		this.setSize(800, 685);
		this.getContentPane().setLayout(null);
	}
	private void btnInit() {
		fr1= new JPanel();
		fr1.setBorder(new TitledBorder("任务操作"));
	    this.addBtn=new JButton("添加");
	    this.deleteBtn=new JButton("删除");
	    this.detailBtn=new JButton("详细");
	    
	    addBtn.addActionListener(this);
	    deleteBtn.addActionListener(this);
	    detailBtn.addActionListener(this);
	    fr1.setLayout(new BoxLayout(fr1, BoxLayout.X_AXIS));
	    fr1.add(this.addBtn);
	    fr1.add(Box.createVerticalStrut(10));
	    fr1.add(this.deleteBtn);
	    fr1.add(Box.createVerticalStrut(10));
	    fr1.add(this.detailBtn);
	    fr1.setBounds(10, 5, 300, 65);
	    this.add(fr1,0);
	    
	}
		
	@Override
	public void actionPerformed(ActionEvent e) {
	     if(e.getSource()==(addBtn)){//新增任务
	    	 new AddTaskInfo();
		     list=TaskInfoDao.getRealTimeAllTaskInfo();
	     }else if(e.getSource()==(deleteBtn)){//删除
	    	 this.repaint();
		     list=TaskInfoDao.getRealTimeAllTaskInfo();
	    	   int row=this.taskTable.getSelectedRow();
	    	   System.out.println(row);
	           if (row==-1) {
				   JOptionPane.showMessageDialog(this, "请选择一行", null,JOptionPane.WARNING_MESSAGE);  
				   return;
	           }
	           int isDelete = JOptionPane.showConfirmDialog(this, "真要删除小车"+taskTable.getValueAt(row, 1)+"?", "删除?",JOptionPane.YES_NO_OPTION);
	           if (isDelete==0) {//点击否按钮，不删除，咱回去
		           String iNo =(String) taskTable.getValueAt(row, 0); //要删除的iNo
		           boolean result =TaskInfoDao.delTaskInfoByTaskId(iNo);
				   if (result) {
					   JOptionPane.showMessageDialog(this, "已经删除小车！", "删除成功",JOptionPane.WARNING_MESSAGE);  
					     list=TaskInfoDao.getRealTimeAllTaskInfo();
				   }
	           }
	     }else if(e.getSource()==(detailBtn)){
	    	 int[] row=taskTable.getSelectedRows();//当前选中的行
	    	 for (int i : row) {
	    		 System.out.println("row  "+i);
			}
	    	// taskTable.clearSelection();
	         /*  if (row==-1) {
	   				   JOptionPane.showMessageDialog(this, "请选择一行", null,JOptionPane.WARNING_MESSAGE);  
	   				   return;
	   	       }
	           String taskId =(String) taskTable.getValueAt(row, 0); //要查看详细的iNo
	           TaskInfo taskInfo = TaskInfoDao.queryTaskInfoById(taskId);*/
	    	   //new DetailTaskInfo(taskInfo);
	     }
	     loadTable();
	}
	private void showDate() {
		final String[] headCols = {"","任务名称","1数据库ip","2数据库ip","1数据库","2数据库","1表名","2表名"};
		Object[][] cells =  new Object[list.size()][8];
		for(int i = 0; i < cells.length; i++) {
			cells[i][0] = nullDo(list.get(i).getTaskId());
			cells[i][1] = nullDo(list.get(i).getTaskName());
			cells[i][2] = nullDo(list.get(i).getConnInfo1().getHost());
			cells[i][3] = nullDo(list.get(i).getConnInfo2().getHost());
			cells[i][4] = nullDo(list.get(i).getConnInfo1().getDbName());
			cells[i][5] = nullDo(list.get(i).getConnInfo2().getDbName());
			cells[i][6] = nullDo(list.get(i).getConnInfo1().getTableName());
			cells[i][7] = nullDo(list.get(i).getConnInfo2().getTableName());
		}
		taskTable = new JTable();
		tableModel = new MyTableModel();
   	    tableModel.setColumnIdentifiers(headCols);
   	    for (int i = 0; i < cells.length; i++) {
			tableModel.addRow(cells[i]);
		}
   	    taskTable.setModel(tableModel);
   		TableColumnModel colmodel = taskTable.getColumnModel();
		
		colmodel.getColumn(0).setMinWidth(0);  //将第一列的最小宽度、最大宽度都设置为0，就看不到了
		colmodel.getColumn(0).setMaxWidth(0);
	}
	private void loadTable(){
		list = TaskInfoDao.getAllTaskInfo();
		//list.add(taskInfo);
		showDate();
		taskTable.setPreferredScrollableViewportSize(new Dimension(800, 400));
		DefaultTableCellRenderer   r   =   new   DefaultTableCellRenderer();   
		r.setHorizontalAlignment(JLabel.CENTER);
		taskTable.setDefaultRenderer(Object.class,r);
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(taskTable);
        this.add(scrollPane);
	    scrollPane.setBounds(10, 100, 1200, 500);
	}	
	public static void main(String[] args) {
		new TaskManagerGUI();
	}
	/**
	 * 处理null，将null字符串转换成“”,便于展示,屏蔽bug
	 * @param thisString
	 * @return
	 */
	private static String nullDo(String thisString) {
		if (null==thisString) {
			thisString="";
		}
		return thisString;
	} 
	private class AddTaskInfo extends JDialog implements ActionListener{
		JTextField taskNameText;
		//连接信息
		JComboBox<Object> connNameField1,connNameField2;
	    JLabel taskNameLabel,conn1Label,conn2Label;
	    JButton Addyes,Addno;
	    TaskInfo task = new TaskInfo();
	    AddTaskInfo(){
	    	this.setTitle("增加一个任务");
	    	taskNameText=new JTextField();
	    	connNameField1= new JComboBox<Object>();       
	    	connNameField2= new JComboBox<Object>();       
		    //增加日期控件
		 /*   Chooser creSer = Chooser.getInstance();
		    creSer.register(AdddCreDate);
		    Chooser buySer = Chooser.getInstance();
		    buySer.register(AdddBuyDate);*/
		    
	    	taskNameLabel=new JLabel("任务名称",SwingConstants.RIGHT);
			
	    	conn1Label=new JLabel("数据源1",SwingConstants.RIGHT);
	    	conn2Label=new JLabel("数据源2",SwingConstants.RIGHT);
		  
		    Addyes=new JButton("确定");
		    Addno=new JButton("取消");
		    this.add(taskNameLabel);this.add(taskNameText);
		    this.add(conn1Label);this.add(connNameField1); 
		    this.add(conn2Label);this.add(connNameField2);
	
		    this.add(Addyes); this.add(Addno);
		             
		    this.Addyes.addActionListener(this);
		    this.Addno.addActionListener(this);
		    this.setSize(350,100);
		    this.setLocation(200, 90);
		    this.setLayout(new GridLayout(4,1));
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
				loadTable();
			}
			this.dispose();
		 }
	}
	private class DetailTaskInfo extends JDialog implements ActionListener{
		JTextField taskNameText;
	    JLabel taskNameLabel;
	    JButton modifyBtn,Addno;
	    TaskInfo taskInfo;//系统当前查看的taskInfo
	    private boolean canChange=true;
	    public DetailTaskInfo(TaskInfo taskInfo){
	    	this.taskInfo = taskInfo;
	    	this.setTitle("展示一个任务详情");
	    	taskNameText=new JTextField(taskInfo.getTaskName());
			
	    	taskNameLabel=new JLabel("任务名称",SwingConstants.RIGHT);
			
			modifyBtn =  new JButton("修改");
		    Addno=new JButton("返回");
		    
		    
		    this.add(taskNameLabel); this.add(taskNameText);
		    this.add(modifyBtn); this.add(Addno);

		    this.modifyBtn.addActionListener(this);
		    this.Addno.addActionListener(this);
		    this.setSize(350,400);
		    this.setLocation(100, 260);
		    this.setLayout(new GridLayout(2,1));
		    this.setVisible(true);
		    this.setResizable(false);
		    setAllEnabled(false);
	    }
	    /**
	     * 设置所有的文本框
	     * @param b true为可编辑，false为不可编辑
	     */
	    private void setAllEnabled(boolean b){
	    	taskNameText.setEnabled(b);
		  	repaint();
	    }
	 /*   private void setText(){
	    	AddcName.setText(agvInfo.getcName());
			AddcName.setText(agvInfo.getcName());
			AddcEQType.setText(agvInfo.getcEQType());        
			AdddBuyDate.setText(agvInfo.getdBuyDate());      
			AddcPPECode.setText(agvInfo.getcPPECode());       
			AddcNote.setText(agvInfo.getcNote());         
			Addx.setText(agvInfo.getX());             
			Addy.setText(agvInfo.getY());             
			AddiAngle.setText(agvInfo.getiAngle());        
			AddcIconCd.setText(agvInfo.getcIconCd());        
			AddcNGIconCd.setText(agvInfo.getcNGIconCd());     
			AddcDescription.setText(agvInfo.getcDescription());  
			AddiLock.setText(agvInfo.getiLock());         
			Addip.setText(agvInfo.getIp());            
			AddcCreId.setText(agvInfo.getcCreId());         
			AdddCreDate.setText(agvInfo.getdCreDate()); 
	    }*/
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource()==modifyBtn){
		    	setAllEnabled(canChange);
				if (canChange==false) {
					/*AgvInfo changeAgv =  new AgvInfo(agvInfo.getiNO(),AddcName.getText(),AddcEQType.getText(),AdddBuyDate.getText(),
			    			AddcPPECode.getText(),AddcNote.getText(),Addx.getText(),Addy.getText(),AddiAngle.getText(),
			    			AddcIconCd.getText(),AddcNGIconCd.getText(),AddcDescription.getText(),AddiLock.getText(),         
			    			  	Addip.getText(),AddcCreId.getText(),AdddCreDate.getText());		*/
					/*if (!changeAgv.equals(agvInfo)) {
				        int isChange = JOptionPane.showConfirmDialog(this, "真要修改此车?", "修改?",JOptionPane.YES_NO_OPTION);
				        if (isChange==0) {
				        	changeAgv.setiNO(agvInfo.getiNO());
				        	agvInfoService.updateAgvInfo(changeAgv);	
				        	agvInfo = changeAgv;
						} else{
							setText();
						}
					}*/
					modifyBtn.setText("修改");
				}else{//修改中
					modifyBtn.setText("提交");
				}
				canChange =!canChange;
			}else if(e.getSource()==Addno){
				//refresh();
				loadTable();
				this.dispose();
			}
		 }
	}
}

