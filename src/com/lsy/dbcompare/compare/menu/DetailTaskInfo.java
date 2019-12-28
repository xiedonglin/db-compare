package com.lsy.dbcompare.compare.menu;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.lsy.dbcompare.entity.TaskInfo;

public class DetailTaskInfo extends JDialog implements ActionListener{
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
	    
	    this.add(modifyBtn); this.add(Addno);
	    this.modifyBtn.addActionListener(this);
	    this.Addno.addActionListener(this);
	    this.setSize(350,400);
	    this.setLocation(100, 260);
	    this.setLayout(new GridLayout(1,1));
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
			//showData();
			this.dispose();
		}
	 }
}