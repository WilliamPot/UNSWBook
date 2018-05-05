<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<html>
<head>
    <title>${sessionScope.user.name}</title>
    <style type="text/css">
        td {  text-align:center;  }
        div{  padding:20px;  margin:0px;  }
        .comments {  width:80%;}
        .head{  height:130px;  width:100%;  text-align:center;  border-bottom:2px solid black;
            background-image:url(picture/Webpic/head.png);}
        .left{  width:21%;  min-height:800px;  float:left;  text-align:left;
            border-right:2px solid black;  border-left:2px solid black;
            border-bottom:2px solid black;  margin:0;  }
        .postupload{width:100%; min-height:60px; text-align:left;}
        .right{  width:75%;  min-height:800px;  float:right;  margin:0;  text-align:left;  }
        .postleft{  width:15%;   float:left; margin:0;  text-align:left; margin:3px; }
        .postright{  width:80%;   float:right;  margin:0;  text-align:left; margin:5px; }
        .nav {  height: 40px;  width: 100%;  background-color: black;  }
        .nav ul {  margin: 0 0 0 10px;  padding: 0px;  font-size: 20px;  color: #FFF;
            line-height: 0px;  whitewhite-space: nowrap;  }
        .nav li {  list-style-type: none;  display:inline;  margin-left:80px;  }
        .nav li a {  text-decoration: none;  font-family: Arial, Helvetica, sans-serif;
            padding: 7px 10px;  color: #FFF;  }
        .nav li a:hover {  color: yellow;  background-color: black;  }
        .newpost{text-align:center; width:100%;}
    </style>
    <style>
        input[type=submit] {  background-color: #778899;  color: white;
            padding: 14px 20px;  margin: 8px 0;  border: none;  cursor: pointer;
            width: 15%;  }
        button:hover {  opacity: 0.8;  }
    </style>
    <script type="text/javascript" src="resource/jquery-3.2.1.min.js"></script>
    <link rel="stylesheet" href="resource/bootstrap.css"/>
    <script src="resource/bootstrap.js"></script>
    <link href="resource/toastr.css" rel="stylesheet"/>
    <script src="resource/toastr.js"></script>
    <script type="text/javascript" src="https://cdn-singapore.goeasy.io/goeasy.js"></script>
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
    <script>
        function likeservlet(index,param)
        {
            $.ajax({
                type: "POST",
                //dataType:'json',
                url: "likeServlet",
                data: {id:index,like:"${sessionScope.user.username}"},
                success: function(response){
                    document.getElementById(index+"_likesum").innerHTML = response;
                }
            });
            obj = document.getElementById(index);
            obj.innerHTML= param;
            obj = document.getElementById("href_"+index);
            var parama = param-1;
            obj.innerHTML= "<a href=\"javascript:void(0);\" onclick=\"unlikeservlet("+index+","+parama+")\">Unlike</a>";
        }
        function unlikeservlet(index,param)
        {
            $.ajax({
                type: "POST",
                //dataType:'json',
                url: "unlikeServlet",
                data: {id:index,unlike:"${sessionScope.user.username}"},
                success : function(response){
                    document.getElementById(index+"_likesum").innerHTML = response;
                }
            });
            obj = document.getElementById(index);
            obj.innerHTML= param;
            obj = document.getElementById("href_"+index);
            var parama = param+1;
            obj.innerHTML= "<a href=\"javascript:void(0);\" onclick=\"likeservlet("+index+","+parama+")\">Like</a>";
        }
    </script>
</head>
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
                    toastr.info(message.substring(4,len));
                    document.getElementById('newpost').innerHTML = "<a href='user.jsp'>Someone likes your post.</a>"
                }else if(message.substring(0,4) == "2222"){
                    document.getElementById('newpost').innerHTML = "<a href='user.jsp'>You have something new to see.</a>";
                }else if(message.substring(0,4) == "3333"){
                    toastr.info(message.substring(4,len));
                }
                else if(message.substring(0,4) == "4444"){
                    toastr.info(message.substring(4,len));
                    document.getElementById('newpost').innerHTML = "<a href='user.jsp'>You have something new to see.</a>";
                }else if(message.substring(0,4) == "6666"){
                    window.location.href = "LogoutServlet";
                }
            }
        });
    </script>
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
        <li><a href="WholeGraphServlet">Graph</a></li>
    </ul>
</div>
<div class="left">
    <ul class="list-group">
        <li class="list-group-item"><img src="picture${sessionScope.user.picturepath}/photo.jpg?id=<%=new Date()%>"
                 class="img-thumbnail" style="height:200px; width:200px"/></li>
        <li class="list-group-item">Name: ${sessionScope.user.name}</li>
        <li class="list-group-item">Gender: ${sessionScope.user.gender}</li>
        <li class="list-group-item">DoB: ${sessionScope.user.dateofbirth}</li>
    </ul>
</div>
<div class="right">
    <ul class="list-group">
        <li class="list-group-item">
            <div class="postupload">
                <form action="uploadServlet" enctype="multipart/form-data" method="POST">
                    <textarea class="comments" rows="6" name="text" maxlength="999" placeholder="What's on your mind?"></textarea><br/>
                    <input type="file"name="picture"/><br/>
                    <input type="submit"value="Share"/>
                </form>
            </div>
        </li>
    </ul>
    <div class = "newpost" id="newpost"></div>
    <ul class="list-group">
        <c:forEach items="${sessionScope.postlist}" varStatus="status" var="post" >
            <li class="list-group-item">
                <div class="postleft">
                    ${sessionScope["friendsmap"][post.username]} <br/>
                     <img src="picture${sessionScope["photomap"][post.username]}/photo.jpg?id=<%=new Date()%>"
                             class="img-thumbnail" style="height:100px; width:100px"/>
                </div>
                <div class="postright">
                        ${post.text} <br/>
                    <c:if test="${not empty post.picturepath}">
                    <img src="picture${post.picturepath}?id=<%=new Date()%>" class="img-thumbnail" style="width:500px"/> <br/>
                    </c:if>
                            <table>
                                <tr>
                                    <td>
                                        <ul class="list-group">
                                            <li class="list-group-item" id="${status.index}">
                                                ${post.likesnum}
                                            </li>
                                        </ul>
                                    </td>
                                    <td>
                                        <ul class="list-group">
                                            <li class="list-group-item" id="${status.index}_likesum">
                                            ${post.wholikesstring}
                                            </li>
                                        </ul>
                                    </td>
                                    <td>
                                        <ul class="list-group">
                                            <li class="list-group-item" id="href_${status.index}">
                                                <c:set var="flag" value="false"></c:set>
                                                <c:forEach items="${post.wholikes}" var="obj">
                                                    <c:if test="${obj==sessionScope.user.username}">
                                                        <c:set var="flag" value="true"></c:set>
                                                    </c:if>
                                                </c:forEach>
                                                <c:if test="${flag==true}">
                                                    <a href="javascript:void(0);" onclick="unlikeservlet(${status.index},${post.likesnum}-1)">Cancel</a>
                                                </c:if>
                                                <c:if test="${flag==false}">
                                                    <a href="javascript:void(0);" onclick="likeservlet(${status.index},${post.likesnum}+1)">Like</a>
                                                </c:if>
                                            </li>
                                        </ul>
                                    </td>
                                </tr>
                            </table><br/><br/>
                    ${post.date}
                </div>
            </li>
        </c:forEach>
    </ul>
</div>
</body>
</html>