package model;

public class Component {
    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTypeOfMeasure() {
        return typeOfMeasure;
    }

    public void setTypeOfMeasure(String typeOfMeasure) {
        this.typeOfMeasure = typeOfMeasure;
    }

    private Integer amount;
    private String name;
    private String typeOfMeasure;
}
