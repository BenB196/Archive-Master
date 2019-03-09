package com.archivemaster.fedora;

import com.archivemaster.validation.Validation;
import org.apache.commons.io.IOUtils;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;

public class FedoraFile {
	private static String NULL_F_NAME = "File Name cannot be NULL or Empty";

	private static String INVALID_F_NAME = "File Name cannot contain a /";

	private static String DIR_SELECTED = "File must be a file, not a directory";

	private byte[] byteArray;

	private String 	fileName,
					title,
					creator,
					subject,
					description,
					publisher,
					contributor,
					sDate,
					type,
					format,
					identifier,
					source,
					language,
					//Relation?
					coverage,
					rights,
					sha1,
					sha256,
					collectionName;
	private Date	date;

	public FedoraFile () {

	}

	public FedoraFile (byte[] byteArray, String fileName, String title, String creator, String subject, String description, String publisher, String contributor, Date date, String type, String format, String identifier, String source, String language, String coverage, String rights, String sha1, String sha256, String collectionName) {
		this.byteArray = byteArray;
		this.fileName = fileName;
		this.title = title;
		this.creator = creator;
		this.subject = subject;
		this.description = description;
		this.publisher = publisher;
		this.contributor = contributor;
		this.date = date;
		this.type = type;
		this.format = format;
		this.identifier = identifier;
		this.source = source;
		this.language = language;
		this.coverage = coverage;
		this.rights = rights;
		this.sha1 = sha1;
		this.sha256 = sha256;
		this.collectionName = collectionName;
	}

	public FedoraFile (byte[] byteArray, String fileName, String title, String creator, String subject, String description, String publisher, String contributor, String sDate, String type, String format, String identifier, String source, String language, String coverage, String rights, String sha1, String sha256, String collectionName) {
		this.byteArray = byteArray;
		this.fileName = fileName;
		this.title = title;
		this.creator = creator;
		this.subject = subject;
		this.description = description;
		this.publisher = publisher;
		this.contributor = contributor;
		this.sDate = sDate;
		this.type = type;
		this.format = format;
		this.identifier = identifier;
		this.source = source;
		this.language = language;
		this.coverage = coverage;
		this.rights = rights;
		this.sha1 = sha1;
		this.sha256 = sha256;
		this.collectionName = collectionName;
	}

	public byte[] getByteArray() {
		return byteArray;
	}

	public void setByteArray(byte[] byteArray) {
		this.byteArray = byteArray;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getContributor() {
		return contributor;
	}

	public void setContributor(String contributor) {
		this.contributor = contributor;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getCoverage() {
		return coverage;
	}

	public void setCoverage(String coverage) {
		this.coverage = coverage;
	}

	public String getRights() {
		return rights;
	}

	public void setRights(String rights) {
		this.rights = rights;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getsDate() {
		return sDate;
	}

	public void setsDate(String sDate) {
		this.sDate = sDate;
	}

	public String getSha1() {
		return sha1;
	}

	public void setSha1(String sha1) {
		this.sha1 = sha1;
	}

	public String getSha256() {
		return sha256;
	}

	public void setSha256(String sha256) {
		this.sha256 = sha256;
	}

	public String getCollectionName() {
		return collectionName;
	}

	public void setCollectionName(String collectionName) {
		this.collectionName = collectionName;
	}

	public static String getTitleFromFileName (String fileName) {
		int p = fileName.lastIndexOf(".");
		return (p == -1) ? fileName : fileName.substring(0, p);
	}

	public static String getFileTypeFromFileName (String fileName) {
		int p = fileName.lastIndexOf(".");
		String extension = fileName.substring(p + 1);
		return (p == -1 || !extension.matches("\\w+")) ? null : extension;
	}

	public static String generateSHAHash (InputStream inputStream, int shaHash) throws IllegalArgumentException {
		if (shaHash != 1 && shaHash != 256) {
			throw new IllegalArgumentException("shaHash must either be 1 or 256");
		}

		try {
			MessageDigest sha = MessageDigest.getInstance("SHA-" + shaHash);
			try {
				byte[] buffer = new byte[8192];
				int len = inputStream.read(buffer);

				while (len != -1) {
					sha.update(buffer, 0, len);
					len = inputStream.read(buffer);
				}

				return new HexBinaryAdapter().marshal(sha.digest());
			} catch (IOException ex) {
				System.out.println(ex.getMessage()); //TODO throw some sort of error message back and handle cleanly.
			}
		} catch (NoSuchAlgorithmException ex) {
			System.out.println(ex.getMessage()); //TODO throw some sort of error message back and handle cleanly.
		}
		return null;
	}

	public static Validation validateSDate (String sDate) {
		return null; //TODO make sure sDate matches correct format
	}

	public static Validation validateFedoraFile (FedoraFile file) {
		return null; //TODO validate FedoraFile
	}

	//TODO I do not like how this only returns a boolean, it should have a better response
	public static boolean createFedoraFile (FedoraFile file, byte[] byteArray) {
		try {
			URL url = new URL(Fedora.RESTURL + URLEncoder.encode(file.getCollectionName(), "UTF-8") + "/" + URLEncoder.encode(file.getFileName(), "UTF-8"));

			//Create file
			try {
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();

				connection.setDoOutput(true);
				connection.setRequestMethod("PUT");

				System.out.println(file.getFormat());
				connection.setRequestProperty("Content-Type", file.getFormat());
				System.out.println(file.getFileName());
				connection.setRequestProperty("Content-Disposition", "attachment; filename=\"" + file.getFileName() + "\"");
				System.out.println(file.getSha1());
				connection.setRequestProperty("digest", "sha=" + file.getSha1());
				System.out.println(file.getSha256());
				//connection.setRequestProperty("digest", "sha-256=" + file.getSha256());

				//InputStream inputStream = new ByteArrayInputStream(byteArray);
				IOUtils.copy(new ByteArrayInputStream(byteArray), connection.getOutputStream());
				String result = IOUtils.toString(connection.getInputStream());
				System.out.println("Upload File Result: " + result);

				connection.disconnect();

				//TODO Handle add metadata
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
	public static boolean deleteFile (String collectionName, String fileName) {
		try {
			URL url = new URL(Fedora.RESTURL + URLEncoder.encode(collectionName, "UTF-8") + "/" + URLEncoder.encode(fileName, "UTF-8"));

			//Delete File
			try {
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();

				connection.setRequestMethod("DELETE");

				boolean deleteFileResponse = Fedora.apiResponseCheck(connection.getResponseCode());

				connection.disconnect();

				//Delete File tombstone
				if (deleteFileResponse) {
					url = new URL(Fedora.RESTURL + URLEncoder.encode(collectionName, "UTF-8") + "/" + URLEncoder.encode(fileName, "UTF-8") + Fedora.TOMBSTONEURL);

					connection = (HttpURLConnection) url.openConnection();

					connection.setRequestMethod("DELETE");

					boolean deleteFileTombstoneResponse = Fedora.apiResponseCheck(connection.getResponseCode());

					connection.disconnect();

					if (deleteFileTombstoneResponse) {
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
	public static boolean editFile (FedoraFile file) {
		return false; //TODO edit file
	}

	public static String getMetadataValue (String collectionName, String fileName, String metadataName) {
		return null; //TODO this
	}

	public static ArrayList<FedoraFile> getFiles (String collectionName) {
		return null; //TODO this
	}
}
