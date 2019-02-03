<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.archive_master.test" %>

<html>
<head>
    <title>$Title$</title>
</head>
<body>
<h1>Below should say Hello World</h1>
<%= test.getMessage()%>
</body>

<form action="${pageContext.request.contextPath}/test" method="post">
    <button type="submit" name="button" value="button1">Test API Connection</button>
</form>

<form action="${pageContext.request.contextPath}/fedora" method="post">
    <input type="submit" name="submit" value="getFedoraContainers">
</form>

<form action="${pageContext.request.contextPath}/fedora" method="post">
    <input type="submit" name="submit" value="createFedoraNode">
</form>

<form action="${pageContext.request.contextPath}/upload" method="post" enctype="multipart/form-data">
    <input type="file" name="file" />
    <input type="submit" />
</form>
</html>

