package Servlet;

import java.io.IOException;  
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;  
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;

import static DBMethods.DBTools.UsernameExist;

@WebServlet("/MailBackServlet")  
public class MailBackServlet extends HttpServlet {  
    private static final long serialVersionUID = 1L;  
  
    public void doGet(HttpServletRequest request, HttpServletResponse response)  
            throws ServletException, IOException {  
        String registerID = request.getParameter("Id");
        String registerName = request.getParameter("Name");
        String registerEmail = request.getParameter("Email");
        if(registerID == null || registerName == null||UsernameExist(registerName)){
            request.getRequestDispatcher("index.jsp").forward(request,response);
            return ;  
        }  
          
 /*       String registerName = (String)request.getSession().getAttribute(registerID);  
          
        if(registerName == null || registerName.equals("")){  
            request.getRequestDispatcher("/index.jsp").forward(request,response);  
            return ;  
        }   */
          
        request.setAttribute("registerName", registerName);
        request.setAttribute("registerEmail", registerEmail); 
        request.getRequestDispatcher("registerSuccess.jsp").forward(request,response);
    }  
  
    public void doPost(HttpServletRequest request, HttpServletResponse response)  
            throws ServletException, IOException {  
        doGet(request, response);  
    }  
  
}  