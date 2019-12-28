package com.lsy.dbcompare.compare.menu;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;

import com.lsy.dbcompare.entity.ConnInfo;
import com.lsy.dbcompare.entity.TaskInfo;

public class DatePanel extends JPanel implements ActionListener{
	//页面上必须的按钮，依次分是删除，新增，详情
	private JButton deleteBtn,addBtn,detailBtn;
	private JTable taskTable;
	public DatePanel() {
		this.btnInit();
	}
	
	private void btnInit() {
		JPanel fr1= new JPanel();
		fr1.setLocation(10, 20);
		fr1.setBorder(new TitledBorder("AGV小车数据操作"));
	    this.addBtn=new JButton("添加");
	    this.deleteBtn=new JButton("删除");
	    this.detailBtn=new JButton("详细");
	    
	    addBtn.addActionListener(this);
	    deleteBtn.addActionListener(this);
	    detailBtn.addActionListener(this);
	    fr1.setLayout(new BoxLayout(fr1, BoxLayout.X_AXIS));
	    fr1.add(this.addBtn);fr1.add(this.deleteBtn);fr1.add(this.detailBtn);
	    this.add(fr1,0);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
	     if(e.getSource()==(addBtn)){//新增AgvInfo
	     }else if(e.getSource()==(deleteBtn)){//删除
	     }else if(e.getSource()==detailBtn){
	     }
	}
	private static JTable createTable(List<TaskInfo> list) {
		final String[] headCols = {"任务名称","1数据库ip","2数据库ip","1数据库","2数据库","1表名","2表名"};
		Object[][] cells =  new Object[list.size()][7];
		for(int i = 0; i < cells.length; i++) {
			cells[i][0] = nullDo(list.get(i).getTaskName());
			cells[i][1] = nullDo(list.get(i).getConnInfo1().getHost());
			cells[i][2] = nullDo(list.get(i).getConnInfo2().getHost());
			cells[i][3] = nullDo(list.get(i).getConnInfo1().getDbName());
			cells[i][4] = nullDo(list.get(i).getConnInfo2().getDbName());
			cells[i][5] = nullDo(list.get(i).getConnInfo1().getTableName());
			cells[i][6] = nullDo(list.get(i).getConnInfo2().getTableName());
		}
		JTable table = new JTable(cells,headCols);
		
		table.setAutoCreateRowSorter(true);//使用默认的行排序器
		table.setFillsViewportHeight(true);//表格自适应视口
		
		return table;
	}
	private void loadTable(){
		List<TaskInfo> list = new ArrayList<TaskInfo>();
		TaskInfo taskInfo = new TaskInfo();
		taskInfo.setTaskName("任务1");
		ConnInfo conn1 = new ConnInfo();
		ConnInfo conn2 = new ConnInfo();
		taskInfo.setConnInfo1(conn1);
		taskInfo.setConnInfo2(conn2);
		
		list.add(taskInfo);
		this.taskTable = createTable(list);
		taskTable.setPreferredScrollableViewportSize(new Dimension(800, 400));
		DefaultTableCellRenderer   r   =   new   DefaultTableCellRenderer();   
		r.setHorizontalAlignment(JLabel.CENTER);
		taskTable.setDefaultRenderer(Object.class,r);
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(taskTable);
        this.add(scrollPane);
        this.setVisible(true);
/*		int v=ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
		int h=ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;

		JScrollPane jsp=new JScrollPane(taskTable,v,h);
		  Container cp=getContentPane();
		   cp.setLayout(new BorderLayout());
		cp.add(jsp,BorderLayout.CENTER);*/
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
	public static void main(String[] args) {
		new DatePanel();
	}
}
