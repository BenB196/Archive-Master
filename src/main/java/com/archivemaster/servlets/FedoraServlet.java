package com.archivemaster.servlets;

import com.archivemaster.Fedora;
import com.archivemaster.fedora.Collection;
import com.archivemaster.validation.Validation;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;

import static com.archivemaster.utils.ArchiveMasterUtils.isEmptyString;

@WebServlet("/fedora")
@MultipartConfig
public class FedoraServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String submit = request.getParameter("submit");

		if (submit.equalsIgnoreCase("Create Collection")) {
			String collectionName = request.getParameter("collectionName");
			String collectionDescription = request.getParameter("collectionDescription");

			Collection collection = new Collection(collectionName, collectionDescription);

			Validation validation = Collection.validateCollection(collection);

			if (validation.isValid()) {
				boolean addCollection = Collection.creationCollection((Collection) validation.getObject());
				System.out.println(addCollection); //TODO Actually use response
			} else {
				System.out.println(validation.getResult());
			}
		} else if (submit.equalsIgnoreCase("Delete Collection")) {
			String collectionName = request.getParameter("collectionName");

			Validation validation = Collection.validateCollectionName(collectionName);

			if (validation.isValid()) {
				boolean deleteCollection = Collection.deleteCollection(collectionName);
				System.out.println(deleteCollection); //TODO Actually use response
			} else {
				System.out.println(validation.getResult());
			}
		} else if (submit.equalsIgnoreCase("Delete")) {
			String file = request.getParameter("toDelete");
			try {
				Fedora.fedoraAPIDelete(file);
			} catch (MalformedURLException ex) {
				System.out.println(ex.getMessage()); //TODO throw some sort of error message back and handle cleanly.
			} catch (IOException ex) {
				System.out.println(ex.getMessage()); //TODO throw some sort of error message back and handle cleanly.
			} catch (IllegalArgumentException ex) {
				System.out.println(ex.getMessage()); //TODO throw some sort of error message back and handle cleanly.
			}
		} else if (submit.equalsIgnoreCase("Search Collections")) {
			try {
				System.out.println(Fedora.fedoraAPIGetArray("collection", null));
			} catch (UnsupportedEncodingException ex) {
				System.out.println(ex.getMessage()); //TODO throw some sort of error message back and handle cleanly.
			} catch (MalformedURLException ex) {
				System.out.println(ex.getMessage()); //TODO throw some sort of error message back and handle cleanly.
			} catch (IOException ex) {
				System.out.println(ex.getMessage()); //TODO throw some sort of error message back and handle cleanly.
			} catch (IllegalArgumentException ex) {
				System.out.println(ex.getMessage()); //TODO throw some sort of error message back and handle cleanly.
			}
		} else if (submit.equalsIgnoreCase("Search Collection")) {
			String collectionName = request.getParameter("collectionName");
			try {
				System.out.println(Fedora.fedoraAPIGetArray("file", collectionName));
			} catch (UnsupportedEncodingException ex) {
				System.out.println(ex.getMessage()); //TODO throw some sort of error message back and handle cleanly.
			} catch (MalformedURLException ex) {
				System.out.println(ex.getMessage()); //TODO throw some sort of error message back and handle cleanly.
			} catch (IOException ex) {
				System.out.println(ex.getMessage()); //TODO throw some sort of error message back and handle cleanly.
			} catch (IllegalArgumentException ex) {
				System.out.println(ex.getMessage()); //TODO throw some sort of error message back and handle cleanly.
			}
		}
	}

	//protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	//
	//}
}