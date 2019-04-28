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

  <div class="text-center text-primary">
    <h1 class="display-4">Edit File in Collection</h1>
    <hr />
    <h3 style="color:#ff9900">Collections allow you to group related files</h3>
  </div>
  <div class="container">
    <form class="mx-auto" style="width: 700px" action="${pageContext.request.contextPath}/addFile" method="post" enctype="multipart/form-data">
      <!--
      <div class="form-group">
        <label for="title">Title</label>
        <input type="email" class="form-control" id="title">
      </div>
      -->
      <div class="form-group">
        <label for="whatDo">Collection Name</label>
        <input type="text" class="form-control" name="whatDo" value="${whatDo}" id="whatDo" readonly>
      </div>
      <div class="form-group">
        <label for="collectionName">Collection Name</label>
        <input type="text" class="form-control" name="collectionName" value="${collectionName}" id="collectionName" readonly>
      </div>
      <div class="form-group">
        <label for="creator">Creator</label>
        <input type="text" class="form-control" name="creator" id="creator" value="${file.creator}" required>
      </div>
      <div class="form-group">
        <label for="subject">Subject</label>
        <input type="text" class="form-control" name="subject" id="subject" value="${file.subject}" required>
      </div>
      <div class="form-group">
        <label for="description">Description</label>
        <input type="text" class="form-control" name="description" id="description" value="${file.description}" required>
      </div>
      <div class="form-group">
        <label for="publisher">Publisher</label>
        <input type="text" class="form-control" name="publisher" id="publisher" value="${file.publisher}" required>
      </div>
      <div class="form-group">
        <label for="contributor">Contributor</label>
        <input type="text" class="form-control" name="contributor" id="contributor" value="${file.contributor}" required>
      </div>
      <div class="form-group">
        <label for="sDate">Date</label>
        <input type="text" class="form-control" name="sDate" id="sDate" value="${file.sDate}" required>
      </div>
      <div class="form-group">
        <label for="type">Type</label>
        <input type="text" class="form-control" name="type" id="type" value="${file.type}" readonly>
      </div>
      <div class="form-group">
        <label for="format">Format</label>
        <input type="text" class="form-control" name="format" id="format" value="${file.format}" readonly>
      </div>
      <div class="form-group">
        <label for="identifier">Identifier</label>
        <input type="text" class="form-control" name="identifier" id="identifier" value="${file.identifier}" required>
      </div>
      <div class="form-group">
        <label for="source">Source</label>
        <input type="text" class="form-control" name="source" id="source" value="${file.source}" required>
      </div>
      <div class="form-group">
        <label for="language">Language</label>
        <input type="text" class="form-control" name="language" id="language" value="${file.language}" required>
      </div>
      <!--
      <div class="form-group">
        <label for="description">Relation</label>
        <input type="password" class="form-control" id="exampleInputPassword1">
      </div>
      -->
      <div class="form-group">
        <label for="coverage">Coverage</label>
        <input type="text" class="form-control" name="coverage" id="coverage" value="${file.coverage}" required>
      </div>
      <div class="form-group">
        <label for="rights">Rights</label>
        <input type="text" class="form-control" name="rights" id="rights" value="${file.rights}" required>
      </div>
      <div class="form-group">
        <label for="file">Select File</label>
        <input type="file" class="form-control" name="file" id="file" required>
      </div>
      <button class="button-orange button-hover">Submit</button>
    </form>
  </div>

  <div class="text-center">
    <form action="${pageContext.request.contextPath}/deleteFile" method="post" enctype="multipart/form-data">
      <button class="button-orange button-hover" type="submit" value="${collectionName}/${file.fileName}" name="deleteFile">Delete File</button>
    </form>
  </div>

  <a  href="./"><button class="button-orange button-hover">Go Home</button></a>
</body>
</html>

<!-- ** Metadata Categories **
1 Title
2 Creator
3 Subject
4 Description
5 Publisher
6 Contributor
7 Date
8 Type
9 Format
10 Identifier
11 Source
12 Language
13 Relation
14 Coverage
15 Rights
-->