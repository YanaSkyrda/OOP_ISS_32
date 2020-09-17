package com.momotenko.lab1.model.entity.clothes;

import com.momotenko.lab1.model.entity.Ammunition;

/**
 * Class for reflecting boots of the knight
 */
public class Boots extends Ammunition {
    /** Constructor such as super class has */
    public Boots(String name, double weight, double price) {
        super(name, weight, price);
    }

    /** Overloaded getter for type returning the name of the class */
    @Override
    public String getType() {
        return this.getClass().getSimpleName();
    }
}
