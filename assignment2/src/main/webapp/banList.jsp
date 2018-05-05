
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Banned List</title>
    <script type="text/javascript" src="resource/jquery-3.2.1.min.js"></script>
    <link rel="stylesheet" href="resource/bootstrap.css"/>
    <script src="resource/bootstrap.js"></script>
</head>
<body>
    <div class="container">
        <h2>Banned List</h2>
        <div class="row">
            <div class="col-md-10"></div>
            <div class="col-md-2"><a href="adminLogoutServlet">Log out</a></div>
        </div>
        <br>
        <form action="banListServlet">
            <button name="unban" type="submit" value="all" class="btn btn-default">Unban All</button>
        </form>
        <table class="table table-bordered">
            <tr>
                <th>Name</th>
                <th>Username</th>
                <th>Email</th>
            </tr>
            <c:forEach var="i" begin="${start}" end="${end}" >
                <tr>
                    <td><a href="detailServlet?user=${ban_list.get(i).getUsername()}">${ban_list.get(i).getName()}</a></td>
                    <td><a href="detailServlet?user=${ban_list.get(i).getUsername()}">${ban_list.get(i).getUsername()}</a></td>
                    <td><a href="detailServlet?user=${ban_list.get(i).getUsername()}">${ban_list.get(i).getEmail()}</a></td>
                </tr>
            </c:forEach>
        </table>
        <ul class="pager">
            <c:choose>
                <c:when test="${prev eq true}">
                    <li class="previous"><a href="banListServlet?start=${start-max_num_per_page}">Previous</a></li>
                </c:when>
            </c:choose>
            <c:choose>
                <c:when test="${next eq true}">
                    <li class="next"><a href="banListServlet?start=${start+max_num_per_page}">Next</a></li>
                </c:when>
            </c:choose>
        </ul>
        <div>
            <a href="adminHomeServlet" class="btn btn-default">Back to Home</a>
        </div>
    </div>

</body>
<footer></footer>
</html>
