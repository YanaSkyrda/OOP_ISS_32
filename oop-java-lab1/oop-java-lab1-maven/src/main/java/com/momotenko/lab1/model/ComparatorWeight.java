package com.momotenko.lab1.model;

import com.momotenko.lab1.model.entity.Ammunition;

import java.util.Comparator;

/**
 * This class implements Comparator interface to provide the ability
 * to sort Ammunition by its weight.
 */
public class ComparatorWeight implements Comparator<Ammunition> {
    /** Overloaded method from Comparator interface for custom comparing */
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
