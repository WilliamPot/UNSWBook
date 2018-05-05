package Servlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static DBMethods.DBTools.AdminAccountExist;

@WebServlet(name = "adminLoginServlet")
public class adminLoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //get input details
        String adminUsername = request.getParameter("adminUsername");
        String adminPw = request.getParameter("adminPw");
        //set parameters
        String resultPage = "adminHomeServlet";
        String errMsg = "Invalid login details!";
        request.setAttribute("err", false);

        if (AdminAccountExist(adminUsername,adminPw)){
            //create new session
            HttpSession session = request.getSession(true);
            session.setAttribute("username",adminUsername);
            //forward to homepage
            response.sendRedirect(resultPage);
        } else {
            //display error message on Login page
            resultPage = "adminLogin.jsp";
            request.setAttribute("err", true);
            request.setAttribute("errMsg", errMsg);
            RequestDispatcher rd = request.getRequestDispatcher(resultPage);
            rd.forward(request, response);
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
