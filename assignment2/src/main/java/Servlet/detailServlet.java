package Servlet;


import Classes.User;
import ForAdminister.Entry;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static Classes.Tools.historySort;
import static DBMethods.DBTools.getUserHistory;


@WebServlet("/detailServlet")
public class detailServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        HttpSession session = req.getSession(false);
        // get user data from session
        Map<Integer, User> UserDetail = (HashMap) session.getAttribute("UserDetail");
        Map<String,List<Entry>> history_reports = new HashMap<String, List<Entry>>();
        String u = req.getParameter("user");
        User ud = new User();
        int size = UserDetail.size();
        int max_num_per_page = 30;
        int start = 0;
        int end = 0;
        boolean prev = false;
        boolean next = false;
        boolean ban = false;
        //***************newly added************************
        String search_key = (String) session.getAttribute("search_key");
        //***************newly added************************

        // find target user
        for(int i=0;i<size;i++){
            if (UserDetail.get(i).getUsername().equals(u)){
                ud = UserDetail.get(i);
            }
        }


        /**if (session.getAttribute("history_reports") == null){
            // ****************XML related part***********************
            // parse userReport.xml
            try {
                String userReports = "userReports.xml";
                XMLParser p = new XMLParser();
                Document parsed_userReports = p.getDoc(userReports);
                history_reports = p.getReportsList(parsed_userReports);

            }catch (Exception e){
                System.out.println(e);
            }
            //*********************************************************
            session.setAttribute("history_reports",history_reports);
        }else{
            history_reports = (HashMap) session.getAttribute("history_reports");
        }**/

        // get reports of target user
        List<Entry> report = getUserHistory(u);
        historySort(report);
        int report_size = 0;
        if(report != null){
            report_size = report.size();
        }

        // page partition
        if (report_size != 0){
            if (report_size <= max_num_per_page){
                end = report_size-1;
            }else{
                if (req.getParameter("start") != null) {
                    start = Integer.parseInt(req.getParameter("start"));
                }
                if (start + max_num_per_page > report_size){
                    end = report_size -1;
                }else{
                    end = start + max_num_per_page -1;
                    next = true;
                }
                if (start != 0){
                    prev = true;
                }
            }
        }


        if (ud.getStatus() == 0){
            ban = true;
        }

        req.setAttribute("User", ud);
        req.setAttribute("Report",report);
        req.setAttribute("ReportSize",report_size);
        req.setAttribute("start", start);
        req.setAttribute("end", end);
        req.setAttribute("max_num_per_page", max_num_per_page);
        req.setAttribute("prev", prev);
        req.setAttribute("next", next);
        req.setAttribute("ban", ban);
        //***************newly added************************
        req.setAttribute("search_key", search_key);
        //***************newly added************************
        RequestDispatcher rd = req.getRequestDispatcher("detail.jsp");
        rd.forward(req,resp);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }


}
