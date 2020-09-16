package com.momotenko.lab1.model.entity.weapons;

import com.momotenko.lab1.model.entity.Ammunition;

public class Spear extends Ammunition {
    public Spear(String name, double weight, double price) {
        super(name, weight, price);
    }

    @Override
    public String getType() {
        return "Spear";
    }
}
