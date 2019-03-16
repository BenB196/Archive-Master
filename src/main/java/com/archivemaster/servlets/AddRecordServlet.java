package com.archivemaster.servlets;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/addRecord")
@MultipartConfig
public class AddRecordServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String collectionName = request.getParameter("addFile");
		request.setAttribute("collectionName", collectionName);
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/views/addRecord.jsp");
		dispatcher.forward(request,response);
	}
}
