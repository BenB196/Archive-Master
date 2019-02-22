package com.archivemaster.servlets;

import com.archivemaster.Upload;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
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

@WebServlet("/upload")
@MultipartConfig
public class UploadServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String description = request.getParameter("description");
			Part filePart = request.getPart("file");
			String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
			InputStream fileContent = filePart.getInputStream();
			System.out.println("Basic File InputStream Metadata");
			System.out.println("Submitted File Name: " + filePart.getSubmittedFileName());
			System.out.println("File Content Type: " + filePart.getContentType());
			System.out.println("File Headers: " + filePart.getHeaderNames());
			System.out.println("File Name: " + filePart.getName());
			System.out.println("File Size(bytes): " + filePart.getSize());
			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write("Basic File InputStream Metadata");
			response.getWriter().write("Submitted File Name: " + filePart.getSubmittedFileName());
			response.getWriter().write("File Content Type: " + filePart.getContentType());
			response.getWriter().write("File Headers: " + filePart.getHeaderNames());
			response.getWriter().write("File Name: " + filePart.getName());
			response.getWriter().write("File Size(bytes): " + filePart.getSize());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} catch (ServletException e) {
			System.out.println(e.getMessage());
		}
	}
}
