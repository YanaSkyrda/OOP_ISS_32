package com.momotenko.lab1.model.entity.weapons;

import com.momotenko.lab1.model.entity.Ammunition;

public class Bow extends Ammunition {
    public Bow(String name, double weight, double price) {
        super(name, weight, price);
    }

    @Override
    public String getType() {
        return "Bow";
    }
}
