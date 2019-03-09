package com.archivemaster.fedora;

import com.archivemaster.utils.HTTPAdditionalUtils;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;

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
	public static boolean addMetadata (String metadataName, String metadataValue, URL url) {
		try {
			HTTPAdditionalUtils.allowMethods("PATCH");

			HttpURLConnection connection = (HttpURLConnection) url.openConnection();

			connection.setDoOutput(true);
			connection.setRequestMethod("PATCH");

			connection.setRequestProperty("Content-Type", Metadata.META_DATA_CONTENT_TYPE);

			String metadataAPIString = metadataAPIStringBuilder(metadataName, metadataValue, Metadata.INSERT);
			InputStream inputStream = new ByteArrayInputStream(metadataAPIString.getBytes(StandardCharsets.UTF_8));
			IOUtils.copy(inputStream, connection.getOutputStream());

			boolean addMetadataResponse = Fedora.apiResponseCheck(connection.getResponseCode());

			connection.disconnect();

			if (addMetadataResponse) {
				return true;
			} else {
				System.out.println(connection.getResponseMessage());
				return false;
			}

		} catch (IOException ex) {
			System.out.println(ex.getMessage()); //TODO throw some sort of error message back and handle cleanly.
		}
		return false;
	}

	public static boolean deleteMetadata (String metadataName, String metadataValue, URL url) {
		return false;
	}

	public static boolean editMetadataValue (String metadataName, String currentMetadataValue, String newMetadataValue, URL url) {
		return false;
	}
}
