package com.momotenko.model.entity.clothes;

import com.momotenko.model.entity.Ammunition;

public class Boots extends Ammunition {
    public Boots(String name, double weight, double price) {
        super(name, weight, price);
    }

    @Override
    public String getType() {
        return "Boots";
    }
}
