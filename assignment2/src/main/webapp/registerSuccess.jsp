<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ page import="Servlet.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
 <script type="text/javascript" src="resource/jquery-3.2.1.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Finish Regisrtation</title>
<style>
form {
    border: 3px solid #f1f1f1;
    
}

input[type=text], input[type=password], input[type=date] {

    width: 50%;
    padding: 12px 20px;
    margin: 8px 0;
    display: inline-block;
    border: 1px solid #ccc;
    box-sizing: border-box;
    text-align: center;
}
label {
        display: inline-block;
        width: 100px;
        text-align: left;
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



.container {
    padding: 16px;
}

span.psw {
    float: right;
    padding-top: 16px;
}
</style>
</head>
<body>
<c:if test="${empty requestScope.registerName}">
 <%
  response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
  String newLocn = "signup.jsp";
  response.setHeader("Location",newLocn);
 %>
</c:if>
<br>
<h3>Congratulations, <%= ((String)request.getAttribute("registerName"))%>, your email is valid! Please finish the rest registration.</h3>
<form action="FileStoreServlet" method="post" enctype="multipart/form-data">

<!-- all input fields are required except photo,
and photo input only accepts images  -->
   

  <div class="container">
   <b><a id="invalid">${requestScope.invalid}</a></b></br>
   <input type="hidden" value="${requestScope.registerName}" name="registerName" />
   <input type="hidden" value="${requestScope.registerEmail}" name="registerEmail" />
    <label><b>Name(4-20 characters)</b></label>
    <input type="text" placeholder="Enter Your Name" oninput="namevalidate()" id="name" pattern="[0-9a-zA-Z _]{4,20}" name="name" required><b><a id="error"></a></b>
    <br>
    <label><b>Password(6-20 characters)</b></label>
    <input type="password" placeholder="Enter Your Password" pattern="[0-9a-zA-Z]{6,20}" name="psw" id="psw" required>
    <br>
    <label><b>Confirm Password</b></label>
    <input type="password" placeholder="Repeat Your Password" oninput="validate()" id="pswrepeat" required ><b><a id="demo"></a></b>
    <br>
    <label><b>Gender </b></label>
    <input type="radio" name="gender" value="M" checked> Male
    <input type="radio" name="gender" value="F" > Female
    <input type="radio" name="gender" value="O" > Other<br>
    <label><b>Birthday </b></label>
    <input type="date" name="bday"  required >
    <br>
    <label><b>Photo </b></label>
    <input type="file" name="photo" accept="image/*" required>
    <br>
    <button type="submit" title="Finish Your Registration">Finish</button>
    <button type="button" onclick="skipto()">Cancel</button>
    
  </div>


</form>

<script>
	function skipto()
	{
		window.location.href="index.jsp";
	}
</script>
<script>
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

</body>
</html> 