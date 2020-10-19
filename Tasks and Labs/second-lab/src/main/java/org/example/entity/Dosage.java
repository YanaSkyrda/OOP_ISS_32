package org.example.entity;

public class Dosage {
    private double dosageAmount;

    private int periodicity;

    public double getAmount() {
        return dosageAmount;
    }

    public int getPeriodicity() {
        return periodicity;
    }

    public void setAmount(double amount) {
        this.dosageAmount = amount;
    }

    public void setPeriodicity(int periodicity) {
        this.periodicity = periodicity;
    }
}
