package com.archivemaster;

import org.apache.commons.io.IOUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

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
	 * @param sha1 - sha1 hash
	 * @param sha256 - sha256 hash
	 */
	public static void fedoraAPIHandler (String appendURL, String method, String contentType, String contentDisposition, File file, String sha1, String sha256) throws MalformedURLException, IOException {
		URL url = new URL("http://localhost:8080/rest/"); //Set Base URL

		url = new URL(url, URLEncoder.encode(appendURL, "UTF-8")); //Append Base URL

		HttpURLConnection conn = (HttpURLConnection) url.openConnection(); //Try to open new connection to Fedora

		conn.setDoOutput(true);
		conn.setRequestMethod(method); //Set request method

		if (contentType != null && !contentType.isEmpty()) { //Set content-type if not null/empty
			conn.setRequestProperty("Content-Type", contentType);
		}

		if (contentDisposition != null && !contentDisposition.isEmpty()) { //Set content-dispositon if not null/empty
			conn.setRequestProperty("Content-Disposition", contentDisposition);
		}

		if (sha1 != null && !sha1.isEmpty()) {  //Set digest if sha1 has been specified
			conn.setRequestProperty("digest", "sha=" + sha1);
		}
		if (sha256 != null && !sha256.isEmpty()) { //Set digest if sha256 has been specified
			conn.setRequestProperty("digest", "sha-256=" + sha256);
		}

		if (file != null) {
			IOUtils.copy(new FileInputStream(file), conn.getOutputStream());
			String res = IOUtils.toString(conn.getInputStream());
			System.out.println("Upload file result" + res);
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
