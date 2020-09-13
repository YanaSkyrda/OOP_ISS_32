package com.momotenko.model.entity;

public abstract class Ammunition {
    private double weight;
    private double price;

    public Ammunition(double weight, double price){
        this.weight = weight;
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public double getWeight() {
        return weight;
    }

    public Ammunition setPrice(double price) {
        this.price = price;
        return this;
    }

    public Ammunition setWeight(double weight) {
        this.weight = weight;
        return this;
    }
}
