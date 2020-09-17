package com.momotenko.lab1.model.entity.clothes;

import com.momotenko.lab1.model.entity.Ammunition;

/**
 * Class for reflecting helmet of the knight
 */
public class Helmet extends Ammunition {
    /** Constructor such as super class has */
    public Helmet(String name,
                  double weight,
                  double price) {
        super(name, weight, price);
    }

    /** Overloaded getter for type returning the name of the class */
    @Override
    public String getType() {
        return this.getClass().getSimpleName();
    }
}
