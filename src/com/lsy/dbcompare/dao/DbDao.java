package com.lsy.dbcompare.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

import com.lsy.dbcompare.entity.ConnInfo;
import com.lsy.dbcompare.entity.DbType;
import com.lsy.dbcompare.util.JDBCUtil;

/**
 * <h4>功能描述：数据库CRUD
 * <h5><pre>
 * @author 纳川
 * 2018年5月16日
 * email 1029243332@qq.com
 * </pre></h5>
 */
public class DbDao {
	/**获取当前用户的数据库*/
	public static ArrayList<String> getAllDbs(ConnInfo info){
		ArrayList<String> list = new ArrayList<String>();
		try {
			if(DbType.DB_ORACLE.equals(info.getDbType())){
				list.add(info.getDbName());
			}else if(DbType.DB_MYSQL.equals(info.getDbType())){
				String sql = "SELECT SCHEMA_NAME FROM information_schema.SCHEMATA";
				Statement stmt = JDBCUtil.getConnection(info).createStatement();
				ResultSet rs = stmt.executeQuery(sql);
				while(rs.next()){
					list.add(rs.getString(1));
				}
				JDBCUtil.closeConnection();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**获取当前数据库中所有的表*/
	public static ArrayList<String> getAllTables(ConnInfo info){
		ArrayList<String> list = new ArrayList<String>();
		try {
			String sql = null;
			if(DbType.DB_ORACLE.equals(info.getDbType())){
				sql = "select * from user_tables";
			}else if(DbType.DB_MYSQL.equals(info.getDbType())){
				sql = "select table_name from information_schema.tables where table_schema='"+info.getDbName()+"'";
			}
			Statement stmt = JDBCUtil.getConnection(info).createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				list.add(rs.getString(1));
			}
			JDBCUtil.closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**获取表中的字段*/
	public static ArrayList<String> getAllCols(ConnInfo info){
		ArrayList<String> list = new ArrayList<String>();
		String sql = null;
		try {
			if(DbType.DB_ORACLE.equals(info.getDbType())){
				sql = "select COLUMN_NAME from user_tab_columns where table_name = '"+info.getTableName().toUpperCase()+"'";
			}else if(DbType.DB_MYSQL.equals(info.getDbType())){
				sql = "select COLUMN_NAME from information_schema.columns where table_name='"+info.getTableName()+"'";
			}
			Statement stmt = JDBCUtil.getConnection(info).createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				list.add(rs.getString(1));
			}
			JDBCUtil.closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		System.out.println(list);
		System.out.println("sql"+sql);
		return list;
	}
	
	/**查询数据*/
	public static Vector<Object[]> getSelData(ConnInfo info,ArrayList<String> cols){
		Vector<Object[]> list = new Vector<Object[]>();
		try {
			//构建sql
			StringBuilder sb = new StringBuilder("select ");
			for (int i = 0; i < cols.size(); i++) {
				sb.append(cols.get(i));
				if(i != cols.size() - 1){
					sb.append(", ");
				}
			}
			sb.append(" from ");
			sb.append(info.getTableName());
			System.out.println("SQL: "+sb.toString());
			Statement stmt = JDBCUtil.getConnection(info).createStatement();
			ResultSet rs = stmt.executeQuery(sb.toString());
			
			while(rs.next()){
				Object[] row = new Object[cols.size()];
				for (int i = 0; i < cols.size(); i++) {
					 row[i] = rs.getObject(i + 1);
				}
				list.add(row);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
}
