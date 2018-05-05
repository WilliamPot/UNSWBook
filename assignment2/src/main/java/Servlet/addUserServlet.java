package Servlet;

import Classes.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

import static DBMethods.DBTools.getPending;

@WebServlet(name = "addUserServlet")
public class addUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        ArrayList<User> ul= (ArrayList<User>) session.getAttribute("searchlist");
        int index_ =  Integer.parseInt(request.getParameter("index"));
        User user_ = ul.get(index_);
        User me = (User)session.getAttribute("user");
        request.setAttribute("requestTargetUser",user_);
        if(getPending(me.getUsername(),user_.getUsername())||getPending(user_.getUsername(),me.getUsername())) {
            request.setAttribute("pending", "true");
        }
        session.setAttribute("searchlist",null);
        RequestDispatcher rd = request.getRequestDispatcher ("addUser.jsp") ;
        rd.forward(request,response);
    }
}
