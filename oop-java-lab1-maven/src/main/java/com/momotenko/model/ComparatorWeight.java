package com.momotenko.model;

import com.momotenko.model.entity.Ammunition;

import java.util.Comparator;

public class ComparatorWeight implements Comparator<Ammunition> {
    @Override
    public int compare(Ammunition o1, Ammunition o2){
        if (o1.getWeight() == o2.getWeight()){
            return 0;
        }
        else if (o1.getWeight() > o2.getWeight()){
            return 1;
        }
        else{
            return -1;
        }
    }
}
