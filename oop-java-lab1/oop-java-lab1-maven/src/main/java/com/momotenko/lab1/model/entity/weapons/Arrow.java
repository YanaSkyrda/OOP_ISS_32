package com.momotenko.lab1.model.entity.weapons;

import com.momotenko.lab1.model.entity.Ammunition;

/**
 * Class for reflecting arrow of the knight
 */
public class Arrow extends Ammunition {
    /** Constructor such as super class has */
    public Arrow(String name, double weight, double price) {
        super(name, weight, price);
    }

    /** Overloaded getter for type returning the name of the class */
    @Override
    public String getType() {
        return this.getClass().getSimpleName();
    }
}
