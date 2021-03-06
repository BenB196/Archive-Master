package com.archivemaster.servlets;

import com.archivemaster.fedora.Collection;
import com.archivemaster.validation.HttpAPIStatus;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/deleteCollection")
@MultipartConfig
public class DeleteCollectionServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String collectionName = request.getParameter("deleteCollection");
		HttpAPIStatus apiStatus = Collection.deleteCollection(collectionName);
		request.setAttribute("apiStatus", apiStatus);
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/views/results.jsp");
		dispatcher.forward(request,response);
	}
}
