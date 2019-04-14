<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en" ng-app="ArchiveMaster">
<head>
    <title>ArchiveMaster</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <link rel="stylesheet" href="css/styles.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
    <script src="lib/angular.min.js"></script>
    <script src="lib/angular-route.min.js"></script>
    <script src="app.js"></script>
</head>
<body class="body">
    <header ng-include="'header.html'"></header>

    <h2 class="display-3">Search Archive</h2>
    <div class="form-group">
        <form class="mx-auto" style="width: 700px" action="${pageContext.request.contextPath}/searchArchive" method="post" enctype="multipart/form-data">
            <label for="search">Select Search Filter:</label>
            <select class="form-control" name="search" id="search">
                <option value="all">All</option>
                <option value="collectionName">Collection Name</option>
                <option value="collectionDescription">Collection Description</option>
                <option value="fileName">File Name</option>
                <option value="creator">Creator</option>
                <option value="subject">Subject</option>
                <option value="fileDescription">File Description</option>
                <option value="publisher">Publisher</option>
                <option value="contributor">Contributor</option>
                <option value="date">Date</option>
                <option value="identifier">Identifier</option>
                <option value="source">Source</option>
                <option value="language">Language</option>
                <option value="coverage">Coverage</option>
                <option value="rights">Rights</option>
            </select>

            <label for="searchFilter">Enter Search:</label>
            <input type="text" class="form-control" name="searchFilter" id="searchFilter" placeholder="Type what you would like to search...">

            <button class="button-orange button-hover">Search</button>
        </form>
    </div>

    <h3>Number of Results: ${numOfResults}</h3>
    <table class="table table-striped">
        <thead class="thead-dark">
        <tr class="table-primary">
            <th scope="col">Collection Name</th>
            <th scope="col">File Name</th>
            <th scope="col">File Title</th>
            <th scope="col">Creator</th>
            <th scope="col">Subject</th>
            <th scope="col">File Description</th>
            <th scope="col">Publisher</th>
            <th scope="col">Contributor</th>
            <th scope="col">Date</th>
            <th scope="col">Identifier</th>
            <th scope="col">Source</th>
            <th scope="col">Language</th>
            <th scope="col">Coverage</th>
            <th scope="col">Rights</th>
            <th scope="col">Download</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${files}" var="file">
            <tr>
                <td>${file.collectionName}</td>
                <td>${file.fileName}</td>
                <td>${file.title}</td>
                <td>${file.creator}</td>
                <td>${file.subject}</td>
                <td>${file.description}</td>
                <td>${file.publisher}</td>
                <td>${file.contributor}</td>
                <td>${file.sDate}</td>
                <td>${file.identifier}</td>
                <td>${file.source}</td>
                <td>${file.language}</td>
                <td>${file.coverage}</td>
                <td>${file.rights}</td>
                <td><a href="${baseDownloadUrl}/${file.collectionName}/${file.fileName}"><button class="button-orange button-hover">Download</button></a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</form>

<a  href="./"><button class="button-orange button-hover">Go Home</button></a>
</body>
</html>
