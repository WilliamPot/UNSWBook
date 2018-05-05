package Report;

import Report.Report;

import java.util.HashSet;
import java.util.Set;

public class PostReport extends Report {
    private String text;
    private String picturepath;
    private Set<String> wholikes = new HashSet<String>();
    public PostReport(){
        this.username = null;
        text = null;
        picturepath = null;
        this.date = null;;
    }
    public PostReport(String u,String t,String p,String d){
        this.username = u;
        text = t;
        picturepath = p;
        this.date = d;
    }
    public void setText(String t){
        text= t;
    }
    public String getText(){
        return text;
    }
    public String getNoty(){
        if(text.length()>15){
            return text.substring(0,15)+"...";
        }else {
            return text;
        }
    }
    public void setPicturepath(String p){
        picturepath= p;
    }
    public String getPicturepath(){
        return picturepath;
    }
    public void addLike(String u){//add username not name
        wholikes.add(u);
    }
    public void removeLike(String u){
        wholikes.remove(u);
    }
    public String groupLikes(){
        String output = "";
        for (String str : wholikes) {
            output += str+",";
        }
        if(output.length()>0) {
            output = output.substring(0, output.length() - 1);
        }else{
            output = "";
        }
        return output;
    }
    public Set<String> getWholikes(){
        return wholikes;
    }
    public void setWholikes(String f){
        if(!f.equals("")) {
            String[] friendlist = f.split(",");
            for (int i = 0; i < friendlist.length; i++) {
                wholikes.add(friendlist[i]);
            }
        }
    }
    public int getLikesnum(){
        return wholikes.size();
    }
    public String getWholikesstring(){
        String output = "";
        for (String str : wholikes) {
            output += str+",";
        }
        if(output.length()>0) {
            output = output.substring(0, output.length() - 1);
        }else{
            output = " ";
        }
        return output;
    }
}
