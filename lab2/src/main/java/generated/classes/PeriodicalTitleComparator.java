package generated.classes;

import java.util.Comparator;

public class PeriodicalTitleComparator implements Comparator<Periodical> {
    @Override
    public int compare(Periodical o1, Periodical o2) {
        return o1.getTitle().compareTo(o2.getTitle());
    }
}
