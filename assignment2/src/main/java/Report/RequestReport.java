package Report;

public class RequestReport extends Report{
    private String targetusername;
    private String request;//delete or add
    public RequestReport(){
        ;
    }
    public RequestReport(String u, String t, String r,String d){
        this.username = u;
        targetusername = t;
        request = r;
        this.date = d;
    }
    public void setTargetusername(String t){
        targetusername= t;
    }
    public String getTargetusername(){
        return targetusername;
    }
    public void setRequest(String r){
        request = r;
    }
    public String getRequest(){
        return request;
    }
}
