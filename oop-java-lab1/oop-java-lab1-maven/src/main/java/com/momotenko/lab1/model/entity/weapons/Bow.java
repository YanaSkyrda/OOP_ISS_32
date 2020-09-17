package com.momotenko.lab1.model.entity.weapons;

import com.momotenko.lab1.model.entity.Ammunition;

/**
 * Class for reflecting bow of the knight
 */
public class Bow extends Ammunition {
    /** Constructor such as super class has */
    public Bow(String name, double weight, double price) {
        super(name, weight, price);
    }

    /** Overloaded getter for type returning the name of the class */
    @Override
    public String getType() {
        return this.getClass().getSimpleName();
    }
}
