<%@ page language="java" import="java.util.*" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Successfully</title>
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
                "closeButton":false,//??????
                "debug":false,//??debug
                "positionClass":"toast-bottom-right",//?????
                "showDuration":"500",//?????
                "hideDuration":"1000",//?????
                "timeOut":"5000",//?????
                "extendedTimeOut":"1000",//????
                "showEasing":"swing",//??????????
                "hideEasing":"linear",//??????????
                "showMethod":"fadeIn",//????????
                "hideMethod":"fadeOut"//????????
            };
    </script>
</head>
<body>
<h1>Email is sent successfully!</h1>
<a href="index.jsp">OK</a>
</body>
</html>