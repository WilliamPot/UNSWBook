package Servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static DBMethods.DBTools.getGraphDOT;

@WebServlet(name = "WholeGraphServlet")
public class WholeGraphServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String content = getGraphDOT();
        HttpSession session = request.getSession();
        if(!content.equals("")){
            String graphDOT = "dinetwork {"+content+"}";
            session.setAttribute("wholegraph", graphDOT);
        }
        response.sendRedirect("graph.jsp");
    }
}
