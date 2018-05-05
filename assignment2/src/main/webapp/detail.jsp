<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<html>

<head>
    <title>Details</title>
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
</head>
<body>
<div class="container">
    <h2>Details</h2>
    <div class="row">
        <div class="col-md-3"><a href="adminHomeServlet" class="btn btn-default">Back to Home</a></div>
        <div class="col-md-7"></div>
        <div class="col-md-2"><a href="adminLogoutServlet">Log out</a></div>
    </div>
    <br>
    <table class="table table-bordered">
        <tr>
            <td class="col-md-2"><img src="picture${User.picturepath}/photo.jpg?id=<%=new Date()%>" class="img-thumbnail" style="height:200px; width:200px"/></td>
        </tr>
        <tr>
            <td class="col-md-1" style="font-weight:bold">Username:</td><td class="col-md-3">${User.username}</td>
            <td class="col-md-1" style="font-weight:bold">Name:</td><td class="col-md-2">${User.name}</td>
        </tr>
        <tr>
            <td class="col-md-1" style="font-weight:bold">Gender:</td><td class="col-md-1">${User.gender}</td>
            <td class="col-md-1" style="font-weight:bold">DoB:</td><td class="col-md-2">${User.getDateofbirth()}</td>
        </tr>
        <tr>
            <td class="col-md-1" style="font-weight:bold">Email:</td><td class="col-md-3">${User.email}</td>
            <c:choose>
                <c:when test="${User.status eq 1}">
                    <td class="col-md-1" style="font-weight:bold">Status: </td><td class="col-md-3">free</td>
                </c:when>
            </c:choose>
            <c:choose>
                <c:when test="${User.status eq 0}">
                    <td class="col-md-1" style="font-weight:bold">Status: </td><td class="col-md-3">banned</td>
                </c:when>
            </c:choose>
        </tr>
    </table>

    <form action="changeStatusServlet">
        <input type="hidden" name="start" value="${start}"/>
        <c:choose>
            <c:when test="${ban eq true}">
                <button name="user" type="submit" value="${User.getUsername()}" class="btn btn-default">Unban</button>
            </c:when>
        </c:choose>
        <c:choose>
            <c:when test="${ban eq false}">
                <button name="user" type="submit" value="${User.getUsername()}" class="btn btn-default">Ban</button>
            </c:when>
        </c:choose>
    </form>
</div>

<div class ="container">
    <h2>History</h2>
    <table class="table table-condensed">
        <tr>
            <th class="col-md-1">Type</th>
            <th class="col-md-2">Message</th>
            <th class="col-md-1">Date</th>
            <th class="col-md-5">Picture</th>
            <th class="col-md-1">TargetUser</th>
            <th class="col-md-1">Action</th>
            <th class="col-md-1">Sensitive</th>
        </tr>
        <c:if test="${not empty Report}">
        <c:forEach var="i" begin="${start}" end="${end}">
            <tr>
                <td class="col-md-1">${Report.get(i).getType()}</td>
                <td class="col-md-2">${Report.get(i).getMessage()}</td>
                <td class="col-md-1">${Report.get(i).getDateToString()}</td>
                <td class="col-md-5">
                <c:if test="${not empty Report.get(i).getPicturePath()}">
                <img src="picture${Report.get(i).getPicturePath()}?id=<%=new Date()%>" class="img-thumbnail" style="width:600px"/>
                </c:if>
                </td>

                <td class="col-md-1">${Report.get(i).getTargetUser()}</td>
                <td class="col-md-1">${Report.get(i).getAction()}</td>
                <td class="col-md-1">${Report.get(i).getSensitive()}</td>
            </tr>
        </c:forEach>
        </c:if>
    </table>
    <ul class="pager">
        <c:choose>
            <c:when test="${prev eq true}">
                <li class="previous"><a href="detailServlet?user=${User.getUsername()}&start=${start-max_num_per_page}">Previous</a></li>
            </c:when>
        </c:choose>
        <c:choose>
            <c:when test="${next eq true}">
                <li class="next"><a href="detailServlet?user=${User.getUsername()}&start=${start+max_num_per_page}">Next</a></li>
            </c:when>
        </c:choose>
    </ul>
    <c:choose>
        <c:when test="${search_key != null}">
            <div class="col-sm-2">
                <a href="adminSearchServlet?search_key=${search_key}" class="btn btn-default">Back to Results</a>
            </div>
        </c:when>
    </c:choose>
</div>
</body>
<footer><br></footer>
</html>
