package com.archivemaster.servlets;

import com.archivemaster.fedora.Collection;
import com.archivemaster.fedora.FedoraFile;
import com.archivemaster.validation.HttpAPIStatus;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/deleteFile")
@MultipartConfig
public class DeleteFileServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String collectionAndFileName = request.getParameter("deleteFile");
		String[] output = collectionAndFileName.split("/");
		final String collectionName = output[0];
		final String fileName = output[1];
		HttpAPIStatus apiStatus = FedoraFile.deleteFile(collectionName, fileName);
		request.setAttribute("apiStatus", apiStatus);
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/views/results.jsp");
		dispatcher.forward(request,response);
	}
}
