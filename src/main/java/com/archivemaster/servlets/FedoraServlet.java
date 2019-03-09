package com.archivemaster.servlets;

import com.archivemaster.Fedora;
import com.archivemaster.fedora.Collection;
import com.archivemaster.fedora.FedoraFile;
import com.archivemaster.validation.Validation;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
			//try {
				//System.out.println(Fedora.fedoraAPIGetArray("collection", null));
				Collection.getCollections();
			//} catch (UnsupportedEncodingException ex) {
			//	System.out.println(ex.getMessage()); //TODO throw some sort of error message back and handle cleanly.
			//} catch (MalformedURLException ex) {
			//	System.out.println(ex.getMessage()); //TODO throw some sort of error message back and handle cleanly.
			//} catch (IOException ex) {
			//	System.out.println(ex.getMessage()); //TODO throw some sort of error message back and handle cleanly.
			//} catch (IllegalArgumentException ex) {
			//	System.out.println(ex.getMessage()); //TODO throw some sort of error message back and handle cleanly.
			//}
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
		} else if (submit.equalsIgnoreCase("Upload File")) {
			final Part filePart = request.getPart("file");
			final InputStream inputStream = filePart.getInputStream();
			final String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
			final String title = FedoraFile.getTitleFromFileName(fileName);
			final String creator = request.getParameter("creator");//TODO Creator
			final String subject = request.getParameter("subject");//TODO Subject
			final String description = request.getParameter("description");//TODO Description
			final String publisher = request.getParameter("publisher");//TODO Publisher
			final String contributor = request.getParameter("contributor");//TODO Contributor
			final String sDate = request.getParameter("date");//TODO Date
			final String type = FedoraFile.getFileTypeFromFileName(fileName);
			final String format = filePart.getContentType();
			final String identifier = request.getParameter("identifier");//TODO Identifier
			final String source = request.getParameter("source");//TODO source
			final String lanuage = request.getParameter("lanuage");//TODO Language
			final String coverage = request.getParameter("coverage");//TODO Coverage
			final String rights = request.getParameter("rights");//TODO Rights
			final String sha1 = FedoraFile.generateSHAHash(inputStream, 1);
			final String sha256 = FedoraFile.generateSHAHash(inputStream, 256);
			final String collectionName = request.getParameter("collectionName");//TODO Rights

			FedoraFile file = new FedoraFile(inputStream, fileName, title, creator, subject, description, publisher, contributor, sDate, type, format, identifier, source, lanuage, coverage, rights, sha1, sha256, collectionName);
		}
	}

	//protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	//
	//}
}