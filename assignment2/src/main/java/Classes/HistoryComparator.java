package Classes;

import ForAdminister.Entry;

import java.util.Comparator;
import java.util.Date;

public class HistoryComparator implements Comparator {
    public int compare(Object object1,Object object2 ){
        Entry post1 = (Entry) object1;
        Entry post2 = (Entry)object2;
        Date date1 = post1.getDate();
        Date date2 = post2.getDate();
        return date2.compareTo(date1);
    }
}
