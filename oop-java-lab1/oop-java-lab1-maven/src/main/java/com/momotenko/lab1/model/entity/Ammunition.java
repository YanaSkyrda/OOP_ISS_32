package com.momotenko.lab1.model.entity;

/**
 * Abstract class for ammunition.
 * Contains all classes required.
 * Getter for type has to be overloaded.
 */
public abstract class Ammunition {
    private double weight;
    private double price;
    private String name;

    /** Constructor for ammunition */
    public Ammunition(String name,
                      double weight,
                      double price){
        this.name = name;
        this.weight = weight;
        this.price = price;
    }

    /** Abstract method which lower classes have to implement returning the name of their classes */
    public abstract String getType();

    /** Getter for name */
    public String getName() {
        return name;
    }

    /** Getter for price */
    public double getPrice() {
        return price;
    }

    /** Getter for weight */
    public double getWeight() {
        return weight;
    }

    /** Setter for name*/
    public Ammunition setName(String name) {
        this.name = name;
        return this;
    }

    /** Setter for price */
    public Ammunition setPrice(double price) {
        this.price = price;
        return this;
    }

    /** Setter for weight */
    public Ammunition setWeight(double weight) {
        this.weight = weight;
        return this;
    }

    /** Overloaded custom toString method */
    @Override
    public String toString() {
        return getType() +
                ": name = " + name +
                ", weight = " + weight +
                ", price = " + price + ".";
    }
}
