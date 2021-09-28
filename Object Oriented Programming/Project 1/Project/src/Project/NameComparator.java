package Project;

import java.util.Comparator;

//Part 3 - sort name

public class NameComparator implements Comparator<CloseContact>{

    @Override
    public int compare(CloseContact cc1, CloseContact cc2) {
        Contact c1 = cc1.getContact();
        Contact c2 = cc2.getContact();
        return c1.compareTo(c2);
    }
}