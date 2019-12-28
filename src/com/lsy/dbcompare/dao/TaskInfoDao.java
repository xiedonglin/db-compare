package com.lsy.dbcompare.dao;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import com.lsy.dbcompare.entity.TaskInfo;

public class TaskInfoDao {
private static ArrayList<TaskInfo> list = null;
	
	/**预读任务信息*/
	static{
		try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream("src/com/lsy/dbcompare/data/taskInfo.data"))) {
			list = (ArrayList<TaskInfo>) ois.readObject();
		} catch (IOException | ClassNotFoundException e) {
			list = new ArrayList<TaskInfo>();
		} 
	}
	
	/**获取所有的任务信息*/
	public static ArrayList<TaskInfo> getRealTimeAllTaskInfo(){
		try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream("src/com/lsy/dbcompare/data/taskInfo.data"))) {
			list = (ArrayList<TaskInfo>) ois.readObject();
		} catch (IOException | ClassNotFoundException e) {
			list = new ArrayList<TaskInfo>();
		} 
		return list;
	}
	/**获取所有的任务信息*/
	public static ArrayList<TaskInfo> getAllTaskInfo(){
		return list;
	}
	
	/**获取所有的任务名称
	 * @return */
	public static ArrayList<String> getAllTaskNames(){
		ArrayList<String> names = new ArrayList<String>();
		for (TaskInfo c : list) {
			names.add(c.getTaskName());
		}
		return names;
	}
	
	/**
	 * 根据连接名删除连接信息
	 * @param connName
	 * @return
	 */
	public static boolean delTaskInfoByTaskId(String taskId) {
		boolean flag = false;
		flag = list.remove(getTaskInfoByTaskId(taskId));
		if(flag){
			saveTaskInfo();
		}
		return flag;
	}
	
	/**新增连接信息*/
	public static boolean addTaskInfo(TaskInfo info) {
		list.add(info);
		return saveTaskInfo();
	}
	
	/** 根据连接名获取连接信息*/
	public static TaskInfo getTaskInfoByTaskId(String taskId) {
		TaskInfo info = null;
		for (TaskInfo c : list) {
			if(c.getTaskId().equals(taskId)){
				info = c;//-------TODO
				break;
			}
		}
		return info;
	}
	
	/**保存连接信息*/
	private static boolean saveTaskInfo() {
		boolean flag = true;
		try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("src/com/lsy/dbcompare/data/taskInfo.data"))) {
			oos.writeObject(list);
		} catch (IOException e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}
	/**
	 * 根据id查询任务信息
	 * @param taskId 任务id
	 * @return
	 */
	public static TaskInfo queryTaskInfoById(String taskId) {
		for (TaskInfo taskInfo : list) {
			if (taskId.equals(taskInfo.getTaskId())) {
				return taskInfo;
			}
		}
		return null;
	}
}
