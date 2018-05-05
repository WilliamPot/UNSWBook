package Servlet;

import Classes.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

import static DBMethods.DBTools.NameExist;
import static DBMethods.DBTools.UsernameExist;

@WebServlet(name = "NameServlet")
public class NameServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        String name = request.getParameter("name");
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        if(NameExist(name)&&!name.equals(user.getName())){
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            PrintWriter writer = response.getWriter();
            writer.print("false");
            writer.flush();
        }else{
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            PrintWriter writer = response.getWriter();
            writer.print("true");
            writer.flush();
        }
    }
}
