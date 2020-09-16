package com.momotenko.lab1.model.entity.clothes;

import com.momotenko.lab1.model.entity.Ammunition;

public class Gloves extends Ammunition {
    public Gloves(String name, double weight, double price) {
        super(name, weight, price);
    }

    @Override
    public String getType() {
        return "Gloves";
    }
}
