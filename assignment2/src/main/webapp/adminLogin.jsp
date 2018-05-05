<%--
  Created by IntelliJ IDEA.
  User: kitty
  Date: 13/9/2017
  Time: 9:57 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Admin Login</title>
    <script type="text/javascript" src="resource/jquery-3.2.1.min.js"></script>
    <link rel="stylesheet" href="resource/bootstrap.css"/>
    <script src="resource/bootstrap.js"></script>
</head>
<body>
<div class="container">
    <h2><center>UNSWBook Admin Login</center></h2>
    <c:choose>
        <c:when test="${err eq true}">
            <h5>${errMsg}</h5>
        </c:when>
    </c:choose>
    <c:choose>
        <c:when test="${logout eq true}">
            <h5>${logoutMsg}</h5>
        </c:when>
    </c:choose>
    <div class="row">
        <div class="col-md-10"></div>
        <div class="col-md-2"><a href="index.jsp">User Sign in</a></div>
    </div>
    <form name ="form" action="adminLoginServlet" method="POST" class="form-horizontal" >
        <div class="form-group">
            <label class="control-label col-sm-2" for="adminUsername">AdminUsername:</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="adminUsername" name="adminUsername">
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-sm-2" for="adminPw">AdminPassword:</label>
            <div class="col-sm-10">
                <input type="password" class="form-control" id="adminPw" name="adminPw">
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-10"></div>
            <div class="col-sm-2">
                <input type="submit" class="form-control" name="login" value="Login">
            </div>
        </div>
    </form>
</div>
</body>
</html>
