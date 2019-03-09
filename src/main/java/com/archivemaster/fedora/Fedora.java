package com.archivemaster.fedora;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Fedora {
	public static String BASEURL = "http://localhost:8080/";

	public static String RESTURL = BASEURL + "rest/";

	public static String TOMBSTONEURL = "/fcr:tombstone";

	public static String METADATAURL = "/fcr:metadata";

	public static int getFedoraAPIStatus () {
		try {
			URL url = new URL(Fedora.RESTURL);

			try {
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				connection.setRequestMethod("GET");
				connection.disconnect();
				return connection.getResponseCode();
			} catch (IOException ex) {
				System.out.println(ex.getMessage()); //TODO throw some sort of error message back and handle cleanly.
			}

		} catch (MalformedURLException ex) {
			System.out.println(ex.getMessage()); //TODO throw some sort of error message back and handle cleanly.
		}
		return 0;
	}
}
