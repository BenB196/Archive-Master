<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="container-fluid">
    <h2 class="display-3">Sign Up</h2>
    <div class="form-group">
        <form action="${pageContext.request.contextPath}/rest/signup" method="post">
            <table style="width: 50%">
                <tr>
                    <td>First Name</td>
                    <td><input type="text" name="first_name" /></td>
                </tr>
                <tr>
                    <td>Last Name</td>
                    <td><input type="text" name="last_name" /></td>
                </tr>
                <tr>
                    <td>UserName</td>
                    <td><input type="text" name="username" /></td>
                </tr>
                <tr>
                    <td>Password</td>
                    <td><input type="password" name="password" /></td>
                </tr>
                <tr>
                    <td>Address</td>
                    <td><input type="text" name="address" /></td>
                </tr>
                <tr>
                    <td>Contact No</td>
                    <td><input type="text" name="contact" /></td>
                </tr></table>
            <input type="submit" value="Sign Up" />
        </form>
        <% String username = request.getParameter("username"); %>
        <% System.out.println("New User Added:" + username); %>
    </div>
</div>