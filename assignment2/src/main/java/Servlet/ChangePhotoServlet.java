package Servlet;

import Classes.User;
import io.goeasy.GoEasy;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@WebServlet(name = "ChangePhotoServlet")
public class ChangePhotoServlet extends HttpServlet {
    public static String filename = null;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        String username = user.getUsername();
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        factory.setSizeThreshold(1024 * 1024);
        List items = null;
        try {
            items = upload.parseRequest(request);
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
        Iterator iter = items.iterator();

        while (iter.hasNext()) {
            FileItem item = (FileItem) iter.next();
            String value = item.getString();
            value = new String(value.getBytes("ISO-8859-1"), "UTF-8");
            //System.out.println(item.getFieldName() + value);
            if (!item.isFormField()) {
                //System.out.println(name); photo named by the unique email address
                filename = "photo.jpg";
                //the path of photo storage
                File f = new File( this.getServletConfig().getServletContext().getRealPath("/")+ "/picture/selfishes/" + username);
                if (!f.exists()) {
                    f.mkdir();
                }
                String imgsrc = f + "/" + filename;
                InputStream is = item.getInputStream();
                FileOutputStream fos = new FileOutputStream(imgsrc);
                byte b[] = new byte[1024 * 1024];
                int length = 0;
                while (-1 != (length = is.read(b))) {
                    fos.write(b, 0, length);
                }
                fos.flush();
                fos.close();
            } else {
                System.out.println("Error");
            }
        }
        GoEasy goEasy = new GoEasy("https://rest-singapore.goeasy.io", "BC-0908a32e047540bea70d3d3a39bb75fe");
        Set<String> friends = user.getFriends();
        for(String friend:friends){
            goEasy.publish(friend + "_like", "5555");
        }
        response.sendRedirect("user.jsp");
    }
}
