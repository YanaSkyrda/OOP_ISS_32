package com.momotenko.lab1.model.entity;

import java.util.Objects;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ammunition that = (Ammunition) o;
        return Double.compare(that.weight, weight) == 0 &&
                Double.compare(that.price, price) == 0 &&
                name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(weight, price, name);
    }

    @Override
    public String toString() {
        return getType() +
                ": name = " + name +
                ", weight = " + weight +
                ", price = " + price + ".";
    }
}
