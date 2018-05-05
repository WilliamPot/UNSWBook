package Servlet;

import Classes.User;
import io.goeasy.GoEasy;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static DBMethods.DBTools.banUser;
import static DBMethods.DBTools.getStatus;
import static DBMethods.DBTools.unbanUser;

@WebServlet("/changeStatusServlet")
public class ChangeStatusServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String u = req.getParameter("user");
        String start = req.getParameter("start");
        HttpSession session = req.getSession(false);
        // get user data from session
        Map<Integer, User> UserDetail = (HashMap) session.getAttribute("UserDetail");
        String new_status = null;

        //************XML Related Part *****************
        // parse xml and change status
        if(getStatus(u)){
            new_status = banUser(u);
            GoEasy goEasy = new GoEasy("https://rest-singapore.goeasy.io", "BC-0908a32e047540bea70d3d3a39bb75fe");
            goEasy.publish(u + "_like", "6666You have been kicked out by Administer.");
        }else{
            new_status = unbanUser(u);
        }

        //**********************************************

        // Update UserDetail
        for(int i=0;i<UserDetail.size();i++){
            User ud = UserDetail.get(i);
            if(ud.getUsername().equals(u)){
                ud.setStatus(Integer.parseInt(new_status));
            }
        }
        session.setAttribute("UserDetail", UserDetail);
        RequestDispatcher rd = req.getRequestDispatcher("detailServlet?user="+u+"&start="+start);
        rd.forward(req,resp);
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

}
