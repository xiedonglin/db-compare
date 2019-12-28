package com.lsy.dbcompare.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.lsy.dbcompare.entity.ConnInfo;
import com.lsy.dbcompare.entity.DbType;

/**
 * <h4>鍔熻兘鎻忚堪锛氭暟鎹簱杩炴帴宸ュ叿
 * <h5><pre>
 * @author 绾冲窛
 * 2018骞�5鏈�15鏃�
 * email 1029243332@qq.com
 * </pre></h5>
 */
public class JDBCUtil {
	private static Connection conn = null;
	
	/**
	 * 鍔熻兘鎻忚堪锛氳幏鍙栨暟鎹簱杩炴帴
	 * @return Connection杩炴帴瀵硅薄
	 */
	public static Connection getConnection(ConnInfo info){
		try {
			Class.forName(info.getDriver());
			StringBuilder url = new StringBuilder();
			
			switch (info.getDbType()) {
			case DbType.DB_ORACLE:
				url.append("jdbc:oracle:thin:@");
				url.append(info.getHost());
				url.append(":");
				url.append(info.getPort());
				url.append(":");
				url.append(info.getDbName());
				break;
			case DbType.DB_MYSQL:
				url.append("jdbc:mysql://");
				url.append(info.getHost());
				url.append(":");
				url.append(info.getPort());
				url.append("/");
				url.append(info.getDbName());
				break;
            case DbType.DB_POSTGRES:
                url.append("jdbc:postgresql://");
                url.append(info.getHost());
                url.append(":");
                url.append(info.getPort());
                url.append("/");
                url.append(info.getDbName());
                break;
			}
			
			System.out.println(url);
			conn = DriverManager.getConnection(url.toString(), info.getUsername(), info.getPwd());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	/**
	 * 鍔熻兘鎻忚堪锛氬叧闂繛鎺ュ璞�
	 * @param conn 杩炴帴瀵硅薄
	 */
	public static void closeConnection(){
		try {
			if (null != conn) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
