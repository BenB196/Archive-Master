package com.archivemaster.servlets;

import com.archivemaster.Upload;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;

@WebServlet(name = "upload")
public class UploadServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//String description = request.getParameter("description") If you want people to upload a description with the image
		try {
			final Part filePart = request.getPart("file");
			final String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
			final InputStream fileContent = filePart.getInputStream();

			final String PREFIX = Upload.fileNameOnly(fileName);
			final String SUFFIX = Upload.fileExtension(fileName);

			if (SUFFIX == null || SUFFIX.isEmpty()
					|| PREFIX == null || PREFIX.isEmpty()) {
				System.out.println("PREFIX/SUFFIX cannot be empty"); //TODO Return a real error message for this
			} else {
				File tempFile = Upload.stream2File(fileContent,PREFIX,SUFFIX);

				System.out.println("File Metadata");
				String path = tempFile.getAbsolutePath();
				Path file = Paths.get(path);
				BasicFileAttributes attr = Files.readAttributes(file, BasicFileAttributes.class);
				System.out.println("Creation Time: " + attr.creationTime());
			}

			//Basic File Metadata
			System.out.println("Basic File InputStream Metadata");
			System.out.println("Submitted File Name: " + filePart.getSubmittedFileName());
			System.out.println("File Content Type: " + filePart.getContentType());
			System.out.println("File Headers: " + filePart.getHeaderNames());
			System.out.println("File Name: " + filePart.getName());
			System.out.println("File Size(bytes): " + filePart.getSize());
		} catch (IOException ex) {
			System.out.println(ex.getMessage()); //TODO throw some sort of error message back and handle cleanly.
		} catch (ServletException ex) {
			System.out.println(ex.getMessage()); //TODO throw some sort of error message back and handle cleanly.
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}
}
