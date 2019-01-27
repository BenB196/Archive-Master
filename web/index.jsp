<%--
  Created by IntelliJ IDEA.
  User: benbr
  Date: 1/19/2019
  Time: 12:16 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.archive_master.test" %>
<%@ page import="com.archive_master.fedora_test" %>

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
</html>
