package com.archive_master;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author benbr
 */

@WebServlet("/fedora")
public class FedoraServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		String submit = request.getParameter("submit");

		if (submit.equalsIgnoreCase("getFedoraContainers")) { //Get Fedora Container DOES NOT WORK
			fedoraAPIHandler("0a241fde-a4f8-4ab6-b19a-fe04c4531172", "GET",null,null, null);
		} else if (submit.equalsIgnoreCase("createFedoraNode")) {
			fedoraAPIHandler("", "POST",null,null, null);
		}
	}

	private void fedoraAPIHandler (String appendURL, String method, String contentType, String contentDisposition, File file) {
		try {
			URL url  = new URL("http://localhost:8080/rest/"); //Set Base URL

			url = new URL(url, appendURL); //Append Base URL
			try {
				HttpURLConnection conn = (HttpURLConnection) url.openConnection(); //Try to open new connection to Fedora
				conn.setRequestMethod(method); //Set request method

				if (contentType != null && !contentType.isEmpty()) { //Set content-type if not null/empty
					conn.setRequestProperty("Content-Type", contentType);
				}

				if (contentDisposition != null && !contentDisposition.isEmpty()) { //Set content-dispositon if not null/empty
					conn.setRequestProperty("Content-Disposition", contentDisposition);
				}

				System.out.println("Response Code: " + conn.getResponseCode()); //Get Response code

				BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream())); //Create reader to read response context

				StringBuffer result = new StringBuffer(); //Create string buffer
				String line; //Init line var
				while ((line = reader.readLine()) != null) { //Loop through reader and append lines to result
					result.append(line);
				}
				System.out.println(result); //Print result
				reader.close(); //Close reader
				conn.disconnect(); //Close fedora connection
			} catch (IOException ex) {
				System.out.println(ex.getMessage());
				//TODO throw some sort of error message back and handle cleanly.
			}
		} catch (MalformedURLException ex) {
			System.out.println(ex.getMessage());
			//TODO throw some sort of error message back and handle cleanly.
		}
	}
}
