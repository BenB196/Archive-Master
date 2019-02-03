package com.archive_master.servlets;

import com.archive_master.Fedora;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.MalformedURLException;

/**
 * FedoraServlet class to handle communication between jsp and java for Fedora actions
 *
 * @author benbr
 */

@WebServlet("/fedora")
public class FedoraServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		String submit = request.getParameter("submit");

		if (submit.equalsIgnoreCase("getFedoraContainers")) { //Get Fedora Container DOES NOT WORK
			try {
				Fedora.fedoraAPIHandler("0a241fde-a4f8-4ab6-b19a-fe04c4531172", "GET",null,null, null);
			} catch (MalformedURLException ex) {
				System.out.println(ex.getMessage()); //TODO throw some sort of error message back and handle cleanly.
			} catch (IOException ex) {
				System.out.println(ex.getMessage()); //TODO throw some sort of error message back and handle cleanly.
			}
		} else if (submit.equalsIgnoreCase("createFedoraNode")) {
			try {
				Fedora.fedoraAPIHandler("", "POST", null, null, null);
			} catch (MalformedURLException ex) {
				System.out.println(ex.getMessage()); //TODO throw some sort of error message back and handle cleanly.
			} catch (IOException ex) {
				System.out.println(ex.getMessage()); //TODO throw some sort of error message back and handle cleanly.
			}
		}
	}
}