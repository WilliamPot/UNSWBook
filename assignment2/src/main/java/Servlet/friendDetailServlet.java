package Servlet;

import Classes.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static DBMethods.DBTools.getUser;

@WebServlet(name = "friendDetailServlet")
public class friendDetailServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType ( "text/html" ) ;
        String username = request.getParameter("username") ;
        User user = new User();
        user.setUsername(username);
        getUser(user);
        request.setAttribute("friend",user);
        RequestDispatcher rd = request.getRequestDispatcher("frienddetail.jsp");
        rd.forward(request,response);
    }
}
