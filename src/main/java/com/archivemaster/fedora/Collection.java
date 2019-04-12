package com.archivemaster.fedora;

import com.archivemaster.utils.HTTPAdditionalUtils;
import com.archivemaster.validation.HttpAPIStatus;
import com.archivemaster.validation.Validation;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
		Validation collectionNameValidation = validateCollectionName(collection.getName());

		if (!collectionNameValidation.isValid()) {
			return collectionNameValidation;
		}

		Validation collectionDescriptionValidation = validateCollectionDescription(collection.getDescription());

		if (!collectionDescriptionValidation.isValid()) {
			return collectionDescriptionValidation;
		}

		//TODO should also pass through collection array to see if collection already exists

		return new Validation(true, collection, null);
	}

	public static HttpAPIStatus creationCollection (Collection collection) {
		try {
			URL url = new URL(Fedora.RESTURL + URLEncoder.encode(collection.getName(), "UTF-8"));

			//Create Collection
			try {
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();

				connection.setDoOutput(true);
				connection.setRequestMethod("PUT");

				HttpAPIStatus addCollectionStatus = new HttpAPIStatus(HttpAPIStatus.getSuccessFromCode(connection.getResponseCode()),connection.getResponseCode(),connection.getResponseMessage());

				connection.disconnect();
				//Check response of collection add before continuing
				if (addCollectionStatus.isSuccess()) {
					//Add collection description
					return Metadata.addMetadata("description", collection.getDescription(), url);
				} else {
					addCollectionStatus.setResponseMessage("Failed to Add Collection");
					return addCollectionStatus;
				}
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

	public static HttpAPIStatus deleteCollection (String collectionName) {
		try {
			URL url = new URL(Fedora.RESTURL + URLEncoder.encode(collectionName, "UTF-8"));

			//Delete Collection
			try {
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();

				connection.setRequestMethod("DELETE");

				HttpAPIStatus deleteCollectionStatus = new HttpAPIStatus(HttpAPIStatus.getSuccessFromCode(connection.getResponseCode()),connection.getResponseCode(),connection.getResponseMessage());

				connection.disconnect();
				//Delete Collection tombstone
				if (deleteCollectionStatus.isSuccess()) {
					url = new URL(Fedora.RESTURL + URLEncoder.encode(collectionName, "UTF-8") + Fedora.TOMBSTONEURL);
					connection = (HttpURLConnection) url.openConnection();

					connection.setRequestMethod("DELETE");

					HttpAPIStatus deleteCollectionTombStoneStatus = new HttpAPIStatus(HttpAPIStatus.getSuccessFromCode(connection.getResponseCode()),connection.getResponseCode(),connection.getResponseMessage());

					connection.disconnect();

					if (deleteCollectionTombStoneStatus.isSuccess()) {
						deleteCollectionTombStoneStatus.setResponseMessage("Deleted Collection");
					} else {
						deleteCollectionTombStoneStatus.setResponseMessage("Failed to Delete Collection");
					}

					return deleteCollectionTombStoneStatus;
				} else {
					deleteCollectionStatus.setResponseMessage("Failed to Delete Collection");
					return deleteCollectionStatus;
				}

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

	//TODO I do not like how this only returns a boolean, it should have a better response
	public static boolean editCollection (Collection currentCollection, Collection newCollection) {
		return false; //TODO edit collection
	}

	public static ArrayList<Collection> getCollections () {
		try {
			URL url = new URL(Fedora.RESTURL);

			try {
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				connection.setRequestMethod("GET");
				ArrayList<Collection> collections = new ArrayList<>();
				BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String line;

				Pattern p = Pattern.compile("<li><a href=\"" + Fedora.RESTURL + ".+\">" + Fedora.RESTURL + ".+</a></li>");
				Matcher m = p.matcher("");

				String pathRegex = Pattern.quote("<li><a href=\"")  + Fedora.RESTURL + ".+\">" + Fedora.RESTURL + "(.*?)" + Pattern.quote("</a></li>");
				Pattern pathPattern = Pattern.compile(pathRegex);

				while ((line = reader.readLine()) != null) {
					if (m.reset(line.trim()).matches()) {
						Matcher pathMatcher = pathPattern.matcher(line.trim());
						while (pathMatcher.find()) {
							String collectionName = URLDecoder.decode(pathMatcher.group(1), StandardCharsets.UTF_8.name());
							String collectionDescription = Metadata.getMetadataValue(collectionName, null, "description");
							collections.add(new Collection(collectionName, collectionDescription));
						}
					}
				}

				reader.close();
				connection.disconnect();

				Collections.sort(collections, compareByCollectionName);
				return collections;

				//System.out.println(connectionResponseMessage);
			} catch (IOException ex) {
				System.out.println(ex.getMessage()); //TODO throw some sort of error message back and handle cleanly.
			}

		} catch (MalformedURLException ex) {
			System.out.println(ex.getMessage()); //TODO throw some sort of error message back and handle cleanly.
		}
		return null;
	}

	public static Collection getCollectionByName (String collectionName) {
		String description = Metadata.getMetadataValue(collectionName, null, "description");

		return new Collection(collectionName, description);
	}

	private static Comparator<Collection> compareByCollectionName = new Comparator<Collection>() {
		@Override
		public int compare(Collection o1, Collection o2) {
			return o1.getName().compareTo(o2.getName());
		}
	};
}
