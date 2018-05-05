package ForAdminister;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Entry implements Comparable<Entry>{
    private String type;
    private String message;
    private String picturepath;
    private String targetuser;
    private Date date;
    private String action;
    private String sensitive;
    public Entry(){

    }

    public Entry(String t, String m, String pp, String tu, Date d, String a){
        type = t;
        message = m;
        picturepath = pp;
        targetuser = tu;
        date = d;
        action = a;
    }

    public void setType(String t){
        type = t;
    }

    public String getType(){
        return type;
    }

    public void setMessage(String m){
        message = m;
    }

    public String getMessage(){
        return message;
    }

    public void setPicturePath(String pp){
        picturepath = pp;
    }

    public String getPicturePath(){
        return picturepath;
    }

    public void setTargetUser(String tu){
        targetuser = tu;
    }

    public String getTargetUser(){
        return targetuser;
    }

    public void setDate(Date d){
        date = d;
    }

    public Date getDate(){
        return date;
    }

    public void setAction(String a){
        action = a;
    }
    public void setSensitive(String s){
        sensitive = s;
    }
    public String getSensitive(){
        return sensitive;
    }

    public String getAction(){
        return action;
    }

    public String getDateToString(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = format.format(date);
        return str;
    }

    public int compareTo(Entry en) {
        if(this.date.getTime()>en.date.getTime()){
            return -1;
        }else if(this.date.getTime()<en.date.getTime()){
            return 1;
        }else{
            return 0;
        }
    }
}
