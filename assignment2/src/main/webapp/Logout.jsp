<%--
  Created by IntelliJ IDEA.
  User: Chen
  Date: 2017/9/21
  Time: 19:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Goodbye</title>
</head>
<body>
You have logged out, redirect to welcome page in 2 seconds
<%
    response.setHeader("Refresh", "3;URL=index.jsp");
%>
</body>
</html>
