<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.archivemaster.test" %>
<%@ page import="com.archivemaster.ControlledVocab" %>

<html>
<head>
    <title>$Title$</title>
</head>
<body>
<h1>Below should say Hello World</h1>
<%= test.getMessage()%>

<h2>${pageContext.request.contextPath}</h2>

<form action="${pageContext.request.contextPath}/fedora" method="post">
    <input type="submit" name="submit" value="getFedoraContainers">
</form>

<form action="${pageContext.request.contextPath}/fedora" method="post">
    <input type="text" name="collectionName">
    <input type="submit" name="submit" value="createFedoraNode">
</form>

<form action="${pageContext.request.contextPath}/upload" method="post" enctype="multipart/form-data">
    <input type="file" name="file" />
    <input type="submit" />
</form>

<form action="${pageContext.request.contextPath}/fedora" method="post">
    <input type="text" name="deleteFile">
    <input type="submit" name="submit" value="deleteFile">
</form>
</body>
</html>

