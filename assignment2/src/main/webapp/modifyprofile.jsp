<%--
  Created by IntelliJ IDEA.
  User: Chen
  Date: 2017/9/22
  Time: 2:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<html>
<head>
    <title>User Profile</title>
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
        form {  border: 3px solid #f1f1f1;  text-align: center;  }

        input[type=text], input[type=password] {  width: 50%;  padding: 12px 20px;
            margin: 8px 0;  display: inline-block;  border: 1px solid #ccc;
            box-sizing: border-box;  text-align: center;  }
        button {  background-color: #778899;  color: white;
            padding: 14px 20px;  margin: 8px 0;  border: none;  cursor: pointer;
            width: 10%;  }
        button:hover {  opacity: 0.8;  }

        label {  display: inline-block;  width: 100px;  text-align: left;  }
        .container {  width:100%; padding: 16px;  }
    </style>
    <style>
        input[type=text], input[type=password] {  width: 20%;  padding: 12px 20px;
            margin: 8px 0;  display: inline-block;  border: 1px solid #ccc;
            box-sizing: border-box;  text-align: center;  }
        button {  background-color: #778899;  color: white;
            padding: 14px 20px;  margin: 8px 0;  border: none;  cursor: pointer;
            width: 30%;  }
        button:hover {  opacity: 0.8;  }

        label {  display: inline-block;  width: 100px;  text-align: left;  }
        .container {  padding: 16px;  }
        td {  text-align:center;  }
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
<div class="container">
<form action="ChangeDetailServlet">
    <label><b>Name(4-20 characters)</b></label>
    <input type="text" placeholder="Enter Your Name" oninput="namevalidate()" pattern="[0-9a-zA-Z _]{4,20}" name="name" id="name" required>
    <b><a id="error">${sessionScope.nameexist}</a></b><br/>
    <label><b>Gender </b></label>
    <input type="radio" name="gender" value="M" checked> Male
    <input type="radio" name="gender" value="F" > Female
    <input type="radio" name="gender" value="O" > Other<br>
    <label><b>Birthday </b></label>
    <input type="date" name="bday"  required ><br/>
    <button type="submit" title="Modify Profile">Submit</button>
    <button type="button" onclick="skipto()">Cancel</button>
    <br>
</form>
<script>
    function skipto()
    {
        window.location.href="profile.jsp";
    }
    function namevalidate()
    {
        var n = document.getElementById("name").value;
        $.ajax({
            type: "GET",
            dataType:'json',
            url: "NameServlet",
            data: {name:n},
            success: function(response){
                if (response==false) {
                    document.getElementById("error").innerHTML = "This name has been used";
                } else {
                    document.getElementById("error").innerHTML = "This name can be used";
                }
            }
        });
    }
</script>
</div>
</body>
</html>
