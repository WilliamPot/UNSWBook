package Servlet;

import Classes.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static DBMethods.DBTools.updateAccount;
import static DBMethods.DBTools.updateDetails;

@WebServlet(name = "ChangePasswordServlet")
public class ChangePasswordServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType ( "text/html" ) ;
        String password = request.getParameter("password") ;
        String cpassword = request.getParameter("cpassword") ;
        if(!password.equals(cpassword)){
            response.sendRedirect("changeps.jsp");
        }
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        user.setPassword(password);
        session.setAttribute("user",user);
        updateAccount(user);
        response.sendRedirect("user.jsp");
    }
}
