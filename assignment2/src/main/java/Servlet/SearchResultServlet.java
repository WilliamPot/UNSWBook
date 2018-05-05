package Servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Classes.User;

import static DBMethods.DBTools.getAllName;
import static DBMethods.DBTools.getUser;
import static DBMethods.DBTools.getUsername;

@WebServlet(name = "SearchResultServlet")
public class SearchResultServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.setAttribute("searchlist",null);
        if(request.getParameter("searched_name").equals("")||request.getParameter("searched_name")==null){
            request.setAttribute("error", "Invalid input!");
            request.getRequestDispatcher("searchFriends.jsp").forward(request, response);}
        int found_=0;
        String name_input = request.getParameter("searched_name");
        String gender_ = null;
        gender_=request.getParameter("sex");
        String byear =null;
        String bmonth =null;
        String bday =null;
        byear=request.getParameter("birth_y");
        bmonth=request.getParameter("birth_m");
        bday=request.getParameter("birth_d");
        if(bmonth!=null&&!bmonth.equals("")) {
            if (Integer.parseInt(bmonth) > 12 || Integer.parseInt(bmonth) == 0) {
                request.setAttribute("bug", "Month should be '1-12'");
                request.getRequestDispatcher("searchFriends.jsp").forward(request, response);
            }
        }
        if(bday!=null&&!bday.equals("")) {
            if (Integer.parseInt(bday) > 31 || Integer.parseInt(bday) == 0) {
                request.setAttribute("bug", "Day should be '1-31'");
                request.getRequestDispatcher("searchFriends.jsp").forward(request, response);
            }
        }
        List<String> nameList = getAllName();
        List<User> userList = new ArrayList<User>();
        User me = (User)session.getAttribute("user");
        for(String name:nameList){
            if(name.contains(name_input)){
                if(name.equals(me.getName())){
                    continue;
                }
                String username = getUsername(name);
                User user = new User();
                user.setUsername(username);
                getUser(user);
                if(gender_!=null&&!gender_.equals(user.getGender())){
                    continue;
                }
                String[] dob = user.getDateofbirth().split("-");
                if(byear!=null&&!byear.equals("")&&!byear.equals(dob[0])){
                    continue;
                }
                if(bmonth!=null&&!bmonth.equals("")&&!bmonth.equals(dob[1])){
                    continue;
                }
                if(bday!=null&&!bday.equals("")&&!bday.equals(dob[2])){
                    continue;
                }
                found_=1;
                userList.add(user);
            }
        }
        if(found_==1){
            session.setAttribute("searchlist", userList);
            response.sendRedirect("searchFriends.jsp");

        }else{
            request.setAttribute("bug", "Can't find the user!");
            request.getRequestDispatcher("searchFriends.jsp").forward(request, response);
        }
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}
