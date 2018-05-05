package Classes;

import ForAdminister.Entry;
import Report.PostReport;
import io.goeasy.GoEasy;

import java.util.Collections;
import java.util.List;

public class Tools {
    public static List<PostReport> postSort(List<PostReport> list){
        Collections.sort(list, new DateComparator());
        return list;
    }
    public static List<Entry> historySort(List<Entry> list){
        Collections.sort(list, new HistoryComparator());
        return list;
    }
    public static void sendNotification(String username,String message){
        GoEasy goEasy = new GoEasy("http(s)://rest-virginia.goeasy.io","BC-c5940c1cbd8e438abc4000a2ff6aca71");
        goEasy.publish(username,message);
    }
}
