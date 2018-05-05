package Classes;

import java.util.HashSet;
import java.util.Set;

public class User{
    private String username;
    private String password;
    private String name;
    private String gender;//'M' or 'F'
    private String dateofbirth;//'xxxx-xx-xx'
    private String email;
    private String picturepath; //store the file path
    private Set<String> friends = new HashSet<String>();//
    private int status;
    public User(){
        ;
    }
    public User(String u,String p,String n,String g,String d,String e,String pic){
        //you can use this constructor
        //or method below to set value
        username = u;
        password = p;
        name = n;
        gender = g;
        dateofbirth = d;
        email = e;
        picturepath = pic;
    }
    public void setUsername(String u){
        username = u;
    }
    public String getUsername(){
        return username;
    }

    public void setPassword(String p){
        password = p;
    }
    public String getPassword(){
        return password;
    }

    public void setName(String n){
        name = n;
    }
    public String getName(){
        return name;
    }

    public void setGender(String g){
        gender = g;
    }
    public String getGender(){
        return gender;
    }

    public void setDateofbirth(String d){
        dateofbirth = d;
    }
    public String getDateofbirth(){
        return dateofbirth;
    }

    public void setEmail(String e){
        email = e;
    }
    public String getEmail(){
        return email;
    }

    public void setPicturepath(String pic){
        picturepath = pic;
    }
    public String getPicturepath(){
        return picturepath;
    }
    public void setFriends(String f){
        if(!f.equals("")&&f!=null) {
            String[] friendlist = f.split(",");
            for (int i = 0; i < friendlist.length; i++) {
                friends.add(friendlist[i]);
            }
        }
    }
    public void addFriend(String u){//add username not name
        friends.add(u);
    }
    public void removeFriend(String u){
        friends.remove(u);
    }
    public String groupFriends(){
        String output = "";
        for (String str : friends) {
            output += str+",";
        }
        if(output.length()>0) {
            output = output.substring(0, output.length() - 1);
        }else{
            output = "";
        }
        return output;
    }
    public Set<String> getFriends(){
        return friends;
    }
    public void setStatus(int s){
        status = s;
    }
    public int getStatus(){
        return status;
    }
}