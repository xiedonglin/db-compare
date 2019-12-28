package com.lsy.dbcompare.entity;

import java.io.Serializable;
import java.util.List;

public class TaskInfo implements Serializable{
	/** 任务id 系统随机生成*/
	private String taskId;
	/** 任务名 */
	private String taskName;
	/** 连接信息1 */
	private ConnInfo connInfo1;
	/** 连接信息2 */
	private ConnInfo connInfo2;
	/** 任务启动时间*/
	private String startTime;
	/** 任务结束时间*/
	private String endTime;
	/** 比较的字段集合*/
	private List<String> fileds1;
	/** 比较的字段集合*/
	private List<String> fileds2;
	
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public ConnInfo getConnInfo1() {
		return connInfo1;
	}
	public void setConnInfo1(ConnInfo connInfo1) {
		this.connInfo1 = connInfo1;
	}
	public ConnInfo getConnInfo2() {
		return connInfo2;
	}
	public void setConnInfo2(ConnInfo connInfo2) {
		this.connInfo2 = connInfo2;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public List<String> getFileds1() {
		return fileds1;
	}
	public void setFileds1(List<String> fileds1) {
		this.fileds1 = fileds1;
	}
	public List<String> getFileds2() {
		return fileds2;
	}
	public void setFileds2(List<String> fileds2) {
		this.fileds2 = fileds2;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	@Override
	public String toString() {
		return "TaskInfo [taskId=" + taskId + ", taskName=" + taskName + ", connInfo1=" + connInfo1 + ", connInfo2="
				+ connInfo2 + ", startTime=" + startTime + ", endTime=" + endTime + ", fileds1=" + fileds1
				+ ", fileds2=" + fileds2 + "]";
	}
}
