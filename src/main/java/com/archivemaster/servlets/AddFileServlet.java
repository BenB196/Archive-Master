package com.archivemaster.servlets;

import com.archivemaster.fedora.FedoraFile;
import com.archivemaster.validation.HttpAPIStatus;
import org.apache.commons.io.IOUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;

@WebServlet("/addFile")
@MultipartConfig
public class AddFileServlet extends HttpServlet {
	@Override
	protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String whatDo = request.getParameter("whatDo");
		final Part filePart = request.getPart("file");
		final InputStream inputStream = filePart.getInputStream();
		byte[] byteArray = IOUtils.toByteArray(inputStream); //Need to store input stream for multiple uses
		final String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
		final String title = FedoraFile.getTitleFromFileName(fileName);
		final String creator = request.getParameter("creator");
		final String subject = request.getParameter("subject");
		final String description = request.getParameter("description");
		final String publisher = request.getParameter("publisher");
		final String contributor = request.getParameter("contributor");
		final String sDate = request.getParameter("sDate");
		final String type = FedoraFile.getFileTypeFromFileName(fileName);
		final String format = filePart.getContentType();
		final String identifier = request.getParameter("identifier");
		final String source = request.getParameter("source");
		final String language = request.getParameter("language");
		final String coverage = request.getParameter("coverage");
		final String rights = request.getParameter("rights");
		final String sha1 = FedoraFile.generateSHAHash(new ByteArrayInputStream(byteArray), 1).toLowerCase();
		final String sha256 = FedoraFile.generateSHAHash(new ByteArrayInputStream(byteArray), 256).toLowerCase();

		//TODO need to figure out how to pass the collection name
		final String collectionName = request.getParameter("collectionName");

		FedoraFile file = new FedoraFile(byteArray, fileName, title, creator, subject, description, publisher, contributor, sDate, type, format, identifier, source, language, coverage, rights, sha1, sha256, collectionName);

		//TODO FedoraFile validation

		HttpAPIStatus apiStatus = null;

		if ((whatDo != null && !whatDo.isEmpty()) && whatDo.equalsIgnoreCase("edit")) {
			apiStatus = FedoraFile.editFedoraFile(file);
		} else {
			apiStatus = FedoraFile.createFedoraFile(file);
		}

		request.setAttribute("apiStatus", apiStatus);
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/views/results.jsp");
		dispatcher.forward(request,response);
	}
}
