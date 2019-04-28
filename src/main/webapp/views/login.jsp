<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="container-fluid">
    <h2 class="display-3">Login</h2>
    <div class="form-group">
        <form action="${pageContext.request.contextPath}/rest/login" method="post">
            <table style="width: 50%">
                <tr>
                    <td>UserName</td>
                    <td><input type="text" name="username" /></td>
                </tr>
                <tr>
                    <td>Password</td>
                    <td><input type="password" name="password" /></td>
                </tr>
            </table>
            <input type="submit" value="Login" />
        </form>
        <% String username = request.getParameter("username"); %>
        <% System.out.println("Logged In:" + username); %>
    </div>
</div>