package Servlet;

import Classes.User;
import ForAdminister.Entry;
import io.goeasy.GoEasy;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

import static DBMethods.DBTools.*;

@WebServlet(name = "friendConfirmServlet")
public class friendConfirmServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String ID = request.getParameter("Id");
        String Name = request.getParameter("Name");
        String TName = request.getParameter("TName");
        User user = new User();
        user.setUsername(Name);
        getUser(user);
        User tuser = new User();
        tuser.setUsername(TName);
        getUser(tuser);
        if(ID == null || Name == null||TName==null||user.getFriends().contains(TName)){
            request.getRequestDispatcher("index.jsp").forward(request,response);
            return ;
        }
        user.addFriend(TName);
        tuser.addFriend(Name);
        updateFriend(user);
        updateFriend(tuser);
        insertGraph(user.getUsername(),"friendof",tuser.getUsername());
        insertGraph(tuser.getUsername(),"friendof",user.getUsername());
        Entry history = new Entry();
        Date now = new Date();
        history.setDate(now);
        history.setTargetUser(Name);
        history.setType("request");
        history.setAction("accept");
        insertHistory(history,TName);
        GoEasy goEasy = new GoEasy("https://rest-singapore.goeasy.io", "BC-0908a32e047540bea70d3d3a39bb75fe");
        goEasy.publish(Name + "_like", "4444"+TName+" has accepted your friend request.");
        goEasy.publish(TName + "_like", "4444"+Name+" is your friend now.");
        request.setAttribute("Name", Name);
        request.getRequestDispatcher("requestSuccess.jsp").forward(request,response);
    }
}
