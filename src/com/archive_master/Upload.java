package com.archive_master;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Upload class to handle methods that need to work with Uploading
 *
 * @author benbr
 */

public class Upload {

	/**
	 * stream2File - ccnverts an InputStream to a temp file
	 * @param inputStream - An InputStream of a file
	 * @param PREFIX - File Name
	 * @param SUFFIX - File extension
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
}
