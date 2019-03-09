package com.archivemaster.fedora;

import com.archivemaster.validation.Validation;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

public class FedoraFile {
	private static String NULL_F_NAME = "File Name cannot be NULL or Empty";

	private static String INVALID_F_NAME = "File Name cannot contain a /";

	private static String DIR_SELECTED = "File must be a file, not a directory";

	private InputStream inputStream;

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

	public FedoraFile (InputStream inputStream, String fileName, String title, String creator, String subject, String description, String publisher, String contributor, Date date, String type, String format, String identifier, String source, String language, String coverage, String rights, String sha1, String sha256, String collectionName) {
		this.inputStream = inputStream;
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

	public FedoraFile (InputStream inputStream, String fileName, String title, String creator, String subject, String description, String publisher, String contributor, String sDate, String type, String format, String identifier, String source, String language, String coverage, String rights, String sha1, String sha256, String collectionName) {
		this.inputStream = inputStream;
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

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
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
	public static boolean createFedoraFile (FedoraFile file) {
		return false; //TODO create file
	}

	//TODO I do not like how this only returns a boolean, it should have a better response
	public static boolean deleteFile (String fileName) {
		return false; //TODO delete file
	}

	//TODO I do not like how this only returns a boolean, it should have a better response
	public static boolean editFile (FedoraFile file) {
		return false; //TODO edit file
	}

	public static
}
