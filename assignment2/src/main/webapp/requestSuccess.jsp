<%--
  Created by IntelliJ IDEA.
  User: Chen
  Date: 2017/9/26
  Time: 16:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Success</title>
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
</head>
<body>
<h1>${requestScope.Name} and you are friends now!</h1>
<a href="index.jsp">OK</a>
</body>
</html>
