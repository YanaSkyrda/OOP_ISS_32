package org.example.entity;

import java.util.ArrayList;
import java.util.List;

public class Medicine {
    private List<Drug> drugList;

    public List<Drug> getDrugList() {
        if(drugList == null){
            drugList = new ArrayList<>();
        }
        return drugList;
    }

    @Override
    public String toString() {
        if (drugList == null || drugList.isEmpty()) {
            return "Test contains no questions";
        }
        StringBuilder result = new StringBuilder();
        for (Drug drug : drugList) {
            result.append(drug).append('\n');
        }
        return result.toString();
    }
}
