package com.archivemaster.servlets;

import com.archivemaster.fedora.Collection;
import com.archivemaster.fedora.FedoraFile;
import com.archivemaster.validation.HttpAPIStatus;
import com.archivemaster.validation.Validation;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.nio.file.Paths;

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
				HttpAPIStatus addCollection = Collection.creationCollection((Collection) validation.getObject());
				System.out.println(addCollection.isSuccess()); //TODO Actually use response
			} else {
				System.out.println(validation.getResult());
			}
		} else if (submit.equalsIgnoreCase("Delete Collection")) {
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
		} else if (submit.equalsIgnoreCase("Upload File")) {
			final Part filePart = request.getPart("file");
			final InputStream inputStream = filePart.getInputStream();
			byte[] byteArray = IOUtils.toByteArray(inputStream); //Need to store input stream for multiple uses
			final String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
			final String title = FedoraFile.getTitleFromFileName(fileName);
			final String creator = request.getParameter("creator");//TODO Creator
			final String subject = request.getParameter("subject");//TODO Subject
			final String description = request.getParameter("description");//TODO Description
			final String publisher = request.getParameter("publisher");//TODO Publisher
			final String contributor = request.getParameter("contributor");//TODO Contributor
			final String sDate = request.getParameter("sDate");//TODO Date
			final String type = FedoraFile.getFileTypeFromFileName(fileName);
			final String format = filePart.getContentType();
			final String identifier = request.getParameter("identifier");//TODO Identifier
			final String source = request.getParameter("source");//TODO source
			final String language = request.getParameter("language");//TODO Language
			final String coverage = request.getParameter("coverage");//TODO Coverage
			final String rights = request.getParameter("rights");//TODO Rights
			final String sha1 = FedoraFile.generateSHAHash(new ByteArrayInputStream(byteArray), 1).toLowerCase();
			final String sha256 = FedoraFile.generateSHAHash(new ByteArrayInputStream(byteArray), 256).toLowerCase();
			final String collectionName = request.getParameter("collectionName");//TODO Rights

			FedoraFile file = new FedoraFile(byteArray, fileName, title, creator, subject, description, publisher, contributor, sDate, type, format, identifier, source, language, coverage, rights, sha1, sha256, collectionName);

			FedoraFile.createFedoraFile(file);
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