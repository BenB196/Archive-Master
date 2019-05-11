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
        <label for="collectionName">Collection Name <a href="#" data-toggle="tooltip" data-placement="top" title="The collection that the current record belongs to."><sup><b>i</b></sup></a></label>
        <input type="text" class="form-control" name="collectionName" value="${collectionName}" id="collectionName" readonly>
      </div>
      <div class="form-group">
        <label for="creator">Creator <a href="#" data-toggle="tooltip" data-placement="top" title="The person or organization who created the resource."><sup><b>i</b></sup></a></label>
        <input type="text" class="form-control" name="creator" id="creator" value="${file.creator}">
      </div>
      <div class="form-group">
        <label for="subject">Subject</label>
        <input type="text" class="form-control" name="subject" id="subject" value="${file.subject}">
      </div>
      <div class="form-group">
        <label for="description">Description <a href="#" data-toggle="tooltip" data-placement="top" title="A description of the resource's content."><sup><b>i</b></sup></a></label>
        <input type="text" class="form-control" name="description" id="description" value="${file.description}">
      </div>
      <div class="form-group">
        <label for="publisher">Publisher <a href="#" data-toggle="tooltip" data-placement="top" title="The organization that published the resource."><sup><b>i</b></sup></a></label>
        <input type="text" class="form-control" name="publisher" id="publisher" value="${file.publisher}">
      </div>
      <div class="form-group">
        <label for="contributor">Contributor <a href="#" data-toggle="tooltip" data-placement="top" title="People or organizations, other than the creator, that made significant contributions to the creation or availability of the resource."><sup><b>i</b></sup></a></label>
        <input type="text" class="form-control" name="contributor" id="contributor" value="${file.contributor}">
      </div>
      <div class="form-group">
        <label for="description">Date</label>
        <input title="The year that the resource was created or became available" type="number" min="1000" max="2099" step="1" value="2019" class="form-control" id="exampleInputPassword1">
      </div>
      <div class="form-group">
        <label for="type">Type <a href="#" data-toggle="tooltip" data-placement="top" title="The type of the resource."><sup><b>i</b></sup></a></label>
        <input type="text" class="form-control" name="type" id="type" value="${file.type}">
      </div>
      <div class="form-group">
        <label for="format">Format <a href="#" data-toggle="tooltip" data-placement="top" title="The type of file being uploaded Ex: .jpeg .doc."><sup><b>i</b></sup></a></label>
        <input type="text" class="form-control" name="format" id="format" value="${file.format}">
      </div>
      <div class="form-group">
        <label for="identifier">Identifier <a href="#" data-toggle="tooltip" data-placement="top" title="The unique identifier of the resource (URL, ISBN, item/serial number, etc.)."><sup><b>i</b></sup></a></label>
        <input type="text" class="form-control" name="identifier" id="identifier" value="${file.identifier}">
      </div>
      <div class="form-group">
        <label for="source">Source</label>
        <input type="text" class="form-control" name="source" id="source" value="${file.source}">
      </div>
      <div class="form-group">
        <label for="language">Language</label>
        <select class="form-control" name="language" id="language" required>
          <option value="AF">Afrikanns</option>
          <option value="SQ">Albanian</option>
          <option value="AR">Arabic</option>
          <option value="HY">Armenian</option>
          <option value="EU">Basque</option>
          <option value="BN">Bengali</option>
          <option value="BG">Bulgarian</option>
          <option value="CA">Catalan</option>
          <option value="KM">Cambodian</option>
          <option value="ZH">Chinese (Mandarin)</option>
          <option value="HR">Croation</option>
          <option value="CS">Czech</option>
          <option value="DA">Danish</option>
          <option value="NL">Dutch</option>
          <option value="EN">English</option>
          <option value="ET">Estonian</option>
          <option value="FJ">Fiji</option>
          <option value="FI">Finnish</option>
          <option value="FR">French</option>
          <option value="KA">Georgian</option>
          <option value="DE">German</option>
          <option value="EL">Greek</option>
          <option value="GU">Gujarati</option>
          <option value="HE">Hebrew</option>
          <option value="HI">Hindi</option>
          <option value="HU">Hungarian</option>
          <option value="IS">Icelandic</option>
          <option value="ID">Indonesian</option>
          <option value="GA">Irish</option>
          <option value="IT">Italian</option>
          <option value="JA">Japanese</option>
          <option value="JW">Javanese</option>
          <option value="KO">Korean</option>
          <option value="LA">Latin</option>
          <option value="LV">Latvian</option>
          <option value="LT">Lithuanian</option>
          <option value="MK">Macedonian</option>
          <option value="MS">Malay</option>
          <option value="ML">Malayalam</option>
          <option value="MT">Maltese</option>
          <option value="MI">Maori</option>
          <option value="MR">Marathi</option>
          <option value="MN">Mongolian</option>
          <option value="NE">Nepali</option>
          <option value="NO">Norwegian</option>
          <option value="FA">Persian</option>
          <option value="PL">Polish</option>
          <option value="PT">Portuguese</option>
          <option value="PA">Punjabi</option>
          <option value="QU">Quechua</option>
          <option value="RO">Romanian</option>
          <option value="RU">Russian</option>
          <option value="SM">Samoan</option>
          <option value="SR">Serbian</option>
          <option value="SK">Slovak</option>
          <option value="SL">Slovenian</option>
          <option value="ES">Spanish</option>
          <option value="SW">Swahili</option>
          <option value="SV">Swedish </option>
          <option value="TA">Tamil</option>
          <option value="TT">Tatar</option>
          <option value="TE">Telugu</option>
          <option value="TH">Thai</option>
          <option value="BO">Tibetan</option>
          <option value="TO">Tonga</option>
          <option value="TR">Turkish</option>
          <option value="UK">Ukranian</option>
          <option value="UR">Urdu</option>
          <option value="UZ">Uzbek</option>
          <option value="VI">Vietnamese</option>
          <option value="CY">Welsh</option>
          <option value="XH">Xhosa</option>
      </div>
      <!--
      <div class="form-group">
        <label for="description">Relation</label>
        <input type="password" class="form-control" id="exampleInputPassword1">
      </div>
      -->
      <div class="form-group">
        <label for="coverage">Coverage</label>
        <input type="text" class="form-control" name="coverage" id="coverage" value="${file.coverage}">
      </div>
      <div class="form-group">
        <label for="rights">Rights</label>
        <input type="text" class="form-control" name="rights" id="rights" value="${file.rights}">
      </div>
      <div class="form-group">
        <label for="file">Select File <a href="#" data-toggle="tooltip" data-placement="top" title="Any digital file you want to save - it could be a letter, a photograph, a word document or anything else from your computer."><sup><b>i</b></sup></a></label>
        <input type="file" class="form-control" name="file" id="file" required>
      </div>
      <button class="button-orange button-hover">Update File</button>
    </form>
  </div>

  <div class="text-center">
    <form action="${pageContext.request.contextPath}/deleteFile" method="post" enctype="multipart/form-data">
      <button class="button-orange button-hover" type="submit" value="${collectionName}/${file.fileName}" name="deleteFile" data-toggle="tooltip" data-placement="top" title="">Delete File</button>
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
