<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Product CMS</title>
    <link rel="stylesheet"
          href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
          crossorigin="anonymous">
</head>
<body>

<header>
    <nav class="navbar navbar-expand-md"
         style="background-color: palegreen">
        <div>
            <a href="https://www.javaguides.net" class="navbar-brand"> Product
                List </a>
        </div>

        <ul class="navbar-nav">
            <li><a href="<%=request.getContextPath()%>/list"
                   class="nav-link">Admin list</a></li>
        </ul>
    </nav>
</header>
<br>

<div class="row">
    <div class="container">
<%--        <c:if test="${message != null}">
            <div class="alert alert-success" *ngIf='message'><c:out value="${message}"/></div>
        </c:if>--%>

        <h3 class="text-center">Products System</h3>
        <hr>
        <form action="filter" method="get">
            <div class="form-inline">
                <a href="<%=request.getContextPath()%>/new" class="btn btn-primary mr-3">Add product</a>
                <fieldset class="form-group">
                    <label class="mr-1">Filter</label> <input type="text"
                                                              value="<c:out value='${product.brand}' />"
                                                              class="form-control"
                                                              name="name">
                </fieldset>
                <button type="submit" class="btn btn-primary ml-3">Search</button>
            </div>
        </form>
        <br>
        <table class="table table-bordered">
            <thead>
            <tr>
                <th>ID</th>
                <th>Item</th>
                <th>Brand</th>
                <th>Item</th>
                <th>Controls</th>
            </tr>
            </thead>
            <tbody>
            <!--   for (Todo todo: todos) {  -->
            <c:forEach var="product" items="${listProduct}">
                <tr>
                    <td><c:out value="${product.id}"/></td>
                    <td><c:out value="${product.brand}"/></td>
                    <td><c:out value="${product.name}"/></td>
                    <td><c:out value="${product.price}"/></td>
                    <td><a href="edit?id=<c:out value='${product.id}' />">Edit</a>
                        &nbsp;&nbsp;&nbsp;&nbsp; <a
                                href="delete?id=<c:out value='${product.id}' />">Delete</a></td>
                </tr>
            </c:forEach>
            <!-- } -->
            </tbody>
        </table>
    </div>
</div>
</body>
</html>
