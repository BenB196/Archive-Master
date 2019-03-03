package com.archivemaster;

import org.apache.commons.io.IOUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	 * fedoraAPICreate - used to create collections and upload files
	 * @param whatToCreate
	 * @param collectionName
	 * @param fileName
	 * @param contentType
	 * @param contentDisposition
	 * @param file
	 * @param sha1
	 * @param sha256
	 * @throws UnsupportedEncodingException
	 * @throws MalformedURLException
	 * @throws IllegalArgumentException
	 * @throws IOException
	 */
	public static void fedoraAPICreate (String whatToCreate, String collectionName, String fileName, String contentType, String contentDisposition, File file, String sha1, String sha256) throws UnsupportedEncodingException, MalformedURLException, IllegalArgumentException, IOException {
		URL url = null;

		if (collectionName == null || collectionName.isEmpty()) {
			throw new IllegalArgumentException("Collection Name cannot be null");
		} else if (collectionName.contains("/")) { //Check if collection name contains a /
			throw new IllegalArgumentException("Collection Name cannot contain a /");
		}

		//TODO add metadata support
		if (whatToCreate == null || whatToCreate.isEmpty() || whatToCreate.equalsIgnoreCase("collection")) {
			ArrayList collections = fedoraAPIGetArray("collection", null);
			if (collections.size() > 0 && collections.contains(collectionName)) {
				throw new IllegalArgumentException("The specified collection already exists: " + collectionName); //Check if collection already exists
			} else {
				url = generateURL(collectionName, null);
			}
		} else if (whatToCreate.equalsIgnoreCase("file")) {
			if (fileName == null || fileName.isEmpty()) {
				throw new IllegalArgumentException("File Name cannot be null");
			} else if (fileName.contains("/")) { //Check if file name contains a /
				throw new IllegalArgumentException("File Name cannot contain a /");
			} else {
				ArrayList collections = fedoraAPIGetArray("collection", null);

				if (collections.size() > 0 && !collections.contains(collectionName)) {
					throw new IllegalArgumentException("The specified collection does not exist: " + collectionName); //Check if collection exists to put file in
				} else {
					ArrayList files = fedoraAPIGetArray("file", collectionName); //Check if file already exists in the specified collecion
					if (files.size() > 0 && files.contains(fileName)) {
						throw new IllegalArgumentException("The specified file name already exists within the specified collection.");
					} else {
						url = generateURL(collectionName + "/", fileName);
					}
				}
			}
		}

		HttpURLConnection connection = (HttpURLConnection) url.openConnection(); //Open a new connection to Fedora

		connection.setDoOutput(true);
		connection.setRequestMethod("PUT"); //Set request method

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

		connection.disconnect(); //Close fedora connection
	}

	public static int fedoraAPICheck () throws MalformedURLException, IOException {
		URL url = generateURL("/", null);

		HttpURLConnection connection = (HttpURLConnection) url.openConnection(); //Open a new connection to Fedora
		connection.setRequestMethod("GET"); //Set request method

		connection.disconnect(); //Close fedora connection

		return connection.getResponseCode();
	}

	/**
	 * fedoraAPIGetArray - gets results of search and returns the results as an ArrayList
	 * @param whatToGet - string; either collection or file
	 * @param collectionName - string; if whatToGet equals file, a collection must be set
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws IllegalArgumentException
	 */
	public static ArrayList fedoraAPIGetArray (String whatToGet, String collectionName) throws UnsupportedEncodingException, MalformedURLException, IOException, IllegalArgumentException {
		URL url = null;

		//TODO Validate that collectionName does not contain a /

		if (whatToGet == null || whatToGet.isEmpty() || whatToGet.equalsIgnoreCase("collection")) { //Get collection array
			url = generateURL("/", null);
		} else if (whatToGet.equalsIgnoreCase("file")) {
			if (collectionName == null || collectionName.isEmpty()) {
				throw new IllegalArgumentException("Collection Not Specified"); //Get file array for specified collection
			} else {
				url = generateURL(collectionName, null);
			}
		} else {
			throw new IllegalArgumentException("whatToGet must either equal collection or file");
		}


		HttpURLConnection connection = (HttpURLConnection) url.openConnection();

		connection.setRequestMethod("GET");

		ArrayList list = new ArrayList<String>();

		BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

		StringBuffer result = new StringBuffer();
		String line;

		Pattern p = Pattern.compile("<li><a href=\"" + BASEURL + ".+\">" + BASEURL + ".+</a></li>");
		Matcher m = p.matcher("");

		String pathRegex = Pattern.quote("<li><a href=\"")  + BASEURL + ".+\">" + BASEURL + "(.*?)" + Pattern.quote("</a></li>");
		Pattern pathPattern = Pattern.compile(pathRegex);
		while ((line = reader.readLine()) != null) {
			if (m.reset(line.trim()).matches()) {
				Matcher pathMatcher = pathPattern.matcher(line.trim());

				while (pathMatcher.find()) {
					String match = pathMatcher.group(1);
					if (whatToGet.equalsIgnoreCase("file")) {
						list.add(match.substring(match.lastIndexOf("/") + 1));
					} else {
						list.add(match);
					}
				}
			}
		}

		reader.close(); //Close reader
		connection.disconnect(); //Close fedora connection

		if (list.size() == 0) {
			return null;
		} else {
			Collections.sort(list);
			return list;
		}
	}

	/**
	 * fedoraAPIDelete - Used for deleting things
	 * @param deletePath
	 * @throws UnsupportedEncodingException
	 * @throws MalformedURLException
	 * @throws IOException
	 */
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
