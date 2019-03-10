package com.archivemaster.servlets;

import com.archivemaster.fedora.Collection;
import com.archivemaster.validation.HttpAPIStatus;
import com.archivemaster.validation.Validation;

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
			HttpAPIStatus addCollection = Collection.creationCollection((Collection) validation.getObject());
			System.out.println(addCollection.isSuccess()); //TODO this is where you should redirect to results page
		} else {
			System.out.println(validation.getResult()); //TODO this is where you should redirect back to input page with validation error.
		}
	}
}
