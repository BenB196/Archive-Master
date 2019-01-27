package com.archive_master;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.*;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;


@WebServlet("/test")
public class fedora_test extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String button = request.getParameter("button");

		String url = "localhost:8080/rest/";

		if ("button1".equals(button)) {
			System.out.println("Creating HTTP Client");
			HttpClient client = HttpClientBuilder.create().build();
			System.out.println("Creating GET Request");
			HttpGet httpRequest = new HttpGet(url);
			System.out.println("Executing/Getting Response");
			HttpResponse httpResponse = client.execute(httpRequest);

			System.out.println("Response Code: " + httpResponse.getStatusLine().getStatusCode());

			BufferedReader rd = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));

			StringBuffer result = new StringBuffer();
			String line ="";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
		}
	}
}
