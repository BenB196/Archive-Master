package com.archivemaster.fedora;

import com.archivemaster.utils.HTTPAdditionalUtils;
import com.archivemaster.validation.Validation;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static com.archivemaster.utils.ArchiveMasterUtils.isEmptyString;

public class Collection {

	private static int MAX_C_NAME_LEN = 50;

	private static int MAX_C_DESC_LEN = 500;

	private static String NULL_C_NAME = "Collection Name cannot be NULL or Empty";

	private static String INVALID_C_NAME = "Collection Name cannot contain a /";

	private static String C_NAME_LONG = "Collection Name is greater than " + MAX_C_NAME_LEN;

	private static String NULL_C_DESC = "Collection Description cannot be NULL or Empty";

	private static String C_DESC_LONG = "Collection Description is greater than " + MAX_C_DESC_LEN;

	private String name;

	private String description;

	public Collection () {

	}

	public Collection (String name, String description) {
		this.name = name;
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public static Validation validateCollectionName (String collectionName) {
		if (isEmptyString(collectionName)) {
			return new Validation(false, null, NULL_C_NAME);
		} else if (collectionName.contains("/")) {
			return new Validation(false, null, INVALID_C_NAME);
		} else if (collectionName.length() > MAX_C_NAME_LEN) {
			return new Validation(false, null, C_NAME_LONG);
		}
		return new Validation(true, collectionName, null);
	}

	public static Validation validateCollectionDescription (String collectionDescription) {
		if (isEmptyString(collectionDescription)) {
			return new Validation(false, null, NULL_C_DESC);
		} else if (collectionDescription.length() > MAX_C_DESC_LEN) {
			return new Validation(false, null, C_DESC_LONG);
		}
		return new Validation(true, collectionDescription, null);
	}

	public static Validation validateCollection (Collection collection) {
		Validation collectionNameValidation = validateCollectionName(collection.name);

		if (!collectionNameValidation.isValid()) {
			return collectionNameValidation;
		}

		Validation collectionDescriptionValidation = validateCollectionDescription(collection.description);

		if (!collectionDescriptionValidation.isValid()) {
			return collectionDescriptionValidation;
		}

		//TODO should also pass through collection array to see if collection already exists

		return new Validation(true, collection, null);
	}

	//TODO I do not like how this only returns a boolean, it should have a better response
	public static boolean creationCollection (Collection collection) {
		try {
			URL url = new URL(Fedora.RESTURL + URLEncoder.encode(collection.name, "UTF-8"));

			//Create Collection
			try {
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();

				connection.setDoOutput(true);
				connection.setRequestMethod("PUT");

				boolean addCollectionResponse = Fedora.apiResponseCheck(connection.getResponseCode());

				connection.disconnect();
				//Check response of collection add before continuing
				if (addCollectionResponse) {
					//Add collection description
					//url = new URL(Fedora.RESTURL + URLEncoder.encode(collection.name, "UTF-8") + "/fcr:metadata");

					HTTPAdditionalUtils.allowMethods("PATCH");

					connection = (HttpURLConnection) url.openConnection();

					connection.setDoOutput(true);
					connection.setRequestMethod("PATCH");

					connection.setRequestProperty("Content-Type", Metadata.META_DATA_CONTENT_TYPE);

					String collectionDescriptionAPIString = Metadata.metadataAPIStringBuilder("description",collection.description, Metadata.INSERT);
					InputStream is = new ByteArrayInputStream(collectionDescriptionAPIString.getBytes(StandardCharsets.UTF_8));
					IOUtils.copy(is, connection.getOutputStream());

					boolean addCollectionDescriptionResponse = Fedora.apiResponseCheck(connection.getResponseCode());

					connection.disconnect();

					if (addCollectionDescriptionResponse) {
						return true;
					} else {
						System.out.println(connection.getResponseMessage());
						return false;
					}
				} else {
					System.out.println(connection.getResponseMessage());
					return false;
				}
			} catch (IOException ex) {
				System.out.println(ex.getMessage()); //TODO throw some sort of error message back and handle cleanly.
			}

		} catch (UnsupportedEncodingException ex) {
			System.out.println(ex.getMessage()); //TODO throw some sort of error message back and handle cleanly.
		} catch (MalformedURLException ex) {
			System.out.println(ex.getMessage()); //TODO throw some sort of error message back and handle cleanly.
		}

		return false;
	}

	//TODO I do not like how this only returns a boolean, it should have a better response
	public static boolean deleteCollection (String collectionName) {
		try {
			URL url = new URL(Fedora.RESTURL + URLEncoder.encode(collectionName, "UTF-8"));

			//Delete Collection
			try {
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();

				connection.setRequestMethod("DELETE");

				boolean deleteCollectionResponse = Fedora.apiResponseCheck(connection.getResponseCode());

				connection.disconnect();
				//Delete Collection tombstone
				if (deleteCollectionResponse) {
					url = new URL(Fedora.RESTURL + URLEncoder.encode(collectionName, "UTF-8") + Fedora.TOMBSTONEURL);
					connection = (HttpURLConnection) url.openConnection();

					connection.setRequestMethod("DELETE");

					boolean deleteCollectionTombStoneResponse = Fedora.apiResponseCheck(connection.getResponseCode());

					connection.disconnect();

					if (deleteCollectionTombStoneResponse) {
						return true;
					} else {
						System.out.println(connection.getResponseMessage());
						return false;
					}
				} else {
					System.out.println(connection.getResponseMessage());
					return false;
				}

			} catch (IOException ex) {
				System.out.println(ex.getMessage()); //TODO throw some sort of error message back and handle cleanly.
			}

		} catch (UnsupportedEncodingException ex) {
			System.out.println(ex.getMessage()); //TODO throw some sort of error message back and handle cleanly.
		} catch (MalformedURLException ex) {
			System.out.println(ex.getMessage()); //TODO throw some sort of error message back and handle cleanly.
		}

		return false;
	}

	public static boolean editCollection (Collection currentCollection, Collection newCollection) {
		return false;
	}
}
