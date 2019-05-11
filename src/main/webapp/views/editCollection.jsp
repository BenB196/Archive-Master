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
        <h1 class="display-4">Edit Collection: ${collection.name}</h1>
        <hr />
        <h3 style="color:#ff9900">You are Editing Collection: ${collection.name}</h3>
    </div>

    <form class="mx-auto" style="width: 600px" action="${pageContext.request.contextPath}/addCollection" method="post" enctype="multipart/form-data">
        <div class="form-group">
            <label for="title">Title</label>
            <input id="title" name="title" type="text" class="form-control" value="${collection.name}" placeholder="Give your collection a name." readonly>
        </div>
        <div class="form-group">
            <label for="description">Description</label>
            <input id="description" name="description" type="text" value="${collection.description}" class="form-control" placeholder="What's in your collection?" readonly>
        </div>
        <button class="button-orange button-hover" hidden>Submit</button>
    </form>

    <div class="text-center">
        <form action="${pageContext.request.contextPath}/addRecord" method="post" enctype="multipart/form-data">
            <button class="button-orange button-hover" type="submit" value="${collection.name}" name="addFile">Add File to Collection</button>
        </form>
    </div>

    <form action="${pageContext.request.contextPath}/editFile" method="post" enctype="multipart/form-data">
        <table class="table table-striped">
            <thead class="thead-dark">
            <tr class="table-primary">
                <th scope="col">Name</th>
                <th scope="col">Title</th>
                <th scope="col">Creator</th>
                <th scope="col">Description</th>
                <th scope="col">Edit</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${files}" var="file">
                <tr>
                    <td>${file.fileName}</td>
                    <td>${file.title}</td>
                    <td>${file.creator}</td>
                    <td>${file.description}</td>
                    <td><button class="button-orange button-hover" type="submit" name="editFile" value="${collection.name}/${file.fileName}">Edit File</button></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </form>

    <div class="text-center">
        <form action="${pageContext.request.contextPath}/deleteCollection" method="post" enctype="multipart/form-data">
            <button class="button-orange button-hover" type="submit" value="${collection.name}" name="deleteCollection">Delete Collection</button>
        </form>
    </div>
    <a  href="./"><button class="button-orange button-hover">Go Home</button></a>
</body>
</html>