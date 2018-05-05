<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Email Validation</title>
<style>
form {
    border: 3px solid #f1f1f1;
    /* text-align: center; */
}

input[type=text], input[type=email] {

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
label {
        display: inline-block;
        width: 300px;
        text-align: left;
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
<!-- <h2>for sign up</h2>
 -->
<form name="myform" action="RegisterServlet">
  <div class="container">
    <label><b>Username(6-32 characters)</b></label><br/>
    <input type="text" placeholder="Enter Username" oninput="usernameExist()" pattern="(?!^\d+$)[0-9a-zA-Z]{6,32}" id = "name" name="userName" required>
    <div id="error">${sessionScope.usernameexist}</div>
    <label title="Try Your UNSW Email XXXXX@ad.unsw.edu.au"><b>EmailAddress(unsw email only)</b></label><br/>
    <input type="text" id = "email" placeholder="Enter Email" name="email" oninput="emailpattern()"  pattern="[a-z0-9._%+-]+@ad.unsw.edu.au" title="Try Your UNSW Email XXXXX@ad.unsw.edu.au" required>
    <div id="error1"></div>
    <!-- <a title="Validate Your UNSW Email" onclick="validate()"><u>Validate</u></a>
    pattern="[a-z0-9._%+-]+@ad.unsw.edu.au" -->
    <br>
   
    <button type="submit" title="Email Validation">Next</button>
    <button type="button" onclick="skipto()">Cancel</button>
    
  </div>
</form>
<script type="text/javascript" src="resource/jquery-3.2.1.min.js"></script>
<script>
	function skipto()
	{
		window.location.href="index.jsp";
	}
	function usernameExist(){
        var uname =document.getElementById("name").value;
        $.ajax({
            type: "POST",
            dataType:'json',
            url: "UsernameServlet",
            data: {username:uname},
            success: function(response){
                if (response == true) {
                    document.getElementById("error").innerHTML = "This username has been used";
                } else {
                    document.getElementById("error").innerHTML = "This username can be used";
                }
            }
        });
    }
    function emailpattern(){
        var email =document.getElementById("email").value;
        $.ajax({
            type: "POST",
            dataType:'json',
            url: "EmailServlet",
            data: {email:email},
            success: function(response){
                if (response == true) {
                    document.getElementById("error1").innerHTML = "Valid";
                } else {
                    document.getElementById("error1").innerHTML = "Incorrect format";
                }
            }
        });
    }

</script> 
</body>
</html>

