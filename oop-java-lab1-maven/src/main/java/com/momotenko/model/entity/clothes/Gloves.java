package com.momotenko.model.entity.clothes;

import com.momotenko.model.entity.Ammunition;

public class Gloves extends Ammunition {
    public Gloves(String name, double weight, double price) {
        super(name, weight, price);
    }

    @Override
    public String getType() {
        return "Gloves";
    }
}
