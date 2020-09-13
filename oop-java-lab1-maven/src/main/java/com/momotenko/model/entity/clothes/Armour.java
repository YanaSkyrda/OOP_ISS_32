package com.momotenko.model.entity.clothes;

import com.momotenko.model.entity.Ammunition;

public class Armour extends Ammunition {
    private String name;
    private String type;

    public Armour(String name, String type, double weight, double price){
        super(weight, price);
        this.name = name;
        this.type = type;
    }
}
