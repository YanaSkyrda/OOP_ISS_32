package model;

import java.io.Serializable;

/**
 * main.model.Cat class is used as victim of serialize and deserialize processes
 */
public class Cat implements Serializable {
    private String name;
    private String color;
    private Integer age;

    public Cat(){
        this.name = "";
        this.color = "";
        this.age = 1;
    }

    public Cat(String name, String color, Integer age) {
        this.name = name;
        this.color = color;
        if(age <= 0)
            this.age = 1;
        else
            this.age = age;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public Integer getAge() {
        return age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setAge(Integer age) {
        if(age <= 0)
            this.age = 1;
        else
            this.age = age;
    }

    @Override
    public String toString() {
        return "model.Cat{" +
                "name='" + name + '\'' +
                ", color='" + color + '\'' +
                ", age=" + age +
                '}';
    }

    public boolean equals(Cat obj) {
        if(this.age.equals(obj.age) && this.color.equals(obj.color) && this.name.equals(obj.name))
            return true;
        return false;
    }
}
