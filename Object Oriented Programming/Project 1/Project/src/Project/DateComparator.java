package Project;

import java.util.Comparator;

//Part 3 - sort date

public class DateComparator implements Comparator<CloseContact>{

    @Override
    public int compare(CloseContact cc1, CloseContact cc2) {
        String d1 = cc1.getDate();
        String d2 = cc2.getDate();
        return d1.compareTo(d2);
    }
}