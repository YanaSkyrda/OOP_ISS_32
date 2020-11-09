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
}
