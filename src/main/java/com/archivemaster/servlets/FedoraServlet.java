package com.archivemaster.servlets;

import com.archivemaster.fedora.Collection;
import com.archivemaster.fedora.FedoraFile;
import com.archivemaster.validation.HttpAPIStatus;
import com.archivemaster.validation.Validation;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@WebServlet("/fedora")
@MultipartConfig
public class FedoraServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String submit = request.getParameter("submit");
		 if (submit.equalsIgnoreCase("Delete Collection")) {
			String collectionName = request.getParameter("collectionName");

			Validation validation = Collection.validateCollectionName(collectionName);

			if (validation.isValid()) {
				HttpAPIStatus deleteCollection = Collection.deleteCollection(collectionName);
				System.out.println(deleteCollection.isSuccess()); //TODO Actually use response
			} else {
				System.out.println(validation.getResult());
			}
		} else if (submit.equalsIgnoreCase("Search Collections")) {
			Collection.getCollections();
		} else if (submit.equalsIgnoreCase("Search Collection")) {
			String collectionName = request.getParameter("collectionName");
			System.out.println(FedoraFile.getFiles(collectionName));
		} else if (submit.equalsIgnoreCase("Delete File")) {
			final String collectionName = request.getParameter("collectionName");
			final String fileName = request.getParameter("fileName");

			//TODO validate collection and file name

			FedoraFile.deleteFile(collectionName, fileName);
		}
	}

	//protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	//
	//}
}