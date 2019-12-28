package com.lsy.dbcompare.compare.content;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

/**
 * <h4>功能描述：数据比对-结果显示面板
 * <h5><pre>
 * @author 纳川
 * 2018年5月14日
 * email 1029243332@qq.com
 * </pre></h5>
 */
public class DBCompareContent extends JPanel{
	private static final long serialVersionUID = 1854940685411398717L;
	private static DBCompareContent self = new DBCompareContent();
	private JSplitPane treeTableSplit ;
	private JSplitPane twoTableSplit;
	private JScrollPane leftScroll;
	private JScrollPane rightScroll;
	private static HashMap<String, List<Object[]>> compareData;
	
	private DBCompareContent(){
		this.setLayout(new BorderLayout());
		this.add(this.getSplitPane());
	}
	
	public static DBCompareContent getDbCompareContent(){
		return self ;
	}
	
	/** 显示分割线 */
	public void showDiverLocation(){
		//分割线只有在页面绘制完毕设置才有效
		twoTableSplit.setDividerLocation(0.5);
//		treeTableSplit.setDividerLocation(0.1);
	}
	
	/** 主面板：一分为二（左树 ———— 右表） */
	private JSplitPane getSplitPane(){
		treeTableSplit = new JSplitPane();
		treeTableSplit.setDividerSize(1);		//分隔线宽度(像素)
		
//		treeTableSplit.setLeftComponent(this.getTreePanel());
		treeTableSplit.setLeftComponent(null);
		treeTableSplit.setRightComponent(this.getTablePanel());
		return treeTableSplit;
	}

	/** 连接树 容器 */
	private JPanel getTreePanel() {
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
//		panel.setLayout();
		
		JTree connTree = new JTree();
		connTree.setShowsRootHandles(true);
		connTree.setModel(new DefaultTreeModel(
			new DefaultMutableTreeNode("JTree") {
				{
					DefaultMutableTreeNode node;
					node = new DefaultMutableTreeNode("colors");
						node.add(new DefaultMutableTreeNode("blue"));
						node.add(new DefaultMutableTreeNode("violet"));
						node.add(new DefaultMutableTreeNode("red"));
						node.add(new DefaultMutableTreeNode("yellow"));
					add(node);
					node = new DefaultMutableTreeNode("sports");
						node.add(new DefaultMutableTreeNode("basketball"));
						node.add(new DefaultMutableTreeNode("soccer"));
						node.add(new DefaultMutableTreeNode("football"));
						node.add(new DefaultMutableTreeNode("hockey"));
					add(node);
					node = new DefaultMutableTreeNode("food");
						node.add(new DefaultMutableTreeNode("hot dogs"));
						node.add(new DefaultMutableTreeNode("pizza"));
						node.add(new DefaultMutableTreeNode("ravioli"));
						node.add(new DefaultMutableTreeNode("bananas"));
					add(node);
					node = new DefaultMutableTreeNode("ball");
						node.add(new DefaultMutableTreeNode("basketball"));
						node.add(new DefaultMutableTreeNode("football"));
					add(node);
				}
			}
		));
		panel.add(connTree);
		return panel;
	}
	
	/** 表格容器:一分为二（左表 ———— 右表） */
	private JPanel getTablePanel(){
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		
		twoTableSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,true);
		twoTableSplit.setDividerSize(1);
		
		leftScroll = new JScrollPane();
		rightScroll = new JScrollPane();
		leftScroll.getViewport().setBackground(Color.WHITE);
		rightScroll.getViewport().setBackground(Color.WHITE);
		
		twoTableSplit.setLeftComponent(leftScroll);
		twoTableSplit.setRightComponent(rightScroll);
		panel.add(twoTableSplit);
		
		return panel;
	}

	public void showTables(HashMap<String, List<Object[]>> data,
			ArrayList<String> selColsLeft, ArrayList<String> selColsRight) {
		compareData = data;
		if(null != data){
			leftScroll.setViewportView(createTable(data.get("left"),selColsLeft));
			rightScroll.setViewportView(createTable(data.get("right"),selColsRight));
			self.repaint();
			twoTableSplit.setDividerLocation(0.5);
		}
	}
	
	private static JTable createTable(List<Object[]> list, ArrayList<String> headCols) {
		String[] head = new String[headCols.size()];
		headCols.toArray(head);
		Object[][] cells = new Object[list.size()][];
		for(int i = 0; i < cells.length; i++) {
			cells[i] = list.get(i);
		}
		JTable table = new JTable(cells,head);
		
		table.setAutoCreateRowSorter(true);//使用默认的行排序器
		table.setFillsViewportHeight(true);//表格自适应视口
		
		return table;
	}
	
	/**比对数据下载*/
	public static void downloadCompareData(){
		try {
			if(null != compareData){
				String path = JOptionPane.showInputDialog("请输入下载路径：", "c:/dbtool/data");
				if("c:/dbtool/data".equals(path)){
					new File(path).mkdir();
				}
				if(!new File(path).exists()){
					path = "c:/dbtool/data";
					new File(path).mkdir();
					JOptionPane.showMessageDialog(self, "路径有误，数据将自动下载至c:/dbtool/data");
				}
				
				String leftPath = path + "/leftTable.txt";
				List<Object[]> leftData = compareData.get("left");
				BufferedWriter bw = new BufferedWriter(new FileWriter(leftPath));
				for (Object[] data : leftData) {
					for (int i = 0; i < data.length; i++) {
						bw.write(data[i].toString()+", ");
					}
					bw.newLine();
				}
				bw.close();
				
				String rightPath = path + "/rightTable.txt";
				List<Object[]> rightData = compareData.get("right");
				BufferedWriter bw2 = new BufferedWriter(new FileWriter(rightPath));
				for (Object[] data : rightData) {
					for (int i = 0; i < data.length; i++) {
						bw2.write(data[i].toString()+", ");
					}
					bw2.newLine();
				}
				bw2.close();
				
				JOptionPane.showMessageDialog(self, "数据已下载完毕！");
			}else{
				JOptionPane.showMessageDialog(self, "无比对数据可下载！");
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(self, "下载失败，请查看下载文件夹是否有访问权限！");
		}
	}
}
