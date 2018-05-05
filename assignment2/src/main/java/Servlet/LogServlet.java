package Servlet;


import Classes.User;
import Report.PostReport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import static DBMethods.DBTools.*;

@WebServlet(name = "LoginServlet")
public class LogServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        String username = request.getParameter("username") ;
        String password = request.getParameter("password") ;
        List<PostReport> postList;
        if(AccountExist(username,password)){
            User user = new User();
            user.setUsername(username);
            //user.setPassword(password);
            getUser(user);
            if(user.getStatus()==0){
                response.setHeader("Pragma", "No-cache");
                response.setHeader("Cache-Control", "no-cache");
                response.setDateHeader("Expires", 0);
                PrintWriter writer = response.getWriter();
                writer.print("ban");
                writer.flush();
            }else{
                postList = getPost(user);
                Set<String> set = new HashSet<String>();
                for(String friend:user.getFriends()){
                    set.add(friend);
                }
                set.add(user.getUsername());
                HttpSession session = request.getSession();
                Map<String,String> friendsmap = new HashMap<String, String>();
                Map<String,String> photosmap = new HashMap<String, String>();
                for(String person:set){
                    friendsmap.put(person,getName(person));
                    photosmap.put(person,getPhoto(person));
                }
                session.setAttribute("photomap", photosmap);
                session.setAttribute("friendsmap", friendsmap);
                session.setAttribute("user", user);
                session.setAttribute("postlist", postList);
                //response.sendRedirect("/user.jsp");
                response.setHeader("Pragma", "No-cache");
                response.setHeader("Cache-Control", "no-cache");
                response.setDateHeader("Expires", 0);
                PrintWriter writer = response.getWriter();
                writer.print("true");
                writer.flush();
            }
        }else{
            //request.setAttribute("error","tryagain");
            //RequestDispatcher rd = request.getRequestDispatcher ("/index.jsp") ;
            //rd.forward(request,response);
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            PrintWriter writer = response.getWriter();
            writer.print("false");
            writer.flush();
        }
    }
}

