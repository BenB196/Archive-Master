package com.archive_master;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Upload class to handle methods used by UploadServlet
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

}
