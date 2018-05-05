<%--
  Created by IntelliJ IDEA.
  User: kitty
  Date: 15/9/2017
  Time: 6:28 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
            <div class="col-md-10"></div>
           <div class="col-md-2"><a href="adminLogoutServlet">Log out</a></div>
        </div>
        <div>
            <h4>Sorry, no matching users found!</h4>
        </div>

        <div class="col-sm-2">
            <a href="adminHomeServlet" class="btn btn-default">Back to Home</a>
        </div>
    </div>
</body>
</html>
