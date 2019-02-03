package com.archive_master;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * @author benbr
 */

@WebServlet("/upload")
@MultipartConfig
public class UploadServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		//String description = request.getParameter("description") If you want people to upload a description with the image
		try {
			Part filePart = request.getPart("file");
			String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
			InputStream fileContent = filePart.getInputStream();

			//Basic File Metadata
			System.out.println("Basic File Metadata");
			System.out.println("Submitted File Name: " + filePart.getSubmittedFileName());
			System.out.println("File Content Type: " + filePart.getContentType());
			System.out.println("File Headers: " + filePart.getHeaderNames());
			System.out.println("File Name: " + filePart.getName());
			System.out.println("File Size(bytes): " + filePart.getSize());
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
			//TODO throw some sort of error message back and handle cleanly.
		} catch (ServletException ex) {
			System.out.println(ex.getMessage());
			//TODO throw some sort of error message back and handle cleanly.
		}
	}
}
