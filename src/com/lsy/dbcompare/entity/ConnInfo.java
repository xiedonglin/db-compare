package com.lsy.dbcompare.entity;

import java.io.Serializable;

/**
 * <h4>功能描述：数据源信息
 * <h5><pre>
 * @author 纳川
 * 2018年5月16日
 * email 1029243332@qq.com
 * </pre></h5>
 */
public class ConnInfo implements Serializable{
	/** 连接名 */
	private String connName;
	/** 数据库类别 */
	private String dbType ;
	/** 数据库名 */
	private String dbName ;
	/** 数据库主机名或IP */
	private String host;
	/** 数据库端口号 */
	private String port;
	/** 登录数据库用户名 */
	private String username;
	/** 登录数据库密码 */
	private String pwd;
	/** 数据库驱动 */
	private String driver;
	
	/**表名*/
	private transient String tableName;
	
	public ConnInfo(){}

	public ConnInfo(String connName, String dbType, String host, String port,
			String username, String pwd) {
		super();
		this.connName = connName;
		this.dbType = dbType;
		this.host = host;
		this.port = port;
		this.username = username;
		this.pwd = pwd;
		switch (this.dbType) {
		case DbType.DB_ORACLE:
			this.driver = "oracle.jdbc.driver.OracleDriver";
			break;
		case DbType.DB_MYSQL:
			this.driver = "com.mysql.jdbc.Driver";
			this.dbName = "information_schema";
			break;
        case DbType.DB_POSTGRES:
            this.driver = "org.postgresql.Driver";
            break;
		}
	}

	public ConnInfo(String connName, String dbType, String dbName, String host,
			String port, String username, String pwd, String driver) {
		this(connName, dbType, host, port, username, pwd);
		this.dbName = dbName;
	}

	public String getConnName() {
		return connName;
	}

	public void setConnName(String connName) {
		this.connName = connName;
	}

	public String getDbType() {
		return dbType;
	}

	public void setDbType(String dbType) {
		this.dbType = dbType;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}
	
	public String getTableName() {
		return tableName;
	}
	
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	@Override
	public String toString() {
		return "ConnInfo [connName=" + connName + ", dbType=" + dbType
				+ ", dbName=" + dbName + ", host=" + host + ", port=" + port
				+ ", username=" + username + ", pwd=" + pwd + ", driver="
				+ driver + ", tableName=" + tableName + "]";
	}

}
