package com.archivemaster.servlets;

import com.archivemaster.fedora.Collection;
import com.archivemaster.fedora.FedoraFile;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/editCollection")
@MultipartConfig
public class EditCollectionServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String collectionName = request.getParameter("editCollection");
		System.out.println(collectionName);
		Collection collection = Collection.getCollectionByName(collectionName);
		ArrayList<FedoraFile> files = FedoraFile.getFiles(collectionName);
		request.setAttribute("collection", collection);
		request.setAttribute("files", files);
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/views/editCollection.jsp");
		dispatcher.forward(request,response);
	}
}
