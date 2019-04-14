package com.archivemaster.fedora;

import com.archivemaster.utils.HTTPAdditionalUtils;
import com.archivemaster.validation.HttpAPIStatus;
import com.archivemaster.validation.Validation;
import org.apache.commons.io.IOUtils;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
		System.out.println(fileName.substring(0, p));
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

	public static HttpAPIStatus createFedoraFile (FedoraFile file) {
		try {
			URL url = new URL(Fedora.RESTURL + URLEncoder.encode(file.getCollectionName(), "UTF-8") + "/" + URLEncoder.encode(file.getFileName(), "UTF-8"));

			//Create file
			try {
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();

				connection.setDoOutput(true);
				connection.setRequestMethod("PUT");

				connection.setRequestProperty("Content-Type", file.getFormat());
				connection.setRequestProperty("Content-Disposition", "attachment; filename=\"" + file.getFileName() + "\"");
				connection.setRequestProperty("digest", "sha=" + file.getSha1());
				connection.setRequestProperty("digest", "sha-256=" + file.getSha256());

				IOUtils.copy(new ByteArrayInputStream(file.getByteArray()), connection.getOutputStream());

				HttpAPIStatus addFileStatus = new HttpAPIStatus(HttpAPIStatus.getSuccessFromCode(connection.getResponseCode()),connection.getResponseCode(),connection.getResponseMessage());

				connection.disconnect();

				//Check response of file add before continuing
				if (addFileStatus.isSuccess()) {
					url = new URL(Fedora.RESTURL + URLEncoder.encode(file.getCollectionName(), StandardCharsets.UTF_8.name()) + "/" + URLEncoder.encode(file.getFileName(), StandardCharsets.UTF_8.name()) + Fedora.METADATAURL);
					System.out.println(url.toString());
					HttpAPIStatus addTitleStatus = Metadata.addMetadata("title", file.getTitle(), url);
					if (!addTitleStatus.isSuccess()) return addTitleStatus;
					HttpAPIStatus addCreatorStatus = Metadata.addMetadata("creator", file.getCreator(), url);
					if (!addCreatorStatus.isSuccess()) return addCreatorStatus;
					HttpAPIStatus addSubjectStatus = Metadata.addMetadata("subject", file.getSubject(), url);
					if (!addSubjectStatus.isSuccess()) return addSubjectStatus;
					HttpAPIStatus addDescriptionStatus = Metadata.addMetadata("description", file.getDescription(), url);
					if (!addDescriptionStatus.isSuccess()) return addDescriptionStatus;
					HttpAPIStatus addPublisherStatus = Metadata.addMetadata("publisher", file.getPublisher(), url);
					if (!addPublisherStatus.isSuccess()) return addPublisherStatus;
					HttpAPIStatus addContributorStatus = Metadata.addMetadata("contributor", file.getContributor(), url);
					if (!addContributorStatus.isSuccess()) return addContributorStatus;
					HttpAPIStatus addSDateStatus = Metadata.addMetadata("sDate", file.getsDate(), url);
					if (!addSDateStatus.isSuccess()) return addSDateStatus;
					HttpAPIStatus addTypeStatus = Metadata.addMetadata("type", file.getType(), url);
					if (!addTypeStatus.isSuccess()) return addTypeStatus;
					HttpAPIStatus addFormatStatus = Metadata.addMetadata("format", file.getFormat(), url);
					if (!addFormatStatus.isSuccess()) return addFormatStatus;
					HttpAPIStatus addIdentifier = Metadata.addMetadata("identifier", file.getIdentifier(), url);
					if (!addIdentifier.isSuccess()) return addIdentifier;
					HttpAPIStatus addSourceStatus = Metadata.addMetadata("source", file.getSource(), url);
					if (!addSourceStatus.isSuccess()) return addSourceStatus;
					HttpAPIStatus addLanguageStatus = Metadata.addMetadata("language", file.getLanguage(), url);
					if (!addLanguageStatus.isSuccess()) return addLanguageStatus;
					HttpAPIStatus addCoverageStatus = Metadata.addMetadata("coverage", file.getCoverage(), url);
					if (!addCoverageStatus.isSuccess()) return addCoverageStatus;
					HttpAPIStatus addRightsStatus = Metadata.addMetadata("rights", file.getRights(), url);
					return addRightsStatus;
				} else {
					addFileStatus.setResponseMessage("Failed to Add File");
					return addFileStatus;
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
	public static HttpAPIStatus deleteFile (String collectionName, String fileName) {
		try {
			URL url = new URL(Fedora.RESTURL + URLEncoder.encode(collectionName, "UTF-8") + "/" + URLEncoder.encode(fileName, "UTF-8"));

			//Delete File
			try {
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				connection.setRequestMethod("DELETE");
				HttpAPIStatus deleteFileStatus = new HttpAPIStatus(HttpAPIStatus.getSuccessFromCode(connection.getResponseCode()),connection.getResponseCode(),connection.getResponseMessage());
				connection.disconnect();

				//Delete File tombstone
				if (deleteFileStatus.isSuccess()) {
					url = new URL(Fedora.RESTURL + URLEncoder.encode(collectionName, "UTF-8") + "/" + URLEncoder.encode(fileName, "UTF-8") + Fedora.TOMBSTONEURL);
					connection = (HttpURLConnection) url.openConnection();
					connection.setRequestMethod("DELETE");
					HttpAPIStatus deleteFileTomeStoneStatus = new HttpAPIStatus(HttpAPIStatus.getSuccessFromCode(connection.getResponseCode()),connection.getResponseCode(),connection.getResponseMessage());
					connection.disconnect();

					if (deleteFileTomeStoneStatus.isSuccess()) {
						deleteFileTomeStoneStatus.setResponseMessage("Deleted File");
					} else {
						deleteFileTomeStoneStatus.setResponseMessage("Failed to Delete File");
					}
					return deleteFileTomeStoneStatus;
				} else {
					deleteFileStatus.setResponseMessage("Failed to Delete File");
					return deleteFileStatus;
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
	public static HttpAPIStatus editFedoraFile (FedoraFile file) {
		HttpAPIStatus deleteFileStatus = deleteFile(file.collectionName, file.fileName);

		if (deleteFileStatus.isSuccess()) {
			HttpAPIStatus createFileStatus = createFedoraFile(file);
			if (createFileStatus.isSuccess()) {
				createFileStatus.setResponseMessage("Updated File");
			} else {
				createFileStatus.setResponseMessage("Failed to Update File");
			}
			return createFileStatus;
		} else {
			deleteFileStatus.setResponseMessage("Failed to Update File");
			return deleteFileStatus;
		}
	}

	public static ArrayList<FedoraFile> getFiles (String collectionName) {
		try {
			URL url = new URL(Fedora.RESTURL + URLEncoder.encode(collectionName, "UTF-8"));

			try {
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				connection.setRequestMethod("GET");
				ArrayList<FedoraFile> files = new ArrayList<>();
				BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String line;

				Pattern p = Pattern.compile("<li><a href=\"" + Fedora.RESTURL + collectionName + ".+\">" + Fedora.RESTURL + collectionName + ".+</a></li>");
				Matcher m = p.matcher("");

				String pathRegex = Pattern.quote("<li><a href=\"") + Fedora.RESTURL + collectionName + ".+\">" + Fedora.RESTURL + collectionName + "/" + "(.*?)" + Pattern.quote("</a></li>");
				System.out.println(pathRegex);
				Pattern pathPattern = Pattern.compile(pathRegex);
				while ((line = reader.readLine()) != null) {
					String trimmedLine = line.trim();
					if (m.reset(trimmedLine).matches()) {
						Matcher pathMatcher = pathPattern.matcher(trimmedLine);
						while (pathMatcher.find()) {
							String fileName = URLDecoder.decode(pathMatcher.group(1), StandardCharsets.UTF_8.name());
							FedoraFile file = new FedoraFile();
							file.setFileName(fileName);
							file.setTitle(Metadata.getMetadataValue(collectionName, fileName, "title"));
							file.setCreator(Metadata.getMetadataValue(collectionName, fileName, "creator"));
							file.setSubject(Metadata.getMetadataValue(collectionName, fileName, "subject"));
							file.setDescription(Metadata.getMetadataValue(collectionName, fileName, "description"));
							file.setPublisher(Metadata.getMetadataValue(collectionName, fileName, "publisher"));
							file.setContributor(Metadata.getMetadataValue(collectionName, fileName, "contributor"));
							file.setsDate(Metadata.getMetadataValue(collectionName, fileName, "sDate"));
							file.setType(Metadata.getMetadataValue(collectionName, fileName, "type"));
							file.setFormat(Metadata.getMetadataValue(collectionName, fileName, "format"));
							file.setIdentifier(Metadata.getMetadataValue(collectionName, fileName, "identifier"));
							file.setSource(Metadata.getMetadataValue(collectionName, fileName, "source"));
							file.setLanguage(Metadata.getMetadataValue(collectionName, fileName, "language"));
							file.setCoverage(Metadata.getMetadataValue(collectionName, fileName, "coverage"));
							file.setRights(Metadata.getMetadataValue(collectionName, fileName, "rights"));
							file.setCollectionName(collectionName);
							files.add(file);
							System.out.println(fileName);
							//TODO get metadata for file.
						}
					}
				}
				return files;
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

	public static FedoraFile getFedoraFile(String collectionName, String fileName) {
		try {
			URL url = new URL(Fedora.RESTURL + URLEncoder.encode(collectionName, "UTF-8"));

			try {
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				connection.setRequestMethod("GET");

				BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String line;

				Pattern p = Pattern.compile("<li><a href=\"" + Fedora.RESTURL + collectionName + ".+\">" + Fedora.RESTURL + collectionName + ".+</a></li>");
				Matcher m = p.matcher("");

				String pathRegex = Pattern.quote("<li><a href=\"") + Fedora.RESTURL + collectionName + ".+\">" + Fedora.RESTURL + collectionName + "/" + "(.*?)" + Pattern.quote("</a></li>");
				System.out.println(pathRegex);
				Pattern pathPattern = Pattern.compile(pathRegex);

				while ((line = reader.readLine()) != null) {
					String trimmedLine = line.trim();
					if (m.reset(trimmedLine).matches()) {
						Matcher pathMatcher = pathPattern.matcher(trimmedLine);
						while (pathMatcher.find()) {
							String fileNameAPI = URLDecoder.decode(pathMatcher.group(1), StandardCharsets.UTF_8.name());
							if (fileNameAPI.equalsIgnoreCase(fileName)) {
								FedoraFile file = new FedoraFile();
								file.setFileName(fileNameAPI);
								file.setTitle(Metadata.getMetadataValue(collectionName, fileName, "title"));
								file.setCreator(Metadata.getMetadataValue(collectionName, fileName, "creator"));
								file.setSubject(Metadata.getMetadataValue(collectionName, fileName, "subject"));
								file.setDescription(Metadata.getMetadataValue(collectionName, fileName, "description"));
								file.setPublisher(Metadata.getMetadataValue(collectionName, fileName, "publisher"));
								file.setContributor(Metadata.getMetadataValue(collectionName, fileName, "contributor"));
								file.setsDate(Metadata.getMetadataValue(collectionName, fileName, "sDate"));
								file.setType(Metadata.getMetadataValue(collectionName, fileName, "type"));
								file.setFormat(Metadata.getMetadataValue(collectionName, fileName, "format"));
								file.setIdentifier(Metadata.getMetadataValue(collectionName, fileName, "identifier"));
								file.setSource(Metadata.getMetadataValue(collectionName, fileName, "source"));
								file.setLanguage(Metadata.getMetadataValue(collectionName, fileName, "language"));
								file.setCoverage(Metadata.getMetadataValue(collectionName, fileName, "coverage"));
								file.setRights(Metadata.getMetadataValue(collectionName, fileName, "rights"));
								return file;
							}
						}
					}
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
}
