package com.lsy.dbcompare.compare.menu;

import javax.swing.table.DefaultTableModel;
/**
 * @Title: MyTableModel.java 
 * @Package: org.opentcs.guing.plugins.panels.statistics 
 * @Description: 设置jtable可编辑或者不可编辑，设置哪一行是特殊的行
 * 			（当默认Table不可编辑，此行可编辑，当默认Table可编辑，此行设置不可编辑）
 * 			希望有人能看得懂，哈哈哈哈哈！
 * @author: yuanzy
 * @date: 2017年7月13日 上午10:17:14 
 * @email: 1009198169@qq.com
 * @TEL: 18811398512
 * @version V1.0
 */
public class MyTableModel extends DefaultTableModel{
	private boolean canChange = false;//设置该Table默认情况是可编辑还是不可编辑
	private int line =-1;//特殊行，默认是-1，都不特殊
	@Override
	public boolean isCellEditable(int row, int column) {
		if (row==line) {
			return !canChange;
		}
		return canChange;
	}
	/**
	 * 设置某一行为特殊行
	 * @param line
	 */
	public void setLine(int line){
		this.line = line;
	}
	/**
	 * 设置默认的模式（可编辑，不可编辑）
	 * @param canChange
	 */
	public void setCanchange(boolean canChange){
		this.canChange = canChange;
	}
}