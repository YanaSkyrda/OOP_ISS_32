package model;

import java.util.Collections;
import java.util.List;

public class Utils {
    public static void sortCandies(List<Candy> candies) {
        Collections.sort(candies, new CandyComparator());
    }
}
