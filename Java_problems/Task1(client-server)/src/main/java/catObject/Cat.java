package catObject;

import java.io.Serializable;

public class Cat implements Serializable {
    private String name;
    private int age;

    public Cat(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public void meow() {
        System.out.print("meow");
    }

    @Override
    public String toString() {
        return "cat's name: "+ name + ", cat's age: " + age + ".";
    }
}
