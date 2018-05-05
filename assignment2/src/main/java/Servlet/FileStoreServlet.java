package Servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Classes.User;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import static DBMethods.DBTools.*;

@WebServlet("/FileStoreServlet")
public class FileStoreServlet extends HttpServlet {
public static String filename = null;

 private static final long serialVersionUID = 1L;
 public void doGet(HttpServletRequest request,HttpServletResponse response){
     doGet(request,response);
 }
 @SuppressWarnings("rawtypes")
 public void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException{
     User user = new User();
  String name = null;
  String psw = null;
  String pswrepeat = null;
  String gender = null;
  String bday = null;
  String registerName = null;
  String registerEmail = null;
  
  //System.out.println(registerName);
  //String filename = null;
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
  
  while(iter.hasNext()){
   FileItem item = (FileItem) iter.next();
   String value = item.getString();
   value = new String(value.getBytes("ISO-8859-1"), "UTF-8");
   //System.out.println(item.getFieldName() + value);
   if (item.getFieldName().equals("name")){
	   name = value;
       user.setName(name);
	   }
   else if (item.getFieldName().equals("psw")){
	   psw = value;
	   user.setPassword(psw);
	   }
   else if (item.getFieldName().equals("pswrepeat")){
	   pswrepeat = value;
	   }
   else if (item.getFieldName().equals("gender")){
	   gender = value;
	   user.setGender(gender);
	   }
   else if (item.getFieldName().equals("bday")){
	   bday = value;
	   bday = bday.replaceAll("/","-");
	   user.setDateofbirth(bday);
	   }
   else if (item.getFieldName().equals("registerName")){
	   registerName = value;
	   user.setUsername(registerName);
	   }
   else if (item.getFieldName().equals("registerEmail")){
	   registerEmail = value;
	   user.setEmail(registerEmail);
	   }
   else if(!item.isFormField()){
	   //System.out.println(name); photo named by the unique email address
    filename = "photo.jpg";
    //the path of photo storage
    File f = new File(this.getServletConfig().getServletContext().getRealPath("/")+"/picture/selfishes/"+registerName);
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
   }else{
	   System.out.println("Error");
   }

  }
  user.setPicturepath("/selfishes/"+user.getUsername());
  insertAccounts(user);
  insertDetails(user);
  createTable(user);
  createHistory(user);
  insertEntity(user);
  //request.setAttribute("filename", filename);  
  //request.getRequestDispatcher("/showPhoto").forward(request, response); reference http://m.jb51.net/article/120299.htm
  System.out.println("Upload successfully!!!");
     response.sendRedirect("registerOk.jsp?username="+registerName);
 }

}


/* I have already got the registration parameter: name, psw, pswrepeat, gender, bday, registerName, registerEmail here.
 * you can use them directly here, to store in database or search...... 
 */