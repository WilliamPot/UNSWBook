<%--
  Created by IntelliJ IDEA.
  User: Chen
  Date: 2017/9/22
  Time: 2:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<html>
<head>
    <title>Change Password</title>
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
            width: 30%;  }
        button:hover {  opacity: 0.8;  }

        label {  display: inline-block;  width: 300px;  text-align: left;  }
        .container {  padding: 16px;  }
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
<h2><center>Change Password</center></h2>
<form action="ChangePasswordServlet">
    <label><b>Password(6-20 characters)</b></label>
    <input name="password"  id="psw" type="password" pattern="[0-9a-zA-Z]{6,20}"
                    placeholder="Enter New Password" required/></br>
    <label><b>Confirm Password</b></label>
    <input name="cpassword"  id="pswrepeat" type="password" oninput="validate()"
                   placeholder="Once more" required/></br><b><a id="demo"></a></b></br>
    <button type="submit">Submit</button>
    <button type="button" onclick="skipto()">Cancel</button>
</form>
<script>
    function skipto()
    {
        window.location.href="account.jsp";
    }
    function validate()
    {
        var psw = document.getElementById("psw").value;
        var pswrepeat = document.getElementById("pswrepeat").value;
        if(psw != pswrepeat ){
            document.getElementById("demo").innerHTML = "The input passwords should be the same!";
        }
        else{
            document.getElementById("demo").innerHTML = "";
        }

    }
</script>
</body>
</html>
