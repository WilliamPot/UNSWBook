<%--
  Created by IntelliJ IDEA.
  User: kitty
  Date: 15/9/2017
  Time: 6:26 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Search results</title>
    <script type="text/javascript" src="resource/jquery-3.2.1.min.js"></script>
    <link rel="stylesheet" href="resource/bootstrap.css"/>
    <script src="resource/bootstrap.js"></script>
</head>
<body>
    <div class="container">
        <h2>Search results</h2>
        <div class="row">
            <div class="col-sm-2">
                <a href="adminHomeServlet" class="btn btn-default">Back to Home</a>
            </div>
            <div class="col-md-8"></div>
            <div class="col-md-2"><a href="adminLogoutServlet">Log out</a></div>
        </div>
        <table class="table table-bordered">
            <tr><th>Name</th><th>Username</th><th>Email</th></tr>
                <c:forEach var="i" begin="${start}" end="${end}" >
                    <tr>
                        <td class="col-md-4"><a href="detailServlet?user=${results[i].getUsername()}">${results[i].getName()}</a></td>
                        <td class="col-md-4"><a href="detailServlet?user=${results[i].getUsername()}">${results[i].getUsername()}</a></td>
                        <td class="col-md-4"><a href="detailServlet?user=${results[i].getUsername()}">${results[i].getEmail()}</a></td>
                    </tr>
                </c:forEach>
        </table>
        <ul class="pager">
            <c:choose>
                <c:when test="${prev eq true}">
                    <li class="previous"><a href="adminSearchServlet?search_key=${search_key}&page=${page-1}">Previous</a></li>
                </c:when>
            </c:choose>
            <c:choose>
                <c:when test="${next eq true}">
                    <li class="next"><a href="adminSearchServlet?search_key=${search_key}&page=${page+1}">Next</a></li>
                </c:when>
            </c:choose>
        </ul>
    </div>
</body>
</html>
