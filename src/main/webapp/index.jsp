<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.archivemaster.ControlledVocab" %>
<%@ page import="com.archivemaster.Fedora" %>

<html>
<head>
    <title>$Title$</title>
</head>
<body>

<h1>Fedora API Status</h1>
<%= Fedora.fedoraAPICheck() %>

<h2>${pageContext.request.contextPath}</h2>

<form action="${pageContext.request.contextPath}/fedora" method="post">
    <input type="text" name="collectionName">
    <input type="text" name="collectionDescription">
    <input type="submit" name="submit" value="Create Collection">
</form>

<form action="${pageContext.request.contextPath}/fedora" method="post">
    <input type="text" name="collectionName">
    <input type="submit" name="submit" value="Delete Collection">
</form>

<form action="${pageContext.request.contextPath}/upload" method="post" enctype="multipart/form-data">
    <input type="text" name="collectionName">
    <input type="file" name="file" />
    <input type="submit" value="Upload File" />
</form>

<form action="${pageContext.request.contextPath}/fedora" method="post" enctype="multipart/form-data">
    <input type="text" name="collectionName"><br>
    <input type="file" name="file" /><br>
    <input type="text" name="creator"><br>
    <input type="text" name="subject"><br>
    <input type="text" name="description"><br>
    <input type="text" name="publisher"><br>
    <input type="text" name="contributor"><br>
    <input type="text" name="sDate"><br>
    <input type="text" name="identifier"><br>
    <input type="text" name="source"><br>
    <input type="text" name="language"><br>
    <input type="text" name="coverage"><br>
    <input type="text" name="rights"><br>
    <input type="submit" name="submit" value="Upload File" />
</form>

<form action="${pageContext.request.contextPath}/fedora" method="post">
    <input type="text" name="collectionName"><br>
    <input type="text" name="fileName"><br>
    <input type="submit" name="submit" value="Delete File">
</form>

<form action="${pageContext.request.contextPath}/fedora" method="post">
    <input type="text" name="toDelete">
    <input type="submit" name="submit" value="Delete">
</form>

<form action="${pageContext.request.contextPath}/fedora" method="post">
    <input type="submit" name="submit" value="Search Collections">
</form>

<form action="${pageContext.request.contextPath}/fedora" method="post">
    <input type="text" name="collectionName">
    <input type="submit" name="submit" value="Search Collection">
</form>
</body>
</html>

