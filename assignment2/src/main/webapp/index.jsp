<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <script type="text/javascript" src="resource/jquery-3.2.1.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="resource/bootstrap.css"/>
    <script src="resource/bootstrap.js"></script>
<title>Login</title>
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
form {
    border: 3px solid #f1f1f1;
    text-align: center;
}

input[type=text], input[type=password] {

    width: 50%;
    padding: 12px 20px;
    margin: 8px 0;
    display: inline-block;
    border: 1px solid #ccc;
    box-sizing: border-box;
    text-align: center;
}

button {
    background-color: #778899;
    color: white;
    padding: 14px 20px;
    margin: 8px 0;
    border: none;
    cursor: pointer;
    width: 30%;

}

button:hover {
    opacity: 0.8;
}

label {
        display: inline-block;
        width: 100px;
        text-align: left;
}



.container {
    padding: 16px;
}

span.psw {
    float: right;
    padding-top: 16px;
}
</style>
  <script>
      function validate()
      {
          var psw = document.getElementById("psw").value;
          var uname = document.getElementById("uname").value;
          $.ajax({
              type: "GET",
              url: "LogServlet",
              data: {username:uname,password:psw},
              success: function(response){
                  if (response=="false") {
                      document.getElementById("demo").innerHTML = "Invalid username or incorrect password, please try again.";
                  } else if(response=="true"){
                      window.location.href = "user.jsp";
                  }else if(response=="ban"){
                      document.getElementById("demo").innerHTML = "This user is disabled by Administer, \n" +
                          "please contact us to unlock it.";
                  }
              }
          });
      }
  </script>
</head>
<body>
<h4 id="demo" style="text-align: center"></h4>
<c:if test="${not empty sessionScope.user}">
  <%
    response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
    String newLocn = "user.jsp";
    response.setHeader("Location",newLocn);
  %>
</c:if>
<form>
  <div class="container">
      <h2><center>UNSWBook User Login</center></h2>
      <div class="row">
          <div class="col-md-10"></div>
          <div class="col-md-2"><a href="adminLogin.jsp">Administer Sign in</a></div>
      </div>
    <label><b>Username</b></label>
    <input type="text" placeholder="Enter Username" id="uname" required><br>

    <label><b>Password</b></label>
    <input type="password" placeholder="Enter Password" id="psw" required>
    <br>
      <div class="row">
          <div class="col-md-6"></div>
          <div class="col-md-2"><a href="signup.jsp">Registration</a></div>
          <div class="col-md-4"></div>
      </div>
    <br>
    <button type="button" onclick="validate()">Sign In</button>
  </div>
</form>
</body>
</html>