package com.archive_master;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@WebServlet("/test")
public class fedora_test extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String button = request.getParameter("button");



		if ("button1".equals(button)) {
			System.out.println("Creating URL");
			URL url = new URL("http://localhost:8080/rest/");
			System.out.println("Creating HTTP Client");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			System.out.println("Creating GET Request");
			con.setRequestMethod("GET");

			System.out.println("Response Code: " + con.getResponseCode());

			BufferedReader rd = new BufferedReader(new InputStreamReader(con.getInputStream()));

			StringBuffer result = new StringBuffer();
			String line;
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
			System.out.println(result);
			rd.close();
			con.disconnect();
		}
	}
}
