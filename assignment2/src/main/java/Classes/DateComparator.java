package Classes;

import Report.PostReport;

import java.sql.Timestamp;
import java.util.Comparator;

public class DateComparator implements Comparator {
    public int compare(Object object1,Object object2 ){
        PostReport post1 = (PostReport)object1;
        PostReport post2 = (PostReport)object2;
        Timestamp date1 = Timestamp.valueOf(post1.getDate());
        Timestamp date2 = Timestamp.valueOf(post2.getDate());
        return date2.compareTo(date1);
    }
}
