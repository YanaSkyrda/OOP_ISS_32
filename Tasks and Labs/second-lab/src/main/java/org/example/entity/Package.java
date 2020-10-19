package org.example.entity;

public class Package {
    private String packtype;

    private int amount;

    private double price;

    public String getType() {
        return packtype;
    }

    public int getAmount() {
        return amount;
    }

    public double getPrice() {
        return price;
    }

    public void setType(String type) {
        this.packtype = type;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
