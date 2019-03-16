package com.archivemaster.servlets;

import com.archivemaster.fedora.FedoraFile;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/editFile")
@MultipartConfig
public class EditFileServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String collectionAndFileName = request.getParameter("editFile");
		String[] output = collectionAndFileName.split("/");
		final String collectionName = output[0];
		final String fileName = output[1];
		FedoraFile file = FedoraFile.getFedoraFile(collectionName, fileName);
		request.setAttribute("file", file);
		request.setAttribute("collectionName", collectionName);
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/views/editRecord.jsp");
		dispatcher.forward(request,response);
	}
}
