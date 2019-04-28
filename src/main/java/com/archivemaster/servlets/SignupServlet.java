package com.archivemaster.servlets;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

@WebServlet("/rest/signup")
@MultipartConfig
public class SignupServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String first_name = request.getParameter("first_name");
		String last_name = request.getParameter("last_name");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String address = request.getParameter("address");
		String contact = request.getParameter("contact");

		if(first_name.isEmpty() || last_name.isEmpty() || username.isEmpty() ||
				password.isEmpty() || address.isEmpty() || contact.isEmpty())
		{
			System.out.println("Please enter all fields properly");
			RequestDispatcher req = request.getRequestDispatcher("/views/signup.jsp");
			req.include(request, response);
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
				sql = "INSERT INTO `users`(`first_name`, `last_name`, `username`, `password`, `address`, `contact`) VALUES (?,?,?,?,?,?)";
				ResultSet rs = stmt.executeQuery(sql);

				//STEP 5: Extract data from result set
				while (rs.rowInserted()) {
					System.out.print("New User Added");
					RequestDispatcher req = request.getRequestDispatcher("/views/signup.jsp");
					req.forward(request, response);
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