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
import java.util.*;

import static DBMethods.DBTools.*;

@WebServlet(name = "updatePostServlet")
public class updatePostServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType ( "text/html" ) ;
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        getUser(user);
        Set<String> set = new HashSet<String>();
        for(String friend:user.getFriends()){
            set.add(friend);
        }
        set.add(user.getUsername());
        Map<String,String> friendsmap = new HashMap<String, String>();
        Map<String,String> photosmap = new HashMap<String, String>();
        for(String person:set){
            friendsmap.put(person,getName(person));
            photosmap.put(person,getPhoto(person));
        }
        session.setAttribute("photomap", photosmap);
        session.setAttribute("friendsmap", friendsmap);
        List<PostReport> postList = getPost(user);
        session.setAttribute("postlist",postList);
    }
}
