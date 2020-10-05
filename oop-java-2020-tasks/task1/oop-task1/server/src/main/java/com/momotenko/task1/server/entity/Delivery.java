package com.momotenko.task1.server.entity;

import java.io.Serializable;
import java.math.BigDecimal;

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
