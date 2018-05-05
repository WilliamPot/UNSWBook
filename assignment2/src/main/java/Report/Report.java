package Report;

public class Report {
    protected String date;//xxxx-xx-xx
    protected String username;
    public Report(){
        ;
    }
    public Report(String u, String d){
        username = u;
        date = d;
    }
    public void setDate(String d){
        date = d;
    }
    public String getDate(){
        return date;
    }
    public void setUsername(String u){
        username= u;
    }
    public String getUsername(){
        return username;
    }
}
