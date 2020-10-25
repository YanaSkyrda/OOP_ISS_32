package vegetables;

import java.util.Comparator;

public class CaloriesComparator implements Comparator<Vegetable> {
    @Override
    public int compare(Vegetable c1, Vegetable c2) {
        return  Float.compare(c1.getCalories(), c2.getCalories());
    }
}
