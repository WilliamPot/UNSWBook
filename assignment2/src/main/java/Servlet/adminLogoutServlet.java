package Servlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "adminLogoutServlet")
public class adminLogoutServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String resultPage = "adminLogin.jsp";
        String logoutMsg = "You have logged out!";

        //invalidate current session
        HttpSession session = request.getSession(false);
        session.removeAttribute("username");
        session.invalidate();
        request.setAttribute("logout", true);
        request.setAttribute("logoutMsg", logoutMsg);

        RequestDispatcher rd = request.getRequestDispatcher(resultPage);
        rd.forward(request, response);
    }
}
