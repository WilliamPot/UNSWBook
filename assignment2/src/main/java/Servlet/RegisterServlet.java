package Servlet;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;  
  
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
  
import model.MyAuthenticator;

import static DBMethods.DBTools.UsernameExist;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {  
    private static final long serialVersionUID = 1L;
    final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
    public void doGet(HttpServletRequest request, HttpServletResponse response)  
            throws ServletException, IOException {
        response.setContentType ( "text/html" ) ;
        String toMail = request.getParameter("email");  
        String registerName = request.getParameter("userName");
        String userName = "wsgc1105@gmail.com";
        String password = "wsgc350402";
        HttpSession httpSession = request.getSession();
        if(UsernameExist(registerName)){
            httpSession.setAttribute("usernameexist","This username has been used.");
            response.sendRedirect("signup.jsp");
        }else {
            String registerId = "" + Math.random() * Math.random();
            String url = "http://localhost:8080"+request.getContextPath()+"/MailBackServlet?Id=" + registerId + "&Name=" + registerName + "&Email=" + toMail;
            httpSession.setAttribute(registerId, registerName);
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
                msg.setSubject("UNSWBOOK Email Verification");
                msg.setFrom("wsgc1105@gmail.com");
                msg.setSentDate(new Date());
                msg.setContent("Please use this hyperlink to continue UNSWBOOK registration:\n<a href='" + url + "'>" + url + "</a>", "text/html;charset=utf-8");
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
                request.setAttribute("invalid", "Email Address Invalid!");
                request.getRequestDispatcher("registerSuccess.jsp").forward(request, response);
            }

            request.getRequestDispatcher("sendMailSuccess.jsp").forward(request, response);
        }
    }
  
    public void doPost(HttpServletRequest request, HttpServletResponse response)  
            throws ServletException, IOException {  
        doGet(request, response);  
    }  
}  