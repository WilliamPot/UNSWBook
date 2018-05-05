package Servlet;

import Classes.User;
import io.goeasy.GoEasy;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Set;

import static DBMethods.DBTools.UsernameExist;
import static DBMethods.DBTools.getUser;
import static DBMethods.DBTools.updateEmail;

@WebServlet(name = "NewEmailValidServlet")
public class NewEmailValidServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String registerID = request.getParameter("Id");
        String userName = request.getParameter("Name");
        String newEmail = request.getParameter("Email");
        if(registerID == null || userName == null){
            request.getRequestDispatcher("index.jsp").forward(request,response);
            return ;
        }
        updateEmail(userName,newEmail);
 /*       String registerName = (String)request.getSession().getAttribute(registerID);

        if(registerName == null || registerName.equals("")){
            request.getRequestDispatcher("/index.jsp").forward(request,response);
            return ;
        }   */
        HttpSession session = request.getSession();
        if(session.getAttribute("user")!=null){
            User user = (User)session.getAttribute("user");
            getUser(user);
            GoEasy goEasy = new GoEasy("https://rest-singapore.goeasy.io", "BC-0908a32e047540bea70d3d3a39bb75fe");
            Set<String> friends = user.getFriends();
            for(String friend:friends){
                goEasy.publish(friend + "_like", "5555");
            }
        }
        response.sendRedirect("changeEmailSuccess.jsp");
    }
}
