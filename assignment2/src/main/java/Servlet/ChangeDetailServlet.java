package Servlet;

import Classes.User;
import Report.PostReport;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

import static DBMethods.DBTools.*;

@WebServlet(name = "ChangeDetailServlet")
public class ChangeDetailServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType ( "text/html" ) ;
        String name = request.getParameter("name") ;
        String gender = request.getParameter("gender") ;
        String dateofbirth = request.getParameter("bday") ;
        dateofbirth = dateofbirth.replaceAll("/","-");
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        if(NameExist(name)&&!name.equals(user.getName())){
            session.setAttribute("nameexist","This name has been used.");
            response.sendRedirect("modifyprofile.jsp");
        }else{
            user.setName(name);
            user.setGender(gender);
            user.setDateofbirth(dateofbirth);
            session.setAttribute("user",user);
            updateDetails(user);
            changeEntity(user);
            List<PostReport> postList = getPost(user);
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
            session.setAttribute("user", user);
            session.setAttribute("postlist", postList);
            response.sendRedirect("user.jsp");
        }
    }
}
