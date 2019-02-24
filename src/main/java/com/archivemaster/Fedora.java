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

	private static String BASEURL = "http://localhost:8080/rest/";

	/**
	 * generateURL - Generates the required URL by appending the base url with the appendURL variable
	 * @param appendURL
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws MalformedURLException
	 */
	private static URL generateURL (String appendURL, String additionalAppend) throws UnsupportedEncodingException, MalformedURLException{
		return (additionalAppend == null || additionalAppend.isEmpty()) ? new URL(BASEURL + URLEncoder.encode(appendURL, "UTF-8")) : new URL(BASEURL + URLEncoder.encode(appendURL, "UTF-8") + additionalAppend);
	}

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
	public static void fedoraAPIHandler (String appendURL, String method, String contentType, String contentDisposition, File file, String sha1, String sha256) throws UnsupportedEncodingException, MalformedURLException, IOException {
		URL url = generateURL(appendURL, null);

		HttpURLConnection connection = (HttpURLConnection) url.openConnection(); //Open a new connection to Fedora

		connection.setDoOutput(true);
		connection.setRequestMethod(method); //Set request method

		if (contentType != null && !contentType.isEmpty()) { //Set content-type if not null/empty
			connection.setRequestProperty("Content-Type", contentType);
		}

		if (contentDisposition != null && !contentDisposition.isEmpty()) { //Set content-dispositon if not null/empty
			connection.setRequestProperty("Content-Disposition", contentDisposition);
		}

		if (sha1 != null && !sha1.isEmpty()) {  //Set digest if sha1 has been specified
			connection.setRequestProperty("digest", "sha=" + sha1);
		}
		if (sha256 != null && !sha256.isEmpty()) { //Set digest if sha256 has been specified
			connection.setRequestProperty("digest", "sha-256=" + sha256);
		}

		if (file != null) {
			IOUtils.copy(new FileInputStream(file), connection.getOutputStream());
			String res = IOUtils.toString(connection.getInputStream());
			System.out.println("Upload file result" + res);
		}

		System.out.println("Response Code: " + connection.getResponseCode()); //Get Response code

		BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream())); //Create reader to read response context

		StringBuffer result = new StringBuffer(); //Create string buffer
		String line; //Init line var
		while ((line = reader.readLine()) != null) { //Loop through reader and append lines to result
			result.append(line);
		}
		System.out.println(result); //Print result
		reader.close(); //Close reader
		connection.disconnect(); //Close fedora connection
	}

	public static void fedoraAPIDelete (String deletePath) throws UnsupportedEncodingException, MalformedURLException, IOException {
		URL url = generateURL(deletePath, null);

		HttpURLConnection connection = (HttpURLConnection) url.openConnection(); //Open a new connection to Fedora

		connection.setRequestMethod("DELETE"); //DELETE specified Path

		int responseCode = connection.getResponseCode(); //Get response code of the DELETE (204 is good)

		System.out.println("Response Code of Delete Path: " + responseCode);

		if (responseCode == 204) {
			connection.disconnect(); //Close fedora connection

			connection = (HttpURLConnection) url.openConnection(); //Open a new connection to Fedora

			connection.setRequestMethod("GET"); //GET specified Path this is to double check that the content was deleted

			responseCode = connection.getResponseCode(); //Get response code of the DELETE (410 is good)

			System.out.println("Response Code of Get Tombstone: " + responseCode);

			if (responseCode == 410) {
				connection.disconnect(); //Close fedora connection

				url = generateURL(deletePath, "/fcr:tombstone"); //Generate new URL to delete tombstone

				connection = (HttpURLConnection) url.openConnection(); //Open a new connection to Fedora

				connection.setRequestMethod("DELETE"); //DELETE specified Path tombstone

				responseCode = connection.getResponseCode(); //Get response code of the DELETE (204 is good)

				System.out.println("Response Code of Delete Tombstone: " + responseCode);

				connection.disconnect(); //Close fedora connection
			}
		}
	}
}
