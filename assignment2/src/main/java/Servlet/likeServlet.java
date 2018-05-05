package Servlet;

import Classes.User;
import Report.PostReport;
import io.goeasy.GoEasy;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import static DBMethods.DBTools.getID;
import static DBMethods.DBTools.insertGraph;
import static DBMethods.DBTools.updateLike;

@WebServlet(name = "likeServlet")
public class likeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        String name = user.getName();
        List<PostReport> postList = (List<PostReport>)session.getAttribute("postlist");
        String index = request.getParameter("id");
        PostReport post = postList.get(Integer.parseInt(index));
        String username = request.getParameter("like");
        post.addLike(username);
        updateLike(post);
        if(post.getText()!=null&&!post.getText().equals("")) {
            String ID = getID(post.getText(),"Message");
            System.out.println(ID);
            insertGraph(user.getUsername(), "liked", ID);
        }
        if(!username.equals(post.getUsername())) {
            GoEasy goEasy = new GoEasy("https://rest-singapore.goeasy.io", "BC-0908a32e047540bea70d3d3a39bb75fe");
            goEasy.publish(post.getUsername() + "_like", "1111"
                    + name + " likes your post: '" + post.getNoty() + "'");

        //username+" likes your post: '"+post.getText()+"'"
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
