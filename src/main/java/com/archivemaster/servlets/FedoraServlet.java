package com.archivemaster.servlets;

import com.archivemaster.Fedora;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.MalformedURLException;

@WebServlet("/fedora")
@MultipartConfig
public class FedoraServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String submit = request.getParameter("submit");

		if (submit.equalsIgnoreCase("getFedoraContainers")) { //Get Fedora Container DOES NOT WORK
			try {
				Fedora.fedoraAPIHandler("", "GET",null,null, null);
			} catch (MalformedURLException ex) {
				System.out.println(ex.getMessage()); //TODO throw some sort of error message back and handle cleanly.
			} catch (IOException ex) {
				System.out.println(ex.getMessage()); //TODO throw some sort of error message back and handle cleanly.
			}
		} else if (submit.equalsIgnoreCase("createFedoraNode")) {
			String collectionName = request.getParameter("collectionName");
			try {
				Fedora.fedoraAPIHandler(collectionName, "PUT", null, null, null);
			} catch (MalformedURLException ex) {
				System.out.println(ex.getMessage()); //TODO throw some sort of error message back and handle cleanly.
			} catch (IOException ex) {
				System.out.println(ex.getMessage()); //TODO throw some sort of error message back and handle cleanly.
			}
		}
	}

	//protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	//
	//}
}
