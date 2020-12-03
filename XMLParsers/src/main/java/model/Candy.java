package model;

import java.util.ArrayList;
import java.util.List;

public class Candy {
    private String name;
    private String id;
    private Integer energy;
    private List<Types> types = new ArrayList<>();
    private Value value;
    private List<Component> components = new ArrayList<>();





    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Types> getTypes() {
        return types;
    }

    public void setTypes(List<Types> types) {
        this.types = types;
    }

    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
    }

    public List<Component> getComponents() {
        return components;
    }

    public void setComponents(List<Component> components) {
        this.components = components;
    }

    public Integer getEnergy() {
        return energy;
    }

    public void setEnergy(Integer energy) {
        this.energy = energy;
    }

}
