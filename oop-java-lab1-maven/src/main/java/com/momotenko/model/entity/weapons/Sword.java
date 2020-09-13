package com.momotenko.model.entity.weapons;

import com.momotenko.model.entity.Ammunition;

public class Sword extends Ammunition {
    public Sword(String name, double weight, double price) {
        super(name, weight, price);
    }

    @Override
    public String getType() {
        return "Sword";
    }
}
