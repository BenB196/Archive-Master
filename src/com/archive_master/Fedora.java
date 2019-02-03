package com.archive_master;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Fedora class to handle methods that need to talk with the Fedora API
 *
 * @author benbr
 */

public class Fedora {

	/**
	 * fedoraAPIHandler - handles actual interaction between java and the fedora API
	 * @param appendURL - Addition part of API URL that needs to be added
	 * @param method - API submit method GET, POST, PUT, PATCH, HEAD, OPTIONS, DELETE, MOVE, COPY
	 * @param contentType - URL content-type value
	 * @param contentDisposition - URL content-disposition value
	 * @param file - file to be uploaded if uploading a file
	 */
	public static void fedoraAPIHandler (String appendURL, String method, String contentType, String contentDisposition, File file) throws MalformedURLException, IOException {
		URL url  = new URL("http://localhost:8080/rest/"); //Set Base URL

		url = new URL(url, appendURL); //Append Base URL

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
	}
}
