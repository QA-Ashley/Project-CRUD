package main.java.com.qa.db;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	static String db;
	static String DB_URL;
	static final String USER = "root";
	static final String PASS = "toor";

	public Connection conn = null;
	public Statement stmt = null;

	
	public Database(String db) {
		Database.db = db;
		Database.DB_URL = "jdbc:mysql://35.189.114.24/"+db;//+"?allowPublicKeyRetrieval=true&useSSL=false";
		try {
			Class.forName(JDBC_DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			stmt = conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public Statement getStmt() {
		return stmt;
	}
}
