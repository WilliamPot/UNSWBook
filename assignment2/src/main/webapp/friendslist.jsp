<%--
  Created by IntelliJ IDEA.
  User: Chen
  Date: 2017/9/27
  Time: 21:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<html>
<head>
    <title>Friends List</title>
    <script type="text/javascript" src="resource/jquery-3.2.1.min.js"></script>
    <link rel="stylesheet" href="resource/bootstrap.css"/>
    <script src="resource/bootstrap.js"></script>
    <link href="resource/toastr.css" rel="stylesheet"/>
    <script src="resource/toastr.js"></script>
    <script type="text/javascript" src="https://cdn-singapore.goeasy.io/goeasy.js"></script>
    <script type="text/javascript">
        var goeasy = new GoEasy({appkey: 'BC-0908a32e047540bea70d3d3a39bb75fe'});
        goeasy.subscribe({
            channel:"${sessionScope.user.username}_like",
            onMessage:function(result){
                var message= result.content;
                var len = message.length;
                $.ajax({
                    type: "POST",
                    dataType:'json',
                    url: "updatePostServlet"
                });
                if(message.substring(0,4) == "1111") {
                    toastr.info(message.substring(4, len));
                } else if(message.substring(0,4) == "3333"){
                    toastr.info(message.substring(4,len));
                }
                else if(message.substring(0,4) == "4444"){
                    toastr.info(message.substring(4,len));
                }else if(message.substring(0,4) == "6666"){
                    window.location.href = "LogoutServlet";
                }
            }
        });
    </script>
    <script type="text/javascript">
        toastr.options=
            {
                "closeButton":false,//显示关闭按钮
                "debug":false,//启用debug
                "positionClass":"toast-bottom-right",//弹出的位置
                "showDuration":"500",//显示的时间
                "hideDuration":"1000",//消失的时间
                "timeOut":"5000",//停留的时间
                "extendedTimeOut":"1000",//控制时间
                "showEasing":"swing",//显示时的动画缓冲方式
                "hideEasing":"linear",//消失时的动画缓冲方式
                "showMethod":"fadeIn",//显示时的动画方式
                "hideMethod":"fadeOut"//消失时的动画方式
            };
    </script>
    <style>
        form {  text-align: center;  }
        .container {
            width:100%; padding: 16px;
        }
    </style>
    <style>
        button {  background-color: #778899;  color: white;
            padding: 14px 20px;  margin: 8px 0;  border: none;  cursor: pointer;
            width: 30%;  }
        button:hover {  opacity: 0.8;  }
        div{  padding:20px;  margin:0px;  }
        .head{  height:130px;  width:100%;  text-align:center;  border-bottom:2px solid black;
            background-image:url(picture/Webpic/head.png);}
        .nav {  height: 40px;  width: 100%;  background-color: black;  }
        .nav ul {  margin: 0 0 0 10px;  padding: 0px;  font-size: 20px;  color: #FFF;
            line-height: 0px;  whitewhite-space: nowrap;  }
        .nav li {  list-style-type: none;  display:inline;  margin-left:80px;  }
        .nav li a {  text-decoration: none;  font-family: Arial, Helvetica, sans-serif;
            padding: 7px 10px;  color: #FFF;  }
        .nav li a:hover {  color: yellow;  background-color: black;  }
    </style>
</head>
<body>
<c:if test="${empty sessionScope.user}">
    <%
        response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
        String newLocn = "Logout.jsp";
        response.setHeader("Location",newLocn);
    %>
</c:if>
<div class="head">
    <div class="row">
        <div class="col-md-10"></div>
        <div class="col-md-2"><a href="LogoutServlet">Log out</a></div>
    </div>
</div>
<div class="nav">
    <ul>
        <li><a href="user.jsp">Home</a></li>
        <li><a href="SearchServlet">Search</a></li>
        <li><a href="friendslist.jsp">Friends List</a></li>
        <li><a href="profile.jsp">Personal Profile</a></li>
        <li><a href="account.jsp">Account Settings</a></li>
        <li><a href="graph.jsp">Graph</a></li>
    </ul>
</div>
<div class="container">
    <c:if test="${not empty sessionScope.user.getFriends()}">
        <table class="table table-bordered">
            <tr><th>Username</th></tr>
            <c:forEach items="${sessionScope.user.getFriends()}" varStatus="status" var="friend" >
                <tr><td><a href="friendDetailServlet?username=${friend}">${friend}</a></td></tr>
            </c:forEach>
        </table>
    </c:if>
</div>
</body>
</html>
