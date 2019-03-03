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
    <input type="submit" name="submit" value="Create Collection">
</form>

<form action="${pageContext.request.contextPath}/upload" method="post" enctype="multipart/form-data">
    <input type="text" name="collectionName">
    <input type="file" name="file" />
    <input type="submit" value="Upload File" />
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

