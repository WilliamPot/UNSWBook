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
import java.util.List;
import java.util.Map;

@WebServlet(name = "adminSearchServlet")
public class adminSearchServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String search_key;
        HttpSession session = request.getSession();
        String resultPage;

        if (request.getParameter("search_key") != null){
            //get search_key
            search_key = request.getParameter("search_key");
            search_key = search_key.toLowerCase();
            request.setAttribute("search_key", search_key);

            //***************newly added************************
            session.setAttribute("search_key", search_key);
            //**************************************************

            resultPage = "adminResult.jsp";
            //retrieve user details from current session
            Map <Integer, User> UserDetail = (Map)session.getAttribute("UserDetail");
            List<User> results = new ArrayList<User>();
            int maxRecordsPerPage = 10;
            int start = 0;
            int end = 0;
            int page = 1;
            boolean prev = true;
            boolean next = true;

            //*************** modified ************************
            if (session.getAttribute("results") == null){
                //loop through hashmap and compare user's name
                for(Map.Entry<Integer, User> entry:UserDetail.entrySet()){
                    User u = entry.getValue();
                    String currName = u.getName();
                    if (currName.toLowerCase().contains(search_key)){
                        results.add(u);
                    }
                }
                session.setAttribute("results", results);
            } else {
                results = (List) session.getAttribute("results");
            }
            //*************** modified ************************


            if (results.isEmpty()){
                //direct to empty page
                resultPage = "adminEmpty.jsp";
            } else {
                //get page number to display
                if (request.getParameter("page") != null){
                    page = Integer.parseInt(request.getParameter("page"));
                }
                //define start index and end index for displaying records
                if(results.size() > maxRecordsPerPage){
                    end = page*maxRecordsPerPage;
                    start = end-maxRecordsPerPage;
                    end = end - 1;
                    if(end > results.size()){
                        end = results.size()-1;
                    }
                } else{
                    end = results.size()-1;
                }

                if(start == 0){
                    prev = false;
                }
                if(end == results.size()-1){
                    next = false;
                }

                session.setAttribute("start", start);
                session.setAttribute("end", end);
                request.setAttribute("results", results);
                request.setAttribute("start", start);
                request.setAttribute("end", end);
                request.setAttribute("page", page);

                if (prev){
                    request.setAttribute("prev", true);
                }else {
                    request.setAttribute("prev", false);
                }
                if (next){
                    request.setAttribute("next", true);
                }else {
                    request.setAttribute("next", false);
                }
            }
            RequestDispatcher rd = request.getRequestDispatcher(resultPage);
            rd.forward(request, response);
        } else {
            //prevent unauthorised access to result page
            resultPage = "adminHomeServlet";
            response.sendRedirect(resultPage);
        }
    }
}
