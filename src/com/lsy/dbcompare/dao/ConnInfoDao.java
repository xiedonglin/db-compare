package com.lsy.dbcompare.dao;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import com.lsy.dbcompare.entity.ConnInfo;

/**
 * <h4>功能描述：数据源CRUD
 * <h5><pre>
 * @author 纳川
 * 2018年5月16日
 * email 1029243332@qq.com
 * </pre></h5>
 */
public class ConnInfoDao {
	private static ArrayList<ConnInfo> list = null;
	
	/**预读连接信息*/
	static{
		try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream("src/com/lsy/dbcompare/data/connInfo.data"))) {
			list = (ArrayList<ConnInfo>) ois.readObject();
		} catch (IOException | ClassNotFoundException e) {
			list = new ArrayList<ConnInfo>();
		} 
	}
	
	/**获取所有的连接信息*/
	public static ArrayList<ConnInfo> getAllConnInfo(){
		return list;
	}
	
	/**获取所有的连接名
	 * @return */
	public static ArrayList<String> getAllConnNames(){
		ArrayList<String> names = new ArrayList<String>();
		for (ConnInfo c : list) {
			names.add(c.getConnName());
		}
		return names;
	}
	/**
	 * 根据连接名删除连接信息
	 * @param connName
	 * @return
	 */
	public static boolean delConnInfoByConnName(String connName) {
		boolean flag = false;
		flag = list.remove(getConnInfoByConnName(connName));
		if(flag){
			saveConnInfo();
		}
		return flag;
	}
	
	/**新增连接信息*/
	public static boolean addConnInfo(ConnInfo info) {
		list.add(info);
		return saveConnInfo();
	}
	
	/** 根据连接名获取连接信息*/
	public static ConnInfo getConnInfoByConnName(String connName) {
		ConnInfo info = null;
		for (ConnInfo c : list) {
			if(c.getConnName().equals(connName)){
				info = new ConnInfo(c.getConnName(), c.getDbType(), 
						c.getDbName(), c.getHost(), c.getPort(), 
						c.getUsername(), c.getPwd(), c.getDriver());
				break;
			}
		}
		return info;
	}
	
	/**保存连接信息*/
	private static boolean saveConnInfo() {
		boolean flag = true;
		try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("src/com/lsy/dbcompare/data/connInfo.data"))) {
			oos.writeObject(list);
		} catch (IOException e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}
}
