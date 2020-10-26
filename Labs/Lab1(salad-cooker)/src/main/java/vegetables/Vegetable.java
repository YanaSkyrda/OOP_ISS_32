package vegetables;

import vegetables.exceptions.NegativeCaloriesAmount;

import java.util.Objects;

public abstract class Vegetable {
    private float calories;
    private String name;
    private Color color;

    public Vegetable(String name, Color color, float calories) {
        this.name = name;
        this.color = color;
        this.calories = calories;
    }

    public float getCalories() {
        return calories;
    }

    public void setCalories(float calories) throws NegativeCaloriesAmount {
        if (calories <= 0) {
            throw new NegativeCaloriesAmount("Attempt to change calories amount to negative number.");
        }
        this.calories = calories;
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vegetable vegetable = (Vegetable) o;
        return Float.compare(vegetable.calories, calories) == 0 &&
                name.equals(vegetable.name) &&
                color == vegetable.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(calories, name, color);
    }
}
