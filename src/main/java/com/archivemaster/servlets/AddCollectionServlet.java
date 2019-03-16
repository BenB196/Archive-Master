package com.archivemaster.servlets;

import com.archivemaster.fedora.Collection;
import com.archivemaster.validation.HttpAPIStatus;
import com.archivemaster.validation.Validation;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/addCollection")
@MultipartConfig
public class AddCollectionServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String collectionName = request.getParameter("title");
		String collectionDescription = request.getParameter("description");

		Collection collection = new Collection(collectionName, collectionDescription);

		Validation validation = Collection.validateCollection(collection);

		if (validation.isValid()) {
			HttpAPIStatus apiStatus = Collection.creationCollection((Collection) validation.getObject());
			request.setAttribute("apiStatus", apiStatus);
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/views/results.jsp");
			dispatcher.forward(request,response);
		} else {
			System.out.println(validation.getResult()); //TODO this is where you should redirect back to input page with validation error.
		}
	}
}
