package Servlet;

import Classes.User;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.*;

import ForAdminister.Entry;
import Report.PostReport;
import io.goeasy.GoEasy;
import model.MyAuthenticator;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import unsw.curation.api.domain.ExtractNamedEntity;
import unsw.curation.api.extractnamedentity.ExtractEntitySentence;
import unsw.curation.api.tokenization.ExtractionKeywordImpl;

import static Classes.Tools.postSort;
import static DBMethods.DBTools.*;

@WebServlet(name = "uploadServlet")
public class uploadServlet extends HttpServlet {
    final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String stops = this.getServletConfig().getServletContext().getRealPath("/")+"/resource/englishStopwords.txt";
        String bullyingword = this.getServletConfig().getServletContext().getRealPath("/")+"/resource/bullying.txt";
        response.setContentType("text/html");
        PostReport post = new PostReport();
        Entry history = new Entry();
        history.setType("post");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String uploadPath = this.getServletConfig().getServletContext().getRealPath("/")+"/picture/postpicture/" + user.getUsername();
        File uploadDir = new File(uploadPath);
        post.setUsername(user.getUsername());
        Date now = new Date();
        history.setDate(now);
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        post.setDate(dateFormat1.format(now));
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload sfu = new ServletFileUpload(factory);
        factory.setSizeThreshold(4000 * 4000);
        List formItems = null;
        try {
            formItems = sfu.parseRequest(request);
            Iterator iter = formItems.iterator();
            while (iter.hasNext()) {
                FileItem item = (FileItem) iter.next();
                String value = item.getString();
                value = new String(value.getBytes("ISO-8859-1"), "UTF-8");
                if (!item.isFormField() && item.getName() != "") {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                    //history.setDate(dateFormat1.format(now));
                    String filename = dateFormat.format(now) + ".jpg";
                    post.setPicturepath("/postpicture/" + user.getUsername() + "/" + filename);
                    history.setPicturePath("/postpicture/" + user.getUsername() + "/" + filename);
                    //history.setPicturepath("/postpicture/"+user.getUsername()+"/"+filename);
                    String picturepath = uploadPath + "/" + filename;
                    File file = new File(uploadPath);
                    if (!file.exists()) {
                        file.mkdir();
                    }
                    InputStream is = item.getInputStream();
                    FileOutputStream fos = new FileOutputStream(picturepath);
                    byte b[] = new byte[4000 * 4000];
                    int length = 0;
                    while (-1 != (length = is.read(b))) {
                        fos.write(b, 0, length);
                    }
                    fos.flush();
                    fos.close();
                } else if (item.getFieldName().equals("text")) {
                    String message = value;
                    //data API
                    String sensitive = "";
                    String keywords = null;
                    ExtractEntitySentence sentenceE = new ExtractEntitySentence();
                    List<ExtractNamedEntity> elist = null;
                    Set<String> words = new HashSet<String>();
                    try {
                        elist = sentenceE.ExtractNamedEntitySentence(message);
                    } catch (URISyntaxException e) {
                        System.out.println(e);
                    }
                    ExtractionKeywordImpl keyword = new ExtractionKeywordImpl();
                    try {
                        keywords = keyword.ExtractSentenceKeyword(message, new File(stops));
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                    if (keywords != null && !keywords.equals("")) {
                        for (String word : keywords.split(",")) {
                            words.add(word);
                        }
                    }
                    if (elist != null && elist.size() != 0) {
                        for (ExtractNamedEntity name : elist) {
                            words.add(name.ner);
                        }
                    }
                    boolean found = false;
                    Set<String> bullying = readText(bullyingword);
                    for (String word : words) {
                        System.out.println(word);
                        if (bullying.contains(word)) {
                            found = true;
                            break;
                        }
                    }
                    if (found) {
                        sensitive = "Bullying";
                        history.setSensitive(sensitive);
                        sendEmail(user.getUsername(),dateFormat1.format(now));
                    }
                    //API
                    post.setText(message);
                    history.setMessage(message);
                }
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
        //need to update the database and object.
        List<PostReport> postList = (List<PostReport>) session.getAttribute("postlist");
        postList.add(post);
        postList = postSort(postList);
        int index = getRows(user.getUsername()+"_post");
        insertHistory(history, user.getUsername());
        insertPost(post, user.getUsername());
        String subject = user.getUsername()+Integer.toString(index);
        if(post.getText()!=null&&!post.getText().equals("")) {
            insertPostEntity(subject, post.getText());
            insertGraph(user.getUsername(),"posted",subject);
        }
        GoEasy goEasy = new GoEasy("https://rest-singapore.goeasy.io", "BC-0908a32e047540bea70d3d3a39bb75fe");
        Set<String> friends = user.getFriends();
        for (String friend : friends) {
            if (!friend.equals(post.getUsername())) {
                goEasy.publish(friend + "_like", "2222");
            }
        }
        response.sendRedirect("user.jsp");
    }

    public Set readText(String Dir) throws IOException {
        File sampleDir = new File(Dir);
        FileReader samReader = new FileReader(sampleDir);
        BufferedReader samBR = new BufferedReader(samReader);
        String word = new String();www.
        StringBuffer words = new StringBuffer();
        Set arr = new HashSet();
        while ((word = samBR.readLine()) != null) {
            words.append(word);
            //word = samBR.readLine();
        }
        String wordss = words.substring(0);
        String w[] = wordss.split(", ");
        for (int i = 0; i < w.length; i++) {
            String str = w[i];
            arr.add(str);
            String str1 = str.replace(str.substring(0, 1), str.substring(0, 1).toLowerCase());
            arr.add(str1);
        }
        return arr;
    }

    public void sendEmail(String username,String date) {
        String toMail = "z5103300@ad.unsw.edu.au";
        String userName = "wsgc1105@gmail.com";
        String password = "wsgc350402";
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
            msg.setContent("User "+username+" posted a message containing words related to bullying at "+date+".", "text/html;charset=utf-8");
            msg.setRecipient(Message.RecipientType.TO, to);
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
    }
}