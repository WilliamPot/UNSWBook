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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static DBMethods.DBTools.unbanUser;

@WebServlet("/banListServlet")
public class banListServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<Integer, User> UserDetail = new HashMap<Integer, User>();
        List<User> ban_list = new ArrayList<User>();
        HttpSession session = req.getSession(false);
        int max_num_per_page = 10;
        int start = 0;
        int end = 0;
        boolean prev = false;
        boolean next = false;

        try {
            //get user data from session
            UserDetail = (HashMap) session.getAttribute("UserDetail");
            int size = UserDetail.size();
            // Search for banned user and store in a list
            for(int i=0;i<size;i++){
                User u = UserDetail.get(i);
                if (u.getStatus() == 0){
                    ban_list.add(u);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }


        int list_size = ban_list.size();


        // if no banned user, direct to empty page
        if (list_size==0){
            RequestDispatcher rd = req.getRequestDispatcher("banListEmpty.jsp");
            rd.forward(req,resp);
        }else{
            // process unban all
            //******************************XML related part*****************************************
            if (req.getParameter("unban") != null){
                if (req.getParameter("unban").equals("all")){
                    for (User banned_user: ban_list){
                        String new_status = unbanUser(banned_user.getUsername());
                        //**********************************************************************
                        for(int i=0;i<UserDetail.size();i++){
                            User user_detail = UserDetail.get(i);
                            if(user_detail.getUsername().equals(banned_user.getUsername())){
                                user_detail.setStatus(Integer.parseInt(new_status));
                            }
                        }
                    }
                    session.setAttribute("UserDetail", UserDetail);
                    RequestDispatcher rd = req.getRequestDispatcher("banListServlet");
                    rd.forward(req,resp);
                }
            }else{
                if (list_size <= max_num_per_page){
                    end = list_size-1;
                }else{
                    // page partition
                    if (req.getParameter("start") != null) {
                        start = Integer.parseInt(req.getParameter("start"));
                    }
                    if (start + max_num_per_page > list_size){
                        end = list_size -1;
                    }else{
                        end = start + max_num_per_page -1;
                        next = true;
                    }
                    if (start != 0){
                        prev = true;
                    }
                }
                req.setAttribute("ban_list", ban_list);
                req.setAttribute("list_size", list_size);
                req.setAttribute("start", start);
                req.setAttribute("end", end);
                req.setAttribute("max_num_per_page", max_num_per_page);
                req.setAttribute("prev", prev);
                req.setAttribute("next", next);
                RequestDispatcher rd = req.getRequestDispatcher("banList.jsp");
                rd.forward(req,resp);
            }

        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

}
