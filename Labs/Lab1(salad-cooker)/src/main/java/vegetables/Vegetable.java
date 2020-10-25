package vegetables;

import vegetables.exceptions.NegativeCaloriesAmount;

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
}
