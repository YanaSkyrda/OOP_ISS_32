package model;

import java.util.Comparator;
import java.util.List;


public class CandyComparator implements Comparator<Candy> {

    @Override
    public int compare(Candy o1, Candy o2) {
       return o1.getEnergy().compareTo(o2.getEnergy());
    }
}