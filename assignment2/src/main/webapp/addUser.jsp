<%@ page import="java.util.Date" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Chen
  Date: 2017/9/26
  Time: 14:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User Detail</title>
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
        .col-md-12 {  width: 100%;}
        .col-md-11 {  width: 91.66666666666666%;}
        .col-md-10 {  width: 83.33333333333334%;}
        .col-md-9 {  width: 75%;}
        .col-md-8 {  width: 66.66666666666666%;}
        .col-md-7 {  width: 58.333333333333336%;}
        .col-md-6 {  width: 50%;}
        .col-md-5 {  width: 41.66666666666667%;}
        .col-md-4 {  width: 33.33333333333333%;}
        .col-md-3 {  width: 25%;}
        .col-md-2 {  width: 16.666666666666664%;}
        .col-md-1 {  width: 8.333333333333332%;}
    </style>
    <script>
        addfriends = function(user,target)
        {
            $.ajax({
                type: "POST",
                url: "friendRequestServlet",
                data: {username:user,targetname:target},
                success: function(response) {
                    if (response == "true") {
                        toastr.info("Friend request has been sent.");
                    }
                }
            });
            document.getElementById("uaction").innerHTML = "Pending";
        }
    </script>
</head>
<body>
<div class="container">
    <h2>Details</h2>
    <div class="row">
        <div class="col-md-10"></div>
        <div class="col-md-2"><a href="LogoutServlet">Log out</a></div>
    </div>
    <br>
    <table class="table table-bordered">
        <tr>
            <td class="col-md-2"><img src="picture${requestScope.requestTargetUser.picturepath}/photo.jpg?id=<%=new Date()%>" class="img-thumbnail" style="height:200px; width:200px"/></td>
        </tr>
        <tr>
            <td class="col-md-1" style="font-weight:bold">Username:</td><td class="col-md-3">${requestScope.requestTargetUser.username}</td>
            <td class="col-md-1" style="font-weight:bold">Name:</td><td class="col-md-2">${requestScope.requestTargetUser.name}</td>
        </tr>
        <tr>
            <td class="col-md-1" style="font-weight:bold">Gender:</td><td class="col-md-1">${requestScope.requestTargetUser.gender}</td>
            <td class="col-md-1" style="font-weight:bold">DoB:</td><td class="col-md-2">${requestScope.requestTargetUser.dateofbirth}</td>
        </tr>
        <tr>
            <td class="col-md-1" style="font-weight:bold">Email:</td><td class="col-md-3">${requestScope.requestTargetUser.email}</td>
            <td class="col-md-1" style="font-weight:bold">Relation:</td>
            <td class="col-md-3" style="font-weight:bold">
                <div id="uaction">
                <c:set var="flag" value="false"></c:set>
                <c:forEach items="${requestScope.requestTargetUser.friends}" var="obj">
                    <c:if test="${obj==sessionScope.user.username}">
                        <c:set var="flag" value="true"></c:set>
                    </c:if>
                </c:forEach>
                <%--<c:if test="${flag==true}">
                    <a href="javascript:void(0);" onclick="unlikeservlet(${status.index},${post.likesnum}-1)">Unlike</a>
                </c:if>--%>
                <c:if test="${flag==false}">
                    <c:if test="${empty requestScope.pending}">
                        <a href="javascript:void(0);" onclick="addfriends('${sessionScope.user.username}','${requestScope.requestTargetUser.username}')">Add</a>
                    </c:if>
                    <c:if test="${not empty requestScope.pending}">Pending</c:if>
                </c:if>
                <c:if test="${flag==true}">
                    Added
                </c:if>
                </div>
            </td>
        </tr>
    </table>
    <br>
    <div>
        <a href="searchFriends.jsp" class="btn btn-default">Back</a>
    </div>
</div>
</body>
</html>
