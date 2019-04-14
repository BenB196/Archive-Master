package com.archivemaster.servlets;

import static com.archivemaster.utils.ArchiveMasterUtils.isEmptyString;

import com.archivemaster.fedora.Collection;
import com.archivemaster.fedora.Fedora;
import com.archivemaster.fedora.FedoraFile;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/searchArchive")
@MultipartConfig
public class SearchServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<FedoraFile> fedoraFiles = Fedora.getEverything();

		String search = request.getParameter("search");
		if (search == null || search.isEmpty()) {
			search = "all";
		}

		final String searchFilter = request.getParameter("searchFilter");

		ArrayList<FedoraFile> fedoraFilesToReturn = new ArrayList<>();

		if (fedoraFiles != null && fedoraFiles.size() > 0) {
			if (searchFilter == null || searchFilter.isEmpty()) {
				fedoraFilesToReturn = fedoraFiles;
			} else {
				switch(search) {
					case "collectionName":
						for (FedoraFile fedoraFile : fedoraFiles) {
							if (fedoraFile.getCollectionName() != null && !fedoraFile.getCollectionName().isEmpty()) {
								String collectionName = fedoraFile.getCollectionName().toLowerCase();
								if (collectionName.contains(searchFilter)) {
									System.out.println("!!!!!!!!!!!!!!!!!!! Added file based off of collection name: " + collectionName);
									fedoraFilesToReturn.add(fedoraFile);
								}
							}
						}
					case "collectionDescription":
						for (FedoraFile fedoraFile : fedoraFiles) {
							if (fedoraFile.getCollectionName() != null && !fedoraFile.getCollectionName().isEmpty()) {
								String collectionDescription = Collection.getCollectionByName(fedoraFile.getCollectionName()).getDescription();
								if (collectionDescription != null && !collectionDescription.isEmpty()) {
									collectionDescription = collectionDescription.toLowerCase();
									if (collectionDescription.contains(searchFilter)) {
										System.out.println("!!!!!!!!!!!!!!!!!!! Added file based off of collection description: " + collectionDescription);
										fedoraFilesToReturn.add(fedoraFile);
									}
								}
							}
						}
					case "fileName":
						for (FedoraFile fedoraFile : fedoraFiles) {
							if (fedoraFile.getFileName() != null && !fedoraFile.getFileName().isEmpty()) {
								String fileName = fedoraFile.getFileName().toLowerCase();
								if (fileName.contains(searchFilter)) {
									System.out.println("!!!!!!!!!!!!!!!!!!! Added file based off of file name: " + fileName);
									fedoraFilesToReturn.add(fedoraFile);
								}
							}
						}
					case "creator":
						for (FedoraFile fedoraFile : fedoraFiles) {
							if (fedoraFile.getCreator() != null && !fedoraFile.getCreator().isEmpty()) {
								String creator = fedoraFile.getCreator().toLowerCase();
								if (creator.contains(searchFilter)) {
									System.out.println("!!!!!!!!!!!!!!!!!!! Added file based off of creator: " + creator);
									fedoraFilesToReturn.add(fedoraFile);
								}
							}
						}
					case "subject":
						for (FedoraFile fedoraFile : fedoraFiles) {
							if (fedoraFile.getSubject() != null && !fedoraFile.getSubject().isEmpty()) {
								String subject = fedoraFile.getSubject().toLowerCase();
								if (subject.contains(searchFilter)) {
									System.out.println("!!!!!!!!!!!!!!!!!!! Added file based off of subject: " + subject);
									fedoraFilesToReturn.add(fedoraFile);
								}
							}
						}
					case "fileDescription":
						for (FedoraFile fedoraFile : fedoraFiles) {
							if (fedoraFile.getDescription() != null && !fedoraFile.getDescription().isEmpty()) {
								String description = fedoraFile.getDescription().toLowerCase();
								if (description.contains(searchFilter)) {
									System.out.println("!!!!!!!!!!!!!!!!!!! Added file based off of file description: " + description);
									fedoraFilesToReturn.add(fedoraFile);
								}
							}
						}
					case "publisher":
						for (FedoraFile fedoraFile : fedoraFiles) {
							if (fedoraFile.getPublisher() != null && !fedoraFile.getPublisher().isEmpty()) {
								String publisher = fedoraFile.getPublisher().toLowerCase();
								if (publisher.contains(searchFilter)) {
									System.out.println("!!!!!!!!!!!!!!!!!!! Added file based off of publisher: " + publisher);
									fedoraFilesToReturn.add(fedoraFile);
								}
							}
						}
					case "contributor":
						for (FedoraFile fedoraFile : fedoraFiles) {
							if (fedoraFile.getContributor() != null && !fedoraFile.getContributor().isEmpty()) {
								String contributor = fedoraFile.getContributor().toLowerCase();
								if (contributor.contains(searchFilter)) {
									System.out.println("!!!!!!!!!!!!!!!!!!! Added file based off of contributor: " + contributor);
									fedoraFilesToReturn.add(fedoraFile);
								}
							}
						}
					case "date":
						for (FedoraFile fedoraFile : fedoraFiles) {
							if (fedoraFile.getsDate() != null && !fedoraFile.getsDate().isEmpty()) {
								String sDate = fedoraFile.getsDate().toLowerCase();
								if (sDate.contains(searchFilter)) {
									System.out.println("!!!!!!!!!!!!!!!!!!! Added file based off of date: " + sDate);
									fedoraFilesToReturn.add(fedoraFile);
								}
							}
						}
					case "identifier":
						for (FedoraFile fedoraFile : fedoraFiles) {
							if (fedoraFile.getIdentifier() != null && !fedoraFile.getIdentifier().isEmpty()) {
								String identifier = fedoraFile.getIdentifier().toLowerCase();
								if (identifier.contains(searchFilter)) {
									System.out.println("!!!!!!!!!!!!!!!!!!! Added file based off of identifier: " + identifier);
									fedoraFilesToReturn.add(fedoraFile);
								}
							}
						}
					case "source":
						for (FedoraFile fedoraFile : fedoraFiles) {
							if (fedoraFile.getSource() != null && !fedoraFile.getSource().isEmpty()) {
								String source = fedoraFile.getSource().toLowerCase();
								if (source.contains(searchFilter)) {
									System.out.println("!!!!!!!!!!!!!!!!!!! Added file based off of source: " + source);
									fedoraFilesToReturn.add(fedoraFile);
								}
							}
						}
					case "language":
						for (FedoraFile fedoraFile : fedoraFiles) {
							if (fedoraFile.getLanguage() != null && !fedoraFile.getLanguage().isEmpty()) {
								String language = fedoraFile.getLanguage().toLowerCase();
								if (language.contains(searchFilter)) {
									System.out.println("!!!!!!!!!!!!!!!!!!! Added file based off of language: " + language);
									fedoraFilesToReturn.add(fedoraFile);
								}
							}
						}
					case "coverage":
						for (FedoraFile fedoraFile : fedoraFiles) {
							if (fedoraFile.getCoverage() != null && !fedoraFile.getCoverage().isEmpty()) {
								String coverage = fedoraFile.getCoverage().toLowerCase();
								if (coverage.contains(searchFilter)) {
									System.out.println("!!!!!!!!!!!!!!!!!!! Added file based off of coverage: " + coverage);
									fedoraFilesToReturn.add(fedoraFile);
								}
							}
						}
					case "rights":
						for (FedoraFile fedoraFile : fedoraFiles) {
							if (fedoraFile.getRights() != null && !fedoraFile.getRights().isEmpty()) {
								String rights = fedoraFile.getRights().toLowerCase();
								if (rights.contains(searchFilter)) {
									System.out.println("!!!!!!!!!!!!!!!!!!! Added file based off of rights: " + rights);
									fedoraFilesToReturn.add(fedoraFile);
								}
							}
						}
					default:
						for (FedoraFile fedoraFile : fedoraFiles) {
							Boolean resultFound = false;

							String collectionName = fedoraFile.getCollectionName();
							String collectionDescription = Collection.getCollectionByName(fedoraFile.getCollectionName()).getDescription();
							String fileName = fedoraFile.getFileName();
							String creator = fedoraFile.getCreator();
							String subject = fedoraFile.getSubject();
							String fileDescription = fedoraFile.getDescription();
							String publisher = fedoraFile.getPublisher();
							String contributor = fedoraFile.getContributor();
							String date = fedoraFile.getsDate();
							String identifier = fedoraFile.getIdentifier();
							String source = fedoraFile.getSource();
							String language = fedoraFile.getLanguage();
							String coverage = fedoraFile.getCoverage();
							String rights = fedoraFile.getRights();

							if (!isEmptyString(collectionName)) {
								if (collectionName.toLowerCase().contains(searchFilter)) {
									resultFound = true;
								}
							}

							resultFound = checkResultFound(resultFound, collectionDescription, searchFilter);

							resultFound = checkResultFound(resultFound, fileName, searchFilter);

							resultFound = checkResultFound(resultFound, creator, searchFilter);

							resultFound = checkResultFound(resultFound, subject, searchFilter);

							resultFound = checkResultFound(resultFound, fileDescription, searchFilter);

							resultFound = checkResultFound(resultFound, publisher, searchFilter);

							resultFound = checkResultFound(resultFound, contributor, searchFilter);

							resultFound = checkResultFound(resultFound, date, searchFilter);

							resultFound = checkResultFound(resultFound, identifier, searchFilter);

							resultFound = checkResultFound(resultFound, source, searchFilter);

							resultFound = checkResultFound(resultFound, language, searchFilter);

							resultFound = checkResultFound(resultFound, coverage, searchFilter);

							resultFound = checkResultFound(resultFound, rights, searchFilter);

							if (resultFound) {
								fedoraFilesToReturn.add(fedoraFile);
							}
						}
				}
			}
		}
		if (fedoraFilesToReturn == null || fedoraFilesToReturn.size() == 0) {
			request.setAttribute("numOfResults", 0);
			request.setAttribute("files", null);
		} else {
			request.setAttribute("numOfResults", fedoraFilesToReturn.size());
			request.setAttribute("files", fedoraFilesToReturn);
		}
		request.setAttribute("baseDownloadUrl", Fedora.RESTURL);
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/views/viewSearch.jsp");
		dispatcher.forward(request,response);

	}

	private static boolean ignoreCaseMatch(String s, String filter) {
		return s.toLowerCase().contains(filter);
	}

	private static boolean checkResultFound(boolean resultFound, String s, String filter) {
		return resultFound ? true : (!isEmptyString(s) ? ignoreCaseMatch(s, filter) : false);
	}
}
