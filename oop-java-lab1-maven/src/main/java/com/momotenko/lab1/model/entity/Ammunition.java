package com.momotenko.lab1.model.entity;

public abstract class Ammunition {
    private double weight;
    private double price;
    private String name;

    public Ammunition(String name,
                      double weight,
                      double price){
        this.name = name;
        this.weight = weight;
        this.price = price;
    }

    public abstract String getType();

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public double getWeight() {
        return weight;
    }

    public Ammunition setName(String name) {
        this.name = name;
        return this;
    }

    public Ammunition setPrice(double price) {
        this.price = price;
        return this;
    }

    public Ammunition setWeight(double weight) {
        this.weight = weight;
        return this;
    }

    @Override
    public String toString() {
        return getType() +
                ": name = " + name +
                ", weight = " + weight +
                ", price = " + price + ".";
    }
}
