package com.momotenko.model.entity.weapons;

import com.momotenko.model.entity.Ammunition;

public class Arrow extends Ammunition {
    public Arrow(String name, double weight, double price) {
        super(name, weight, price);
    }

    @Override
    public String getType() {
        return "Arrow";
    }
}
