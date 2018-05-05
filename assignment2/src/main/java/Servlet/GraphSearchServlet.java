package Servlet;

import Classes.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

import static DBMethods.DBTools.*;

@WebServlet(name = "GraphSearchServlet")
public class GraphSearchServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        String searchwords = request.getParameter("searchword");
        String type = request.getParameter("select");
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        String dotgraph = "";
        if(type.equals("friend")){
            Set<String> friends = user.getFriends();
            ArrayList<String> graphfriends = new ArrayList<String>();
            for(String friend:friends) {
                if (getName(friend).contains(searchwords)) {
                    graphfriends.add(friend);
                }
            }
            String subgraph = "";
            for(String graphfriend:graphfriends){
                subgraph = graphSearchFriend(graphfriend);
                dotgraph+=subgraph;
            }
            if(!dotgraph.equals("")){
                dotgraph = dotgraph.substring(0,dotgraph.length()-1);
            }
            if(!dotgraph.equals("")){
                String graphDOT = "dinetwork {"+dotgraph+"}";
                request.setAttribute("usergraph", graphDOT);
                request.getRequestDispatcher("graph.jsp").forward(request, response);
            }else{
                request.setAttribute("error", "Nothing found.");
                request.getRequestDispatcher("graph.jsp").forward(request, response);
            }
        }else if(type.equals("message")){
            String subgraph = "";
            ArrayList<String> graphmsg = graphMessage(searchwords);
            for(String msg:graphmsg){
                subgraph = graphSearchMsg(msg);
                dotgraph+=subgraph;
            }
            if(!dotgraph.equals("")){
                dotgraph = dotgraph.substring(0,dotgraph.length()-1);
            }
            if(!dotgraph.equals("")){
                String graphDOT = "dinetwork {"+dotgraph+"}";
                request.setAttribute("messagegraph", graphDOT);
                request.getRequestDispatcher("graph.jsp").forward(request, response);
            }else{
                request.setAttribute("error", "Nothing found.");
                request.getRequestDispatcher("graph.jsp").forward(request, response);
            }
        }
    }
}
