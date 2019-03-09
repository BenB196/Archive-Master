package com.archivemaster;

import org.apache.commons.io.IOUtils;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Upload class to handle methods that need to work with Uploading
 *
 * @author benbr
 */

public class Upload {

	/**
	 * stream2File - ccnverts an InputStream to a temp file
	 * @param inputStream - An InputStream of a file
	 * @param PREFIX - FedoraFile Name
	 * @param SUFFIX - FedoraFile extension
	 * @return - returns temp file
	 * @throws IOException - Throws IO Exception
	 */
	public static File stream2File (InputStream inputStream, String PREFIX, String SUFFIX) throws IOException {
		final File tempFile = File.createTempFile(PREFIX, SUFFIX);
		tempFile.deleteOnExit();
		try (FileOutputStream outputStream = new FileOutputStream(tempFile)) {
			IOUtils.copy(inputStream, outputStream);
		}
		return tempFile;
	}

	/**
	 * fileExtension - gets the file extension based off of file name
	 * @param fileName - fileName
	 * @return - Returns extension
	 */
	public static String fileExtension (String fileName) {
		int p = fileName.lastIndexOf(".");
		String extension = fileName.substring(p + 1);
		if (p == -1 || !extension.matches("\\w+")) {
			return null;
		} else {
			return extension;
		}
	}

	/**
	 * fileNameOnly - gets the file name only, excluding the extension
	 * @param fileName - fileName
	 * @return - returns the fileName without and extension
	 */
	public static String fileNameOnly (String fileName) {
		int p = fileName.lastIndexOf(".");
		if (p == -1 ) {
			return fileName;
		} else {
			return fileName.substring(0, p);
		}
	}

	/**
	 * fileSHA1 - generates sha 1 hash for a given file
	 * @param file
	 * @param shaHash - either 1 or 256
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 */
	public static String fileSHA1 (File file, int shaHash) throws FileNotFoundException, IOException, NoSuchAlgorithmException, IllegalArgumentException {
		if (shaHash != 1 && shaHash != 256) {
			throw new IllegalArgumentException("shaHash must either be 1 or 256");
		}
		MessageDigest sha1 = MessageDigest.getInstance("SHA-" + shaHash);
		try (InputStream input = new FileInputStream(file)) {
			byte[] buffer = new byte[8192];
			int len = input.read(buffer);

			while (len != -1) {
				sha1.update(buffer, 0, len);
				len = input.read(buffer);
			}

			return new HexBinaryAdapter().marshal(sha1.digest());
		}
	}
}
