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
import java.util.*;

import static DBMethods.DBTools.getAllUsername;
import static DBMethods.DBTools.getUser;


@WebServlet(name = "adminHomeServlet")
public class adminHomeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        String resultPage = "adminHome.jsp";
        //set parameters
        Map<Integer, User> UserDetail = new HashMap<Integer, User>();
        int rec_length = 0;
        //define num of users display on page
        int start = 0;
        int end = 9;
        List<String> userlist = new ArrayList<String>();
        if (session.getAttribute("UserDetail") == null) {
                //store users detail into hash map
                userlist = getAllUsername();
                for (int i=0; i<userlist.size(); i++){
                    User user = new User();
                    user.setUsername(userlist.get(i));
                    getUser(user);
                    UserDetail.put(i,user);
                }
                rec_length = UserDetail.size();
                //store in session
                session.setAttribute("UserDetail", UserDetail);
                session.setAttribute("rec_length", rec_length);
        } else {
            //retrieve details in current session
            UserDetail = (HashMap)session.getAttribute("UserDetail");
            rec_length = Integer.parseInt(session.getAttribute("rec_length").toString());

            //***************newly added************************
            //removed previous search results and search_key
            if (session.getAttribute("search_key") != null){
                session.removeAttribute("search_key");
            }
            if (session.getAttribute("results") != null){
                session.removeAttribute("results");
            }
            //**************************************************
        }

        if (request.getParameter("num") != null) {
            //get display_num
            if (request.getParameter("num").equals("all")) {
                end = rec_length-1;
            } else {
                end = Integer.parseInt(request.getParameter("num"));
                if (end > rec_length) {
                    end = rec_length-1;
                }else{
                    end -= 1;
                }
            }
        }

        request.setAttribute("UserDetail",UserDetail);
        request.setAttribute("start", start);
        request.setAttribute("end", end);

        RequestDispatcher rd = request.getRequestDispatcher(resultPage);
        rd.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
