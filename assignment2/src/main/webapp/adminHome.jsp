<%--
  Created by IntelliJ IDEA.
  User: kitty
  Date: 13/9/2017
  Time: 10:47 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Admin Homepage</title>
    <script type="text/javascript" src="resource/jquery-3.2.1.min.js"></script>
    <link rel="stylesheet" href="resource/bootstrap.css"/>
    <script src="resource/bootstrap.js"></script>
</head>
<body>

    <div class="container">
        <h2>Admin Homepage</h2>
        <div class="row">
            <div class="col-md-8"></div>
            <div class="col-md-2"><a href="banListServlet">Banned List</a></div>
            <div class="col-md-2"><a href="adminLogoutServlet">Log out</a></div>
        </div>
        <form class="form-inline" action="adminSearchServlet" onsubmit="return validSearch()">
            <div class="form-group">
                <input type="text" class="form-control" name="search_key" id="search_key" placeholder="Search for users">
            </div>
                <button type="submit" name="search" class="btn btn-default">
                    <span class="glyphicon glyphicon-search"></span> Search
                </button>
        </form>
        <script>
            function validSearch(){
                if (document.getElementById("search_key").value === "" ){
                    alert ( "Please specify search content!" );
                    return false;
                }
                return true;
            }
        </script>
        <div class="btn-group">
            <a href="adminHomeServlet?num=10"  class="btn btn-link">10</a>
            <a href="adminHomeServlet?num=100" class="btn btn-link">100</a>
            <a href="adminHomeServlet?num=1000" class="btn btn-link">1000</a>
            <a href="adminHomeServlet?num=all" class="btn btn-link">All</a>
        </div>
        <table class="table table-bordered">
            <tr><th>Name</th><th>Username</th><th>Email</th></tr>
                <c:forEach var="i" begin="${start}" end="${end}">
                <tr>
                    <td class="col-md-4"><a href="detailServlet?user=${UserDetail[i].username}">${UserDetail[i].getName()}</a></td>
                    <td class="col-md-4"><a href="detailServlet?user=${UserDetail[i].username}">${UserDetail[i].getUsername()}</a></td>
                    <td class="col-md-4"><a href="detailServlet?user=${UserDetail[i].username}">${UserDetail[i].getEmail()}</a></td>
                </tr>
            </c:forEach>
        </table>
    </div>
</body>
</html>
