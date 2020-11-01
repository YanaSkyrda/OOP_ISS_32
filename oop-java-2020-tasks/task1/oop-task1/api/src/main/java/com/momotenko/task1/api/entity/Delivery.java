package com.momotenko.task1.api.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class Delivery implements Serializable {
    private static final long serialVersionUID = 1L;

    private String city;
    private String sender;
    private String receiver;
    private BigDecimal cost;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public Delivery() {
    }

    public Delivery(String city, String sender, String receiver, BigDecimal cost) {
        this.city = city;
        this.sender = sender;
        this.receiver = receiver;
        this.cost = cost;
    }

    public Delivery(Delivery toCopy){
        this.city = toCopy.city;
        this.sender = toCopy.sender;
        this.receiver = toCopy.receiver;
        this.cost = toCopy.cost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Delivery delivery = (Delivery) o;
        return Objects.equals(city, delivery.city) &&
                Objects.equals(sender, delivery.sender) &&
                Objects.equals(receiver, delivery.receiver) &&
                Objects.equals(cost, delivery.cost);
    }

    @Override
    public int hashCode() {
        return Objects.hash(city, sender, receiver, cost);
    }

    @Override
    public String toString() {
        return "Delivery{" +
                "city='" + city + '\'' +
                ", sender='" + sender + '\'' +
                ", receiver='" + receiver + '\'' +
                ", cost=" + cost +
                '}';
    }
}
