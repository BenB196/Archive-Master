package com.archivemaster.servlets;

import com.archivemaster.Fedora;
import com.archivemaster.Upload;


import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.NoSuchAlgorithmException;

@WebServlet("/upload")
@MultipartConfig
public class UploadServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String collectionName = request.getParameter("collectionName");
		try {
			final Part filePart = request.getPart("file");
			final String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
			final InputStream fileContent = filePart.getInputStream();

			final String PREFIX = Upload.fileNameOnly(fileName);
			final String SUFFIX = Upload.fileExtension(fileName);

			//Basic FedoraFile Metadata
			System.out.println("Basic FedoraFile InputStream Metadata");
			System.out.println("Submitted FedoraFile Name: " + filePart.getSubmittedFileName());
			System.out.println("FedoraFile Content Type: " + filePart.getContentType());
			System.out.println("FedoraFile Headers: " + filePart.getHeaderNames());
			System.out.println("FedoraFile Name: " + filePart.getName());
			System.out.println("FedoraFile Size(bytes): " + filePart.getSize());

			if (SUFFIX == null || SUFFIX.isEmpty()
					|| PREFIX == null || PREFIX.isEmpty()) {
				System.out.println("PREFIX/SUFFIX cannot be empty"); //TODO Return a real error message for this
			} else {
				File tempFile = Upload.stream2File(fileContent, PREFIX, SUFFIX);

				System.out.println("FedoraFile Metadata");
				String path = tempFile.getAbsolutePath();
				Path file = Paths.get(path);
				BasicFileAttributes attr = Files.readAttributes(file, BasicFileAttributes.class);
				System.out.println("FedoraFile Creation Time: " + attr.creationTime());
				System.out.println("FedoraFile Key: " + attr.fileKey());
				System.out.println("FedoraFile is Directory: " + attr.isDirectory());
				System.out.println("FedoraFile Other: " + attr.isOther());
				System.out.println("FedoraFile Reg: " + attr.isRegularFile());
				System.out.println("FedoraFile Sym Link: " + attr.isSymbolicLink());
				System.out.println("FedoraFile Last Access: " + attr.lastAccessTime());
				System.out.println("FedoraFile Last Mod: " + attr.lastModifiedTime());
				System.out.println("FedoraFile Size: " + attr.size());
				try {
					final String sha1 = Upload.fileSHA1(tempFile, 1).toLowerCase(); //Generate SHA 1 hash
					final String sha256 = Upload.fileSHA1(tempFile, 256).toLowerCase(); //Generate SHA 256 hash
					System.out.println("FIle SHA-1 hash: " + sha1);
					System.out.println("FIle SHA-256 hash: " + sha256);

					//Upload file to Fedora
					Fedora.fedoraAPICreate("file", collectionName, fileName, filePart.getContentType(), "attachment; filename=\"" + fileName + "\"", tempFile, sha1, sha256);
				} catch (NoSuchAlgorithmException ex) {
					System.out.println(ex.getMessage()); //TODO throw some sort of error message back and handle cleanly.
				} catch (FileNotFoundException ex) {
					System.out.println(ex.getMessage()); //TODO throw some sort of error message back and handle cleanly.
				} catch (IllegalArgumentException ex) {
					System.out.println(ex.getMessage()); //TODO throw some sort of error message back and handle cleanly.
				}
			}
		} catch (IOException ex) {
			System.out.println(ex.getMessage()); //TODO throw some sort of error message back and handle cleanly.
		} catch (ServletException ex) {
			System.out.println(ex.getMessage()); //TODO throw some sort of error message back and handle cleanly.
		}
	}

	//protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	//
	//}
}
