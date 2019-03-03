package com.archivemaster.servlets;

import com.archivemaster.Fedora;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;

@WebServlet("/fedora")
@MultipartConfig
public class FedoraServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String submit = request.getParameter("submit");

		if (submit.equalsIgnoreCase("Create Collection")) {
			String collectionName = request.getParameter("collectionName"); //TODO Validate Collection Name
			try {
				Fedora.fedoraAPICreate("collection", collectionName, null, null, null, null, null, null);
			} catch (MalformedURLException ex) {
				System.out.println(ex.getMessage()); //TODO throw some sort of error message back and handle cleanly.
			} catch (IOException ex) {
				System.out.println(ex.getMessage()); //TODO throw some sort of error message back and handle cleanly.
			} catch (IllegalArgumentException ex) {
				System.out.println(ex.getMessage()); //TODO throw some sort of error message back and handle cleanly.
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