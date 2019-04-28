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

    <br/>

    ${apiStatus.responseMessage}

    <br/>

    <a  href="./"><button class="button-orange button-hover">Go Home</button></a>
</body>
</html>