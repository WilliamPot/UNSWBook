package Servlet;

import Classes.User;
import ForAdminister.Entry;
import io.goeasy.GoEasy;
import model.MyAuthenticator;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Properties;

import static DBMethods.DBTools.getUser;
import static DBMethods.DBTools.insertHistory;

@WebServlet(name = "friendRequestServlet")
public class friendRequestServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType ( "text/html" ) ;
        HttpSession httpSession = request.getSession();
        String username = request.getParameter("username");
        String targetusername = request.getParameter("targetname");
        System.out.println(username);
        System.out.println(targetusername);
        User target = new User();
        target.setUsername(targetusername);
        getUser(target);
        User thisuser = (User)httpSession.getAttribute("user");
        String nyname = thisuser.getName();
        String toMail = target.getEmail();
        String userName = "wsgc1105@gmail.com";
        String password = "wsgc350402";
        String registerId = "" + Math.random() * Math.random();
        String url = "http://localhost:8080"+request.getContextPath()+"/friendConfirmServlet?Id=" + registerId + "&Name=" + username + "&TName=" + targetusername;
        httpSession.setAttribute(registerId, username);
        httpSession.setMaxInactiveInterval(600);

        Properties props = new Properties();
        props.setProperty("mail.smtp.host", "smtp.gmail.com");
        props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.socketFactory.port", "465");
        props.setProperty("mail.smtp.auth", "true");
        //props.setProperty("mail.smtp.host", "smtp.163.com");
        //props.setProperty("mail.smtp.auth", "true");
        //props.setProperty("mail.smtp.port", "25");
        //props.setProperty("mail.smtp.port", "25");
        Authenticator authenticator = new MyAuthenticator(userName, password);

        javax.mail.Session session = javax.mail.Session.getDefaultInstance(props, authenticator);
        session.setDebug(true);

        try {
            Address from = new InternetAddress(userName);
            Address to = new InternetAddress(toMail);

            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(from);
            msg.setSubject("Friend Request from "+username);
            msg.setFrom("wsgc1105@gmail.com");
            msg.setSentDate(new Date());
            msg.setContent("Please use this hyperlink to accept the friend request from "+nyname+":\n<a href='" + url + "'>" + url + "</a>", "text/html;charset=utf-8");
            msg.setRecipient(RecipientType.TO, to);
            /*
            Transport transport = session.getTransport("smtp");
            transport.connect("smtp.163.com", userName, password);
            transport.sendMessage(msg,msg.getAllRecipients());
            transport.close();
            */
            Transport transport = session.getTransport("smtp");
            transport.connect("smtp.gmail.com", userName, password);
            transport.sendMessage(msg, msg.getAllRecipients());
            transport.close();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        Entry history = new Entry();
        Date now = new Date();
        history.setDate(now);
        history.setTargetUser(targetusername);
        history.setType("request");
        history.setAction("add");
        insertHistory(history,username);
        GoEasy goEasy = new GoEasy("https://rest-singapore.goeasy.io", "BC-0908a32e047540bea70d3d3a39bb75fe");
        goEasy.publish(targetusername + "_like", "3333"+userName+" has sent a friend request to your email.");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        PrintWriter writer = response.getWriter();
        writer.print("true");
        writer.flush();
    }
}
