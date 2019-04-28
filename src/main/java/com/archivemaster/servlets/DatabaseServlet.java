package com.archivemaster.servlets;

import java.sql.*;

public class DatabaseServlet {
	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_HOST = "localhost";
    static final String DB_NAME = "ArchiveMaster";
	static final String DB_URL = "jdbc:mysql://" + DB_HOST + "/" + DB_NAME;

	//  Database credentials
	static final String DB_USER = "username";
	static final String DB_PASS = "password";

	public static Connection getConnection(){

		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}

		return con;
	}
}