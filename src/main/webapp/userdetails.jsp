<%@ page import="entities.User" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>User details</title>
</head>
<body>
User registered successfully!
<br>
<h2>User details</h2>
<br>
<div class="table-responsive">
    <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
        <thead>
        <tr>
            <th>Id</th>
            <th>Name</th>
            <th>Surname</th>
            <th>Email</th>
            <th>Age</th>
            <th>Modify</th>
        </tr>
        </thead>
        <tbody>

        <c:forEach items="${list}" var="user">
            <tr>
                <td>${user.id}</td>
                <td>${user.firstName}</td>
                <td>${user.lastName}</td>
                <td>${user.email}</td>
                <td>${user.age}</td>
                <td>
                    <a href="userdetails.jsp?action=update">Update</a>
                    <a href="userdetails.jsp?action=delete">Delete</a>
                </td>

            </tr>
        </c:forEach>

        </tbody>
    </table>
</div>
</body>
</html>
