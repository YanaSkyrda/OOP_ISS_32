package org.example.entity;

import java.util.ArrayList;
import java.util.List;

public class Medicine {
    List<Drug> medicineList;

    public List<Drug> getMedicineList() {
        if(medicineList == null){
            medicineList = new ArrayList<>();
        }
        return medicineList;
    }

    @Override
    public String toString() {
        if (medicineList == null || medicineList.isEmpty()) {
            return "Test contains no questions";
        }
        StringBuilder result = new StringBuilder();
        for (Drug drug : medicineList) {
            result.append(drug).append('\n');
        }
        return result.toString();
    }
}
