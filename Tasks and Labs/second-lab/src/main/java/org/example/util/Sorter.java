package org.example.util;

import org.example.entity.Drug;
import org.example.entity.Medicine;
import java.util.Comparator;

public class Sorter {

    private Sorter() {}
    /**
     * Sorts drugs by pharmes.
     */
    public static final Comparator<Drug> SORT_DRUGS_BY_PHARMS = (o1, o2) -> o1.getPharmName().compareTo(o2.getPharmName());

    /**
     * Sorts drugs by their periodicity.
     */
    public static final Comparator<Drug> SORT_DRUGS_BY_PERIODICITY = (o1, o2) -> o1.getVersion().getDosage().getPeriodicity() - o2.getVersion().getDosage().getPeriodicity();

    /**
     * Sorts drugs by names.
     */
    public static final Comparator<Drug> SORT_DRUGS_BY_NAMES = (o1, o2) -> o1.getName().compareTo(o2.getName());

    public static void sortDrugsByPharms(Medicine drugs) {
        drugs.getMedicineList().sort(SORT_DRUGS_BY_PHARMS);
    }

    public static void sortDrugsByPeriodicity(Medicine drugs) {
        drugs.getMedicineList().sort(SORT_DRUGS_BY_PERIODICITY);
    }

    public static void sortDrugsByNames(Medicine drugs) {
        drugs.getMedicineList().sort(SORT_DRUGS_BY_NAMES);
    }

}
