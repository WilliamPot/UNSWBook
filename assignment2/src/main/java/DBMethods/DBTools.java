package DBMethods;
import Classes.User;
import ForAdminister.Entry;
import Report.PostReport;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static Classes.Tools.postSort;

public class DBTools {
    public static Connection getConn() {
        String driver = "com.mysql.jdbc.Driver";
        //String driver = "org.postgresql.Driver";
        //"org.postgresql.Driver"
        String url = "jdbc:mysql://localhost:3306/comp9321assign2?useSSL=true";
        //String url = "jdbc:postgresql://localhost:5432/comp9321assign2?useSSL=true";
        //"jdbc:postgresql://localhost:5432/comp9321assign2?useSSL=true"
        String username = "root";
        String password = "livxinyv1105";
        Connection conn = null;
        try {
            Class.forName(driver); //classLoader,加载对应驱动
            conn = (Connection) DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
    public static int insertAccounts(User user) {
        Connection conn = getConn();
        int i = 0;
        String sql = "insert into accounts (username,password,email) values(?,?,?)";
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getEmail());
            i = pstmt.executeUpdate();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }
    public static int insertDetails(User user) {
        Connection conn = getConn();
        int i = 0;
        String sql = "insert into userdetails (username,name,gender,dateofbirth,photopath) values(?,?,?,?,?)";
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getName());
            pstmt.setString(3, user.getGender());

            pstmt.setTimestamp(4, Timestamp.valueOf(user.getDateofbirth()+" 00:00:00"));
            pstmt.setString(5, user.getPicturepath());
            i = pstmt.executeUpdate();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }
    public static int insertPost(PostReport report, String username) {
        Connection conn = getConn();
        int i = 0;
        String sql = "insert into "+username+"_post (message,picturepath,userlike,date) values(?,?,?,?)";
        //You can change 'test_post' here.
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            pstmt.setString(1, report.getText().replaceAll("'","\'"));
            pstmt.setString(2, report.getPicturepath());
            pstmt.setString(3, report.groupLikes());
            pstmt.setTimestamp(4, Timestamp.valueOf(report.getDate()));
            i = pstmt.executeUpdate();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }
    public static int insertHistory(Entry history, String username) {
        Connection conn = getConn();
        int i = 0;
        String sql = "insert into "+username+"_history (type,message,picturepath,targetuser,sens,uaction,date) values(?,?,?,?,?,?,?)";
        //You can change 'test_post' here.
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            pstmt.setString(1, history.getType());
            if(history.getMessage()!=null) {
                pstmt.setString(2, history.getMessage().replaceAll("'", "\'"));
            }else{
                pstmt.setString(2, history.getMessage());
            }
            pstmt.setString(3, history.getPicturePath());
            pstmt.setString(4, history.getTargetUser());
            pstmt.setString(5, history.getSensitive());
            pstmt.setString(6, history.getAction());
            pstmt.setTimestamp(7, new Timestamp(history.getDate().getTime()));
            i = pstmt.executeUpdate();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }
    public static int updateAccount(User user) {
        Connection conn = getConn();
        int i = 0;
        String sql = "update accounts set password='"+user.getPassword()+"',email = '"+user.getEmail()+"' where username='"+user.getUsername()+"'";
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            i = pstmt.executeUpdate();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }
    public static int updateEmail(String username,String email) {
        Connection conn = getConn();
        int i = 0;
        String sql = "update accounts set email = '"+email+"' where username='"+username+"'";
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            i = pstmt.executeUpdate();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }
    public static int updateFriend(User user) {
        Connection conn = getConn();
        int i = 0;
        String update = user.groupFriends();
        String sql = null;
        System.out.println(update);
        if(update.equals("")) {
            sql = "update accounts set friends=null where username='" + user.getUsername() + "'";
        }else {
            sql = "update accounts set friends='" + update + "' where username='" + user.getUsername() + "'";
        }PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            i = pstmt.executeUpdate();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }
    public static int updateDetails(User user) {
        Connection conn = getConn();
        int i = 0;
        String sql = "update userdetails set name='"+user.getName()+"',gender = '"+user.getGender()+
                "',dateofbirth = '"+user.getDateofbirth()+ "' where username='"+user.getUsername()+"'";
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            i = pstmt.executeUpdate();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }
    public static String banUser(String username){
        Connection conn = getConn();
        int i = 0;
        int action = 0;
        String sql = "update accounts set status='"+action+"' where username='"+username+"'";
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            i = pstmt.executeUpdate();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "0";
    }
    public static String unbanUser(String username){
        Connection conn = getConn();
        int i = 0;
        int action = 1;
        String sql = "update accounts set status='"+action+"' where username='"+username+"'";
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            i = pstmt.executeUpdate();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "1";
    }
    public static int updateLike(PostReport report) {
        Connection conn = getConn();
        int i = 0;
        String update = report.groupLikes();
        String sql = null;
        if(update.equals("")) {
            sql = "update " + report.getUsername() + "_post set userlike=null where date='" + Timestamp.valueOf(report.getDate()) + "'";
        }else{
            sql = "update " + report.getUsername() + "_post set userlike='"+update+"' where date='" + Timestamp.valueOf(report.getDate()) + "'";
        }
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            i = pstmt.executeUpdate();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }
    public static Boolean AccountExist(String username,String password){
        Connection conn = getConn();
        String sql = "select * from accounts where username = '"+ username+"' and password = '"+ password+"'";
        PreparedStatement pstmt;
        boolean result;
        try {
            pstmt = (PreparedStatement)conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                result = true;
            }else{
                result = false;
            }
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            result = false;
        }
        return result;
    }
    public static Boolean AdminAccountExist(String username,String password){
        Connection conn = getConn();
        String sql = "select * from adminaccounts where username = '"+ username+"' and password = '"+ password+"'";
        PreparedStatement pstmt;
        boolean result;
        try {
            pstmt = (PreparedStatement)conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                result = true;
            }else{
                result = false;
            }
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            result = false;
        }
        return result;
    }
    public static Boolean UsernameExist(String username){
        Connection conn = getConn();
        String sql = "select * from accounts where username = '"+ username+"'";
        PreparedStatement pstmt;
        boolean result;
        try {
            pstmt = (PreparedStatement)conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                result = true;
            }else{
                result = false;
            }
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            result = false;
        }
        return result;
    }
    public static Boolean NameExist(String name){
        Connection conn = getConn();
        String sql = "select * from userdetails where name = '"+ name+"'";
        PreparedStatement pstmt;
        boolean result;
        try {
            pstmt = (PreparedStatement)conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                result = true;
            }else{
                result = false;
            }
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            result = false;
        }
        return result;
    }
    public static List<String> getAllUsername() {
        List<String> usernameSet = new ArrayList<String>();
        Connection conn = getConn();
        String sql = "select username from accounts";
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement)conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                usernameSet.add(rs.getString("username"));
            }
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usernameSet;
    }

    public static List<String> getAllName() {
        List<String> nameSet = new ArrayList<String>();
        Connection conn = getConn();
        String sql = "select name from userdetails";
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement)conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                nameSet.add(rs.getString("name"));
            }
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nameSet;
    }
    public static void getUser(User user) {
        Connection conn = getConn();
        String theUsername = user.getUsername();
        String sql = "select * from userdetails where username = '" + theUsername+"'";
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement)conn.prepareStatement(sql);
            DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                user.setName(rs.getString("name"));
                user.setGender(rs.getString("gender"));
                Timestamp date = rs.getTimestamp("dateofbirth");
                String dateStr = sdf.format(date);
                user.setDateofbirth(dateStr.substring(0,10));
                user.setPicturepath(rs.getString("photopath"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        sql = "select * from accounts where username = '" + theUsername +"'";
        try {
            pstmt = (PreparedStatement)conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setStatus(rs.getInt("status"));
                if(rs.getString("friends")!=null&&!rs.getString("friends").equals("")) {
                    user.setFriends(rs.getString("friends"));
                }
            }
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static List<PostReport> getPost(User user) {
        List<PostReport> postList = new ArrayList<PostReport>();
        Connection conn = getConn();
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Set<String> set = new HashSet<String>();
        for(String friend:user.getFriends()){
            set.add(friend);
        }
        set.add(user.getUsername());
        try{
            for(String person:set) {
                String tablename = person + "_post";
                String sql = "select * from " + tablename;
                PreparedStatement pstmt;
                pstmt = (PreparedStatement) conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    PostReport post = new PostReport();
                    post.setUsername(person);
                    post.setText(rs.getString("message"));
                    post.setPicturepath(rs.getString("picturepath"));
                    if(rs.getString("userlike")!=null){
                        post.setWholikes(rs.getString("userlike"));
                    }else{
                        post.setWholikes("");
                    }
                    Timestamp date = rs.getTimestamp("date");
                    String dateStr = sdf.format(date);
                    post.setDate(dateStr);
                    postList.add(post);
                }
                pstmt.close();
            }
            conn.close();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return postSort(postList);
    }
    public static String graphSearchMsg(String id){
        Connection conn = getConn();
        String dotgraph = "";
        try {
            String sql = "select * from graph where Object='"+id+"'";
            PreparedStatement pstmt;
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String relation = "";
                if(rs.getString("Predicate").equals("liked")){
                    String subject = " User:"+rs.getString("Subject");
                    String object = " Message:"+rs.getString("Object");
                    relation += subject+"[level=1];";
                    relation += object+"[shape=box,color=greenyellow,level=0];"+subject+" -> "+object+"[label=liked,color=grey,length=400];";
                }else if(rs.getString("Predicate").equals("posted")){
                    String subject = " User:"+rs.getString("Subject");
                    String object = " Message:"+rs.getString("Object");
                    relation += subject+"[level=1];";
                    relation += object+"[shape=box,color=greenyellow,level=0];"+subject+" -> "+object+"[label=posted,color=grey,length=400];";
                }
                dotgraph += relation;
            }
            pstmt.close();
            conn.close();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return dotgraph;
    }
    public static String graphSearchFriend(String username){
        Connection conn = getConn();
        String dotgraph = "";
        try {
            String sql = "select * from graph where Subject='"+username+"' and Predicate='friendof'";
            PreparedStatement pstmt;
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String relation = "";
                String subject = " User:"+rs.getString("Subject");
                String object = " User:"+rs.getString("Object");
                relation += subject+"[level=0,color=pink];"+object+"[level=1];";
                relation += subject + " -- " + object + "[label=friendof,color=grey,length=300];";
                dotgraph += relation;
            }
            pstmt.close();
            conn.close();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return dotgraph;
    }
    public static ArrayList<String> graphMessage(String searchwords){
        Connection conn = getConn();
        ArrayList<String> list = new ArrayList<String>();
        try {
            String sql = "select * from entity where Predicate='Message'";
            PreparedStatement pstmt;
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                if(rs.getString("Object").contains(searchwords)){
                    list.add(rs.getString("Subject"));
                }
            }
            pstmt.close();
            conn.close();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public static String getGraphDOT() {
        Connection conn = getConn();
        String dotgraph = "";
        try{
            String sql = "select * from graph";
            PreparedStatement pstmt;
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            Set<String> friendpair = new HashSet<String>();
            while (rs.next()) {
                String relation = "";
                if(rs.getString("Predicate").equals("friendof")){
                    String subject = " User:"+rs.getString("Subject");
                    String object = " User:"+rs.getString("Object");
                    String pair1 = subject+","+object;
                    String pair2 = object+","+subject;
                    if(!friendpair.contains(pair1)&&!friendpair.contains(pair2)) {
                        relation += subject+"[level=0];"+object+"[level=0];";
                        relation += subject + " -- " + object + "[label=friendof,color=grey];";
                        friendpair.add(pair1);
                    }
                }else if(rs.getString("Predicate").equals("liked")){
                    String subject = " User:"+rs.getString("Subject");
                    String object = " Message:"+rs.getString("Object");
                    relation += subject+"[level=0];";
                    relation += object+"[shape=box,color=greenyellow,level=1];"+subject+" -> "+object+"[label=liked,color=grey,length=400];";
                }else if(rs.getString("Predicate").equals("posted")){
                    String subject = " User:"+rs.getString("Subject");
                    String object = " Message:"+rs.getString("Object");
                    relation += subject+"[level=0];";
                    relation += object+"[shape=box,color=greenyellow,level=1];"+subject+" -> "+object+"[label=posted,color=grey,length=400];";
                }
                dotgraph += relation;
            }
            if(!dotgraph.equals("")){
                dotgraph = dotgraph.substring(0,dotgraph.length()-1);
            }
            pstmt.close();
            conn.close();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return dotgraph;
    }
    public static List<Entry> getUserHistory(String username) {
        List<Entry> userHistory = new ArrayList<Entry>();
        Connection conn = getConn();
        String sql = "select * from " + username+"_history";
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement)conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Entry history = new Entry();
                history.setType(rs.getString("type"));
                history.setMessage(rs.getString("message"));
                history.setPicturePath(rs.getString("picturepath"));
                history.setAction(rs.getString("uaction"));
                history.setTargetUser(rs.getString("targetuser"));
                history.setSensitive(rs.getString("sens"));
                Timestamp date = rs.getTimestamp("date");
                history.setDate(date);
                userHistory.add(history);
            }
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userHistory;
    }
    public static String getName(String username){
        String name = null;
        Connection conn = getConn();
        String sql = "select name from userdetails where username = '"+username+"'";
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement)conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                name = rs.getString("name");
            }
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return name;
    }
    public static boolean getStatus(String username){
        int status = 0;
        Connection conn = getConn();
        String sql = "select status from accounts where username = '"+username+"'";
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement)conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                status = rs.getInt("status");
            }
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(status == 0) {
            return false;
        }else{
            return true;
        }
    }
    public static String getPhoto(String username){
        String photo = null;
        Connection conn = getConn();
        String sql = "select photopath from userdetails where username = '"+username+"'";
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement)conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                photo = rs.getString("photopath");
            }
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return photo;
    }
    public static int createTable(User user) {
        Connection conn = getConn();
        int i = 0;
        String sql = "CREATE TABLE "+user.getUsername()+"_post\n" +
                "(\n" +
                "message varchar(1000),\n" +
                "picturepath varchar(244),\n" +
                "userlike varchar(10000),\n" +
                "date datetime\n"+
                ")";
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            i = pstmt.executeUpdate();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }
    public static int createHistory(User user) {
        Connection conn = getConn();
        int i = 0;
        String sql = "CREATE TABLE "+user.getUsername()+"_history\n" +
                "(\n" +
                "type varchar(15),\n" +
                "message varchar(1000),\n" +
                "picturepath varchar(244),\n" +
                "targetuser varchar(20),\n" +
                "date datetime,\n"+
                "uaction varchar(10),\n" +
                "sens varchar(15)\n"+
                ")";
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            i = pstmt.executeUpdate();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }
    public static String getUsername(String name){
        String username = null;
        Connection conn = getConn();
        String sql = "select username from userdetails where name = '"+name+"'";
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement)conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                username = rs.getString("username");
            }
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return username;
    }
    public static boolean getPending(String username,String tusername){
        boolean result = false;
        Connection conn = getConn();
        String sql = "select * from " + username+"_history where type = 'request' and targetuser = '"+tusername+"' and uaction = 'add'";
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement)conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                result = true;
            }
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    public static Integer getRows(String tablename) {
        Connection conn = getConn();
        String table_name = tablename;
        String sql = "select count(*) num from " + table_name;
        PreparedStatement pstmt;
        int rowCount = 0;
        try {
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt("num");
                System.out.println(count);
                rowCount = count+1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowCount;
    }
    public static void changeEntity(User user){
        Connection conn = getConn();
        String sql = "update entity set Object='"+user.getName()+"' where Subject='"+user.getUsername()+"' and Predicate='Name'";
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        sql = "update entity set Object='"+user.getGender()+"' where Subject='"+user.getUsername()+"' and Predicate='Gender'";
        try {
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        sql = "update entity set Object='"+user.getDateofbirth()+"' where Subject='"+user.getUsername()+"' and Predicate='DoB'";
        try {
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            pstmt.executeUpdate();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void insertEntity(User user) {
        Connection conn = getConn();
        String sql = "insert into entity (Subject,Predicate,Object) values(?,?,?)";
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, "Name");
            pstmt.setString(3, user.getName());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        sql = "insert into entity (Subject,Predicate,Object) values(?,?,?)";
        try {
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, "Gender");
            pstmt.setString(3, user.getGender());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        sql = "insert into entity (Subject,Predicate,Object) values(?,?,?)";
        try {
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, "DoB");
            pstmt.setString(3, user.getDateofbirth());
            pstmt.executeUpdate();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void insertPostEntity(String subject,String message) {
        Connection conn = getConn();
        String sql = "insert into entity (Subject,Predicate,Object) values(?,?,?)";
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            pstmt.setString(1, subject);
            pstmt.setString(2, "Message");
            pstmt.setString(3, message.replaceAll("'","\'"));
            pstmt.executeUpdate();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void insertGraph(String subject,String predicate,String object) {
        Connection conn = getConn();
        String sql = "insert into graph (Subject,Predicate,Object) values(?,?,?)";
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            pstmt.setString(1, subject);
            pstmt.setString(2, predicate);
            pstmt.setString(3, object);
            pstmt.executeUpdate();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static String getID(String message,String predicate){
        String subject = null;
        Connection conn = getConn();
        String sql = "select Subject from entity where Object = \""+message+"\" and Predicate = '"+predicate+"'";
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement)conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                subject = rs.getString("Subject");
            }
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return subject;
    }
    public static void deleteGraph(String subject,String predicate,String object ){
        Connection conn = getConn();
        String sql = "delete from graph where Object='"+object+"' and Subject='"+subject+"' and predicate = '"+predicate+"'";
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            pstmt.executeUpdate();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static String getMsg(String id){
        String object = null;
        Connection conn = getConn();
        String sql = "select Object from entity where Subject = '"+id+"' and Predicate = 'Message'";
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement)conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                object = rs.getString("Object");
            }
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return object;
    }
    public static String getPerson(String id){
        String object = "";
        Connection conn = getConn();
        String sql = "select Object from entity where Subject = '"+id+"' and Predicate = 'Name'";
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement)conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                object += "Name:"+rs.getString("Object")+"\n";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        sql = "select Object from entity where Subject = '"+id+"' and Predicate = 'Gender'";
        try {
            pstmt = (PreparedStatement)conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                if(rs.getString("Object").equals("M")) {
                    object += "Gender:" + "Male" + "\n";
                }else{
                    object += "Gender:" + "Female" + "\n";
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        sql = "select Object from entity where Subject = '"+id+"' and Predicate = 'DoB'";
        try {
            pstmt = (PreparedStatement)conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                object += "DoB:"+rs.getString("Object");
            }
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return object;
    }
}
