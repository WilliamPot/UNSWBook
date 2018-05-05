<%--
  Created by IntelliJ IDEA.
  User: 38269
  Date: 2017/9/22
  Time: 11:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
        <div>
            <h4>Sorry, no users are banned!</h4>
        </div>

        <div>
            <a href="adminHomeServlet" class="btn btn-default">Back to Home</a>
        </div>
    </div>

</body>
</html>
