package com.archivemaster.servlets;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@WebServlet("/rest/login")
@MultipartConfig
public class LoginServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		if(username.isEmpty() || password.isEmpty() )
		{
			System.out.println("Please enter all fields properly");
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/views/login.jsp");
			dispatcher.include(request,response);
		}
		else
		{
			Connection conn = null;
			Statement stmt = null;
			try {
				//STEP 2: Register JDBC driver
				Class.forName("com.mysql.jdbc.Driver");

				//STEP 3: Open a connection
				System.out.println("Connecting to database...");
				conn = DatabaseServlet.getConnection();

				//STEP 4: Execute a query
				System.out.println("Creating statement...");
				stmt = conn.createStatement();
				String sql;
				sql = "SELECT * FROM `users` WHERE `username` =?";
				ResultSet rs = stmt.executeQuery(sql);

				//STEP 5: Extract data from result set
				while (rs.next()) {
					if (rs.getString("password").equals("password")) {
						System.out.print("Logged in");
						RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/views/login.jsp");
						dispatcher.forward(request,response);
					}
					else {
						System.out.print("Username or password invalid");
					}
				}
				//STEP 6: Clean-up environment
				rs.close();
				stmt.close();
				conn.close();
			} catch (SQLException se) {
				//Handle errors for JDBC
				se.printStackTrace();
			} catch (Exception e) {
				//Handle errors for Class.forName
				e.printStackTrace();
			}
		}
	}

}