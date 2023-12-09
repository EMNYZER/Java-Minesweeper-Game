package minesweeper;

import java.util.Comparator;

public class TimeComparator implements Comparator<Time> {
    @Override
    public int compare(Time a, Time b) {
        int timeA = a.getTimeValue();
        int timeB = b.getTimeValue();

        if (timeA > timeB)
            return 1;
        else if (timeA < timeB)
            return -1;
        else
            return 0;
    }                        
}
