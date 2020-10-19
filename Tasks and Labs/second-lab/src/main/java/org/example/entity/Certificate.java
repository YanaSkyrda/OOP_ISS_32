package org.example.entity;

public class Certificate {
    private int number;

    private int created;

    private int till;

    private String organization;

    public int getNumber() {
        return number;
    }

    public int getCreated() {
        return created;
    }

    public int getTill() {
        return till;
    }

    public String getOrganization() {
        return organization;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setCreated(int created) {
        this.created = created;
    }

    public void setTill(int till) {
        this.till = till;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }
}
