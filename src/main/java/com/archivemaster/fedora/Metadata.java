package com.archivemaster.fedora;

import com.archivemaster.utils.HTTPAdditionalUtils;
import com.archivemaster.validation.HttpAPIStatus;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.archivemaster.utils.ArchiveMasterUtils.isEmptyString;

public class Metadata {

	public static String META_DATA_CONTENT_TYPE = "application/sparql-update";

	public static String INSERT = "INSERT";

	public static String DELETE = "DELETE";


	public static String metadataAPIStringBuilder (String metadataName, String metadataValue, String insertOrDelete) {
		return "PREFIX ebucore: <http://www.ebu.ch/metadata/ontologies/ebucore/ebucore#> " + insertOrDelete + " { <> ebucore:" + metadataName + " \"" + metadataValue + "\" } WHERE { }";
	}

	public static String metadataSearchStringBuilder (String metadataName) {
		return Pattern.quote("<span property=\"http://www.ebu.ch/metadata/ontologies/ebucore/ebucore#") + metadataName + "\">" + "(.*?)" + Pattern.quote("</span>");
	}

	//TODO I do not like how this only returns a boolean, it should have a better response
	public static HttpAPIStatus addMetadata (String metadataName, String metadataValue, URL url) {
		try {
			HTTPAdditionalUtils.allowMethods("PATCH");

			HttpURLConnection connection = (HttpURLConnection) url.openConnection();

			connection.setDoOutput(true);
			connection.setRequestMethod("PATCH");

			connection.setRequestProperty("Content-Type", Metadata.META_DATA_CONTENT_TYPE);

			String metadataAPIString = metadataAPIStringBuilder(metadataName, metadataValue, Metadata.INSERT);
			InputStream inputStream = new ByteArrayInputStream(metadataAPIString.getBytes(StandardCharsets.UTF_8));
			IOUtils.copy(inputStream, connection.getOutputStream());

			HttpAPIStatus addMetadataStatus = new HttpAPIStatus(HttpAPIStatus.getSuccessFromCode(connection.getResponseCode()),connection.getResponseCode(),connection.getResponseMessage());

			connection.disconnect();

			return addMetadataStatus;

		} catch (IOException ex) {
			System.out.println(ex.getMessage()); //TODO throw some sort of error message back and handle cleanly.
		}
		return null;
	}

	public static boolean deleteMetadata (String metadataName, String metadataValue, URL url) {
		return false;
	}

	public static boolean editMetadataValue (String metadataName, String currentMetadataValue, String newMetadataValue, URL url) {
		return false;
	}

	public static String getMetadataValue (String collectionName, String fileName, String metadataName) {
		try {
			URL url = (isEmptyString(fileName)) ? new URL(Fedora.RESTURL + URLEncoder.encode(collectionName, "UTF-8")) : new URL(Fedora.RESTURL + URLEncoder.encode(collectionName, "UTF-8") + "/" + URLEncoder.encode(fileName, StandardCharsets.UTF_8.name()) + Fedora.METADATAURL);

			try {
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				connection.setRequestMethod("GET");

				BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String line;

				Pattern p = Pattern.compile(metadataSearchStringBuilder(metadataName));
				Matcher m = p.matcher("");
				while ((line = reader.readLine()) != null) {
					if (m.reset(line.trim()).matches()) {
						return m.group(1);
					}
				}

				reader.close();
				connection.disconnect();
			} catch (IOException ex) {
				System.out.println(ex.getMessage()); //TODO throw some sort of error message back and handle cleanly.
			}
		} catch (UnsupportedEncodingException ex) {
			System.out.println(ex.getMessage()); //TODO throw some sort of error message back and handle cleanly.
		} catch (MalformedURLException ex) {
			System.out.println(ex.getMessage()); //TODO throw some sort of error message back and handle cleanly.
		}
		return null;
	}
}
