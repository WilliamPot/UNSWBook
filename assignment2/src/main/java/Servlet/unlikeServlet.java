package Servlet;

import Report.PostReport;
import io.goeasy.GoEasy;
import net.sf.json.JSONArray;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import static DBMethods.DBTools.*;

@WebServlet(name = "unlikeServlet")
public class unlikeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        List<PostReport> postList = (List<PostReport>)session.getAttribute("postlist");
        String index = request.getParameter("id");
        PostReport post = postList.get(Integer.parseInt(index));
        String username = request.getParameter("unlike");
        post.removeLike(username);
        updateLike(post);
        if(post.getText()!=null&&!post.getText().equals("")) {
            String ID = getID(post.getText(),"Message");
            deleteGraph(username, "liked", ID);
        }
        GoEasy goEasy = new GoEasy("https://rest-singapore.goeasy.io","BC-0908a32e047540bea70d3d3a39bb75fe");
        goEasy.publish(post.getUsername()+"_like","0000");
        //session.setAttribute("postlist",post);
        //send notification
        //response.sendRedirect("/user.jsp");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        PrintWriter writer = response.getWriter();
        String react = post.getWholikesstring();
        writer.print(String.valueOf(react));
        writer.flush();
    }
}
